package kerberos.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kerberos.messages.Message;


/**
 *
 * @author Steffen
 */
public class NetworkClient {
  
  private Socket socket;
  private ObjectInputStream in;
  private ObjectOutputStream out;
  private MessageListener listener;
  
  NetworkClient(MessageListener listener){
    this.listener = listener;
  }
  
  public void setMessageListener(MessageListener msg){
    this.listener = msg;
  }
  
  public void connectTo(String address){
    try {
      String[] parts = address.split(":");
      String ip = parts[0];
      int port = Integer.parseInt(parts[1]);
      System.out.println("Connecting to " + address);
      socket = new Socket(ip, port);
      in = new ObjectInputStream(socket.getInputStream());
      out = new ObjectOutputStream(socket.getOutputStream());
    } catch (Exception ex) {
      System.out.println(ex.toString());
    } 
    Thread t = new Thread(new Runnable() {

      public void run() {
        try {
          Message msg = (Message) in.readObject();
          listener.put(msg);
        } catch (Exception ex) {
          System.out.println(ex.toString());
        } 
        
      }
    });
    t.start();
  }
  
  
  
  public void send(Message msg){
    try {
      System.out.println("Sending:" + msg);
      out.writeObject(msg);
    } catch (IOException ex) {
      System.out.println(ex.toString());
    }
  }
  
  public void disconnect(){
    try {
      in.close();
      out.close();
      socket.close();
    } catch (IOException ex) {
       System.out.println(ex.toString());
    }
    in = null;
    out = null;
    socket = null;
  }

}
