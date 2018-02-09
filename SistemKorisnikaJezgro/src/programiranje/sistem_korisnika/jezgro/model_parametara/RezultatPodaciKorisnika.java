/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.jezgro.model_parametara;

import programiranje.sistem_korisnika.jezgro.model_podataka.KorisnikBean;

/**
 *
 * @author Mikec
 */
public class RezultatPodaciKorisnika {
    private KorisnikBean korisnik; 
    public RezultatPodaciKorisnika(KorisnikBean kb){
        korisnik = kb; 
    }
    
    public KorisnikBean getKorisnik(){
        return korisnik;
    }
}
