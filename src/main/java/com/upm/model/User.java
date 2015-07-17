package com.upm.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name="USER")
public class User {

	@Id
	@Column(name = "LOGIN_ID", nullable = false)
	private String loginId;
	
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="EMAIL_ID")
	private String email;
	
	@Column(name="PASSWORD")
	private String password;
	
/*	@Column(name="IMAGE ")
	private byte[] image;*/
	
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
	private List<Address> addresses;
	
	@OneToOne(mappedBy = "user",orphanRemoval=true)  
	private UserOTP userOTP;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;

		for (Address address : addresses) {
			address.setUser(this);

		}
	}

	public UserOTP getUserOTP() {
		return userOTP;
	}

	public void setUserOTP(UserOTP userOTP) {
		this.userOTP = userOTP;
	}

/*	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	*/
	
	
}
