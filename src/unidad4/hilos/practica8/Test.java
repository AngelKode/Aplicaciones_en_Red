/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad4.hilos.practica8;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author depot
 */
public class Test{
    //Creamos el pool
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(10,10,50,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
    
        //Creamos los arreglos
        Cliente clientes[] = new Cliente[5];
        clientes[0] = new Cliente("Pedro", "La Llorona", 6);
        clientes[1] = new Cliente("Juan", "Transformers", 1);
        clientes[2] = new Cliente("Luis", "Los Increibles", 2);
        clientes[3] = new Cliente("Hector", "La Llorona", 4);
        clientes[4] = new Cliente("Roberto", "La Llorona", 3);
        
        Cajera cajeras[] = new Cajera[5];
        cajeras[0] = new Cajera("Juana");
        cajeras[1] = new Cajera("Lupe");
        cajeras[2] = new Cajera("Luis");
        cajeras[3] = new Cajera("Pedro");
        cajeras[4] = new Cajera("Fer");
        
        boolean atendido = false;
        for(int i = 0; i < 5; i++){
            //Checamos cual cajera está lista
            int j = 0;
            do{
                for(j = 0; j < 5; j++){
                    if(!cajeras[j].isOcupada()){
                        atendido = true;
                        break;
                    }
                }
            }while(!atendido);
            cajeras[j].setClienteNuevo(clientes[i]);
            pool.execute(cajeras[i]);
        }
        pool.shutdown();
        
        for(int i = 0; i < 5; i++){
            System.out.println("La cajera: " + cajeras[i].getName() + " atendió a ");
        }
        
    }
    
}
