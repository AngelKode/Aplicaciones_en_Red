/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_ahorcado.rmi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import static java.lang.System.exit;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Angel
 */
public class MenuItemAhorcado extends JMenuBar implements ActionListener, ItemListener{

    private JMenuItem itemReiniciar, itemSalir;
    private ClienteAhorcado cliente;
    private ChangerUI changer;
    
    public MenuItemAhorcado(ClienteAhorcado cliente, ChangerUI changer) {
        this.cliente = cliente;
        this.changer = changer;
        initComponents();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Reiniciar": {
                try {
                    this.cliente.resetGame();
                    this.changer.setTituloEsperaAdivinador();
                    this.changer.toggleBtnNuevaPalabra();
                } catch (RemoteException ex) {
                    Logger.getLogger(MenuItemAhorcado.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "Salir":{
                //exit(1);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {}
    
    private void initComponents(){
        JMenu menuOpciones = new JMenu("Opciones");
            this.itemReiniciar = new JMenuItem("Reiniciar Juego");
                itemReiniciar.addActionListener(this);
                itemReiniciar.setActionCommand("Reiniciar");
                itemReiniciar.setEnabled(true);
            this.itemSalir = new JMenuItem("Salir");
                itemSalir.addActionListener(this);
                itemSalir.setActionCommand("Salir");
                itemSalir.setEnabled(true);
        menuOpciones.add(this.itemReiniciar);
        menuOpciones.add(this.itemSalir);
        this.add(menuOpciones);
    }
    
    public JMenuItem getItemReiniciar(){
        return this.itemReiniciar;
    }
}
