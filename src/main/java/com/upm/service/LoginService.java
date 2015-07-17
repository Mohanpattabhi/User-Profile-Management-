package com.upm.service;

import java.util.List;

import com.upm.Exceptions.UPMException;
import com.upm.model.LoginFormBean;
import com.upm.model.States;
import com.upm.model.UserImages;
import com.upm.model.UserLoginDetailsVO;

/*
 * Interface for login and registration related operations
 * 
 */

public interface LoginService {

	//validates the login
	public UserLoginDetailsVO validateLogin(LoginFormBean loginFormBean);

	//Registers the use with the system
	public void registerUser(LoginFormBean loginFormBean);

	//Generates the OTP
	public String generateOTP(LoginFormBean loginFormBean) throws UPMException;

	//Stores the photo Image
	public void storeImage(byte[] imageData, String name);

	//This method updates the user details
	public void updateUserDetails(LoginFormBean loginFormBean);

	//This method get the states stored in DB
	public List<States> getStates();

	//This method is used to download the image
	public UserImages getUserImage(String name);

	// This method is used to check the duplicate user
	public Boolean checkForExistingLogin(String loginId);
}
