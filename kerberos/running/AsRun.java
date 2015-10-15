package kerberos.running;

import kerberos.cores.AsCore;

/**
 *
 * @author steffen
 */
public class AsRun {

    public static void main(String[] args){
        System.out.println("AsRun:");
        AsCore ascore = Kerberos.createAsServer("localhost:3000");

        ascore.addTgs("localhost:3001", "123456789012345678901234");
        ascore.addUser("nelson", "1234567890");
        ascore.start();


    }

}
