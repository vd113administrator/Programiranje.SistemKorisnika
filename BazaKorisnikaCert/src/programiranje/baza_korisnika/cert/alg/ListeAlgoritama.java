/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.alg;

import java.security.Provider;
import java.security.Security;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.util.Pair;

/**
 * <b>Dobijanje naziva algritama</b>
 * @author Mikec
 */

public final class ListeAlgoritama {
 private ListeAlgoritama(){}
    
 /**
  * <b>Dobijanje naziva algoritama</b>
  * @param alg algoritmi
  * @return mapa naziva algoritama mapirana po namjeni
  */
 public static Map<String,String> getAlgorithm(String alg){
     HashMap<String,String> algs = new HashMap<>();
     for(Map.Entry<String,Set<String>> me : getAlgorithms().entrySet()){
         if(me.getValue().contains(alg))
         algs.put(me.getKey(),alg);
     }
     return algs;
 }
 
 /**
  * <b>Dobijanje konkretnog algoritma sa nazivom namjene. Potvrda parametara. Null.</b>
  * @param key namjena algoritma 
  * @param alg naziv algoritma
  * @return 
  */
 public static Pair<String,String> getAlgorithmFor(String key, String alg){
     Map.Entry<String, Set<String>> me = getAlgorithmsFor(key);
     if(me==null) return null;
     Set<String> set = me.getValue();
     if(set.contains(alg)){
         return new Pair<>(key,alg);
     }
     return null;
 }
 /**
  * <b>Dobijanje svih algoritama za zadatu namjenu.</b>
  * @param key namjena
  * @return algoritmi - nazivi
  */
 public static Map.Entry<String,Set<String>> getAlgorithmsFor(String key){
     for(Map.Entry<String,Set<String>> me : getAlgorithms().entrySet()){
         if(key.toUpperCase().trim().equals(me.getKey()))
             return me;
     }
     return null; 
 }
 
 /**
  * Dobijanje svih algoritama za sve namjene
  * @return mapa algoritama
  */
 public static Map<String,Set<String>> getAlgorithms() {
  Provider[] providers = Security.getProviders();
  Set<String> ciphers = new HashSet<String>();
  Set<String> keyAgreements = new HashSet<String>();
  Set<String> macs = new HashSet<String>();
  Set<String> messageDigests = new HashSet<String>();
  Set<String> signatures = new HashSet<String>();
  HashMap<String,Set<String>> mapa = new HashMap<>();
  
  mapa.put("CIPHER", ciphers);
  mapa.put("KEY AGREEMENT", keyAgreements);
  mapa.put("MAC", macs);
  mapa.put("MESSAGE DIGEST", messageDigests);
  mapa.put("SIGNATURE", signatures);
  
  for (int i = 0; i != providers.length; i++) {
   Iterator<Object> it = providers[i].keySet().iterator();

   while (it.hasNext()) {
    String entry = (String) it.next();

    if (entry.startsWith("Alg.Alias.")) {
     entry = entry.substring("Alg.Alias.".length());
    }

    if (entry.startsWith("Cipher.")) {
     ciphers.add(entry.substring("Cipher.".length()));
    } else if (entry.startsWith("KeyAgreement.")) {
     keyAgreements
       .add(entry.substring("KeyAgreement.".length()));
    } else if (entry.startsWith("Mac.")) {
     macs.add(entry.substring("Mac.".length()));
    } else if (entry.startsWith("MessageDigest.")) {
     messageDigests.add(entry.substring("MessageDigest."
       .length()));
    } else if (entry.startsWith("Signature.")) {
     signatures.add(entry.substring("Signature.".length()));
    }
   }
  }
  return mapa;
 }
 
 
 public static void main(String ... args){
     Map<String,Set<String>> mapa = getAlgorithms();
     for(String m: mapa.keySet()){
         System.out.println("\n"+m);
         for(String s: mapa.get(m))
             System.out.println("\t"+s);
     }
 }
}
