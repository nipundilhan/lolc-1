package com.fusionx.lending.origination.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Remote Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-07-2020   FX-3963       FX-4080    SahanAm     Created
 *    
 ********************************************************************************************************
 */

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.enums.CommonListReferenceCode;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.ServiceStatus;
import com.fusionx.lending.origination.enums.WorkflowType;
import com.fusionx.lending.origination.resource.AssetEntityResponseResource;
import com.fusionx.lending.origination.resource.AssetTypePledgeTypeMappingResponce;
import com.fusionx.lending.origination.resource.AssetsDetailsRequestResource;
import com.fusionx.lending.origination.resource.ColInetragationAssetsDetailsRequestResource;
import com.fusionx.lending.origination.resource.ColInetragationRequestResponceResource;
import com.fusionx.lending.origination.resource.ContactValidateResource;
import com.fusionx.lending.origination.resource.CustomerSubTypeNonIndividual;
import com.fusionx.lending.origination.resource.ExistsDocumentResponseResource;
import com.fusionx.lending.origination.resource.ExternalCribRulesResource;
import com.fusionx.lending.origination.resource.IdentificationValidateResource;
import com.fusionx.lending.origination.resource.InsuranceDetailsAddResource;
import com.fusionx.lending.origination.resource.InsuranceDetailsResponseResource;
import com.fusionx.lending.origination.resource.LastValuationDateResponseResource;
import com.fusionx.lending.origination.resource.PerCommonList;
import com.fusionx.lending.origination.resource.PersonResponceValidateResource;
import com.fusionx.lending.origination.resource.ValuationAndInsuranceDetListResponseResource;
import com.fusionx.lending.origination.resource.ValuationDetailsAddResource;
import com.fusionx.lending.origination.resource.ValuationDetailsListAddResource;
import com.fusionx.lending.origination.resource.ValuationDetailsUpdateResource;


@Service
public interface RemoteService {
	
	public Object checkIsExist(String tenantId, String id, String serviceUrl, Class<?> classObject);

	public Long stringToLong(String value);
	
	public Date formatDate(String date);
	
	public Timestamp getCreateOrModifyDate();
	
	public void futureDateValidation(String date, ServiceEntity serviceEntity, ServicePoint servicePoint);
	
	public ExternalCribRulesResource invokeExternalCribRule(WorkflowType workflowType, String domainPath, String domainName,String tenantId);

	public Boolean checkIsExistPartsForAssetSubType(String tenantId, String id, String serviceUrl);
    
	public Object checkIsExistPartsByAssetSubType(String tenantId, String id, String serviceUrl, Class<?> classObject);
	
	public Object checkIsExistAssetsDetail(String tenantId, String id, String serviceUrl, Class<?> classObject);

	void serviceValidationExceptionHadle(ServiceStatus serviceStatus, ServiceEntity serviceEntity,
			ServicePoint servicePoint);

	Object checkIsExistAssetTypeSubType(String tenantId, String assetTypeId, String subTypeId, String serviceUrl,
			Class<?> classObject);
	
	public ExistsDocumentResponseResource existDocument(String tenantId, String documentId, String origin);

	void serviceValidationListExceptionHadle(ServiceStatus serviceStatus, ServiceEntity serviceEntity,
			ServicePoint servicePoint, Integer index);

	public ColInetragationAssetsDetailsRequestResource integrateWithCollateral(String tenantId,String createdUser,
			AssetsDetailsRequestResource assetsDetailsRequestResource,
			Class<ColInetragationAssetsDetailsRequestResource> class1);

	Boolean existsPersonContact(String tenantId, Long personId, Long pconId);

	Boolean existsPersonAddress(String tenantId, Long personId, Long paddId);

	Boolean existsPendingPersonIdentification(String tenantId, Long ppidtId);

	Boolean existsPersonIdentification(String tenantId, Long personId, Long pidtId);

	Boolean validatePersonIdentification(String tenantId,
			IdentificationValidateResource identificationValidateResource);

	public Boolean validatePersonContact(String tenantId, ContactValidateResource contactValidateResource);

	//public Object checkIsExistAssetTypePledgeTypeMap(String tenantId, String id, String serviceUrl, Class<?> classObject);


	public AssetTypePledgeTypeMappingResponce checkIsExistAssetTypePledgeTypeMap(String tenantId, Long assetTypeId, String serviceUrl,
			Class<?> classObject);

	public Object checkIsExistAssetDetails(String id, String serviceUrl, Class<?> classObject);


	public Object validateRelationRelationshipType(String tenantId, String relationCode,
			CommonListReferenceCode relationshiptype, ServicePoint externalCrib, Class<?> classObject);


	public LastValuationDateResponseResource checkLastValuationDate(String tenantId, Long assetEntityId,
			String urlAssetValuationDetail, Class<?> classObject);

	public ColInetragationRequestResponceResource valuationUpdateIntegratewithCol(String tenantId, String createdUser,
			ValuationDetailsUpdateResource valuationDetails, Class<ColInetragationRequestResponceResource> class1);

	public ColInetragationRequestResponceResource insuranceAddIntegratewithCol(String tenantId, String createdUser,
			InsuranceDetailsAddResource insuranceDetails, Class<ColInetragationRequestResponceResource> class1);

	public InsuranceDetailsResponseResource checkIsExistInsuDetails(String tenantId, String string, String urlAssetEntity,
			Class<?> classObject);

	public ValuationAndInsuranceDetListResponseResource checkIsExitingAssetValuations(String tenantId, Long assetEntityId,
			String urlColValuationDetails, Class<?> classObject);

	public ValuationAndInsuranceDetListResponseResource checkIsExitingAssetInsurance(String tenantId,
			Long assetEntityId, String urlColValuationDetails,
			Class<?> classObject);

	public ColInetragationRequestResponceResource valuationsIntegratewithCol(String tenantId, String createdUser,
			ValuationDetailsListAddResource valuationDetailsAddResourceList, Long assetEntityId,
			Class<ColInetragationRequestResponceResource> class1);

	public Object checkIsExistInsuAsset(String tenantId, String string, String urlAssetEntity,
			Class<?> classObject);

	public ColInetragationRequestResponceResource insuranceUPdateIntegratewithCol(String tenantId, String createdUser,
			InsuranceDetailsAddResource insuranceDetails, Class<ColInetragationRequestResponceResource> class1);

	public PerCommonList isExistPerCommonList(String tenantId, String genderComListId, CommonListReferenceCode gender,
			String urlPerson, Class<PerCommonList> class1);

	public Boolean validatePersonIdentificationType(String tenantId, Long personId, Long penPersonId, Long pidtId,
			Long ppidtId, Long stringToLong);

	public Boolean validatePersonIdentificationNumber(String tenantId, Long parseLong, Long parseLong2, Long parseLong3,
			String identificationNo);

	public List<PerCommonList> getPerCommonListByType(String tenantId,  String type ,Class<?> class1);

}
