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
 * @author Steffen
 */
public class ServerCore extends TicketServer{


    ServerCore(NetworkServer network, UserInterface ui,String name, String key){
        super(network,ui,name,key);
    }


    public Message aktion(Message msg) {
        if(msg.getHeader().equals("REQUEST")){
            System.out.println(msg);
            if(! this.checkPermission(msg)){
                return error("Authenticationerror!");
            }
            String string = new String(msg.get("REQUEST"));
            return Messages.createResponse(new StringBuffer(string).reverse().toString());
        }else{
             return error("Wrong Message!");
        }
    }


}
