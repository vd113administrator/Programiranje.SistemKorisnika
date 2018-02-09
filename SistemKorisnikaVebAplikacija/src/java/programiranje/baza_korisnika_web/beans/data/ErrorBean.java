/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.beans.data;

import java.io.Serializable;

/**
 * Zrno sa podacima o gresci - JSF sesijski globalno 
 * @author Mikec
 */
public class ErrorBean implements Serializable{
    private String error = "";
   
    /**
     * Podrazumjevani konstruktor 
     */
    public ErrorBean(){}
    /**
     * Konstruktor poruke 
     * @param err 
     */
    public ErrorBean(String err){error=err;}
    
    /**
     * Dobijanje poruke
     * @return poruka 
     */
    public String getError(){
        return error; 
    }
    /**
     * Postavljanje poruke 
     * @param error poruka
     */
    public void setError(String error){
        this.error = error; 
    }
}
