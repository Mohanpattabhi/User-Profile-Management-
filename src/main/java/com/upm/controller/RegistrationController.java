package com.upm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.upm.model.LoginFormBean;
import com.upm.service.LoginService;
import com.upm.util.Constants;
import com.upm.validator.UPMRegistrationValidator;

/*
 * This class acts as a controller for all the login and Registration 
 * related operations
 * 
 */
@Controller
public class RegistrationController {

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
	 * This method handles the registration page
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(Model model) {

		LoginFormBean loginFormBean = new LoginFormBean();
		/*
		 * Values for state population get from DB
		 */
		loginFormBean.setStates(loginService.getStates());
		model.addAttribute("loginForm", loginFormBean);
		model.addAttribute("states", loginService.getStates());
		return "register";
	}

	/*
	 * This method saves tje registration details to DB after checks and
	 * validations
	 */
	@RequestMapping(value = "/registerSubmit", method = RequestMethod.POST)
	public String registerSubmitAction(
			@ModelAttribute("loginForm") LoginFormBean loginFormBean,
			BindingResult bindingResult) {

		// check if the loginId already exists
		Boolean loginIdPresent = loginService
				.checkForExistingLogin(loginFormBean.getLoginId());
		if (loginIdPresent) {
			bindingResult.rejectValue("loginId", "loginId.duplicate",
					"LoginId is already present. Pls enter a different one");
		}

		// Validate input details
		UPMRegistrationValidator upmRegistrationValidator = new UPMRegistrationValidator(
				Constants.REGISTER_PAGE);
		upmRegistrationValidator.validate(loginFormBean, bindingResult);

		if (bindingResult.hasErrors()) {
			loginFormBean.setStates(loginService.getStates());
			return "register";
		}

		// save the details to DB
		loginService.registerUser(loginFormBean);
		loginFormBean.clear();
		return "login";
	}

	/*
	 * This method stores the details that the use has updated to the DB after
	 * making necessary validations
	 */

	@RequestMapping(value = "/updateSubmit", method = RequestMethod.POST)
	public String updateSubmitAction(
			@ModelAttribute("loginForm") LoginFormBean loginFormBean,
			Model model, BindingResult bindingResult) {

		// validate the details
		UPMRegistrationValidator upmRegistrationValidator = new UPMRegistrationValidator(
				Constants.UPDATE_PAGE);
		upmRegistrationValidator.validate(loginFormBean, bindingResult);

		if (bindingResult.hasErrors()) {
			loginFormBean.setStates(loginService.getStates());
			return "update";
		}
		loginService.updateUserDetails(loginFormBean);
		loginFormBean.setStates(loginService.getStates());
		model.addAttribute("loginForm", loginFormBean);
		model.addAttribute("updatedMessage", Constants.LOGIN_UPDATION_SUCCESS);
		return "update";
	}

	/*
	 * This method displays the update page
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateDetails(
			@ModelAttribute("loginForm") LoginFormBean loginFormBean) {
		loginService.updateUserDetails(loginFormBean);
		return "update";
	}

}
