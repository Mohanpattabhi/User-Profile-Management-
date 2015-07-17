package com.upm.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.upm.Exceptions.UPMException;
import com.upm.dao.LoginDAO;
import com.upm.model.Address;
import com.upm.model.LoginFormBean;
import com.upm.model.States;
import com.upm.model.User;
import com.upm.model.UserImages;
import com.upm.model.UserLoginDetailsVO;
import com.upm.model.UserOTP;
import com.upm.util.Constants;
import com.upm.util.OTPGenerator;


/*
 * This class provides the business operations for all the
 * Login and Registration related operations
 * 
 */
@Service
public class LoginServiceImpl implements LoginService {

	private LoginDAO loginDAO;

	
	// Opt expire time defined in TimeConfig.xml
	@Value("${otpDiffTime}")
	private String otpDiffTime;

	
	/*
	 * DAO initalization
	 */
	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}
	
	/*
	 * This method stores the user details in DB
	 */

	public void registerUser(LoginFormBean loginFormBean) {
		loginDAO.addOrUpdateUser(prepareUserEntity(loginFormBean));
	}
	
	/*
	 * This method checks for the existing login
	 */
	public Boolean checkForExistingLogin(String loginId) {
		Boolean loginPresent = false;
		User user = loginDAO.getUser(loginId);
		if (user != null) {
			loginPresent = true;
		}
		return loginPresent;

	}
	
	/*
	 * This method generates the OTP
	 */

	public String generateOTP(LoginFormBean loginFormBean) throws UPMException {

		User user = loginDAO.getUser(loginFormBean.getLoginId());
		if (null == user) {
			throw new UPMException(Constants.LOGIN_ID_DOES_NOT_EXIST);
		}
		
		// OTP is generated. 8-digit string with 1 special char and digit
		String otp = OTPGenerator.generateOTP();
		loginFormBean.setOtp(otp);
		loginDAO.addUserOTP(prepareUserOTPEntity(loginFormBean, user));
		return otp;

	}
	
	
	/*
	 * This method generates validates the login. This has following status
	 * 1. LoginId dos not exist
	 * 2. login password is not matching for the user
	 * 3. OTP does not match with the version in DB
	 * 4. OTP has elapsed the configured time
	 * 5. OTP doesn't exist in the DB
	 * 6. login is succesful
	 * 
	 * In the login is successful, the otp entityis removed from the DB
	 */

	public UserLoginDetailsVO validateLogin(LoginFormBean loginFormBean) {
		int status = Constants.LOGIN_SUCCESS;
		boolean loginIdNotPresent = false;
		boolean passwordNotMatching = false;
		UserLoginDetailsVO userLoginDetailsVO = new UserLoginDetailsVO();
		UserOTP userOTP = null;
		User userEntity = loginDAO.getUser(loginFormBean.getLoginId());
		
		//login does not exist
		if (null == userEntity) {
			status = Constants.LOGIN_ID_DOESNOT_EXIST;
			loginIdNotPresent = true;
		}

		if (!loginIdNotPresent) {
			userOTP = userEntity.getUserOTP();

			if (!StringUtils.isNullOrEmpty(userEntity.getPassword())
					&& !(loginFormBean.getPassword().equals(userEntity
							.getPassword()))) {
				
				// login passwords dosn't match
				status = Constants.LOGIN_PASSWORD_NOT_MATCHING;
				passwordNotMatching = true;
			}
			if (!passwordNotMatching) {
				if (null != userOTP) {
					if (!loginFormBean.getOtp().equals(userOTP.getOtp())) {
						
						//login otp dosnt match
						status = Constants.LOGIN_OTP_NOT_MATCHING;
					} else {
						java.sql.Timestamp createdDateTime = userOTP
								.getCreatedDate();
						java.sql.Timestamp currentTime = new java.sql.Timestamp(
								System.currentTimeMillis());

						long diff = currentTime.getTime()
								- createdDateTime.getTime();
						long diffMinutes = diff / (60 * 1000);
						
						//long diffDays = diff / (24 * 60 * 60 * 1000);
						
						//created time of the OTP is pas the configure diff time
						if (diffMinutes > Long.parseLong(otpDiffTime)) {
							status = Constants.LOGIN_OTP_EXPIRED;
						}
					}
				} else {
					status = Constants.LOGIN_OTP_DOESNOT_EXIST;
				}
			}
			userLoginDetailsVO.setStatus(status);
			userLoginDetailsVO.setUser(userEntity);
		}

		/*
		 * Login is succesful and opt is removed from the DB
		 */
		if (status == Constants.LOGIN_SUCCESS && null != userOTP) {
			loginDAO.removeUserOTP(userOTP);
		}

		return userLoginDetailsVO;

	}

	/*
	 * Stores the image data in DB
	 */
	public void storeImage(byte[] imageData, String name) {
		loginDAO.storeImage(imageData, name);
	}

	/*
     * Updates the user registration details
	 */
	public void updateUserDetails(LoginFormBean loginFormBean) {
		User userEntity = loginDAO.getUser(loginFormBean.getLoginId());
		loginDAO.addOrUpdateUser(prepareUserEntityforUpdate(userEntity,
				loginFormBean));
	}

	/* 
	 * Fetch the  states from DB
	 */
	public List<States> getStates() {
		return loginDAO.getStates();
	}

	
	/* 
	 * Fetch the Image entity from DB
	 */
	public UserImages getUserImage(String name) {
		return loginDAO.getImage(name);
	}
	
	
	/*
	 * Construct user entity from loginFormBean
	 */
	private User prepareUserEntity(LoginFormBean loginFormBean) {

		User user = new User();
		user.setEmail(loginFormBean.getEmail());
		user.setLoginId(loginFormBean.getLoginId());
		user.setPassword(loginFormBean.getPassword());
		user.setUserName(loginFormBean.getUserName());

		Address address = new Address();
		address.setCity(loginFormBean.getCity());
		address.setCountry(loginFormBean.getCountry());
		address.setHouseNumber(loginFormBean.getHouseNumber());
		address.setStreet(loginFormBean.getStreet());
		address.setStateId(loginFormBean.getState());
		address.setUser(user);
		user.setAddresses(Arrays.asList(address));
		return user;

	}

	/*
	 * Construct UserEntity object from loginFormBean
	 */
	private UserOTP prepareUserOTPEntity(LoginFormBean loginFormBean, User user) {

		UserOTP userOTP = new UserOTP();
		userOTP.setCreatedDate(new java.sql.Timestamp(Calendar.getInstance()
				.getTime().getTime()));
		userOTP.setOtp(loginFormBean.getOtp());

		userOTP.setUser(user);
		return userOTP;

	}
	
	/*
	 * Construct UserEntity object from loginFormBean for updation
	 */

	private User prepareUserEntityforUpdate(User user,
			LoginFormBean loginFormBean) {

		user.setEmail(loginFormBean.getEmail());
		user.setLoginId(loginFormBean.getLoginId());
		user.setPassword(loginFormBean.getPassword());
		user.setUserName(loginFormBean.getUserName());

		if (null != user.getAddresses() && user.getAddresses().size() > 0) {
			Address address = user.getAddresses().get(0);
			address.setCity(loginFormBean.getCity());
			address.setCountry(loginFormBean.getCountry());
			address.setHouseNumber(loginFormBean.getHouseNumber());
			address.setStreet(loginFormBean.getStreet());
			address.setStateId(loginFormBean.getState());
			address.setUser(user);
			address.addUser(user);
			user.setAddresses(Arrays.asList(address));
		}
		return user;

	}

}
