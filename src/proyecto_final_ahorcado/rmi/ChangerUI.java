/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_ahorcado.rmi;

import java.rmi.RemoteException;
import proyecto_final_ahorcado.gui.JFrameAdivinador;
import proyecto_final_ahorcado.gui.JFramePalabra;

/**
 *
 * @author 200an
 */
public class ChangerUI {

    private JFrameAdivinador adivinador;
    private JFramePalabra palabra;
    
    public ChangerUI() {
        this.adivinador = null;
        this.palabra = null;
    }
    
    public void setAdivinador(JFrameAdivinador adivinador){
        this.adivinador = adivinador;
    }
    
    public void setPalabra(JFramePalabra palabra){
        this.palabra = palabra;
    }
    
    public void toggleBtnNuevaPalabra(){
        boolean toggle = (this.palabra.getBtn().isEnabled()) ? false : true;
        this.palabra.getBtn().setEnabled(toggle);
    }
    
    public void toggleBtnChecarPalabra(){
        boolean toggle = (this.adivinador.getBtn().isEnabled()) ? false : true;
        this.adivinador.getBtn().setEnabled(toggle);
    }
    
    public void toggleReiniciarPartida(){
        boolean toggle = (this.adivinador.itemReinicar().isEnabled()) ? false : true;   
        this.adivinador.itemReinicar().setEnabled(toggle);
    }
    
    public void setTituloEsperaAdivinador(){
        this.adivinador.setCarga();
    }
    
    public void setListo() throws RemoteException{
        this.adivinador.setListo();
    }
    
}
