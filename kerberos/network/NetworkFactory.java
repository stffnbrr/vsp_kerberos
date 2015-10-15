package kerberos.network;

/**
 *
 * @author steffen
 */
public class NetworkFactory {

    public static NetworkClient create(MessageListener listener){
        return new NetworkClient(listener);
    }

    public static NetworkServer create(int port,int state,MessageListener listener){
        return new NetworkServer(port,state,listener);
    }

}
