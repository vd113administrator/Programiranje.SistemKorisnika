/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.jezgro.model_parametara;

/**
 *
 * @author Mikec
 */
public class RezultatPreimenovanjaGrupaBean {
    private String uspeh; 
    private String greska; 
    
    public RezultatPreimenovanjaGrupaBean(String uspeh){
        this.uspeh = uspeh; 
    }
    
    public RezultatPreimenovanjaGrupaBean(String uspeh, String greska){
        this.uspeh = uspeh; 
        this.greska = greska; 
    }
    
    public String getUspjeh(){
        return uspeh;
    }
    
    public String getGreska(){
        return greska; 
    }
}
