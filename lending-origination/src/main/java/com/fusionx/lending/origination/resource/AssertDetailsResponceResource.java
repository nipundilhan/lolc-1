package com.fusionx.lending.origination.resource;

import java.sql.Date;

public class AssertDetailsResponceResource {
	
	private Long id;
	private String vEngineNo;
	private String vChassisNo;
	private String vRegistraionNo;
	private Date vRegDate;
	private Date vBookReceivedDate;
	private Long assetEntityId;
	private Long makeTypeId;
	private Long modelTypeId;
	private String vYearOfManufacture;
	private String vRegBookNo;
	private Long conditionId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getvEngineNo() {
		return vEngineNo;
	}
	public void setvEngineNo(String vEngineNo) {
		this.vEngineNo = vEngineNo;
	}
	public String getvChassisNo() {
		return vChassisNo;
	}
	public void setvChassisNo(String vChassisNo) {
		this.vChassisNo = vChassisNo;
	}
	public String getvRegistraionNo() {
		return vRegistraionNo;
	}
	public void setvRegistraionNo(String vRegistraionNo) {
		this.vRegistraionNo = vRegistraionNo;
	}
	public Date getvRegDate() {
		return vRegDate;
	}
	public void setvRegDate(Date vRegDate) {
		this.vRegDate = vRegDate;
	}
	public Date getvBookReceivedDate() {
		return vBookReceivedDate;
	}
	public void setvBookReceivedDate(Date vBookReceivedDate) {
		this.vBookReceivedDate = vBookReceivedDate;
	}
	public Long getAssetEntityId() {
		return assetEntityId;
	}
	public void setAssetEntityId(Long assetEntityId) {
		this.assetEntityId = assetEntityId;
	}
	public Long getMakeTypeId() {
		return makeTypeId;
	}
	public void setMakeTypeId(Long makeTypeId) {
		this.makeTypeId = makeTypeId;
	}
	public Long getModelTypeId() {
		return modelTypeId;
	}
	public void setModelTypeId(Long modelTypeId) {
		this.modelTypeId = modelTypeId;
	}
	public String getvYearOfManufacture() {
		return vYearOfManufacture;
	}
	public void setvYearOfManufacture(String vYearOfManufacture) {
		this.vYearOfManufacture = vYearOfManufacture;
	}
	public String getvRegBookNo() {
		return vRegBookNo;
	}
	public void setvRegBookNo(String vRegBookNo) {
		this.vRegBookNo = vRegBookNo;
	}
	public Long getConditionId() {
		return conditionId;
	}
	public void setConditionId(Long conditionId) {
		this.conditionId = conditionId;
	}
}
