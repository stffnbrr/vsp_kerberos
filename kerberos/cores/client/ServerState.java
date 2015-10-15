package kerberos.cores.client;

import java.util.Date;
import kerberos.messages.Auth;
import kerberos.messages.Message;
import kerberos.messages.Messages;
import kerberos.network.MessageListener;

/**
 *
 * @author NPS
 */
public class ServerState implements State, MessageListener{
    private ClientCore client;
    private int state;
    private Message response;


    public ServerState(ClientCore client){
       this.client = client;
       this.state = 0;
    }
    public State run() {
        System.out.println("##ServerState");
        client.network.setMessageListener(this);
        client.network.connectTo(client.server);
        Auth auth = new Auth(client.user, new Date());
        String re = client.ui.read();
        Message msg = Messages.createRequest(auth,client.sessionkeys.get(client.server),client.tickets.get(client.server),re,client.stamp );
        client.network.send(msg);
        while(state != 1){
          try {
            Thread.sleep(1000);
          } catch (InterruptedException ex) {
            System.out.println(ex.toString());
          }
        }
        
        byte[] result = response.get("RESPONSE");
        System.out.println(new String(result));
        client.network.disconnect();
        return new EndState();
    }

    public Message aktion(Message msg) {
        return null;
    }

    public void put(Message msg) {
        if(msg.getHeader().equals("RESPONSE")){
          this.response = msg;
          state = 1;
        }else{
          System.out.println("Wrong Message:" + msg);
        }
    }

}
