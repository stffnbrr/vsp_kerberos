package kerberos.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Steffen
 */
public class UserInterface {
  
  public UserInterface(){
    
  }
  
  public String read(){
      System.out.println("Bitte etwas eingeben:");
      BufferedReader in = new BufferedReader( new InputStreamReader( System.in ));
      String input = null;
        try {
            input = in.readLine();
        } catch (IOException ex) {
            
        }
      input = input.replace("\n", "");

      return input;
  }

}
