/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad4.hilos.practica8;

/**
 *
 * @author depot
 */
public class Cliente{
    
    private final String nombre, peliculaVer;
    private final int cantidadBoletos,cantidadPagar;

    public Cliente(String nombre, String pelicula, int numBoletos){
        this.nombre = nombre;
        this.peliculaVer = pelicula;
        this.cantidadBoletos = numBoletos;
        this.cantidadPagar = this.cantidadBoletos * 60;
    }
    
    public int getCantidadBoletos(){
        return this.cantidadBoletos;
    }
    
    public int getCantidadPagar(){
        return this.cantidadPagar;
    }
    
    public synchronized String getNombre(){
        return this.nombre;
    }
    
    public String getPelicula(){
        return this.peliculaVer;
    }
}
