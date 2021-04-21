/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad3.ejercicioHTTP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author depot
 */
public class ServidorHTTP {
    
    private ServerSocket servidor;
    private Socket cliente;

    public ServidorHTTP() {
        
        try {
            this.servidor = new ServerSocket(8889);
            System.out.println("Servidor HTTP escuchando en el puerto 8080");
            ejecutar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    
    private void ejecutar(){
        while(true){
            try {
                //Aceptamos un nuevo cliente
                this.cliente = this.servidor.accept();
                if(this.cliente != null){
                    BufferedReader entrada = new BufferedReader(new InputStreamReader(this.cliente.getInputStream()));
                    System.out.println("Cliente conectado correctamente: " + this.cliente);
                    String data = entrada.readLine();
                    System.out.println(data);
                    System.out.println("----");
                    //Obtenemos el recurso que se solicita
                    String recurso = data.substring(data.indexOf('/') + 1, data.lastIndexOf('/') - 5);
                    String metodo = data.substring(0,data.indexOf('/'));
                    
                    System.out.println(recurso);
                    System.out.println(metodo);
                    
                    while((data = entrada.readLine()) != null){
                        if(data.equals("")){
                            break;
                        }
                    }
                    responder(recurso,metodo);
                }
            } catch (IOException e) {
            }  
        }
    }
    
    public void responder(String recurso, String metodo) throws IOException{
        
        String rutaIndex = "";
        try {
            PrintStream salida = new PrintStream(this.cliente.getOutputStream(),true); 
            File archivo = new File(rutaIndex.concat(recurso));
            System.out.println("Archivo a enviar: " + archivo);
            
            if(archivo.exists() && !archivo.isDirectory()){
                salida.println("HTTP/1.1 200 OK");
                salida.println("Content-Type: text/html; charset = UTF-8");
                try (FileInputStream reader = new FileInputStream(archivo)) {
                    byte data[] = new byte[reader.available()];
                    reader.read(data);
                    salida.write(data);
                    salida.close();
                }catch(IOException e){
                
                }
            }else{
                System.out.println("No se pudo");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.cliente.close();
    }
    public static void main(String[] args) {
        ServidorHTTP servidor = new ServidorHTTP();
    }
    
}
