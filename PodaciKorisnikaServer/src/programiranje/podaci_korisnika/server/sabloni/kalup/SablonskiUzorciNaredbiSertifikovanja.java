/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.server.sabloni.kalup;

import java.util.HashMap;
import java.util.Scanner;
import programiranje.podaci_korisnika.konfiguracija.KonstanteDirektorijuma;
import programiranje.podaci_korisnika.konfiguracija.KonstanteSertifikataServera;
import programiranje.podaci_korisnika.tekst.FormatiranjeDatuma;

/**
 *
 * @author Mikec
 */
public class SablonskiUzorciNaredbiSertifikovanja {
    public final static String dir = "<dir>"; 
    public final static String mainstore = "<mainstore>"; 
    public final static String username = "<username>";
    public final static String certname = "<certname>"; 
    public final static String imeIPrezime = "<cn>";
    public final static String country = "<c>";
    public final static String state = "<st>";
    public final static String location = "<l>"; 
    public final static String organization = "<o>";
    public final static String organizationUnit = "<ou>";
    public final static String revocationDir = "<revokedir>";
    
    private String mainStoreValue = KonstanteSertifikataServera.glavniKeyStore;
    private String dirValue = "."; 
    private String usernameValue; 
    private String certnameValue; 
    private String imeIPrezimeValue = "";
    private String countryValue = "BA";
    private  String stateValue = "Republika Srpska";
    private  String locationValue = "";
    private  String organizationValue = "";
    private  String organizationUnitValue = "";
    private  String revocationDirValue = "."; 
    
    public String getDirValue() {
        return dirValue;
    }
    
    public String getRevocationDirValue() {
        return revocationDirValue;
    }

    public SablonskiUzorciNaredbiSertifikovanja setDirValue(String dirValue) {
        this.dirValue = dirValue;
        return this; 
    }

    public SablonskiUzorciNaredbiSertifikovanja setRevocationDirValue(String dirValue) {
        this.revocationDirValue = dirValue;
        return this; 
    }
    
    public String getMainStoreValue() {
        return mainStoreValue;
    }

    public SablonskiUzorciNaredbiSertifikovanja setMainStoreValue(String mainStoreValue) {
        this.mainStoreValue = mainStoreValue;
        return this;
    }

    public String getUsernameValue() {
        return usernameValue;
    }

    public SablonskiUzorciNaredbiSertifikovanja setUsernameValue(String usernameValue) {
        this.usernameValue = usernameValue;
        return this; 
    }
    
    public String getCertnameValue() {
        return certnameValue;
    }

    public SablonskiUzorciNaredbiSertifikovanja setCertnameValue(String certnameValue) {
        this.certnameValue = certnameValue;
        return this; 
    }
    
    public SablonskiUzorciNaredbiSertifikovanja setMainStoreValue(){
        mainStoreValue = KonstanteSertifikataServera.glavniKeyStore; 
        return this; 
    }
    
    public SablonskiUzorciNaredbiSertifikovanja setLinuxDirValue(){
        dirValue = "../"+ KonstanteDirektorijuma.arhivacertifikata; 
        return this;
    }
    
    public SablonskiUzorciNaredbiSertifikovanja setWindowsDirValue(){
        dirValue = "..\\\\"+ KonstanteDirektorijuma.arhivacertifikata; 
        return this; 
    }
    
    public SablonskiUzorciNaredbiSertifikovanja setLinuxDirRevokeValue(){
        revocationDirValue = "../"+ KonstanteDirektorijuma.arhivapovucenikcertifikata; 
        return this;
    }
    
    public SablonskiUzorciNaredbiSertifikovanja setWindowsDirRevokeValue(){
        revocationDirValue = "..\\\\"+ KonstanteDirektorijuma.arhivapovucenikcertifikata; 
        return this; 
    }
    
    public SablonskiUzorciNaredbiSertifikovanja setCertnameValue() {
        this.certnameValue = usernameValue +"-"+ FormatiranjeDatuma.formatranjeDatuma();
        return this; 
    }

    public String getImeIPrezimeValue() {
        return imeIPrezimeValue;
    }

    public void setImeIPrezimeValue(String imeIPrezimeValue) {
        this.imeIPrezimeValue = imeIPrezimeValue;
    }

    public String getCountryValue() {
        return countryValue;
    }

    public void setCountryValue(String countryValue) {
        this.countryValue = countryValue;
    }

    public String getStateValue() {
        return stateValue;
    }

