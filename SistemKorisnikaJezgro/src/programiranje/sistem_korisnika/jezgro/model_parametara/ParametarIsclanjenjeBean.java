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
public class ParametarIsclanjenjeBean {
    private String clan; 
    private String naziv_grupe; 
    
    public ParametarIsclanjenjeBean(String clan, String naziv_grupe){
        this.clan = clan; 
        this.naziv_grupe = naziv_grupe;
    }
    
    public String getClan(){
        return clan; 
    }
    
    public String getNazivGrupe(){
        return naziv_grupe; 
    }
}
