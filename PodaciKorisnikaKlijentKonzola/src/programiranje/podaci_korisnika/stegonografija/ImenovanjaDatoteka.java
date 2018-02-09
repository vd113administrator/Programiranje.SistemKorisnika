/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.stegonografija;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import programiranje.podaci_korisnika.tekst.FormatiranjeDatuma;

/**
 *
 * @author Mikec
 */
public class ImenovanjaDatoteka {
    private Date datum;
    private String usernameSender;
    private String usernameReceiver; 
    private boolean inbox; 
    private boolean outbox;   
    private String extension; 
    
    public ImenovanjaDatoteka(){
    }
    
    public ImenovanjaDatoteka(String usernameSender){
        this.usernameSender = usernameSender; 
        this.datum = new Date();
    }
    
    
    public ImenovanjaDatoteka(String sender, String receiver){
        this.usernameReceiver = receiver;
        this.usernameSender = sender;
        this.datum = new Date();
    }
    
    public ImenovanjaDatoteka(String sender, Date date){
        this.usernameSender = sender; 
        this.datum = date; 
    }
    
    public ImenovanjaDatoteka(String sender, String receiver, Date date){
        this.usernameSender = sender; 
        this.usernameReceiver = receiver; 
        this.datum = date; 
    }

    public ImenovanjaDatoteka(ImenovanjaDatoteka ida){
        this.usernameSender = ida.usernameSender;
        this.usernameReceiver = ida.usernameReceiver;
        this.extension = ida.extension;
        this.datum = ida.datum;
        this.inbox = ida.inbox;
        this.outbox = ida.outbox; 
    }
    
    public Date getDatum() {
        return datum;
    }

    public ImenovanjaDatoteka setDatum(Date datum) {
        this.datum = datum;
        return this; 
    }

    public String getUsernameSender() {
        return usernameSender;
    }

    public ImenovanjaDatoteka setUsernameSender(String usernameSender) {
        this.usernameSender = usernameSender;
        return this; 
    }

    public String getUsernameReceiver() {
        return usernameReceiver;
    }

    public ImenovanjaDatoteka setUsernameReceiver(String usernameReceiver) {
        this.usernameReceiver = usernameReceiver;
        return this; 
    }
    
    
    public String getName(){
       String name = "";
       if(usernameSender==null) return null; 
       name = usernameSender;  
       if(usernameReceiver==null) return name; 
       name+="-"+usernameReceiver;
       if(datum==null) return null; 
       name+="-"+FormatiranjeDatuma.formatranjeDatuma(datum);
       return name;
    }
    
    public String getInboxName(){
        return getName()+"-inbox"; 
    }
    
    public String getOutboxName(){
        return getName()+"-outbox"; 
    }
    
    public ImenovanjaDatoteka applyInboxName(File file){
        String ext = ""; 
        if(file.getName().contains(".")){
            String[] parts = file.getName().split("[.]"); 
            ext = parts[parts.length-1]; 
        }
        file.renameTo(new File(file.getParent(), getInboxName()+ext));
        return this; 
    }
    
    public ImenovanjaDatoteka applyOutboxName(File file){
        String ext = ""; 
        if(file.getName().contains(".")){
            String[] parts = file.getName().split("[.]"); 
            ext = parts[parts.length-1]; 
        }
        file.renameTo(new File(file.getParent(), getOutboxName()+ext));
        return this; 
    }
    
    
    public ImenovanjaDatoteka applyName(File file){
        String ext = ""; 
        if(file.getName().contains(".")){
            String[] parts = file.getName().split("[.]"); 
            ext = parts[parts.length-1]; 
        }
        file.renameTo(new File(file.getParent(), getName()+ext));
        return this; 
    }
    
    public ImenovanjaDatoteka applyFilename(File file){
        String ext = ""; 
        if(file.getName().contains(".")){
            String[] parts = file.getName().split("[.]"); 
            ext = parts[parts.length-1]; 
        }
        file.renameTo(new File(file.getParent(), getFilename()+ext));
        return this; 
    }
    
    public ImenovanjaDatoteka ifApplyFilename(File file){
        String ext = ""; 
        if(file.getName().contains(".")){
            String[] parts = file.getName().split("[.]"); 
            ext = parts[parts.length-1]; 
        }
        if(ext.equals(extension))
        file.renameTo(new File(file.getParent(), getFilename()+ext));
        return this; 
    }
    
    public String getTextMessageName(){
        return getName()+".txt"; 
    }
    
    public String getEncryptedMessageName(){
        return getName()+".enc"; 
    }
    
    public String getExtension(){
        return extension; 
    }
    
    public ImenovanjaDatoteka setExtension(String ekstenzija){
        this.extension = ekstenzija; 
        return this; 
    }
   
    
    public boolean isText(){
        if(extension==null) return false; 
        return extension.toLowerCase().equals(".txt"); 
    }
    
    
    public boolean isMessageForControl(){
        if(extension==null) return false; 
        return extension.toLowerCase().equals(".msg"); 
    }
    
