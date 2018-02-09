/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.alatke;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mikec
 */
public final class SelekcijaDatoteka {
    private SelekcijaDatoteka(){
    }
    
    public static List<File> selectByPrefix(File direktorijum, String prefix){
        ArrayList<File> files = new ArrayList<>();
        if(direktorijum==null) return files;
        if(!direktorijum.exists()) return files;
        if(!direktorijum.isDirectory()) return files;
        for(File file: direktorijum.listFiles()){
            if(file.isFile() && file.getName().startsWith(prefix))
                files.add(file);
        }
        return files; 
    }
    
    public static List<File> selectBySufix(File direktorijum, String sufix){
        ArrayList<File> files = new ArrayList<>();
        if(direktorijum==null) return files;
        if(!direktorijum.exists()) return files;
        if(!direktorijum.isDirectory()) return files;
        for(File file: direktorijum.listFiles()){
            if(file.isFile() && file.getName().endsWith(sufix))
                files.add(file);
        }
        return files; 
    }
    
    public static List<File> selectByInfix(File direktorijum, String infix){
        ArrayList<File> files = new ArrayList<>();
        if(direktorijum==null) return files;
        if(!direktorijum.exists()) return files;
        if(!direktorijum.isDirectory()) return files;
        for(File file: direktorijum.listFiles()){
            if(file.isFile() && file.getName().contains(infix))
                files.add(file);
        }
        return files; 
    }
    
    public static List<File> selectByPrefixAndSufix(File direktorijum, String prefix, String sufix){
        List<File> list = selectByPrefix(direktorijum,prefix);
        list.retainAll(selectBySufix(direktorijum,sufix));
        return list; 
    }
}
