package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class BlacklistAddResource {

	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	private String reasonId;
	private String reason;
	
	private String comment;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{specialApproval.pattern}")
	private String specialApproval;

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

	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
	
	
	
}
