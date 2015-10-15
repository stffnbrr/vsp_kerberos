package kerberos.running;

import kerberos.cores.Core;

/**
 *
 * @author steffen
 */
public class ServerRun {
    public static void main(String[] args){
        System.out.println("ServerRun:");
        Core server = Kerberos.createServer("localhost:3002", "098765432109876543210987");
        server.start();

    }

}
