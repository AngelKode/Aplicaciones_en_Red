/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad5;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author depot
 */
public class RemoteObjectArchivosRMI extends UnicastRemoteObject implements OperacionesArchivos{
    
    private FileInputStream in;
    private DatagramSocket servidor;
    private DatagramPacket datos_enviados;   
    private SocketAddress ip_cliente;
    private byte[] buffer;
    private FileInputStream stream_in;
    private static String PATH = "servidor/";
    
    public RemoteObjectArchivosRMI() throws RemoteException, SocketException {
        super();
    }
    
    @Override
    public ArrayList<String> getArchivos() throws RemoteException {
       ArrayList<String> archivosDisponibles = new ArrayList<>();
       
       //Obtenemos la carpeta donde están los archivos
       File carpeta = new File(PATH);
       
       //Obtenemos cada nombre de los archivos
       for(File archivo : carpeta.listFiles()){
           //Checamos que sea un archivo
           if(archivo.isFile()){
               archivosDisponibles.add(archivo.getName());
           }
       }
       
       return archivosDisponibles;
    }

    @Override
    public byte[] getArchivo(String name) throws RemoteException {
        File file = new File(PATH+name);
        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(file);
            BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
            this.buffer = new byte[(int) file.length()];
            bufferedInput.read(this.buffer, 0, this.buffer.length);
            return this.buffer;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RemoteObjectArchivosRMI.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException e){
        
        }
        return null;
    } 

    @Override
    public String[] getArchivoEncontrado(String name) throws RemoteException {
        File carpeta = new File(PATH);
        
        for(File archivo : carpeta.listFiles()){
            if(archivo.isFile()){
                //Comparamos el archivo con el nombre
                String nombreAux = archivo.getName();
                String extension;
                extension = nombreAux.substring(nombreAux.indexOf('.'), nombreAux.length());
                nombreAux = nombreAux.substring(0, nombreAux.indexOf('.'));
                if(nombreAux.equals(name)){
                    return new String[]{nombreAux, extension};
                }
            }
        }
        return null;
    }
    
}
