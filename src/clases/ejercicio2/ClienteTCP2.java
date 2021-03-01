/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.ejercicio2;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author depot
 */
public class ClienteTCP2 {
    
    Socket cliente;
    OutputStream salida;
    InputStream entrada;
    DataOutputStream datos_salida;
    DataInputStream datos_entrada;
    Scanner scanner;

    public ClienteTCP2(){
        
        try {
            cliente = new Socket("127.0.0.1",1026);//Hacemos la conexion del cliente con el servidor
            salida = cliente.getOutputStream();
            entrada = cliente.getInputStream();
            
            //Enlazamos el flujo de e/s con los objetos de tipo 'dato'
            datos_entrada = new DataInputStream(entrada);
            datos_salida = new DataOutputStream(salida);
            
            //imprimimos mensaje que manda el servidor
            String msg_servidor = datos_entrada.readUTF();
            System.out.println(msg_servidor);
            
            //Para leer desde la consola de salida
            scanner = new Scanner(System.in);
            
            System.out.println("Escribe un mensaje: ");
            String mensaje;
            while(true){
              //obtenemos lo que se escribió en consola  
              mensaje = scanner.nextLine();
              if(!mensaje.equalsIgnoreCase("exit")){//mientras lo que escriba sea diferente de exit
                  datos_salida.writeUTF(mensaje);//Mandamos el mensaje al servidor
              }else{
                  break;
              }
            }
            
            //TODO
            //Funcionamiento BufferedReader, BufferedWriter, PrintReader y PrintWriter
            
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }
      
    }
    
    public static void main(String args[]){
        ClienteTCP2 cliente1 = new ClienteTCP2();
    }
    
    
}
