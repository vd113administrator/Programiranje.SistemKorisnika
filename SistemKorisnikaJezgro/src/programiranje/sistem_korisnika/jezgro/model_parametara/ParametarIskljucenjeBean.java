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
public class ParametarIskljucenjeBean {
    private String administrator; 
    private String korinsik; 
    private String grupa; 
    
    public ParametarIskljucenjeBean(String admin, String user, String group){
        administrator = admin; 
        korinsik = user; 
        grupa = group; 
    }
    
    public String getAdministrator(){
        return administrator; 
    }
    
    public String getKorisnik(){
        return korinsik; 
    }
    
    public String getGrupa(){
        return grupa; 
    }
}
