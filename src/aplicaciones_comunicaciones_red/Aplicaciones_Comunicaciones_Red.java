/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicaciones_comunicaciones_red;

import java.rmi.RemoteException;
import proyecto_final_ahorcado.gui.JFrameAdivinador;
import proyecto_final_ahorcado.gui.JFramePalabra;
import proyecto_final_ahorcado.rmi.ChangerUI;
import proyecto_final_ahorcado.rmi.ClienteAhorcado;
import proyecto_final_ahorcado.rmi.ListenerBotonAdivinador;
import proyecto_final_ahorcado.rmi.ServidorAhorcado;

/**
 *
 * @author depot
 */
public class Aplicaciones_Comunicaciones_Red {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, RemoteException {
        ServidorAhorcado servidor = new ServidorAhorcado();
        servidor.runServer();
        
        
        ClienteAhorcado palabra = new ClienteAhorcado(1);
        ClienteAhorcado adivinador = new ClienteAhorcado(2);
        
        ChangerUI changer = new ChangerUI();
        
        JFramePalabra aux = new JFramePalabra(palabra,changer);
        JFrameAdivinador aux2 = new JFrameAdivinador(adivinador,changer);
        
        
    }
    
}
