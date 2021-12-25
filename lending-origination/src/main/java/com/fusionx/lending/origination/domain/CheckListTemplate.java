package com.fusionx.lending.origination.domain;

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
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonApproveStatus;
import com.fusionx.lending.origination.enums.CommonStatus;

@Entity
@Table(name = "check_list_template")
public class CheckListTemplate extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1968537281164319989L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@Column(name = "code", length = 4, nullable = false)
	private String code;

//	@Column(name = "applicable_level", length = 70, nullable = false)
//	private CommonList applicableLevel;

	@Column(name = "main_product", length = 70, nullable = false)
	private String mainProduct;

	@Column(name = "sub_product", length = 70, nullable = false)
	private String subProduct;

	@Column(name = "name", length = 70, nullable = false)
	private String name;

	@Column(name = "app_rej_comment", length = 350, nullable = true)
	private String comment;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private CommonStatus status;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "approve_status", nullable = true, length = 30)
	private CommonApproveStatus approveStatus;

	@Transient
	private List<CheckListTemplateDetails> checkListTemplateDetails;

	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", nullable = true, length = 255)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

//	public CommonList getApplicableLevel() {
//		return applicableLevel;
//	}
//
//	public void setApplicableLevel(CommonList applicableLevel) {
//		this.applicableLevel = applicableLevel;
//	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public void setSubProduct(String subProduct) {
		this.subProduct = subProduct;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public CommonApproveStatus getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(CommonApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
	}

	public List<CheckListTemplateDetails> getCheckListTemplateDetails() {
		return checkListTemplateDetails;
	}

	public void setCheckListTemplateDetails(List<CheckListTemplateDetails> checkListTemplateDetails) {
		this.checkListTemplateDetails = checkListTemplateDetails;
	}

	public String getSubProduct() {
		return subProduct;
	}

}
