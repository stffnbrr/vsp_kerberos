package kerberos.running;

import kerberos.cores.AsCore;
import kerberos.cores.Core;
import kerberos.cores.Cores;
import kerberos.cores.TgsCore;
import kerberos.messages.Message;
import kerberos.network.MessageListener;
import kerberos.network.NetworkClient;
import kerberos.network.NetworkFactory;
import kerberos.network.NetworkServer;
import kerberos.ui.UserInterface;

/**
 *
 * @author steffen
 */
public class Kerberos {

    public static AsCore createAsServer(String name){
        int port = Integer.parseInt(name.split(":")[1]);
        UserInterface ui = new UserInterface();
        NetworkServer server = NetworkFactory.create(port, NetworkServer.PARA, new MessageListener() {

            public Message aktion(Message msg) {
                System.out.println(msg);
                return null;
            }

            public void put(Message msg) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        return Cores.createAsCore(server,ui,name);
    }

    public static TgsCore createTgsServer(String name,String key){
        int port = Integer.parseInt(name.split(":")[1]);
        UserInterface ui = new UserInterface();
        NetworkServer server = NetworkFactory.create(port, NetworkServer.PARA, new MessageListener() {

            public Message aktion(Message msg) {
                System.out.println(msg);
                return null;
            }

            public void put(Message msg) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        return Cores.createTgsCore(server, ui, name, key);
    }

    public static Core createServer(String name, String key){
        int port = Integer.parseInt(name.split(":")[1]);
        UserInterface ui = new UserInterface();
        NetworkServer server = NetworkFactory.create(port, NetworkServer.PARA, new MessageListener() {

            public Message aktion(Message msg) {
                System.out.println(msg);
                return null;
            }

            public void put(Message msg) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        return Cores.createServerCore(server, ui, name, key);

    }

    public static Core createClient(String user,String as, String tgs, String server){
        UserInterface ui  = new UserInterface();
        NetworkClient nc = NetworkFactory.create(new MessageListener() {

            public Message aktion(Message msg) {

                return null;
            }

            public void put(Message msg) {
                System.out.println(msg);
            }
        });
        return Cores.createClientCore(user, as, tgs, server, nc, ui);
    }

}
