package entity.impl;

import entity.ShowedUser;

public class SimpleShowedUser implements ShowedUser {

	private String firstName;
	private String lastName;
	private String eMail;
	private String login;
	private int userId;
	
	@Override
	public String getFirstName() { 
		return firstName;
	}

	@Override
	public String getLastName() { 
		return lastName;
	}

	@Override
	public String getEMail() { 
		return eMail;
	}
	
	

	public SimpleShowedUser(String login, String firstName, String lastName, String eMail,int userId) {
		super();
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		this.eMail = eMail;
		this.userId = userId;
	}

	@Override
	public String getLogin() { 
		return login;
	}

	@Override
	public int getUserId() { 
		return userId;
	}

}
