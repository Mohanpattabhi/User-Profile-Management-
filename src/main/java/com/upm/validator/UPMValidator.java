package com.upm.validator;

import org.h2.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.upm.model.LoginFormBean;

/*
 * This class validates the fields in the login page
 */
public class UPMValidator implements Validator {

	public static final String PASSWORD_REGEX_PATTERN = "^[a-zA-Z0-9]*$";

	@Override
	public boolean supports(Class clazz) {
		return LoginFormBean.class.equals(clazz);
	}

	
	//Validation logic
	@Override
	public void validate(Object target, Errors errors) {
		LoginFormBean loginForm = (LoginFormBean) target;
		String password = loginForm.getPassword();

		if (StringUtils.isNullOrEmpty(password)) {
			errors.rejectValue("password", "password.mandatory",
					"Password is Mandatory.");
		} else if (!StringUtils.isNullOrEmpty(password)
				&& (!(password.length() >= 6 && password.length() <= 10))) {
			errors.rejectValue("password", "password.alphaNumeric",
					"Password must between 6 and 10 characters");
		}

		else if (!password.matches(PASSWORD_REGEX_PATTERN)) {
			errors.rejectValue("password", "password.alphaNumeric",
					"Password should be Alphanueric");
		}

	}

}
