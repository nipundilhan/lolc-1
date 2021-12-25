package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.Transactional;

import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;

@Entity
@Table(name = "serial_table")
@Transactional
@Data
public class SerialTable extends BaseEntity implements Serializable{

	@Column(name="tenant_id")
	private String tenantId;
	
	@Column(name="domain_name")
	private String domainName;
	
	@Column(name="serial_val")
	private long serialVal;
	
	@Column(name="status")
	private String status; 
	
	@Column(name="created_user")
	private String createdUser;
	
	@Column(name="created_date")
	private Date createdDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public long getSerialVal() {
		return serialVal;
	}

	public void setSerialVal(long serialVal) {
		this.serialVal = serialVal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	

}
