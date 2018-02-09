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
public class ParametarPodaciKorisnikaBean {
    private String administrator; 
    private String grupa; 
    private String korisnik; 
    
    public ParametarPodaciKorisnikaBean(String admin, String grupa, String user){
        this.administrator = admin; 
        this.grupa = grupa; 
        this.korisnik = user; 
    }
    
    public String getAdministrator(){
        return administrator; 
    }
    
    public String getGrupa(){
        return grupa; 
    }
    
    public String getKorisnik(){
        return korisnik;
    }
}
