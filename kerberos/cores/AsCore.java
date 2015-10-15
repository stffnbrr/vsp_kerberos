package kerberos.cores;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import kerberos.des.TripleDes;
import kerberos.messages.Head;
import kerberos.messages.Message;
import kerberos.messages.Messages;
import kerberos.messages.Ticket;
import kerberos.network.MessageListener;
import kerberos.network.NetworkServer;
import kerberos.ui.UserInterface;

/**
 *
 * @author Steffen
 */
public class AsCore extends AbstractCore{
    
    private Map<String,byte[]> users = new HashMap<String,byte[]>();
    private Map<String,byte[]> tgs   = new HashMap<String,byte[]>();

    public AsCore(NetworkServer network, UserInterface ui, String name){
        super(network,ui,name);
    }

    public void addUser(String name, String pw){
        users.put(name, TripleDes.createDesKey(pw));
    }

    public void addTgs(String name, String pw){
        tgs.put(name, TripleDes.createTripleDesKey(pw));
    }
    /*
     * Interpretiert eingehende Nachrichten.
     */
    public Message aktion(Message msg) {
        if(msg.getHeader().equals("ASREQUEST")){
            System.out.println(msg);
            String user = new String(msg.get("USER"));
            String tgsname = new String(msg.get("TGS"));
            System.out.println("tgsname: "+tgsname);
            int stamp = Integer.parseInt(new String(msg.get("STAMP")));
            byte[] pw = users.get(user);
            byte[] kt = tgs.get(tgsname);
            System.out.println("User:" + user);
            if(pw == null || kt == null){
                return Messages.createError("Unknown user or tgs!");
            }
            byte[] session = this.generateSessionKey();
            Head head = this.createHead(session, stamp);
            Date t1 = new Date();
            //TODO Date ist scheiße!
            Date t2 = new Date();
            t2.setHours(t1.getHours() + 12);

            Ticket ticket = new Ticket(user,tgsname,t1,t2,session);
            System.out.println("SESSION"+ session);
            Message newmsg = Messages.createAsResponse(head, pw, ticket, kt);
            System.out.println("Send:" + newmsg);
            return newmsg;
        }else{
            System.out.println("Wrong Message:" + msg);
            return Messages.createError("Can't understand the Message.");
        }
        
    }

    public Head createHead(byte[] session, int stamp){
        return new Head(session,stamp);
    }

    public void put(Message msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
