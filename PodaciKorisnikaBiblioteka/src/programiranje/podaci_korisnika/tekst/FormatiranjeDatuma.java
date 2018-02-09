/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.tekst;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Mikec
 */
public final class FormatiranjeDatuma {
    private FormatiranjeDatuma(){}
    public static String formatranjeDatuma(Date vrijeme){
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyLocalizedPattern("dd.MM.yyyy.HH.mm.ss");
        return sdf.format(vrijeme);
    }
    
    public static String formatranjeDatuma(){
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyLocalizedPattern("dd.MM.yyyy.HH.mm.ss");
        return sdf.format(new Date());
    }
    
    public static Date uklapanjeDatuma(String datestr) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyLocalizedPattern("dd.MM.yyyy.HH.mm.ss");
        return sdf.parse(datestr);
    }
}
