/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonenumbervalidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author giorgos
 */
public class PhoneNumberValidation {

    public String[] input;
    public Map<String, List<String>> interpretations = new HashMap();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String s = "0 0 30 69 700 24 1 3 50 2";

        PhoneNumberValidation app = new PhoneNumberValidation();
        app.start(s);

    }

    public void start(String s) {
        if (validateAndInitializeInput(s)) {
            interpret();

        } else {
            System.out.println("Not valid input.");
        }
    }

    /**
     * This method validates the string input and in case of validity it
     * initializes the input array.
     *
     * An input is valid if each element: a) has length up to 3 characters, b)
     * can be parsed into an integer, c) is not negative.
     *
     * @param in
     * @return
     */
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
     * This method generates the interpretations for the strings in the input
     * array. For each element of the input array the method: a) Checks if the
     * element has length == 2 and if it contains 0s. b) Checks if the element
     * has length >= 2 and ends in 0 c) Checks if the element is the last of the
     * input array.
     *
     * If 'a' is true,then calls the addZeros() method and the returned list is
     * added to the temporary list. If 'b' and 'c' are true it just adds the
     * element to the temporary array list. If only 'b' is true, it calls the
     * removeZero() method and the returned list is added to the temporary list.
     * If nothing is true it just adds the element to the temporary list.
     *
     * At last the temporary list that holds all the interpretations for this
     * element it is stored as a value in the interpretations HashMap, with the
     * key "element + i", where i the number of the loop.
     *
     */
    public void interpret() {
        for (int i = 0; i < input.length; i++) {
            String number = input[i];
            int length = number.length();
            boolean hasLength2AndNoZero = length == 2 && !number.contains("0");
            boolean hasLenth2AndEndsInZero = length >= 2 && number.charAt(length - 1) == '0';
            boolean isLastElement = i == input.length - 1;

            List<String> list = new ArrayList();

            if (hasLength2AndNoZero) {
                list = addZeros(number);
            } else if (hasLenth2AndEndsInZero && isLastElement) {
                list.add(number);

            } else if (hasLenth2AndEndsInZero && !isLastElement) {
                list = removeZeros(number, input[i + 1]);

            }

            interpretations.put(number + i, list);
        }
    }

    /**
     * This method takes a string representing an integer returns a list
     * containing the interpretations of this string after adding a zero between
     * it's first and second character. e.g. input "25", returns an output list
     * of {"25", "205"}.
     *
     * @param s
     * @return
     */
    public List<String> addZeros(String s) {
        List<String> numbers = new ArrayList();
        numbers.add(s);
        numbers.add(s.substring(0, 1).concat("0").concat(s.substring(1)));
        return numbers;
    }

    /**
     * This method should be invoked only in the case where the current string
     * ends in zero.
     *
     * The method starts by first adding the current string in the return list.
     *
     * Then it continues by counting the number of 0s the current string ends
     * with starting from the 2nd character up until the last character.
     *
     * After this, it checks if the number of ending 0s is greater than or equal
     * to the length of the next string. If true, it creates a new
     * interpretation if the current string which is the same string without the
     * ending 0s that "refer" to the next string.
     *
     * Example #1: Current string: "700", Next string: "24", Output list:
     * {"700", "7"}
     *
     * Example #2: Current string: "700", Next string: "2", Output list: {"700",
     * "70"}
     *
     *
     * @param current
     * @param next
     * @return
     */
    public List<String> removeZeros(String current, String next) {
        List<String> numbers = new ArrayList();
        numbers.add(current);

        int countEndingZeros = 0;
        for (int i = 1; i < current.length(); i++) {
            if (current.charAt(i) == '0') {
                countEndingZeros++;
            } else {
                countEndingZeros = 0;
            }
        }

        int nextLength = next.length();

        if (countEndingZeros >= nextLength) {
            numbers.add(current.substring(0, current.length() - nextLength));
        }

        return numbers;
    }
    
    public 
    
    
    

}