    public boolean isEncryptedText(){
        if(extension==null) return false; 
        return extension.toLowerCase().equals(".enc"); 
    }
    
    
    public boolean isFileName(){
        if(extension==null) return false; 
        return true; 
    }
    
    public boolean isMessage(){
        return isFileName() && !isText() && !isEncryptedText();
    }
    
    public ImenovanjaDatoteka setText(){
        extension = ".txt"; 
        return this; 
    }
    
    public ImenovanjaDatoteka setEncryptedText(){
        extension = ".enc"; 
        return this; 
    }
    
    
    public boolean isOutbox(){
        return outbox; 
    }
    
    public boolean isInbox(){
        return inbox; 
    }
    
    public ImenovanjaDatoteka setOutbox(){
        outbox = true; 
        inbox = false;
        return this; 
    }
    
    public ImenovanjaDatoteka setInbox(){
        inbox = true; 
        outbox = false; 
        return this; 
    }
    
    public ImenovanjaDatoteka resetInbox(){
        inbox = false; 
        return this; 
    }
    
    
    public ImenovanjaDatoteka resetOutbox(){
        outbox = false; 
        return this; 
    }
    
    public ImenovanjaDatoteka resetExtension(){
        extension = null; 
        return this; 
    }
    
    
    public String getFilename(){
        if(!isFileName()) return null;
        if((isInbox() || isOutbox()) && (isText()||isEncryptedText())) return null;
        if(this.usernameReceiver==null) return null;
        if(isInbox()) return getInboxName();
        if(isOutbox()) return getOutboxName(); 
        return getName(); 
    }
    
    public String getFullFilename(){
        String name = getFilename(); 
        if(name==null) return null; 
        if(extension==null) return null; 
        return getFilename()+extension;
    }
    
    public File generateFile(){
        String name = getFullFilename(); 
        if(name==null) return null; 
        return new File(name);
    }
    
    public boolean checkFullFilename(String name) throws ParseException{
        String parts[] = name.split("-");
        if(parts.length>4 || parts.length<3) return false;
        
        if(parts.length==3){
            String [] eparts = parts[2].split("[.]");
            String ext = "."+eparts[eparts.length-1];
            String date = parts[2].substring(0, parts[2].length()-ext.length());
            if(FormatiranjeDatuma.uklapanjeDatuma(date)==null)  return false;
            if(!ext.toLowerCase().equals(".txt") && !ext.toLowerCase().equals(".enc"))
                return false;
        }
        else{
            if(FormatiranjeDatuma.uklapanjeDatuma(parts[2])==null) return false;
            if(!parts[3].startsWith("inbox") && !parts[3].startsWith("outbox")) return false; 
            if(parts[3].toLowerCase().equals("inbox.txt")) return false; 
            if(parts[3].toLowerCase().equals("outbox.txt")) return false; 
            if(parts[3].toLowerCase().equals("inbox.enc")) return false;  
            if(parts[3].toLowerCase().equals("outbox.enc")) return false; 
        }
        return true;
    }
    
    public boolean validFullFilename(String name) throws ParseException{
        name.equals(getFullFilename()); 
        return false; 
    }
    
    public ImenovanjaDatoteka reset(){
        this.resetInbox().resetOutbox().resetExtension();
        this.setUsernameSender(null).setUsernameReceiver(null).setDatum(null);
        return this; 
    }
    
    public boolean parseFullFilename(String name) throws ParseException{
        if(!this.checkFullFilename(name)) return false; 
        String parts[] = name.split("-");
        reset();
        usernameSender = parts[0];
        usernameReceiver =  parts[1];
        if(parts.length==3){
            String [] eparts = parts[2].split("[.]");
            String ext = "."+eparts[eparts.length-1];
            String date = parts[2].substring(0, parts[2].length()-ext.length());
            datum = FormatiranjeDatuma.uklapanjeDatuma(date); 
            extension = ext;
        }
        else{
            datum = FormatiranjeDatuma.uklapanjeDatuma(parts[2]);
            if(parts[3].startsWith("inbox")) this.setInbox();
            if(parts[3].startsWith("outbox")) this.setOutbox(); 
            if(parts[3].startsWith("inbox")) extension = parts[3].substring("inbox".length());
            if(parts[3].startsWith("outbox")) extension = parts[3].substring("outbox".length());
        }
        
        return true; 
    }
    
    public String generateBatFilenameForEmneding(){
        String str = this.getName(); 
        if(str==null) return null;
        return str+"-embedding.bat";
    }
    
    public String generateShellFilenameForEmneding(){
        String str = this.getName(); 
        if(str==null) return null;
        return str+"-embedding.sh";
    }
    
    public String generateBatFilenameForExtracting(){
        String str = this.getName(); 
        if(str==null) return null;
        return str+"-extracting.bat";
    }
    
    public String generateShellFilenameForExtracting(){
        String str = this.getName(); 
        if(str==null) return null;
        return str+"-extracting.sh";
    }
}
