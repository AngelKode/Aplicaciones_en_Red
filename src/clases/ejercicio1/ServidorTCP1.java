/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.ejercicio1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author depot
 */
public class ServidorTCP1 {
    
    ServerSocket servidor;
    Socket cliente;
    OutputStream salida;
    InputStream entrada;

    public ServidorTCP1() {
        
        try {
            servidor = new ServerSocket(1025); //puerto 1-65535, 1-1024 reservados
            cliente = servidor.accept();//aceptamos y asignamos el nuevo socket para el cliente concreto
            salida = cliente.getOutputStream();
            entrada = cliente.getInputStream();
            
            //Mandamos mensaje al cliente
            salida.write("Bienvenido Cliente!".getBytes());//le mandamos al cliente un mensaje
            
            //Obtenemos mensaje del cliente
            byte msg[] = new byte[15];
            int nob = entrada.read(msg);
            System.out.println("No de bytes: " + nob + "\nMensaje: " + new String(msg));
            
            entrada.close();
            salida.close();
            cliente.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    
    public static void main(String args[]){
        ServidorTCP1 serv1 = new ServidorTCP1();
    }
    
    
}