    public void setStateValue(String stateValue) {
        this.stateValue = stateValue;
    }

    public String getLocationValue() {
        return locationValue;
    }

    public void setLocationValue(String locationValue) {
        this.locationValue = locationValue;
    }

    public String getOrganizationValue() {
        return organizationValue;
    }

    public void setOrganizationValue(String organizationValue) {
        this.organizationValue = organizationValue;
    }

    public String getOrganizationUnitValue() {
        return organizationUnitValue;
    }

    public void setOrganizationUnitValue(String organizationUnitValue) {
        this.organizationUnitValue = organizationUnitValue;
    }
    
    public void setImeIPrezimeValue(String ime,String prezime) {
        this.imeIPrezimeValue = ime+" "+prezime;
    }
    
    public String zamijenaUzorakaVrijednostima(String text){
        return text.replaceAll(dir, dirValue).replaceAll(revocationDir, revocationDirValue)
                   .replaceAll(username.replaceAll("[<]","[<]").replaceAll("[>]", "[>]"), usernameValue)
                   .replaceAll(certname.replaceAll("[<]","[<]").replaceAll("[>]", "[>]"), certnameValue)
                   .replaceAll(mainstore.replaceAll("[<]","[<]").replaceAll("[>]", "[>]"), mainStoreValue)
                   .replaceAll(imeIPrezime.replaceAll("[<]","[<]").replaceAll("[>]", "[>]"), imeIPrezimeValue.replaceAll("[,]","\\\\\\\\,"))
                   .replaceAll(country.replaceAll("[<]","[<]").replaceAll("[>]", "[>]"), countryValue.replaceAll("[,]","\\\\\\\\,"))
                   .replaceAll(state.replaceAll("[<]","[<]").replaceAll("[>]", "[>]"), stateValue.replaceAll("[,]","\\\\\\\\,"))
                   .replaceAll(location.replaceAll("[<]","[<]").replaceAll("[>]", "[>]"), locationValue.replaceAll("[,]","\\\\\\\\,"))
                   .replaceAll(organization.replaceAll("[<]","[<]").replaceAll("[>]", "[>]"), organizationValue.replaceAll("[,]","\\\\\\\\,"))
                   .replaceAll(organizationUnit.replaceAll("[<]","[<]").replaceAll("[>]", "[>]"), organizationUnitValue.replaceAll("[,]","\\\\\\\\,"));
    }
    
    public String generateBatScriptSadrzaj(){
        String res = "";
        Scanner scan = new Scanner(getClass().getResourceAsStream("/programiranje/podaci_korisnika/server/sabloni/korisnik.sertifikacija.bat.txt"));
        while(scan.hasNextLine()){
            res+=scan.nextLine()+"\n";
        }
        this.setWindowsDirValue();
        res = this.zamijenaUzorakaVrijednostima(res);
        scan.close();
        return res;
    }
    
    public String generateShellScriptSadrzaj(){
        String res = "";
        Scanner scan = new Scanner(getClass().getResourceAsStream("/programiranje/podaci_korisnika/server/sabloni/korisnik.sertifikacija.sh.txt"));
        while(scan.hasNextLine()){
            res+=scan.nextLine()+"\n";
        }
        this.setLinuxDirValue();
        res = this.zamijenaUzorakaVrijednostima(res);
        scan.close();
        return res;
    }
    
    public String generateBatScriptZaPovlacenjeSadrzaj(){
        String res = "";
        Scanner scan = new Scanner(getClass().getResourceAsStream("/programiranje/podaci_korisnika/server/sabloni/korisnik.sertifikacija.revoke.bat.txt"));
        while(scan.hasNextLine()){
            res+=scan.nextLine()+"\n";
        }
        this.setWindowsDirValue().setWindowsDirRevokeValue();
        res = this.zamijenaUzorakaVrijednostima(res);
        scan.close();
        return res;
    }
    
    public String generateShellScriptZaPovlacenjeSadrzaj(){
        String res = "";
        Scanner scan = new Scanner(getClass().getResourceAsStream("/programiranje/podaci_korisnika/server/sabloni/korisnik.sertifikacija.revoke.sh.txt"));
        while(scan.hasNextLine()){
            res+=scan.nextLine()+"\n";
        }
        this.setLinuxDirValue().setLinuxDirRevokeValue();
        res = this.zamijenaUzorakaVrijednostima(res);
        scan.close();
        return res;
    }
}
