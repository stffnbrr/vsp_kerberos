package kerberos.cores;

import kerberos.des.TripleDes;
import kerberos.messages.Message;
import kerberos.messages.Messages;
import kerberos.network.MessageListener;
import kerberos.network.NetworkServer;
import kerberos.ui.UserInterface;

/**
 *
 * @author steffen
 */
public abstract class AbstractCore implements MessageListener, Core{

    protected String name;
    protected NetworkServer network;
    protected UserInterface ui;

    AbstractCore(NetworkServer network, UserInterface ui, String name){
        this.network = network;
        this.ui = ui;
    }

    public void start() {
        System.out.println("Starting " + this.getClass().toString() +"...");
        network.setMessageListener(this);
        network.connect();
    }

    public abstract Message aktion(Message msg);

    public byte[] generateSessionKey(){
        return TripleDes.createTripleDeyKey();
    }


    public Message error(String text){
        System.out.println(text);
        return Messages.createError(text);
    }


    public void put(Message msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
