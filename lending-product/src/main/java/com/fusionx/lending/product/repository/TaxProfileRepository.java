package com.fusionx.lending.product.repository;

import java.math.BigDecimal;

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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.TaxProfile;
import com.fusionx.lending.product.enums.ApplicableAccTypeComnListEnum;
import com.fusionx.lending.product.enums.ApplicableProductStatus;
import com.fusionx.lending.product.enums.TaxAmountType;
import com.fusionx.lending.product.enums.TaxApplicableLevel;
import com.fusionx.lending.product.enums.TaxProfileStatus; 

@Repository
public interface TaxProfileRepository extends JpaRepository<TaxProfile, Long> {

	Optional<Collection<TaxProfile>> findByTaxProfileStatus(TaxProfileStatus status);

	Optional<Collection<TaxProfile>> findByTaxCodeIdAndTaxProfileStatus(Long taxCodeId, TaxProfileStatus status);

	Optional<Collection<TaxProfile>> findByTaxCodeIdAndTaxProfileStatusNotIn(Long taxCodeId, TaxProfileStatus status);

	Optional<Collection<TaxProfile>> findByTaxCodeIdAndTaxApplicableLevelAndTaxProfileStatus(Long taxCodeId,TaxApplicableLevel taxApplicableLevel, TaxProfileStatus status);
	
	Optional<TaxProfile> findByTaxCodeIdAndTaxApplicableLevelAndProductCategoryComnListIdAndProductCategoryDescAndApplicableAccTypeComnListIdAndApplicableProductStatusAndApplicableProductIdAndDeclarationTypeComnListIdAndCustomerCategoryIdAndCustomerSubTypeIndividualIdAndCustomerSubTypeNonIndividualIdAndCustomerResidentTypeIdAndTaxApplicableMinAgeAndTaxApplicableMaxAgeAndAgeEffectiveDateTypeAndTaxAmountTypeAndTaxApplicableMinValueAndTaxApplicableMaxValueAndCeilingFloorfMinValueAndCeilingFloorfMaxValueAndEffectiveDate(
			Long taxCodeId, TaxApplicableLevel taxApplicableLevel, Long productCategoryComnListId, String productCategoryDesc, ApplicableAccTypeComnListEnum applicableAccTypeComnListId, ApplicableProductStatus applicableProductStatus, 
			Long applicableProductId, String declarationTypeComnListId, String customerCategoryId, Long customerSubTypeIndividualId, Long customerSubTypeNonIndividualId, Long customerResidentTypeId,
			Long taxApplicableMinAge, Long taxApplicableMaxAge, String ageEffectiveDateType, TaxAmountType taxAmountType, BigDecimal taxApplicableMinValue, BigDecimal taxApplicableMaxValue, BigDecimal ceilingFloorfMinValue, BigDecimal ceilingFloorfMaxValue, Date taxEffectiveDate);
	
	
	List<TaxProfile> findAllByTaxCodeIdAndTaxApplicableLevelAndSubProductIdOrderByEffectiveDateDesc(Long taxCodeId, TaxApplicableLevel taxApplicableLevel , Long subProductId);
	
	Optional<TaxProfile> findBytaxCodeIdAndTaxApplicableLevel(Long taxCodeId, TaxApplicableLevel taxApplicableLevel);
	
