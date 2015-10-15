package kerberos.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import kerberos.des.TripleDes;

/**
 *
 * @author steffen
 */
public class Head implements Serializable{

    private byte[] kct;
    private int stamp;

    public Head(byte[] kct,int stamp){
        this.kct = kct;
        this.stamp = stamp;
    }

    public byte[] getSessionKey(){return kct;}
    public int getStamp(){return stamp;}

    public static Head fromBytes(byte[] auth,byte[] key){
        ObjectInputStream ois = null;
        Head result = null;
        try {
            TripleDes des = new TripleDes(key);
            byte[] decrpted = des.decrypt(auth);
            ByteArrayInputStream bis = new ByteArrayInputStream(decrpted);
            ois = new ObjectInputStream(bis);
            result = (Head)ois.readObject();
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
        return "HEAD:\nKEY:" + kct +"\nSTAMP" + stamp;
    }

}
