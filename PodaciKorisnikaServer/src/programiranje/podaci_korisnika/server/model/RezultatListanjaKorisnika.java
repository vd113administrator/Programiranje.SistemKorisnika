/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.server.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mikec
 */
public class RezultatListanjaKorisnika {
    private List<String> lista; 
    
    public RezultatListanjaKorisnika(List<String> lista){
        this.lista = lista; 
    }
    
    public List<String> getImenaClanova(){
        return lista; 
    }
}
