package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class NetWorthAssetResponseResource {
	
	private String typeOfAssetId;
	private String typeOfAssetName;
	private String description;
	private String ownershipId;
	private String ownershipName;
	private String value;
	private String comment;
	private String status;
	
	public String getTypeOfAssetId() {
		return typeOfAssetId;
	}
	public void setTypeOfAssetId(String typeOfAssetId) {
		this.typeOfAssetId = typeOfAssetId;
	}
	public String getTypeOfAssetName() {
		return typeOfAssetName;
	}
	public void setTypeOfAssetName(String typeOfAssetName) {
		this.typeOfAssetName = typeOfAssetName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOwnershipId() {
		return ownershipId;
	}
	public void setOwnershipId(String ownershipId) {
		this.ownershipId = ownershipId;
	}
	public String getOwnershipName() {
		return ownershipName;
	}
	public void setOwnershipName(String ownershipName) {
		this.ownershipName = ownershipName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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

}
