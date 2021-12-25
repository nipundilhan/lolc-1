package com.fusionx.lending.origination.service.impl;

/**
 * Credit App Collateral Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		     			VenukiR      Created
 *    
 ********************************************************************************************************
 */
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CreditAppCollateralDetail;
import com.fusionx.lending.origination.domain.CreditAppCollateralDocuments;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.enums.ActionType;
import com.fusionx.lending.origination.enums.CollateralType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.TransferType;
import com.fusionx.lending.origination.exception.DetailValidateException;
import com.fusionx.lending.origination.exception.InvalidDetailListServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CreditAppCollateralDetailsRepository;
import com.fusionx.lending.origination.repository.CreditAppCollateralDocumentsRepository;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.resource.AssetDetailsDocumentsUploadRequestResource;
import com.fusionx.lending.origination.resource.AssetSubTypePartsResponseResource;
import com.fusionx.lending.origination.resource.AssetSubTypeResponseResource;
import com.fusionx.lending.origination.resource.AssetTypePledgeTypeMappingResource;
import com.fusionx.lending.origination.resource.AssetTypeResponseResource;
import com.fusionx.lending.origination.resource.AssetTypeSubTypeResponseResource;
import com.fusionx.lending.origination.resource.AssetsDetailsRequestResource;
import com.fusionx.lending.origination.resource.AssetsDocumentsUpload;
import com.fusionx.lending.origination.resource.ComnSuppliesEntitiesResource;
import com.fusionx.lending.origination.resource.CreAppCollateralDocumentDetailsResource;
import com.fusionx.lending.origination.resource.CreAppCollateralIntergrateResource;
import com.fusionx.lending.origination.resource.CreditAppraiselCollateralDetailsAddResource;
import com.fusionx.lending.origination.resource.CreditAppraiselCollateralDetailsUpdateResource;
import com.fusionx.lending.origination.resource.ExistsDocumentResponseResource;
import com.fusionx.lending.origination.resource.RegistrationAuthorityValidationResource;
import com.fusionx.lending.origination.service.CreditAppCollateralDetailsService;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class CreditAppCollateralDetailsServiceImpl extends MessagePropertyBase
		implements CreditAppCollateralDetailsService {

	@Autowired
	private CreditAppCollateralDetailsRepository creditAppCollateralDetailsRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CreditAppCollateralDocumentsRepository creditAppCollateralDocumentsRepository;

	@Autowired
	private ValidateService validateService;

	@Autowired
	private RemoteService remoteService;

	@Value("${col-collateral-part-asset-sub-type}")
	private String urlAssetPartByAssetSubType;

	@Override
	public Page<CreditAppCollateralDetail> findAll(Pageable pageable) {
		return creditAppCollateralDetailsRepository.findAll(pageable);
	}

	@Override
	public Optional<CreditAppCollateralDetail> findById(Long id, String tenantId) {
		Optional<CreditAppCollateralDetail> approvalCategoryProductMapping = creditAppCollateralDetailsRepository
				.findById(id);
		List<CreditAppCollateralDetail> creditAppCollateralList = new ArrayList<>();
		if (approvalCategoryProductMapping.isPresent()) {
			creditAppCollateralList.add(approvalCategoryProductMapping.get());
			creditAppCollateralList = getColDetInfo(creditAppCollateralList, tenantId);

			return Optional.ofNullable(creditAppCollateralList.get(0));
		} else {
			return Optional.empty();
		}

//		Optional<CreditAppCollateralDetail> approvalCategoryProductMapping = creditAppCollateralDetailsRepository
//				.findById(id);
//		if (approvalCategoryProductMapping.isPresent()) {
//			return Optional.ofNullable(approvalCategoryProductMapping.get());
//		} else {
//			return Optional.empty();
//		}
	}

	@Override
	public List<CreditAppCollateralDetail> findByStatus(String status, String tenantId) {

		List<CreditAppCollateralDetail> details = creditAppCollateralDetailsRepository
				.findByStatus(CommonStatus.valueOf(status));

		List<CreditAppCollateralDetail> detailsAll;

		detailsAll = getColDetInfo(details, tenantId);

		return detailsAll;
	}

	@Override
	public Boolean existsById(Long id) {
		return creditAppCollateralDetailsRepository.existsById(id);
	}

	@Override
	public List<CreditAppCollateralDetail> findAll(String tenantId) {
		List<CreditAppCollateralDetail> details = creditAppCollateralDetailsRepository.findAll();

		List<CreditAppCollateralDetail> detailsAll;

		detailsAll = getColDetInfo(details, tenantId);

		return detailsAll;

	}

	@Override
	public List<CreditAppCollateralDetail> getCreditAppCollateralDetails(Long customerId, String tenantId) {

		List<CreditAppCollateralDetail> creditAppCollateralDetailList = creditAppCollateralDetailsRepository
				.findByCustomerId(customerId);
		List<CreditAppCollateralDetail> detailsAll;

		detailsAll = getColDetInfo(creditAppCollateralDetailList, tenantId);

		return detailsAll;
	}

	public List<CreditAppCollateralDetail> getColDetInfo(List<CreditAppCollateralDetail> collateralDetail,
			String tenantId) {

		List<CreditAppCollateralDetail> collateralDetailAll = new ArrayList<>();
		for (CreditAppCollateralDetail creditAppCollateralDetail : collateralDetail) {
			List<CreditAppCollateralDocuments> documentList = creditAppCollateralDocumentsRepository
					.findByCreditAppCollateralDetailId(creditAppCollateralDetail.getId());
			creditAppCollateralDetail.setCreditAppCollateralDocuments(documentList);

			AssetTypeResponseResource assetTypeResponseResource = validateService.validateAssetType(tenantId,
					creditAppCollateralDetail.getAssetTypeId(), null);
			creditAppCollateralDetail.setAssetTypeName(assetTypeResponseResource.getName());
			AssetSubTypeResponseResource assetSubTypeResponseResource = validateService.validateAssetSubType(tenantId,
					creditAppCollateralDetail.getAssetSubTypeId(), null);
			creditAppCollateralDetail.setAssetSubTypeName(assetSubTypeResponseResource.getName());
			creditAppCollateralDetail.setAssetTypeCode(assetSubTypeResponseResource.getCode());

			if (creditAppCollateralDetail.getAssetPartId() != null) {
				AssetSubTypePartsResponseResource assetSubTypePartsResponseResource = validateService
						.validateAssetSubTypeParts(tenantId, creditAppCollateralDetail.getAssetPartId(), null);
				creditAppCollateralDetail.setAssetPartName(assetSubTypePartsResponseResource.getName());
			}

			if (creditAppCollateralDetail.getSubTypeId() != null) {
				AssetTypeSubTypeResponseResource assetTypeSubTypeResponseResource = validateService
						.validateAssetTypeSubType(tenantId, creditAppCollateralDetail.getSubTypeId(), null);
				creditAppCollateralDetail.setSubTypeName(assetTypeSubTypeResponseResource.getName());
			}

			if (creditAppCollateralDetail.getSupplierId() != null) {
				ComnSuppliesEntitiesResource comnSuppliesEntitiesResource = validateService
						.validateComnSuppliesEntities(tenantId, creditAppCollateralDetail.getSupplierId(), null);
				creditAppCollateralDetail.setSupplierReferenceCode(comnSuppliesEntitiesResource.getSupReferenceCode());

			}

			if (creditAppCollateralDetail.getRegistrationAuthorityId() != null) {
				RegistrationAuthorityValidationResource registrationAuthorityValidationResource = validateService
						.validateRegistrationAuthority(tenantId, creditAppCollateralDetail.getRegistrationAuthorityId(),
								null);
				creditAppCollateralDetail
						.setRegistrationAuthority(registrationAuthorityValidationResource.getAuthorityName());
			}

			if (creditAppCollateralDetail.getOwnershipTypeId() != null) {
				AssetTypePledgeTypeMappingResource assetTypePledgeTypeMappingResource = validateService
						.validatePledgeType(tenantId, creditAppCollateralDetail.getOwnershipTypeId(), null);
				creditAppCollateralDetail.setOwnershipType(assetTypePledgeTypeMappingResource.getName());
			}

			collateralDetailAll.add(creditAppCollateralDetail);
		}

		return collateralDetailAll;
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		return new Timestamp(now.getTime());
	}

	@Override
	public Boolean saveAndValidateCreditAppCollateralDetails(String tenantId, String createdUser,
			CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetails) {

		if (creditAppraiselCollateralDetails != null) {

			if ((creditAppraiselCollateralDetails.getComnCustomerId() == null
					|| creditAppraiselCollateralDetails.getComnCustomerId().isEmpty())
					&& (creditAppraiselCollateralDetails.getPendingCustomerId() == null
							|| creditAppraiselCollateralDetails.getPendingCustomerId().isEmpty()))
				throw new ValidateRecordException(environment.getProperty("custId.penCustId.required"), "message");

	    	if((creditAppraiselCollateralDetails.getComnCustomerId()!=null && !creditAppraiselCollateralDetails.getComnCustomerId().isEmpty()) && 
	    			(creditAppraiselCollateralDetails.getPendingCustomerId()!=null && !creditAppraiselCollateralDetails.getPendingCustomerId().isEmpty()))
	    		throw new ValidateRecordException(environment.getProperty("custId.penCustId.invalid"), "message");

			// validate cutomer details
			Optional<Customer> cutomerExists = customerRepository.findByIdAndStatus(
					Long.parseLong(creditAppraiselCollateralDetails.getCustomerId()), CommonStatus.ACTIVE.toString());
			if (cutomerExists.isPresent()) {
				if (!cutomerExists.get().getFullName().equals(creditAppraiselCollateralDetails.getCustomerName()))
					throw new DetailValidateException(environment.getProperty("invalid.customer"),
							ServiceEntity.CUSTOMER_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);

				if (creditAppraiselCollateralDetails.getComnCustomerId() != null
						&& !creditAppraiselCollateralDetails.getComnCustomerId().isEmpty()) {
					if (!cutomerExists.get().getCustomerId()
							.equals(Long.parseLong(creditAppraiselCollateralDetails.getComnCustomerId()))
							|| !cutomerExists.get().getCusReferenceCode()
									.equalsIgnoreCase(creditAppraiselCollateralDetails.getComnCustomerReference()))
						throw new DetailValidateException(environment.getProperty("invalid.comn.cust"),
								ServiceEntity.COMN_CUST_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);

				}
				else if(creditAppraiselCollateralDetails.getPendingCustomerId()!=null && !creditAppraiselCollateralDetails.getPendingCustomerId().isEmpty()) {
					if(!cutomerExists.get().getPendingCustomerId().equals(Long.parseLong(creditAppraiselCollateralDetails.getPendingCustomerId())))
						throw new DetailValidateException(environment.getProperty("invalid.pending.cust"), ServiceEntity.PEN_CUSTOMER_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
				}

			} else {
				throw new DetailValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.CUSTOMER_ID,
						ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
			}

			Integer index = 0;
			CreditAppCollateralDetail creditAppCollateralDetail = new CreditAppCollateralDetail();

			if (creditAppraiselCollateralDetails.getCollateralType() != null
					&& !creditAppraiselCollateralDetails.getCollateralType().isEmpty()) {
				if (creditAppraiselCollateralDetails.getCollateralType().equalsIgnoreCase("EXISTING")) {
					if (creditAppraiselCollateralDetails.getAssetDetailId() != null
							&& !creditAppraiselCollateralDetails.getAssetDetailId().isEmpty()) {

						if (creditAppraiselCollateralDetails.getAssetEntityId() == null
								|| creditAppraiselCollateralDetails.getAssetEntityId().isEmpty())
							throw new DetailValidateException(environment.getProperty(INVALID_ASSET_ENTITY_ID),
									ServiceEntity.ASSETS_ENTITY_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);

						LoggerRequest.getInstance().logInfo(
								"credit-appraisal-collateral-details********************************Validate collateral details****************");
						validateService.validateExistingCollateraldetails(tenantId, creditAppraiselCollateralDetails);

					} else {
						throw new DetailValidateException(environment.getProperty(INVALID_ASSET_DETAIL_ID),
								ServiceEntity.ASSETS_DETAIL_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
					}

					creditAppCollateralDetail.setCollateralType(
							CollateralType.valueOf(creditAppraiselCollateralDetails.getCollateralType()));

				} else if (creditAppraiselCollateralDetails.getCollateralType().equalsIgnoreCase("NEW")) {

					LoggerRequest.getInstance().logInfo(
							"credit-appraisal-collateral-details********************************Validate asset type****************");
					validateService.validateAssetType(tenantId,
							Long.parseLong(creditAppraiselCollateralDetails.getAssetTypeId()),
							creditAppraiselCollateralDetails.getAssetTypeName());

					AssetSubTypeResponseResource assetSubTypeResponseResource = validateService.validateAssetSubType(
							tenantId, Long.parseLong(creditAppraiselCollateralDetails.getAssetSubTypeId()),
							creditAppraiselCollateralDetails.getAssetSubTypeName());

//					if (!assetSubTypeResponseResource.getAssetTypeId().toString()
//							.equals(creditAppraiselCollateralDetails.getAssetTypeId()))
//						throw new DetailValidateException(environment.getProperty("common.invalid"),
//								ServiceEntity.ASSET_SUB_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);

					// check for asset parts by asset sub type id.
					if (remoteService.checkIsExistPartsForAssetSubType(tenantId,
							creditAppraiselCollateralDetails.getAssetSubTypeId(), urlAssetPartByAssetSubType)) {
						if (creditAppraiselCollateralDetails.getAssetPartId().isEmpty()
								|| creditAppraiselCollateralDetails.getAssetPartId() == null)
							throw new DetailValidateException(environment.getProperty("common.required"),
									ServiceEntity.ASSETS_PART_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);

					} else {
						if (!creditAppraiselCollateralDetails.getAssetPartId().isEmpty()
								|| creditAppraiselCollateralDetails.getAssetPartId() != null
								|| creditAppraiselCollateralDetails.getAssetPartName() != null
								|| !creditAppraiselCollateralDetails.getAssetPartName().isEmpty())
							throw new DetailValidateException(environment.getProperty("no.mapped.parts"),
									ServiceEntity.ASSETS_PART_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
					}

					if (!creditAppraiselCollateralDetails.getAssetPartId().isEmpty()
							&& creditAppraiselCollateralDetails.getAssetPartId() != null) {
						if (creditAppraiselCollateralDetails.getAssetPartName() == null
								|| creditAppraiselCollateralDetails.getAssetPartName().isEmpty())
							throw new DetailValidateException(environment.getProperty("common.required"),
									ServiceEntity.ASSETS_PART_NAME, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);

						validateService.validateAssetSubTypeParts(tenantId,
								Long.parseLong(creditAppraiselCollateralDetails.getAssetSubTypeId()),
								Long.parseLong(creditAppraiselCollateralDetails.getAssetPartId()),
								creditAppraiselCollateralDetails.getAssetPartName());
					}

					if (creditAppraiselCollateralDetails.getSubTypeId() != null
							&& !creditAppraiselCollateralDetails.getSubTypeId().isEmpty()) {
						if (creditAppraiselCollateralDetails.getSubTypeName() == null
								|| creditAppraiselCollateralDetails.getSubTypeName().isEmpty())
							throw new DetailValidateException(environment.getProperty("common.required"),
									ServiceEntity.SUB_TYPE_NAME, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
						validateService.validateAssetTypeSubType(tenantId, creditAppraiselCollateralDetails);
					}

					// validate sypplier
					if (creditAppraiselCollateralDetails.getSupplierId() != null
							&& !creditAppraiselCollateralDetails.getSupplierId().isEmpty()) {
						if (creditAppraiselCollateralDetails.getSupplierReferenceCode() == null
								|| creditAppraiselCollateralDetails.getSupplierReferenceCode().isEmpty())
							throw new DetailValidateException(environment.getProperty("common.required"),
									ServiceEntity.SUUPLIER_REF_CODE, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
						validateService.validateComnSuppliesEntities(tenantId,
								Long.parseLong(creditAppraiselCollateralDetails.getSupplierId()),
								creditAppraiselCollateralDetails.getSupplierReferenceCode());
					}

					// validate registration Authority
					if (creditAppraiselCollateralDetails.getRegistrationAuthorityId() != null
							&& !creditAppraiselCollateralDetails.getRegistrationAuthorityId().isEmpty()) {
						if (creditAppraiselCollateralDetails.getRegistrationAuthority() == null
								|| creditAppraiselCollateralDetails.getRegistrationAuthority().isEmpty())
							throw new DetailValidateException(environment.getProperty("common.required"),
									ServiceEntity.REG_AUTHORITY, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
						validateService.validateRegistrationAuthority(tenantId,
								Long.parseLong(creditAppraiselCollateralDetails.getRegistrationAuthorityId()),
								creditAppraiselCollateralDetails.getRegistrationAuthority());
					}

					// validate pledge details
					if (creditAppraiselCollateralDetails.getPledgeTypeId() != null
							&& !creditAppraiselCollateralDetails.getPledgeTypeId().isEmpty()) {
						if (creditAppraiselCollateralDetails.getPledgeTypeName() == null
								|| creditAppraiselCollateralDetails.getPledgeTypeName().isEmpty())
							throw new DetailValidateException(environment.getProperty("common.required"),
									ServiceEntity.PLEDGE_TYPE_NAME, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
						validateService.validatePledgeType(tenantId,
								Long.parseLong(creditAppraiselCollateralDetails.getAssetTypeId()),
								Long.parseLong(creditAppraiselCollateralDetails.getPledgeTypeId()),
								creditAppraiselCollateralDetails.getPledgeTypeName());
					}

					creditAppCollateralDetail.setCollateralType(
							CollateralType.valueOf(creditAppraiselCollateralDetails.getCollateralType()));

				}

				creditAppCollateralDetail.setTenantId(tenantId);
				creditAppCollateralDetail.setCustomer(cutomerExists.get());
				creditAppCollateralDetail
						.setAssetTypeId(Long.parseLong(creditAppraiselCollateralDetails.getAssetTypeId()));
				creditAppCollateralDetail
						.setAssetSubTypeId(Long.parseLong(creditAppraiselCollateralDetails.getAssetSubTypeId()));
				if (creditAppraiselCollateralDetails.getAssetPartId() != null
						&& !creditAppraiselCollateralDetails.getAssetPartId().isEmpty())
					creditAppCollateralDetail
							.setAssetPartId(Long.parseLong(creditAppraiselCollateralDetails.getAssetPartId()));
				if (creditAppraiselCollateralDetails.getSubTypeId() != null
						&& !creditAppraiselCollateralDetails.getSubTypeId().isEmpty())
					creditAppCollateralDetail
							.setSubTypeId(Long.parseLong(creditAppraiselCollateralDetails.getSubTypeId()));
				if (creditAppraiselCollateralDetails.getPledgeTypeId() != null
						&& !creditAppraiselCollateralDetails.getPledgeTypeId().isEmpty())
					creditAppCollateralDetail
							.setOwnershipTypeId(Long.parseLong(creditAppraiselCollateralDetails.getPledgeTypeId()));
				if (creditAppraiselCollateralDetails.getInvoiceNo() != null
						&& !creditAppraiselCollateralDetails.getInvoiceNo().isEmpty())
					creditAppCollateralDetail.setInvoiceNo(creditAppraiselCollateralDetails.getInvoiceNo());
				creditAppCollateralDetail.setName(creditAppraiselCollateralDetails.getName());
				if (creditAppraiselCollateralDetails.getDescription() != null
						&& !creditAppraiselCollateralDetails.getDescription().isEmpty())
					creditAppCollateralDetail.setDescription(creditAppraiselCollateralDetails.getDescription());
				if (creditAppraiselCollateralDetails.getSupplierId() != null
						&& !creditAppraiselCollateralDetails.getSupplierId().isEmpty())
					creditAppCollateralDetail
							.setSupplierId(Long.parseLong(creditAppraiselCollateralDetails.getSupplierId()));
				if (creditAppraiselCollateralDetails.getRegistrationAuthorityId() != null
						&& !creditAppraiselCollateralDetails.getRegistrationAuthorityId().isEmpty())
					creditAppCollateralDetail.setRegistrationAuthorityId(
							Long.parseLong(creditAppraiselCollateralDetails.getRegistrationAuthorityId()));
				creditAppCollateralDetail.setStatus(CommonStatus.valueOf(creditAppraiselCollateralDetails.getStatus()));
				creditAppCollateralDetail.setCreatedUser(createdUser);
				creditAppCollateralDetail.setCreatedDate(getCreateOrModifyDate());
				creditAppCollateralDetail.setSyncTs(getCreateOrModifyDate());
				// creditAppCollateralDetail.setAssetEntityId(Long.parseLong(creditAppraiselCollateralDetails.getRegistrationAuthorityId()));
				creditAppCollateralDetail = creditAppCollateralDetailsRepository
						.saveAndFlush(creditAppCollateralDetail);

				if (creditAppCollateralDetail.getId() != null)
					LoggerRequest.getInstance().logInfo(
							"credit-appraisal-collateral-details********************************Validate image upload****************");
				if (creditAppraiselCollateralDetails.getDocumentUploadDetails() != null) {
					validateCollateralDocUpload(tenantId, createdUser, creditAppCollateralDetail,
							creditAppraiselCollateralDetails.getDocumentUploadDetails(), ActionType.SAVE);
				}

				LoggerRequest.getInstance().logInfo(
						"credit-appraisal-collateral-details********************************Integrate with collateral****************");

				AssetsDetailsRequestResource assetsDetailsRequestResource = setAssetsDetailsRequest(
						creditAppraiselCollateralDetails, cutomerExists.get());
				Long assetEntityId = validateService.validateAndIntergrateWithCollateral(tenantId, createdUser,
						assetsDetailsRequestResource, creditAppCollateralDetail.getId());
				if (assetEntityId != null) {
					updateCreditAppCollateralDetails(tenantId, createdUser, assetEntityId,
							creditAppCollateralDetail.getId());

				}

			}

			index++;
		}
		return Boolean.TRUE;

	}

	private AssetsDetailsRequestResource setAssetsDetailsRequest(
			CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetails, Customer cutomerExists) {
		List<AssetDetailsDocumentsUploadRequestResource> assetDetailsDocumentsUploadList = new ArrayList<>();
		AssetsDocumentsUpload assetsDocumentsUpload = new AssetsDocumentsUpload();

		AssetsDetailsRequestResource assetsDetailsRequestResource = new AssetsDetailsRequestResource();

		if (creditAppraiselCollateralDetails.getComnCustomerId() != null
				&& !creditAppraiselCollateralDetails.getComnCustomerId().isEmpty()) {
			assetsDetailsRequestResource.setCustomerId(creditAppraiselCollateralDetails.getComnCustomerId());
			assetsDetailsRequestResource
					.setCustomerReferenceCode(creditAppraiselCollateralDetails.getComnCustomerReference());
		} else if (creditAppraiselCollateralDetails.getPendingCustomerId() != null) {
			assetsDetailsRequestResource.setPendingCustomerId(creditAppraiselCollateralDetails.getPendingCustomerId());
		}

		if (creditAppraiselCollateralDetails.getCollateralType().equals(CollateralType.EXISTING.toString())) {
			assetsDetailsRequestResource.setId(creditAppraiselCollateralDetails.getAssetDetailId());
			assetsDetailsRequestResource.setAssetEntityId(creditAppraiselCollateralDetails.getAssetEntityId());
			assetsDetailsRequestResource.setVersion(creditAppraiselCollateralDetails.getAssetDetailVersion());

		} else {
			assetsDetailsRequestResource.setAssetEntityId("");
		}
		if (creditAppraiselCollateralDetails.getCustomerAssetId() != null)
			assetsDetailsRequestResource.setCustomerAssetId(creditAppraiselCollateralDetails.getCustomerAssetId());

		assetsDetailsRequestResource.setAssetTypeId(creditAppraiselCollateralDetails.getAssetTypeId());
		assetsDetailsRequestResource.setAssetTypeName(creditAppraiselCollateralDetails.getAssetTypeName());
		assetsDetailsRequestResource.setAssetSubTypeId(creditAppraiselCollateralDetails.getAssetSubTypeId());
		assetsDetailsRequestResource.setAssetSubTypeName(creditAppraiselCollateralDetails.getAssetSubTypeName());
		assetsDetailsRequestResource.setAssetPartId(creditAppraiselCollateralDetails.getAssetPartId());
		assetsDetailsRequestResource.setAssetPartName(creditAppraiselCollateralDetails.getAssetPartName());
		assetsDetailsRequestResource.setAssetPartId(creditAppraiselCollateralDetails.getAssetPartId());
		assetsDetailsRequestResource.setSubTypeId(creditAppraiselCollateralDetails.getSubTypeId());
		assetsDetailsRequestResource.setSubTypeName(creditAppraiselCollateralDetails.getSubTypeName());
		assetsDetailsRequestResource
				.setRegistrationAuthorityId(creditAppraiselCollateralDetails.getRegistrationAuthorityId());
		assetsDetailsRequestResource
				.setRegistrationAuthorityName(creditAppraiselCollateralDetails.getRegistrationAuthority());
		assetsDetailsRequestResource.setPledgeTypeId(creditAppraiselCollateralDetails.getPledgeTypeId());
		assetsDetailsRequestResource.setPledgeTypeName(creditAppraiselCollateralDetails.getPledgeTypeName());
		assetsDetailsRequestResource.setName(creditAppraiselCollateralDetails.getName());
		assetsDetailsRequestResource.setDescription(creditAppraiselCollateralDetails.getDescription());
		assetsDetailsRequestResource.setSupplierId(creditAppraiselCollateralDetails.getSupplierId());
		assetsDetailsRequestResource
				.setSupplierReferenceCode(creditAppraiselCollateralDetails.getSupplierReferenceCode());
		assetsDetailsRequestResource.setInvoiceNo(creditAppraiselCollateralDetails.getInvoiceNo());
		assetsDetailsRequestResource.setStatus("ACTIVE");

		if (creditAppraiselCollateralDetails.getDocumentUploadDetails() != null) {
			for (CreAppCollateralDocumentDetailsResource assetDetailsDocuments : creditAppraiselCollateralDetails
					.getDocumentUploadDetails()) {
				AssetDetailsDocumentsUploadRequestResource assetDetailsDocumentsUpload = new AssetDetailsDocumentsUploadRequestResource();
				assetDetailsDocumentsUpload.setUploadId(assetDetailsDocuments.getDocumentId());
				assetDetailsDocumentsUpload.setStatus("ACTIVE");
				assetDetailsDocumentsUpload.setOrigin("MICRO_BPR");
				assetDetailsDocumentsUploadList.add(assetDetailsDocumentsUpload);
			}

			assetsDocumentsUpload.setDocumentUploadDetails(assetDetailsDocumentsUploadList);
		} else {
			AssetDetailsDocumentsUploadRequestResource assetDetailsDocumentsUpload = new AssetDetailsDocumentsUploadRequestResource();

			assetDetailsDocumentsUpload.setStatus("ACTIVE");
			assetDetailsDocumentsUploadList.add(assetDetailsDocumentsUpload);
			assetsDocumentsUpload.setDocumentUploadDetails(assetDetailsDocumentsUploadList);
		}

		assetsDetailsRequestResource.setAssetsDocumentsUpload(assetsDocumentsUpload);
		return assetsDetailsRequestResource;
	}

	private void updateCreditAppCollateralDetails(String tenantId, String createdUser, Long collateralRefNo, Long id) {
		CreditAppCollateralDetail creditAppCollateralDetail = creditAppCollateralDetailsRepository.getOne(id);
		creditAppCollateralDetail.setAssetEntityId(collateralRefNo);
		creditAppCollateralDetail.setModifiedUser(createdUser);
		creditAppCollateralDetail.setModifiedDate(getCreateOrModifyDate());
		creditAppCollateralDetail.setSyncTs(getCreateOrModifyDate());
		creditAppCollateralDetailsRepository.saveAndFlush(creditAppCollateralDetail);

	}

	private void validateCollateralDocUpload(String tenantId, String createdUser,
			CreditAppCollateralDetail creditAppCollateralDetail,
			List<CreAppCollateralDocumentDetailsResource> collateralDocumentDetails, ActionType save) {
		Integer index = 0;
		for (CreAppCollateralDocumentDetailsResource creAppCollateralDocumentDetailsResource : collateralDocumentDetails) {

			LoggerRequest.getInstance().logInfo(
					"CollateralDocumentDetails********************************Validate Document Id***************************");
			validateDocumentId(tenantId, creAppCollateralDocumentDetailsResource.getDocumentId(), index,
					ActionType.SAVE);

			LoggerRequest.getInstance().logInfo(
					"CollateralDocumentDetails********************************Save Collateral Documents***********************");
			saveCreAppColDocumenrDetails(tenantId, createdUser, creditAppCollateralDetail,
					creAppCollateralDocumentDetailsResource);

			index++;
		}

	}

	private void validateCollateralDocUploadUpdate(String tenantId, String createdUser,
			CreditAppCollateralDetail creditAppCollateralDetail,
			List<CreAppCollateralDocumentDetailsResource> collateralDocumentDetails, ActionType save) {
		Integer index = 0;
		for (CreAppCollateralDocumentDetailsResource creAppCollateralDocumentDetailsResource : collateralDocumentDetails) {
			if (creAppCollateralDocumentDetailsResource.getCreditAppraiselCollateralDetId() == null) {
				LoggerRequest.getInstance().logInfo(
						"CollateralDocumentDetails********************************Validate Document Id***************************");
				validateDocumentId(tenantId, creAppCollateralDocumentDetailsResource.getDocumentId(), index,
						ActionType.SAVE);

				LoggerRequest.getInstance().logInfo(
						"CollateralDocumentDetails********************************Save Collateral Documents***********************");
				saveCreAppColDocumenrDetails(tenantId, createdUser, creditAppCollateralDetail,
						creAppCollateralDocumentDetailsResource);
			} else {

				LoggerRequest.getInstance().logInfo(
						"CollateralDocumentDetails********************************Validate Document Id***************************");
				validateDocumentId(tenantId, creAppCollateralDocumentDetailsResource.getDocumentId(), index,
						ActionType.UPDATE);

				LoggerRequest.getInstance().logInfo(
						"CollateralDocumentDetails********************************Save Collateral Documents***********************");
				updateCreAppColDocumenrDetails(tenantId, createdUser, creditAppCollateralDetail,
						creAppCollateralDocumentDetailsResource);

			}
			index++;
		}

	}

	private void saveCreAppColDocumenrDetails(String tenantId, String createdUser,
			CreditAppCollateralDetail creditAppCollateralDetail,
			CreAppCollateralDocumentDetailsResource creAppCollateralDocumentDetailsResource) {
		CreditAppCollateralDocuments creditAppCollateralDocuments = new CreditAppCollateralDocuments();
		creditAppCollateralDocuments.setTenantId(tenantId);
		creditAppCollateralDocuments.setCreditAppCollateralDetail(creditAppCollateralDetail);
		creditAppCollateralDocuments
				.setDocumentId(Long.parseLong(creAppCollateralDocumentDetailsResource.getDocumentId()));
		creditAppCollateralDocuments.setDocumentName(creAppCollateralDocumentDetailsResource.getDocumentName());
		creditAppCollateralDocuments.setStatus(creAppCollateralDocumentDetailsResource.getStatus());
		creditAppCollateralDocuments.setCreatedUser(createdUser);
		creditAppCollateralDocuments.setCreatedDate(getCreateOrModifyDate());
		creditAppCollateralDocuments.setSyncTs(getCreateOrModifyDate());
		creditAppCollateralDocuments = creditAppCollateralDocumentsRepository
				.saveAndFlush(creditAppCollateralDocuments);

	}

	private void updateCreAppColDocumenrDetails(String tenantId, String createdUser,
			CreditAppCollateralDetail creditAppCollateralDetail,
			CreAppCollateralDocumentDetailsResource creAppCollateralDocumentDetailsResource) {

		CreditAppCollateralDocuments creditAppCollateralDocumentsOld = creditAppCollateralDocumentsRepository
				.getOne(Long.parseLong(creAppCollateralDocumentDetailsResource.getCreditAppraiselCollateralDetId()));

//		LoggerRequest.getInstance().logInfo("ApprovalCategory********************************Validate Version*********************************************");
//		if(!creditAppCollateralDocumentsOld.getVersion().equals(Long.parseLong(creditAppraiselCollateralDetailsUpdateResource.getVersion())))
//			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");

		creditAppCollateralDocumentsOld.setTenantId(tenantId);
		creditAppCollateralDocumentsOld.setCreditAppCollateralDetail(creditAppCollateralDetail);
		creditAppCollateralDocumentsOld
				.setDocumentId(Long.parseLong(creAppCollateralDocumentDetailsResource.getDocumentId()));
		creditAppCollateralDocumentsOld.setDocumentName(creAppCollateralDocumentDetailsResource.getDocumentName());
		creditAppCollateralDocumentsOld.setStatus(creAppCollateralDocumentDetailsResource.getStatus());
		creditAppCollateralDocumentsOld.setModifiedUser(createdUser);
		creditAppCollateralDocumentsOld.setModifiedDate(getCreateOrModifyDate());
		creditAppCollateralDocumentsOld.setSyncTs(getCreateOrModifyDate());
		creditAppCollateralDocumentsRepository.saveAndFlush(creditAppCollateralDocumentsOld);

	}

	private void validateDocumentId(String tenantId, String documentId, Integer index, ActionType actionType) {
		ExistsDocumentResponseResource existsDocumentResponseResource = remoteService.existDocument(tenantId,
				documentId, Constants.ORIGIN_MICRO_BPR);
		if (actionType.equals(ActionType.SAVE)) {
			if (existsDocumentResponseResource == null || existsDocumentResponseResource.getServiceStatus() == null) {
				if (existsDocumentResponseResource == null || !existsDocumentResponseResource.getValue())
					throw new InvalidDetailListServiceIdException(environment.getProperty(COMMON_NOT_MATCH),
							ServiceEntity.DOCUMENT_ID, TransferType.COLLLATERAL_DOC_DETAILS, index);
			} else {
				remoteService.serviceValidationListExceptionHadle(existsDocumentResponseResource.getServiceStatus(),
						ServiceEntity.DOCUMENT_ID, ServicePoint.CREDIT_APP_COLL_DOC_DETAILS, index);
			}
		} else if (actionType.equals(ActionType.UPDATE)) {
			if (existsDocumentResponseResource == null || existsDocumentResponseResource.getServiceStatus() == null) {
				if (existsDocumentResponseResource == null || !existsDocumentResponseResource.getValue())
					throw new InvalidDetailListServiceIdException(environment.getProperty(COMMON_NOT_MATCH),
							ServiceEntity.DOCUMENT_ID, TransferType.COLLLATERAL_DOC_DETAILS, index);
			} else {
				remoteService.serviceValidationListExceptionHadle(existsDocumentResponseResource.getServiceStatus(),
						ServiceEntity.DOCUMENT_ID, ServicePoint.CREDIT_APP_COLL_DOC_DETAILS, index);
			}
		}

	}

	@Override
	public Long updateAndValidateCreditAppCollateralDetails(String tenantId, String createdUser, Long id,
			CreditAppraiselCollateralDetailsUpdateResource creditAppraiselCollateralDetailsUpdateResource) {

		CreditAppCollateralDetail creditAppCollateralDetailOld = creditAppCollateralDetailsRepository.getOne(id);

		LoggerRequest.getInstance().logInfo(
				"ApprovalCategory********************************Validate Version*********************************************");
		if (!creditAppCollateralDetailOld.getVersion()
				.equals(Long.parseLong(creditAppraiselCollateralDetailsUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");

		if (creditAppCollateralDetailOld.getAssetEntityId() != null) {
			creditAppraiselCollateralDetailsUpdateResource
					.setAssetDetailRefCode(creditAppCollateralDetailOld.getAssetEntityId());
			LoggerRequest.getInstance().logInfo(
					"credit-appraisal-collateral-details********************************Validate collateral details****************");
			validateService.validateCollateraldetailsForUpdate(tenantId,
					creditAppraiselCollateralDetailsUpdateResource);

		} else {
			throw new DetailValidateException(environment.getProperty(INVALID_ASSET_DETAIL_REF),
					ServiceEntity.ASSETS_DETAIL_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
		}

		creditAppCollateralDetailOld
				.setAssetTypeId(Long.parseLong(creditAppraiselCollateralDetailsUpdateResource.getAssetTypeId()));
		creditAppCollateralDetailOld
				.setAssetSubTypeId(Long.parseLong(creditAppraiselCollateralDetailsUpdateResource.getAssetSubTypeId()));
		if (creditAppraiselCollateralDetailsUpdateResource.getAssetPartId() != null
				&& !creditAppraiselCollateralDetailsUpdateResource.getAssetPartId().isEmpty())
			creditAppCollateralDetailOld
					.setAssetPartId(Long.parseLong(creditAppraiselCollateralDetailsUpdateResource.getAssetPartId()));
		if (creditAppraiselCollateralDetailsUpdateResource.getSubTypeId() != null
				&& !creditAppraiselCollateralDetailsUpdateResource.getSubTypeId().isEmpty())
			creditAppCollateralDetailOld
					.setSubTypeId(Long.parseLong(creditAppraiselCollateralDetailsUpdateResource.getSubTypeId()));
		if (creditAppraiselCollateralDetailsUpdateResource.getPledgeTypeId() != null
				&& !creditAppraiselCollateralDetailsUpdateResource.getPledgeTypeId().isEmpty())
			creditAppCollateralDetailOld.setOwnershipTypeId(
					Long.parseLong(creditAppraiselCollateralDetailsUpdateResource.getPledgeTypeId()));
		if (creditAppraiselCollateralDetailsUpdateResource.getInvoiceNo() != null
				&& !creditAppraiselCollateralDetailsUpdateResource.getInvoiceNo().isEmpty())
			creditAppCollateralDetailOld.setInvoiceNo(creditAppraiselCollateralDetailsUpdateResource.getInvoiceNo());
		creditAppCollateralDetailOld.setName(creditAppraiselCollateralDetailsUpdateResource.getName());
		// if(creditAppraiselCollateralDetailsUpdateResource.getDescription() != null &&
		// !creditAppraiselCollateralDetailsUpdateResource.getDescription().isEmpty())
		creditAppCollateralDetailOld.setDescription(creditAppraiselCollateralDetailsUpdateResource.getDescription());
		if (creditAppraiselCollateralDetailsUpdateResource.getSupplierId() != null
				&& !creditAppraiselCollateralDetailsUpdateResource.getSupplierId().isEmpty())
			creditAppCollateralDetailOld
					.setSupplierId(Long.parseLong(creditAppraiselCollateralDetailsUpdateResource.getSupplierId()));
		if (creditAppraiselCollateralDetailsUpdateResource.getRegistrationAuthorityId() != null
				&& !creditAppraiselCollateralDetailsUpdateResource.getRegistrationAuthorityId().isEmpty())
			creditAppCollateralDetailOld.setRegistrationAuthorityId(
					Long.parseLong(creditAppraiselCollateralDetailsUpdateResource.getRegistrationAuthorityId()));
		creditAppCollateralDetailOld
				.setStatus(CommonStatus.valueOf(creditAppraiselCollateralDetailsUpdateResource.getStatus()));
		creditAppCollateralDetailOld.setModifiedUser(createdUser);
		creditAppCollateralDetailOld.setModifiedDate(getCreateOrModifyDate());
		creditAppCollateralDetailOld.setSyncTs(getCreateOrModifyDate());
		creditAppCollateralDetailOld = creditAppCollateralDetailsRepository.saveAndFlush(creditAppCollateralDetailOld);

		creditAppraiselCollateralDetailsUpdateResource.setCollateralType(CollateralType.EXISTING.toString());
		LoggerRequest.getInstance().logInfo(
				"credit-appraisal-collateral-details********************************Validate image upload****************");
		if (creditAppraiselCollateralDetailsUpdateResource.getDocumentUploadDetails() != null
				&& creditAppraiselCollateralDetailsUpdateResource.getDocumentUploadDetails().isEmpty()) {
			validateCollateralDocUploadUpdate(tenantId, createdUser, creditAppCollateralDetailOld,
					creditAppraiselCollateralDetailsUpdateResource.getDocumentUploadDetails(), ActionType.SAVE);
		}
		LoggerRequest.getInstance().logInfo(
				"credit-appraisal-collateral-details********************************Integrate with collateral****************");
		AssetsDetailsRequestResource assetsDetailsRequestResource = setAssetsDetailsRequest(
				creditAppraiselCollateralDetailsUpdateResource, creditAppCollateralDetailOld.getCustomer());
		Long assetEntityId = validateService.validateAndIntergrateWithCollateral(tenantId, createdUser,
				assetsDetailsRequestResource, creditAppCollateralDetailOld.getId());
		return creditAppCollateralDetailOld.getId();

	}

	@Override
	public void intergrateAndSaveNewColateralDetails(String tenantId, String userName,
			@Valid CreAppCollateralIntergrateResource creAppCollateralIntergrateResource) {
		// TODO Auto-generated method stub

	}

	@Override
	public CreditAppCollateralDetail addCreditAppCollateralDetail(String tenantId,
			CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetailsAddResource) {

		CreditAppCollateralDetail creditAppCollateralDetail = new CreditAppCollateralDetail();

		Optional<Customer> cutomerExists = customerRepository.findByIdAndStatus(
				Long.parseLong(creditAppraiselCollateralDetailsAddResource.getCustomerId()),
				CommonStatus.ACTIVE.toString());
		if (cutomerExists.isPresent()) {
			if (!cutomerExists.get().getFullName()
					.equals(creditAppraiselCollateralDetailsAddResource.getCustomerName()))
				throw new DetailValidateException(environment.getProperty("invalid.customer"),
						ServiceEntity.CUSTOMER_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);

			if (creditAppraiselCollateralDetailsAddResource.getComnCustomerId() != null
					&& !creditAppraiselCollateralDetailsAddResource.getComnCustomerId().isEmpty()) {
				if (!cutomerExists.get().getCustomerId()
						.equals(Long.parseLong(creditAppraiselCollateralDetailsAddResource.getComnCustomerId()))
						|| !cutomerExists.get().getCusReferenceCode().equalsIgnoreCase(
								creditAppraiselCollateralDetailsAddResource.getComnCustomerReference()))
					throw new DetailValidateException(environment.getProperty("invalid.comn.cust"),
							ServiceEntity.COMN_CUST_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);

			}
//			else if(creditAppraiselCollateralDetails.getPendingCustomerId()!=null && !creditAppraiselCollateralDetails.getPendingCustomerId().isEmpty()) {
//				if(!cutomerExists.get().getPendingCustomerId().equals(Long.parseLong(creditAppraiselCollateralDetails.getPendingCustomerId())))
//					throw new DetailValidateException(environment.getProperty("invalid.pending.cust"), ServiceEntity.PEN_CUSTOMER_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
//			}

		} else {
			throw new DetailValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.CUSTOMER_ID,
					ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
		}

		if (creditAppraiselCollateralDetailsAddResource.getCollateralType() != null
				&& !creditAppraiselCollateralDetailsAddResource.getCollateralType().isEmpty()) {
			if (creditAppraiselCollateralDetailsAddResource.getCollateralType().equalsIgnoreCase("EXISTING")) {
				if (creditAppraiselCollateralDetailsAddResource.getAssetDetailId() != null
						&& !creditAppraiselCollateralDetailsAddResource.getAssetDetailId().isEmpty()) {

					if (creditAppraiselCollateralDetailsAddResource.getAssetEntityId() == null
							|| creditAppraiselCollateralDetailsAddResource.getAssetEntityId().isEmpty())
						throw new DetailValidateException(environment.getProperty(INVALID_ASSET_ENTITY_ID),
								ServiceEntity.ASSETS_ENTITY_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);

					LoggerRequest.getInstance().logInfo(
							"credit-appraisal-collateral-details********************************Validate collateral details****************");
					validateService.validateExistingCollateraldetails(tenantId,
							creditAppraiselCollateralDetailsAddResource);

				} else {
					throw new DetailValidateException(environment.getProperty(INVALID_ASSET_DETAIL_ID),
							ServiceEntity.ASSETS_DETAIL_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
				}

				creditAppCollateralDetail.setCollateralType(
						CollateralType.valueOf(creditAppraiselCollateralDetailsAddResource.getCollateralType()));

			} else if (creditAppraiselCollateralDetailsAddResource.getCollateralType().equalsIgnoreCase("NEW")) {

				LoggerRequest.getInstance().logInfo(
						"credit-appraisal-collateral-details********************************Validate asset type****************");
				validateService.validateAssetType(tenantId,
						Long.parseLong(creditAppraiselCollateralDetailsAddResource.getAssetTypeId()),
						creditAppraiselCollateralDetailsAddResource.getAssetTypeName());

				AssetSubTypeResponseResource assetSubTypeResponseResource = validateService.validateAssetSubType(
						tenantId, Long.parseLong(creditAppraiselCollateralDetailsAddResource.getAssetSubTypeId()),
						creditAppraiselCollateralDetailsAddResource.getAssetSubTypeName());

//				if (!assetSubTypeResponseResource.getAssetTypeId().toString()
//						.equals(creditAppraiselCollateralDetailsAddResource.getAssetTypeId()))
//					throw new DetailValidateException(environment.getProperty("common.invalid"),
//							ServiceEntity.ASSET_SUB_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);

				// check for asset parts by asset sub type id.
				if (remoteService.checkIsExistPartsForAssetSubType(tenantId,
						creditAppraiselCollateralDetailsAddResource.getAssetSubTypeId(), urlAssetPartByAssetSubType)) {
					if (creditAppraiselCollateralDetailsAddResource.getAssetPartId().isEmpty()
							|| creditAppraiselCollateralDetailsAddResource.getAssetPartId() == null)
						throw new DetailValidateException(environment.getProperty("common.required"),
								ServiceEntity.ASSETS_PART_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);

				} else {
					if (!creditAppraiselCollateralDetailsAddResource.getAssetPartId().isEmpty()
							|| creditAppraiselCollateralDetailsAddResource.getAssetPartId() != null
							|| creditAppraiselCollateralDetailsAddResource.getAssetPartName() != null
							|| !creditAppraiselCollateralDetailsAddResource.getAssetPartName().isEmpty())
						throw new DetailValidateException(environment.getProperty("no.mapped.parts"),
								ServiceEntity.ASSETS_PART_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
				}

				if (!creditAppraiselCollateralDetailsAddResource.getAssetPartId().isEmpty()
						&& creditAppraiselCollateralDetailsAddResource.getAssetPartId() != null) {
					if (creditAppraiselCollateralDetailsAddResource.getAssetPartName() == null
							|| creditAppraiselCollateralDetailsAddResource.getAssetPartName().isEmpty())
						throw new DetailValidateException(environment.getProperty("common.required"),
								ServiceEntity.ASSETS_PART_NAME, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);

					validateService.validateAssetSubTypeParts(tenantId,
							Long.parseLong(creditAppraiselCollateralDetailsAddResource.getAssetSubTypeId()),
							Long.parseLong(creditAppraiselCollateralDetailsAddResource.getAssetPartId()),
							creditAppraiselCollateralDetailsAddResource.getAssetPartName());
				}

				if (creditAppraiselCollateralDetailsAddResource.getSubTypeId() != null
						&& !creditAppraiselCollateralDetailsAddResource.getSubTypeId().isEmpty()) {
					if (creditAppraiselCollateralDetailsAddResource.getSubTypeName() == null
							|| creditAppraiselCollateralDetailsAddResource.getSubTypeName().isEmpty())
						throw new DetailValidateException(environment.getProperty("common.required"),
								ServiceEntity.SUB_TYPE_NAME, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
					validateService.validateAssetTypeSubType(tenantId, creditAppraiselCollateralDetailsAddResource);
				}

				// validate sypplier
				if (creditAppraiselCollateralDetailsAddResource.getSupplierId() != null
						&& !creditAppraiselCollateralDetailsAddResource.getSupplierId().isEmpty()) {
					if (creditAppraiselCollateralDetailsAddResource.getSupplierReferenceCode() == null
							|| creditAppraiselCollateralDetailsAddResource.getSupplierReferenceCode().isEmpty())
						throw new DetailValidateException(environment.getProperty("common.required"),
								ServiceEntity.SUUPLIER_REF_CODE, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
					validateService.validateComnSuppliesEntities(tenantId,
							Long.parseLong(creditAppraiselCollateralDetailsAddResource.getSupplierId()),
							creditAppraiselCollateralDetailsAddResource.getSupplierReferenceCode());
				}

				// validate registration Authority
				if (creditAppraiselCollateralDetailsAddResource.getRegistrationAuthorityId() != null
						&& !creditAppraiselCollateralDetailsAddResource.getRegistrationAuthorityId().isEmpty()) {
					if (creditAppraiselCollateralDetailsAddResource.getRegistrationAuthority() == null
							|| creditAppraiselCollateralDetailsAddResource.getRegistrationAuthority().isEmpty())
						throw new DetailValidateException(environment.getProperty("common.required"),
								ServiceEntity.REG_AUTHORITY, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
					validateService.validateRegistrationAuthority(tenantId,
							Long.parseLong(creditAppraiselCollateralDetailsAddResource.getRegistrationAuthorityId()),
							creditAppraiselCollateralDetailsAddResource.getRegistrationAuthority());
				}

				// validate pledge details
				if (creditAppraiselCollateralDetailsAddResource.getPledgeTypeId() != null
						&& !creditAppraiselCollateralDetailsAddResource.getPledgeTypeId().isEmpty()) {
					if (creditAppraiselCollateralDetailsAddResource.getPledgeTypeName() == null
							|| creditAppraiselCollateralDetailsAddResource.getPledgeTypeName().isEmpty())
						throw new DetailValidateException(environment.getProperty("common.required"),
								ServiceEntity.PLEDGE_TYPE_NAME, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
					validateService.validatePledgeType(tenantId,
							Long.parseLong(creditAppraiselCollateralDetailsAddResource.getAssetTypeId()),
							Long.parseLong(creditAppraiselCollateralDetailsAddResource.getPledgeTypeId()),
							creditAppraiselCollateralDetailsAddResource.getPledgeTypeName());
				}

				creditAppCollateralDetail.setCollateralType(
						CollateralType.valueOf(creditAppraiselCollateralDetailsAddResource.getCollateralType()));

			}
		}

		creditAppCollateralDetail.setTenantId(tenantId);
		creditAppCollateralDetail.setCustomer(cutomerExists.get());
		creditAppCollateralDetail
				.setAssetEntityId(Long.parseLong(creditAppraiselCollateralDetailsAddResource.getAssetEntityId()));
		creditAppCollateralDetail.setCollateralType(
				CollateralType.valueOf(creditAppraiselCollateralDetailsAddResource.getCollateralType()));
		creditAppCollateralDetail
				.setAssetTypeId(Long.parseLong(creditAppraiselCollateralDetailsAddResource.getAssetTypeId()));
		creditAppCollateralDetail
				.setAssetSubTypeId(Long.parseLong(creditAppraiselCollateralDetailsAddResource.getAssetSubTypeId()));
		if (creditAppraiselCollateralDetailsAddResource.getAssetPartId() != null
				&& !creditAppraiselCollateralDetailsAddResource.getAssetPartId().isEmpty())
			creditAppCollateralDetail
					.setAssetPartId(Long.parseLong(creditAppraiselCollateralDetailsAddResource.getAssetPartId()));
		if (creditAppraiselCollateralDetailsAddResource.getSubTypeId() != null
				&& !creditAppraiselCollateralDetailsAddResource.getSubTypeId().isEmpty())
			creditAppCollateralDetail
					.setSubTypeId(Long.parseLong(creditAppraiselCollateralDetailsAddResource.getSubTypeId()));
		if (creditAppraiselCollateralDetailsAddResource.getPledgeTypeId() != null
				&& !creditAppraiselCollateralDetailsAddResource.getPledgeTypeId().isEmpty())
			creditAppCollateralDetail
					.setOwnershipTypeId(Long.parseLong(creditAppraiselCollateralDetailsAddResource.getPledgeTypeId()));
		if (creditAppraiselCollateralDetailsAddResource.getInvoiceNo() != null
				&& !creditAppraiselCollateralDetailsAddResource.getInvoiceNo().isEmpty())
			creditAppCollateralDetail.setInvoiceNo(creditAppraiselCollateralDetailsAddResource.getInvoiceNo());
		creditAppCollateralDetail.setName(creditAppraiselCollateralDetailsAddResource.getName());
		if (creditAppraiselCollateralDetailsAddResource.getDescription() != null
				&& !creditAppraiselCollateralDetailsAddResource.getDescription().isEmpty())
			creditAppCollateralDetail.setDescription(creditAppraiselCollateralDetailsAddResource.getDescription());
		if (creditAppraiselCollateralDetailsAddResource.getSupplierId() != null
				&& !creditAppraiselCollateralDetailsAddResource.getSupplierId().isEmpty())
			creditAppCollateralDetail
					.setSupplierId(Long.parseLong(creditAppraiselCollateralDetailsAddResource.getSupplierId()));
		if (creditAppraiselCollateralDetailsAddResource.getRegistrationAuthorityId() != null
				&& !creditAppraiselCollateralDetailsAddResource.getRegistrationAuthorityId().isEmpty())
			creditAppCollateralDetail.setRegistrationAuthorityId(
					Long.parseLong(creditAppraiselCollateralDetailsAddResource.getRegistrationAuthorityId()));
		creditAppCollateralDetail
				.setStatus(CommonStatus.valueOf(creditAppraiselCollateralDetailsAddResource.getStatus()));
		creditAppCollateralDetail.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		creditAppCollateralDetail.setCreatedDate(getCreateOrModifyDate());
		creditAppCollateralDetail.setSyncTs(getCreateOrModifyDate());
		creditAppCollateralDetail = creditAppCollateralDetailsRepository.saveAndFlush(creditAppCollateralDetail);

		for (CreAppCollateralDocumentDetailsResource creAppCollateralDocumentDetailsResource : creditAppraiselCollateralDetailsAddResource
				.getDocumentUploadDetails()) {
			Integer index = 0;
			validateService.validateDocument(tenantId, creAppCollateralDocumentDetailsResource.getDocumentId(),
					creAppCollateralDocumentDetailsResource.getDocumentName(),
					ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS, Constants.ORIGIN_MICRO_BPR, index);

			Optional<CreditAppCollateralDocuments> isCreditAppCollateralDocuments = creditAppCollateralDocumentsRepository
					.findByCreditAppCollateralDetailIdAndDocumentIdAndStatus(creditAppCollateralDetail.getId(),
							validateService.stringToLong(creAppCollateralDocumentDetailsResource.getDocumentId()),
							CommonStatus.ACTIVE);
			if (isCreditAppCollateralDocuments.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "documentId");
			}

			CreditAppCollateralDocuments creditAppCollateralDocuments = new CreditAppCollateralDocuments();
			creditAppCollateralDocuments.setTenantId(tenantId);
			creditAppCollateralDocuments.setCreditAppCollateralDetail(creditAppCollateralDetail);
			creditAppCollateralDocuments
					.setDocumentId(Long.parseLong(creAppCollateralDocumentDetailsResource.getDocumentId()));
			creditAppCollateralDocuments.setDocumentName(creAppCollateralDocumentDetailsResource.getDocumentName());
			creditAppCollateralDocuments.setStatus(creAppCollateralDocumentDetailsResource.getStatus());
			creditAppCollateralDocuments.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			creditAppCollateralDocuments.setCreatedDate(getCreateOrModifyDate());
			creditAppCollateralDocuments.setSyncTs(getCreateOrModifyDate());
			creditAppCollateralDocumentsRepository.save(creditAppCollateralDocuments);
			index++;
		}

		return creditAppCollateralDetail;
	}

}