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
public class ParametarUclanjenjeBean {
    private String naziv_grupe; 
    private String korisnik; 
    
    public ParametarUclanjenjeBean(String naziv_grupe, String korisnik){
            this.naziv_grupe = naziv_grupe; 
            this.korisnik = korisnik;
    }
    
    public String getKorisnik(){
        return korisnik; 
    }
    
    public String getNazivGrupe(){
        return naziv_grupe;
    }
}
