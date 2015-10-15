package kerberos.cores;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import kerberos.des.TripleDes;
import kerberos.messages.Auth;
import kerberos.messages.Head;
import kerberos.messages.Message;
import kerberos.messages.Messages;
import kerberos.messages.Ticket;
import kerberos.network.NetworkServer;
import kerberos.ui.UserInterface;

/**
 *
 * @author steffen
 */
public abstract class TicketServer extends AbstractCore{

    private byte[] ownKey;
    private Map<String,byte[]> sessionKeys;
    private Map<String,Ticket> tickets;
    protected Map<String,byte[]> servers;
    private Map<String,Integer> users;

    TicketServer(NetworkServer network, UserInterface ui, String name, String key){
        super(network,ui,name);
        ownKey = TripleDes.createTripleDesKey(key);
        sessionKeys = new HashMap<String,byte[]>();
        tickets = new HashMap<String,Ticket>();
        servers = new HashMap<String,byte[]>();
        users = new HashMap<String,Integer>();
    }


    public void addServer(String server, String pw){
        servers.put(server, TripleDes.createTripleDesKey(pw));
    }

    protected Ticket decryptTicket(byte[] ticket){
        System.out.println(ownKey);
        Ticket t =  Ticket.fromBytes(ticket, ownKey);
        tickets.put(t.getName(), t);
        return t;
    }

    protected Auth decryptAuth(byte[] auth, byte[] sessionkey){
        return Auth.fromBytes(auth, sessionkey);
    }

    protected boolean checkPermission(Message msg){
        int stamp = Integer.parseInt(new String(msg.get("STAMP")));
        Ticket ticket = decryptTicket(msg.get("TICKET"));
        Auth auth = decryptAuth(msg.get("AUTH"),ticket.getSessionKey());
        return this.checkStamp(auth.getName(), stamp) && this.checkTicket(auth);
    }

    protected Head createHead(byte[] serverSession, int stamp){
        return new Head(serverSession,stamp);
    }


    public boolean checkTicket(Auth auth){
        Ticket ticket = tickets.get(auth.getName());
        System.out.println("AUTH:" +auth.getTimeStamp());
        System.out.println("BEGIN:" + ticket.getBegin());
        System.out.println("END:" + ticket.getEnd());
        if(auth.getTimeStamp().after(ticket.getBegin()) && auth.getTimeStamp().before(ticket.getEnd())){
            return true;
        }else{
            return false;
        }

    }


    public boolean checkStamp(String user,int stamp){
        if(users.get(user) != null){
            if(users.get(user) == stamp){
                users.put(user, stamp+1);
                return true;
            }else{
                return false;
            }
        }else{
            users.put(user, stamp);
            return true;
        }
    }

    public void put(Message msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
