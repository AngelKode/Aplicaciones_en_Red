/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_ahorcado.rmi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author 200an
 */
public class ListenerBototonPalabra implements ActionListener{
    
    ClienteAhorcado clientePalabra;
    JTextField txtPalabraAdivinar;
    JButton btnAgregarPalabra;
    ChangerUI changer;

    public ListenerBototonPalabra(JTextField txtPalabraAdivinar, JButton btnAgregarPalabra, ChangerUI changer) throws InterruptedException {
        this.clientePalabra = new ClienteAhorcado(1);
        this.txtPalabraAdivinar = txtPalabraAdivinar;;
        this.btnAgregarPalabra = btnAgregarPalabra;
        this.changer = changer;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("EnviarPalabraAdivinar")){
            try {
                this.clientePalabra.setPalabraAdivinar(this.txtPalabraAdivinar.getText().trim());
                this.txtPalabraAdivinar.setText(" ");
                //Actualizamos la UI de ambos
                this.changer.toggleBtnNuevaPalabra();
                this.changer.toggleBtnChecarPalabra();
                this.changer.setListo();
                this.changer.toggleReiniciarPartida();
                this.txtPalabraAdivinar.setText(" ");
            } catch (RemoteException ex) {}
        }
    }
    
    public void reset(){
        this.btnAgregarPalabra.setEnabled(true);
    }
    
}
