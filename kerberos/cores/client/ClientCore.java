package kerberos.cores.client;

import java.util.HashMap;
import java.util.Map;
import kerberos.cores.Core;
import kerberos.network.NetworkClient;
import kerberos.ui.UserInterface;

/**
 *
 * @author Steffen
 */
public class ClientCore implements Core {
  
  public String user;
  public String tgs;
  public String server;
  public String as;
  public Map<String,byte[]> sessionkeys;
  public Map<String,byte[]> tickets;
  public NetworkClient network;
  public State state;
  public UserInterface ui;
  public int stamp;
  
  public ClientCore(String user,String as, String tgs, String server, NetworkClient network, UserInterface ui){
    this.user = user;
    this.network = network;
    this.tgs = tgs;
    this.as = as;
    this.server = server;
    this.ui = ui;
    this.stamp = 1;
    sessionkeys = new HashMap<String,byte[]>();
    tickets = new HashMap<String,byte[]>();
  }
  
  public int getStamp(){
    stamp = stamp +1;
    return stamp;
  }

  public boolean checkStamp(int stamp1,int stamp2){
      return stamp1 == stamp2;
  }
  
  public void start(){
    State state = new AsState(this);
    while(state != null){
      state = state.run();
    }
  }
  

}
