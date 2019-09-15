/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonenumbervalidation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author giorgos
 */
public class PhoneNumberValidation {

    private String[] input;
    private Map<Integer, Set<String>> interpretations = new HashMap();

    public String[] getInput() {
        return input;
    }

    public Map<Integer, Set<String>> getInterpretations() {
        return interpretations;
    }

    /**
     * This method handles the validation process of the input.
     *
     * The validation process is step-specific: First the input is checked, then
     * interpretations for each element are generated, then combinations of the
     * interpretations are created, and lastly each combination is checked and
     * print.
     *
     * It should be a mistake to expose this logic outside the class. Thus, this
     * public and static method can be called from outside the class, and can
     * handle this logic.
     *
     * @param s
     */
    public void startValidation(String s) {
        interpretationsHandler();
        populate();
        printInterpretations();

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
                return false;
            }
        }
        input = temp;
        return true;
    }

    /**
     * This method handles the generation of interpretations for each element of
     * the input. For each element of the input the method creates a set of
     * strings. This set will contain all interpretations and it will be stored
     * in the interpretations HashMap.
     *
     * For each element the method first calls the addZerosHandler method which
     * handles the ambiguities in a certain string.
     *
     * After this, if the element is not the last element of the input, it calls
     * the removeZeros method which handles the ambiguities between two strings.
     */
    private void interpretationsHandler() {
        for (int i = 0; i < input.length; i++) {
            Set<String> number = new HashSet();
            number.add(input[i]);
            number = addZerosHandler(number, 0);
            if (i < input.length - 1) {
                number = removeZeros(number, input[i + 1]);
            }
            interpretations.put(i, number);
        }
    }

    /**
     * This method is handles the invocation of addZerosToString method. It
     * examines the conditions that should apply in order to add 0s to a number
     * (in string format).
     *
     * It accepts a set of strings ('original') and creates a copy of it. It
     * also accepts an index, which defines the starting character for each
     * string, inside the for loop.
     *
     * The original set is used to iterate over it's elements, and the copy set
     * to store any new element that is created. At the end of the iteration, if
     * the original and the copy sets differ in size, it means that there are
     * new elements that need to be evaluated (to examine if there is need of 0s
     * insertion). In case the two sets differ, the method calls itself passing
     * as argument the modified copied set, and the index raised by 1.
     *
     * The following conditions must apply in order to insert 0s in a string:
     *
     * 1. The current and the next characters are not 0:
     *
     * e.g. The numbers 2, 20, 02, 000, 002 doesn't need 0s insertion.
     *
     * e.g. The number 22 is evaluated to 22 and 202
     *
     * e.g. The number 220 is evaluated to 220 and 20020.
     *
     * e.g. The number 222 is evaluated to 222, 20022, 2202, 200202
     *
     *
     * 2. The current character is 0, and the previous and next characters are
     * not 0:
     *
     * e.g. The number 202 is evaluated to 202 and 2002
     *
     * e.g. The number 2202 should NOT be evaluated to 22002
     *
     * The number 200202 SHOULD NOT be evaluated to 2002002
     *
     * as this does not make sense. In order to avoid this, the index is used.
     * When the index is not 0, this means that we are not in the 1st call of
     * the method. Because the original strings that are evaluated have length 1
     * to 3, there is no use of the 2nd condition described above.
     *
     * For example, the number 222 is evaluated to 222, 20022 and 2202 in the
     * 1st recursion, applying the 1st condition. In the 2nd recursion, the 1st
     * condition generates the number 200202 (from 20022) which is correct. The
     * 2nd condition generates the number 22002 (from 2202) which is incorrect.
     * The 3rd recursion, applying the 2nd condition will generate the number
     * 2002002 (from 200202) which is incorrect. We can skip the generation of
     * incorrect values if we think that in the 2nd recursion and above, there
     * is no need to evaluate the strings using the 2nd condition. That is
     * because we are only evaluating strings of length 1 to 3.
     *
     *
     * @param set
     * @param index
     * @return
     */
    private Set<String> addZerosHandler(Set<String> set, int index) {
        Set<String> numbers = new HashSet(set);                           // copy of the original set
        Iterator<String> it = set.iterator();                            // iterator on the original set
        while (it.hasNext()) {
            String s = it.next();
            for (int i = index; i < s.length() - 1; i++) {
                if (s.charAt(i) != '0' && s.charAt(i + 1) != '0') {     // comparing current and next char with '0'
                    numbers.add(addZerosToString(s, i));
                } else if (index == 0 && i > 0 && s.charAt(i - 1) != '0' && s.charAt(i + 1) != '0') {
                    numbers.add(addZerosToString(s, i));
                }
            }
        }

        if (numbers.size() != set.size()) {
            numbers.addAll(addZerosHandler(numbers, ++index));
        }

        return numbers;
    }

    /**
     * This method inserts 0s to a given string starting at the position defined
     * by the index. The number of 0s is calculated by the number of the
     * characters that follow the starting character.
     *
     * e.g. For the string "22" starting at index = 0, the result will be "202",
     * because there is only 1 character that follows the starting character.
     *
     * e.g. For the string "220" starting at index = 0, the result will be
     * "20020", because there are 2 characters that follow the starting
     * character.
     *
     * @param s
     * @param index
     * @return
     */
    private String addZerosToString(String s, int index) {
        int howManyZeros = s.length() - (index + 1);
        StringBuilder string = new StringBuilder();
        string
                .append(s.substring(0, index + 1))
                .append(String.join("", Collections.nCopies(howManyZeros, "0")))
                .append(s.substring(index + 1));
        return string.toString();
    }

    /**
     * This method is used to check for ambiguities between two stings (e.g.
     * string "200" followed by "20" could be evaluated to "20020" or "220").
     *
     * The method accepts a set of strings that represent the first string and
     * another string that represents the string that follows the strings of the
     * set.
     *
     * The method starts by first creating a copy of the original set, and
     * setting an iterator on the original set.
     *
     * After this, it checks if the length of the element is bigger than 1, and
     * if the element ends in 0. If true, it continues by counting the number of
     * 0s the current string ends with, starting from the 2nd character up until
     * the last character.
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
    private Set<String> removeZeros(Set<String> set, String next) {
        Set<String> returned = new HashSet(set);
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            int zeros = 0;
            String current = it.next();
            int length = current.length();
            if (length >= 2 && current.charAt(length - 1) == '0') {
                for (int i = 1; i < length; i++) {
                    if (current.charAt(i) == '0') {
                        zeros++;
                    } else {
                        zeros = 0;
                    }
                }
                int nextLength = next.length();
                if (zeros >= nextLength) {
                    returned.add(current.substring(0, length - nextLength));
                }
            }
        }
        return returned;
    }

    /**
     * This method populates all possible combinations of the interpretations
     * included in the HashMap. Each key of the HashMap contains as value a set
     * of interpretations. The method starts from the two last keys of the
     * HashMap and moves to the top. In each loop it combines the elements of
     * the current set with these of the previous set. Through this combination,
     * a new set is created, which replaces the set of the previous key. At the
     * end of all loops, the first key of the HashMap contains a set with all
     * possible combinations of the interpretations.
     */
    private void populate() {
        for (int i = input.length - 1; i > 0; i--) {

            Set<String> last = interpretations.get(i);
            Set<String> previous = interpretations.get(i - 1);

            Set<String> replacement = new HashSet();

            for (String p : previous) {
                for (String l : last) {
                    replacement.add(p.concat(l));
                }
            }

            interpretations.put(i - 1, replacement);
        }
    }

    /**
     * This method prints each element while calling the checkTelephone() to
     * check of the element is a valid telephone number.
     */
    private void printInterpretations() {
        Set<String> list = interpretations.get(0);
        int count = 0;
        for (String s : list) {
            count++;
            System.out.printf("Interpretation %3s: %-20s%10s\n", count, s,
                    "[phone number: " + checkTelephone(s) + "]");
        }

    }

    /**
     * This method checks if the string is a valid telephone number. To be
     * valid, a telephone number must either have 10 digits (starting from 2 or
     * 69) or to have 14 digits (starting from 00302 or 003069).
     *
     * @param s
     * @return
     */
    private String checkTelephone(String s) {
        int length = s.length();
        if (length == 10 && (s.startsWith("2") || s.startsWith("69"))) {
            return "VALID";
        }
        if (length == 14 && (s.startsWith("00302") || s.startsWith("003069"))) {
            return "VALID";
        }
        return "INVALID";
    }

}
