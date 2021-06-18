/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_ahorcado.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import proyecto_final_ahorcado.rmi.ChangerUI;
import proyecto_final_ahorcado.rmi.ClienteAhorcado;
import proyecto_final_ahorcado.rmi.ListenerBotonAdivinador;
import proyecto_final_ahorcado.rmi.MenuItemAhorcado;

/**
 *
 * @author 200an
 */
public class JFrameAdivinador extends JFrame{
    
    private JLabel lblImagenEstatus, lblAdivinaPalabra;
    private ArrayList<JTextField> txtLetrasCorrectas;
    private JTextField txtRespuesta;
    private JButton btnEnviarRespuesta;
    private ClienteAhorcado cliente;
    private MenuItemAhorcado menu;
    private JPanel panelPrincipal,panelImagenAux,panelAuxTituloLetras,panelAuxLetras,panelEspera,panelAuxTextAdivinarPalabra;
    private ListenerBotonAdivinador listener;
    private ChangerUI changer;
    
    public JFrameAdivinador(ClienteAhorcado cliente,ChangerUI changer) throws InterruptedException, RemoteException{
        this.lblImagenEstatus = new JLabel();
        this.lblAdivinaPalabra = new JLabel("Adivina la palabra!");
        this.txtLetrasCorrectas = new ArrayList<>();
        this.txtRespuesta = new JTextField(20);
        this.btnEnviarRespuesta = new JButton("Enviar");
        this.btnEnviarRespuesta.setEnabled(false);
        this.cliente = cliente;
        this.changer = changer;
        this.changer.setAdivinador(this);
        initComponents();
    }
    
    private void initComponents() throws InterruptedException, RemoteException{
        
        //Imagen
        this.panelImagenAux = new JPanel();
            this.lblImagenEstatus.setIcon(new ImageIcon("src/proyecto_final_ahorcado/imagenes/Error_0.png"));
            panelImagenAux.add(this.lblImagenEstatus);
        
        //Panel para el titulo de las letras correctas
        this.panelAuxTituloLetras = new JPanel();
            JLabel labelTituloLetras = new JLabel("Letras adivinadas");
            labelTituloLetras.setFont(new Font("Arial",Font.BOLD,18));
            panelAuxTituloLetras.add(labelTituloLetras);
        //Panel de las letras correctas
        this.panelAuxLetras = new JPanel();
            panelAuxLetras.setLayout(new BoxLayout(panelAuxLetras,BoxLayout.X_AXIS));
        
        //Tambien creamos el panel de espera en lo que se envia la palabra a adivinar
        this.panelEspera = new JPanel();
            JLabel labelEspera = new JLabel("Esperando a que se envíe la palabra...");
            labelEspera.setFont(new Font("Arial", Font.ITALIC, 16));
            panelEspera.add(labelEspera);
        
        //Panel para las respuestas
        this.panelAuxTextAdivinarPalabra = new JPanel();
            panelAuxTextAdivinarPalabra.add(this.lblAdivinaPalabra);
            panelAuxTextAdivinarPalabra.add(this.txtRespuesta);
            panelAuxTextAdivinarPalabra.add(this.btnEnviarRespuesta);
        
        //Panel principal
        this.panelPrincipal = new JPanel();
        setCarga();
        
        //Agregamos una barra de opciones para reiniciar el juego o salirse
        this.menu = new MenuItemAhorcado(cliente,this.changer);
        
        this.setJMenuBar(menu);
        
        
        this.listener = new ListenerBotonAdivinador(this.txtRespuesta,panelAuxLetras,this.lblImagenEstatus,this.btnEnviarRespuesta,this.cliente,menu.getMenu(0).getItem(0),this.changer);
        this.btnEnviarRespuesta.addActionListener(listener);
        this.btnEnviarRespuesta.setActionCommand("EnviarRespuesta");
        
        this.add(panelPrincipal);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }
    
    public JButton getBtn(){
        return this.btnEnviarRespuesta;
    }
    
    public JMenuItem itemReinicar(){
        return this.menu.getItemReiniciar();
    }
    public void setCarga(){
        this.lblImagenEstatus.setIcon(new ImageIcon("src/proyecto_final_ahorcado/imagenes/Error_0.png"));
        this.panelPrincipal.removeAll();
        this.panelPrincipal.setLayout(new BoxLayout(panelPrincipal,BoxLayout.Y_AXIS));
        this.panelPrincipal.add(panelImagenAux);
        this.panelPrincipal.add(panelAuxTituloLetras);
        this.panelPrincipal.add(panelEspera);
        this.panelPrincipal.add(panelAuxTextAdivinarPalabra);
        this.repaint();
        this.revalidate();
        this.pack();
    }
    
    public void setListo() throws RemoteException{
        this.panelPrincipal.removeAll();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal,BoxLayout.Y_AXIS));
        panelPrincipal.add(panelImagenAux);
        panelPrincipal.add(panelAuxTituloLetras);
        //Configuramos los textfield
        ArrayList<JTextField> aux = new ArrayList<>();
        panelAuxLetras.removeAll();
        for(int i=0; i  < this.cliente.getSizePalabraAdivinar();i++){
            JTextField auxTex = new JTextField(2);
            aux.add(auxTex);
            panelAuxLetras.add(auxTex);
        }
        panelPrincipal.add(panelAuxLetras);
        panelPrincipal.add(panelAuxTextAdivinarPalabra);
        listener.setArrayList(aux);
        this.repaint();
        this.revalidate();
        this.pack();
    }
    
}
