/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika_web.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Skupina alatiki za laksi rad sa datotekama
 * @author Mikec
 */
public class FileUtil {
    private FileUtil(){}
    
    /**
     * Ocitavanje teksata 
     * @param file datoteka
     * @return sadrzaj datoteke 
     */
    public static String readText(File file){
        String string = "";
        if(!file.exists()) return null; 
        try(Scanner scanner = new Scanner(new InputStreamReader(new FileInputStream(file),"UTF-8"))){
            while(scanner.hasNextLine()){
                string += scanner.nextLine();
            }
        }catch(Exception ex){
            return null;
        }
        return string;
    }
}
