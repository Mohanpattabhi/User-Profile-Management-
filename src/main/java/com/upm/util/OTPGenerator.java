package com.upm.util;

import java.util.Random;


/*
 * Thsi class generates the OTP for login
 */
public class OTPGenerator {
	private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String NUM = "0123456789";
	private static final String SPL_CHARS = ":<=>?@_!#%&()*+,-.~";
	
	/*
	 * Thsi method generates the OTP which has 1 spl char and digit
	 */

	public static String generateOTP() {

		Random rnd = new Random();
		int noOfDigits = 1;
		int noOfSplChars = 1;
		int len = 8;
		char[] otpChars = new char[len];
		int index = 0;
		for (int i = 0; i < noOfDigits; i++) {
			index = getNextIndex(rnd, len, otpChars);
			otpChars[index] = NUM.charAt(rnd.nextInt(NUM.length()));
		}
		for (int i = 0; i < noOfSplChars; i++) {
			index = getNextIndex(rnd, len, otpChars);
			otpChars[index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
		}
		for (int i = 0; i < len; i++) {

			if (otpChars[i] == 0) {
				otpChars[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
			}
		}
		return new String(otpChars);
	}

	private static int getNextIndex(Random rnd, int len, char[] pswd) {
		int index = rnd.nextInt(len);
		while (pswd[index = rnd.nextInt(len)] != 0)
			;
		return index;
	}

}