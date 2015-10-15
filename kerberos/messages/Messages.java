package kerberos.messages;

import kerberos.des.TripleDes;

/**
 *
 * @author steffen
 */
public class Messages {

    public static Message createAsRequest(String user, String tgs,int stamp){
        Message msg = new Message("ASREQUEST");
        msg.add("USER", user.getBytes());
        msg.add("TGS", tgs.getBytes());
        msg.add("STAMP", Integer.toString(stamp).getBytes());
        return msg;
    }

    public static Message createAsResponse(Head head, byte[] headkey, Ticket ticket, byte[] ticketkey){
        Message msg = new Message("ASRESPONSE");
        msg.add("HEAD", head.getBytes(TripleDes.desToTripleDes(headkey)));
        msg.add("TICKET", ticket.getBytes(ticketkey));
        return msg;
    }

    public static Message createTgsRequest(Auth auth,byte[] authkey,byte[]ticket,String nameS,int stamp){
        Message msg = new Message("TGSREQUEST");
        msg.add("AUTH", auth.getBytes(authkey));
        msg.add("TICKET", ticket);
        msg.add("SERVER", nameS.getBytes());
        msg.add("STAMP", Integer.toString(stamp).getBytes());
        return msg;
    }

    public static Message createTgsResponse(Head head, byte[] headkey,Ticket ticket, byte[]ticketkey){
        Message msg = new Message("TGSRESPONSE");
        msg.add("HEAD", head.getBytes(headkey));
        msg.add("TICKET", ticket.getBytes(ticketkey));
        return msg;
    }

    public static Message createRequest(Auth auth,byte[] authkey, byte[] ticketkey,String request,int stamp){
        Message msg = new Message("REQUEST");
        msg.add("AUTH", auth.getBytes(authkey));
        msg.add("TICKET", ticketkey);
        msg.add("REQUEST", request.getBytes());
        msg.add("STAMP", Integer.toString(stamp).getBytes());
        return msg;
    }

    public static Message createResponse(String response){
        Message msg = new Message("RESPONSE");
        msg.add("RESPONSE", response.getBytes());
        return msg;
    }

    public static Message createError(String error){
        Message msg = new Message("ERROR");
        msg.add("MESSAGE", error.getBytes());
        return msg;
    }

}
