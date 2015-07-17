package com.upm.model;

/*
 * VO for LoginDetails
 */
public class UserLoginDetailsVO {

	private Integer status;
	private User user;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
