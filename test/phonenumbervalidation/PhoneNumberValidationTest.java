/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonenumbervalidation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author giorgos
 */
public class PhoneNumberValidationTest {

    private static String[] legal = {
        "2 10 69 30 6 6 4", "210 69 30 664", "2 10 69 30 6 60 4",
        "0 0 30 69 74 0 9 22 52", "0 030 69 74 09 22 52"
    };

    private static String[] notLegal = {
        "2106 9 30 6 6 4", "21069 30 664", "21069306604",
        "0a 0 30 69 74 0 9 22 52", "not number"
    };
    
    
    
    private static Map<String, Integer> legalElements = new HashMap();
    
    static {
        legalElements.put("2 10 69 30 6 6 4", 7);
        legalElements.put("210 69 30 664", 4);
        legalElements.put("2 10 69 30 6 60 4", 7);
        legalElements.put("0 0 30 69 74 0 9 22 52", 9);
        legalElements.put("0 030 69 74 09 22 52", 7);
    }
    

    public PhoneNumberValidationTest() {
    }

    /**
     * Test of startValidation method, of class PhoneNumberValidation.
     */
    @Test
    public void testStartValidation_Legal() {
        System.out.println("startValidation");
        for (String s : legal) {
            PhoneNumberValidation.startValidation(s);
        }

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void validateAndInitializeInput_Legal() throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        System.out.println("validateAndInitializeInput");
        Method m = PhoneNumberValidation.class.getDeclaredMethod("validateAndInitializeInput", String.class);
        m.setAccessible(true);
        PhoneNumberValidation obj = new PhoneNumberValidation();
        for (String s : legal) {
            boolean result = (boolean) m.invoke(obj, s);
            assertTrue(s, result);
            assertEquals(legalElements.get(s), (Integer) obj.getInput().length);
        }
    }

    @Test
    public void validateAndInitializeInput_NotLegal() throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        System.out.println("validateAndInitializeInput");
        Method m = PhoneNumberValidation.class.getDeclaredMethod("validateAndInitializeInput", String.class);
        m.setAccessible(true);
        PhoneNumberValidation obj = new PhoneNumberValidation();
        for (String s : notLegal) {
            boolean result = (boolean) m.invoke(obj, s);
            assertFalse(s, result);
            assertArrayEquals(null, obj.getInput());
        }
    }

    @Test
    public void interpretationsHandler() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        System.out.println("interpretationsHandler");
        Method m = PhoneNumberValidation.class.getDeclaredMethod("interpretationsHandler", null);
        m.setAccessible(true);
        
        Method m2 = PhoneNumberValidation.class.getDeclaredMethod("validateAndInitializeInput", String.class);
        m2.setAccessible(true);
        
        PhoneNumberValidation obj = new PhoneNumberValidation();
        
        for(String s : legal) {
            m2.invoke(obj, s);
            m.invoke(obj, null);
        }
        

    }
    
    @Test
    public void addZerosHandler() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        System.out.println("addZerosHandler");
        Method m = PhoneNumberValidation.class.getDeclaredMethod("addZerosHandler", Set.class, int.class);
        m.setAccessible(true);
        
        PhoneNumberValidation obj = new PhoneNumberValidation();
        Set<String> set = new HashSet();
        set.add("2");
        set.add("20");
        set.add("22");
        set.add("02");
        set.add("000");
        set.add("002");
        set.add("020");
        set.add("022");
        set.add("200");
        set.add("202");
        set.add("220");
        set.add("222");
        
        Set<String> result = (Set<String>) m.invoke(obj, set, 0);
        
        Set<String> expected = new HashSet();
        expected.add("2");
        expected.add("20");
        expected.add("22");
        expected.add("202");
        expected.add("02");
        expected.add("000");
        expected.add("002");
        expected.add("020");
        expected.add("022");
        expected.add("0202");
        expected.add("200");
        expected.add("202");
        expected.add("2002");
        expected.add("220");
        expected.add("20020");
        expected.add("222");
        expected.add("20022");
        expected.add("2202");
        expected.add("200202");
        
        
        for(String s : result) {
            Iterator<String> it = expected.iterator();
            while(it.hasNext()) {
                String str = it.next();
                if(s.equals(str)) {
                    it.remove();
                    break;
                }
            }
        }
        
        assertEquals(0, expected.size());
        
    }
    
    

}
