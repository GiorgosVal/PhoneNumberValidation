/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonenumbervalidation;

/**
 *
 * @author giorgos
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String s = "0 0 30 69 700 24 1 3 50 2";
        //s = "2 10 34 22 154";

        PhoneNumberValidation.startValidation(s);

    }

}
