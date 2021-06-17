/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad5;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author depot
 */
public interface OperacionesArchivos extends Remote{
    //Para obtener los archivos
    ArrayList<String> getArchivos() throws RemoteException;
    //Para descargar el archivo
    byte[] getArchivo(String name) throws RemoteException;
    //Buscar el archivo
    String[] getArchivoEncontrado(String name) throws RemoteException;
}
