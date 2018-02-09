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
public class RezultatProvereVlasnistvaBean {
    private ArrayList<String> imena_grupa = new ArrayList<>(); 
    public RezultatProvereVlasnistvaBean(List<String> imena){
        imena_grupa = new ArrayList<>(imena);
    }
    public List<String> getImenaGrupa(){
        return imena_grupa;
    }
}
