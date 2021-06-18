/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_ahorcado.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.rmi.RemoteException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import proyecto_final_ahorcado.rmi.ChangerUI;
import proyecto_final_ahorcado.rmi.ClienteAhorcado;
import proyecto_final_ahorcado.rmi.ListenerBototonPalabra;

/**
 *
 * @author Carito, Sofi, La señora kim, Angel
 */
public class JFramePalabra extends JFrame{

    private JLabel lblIngresarPalabra;
    private JTextField txtIngresarPalabra;
    private JButton btnEnviarPalabra;
    private JPanel panelPrincipal, panelComponentes;
    private ClienteAhorcado cliente;
    private ChangerUI changer;
    
    public JFramePalabra(ClienteAhorcado cliente, ChangerUI changer) throws InterruptedException, RemoteException{
        this.lblIngresarPalabra = new JLabel("Palabra a adivinar...");
        this.txtIngresarPalabra = new JTextField(20);
        this.btnEnviarPalabra = new JButton("Enviar");
        this.panelPrincipal = new JPanel();
        this.panelComponentes = new JPanel(new GridLayout(3,1));
        this.cliente = cliente;
        this.changer = changer;
        this.changer.setPalabra(this);
        initComponents();
    }
    
    private void initComponents() throws InterruptedException, RemoteException{
        JPanel panelLblAux = new JPanel();
            panelLblAux.add(this.lblIngresarPalabra);
        JPanel panelTxtAux = new JPanel();
            panelTxtAux.add(this.txtIngresarPalabra);
        JPanel panelAuxBtn = new JPanel();
            panelAuxBtn.add(this.btnEnviarPalabra);
        
        //Agregamos listener
        ListenerBototonPalabra listener = new ListenerBototonPalabra(this.txtIngresarPalabra,this.btnEnviarPalabra, this.changer);
        this.btnEnviarPalabra.addActionListener(listener);
        this.btnEnviarPalabra.setActionCommand("EnviarPalabraAdivinar");
            
        this.panelComponentes.add(panelLblAux);
        this.panelComponentes.add(panelTxtAux);
        this.panelComponentes.add(panelAuxBtn);
        
        this.panelPrincipal.add(this.panelComponentes);
        
        this.add(this.panelPrincipal); 
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Cliente 1");
        this.pack();
    }
    
    public JButton getBtn(){
        return this.btnEnviarPalabra;
    }

}
