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
public class RezultatClanstvoBean {
    private String rezultat; 
    private String greska; 
    
    public RezultatClanstvoBean(String rezultat, String greska){
        this.rezultat = rezultat; 
        this.greska = greska; 
    }
    
    public String getRezultat(){
        return rezultat; 
    }
    
    public String getGreska(){
        return greska; 
    }
}
