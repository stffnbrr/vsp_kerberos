package kerberos.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import kerberos.des.TripleDes;

/**
 *
 * @author steffen
 */
public class Auth implements Serializable {

    private String nameC;
    private Date t;

    public Auth(String nameC,Date t){
        this.nameC = nameC;
        this.t = t;
    }

    public String getName(){return nameC;}

    public Date getTimeStamp(){return t;}

    public static Auth fromBytes(byte[] auth,byte[] key){
        ObjectInputStream ois = null;
        Auth result = null;
        try {
            TripleDes des = new TripleDes(key);
            byte[] decrpted = des.decrypt(auth);
            ByteArrayInputStream bis = new ByteArrayInputStream(decrpted);
            ois = new ObjectInputStream(bis);
            result = (Auth)ois.readObject();
            ois.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    public byte[] getBytes(byte[] key){
        ObjectOutputStream oos = null;
        byte[] data = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
            bos.close();
            data = bos.toByteArray();
            TripleDes des = new TripleDes(key);
            data =  des.encrypt(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public String toString(){
        return "AUTH:\nName: " + nameC + "\nTime:"+ t+ "\n";
    }


}
