/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author depot
 */
public class RMICliente {
    
    private ArrayList<String> archivos;
    private File archivo;
    private OperacionesArchivos operador_archivos;
    private DatagramSocket cliente;
    private DatagramPacket paquetes;
    private FileOutputStream stream_out;
    private byte[] buffer_data;

    public RMICliente() {
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        try {
            this.operador_archivos = (OperacionesArchivos) Naming.lookup("rmi://localhost:1099/archivos");
        } catch (Exception e) {}
    }
    
    public ArrayList<String> obtenerArchivos() throws RemoteException{
        this.archivos = this.operador_archivos.getArchivos();
        return this.operador_archivos.getArchivos();
    }
    
    public void obtenerArchivo(String name) throws RemoteException, SocketException, FileNotFoundException, IOException{
       String nombre = name.substring(0,name.lastIndexOf('.'));
       if(buscarArchivo(nombre) != null){
             byte[] bytes = this.operador_archivos.getArchivo(name);
             File file = new File("C:\\Users\\depot\\Desktop\\"+name);
             file.createNewFile();
             FileOutputStream in = new FileOutputStream(file);
             in.write(bytes);
             JOptionPane.showMessageDialog(null, "Archivo descargado completamente");
             in.close();
       }
    }
    
    public String[] buscarArchivo(String name) throws RemoteException{
        return this.operador_archivos.getArchivoEncontrado(name);
    }
}
