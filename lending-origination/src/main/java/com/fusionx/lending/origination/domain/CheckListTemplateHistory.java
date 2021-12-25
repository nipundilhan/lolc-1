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
@Table(name = "check_list_template_history")
public class CheckListTemplateHistory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1419675825820343420L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@Column(name = "check_list_template_id", nullable = false)
	private Long checkListTemplateId;

	@Column(name = "code", length = 4, nullable = false)
	private String code;

	@Column(name = "applicable_level", length = 70, nullable = false)
	private CommonList applicableLevel;

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
	private List<CheckListTemplateDetailsHistory> checkListTemplateDetails;

	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", nullable = true, length = 255)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;

	@Column(name = "history_created_user", length = 255, nullable = false)
	private String historyCreatedUser;

	@Column(name = "history_created_date", nullable = false)
	private Timestamp historyCreatedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getCheckListTemplateId() {
		return checkListTemplateId;
	}

	public void setCheckListTemplateId(Long checkListTemplateId) {
		this.checkListTemplateId = checkListTemplateId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public CommonList getApplicableLevel() {
		return applicableLevel;
	}

	public void setApplicableLevel(CommonList applicableLevel) {
		this.applicableLevel = applicableLevel;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getSubProduct() {
		return subProduct;
	}

	public void setSubProduct(String subProduct) {
		this.subProduct = subProduct;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public CommonApproveStatus getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(CommonApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
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

	public List<CheckListTemplateDetailsHistory> getCheckListTemplateDetails() {
		return checkListTemplateDetails;
	}

	public void setCheckListTemplateDetails(List<CheckListTemplateDetailsHistory> checkListTemplateDetails) {
		this.checkListTemplateDetails = checkListTemplateDetails;
	}

}
