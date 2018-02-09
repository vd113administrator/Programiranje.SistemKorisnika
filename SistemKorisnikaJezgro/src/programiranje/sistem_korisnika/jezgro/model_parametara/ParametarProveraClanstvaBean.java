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
public class ParametarProveraClanstvaBean {
    private String korisnik; 
    public ParametarProveraClanstvaBean(String korisnik){
        this.korisnik = korisnik; 
    }
    public String getKorisnik(){
        return korisnik; 
    }
}
