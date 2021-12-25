package com.fusionx.lending.origination.resource;

import java.util.List;


public class ComnSupplierResponceResource {
	
	private String perFullName;
	
	private List<PersonResponseIdentificationResource> perIdentifications;
	
	private List<PersonResponseAddressResource> perAddresses;
	
	private List<PersonResponseContactResource> perContacts;

	public String getPerFullName() {
		return perFullName;
	}

	public void setPerFullName(String perFullName) {
		this.perFullName = perFullName;
	}

	public List<PersonResponseIdentificationResource> getPerIdentifications() {
		return perIdentifications;
	}

	public void setPerIdentifications(List<PersonResponseIdentificationResource> perIdentifications) {
		this.perIdentifications = perIdentifications;
	}

	public List<PersonResponseAddressResource> getPerAddresses() {
		return perAddresses;
	}

	public void setPerAddresses(List<PersonResponseAddressResource> perAddresses) {
		this.perAddresses = perAddresses;
	}

	public List<PersonResponseContactResource> getPerContacts() {
		return perContacts;
	}

	public void setPerContacts(List<PersonResponseContactResource> perContacts) {
		this.perContacts = perContacts;
	}

}
