package com.upm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="USER_OTP")
public class UserOTP {

	@Id
	@SequenceGenerator(name = "UPMSEQ", sequenceName = "UPMSEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UPMSEQ")
	@Column(name = "ID", nullable = false)
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	/*@Column(name="LOGIN_ID")
	private String loginId;*/
	
	@Column(name="CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name="OTP")
	private String otp;
	
	 @OneToOne  
	 @JoinColumn(name="LOGIN_ID")
	private User user;
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
/*	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}*/
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
