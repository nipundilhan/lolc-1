package com.fusionx.lending.transaction.service.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

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
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusionx.lending.transaction.core.DefaultRequestHeaders;
import com.fusionx.lending.transaction.core.LoggerRequest;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.enums.ServiceEntity;
import com.fusionx.lending.transaction.enums.ServiceStatus;
import com.fusionx.lending.transaction.enums.WorkflowType;
import com.fusionx.lending.transaction.exception.InvalidServiceIdException;
import com.fusionx.lending.transaction.exception.ValidateRecordException;
import com.fusionx.lending.transaction.exception.WorkFlowException;
import com.fusionx.lending.transaction.resource.AccountServiceResource;
import com.fusionx.lending.transaction.resource.AccountStatusResource;
import com.fusionx.lending.transaction.resource.AccountValidateResource;
import com.fusionx.lending.transaction.resource.ApplicationFrequencyResponse;
import com.fusionx.lending.transaction.resource.TaxCodeResponse;
import com.fusionx.lending.transaction.resource.UserProfileResource;
import com.fusionx.lending.transaction.resource.WorkFlowWorkItem;
import com.fusionx.lending.transaction.resource.WorkflowInitiationRequestResource;
import com.fusionx.lending.transaction.resource.WorkflowRulesResource;
import com.fusionx.lending.transaction.service.ValidateService;

@Component
public class ValidateServiceImpl implements ValidateService {


    protected static final String RECORD_NOT_FOUND = "common.record-not-found";
    protected static final String COMMON_NOT_MATCH = "common.not-match";
    protected static final String COMMON_INVALID_VALUE = "common.invalid-value";
    protected static final String VALID_ACCOUNT = "acc-valid";
    protected static final String INVALID_ACCOUNT = "acc-invalid";
    protected static final String COMMON_INTERNAL_SERVER_ERROR = "common.internal-server-error";
    protected static final String INVALID_CURRENCY = "common-invalid.currency";

    protected static final String TAX_CODE_STATUS = "taxCode";
    protected static final String FEE_TYPE_CODE = "feeTypeCode";
    protected static final String CHARGE_AMOUNT = "chargeAmount";
    protected static final String NEGOTIBLE_INDICATOR = "negotiableIndicator";
    protected static final String FEE_CHARGE_DETAIL_ID = "feeChargeDetailId";
    protected static final String FEE_RATE = "feeRate";
    protected static final String FEE_INDICATOR = "feeIndicator";
    protected static final String DEDUCT_INDICATOR = "deductIndicator";
    protected static final String FEE_TYPE_ID = "feeTypeId";
    protected static final String FEE_CATEGORY_ID = "feeCategoryId";
    protected static final String FEE_CATEGORY_CODE = "feeCategoryCode";
    protected static final String AMOUNT = "feeAmount";

    protected static final String CALCULATION_FREQ_CODE = "calculationFrequencyCode";
    protected static final String CALCULATION_FREQ_NAME = "calculationFrequencyName";
    protected static final String APPLICATION_FREQ_CODE = "applicationFrequencyCode";
    protected static final String APPLICATION_FREQ_NAME = "applicationFrequencyName";
    protected static final String SUB_PRD_CODE = "subProductCode";
    protected static final String FEE_TYPE_NAME = "feeTypeName";
    protected static final String FEE_CATEGORY_NAME = "feeCategoryName";
    protected static final String TRN_CODE_ID = "transactionCodeId";
    protected static final String TRN_SUB_CODE_ID = "transactionSubCodeId";
    protected static final String TRN_SUB_CODE = "transactionSubCode";
    protected static final String CHARGE_DETAIL_ID = "feeChargeDetailId";
    protected static final String AGE_ELIGIBILITY = "ageEligibility";
    protected static final String MINIMUM_AGE = "minimumAge";
    protected static final String MAXIMUM_AGE = "maximumAge";
    protected static final String RECORD_STATUS = "status";
    @Autowired
    Environment environment;
    private String serviceStatus = "{\"serviceStatus\" : \"";
    @Autowired
    private RestTemplate restTemplate;
    @Value("${accStatus}")
    private String urlAccountStatus;

    @Value("${charges}")
    private String urlCharges;

    @Value("${account}")
    private String urlAccount;

    @Value("${Transfer_out}")
    private String urlTransferOut;

    @Value("${recovery-request}")
    private String urlRecoveryRequest;

    //Added by Sanatha for CASA GL on 20-APR-2020
    @Value("${finance_settings_account_mapping}")
    private String urlFinanceAccountMapping;

