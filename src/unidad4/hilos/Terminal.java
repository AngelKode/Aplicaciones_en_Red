/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad4.hilos;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author depot
 */
public class Terminal extends Thread{
    
    private final BD baseDatos;
    
    public Terminal(String nombre, BD bd){
        super(nombre);
        this.baseDatos = bd;
    }
    
    @Override
    public synchronized void run(){
        for(int i = 0; i<10;i++){
            this.baseDatos.addContador();
            System.out.println(this.getName()+": " + this.baseDatos.getContador());
            try {
                long tiempo = (long)Math.random() * 3000;
                this.wait(tiempo);
            } catch (InterruptedException ex) {
               
            }
        }
    }
}
