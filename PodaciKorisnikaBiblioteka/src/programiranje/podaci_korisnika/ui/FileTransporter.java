/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Mikec
 */
public class FileTransporter implements Serializable{
    public final HashMap<String, FileUnit> files = new HashMap<>();
    
    public FileTransporter openAll() throws IOException{
        for(FileUnit file: files.values()){
            file.open();
        }
        return this;
    }
    
    public FileTransporter saveAll() throws FileNotFoundException, IOException{
        for(FileUnit file: files.values()){
            file.save();
        }
        return this;
    }
    
    public FileTransporter setDirectoryAll(File dir){
        for(FileUnit file: files.values()){
            file.setDirectory(dir);
        }
        return this;
    }
    
    public FileTransporter eraseAll(){
        for(FileUnit file: files.values()){
            file.erase();
        }
        return this;
    }
}
