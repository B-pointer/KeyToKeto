package Calculations;
/** This class check password validity.
 * 
 * @version 1.0
 * @author dds19 Dwight Sims
 * KeyToKeto
 * File Name: CheckPassword.java
 */
import java.util.*;
public class CheckPassword {
	private static int minPwSize = 5;			// Min password length
	private static int minPwComplex = 8;		// Sets how complex the user password is (0-10)
	private static boolean goodPw = false;		// Pass or fail for a new password
	private static String reason = "None";		// Why the password failed check
	private static LinkedList<Character> newPW = new LinkedList<Character>();
	private static LinkedList<Character> secondPW = new LinkedList<Character>();
	
	public CheckPassword() {
		// TODO Auto-generated constructor stub
	}

	public static boolean CheckPassword(String myString) {
//		checkValidPW(myString);
		goodPw = checkValidPW(myString);
		System.out.println("Good password: " + goodPw);
		return goodPw;
	}

	public static boolean matchPassword(String myString1, String myString2) {
		boolean match = myString1.equals(myString2);		// Check if the passwords match
		return match;
	}
	
    private static boolean checkValidPW(String password){
    	reason = "Password does not meet mininum requirements: \n";
    	String subReason = "";
               
        int iPasswordScore = 0;								//total score of password complexity
        
        if( password.length() < minPwSize )	{				// Check password length
        	subReason = subReason + "Minimum length " + minPwSize + " \n";
        	reason = reason + subReason;
            return false;
        }
        else if( password.length() >= 10 ) {
            iPasswordScore += 2;
        }
        else {
            iPasswordScore += 1;
        }
            
        if( password.matches("(?=.*[0-9]).*") )				//if it contains one digit, add 2 to total score
            iPasswordScore += 2;
        else {
        	subReason = subReason + "Number [0-9] \\n";
        }
                
        if( password.matches("(?=.*[a-z]).*") )				//if it contains one lower case letter, add 2 to total score
            iPasswordScore += 2;
        else {
        	subReason = subReason + "Lowercase [a-z] \\n";
        }
                
        if( password.matches("(?=.*[A-Z]).*") )				//if it contains one upper case letter, add 2 to total score
            iPasswordScore += 2;    
        else {
        	subReason = subReason + "Uppercase [A-Z] \\n";
        }
                
        if( password.matches("(?=.*[~!@#$%^&*()_-]).*") )	//if it contains one special character, add 2 to total score
            iPasswordScore += 2;
        else {
        	subReason = subReason + "Special character [~!@#$%^&*()_-] \n";
        }
        
        if(iPasswordScore < minPwComplex) {					//Check total score of password complexity
        	System.out.println("Password does not meet mininum requirements");
        	subReason = "Complexity:" + subReason;
        	reason = reason + subReason;
            return false;
        }
        return true;        
    }
    
    static String getReason() {
    	return reason;
    }
}
