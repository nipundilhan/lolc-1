package com.fusionx.lending.product.resources;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.product.domain.MasterDefCountry;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MasterDefLocationDetailsReponse {
	
	private List<MasterDefCountry> country;

	public List<MasterDefCountry> getCountry() {
		return country;
	}

	public void setCountry(List<MasterDefCountry> country) {
		this.country = country;
	}


	
	

}
