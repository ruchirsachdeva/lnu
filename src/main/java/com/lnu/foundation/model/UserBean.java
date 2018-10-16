
package com.lnu.foundation.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UserBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String title;
	private String country;
	private String password;
	private String passwordConfirm;	
	private String provider;
	private String image;

}
