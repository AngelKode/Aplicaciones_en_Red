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
public class Contenedor {
    
    private final int data[];
    private final boolean dataAvailable[];

    public Contenedor(int sizeArray) {
        this.dataAvailable = new boolean[sizeArray];
        this.data = new int[sizeArray];
    }
    
    
    public synchronized int getData(int posicion){
        while(!this.dataAvailable[posicion]){
            try {
               wait(); 
            } catch (InterruptedException e) {
            }
            
        }
        this.dataAvailable[posicion] = false;
        notifyAll();
        return this.data[posicion];
    }
    
    public synchronized void setData(int posicion, int data){
        while(this.dataAvailable[posicion]){
            try {
               wait(); 
            } catch (InterruptedException e) {
            }
        }
        this.data[posicion] = data;
        this.dataAvailable[posicion] = true;
        notifyAll();
    }
    
    public static void main(String[] args) {
        Contenedor cont = new Contenedor(11);
        Productor p1 = new Productor("P1", cont);
        Productor p2 = new Productor("P2", cont);
        Consumidor c1 = new Consumidor("C1", cont);
        Consumidor c2 = new Consumidor("C2", cont);
        p1.start();
        p2.start();
        c1.start();
        c2.start();
    }
    
}
