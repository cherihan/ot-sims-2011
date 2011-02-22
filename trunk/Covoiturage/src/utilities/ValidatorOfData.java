package utilities;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidatorOfData {
	
	public static Boolean validateData(String data){
			
		//TODO
		
		return true;
	}
	
	
	public static Boolean validateEMail(String email){
						
		// Input the string for validation
		// String email = "xyz@.com";
		// Set the email pattern string
		Pattern p = Pattern.compile("[a-zA-Z0-9\\-_+.]+@[a-zA-Z0-9\\-_+.]+\\.[a-zA-Z]{2,3}");

		// Match the given string with the pattern
		Matcher m = p.matcher(email);

		// check whether match is found
		boolean matchFound = m.matches();

		StringTokenizer st = new StringTokenizer(email, ".");
		String lastToken = null;
		while (st.hasMoreTokens()) {
			lastToken = st.nextToken();
		}

		if (matchFound && lastToken.length() >= 2
				&& email.length() - 1 != lastToken.length()) {

			// validate the country code
			return true;
		} else
			return false;
	}
	
	
	public static Boolean validatePhone(String phone){
		Pattern pattern = Pattern.compile("(0|(00\\d{2})|(\\+\\d{2}))\\d{9}");
	    Matcher matcher = pattern.matcher(phone);
	
	    if (matcher.matches()) {
	  	  return true;
	    }
	    else{
	    	return false;
	    }
	}
	
	
	public static Boolean validatePassWord(String password){
		
		//TODO
		
		return true;
	}
	
	

}
	
	


