package kerberos.cores.client;

import java.util.Date;
import kerberos.messages.Auth;
import kerberos.messages.Head;
import kerberos.messages.Message;
import kerberos.messages.Messages;
import kerberos.messages.Ticket;
import kerberos.network.MessageListener;

/**
 *
 * @author Steffen
 */
public class TgsState implements State, MessageListener{
  private ClientCore client;
  private int state;
  private Message response;


  public TgsState(ClientCore client){
    this.client = client;
    this.state = 0;
  }

  public Message aktion(Message msg) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void put(Message msg) {
    if(msg.getHeader().equals("TGSRESPONSE")){
      this.response = msg;
      state = 1;
    }else{
      System.out.println("Wrong Message:" + msg+ "\n" + new String(msg.get("MESSAGE")));
    }
  }


  public State run() {
      System.out.println("##TgsState");
    client.network.setMessageListener(this);
    client.network.connectTo(client.tgs);
    //Auth auth,byte[] authkey,byte[]ticket,String nameS,int stamp
    int stamp_user = client.getStamp();
    Date stamp = new Date();
    Auth auth = new Auth(client.user, stamp);


    Message msg = Messages.createTgsRequest(auth,  client.sessionkeys.get(client.tgs),client.tickets.get(client.tgs),client.server,client.stamp );
    client.network.send(msg);

    while(state != 1){
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
    Head head = Head.fromBytes(response.get("HEAD"), client.sessionkeys.get(client.tgs));
    client.checkStamp(stamp_user, head.getStamp());
    client.sessionkeys.put(client.server, head.getSessionKey());
    this.client.tickets.put(client.server, response.get("TICKET"));
    client.network.disconnect();
    return new ServerState(client);
  }

}
