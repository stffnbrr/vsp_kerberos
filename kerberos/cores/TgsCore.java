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
import kerberos.network.MessageListener;
import kerberos.network.NetworkServer;
import kerberos.ui.UserInterface;

/**
 *
 * @author Steffen
 */
public class TgsCore extends TicketServer{



    public TgsCore(NetworkServer network, UserInterface ui, String name, String key){
        super(network,ui,name,key);
    }


    public Message aktion(Message msg) {
        if(msg.getHeader().equals("TGSREQUEST")){
            System.out.println(msg);
            if(! this.checkPermission(msg)){
                return error("Authenticationerror!");
            }
            int stamp = Integer.parseInt(new String(msg.get("STAMP")));
            String servername = new String(msg.get("SERVER"));
            Ticket ticket = decryptTicket(msg.get("TICKET"));
            Auth auth = decryptAuth(msg.get("AUTH"),ticket.getSessionKey());
            System.out.println("Message:\nAuth:"+ auth + "\nTICKET:" + ticket + "\nSERVER:" + servername + "\nSTAMP:" + stamp);
            byte[] serverSession = this.generateSessionKey();
           
            Head head = this.createHead(serverSession, stamp);
            System.out.println(head);
            byte[] serverKey = servers.get(servername);
            System.out.println(serverKey);
            Date t1 = new Date();
            //TODO Date ist scheiße!
            Date t2 = new Date();
            t2.setHours(t1.getHours() + 12);
            Ticket newTicket = new Ticket(auth.getName(), servername,t1, t2, serverSession);
            return Messages.createTgsResponse(head, ticket.getSessionKey(), newTicket, serverKey);
        }else{
             return error("Wrong Message!");
        }
    }

    
}
