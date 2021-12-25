package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;

@Entity
@Table(name = "blacklist_detail")
@Transactional
@Data
public class BlacklistDetail extends BaseEntity implements Serializable{
			
			private static final long serialVersionUID = 4537474747L;

			@Column(name="tenant_id")
			private String tenantId;
			
			@Column(name="reason")
			private String reason;
			
			@Column(name="reason_id")
			private Long reasonId;
			
			@Column(name="blacklist_comment")
			private String comment;
			
			@Column(name="special_approval")
			private String specialApproval;
			
			@JsonIgnore
			@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
			@JoinColumn(name = "customer_id", nullable=false)
			private Customer customer;
			
			@JsonIgnore
			@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
			@JoinColumn(name = "guarantor_id", nullable=false)
			private Guarantor guarantor;
			
			@JsonIgnore
			@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
			@JoinColumn(name = "linked_person_id", nullable=false)
			private LinkedPerson linkedPerson;
			
			@Column(name="status")
			private String status; 
			
			@Column(name="created_user")
			private String createdUser;
			
			@Column(name="created_date")
			private Date createdDate;
			
			@Column(name = "modified_user")
			private String modifiedUser;
			
			@Column(name = "modified_date")
			private Timestamp modifiedDate;

			public String getTenantId() {
				return tenantId;
			}

			public void setTenantId(String tenantId) {
				this.tenantId = tenantId;
			}

			public String getReason() {
				return reason;
			}

			public void setReason(String reason) {
				this.reason = reason;
			}

			public String getComment() {
				return comment;
			}

			public void setComment(String comment) {
				this.comment = comment;
			}

			public String getSpecialApproval() {
				return specialApproval;
			}

			public void setSpecialApproval(String specialApproval) {
				this.specialApproval = specialApproval;
			}

			public Customer getCustomer() {
				return customer;
			}

			public void setCustomer(Customer customer) {
				this.customer = customer;
			}

			public Guarantor getGuarantor() {
				return guarantor;
			}

			public void setGuarantor(Guarantor guarantor) {
				this.guarantor = guarantor;
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

			public LinkedPerson getLinkedPerson() {
				return linkedPerson;
			}

			public void setLinkedPerson(LinkedPerson linkedPerson) {
				this.linkedPerson = linkedPerson;
			}

			public Long getReasonId() {
				return reasonId;
			}

			public void setReasonId(Long reasonId) {
				this.reasonId = reasonId;
			}


}
