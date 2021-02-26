/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.ejercicio1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author depot
 */
public class ClienteTCP1 {
    
    Socket cliente;
    OutputStream salida;
    InputStream entrada;

    public ClienteTCP1(){
        
        try {
            cliente = new Socket("127.0.0.1",1025);//Hacemos la conexion del cliente con el servidor
            salida = cliente.getOutputStream();
            entrada = cliente.getInputStream();
            
            //Recibir lo que mandó el servidor
            byte mensaje[] = new byte[20];
            int tamañoBytes = entrada.read(mensaje);
            System.out.println("No de bytes: " + tamañoBytes + "\nEl servidor envia: " + new String(mensaje));
            
            //Devolver mensaje al servidor
            String saludo = "Hola servidor";
            salida.write(saludo.getBytes());
            
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }
      
    }
    
    public static void main(String args[]){
        ClienteTCP1 cliente1 = new ClienteTCP1();
    }
    
    
}
