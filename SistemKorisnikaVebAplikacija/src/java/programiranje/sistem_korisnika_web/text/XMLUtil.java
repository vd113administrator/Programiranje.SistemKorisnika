/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika_web.text;

import nu.xom.Text;

/**
 * Alati za baratanje XML-om 
 * @author Mikec
 */
public final class XMLUtil {
    private XMLUtil(){}
    
    /**
     * Pretvara tekst u XML 
     * @param xml xml 
     * @return tekst
     */
    public static String getXMLEmbended(String xml){
        Text text = new Text(xml);
        return text.toXML(); 
    }
    
    /**
     * Isijecanje koda izmedju XML karaktera
     * @param xml xml 
     * @param startKey pocetni tag 
     * @param endKey zavrsni tag
     * @return tekst
     */
    public static String getXMLInFirstKeyTag(String xml, String startKey, String endKey){
        int a = xml.indexOf(startKey);
        int b = xml.indexOf(endKey);
        if(a==-1 || b==-1 || a+startKey.length()>b){
            a=0;
            b=xml.length();
        }else{
            a+=startKey.length();
        }
        return xml.substring(a,b);
    }
}
