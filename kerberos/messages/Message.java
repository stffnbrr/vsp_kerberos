package kerberos.messages;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Steffen
 */
public class Message implements Serializable{

    private String header;
    private Map<String,byte[]> content;

    public Message(String header){
        this.header = header;
        content = new HashMap<String,byte[]>();
    }

    public String getHeader(){
        return header;
    }

    public void add(String str, byte[] value){
        content.put(str, value);
    }

    public byte[] get(String str){
        return content.get(str);
    }


    public String toString(){
        return "Message: " + header + "\n" + content.toString();
    }



}
