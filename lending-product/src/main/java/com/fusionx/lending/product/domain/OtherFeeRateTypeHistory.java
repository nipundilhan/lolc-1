package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * Other Fee Rate Type History
 *  
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6605   	 FX-6510	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Data
@Table(name = "other_fee_rate_type_history")
public class OtherFeeRateTypeHistory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 0000000000001;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "other_fee_rate_type_id")
	private Long otherFeeRateTypeId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private CommonStatus status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Column(name = "other_fee_rate_type_version")
	private Long otherFeeRateTypeVersion;

	@Column(name="history_created_user")
	private String historyCreatedUser;
	
	@Column(name="history_created_date")
	private Timestamp historyCreatedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getOtherFeeRateTypeId() {
		return otherFeeRateTypeId;
	}

	public void setOtherFeeRateTypeId(Long otherFeeRateTypeId) {
		this.otherFeeRateTypeId = otherFeeRateTypeId;
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

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getOtherFeeRateTypeVersion() {
		return otherFeeRateTypeVersion;
	}

	public void setOtherFeeRateTypeVersion(Long otherFeeRateTypeVersion) {
		this.otherFeeRateTypeVersion = otherFeeRateTypeVersion;
	}

	public String getHistoryCreatedUser() {
		return historyCreatedUser;
	}

	public void setHistoryCreatedUser(String historyCreatedUser) {
		this.historyCreatedUser = historyCreatedUser;
	}

	public Timestamp getHistoryCreatedDate() {
		return historyCreatedDate;
	}

	public void setHistoryCreatedDate(Timestamp historyCreatedDate) {
		this.historyCreatedDate = historyCreatedDate;
	}

}
