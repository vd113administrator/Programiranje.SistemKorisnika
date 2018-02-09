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
public class RezultatBrisanjeGrupeBean {
    private String rezultat; 
    private String greska; 
    
    public RezultatBrisanjeGrupeBean(String res){
        rezultat = res; 
    }
    
    public RezultatBrisanjeGrupeBean(String res, String err){
        rezultat = res; 
        greska = err; 
    }
    
    public String getRezultat(){
        return rezultat; 
    }
    
    public String getGreska(){
        return greska;
    }
}
