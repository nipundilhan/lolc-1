package com.fusionx.lending.origination.service.impl;
  
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.DefaultRequestHeaders;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.enums.ActionType;
import com.fusionx.lending.origination.enums.CommonListReferenceCode;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.ServiceStatus;
import com.fusionx.lending.origination.enums.WorkflowType;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.DetailValidateException;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.OtherException;
import com.fusionx.lending.origination.exception.PersonServiceException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.exception.WorkFlowException;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.resource.*;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * Schedule Validation Service Impl
 * @author     SenithaP
 * @Date     18-02-2020
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-11-2019   FX-3037       FX-2962    SenithaP      Created
 *    
 ********************************************************************************************************
 */

@Component

public class ValidateServiceImpl extends MessagePropertyBase implements ValidateService {

	private String serviceStatus= "{\"serviceStatus\" : \"";

	@Autowired
	private Environment environment;
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Value("${pam.username}") 
    private String usernameBPM;

    @Value("${pam.password}")
    private String passwordBPM;
    
    @Value("${pam-cert-location}")
    private String pamCertLocation;
    
    @Value("${micro-bpr-rule-container}")
	private String urlMicroBprRuleContainer;
    
    @Value("${applicant-blacklist-bpm}")
    private String appBlacklistBmp;
    
    @Value("${applicant-blacklist-approval-bpm}")
    private String appBlacklistApprovalBmp;
    
	@Value("${col-collateral-asset-detail}")
    private String urlAssetDetail;
	
	@Value("${col-collateral-asset-entity}")
    private String urlAssetEntity;
	
	@Value("${col-collateral-asset-type}")
	private String assetTypeUrl;

	@Value("${col-collateral-asset-sub-type}")
	private String assetSubTypeUrl;
	
	@Value("${col-collateral-pledge}")
    private String urlPledgeDetail;
	
	@Value("${col-collateral-asset-type-pledge-type}")
    private String urlAssetTypePledgeType;

	@Value("${col-collateral-asset-type-sub-type}")
	private String assetTypeSubTypeUrl;
	
	@Value("${col-collateral-part-asset-sub-type}")
	private String urlAssetPartByAssetSubType;
	
	@Value("${comn-supplies-entities}")
	private String urlComnSuppliesEntities;
	
	@Value("${col-collateral-reg-authpority}")
	private String urlRegAuthpority;
	
	@Value("${create-guarantor}")
	private String urlCreateGuarantor;
	
	@Value("${person-code}")
	private String urlPersonCode;
	
	@Value("${guarantor-code}")
	private String urlGuarantorCode;
	
	@Value("${guarantor-Identi}")
	private String urlCreateGuarantorIdenti;
	
	@Value("${guarantor-address}")
    private String urlCreateGuarantorAddress;
	
	@Value("${guarantor-contact}")
    private String urlCreateGuarantorContact;

	// added by Senitha
    @Value("${bank}")
    private String urlBank;
	
	@Value("${comn-person-contact-type}")
    private String urlComnPersonContactType;
	
	@Value("${comn-person-identification-type}")
    private String urlComnPersonIdentificationType;
	
	@Value("${asset-sub-type}")
    private String urlAssetSubType;
	// ended by Senitha
	
	@Value("${pending-person-supplies}")
    private String urlPendingPersonSupplies;
	
	@Value("${pending-person}")
    private String urlPendingPerson;
	
	@Value("${customer-code}")
    private String urlCustomerCode;
	
	@Value("${comn-customer}")
    private String urlCommonCustomer;
	
	@Value("${customer-address}")
    private String urlCustomerAddress;
	
	@Value("${customer-identi}")
    private String urlCustomerIdenti;
	
	@Value("${customer-contact}")
    private String urlCustomerContact;
	
	@Value("${pending-customer}")
    private String urlPendingCustomer;
	
    @Value("${col-valuation-details}")
    private String urlColValuationDetails;

	@Value("${customer-relationship}")
    private String urlCustomerRelationship;
	
	@Value("${customer-relationship-update}")
    private String urlCustomerRelationshipUpdate;
	
	@Value("${customer-link-person}")
    private String urlCustomerLinkPerson;
	
	@Value("${customer-link-person-identification-update}")
    private String urlCustomerLinkPersonIdentificationUpdate;
	
	@Value("${customer-on-board}")
    private String urlCustomerOnboard;
	
	@Value("${customer-on-board-product}")
    private String urlCustomerOnboardProduct;
	
	@Value("${comn-person}")
    private String urlPerson;
	
    @Value("${comn-person-common-list}")
    private String urlPersonCommonList;

    @Value("${pending-customer-identification}")
    private String urlPendingCustomerIdentification;
    
    @Value("${col-collateral-sub-type}")
    private String urlSubType;
    
    @Value("${col-collateral-pledge-type}")
    private String pledgeTypeUrl;
    
    @Value("${comn-common-legal_structure}")
	private String urlComnCommonLegalStructure;
    
    @Value("${bank-branch}")
    private String urlBankBranch;
    
    @Value("${sector}")
    private String urlSector;
    
    @Value("${sub-sector}")
    private String urlSubSector;
    
    @Value("${customer-key-person}")
    private String urlCustomerKeyPerson;
    
    @Value("${customer-key-person-update}")
    private String urlCustomerKeyPersonUpdate;
    
	// start - added by Nipun
    @Value("${repayment-frequency}")
    private String urlRepaymentFrequency;
	// end - added by Nipun
    
    @Value("${comn-branch}")
    private String urlCommonBranch;
    
    @Value("${base-url}")
    private String baseUrl;
    
	// start - added by Dilhan
    @Value("${frequency}")
    private String urlFrequency;
    
    @Value("${designation}")
    private String urlDesignation;
	// end - added by Dilhan
    
 // start - added by Dilki
    @Value("${product}")
    private String urlProduct;
    
    @Value("${sub-product}")
    private String urlSubProduct;
	// end - added by DilkiurlProduct
    
    //Added by SewwandiH for Risk Grading
    @Value("${business-person-type}")
    private String urlBusinessPersonType;
    
    @Value("${industry-type}")
    private String urlIndustryType;
    //End 
    @Value("${url-user-profile}")
    private String urlUserProfile;
    
    @Value("${customer-employment-update}")
    private String urlCustomerEmploymentUpdate;
    
    @Value("${analyst-exception-bpm}")
	private String urlAnalystExceptionBpm;
    
    @Value("${comn-common-list}")
	private String urlComnCommonList;
    
    @Value("${document-upload}")
    private String urlDocumentUpload;
    
    @Value("${employer}")
    private String urlEmployer;
    
    @Value("${tax-code}")
    private String urlTaxCode;

    @Value("${comn-currency-detail}")
    private String urlCurrency;
    
    @Value("${language}")
    private String urlLanguage;
    
    @Value("${document-maintenance}")
	private String urlDocumentMaintenance;
    
//    @Value("${disbursement-conditions}")
//    private String urlDisbursementConditions;
       
	@Autowired
	private CommonListRepository commonListRepository;
    
	@Autowired
	private RemoteService remoteService;

