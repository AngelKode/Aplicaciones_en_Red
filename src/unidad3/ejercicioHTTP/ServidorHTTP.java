/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad3.ejercicioHTTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
    private PrintStream salidaServidor;

    public ServidorHTTP() {

        try {
            this.servidor = new ServerSocket(8080);
            System.out.println("Servidor HTTP escuchando en el puerto 8080");
            ejecutar();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void ejecutar() {
        while (true) {
            try {
                //Aceptamos un nuevo cliente
                this.cliente = this.servidor.accept();
                if (this.cliente != null) {
                    System.out.println("---------------------------------------------------------------------------");
                    BufferedReader entrada = new BufferedReader(new InputStreamReader(this.cliente.getInputStream()));
                    System.out.println("Cliente conectado correctamente desde el puerto: " + this.cliente.getPort());
                    String data = entrada.readLine();

                    if (data != null) {

                        if (!data.contains("iniciarSesion") && !data.contains("View")) {
                            //Obtenemos el recurso que se solicita
                            String recurso = data.substring(data.indexOf('/') + 1, data.lastIndexOf('/') - 5);
                            String metodo = data.substring(0, data.indexOf('/'));

                            System.out.println("Recurso: " + recurso);
                            System.out.println("Metodo: " + metodo);

                            while ((data = entrada.readLine()) != null) {
                                if (data.equals("")) {
                                    break;
                                }
                            }
                            responder(recurso, metodo);
                        } else {
                            if (data.contains("user=admin") && data.endsWith("passwd=1234")) {
                                responder("inicio.html", "GET");
                            } else if (data.contains("audioView")) {
                                responder("audio.mp3", "GET");
                            } else if (data.contains("videoView")) {
                                responder("video.mp4", "GET");
                            } else {
                                responder("error.html", "GET");
                            }
                        }
                        this.cliente.close();
                    }
                }
                this.cliente.close();
            } catch (IOException e) {
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("Error");
            }
        }
    }

    public void responder(String recurso, String metodo) throws IOException {

        String rutaIndex = "archivosHTTP/";
        switch (recurso) {
            case "index.html": {
                enviarArchivo(rutaIndex, recurso);
                break;
            }

            case "inicio.html": {
                enviarArchivo(rutaIndex, recurso);
                break;
            }

            case "error.html": {
                enviarArchivo(rutaIndex, recurso);
                break;
            }

            case "imagen.jpg": {
                enviarArchivo(rutaIndex, recurso);
                break;
            }

            case "video.mp4": {
                enviarMedia(rutaIndex, recurso,"video");
                break;
            }

            case "audio.mp3": {
                enviarMedia(rutaIndex, recurso,"audio");
                break;
            }

            default: {
                this.salidaServidor = new PrintStream(this.cliente.getOutputStream(), true);
                String messageError = "HTTP/1.1 404 File Not Found \r\n"
                        + "Content-type:text/html;charset=UTF-8\r\n\rn"
                        + "<!DOCTYPE html><html><body><h1>Página no encontrda</h1></body</html>";
                this.salidaServidor.write(messageError.getBytes());
                this.salidaServidor.close();
                break;
            }

        }
        this.cliente.close();
    }

    private void enviarArchivo(String ruta, String recurso) {
        try {
            this.salidaServidor = new PrintStream(this.cliente.getOutputStream(), true);
            File archivo = new File(ruta.concat(recurso));

            if (archivo.exists() && !archivo.isDirectory()) {

                if (!recurso.contains(".jpg")) {
                    this.salidaServidor.println("HTTP/1.1 200 OK\r\n"
                            + "Content-Type:text/html\r\n"
                            + "Content-Length: " + archivo.length()
                            + "\r\n");
                    FileInputStream streamData = new FileInputStream(archivo);
                    byte informacionHTML[] = new byte[streamData.available()];
                    streamData.read(informacionHTML);
                    this.salidaServidor.write(informacionHTML);
                    this.salidaServidor.close();
                    streamData.close();
                } else {
                    this.salidaServidor.println("HTTP/1.1 200 OK\r\n"
                            + "Content-Type:image/jpg\r\n"
                            + "Content-Length: " + archivo.length()
                            + "\r\n");
                    FileInputStream streamData = new FileInputStream(archivo);
                    byte informacionHTML[] = new byte[streamData.available()];
                    streamData.read(informacionHTML);
                    this.salidaServidor.write(informacionHTML);
                    this.salidaServidor.close();
                    streamData.close();
                }

            } else {
                String error = "HTTP/1.1 404 ERROR";
                this.salidaServidor.write(error.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void enviarMedia(String ruta, String recurso, String tipo) {
        try {
            File archivo = new File(ruta.concat(recurso));

            if (archivo.exists() && !archivo.isDirectory()) {
                FileInputStream streamData = new FileInputStream(archivo);
                byte informacionHTML[] = new byte[streamData.available()];
                streamData.read(informacionHTML);
                streamData.close();
                
                DataOutputStream data = new DataOutputStream(this.cliente.getOutputStream());
                if(tipo.equals("video")){
                    data.writeBytes("HTTP/1.1 200 OK \r\n"
                            + "Content-Type:video/mp4\r\n"
                            + "Content-Length: " + archivo.length()
                            + "\r\n\r\n");
                    data.write(informacionHTML);
                }else{
                    data.writeBytes("HTTP/1.1 200 OK \r\n"
                            + "Content-Type:audio/mp3\r\n"
                            + "Content-Length: " + archivo.length()
                            + "\r\n\r\n");
                    data.write(informacionHTML);
                }
                data.close();
            } else {
                DataOutputStream data = new DataOutputStream(this.cliente.getOutputStream());
                data.writeBytes("HTTP/1.1 404 File Not Found \r\n"
                        + "\r\n");
                data.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ServidorHTTP servidor = new ServidorHTTP();
    }

}
