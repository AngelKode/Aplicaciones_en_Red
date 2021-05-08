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
public class Consumidor extends Thread{
    
    private Contenedor data;

    public Consumidor(String target, Contenedor c) {
        super(target);
        this.data = c;
    }
    
    @Override
    public synchronized void run(){
        for(int i = 1; i <= 10; i++){
            System.out.println(this.getName()+":" + this.data.getData(i));
        }
    }
    
    
}
