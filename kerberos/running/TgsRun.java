package kerberos.running;

import kerberos.cores.Core;
import kerberos.cores.TgsCore;

/**
 *
 * @author steffen
 */
public class TgsRun {

    public static void main(String[] args){
        System.out.println("TGSRun:");
        TgsCore ascore = Kerberos.createTgsServer("localhost:3001", "123456789012345678901234");
        ascore.addServer("localhost:3002", "098765432109876543210987");
        ascore.start();

    }

}
