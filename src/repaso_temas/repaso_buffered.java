/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repaso_temas;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author depot
 */
public class repaso_buffered {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException{
        
        //****************BufferedReader*****************
        
        /*Para consola
        InputStreamReader flujo_datos = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(flujo_datos);
        
        //Imprimimos en pantalla lo que se escribió en consola
        System.out.println("Escribiste: " + reader.readLine());
        reader.close();
        
        //Para archivos
        //Para elegir el archivo
        JFileChooser archivo = new JFileChooser();
        archivo.showOpenDialog(null);
        
        //Obtenemos el archivo seleccionado
        FileReader file_reader = new FileReader(archivo.getSelectedFile());
        BufferedReader reader_2 = new BufferedReader(file_reader);
        
        reader_2.mark(1000000);
        
        //Leemos linea por linea el archivo
        for(String fila = reader_2.readLine(); fila != null; fila = reader_2.readLine()){
            System.out.println(fila);
        }
        
        reader_2.reset();
        
        //Volvemos a leer el archivo
        while(reader_2.ready()){
            System.out.println(reader_2.readLine());
        }
        reader_2.close();
        
        
        //****************BufferedWriter********************
        //Para escoger el archivo
        JFileChooser selector = new JFileChooser();
        selector.showOpenDialog(null);
        
        //Para poder escribir
        FileWriter escritor = new FileWriter(selector.getSelectedFile());
        BufferedWriter escritor_flujo = new BufferedWriter(escritor);
        
        String mensaje = JOptionPane.showInputDialog("Que quieres escribir?");//Obtenemos el mensaje
        escritor_flujo.write(mensaje);//Escribimos en el archivo
        escritor_flujo.newLine();//Salto de linea
        escritor_flujo.flush();//Guardamos los cambios
        escritor.close();//cerramos el flujo
                
        */
        
        //******************PrintReader******************
        JFileChooser escoger_archivo = new JFileChooser();
        escoger_archivo.showOpenDialog(null);
        
        //Escribimos en el archivo
        FileWriter escritor = new FileWriter(escoger_archivo.getSelectedFile());
        BufferedWriter escritor_flujo = new BufferedWriter(escritor);
        PrintWriter writer = new PrintWriter(escritor_flujo);
        
        //Escribimos en el archivo
        writer.print(12.0);
        writer.print("\nYa jala\n");
        writer.print('a');
        
        //Cerramos el flujo
        writer.close();
    }
    
}
