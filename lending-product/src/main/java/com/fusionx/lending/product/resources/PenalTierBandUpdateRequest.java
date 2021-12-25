package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;


/**
 * Penal Interest Service - Resource
 * @author 	Dilhan
 * @Date    02-06-2020
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-06-2020   FX-1517       FX-3902    Dilhan       Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PenalTierBandUpdateRequest extends PenalTierBandAddRequest {

	@JsonProperty("version")
	@NotBlank(message = "{version.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{version.pattern}")
	private String version;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String penalInterestTempPendingId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String penalTierBandSetPendingId;
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPenalInterestTempPendingId() {
		return penalInterestTempPendingId;
	}

	public void setPenalInterestTempPendingId(String penalInterestTempPendingId) {
		this.penalInterestTempPendingId = penalInterestTempPendingId;
	}

	public String getPenalTierBandSetPendingId() {
		return penalTierBandSetPendingId;
	}

	public void setPenalTierBandSetPendingId(String penalTierBandSetPendingId) {
		this.penalTierBandSetPendingId = penalTierBandSetPendingId;
	}


}
