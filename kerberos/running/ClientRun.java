package kerberos.running;

import kerberos.cores.Core;

/**
 *
 * @author steffen
 */
public class ClientRun {
    public static void main(String[] args){
        System.out.println("ClientRun:");
        Core client = Kerberos.createClient("nelson","localhost:3000", "localhost:3001", "localhost:3002");
        client.start();

    }
}