    @Value("${tax-code}")
    private String urlTaxCode;

    @Value("${application-frequency}")
    private String urlApplicationFrequency;

    @Value("${pam.username}")
    private String pUsername;

    @Value("${pam.password}")
    private String password;

    @Value("${pam-cert-location}")
    private String pamCertLocation;

    @Value("${recovery-rule-container1}")
    private String urlRecoveryRuleContainer1;

    @Value("${recovery-rule-container2}")
    private String urlRecoveryRuleContainer2;

    @Value("${recovery-rule-container3}")
	private String urlRecoveryRuleContainer3;
    
    @Value("${pam.username}") 
    private String usernameBPM;

    @Value("${pam.password}")
    private String passwordBPM;
    
    
    @Value("${lending-transaction-rule-container}")
	private String urlLendingRuleContainer;
    
	@Value("${master-def-bpm}")
	private String masterDefBpm;
	
	@Value("${master-def-approval-bpm}")
	private String masterDefApprovalBpm;
	
	@Value("${loan-account-status}")
    private String urlLoanAccountStatus;
	
	@Value("${user-id}")
    private String urlUserProfile;


    @Override
    public boolean validateAccountStatus(String tenantId, String status) {
        ArrayList<String> statusList = (ArrayList<String>) getAccountStatus(tenantId, urlAccountStatus, ArrayList.class);
        if (statusList != null) {
            for (String accstatus : statusList) {
                if (accstatus.equals(status)) {
                    return false;
                }
            }
        }
		/*if(statusList==null || account.getServiceStatus()==null) {
			if(account==null || !account.getAccountData().getCasaIdentification().equalsIgnoreCase(accountValidateResource.getAccountNo())) {
				throw new InvalidServiceIdException(environment.getProperty("accountId.not-match"), accountValidateResource.getServiceEntity());
			}
		}else {
			serviceValidationExceptionHadle(account.getServiceStatus(), accountValidateResource.getServiceEntity());
		}*/
        return true;
    }


    @Override
    public AccountServiceResource validateAccount(String tenantId, AccountValidateResource accountValidateResource) {
        String url = urlAccount + tenantId + "/validate/" + accountValidateResource.getAccountId();
        AccountServiceResource account = (AccountServiceResource) getServiceDetail(url, AccountServiceResource.class);
        if (account == null || account.getServiceStatus() == null) {
            if (account == null || !account.getAccountData().getCasaIdentification().equalsIgnoreCase(accountValidateResource.getAccountNo()) || account.getAccountValidityStatus().equals("INVALID")) {
                throw new InvalidServiceIdException(environment.getProperty("common.invalid-value"), accountValidateResource.getServiceEntity());
            }
        } else {
            serviceValidationExceptionHadle(account.getServiceStatus(), accountValidateResource.getServiceEntity());
        }
        return account;
    }

