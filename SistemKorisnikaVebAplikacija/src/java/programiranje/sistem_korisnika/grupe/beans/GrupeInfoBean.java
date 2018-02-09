/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.grupe.beans;

import java.io.Serializable;
import java.util.List;
import programiranje.baza_korisnika_web.global.GlobalneFunkcije;
import programiranje.sistem_korisnika.grupe.veb.ulaz_izlaz.SKServerAdapter;
import programiranje.sistem_korisnika_web.global.SKGlobalneFunkcije;
import programiranje.sistem_korisnika_web.text.TextUtil;

/**
 * Ocitavanje informacija o grupi - model podataka 
 * @author Mikec
 */
public class GrupeInfoBean implements Serializable{
    private String grupeInfoTableHTML="";
    private String korisniciInfoTableHTML="";
    
    private String generisiTekstFN(int i){
        String res = "akcijaFN("+i+")";
        return res;
    }
    
    /**
     * Dobijanje podataka za grupe u HTML formi tabele 
     * @return HTML tabela ispisa grupa
     */
    public String getGrupeInfoTableHTML(){
        int i = 0;
        String res = "";
        SKServerAdapter adapter = GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter().getSKServerAdapter(); 
        if(SKGlobalneFunkcije.getSelekcijaGrupaMode().equals("1")){
            List<String> lista = adapter.getListaSvihGrupa();
            res+="<script>grupeBroj="+lista.size()+";</script>\n";
            for(String s : lista){    
              String data = adapter.getPodaci(s).get(1); 
              res+="<script>grupeCekiranja["+i+"]=false;</script>\n";
              res+="<script>function fn"+i+"(){"+generisiTekstFN(i)+"}</script>\n";
              res+="<tr class=\'checkable\'><td class=\'checkable\'><input type=\'checkbox\' name=\'db"+i+"\' id=\'db"+i+"\' onchange=\'fn"+i+"()\' value=\'false\'></td><td class=\'checkable\'>"
                      +"<div id=\'gname"+i+"\'>"+TextUtil.removeALG(s)+"</div>"+
                      "</td><td class=\'checkable\'>"
                      +TextUtil.removeALG(data)+"</td>"
                      +"<td class=\'checkable\'>"
                      +"<u><font color='blue'><div"
                      +" onclick=\"document.getElementById('unos_grupa:gdata').value='"+s.replaceAll("#","#k")+"#o"+data.replaceAll("#","#k")+"';"
                      + "document.getElementById('unos_grupa:gdata').click();\">"
                      +TextUtil.removeALG(s)+"</div></font></u>"
                      +"</td></tr>";
              i++;
            }
        }
        else if(SKGlobalneFunkcije.getSelekcijaGrupaMode().equals("2")){
            List<String> lista = adapter.getImenaGrupaZaClanstvo();
            res+="<script>grupeBroj="+lista.size()+";</script>\n";
            for(String s : lista){
              String data = adapter.getPodaci(s).get(1);
              res+="<script>grupeCekiranja["+i+"]=false</script>\n";
              res+="<script>function fn"+i+"(){"+generisiTekstFN(i)+"}</script>\n";
              res+="<tr class=\'checkable\'><td class=\'checkable\'><input type=\'checkbox\' name=\'db"+i+"\' id=\'db"+i+"\' onchange=\'fn"+i+"()\' value=\'false\'></td><td class=\'checkable\'>"
                      +"<div id=\'gname"+i+"\'>"+TextUtil.removeALG(s)+"</div>"+
                      "</td><td class=\'checkable\'>"
                      +TextUtil.removeALG(data)+"</td>"
                      +"<td class=\'checkable\'>"
                      +"<u><font color='blue'><div"
                      +" onclick=\"document.getElementById('unos_grupa:gdata').value='"
                      +s+"'; document.getElementById('unos_grupa:gdata').click();\">"+TextUtil.removeALG(s)+"</div></font></u>"
                      +"</td></tr>"; 
              i++;
            }
        }
        else if(SKGlobalneFunkcije.getSelekcijaGrupaMode().equals("3")){
            List<String> lista = adapter.getImenaGrupaZaVlasnistvo();
            res+="<script>grupeBroj="+lista.size()+";</script>\n";
            for(String s : lista){
              String data = adapter.getPodaci(s).get(1);
              res+="<script>grupeCekiranja["+i+"]=false</script>\n";
              res+="<script>function fn"+i+"(){"+generisiTekstFN(i)+"}</script>\n";
              res+="<tr class=\'checkable\'><td class=\'checkable\'><input type=\'checkbox\' name=\'db"+i+"\' id=\'db"+i+"\' onchange=\'fn"+i+"()\' value=\'false\'></td><td class=\'checkable\'>"
                      +"<div id=\'gname"+i+"\'>"+TextUtil.removeALG(s)+"</div>"+
                      "</td><td class=\'checkable\'>"
                      +TextUtil.removeALG(data)+"</td>"
                      +"<td class=\'checkable\'>"
                      +"<u><font color='blue'><div"
                      +" onclick=\"document.getElementById('unos_grupa:gdata').value='"
                      +s+"'; document.getElementById('unos_grupa:gdata').click();\">"+TextUtil.removeALG(s)+"</div></font></u>"
                      +"</td></tr>";
              i++;
            }
        }
        grupeInfoTableHTML = res;
        return grupeInfoTableHTML; 
    }
    
    /**
     * Postavljanje HTML-a za informacije o grupama 
     * @param html novi html informacija o grupama
     */
    public void setGrupeInfoTableHTML(String html){
        grupeInfoTableHTML =  html; 
    }
    
    /**
     * Ocitavanje informacija o korisnicima u formi HTMl tabele
     * @return html informacija o korisnicima  
     */
    public String getKorisniciInfoTableHTML(){
        korisniciInfoTableHTML = SKGlobalneFunkcije.getKorisniciInfoHTML();
        return korisniciInfoTableHTML; 
    }
    
    /**
     * Postavlanje informacija o korisnicima u vidu HTML 
     * @param user korisnik 
     */
    public void setKorisniciInfoTableHTML(String user){
        korisniciInfoTableHTML = user;
    }
}
