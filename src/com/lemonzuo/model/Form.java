package com.lemonzuo.model;

public class Form {
	
	public String userName;
	public String userPass;
	public String name;
	public int sex;
	public String userAge;
	public String userMobile;
	public String userAdress;
	public String userEmail;
	
	public Form(String userName, String userPass, String name, int sex, String userAge, 
			String userMobile, String userAdress, String userEmail) {
		super();
		this.userName = userName;
		this.userPass = userPass;
		this.name = name;
		this.sex = sex;
		this.userAge = userAge;
		this.userMobile = userMobile;
		this.userAdress = userAdress;
		this.userEmail = userEmail;
	}
	
}
