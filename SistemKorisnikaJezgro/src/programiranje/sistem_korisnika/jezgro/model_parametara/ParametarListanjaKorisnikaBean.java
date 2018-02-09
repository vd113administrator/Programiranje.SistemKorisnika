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
public class ParametarListanjaKorisnikaBean {
    private String administrator; 
    private String grupa;
    
    public ParametarListanjaKorisnikaBean(String administrator, String grupa){
        this.administrator = administrator; 
        this.grupa = grupa; 
    }
    
    public String getAdministrator(){
        return administrator; 
    }
    
    public String getGrupa(){
        return grupa; 
    }
}
