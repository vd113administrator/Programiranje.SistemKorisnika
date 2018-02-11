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

/**
 *
 * @author Mikec
 */
public class FileUnit implements Serializable{
    private File file;

    private byte[] content; 
    
    public FileUnit(File file){
       this.file = file; 
    }
    
    public FileUnit(File file, byte[] content){
        this.file = file;
        this.content = content; 
    }
    
    public File getFile() {
        return file;
    }

    public FileUnit setFile(File file) {
        this.file = file;
        return this;
    }

    public byte[] getContent() {
        return content;
    }

    public FileUnit setContent(byte[] content) {
        this.content = content;
        return this;
    }
    
    public FileUnit open() throws IOException{
        FileInputStream fis = new FileInputStream(file);
        content = new byte[fis.available()];
        fis.read(content);
        fis.close();
        return this;
    }
    
    public FileUnit save() throws FileNotFoundException, IOException{
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content);
        fos.close();
        return this;
    }
    
    public FileUnit openIfExists() throws IOException{
        if(!file.exists()) return this; 
        if(!file.isFile()) return this;
        FileInputStream fis = new FileInputStream(file);
        content = new byte[fis.available()];
        fis.read(content);
        fis.close();
        return this;
    }
    
    public FileUnit saveIfnotExists() throws FileNotFoundException, IOException{
        if(file.exists()) return this; 
        System.err.println(file);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content);
        fos.close();
        return this;
    }
    
    public FileUnit setDirectory(File file){
        this.file = new File(file,this.file.getName());
        return this;
    }
    
    public FileUnit setFileName(String name){
        file = new File(file.getParent(),name);
        return this;
    }
    
    public FileUnit erase(){
        file.delete();
        return this;
    }
}
