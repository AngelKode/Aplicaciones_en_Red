/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_ahorcado.rmi;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author 200an
 */
public class ListenerBotonAdivinador implements ActionListener{

    ClienteAhorcado clienteAdivinador;
    JTextField txtRespuesta;
    JPanel panelLetrasCorrectas;
    ArrayList<JTextField> letrasCorrectas;
    JLabel imagenEstatus;
    JButton btnEnviarRespuesta;
    boolean isDone;
    JMenuItem itemReiniciar;
    private ChangerUI changer; 
    
    public ListenerBotonAdivinador(JTextField txtRespuesta,JPanel panelAuxLetras, JLabel imagen, JButton enviar, ClienteAhorcado cliente, JMenuItem itemReiniciar, ChangerUI changer) throws InterruptedException, RemoteException {
        this.clienteAdivinador = cliente;
        this.txtRespuesta = txtRespuesta;
        this.panelLetrasCorrectas = panelAuxLetras;
        this.letrasCorrectas = new ArrayList<>();
        this.imagenEstatus = imagen;
        this.btnEnviarRespuesta = enviar;
        this.isDone = false;
        this.itemReiniciar = itemReiniciar;
        this.changer = changer;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("EnviarRespuesta")){
            try {
                boolean estatus = this.clienteAdivinador.enviarRespuesta(this.txtRespuesta.getText().trim());
                int contador = 0;
                for(Object obj : this.clienteAdivinador.getLetrasCorrectas()){
                    if(obj instanceof String){
                        this.letrasCorrectas.get(contador).setText((String) obj);
                    }
                    contador++;
                }
                
                int cantidadErrores = this.clienteAdivinador.getCantidadErrores();
                if(cantidadErrores > 9){
                    this.imagenEstatus.setIcon(new ImageIcon("src/proyecto_final_ahorcado/imagenes/Error_" +cantidadErrores+".png"));
                    JOptionPane.showMessageDialog(null, "Has perdido!", "Atención", JOptionPane.WARNING_MESSAGE);
                    this.isDone = true;
                    this.changer.toggleReiniciarPartida();
                    this.changer.toggleBtnChecarPalabra();
                }else{
                    if(!estatus){
                        this.imagenEstatus.setIcon(new ImageIcon("src/proyecto_final_ahorcado/imagenes/Error_" +cantidadErrores+".png"));
                    }else{
                        JOptionPane.showMessageDialog(null, "Ganaste!");
                        this.isDone = true;
                        this.changer.toggleBtnChecarPalabra();
                        this.changer.toggleReiniciarPartida();
                    } 
                }
  
            } catch (RemoteException ex) {}
        }
    }
    
    public void reset() throws RemoteException{
        this.imagenEstatus.setIcon(new ImageIcon("src/proyecto_final_ahorcado/imagenes/Error_" +0+".png"));//Actualizamos la imagen
        //Limpiamos los text field
        for(JTextField text : this.letrasCorrectas){
            text.setText(" ");
        }
        //Habilitamos el boton
        this.btnEnviarRespuesta.setEnabled(true);
        this.isDone = true;
        this.itemReiniciar.setEnabled(false);
    }
    
    public void setArrayList(ArrayList<JTextField> aux) throws RemoteException{
        this.letrasCorrectas = aux;
    }
    
    public boolean isDone(){
        return this.isDone;
    }
}
