/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika_web.opis.kontrola;

import java.util.ArrayList;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Klasa za cuvanje privremenih infomacija na nivou sesije i aplikacije
 * @author Mikec
 */
public class PrivremeneInformacije {
    private PrivremeneInformacije(){
    }
    
    /**
     * Dobijanje JSF konteksta
     * @return JSF kontekst
     */
    private static ExternalContext getExternalContext(){
        FacesContext fcontext = FacesContext.getCurrentInstance();
        if(fcontext==null) return null;
        ExternalContext context = fcontext.getExternalContext();
        return context;
    }
    
    /**
     * Dobijanje podataka o imenima grupa
     * @return podaci o grupi 
     */
    public static ArrayList<String> getImenaGrupa(){
        ExternalContext context = getExternalContext();
        Map<String,Object> mapa = context.getSessionMap();
        return  (ArrayList<String>) mapa.get("_imena_grupa_");
    }
    
    /**
     * Postavljanje podataka o imenima grupa
     * @param lista novi podaci o imenu grupe
     */
    public static void setImenaGrupa(ArrayList<String> lista){
        ExternalContext context = getExternalContext();
        Map<String,Object> mapa = context.getSessionMap();
        mapa.put("_imena_grupa_", lista);
    }
}
