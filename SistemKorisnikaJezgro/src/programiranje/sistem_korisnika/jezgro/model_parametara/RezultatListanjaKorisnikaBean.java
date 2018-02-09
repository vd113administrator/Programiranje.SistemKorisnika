/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.jezgro.model_parametara;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mikec
 */
public class RezultatListanjaKorisnikaBean {
    private List<String> lista; 
    
    public RezultatListanjaKorisnikaBean(List<String> lista){
        this.lista = lista; 
    }
    
    public List<String> getImenaClanova(){
        return lista; 
    }
}
