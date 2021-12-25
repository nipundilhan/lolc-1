package com.fusionx.lending.origination.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.NetWorthAsset;
import com.fusionx.lending.origination.domain.NetWorthLiability;
import com.fusionx.lending.origination.enums.CommonInternalExternal;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.CommonYesNoStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.repository.GuarantorRepository;
import com.fusionx.lending.origination.repository.NetWorthAssetRepository;
import com.fusionx.lending.origination.repository.NetWorthLiabilityRepository;
import com.fusionx.lending.origination.resource.BankResponse;
import com.fusionx.lending.origination.resource.GuarantorDetailAddResource;
import com.fusionx.lending.origination.resource.GuarantorDetailResponseRecource;
import com.fusionx.lending.origination.resource.GuarantorDetailUpdateResource;
import com.fusionx.lending.origination.resource.NetWorthAssetAddResource;
import com.fusionx.lending.origination.resource.NetWorthAssetUpdateResource;
import com.fusionx.lending.origination.resource.NetWorthLiabilityAddResource;
import com.fusionx.lending.origination.resource.NetWorthLiabilityUpdateResource;
import com.fusionx.lending.origination.service.GuarantorNetWorthDetailService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * Guarantor Details Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-04-2021   		         FX-6157    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class GuarantorNetWorthDetailServiceImpl  extends MessagePropertyBase implements GuarantorNetWorthDetailService {
	
	@Autowired
	private GuarantorRepository guarantorRepository;
	
	@Autowired
	private NetWorthAssetRepository netWorthAssetRepository;
	
	@Autowired
	private NetWorthLiabilityRepository netWorthLiabilityRepository;
	
	@Autowired
	private ValidateService validateService;
	
	@Autowired
	private CommonListRepository commonListRepository;

	/**
	 * 
	 * Find all Guarantor Detail guarantor ID
	 * @author Senitha
	 * @return -JSON array of Guarantor Details
	 * 
	 * */
	@Override
	public GuarantorDetailResponseRecource getAllByGuarantorId(Long guarantorId) {

		/*Optional<Guarantor> optionalGuarantor = guarantorRepository.findById(guarantorId);
		if (!optionalGuarantor.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "message");
		}
		
		GuarantorDetailResponseRecource assetDetailsResponseResource = new GuarantorDetailResponseRecource();
	*/
		return null;
	}
	
	/**
	 * 
	 * Find Guarantor Detail by guarantor ID
	 * @author Senitha
	 * @return -JSON array of Guarantor Details
	 * 
	 * */
	@Override
	public Guarantor getSummaryByGuarantorId(String tenantId, Long guarantorId) {
		Optional<Guarantor> isPresentFeeChargeDetail = guarantorRepository.findById(guarantorId);
		if (isPresentFeeChargeDetail.isPresent()) {
			
			Guarantor guarantor = isPresentFeeChargeDetail.get();
			List<NetWorthAsset> netWorthAssets = netWorthAssetRepository.findByGuarantorId(guarantor.getId());
			for (NetWorthAsset netWorthAsset : netWorthAssets) {
				setNetWorthAssetCommonListName(netWorthAsset);
			}
			List<NetWorthLiability> netWorthLiabilities = netWorthLiabilityRepository.findByGuarantorId(guarantor.getId());
			for (NetWorthLiability netWorthLiability : netWorthLiabilities) {
				setNetWorthLiabilityCommonListName(tenantId, netWorthLiability);
			}
			guarantor.setAssets(netWorthAssets);
			guarantor.setLiabilities(netWorthLiabilities);
			
			return guarantor;
			
		}
		else {
			return null;
		}
	}


	/**
	 * 
	 * Insert Guarantor Details
	 * @author Senitha
	 * @param  - GuarantorDetailsAddResource
	 * @return - Successfully saved
	 * 
	 * */
	@Override
	public Guarantor addGuarantorDetail(String tenantId, GuarantorDetailAddResource guarantorDetailsAddResource, Long guarantorId) {

		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty("common.not-null"), "username");
		
		Guarantor guarantorDetail = new Guarantor();

		LoggerRequest.getInstance().logInfo("/******************************** Save Guarantor Detail *********************************************/");
		guarantorDetail = saveGuarantorDetail(tenantId, guarantorDetailsAddResource, guarantorId);
		
		return guarantorDetail;
		
	}
	
	// save guarantor details
	private Guarantor saveGuarantorDetail(String tenantId, GuarantorDetailAddResource guarantorDetailsAddResource, Long guarantorId) {
		
		LoggerRequest.getInstance().logInfo("/******************************** Validate Guarantor *********************************************/");
		Optional<Guarantor> isPresentGuarantor = guarantorRepository.findByIdAndStatus(guarantorId, "ACTIVE");
		if (!isPresentGuarantor.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("guarantorId.not-found"), "guarantorId");
		}

		Guarantor guarantorDetail = isPresentGuarantor.get();
		
		guarantorDetail.setBasedOnNetIncome(CommonYesNoStatus.valueOf(guarantorDetailsAddResource.getBasedOnNetIncome()));
		guarantorDetail.setBasedOnNetWorth(CommonYesNoStatus.valueOf(guarantorDetailsAddResource.getBasedOnNetWorth()));
		guarantorDetail.setAsAnAdditionalSecurity(CommonYesNoStatus.valueOf(guarantorDetailsAddResource.getAsAnAdditionalSecurity()));
		guarantorDetail.setTotalAssetValue(stringToBigDecimal(guarantorDetailsAddResource.getTotalAssetValue()));
		guarantorDetail.setTotalLiabilityValue(stringToBigDecimal(guarantorDetailsAddResource.getTotalLiabilityValue()));
		guarantorDetail.setNetworth(stringToBigDecimal(guarantorDetailsAddResource.getNetworth()));
		guarantorDetail.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		guarantorDetail.setModifiedDate(getCreateOrModifyDate());
		guarantorDetail.setSyncTs(getCreateOrModifyDate());
		guarantorDetail = guarantorRepository.saveAndFlush(guarantorDetail);

		LoggerRequest.getInstance().logInfo("/******************************** Save Asset Detail & Calulate Total Asset Value *********************************************/");
		BigDecimal totalAssetValue = saveAssetDetail(tenantId, guarantorDetail, guarantorDetailsAddResource.getAssets());
		if(!totalAssetValue.equals(stringToBigDecimal(guarantorDetailsAddResource.getTotalAssetValue()))) {
			throw new ValidateRecordException(environment.getProperty("totalAssest.invalid"), "totalAssetValue");
		}

		LoggerRequest.getInstance().logInfo("/******************************** Save Liability Detail & Calulate Total Liability Value *********************************************/");
		BigDecimal totalLiabilityValue = saveLiabilityDetail(tenantId, guarantorDetail, guarantorDetailsAddResource.getLiabilities());
		if(!totalLiabilityValue.equals(stringToBigDecimal(guarantorDetailsAddResource.getTotalLiabilityValue()))) {
			throw new ValidateRecordException(environment.getProperty("totalLiability.invalid"), "totalLiabilityValue");
		}
		
		LoggerRequest.getInstance().logInfo("/******************************** Calulate Networth *********************************************/");
		BigDecimal networth = totalAssetValue.subtract(totalLiabilityValue);
		if(!networth.equals(stringToBigDecimal(guarantorDetailsAddResource.getNetworth()))) {
			throw new ValidateRecordException(environment.getProperty("networth.invalid"), "networth");
		}
		
		return guarantorDetail;
		
	}
	
	// save asset details
	private BigDecimal saveAssetDetail(String tenantId, Guarantor guarantorDetail, List<NetWorthAssetAddResource> netWorthAssetAddResource) {
		
		BigDecimal totalValue = BigDecimal.ZERO;
		Integer index=0;
		
		for(NetWorthAssetAddResource netWorthAssetAddResources : netWorthAssetAddResource) {
			
			LoggerRequest.getInstance().logInfo("/******************************** Validate Type Of Asset *********************************************/");
			Optional<CommonList> isPresentTypeOfAsset = commonListRepository.findByIdAndNameAndReferenceCodeAndStatus(Long.parseLong(netWorthAssetAddResources.getTypeOfAssetId()), 
					netWorthAssetAddResources.getTypeOfAssetName(), "TYPEOFASSET", "ACTIVE");
			if (!isPresentTypeOfAsset.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("typeOfAssetId.invalid"), "typeOfAssetId");
			}

			LoggerRequest.getInstance().logInfo("/******************************** Validate Type Of Ownership *********************************************/");
			Optional<CommonList> isPresentOwnership = commonListRepository.findByIdAndNameAndReferenceCodeAndStatus(Long.parseLong(netWorthAssetAddResources.getOwnershipId()), 
					netWorthAssetAddResources.getOwnershipName(), "OWNERSHIPTYPE", "ACTIVE");
			if (!isPresentOwnership.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("ownershipId.invalid"), "ownershipId");
			}
			
			NetWorthAsset netWorthAsset = new NetWorthAsset();
			netWorthAsset.setTenantId(tenantId);
            netWorthAsset.setGuarantor(guarantorDetail);
			netWorthAsset.setTypeOfAssetId(Long.parseLong(netWorthAssetAddResources.getTypeOfAssetId()));
			netWorthAsset.setDescription(netWorthAssetAddResources.getDescription());
			netWorthAsset.setOwnershipId(Long.parseLong(netWorthAssetAddResources.getOwnershipId()));
			netWorthAsset.setValue(stringToBigDecimal(netWorthAssetAddResources.getValue()));
			netWorthAsset.setNote(netWorthAssetAddResources.getComment());
			netWorthAsset.setStatus(CommonStatus.valueOf(netWorthAssetAddResources.getStatus()));
			netWorthAsset.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			netWorthAsset.setCreatedDate(getCreateOrModifyDate());
			netWorthAsset.setSyncTs(getCreateOrModifyDate());
			netWorthAssetRepository.save(netWorthAsset);
			
			LoggerRequest.getInstance().logInfo("/******************************** Calculate Sum of Value *********************************************/");
			totalValue = totalValue.add(stringToBigDecimal(netWorthAssetAddResources.getValue()));
			
			index++;
			
		}
		
		return totalValue;
		
	}
	
	// save liability details
	private BigDecimal saveLiabilityDetail(String tenantId, Guarantor guarantorDetail, List<NetWorthLiabilityAddResource> netWorthLiabilityAddResources) {
		
		BigDecimal totalOutstandingAmount = BigDecimal.ZERO;
		Integer index=0;
		for(NetWorthLiabilityAddResource netWorthLiabilityAddResource : netWorthLiabilityAddResources) {

			LoggerRequest.getInstance().logInfo("/******************************** Validate Company *********************************************/");
			validateService.validateCompany(tenantId, Long.parseLong(netWorthLiabilityAddResource.getCompanyId()), netWorthLiabilityAddResource.getCompanyName());

			LoggerRequest.getInstance().logInfo("/******************************** Validate Type Of Facility *********************************************/");
			Optional<CommonList> isPresentTypeOfFacility = commonListRepository.findByIdAndNameAndReferenceCodeAndStatus(Long.parseLong(netWorthLiabilityAddResource.getTypeOfFacilityId()), 
					netWorthLiabilityAddResource.getTypeOfFacilityName(), "TYPEOFFACILITY", "ACTIVE");
			if (!isPresentTypeOfFacility.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("typeOfFacilityId.invalid"), "typeOfFacilityId");
			}

			NetWorthLiability netWorthLiability = new NetWorthLiability();
			netWorthLiability.setTenantId(tenantId);
            netWorthLiability.setGuarantor(guarantorDetail);
			netWorthLiability.setTypeOfLiability(CommonInternalExternal.valueOf(netWorthLiabilityAddResource.getTypeOfLiability()));
			netWorthLiability.setCompanyId(Long.parseLong(netWorthLiabilityAddResource.getCompanyId()));
			netWorthLiability.setTypeOfFacilityId(Long.parseLong(netWorthLiabilityAddResource.getTypeOfFacilityId()));
			netWorthLiability.setFacilityAmount(stringToBigDecimal(netWorthLiabilityAddResource.getFacilityAmount()));
			netWorthLiability.setRental(stringToBigDecimal(netWorthLiabilityAddResource.getRental()));
			netWorthLiability.setOutstandingAmount(stringToBigDecimal(netWorthLiabilityAddResource.getOutstandingAmount()));
			netWorthLiability.setNote(netWorthLiabilityAddResource.getComment());
			netWorthLiability.setStatus(CommonStatus.valueOf(netWorthLiabilityAddResource.getStatus()));
			netWorthLiability.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			netWorthLiability.setCreatedDate(getCreateOrModifyDate());
			netWorthLiability.setSyncTs(getCreateOrModifyDate());
			netWorthLiabilityRepository.save(netWorthLiability);
			
			LoggerRequest.getInstance().logInfo("/******************************** Calculate Sum of Outsanding Amount *********************************************/");
			totalOutstandingAmount = totalOutstandingAmount.add(stringToBigDecimal(netWorthLiabilityAddResource.getOutstandingAmount()));
			
			index++;
			
		}
		
		return totalOutstandingAmount;
		
	}
	
	/**
	 * 
	 * Update Guarantor Details
	 * @author Senitha
	 * @param  - GuarantorDetailUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	@Override
	public Guarantor updateGuarantorDetail(String tenantId, GuarantorDetailUpdateResource guarantorDetailUpdateResource) {

		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<Guarantor> isPresentGuarantorDetail = guarantorRepository.findById(Long.parseLong(guarantorDetailUpdateResource.getId()));
		
		if (isPresentGuarantorDetail.isPresent()) {
			
			if (!isPresentGuarantorDetail.get().getVersion().equals(Long.parseLong(guarantorDetailUpdateResource.getVersion()))) {
	        	throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
			}

			LoggerRequest.getInstance().logInfo("/******************************** Update Guarantor Detail *********************************************/");
			Guarantor guarantorDetail = guarantorDetailUpdate (tenantId, guarantorDetailUpdateResource);
			
			return guarantorDetail;
		}
		
		else {
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
		}
		
	}
	
	// update guarantor details
	private Guarantor guarantorDetailUpdate(String tenantId, GuarantorDetailUpdateResource guarantorDetailUpdateResource) {
		
		Optional<Guarantor> isPresentGuarantorDetail = guarantorRepository.findById(Long.parseLong(guarantorDetailUpdateResource.getId()));
		
		Guarantor guarantorDetail = isPresentGuarantorDetail.get();
		guarantorDetail.setTotalAssetValue(stringToBigDecimal(guarantorDetailUpdateResource.getTotalAssetValue()));
		guarantorDetail.setTotalLiabilityValue(stringToBigDecimal(guarantorDetailUpdateResource.getTotalLiabilityValue()));
		guarantorDetail.setNetworth(stringToBigDecimal(guarantorDetailUpdateResource.getNetworth()));
		guarantorDetail.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		guarantorDetail.setModifiedDate(getCreateOrModifyDate());
		guarantorDetail.setVersion(Long.parseLong(guarantorDetailUpdateResource.getVersion()));
		guarantorDetail.setSyncTs(getCreateOrModifyDate());
		guarantorDetail = guarantorRepository.saveAndFlush(guarantorDetail);

		LoggerRequest.getInstance().logInfo("/******************************** Update Asset Detail & Calulate Total Asset Value *********************************************/");
		BigDecimal totalAssetValue = updateAssetDetail(tenantId, guarantorDetail, guarantorDetailUpdateResource.getAssets());
		if(!totalAssetValue.equals(stringToBigDecimal(guarantorDetailUpdateResource.getTotalAssetValue()))) {
			throw new ValidateRecordException(environment.getProperty("totalAssest.invalid"), "totalAssetValue");
		}

		LoggerRequest.getInstance().logInfo("/******************************** Save Liability Detail & Calulate Total Liability Value *********************************************/");
		BigDecimal totalLiabilityValue = updateLiabilityDetail(tenantId, guarantorDetail, guarantorDetailUpdateResource.getLiabilities());
		if(!totalLiabilityValue.equals(stringToBigDecimal(guarantorDetailUpdateResource.getTotalLiabilityValue()))) {
			throw new ValidateRecordException(environment.getProperty("totalLiability.invalid"), "totalLiabilityValue");
		}
		
		LoggerRequest.getInstance().logInfo("/******************************** Calulate Networth *********************************************/");
		BigDecimal networth = totalAssetValue.subtract(totalLiabilityValue);
		if(!networth.equals(stringToBigDecimal(guarantorDetailUpdateResource.getNetworth()))) {
			throw new ValidateRecordException(environment.getProperty("networth.invalid"), "networth");
		}
		
		return guarantorDetail;
		
	}
	
	// update asset details
	private BigDecimal updateAssetDetail(String tenantId, Guarantor guarantorDetail, List<NetWorthAssetUpdateResource> netWorthAssetUpdateResource) {
		
		BigDecimal totalValue = BigDecimal.ZERO;
		Integer index=0;
		for(NetWorthAssetUpdateResource netWorthAssetupdateResources : netWorthAssetUpdateResource) {
			
			if(netWorthAssetupdateResources.getId() != null && !netWorthAssetupdateResources.getId().isEmpty()) {
				
				Optional<NetWorthAsset> isPresentNetWorthAsset = netWorthAssetRepository.findByGuarantorIdAndId(guarantorDetail.getId(), Long.parseLong(netWorthAssetupdateResources.getId()));
				if (isPresentNetWorthAsset.isPresent()) {
					
					if (!isPresentNetWorthAsset.get().getVersion().equals(Long.parseLong(netWorthAssetupdateResources.getVersion()))) {
			        	throw new ValidateRecordException(environment.getProperty("assetId.invalid-version"), "version");
					}
					
					LoggerRequest.getInstance().logInfo("/******************************** Validate Type Of Asset *********************************************/");
					Optional<CommonList> isPresentTypeOfAsset = commonListRepository.findByIdAndNameAndReferenceCodeAndStatus(Long.parseLong(netWorthAssetupdateResources.getTypeOfAssetId()), 
							netWorthAssetupdateResources.getTypeOfAssetName(), "TYPEOFASSET", "ACTIVE");
					if (!isPresentTypeOfAsset.isPresent()) {
						throw new ValidateRecordException(environment.getProperty("typeOfAssetId.invalid"), "typeOfAssetId");
					}

					LoggerRequest.getInstance().logInfo("/******************************** Validate Type Of Ownership *********************************************/");
					Optional<CommonList> isPresentOwnership = commonListRepository.findByIdAndNameAndReferenceCodeAndStatus(Long.parseLong(netWorthAssetupdateResources.getOwnershipId()), 
							netWorthAssetupdateResources.getOwnershipName(), "OWNERSHIPTYPE", "ACTIVE");
					if (!isPresentOwnership.isPresent()) {
						throw new ValidateRecordException(environment.getProperty("ownershipId.invalid"), "ownershipId");
					}
					
					NetWorthAsset netWorthAsset = isPresentNetWorthAsset.get();
					netWorthAsset.setTenantId(tenantId);
					netWorthAsset.setTypeOfAssetId(Long.parseLong(netWorthAssetupdateResources.getTypeOfAssetId()));
					netWorthAsset.setDescription(netWorthAssetupdateResources.getDescription());
					netWorthAsset.setOwnershipId(Long.parseLong(netWorthAssetupdateResources.getOwnershipId()));
					netWorthAsset.setValue(stringToBigDecimal(netWorthAssetupdateResources.getValue()));
					netWorthAsset.setNote(netWorthAssetupdateResources.getComment());
					netWorthAsset.setStatus(CommonStatus.valueOf(netWorthAssetupdateResources.getStatus()));
					netWorthAsset.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
					netWorthAsset.setModifiedDate(getCreateOrModifyDate());
					netWorthAsset.setVersion(Long.parseLong(netWorthAssetupdateResources.getVersion())); 
					netWorthAsset.setSyncTs(getCreateOrModifyDate());
					netWorthAssetRepository.save(netWorthAsset);
					
					LoggerRequest.getInstance().logInfo("/******************************** Calculate Sum of Value *********************************************/");
					totalValue = totalValue.add(stringToBigDecimal(netWorthAssetupdateResources.getValue()));
					
					index++;
					
				} else {
					throw new ValidateRecordException(environment.getProperty("assetId.not-found"), "id");
				}
				
			}
		}
		
		return totalValue;
		
	}
	
	// update liability details
	private BigDecimal updateLiabilityDetail(String tenantId, Guarantor guarantorDetail, List<NetWorthLiabilityUpdateResource> netWorthLiabilityupdateResources) {
		
		BigDecimal totalOutstandingAmount = BigDecimal.ZERO;
		Integer index=0;
		for(NetWorthLiabilityUpdateResource netWorthLiabilityUpdateResource : netWorthLiabilityupdateResources) {
			
			Optional<NetWorthLiability> isPresentNetWorthLiability = netWorthLiabilityRepository.findByGuarantorIdAndId(guarantorDetail.getId(), Long.parseLong(netWorthLiabilityUpdateResource.getId()));
			if (isPresentNetWorthLiability.isPresent()) {
				
				if (!isPresentNetWorthLiability.get().getVersion().equals(Long.parseLong(netWorthLiabilityUpdateResource.getVersion()))) {
		        	throw new ValidateRecordException(environment.getProperty("liability.invalid-version"), "version");
				}
				
				LoggerRequest.getInstance().logInfo("/******************************** Validate Company *********************************************/");
				validateService.validateCompany(tenantId, Long.parseLong(netWorthLiabilityUpdateResource.getCompanyId()), netWorthLiabilityUpdateResource.getCompanyName());

				LoggerRequest.getInstance().logInfo("/******************************** Validate Type Of Facility *********************************************/");
				Optional<CommonList> isPresentTypeOfFacility = commonListRepository.findByIdAndNameAndReferenceCodeAndStatus(Long.parseLong(netWorthLiabilityUpdateResource.getTypeOfFacilityId()), 
						netWorthLiabilityUpdateResource.getTypeOfFacilityName(), "TYPEOFFACILITY", "ACTIVE");
				if (!isPresentTypeOfFacility.isPresent()) {
					throw new ValidateRecordException(environment.getProperty("typeOfFacilityId.invalid"), "typeOfFacilityId");
				}
				
				NetWorthLiability netWorthLiability = isPresentNetWorthLiability.get();
				netWorthLiability.setTenantId(tenantId);
				netWorthLiability.setTypeOfLiability(CommonInternalExternal.valueOf(netWorthLiabilityUpdateResource.getTypeOfLiability()));
				netWorthLiability.setCompanyId(Long.parseLong(netWorthLiabilityUpdateResource.getCompanyId()));
				netWorthLiability.setTypeOfFacilityId(Long.parseLong(netWorthLiabilityUpdateResource.getTypeOfFacilityId()));
				netWorthLiability.setFacilityAmount(stringToBigDecimal(netWorthLiabilityUpdateResource.getFacilityAmount()));
				netWorthLiability.setRental(stringToBigDecimal(netWorthLiabilityUpdateResource.getRental()));
				netWorthLiability.setOutstandingAmount(stringToBigDecimal(netWorthLiabilityUpdateResource.getOutstandingAmount()));
				netWorthLiability.setNote(netWorthLiabilityUpdateResource.getComment());
				netWorthLiability.setStatus(CommonStatus.valueOf(netWorthLiabilityUpdateResource.getStatus()));
				netWorthLiability.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
				netWorthLiability.setModifiedDate(getCreateOrModifyDate());
				netWorthLiability.setVersion(Long.parseLong(netWorthLiabilityUpdateResource.getVersion()));
				netWorthLiability.setSyncTs(getCreateOrModifyDate());
				netWorthLiabilityRepository.save(netWorthLiability);

				LoggerRequest.getInstance().logInfo("/******************************** Calculate Sum of Outsanding Amount *********************************************/");
				totalOutstandingAmount = totalOutstandingAmount.add(stringToBigDecimal(netWorthLiabilityUpdateResource.getOutstandingAmount()));
				
				index++;
				
			} else {
				throw new ValidateRecordException(environment.getProperty("liability.not-found"), "id");
			}
			
		}
		
		return totalOutstandingAmount;
		
	}

	/**
	 * 
	 * Find Net Worth Asset by Guarantor ID
	 * @author Senitha
	 * @return -JSON array of Calculation Frequency
	 * 
	 * */
	@Override
	public List<NetWorthAsset> getNetWorthAssetByGuarantorId(String tenantId, Long guarantorId) {
		
		List<NetWorthAsset> netWorthAssets = netWorthAssetRepository.findByGuarantorId(guarantorId);
		for (NetWorthAsset netWorthAsset : netWorthAssets) {
			setNetWorthAssetCommonListName(netWorthAsset);
		}
		return netWorthAssets;
		
	}

	/**
	 * 
	 * Find Net Worth Liability by Guarantor ID
	 * @author Senitha
	 * @return -JSON array of Calculation Frequency
	 * 
	 * */
	@Override
	public List<NetWorthLiability> getNetWorthLiabilityByGuarantorId(String tenantId, Long guarantorId) {
		
		List<NetWorthLiability> netWorthLiabilities = netWorthLiabilityRepository.findByGuarantorId(guarantorId);
		for (NetWorthLiability netWorthLiability : netWorthLiabilities) {
			setNetWorthLiabilityCommonListName(tenantId, netWorthLiability);
		}
		return netWorthLiabilities;
		
	}

	/**
	 * @author Senitha
	 * */
	private void setNetWorthAssetCommonListName(NetWorthAsset netWorthAsset) {
		
		Optional<CommonList> isPresentTypeOfAsset = commonListRepository.findById(netWorthAsset.getTypeOfAssetId());
		netWorthAsset.setTypeOfAssetName(isPresentTypeOfAsset.get().getName());
		
		Optional<CommonList> isPresentOwnership = commonListRepository.findById(netWorthAsset.getOwnershipId());
		netWorthAsset.setOwnershipName(isPresentOwnership.get().getName());
	}
	
	/**
	 * @author Senitha
	 * */
	private void setNetWorthLiabilityCommonListName(String tenantId, NetWorthLiability netWorthLiability) {
		
		Optional<CommonList> isPresentTypeOfLiability = commonListRepository.findById(netWorthLiability.getTypeOfFacilityId());
		netWorthLiability.setTypeOfFacilityName(isPresentTypeOfLiability.get().getName());
		
		BankResponse bankResponse = validateService.getCompanyName(tenantId, netWorthLiability.getCompanyId());
		netWorthLiability.setCompanyName(bankResponse.getBankName());
		
	}
	
	// Get a created and modified date
	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	// String to BigDecimal
	private BigDecimal stringToBigDecimal(String value) {
		if (value != null) {
			return new BigDecimal(value);
		} else {
			return BigDecimal.valueOf(0.0);
		}
	}
	
}
