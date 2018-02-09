/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.alatke;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Mikec
 */
public final class SRZSerializer {
    private SRZSerializer(){}
    public static byte[] marshall(Serializable srz) throws IOException{
        byte[] bytes = {};
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(srz);
        bytes = baos.toByteArray();
        oos.close();
        return bytes; 
    }
    public static byte[] serialize(Serializable srz) throws IOException{
        return marshall(srz);
    }
    public static Serializable unmarshal(byte [] bytes) throws IOException, ClassNotFoundException{
        Serializable srz = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        srz = (Serializable)ois.readObject();
        ois.close(); 
        return srz; 
    }
    public static Serializable deserialize(byte [] bytes) throws IOException, ClassNotFoundException{
        return unmarshal(bytes);
    }
}
