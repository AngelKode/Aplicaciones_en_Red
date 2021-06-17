/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad5;

import java.net.MalformedURLException;
import java.net.SocketException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author depot
 */
public class RMIServidor implements Runnable{

    @Override
    public void run() {
        
        try {
            OperacionesArchivos archivos = new RemoteObjectArchivosRMI();
        Registry registro = LocateRegistry.createRegistry(1099);
            Naming.bind("rmi://localhost:1099/archivos", archivos);
        } catch (RemoteException ex) {
        } catch (SocketException ex) {} catch (AlreadyBoundException ex) {
        } catch (MalformedURLException ex) {}
        
        
    }

}
