/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.jezgro.model_parametara;

import java.util.List;

/**
 * 
 * @author Mikec
 */
public class RezultatListanjaGrupa{
    private List<String> grupe; 
    
    public RezultatListanjaGrupa(List<String> lista){
        grupe = lista; 
    }
    
    public List<String> getGrupe(){
        return grupe; 
    }
}
