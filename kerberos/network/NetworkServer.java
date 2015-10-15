package kerberos.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Steffen
 */
public class NetworkServer{
  
  public static int ITER = 0;
  public static int PARA = 1;
  
  private int port;
  private ServerSocket server;
  private Socket socket;
  private int state;
  private MessageListener listener;
  
  NetworkServer(int port,int state,MessageListener listener){
    this.port = port;
    this.state = state;
    this.listener = listener;
  }

  public void setMessageListener(MessageListener msg){
    this.listener = msg;
  }
  
  public void connect() {
    try{
        server = new ServerSocket(port);
        System.out.println("Server is running on Port:" + port);
        if(state == ITER){
            while(true){
                socket = server.accept();
                Worker worker = new Worker(socket,listener);
                worker.start();
            }
            
        }else{
            Thread t = new Thread(new Runnable() {

                public void run(){
                    while(true){
                    try {
                        socket = server.accept();
                        Worker worker = new Worker(socket,listener);
                        worker.start();
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                        break;
                    }
                    }
                    
          
                }
            });
            t.start();
        }
      }catch(Exception ex){
          ex.printStackTrace();
      }
    }
  
  public void disconnect(){
        try {
            socket.close();
            server.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
  }


  

}
