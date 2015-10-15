package kerberos.network;

import kerberos.messages.Message;

/**
 *
 * @author Steffen
 */
public interface MessageListener {
  
  Message aktion(Message msg);
  void put(Message msg);

}
