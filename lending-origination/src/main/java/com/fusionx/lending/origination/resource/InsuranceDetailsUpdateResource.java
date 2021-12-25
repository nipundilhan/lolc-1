package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * Insurance Details Service
 * @author Sanatha
 * @Date 16-SEP-2020
 *
 *********************************************************************************************************
 *  ###        Date                  Story Point         Task No              Author           Description
 *-------------------------------------------------------------------------------------------------------
 *    1        16-SEP-2020   		 FX-4293             FX-4693              Sanatha         Initial Development.
 *    
 ********************************************************************************************************
 */
@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class InsuranceDetailsUpdateResource extends InsuranceDetailsAddResource{
	
	@NotBlank(message = "{version.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{version.pattern}")
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


}
