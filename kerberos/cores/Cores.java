package kerberos.cores;

import kerberos.cores.client.ClientCore;
import kerberos.network.NetworkClient;
import kerberos.network.NetworkServer;
import kerberos.ui.UserInterface;

/**
 *
 * @author steffen
 */
public class Cores {

    public static AsCore createAsCore(NetworkServer network, UserInterface ui,String name){
        return new AsCore(network,ui,name);
    }

    public static TgsCore createTgsCore(NetworkServer network, UserInterface ui, String name, String key){
        return new TgsCore(network,ui,name,key);
    }

    public static Core createServerCore(NetworkServer network, UserInterface ui, String name, String key){
        return new ServerCore(network,ui,name,key);
    }

    public static Core createClientCore(String user,String as, String tgs, String server, NetworkClient network, UserInterface ui){
        return new ClientCore(user,as,tgs,server,network,ui);
    }

}
