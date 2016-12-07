package com.ibm.lms.engine.util;
import java.security.SecureRandom;
import java.util.Random;
public class OTPGenerator {
	
		
					public String generateOTP() {
			        String chars="0123456789";

			        final int OTP_LENGTH =8;
			        Random rnd = new SecureRandom();
			        StringBuilder OTP = new StringBuilder();
			        for (int i = 0; i < OTP_LENGTH; i++)
			            OTP.append(chars.charAt(rnd.nextInt(chars.length())));
			        return OTP.toString();
			    }
		}



