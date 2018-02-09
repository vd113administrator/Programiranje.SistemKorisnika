/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.alati;

import java.util.Scanner;
import programiranje.baza_korisnika_shell.server.ServerSredstva;

/**
 * Staticke metode za rad sa sql iskazima 
 * @author Mikec
 */
public class SQLUtil {
    private SQLUtil(){}
    
    /**
     * Formiranje stringa sql naredbe 
     * @param sqlressta kljuc za naredbu
     * @param params parametri
     * @return sql iskazs
     */
    public static String getStatement(String sqlressta, String ... params){
        String sta = ""; 
        Object sp[] = new String[params.length];
        String res = ServerSredstva.sqlNaredbeObj.getProperty(sqlressta);
        Scanner scan = new Scanner(SQLUtil.class.getResourceAsStream(res));
        while(scan.hasNextLine()){
            sta+=scan.nextLine()+"\n";
        }
        scan.close();
        for(int i=0; i<params.length; i++)
            sp[i] = StringUtil.sqlEscape(params[i]); 
        return String.format(sta, sp);
    }
}
