/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.server.tekst;

/**
 *
 * @author Mikec
 */
public class FilenameExtension {
    private String filename; 
    
    public FilenameExtension(String fne){
        filename = fne; 
    }
    
    public String getFilename(){
        return filename; 
    }
    
    public String getExtension(){
        String[] str = filename.split("[.]");
        if(str.length<2) return ""; 
        return "."+str[str.length-1]; 
    }
    
    public String getFileName(){
        return filename.substring(0,filename.length()-getExtension().length());
    }
}
