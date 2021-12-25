package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Business Clearance Detail Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BusinessClearanceDetailResource {
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String clearanceTypeId;	
	
	@NotBlank(message = "{common.not-null}")
	private String clearanceTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 300, message = "{business-name.size}")
	private String memo;
	
	@Pattern(regexp = "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" , message = "{common.invalid-date-pattern}" )
	private String obtainDate;
	
	@Pattern(regexp = "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" , message = "{common.invalid-date-pattern}" )
	private String expireDate;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String authorityId;	
	
	@NotBlank(message = "{common.not-null}")
	private String authorityName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{common-status.pattern}")
	private String status;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


	public String getClearanceTypeId() {
		return clearanceTypeId;
	}

	public void setClearanceTypeId(String clearanceTypeId) {
		this.clearanceTypeId = clearanceTypeId;
	}

	public String getClearanceTypeName() {
		return clearanceTypeName;
	}

	public void setClearanceTypeName(String clearanceTypeName) {
		this.clearanceTypeName = clearanceTypeName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getObtainDate() {
		return obtainDate;
	}

	public void setObtainDate(String obtainDate) {
		this.obtainDate = obtainDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
