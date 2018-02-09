/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.alati.server.vezivanje;

/**
 * Kontrolna tacka za vezivanje
 * @author Mikec
 */
public final  class VezivanjeControl {
   private static VezivanjeServer vezivanje;
   /**
    * Dobijanje servera za vezivanje datog servera
    * @return server vezivanja
    */
   public static VezivanjeServer getVezivanjeServer(){
       return vezivanje; 
   }
   
   /**
    * Postavljanje servera za vezivanje 
    * @param server server za vezivanje
    */
   public static void setVezaniServer(VezivanjeServer server){
       vezivanje = server; 
   }
}
