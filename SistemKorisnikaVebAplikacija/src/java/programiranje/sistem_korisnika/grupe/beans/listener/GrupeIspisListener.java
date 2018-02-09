/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.grupe.beans.listener;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import programiranje.sistem_korisnika_web.global.SKGlobalneFunkcije;

/**
 * Reakcija na potraznju za ispis grupa
 * @author Mikec
 */
public class GrupeIspisListener implements ValueChangeListener{
    /**
     * Proces promjene vrijednosti imena grupe 
     * @param event obijekat dogadjaja 
     * @throws AbortProcessingException 
     */
    @Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException{
        SKGlobalneFunkcije.setSelekcijaGrupaMode((String)event.getNewValue());
        SKGlobalneFunkcije.setStranicaGrupa(2);
    }
}
