/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonenumbervalidation;

import java.util.Scanner;

/**
 *
 * @author giorgos
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        PhoneNumberValidation app = new PhoneNumberValidation();

        Scanner scan = new Scanner(System.in);
        System.out.println("Give a telephone number.");
        System.out.println("The numbers must be 1 to 3 characters long and"
                + " each element must be space separated.");
        System.out.print("> ");
        String s = scan.nextLine();
        
        
        while(!app.validateAndInitializeInput(s)) {
            System.out.print("Not valid input.\n> ");
            s = scan.nextLine();
        }
        
        app.startValidation(s);
        
    }

}
