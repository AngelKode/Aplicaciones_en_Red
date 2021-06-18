/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_ahorcado.rmi;

import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author 200an
 */
public class RemoteObjectAhorcado extends UnicastRemoteObject implements InstruccionesAhorcado{
    
    private String palabraAdivinar;
    private String respuestaCliente;
    private int cantidadErrores;
    private boolean isGameStart;
    
    public RemoteObjectAhorcado()throws RemoteException, SocketException {
        super();
        this.palabraAdivinar = "";
        this.respuestaCliente = "";
        this.cantidadErrores = 0;
        this.isGameStart = false;
    }

    @Override
    public void obtenerPalabraAdivinar(String palabra) throws RemoteException {
        this.isGameStart = true;
        this.palabraAdivinar = palabra;
    }

    @Override
    public void enviarRespuesta(String respuesta) throws RemoteException {
        this.respuestaCliente = respuesta;
    }

    @Override
    public int getSizePalabra() throws RemoteException {
        while(!this.isGameStart){System.out.println(this.isGameStart);}//Hasta que se ingrese una palabra a adivinar
        
        return this.palabraAdivinar.length();
    }

    @Override
    public boolean isCorrect() throws RemoteException {
        if(!this.respuestaCliente.equalsIgnoreCase(this.palabraAdivinar)){
           this.cantidadErrores++;
        }
        return this.respuestaCliente.equals(this.palabraAdivinar);
    }

    @Override
    public int getCantidadErrores() throws RemoteException {
        return this.cantidadErrores;
    }

    @Override
    public Object[] getLetrasCorrectas() throws RemoteException {
        Object letrasCorrectas[] = new Object[this.palabraAdivinar.length()];
        
        //Checamos si está correcta la respuesta o no
        int ultimaPosicion = 0;
        for(int posicionCarater = 0; posicionCarater < this.respuestaCliente.length(); posicionCarater++){
            ultimaPosicion = posicionCarater;
            boolean correcto = this.palabraAdivinar.charAt(posicionCarater) == this.respuestaCliente.charAt(posicionCarater);
            
            Object valor = (correcto) ? this.palabraAdivinar.charAt(posicionCarater) + "" : false;
            letrasCorrectas[posicionCarater] = valor;
        }
        
        //En caso de que la respuesta sea más chica, rellenamos con false
        if(this.palabraAdivinar.length() > this.respuestaCliente.length()){
            for(int posicionRellenar = ultimaPosicion + 1; posicionRellenar < this.palabraAdivinar.length(); posicionRellenar++){
                letrasCorrectas[posicionRellenar] = false;
            }
        }
        
        return letrasCorrectas;
    }

    @Override
    public void resetGame() throws RemoteException {
        this.palabraAdivinar = "";
        this.respuestaCliente = "";
        this.cantidadErrores = 0;
        this.isGameStart = false;
    }

    @Override
    public boolean isGameStart() throws RemoteException {
        return this.isGameStart;
    }

    
}
