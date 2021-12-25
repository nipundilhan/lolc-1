package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Statement Template Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-AUG-2021   FXL-473	      FXL-426    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StatementTemplateAddResource {
	
	@NotNull(message = "{common.not-null}")
    private Long statementTypeId;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 70, message = "{common-name.size}")
    private String statementTypeName;
	
	@NotBlank(message = "{common.not-null}")
    @Size(max = 4, min = 4, message = "{common-code.size}")
    @Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
    private String code;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 70, message = "{common-name.size}")
    private String name;

    @Size(max = 255, message = "{description.size}")
    private String description;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
    private String status;
    
    @Valid
    private List<StatementTemplateDetailAddResource> statementTemplateDetails;

	public Long getStatementTypeId() {
		return statementTypeId;
	}

	public void setStatementTypeId(Long statementTypeId) {
		this.statementTypeId = statementTypeId;
	}

	public String getStatementTypeName() {
		return statementTypeName;
	}

	public void setStatementTypeName(String statementTypeName) {
		this.statementTypeName = statementTypeName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<StatementTemplateDetailAddResource> getStatementTemplateDetails() {
		return statementTemplateDetails;
	}

	public void setStatementTemplateDetails(List<StatementTemplateDetailAddResource> statementTemplateDetails) {
		this.statementTemplateDetails = statementTemplateDetails;
	}
    
    

}
