/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad4.hilos.practica8;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author depot
 */
public class Cajera implements Runnable{
    
    int clientes_atendidos;
    long tiempoAtendiendo;
    String nombre;
    Cliente cliente;
    boolean ocupada;

    public Cajera(String nombre){
        this.nombre = nombre;
        this.clientes_atendidos = 0;
        this.cliente = null;
        this.tiempoAtendiendo = 0;
        this.ocupada = false;
    }
    
    public synchronized void setClienteNuevo(Cliente cliente){
        this.ocupada = true;
        this.cliente = cliente;
    }
    
    public String getName(){
        return this.nombre;
    }
    
    public int clientesAtendidos(){
        return this.clientes_atendidos;
    }
    
    public boolean isOcupada(){
        return this.ocupada;
    }
    
    @Override
    public synchronized void run() {
        this.clientes_atendidos++;
        System.out.println(this.cliente.getNombre());
        System.out.println("La cajera " + this.nombre + " comienza a atender al cliente: " + this.cliente.getNombre());
        long tiempoInicial = System.currentTimeMillis();
        try {
            Thread.sleep((long) (((long)(Math.random()) * (long)(10000)) + 1000));
        } catch (InterruptedException ex) {}
        System.out.println("Pelicula a ver: " + this.cliente.getPelicula());
        System.out.println("No de Boletos: " + this.cliente.getCantidadBoletos());
        System.out.println("Total: " + this.cliente.getCantidadPagar());
        this.tiempoAtendiendo = System.currentTimeMillis() - tiempoInicial;
        System.out.println("La cajera terminó en " + this.tiempoAtendiendo/1000 + " segundos");
        this.tiempoAtendiendo = 0;
        this.ocupada = false;
    }
 
}
