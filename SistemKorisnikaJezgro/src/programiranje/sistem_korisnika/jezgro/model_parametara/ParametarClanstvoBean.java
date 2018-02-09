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
public class ParametarClanstvoBean {
    private String korisnik;
    private String grupa;
    
    public ParametarClanstvoBean(String korisnik, String grupa){
        this.korisnik = korisnik; 
        this.grupa = grupa; 
    }
    
    public String getKorisnik(){
        return korisnik; 
    }
    
    public String getGrupa(){
        return grupa; 
    }
}
