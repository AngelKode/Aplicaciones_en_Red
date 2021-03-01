/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.ejercicio2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author depot
 */
public class ServidorTCP2 {
    
    ServerSocket servidor;
    Socket cliente;
    OutputStream salida;
    InputStream entrada;
    DataOutputStream datos_salida;
    DataInputStream datos_entrada;

    public ServidorTCP2() {
        
        try {
            servidor = new ServerSocket(1026); //puerto 1-65535, 1-1024 reservados
            while(true){//Ciclo para que siempre esté a la escucha
               cliente = servidor.accept();//aceptamos y asignamos el nuevo socket para el cliente concreto
                salida = cliente.getOutputStream();
                entrada = cliente.getInputStream();

                //Enlazamos los flujos de entrada y salida con los objetos de tipo 'data'
                datos_entrada = new DataInputStream(entrada);
                datos_salida = new DataOutputStream(salida);
                
                //mandamos mensaje al cliente
                datos_salida.writeUTF("Bienvenido cliente");

                //imprimimos mensaje del cliente
                while(true){
                    try {
                       String mensaje = datos_entrada.readUTF();//Obtenemos lo que dice el cliente
                       System.out.println("El cliente dice: " + mensaje);
                    } catch (IOException e) {
                        System.out.println("El cliente salió");
                        break;
                    }
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    
    public static void main(String args[]){
        ServidorTCP2 serv1 = new ServidorTCP2();
    }
    
    
}
