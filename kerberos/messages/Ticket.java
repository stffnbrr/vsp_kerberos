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
public class Ticket implements Serializable{

    private String nameC;
    private String nameT;
    private Date t1;
    private Date t2;
    byte[] kct;

    public Ticket(String nameC, String nameT,Date t1, Date t2, byte[] kct){
        this.nameC = nameC;
        this.nameT = nameT;
        this.t1 = t1;
        this.t2 = t2;
        this.kct =kct;
    }

    public String getName(){return nameC;}

    public String getTarget(){return nameT;}

    public Date getBegin(){return t1;}

    public Date getEnd(){return t2;}

    public byte[] getSessionKey(){return kct;}

    /**
     * Wandelt einen verschlüsselten ByteStream in ein Ticket um.
     */
    public static Ticket fromBytes(byte[] auth,byte[] key){
        ObjectInputStream ois = null;
        Ticket result = null;
        try {
            TripleDes des = new TripleDes(key);
            byte[] decrpted = des.decrypt(auth);
            ByteArrayInputStream bis = new ByteArrayInputStream(decrpted);
            ois = new ObjectInputStream(bis);
            result = (Ticket)ois.readObject();
            ois.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }
    /*
     * Wandelt ein Ticket in einen verschlüsselten ByteStream um.
     */
    public byte[] getBytes(byte[] key){
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
            bos.close();
            byte[] data = bos.toByteArray();
            TripleDes des = new TripleDes(key);
            return des.encrypt(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String toString(){
        return "TICKET:\nNAME:" + nameC + "\nNAMESERVER:" + nameT + "\nt1:" + t1 + "\nt2:" + t2 + "\nKEY:" + kct;
    }

}
