/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.ejercicio4;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author depot
 */
public class ArchivosNIO {
    
    FileChannel canal;
    ArrayList<ByteBuffer> buffers;
    private final static int size_buffer = 48;

    public ArchivosNIO() throws FileNotFoundException {
        this.canal = new RandomAccessFile("archivosNIO.txt", "rw").getChannel();//para escritura y lectura(rw) - (read write)
        this.buffers = new ArrayList<>();
    }
    
    public void escribir(String mensaje_escribir) throws IOException{
        
        if(mensaje_escribir.length() > size_buffer){
            //Obtenemos la division entre el tamaño del mensaje y la cantidad inicial
            double division = (double)mensaje_escribir.length()/size_buffer;
            //Obtenemos el numero menor que le sigue
            double floor_number = Math.floor(division);
            
            if(Double.compare(division, floor_number) == 0){
                createBuffers(mensaje_escribir, floor_number);
            }else{
                createBuffers(mensaje_escribir, floor_number + 1);
            }
            
        }else{
            this.buffers.add(ByteBuffer.allocate(size_buffer));
            this.buffers.get(0).clear();//Limpiamos el buffer
            this.buffers.get(0).put(mensaje_escribir.getBytes());//Ponemos el mensaje en el buffer
        }
        
        //escribimos en el archivo con los buffers que tenemos
        for(ByteBuffer buffer_info : buffers) {
            buffer_info.flip();//Preparamos para la lectura
            //Enviamos la informacion
            this.canal.write(buffer_info);
        }
        this.canal.close();
    }
    
    public void leer() throws IOException{
        
        this.canal = new RandomAccessFile("archivosNIO.txt", "r").getChannel();//Ahora abrimos el el canal en modo lectura
        ByteBuffer buffer;//Buffer para guardar los bytes obtenidos
        
        for(int num_buffers = 0; num_buffers < this.buffers.size(); num_buffers++) {
            
            buffer = ByteBuffer.allocate(size_buffer);//Volvemos a crear un nuevo buffer
            int leido = this.canal.read(buffer);//Leemos y obtenemos la cantidad de bytes
            
            while(leido != -1){
                
                buffer.flip();//Preparamos para la lectura
                System.out.println("Bytes leidos: " + leido);
                
                while(buffer.hasRemaining()){//Leer los bytes del archivo
                    System.out.print((char)buffer.get());
                }
                System.out.println("");
               
                buffer.clear();//Limpiamos el buffer para nuevos datos
                leido = this.canal.read(buffer);//Obtenemos el siguiente conjunto de bytes
            }
        }
        this.canal.close();//Cerramos el canal
        
    }
    
    private void createBuffers(String mensaje_escribir, double numero_buffers){
        int cantidad_sub_buffers = ((int) numero_buffers);                
        int comparador = cantidad_sub_buffers;
        int cantidadBuffers = 0;
        int inicio = 0;
        int fin = 0;

        while(comparador != 0){//Hasta que sea la cantidad de buffers
            this.buffers.add(ByteBuffer.allocate(size_buffer));
            this.buffers.get(cantidadBuffers).clear();//Limpiamos los buffers
                    
            //Obtenemos el final del siguiente grupo de letras
            fin = (((cantidadBuffers + 1) * size_buffer) <= mensaje_escribir.length()) ? ((cantidadBuffers + 1) * size_buffer) - 1 : mensaje_escribir.length();
            this.buffers.get(cantidadBuffers)
                        .put(mensaje_escribir.substring(inicio, fin).getBytes());
            
            //Inicio del siguiente dato
            inicio += size_buffer - 1;
                    
            comparador--;
            cantidadBuffers++;
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ArchivosNIO archivos = new ArchivosNIO();
        System.out.println("Escribe el texto para el archivo: ");
        archivos.escribir(new Scanner(System.in).nextLine());
        archivos.leer();
    }
    
}