	private Boolean pledgeTypeExist;

 
    @Override
    public Timestamp getCreateOrModifyDate() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        return new Timestamp(now.getTime());
    }
 
    
    @Override
    public Date formatDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    @Override
	public BigDecimal setScale(BigDecimal value) {
		return value.setScale(2, RoundingMode.HALF_UP);
	}
    
    @Override
	public Long stringToLong(String value){
		return Long.parseLong(value);
	}
    
    @Override
	public BigDecimal stringToBigDecimal(String value){
		return new BigDecimal(value);
	}
    
    @Override
	public Float stringToFloat(String value){
		return Float.parseFloat(value);
	}

    @Override
	public Double stringToDouble(String value){
		return Double.parseDouble(value);
	}

	@Override
	public WorkflowRulesResource invokedMicroBprRule(WorkflowType workflowType, String domainPath, String domainName, String tenantId) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(usernameBPM, passwordBPM);
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplateSetRequestFactory();
        String json="{\"lookup\": \"kie-session\", \"commands\": [{\"insert\": {\"object\": {\""+domainPath+"\": {\"referenceCode\":\""+workflowType+"\"}},\"out-identifier\":\""+domainName+"\"}},{\"fire-all-rules\": {}}]}";
        HttpEntity <String> entity = new HttpEntity<>(json, headers);
        WorkflowRulesResource workflowRulesResource=new WorkflowRulesResource();
        try {
            String responseString=restTemplate.exchange(urlMicroBprRuleContainer.replace("tenantId", tenantId), HttpMethod.POST, entity, String.class).getBody();
            JSONObject responseObj = new JSONObject(responseString);
            JSONObject responseResult =responseObj.getJSONObject("result");
            JSONObject responseExecutionResults =responseResult.getJSONObject("execution-results");
            JSONArray responseResults = responseExecutionResults.getJSONArray("results");
            JSONObject responseResults0 = responseResults.getJSONObject(0);
            JSONObject value = responseResults0.getJSONObject("value");
            JSONObject responseTransactionApproval =value.getJSONObject(domainPath);
           
            workflowRulesResource.setApprovalRequired(responseTransactionApproval.getString("approvalRequired"));   
            workflowRulesResource.setApprovalAllowed(responseTransactionApproval.getString("approvalAllowed"));
           
           
        } catch (RestClientException e) {
            if(e.getMessage().contains("503")) {
                throw new WorkFlowException(environment.getProperty("common.not-available"), workflowType);
            }else if(e.getMessage().contains("500")) {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
            }else if(e.getMessage().contains("404")) {
                throw new WorkFlowException(environment.getProperty("common.not-found"), workflowType);
            }else if(e.getMessage().contains("400")) {
                throw new WorkFlowException(environment.getProperty("common.bad-request"), workflowType);
            }else if(e.getMessage().contains("504")) {
                throw new WorkFlowException(environment.getProperty("common.gateway-time-out"), workflowType);
            }else {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
            }
        }catch (JSONException je) {
            throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
        }
        return workflowRulesResource;
	}
   
	public void restTemplateSetRequestFactory(){
		try {
			KeyStore clientStore = KeyStore.getInstance("PKCS12");
		    clientStore.load(getClass().getResourceAsStream("classpath:cert/"+pamCertLocation), "certpassword".toCharArray());
		 
		    SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
		    sslContextBuilder.useProtocol("TLS");
		    
				sslContextBuilder.loadKeyMaterial(clientStore, "certpassword".toCharArray());
			
		    sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());
		 
		    SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build());
		    CloseableHttpClient httpClient = HttpClients.custom()
		            .setSSLSocketFactory(sslConnectionSocketFactory)
		            .build();
		    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		    requestFactory.setConnectTimeout(10000); // 10 seconds
		    requestFactory.setReadTimeout(10000); // 10 seconds
		    restTemplate.setRequestFactory(requestFactory);
		} catch (UnrecoverableKeyException|NoSuchAlgorithmException|KeyStoreException|CertificateException|IOException|KeyManagementException e) {
			LoggerRequest.getInstance().logCommonError(e.getMessage());
		}
	}
	
	@Override
	public Long initiateMicroBprWorkFlow(WorkflowInitiationRequestResource workflowInitiationRequestResource, String tenantId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBasicAuth(usernameBPM, passwordBPM);
		headers.setContentType(MediaType.APPLICATION_JSON);
		restTemplateSetRequestFactory();
		Long processId=null;
		if(workflowInitiationRequestResource.getApprovWorkflowType()!=null &&workflowInitiationRequestResource.getApprovWorkflowType().equals(WorkflowType.BLACK_LIST_APPROVE)) {
			workflowInitiationRequestResource.setApprovalUser("MicroBpr-Blacklist-Approve");
			HttpEntity <WorkflowInitiationRequestResource> entity = new HttpEntity<>(workflowInitiationRequestResource, headers);
			processId=initiateWorkFlow(appBlacklistBmp.replace("tenantId", tenantId), entity, WorkflowType.BLACK_LIST_APPROVE);
		}else if(workflowInitiationRequestResource.getApprovWorkflowType()!=null && workflowInitiationRequestResource.getApprovWorkflowType().equals(WorkflowType.ANALYST_EXCEPTION)) {
			HttpEntity <WorkflowInitiationRequestResource> entity = new HttpEntity<>(workflowInitiationRequestResource, headers);
			processId=initiateWorkFlow(urlAnalystExceptionBpm.replace("tenantId", tenantId), entity, WorkflowType.ANALYST_EXCEPTION);	
		} 
		return processId;
	}
	
	private Long initiateWorkFlow(String url, HttpEntity <WorkflowInitiationRequestResource> entity, WorkflowType workflowType) {
		Long processId=null;
		try {
			processId= restTemplate.exchange(url, HttpMethod.POST, entity, Long.class).getBody();
		} catch (RestClientException e) {
			if(e.getMessage().contains("503")) {
				throw new WorkFlowException(environment.getProperty("common.not-available"), workflowType);
			}else if(e.getMessage().contains("500")) {
				throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
			}else if(e.getMessage().contains("404")) {
				throw new WorkFlowException(environment.getProperty("common.not-found"), workflowType);
			}else if(e.getMessage().contains("400")) {
				throw new WorkFlowException(environment.getProperty("common.bad-request"), workflowType);
			}else {
				throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
			}
		}
		return processId;
	}
	
	private Long getWorkFlowWorkItem(Long workflowProcessId, WorkflowType workflowType, String tenantId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBasicAuth(usernameBPM, passwordBPM);
		headers.setContentType(MediaType.APPLICATION_JSON);
		restTemplateSetRequestFactory();
		HttpEntity <String> entity = new HttpEntity<>(headers);
		
		String url=null;
		if(workflowType.equals(WorkflowType.BLACK_LIST_APPROVE)) {
			url=appBlacklistApprovalBmp.replace("tenantId", tenantId);
		}
		try {
			url =url+workflowProcessId+"/workitems";
			WorkFlowWorkItem workFlowWorkItem=restTemplate.exchange(url, HttpMethod.GET, entity, WorkFlowWorkItem.class).getBody();
			if(workFlowWorkItem!=null && workFlowWorkItem.getWorkItemInstance()!=null && !workFlowWorkItem.getWorkItemInstance().isEmpty()) {
				return workFlowWorkItem.getWorkItemInstance().get(0).getWorkItemId();
			} else {
				throw new WorkFlowException(environment.getProperty("common.workitems-not-available"), workflowType);
			}
		} catch (RestClientException e) {
			if(e.getMessage().contains("503")) {
				throw new WorkFlowException(environment.getProperty("common.not-available"), workflowType);
			} else if(e.getMessage().contains("500")) {
				throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
			} else if(e.getMessage().contains("404")) {
				throw new WorkFlowException(environment.getProperty("common.not-found"), workflowType);
			} else if(e.getMessage().contains("400")) {
				throw new WorkFlowException(environment.getProperty("common.bad-request"), workflowType);
			} else if(e.getMessage().contains("504")) {
				throw new WorkFlowException(environment.getProperty("common.gateway-time-out"), workflowType);
			} else {
				throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
			}
		}
	}
	
	@Override
	public void approveWorkFlow(Long workflowProcessId, WorkflowType workflowType, String username, String tenantId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBasicAuth(usernameBPM, passwordBPM);
		headers.setContentType(MediaType.APPLICATION_JSON);
		restTemplateSetRequestFactory();
		WorkflowInitiationRequestResource workflowInitiationRequestResource=new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(username);
		HttpEntity <WorkflowInitiationRequestResource> entity = new HttpEntity<>(workflowInitiationRequestResource, headers);
		
		Long workItemId= getWorkFlowWorkItem(workflowProcessId, workflowType, tenantId);
		
		String url=null;
		if(workflowType.equals(WorkflowType.BLACK_LIST_APPROVE)) {
			url=appBlacklistApprovalBmp.replace("tenantId", tenantId);
		}

		try {
			url =url+workflowProcessId+"/workitems/"+workItemId+"/completed";
			restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
		} catch (RestClientException e) {
			if(e.getMessage().contains("503")) {
				throw new WorkFlowException(environment.getProperty("common.not-available"), workflowType);
			}else if(e.getMessage().contains("500")) {
				throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
			}else if(e.getMessage().contains("404")) {
				throw new WorkFlowException(environment.getProperty("common.not-found"), workflowType);
			}else if(e.getMessage().contains("400")) {
				throw new WorkFlowException(environment.getProperty("common.bad-request"), workflowType);
			}else {
				throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
			}
		}
		
	}


	@Override
	public void abortedWorkFlow(Long workflowProcessId, WorkflowType workflowType, String username, String tenantId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBasicAuth(usernameBPM, passwordBPM);
		headers.setContentType(MediaType.APPLICATION_JSON);
		restTemplateSetRequestFactory();
		WorkflowInitiationRequestResource workflowInitiationRequestResource=new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(username);
		HttpEntity <WorkflowInitiationRequestResource> entity = new HttpEntity<>(workflowInitiationRequestResource, headers);
		
		Long workItemId=getWorkFlowWorkItem(workflowProcessId, workflowType, tenantId);
		
		String url=null;
		if(workflowType.equals(WorkflowType.BLACK_LIST_APPROVE)) {
			url=appBlacklistApprovalBmp.replace("tenantId", tenantId);
		} 
		try {
			url =url+workflowProcessId+"/workitems/"+workItemId+"/aborted";
			restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
		} catch (RestClientException e) {
			if(e.getMessage().contains("503")) {
				throw new WorkFlowException(environment.getProperty("common.not-available"), workflowType);
			}else if(e.getMessage().contains("500")) {
				throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
			}else if(e.getMessage().contains("404")) {
				throw new WorkFlowException(environment.getProperty("common.not-found"), workflowType);
			}else if(e.getMessage().contains("400")) {
				throw new WorkFlowException(environment.getProperty("common.bad-request"), workflowType);
			}else {
				throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
			}
		}
		
	}



	@Override
	public void validateCreditFacilityandFacilityReasonForExternalCrib(ExternalCribValidationResource externalCribValidationResource, ServicePoint servicePoint) {
		
		//credit facility commonlist
			Optional<CommonList> commonList=commonListRepository.findById(remoteService.stringToLong(externalCribValidationResource.getCribFacltyTypeComListId()));
			if(commonList.isPresent() && commonList.get().getName().equalsIgnoreCase(externalCribValidationResource.getCribFacilityType()) && commonList.get().getReferenceCode().equalsIgnoreCase(CommonListReferenceCode.CRIB_FACILITY_TYPES.toString())
					&& commonList.get().getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
			}else {
				throw new DetailValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.CREDIT_FACILITY_TYPE, servicePoint);
			}
			
		
		//facility reason
			Optional<CommonList> commonListr=commonListRepository.findById(remoteService.stringToLong(externalCribValidationResource.getComlistReasonId()));
			if(commonListr.isPresent() && commonListr.get().getName().equalsIgnoreCase(externalCribValidationResource.getReason()) && commonListr.get().getReferenceCode().equalsIgnoreCase(CommonListReferenceCode.CRIB_REASONS.toString())
					&& commonListr.get().getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
			}else {
				throw new DetailValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.COMMONLIST_REASON, servicePoint);
			}
		
			//facility borrower type
			if(externalCribValidationResource.getBorrowerTypeComListId()!=null && !externalCribValidationResource.getBorrowerTypeComListId().isEmpty()) {
				Optional<CommonList> commonListrb=commonListRepository.findById(remoteService.stringToLong(externalCribValidationResource.getBorrowerTypeComListId()));
				if(commonListrb.isPresent() && commonListrb.get().getName().equalsIgnoreCase(externalCribValidationResource.getBorrowerType()) && commonListrb.get().getReferenceCode().equalsIgnoreCase(CommonListReferenceCode.BORROWER_TYPE.toString())
						&& commonListrb.get().getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
				}else {
					throw new DetailValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.COMMONLIST_BORROWER_TYPE, servicePoint);
				}
			}
	}
	
	
	@Override
	public void validateCollateraldetailsForUpdate(String tenantId,
			CreditAppraiselCollateralDetailsUpdateResource creditAppraiselCollateralDetailsUpdateResource) {

		LoggerRequest.getInstance().logInfo("credit-appraisal-collateral-details********************************Validate asset details****************");
		AssetsDetailsResponseResource assetDeatils =(AssetsDetailsResponseResource)remoteService.checkIsExistAssetDetails(creditAppraiselCollateralDetailsUpdateResource.getAssetDetailRefCode().toString(), urlAssetDetail+tenantId+"/entityId/", AssetsDetailsResponseResource.class);
		if(assetDeatils==null || assetDeatils.getServiceStatus()==null) {
			if(assetDeatils==null || assetDeatils.getStatus().equals(CommonStatus.INACTIVE.toString()))
				throw new DetailValidateException(environment.getProperty(INVALID_ASSET_DETAIL_ID), ServiceEntity.ASSETS_DETAIL_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
			if(assetDeatils.getAssetsEntityId()!=null && !assetDeatils.getAssetsEntityId().toString().isEmpty() && !assetDeatils.getAssetsEntityId().toString().equals(creditAppraiselCollateralDetailsUpdateResource.getAssetEntityId()))
				throw new DetailValidateException(environment.getProperty(INVALID_ASSET_ENTITY_ID), ServiceEntity.ASSETS_ENTITY_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
			if(assetDeatils.getAssetPartId()!=null && !assetDeatils.getAssetPartId().toString().isEmpty() && !assetDeatils.getAssetPartId().toString().equals(creditAppraiselCollateralDetailsUpdateResource.getAssetPartId()))
				throw new DetailValidateException(environment.getProperty(INVALID_ASSET_PART_ID), ServiceEntity.ASSETS_PART_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
			
			creditAppraiselCollateralDetailsUpdateResource.setAssetDetailVersion(assetDeatils.getVersion());
			
			LoggerRequest.getInstance().logInfo("credit-appraisal-collateral-details********************************Validate asset entity details*********************************************");	
			validateExistingAssetsEntityDetails( tenantId, creditAppraiselCollateralDetailsUpdateResource);
			
				
		}else {
			remoteService.serviceValidationExceptionHadle(assetDeatils.getServiceStatus(), ServiceEntity.ASSETS_DETAIL_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
		}
		
	}
	
	@Override
	public void validateExistingCollateraldetails(String tenantId,CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetails) {
		
		LoggerRequest.getInstance().logInfo("credit-appraisal-collateral-details********************************Validate asset details****************");
		AssetsDetailsResponseResource assetDeatils =(AssetsDetailsResponseResource)remoteService.checkIsExistAssetDetails(creditAppraiselCollateralDetails.getAssetEntityId(), urlAssetDetail+tenantId+"/entityId/", AssetsDetailsResponseResource.class);
		if(assetDeatils==null || assetDeatils.getServiceStatus()==null) {
			if(assetDeatils==null || assetDeatils.getStatus().equals(CommonStatus.INACTIVE.toString()))
				throw new DetailValidateException(environment.getProperty(INVALID_ASSET_DETAIL_ID), ServiceEntity.ASSETS_DETAIL_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
			if(assetDeatils.getAssetsEntityId()!=null && !assetDeatils.getAssetsEntityId().toString().isEmpty() && !assetDeatils.getAssetsEntityId().toString().equals(creditAppraiselCollateralDetails.getAssetEntityId()))
				throw new DetailValidateException(environment.getProperty(INVALID_ASSET_ENTITY_ID), ServiceEntity.ASSETS_ENTITY_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
			if(assetDeatils.getAssetPartId()!=null && !assetDeatils.getAssetPartId().toString().isEmpty() && !assetDeatils.getAssetPartId().toString().equals(creditAppraiselCollateralDetails.getAssetPartId()))
				throw new DetailValidateException(environment.getProperty(INVALID_ASSET_PART_ID), ServiceEntity.ASSETS_PART_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
			
			creditAppraiselCollateralDetails.setAssetDetailVersion(assetDeatils.getVersion());
			LoggerRequest.getInstance().logInfo("credit-appraisal-collateral-details********************************Validate asset entity details*********************************************");	
			validateExistingAssetsEntityDetails( tenantId, creditAppraiselCollateralDetails);
			
			LoggerRequest.getInstance().logInfo("credit-appraisal-collateral-details********************************Validate ownership type details*********************************************");	
			//validateOwnerShipType( tenantId, creditAppraiselCollateralDetails);
				
		}else {
			remoteService.serviceValidationExceptionHadle(assetDeatils.getServiceStatus(), ServiceEntity.ASSETS_DETAIL_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
		}
		
	}
	
	public void validateExistingAssetsEntityDetails(String tenantId,
			CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetails) {
		
		if(creditAppraiselCollateralDetails.getAssetEntityId()!=null && !creditAppraiselCollateralDetails.getAssetEntityId().isEmpty()) {
		AssetEntityResponseResource assetsEntity =(AssetEntityResponseResource)remoteService.checkIsExist(tenantId, creditAppraiselCollateralDetails.getAssetEntityId(), urlAssetEntity, AssetEntityResponseResource.class);

			if(assetsEntity==null || assetsEntity.getServiceStatus()==null) {
				
				if(assetsEntity==null || assetsEntity.getStatus().equals(CommonStatus.INACTIVE) && (!assetsEntity.getName().equalsIgnoreCase(creditAppraiselCollateralDetails.getName()) 
						&& !assetsEntity.getDescription().equalsIgnoreCase(creditAppraiselCollateralDetails.getDescription())))
					throw new DetailValidateException(environment.getProperty(INVALID_ASSET_ENTITY_ID), ServiceEntity.ASSETS_ENTITY_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
				
				if(assetsEntity.getAssetTypeId()!=null && !assetsEntity.getAssetTypeId().toString().isEmpty() && !assetsEntity.getAssetTypeId().toString().equals(creditAppraiselCollateralDetails.getAssetTypeId()))
						throw new DetailValidateException(environment.getProperty(INVALID_ASSET_TYPE_ID), ServiceEntity.ASSET_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
				if(assetsEntity.getAssetSubTypeId()!=null && !assetsEntity.getAssetSubTypeId().toString().isEmpty() && !assetsEntity.getAssetSubTypeId().toString().equals(creditAppraiselCollateralDetails.getAssetSubTypeId()))
					throw new DetailValidateException(environment.getProperty(INVALID_ASSET_SUB_TYPE_ID), ServiceEntity.ASSET_SUB_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
				if(assetsEntity.getSubTypeId()!=null && !assetsEntity.getSubTypeId().toString().equals(creditAppraiselCollateralDetails.getSubTypeId()))
					throw new DetailValidateException(environment.getProperty(INVALID_ASSET_SUB_TYPE_ID), ServiceEntity.ASSET_SUB_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
				if(assetsEntity.getRegistrationAuthorityId()!=null && !assetsEntity.getRegistrationAuthorityId().toString().equals(creditAppraiselCollateralDetails.getRegistrationAuthorityId()))
					throw new DetailValidateException(environment.getProperty(ASSET_REGISTR_AUTH_ID), ServiceEntity.ASSET_REGISTR_AUTH_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);							
				if(assetsEntity.getSupplierId()!=null && !assetsEntity.getSupplierId().toString().equals(creditAppraiselCollateralDetails.getSupplierId()))
					throw new DetailValidateException(environment.getProperty(ASSET_SUPPLIER_ID), ServiceEntity.ASSET_SUPPLIER_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);							
				if(assetsEntity.getPledgeTypeId()!=null && !assetsEntity.getPledgeTypeId().toString().equals(creditAppraiselCollateralDetails.getPledgeTypeId()))
					throw new DetailValidateException(environment.getProperty(ASSET_SUPPLIER_ID), ServiceEntity.PLEDGE_TYPE, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);		
			
				creditAppraiselCollateralDetails.setCustomerAssetId(assetsEntity.getCustomerAssetId().toString());
			
			}else {
				remoteService.serviceValidationExceptionHadle(assetsEntity.getServiceStatus(), ServiceEntity.ASSETS_ENTITY_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
			}
		
		}else {
			throw new DetailValidateException(environment.getProperty(INVALID_ASSET_ENTITY_ID), ServiceEntity.ASSETS_ENTITY_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
		}
		
	}
	
	//Validate Pledge Type
	@Override
	public void validatePledgeType(String tenantId, Long assetTypeId, Long pledgeTypeId, String  pledgeTypeName) {
	
		AssetTypePledgeTypeMappingResponce assetTypePledgeTypeMappingList = remoteService.checkIsExistAssetTypePledgeTypeMap(tenantId, assetTypeId, urlAssetTypePledgeType, AssetTypePledgeTypeMappingResource.class);
		
		if(assetTypePledgeTypeMappingList.getAssetTypePledgeTypeMappingResource().size()>0) {
			
		pledgeTypeExist = Boolean.FALSE;
			for(AssetTypePledgeTypeMappingResource assetTypePledgeTypeMappingResource :  assetTypePledgeTypeMappingList.getAssetTypePledgeTypeMappingResource()) {
				
				if(assetTypePledgeTypeMappingResource.getPledge_type_id().equals(pledgeTypeId)){
					if(!(assetTypePledgeTypeMappingResource.getPledgeTypeName().equalsIgnoreCase(pledgeTypeName) 
							&& assetTypePledgeTypeMappingResource.getStatus().equals(CommonStatus.ACTIVE.toString())))
							throw new DetailValidateException(environment.getProperty("common.not-match"),ServiceEntity.PLEDGE_TYPE, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
							
					pledgeTypeExist = Boolean.TRUE;
				}
			}
			 if(pledgeTypeExist = Boolean.FALSE)
				 throw new DetailValidateException(environment.getProperty("common.not-match"),ServiceEntity.PLEDGE_TYPE, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
		}
		
	}
	
	@Override
	public AssetTypePledgeTypeMappingResource validatePledgeType(String tenantId, Long pledgeTypeId, String  pledgeTypeName) {
		AssetTypePledgeTypeMappingResource assetTypePledgeTypeMappingResource = (AssetTypePledgeTypeMappingResource) remoteService.checkIsExist(tenantId, pledgeTypeId.toString(), pledgeTypeUrl,AssetTypePledgeTypeMappingResource.class);
		if (assetTypePledgeTypeMappingResource == null || assetTypePledgeTypeMappingResource.getServiceStatus() == null) {
			if(pledgeTypeName!=null) {
				if (assetTypePledgeTypeMappingResource == null
						|| !assetTypePledgeTypeMappingResource.getPledgeTypeName()
								.equalsIgnoreCase(pledgeTypeName)
						|| !assetTypePledgeTypeMappingResource.getStatus()
								.equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
					throw new DetailValidateException(environment.getProperty("common.not-match"),ServiceEntity.ASSET_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
				}
			}
		} else {
			throw new DetailValidateException(assetTypePledgeTypeMappingResource.getServiceStatus().toString(),ServiceEntity.ASSET_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
		}
		return assetTypePledgeTypeMappingResource;
		
	}

	@Override
	public AssetTypeResponseResource validateAssetType(String tenantId, Long assetTypeId, String assetTypeName) {
		AssetTypeResponseResource assetTypeResponseResource = (AssetTypeResponseResource) remoteService.checkIsExist(tenantId, assetTypeId.toString(), assetTypeUrl,AssetTypeResponseResource.class);
		if (assetTypeResponseResource == null || assetTypeResponseResource.getServiceStatus() == null) {
			if(assetTypeName!=null) {
				if (assetTypeResponseResource == null
						|| !assetTypeResponseResource.getName()
								.equalsIgnoreCase(assetTypeName)
						|| !assetTypeResponseResource.getStatus()
								.equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
					throw new DetailValidateException(environment.getProperty("common.not-match"),ServiceEntity.ASSET_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
				}
			}
		} else {
			throw new DetailValidateException(assetTypeResponseResource.getServiceStatus().toString(),ServiceEntity.ASSET_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
		}
		return assetTypeResponseResource;
		
	}

	@Override
	public AssetSubTypeResponseResource validateAssetSubType(String tenantId, Long assetSubTypeId, String assetSubTypeName) {
//		AssetSubTypeResponseResource assetSubTypeResponseResource = (AssetSubTypeResponseResource) remoteService
//				.checkIsExist(tenantId, assetSubTypeId.toString(),
//						assetSubTypeUrl, AssetSubTypeResponseResource.class);
//		if (assetSubTypeResponseResource == null
//				|| assetSubTypeResponseResource.getServiceStatus() == null) {
//			if(assetSubTypeName!=null) {
//				if (assetSubTypeResponseResource == null
//						|| !assetSubTypeResponseResource.getName()
//								.equalsIgnoreCase(assetSubTypeName)
//						|| !assetSubTypeResponseResource.getStatus()
//								.equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
//					throw new DetailValidateException(environment.getProperty("common.not-match"),
//							ServiceEntity.ASSET_SUB_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
//				}
//			}
//		} else {
//			throw new DetailValidateException(assetSubTypeResponseResource.getServiceStatus().toString(),
//					ServiceEntity.ASSET_SUB_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
//		}
//		return assetSubTypeResponseResource;
		
		String url = assetSubTypeUrl + tenantId + "/" + assetSubTypeId;

		AssetSubTypeResponseResource assetSubTypeResponseResponse = (AssetSubTypeResponseResource) getDetailFromService(
				url, AssetSubTypeResponseResource.class);
		if (assetSubTypeResponseResponse == null) {
			throw new ValidateRecordException(environment.getProperty("common.not-available"),
					"assetSubTypeResponseId");
		}
		if (assetSubTypeResponseResponse.getStatus() != null) {

			if (!assetSubTypeResponseResponse.getStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"),
						"assetSubTypeResponseId");
			}
		} else {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"),
					"assetSubTypeResponseId");
		}
		return assetSubTypeResponseResponse;

	}


	@Override
	public void validateAssetSubTypeParts(String tenantId, Long assetSubTypeId,Long assetPartId, String assetPartName) {
		AssetSubTypePartsResponseList assetSubTypePartsResponseList = (AssetSubTypePartsResponseList) remoteService
				.checkIsExistPartsByAssetSubType(tenantId, assetSubTypeId.toString(), urlAssetPartByAssetSubType,
						AssetSubTypePartsResponseList.class);
		if (assetSubTypePartsResponseList == null
				|| assetSubTypePartsResponseList.getServiceStatus() == null) {
			
			pledgeTypeExist = Boolean.FALSE;
			for(AssetSubTypePartsResponseResource assetSubTypePartsResponseResource : assetSubTypePartsResponseList.getAssetSubTypePartsResponseResource()) {
			
				if(assetSubTypePartsResponseResource.getId().equals(assetPartId)) {
					if (!assetSubTypePartsResponseResource.getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString())
							|| !assetSubTypePartsResponseResource.getName().equalsIgnoreCase(assetPartName)) {
						throw new DetailValidateException(environment.getProperty("common.not-match"),
								ServiceEntity.ASSETS_PART_ID,ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
					}
				pledgeTypeExist = Boolean.TRUE;
				}
			}
			
			 if(pledgeTypeExist = Boolean.FALSE)
				 throw new DetailValidateException(environment.getProperty("common.not-match"),
							ServiceEntity.ASSETS_PART_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);

		} else {
		throw new DetailValidateException(assetSubTypePartsResponseList.getServiceStatus().toString(),
				ServiceEntity.ASSETS_PART_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
	}
		
	}
	
	@Override
	public AssetSubTypePartsResponseResource validateAssetSubTypeParts(String tenantId, Long assetPartId, String assetPartName) {
		AssetSubTypePartsResponseResource assetSubTypePartsResponseResource = (AssetSubTypePartsResponseResource) remoteService
				.checkIsExist(tenantId, assetPartId.toString(), urlAssetPartByAssetSubType, AssetSubTypePartsResponseResource.class);
		
		if (assetSubTypePartsResponseResource == null
				|| assetSubTypePartsResponseResource.getServiceStatus() == null) {
			if(assetPartName!=null) {
				if (assetSubTypePartsResponseResource == null
						|| !assetSubTypePartsResponseResource.getName()
								.equalsIgnoreCase(assetPartName)
						|| !assetSubTypePartsResponseResource.getStatus()
								.equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
					throw new DetailValidateException(environment.getProperty("common.not-match"),
							ServiceEntity.ASSET_SUB_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
				}
			}
		} else {
			throw new DetailValidateException(assetSubTypePartsResponseResource.getServiceStatus().toString(),
					ServiceEntity.ASSET_SUB_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
		}
		return assetSubTypePartsResponseResource;
		
	}
	
	
//	@Override
//	public void validateAssetTypeSubType(String tenantId,
//			CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetails) {
//		AssetSubTypePartsResponseList assetSubTypePartsResponseList = (AssetSubTypePartsResponseList) remoteService
//				.checkIsExistAssetTypeSubType(tenantId,
//						creditAppraiselCollateralDetails.getAssetTypeId(),
//						creditAppraiselCollateralDetails.getSubTypeId(), assetTypeSubTypeUrl,
//						AssetSubTypePartsResponseList.class);
//		
//		pledgeTypeExist = Boolean.FALSE;
//		for(AssetSubTypePartsResponseResource assetTypeSubTypeMappingResponseResource : assetSubTypePartsResponseList.getAssetSubTypePartsResponseResource()) {
//		if (assetTypeSubTypeMappingResponseResource == null
//				|| assetTypeSubTypeMappingResponseResource.getServiceStatus() == null) {
//			
//			
//			if (assetTypeSubTypeMappingResponseResource == null
//					|| !assetTypeSubTypeMappingResponseResource.getAssetSubTypeName()
//							.equalsIgnoreCase(creditAppraiselCollateralDetails.getAssetSubTypeName())
//					|| !assetTypeSubTypeMappingResponseResource.getStatus()
//							.equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
//				throw new DetailValidateException(environment.getProperty("common.invalid"),
//						ServiceEntity.SUB_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
//			}
//		} else {
//			throw new DetailValidateException(assetTypeSubTypeMappingResponseResource.getServiceStatus().toString(),
//					ServiceEntity.SUB_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
//		}
//		}
//		if(pledgeTypeExist = Boolean.FALSE)
//			 throw new DetailValidateException(environment.getProperty("common.not-match"),
//						ServiceEntity.PLEDGE_TYPE, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
//		
//	}


	@Override
	public void validateAssetTypeSubType(String tenantId,
			CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetails) {
		AssetTypeSubTypeResponseResource assetTypeSubTypeMappingResponseResource = (AssetTypeSubTypeResponseResource) remoteService
				.checkIsExistAssetTypeSubType(tenantId,
						creditAppraiselCollateralDetails.getAssetTypeId(),
						creditAppraiselCollateralDetails.getSubTypeId(), assetTypeSubTypeUrl,
						AssetTypeSubTypeResponseResource.class);
		if (assetTypeSubTypeMappingResponseResource == null
				|| assetTypeSubTypeMappingResponseResource.getServiceStatus() == null) {
			if(creditAppraiselCollateralDetails.getSubTypeName()!=null) {
			if (assetTypeSubTypeMappingResponseResource == null
					|| !assetTypeSubTypeMappingResponseResource.getSubTypeName()
							.equalsIgnoreCase(creditAppraiselCollateralDetails.getSubTypeName())
					|| !assetTypeSubTypeMappingResponseResource.getStatus()
							.equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
				throw new DetailValidateException(environment.getProperty("common.invalid"),
						ServiceEntity.SUB_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
			}
			}
		} else {
			throw new DetailValidateException(assetTypeSubTypeMappingResponseResource.getServiceStatus().toString(),
					ServiceEntity.SUB_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
		}
		
	}
	
	@Override
	public AssetTypeSubTypeResponseResource validateAssetTypeSubType(String tenantId, Long subTypeId, String subTypeName) {
		AssetTypeSubTypeResponseResource assetTypeSubTypeResponseResource = (AssetTypeSubTypeResponseResource) remoteService
				.checkIsExist(tenantId, subTypeId.toString(),urlSubType,AssetTypeSubTypeResponseResource.class);
		if (assetTypeSubTypeResponseResource == null
				|| assetTypeSubTypeResponseResource.getServiceStatus() == null) {
			if(subTypeName!=null) {
				if (assetTypeSubTypeResponseResource == null
						|| !assetTypeSubTypeResponseResource.getSubTypeName().equalsIgnoreCase(subTypeName)
						|| !assetTypeSubTypeResponseResource.getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
					throw new DetailValidateException(environment.getProperty("common.invalid"),
							ServiceEntity.SUB_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
				}
			}
		} else {
			throw new DetailValidateException(assetTypeSubTypeResponseResource.getServiceStatus().toString(),
					ServiceEntity.SUB_TYPE_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
		}
		return assetTypeSubTypeResponseResource;
		
	}
	


	@Override
	public ComnSuppliesEntitiesResource validateComnSuppliesEntities(String tenantId, Long supplierId, String SupReferenceCode) {
		ComnSuppliesEntitiesResource comnSuppliesEntitiesResource = (ComnSuppliesEntitiesResource)remoteService.checkIsExist(tenantId, supplierId.toString(), urlComnSuppliesEntities, ComnSuppliesEntitiesResource.class);
		if (comnSuppliesEntitiesResource == null) { 
			throw new ValidateRecordException(environment.getProperty("supplier.not-match"),"message");
		}
		if (comnSuppliesEntitiesResource.getServiceStatus() == null) {
			if(SupReferenceCode!=null) {
				if (comnSuppliesEntitiesResource == null || !comnSuppliesEntitiesResource.getSupReferenceCode().equals(SupReferenceCode)) {
					throw new ValidateRecordException(environment.getProperty("supplier.not-match"),"message");
				}
				if (!comnSuppliesEntitiesResource.getSupStatus().equals("ACTIVE") || !comnSuppliesEntitiesResource.getSupSuppliesType().equals("SUPPLIER")) {
					throw new ValidateRecordException(environment.getProperty("supplier.invalid"),"message");
				}
			}
		}
		else {
			throw new DetailValidateException(comnSuppliesEntitiesResource.getServiceStatus().toString(), ServiceEntity.SUPPLIER_ID, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
		}
		return comnSuppliesEntitiesResource;
		
	}

	@Override
	public RegistrationAuthorityValidationResource validateRegistrationAuthority(String tenantId, Long regAuthorityId, String regAuthority) {
		RegistrationAuthorityValidationResource	 registrationAuthorityValidationResource = (RegistrationAuthorityValidationResource) remoteService.checkIsExist(tenantId, regAuthorityId.toString(), urlRegAuthpority, RegistrationAuthorityValidationResource.class);
		if(registrationAuthorityValidationResource == null || registrationAuthorityValidationResource.getServiceStatus()==null) {
			if(regAuthority!=null) {
				if(registrationAuthorityValidationResource==null || !registrationAuthorityValidationResource.getAuthorityName().equalsIgnoreCase(regAuthority) || 
						 !registrationAuthorityValidationResource.getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString()))
				throw new DetailValidateException(environment.getProperty("common.not-match"),
						ServiceEntity.REG_AUTHORITY, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
			}
		} else {
			throw new DetailValidateException(registrationAuthorityValidationResource.getServiceStatus().toString(),
					ServiceEntity.REG_AUTHORITY, ServicePoint.CREDIT_APP_COLLLATERAL_DETAILS);
		}
		return registrationAuthorityValidationResource;
		
	}


	@Override
	public Long validateAndIntergrateWithCollateral(String tenantId, String createdUser, AssetsDetailsRequestResource assetsDetailsRequestResource, Long id) {
		//get document details
		ColInetragationAssetsDetailsRequestResource colInetragationAssetsDetailsRequestResource = (ColInetragationAssetsDetailsRequestResource) remoteService.integrateWithCollateral(tenantId, createdUser, assetsDetailsRequestResource, ColInetragationAssetsDetailsRequestResource.class);
		if(colInetragationAssetsDetailsRequestResource == null || colInetragationAssetsDetailsRequestResource.getServiceStatus()==null) {
			if(colInetragationAssetsDetailsRequestResource==null || colInetragationAssetsDetailsRequestResource.getValue() == null 
					|| colInetragationAssetsDetailsRequestResource.getAssetEntityId() == null)
				throw new ValidateRecordException(environment.getProperty("collateral.not-match"),"message");
			
			return Long.parseLong(colInetragationAssetsDetailsRequestResource.getAssetEntityId());
			
		} else {
			throw new ValidateRecordException(environment.getProperty("collateral.not-match"),"message");
		}
		
	}
	
	private void serviceValidationExceptionHadle(ServiceStatus serviceStatus, ServiceEntity serviceEntity) {
		switch(serviceStatus) { 
	        case NOT_AVAILABLE: 
	        	throw new InvalidServiceIdException(environment.getProperty("common.not-available"), serviceEntity);
	        case NOT_FOUND: 
	        	throw new InvalidServiceIdException(environment.getProperty("common.not-found"), serviceEntity);
	        case EXCEPTION: 
	        	throw new InvalidServiceIdException(environment.getProperty("common.internal-server-error"), serviceEntity);
	        case BAD_REQUEST: 
	        	throw new InvalidServiceIdException(environment.getProperty("common.bad-request"), serviceEntity);
	        default:
	    }
	}
	
	private Object getDetailFromService(String url, Class<?> classObject) {
		try {
		  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <String> entity = new HttpEntity<>(headers);
	      return restTemplate.exchange(url, HttpMethod.GET, entity, classObject).getBody();
		} catch (RestClientException e) {
			String result=null;
			if(e.getMessage().contains("503")) {
				result=serviceStatus +ServiceStatus.NOT_AVAILABLE.toString()+"\"}";
			}else if(e.getMessage().contains("500")) {
				result=serviceStatus+ ServiceStatus.EXCEPTION.toString()+"\"}";
			}else if(e.getMessage().contains("404")) {
				result=serviceStatus+ ServiceStatus.NOT_FOUND.toString()+"\"}";
			}else if(e.getMessage().contains("400")) {
				result=serviceStatus+ ServiceStatus.BAD_REQUEST.toString()+"\"}";
			}
			ObjectMapper mapper = new ObjectMapper();
			try {
				return mapper.readValue(result, classObject);
			} catch (IOException e1) {
				return null;
			}
		}
	}

	
	private Object getDetailFromServiceList(String url, List<?> classObject) {
		try {
		  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <String> entity = new HttpEntity<>(headers);
	      String responseVal = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
	      ObjectMapper mapper = new ObjectMapper();
	      if (responseVal!=null && !responseVal.equals(""))
	    	  mapper.readValue(responseVal, (TypeReference) classObject);
	      
	      return mapper;
		} catch (RestClientException | IOException e) {
			String result=null;
			if(e.getMessage().contains("503")) {
				result=serviceStatus +ServiceStatus.NOT_AVAILABLE.toString()+"\"}";
			}else if(e.getMessage().contains("500")) {
				result=serviceStatus+ ServiceStatus.EXCEPTION.toString()+"\"}";
			}else if(e.getMessage().contains("404")) {
				result=serviceStatus+ ServiceStatus.NOT_FOUND.toString()+"\"}";
			}else if(e.getMessage().contains("400")) {
				result=serviceStatus+ ServiceStatus.BAD_REQUEST.toString()+"\"}";
			}
			ObjectMapper mapper1 = new ObjectMapper();
			try {
				return mapper1.readValue(result, (TypeReference) classObject);
			} catch (IOException e1) {
				return null;
			}
		}
	}


	/**
	 * @author Senitha
	 * 
     * Validate company
     * @param String tenantId   - tenantId
     * @param String periodId   - bankId
     * @param String periodName   - bankName
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Period class
     * 
     */
	@Override
	public BankResponse validateCompany(String tenantId, Long companyId, String companyName) {
		
		String url = urlBank+tenantId+"/"+companyId.toString();
		BankResponse bankResponse = (BankResponse)getDetailFromService(url, BankResponse.class);
		if (bankResponse == null) { 
			throw new ValidateRecordException(environment.getProperty("company.not-match"),"companyId");
		}
		if (bankResponse.getServiceStatus() == null) {
			if (!bankResponse.getId().equals(companyId) || !bankResponse.getBankName().equals(companyName)) {
				throw new ValidateRecordException(environment.getProperty("company.not-match"),"companyId");
			}
			if (!bankResponse.getBankStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("company.invalid"),"companyId");
			}
		}
		else {
			serviceValidationExceptionHadle(bankResponse.getServiceStatus(), ServiceEntity.COMPANY);
		}
		return bankResponse;
		
	}
	
	/**
	 * @author Senitha
	 * 
     * Get company name
     * @param String tenantId   - tenantId
     * @param String companyId   - companyId
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Bank class
     * 
     */
	@Override
	public BankResponse getCompanyName(String tenantId, Long companyId) {
		BankResponse bankResponse = (BankResponse)getDetailFromService(tenantId, companyId.toString(), urlBank, BankResponse.class);
		return bankResponse;
	}

	/**
	 * @author Senitha
	 * 
     * Validate Identification Type
     * @param String tenantId   - tenantId
     * @param String identificationTypeId   - id
     * @param String identificationTypeCode   - idtpCode
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Identification Type
     * 
     */
	@Override
	public IdentificationType validateIdentificationType(String tenantId, Long id, String idtpCode) {
		
		String url = urlComnPersonIdentificationType+tenantId+"/"+id.toString();
		IdentificationType identificationType = (IdentificationType)getDetailFromService(url, IdentificationType.class);
		if (identificationType == null) { 
			throw new ValidateRecordException(environment.getProperty("identificationTypeId.not-match"),"identificationTypeId");
		}
		if (identificationType.getServiceStatus() == null) {
			if (!identificationType.getId().equals(id) || !identificationType.getIdtpCode().equals(idtpCode)) {
				throw new ValidateRecordException(environment.getProperty("identificationTypeId.not-match"),"identificationTypeId");
			}
			if (!identificationType.getIdtpStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("identificationTypeId.invalid"),"identificationTypeId");
			}
		}
		else {
			serviceValidationExceptionHadle(identificationType.getServiceStatus(), ServiceEntity.IDENTIFICATION_TYPE);
		}
		return identificationType;
		
	}

	/**
	 * @author Senitha
	 * 
     * Validate Identification Number
     * @param String tenantId   - tenantId
     * @param String identificationTypeId   - id
     * @param String identificationNumber   - identificationNumber
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Identification Type
     * 
     */
	@Override
	public IdentificationType validateIdentificationNumber(String tenantId, Long id, String identificationNo) {
		
		String url = urlComnPersonIdentificationType+tenantId+"/"+id.toString();
		IdentificationType identificationType = (IdentificationType)getDetailFromService(url, IdentificationType.class);
		if (identificationType == null) { 
			throw new ValidateRecordException(environment.getProperty("identificationNumber.not-match"),"nicNo");
		}
		if (identificationType.getServiceStatus() == null) {
			if (!identificationType.getId().equals(id) || !identificationNo.matches(identificationType.getIdtpVisualFormtValidtion())) {
				throw new ValidateRecordException(environment.getProperty("identificationNumber.not-match"),"nicNo");
			}
			if (!identificationType.getIdtpStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("identificationNumber.invalid"),"nicNo");
			}
		}
		else {
			serviceValidationExceptionHadle(identificationType.getServiceStatus(), ServiceEntity.CONTACT_TYPE);
		}
		return identificationType;
		
	}
	/**
	 * @author Senitha
	 * 
     * Validate Contact Type
     * @param String tenantId   - tenantId
     * @param String conatactTypeId   - id
     * @param String conatactTypeCode   - cntpCode
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of ContactType class
     * 
     */
	@Override
	public ContactType validateContactType(String tenantId, Long id, String cntpCode) {
		
		String url = urlComnPersonContactType+tenantId+"/"+id.toString();
		ContactType contactType = (ContactType)getDetailFromService(url, ContactType.class);
		if (contactType == null) { 
			throw new ValidateRecordException(environment.getProperty("contactTypeId.not-match"),"contactTypeId");
		}
		if (contactType.getServiceStatus() == null) {
			if (!contactType.getId().equals(id) || !contactType.getCntpCode().equals(cntpCode)) {
				throw new ValidateRecordException(environment.getProperty("contactTypeId.not-match"),"contactTypeCode");
			}
			if (!contactType.getCntpStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("contactTypeId.invalid"),"contactTypeId");
			}
		}
		else {
			serviceValidationExceptionHadle(contactType.getServiceStatus(), ServiceEntity.CONTACT_TYPE);
		}
		return contactType;
		
	}
	
	/**
	 * @author Senitha
	 * 
     * Validate Contact Number
     * @param String tenantId   - tenantId
     * @param String contactTypeId   - contactTypeId
     * @param String contactNumber   - contactNumber
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of ContactType class
     * 
     */
	@Override
	public ContactType validateContactNumber(String tenantId, Long contactTypeId, String contactNumber) {
		
		String url = urlComnPersonContactType+tenantId+"/"+contactTypeId.toString();
		ContactType contactType = (ContactType)getDetailFromService(url, ContactType.class);
		if (contactType == null) { 
			throw new ValidateRecordException(environment.getProperty("contactNumber.not-match"),"contactNumber");
		}
		if (contactType.getServiceStatus() == null) {
			if (!contactType.getId().equals(contactTypeId) || !contactNumber.matches(contactType.getCntpVisualFormtValidtion())) {
				throw new ValidateRecordException(environment.getProperty("contactNumber.not-match"),"contactNumber");
			}
			if (!contactType.getCntpStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("contactNumber.invalid"),"contactNumber");
			}
		}
		else {
			serviceValidationExceptionHadle(contactType.getServiceStatus(), ServiceEntity.CONTACT_TYPE);
		}
		return contactType;
		
	}
	
	/**
	 * @author Senitha
	 * 
     * Validate Asset Type
     * @param String tenantId   - tenantId
     * @param String assetTypeId   - assetTypeId
     * @param String assetTypeName   - assetTypeName
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of ContactType class
     * 
     */
	@Override
	public AssetTypeSubType validateSecurityType(String tenantId, Long assetTypeId, String assetTypeName) {

		String url = assetTypeUrl+tenantId+"/"+assetTypeId.toString();
		AssetTypeSubType assetTypeSubType = (AssetTypeSubType)getDetailFromService(url, AssetTypeSubType.class);
		if (assetTypeSubType == null) { 
			throw new ValidateRecordException(environment.getProperty("securityTypeId.not-match"),"securityTypeId");
		}
		if (assetTypeSubType.getServiceStatus() == null) {
			if (!assetTypeSubType.getId().equals(assetTypeId) || !assetTypeSubType.getName().equals(assetTypeName)) {
				throw new ValidateRecordException(environment.getProperty("securityTypeId.not-match"),"securityTypeId");
			}
			if (!assetTypeSubType.getStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("securityTypeId.invalid"),"securityTypeId");
			}
		}
		else {
			serviceValidationExceptionHadle(assetTypeSubType.getServiceStatus(), ServiceEntity.ASSET_TYPE_ID);
		}
		return assetTypeSubType;
		
	}
	
	/**
	 * @author Senitha
	 * 
     * Validate Asset Sub Type
     * @param String tenantId   - tenantId
     * @param String assetSubTypeId   - assetSubTypeId
     * @param String assetSubTypeName   - assetSubTypeName
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of ContactType class
     * 
     */
	@Override
	public AssetTypeSubType validateSecuritySubType(String tenantId, Long assetTypeId, Long assetSubTypeId, String assetSubTypeName) {

		String url = urlAssetSubType+tenantId+"/"+assetSubTypeId.toString();
		AssetTypeSubType assetTypeSubType = (AssetTypeSubType)getDetailFromService(url, AssetTypeSubType.class);
		if (assetTypeSubType == null) { 
			throw new ValidateRecordException(environment.getProperty("securitySubTypeId.not-match"),"securitySubTypeId");
		}
		if (assetTypeSubType.getServiceStatus() == null) {
			if (!assetTypeSubType.getId().equals(assetSubTypeId) || !assetTypeSubType.getName().equals(assetSubTypeName) 
					|| !assetTypeSubType.getAssetTypeId().equals(assetTypeId)) {
				throw new ValidateRecordException(environment.getProperty("securitySubTypeId.not-match"),"securitySubTypeId");
			}
			if (!assetTypeSubType.getStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("securitySubTypeId.invalid"),"securitySubTypeId");
			}
		}
		else {
			serviceValidationExceptionHadle(assetTypeSubType.getServiceStatus(), ServiceEntity.ASSET_SUB_TYPE_ID);
		}
		return assetTypeSubType;
		
	}

	/**
	 * @author Senitha
	 * 
     * Get Identification Type
     * @param String tenantId   - tenantId
     * @param String identificationTypeId   - id
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Identification Type
     * 
     */
	@Override
	public IdentificationType getIdentificationTypeName(String tenantId, Long id) {
		IdentificationType identificationType = (IdentificationType)getDetailFromService(tenantId, id.toString(), urlComnPersonIdentificationType, IdentificationType.class);
		return identificationType;
	}

	/**
	 * @author Senitha
	 * 
     * Get Contact Type
     * @param String tenantId   - tenantId
     * @param String conatactTypeId   - id
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of ContactType class
     * 
     */
	@Override
	public ContactType getContactType(String tenantId, Long id) {
		ContactType contactType = (ContactType)getDetailFromService(tenantId, id.toString(), urlComnPersonContactType, ContactType.class);
		return contactType;
	}

	/**
	 * @author Senitha
	 * 
     * Get Asset Type
     * @param String tenantId   - tenantId
     * @param String assetTypeId   - assetTypeId
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of ContactType class
     * 
     */
	@Override
	public AssetTypeSubType getSecurityType(String tenantId, Long assetTypeId) {
		AssetTypeSubType assetTypeSubType = (AssetTypeSubType)getDetailFromService(tenantId, assetTypeId.toString(), assetTypeUrl, AssetTypeSubType.class);
		return assetTypeSubType;
	}

	/**
	 * @author Senitha
	 * 
     * Get Asset Sub Type
     * @param String tenantId   - tenantId
     * @param String assetSubTypeId   - assetSubTypeId
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of ContactType class
     * 
     */
	@Override
	public AssetTypeSubType getSecuritySubType(String tenantId, Long assetSubTypeId) {
		AssetTypeSubType assetTypeSubType = (AssetTypeSubType)getDetailFromService(tenantId, assetSubTypeId.toString(), urlAssetSubType, AssetTypeSubType.class);
		return assetTypeSubType;
	}
	
	@Override
	public ResponseGuarantorValueResource createGuarantor(String tenantId, AddSuppliesBasicInfoRequestResource guarantorInfor,String createdUser) {
        try {
            String url = urlCreateGuarantor + tenantId + "/basic";
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", createdUser);
           
            HttpEntity <AddSuppliesBasicInfoRequestResource> guarantorDet = new HttpEntity<>(guarantorInfor, headers);
            
            ResponseGuarantorValueResource getResponse=restTemplate.exchange(url, HttpMethod.POST, guarantorDet, ResponseGuarantorValueResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("guarntor.service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}


	@Override
	public PersonCodeResponce generatePersonCode(String tenantId, String code) {
        	String url = urlPersonCode + tenantId + "/idRefCode/" + code;
        	return (PersonCodeResponce)getDetailFromService(url, PersonCodeResponce.class);
	}


	@Override
	public GuarantorCodeResponce generateGuarantorCode(String tenantId, String code) {
		String url = urlGuarantorCode + tenantId + "/idRefCode/" + code;
    	return (GuarantorCodeResponce)getDetailFromService(url, GuarantorCodeResponce.class);
	}


	@Override
	public ResponseGuarantorIdentificationResource createGuarantorIdentification(String tenantId,AddPersonIdentificationRequestResource guarantorIdentification, String createdUser) {
        try {
            String url = urlCreateGuarantorIdenti + tenantId + "/" +guarantorIdentification.getPsulpId();
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", createdUser);
           
            HttpEntity <AddPersonIdentificationRequestResource> guarantorIdentificationDet = new HttpEntity<>(guarantorIdentification, headers);
            
            ResponseGuarantorIdentificationResource getResponse=restTemplate.exchange(url, HttpMethod.POST, guarantorIdentificationDet, ResponseGuarantorIdentificationResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("guarntor.identi-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}


	@Override
	public ResponseGuarantorAddressResource createGuarantorAddress(String tenantId,	AddPersonAddressRequestResource guarantorAddressRequest, String createdUser) {
		try {
            String url = urlCreateGuarantorAddress + tenantId + "/" +guarantorAddressRequest.getPsulpId();
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", createdUser);
           
            HttpEntity <AddPersonAddressRequestResource> guarantorAddressDet = new HttpEntity<>(guarantorAddressRequest, headers);
            
            ResponseGuarantorAddressResource getResponse=restTemplate.exchange(url, HttpMethod.POST, guarantorAddressDet, ResponseGuarantorAddressResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("guarntor.address-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}


	@Override
	public ResponseGuarantorContactResource createGuarantorContact(String tenantId,	AddPersonContactRequestResource guarantorContactRequest, String createdUser) {
		try {
            String url = urlCreateGuarantorContact + tenantId + "/" +guarantorContactRequest.getPsulpId();
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", createdUser);
           
            HttpEntity <AddPersonContactRequestResource> guarantorContactDet = new HttpEntity<>(guarantorContactRequest, headers);
            
            ResponseGuarantorContactResource getResponse=restTemplate.exchange(url, HttpMethod.POST, guarantorContactDet, ResponseGuarantorContactResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("guarntor.contact-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}


	@Override
	public PendingSuppliesBasicInfoResponseResource getPendingPersonOrSuplies(String tenantId,GuarantorAddResource guarantorAddResource) {
		String url = urlPendingPersonSupplies  + tenantId + "/pending-supplies-or-person/GUARANTOR/" + guarantorAddResource.getPenPerId();
    	return (PendingSuppliesBasicInfoResponseResource)getDetailFromService(url, PendingSuppliesBasicInfoResponseResource.class);
	}

	@Override
	public ContactTypeResponse validationContactType(String tenantId, String id) {
		
		String url = urlComnPersonContactType+tenantId+"/"+id;
		ContactTypeResponse contactType = (ContactTypeResponse)getDetailFromService(url, ContactTypeResponse.class);
		if (contactType == null) { 
			throw new ValidateRecordException(environment.getProperty("contactTypeId.not-match"),"contactTypeId");
		}
		if (contactType.getServiceStatus() == null) {
			if (!contactType.getStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("contactTypeId.invalid"),"contactTypeId");
			}
		}
		else {
			serviceValidationExceptionHadle(contactType.getServiceStatus(), ServiceEntity.CONTACT_TYPE);
		}
		return contactType;
	}

	@Override
	public ResponseGuarantorUpdateResource updatePendingSuplies(String tenantId,PendingSuppliesBasicInfoResponseResource pendingSupplies, UpdateSuppliesBasicInfoRequestResource updateSupplies,String updateUser) {
		try {
            String url = urlPendingPersonSupplies + tenantId + "/basic/" + pendingSupplies.getId();
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", updateUser);
           
            HttpEntity <UpdateSuppliesBasicInfoRequestResource> updateSuppliesDet = new HttpEntity<>(updateSupplies, headers);
            
            ResponseGuarantorUpdateResource getResponse=restTemplate.exchange(url, HttpMethod.PUT, updateSuppliesDet, ResponseGuarantorUpdateResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("guarntor.update-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
		
	}


	@Override
	public SuppliesBasicInfoResponseResource getPersonOrSuplies(String tenantId,GuarantorAddResource guarantorAddResource) {
		String url = urlCreateGuarantor  + tenantId + "/supplier-or-person/" + guarantorAddResource.getPerId();
    	return (SuppliesBasicInfoResponseResource)getDetailFromService(url, SuppliesBasicInfoResponseResource.class);
	}


	@Override
	public ResponseGuarantorUpdateResource updateSuplies(String tenantId, SuppliesBasicInfoResponseResource suppliesDet,UpdateSuppliesBasicInfoRequestResource supplies, String updateUser) {
		try {
            String url = urlCreateGuarantor  + tenantId + "/basic/" + suppliesDet.getId();
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", updateUser);
           
            HttpEntity <UpdateSuppliesBasicInfoRequestResource> updateSuppliesDet = new HttpEntity<>(supplies, headers);
            
            ResponseGuarantorUpdateResource getResponse=restTemplate.exchange(url, HttpMethod.PUT, updateSuppliesDet, ResponseGuarantorUpdateResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("guarntor.update-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}
	
	@Override
	public Long initiateWorkFlowDynamic(String url, DynaBPMStaticParamsResource bpmRequestResource, WorkflowType workflowType) {
		try {
			HttpHeaders headers = new HttpHeaders();
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	        headers.setBasicAuth(usernameBPM, passwordBPM);
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        
			HttpEntity <DynaBPMStaticParamsResource> entity = new HttpEntity<>(bpmRequestResource, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, Long.class).getBody();
		} catch (RestClientException e) {
			if(e.getMessage().contains(HttpStatus.SERVICE_UNAVAILABLE.toString())) 
				throw new WorkFlowException(environment.getProperty("common.not-available"), workflowType);
			else if(e.getMessage().contains(HttpStatus.INTERNAL_SERVER_ERROR.toString())) 
				throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
			else if(e.getMessage().contains(HttpStatus.NOT_FOUND.toString())) 
				throw new WorkFlowException(environment.getProperty("common.not-found"), workflowType);
			else if(e.getMessage().contains(HttpStatus.BAD_GATEWAY.toString())) 
				throw new WorkFlowException(environment.getProperty("common.bad-request"), workflowType);
			else 
				throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
		}
	}


	@Override
	public CustomerCodeResponce generateCustomerCode(String tenantId, String code) {
    	String url = urlCustomerCode  + tenantId + "/idRefCode/" + code;
    	return (CustomerCodeResponce)getDetailFromService(url, CustomerCodeResponce.class);
	}


	@Override
	public ResponseCustomerValueResource createCustomer(String tenantId,AddCustomerBasicInfoRequestResource customerInfor, String userName) {
        try {
            String url = urlCommonCustomer  + tenantId + "/basic";
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <AddCustomerBasicInfoRequestResource> customerDet = new HttpEntity<>(customerInfor, headers);
            
            ResponseCustomerValueResource getResponse=restTemplate.exchange(url, HttpMethod.POST, customerDet, ResponseCustomerValueResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("guarntor.service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}


	@Override
	public ResponseCustomerAddressResource createCustomerAddress(String tenantId,AddPersonAddressRequestResource customerAddressRequest, String userName) {
		try {
            String url = urlCustomerAddress + tenantId + "/" +customerAddressRequest.getPsulpId();
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <AddPersonAddressRequestResource> customerAddressDet = new HttpEntity<>(customerAddressRequest, headers);
            
            ResponseCustomerAddressResource getResponse=restTemplate.exchange(url, HttpMethod.POST, customerAddressDet, ResponseCustomerAddressResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("customer.address-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}


	@Override
	public ResponseCustomerIdentificationResource createCustomerIdentification(String tenantId,AddPersonIdentificationRequestResource customerIdentification, String userName) {
        try {
            String url = urlCustomerIdenti  + tenantId + "/" +customerIdentification.getPsulpId();
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <AddPersonIdentificationRequestResource> customerIdentificationDet = new HttpEntity<>(customerIdentification, headers);
            
            ResponseCustomerIdentificationResource getResponse=restTemplate.exchange(url, HttpMethod.POST, customerIdentificationDet, ResponseCustomerIdentificationResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("guarntor.identi-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}


	@Override
	public ResponseCustomerContactResource createCustomerContact(String tenantId,AddPersonContactRequestResource customerContactRequest, String userName) {
		try {
            String url = urlCustomerContact + tenantId + "/" +customerContactRequest.getPsulpId();
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <AddPersonContactRequestResource> customerContactDet = new HttpEntity<>(customerContactRequest, headers);
            
            ResponseCustomerContactResource getResponse=restTemplate.exchange(url, HttpMethod.POST, customerContactDet, ResponseCustomerContactResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("guarntor.contact-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}


	@Override
	public CustomerBasicInfoResponseResource getPersonOrCustomer(String tenantId,@Valid CustomerResource customerResource) {
		String url = urlCommonCustomer  + tenantId + "/customer-or-person/" + customerResource.getPerId();
    	return (CustomerBasicInfoResponseResource)getDetailFromService(url, CustomerBasicInfoResponseResource.class);
	}


	@Override
	public ResponseCustomerValueResource updateCustomer(String tenantId,UpdateCustomerBasicInfoRequestResource customerDet, CustomerBasicInfoResponseResource customer,String userName) {  
		try {
            String url = urlCommonCustomer   + tenantId + "/basic/" + customer.getId();
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <UpdateCustomerBasicInfoRequestResource> updateCustomerDet = new HttpEntity<>(customerDet, headers);
            
            ResponseCustomerValueResource getResponse=restTemplate.exchange(url, HttpMethod.PUT, updateCustomerDet, ResponseCustomerValueResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("guarntor.update-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}


	@Override
	public PendingCustomerBasicInfoResponseResource getPendingPersonOrCustomer(String tenantId,CustomerResource customerResource) {
		String url = urlPendingCustomer   + tenantId + "/pending-customer-or-person/" + customerResource.getPenPerId();
		
    	return (PendingCustomerBasicInfoResponseResource)getDetailFromService(url, PendingCustomerBasicInfoResponseResource.class);
	}


	@Override
	public ResponseCustomerValueResource updatePendingCustomer(String tenantId,UpdateCustomerBasicInfoRequestResource customerDet, CustomerResource customerResource, String userName) {
		try {
            String url = urlPendingCustomer  + tenantId + "/basic/" + customerDet.getPerId();
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <UpdateCustomerBasicInfoRequestResource> updatePendingCustomerDet = new HttpEntity<>(customerDet, headers);
            
            ResponseCustomerValueResource getResponse=restTemplate.exchange(url, HttpMethod.PUT, updatePendingCustomerDet, ResponseCustomerValueResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("guarntor.update-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}


	@Override
	public List<PendingCustomerFinishResponce> bulkFinishPendingCustomer(String tenantId,PendingCutomerApproveRequestResource customerIds, String userName) {
        try {
            String url = urlCommonCustomer  + tenantId + "/bulk-finish";
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <PendingCutomerApproveRequestResource> cusIds = new HttpEntity<>(customerIds, headers);
            
            PendingCustomerFinishResponce[] getResponse=restTemplate.exchange(url, HttpMethod.POST, cusIds, PendingCustomerFinishResponce[].class).getBody();
           
            if(getResponse!=null) {
            	List<PendingCustomerFinishResponce> customerFinishResponces = Arrays.asList(getResponse);
            	return customerFinishResponces;
            }
            else
                throw new OtherException(environment.getProperty("guarntor.service-error"), HttpStatus.BAD_REQUEST);
           
        } catch (Exception e) {
        	if(e.getMessage().contains("503")) {
    			throw new PersonServiceException(environment.getProperty("common.not-available"));
    		}else if(e.getMessage().contains("500")) {
    			throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
    		}else if(e.getMessage().contains("404")) {
    			throw new PersonServiceException(environment.getProperty("common.not-found"));
    		}else if(e.getMessage().contains("400")) {
    			throw new PersonServiceException(environment.getProperty("common.bad-request"));
    		}else if(e.getMessage().contains("504")) {
    			throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
    		}else {
    			throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
    		}
		} 
	}
	
	@Override
	public List<PendingSuppliesFinishResponce> bulkFinishPendingSupplies(String tenantId,PendingSuppliesApproveRequestResource supplierIds, String userName) {
        try {
            String url = urlComnSuppliesEntities  + tenantId + "/bulk-finish";
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <PendingSuppliesApproveRequestResource> supIds = new HttpEntity<>(supplierIds, headers);
            
            PendingSuppliesFinishResponce[] getResponse=restTemplate.exchange(url, HttpMethod.POST, supIds, PendingSuppliesFinishResponce[].class).getBody();
           
            if(getResponse!=null) {
            	List<PendingSuppliesFinishResponce> customerFinishResponces = Arrays.asList(getResponse);
            	return customerFinishResponces;
            }
            else
                throw new OtherException(environment.getProperty("guarntor.service-error"), HttpStatus.BAD_REQUEST);
           
        } catch (Exception e) {
        	if(e.getMessage().contains("503")) {
    			throw new PersonServiceException(environment.getProperty("common.not-available"));
    		}else if(e.getMessage().contains("500")) {
    			throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
    		}else if(e.getMessage().contains("404")) {
    			throw new PersonServiceException(environment.getProperty("common.not-found"));
    		}else if(e.getMessage().contains("400")) {
    			throw new PersonServiceException(environment.getProperty("common.bad-request"));
    		}else if(e.getMessage().contains("504")) {
    			throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
    		}else {
    			throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
    		}
		} 
	}


	@Override
	public ComnSuppliesEntitiesResource validateValuer(String tenantId, Long valuerId, String valuerRefCode) {
		
		ComnSuppliesEntitiesResource comnSuppliesEntitiesResource = (ComnSuppliesEntitiesResource)remoteService.checkIsExist(tenantId, valuerId.toString(), urlComnSuppliesEntities, ComnSuppliesEntitiesResource.class);
		if (comnSuppliesEntitiesResource == null) { 
			throw new DetailValidateException(environment.getProperty("valuer.not-match"),
					ServiceEntity.VALUER_ID, ServicePoint.COL_VALUATION_DETAILS);
		}
		if (comnSuppliesEntitiesResource.getServiceStatus() == null) {
			if (comnSuppliesEntitiesResource == null || !comnSuppliesEntitiesResource.getId().equals(valuerId) || !comnSuppliesEntitiesResource.getSupReferenceCode().equals(valuerRefCode)) {
				throw new DetailValidateException(environment.getProperty("valuer.not-match"),
						ServiceEntity.VALUER_ID, ServicePoint.COL_VALUATION_DETAILS);
			}
			if (!comnSuppliesEntitiesResource.getSupStatus().equals("ACTIVE") || !comnSuppliesEntitiesResource.getSupSuppliesType().equals("VALUATION_AGENT")) {
				throw new DetailValidateException(environment.getProperty("valuer.invalid"),
						ServiceEntity.VALUER_ID, ServicePoint.COL_VALUATION_DETAILS);
			}
		}
		else {
			serviceValidationExceptionHadle(comnSuppliesEntitiesResource.getServiceStatus(), ServiceEntity.VALUER_ID);
		}
		return comnSuppliesEntitiesResource;
		
	}


	@Override
	public LastValuationDateResponseResource validateLastValuationDate(String tenantId, Long assetEntityId) {
		LastValuationDateResponseResource lastValuationDateResponseResource = (LastValuationDateResponseResource)remoteService.checkLastValuationDate(tenantId, assetEntityId, urlColValuationDetails, LastValuationDateResponseResource.class);
		if (lastValuationDateResponseResource != null) { 
			lastValuationDateResponseResource.setValuationsExists(Boolean.TRUE);
		}
	
		return lastValuationDateResponseResource;
	}


	@Override
	public void futureDateValidation(String date, ServiceEntity serviceEntity) {
		Date formatDate=formatDate(date);
		if( formatDate!=null && formatDate.after(new Date())) {
			throw new ValidateRecordException(environment.getProperty("date.future"),"valuationDate");
		  //throw new InvalidServiceIdException(environment.getProperty("date.future"), serviceEntity);
		}
		
	}
	
	@Override
	public void pastDateValidation(String date, ServiceEntity serviceEntity) {
		Date formatDate=formatDate(date);
		String sysDateStr=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Date sysDate= formatDate(sysDateStr);
		if( formatDate!=null && formatDate.before(sysDate)) {
			throw new ValidateRecordException(environment.getProperty("date.past"),"valuationDate");
			//throw new InvalidServiceIdException(environment.getProperty("date.past"), serviceEntity);
		}
	}	

	
//	@Override
//	public void valuationDetInteregation(String tenantId, String createdUser,ValuationDetailsAddResource valuationDetails, Long assetEntityId) {
//		
//		ColInetragationRequestResponceResource colInetragationRequestResponceResource = (ColInetragationRequestResponceResource) remoteService.valuationsIntegratewithCol(tenantId, 
//				createdUser, valuationDetails, assetEntityId, ColInetragationRequestResponceResource.class);
//		if(colInetragationRequestResponceResource == null || colInetragationRequestResponceResource.getServiceStatus()==null) {
//			if(colInetragationRequestResponceResource==null || colInetragationRequestResponceResource.getMessages().isEmpty()
//						|| colInetragationRequestResponceResource.getMessages() == null)
//				throw new ValidateRecordException(environment.getProperty("collateral.not-match"),"message");
//			if(colInetragationRequestResponceResource.getValue()!= null)
//				colInetragationRequestResponceResource.getValue();
//		
//		} else {
//				throw new ValidateRecordException(environment.getProperty("collateral.not-match"),"message");
//		}
//			
//	}
	
	@Override
	public Long valuationDetInteregation(String tenantId, String createdUser, ValuationDetailsListAddResource valuationDetailsAddResourceList, Long assetEntityId) {
		Long getValue = null;	
		//get document details
		ColInetragationRequestResponceResource colInetragationRequestResponceResource = (ColInetragationRequestResponceResource) remoteService.valuationsIntegratewithCol(tenantId, 
				createdUser, valuationDetailsAddResourceList, assetEntityId, ColInetragationRequestResponceResource.class);
		if(colInetragationRequestResponceResource == null || colInetragationRequestResponceResource.getServiceStatus()==null ) {
			
			if(colInetragationRequestResponceResource.getValue()!=null) {
				getValue = Long.parseLong(colInetragationRequestResponceResource.getValue());
			}else if(colInetragationRequestResponceResource==null ||( colInetragationRequestResponceResource.getServiceStatus()==null &&
					colInetragationRequestResponceResource.getMessages()== null)) {
				if(colInetragationRequestResponceResource.getVersion() !=null) {
					throw new DetailValidateException(environment.getProperty(colInetragationRequestResponceResource.getVersion()), ServiceEntity.VERSION,ServicePoint.COL_VALUATION_DETAILS);

				}else
					throw new ValidateRecordException(environment.getProperty("valuation.save.failed"),"message");
				
			}else if(colInetragationRequestResponceResource==null ||( colInetragationRequestResponceResource.getServiceStatus()==null &&
					colInetragationRequestResponceResource.getMessages()!= null)){
				throw new DetailValidateException(colInetragationRequestResponceResource.getMessages(), ServiceEntity.COL_VALUATION_DETAILS, ServicePoint.COL_VALUATION_DETAILS);	
			}
			
		} else {
				throw new ValidateRecordException(environment.getProperty("collateral.not-match"),"message");
		}
		return getValue;
			
	}
	
	@Override
	public void valuationDetUpdateInteregation(String tenantId, String createdUser, ValuationDetailsUpdateResource valuationDetails) {
		ColInetragationRequestResponceResource colInetragationRequestResponceResource = (ColInetragationRequestResponceResource) remoteService.valuationUpdateIntegratewithCol(tenantId, 
				createdUser, valuationDetails, ColInetragationRequestResponceResource.class);
		if(colInetragationRequestResponceResource == null || colInetragationRequestResponceResource.getServiceStatus()==null) {
			if(colInetragationRequestResponceResource==null ||( colInetragationRequestResponceResource.getServiceStatus()==null &&
					colInetragationRequestResponceResource.getMessages()== null)) {
				if(colInetragationRequestResponceResource.getVersion() !=null) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getVersion(), ServiceEntity.VERSION,ServicePoint.COL_VALUATION_DETAILS);
				}else if (colInetragationRequestResponceResource.getValuationDetailsId()!=null || colInetragationRequestResponceResource.getMessage() != null ) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getMessage(), ServiceEntity.COL_VALUATION_DETAILS,
							ServicePoint.COL_VALUATION_DETAILS);		
				
				}else
					throw new ValidateRecordException(environment.getProperty("valuation.save.failed"),"message");
				
			}	
		
		} else {
				throw new ValidateRecordException(environment.getProperty("collateral.not-match"),"message");
		}
		
	}


	@Override
	public Long insuranceDetAddColInteregation(String tenantId, String createdUser,
			InsuranceDetailsAddResource insuranceDetails, ActionType actionType) {
		ColInetragationRequestResponceResource colInetragationRequestResponceResource = null;
		if(actionType == ActionType.SAVE) {
			colInetragationRequestResponceResource = (ColInetragationRequestResponceResource) remoteService.insuranceAddIntegratewithCol(tenantId, 
				createdUser, insuranceDetails, ColInetragationRequestResponceResource.class);
		}else if(actionType == ActionType.UPDATE) {
			 colInetragationRequestResponceResource = (ColInetragationRequestResponceResource) remoteService.insuranceUPdateIntegratewithCol(tenantId, 
				createdUser, insuranceDetails, ColInetragationRequestResponceResource.class);
			
		}
		
		if(colInetragationRequestResponceResource == null || colInetragationRequestResponceResource.getServiceStatus()==null) {
			if(colInetragationRequestResponceResource==null ||( colInetragationRequestResponceResource.getServiceStatus()==null &&
					colInetragationRequestResponceResource.getMessages()== null)) {
				if(colInetragationRequestResponceResource.getVersion() !=null) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getVersion(), ServiceEntity.VERSION,ServicePoint.COL_INSURANCE_DETAILS);
				}else if (colInetragationRequestResponceResource.getAssetsEntity() !=null) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getAssetsEntity(), ServiceEntity.ASSETS_ENTITY_ID,
							ServicePoint.COL_INSURANCE_DETAILS);
				}else if (colInetragationRequestResponceResource.getInsuranceTypeId()!=null) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getInsuranceTypeId().toString(), ServiceEntity.INSURANCE_TYPE,
							ServicePoint.COL_INSURANCE_DETAILS);
				}else if (colInetragationRequestResponceResource.getInsuranceType() !=null) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getInsuranceType(), ServiceEntity.INSURANCE_TYPE,
							ServicePoint.COL_INSURANCE_DETAILS);
				}else if (colInetragationRequestResponceResource.getInsuranceTypeCode() !=null) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getInsuranceTypeCode(), ServiceEntity.INSURANCE_TYPE_CODE,
							ServicePoint.COL_INSURANCE_DETAILS);
				}else if (colInetragationRequestResponceResource.getInsuranceCompanyMaint() !=null ) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getInsuranceCompanyMaint(), ServiceEntity.INSU_COMP_MAINT,
							ServicePoint.COL_INSURANCE_DETAILS);	
				}else if (colInetragationRequestResponceResource.getInsuranceCompanyMaintCode() !=null) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getInsuranceCompanyMaintCode(), ServiceEntity.INSU_COMP_MAINT_CODE,
							ServicePoint.COL_INSURANCE_DETAILS);						
				}else if (colInetragationRequestResponceResource.getInsuranceCoverType() !=null ) {
					throw new DetailValidateException(environment.getProperty(colInetragationRequestResponceResource.getInsuranceCoverType()), ServiceEntity.INSU_COVER_TYPE,
							ServicePoint.COL_INSURANCE_DETAILS);
				}else if (colInetragationRequestResponceResource.getInsuranceCoverTypeCode() !=null) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getInsuranceCoverTypeCode(), ServiceEntity.INSU_COVER_TYPE_CODE,
							ServicePoint.COL_INSURANCE_DETAILS);					
				}else if (colInetragationRequestResponceResource.getInsuranceSubType() !=null ) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getInsuranceSubType(), ServiceEntity.INSU_SUB_TYPE,
							ServicePoint.COL_INSURANCE_DETAILS);		
				}else if (colInetragationRequestResponceResource.getCoverNoteNumber() !=null ) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getCoverNoteNumber(), ServiceEntity.COVER_NOTE_NO,
							ServicePoint.COL_INSURANCE_DETAILS);	
				}else if (colInetragationRequestResponceResource.getCoverNotePeriodFrom() !=null ) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getCoverNotePeriodFrom(), ServiceEntity.COVER_NOTE_PERIOD_FROM,
							ServicePoint.COL_INSURANCE_DETAILS);	
				}else if (colInetragationRequestResponceResource.getCoverNotePeriodTo() !=null ) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getCoverNotePeriodTo(), ServiceEntity.COVER_NOTE_PERIOD_TO,
							ServicePoint.COL_INSURANCE_DETAILS);	
				}else if (colInetragationRequestResponceResource.getCoverNoteStatus() !=null ) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getCoverNoteStatus(), ServiceEntity.COVER_NOTE_STATUS,
							ServicePoint.COL_INSURANCE_DETAILS);
				}else if (colInetragationRequestResponceResource.getPolicyNo() !=null ) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getPolicyNo(), ServiceEntity.POLICY_NO,
							ServicePoint.COL_INSURANCE_DETAILS);	
				}else if (colInetragationRequestResponceResource.getPolicyCoverPeriodFrom() !=null ) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getPolicyCoverPeriodFrom(), ServiceEntity.POLICY_PERIOD_FROM,
							ServicePoint.COL_INSURANCE_DETAILS);		
				}else if (colInetragationRequestResponceResource.getPolicyCoverPeriodTo() !=null ) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getPolicyCoverPeriodTo(), ServiceEntity.POLICY_PERIOD_TO,
							ServicePoint.COL_INSURANCE_DETAILS);	
				}else if (colInetragationRequestResponceResource.getNextRenewalDate() !=null ) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getNextRenewalDate(), ServiceEntity.NEXT_RENEW_DATE,
							ServicePoint.COL_INSURANCE_DETAILS);
				}else if (colInetragationRequestResponceResource.getInsuranceDetailsId() !=null) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getInsuranceDetailsId(), ServiceEntity.INSU_DETAIL_ID,
							ServicePoint.COL_INSURANCE_DETAILS);	
				}else if (colInetragationRequestResponceResource.getMessage() != null ) {
					throw new DetailValidateException(colInetragationRequestResponceResource.getMessage(), ServiceEntity.INSU_DETAIL_ID,
							ServicePoint.COL_INSURANCE_DETAILS);	
					
				}else
					throw new ValidateRecordException(environment.getProperty("insuarance.save.failed"),"message");	
			}	
		
		} else {
				throw new ValidateRecordException(environment.getProperty("collateral.not-match"),"message");
		}
		return null;
	}


	@Override
	public CurencyResponse validateCurrency(String tenantId, String applicableCurrencyId, String comnCurrencyUrl,
			String applicableCurrencyCode, Class<CurencyResponse> class1) {

	 		CurencyResponse currency=(CurencyResponse)getDetailFromService(tenantId, applicableCurrencyId, comnCurrencyUrl, CurencyResponse.class);
	
	 		if(currency==null || currency.getServiceStatus()==null) {
	 			if(currency==null || !currency.getCurrencyCode().equalsIgnoreCase(applicableCurrencyCode) 
	 					|| !currency.getCurrencyStatus().equalsIgnoreCase("ACTIVE")) {
	 				throw new InvalidServiceIdException(environment.getProperty("common.not-available"), ServiceEntity.INSURANCE_DETAIL_CURRENCY_ID);
	 			}
	 			
	 		}else {
	 			throw new InvalidServiceIdException(currency.getServiceStatus().toString(), ServiceEntity.INSURANCE_DETAIL_CURRENCY_ID);
	 		}
	 		LoggerRequest.getInstance().logInfo1("******<<<<<<#######13######>>>>>>******");
	 		return currency;
		 }
	
	
	/** 
	 * get id and description from service
	 * @param Url
	 * @param Description
	 * @param tenantId
	 * @return Object
	 */
	 private Object getDetailFromService(String tenantId, String id, String serviceUrl, Class<?> classObject) {
	     try {
	       HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	       headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	       HttpEntity <String> entity = new HttpEntity<>(headers);
	       
	       System.out.println(serviceUrl+tenantId+"/"+id);
	       
	       return restTemplate.exchange(serviceUrl+tenantId+"/"+id, HttpMethod.GET, entity, classObject).getBody();
	       
	     
	       
	     } catch (RestClientException e) {
	         String result=null;
	         if(e.getMessage().contains("503")) {
	             result=serviceStatus +ServiceStatus.NOT_AVAILABLE.toString()+"\"}";
	         }else if(e.getMessage().contains("500")) {
	             result=serviceStatus+ ServiceStatus.EXCEPTION.toString()+"\"}";
	         }else if(e.getMessage().contains("404")) {
	             result=serviceStatus+ ServiceStatus.NOT_FOUND.toString()+"\"}";
	         }else if(e.getMessage().contains("400")) {
	             result=serviceStatus+ ServiceStatus.BAD_REQUEST.toString()+"\"}";
	         }
	         ObjectMapper mapper = new ObjectMapper();
	         try {
	             return mapper.readValue(result, classObject);
	         } catch (IOException e1) {
	             return null;
	         }
	     }
	 }


	@Override
	public AssetEntityResponseResource validateAssetEntity(String tenantId, Long assetEntityId, ServicePoint servicePoint) {
		AssetEntityResponseResource assetEntityIdResponseResource = (AssetEntityResponseResource) remoteService.checkIsExistInsuAsset(tenantId, 
				assetEntityId.toString(), urlAssetEntity,AssetEntityResponseResource.class);
		if (assetEntityIdResponseResource == null || assetEntityIdResponseResource.getServiceStatus() == null) {
			if (assetEntityIdResponseResource == null
					|| assetEntityIdResponseResource.getStatus().equals(CommonStatus.INACTIVE)) {
				throw new DetailValidateException(environment.getProperty("common.not-match"),
						ServiceEntity.ASSETS_ENTITY_ID, servicePoint);
				
			}
		} else {
			throw new DetailValidateException(assetEntityIdResponseResource.getServiceStatus().toString(),
					ServiceEntity.ASSETS_ENTITY_ID, servicePoint);
		}
		return assetEntityIdResponseResource;
		
	}


	@Override
	public void validateAssetEntityInsuDetails(String tenantId, String insuId, Long assetEntityId, ActionType actionType, String status) {
		InsuranceDetailsResponseResource insuranceDetailsResponseList = (InsuranceDetailsResponseResource) remoteService.checkIsExistInsuDetails(tenantId, 
				assetEntityId.toString(), urlAssetEntity,InsuranceDetailsResponseResource.class);
		//if(insuranceDetailsResponseList.getInsuranceDetailsRequestResource().size()>0) {
		if(insuranceDetailsResponseList != null) {
			if(actionType.equals(ActionType.SAVE)) {
				for(InsuranceDetailsRequestResource insuranceDetailsRequestResource : insuranceDetailsResponseList.getInsuranceDetailsRequestResource()) {
						if(insuranceDetailsRequestResource.getStatus().equals(CommonStatus.ACTIVE) && CommonStatus.valueOf(status).equals(CommonStatus.ACTIVE))
								throw new DetailValidateException(environment.getProperty("active-insurance-det-exist"),
										ServiceEntity.ASSETS_ENTITY_ID, ServicePoint.COL_INSURANCE_DETAILS);	
				}

			}else if(actionType.equals(ActionType.UPDATE)){
				for(InsuranceDetailsRequestResource insuranceDetailsRequestResource : insuranceDetailsResponseList.getInsuranceDetailsRequestResource()) {
					if(insuId !=null)
					if(insuranceDetailsRequestResource.getStatus().equals(CommonStatus.ACTIVE) && !insuranceDetailsRequestResource.getId().equals(insuId))
							throw new DetailValidateException(environment.getProperty("active-insurance-det-exist"),
									ServiceEntity.ASSETS_ENTITY_ID, ServicePoint.COL_INSURANCE_DETAILS);	
				}
				}
		}
		
	}

	

	@Override
	public ValuationAndInsuranceDetListResponseResource getValuationsForasset(String tenantId, Long assetEntityId) {
		ValuationAndInsuranceDetListResponseResource valuationList = remoteService.checkIsExitingAssetValuations(tenantId, assetEntityId, 
				urlColValuationDetails, ValuationAndInsuranceDetListResponseResource.class);
		if (valuationList == null || valuationList.getServiceStatus() != null)
			throw new InvalidServiceIdException(valuationList.getServiceStatus().toString(), ServiceEntity.COL_VALUATION_DETAILS);
			
		return valuationList;
	}
	
	@Override
	public ValuationAndInsuranceDetListResponseResource getInsuranceForasset(String tenantId, Long assetEntityId) {
		ValuationAndInsuranceDetListResponseResource insuList = remoteService.checkIsExitingAssetInsurance(tenantId, assetEntityId, 
				urlColValuationDetails, ValuationAndInsuranceDetListResponseResource.class);
		if (insuList == null || insuList.getServiceStatus() != null)
			throw new InvalidServiceIdException(insuList.getServiceStatus().toString(), ServiceEntity.COL_INSURANCE_DETAILS);
			
		return insuList;
	}
	

	public ResponseCustomerRelationshipResource saveCommonCustomerRelationship(String tenantId, String createdUser,AddRelationshipBasicInfoRequestResource addRelationshipBasicInfoRequestResource,Long pendingCustomerId) {
		try {
            String url = urlCustomerRelationship + tenantId + "/basic/" + pendingCustomerId;
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", createdUser);
           
            HttpEntity <AddRelationshipBasicInfoRequestResource> insertCustomerRelationship = new HttpEntity<>(addRelationshipBasicInfoRequestResource, headers);
            
            ResponseCustomerRelationshipResource getResponse=restTemplate.exchange(url, HttpMethod.POST, insertCustomerRelationship, ResponseCustomerRelationshipResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("customer-relationship.insert-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}

	
	@Override
	public List<PendingPersonResponseIdentificationResource> getAllPendingPersonIdentification(String tenantId,	String penCustomerrId) {
		String url = urlPendingCustomerIdentification   + tenantId + "/" + penCustomerrId + "/all";
		try {
			return getAllPendingPersonIdentificationList(url );
		} catch (RestClientException e) {
			if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
		}
    	
	}
	
	private List<PendingPersonResponseIdentificationResource> getAllPendingPersonIdentificationList(String url) {
		  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <String> entity = new HttpEntity<>(headers);
	      ResponseEntity<PendingPersonResponseIdentificationResource[]> responseVal = restTemplate.exchange(url, HttpMethod.GET, entity, PendingPersonResponseIdentificationResource[].class);
	      if(responseVal.getBody()!=null)
	    	  return Arrays.stream(responseVal.getBody()).collect(Collectors.toList());
	      else return null;
		}
	

	@Override
	public ResponseCustomerRelationshipIdentificationResource saveCommonCustomerRelationshipIdentification(String tenantId, String createdUser,AddCustomerPersonIdentificationRequestResource addPersonIdentificationRequestResource,Long pendingCustomerId) {
		try {
            String url = urlCustomerLinkPerson   + tenantId + "/identification/" + pendingCustomerId;
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", createdUser);
           
            HttpEntity <AddCustomerPersonIdentificationRequestResource> insertCustomerRelationshipIdentification = new HttpEntity<>(addPersonIdentificationRequestResource, headers);
            
            ResponseCustomerRelationshipIdentificationResource getResponse=restTemplate.exchange(url, HttpMethod.POST, insertCustomerRelationshipIdentification, ResponseCustomerRelationshipIdentificationResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("customer-relationship-identification.insert-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}

	
	@Override
	public CustomerOnBoardResource getOnboardPendingCustomerDetail(String tenantId, Long pCusId) {
		String url = urlCustomerOnboard   + tenantId + "/pending-customer/" + pCusId;
		
    	return (CustomerOnBoardResource)getDetailFromService(url, CustomerOnBoardResource.class);
	}
	

	@Override
	public ResponseCustomerOnboardingResource saveCustomerOnboarding(String tenantId,AddCustomerOnBoardRequestResource addCustomerOnBoardRequestResource,String userName) {
		try {
            String url = urlCustomerOnboard   + tenantId ;
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <AddCustomerOnBoardRequestResource> customerOnBoardRequest = new HttpEntity<>(addCustomerOnBoardRequestResource, headers);
            
            ResponseCustomerOnboardingResource getResponse=restTemplate.exchange(url, HttpMethod.POST, customerOnBoardRequest, ResponseCustomerOnboardingResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("customer-on-board.insert-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}


	@Override
	public ResponseCustomerOnboardProductResource saveCustomerOnboardProduct(String tenantId,AddCustomerOnBoardProductRequestResource addCustomerOnBoardProductRequestResource, String userName) {
		try {
            String url = urlCustomerOnboardProduct   + tenantId ;
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <AddCustomerOnBoardProductRequestResource> customerOnBoardProductRequest = new HttpEntity<>(addCustomerOnBoardProductRequestResource, headers);
            
            ResponseCustomerOnboardProductResource getResponse=restTemplate.exchange(url, HttpMethod.POST, customerOnBoardProductRequest, ResponseCustomerOnboardProductResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("customer-on-board-product.insert-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}
	
	@Override
	public SuccessAndErrorDetailsResource bulkFinishAssetEntity(String tenantId,AssertApproveRequestResource approveRequestResource, String userName) {
        try {
            String url = urlAssetDetail  + tenantId + "/bulk-approve";
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <AssertApproveRequestResource> entity = new HttpEntity<>(approveRequestResource, headers);
            
            SuccessAndErrorDetailsResource getResponse=restTemplate.exchange(url, HttpMethod.POST, entity, SuccessAndErrorDetailsResource.class).getBody();
           
           return getResponse;
           
        } catch (Exception e) {
        	if(e.getMessage().contains("503")) {
    			throw new PersonServiceException(environment.getProperty("common.not-available"));
    		}else if(e.getMessage().contains("500")) {
    			throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
    		}else if(e.getMessage().contains("404")) {
    			throw new PersonServiceException(environment.getProperty("common.not-found"));
    		}else if(e.getMessage().contains("400")) {
    			throw new PersonServiceException(environment.getProperty("common.bad-request"));
    		}else if(e.getMessage().contains("504")) {
    			throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
    		}else {
    			throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
    		}
		} 
	}

	@Override
	public void validatePerCommonListDet(String tenantId, String genderComListId, String relationGender, ServicePoint relExternalCrib, String refCode) {
		PerCommonList perCommonListRemote = (PerCommonList) remoteService.isExistPerCommonList(tenantId, genderComListId, CommonListReferenceCode.valueOf(refCode),
				urlPersonCommonList ,PerCommonList.class);
		if (perCommonListRemote == null || perCommonListRemote.getServiceStatus() == null) {
			if (perCommonListRemote == null || !perCommonListRemote.getCmlsDesc().equalsIgnoreCase(relationGender) || !perCommonListRemote.getPcmlsStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString()))
				throw new DetailValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.valueOf(refCode), relExternalCrib);
		} else
			remoteService.serviceValidationExceptionHadle(perCommonListRemote.getServiceStatus(), ServiceEntity.valueOf(refCode), relExternalCrib);	
	}


	@Override
	public void validateComnPerson(String tenantId, String perId, String perCode, ServicePoint relExternalCrib) {
		String urlSufix = "exist/"+perId+"/"+perCode;
		PersonResponceValidateResource personResponceValidateResource = (PersonResponceValidateResource) remoteService.checkIsExist(tenantId, urlSufix, urlPerson+"person/" ,PersonResponceValidateResource.class);
		if (personResponceValidateResource == null || personResponceValidateResource.getServiceStatus() == null) {
			if (personResponceValidateResource == null || !personResponceValidateResource.getValue().equalsIgnoreCase("true"))
				throw new DetailValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.PERSON_ID, relExternalCrib);
		} else	
			remoteService.serviceValidationExceptionHadle(personResponceValidateResource.getServiceStatus(), ServiceEntity.PERSON_ID, relExternalCrib);
		
	}


	@Override
	public void validatePendingPerson(String tenantId, String penPerId, ServicePoint relExternalCrib) {
		String urlSufix = "valid/"+penPerId;
		PersonResponceValidateResource personResponceValidateResource = (PersonResponceValidateResource)  remoteService.checkIsExist(tenantId, urlSufix, urlPerson+"pending-person/" ,PersonResponceValidateResource.class);
		if (personResponceValidateResource == null || personResponceValidateResource.getServiceStatus() == null) {
			if (personResponceValidateResource == null || !personResponceValidateResource.getValue().equalsIgnoreCase("true"))
				throw new DetailValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.PPERSON_ID, relExternalCrib);
		} else
			remoteService.serviceValidationExceptionHadle(personResponceValidateResource.getServiceStatus(), ServiceEntity.PPERSON_ID, relExternalCrib);
	}

	@Override
	public ValidResource validate(String tenantId,Long perndId) {
		String url = urlPendingPerson  + tenantId + "/valid/" + perndId;
    	return (ValidResource)getDetailFromService(url, ValidResource.class);
	}

	
	@Override
	public void validateCorporateCategory(String tenantId, String corporateCategoryId, String corporateCategoryName, ServicePoint customer, String refCode) {
		CustomerSubTypeNonIndividual corporateCategoryPerCommonList=(CustomerSubTypeNonIndividual)remoteService.checkIsExist(tenantId, corporateCategoryId, urlComnCommonLegalStructure, CustomerSubTypeNonIndividual.class);
		if (corporateCategoryPerCommonList == null || corporateCategoryPerCommonList.getServiceStatus() == null) {
			if (corporateCategoryPerCommonList == null || !corporateCategoryPerCommonList.getName().equalsIgnoreCase(corporateCategoryName) || !corporateCategoryPerCommonList.getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString()))
				throw new DetailValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.valueOf(refCode), customer);
		} else
			remoteService.serviceValidationExceptionHadle(corporateCategoryPerCommonList.getServiceStatus(), ServiceEntity.valueOf(refCode), customer);	
	}

	@Override
	public boolean validateCommonListCribStatus(String cribStatusId, String cribStatus) {
		Boolean isExist = Boolean.FALSE;
		Optional<CommonList> commonList=commonListRepository.findById(remoteService.stringToLong(cribStatusId));
		if(commonList.isPresent() && commonList.get().getName().equalsIgnoreCase(cribStatus) && commonList.get().getReferenceCode().equalsIgnoreCase(CommonListReferenceCode.CRIB_STATUS.toString())
				&& commonList.get().getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
			isExist = Boolean.TRUE;
		}else {
			isExist = Boolean.FALSE;
		}
		return isExist;
	}
	
	/**
	 * @author Sanatha
	 * 
     * Validate Sector
     * @param String tenantId   - tenantId
     * @param Long sectorId   - sectorId
     * @param String sectorCode   - sectorCode
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Sector class
     * 
     */
	@Override
	public SectorResponseResource validateSector(String tenantId, Long sectorId,String sectorCode) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN validateSector>>>>>>******");
		
		String url = urlSector+tenantId+"/"+sectorId.toString();
		LoggerRequest.getInstance().logInfo1("******<<<<<<url>>>>>>******"+url);
		
		SectorResponseResource sectorResponseResource = (SectorResponseResource)getDetailFromService(url, SectorResponseResource.class);
		
		
		if (sectorResponseResource == null) { 
			throw new ValidateRecordException(environment.getProperty("sector.invalid"),"sectorId");
		}
		
		LoggerRequest.getInstance().logInfo1("******<<<<<<SectorCode>>>>>>******"+sectorResponseResource.getSectorCode());
		LoggerRequest.getInstance().logInfo1("******<<<<<<SectorName>>>>>>******"+sectorResponseResource.getSectorName());
		LoggerRequest.getInstance().logInfo1("******<<<<<<SectorStatus>>>>>>******"+sectorResponseResource.getSectorStatus());
		
		if (sectorResponseResource.getServiceStatus() == null) {
			if (!sectorResponseResource.getId().equals(sectorId) || !sectorResponseResource.getSectorCode().equals(sectorCode)) {
				throw new ValidateRecordException(environment.getProperty("sector.not-match"),"sectorId");
			}
			if (!sectorResponseResource.getSectorStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("sector.invalid-status"),"sectorId");
			}
		}
		else {
			serviceValidationExceptionHadle(sectorResponseResource.getServiceStatus(), ServiceEntity.SECTOR);
		}
		return sectorResponseResource;
		
	}
	
	/**
	 * @author Sanatha
	 * 
     * Get Sector name
     * @param String tenantId   - tenantId
     * @param Long sectorId   - sectorId
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Sector class
     * 
     */
	@Override
	public SectorResponseResource getSectorName(String tenantId, Long sectorId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN getSectorName>>>>>>******");
		
 		SectorResponseResource sectorResponseResource = (SectorResponseResource)getDetailFromService(tenantId, sectorId.toString(), urlSector, SectorResponseResource.class);
		
 		LoggerRequest.getInstance().logInfo1("******<<<<<<SectorCode>>>>>>******"+sectorResponseResource.getSectorCode());
		LoggerRequest.getInstance().logInfo1("******<<<<<<SectorName>>>>>>******"+sectorResponseResource.getSectorName());
		LoggerRequest.getInstance().logInfo1("******<<<<<<SectorStatus>>>>>>******"+sectorResponseResource.getSectorStatus());
		return sectorResponseResource;
	}
	
	/**
	 * @author Sanatha
	 * 
     * Validate Sub Sector
     * @param String tenantId   - tenantId
     * @param Long sectorId   - sectorId
     * @param Long subSectorId   - subSectorId
     * @param String subSectorCode   - subSectorCode
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Sub Sector class
     * 
     */
	@Override
	public SubSectorResponseResource validateSubSector(String tenantId, Long sectorId, Long subSectorId, String subSectorCode) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN validateSubSector>>>>>>******");
		
		String url = urlSubSector.trim()+tenantId+"/sector/"+sectorId.toString()+"/id/"+subSectorId.toString();
		LoggerRequest.getInstance().logInfo1("******<<<<<<url>>>>>>******"+url);
		
		SubSectorResponseResource subSectorResponseResource = (SubSectorResponseResource)getDetailFromService(url, SubSectorResponseResource.class);
		
		
		if (subSectorResponseResource == null) { 
			throw new ValidateRecordException(environment.getProperty("subSector.invalid"),"subSectorId");
		}
		
		LoggerRequest.getInstance().logInfo1("******<<<<<<SubSectorCode>>>>>>******"+subSectorResponseResource.getCssCode());
		LoggerRequest.getInstance().logInfo1("******<<<<<<SubSectorName>>>>>>******"+subSectorResponseResource.getCssName());
		LoggerRequest.getInstance().logInfo1("******<<<<<<SubSectorStatus>>>>>>******"+subSectorResponseResource.getCssStatus());
		
		if (subSectorResponseResource.getServiceStatus() == null) {
			if (!subSectorResponseResource.getId().equals(subSectorId) || !subSectorResponseResource.getCssCode().equals(subSectorCode)) {
				throw new ValidateRecordException(environment.getProperty("subSector.not-match"),"subSectorId");
			}
			if (!subSectorResponseResource.getCssStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("subSector.invalid-status"),"subSectorId");
			}
		}
		else {
			serviceValidationExceptionHadle(subSectorResponseResource.getServiceStatus(), ServiceEntity.SUBSECTOR);
		}
		return subSectorResponseResource;
		
	}
	
	/**
	 * @author Sanatha
	 * 
     * Get Sub Sector name
     * @param String tenantId   - tenantId
     * @param Long sectorId   - sectorId
     * @param Long subSectorId   - subSectorId
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Sub Sector class
     * 
     */
	@Override
	public SubSectorResponseResource getSubSectorName(String tenantId, Long sectorId,  Long subSectorId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN getSubSectorName>>>>>>******");
		
 		String url = urlSubSector.trim()+tenantId+"/sector/"+sectorId.toString()+"/id/"+subSectorId.toString();
		LoggerRequest.getInstance().logInfo1("******<<<<<<url>>>>>>******"+url);
		
		SubSectorResponseResource subSectorResponseResource = (SubSectorResponseResource)getDetailFromService(url, SubSectorResponseResource.class);
		
		LoggerRequest.getInstance().logInfo1("******<<<<<<SubSectorCode>>>>>>******"+subSectorResponseResource.getCssCode());
		LoggerRequest.getInstance().logInfo1("******<<<<<<SubSectorName>>>>>>******"+subSectorResponseResource.getCssName());
		LoggerRequest.getInstance().logInfo1("******<<<<<<SubSectorStatus>>>>>>******"+subSectorResponseResource.getCssStatus());
		return subSectorResponseResource;
	}
	
	/**
	 * @author Sanatha
	 * 
     * Validate Bank
     * @param String tenantId   - tenantId
     * @param String bankId   - bankId
     * @param String bankCode   - bankCode
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Bank class
     * 
     */
	@Override
	public BankResponse validateBank(String tenantId, Long bankId, String bankCode, ServiceEntity serviceEntity, ServicePoint servicePoint, Integer index) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN validateBank>>>>>>******");
 		
		String url = urlBank+tenantId+"/"+bankId.toString();
		LoggerRequest.getInstance().logInfo1("******<<<<<<url>>>>>>******"+url);
		
		BankResponse bankResponse = (BankResponse)getDetailFromService(url, BankResponse.class);
		
		if (bankResponse == null) { 
			//throw new ValidateRecordException(environment.getProperty("bank.invalid"),"bankId");
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_DISBURSEMENT_BANK), serviceEntity, servicePoint,index);
		}
		
		LoggerRequest.getInstance().logInfo1("******<<<<<<BankCode>>>>>>******"+bankResponse.getBankCode());
		LoggerRequest.getInstance().logInfo1("******<<<<<<BankName>>>>>>******"+bankResponse.getBankName());
		LoggerRequest.getInstance().logInfo1("******<<<<<<BankStatus>>>>>>******"+bankResponse.getBankStatus());
		
		if (bankResponse.getServiceStatus() == null) {
			if (!bankResponse.getId().equals(bankId) || !bankResponse.getBankCode().equals(bankCode)) {
				//throw new ValidateRecordException(environment.getProperty("bank.not-match"),"bankId");
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_DISBURSEMENT_BANK_NOT_MATCH), serviceEntity, servicePoint,index);
			}
			if (!bankResponse.getBankStatus().equals("ACTIVE")) {
				//throw new ValidateRecordException(environment.getProperty("bank.invalid-status"),"bankId");
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_DISBURSEMENT_BANK_INVALID_STATUS), serviceEntity, servicePoint,index);
			}
		}
		else {
			serviceValidationExceptionHadle(bankResponse.getServiceStatus(), ServiceEntity.BANK);
		}
		return bankResponse;
		
	}
	
	/**
	 * @author Sanatha
	 * 
     * Get Bank name
     * @param String tenantId   - tenantId
     * @param String bankId   - bankId
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Bank class
     * 
     */
	@Override
	public BankResponse getBankName(String tenantId, Long bankId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN getBankName>>>>>>******");
 		
		BankResponse bankResponse = (BankResponse)getDetailFromService(tenantId, bankId.toString(), urlBank, BankResponse.class);
		
		LoggerRequest.getInstance().logInfo1("******<<<<<<BankCode>>>>>>******"+bankResponse.getBankCode());
		LoggerRequest.getInstance().logInfo1("******<<<<<<BankName>>>>>>******"+bankResponse.getBankName());
		LoggerRequest.getInstance().logInfo1("******<<<<<<BankStatus>>>>>>******"+bankResponse.getBankStatus());
		
		return bankResponse;
	}
	
	/**
	 * @author Sanatha
	 * 
     * Validate Bank Branch
     * @param String tenantId   - tenantId
     * @param String bankId   - bankId
     * @param String bankBranchId   - bankBranchId
     * @param String bankBranchCode   - bankBranchCode
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Bank Branch class
     * 
     */
	@Override
	public BankBranchResponseResource validateBankBranch(String tenantId, Long bankId, Long bankBranchId, String bankBranchCode, ServiceEntity serviceEntity, ServicePoint servicePoint, Integer index) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN validateBankBranch>>>>>>******");
 		
		String url = urlBankBranch+tenantId+"/"+bankBranchId.toString();
		LoggerRequest.getInstance().logInfo1("******<<<<<<url>>>>>>******"+url);
		
		BankBranchResponseResource bankBranchResponseResource = (BankBranchResponseResource)getDetailFromService(url, BankBranchResponseResource.class);
		
		if (bankBranchResponseResource == null) { 
			//throw new ValidateRecordException(environment.getProperty("bankBranch.invalid"),"bankBranchId");
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_DISBURSEMENT_BANK_BRANCH), serviceEntity, servicePoint,index);
		}
		
		LoggerRequest.getInstance().logInfo1("******<<<<<<BankBranchCode>>>>>>******"+bankBranchResponseResource.getBbrhCode());
		LoggerRequest.getInstance().logInfo1("******<<<<<<BankBranchName>>>>>>******"+bankBranchResponseResource.getBbrhName());
		LoggerRequest.getInstance().logInfo1("******<<<<<<BankBranchStatus>>>>>>******"+bankBranchResponseResource.getBbrhStatus());
		LoggerRequest.getInstance().logInfo1("******<<<<<<BankBranchBankId>>>>>>******"+bankBranchResponseResource.getBbrhBankId());
		
		if (bankBranchResponseResource.getServiceStatus() == null) {
			if (!bankBranchResponseResource.getId().equals(bankBranchId) || !bankBranchResponseResource.getBbrhCode().equals(bankBranchCode)) {
				//throw new ValidateRecordException(environment.getProperty("bankBranch.not-match"),"bankBranchId");
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_DISBURSEMENT_BANK_BRANCH_NOT_MATCH), serviceEntity, servicePoint,index);
			}
			if (!bankBranchResponseResource.getBbrhStatus().equals("ACTIVE")) {
				//throw new ValidateRecordException(environment.getProperty("bankBranch.invalid-status"),"bankBranchId");
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_DISBURSEMENT_BANK_BRANCH_INVALID_STATUS), serviceEntity, servicePoint,index);
			}
			if(!bankBranchResponseResource.getBbrhBankId().equals(bankId.toString())) {
				//throw new ValidateRecordException(environment.getProperty("bankBranch-bank.not-match"),"bankBranchId");
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_DISBURSEMENT_BANK_BRANCH_BANK_NOT_MATCH), serviceEntity, servicePoint,index);
			}
		}
		else {
			serviceValidationExceptionHadle(bankBranchResponseResource.getServiceStatus(), ServiceEntity.BANK_BRANCH);
		}
		return bankBranchResponseResource;
		
	}
	
	/**
	 * @author Sanatha
	 * 
     * Get Bank Branch name
     * @param String tenantId   - tenantId
     * @param String bankBranchId   - bankBranchId
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Bank Branch class
     * 
     */
	@Override
	public BankBranchResponseResource getBankBranchName(String tenantId, Long bankBranchId) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN getBankBranchName>>>>>>******");
 		
 		BankBranchResponseResource bankBranchResponseResource = (BankBranchResponseResource)getDetailFromService(tenantId, bankBranchId.toString(), urlBankBranch, BankBranchResponseResource.class);
		
 		LoggerRequest.getInstance().logInfo1("******<<<<<<BankBranchCode>>>>>>******"+bankBranchResponseResource.getBbrhCode());
		LoggerRequest.getInstance().logInfo1("******<<<<<<BankBranchName>>>>>>******"+bankBranchResponseResource.getBbrhName());
		LoggerRequest.getInstance().logInfo1("******<<<<<<BankBranchStatus>>>>>>******"+bankBranchResponseResource.getBbrhStatus());
		
		return bankBranchResponseResource;
	}
	
	@Override
	public PendingSuppliesBasicInfoResponseResource getPendingSupliesDetail(String tenantId, String pendingGuarantorId) {
		String url = urlPendingPersonSupplies   + tenantId + "/basic/" + pendingGuarantorId;
		
    	return (PendingSuppliesBasicInfoResponseResource)getDetailFromService(url, PendingSuppliesBasicInfoResponseResource.class);
	}


	@Override
	public PendingCustomerBasicInfoResponseResource getPendingCustomerDetail(String tenantId,String pendingCustomerrId) {
		String url = urlPendingCustomer   + tenantId + "/basic/" + pendingCustomerrId;
				
		return (PendingCustomerBasicInfoResponseResource)getDetailFromService(url, PendingCustomerBasicInfoResponseResource.class);
	}
	
	
	/**
	 * @author Senitha
	 * 
     * Validate Repayment Frequency
     * @param String tenantId   - tenantId
     * @param String repaymentFrequencyId   - repayment Frequency Id
     * @param String serviceUrl   - serviceUrl
     * @return - RepaymentFrequencyResponse class
     * 
     */
	@Override     
	public RepaymentFrequencyResponse validateRepaymentFrequency(String tenantId, String repaymentFrequencyId) {
		
		String url = urlRepaymentFrequency+tenantId+"/"+repaymentFrequencyId;
		//url = "http://132.145.228.83/lending-product/repayment-frequency/AnRkr/"+repaymentFrequencyId;
		RepaymentFrequencyResponse repaymentFrequencyResponse = (RepaymentFrequencyResponse)getDetailFromService(url, RepaymentFrequencyResponse.class);
		if (repaymentFrequencyResponse == null) { 
			throw new ValidateRecordException(environment.getProperty("common.not-available"),"repaymentFrequencyId");
		}
		if (repaymentFrequencyResponse.getStatus() != null) {

			if (!repaymentFrequencyResponse.getStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("common.status-inactive"),"repaymentFrequencyId");
			}
		}
		else {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"),"repaymentFrequencyId");
		}
		return repaymentFrequencyResponse;
	}
		
	@Override
	public ResponseCustomerKeyPersonResource saveCommonCustomerKeyPerson(String tenantId, String createdUser,AddKeyPersonBasicInfoRequestResource addKeyPersonBasicInfoRequestResource,Long pendingCustomerId) {
		try {
            String url = urlCustomerKeyPerson + tenantId + "/basic/" + pendingCustomerId;
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", createdUser);
           
            HttpEntity <AddKeyPersonBasicInfoRequestResource> insertCustomerKeyPerson = new HttpEntity<>(addKeyPersonBasicInfoRequestResource, headers);
            
            ResponseCustomerKeyPersonResource getResponse=restTemplate.exchange(url, HttpMethod.POST, insertCustomerKeyPerson, ResponseCustomerKeyPersonResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("customer-key-person.insert-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}
	
	@Override
	public ResponseCustomerRelationshipResource updateCustomerRelationship(String tenantId,UpdateRelationshipBasicInfoRequestResource updateRelationshipBasicInfoRequestResource, Long pendingCustomerId,Long pculpId,String userName) {  
		try {
            String url = urlCustomerRelationshipUpdate   + tenantId + "/basic/" + pendingCustomerId + "/" + pculpId;
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <UpdateRelationshipBasicInfoRequestResource> updateRelationDet = new HttpEntity<>(updateRelationshipBasicInfoRequestResource, headers);
            
            ResponseCustomerRelationshipResource getResponse=restTemplate.exchange(url, HttpMethod.PUT, updateRelationDet, ResponseCustomerRelationshipResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("customer-relationship.update-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}
	
	@Override
	public ResponseCustomerKeyPersonResource updateCustomerKeyPerson(String tenantId,UpdateKeyPersonBasicInfoRequestResource updateKeyPersonBasicInfoRequestResource, Long pendingCustomerId,Long pculpId,String userName) {  
		try {
            String url = urlCustomerKeyPersonUpdate   + tenantId + "/basic/" + pendingCustomerId + "/" + pculpId;
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <UpdateKeyPersonBasicInfoRequestResource> updateKeyPersonDet = new HttpEntity<>(updateKeyPersonBasicInfoRequestResource, headers);
            
            ResponseCustomerKeyPersonResource getResponse=restTemplate.exchange(url, HttpMethod.PUT, updateKeyPersonDet, ResponseCustomerKeyPersonResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("customer-key-person.update-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}
	
	
	
	@Override
	public ResponseCustomerRelationshipIdentificationResource updateLinkedPersonIdentification(String tenantId,UpdateCustomerPersonIdentificationRequestResource updateCustomerPersonIdentificationRequestResource, Long pendingCustomerId,Long pculpId, Long ppidtId,String userName) {
		try {
            String url = urlCustomerLinkPersonIdentificationUpdate   + tenantId + "/identification/" + pendingCustomerId + "/"+ pculpId +  "/" +ppidtId;
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <UpdateCustomerPersonIdentificationRequestResource> updateKeyPersonIdentification = new HttpEntity<>(updateCustomerPersonIdentificationRequestResource, headers);
            
            ResponseCustomerRelationshipIdentificationResource getResponse=restTemplate.exchange(url, HttpMethod.PUT, updateKeyPersonIdentification, ResponseCustomerRelationshipIdentificationResource.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("customer-relationship-identification.update-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}

	@Override
	public Boolean validateBaseUrl(String tenantId, String serviceUrl, Class<?> classObject) {
		try {
//			String url = "http://132.145.228.83/comn-person/common-list/tenantId/type/RESIDENTIALSTATUS" ;
			String url = baseUrl + serviceUrl;
			Boolean code = Boolean.FALSE;

			HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<>(headers);
			HttpStatus statusCode = restTemplate.exchange(url, HttpMethod.GET, entity, classObject).getStatusCode();
			if (statusCode == HttpStatus.OK) {
				code = Boolean.TRUE;
			}
			return code;
		} catch (RestClientException e) {
			String result = null;
			if (e.getMessage().contains("503")) {
				result = serviceStatus + ServiceStatus.NOT_AVAILABLE.toString() + "\"}";
			} else if (e.getMessage().contains("500")) {
				result = serviceStatus + ServiceStatus.EXCEPTION.toString() + "\"}";
			} else if (e.getMessage().contains("404")) {
				result = serviceStatus + ServiceStatus.NOT_FOUND.toString() + "\"}";
			} else if (e.getMessage().contains("400")) {
				result = serviceStatus + ServiceStatus.BAD_REQUEST.toString() + "\"}";
			} else if (e.getMessage().contains("504")) {
				result = serviceStatus + ServiceStatus.GATEWAY_TIME_OUT.toString() + "\"}";
			} else {
				result = serviceStatus + ServiceStatus.OTHER.toString() + "\"}";
			}
			ObjectMapper mapper = new ObjectMapper();
			try {
				return (Boolean) mapper.readValue(result, classObject);
			} catch (IOException e1) {
				return null;
			}
		} 
	}

	/*
	@Override
	public SubProductResponse validateSubProduct(String tenantId, String subProductId) {
		
		String url = urlSubProduct+tenantId+"/"+subProductId;
	
		SubProductResponse subProductResponse = (SubProductResponse)getDetailFromService(url, SubProductResponse.class);
		if (subProductResponse == null) { 
			throw new ValidateRecordException(environment.getProperty("common.not-available"),"subProductId");
		}
		if (subProductResponse.getStatus() != null) {
			
			if (!subProductResponse.getStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"),"subProductId");
			}
		}
		else {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"),"subProductId");
		}
		return subProductResponse;
		
	}

/*
	@Override
	public ProductResponse validateProduct(String tenantId, String productId) {

		String url = urlProduct + tenantId + "/" + productId;

		ProductResponse productResponse = (ProductResponse) getDetailFromService(url, ProductResponse.class);
		if (productResponse == null) {
			throw new ValidateRecordException(environment.getProperty("common.not-available"), "productId");
		}
		if (productResponse.getStatus() != null) {

			if (!productResponse.getStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "productId");
			}
		} else {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "productId");
		}
		return productResponse;
	}
*/
	@Override
	public FrequencyResponse validateFrequency(String tenantId, String frequencyId) {
		
		String url = urlFrequency+tenantId+"/"+frequencyId;
	
		FrequencyResponse frequencyResponse = (FrequencyResponse)getDetailFromService(url, FrequencyResponse.class);
		if (frequencyResponse == null) { 
			throw new ValidateRecordException(environment.getProperty("common.not-available"),"frequencyId");
		}
		if (frequencyResponse.getStatus() != null) {
			
			if (!frequencyResponse.getStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("frequency.invalid"),"frequencyId");
			}
		}
		else {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"),"frequencyId");
		}
		return frequencyResponse;
		
	}


	@Override
	public DesignationResponse validateDesignation(String tenantId, String designationId) {
		
		String url = urlDesignation+tenantId+"/"+designationId;
		
		DesignationResponse designationResponse = (DesignationResponse)getDetailFromService(url, DesignationResponse.class);
		if (designationResponse == null) { 
			throw new ValidateRecordException(environment.getProperty("common.not-available"),"designationId");
		}
		if (designationResponse.getDesgStatus() != null) {
			
			if (!designationResponse.getDesgStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("designation.invalid"),"designationId");
			}
		}
		else {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"),"designationId");
		}
		return designationResponse;
	}
	
	
	@Override
	public Timestamp getSyncTs() {
		  Calendar calendar = Calendar.getInstance();
	      java.util.Date now = calendar.getTime();
	       return new Timestamp(now.getTime());
	}
	
	@Override
	public CommonBranchResponseResource validateCommonBranch(String tenantId, String branchId, String branchName) {
		String url = urlCommonBranch + tenantId+ "/" + branchId;		
		CommonBranchResponseResource commonBranchResponseResource = (CommonBranchResponseResource)getDetailFromService(url, CommonBranchResponseResource.class);
		
		if (commonBranchResponseResource == null) { 
			throw new ValidateRecordException(environment.getProperty(NOT_AVAILABLE),"branchId");
		}
		if (commonBranchResponseResource.getServiceStatus() == null) {
			if (!commonBranchResponseResource.getName().equals(branchName)) {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH),"branchName");
			}
			if (!commonBranchResponseResource.getBrhStatus().equals(CommonStatus.ACTIVE.toString())) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),"branchId");
			}
		}
		else {
			serviceValidationExceptionHadle(commonBranchResponseResource.getServiceStatus(), ServiceEntity.BRANCH_ID);
		}
		return commonBranchResponseResource;
		
	}
	
	
	//Added by SewwandiH for Risk Grading. >> Business person type and industry type.
	@Override
	public BusinessPersonType getBusinessPersonType(String tenantId, Long businessTypeId) {
		BusinessPersonType businessPersonType = (BusinessPersonType) getDetailFromService(tenantId, businessTypeId.toString(), urlBusinessPersonType, BusinessPersonType.class);
		return businessPersonType;
	}

	@Override
	public BusinessPersonType validateBusinessPersonType(String tenantId, String businessTypeId, String businessTypeName, ServiceEntity serviceEntity) {
		BusinessPersonType businessPersonType = (BusinessPersonType) getDetailFromService(tenantId, businessTypeId, urlBusinessPersonType, BusinessPersonType.class);
		
		if(businessPersonType==null || businessPersonType.getServiceStatus() == null)  {
			if(businessPersonType==null || !businessPersonType.getName().equalsIgnoreCase(businessTypeName)) {
				//throw new InvalidServiceIdException(environment.getProperty("businessTypeId.invalid"), ServiceEntity.BUSINESS_PERSON_TYPE);
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"),"businessPersonTypeId");
			}
			
		}else {
			serviceValidationExceptionHadle(businessPersonType.getServiceStatus(), ServiceEntity.BUSINESS_PERSON_TYPE);
		}
		return businessPersonType;
	}
	
	@Override
	public IndustryType getIndustryType(String tenantId, Long industryTypeId) {
		IndustryType industryType =  (IndustryType) getDetailFromService(tenantId, industryTypeId.toString(), urlIndustryType, IndustryType.class);
		return industryType;
	}

	@Override
	public IndustryType validateIndustryType(String tenantId, String industryTypeId, String industryTypeName, ServiceEntity serviceEntity) {
		IndustryType industryType = (IndustryType) getDetailFromService(tenantId, industryTypeId, urlIndustryType, IndustryType.class);
		
		if(industryType==null || industryType.getServiceStatus() == null) {
			if(industryType==null || !industryType.getSectorName().equalsIgnoreCase(industryTypeName)) {
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"),"industryTypeId");
			}
			
		}else {
			serviceValidationExceptionHadle(industryType.getServiceStatus(), ServiceEntity.INDUSTRY_TYPE);
		}
		return industryType;
	}
	

	@Override
	public UserProfileResponse validateUserProfileByUserId(String tenantId, String userId, String userName) {
		
		String url = urlUserProfile + tenantId+ "/user-id/" +userId;		
		UserProfileResponse userProfileResponse = (UserProfileResponse)getDetailFromService(url, UserProfileResponse.class);
		if (userProfileResponse == null) { 
			throw new ValidateRecordException(environment.getProperty("common.not-available"),"userId");
		}
		if (userProfileResponse.getServiceStatus() == null) {
			if (!userProfileResponse.getUserName().equals(userName)) {
				throw new ValidateRecordException(environment.getProperty("user.not-match"),"userName");
			}
			if (!userProfileResponse.getUserStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("user.invalid"),"userId");
			}
		}
		else {
			serviceValidationExceptionHadle(userProfileResponse.getServiceStatus(), ServiceEntity.USER_PROFILE);
		}
		return userProfileResponse;
		
	}
	
	@Override
    public UserProfileResponse validateUserProfileById(String tenantId, Long userId, String userName) {

        String url = urlUserProfile + tenantId+"/"+userId;
		UserProfileResponse userProfileResponse = (UserProfileResponse)getDetailFromService(url, UserProfileResponse.class);
		if (userProfileResponse == null) { 
			throw new ValidateRecordException(environment.getProperty("common.not-available"),"userId");
		}
		if (userProfileResponse.getServiceStatus() == null) {
			if (!userProfileResponse.getUserName().equals(userName)) {
				throw new ValidateRecordException(environment.getProperty("user.not-match"),"userName");
			}
			if (!userProfileResponse.getUserStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("user.invalid"),"userId");
			}
		}
		else {
			serviceValidationExceptionHadle(userProfileResponse.getServiceStatus(), ServiceEntity.USER_PROFILE);
		}
		return userProfileResponse;

    }
	
	@Override
	public DocumentResponse validateDocument(String tenantId, String documentId, String documentName , ServicePoint servicePoint , String origin, Integer index ) {
		
		String url = urlDocumentUpload + tenantId+ "/" +documentId + "?origin=" + origin ;		
		DocumentResponse documentResponse = (DocumentResponse)getDetailFromService(url, DocumentResponse.class);
		
		if (documentResponse == null) { 
			throw new DetailListValidateException(environment.getProperty(NOT_FOUND),ServiceEntity.
					DOCUMENT_ID, servicePoint, index);
		}
		if (documentResponse.getServiceStatus() == null) {
			if (!documentResponse.getDocumentName().equals(documentName)) {
				throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH),ServiceEntity.
						DOCUMENT_NAME, servicePoint, index);
			}
			if (!documentResponse.getDocumentStatus().equals("ACTIVE")) {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE),ServiceEntity.
						DOCUMENT_ID, servicePoint, index);
			}
		}else 
			throw new DetailListValidateException(documentResponse.getServiceStatus().toString(),ServiceEntity.
					DOCUMENT_ID, servicePoint, index);
		
		return documentResponse;	
	}
	
	
//	@Override
//	public String invokedAnalystExceptionRule(String referenceCode, WorkflowType workflowType) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		headers.setBasicAuth(usernameBPM, passwordBPM);
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		restTemplateSetRequestFactory();
//		String json="{\"lookup\": \"kie-session\", \"commands\": [{\"insert\": {\"object\": {\"AnalystExceptionApproval\": {\"referenceCode\":\""+referenceCode+"\"}},\"out-identifier\":\"AnalystExceptionApproval\"}},{\"fire-all-rules\": {}}]}";
//		HttpEntity <String> entity = new HttpEntity<>(json, headers);
//		String response=null;
//		try {
//			String responseString=restTemplate.exchange(urlAnalystExceptionRuleContainer, HttpMethod.POST, entity, String.class).getBody();
//			JSONObject responseObj = new JSONObject(responseString);
//			JSONObject responseResult =responseObj.getJSONObject("result");
//			JSONObject responseExecutionResults =responseResult.getJSONObject("execution-results");
//			JSONArray responseResults = responseExecutionResults.getJSONArray("results");
//			JSONObject responseResults0 = responseResults.getJSONObject(0);
//			JSONObject value = responseResults0.getJSONObject("value");
//			JSONObject responseCustomerApproval =value.getJSONObject("com.fusionx.comn.customer.kieserver.AnalystExceptionApproval");
//			response=responseCustomerApproval.getString("approvalRequired");
//		} catch (RestClientException e) {
//			if(e.getMessage().contains("503")) {
//				throw new WorkFlowException(environment.getProperty("common.not-available"), workflowType);
//			}else if(e.getMessage().contains("500")) {
//				throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
//			}else if(e.getMessage().contains("404")) {
//				throw new WorkFlowException(environment.getProperty("common.not-found"), workflowType);
//			}else if(e.getMessage().contains("400")) {
//				throw new WorkFlowException(environment.getProperty("common.bad-request"), workflowType);
//			}else if(e.getMessage().contains("504")) {
//				throw new WorkFlowException(environment.getProperty("common.gateway-time-out"), workflowType);
//			}else {
//				throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
//			}
//		}catch (JSONException je) {
//			throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
//		}
//		return response;
//	}
	
	 @Override
	public Integer stringToInteger(String value){
		return Integer.parseInt(value);
	}
	 
	@Override
	public PerCommonList validatePersonCommonList(String tenantId, String id , String desc, String refCode, Integer index ,ServiceEntity serviceEntity , ServicePoint servicePoint, String field) {
		
		PerCommonList employmentCategoryList = (PerCommonList)remoteService.checkIsExist(tenantId, id , urlPersonCommonList, PerCommonList.class);
			
		if(employmentCategoryList == null || employmentCategoryList.getServiceStatus() == null) {					
			if(employmentCategoryList == null) {
				if(index != null) {
					throw new DetailListValidateException(environment.getProperty(NOT_AVAILABLE) ,
								serviceEntity,servicePoint , index);
				}else {
					throw new ValidateRecordException(environment.getProperty(NOT_AVAILABLE), field);
				}
			} else if((desc !=null && !employmentCategoryList.getCmlsDesc().equalsIgnoreCase(desc))
						|| !employmentCategoryList.getPcmlsReferenceCode().equalsIgnoreCase(refCode)) {		
				if(index != null) {
					throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH) ,
								serviceEntity,servicePoint , index);
				}else {
					throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), field);
				}
				
			}else if(!CommonStatus.ACTIVE.toString().equalsIgnoreCase(employmentCategoryList.getPcmlsStatus())) {
				if(index != null) {
					throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE) ,
								serviceEntity,servicePoint , index);
				}else {
					throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), field);
				}
			}
			}else {
				remoteService.serviceValidationExceptionHadle(employmentCategoryList.getServiceStatus(), serviceEntity , servicePoint);
			}
		return employmentCategoryList;
	}
	

	@Override
	public Object updateCustomerEmployment(String tenantId,CustomerEmploymentUpdateRequestResource customerEmploymentUpdateRequestResource, Long customerId , String userName) {
		try {
            String url = urlCustomerEmploymentUpdate   + tenantId +  "/"+ customerId ;
           
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("username", userName);
           
            HttpEntity <CustomerEmploymentUpdateRequestResource> customerEmploymentUpdate = new HttpEntity<>(customerEmploymentUpdateRequestResource, headers);
            
            Object getResponse=restTemplate.exchange(url, HttpMethod.PUT, customerEmploymentUpdate, Object.class).getBody();
           
            if(getResponse!=null)
                return getResponse;
            else
                throw new OtherException(environment.getProperty("customer-employment.update-service-error"), HttpStatus.BAD_REQUEST);
           
        }catch (Exception e) {
            if(e.getMessage().contains("503")) {
                throw new PersonServiceException(environment.getProperty("common.not-available"));
            }else if(e.getMessage().contains("500")) {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }else if(e.getMessage().contains("404")) {
                throw new PersonServiceException(environment.getProperty("common.not-found"));
            }else if(e.getMessage().contains("400")) {
                throw new PersonServiceException(environment.getProperty("common.bad-request"));
            }else if(e.getMessage().contains("504")) {
                throw new PersonServiceException(environment.getProperty("common.gateway-time-out"));
            }else {
                throw new PersonServiceException(environment.getProperty("common.internal-server-error"));
            }
        }
	}
	
	@Override
	public UserProfileResponse getUserProfileById(String tenantId, String id) {
		
		String url = urlUserProfile + tenantId+ "/" + id;		
		UserProfileResponse userProfileResponse = (UserProfileResponse)getDetailFromService(url, UserProfileResponse.class);
		if (userProfileResponse == null) { 
			throw new ValidateRecordException(environment.getProperty(NOT_AVAILABLE),"userId");
		}
		if (userProfileResponse.getServiceStatus() != null) {
			serviceValidationExceptionHadle(userProfileResponse.getServiceStatus(), ServiceEntity.USER_PROFILE);
		}
		return userProfileResponse;
		
	}
	
	@Override
	public CurencyResponse validateCurrencyType(String tenantId, String currencyId, String currencyName) {

		CurencyResponse currency = (CurencyResponse) getDetailFromService(tenantId, currencyId, urlCurrency,
				CurencyResponse.class);

		if (currency == null) {
			throw new ValidateRecordException(environment.getProperty("common.not-available"), "currency");
		}

		if (currency.getServiceStatus() == null) {

			if (!currency.getCurrencyStatus().equals(CommonStatus.ACTIVE.toString())) {
				throw new ValidateRecordException(environment.getProperty("currency.invalid"), "currency");
			}
		} else {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "currency");
		}
		return currency;
	}
	

	@Override
	public CommonListRemote validateComnCommonList(String tenantId, String id , ServiceEntity serviceEntity , ServicePoint servicePoint) {
		
		CommonListRemote commonListRemoteList = (CommonListRemote)remoteService.checkIsExist(tenantId, id , urlComnCommonList, CommonListRemote.class);		
		if (commonListRemoteList != null &&	commonListRemoteList.getServiceStatus() != null) 
				remoteService.serviceValidationExceptionHadle(commonListRemoteList.getServiceStatus(), serviceEntity , servicePoint);
			
		return commonListRemoteList;
	}
	
	@Override
	public EmployerResponse validateEmployerById(String tenantId, String employerId, String employerName) {
		
		String url = urlEmployer + tenantId+ "/" +employerId;		
		EmployerResponse employerResponse = (EmployerResponse)getDetailFromService(url, EmployerResponse.class);
		if (employerResponse == null) { 
			throw new ValidateRecordException(environment.getProperty(NOT_AVAILABLE),"employerId");
		}
		if (employerResponse.getServiceStatus() == null) {
			if (!employerResponse.getName().equals(employerName)) {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH),"employerName");
			}
			if (!employerResponse.getStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),"employerId");
			}
		}
		else {
			serviceValidationExceptionHadle(employerResponse.getServiceStatus(), ServiceEntity.EMPLOYER_ID);
		}
		return employerResponse;
		
	}
	
	@Override
	public TaxCodeResponse validateTaxCode(String tenantId, String taxCodeId, String taxCodeName) {
		
		String url = urlTaxCode + tenantId+ "/" +taxCodeId;		
		TaxCodeResponse taxCodeResponse = (TaxCodeResponse)getDetailFromService(url, TaxCodeResponse.class);
		if (taxCodeResponse == null) { 
			throw new ValidateRecordException(environment.getProperty(NOT_AVAILABLE),"taxCodeId");
		}
		if (taxCodeResponse.getServiceStatus() == null) {
			if (!taxCodeResponse.getTaxCodeName().equals(taxCodeName)) {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH),"taxCodeName");
			}
			if (!taxCodeResponse.getTaxCodeStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),"taxCodeId");
			}
		}
		else {
			serviceValidationExceptionHadle(taxCodeResponse.getServiceStatus(), ServiceEntity.TAX_CODE_ID);
		}
		return taxCodeResponse;
		
	}
	
	@Override
	public ProductResponse validateProduct(String tenantId, String mainProductId) {
		String url = urlProduct + tenantId + "/" + mainProductId;
		ProductResponse productResponse = (ProductResponse) getDetailFromService(url, ProductResponse.class);
		if (productResponse == null) {
			throw new ValidateRecordException(environment.getProperty(NOT_AVAILABLE), "productId");
		}
		if (productResponse.getServiceStatus() == null) {
			if (!productResponse.getStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "productId");
			}
		} else {
			serviceValidationExceptionHadle(productResponse.getServiceStatus(), ServiceEntity.PRODUCT_ID);
		}
		return productResponse;
	}

	@Override
	public SubProductResponse validateSubProduct(String tenantId, String subProductId) {
		String url = urlSubProduct + tenantId + "/" + subProductId;
		SubProductResponse productResponse = (SubProductResponse) getDetailFromService(url, SubProductResponse.class);
		if (productResponse == null) {
			throw new ValidateRecordException(environment.getProperty(NOT_AVAILABLE), "subProductId");
		}
		if (productResponse.getServiceStatus() == null) {
			if (!productResponse.getStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "subProductId");
			}
		} else {
			serviceValidationExceptionHadle(productResponse.getServiceStatus(), ServiceEntity.SUB_PRODUCT_ID);
		}
		return productResponse;
	}
	
	@Override
	public LanguageResponse validateLanguageById(String tenantId, String languageId, String languageDesc) {
		
		String url = urlLanguage + tenantId+ "/" + languageId;		
		LanguageResponse languageResponse = (LanguageResponse)getDetailFromService(url, LanguageResponse.class);
		if (languageResponse == null) { 
			throw new ValidateRecordException(environment.getProperty(NOT_AVAILABLE),"languageId");
		}
		if (languageResponse.getServiceStatus() == null) {
			if (!languageResponse.getLangDesc().equals(languageDesc)) {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH),"languageDesc");
			}
			if (!languageResponse.getLangStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),"languageId");
			}
		}
		else {
			serviceValidationExceptionHadle(languageResponse.getServiceStatus(), ServiceEntity.LANGUAGE_ID);
		}
		return languageResponse;
		
	}
	
	/**
	 * @author SewwandiH 
	 */
	@Override
	public DocumentMaintenanceResource validateDocumentMaintenances(String tenantId, String documentId, String documentName) {
		
		DocumentMaintenanceResource documentMaintenanceResource = (DocumentMaintenanceResource) getDetailFromService(tenantId, documentId, urlDocumentMaintenance, DocumentMaintenanceResource.class);
		
		if(documentMaintenanceResource==null || documentMaintenanceResource.getServiceStatus()==null) {
			if(documentMaintenanceResource==null || !documentMaintenanceResource.getName().equalsIgnoreCase(documentName)) {
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "documentId");
			}
			
		} else {
			serviceValidationExceptionHadle(documentMaintenanceResource.getServiceStatus(), ServiceEntity.DOCUMENT_MAINTENANCE_ID);
		}
		return documentMaintenanceResource;
	}
	
	@Override
	public DocumentMaintenanceResource getDocumentMaintenance(String tenantId, Long documentId) {
		DocumentMaintenanceResource documentMaintenanceResource = (DocumentMaintenanceResource) getDetailFromService(tenantId, documentId.toString(), urlDocumentMaintenance, DocumentMaintenanceResource.class);
		return documentMaintenanceResource;
	}
	
	
	@Override
	public IdentificationDetailResponse validateIdentificationDetail(String tenantId, String id, String description, String identificationNo) {
		
		
		String url = "http://132.145.228.83/comn-person/identification-type/AnRkr/"+id;
	
		IdentificationDetailResponse identificationDetailResponse = (IdentificationDetailResponse)getDetailFromService(url, IdentificationDetailResponse.class);
		if (identificationDetailResponse == null) { 
			throw new ValidateRecordException(environment.getProperty("common.not-available"),"identificationDetaiId");
		}
		if (identificationDetailResponse.getIdtpStatus() != null) {
			
			if (!identificationDetailResponse.getIdtpStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("identification.invalid"),"identificationDetaiId");
			}
			
			if(!identificationDetailResponse.getIdtpVisualFormt().equals(identificationNo)) {
				throw new ValidateRecordException(environment.getProperty("identificationNo.mismatch"),"identificationNo");
			}
		}
		else {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"),"identificationDetaiId");
		}
		return identificationDetailResponse;
		
	}


	@Override
	public DisbursementConditionsResponse validateDisbursementConditions(String tenantId, String id, String name) {
		
		String url = "http://132.145.228.83/lending-product/disbursement-conditions/AnRkr/"+id;
		//String url = urlDisbursementConditions + tenantId+ "/" + id;	
		
		DisbursementConditionsResponse disbursementConditionsResponse = (DisbursementConditionsResponse)getDetailFromService(url, DisbursementConditionsResponse.class);
		if (disbursementConditionsResponse == null) { 
			throw new ValidateRecordException(environment.getProperty("common.not-available"),"disbursementConditionsId");
		}
		if (disbursementConditionsResponse.getStatus() != null) {
			
			if (!disbursementConditionsResponse.getStatus().equals(CommonStatus.ACTIVE.toString())) {
				throw new ValidateRecordException(environment.getProperty("disbursementConditions.invalid"),"disbursementConditionsId");
			}
			
			if(!disbursementConditionsResponse.getName().equals(name)) {
				throw new ValidateRecordException(environment.getProperty("disbursementConditionsName.mismatch"),"disbursementConditionsName");
			}
		}
		else {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"),"disbursementConditionsId");
		}
		return disbursementConditionsResponse;
	}
	
	@Override
	public PayModeResponse validatePayMode(String tenantId, String id, String name) {
		
		String url = "http://132.145.228.83/lending-product/pay-modes/AnRkr/"+id;
		
		PayModeResponse payModeResponse = (PayModeResponse)getDetailFromService(url, PayModeResponse.class);
		if (payModeResponse == null) { 
			throw new ValidateRecordException(environment.getProperty("common.not-available"),"payModeId");
		}
		if (payModeResponse.getStatus() != null) {
			
			if (!payModeResponse.getStatus().equals(CommonStatus.ACTIVE.toString())) {
				throw new ValidateRecordException(environment.getProperty("payMode.invalid"),"payModeId");
			}
			
			if(!payModeResponse.getName().equals(name)) {
				throw new ValidateRecordException(environment.getProperty("payModeName.mismatch"),"payModeName");
			}
		}
		else {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"),"payModeId");
		}
		return payModeResponse;
	}
	
	
	@Override
	public DocumentTypeResponse validateDocumentType(String tenantId, String id, String name) {
		
		String url = "http://132.145.228.83/lending-product/document-checklist-details/AnRkr/"+id;
		
		DocumentTypeResponse documentTypeResponse = (DocumentTypeResponse)getDetailFromService(url, DocumentTypeResponse.class);
		if (documentTypeResponse == null) { 
			throw new ValidateRecordException(environment.getProperty("common.not-available"),"documentCheckListDetId");
		}
		if (documentTypeResponse.getStatus() != null) {
			
			if (!documentTypeResponse.getStatus().equals(CommonStatus.ACTIVE.toString())) {
				throw new ValidateRecordException(environment.getProperty("docChecklist.invalid"),"documentCheckListDetId");
			}
			
			if(!documentTypeResponse.getDocumentTypeName().equals(name)) {
				throw new ValidateRecordException(environment.getProperty("docTypeName.mismatch"),"documentTypeName");
			}
		}
		else {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"),"documentCheckListDetId");
		}
		return documentTypeResponse;
	}

}


