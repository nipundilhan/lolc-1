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

/**
 * Due Date Details History
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-09-2021    FXL-139  	FXL-926		Piyumi      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "due_date_details_history")
public class DueDateDetailsHistory extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;

	@Column(name = "due_date_template_id", nullable = false)
	private Long dueDateTemplateId;

	@Column(name = "due_date_detail_id", nullable = false)
	private Long dueDateDetailId;

	@Column(name = "sequence")
	private Long sequence;

	@Column(name = "due_date", nullable=false)
	private Timestamp dueDate;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private CommonStatus status;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;
	
	@Column(name="history_created_user", length=255, nullable=false)
	private String historyCreatedUser;
	
	@Column(name="history_created_date", nullable=false)
	private Timestamp historyCreatedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public Long getDueDateTemplateId() {
		return dueDateTemplateId;
	}

	public void setDueDateTemplateId(Long dueDateTemplateId) {
		this.dueDateTemplateId = dueDateTemplateId;
	}

	public Long getDueDateDetailId() {
		return dueDateDetailId;
	}

	public void setDueDateDetailId(Long dueDateDetailId) {
		this.dueDateDetailId = dueDateDetailId;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public Timestamp getDueDate() {
		return dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	
}	
