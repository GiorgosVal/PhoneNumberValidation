/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonenumbervalidation;

import java.util.List;
import java.util.Map;

/**
 *
 * @author giorgos
 */
public class PhoneNumberValidation {

    public String[] input;
    public Map<String, List<Integer>> interpretations;

    public boolean validateAndInitializeInput(String in) {
        String[] temp = in.split(" ");
        for (String s : temp) {
            try {
                if (s.length() > 3 || Integer.parseInt(s) < 0) {
                    return false;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }
        input = temp;
        return true;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String s = "0 0 30 69 700 24 1 3 50 2";

        PhoneNumberValidation app = new PhoneNumberValidation();

        boolean a = app.validateAndInitializeInput(s);
        System.out.println(a);
        

        
    }

}
