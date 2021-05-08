/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad4.hilos;

/**
 *
 * @author depot
 */
public class BD {
    
    private int contador;

    public BD() {
        this.contador = 0;
    }
    
    
    public synchronized int getContador(){
        return this.contador;
    }
    
    public void addContador(){
        this.contador++;
    }
    
    public static void main(String[] args) {
        BD aux = new BD();
        //Compartiendo el recurso
        Terminal t1 = new Terminal("Hilo 1", aux);
        Terminal t2 = new Terminal("Hilo 2", aux);
        t1.start();
        t2.start();
    }
    
}

