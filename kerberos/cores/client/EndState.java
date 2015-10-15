package kerberos.cores.client;

/**
 *
 * @author NPS
 */
public class EndState implements State {

    public State run() {
        System.out.println("Ende gut, alles gut!!!");
        return null;
    }

}
