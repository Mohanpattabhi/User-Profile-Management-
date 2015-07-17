package com.upm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.upm.Exceptions.UPMException;
import com.upm.model.Address;
import com.upm.model.LoginFormBean;
import com.upm.model.User;
import com.upm.model.UserLoginDetailsVO;
import com.upm.service.LoginService;
import com.upm.util.Constants;
import com.upm.validator.UPMValidator;

/*
 * This class acts as a controller for all the login and Registration 
 * related operations
 * 
 */
@Controller
public class LoginController {

	private LoginService loginService;

	/*
	 * Service layer injection
	 */
	@Autowired(required = true)
	@Qualifier(value = "loginService")
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	/*
	 * This method handles the re-direction to login page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String displayLoginForm(Model model) {
		model.addAttribute("loginForm", new LoginFormBean());
		return "login";
	}

	/*
	 * This method handles login submit related work-flows
	 */
	@RequestMapping(value = "/loginSubmit", method = RequestMethod.POST)
	public String loginSubmitAction(Model model,
			@Valid @ModelAttribute("loginForm") LoginFormBean loginFormBean,
			BindingResult bindingResult) {
		String globalErrorMessage = Constants.EMPTY_STRING;
		Integer status = Constants.LOGIN_SUCCESS;

		/*
		 * Validating the login
		 */
		UPMValidator userValidator = new UPMValidator();
		userValidator.validate(loginFormBean, bindingResult);

		/*
		 * Any errors re-direct to loginPage with error messages
		 */
		if (bindingResult.hasErrors()) {
			return "login";
		}

		UserLoginDetailsVO userLoginDetailsVO = loginService
				.validateLogin(loginFormBean);
		status = userLoginDetailsVO.getStatus();

		// login id dosnt exist in the database
		if (status == Constants.LOGIN_ID_DOESNOT_EXIST) {
			globalErrorMessage = "LoginId does not exist";

		} else if (status == Constants.LOGIN_OTP_EXPIRED) {
			// created time of the entered OTP has elapsed the time given in
			// TimeConfig.xml
			globalErrorMessage = "OTP is expired";

		} else if (status == Constants.LOGIN_OTP_DOESNOT_EXIST) {
			// OTP does not exist in the database
			globalErrorMessage = "OTP does not exist";
		} else if (status == Constants.LOGIN_OTP_NOT_MATCHING) {
			// Entered OTP is not matching with that of the user in the DB
			globalErrorMessage = "You have entered a wrong OTP. Please generate a new OTP";
		} else if (status == Constants.LOGIN_PASSWORD_NOT_MATCHING) {
			// password is not mathcing
			globalErrorMessage = "Pls enter correct password";
		}

		// global error messages
		if (!StringUtils.isNullOrEmpty(globalErrorMessage)) {
			model.addAttribute("errorMessage", globalErrorMessage);
			return "login";
		}

		if (null != userLoginDetailsVO.getUser()) {
			copyUserToLoginFormBean(userLoginDetailsVO.getUser(), loginFormBean);
		}
		model.addAttribute("loginForm", loginFormBean);
		model.addAttribute("errorMessage", globalErrorMessage);
		return "update";
	}

	/*
	 * This method generates the new OTP. If login Id does not exist. This
	 * method throws exception
	 */
	@RequestMapping(value = "/generateOTP", method = RequestMethod.GET)
	public String genearteOTP(Model model, HttpServletRequest request) {
		String globalErrorMessage = Constants.EMPTY_STRING;
		try {
			String loginId = request.getParameter("loginId");
			LoginFormBean loginFormBean = new LoginFormBean();
			loginFormBean.setLoginId(loginId);

			// OTP is generated
			String otp = loginService.generateOTP(loginFormBean);
			loginFormBean.setOtp(loginFormBean.getOtp());
			model.addAttribute("loginForm", loginFormBean);
		} catch (UPMException ex) {

			/*
			 * If loginId already exists, then throw the exception
			 */
			globalErrorMessage = ex.getMessage();
			if (globalErrorMessage.equals(Constants.LOGIN_ID_DOES_NOT_EXIST))
				model.addAttribute("errorMessage",
						"LogindId does not exist. Pls give valid LoginId");
		}
		return "otp";
	}

	/*
	 * This method redricts to login page
	 */

	@RequestMapping(value = "/logOut", method = RequestMethod.POST)
	public String logOut(Model model) {
		model.addAttribute("loginForm", new LoginFormBean());
		return "login";
	}

	/*
	 * Copy the data from UserEntity to LoginFormBean
	 */

	private void copyUserToLoginFormBean(User user, LoginFormBean loginFormBean) {

		loginFormBean.setEmail(user.getEmail());
		loginFormBean.setLoginId(user.getLoginId());
		loginFormBean.setPassword(user.getPassword());
		loginFormBean.setUserName(user.getUserName());
		loginFormBean.setStates(loginService.getStates());

		if (null != user.getAddresses() && user.getAddresses().size() > 0) {
			Address address = user.getAddresses().get(0);
			loginFormBean.setCity(address.getCity());
			loginFormBean.setCountry(address.getCountry());
			loginFormBean.setHouseNumber(address.getHouseNumber());
			loginFormBean.setStreet(address.getStreet());
			loginFormBean.setState(address.getStateId());
		}
	}

}
