/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad3.practica6;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author depot
 */
public class ServidorDNS {
    
    private DatagramSocket servidor;
    private DatagramPacket recibe;
    private DatagramPacket envia;
    private byte[] buffer;
    
    public ServidorDNS() throws InterruptedException {
        
        try {
            
            this.servidor = new DatagramSocket(3000);
            System.out.println("Servidor activo en el puerto: " + 3000);
            
            while(true){
                String respuesta = recibirDatoCliente();
                mandarRespuestaCliente(respuesta);
            }

        } catch (SocketException e) {
            this.servidor.close();
        } catch(IOException e){
            this.servidor.close();
        }

    }
    
    public String recibirDatoCliente() throws IOException, InterruptedException{
        
        //creamos paquetes de datagrama para recibir el URL
        this.buffer = new byte[32768];
        this.recibe = new DatagramPacket(this.buffer,0,this.buffer.length);
        
        //Esperamos a un cliente que mande el URL
        System.out.println("Esperando mensaje del cliente");
        this.servidor.receive(this.recibe);
        String msg = new String(this.recibe.getData());
        
        //Esperamos 1.5 segundos
        Thread.sleep(1500);
        
        //Creamos objeto URL para evaluar la url del cliente
        URL urlHerramienta = null;
                
        //Verificamos si esta bien hecha la URL
        try {
            urlHerramienta = new URL(msg);
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
            return "URL mal formada";
        }
        
        //Obtenemos el host de la URL
        String host = urlHerramienta.getHost();
                
        //Verfificamos si el host es un dominio
        if(!isIP(host)){
            
            //Creamos un objeto URI para evaluar si está correcta la URL en caso de no ser una IP
            URI uri = null;
            
            try {
                uri = urlHerramienta.toURI();
            } catch (URISyntaxException e) {
                return "URL mal formada";
            }
             
            //Si es nulo, la url está mal escrita
            if(uri.getHost() != null){
                InetAddress address = InetAddress.getByName(host);
                
                if(urlHerramienta.getFile().contains("?")){
                    return ("\nProtocolo: " + urlHerramienta.getProtocol()+"\nHost: " + address.getHostAddress()+"\nArchivo a acceder: " + urlHerramienta.getFile().substring(0,urlHerramienta.getFile().indexOf('?')) +"\nPuerto del Cliente: " + urlHerramienta.getDefaultPort() + "\nQuery: " + urlHerramienta.getQuery());
                }else{
                    return ("\nProtocolo: " + urlHerramienta.getProtocol()+"\nHost: " + address.getHostAddress()+"\nArchivo a acceder: " + urlHerramienta.getFile() +"\nPuerto del Cliente: " + urlHerramienta.getDefaultPort() + "\nQuery: " + urlHerramienta.getQuery());
                }
                
            }
            return "Ingresa una URL valida";
        }
                
        //Verfificamos si el host es una IP
        if(isIP(host)){
            //IP correcta
            InetAddress address = InetAddress.getByName(host);
            
            if(urlHerramienta.getFile().contains("?")){
                    return ("\nProtocolo: " + urlHerramienta.getProtocol()+"\nHost: " + address.getHostName()+"\nArchivo a acceder: " + urlHerramienta.getFile().substring(0,urlHerramienta.getFile().indexOf('?')) +"\nPuerto del Cliente: " + urlHerramienta.getDefaultPort() + "\nQuery: " + urlHerramienta.getQuery());
                }else{
                    return ("\nProtocolo: " + urlHerramienta.getProtocol()+"\nHost: " + address.getHostName()+"\nArchivo a acceder: " + urlHerramienta.getFile() +"\nPuerto del Cliente: " + urlHerramienta.getDefaultPort() + "\nQuery: " + urlHerramienta.getQuery());
                }
        }
                
        //IP incorrecta
        this.buffer = new byte[1024];
        return ("Incorrecta");                
    }
    
    private void mandarRespuestaCliente(String dato) throws IOException{
        //Ahora mandamos mensaje al cliente
        this.buffer = dato.getBytes();
        this.envia = new DatagramPacket(this.buffer,0,this.buffer.length,this.recibe.getAddress(),this.recibe.getPort());
        this.servidor.send(this.envia);
        System.out.println("------------------------------");
        System.out.println("Mensaje enviado al cliente");
        System.out.println("------------------------------");
    }
    public boolean isIP(String data){
        //Verificar que la IP sea correcta
        Pattern patron = Pattern.compile("^((([0-9])|([1-9][0-9])|([1][0-9][0-9])|([2][0-4][0-9])|([2][5][0-5]))[.]){3}((([0-9])|([1-9][0-9])|([1][0-9][0-9])|([2][0-4][0-9])|([2][5][0-5])))$");
        //Checamos si es identificador o no
        Matcher matcher = patron.matcher(data.trim());
        return matcher.find();
    }
    
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        ServidorDNS servidor = new ServidorDNS();
    }
    
    
}
