package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Fee Charge  Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-07-2021      		     			Dilhan      Created
 *    
 ********************************************************************************************************
 */
public class FeeChargeCapUpdateResource extends FeeChargeCapAddResource{
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeChargePendingId;

	@NotBlank(message = "{version.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{version.pattern}")
	private String version;
	
	public String getFeeChargePendingId() {
		return feeChargePendingId;
	}

	public void setFeeChargePendingId(String feeChargePendingId) {
		this.feeChargePendingId = feeChargePendingId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
