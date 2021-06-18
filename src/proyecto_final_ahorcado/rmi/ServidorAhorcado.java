/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_ahorcado.rmi;

import java.net.MalformedURLException;
import java.net.SocketException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import unidad5.OperacionesArchivos;
import unidad5.RemoteObjectArchivosRMI;

/**
 *
 * @author 200an
 */
public class ServidorAhorcado{

    public void runServer() {
       try {
            InstruccionesAhorcado ahorcado = new RemoteObjectAhorcado();
            LocateRegistry.createRegistry(1099);
            Naming.bind("rmi://localhost:1099/ahorcado", ahorcado);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (SocketException ex) {} catch (AlreadyBoundException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }
    
}
