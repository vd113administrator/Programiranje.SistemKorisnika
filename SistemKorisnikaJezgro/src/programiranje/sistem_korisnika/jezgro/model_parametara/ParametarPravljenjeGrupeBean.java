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
public class ParametarPravljenjeGrupeBean{
   private String noviAdministrator;
   private String nazivNoveGrupe;
   
   public ParametarPravljenjeGrupeBean(String noviAdministrator, String nazivNoveGrupe){
       this.noviAdministrator = noviAdministrator; 
       this.nazivNoveGrupe = nazivNoveGrupe;
   }
   
   // RADI SE O KORISNICKOM IMENU ADMINISTRATORA 
   public String getBuduciAdministrator(){
       return noviAdministrator; 
   }
   
   // RADI SE O NAZIVU GRUPE
   public String getNazivNoveGrupe(){
       return nazivNoveGrupe; 
   }
}
