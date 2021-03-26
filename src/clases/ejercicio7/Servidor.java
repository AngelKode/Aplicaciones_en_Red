/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.ejercicio7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author depot
 */
public class Servidor {
    private final ServerSocket servidor;
    private final Socket cliente;
    private final ObjectInputStream entrada;
    private ObjectOutputStream salida;

    public Servidor() throws IOException, ClassNotFoundException {
        this.servidor = new ServerSocket(1028);
        this.cliente = servidor.accept();
        this.entrada = new ObjectInputStream(this.cliente.getInputStream());
        Empleado e1 = (Empleado) this.entrada.readObject();
        System.out.println(e1.getNombre() + "\n" + e1.getEmail());
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Servidor serv = new Servidor();
    }
}
