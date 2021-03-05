/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.ejercicio3;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JFileChooser;
/**
 *
 * @author depot
 */
public class ClienteArchivos {
    
    Socket cliente;
    InputStream entrada;
    OutputStream salida;
    DataInputStream dato_entrada;
    DataOutputStream dato_salida;
    //manejo de archivos
    File archivo;
    FileInputStream entrada_archivo;//Me permite manipular el archivo

    public ClienteArchivos(String ip, int port) {
       
        try {
            cliente = new Socket(ip,port);
            entrada = cliente.getInputStream();
            salida = cliente.getOutputStream();
            
            dato_entrada = new DataInputStream(entrada);
            dato_salida = new DataOutputStream(salida);
            
            /*
            Para enviar archivos:
            1.- Leer el archivo a enviar
            2.- Enviar el archivo
            */
            //Escogemos el archivo
            JFileChooser escoger_archivo = new JFileChooser();
            escoger_archivo.showOpenDialog(null);//Abrimos la ventana para escoger
            //Si el usuario escogió un archivo obtenemos el archivo, si no, saltamos todo
            try {
                archivo = escoger_archivo.getSelectedFile();//Obtenemos el archivo seleccionado
                entrada_archivo = new FileInputStream(archivo);//para manipular el archivo
            
                long tam_archivo = archivo.length();//Obtenemos el tamaño del archivo

                //enviamos el tamaño del archivo al servidor
                dato_salida.writeLong(tam_archivo);
                //enviamos el nombre y la extension del archivo
                String nombre_archivo = archivo.getName();
                    System.out.println("Nombre del archivo: " + nombre_archivo);
                //Mandamos el nombre del archivo al servidor
                dato_salida.writeUTF(nombre_archivo);
                //Para guardar los bytes del archivo
                byte buffer[] = new byte[(int)tam_archivo];
                //Aqui guardaremos la cantidad de bytes que se estan leyendo
                int bytes = 0;
                //Leemos el archivo hasta EOF
                while((bytes = entrada_archivo.read(buffer)) != -1){
                    dato_salida.write(buffer,0,bytes);//mandamos a la salida
                    dato_salida.flush();//Limpiamos el buffer y mandamos al servidor
                }
            } catch (Exception e) {
                System.out.println("Error, no se eligió el archivo");
                e.printStackTrace();
            } 
            
            entrada_archivo.close();//cerramos la conexion del flujo con el archivo
            cliente.close();//cerramos el socket del cliente
            
        }catch(UnknownHostException e){
            e.printStackTrace(); 
        }catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    
    public static void main(String[] args) {
        ClienteArchivos cliente = new ClienteArchivos("127.0.0.1", 1028);
    }
    
    
}
