package com.fusionx.lending.origination.resource;

/**
 * Check List Template
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-75  		 FXL-551 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import java.util.List;

import javax.validation.Valid;
/**
 * Check List Template
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-75  		 FXL-551 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CheckListTemplateUpdateResource {

	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common.invalid-value}")
	private String applicableLevel;

	@Size(max = 70, message = "{common-name.size}")
	private String applicableLevelName;

	// @NotBlank(message = "{common.not-null}")
	@Size(max = 350, message = "{common-description.size}")
	private String comment;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String mainProductId;
	
	@Size(max = 70, message = "{common-name.size}")
	private String mainProductName;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String subProductId;
	 
	@Size(max = 70, message = "{common-name.size}")
	private String subProductName;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	@Valid
	private List<CheckListTemplateDetailsUpdateResource> checkListItem;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApplicableLevel() {
		return applicableLevel;
	}

	public void setApplicableLevel(String applicableLevel) {
		this.applicableLevel = applicableLevel;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CheckListTemplateDetailsUpdateResource> getCheckListItem() {
		return checkListItem;
	}

	public void setCheckListItem(List<CheckListTemplateDetailsUpdateResource> checkListItem) {
		this.checkListItem = checkListItem;
	}

	public String getMainProductId() {
		return mainProductId;
	}

	public void setMainProductId(String mainProductId) {
		this.mainProductId = mainProductId;
	}

	public String getSubProductId() {
		return subProductId;
	}

	public void setSubProductId(String subProductId) {
		this.subProductId = subProductId;
	}

	public String getApplicableLevelName() {
		return applicableLevelName;
	}

	public void setApplicableLevelName(String applicableLevelName) {
		this.applicableLevelName = applicableLevelName;
	}

	public String getMainProductName() {
		return mainProductName;
	}

	public void setMainProductName(String mainProductName) {
		this.mainProductName = mainProductName;
	}

	public String getSubProductName() {
		return subProductName;
	}

	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}

}