	List<TaxProfile> findBytaxCodeIdAndTaxProfileStatus(Long taxCodeId, TaxProfileStatus taxProfileStatus);
	
//	@Query("SELECT A "
//			+ "FROM TaxProfile A "
//			+ "WHERE A.taxCode.id = :taxCodeId "
//			+ "and A.taxProfileStatus = :status "
//			+ "and A.taxApplicableLevel = :taxApplicableLevel "
//			+ "and ((:prodCatId Is Not Null and A.productCategoryComnListId = :prodCatId) Or :prodCatId Is Null ) "
//			+ "and ((:declarationTypeId Is Not Null and A.declarationTypeComnListId = :declarationTypeId) Or :declarationTypeId Is Null ) "
//			+ "and ((:customerResidentTypeId Is Not Null and A.customerResidentTypeId = :customerResidentTypeId) Or :customerResidentTypeId Is Null ) "
//			+ "and ((:applicableAccTypeId Is Not Null and A.applicableAccTypeComnListId = :applicableAccTypeId) Or :applicableAccTypeId Is Null) "
//			+ "and ((:applicableProductId Is Not Null and A.applicableProductId = :applicableProductId) Or (A.applicableProductId Is Null)) "
//			+ "and ((:customerCategory Is Not Null and A.customerCategoryCode = :customerCategory) Or :customerCategory Is Null ) "
//			+ "and ((:customerSubTypeIndividualId Is Not Null and A.customerSubTypeIndividualId = :customerSubTypeIndividualId) Or :customerSubTypeIndividualId Is Null) "
//			+ "and ((:customerSubTypeNonIndividualId Is Not Null and A.customerSubTypeNonIndividualId = :customerSubTypeNonIndividualId) Or :customerSubTypeNonIndividualId Is Null) "
//			+ "and A.effectiveDate in (SELECT max(B.effectiveDate) FROM TaxProfile B "
//			+ "                        WHERE A.productCategoryComnListId = B.productCategoryComnListId "
//			+ "                        And A.applicableAccTypeComnListId = B.applicableAccTypeComnListId "
//			+ "                        And ((:customerResidentTypeId Is Not Null and B.customerResidentTypeId = :customerResidentTypeId) Or :customerResidentTypeId Is Null ) "
//			+ "                        And ((:declarationTypeId Is Not Null and B.declarationTypeComnListId = :declarationTypeId) Or :declarationTypeId Is Null ) "
//			+ "                        And (A.applicableProductId = B.applicableProductId Or (B.applicableProductId Is Null)) "
//			+ "                        And A.taxCode.id  = B.taxCode.id "
//			+ "                        And A.taxApplicableLevel = B.taxApplicableLevel "
//			+ "                        And ((:customerCategory Is Not Null and B.customerCategoryCode = :customerCategory) Or :customerCategory Is Null ) "
//			+ "                        And ((:customerSubTypeNonIndividualId Is Not Null and B.customerSubTypeNonIndividualId = :customerSubTypeNonIndividualId) Or :customerSubTypeNonIndividualId Is Null) "
//			+ "                        And ((:customerSubTypeIndividualId Is Not Null and B.customerSubTypeIndividualId = :customerSubTypeIndividualId) Or :customerSubTypeIndividualId Is Null) "
//			+ "                        And A.taxProfileStatus = B.taxProfileStatus "
//			+ "                        And B.effectiveDate <= :effectiveDate )")
//	List<TaxProfile> findTaxProfileInfo(@Param("declarationTypeId") String declarationTypeId,
//										@Param("customerResidentTypeId") Long customerResidentTypeId,
//										@Param("prodCatId") Long prodCatId,
//										@Param("applicableAccTypeId") ApplicableAccTypeComnListEnum applicableAccTypeId,
//										@Param("applicableProductId") Long applicableProductId,
//										@Param("customerCategory") String customerCategory,
//										@Param("customerSubTypeIndividualId") Long customerSubTypeIndividualId,
//										@Param("customerSubTypeNonIndividualId") Long customerSubTypeNonIndividualId,
//                                        @Param("taxCodeId") Long taxCodeId,
//                                        @Param("status") TaxProfileStatus status,
//                                        @Param("effectiveDate") Date effectiveDate,
//                                        @Param("taxApplicableLevel") TaxApplicableLevel taxApplicableLevel
//                                       );
	
	
	@Query("SELECT A "
	+ "FROM TaxProfile A "
	+ "WHERE A.taxCodeId = :taxCodeId "
	+ "and A.taxProfileStatus = :status "
	+ "and A.subProductId = :subProductId "
//	+ "and ((:applicableAccTypeId Is Not Null and A.applicableAccTypeComnListId = :applicableAccTypeId) or (A.applicableAccTypeComnListId IS NULL) ) "
	+ "and ((:declarationTypeId Is Not Null and A.declarationTypeComnListId = :declarationTypeId) or (A.declarationTypeComnListId IS NULL)  ) "
	+ "and ((:customerCategory Is Not Null and A.customerCategoryCode = :customerCategory) or (A.customerCategoryCode IS NULL) ) "
	+ "and ((:customerSubTypeId Is Not Null and A.customerCategoryCode = 'ORIN' and  A.customerSubTypeIndividualId =:customerSubTypeId) Or (:customerSubTypeId Is Not Null and A.customerCategoryCode = 'ORCO' and  A.customerSubTypeNonIndividualId =:customerSubTypeId) or ( A.customerSubTypeIndividualId is null and A.customerSubTypeNonIndividualId  is null)) "
	+ "and ((:customerResidentTypeId Is Not Null and A.customerResidentTypeId = :customerResidentTypeId) or (A.customerResidentTypeId IS NULL) ) "
	+ " order by A.effectiveDate DESC")
List<TaxProfile> getTaxProfileUsingCustomerInfo(
						        @Param("taxCodeId") Long taxCodeId,
						        @Param("status") TaxProfileStatus status,
						        @Param("subProductId") Long subProductId,
								//@Param("applicableAccTypeId") ApplicableAccTypeComnListEnum applicableAccTypeId,
								@Param("declarationTypeId") String declarationTypeId,
								@Param("customerCategory") String customerCategory,
								@Param("customerSubTypeId") Long customerSubTypeId,
								@Param("customerResidentTypeId") Long customerResidentTypeId
                               );
	
	
	
	

	
	
	
	
}
