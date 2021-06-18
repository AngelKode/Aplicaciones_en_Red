/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_ahorcado.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author 200an
 */
public interface InstruccionesAhorcado extends Remote{
    //Para obtener la palabra que se adivinará
    public void obtenerPalabraAdivinar(String palabra) throws RemoteException;
    //Para enviar la respuesta del cliente
    public void enviarRespuesta(String respuesta) throws RemoteException;
    //Obtener la cantidad de letras
    public int getSizePalabra() throws RemoteException;
    //Para checar la palabra
    public boolean isCorrect() throws RemoteException;
    //Obtener cantidad errores
    public int getCantidadErrores() throws RemoteException;
    //Obtener las letras correctas
    public Object[] getLetrasCorrectas() throws RemoteException;
    //Limpiamos todo
    public void resetGame() throws RemoteException;
    //Checamos si ya empezó el juego
    public boolean isGameStart() throws RemoteException;
}
