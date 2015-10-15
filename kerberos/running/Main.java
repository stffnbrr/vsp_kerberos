package kerberos.running;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import kerberos.cores.AsCore;
import kerberos.cores.Core;
import kerberos.cores.TgsCore;
import kerberos.des.TripleDes;

/**
 *
 * @author steffen
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        System.out.println("AsRun:");
                AsCore ascore = Kerberos.createAsServer("localhost:3000");

        ascore.addTgs("localhost:3001", "123456789012345678901234");
        ascore.addUser("nelson", "1234567890");
        ascore.start();

        //TGS
        System.out.println("TGSRun:");
        TgsCore tgs = Kerberos.createTgsServer("localhost:3001", "123456789012345678901234");
        tgs.addServer("localhost:3002", "098765432109876543210987");
        tgs.start();

        //Server
        System.out.println("ServerRun:");
        Core server = Kerberos.createServer("localhost:3002", "098765432109876543210987");
        server.start();

        //Client
        System.out.println("ClientRun:");
        Core client = Kerberos.createClient("nelson","localhost:3000", "localhost:3001", "localhost:3002");
        client.start();
    }



}
