/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.ejercicio7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author depot
 */
public class Cliente {
    private final Socket cliente;
    private ObjectInputStream entrada;
    private final ObjectOutputStream salida;

    public Cliente() throws IOException, UnknownHostException {
        this.cliente = new Socket("127.0.0.1",1028);
        this.salida = new ObjectOutputStream(this.cliente.getOutputStream());
        Empleado empleado = new Empleado("Juan","correo@live.com.mx");
        this.salida.writeObject(empleado);
        this.cliente.close();
        System.out.println("Se ha enviado el objeto");
    }
    
    public static void main(String[] args) throws IOException {
        Cliente cliente = new Cliente();
    }
    
    
}
