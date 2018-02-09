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
public class ParametarPreimenovanjaGrupaBean {
    private String stariNaziv="";
    private String noviNaziv=""; 
    private String adminUname="";
    
    public ParametarPreimenovanjaGrupaBean(String adminUser, String oldGroup, String newGroup){
        adminUname = adminUser; 
        stariNaziv = oldGroup;
        noviNaziv = newGroup; 
    }
    
    public String getStariNaziv(){
        return stariNaziv; 
    }
    
    public String getNoviNaziv(){
        return noviNaziv; 
    }
    
    public String getAdminUname(){
        return adminUname; 
    }
}
