package kerberos.cores.client;


import kerberos.des.TripleDes;
import kerberos.messages.Head;
import kerberos.messages.Message;
import kerberos.messages.Messages;
import kerberos.network.MessageListener;

/**
 *
 * @author Steffen
 */
public class AsState implements State, MessageListener{
  
  private ClientCore client;
  private int state;
  private Message response;
  
  
  public AsState(ClientCore client){
    this.client = client;
    this.state = 0;
  }

  public State run() {
      System.out.println("##AsState");
    client.network.setMessageListener(this);
    client.network.connectTo(client.as);
    int stamp = client.getStamp();
    Message msg = Messages.createAsRequest(client.user,client.tgs,stamp);
    client.network.send(msg);
    while(state != 1){
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
    System.out.println("Response");
    String pw = client.ui.read();
    TripleDes des = null;   
    String plaintext = null;
    Head head = Head.fromBytes(response.get("HEAD"),TripleDes.desToTripleDes(TripleDes.createDesKey(pw)));
    
    client.checkStamp(stamp, head.getStamp());
    this.client.sessionkeys.put(client.tgs, head.getSessionKey());
    this.client.tickets.put(client.tgs, response.get("TICKET"));
    client.network.disconnect();
    return new TgsState(client);
  }

  public Message aktion(Message msg) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void put(Message msg) {
    if(msg.getHeader().equals("ASRESPONSE")){
        System.out.println("Get:" + msg);
      this.response = msg;
      state = 1;
    }else{
      System.out.println("Wrong Message:" + msg + "\n" + new String(msg.get("MESSAGE")));
    }
  }

}
