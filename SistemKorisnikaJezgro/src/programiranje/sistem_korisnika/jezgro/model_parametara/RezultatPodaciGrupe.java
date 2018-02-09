/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.jezgro.model_parametara;

import programiranje.sistem_korisnika.jezgro.model_podataka.GrupaBean;

/**
 *
 * @author Mikec
 */
public class RezultatPodaciGrupe {
    private GrupaBean grupa;
    public RezultatPodaciGrupe(GrupaBean g){
        grupa = g; 
    }
    public GrupaBean getGrupa(){
        return grupa;
    }
}
