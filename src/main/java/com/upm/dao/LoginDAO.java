package com.upm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.upm.model.Address;
import com.upm.model.States;
import com.upm.model.User;
import com.upm.model.UserImages;
import com.upm.model.UserOTP;

/*
 * This class acts as the DB repository for all the 
 * Login, Registration related operations
 */
@Repository
public class LoginDAO {

	// initalize logger
	private static final Logger logger = LoggerFactory
			.getLogger(LoginDAO.class);

	// initialize session factory
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	
	/*
	 * This method adds the user entity to the DB
	 */
	@Transactional
	public void addOrUpdateUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
	}

	/*
	 * This method checks for duplicate loginId
	 */
	@Transactional
	public void checkForExistingLogin(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
		User userInDB = getUser(user.getLoginId());
	}

	/*
	 * This method fetches the user from DB
	 */
	@Transactional
	public User getUser(String userId) {
		Session session = this.sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, userId);

		return user;
	}

	
	/*
	 * This method returns the UserOTP given a userEntity
	 */
	@Transactional
	public UserOTP getUserOTP(String loginId) {
		Session session = this.sessionFactory.getCurrentSession();
		UserOTP userOTP = null;
		// List userOtpList = session.createQuery("from UserOTP").list();

		List userOtpList = (List) session.createCriteria(UserOTP.class)
				.add(Restrictions.eq("user.loginId", loginId)).list();

		if (null != userOtpList && userOtpList.size() > 0) {
			userOTP = (UserOTP) userOtpList.get(0);
		}
		return userOTP;
	}
	
	/*
	 * Add or update new OTP
	 * 
	 */

	@Transactional
	public void addUserOTP(UserOTP newUserOTP) {
		Session session = this.sessionFactory.getCurrentSession();
		UserOTP userOTP = getUserOTP(newUserOTP.getUser().getLoginId());
		if (userOTP != null) {
			userOTP.setOtp(newUserOTP.getOtp());
			userOTP.setCreatedDate(newUserOTP.getCreatedDate());
			userOTP.setUser(newUserOTP.getUser());
			session.saveOrUpdate(userOTP);
		} else {
			session.save(newUserOTP);
		}
	}

	/*
	 * 
	 * Remove User OTP
	 */
	@Transactional
	public void removeUserOTP(UserOTP userOTP) {
		Session session = this.sessionFactory.getCurrentSession();

		if (null != userOTP) {
			session.delete(userOTP);
		}
		logger.info("User OTP revmoved succesfully");
	}

	
	/*
	 * Store the Image
	 */
	@Transactional
	public void storeImage(byte[] imageData, String name) {
		Session session = this.sessionFactory.getCurrentSession();
		UserImages userImages = new UserImages();
		userImages.setLoginId(name);
		userImages.setImage(imageData);

		session.save(userImages);
		logger.info("User OTP revmoved succesfully");

	}

	/*
	 * Fetch the image details
	 */
	@Transactional
	public UserImages getImage(String name) {
		UserImages userImages = null;
		Session session = this.sessionFactory.getCurrentSession();
		Object object = session.createCriteria(UserImages.class)
				.add(Restrictions.eq("loginId", name)).uniqueResult();
		if (object != null) {
			userImages = (UserImages) object;

		}
		return userImages;

	}
	
	/*
	 * Get the state details
	 */

	@Transactional
	public List<States> getStates() {
		Session session = this.sessionFactory.getCurrentSession();
		List<States> statesList = session.createQuery("from States").list();
		return statesList;

	}

}
