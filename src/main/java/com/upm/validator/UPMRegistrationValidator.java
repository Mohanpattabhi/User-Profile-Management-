package com.upm.validator;



import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.upm.model.LoginFormBean;
import com.upm.util.Constants;
import com.upm.util.ValidationUtils;

/*
 * This class validates the fields in the registration page
 */
public class UPMRegistrationValidator implements Validator {

	public static final String PASSWORD_REGEX_PATTERN = "^[a-zA-Z0-9]*$";
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private String fromPage;
	
	//from Page
	public UPMRegistrationValidator(String fromPage){
		this.fromPage = fromPage;
	}
	
	
	@Override
	public boolean supports(Class clazz) {
		return LoginFormBean.class.equals(clazz);
	}

	//Validation logic
	public void validate(Object target, Errors errors) {
		
		LoginFormBean loginForm = (LoginFormBean) target;
		String loginId = loginForm.getLoginId();
		String password = loginForm.getPassword();
		String email = loginForm.getEmail();
		String userName = loginForm.getUserName();
		String houseNumber = loginForm.getHouseNumber();
		String street = loginForm.getStreet();
		String city = loginForm.getCity();
		Integer state = loginForm.getState();
		String country = loginForm.getCountry();

		if(fromPage.equals(Constants.REGISTER_PAGE)){
		if (!StringUtils.isNotBlank(password)) {
			errors.rejectValue("password", "password.mandatory",
					"Password is mandatory.");
		} else if (StringUtils.isNotBlank(password)
				&& (!(password.length() >= 6 && password.length() <= 10))) {
			errors.rejectValue("password", "password.alphaNumeric",
					"Password must between 6 and 10 characters");
		}

		else if (!password.matches(PASSWORD_REGEX_PATTERN)) {
			errors.rejectValue("password", "password.alphaNumeric",
					"Password should be Alphanueric");
		}
		
		if(!StringUtils.isNotBlank(loginId)){
			errors.rejectValue("loginId", "userName.mandatory",
					"LoginId is mandatory");
		}
	}
		
		if(!StringUtils.isNotBlank(email)){
			errors.rejectValue("email", "email.mandatory",
					"Email is mandatory");
		}
		
		else if (!ValidationUtils.validateEmail(email)) {
			errors.rejectValue("email", "email.pattern",
					"Email is not in correct format");
		}
		
		if(!StringUtils.isNotBlank(houseNumber)){
			errors.rejectValue("houseNumber", "houseNumber.mandatory",
					"HouseNumber is mandatory");
		}
		else if (!StringUtils.isNumeric(houseNumber)) {
			errors.rejectValue("houseNumber", "houseNumber.numeric",
					"HouseNumber should be numeric value");
		}
		
		
		
		if(!StringUtils.isNotBlank(street)){
			errors.rejectValue("street", "street.mandatory",
					"Street is mandatory");
		}
		
		if(!StringUtils.isNotBlank(userName)){
			errors.rejectValue("userName", "userName.mandatory",
					"UserName is mandatory");
		}
		
		if(!StringUtils.isNotBlank(city)){
			errors.rejectValue("city", "city.mandatory",
					"City is mandatory");
		}
		
		if(state == 0){
			errors.rejectValue("state", "state.mandatory",
					"State is mandatory");
		}
		
		if(!StringUtils.isNotBlank(country)){
			errors.rejectValue("country", "country.mandatory",
					"Country is mandatory");
		}
		
		
		

	}

}

