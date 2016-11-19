package entity.impl;

import entity.Adress;

public class SimpleAdressImpl implements Adress {

	private String adress;
	private String contacts;

	@Override
	public String getAdress() { 
		return adress;
	}

	@Override
	public String getContacts() { 
		return contacts;
	}

	@Override
	public String toString() {
		return "SimpleAdressImpl [adress=" + adress + ", contacts=" + contacts + "]";
	}

	public SimpleAdressImpl(String adress, String contacts) {
		super();
		this.adress = adress;
		this.contacts = contacts;
	}

}