    @Override
    public TaxCodeResponse validateTaxCode(String tenantId, String taxCodeId, String taxCodeName) {

        TaxCodeResponse taxCodeResponse = (TaxCodeResponse) this.getDetailFromService(tenantId,
                Long.parseLong(taxCodeId), urlTaxCode, TaxCodeResponse.class);
        if (taxCodeResponse == null) {
            throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "taxCodeId");
        }
        if (taxCodeResponse.getServiceStatus() == null) {
            if (!taxCodeResponse.getTaxCodeName().equals(taxCodeName)) {
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "taxCodeName");
            }
            if (!taxCodeResponse.getTaxCodeStatus().equals("ACTIVE")) {
                throw new ValidateRecordException(environment.getProperty(TAX_CODE_STATUS), "taxCodeId");
            }
        } else {
            serviceValidationExceptionHadle(taxCodeResponse.getServiceStatus(), ServiceEntity.TAX_CODE_ID);
        }
        return taxCodeResponse;

    }

    @Override
    public ApplicationFrequencyResponse validateApplicationFrequency(String tenantId, String applicationFrequencyId) {

        ApplicationFrequencyResponse applicationFrequencyResponse = (ApplicationFrequencyResponse) this
                .getDetailFromService(tenantId, Long.parseLong(applicationFrequencyId), urlApplicationFrequency,
                        ApplicationFrequencyResponse.class);
        if (applicationFrequencyResponse == null) {
            throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "applicationFrequencyId");
        }
        if (applicationFrequencyResponse.getServiceStatus() == null) {
            if (!applicationFrequencyResponse.getStatus().equals("ACTIVE")) {
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),
                        "applicationFrequencyId");
            }
        } else {
            serviceValidationExceptionHadle(applicationFrequencyResponse.getServiceStatus(),
                    ServiceEntity.APPLICATION_FREQUENCY);
        }
        return applicationFrequencyResponse;

    }

    private Object getServiceDetail(String url, Class<?> classObject) {
        System.out.println("in getServiceDetail 01");

        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);

            System.out.println("in getServiceDetail 02");

            return restTemplate.exchange(url, HttpMethod.GET, entity, classObject).getBody();

        } catch (RestClientException e) {

            String result = null;
            if (e.getMessage().contains("503")) {
                result = serviceStatus + ServiceStatus.NOT_AVAILABLE.toString() + "\"}";
            } else if (e.getMessage().contains("500")) {
                System.out.println("in getServiceDetail 03");
                result = serviceStatus + ServiceStatus.EXCEPTION.toString() + "\"}";
            } else if (e.getMessage().contains("404")) {
                result = serviceStatus + ServiceStatus.NOT_FOUND.toString() + "\"}";
            } else if (e.getMessage().contains("400")) {
                result = serviceStatus + ServiceStatus.BAD_REQUEST.toString() + "\"}";
            }

            System.out.println("in getServiceDetail 04");
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("in getServiceDetail 05");
            try {
                System.out.println("in getServiceDetail 06");
                System.out.println("in getServiceDetail 06" + mapper);
                return mapper.readValue(result, classObject);
            } catch (IOException e1) {
                System.out.println("in getServiceDetail 07");
                return null;
            }
        }
    }


    @Override
    public Object getDetailFromService(String tenantId, Long id, String serviceUrl, Class<?> classObject) {
        //return classObject;
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(serviceUrl + tenantId + "/" + id, HttpMethod.GET, entity, classObject).getBody();
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
    public Object getAccountStatus(String tenantId, String serviceUrl, Class<?> classObject) {
        //return classObject;
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(serviceUrl + tenantId + "/account-status/all", HttpMethod.GET, entity, classObject).getBody();
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
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(result, classObject);
            } catch (IOException e1) {
                return null;
            }
        }
    }
	
	/*@Override
	public void validateUserId(String tenantId, String urlUserRestrictedAccount, String userName) {
		UserRestrictedAccount[] transactionEvent=(UserRestrictedAccount[])findByUserId( tenantId, urlUserRestrictedAccount,userName,UserRestrictedAccount[].class);
		if(transactionEvent==null) {
			if(transactionEvent[0].getUserId() == null) 
				throw new InvalidServiceIdException(environment.getProperty("accountId.invalid"), ServiceEntity.USERID);
		}else {
			//serviceValidationExceptionHadle(transactionEventSubcode.getServiceStatus(), ServiceEntity.ACCOUNT_ID);
		}
	}*/


    @Override
    public Object findByUserId(String tenantId, String serviceUrl, String userName, Class<?> classObject) {
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(serviceUrl + tenantId + "/user-id/" + userName, HttpMethod.GET, entity, classObject).getBody();
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
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(result, classObject);
            } catch (IOException e1) {
                return null;
            }
        }
    }


