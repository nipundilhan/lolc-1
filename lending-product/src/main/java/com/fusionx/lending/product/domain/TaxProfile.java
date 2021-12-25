package com.fusionx.lending.product.domain;

/**
 * Tax Profile Service
 * @author 	KilasiG
 * @Date     05-11-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-11-2019   FX-1545       FX-2175    KilasiG      Created
 *    
 ********************************************************************************************************
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.ApplicableAccTypeComnListEnum;
import com.fusionx.lending.product.enums.ApplicableProductStatus;
import com.fusionx.lending.product.enums.BaredBy;
import com.fusionx.lending.product.enums.OtherInterestIncome;
import com.fusionx.lending.product.enums.TaxAmountType;
import com.fusionx.lending.product.enums.TaxApplicableLevel;
import com.fusionx.lending.product.enums.TaxProfileStatus;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity
@Table(name = "tax_profile")
//@Data
public class TaxProfile extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 434534L;

	@Column(name = "tenant_id", length = 20, nullable = false)
	private String taxProfileTenantId;

	@Column(name = "effective_date", nullable = false)
	private Date effectiveDate;

	@Column(name = "tax_code_id", nullable = true)
	private Long taxCodeId;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "tax_applicable_level", length = 255, nullable = false)
	private TaxApplicableLevel taxApplicableLevel;

	@Column(name = "prod_cate_comn_li_id", nullable = false)
	private Long productCategoryComnListId;

	@Column(name = "prod_category_desc", length = 255, nullable = false)
	private String productCategoryDesc;

	@Column(name = "sub_product_id", length = 255, nullable = false)
	private Long subProductId;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "app_acc_type_comn_li_id", nullable = false)
	private ApplicableAccTypeComnListEnum applicableAccTypeComnListId;

	@Column(name = "applicable_acc_type_desc", length = 255, nullable = false)
	private String applicableAccTypeDesc;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "applicable_prod_status", length = 255, nullable = false)
	private ApplicableProductStatus applicableProductStatus;

	@Column(name = "applicable_prod_id", nullable = true)
	private Long applicableProductId;

	@Column(name = "applicable_prod_name", length = 255, nullable = true)
	private String applicableProductName;

//	@Enumerated(value = EnumType.STRING)
	@Column(name = "decl_type_comn_li_id", nullable = true)
	private String declarationTypeComnListId;

//	@Column(name = "declaration_type_desc", length=255, nullable=false)
//	private String declarationTypeDesc;

	@Column(name = "customer_category_id")
	private String customerCategoryId;

	@Column(name = "customer_category", length = 255, nullable = true)
	private String customerCategory;

	@Column(name = "customer_category_code")
	private String customerCategoryCode;

	@Column(name = "cust_sub_type_ind_id", nullable = true)
	private Long customerSubTypeIndividualId;

	@Column(name = "cust_sub_type_ind_desc", length = 255, nullable = true)
	private String customerSubTypeIndividualDesc;

	@Column(name = "cust_sub_type_non_ind_id", nullable = true)
	private Long customerSubTypeNonIndividualId;

	@Column(name = "cust_sub_type_non_ind_desc", length = 255, nullable = true)
	private String customerSubTypeNonIndividualDesc;

	@Column(name = "customer_resident_type_id", length = 255, nullable = true)
	private Long customerResidentTypeId;

	@Column(name = "customer_resident_type_desc", length = 255, nullable = true)
	private String customerResidentTypeDesc;

	@Column(name = "applicable_min_age", nullable = true)
	private Long taxApplicableMinAge;

	@Column(name = "applicable_max_age", nullable = true)
	private Long taxApplicableMaxAge;

	// @Enumerated(value = EnumType.STRING)
	@Column(name = "age_effective_date_type", length = 255, nullable = true)
	private String ageEffectiveDateType;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "tax_amount_type", length = 255, nullable = false)
	private TaxAmountType taxAmountType;

//	@Column(name = "tax_amount", nullable = false)
//	private BigDecimal taxAmount;
//
//	@Column(name = "tax_rate", nullable = false)
//	private BigDecimal taxRate;

	@Column(name = "tax_applicable_min_val", nullable = true)
	private BigDecimal taxApplicableMinValue;

	@Column(name = "tax_applicable_max_val", nullable = true)
	private BigDecimal taxApplicableMaxValue;

	@Column(name = "cf_min_value", nullable = true)
	private BigDecimal ceilingFloorfMinValue;

	@Column(name = "cf_max_value", nullable = true)
	private BigDecimal ceilingFloorfMaxValue;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "other_interest_income", length = 255, nullable = true)
	private OtherInterestIncome otherInterestIncome;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "tax_profile_status", length = 20, nullable = false)
	private TaxProfileStatus taxProfileStatus;

	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", length = 255, nullable = true)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "deduct_indicator")
	private OtherInterestIncome deductIndicator;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "bared_by")
	private BaredBy baredBy;

	@Transient
	private List<TaxProfileDetails> taxProfileDetails;
	
	@Transient
	private TaxProfileDetails taxProfDet;
	
	

//	@Transient
//	@JsonProperty("taxCodeId")
//	private Long taxCode_id;

	public TaxProfileDetails getTaxProfDet() {
		return taxProfDet;
	}

	public void setTaxProfDet(TaxProfileDetails taxProfDet) {
		this.taxProfDet = taxProfDet;
	}

	public String getTaxProfileTenantId() {
		return taxProfileTenantId;
	}

	public void setTaxProfileTenantId(String taxProfileTenantId) {
		this.taxProfileTenantId = taxProfileTenantId;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Long getTaxCode() {
		return taxCodeId;
	}

	public void setTaxCode(Long taxCodeId) {
		this.taxCodeId = taxCodeId;
	}

	public TaxApplicableLevel getTaxApplicableLevel() {
		return taxApplicableLevel;
	}

	public void setTaxApplicableLevel(TaxApplicableLevel taxApplicableLevel) {
		this.taxApplicableLevel = taxApplicableLevel;
	}

	public Long getProductCategoryComnListId() {
		return productCategoryComnListId;
	}

	public void setProductCategoryComnListId(Long productCategoryComnListId) {
		this.productCategoryComnListId = productCategoryComnListId;
	}

	public ApplicableAccTypeComnListEnum getApplicableAccTypeComnListId() {
		return applicableAccTypeComnListId;
	}

	public void setApplicableAccTypeComnListId(ApplicableAccTypeComnListEnum applicableAccTypeComnListId) {
		this.applicableAccTypeComnListId = applicableAccTypeComnListId;
	}

	public String getApplicableAccTypeDesc() {
		return applicableAccTypeDesc;
	}

	public void setApplicableAccTypeDesc(String applicableAccTypeDesc) {
		this.applicableAccTypeDesc = applicableAccTypeDesc;
	}

	public ApplicableProductStatus getApplicableProductStatus() {
		return applicableProductStatus;
	}

	public void setApplicableProductStatus(ApplicableProductStatus applicableProductStatus) {
		this.applicableProductStatus = applicableProductStatus;
	}

	public Long getApplicableProductId() {
		return applicableProductId;
	}

	public void setApplicableProductId(Long applicableProductId) {
		this.applicableProductId = applicableProductId;
	}

	public String getApplicableProductName() {
		return applicableProductName;
	}

	public void setApplicableProductName(String applicableProductName) {
		this.applicableProductName = applicableProductName;
	}

	public String getDeclarationTypeComnListId() {
		return declarationTypeComnListId;
	}

	public void setDeclarationTypeComnListId(String declarationTypeComnListId) {
		this.declarationTypeComnListId = declarationTypeComnListId;
	}

//	public String getDeclarationTypeDesc() {
//		return declarationTypeDesc;
//	}
//
//	public void setDeclarationTypeDesc(String declarationTypeDesc) {
//		this.declarationTypeDesc = declarationTypeDesc;
//	}

	public String getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	public Long getCustomerSubTypeIndividualId() {
		return customerSubTypeIndividualId;
	}

	public void setCustomerSubTypeIndividualId(Long customerSubTypeIndividualId) {
		this.customerSubTypeIndividualId = customerSubTypeIndividualId;
	}

	public String getCustomerSubTypeIndividualDesc() {
		return customerSubTypeIndividualDesc;
	}

	public void setCustomerSubTypeIndividualDesc(String customerSubTypeIndividualDesc) {
		this.customerSubTypeIndividualDesc = customerSubTypeIndividualDesc;
	}

	public Long getCustomerSubTypeNonIndividualId() {
		return customerSubTypeNonIndividualId;
	}

	public void setCustomerSubTypeNonIndividualId(Long customerSubTypeNonIndividualId) {
		this.customerSubTypeNonIndividualId = customerSubTypeNonIndividualId;
	}

	public String getCustomerSubTypeNonIndividualDesc() {
		return customerSubTypeNonIndividualDesc;
	}

	public void setCustomerSubTypeNonIndividualDesc(String customerSubTypeNonIndividualDesc) {
		this.customerSubTypeNonIndividualDesc = customerSubTypeNonIndividualDesc;
	}

	public Long getCustomerResidentTypeId() {
		return customerResidentTypeId;
	}

	public void setCustomerResidentTypeId(Long customerResidentTypeId) {
		this.customerResidentTypeId = customerResidentTypeId;
	}

	public String getCustomerResidentTypeDesc() {
		return customerResidentTypeDesc;
	}

	public void setCustomerResidentTypeDesc(String customerResidentTypeDesc) {
		this.customerResidentTypeDesc = customerResidentTypeDesc;
	}

	public Long getTaxApplicableMinAge() {
		return taxApplicableMinAge;
	}

	public void setTaxApplicableMinAge(Long taxApplicableMinAge) {
		this.taxApplicableMinAge = taxApplicableMinAge;
	}

	public Long getTaxApplicableMaxAge() {
		return taxApplicableMaxAge;
	}

	public void setTaxApplicableMaxAge(Long taxApplicableMaxAge) {
		this.taxApplicableMaxAge = taxApplicableMaxAge;
	}

	public String getAgeEffectiveDateType() {
		return ageEffectiveDateType;
	}

	public void setAgeEffectiveDateType(String ageEffectiveDateType) {
		this.ageEffectiveDateType = ageEffectiveDateType;
	}

	public TaxAmountType getTaxAmountType() {
		return taxAmountType;
	}

	public void setTaxAmountType(TaxAmountType taxAmountType) {
		this.taxAmountType = taxAmountType;
	}

//	public BigDecimal getTaxAmount() {
//		return taxAmount;
//	}
//
//	public void setTaxAmount(BigDecimal taxAmount) {
//		this.taxAmount = taxAmount;
//	}
//
//	public BigDecimal getTaxRate() {
//		return taxRate;
//	}
//
//	public void setTaxRate(BigDecimal taxRate) {
//		this.taxRate = taxRate;
//	}

	public BigDecimal getTaxApplicableMinValue() {
		return taxApplicableMinValue;
	}

	public void setTaxApplicableMinValue(BigDecimal taxApplicableMinValue) {
		this.taxApplicableMinValue = taxApplicableMinValue;
	}

	public BigDecimal getTaxApplicableMaxValue() {
		return taxApplicableMaxValue;
	}

	public void setTaxApplicableMaxValue(BigDecimal taxApplicableMaxValue) {
		this.taxApplicableMaxValue = taxApplicableMaxValue;
	}

	public BigDecimal getCeilingFloorfMinValue() {
		return ceilingFloorfMinValue;
	}

	public void setCeilingFloorfMinValue(BigDecimal ceilingFloorfMinValue) {
		this.ceilingFloorfMinValue = ceilingFloorfMinValue;
	}

	public BigDecimal getCeilingFloorfMaxValue() {
		return ceilingFloorfMaxValue;
	}

	public void setCeilingFloorfMaxValue(BigDecimal ceilingFloorfMaxValue) {
		this.ceilingFloorfMaxValue = ceilingFloorfMaxValue;
	}

	public OtherInterestIncome getOtherInterestIncome() {
		return otherInterestIncome;
	}

	public void setOtherInterestIncome(OtherInterestIncome otherInterestIncome) {
		this.otherInterestIncome = otherInterestIncome;
	}

	public TaxProfileStatus getTaxProfileStatus() {
		return taxProfileStatus;
	}

	public void setTaxProfileStatus(TaxProfileStatus taxProfileStatus) {
		this.taxProfileStatus = taxProfileStatus;
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

	public String getCustomerCategoryCode() {
		return customerCategoryCode;
	}

	public void setCustomerCategoryCode(String customerCategoryCode) {
		this.customerCategoryCode = customerCategoryCode;
	}

	public String getCustomerCategoryId() {
		return customerCategoryId;
	}

	public void setCustomerCategoryId(String customerCategoryId) {
		this.customerCategoryId = customerCategoryId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public OtherInterestIncome getDeductIndicator() {
		return deductIndicator;
	}

	public void setDeductIndicator(OtherInterestIncome deductIndicator) {
		this.deductIndicator = deductIndicator;
	}

	public BaredBy getBaredBy() {
		return baredBy;
	}

	public void setBaredBy(BaredBy baredBy) {
		this.baredBy = baredBy;
	}

	public Long getTaxCode_id() {
		if (taxCodeId != null)
			return taxCodeId;
		else
			return null;
	}

	public Long getTaxCodeId() {
		return taxCodeId;
	}

	public void setTaxCodeId(Long taxCodeId) {
		this.taxCodeId = taxCodeId;
	}

	public Long getSubProductId() {
		return subProductId;
	}

	public void setSubProductId(Long subProductId) {
		this.subProductId = subProductId;
	}

	public String getProductCategoryDesc() {
		return productCategoryDesc;
	}

	public void setProductCategoryDesc(String productCategoryDesc) {
		this.productCategoryDesc = productCategoryDesc;
	}

	public List<TaxProfileDetails> getTaxProfileDetails() {
		return taxProfileDetails;
	}

	public void setTaxProfileDetails(List<TaxProfileDetails> taxProfileDetails) {
		this.taxProfileDetails = taxProfileDetails;
	}

//	public void setTaxCode_id(Long taxCode_id) {
//		this.taxCode_id = taxCode_id;
//	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((ageEffectiveDateType == null) ? 0 : ageEffectiveDateType.hashCode());
//		result = prime * result + ((applicableAccTypeComnListId == null) ? 0 : applicableAccTypeComnListId.hashCode());
//		result = prime * result + ((applicableAccTypeDesc == null) ? 0 : applicableAccTypeDesc.hashCode());
//		result = prime * result + ((applicableProductId == null) ? 0 : applicableProductId.hashCode());
//		result = prime * result + ((applicableProductName == null) ? 0 : applicableProductName.hashCode());
//		result = prime * result + ((applicableProductStatus == null) ? 0 : applicableProductStatus.hashCode());
//		result = prime * result + ((ceilingFloorfMaxValue == null) ? 0 : ceilingFloorfMaxValue.hashCode());
//		result = prime * result + ((ceilingFloorfMinValue == null) ? 0 : ceilingFloorfMinValue.hashCode());
//		result = prime * result + ((customerCategory == null) ? 0 : customerCategory.hashCode());
//		result = prime * result + ((customerResidentTypeDesc == null) ? 0 : customerResidentTypeDesc.hashCode());
//		result = prime * result + ((customerResidentTypeId == null) ? 0 : customerResidentTypeId.hashCode());
//		result = prime * result
//				+ ((customerSubTypeIndividualDesc == null) ? 0 : customerSubTypeIndividualDesc.hashCode());
//		result = prime * result + ((customerSubTypeIndividualId == null) ? 0 : customerSubTypeIndividualId.hashCode());
//		result = prime * result
//				+ ((customerSubTypeNonIndividualDesc == null) ? 0 : customerSubTypeNonIndividualDesc.hashCode());
//		result = prime * result
//				+ ((customerSubTypeNonIndividualId == null) ? 0 : customerSubTypeNonIndividualId.hashCode());
//		result = prime * result + ((declarationTypeComnListId == null) ? 0 : declarationTypeComnListId.hashCode());
////		result = prime * result + ((declarationTypeDesc == null) ? 0 : declarationTypeDesc.hashCode());
//		result = prime * result + ((effectiveDate == null) ? 0 : effectiveDate.hashCode());
//		result = prime * result + ((otherInterestIncome == null) ? 0 : otherInterestIncome.hashCode());
//		result = prime * result + ((productCategoryComnListId == null) ? 0 : productCategoryComnListId.hashCode());
//		result = prime * result + ((productCategoryDesc == null) ? 0 : productCategoryDesc.hashCode());
//		result = prime * result + ((taxAmount == null) ? 0 : taxAmount.hashCode());
//		result = prime * result + ((taxAmountType == null) ? 0 : taxAmountType.hashCode());
//		result = prime * result + ((taxApplicableLevel == null) ? 0 : taxApplicableLevel.hashCode());
//		result = prime * result + ((taxApplicableMaxAge == null) ? 0 : taxApplicableMaxAge.hashCode());
//		result = prime * result + ((taxApplicableMaxValue == null) ? 0 : taxApplicableMaxValue.hashCode());
//		result = prime * result + ((taxApplicableMinAge == null) ? 0 : taxApplicableMinAge.hashCode());
//		result = prime * result + ((taxApplicableMinValue == null) ? 0 : taxApplicableMinValue.hashCode());
//		result = prime * result + ((taxCodeId == null) ? 0 : taxCodeId.hashCode());
//		result = prime * result + ((taxProfileStatus == null) ? 0 : taxProfileStatus.hashCode());
//		result = prime * result + ((taxProfileTenantId == null) ? 0 : taxProfileTenantId.hashCode());
//		result = prime * result + ((taxRate == null) ? 0 : taxRate.hashCode());
//		return result;
//	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		TaxProfile other = (TaxProfile) obj;
//		if (ageEffectiveDateType != other.ageEffectiveDateType)
//			return false;
//		if (applicableAccTypeComnListId == null) {
//			if (other.applicableAccTypeComnListId != null)
//				return false;
//		} else if (!applicableAccTypeComnListId.equals(other.applicableAccTypeComnListId)) {
//			return false;
//		}
//		if (applicableAccTypeDesc == null) {
//			if (other.applicableAccTypeDesc != null)
//				return false;
//		} else if (!applicableAccTypeDesc.equals(other.applicableAccTypeDesc)) {
//			return false;
//		}
//		if (applicableProductId == null) {
//			if (other.applicableProductId != null)
//				return false;
//		} else if (!applicableProductId.equals(other.applicableProductId)) {
//			return false;
//		}
//		if (applicableProductName == null) {
//			if (other.applicableProductName != null)
//				return false;
//		} else if (!applicableProductName.equals(other.applicableProductName)) {
//			return false;
//		}
//		if (applicableProductStatus != other.applicableProductStatus)
//			return false;
//		if (ceilingFloorfMaxValue == null) {
//			if (other.ceilingFloorfMaxValue != null)
//				return false;
//		} else if (ceilingFloorfMaxValue.doubleValue() != other.ceilingFloorfMaxValue.doubleValue()) {
//			return false;
//		}
//		if (ceilingFloorfMinValue == null) {
//			if (other.ceilingFloorfMinValue != null)
//				return false;
//		} else if (ceilingFloorfMinValue.doubleValue() != other.ceilingFloorfMinValue.doubleValue()) {
//			return false;
//		}
//		if (customerCategory != other.customerCategory)
//			return false;
//		if (customerResidentTypeDesc == null) {
//			if (other.customerResidentTypeDesc != null)
//				return false;
//		} else if (!customerResidentTypeDesc.equals(other.customerResidentTypeDesc)) {
//			return false;
//		}
//		if (customerResidentTypeId == null) {
//			if (other.customerResidentTypeId != null)
//				return false;
//		} else if (!customerResidentTypeId.equals(other.customerResidentTypeId)) {
//			return false;
//		}
//		if (customerSubTypeIndividualDesc == null) {
//			if (other.customerSubTypeIndividualDesc != null)
//				return false;
//		} else if (!customerSubTypeIndividualDesc.equals(other.customerSubTypeIndividualDesc)) {
//			return false;
//		}
//		if (customerSubTypeIndividualId == null) {
//			if (other.customerSubTypeIndividualId != null)
//				return false;
//		} else if (!customerSubTypeIndividualId.equals(other.customerSubTypeIndividualId)) {
//			return false;
//		}
//		if (customerSubTypeNonIndividualDesc == null) {
//			if (other.customerSubTypeNonIndividualDesc != null)
//				return false;
//		} else if (!customerSubTypeNonIndividualDesc.equals(other.customerSubTypeNonIndividualDesc)) {
//			return false;
//		}
//		if (customerSubTypeNonIndividualId == null) {
//			if (other.customerSubTypeNonIndividualId != null)
//				return false;
//		} else if (!customerSubTypeNonIndividualId.equals(other.customerSubTypeNonIndividualId)) {
//			return false;
//		}
//		if (declarationTypeComnListId == null) {
//			if (other.declarationTypeComnListId != null)
//				return false;
//		} else if (!declarationTypeComnListId.equals(other.declarationTypeComnListId)) {
//			return false;
//		}
////		if (declarationTypeDesc == null) {
////			if (other.declarationTypeDesc != null)
////				return false;
////		} else if (!declarationTypeDesc.equals(other.declarationTypeDesc)) {
////			return false;
////		}
//		if (effectiveDate == null) {
//			if (other.effectiveDate != null)
//				return false;
//		} else if (!effectiveDate.equals(other.effectiveDate)) {
//			return false;
//		}
//		if (otherInterestIncome != other.otherInterestIncome)
//			return false;
//		if (productCategoryComnListId == null) {
//			if (other.productCategoryComnListId != null)
//				return false;
//		} else if (!productCategoryComnListId.equals(other.productCategoryComnListId)) {
//			return false;
//		}
//		if (productCategoryDesc == null) {
//			if (other.productCategoryDesc != null)
//				return false;
//		} else if (!productCategoryDesc.equals(other.productCategoryDesc)) {
//			return false;
//		}
//		if (taxAmount == null) {
//			if (other.taxAmount != null)
//				return false;
//		} else if (taxAmount.doubleValue() != other.taxAmount.doubleValue()) {
//			return false;
//		}
//		if (taxAmountType != other.taxAmountType)
//			return false;
//		if (taxApplicableLevel != other.taxApplicableLevel)
//			return false;
//		if (taxApplicableMaxAge != other.taxApplicableMaxAge)
//			return false;
//		if (taxApplicableMaxValue == null) {
//			if (other.taxApplicableMaxValue != null)
//				return false;
//		} else if (!taxApplicableMaxValue.equals(other.taxApplicableMaxValue)) {
//			return false;
//		}
//		if (taxApplicableMinAge != other.taxApplicableMinAge)
//			return false;
//		if (taxApplicableMinValue == null) {
//			if (other.taxApplicableMinValue != null)
//				return false;
//		} else if (!taxApplicableMinValue.equals(other.taxApplicableMinValue)) {
//			return false;
//		}
//		if (taxCodeId == null) {
//			if (other.taxCodeId != null)
//				return false;
//		} else if (!taxCodeId.equals(other.taxCodeId)) {
//			return false;
//		}
//		if (taxProfileStatus != other.taxProfileStatus) {
//			return false;
//		}
//		if (taxProfileTenantId == null) {
//			if (other.taxProfileTenantId != null)
//				return false;
//		} else if (!taxProfileTenantId.equals(other.taxProfileTenantId)) {
//			return false;
//		}
//		if (taxRate == null) {
//			if (other.taxRate != null)
//				return false;
//		} else if (taxRate.doubleValue() != other.taxRate.doubleValue()) {
//			return false;
//		}
//		return true;
//	}

//	public boolean equalsFunction(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		TaxProfile other = (TaxProfile) obj;
//		if (ageEffectiveDateType != other.ageEffectiveDateType)
//			return false;
//		if (applicableAccTypeComnListId == null) {
//			if (other.applicableAccTypeComnListId != null)
//				return false;
//		} else if (!applicableAccTypeComnListId.equals(other.applicableAccTypeComnListId)) {
//			return false;
//		}
//		if (applicableAccTypeDesc == null) {
//			if (other.applicableAccTypeDesc != null)
//				return false;
//		} else if (!applicableAccTypeDesc.equals(other.applicableAccTypeDesc)) {
//			return false;
//		}
//		if (applicableProductId == null) {
//			if (other.applicableProductId != null)
//				return false;
//		} else if (!applicableProductId.equals(other.applicableProductId)) {
//			return false;
//		}
//		if (applicableProductName == null) {
//			if (other.applicableProductName != null)
//				return false;
//		} else if (!applicableProductName.equals(other.applicableProductName)) {
//			return false;
//		}
//		if (applicableProductStatus != other.applicableProductStatus)
//			return false;
//		if (ceilingFloorfMaxValue == null) {
//			if (other.ceilingFloorfMaxValue != null)
//				return false;
//		} else if (ceilingFloorfMaxValue.doubleValue() != other.ceilingFloorfMaxValue.doubleValue()) {
//			return false;
//		}
//		if (ceilingFloorfMinValue == null) {
//			if (other.ceilingFloorfMinValue != null)
//				return false;
//		} else if (ceilingFloorfMinValue.doubleValue() != other.ceilingFloorfMinValue.doubleValue()) {
//			return false;
//		}
//		if (customerCategory != other.customerCategory)
//			return false;
//		if (customerResidentTypeDesc == null) {
//			if (other.customerResidentTypeDesc != null)
//				return false;
//		} else if (!customerResidentTypeDesc.equals(other.customerResidentTypeDesc)) {
//			return false;
//		}
//		if (customerResidentTypeId == null) {
//			if (other.customerResidentTypeId != null)
//				return false;
//		} else if (!customerResidentTypeId.equals(other.customerResidentTypeId)) {
//			return false;
//		}
//		if (customerSubTypeIndividualDesc == null) {
//			if (other.customerSubTypeIndividualDesc != null)
//				return false;
//		} else if (!customerSubTypeIndividualDesc.equals(other.customerSubTypeIndividualDesc)) {
//			return false;
//		}
//		if (customerSubTypeIndividualId == null) {
//			if (other.customerSubTypeIndividualId != null)
//				return false;
//		} else if (!customerSubTypeIndividualId.equals(other.customerSubTypeIndividualId)) {
//			return false;
//		}
//		if (customerSubTypeNonIndividualDesc == null) {
//			if (other.customerSubTypeNonIndividualDesc != null)
//				return false;
//		} else if (!customerSubTypeNonIndividualDesc.equals(other.customerSubTypeNonIndividualDesc)) {
//			return false;
//		}
//		if (customerSubTypeNonIndividualId == null) {
//			if (other.customerSubTypeNonIndividualId != null)
//				return false;
//		} else if (!customerSubTypeNonIndividualId.equals(other.customerSubTypeNonIndividualId)) {
//			return false;
//		}
//		if (declarationTypeComnListId == null) {
//			if (other.declarationTypeComnListId != null)
//				return false;
//		} else if (!declarationTypeComnListId.equals(other.declarationTypeComnListId)) {
//			return false;
//		}
////		if (declarationTypeDesc == null) {
////			if (other.declarationTypeDesc != null)
////				return false;
////		} else if (!declarationTypeDesc.equals(other.declarationTypeDesc)) {
////			return false;
////		}
//		if (effectiveDate == null) {
//			if (other.effectiveDate != null)
//				return false;
//		} else if (!effectiveDate.equals(other.effectiveDate)) {
//			return false;
//		}
//		if (otherInterestIncome != other.otherInterestIncome)
//			return false;
//		if (productCategoryComnListId == null) {
//			if (other.productCategoryComnListId != null)
//				return false;
//		} else if (!productCategoryComnListId.equals(other.productCategoryComnListId)) {
//			return false;
//		}
//		if (productCategoryDesc == null) {
//			if (other.productCategoryDesc != null)
//				return false;
//		} else if (!productCategoryDesc.equals(other.productCategoryDesc)) {
//			return false;
//		}
//		/*
//		 * if (taxAmount == null) { if (other.taxAmount != null) return false; } else if
//		 * (taxAmount.doubleValue()!=other.taxAmount.doubleValue()) { return false; }
//		 */
//		if (taxAmountType != other.taxAmountType)
//			return false;
//		if (taxApplicableLevel != other.taxApplicableLevel)
//			return false;
//		if (taxApplicableMaxAge != other.taxApplicableMaxAge)
//			return false;
//		if (taxApplicableMaxValue == null) {
//			if (other.taxApplicableMaxValue != null)
//				return false;
//		} else if (!taxApplicableMaxValue.equals(other.taxApplicableMaxValue)) {
//			return false;
//		}
//		if (taxApplicableMinAge != other.taxApplicableMinAge)
//			return false;
//		if (taxApplicableMinValue == null) {
//			if (other.taxApplicableMinValue != null)
//				return false;
//		} else if (!taxApplicableMinValue.equals(other.taxApplicableMinValue)) {
//			return false;
//		}
//		if (taxCodeId == null) {
//			if (other.taxCodeId != null)
//				return false;
//		} else if (!taxCodeId.equals(other.taxCodeId)) {
//			return false;
//		}
//		if (taxProfileStatus != other.taxProfileStatus) {
//			return false;
//		}
//		if (taxProfileTenantId == null) {
//			if (other.taxProfileTenantId != null)
//				return false;
//		} else if (!taxProfileTenantId.equals(other.taxProfileTenantId)) {
//			return false;
//		}
//		/*
//		 * if (taxRate == null) { if (other.taxRate != null) return false; } else if
//		 * (taxRate.doubleValue()!=other.taxRate.doubleValue()) { return false; }
//		 */
//		return true;
//	}
}