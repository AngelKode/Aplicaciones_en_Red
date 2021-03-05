/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.ejercicio3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author depot
 */
public class ServidorArchivos {
    
    ServerSocket servidor;
    Socket cliente;
    InputStream entrada;
    OutputStream salida;
    DataInputStream datos_entrada;
    DataOutputStream datos_salida;
    
    //Para escritura del archivo
    FileOutputStream escritura_archivo;

    public ServidorArchivos(){
        
        try {
            servidor = new ServerSocket(1028);
            System.out.println("Servidor activo en el puerto:" + servidor.getLocalPort());
            
            while(true){
                cliente = servidor.accept();//Aceptamos al cliente
                //Obtenemos los flujos del cliente
                entrada = cliente.getInputStream();
                salida = cliente.getOutputStream();
                //Flujos de salida para poder manipularlos
                datos_entrada = new DataInputStream(entrada);
                datos_salida = new DataOutputStream(salida);
                //Leemos el tamaño del archivo que mandó el cliente
                long size = datos_entrada.readLong();
                    System.out.println("Tamaño archivo recibido: " + size);
                //Obtenemos el nombre y extensión del archivo enviado
                String nombre_archivo = datos_entrada.readUTF();
                    System.out.println("Nombre del archivo nuevo: " + nombre_archivo);
                escritura_archivo = new FileOutputStream("archivos_servidor/" + nombre_archivo);//Elegimos donde se guardará
                int bytes = 0;
                byte buffer[] = new byte[(int)size];//hacemos un arreglo de bytes del mismo tamaño que los bytes del archivo que se envia

                while(size > 0 && (bytes = datos_entrada.read(buffer,0,buffer.length)) != -1){
                    //escribimos el archivo
                    escritura_archivo.write(buffer,0,bytes);
                    size -= bytes;//Para saber cuantos se han leido
                }
                //Cerramos el flujo
                escritura_archivo.close();
                cliente.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    
    public static void main(String args[]){
        ServidorArchivos servidor = new ServidorArchivos();
    }
    
}