/**
 * @author Dilhan
 * 
 * @date 20-10-2021
 * */
	@Override
	public WorkflowRulesResource invokedLendingProductRule(WorkflowType workflowType, String domainPath, String domainName, String tenantId) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(usernameBPM, passwordBPM);
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplateSetRequestFactory();
        String json="{\"lookup\": \"kie-session\", \"commands\": [{\"insert\": {\"object\": {\""+domainPath+"\": {\"referenceCode\":\""+workflowType+"\"}},\"out-identifier\":\""+domainName+"\"}},{\"fire-all-rules\": {}}]}";
        HttpEntity <String> entity = new HttpEntity<>(json, headers);
        WorkflowRulesResource workflowRulesResource=new WorkflowRulesResource();
        try {
            String responseString=restTemplate.exchange(urlLendingRuleContainer.replace("tenantId", tenantId), HttpMethod.POST, entity, String.class).getBody();
            LoggerRequest.getInstance().logInfo1("***********responseString***********"+responseString);
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
	
	/**
	 * @author Dilhan
	 * 
	 * @date 20-10-2021
	 * */
	@Override
	public Long initiateLendingProductWorkFlow(WorkflowInitiationRequestResource workflowInitiationRequestResource, String tenantId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBasicAuth(usernameBPM, passwordBPM);
		headers.setContentType(MediaType.APPLICATION_JSON);
		restTemplateSetRequestFactory();
		Long processId=null;
			
		if(workflowInitiationRequestResource.getApprovWorkflowType()!=null &&workflowInitiationRequestResource.getApprovWorkflowType().equals(WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL)) {
			workflowInitiationRequestResource.setApprovalUser("master-definition-Approve");
			HttpEntity <WorkflowInitiationRequestResource> entity = new HttpEntity<>(workflowInitiationRequestResource, headers);
			processId=initiateWorkFlow(masterDefBpm.replace("tenantId", tenantId), entity, WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL);
			
			
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
		
		if(workflowType.equals(WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL)) {
			url=masterDefApprovalBpm.replace("tenantId", tenantId);
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
	
	/**
	 * @author Dilhan
	 * 
	 * @date 20-10-2021
	 * */
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
		
		if(workflowType.equals(WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL)) {
			url=masterDefApprovalBpm.replace("tenantId", tenantId);
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

	/**
	 * @author Dilhan
	 * 
	 * @date 10-06-2021
	 * */
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
		if(workflowType.equals(WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL)) {
			url=masterDefApprovalBpm.replace("tenantId", tenantId);
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
	public AccountStatusResource validateAccountStatus(String tenantId, Long accountStatusId,
			String accountStatusName) {
		
		String url = urlLoanAccountStatus;
		AccountStatusResource accountStatusResource = (AccountStatusResource)getDetailFromService(tenantId,accountStatusId,url, AccountStatusResource.class);
		
		if (accountStatusResource == null) { 
			throw new ValidateRecordException(environment.getProperty("accountStatusId.invalid"),"accountStatusId");
		}
		if (accountStatusResource.getStatus() != null) {

			if (!accountStatusResource.getStatus().equals(CommonStatus.ACTIVE.toString())) {
				throw new ValidateRecordException(environment.getProperty("common.status-inactive"),"accountStatusId");
			}else {
				
				if(!accountStatusName.equals(accountStatusResource.getName())) {
					throw new ValidateRecordException(environment.getProperty("accountStatusName.not-match"),"accountStatusName");
				}
			}
		}
		else {
			throw new ValidateRecordException(environment.getProperty("common.status-invaild"),"accountStatusId");
		}
		return accountStatusResource;
	}
    private void serviceValidationExceptionHadle(ServiceStatus serviceStatus, ServiceEntity serviceEntity) {
        switch (serviceStatus) {
            case NOT_AVAILABLE:
                throw new InvalidServiceIdException(environment.getProperty("common.not-available"), serviceEntity);
            case NOT_FOUND:
                throw new InvalidServiceIdException(environment.getProperty("common.record-not-found"), serviceEntity);
            case EXCEPTION:
                throw new InvalidServiceIdException(environment.getProperty("common.internal-server-error"), serviceEntity);
            case BAD_REQUEST:
                throw new InvalidServiceIdException(environment.getProperty("common.bad-request"), serviceEntity);
            default:
        }
    }


    @Override
    public Object findBydesignationId(String tenantId, String urlUserRestrictedAccount, Long designation,
                                      Class<?> classObject) {
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(urlUserRestrictedAccount + tenantId + "/designation/" + designation, HttpMethod.GET, entity, classObject).getBody();
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
    public Object findBydesignation(String tenantId, String serviceUrl, Long designation,
                                    Class<?> classObject) {
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(serviceUrl + tenantId + "/" + designation, HttpMethod.GET, entity, classObject).getBody();
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
    public Timestamp getSystemDate() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        return new Timestamp(now.getTime());
    }


	@Override
	public UserProfileResource validateUserProfileByUserId(String tenantId, String userId, String userName) {
		String url = urlUserProfile + tenantId+ "/user-id/" +userId;		
		UserProfileResource userProfileResource = (UserProfileResource)getDetailFromService(url, UserProfileResource.class);
		if (userProfileResource == null) { 
			throw new ValidateRecordException(environment.getProperty("common.not-available"),"userId");
		}
		if (userProfileResource.getServiceStatus() == null) {
			if (!userProfileResource.getUserName().equals(userName)) {
				throw new ValidateRecordException(environment.getProperty("user.not-match"),"userName");
			}
			if (!userProfileResource.getUserStatus().equals("ACTIVE")) {
				throw new ValidateRecordException(environment.getProperty("user.invalid"),"userId");
			}
		}
		else {
			serviceValidationExceptionHadle(userProfileResource.getServiceStatus(), ServiceEntity.USER_PROFILE);
		}
		return userProfileResource;
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


}
