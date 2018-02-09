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
public class ReazultatUclanjenjeBean {
    private String info; 
    private String error;
    
    public ReazultatUclanjenjeBean(String info){
        this.info= info;
        this.error= ""; 
    }
    
    public ReazultatUclanjenjeBean(String info, String err){
        this.info = info;
        this.error = err; 
    }
    
    public String getInfo(){
        return info;
    }
    
    public String getError(){
        return error; 
    }
}
