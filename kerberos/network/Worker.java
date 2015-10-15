package kerberos.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import kerberos.messages.Message;

/**
 *
 * @author Steffen
 */
public class Worker extends Thread{
  
  private MessageListener listener;
  private Socket socket;
  private ObjectOutputStream out;
  private ObjectInputStream in;
  
  Worker(Socket socket,MessageListener listener) throws Exception{
    System.out.println("New Connection from " +socket.getRemoteSocketAddress().toString());
    this.listener = listener;
    this.socket = socket;
    out = new ObjectOutputStream(socket.getOutputStream());
    in = new ObjectInputStream(socket.getInputStream());
  }
  
  
  public void run(){
    while(true){
        try {
        Message msg = (Message)in.readObject();
        Message next = listener.aktion(msg);
            System.out.println(next);
        out.writeObject(next);
        } catch (Exception ex) {
            try {
            socket.close();
            in.close();
        out.close();
      } catch (IOException ex1) {
        ex1.printStackTrace();
      }
            System.out.println(ex.toString());
      break;
     
    }
    }
     
    
  }

}
