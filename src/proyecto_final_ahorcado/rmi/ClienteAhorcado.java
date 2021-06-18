/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_ahorcado.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import unidad5.OperacionesArchivos;

/**
 *
 * @author 200an
 */
public class ClienteAhorcado {
    
    InstruccionesAhorcado instrucciones;
    private int rol;
    
    public ClienteAhorcado(int rolCliente) {
        this.rol = rolCliente;
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        try {
            this.instrucciones = (InstruccionesAhorcado) Naming.lookup("rmi://localhost:1099/ahorcado");
        } catch (Exception e) {}
    }
    
    public void setPalabraAdivinar(String palabra) throws RemoteException{
        this.instrucciones.obtenerPalabraAdivinar(palabra);
    }
    
    public int getSizePalabraAdivinar() throws RemoteException{
        return this.instrucciones.getSizePalabra();
    }
    
    public boolean enviarRespuesta(String respuesta) throws RemoteException{
        this.instrucciones.enviarRespuesta(respuesta);
        return this.instrucciones.isCorrect();
    }
    
    public Object[] getLetrasCorrectas() throws RemoteException{
        return this.instrucciones.getLetrasCorrectas();
    }
    
    public int getCantidadErrores() throws RemoteException{
        return this.instrucciones.getCantidadErrores();
    }
    
    public boolean isCorrect() throws RemoteException{
        return this.instrucciones.isCorrect();
    }
    
    public void resetGame() throws RemoteException{
        this.instrucciones.resetGame();
    }
    
    public boolean isGameStart() throws RemoteException{
        return this.instrucciones.isGameStart();
    }
    
}
