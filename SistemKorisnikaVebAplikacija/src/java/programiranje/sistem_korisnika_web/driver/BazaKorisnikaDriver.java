/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika_web.driver;

import programiranje.baza_korisnika_web.global.ProtokolBKSP;

/**
 * Drajevri dogadjaja za bazu korisnika
 * @author Mikec
 */
public class BazaKorisnikaDriver extends Driver{
    /**
     *  Konstruktor
     */
    public BazaKorisnikaDriver(){
        add(ProtokolBKSP.BKSP_PRIJAVA);
        add(ProtokolBKSP.BKSP_REGISTRACIJA);
        add(ProtokolBKSP.BKSP_DEREGISTRACIJA);
        add(ProtokolBKSP.BKSP_ODIJAVA);
        add(ProtokolBKSP.BKSP_CLOSE);
        add(ProtokolBKSP.BKSP_PODACI);
        add(ProtokolBKSP.PBKSP_CLOSE);
        add(ProtokolBKSP.PBKSP_STOP);
        add(ProtokolBKSP.BKSP_N1_VEZANJE);
        add(ProtokolBKSP.BKSP_SUCCESS);
        add(ProtokolBKSP.BKSP_ERROR);
    }
}
