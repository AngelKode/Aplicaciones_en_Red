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
public class Productor extends Thread{
    
    private Contenedor data;

    public Productor(String data, Contenedor c) {
        super(data);
        this.data = c;
    }
    
    @Override
    public  synchronized void run(){
        for(int i = 1; i <= 10; i++){
            this.data.setData(i, i);
            System.out.println(this.getName()+" pone " + i);
            try{
                Thread.sleep((int)Math.random()*100);
            }catch(InterruptedException e){     
            }
        }
    }
    
    
}
