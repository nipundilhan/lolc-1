package com.fusionx.lending.product.service.impl;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Maintain Common Service Validations implementation 
 * @author Rangana
 * @Dat 07-06-2021
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019     FX-6        FX-6523    Rangana      Created
 *
 ********************************************************************************************************
 */


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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fusionx.lending.product.config.CommonModuleProperties;
import com.fusionx.lending.product.resources.*;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.DefaultRequestHeaders;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.RiskTemplateResponse;
import com.fusionx.lending.product.domain.TaxCodeResponse;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.ServicePoint;
import com.fusionx.lending.product.enums.ServiceStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.exception.WorkFlowException;
import com.fusionx.lending.product.service.RemoteService;
import com.fusionx.lending.product.service.ValidationService;
import com.fusionx.lending.product.exception.DetailValidateException;


@Component
@Transactional(rollbackFor = Exception.class)
public class ValidationServiceImpl extends MessagePropertyBase implements ValidationService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RemoteService remoteService;

    private String serviceStatus = "{\"serviceStatus\" : \"";

    @Autowired
    private Environment environment;

    @Autowired
    private CommonModuleProperties commonModuleProperties;

    @Value("${period}")
    private String urlPeriod;

    @Value("${comn-currency-detail}")
    private String urlCurrency;

    @Value("${legal-structure}")
    private String urlLegalStructure;

    @Value("${comn-sector}")
    private String urlComnSector;

    @Value("${residency-type}")
    private String urlResidencyType;

    @Value("${comn-document-upload}")
    private String urlDocumentUpload;

    // Added by Senitha
    @Value("${frequency}")
    private String urlFrequency;

    @Value("${pam.username}")
    private String usernameBPM;

    @Value("${pam.password}")
    private String passwordBPM;

    @Value("${pam-cert-location}")
    private String pamCertLocation;

    @Value("${lending-product-rule-container}")
    private String urlLendingRuleContainer;

    @Value("${eligi-temp-bpm}")
    private String eligiTempBpm;

    @Value("${eligi-temp-approval-bpm}")
    private String eligiTempApprovalBpm;

    @Value("${fee-charge-temp-bpm}")
    private String feeChargeTempBpm;

    @Value("${fee-charge-temp-approval-bpm}")
    private String feeChargeTempApprovalBpm;

    @Value("${repayment-temp-bpm}")
    private String repaymentTempBpm;

    @Value("${repayment-temp-approval-bpm}")
    private String repaymentTempApprovalBpm;

    @Value("${transaction-code}")
    private String urlTransactionCode;

    @Value("${transaction-sub-code}")
    private String urlTransactionSubCode;

    @Value("${comn-country}")
    protected String countryUrl;

    @Value("${asset-type}")
    private String assetTypeUrl;

    @Value("${master-def-bpm}")
    private String masterDefBpm;

    @Value("${comn-branch}")
    private String urlCommonBranch;

    @Value("${master-def-approval-bpm}")
    private String masterDefApprovalBpm;

    @Value("${interest-temp-bpm}")
    private String interestTempBpm;

    @Value("${interest-temp-approval-bpm}")
    private String interestTempApprovalBpm;


    @Value("${feature-benefit-template-bpm}")
    private String featureBenefitTemplateBpm;

    @Value("${feature-benefit-template-approval-bpm}")
    private String featureBenefitTemplateApprovalBpm;

//	@Value("${sub-code-transaction}")
//	private String urlSubCodeTransaction;

    // start - added by Nipun
    @Value("${repayment-frequency}")
    private String urlRepaymentFrequency;
    // end - added by Nipun

    @Value("${url-user-profile}")
    private String urlUserProfile;

    @Value("${geo-heirachy}")
    private String urlGeoHierachy;
    // added by dilki
    @Value("${tax-code}")
    private String urlTaxCode;

    @Value("${casa-product-bca-sub-product}")
    private String urlCasaSubProduct;

    @Value("${comn-common-individual-type}")
    private String urlComnCommonIndividualType;

    @Value("${casa-product-bca-comn-list}")
    private String urlCasaProductBcaComnList;

    @Value("${risk-template}")
    private String urlRiskTemplate;

    @Value("${comn-person-common-list}")
    private String urlPersonCommonList;

    @Value("${officer-type}")
    private String urlOfficerType;

    @Value("${document-details}")
    private String urlDocumentDetails;


    @Value("${lending-transaction-tax-event}")
    private String taxEvent;
    
    //added by Achini
    @Value("${credit-appraisal-start-process-temp-bpm}")
    private String creditAppraisalStartProcessTempBpm;
    
    @Value("${credit-appraisal-lead-status-update-url}")
    private String creditAppraisalLeadStatusUpdateUrl;
    
    @Value("${credit-appraisal-states-temp-bpm}")
    private String creditAppraisalStatesTempBpm;
    
    @Value("${credit-appraisal-states-temp-bpm}")
    private String creditAppraisalCompleteTempBpm;
    
    
    
    @Value("${pam.url}")
    private String comnPamSupport;


    @Override
    public PeriodResponse validatePeriod(String tenantId, String periodId, String periodName, EntityPoint entityPoint) {
        LoggerRequest.getInstance().logInfo("validatePeriod ### IN validatePeriod ");
        PeriodResponse period = (PeriodResponse) getDetailFromService(tenantId, periodId, urlPeriod, PeriodResponse.class);
        LoggerRequest.getInstance().logInfo("validatePeriod ### IN validatePeriod urlPeriod " + urlPeriod);

        if (period != null) {
            if (period.getServiceStatus() != null) {
                LoggerRequest.getInstance().logInfo("validatePeriod ### IN validatePeriod 02");
                serviceValidationExceptionHadle(period.getServiceStatus(), ServiceEntity.PERIOD, entityPoint);
            }

            LoggerRequest.getInstance().logInfo("validatePeriod ### IN validatePeriod 01");
            if ((!period.getName().equalsIgnoreCase(periodName) || !period.getStatus().equalsIgnoreCase("ACTIVE"))) {
                LoggerRequest.getInstance().logInfo("validatePeriod ### IN validatePeriod 01.1");
                throw new InvalidServiceIdException(environment.getProperty("periodServiceId.invalid"), ServiceEntity.PERIOD, entityPoint);
            }
            LoggerRequest.getInstance().logInfo("validatePeriod ### IN validatePeriod 01.2");


        } else {
            LoggerRequest.getInstance().logInfo("validatePeriod ### IN validatePeriod 02");
            throw new InvalidServiceIdException(environment.getProperty("periodServiceId.invalid"), ServiceEntity.PERIOD, entityPoint);
        }
        LoggerRequest.getInstance().logInfo("validatePeriod ### IN validatePeriod 03");
        return period;
    }

    @Override
    public PeriodResponse getPeriod(String tenantId, String periodId, EntityPoint entityPoint) {
        LoggerRequest.getInstance().logInfo("getPeriod ### IN getPeriod ");
        PeriodResponse period = (PeriodResponse) getDetailFromService(tenantId, periodId, urlPeriod, PeriodResponse.class);

        return period;
    }


    @Override
    public Currency validateCurrency(String tenantId, String currencyId, String currencyName, EntityPoint entityPoint) {

        Currency currency = (Currency) getDetailFromService(tenantId, currencyId, urlCurrency, Currency.class);

        if (currency == null || currency.getServiceStatus() == null) {
            if (currency == null || !currency.getCurrencyName().equalsIgnoreCase(currencyName)) {
                throw new InvalidServiceIdException(environment.getProperty("currencyId.invalid"), ServiceEntity.CURRENCY, entityPoint);
            }
            if (currency == null || !currency.getCurrencyStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
                throw new InvalidServiceIdException(environment.getProperty("active-status.pattern"), ServiceEntity.CURRENCY, entityPoint);
            }

        } else {
            serviceValidationExceptionHadle(currency.getServiceStatus(), ServiceEntity.CURRENCY, entityPoint);
        }
        return currency;

    }

    @Override
    public Country validateCountry(String tenantId, String countryId, String countryName, EntityPoint entityPoint) {
        Country country = (Country) getDetailFromService(tenantId, countryId, countryUrl, Country.class);

        if (country == null || country.getServiceStatus() == null) {
            if (country == null || !country.getGeohiName().equalsIgnoreCase(countryName)) {
                throw new InvalidServiceIdException(environment.getProperty("countryId.invalid"), ServiceEntity.COUNTRY_NAME, entityPoint);
            }

        } else {
            serviceValidationExceptionHadle(country.getServiceStatus(), ServiceEntity.COUNTRY, entityPoint);
        }
        return country;
    }

    @Override
    public LegalStructure validateLegalStructure(String tenantId, String legalStructureId, String legalStructureName, EntityPoint entityPoint) {

        LegalStructure legalStructure = (LegalStructure) getDetailFromService(tenantId, legalStructureId, urlLegalStructure, LegalStructure.class);

        if (legalStructure == null || legalStructure.getServiceStatus() == null) {
            if (legalStructure == null || !legalStructure.getName().equalsIgnoreCase(legalStructureName))
                throw new InvalidServiceIdException(environment.getProperty("legalStructureId.invalid"), ServiceEntity.LEGALSTRUCTURE, entityPoint);
        } else
            serviceValidationExceptionHadle(legalStructure.getServiceStatus(), ServiceEntity.LEGALSTRUCTURE, entityPoint);

        return legalStructure;
    }

    @Override
    public CommonBranchResponseResource validateComnBranch(String tenantId, String id, String branchName,
                                                           EntityPoint entityPoint) {

        CommonBranchResponseResource commonBranchResponseResource = (CommonBranchResponseResource) getDetailFromService(
                tenantId, id, urlCommonBranch, CommonBranchResponseResource.class);

        if (commonBranchResponseResource == null || commonBranchResponseResource.getServiceStatus() == null) {
            if (commonBranchResponseResource == null
                    || !commonBranchResponseResource.getName().equalsIgnoreCase(branchName))
                throw new InvalidServiceIdException(environment.getProperty("common.not-match"),
                        ServiceEntity.BRANCH_ID, entityPoint);
        } else
            serviceValidationExceptionHadle(commonBranchResponseResource.getServiceStatus(), ServiceEntity.INDUSTRY,
                    entityPoint);

        return commonBranchResponseResource;
    }

    @Override
    public ComnSector validateComnSector(String tenantId, String id, String sectorName, EntityPoint entityPoint) {

        ComnSector comnSector = (ComnSector) getDetailFromService(tenantId, id, urlComnSector, ComnSector.class);

        if (comnSector == null || comnSector.getServiceStatus() == null) {
            if (comnSector == null || !comnSector.getSectorName().equalsIgnoreCase(sectorName))
                throw new InvalidServiceIdException(environment.getProperty("comnSectorId.invalid"),
                        ServiceEntity.INDUSTRY, entityPoint);
        } else
            serviceValidationExceptionHadle(comnSector.getServiceStatus(), ServiceEntity.INDUSTRY, entityPoint);

        return comnSector;
    }

    @Override
    public ResidencyType validateResidencyType(String tenantId, String residencyTypeId, String residencyTypeName, EntityPoint entityPoint) {

        ResidencyType residencyType = (ResidencyType) getDetailFromService(tenantId, residencyTypeId, urlResidencyType, ResidencyType.class);

        if (residencyType == null || residencyType.getServiceStatus() == null) {
            if (residencyType == null || !residencyType.getName().equalsIgnoreCase(residencyTypeName) || !residencyType.getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString()))
                throw new InvalidServiceIdException(environment.getProperty("residencyTypeId.invalid"), ServiceEntity.RESIDENCYTYPE, entityPoint);
        } else
            serviceValidationExceptionHadle(residencyType.getServiceStatus(), ServiceEntity.RESIDENCYTYPE, entityPoint);

        return residencyType;
    }

    /**
     * get id and description from service
     *
     * @param Url
     * @param Description
     * @param tenantId
     * @return Object
     */
    private Object getDetailFromService(String tenantId, String id, String serviceUrl, Class<?> classObject) {
        LoggerRequest.getInstance().logInfo("getDetailFromService ### IN getDetailFromService ");
        try {
            LoggerRequest.getInstance().logInfo("getDetailFromService ### IN getDetailFromService 01");
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            LoggerRequest.getInstance().logInfo("getDetailFromService ### IN getDetailFromService 01.1");
            return restTemplate.exchange(serviceUrl + tenantId + "/" + id, HttpMethod.GET, entity, classObject).getBody();
        } catch (RestClientException e) {
            LoggerRequest.getInstance().logInfo("getDetailFromService ### IN getDetailFromService 01.2");
            String result = null;
            if (e.getMessage().contains("503")) {
                result = serviceStatus + ServiceStatus.NOT_AVAILABLE.toString() + "\"}";
            } else if (e.getMessage().contains("500")) {
                result = serviceStatus + ServiceStatus.EXCEPTION.toString() + "\"}";
            } else if (e.getMessage().contains("404")) {
                result = serviceStatus + ServiceStatus.NOT_FOUND.toString() + "\"}";
            } else if (e.getMessage().contains("400")) {
                result = serviceStatus + ServiceStatus.BAD_REQUEST.toString() + "\"}";
            } else if (e.getMessage().contains("204")) {
                LoggerRequest.getInstance().logInfo("getDetailFromService ### IN getDetailFromService 02");
                result = serviceStatus + ServiceStatus.EXCEPTION.toString() + "\"}";
            }
            LoggerRequest.getInstance().logInfo("getDetailFromService ### IN getDetailFromService 03");
            ObjectMapper mapper = new ObjectMapper();
            LoggerRequest.getInstance().logInfo("getDetailFromService ### IN getDetailFromService 04");
            try {
                LoggerRequest.getInstance().logInfo("getDetailFromService ### IN getDetailFromService 05");
                return mapper.readValue(result, classObject);
            } catch (IOException e1) {
                LoggerRequest.getInstance().logInfo("getDetailFromService ### IN getDetailFromService 06");
                return null;
            }
        }
    }

    private void serviceValidationExceptionHadle(ServiceStatus serviceStatus, ServiceEntity serviceEntity, EntityPoint entityPoint) {
        switch (serviceStatus) {
            case NOT_AVAILABLE:
                throw new InvalidServiceIdException(environment.getProperty("common.not-available"), serviceEntity, entityPoint);
            case NOT_FOUND:
                throw new InvalidServiceIdException(environment.getProperty("common.not-found"), serviceEntity, entityPoint);
            case EXCEPTION:
                throw new InvalidServiceIdException(environment.getProperty("common.internal-server-error"), serviceEntity, entityPoint);
            case BAD_REQUEST:
                throw new InvalidServiceIdException(environment.getProperty("common.bad-request"), serviceEntity, entityPoint);
            default:
        }
    }

    @Override
    public ExistsDocumentResponseResource existDocument(String tenantId, String documentId, EntityPoint entityPoint) {
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(urlDocumentUpload + tenantId + "/exist/" + documentId + "?origin=lending", HttpMethod.GET, entity, ExistsDocumentResponseResource.class).getBody();
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
                return mapper.readValue(result, ExistsDocumentResponseResource.class);
            } catch (IOException e1) {
                return null;
            }
        }

    }

    @Override
    public TaxEventResponse getTaxEvent(String tenantId, String taxCode) {

        //temporary should add into config server repo
//		String url = "http://132.145.228.83/lending-transaction/tax-event/AnRkr/tx-evnt/code/"+taxCode;
        String url = taxEvent + tenantId + "/tx-evnt/code/" + taxCode;
        TaxEventResponse taxEventResponse = (TaxEventResponse) getByService(url, TaxEventResponse.class);
        return taxEventResponse;
    }

    /**
     * @param Url
     * @param Description
     * @param tenantId
     * @return Object
     * @author Senitha
     * <p>
     * get id and description from service
     */
    public Object checkTransactionCodeExist(String tenantId, String id, String serviceUrl, Class<?> classObject) {
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(serviceUrl + tenantId + "/subcode/" + id, HttpMethod.GET, entity, classObject).getBody();
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
    public Timestamp getCreateOrModifyDate() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        return new Timestamp(now.getTime());
    }

    @Override
    public Long stringToLong(String value) {
        return Long.parseLong(value);
    }


    @Override
    public ResidencyType getResidencyType(String tenantId, Long residencyTypeId) {
        ResidencyType residencyType = (ResidencyType) getDetailFromService(tenantId, residencyTypeId.toString(), urlResidencyType, ResidencyType.class);
        return residencyType;
    }

    /**
     * @author Senitha
     * <p>
     * Validations for frequency
     * @date 05-06-2021
     */
    @Override
    public FrequencyResponse validateFrequency(String tenantId, Long frequencyTypeId, String frequencyTypeName, Long unit, EntityPoint entityPoint) {

        FrequencyResponse frequency = (FrequencyResponse) getDetailFromService(tenantId, frequencyTypeId.toString(), urlFrequency, FrequencyResponse.class);
        if (frequency == null) {
            throw new InvalidServiceIdException(environment.getProperty("frequencyTypeId.invalid"), ServiceEntity.FREQUENCY, EntityPoint.APPLICATION_FREQUENCY);
        }
        if (frequency.getServiceStatus() == null) {
            if (!frequency.getId().equals(frequencyTypeId) || !frequency.getName().equalsIgnoreCase(frequencyTypeName)) {
                throw new InvalidServiceIdException(environment.getProperty("frequencyTypeId.not-match"), ServiceEntity.FREQUENCY, EntityPoint.APPLICATION_FREQUENCY);
            }
            if (!frequency.getUnit().equals(unit)) {
                throw new InvalidServiceIdException(environment.getProperty("unit.not-match"), ServiceEntity.UNIT, EntityPoint.APPLICATION_FREQUENCY);
            }
            if (!frequency.getStatus().equals("ACTIVE")) {
                throw new InvalidServiceIdException(environment.getProperty("frequencyTypeId.not-match"), ServiceEntity.FREQUENCY, EntityPoint.APPLICATION_FREQUENCY);
            }
        } else {
            serviceValidationExceptionHadle(frequency.getServiceStatus(), ServiceEntity.FREQUENCY, entityPoint);
        }
        return frequency;

    }

    /**
     * @author Senitha
     * <p>
     * Get Frequency
     * @date 05-06-2021
     */
    @Override
    public FrequencyResponse getFrequency(String tenantId, Long frequencyTypeId, EntityPoint entityPoint) {
        FrequencyResponse frequencyResponse = (FrequencyResponse) getDetailFromService(tenantId, frequencyTypeId.toString(), urlFrequency, FrequencyResponse.class);
        return frequencyResponse;
    }

    /**
     * @author Senitha
     * <p>
     * Validations for Transaction Code
     * @date 05-06-2021
     */
    @Override
    public BankTransactionCodeResponse validateTransactionCode(String tenantId, Long transactionCodeId, String transactionCode, EntityPoint entityPoint) {

        BankTransactionCodeResponse bankTransactionCodeResponse = (BankTransactionCodeResponse) getDetailFromService(tenantId, transactionCodeId.toString(), urlTransactionCode, BankTransactionCodeResponse.class);
        if (bankTransactionCodeResponse == null) {
            throw new InvalidServiceIdException(environment.getProperty("transactionCodeId.invalid"), ServiceEntity.TRANSACTIONCODE, EntityPoint.OTHER_FEE_TYPE);
        }
        if (bankTransactionCodeResponse.getServiceStatus() == null) {
            if (!bankTransactionCodeResponse.getId().equals(transactionCodeId) || !bankTransactionCodeResponse.getBankTransCode().equals(transactionCode)) {
                throw new InvalidServiceIdException(environment.getProperty("transactionCodeId.not-match"), ServiceEntity.TRANSACTIONCODE, EntityPoint.OTHER_FEE_TYPE);
            }
            if (!bankTransactionCodeResponse.getStatus().equals("ACTIVE")) {
                throw new InvalidServiceIdException(environment.getProperty("transactionCodeId.invalid"), ServiceEntity.TRANSACTIONCODE, EntityPoint.OTHER_FEE_TYPE);
            }
        } else {
            serviceValidationExceptionHadle(bankTransactionCodeResponse.getServiceStatus(), ServiceEntity.TRANSACTIONCODE, entityPoint);
        }
        return bankTransactionCodeResponse;

    }

    /**
     * @author Senitha
     * <p>
     * Validations for Transaction Sub Code
     * @date 05-06-2021
     */
    @Override
    public BankTransactionSubCodeResponse validateTransactionSubCode(String tenantId, Long transactionCodeId, Long transactionSubCodeId, String transactionSubCode, EntityPoint entityPoint) {

        BankTransactionSubCodeResponse bankTransactionSubCodeResponse = (BankTransactionSubCodeResponse) checkTransactionCodeExist(tenantId, transactionSubCodeId.toString(), urlTransactionSubCode, BankTransactionSubCodeResponse.class);
        if (bankTransactionSubCodeResponse == null) {
            throw new InvalidServiceIdException(environment.getProperty("transactionSubCodeId.invalid"), ServiceEntity.TRANSACTIONSUBCODE, EntityPoint.OTHER_FEE_TYPE);
        }
        if (bankTransactionSubCodeResponse.getServiceStatus() == null) {
            if (!bankTransactionSubCodeResponse.getId().equals(transactionSubCodeId) || !bankTransactionSubCodeResponse.getCodeId().equals(transactionCodeId)
                    || !bankTransactionSubCodeResponse.getSubCode().equals(transactionSubCode)) {
                throw new InvalidServiceIdException(environment.getProperty("transactionSubCodeId.not-match"), ServiceEntity.TRANSACTIONSUBCODE, EntityPoint.OTHER_FEE_TYPE);
            }
            if (!bankTransactionSubCodeResponse.getStatus().equals("ACTIVE")) {
                throw new InvalidServiceIdException(environment.getProperty("transactionSubCodeId.invalid"), ServiceEntity.TRANSACTIONSUBCODE, EntityPoint.OTHER_FEE_TYPE);
            }
        } else {
            serviceValidationExceptionHadle(bankTransactionSubCodeResponse.getServiceStatus(), ServiceEntity.TRANSACTIONSUBCODE, entityPoint);
        }
        return bankTransactionSubCodeResponse;
    }

    /**
     * @author Senitha
     * <p>
     * Get Transaction Code
     * @date 05-06-2021
     */
    @Override
    public BankTransactionCodeResponse getTransactionCode(String tenantId, Long transactionCodeId, EntityPoint entityPoint) {
        BankTransactionCodeResponse bankTransactionCodeResponse = (BankTransactionCodeResponse) getDetailFromService(tenantId, transactionCodeId.toString(), urlTransactionCode, BankTransactionCodeResponse.class);
        return bankTransactionCodeResponse;
    }

    /**
     * @author Senitha
     * <p>
     * Get Transaction Sub Code
     * @date 05-06-2021
     */
    @Override
    public BankTransactionSubCodeResponse getTransactionSubCode(String tenantId, Long transactionSubCodeId, EntityPoint entityPoint) {
        BankTransactionSubCodeResponse bankTransactionSubCodeResponse = (BankTransactionSubCodeResponse) getDetailFromService(tenantId, transactionSubCodeId.toString(), urlTransactionSubCode, BankTransactionSubCodeResponse.class);
        return bankTransactionSubCodeResponse;
    }

    @Override
    public BankTransactionSubCodeResponse getTransactionTypeCode(String tenantId, String transactionSubCodeId) {
        String url = urlTransactionSubCode + "/" + tenantId + "/subcode/" + transactionSubCodeId;
        BankTransactionSubCodeResponse bankTransactionSubCodeResponse = (BankTransactionSubCodeResponse) getByService(url, BankTransactionSubCodeResponse.class);
        return bankTransactionSubCodeResponse;
    }

    /**
     * @author MenukaJ
     * @date 10-06-2021
     */
    @Override
    public WorkflowRulesResource invokedLendingProductRule(WorkflowType workflowType, String domainPath, String domainName, String tenantId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(usernameBPM, passwordBPM);
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplateSetRequestFactory();
        String json = "{\"lookup\": \"kie-session\", \"commands\": [{\"insert\": {\"object\": {\"" + domainPath + "\": {\"referenceCode\":\"" + workflowType + "\"}},\"out-identifier\":\"" + domainName + "\"}},{\"fire-all-rules\": {}}]}";
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        WorkflowRulesResource workflowRulesResource = new WorkflowRulesResource();
        try {
            String responseString = restTemplate.exchange(urlLendingRuleContainer.replace("tenantId", tenantId), HttpMethod.POST, entity, String.class).getBody();
            LoggerRequest.getInstance().logInfo1("***********responseString***********" + responseString);
            JSONObject responseObj = new JSONObject(responseString);
            JSONObject responseResult = responseObj.getJSONObject("result");
            JSONObject responseExecutionResults = responseResult.getJSONObject("execution-results");
            JSONArray responseResults = responseExecutionResults.getJSONArray("results");
            JSONObject responseResults0 = responseResults.getJSONObject(0);
            JSONObject value = responseResults0.getJSONObject("value");
            JSONObject responseTransactionApproval = value.getJSONObject(domainPath);

            workflowRulesResource.setApprovalRequired(responseTransactionApproval.getString("approvalRequired"));
            workflowRulesResource.setApprovalAllowed(responseTransactionApproval.getString("approvalAllowed"));


        } catch (RestClientException e) {
            if (e.getMessage().contains("503")) {
                throw new WorkFlowException(environment.getProperty("common.not-available"), workflowType);
            } else if (e.getMessage().contains("500")) {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
            } else if (e.getMessage().contains("404")) {
                throw new WorkFlowException(environment.getProperty("common.not-found"), workflowType);
            } else if (e.getMessage().contains("400")) {
                throw new WorkFlowException(environment.getProperty("common.bad-request"), workflowType);
            } else if (e.getMessage().contains("504")) {
                throw new WorkFlowException(environment.getProperty("common.gateway-time-out"), workflowType);
            } else {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
            }
        } catch (JSONException je) {
            throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
        }
        return workflowRulesResource;
    }

    public void restTemplateSetRequestFactory() {
        try {
            KeyStore clientStore = KeyStore.getInstance("PKCS12");
            clientStore.load(getClass().getResourceAsStream("classpath:cert/" + pamCertLocation), "certpassword".toCharArray());

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
        } catch (UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException | KeyManagementException e) {
            LoggerRequest.getInstance().logCommonError(e.getMessage());
        }
    }

    /**
     * @author MenukaJ
     * @date 10-06-2021
     */
    @Override
    public Long initiateLendingProductWorkFlow(WorkflowInitiationRequestResource workflowInitiationRequestResource, String tenantId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(usernameBPM, passwordBPM);
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplateSetRequestFactory();
        Long processId = null;
        if (workflowInitiationRequestResource.getApprovWorkflowType() != null && workflowInitiationRequestResource.getApprovWorkflowType().equals(WorkflowType.ELIGI_TEMP_APPROVAL)) {
            workflowInitiationRequestResource.setApprovalUser("Eligibility-Approve");
            HttpEntity<WorkflowInitiationRequestResource> entity = new HttpEntity<>(workflowInitiationRequestResource, headers);
            processId = initiateWorkFlow(eligiTempBpm.replace("tenantId", tenantId), entity, WorkflowType.ELIGI_TEMP_APPROVAL);

        } else if (workflowInitiationRequestResource.getApprovWorkflowType() != null && workflowInitiationRequestResource.getApprovWorkflowType().equals(WorkflowType.FEE_CHARGE_TEMP_APPROVAL)) {
            workflowInitiationRequestResource.setApprovalUser("Fee-Charge-Approve");
            HttpEntity<WorkflowInitiationRequestResource> entity = new HttpEntity<>(workflowInitiationRequestResource, headers);
            processId = initiateWorkFlow(feeChargeTempBpm.replace("tenantId", tenantId), entity, WorkflowType.FEE_CHARGE_TEMP_APPROVAL);

        } else if (workflowInitiationRequestResource.getApprovWorkflowType() != null && workflowInitiationRequestResource.getApprovWorkflowType().equals(WorkflowType.REPAYMENT_TEMP_APPROVAL)) {
            workflowInitiationRequestResource.setApprovalUser("Repayment-Approve");
            HttpEntity<WorkflowInitiationRequestResource> entity = new HttpEntity<>(workflowInitiationRequestResource, headers);
            processId = initiateWorkFlow(repaymentTempBpm.replace("tenantId", tenantId), entity, WorkflowType.REPAYMENT_TEMP_APPROVAL);

        } else if (workflowInitiationRequestResource.getApprovWorkflowType() != null && workflowInitiationRequestResource.getApprovWorkflowType().equals(WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL)) {
            workflowInitiationRequestResource.setApprovalUser("master-definition-Approve");
            HttpEntity<WorkflowInitiationRequestResource> entity = new HttpEntity<>(workflowInitiationRequestResource, headers);
            processId = initiateWorkFlow(masterDefBpm.replace("tenantId", tenantId), entity, WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL);

        } else if (workflowInitiationRequestResource.getApprovWorkflowType() != null && workflowInitiationRequestResource.getApprovWorkflowType().equals(WorkflowType.INTEREST_TEMP_APPROVAL)) {
            workflowInitiationRequestResource.setApprovalUser("interest-temp-Approve");
            HttpEntity<WorkflowInitiationRequestResource> entity = new HttpEntity<>(workflowInitiationRequestResource, headers);
            processId = initiateWorkFlow(interestTempBpm.replace("tenantId", tenantId), entity, WorkflowType.INTEREST_TEMP_APPROVAL);

        } else if (workflowInitiationRequestResource.getApprovWorkflowType() != null && workflowInitiationRequestResource.getApprovWorkflowType().equals(WorkflowType.FEATURE_BENEFIT_TEMP_APPROVAL)) {
            workflowInitiationRequestResource.setApprovalUser("Feature-Benefit-Template-Approve");
            HttpEntity<WorkflowInitiationRequestResource> entity = new HttpEntity<>(workflowInitiationRequestResource, headers);
            processId = initiateWorkFlow(featureBenefitTemplateBpm.replace("tenantId", tenantId), entity, WorkflowType.FEATURE_BENEFIT_TEMP_APPROVAL);

        }
        return processId;
    }

    private Long initiateWorkFlow(String url, HttpEntity<WorkflowInitiationRequestResource> entity, WorkflowType workflowType) {
        Long processId = null;
        try {
            processId = restTemplate.exchange(url, HttpMethod.POST, entity, Long.class).getBody();
        } catch (RestClientException e) {
            if (e.getMessage().contains("503")) {
                throw new WorkFlowException(environment.getProperty("common.not-available"), workflowType);
            } else if (e.getMessage().contains("500")) {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
            } else if (e.getMessage().contains("404")) {
                throw new WorkFlowException(environment.getProperty("common.not-found"), workflowType);
            } else if (e.getMessage().contains("400")) {
                throw new WorkFlowException(environment.getProperty("common.bad-request"), workflowType);
            } else {
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
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = null;
        if (workflowType.equals(WorkflowType.ELIGI_TEMP_APPROVAL)) {
            url = eligiTempApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.FEE_CHARGE_TEMP_APPROVAL)) {
            url = feeChargeTempApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.REPAYMENT_TEMP_APPROVAL)) {
            url = repaymentTempApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL)) {
            url = masterDefApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.INTEREST_TEMP_APPROVAL)) {
            url = interestTempApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.FEATURE_BENEFIT_TEMP_APPROVAL)) {
            url = featureBenefitTemplateApprovalBpm.replace("tenantId", tenantId);
        }
        try {
            url = url + workflowProcessId + "/workitems";
            WorkFlowWorkItem workFlowWorkItem = restTemplate.exchange(url, HttpMethod.GET, entity, WorkFlowWorkItem.class).getBody();
            if (workFlowWorkItem != null && workFlowWorkItem.getWorkItemInstance() != null && !workFlowWorkItem.getWorkItemInstance().isEmpty()) {
                return workFlowWorkItem.getWorkItemInstance().get(0).getWorkItemId();
            } else {
                throw new WorkFlowException(environment.getProperty("common.workitems-not-available"), workflowType);
            }
        } catch (RestClientException e) {
            if (e.getMessage().contains("503")) {
                throw new WorkFlowException(environment.getProperty("common.not-available"), workflowType);
            } else if (e.getMessage().contains("500")) {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
            } else if (e.getMessage().contains("404")) {
                throw new WorkFlowException(environment.getProperty("common.not-found"), workflowType);
            } else if (e.getMessage().contains("400")) {
                throw new WorkFlowException(environment.getProperty("common.bad-request"), workflowType);
            } else if (e.getMessage().contains("504")) {
                throw new WorkFlowException(environment.getProperty("common.gateway-time-out"), workflowType);
            } else {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
            }
        }
    }

    /**
     * @author MenukaJ
     * @date 10-06-2021
     */
    @Override
    public void approveWorkFlow(Long workflowProcessId, WorkflowType workflowType, String username, String tenantId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(usernameBPM, passwordBPM);
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplateSetRequestFactory();
        WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
        workflowInitiationRequestResource.setApprovalUser(username);
        HttpEntity<WorkflowInitiationRequestResource> entity = new HttpEntity<>(workflowInitiationRequestResource, headers);

        Long workItemId = getWorkFlowWorkItem(workflowProcessId, workflowType, tenantId);

        String url = null;
        if (workflowType.equals(WorkflowType.ELIGI_TEMP_APPROVAL)) {
            url = eligiTempApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.FEE_CHARGE_TEMP_APPROVAL)) {
            url = feeChargeTempApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.REPAYMENT_TEMP_APPROVAL)) {
            url = repaymentTempApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL)) {
            url = masterDefApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.INTEREST_TEMP_APPROVAL)) {
            url = interestTempApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.FEATURE_BENEFIT_TEMP_APPROVAL)) {
            url = featureBenefitTemplateApprovalBpm.replace("tenantId", tenantId);
        }

        try {
            url = url + workflowProcessId + "/workitems/" + workItemId + "/completed";
            restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        } catch (RestClientException e) {
            if (e.getMessage().contains("503")) {
                throw new WorkFlowException(environment.getProperty("common.not-available"), workflowType);
            } else if (e.getMessage().contains("500")) {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
            } else if (e.getMessage().contains("404")) {
                throw new WorkFlowException(environment.getProperty("common.not-found"), workflowType);
            } else if (e.getMessage().contains("400")) {
                throw new WorkFlowException(environment.getProperty("common.bad-request"), workflowType);
            } else {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
            }
        }

    }

    /**
     * @author MenukaJ
     * @date 10-06-2021
     */
    @Override
    public void abortedWorkFlow(Long workflowProcessId, WorkflowType workflowType, String username, String tenantId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(usernameBPM, passwordBPM);
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplateSetRequestFactory();
        WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
        workflowInitiationRequestResource.setApprovalUser(username);
        HttpEntity<WorkflowInitiationRequestResource> entity = new HttpEntity<>(workflowInitiationRequestResource, headers);

        Long workItemId = getWorkFlowWorkItem(workflowProcessId, workflowType, tenantId);

        String url = null;
        if (workflowType.equals(WorkflowType.ELIGI_TEMP_APPROVAL)) {
            url = eligiTempApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.FEE_CHARGE_TEMP_APPROVAL)) {
            url = feeChargeTempApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.REPAYMENT_TEMP_APPROVAL)) {
            url = repaymentTempApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL)) {
            url = masterDefApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.INTEREST_TEMP_APPROVAL)) {
            url = interestTempApprovalBpm.replace("tenantId", tenantId);
        } else if (workflowType.equals(WorkflowType.FEATURE_BENEFIT_TEMP_APPROVAL)) {
            url = featureBenefitTemplateApprovalBpm.replace("tenantId", tenantId);
        }

        try {
            url = url + workflowProcessId + "/workitems/" + workItemId + "/aborted";
            restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        } catch (RestClientException e) {
            if (e.getMessage().contains("503")) {
                throw new WorkFlowException(environment.getProperty("common.not-available"), workflowType);
            } else if (e.getMessage().contains("500")) {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
            } else if (e.getMessage().contains("404")) {
                throw new WorkFlowException(environment.getProperty("common.not-found"), workflowType);
            } else if (e.getMessage().contains("400")) {
                throw new WorkFlowException(environment.getProperty("common.bad-request"), workflowType);
            } else {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), workflowType);
            }
        }

    }


    @Override
    public Currency getCurrency(String tenantId, Long currencyId) {
        Currency currency = (Currency) getDetailFromService(tenantId, currencyId.toString(), urlCurrency, Currency.class);
        return currency;
    }


    @Override
    public AssetType getAssetType(String tenantId, Long assetTypeId) {
        AssetType assetType = (AssetType) getDetailFromService(tenantId, assetTypeId.toString(), assetTypeUrl, AssetType.class);
        return assetType;
    }


    @Override
    public AssetType validateAssetType(String tenantId, String assetTypeId, String assetTypeName, EntityPoint entityPoint) {
        AssetType assetType = (AssetType) getDetailFromService(tenantId, assetTypeId, assetTypeUrl, AssetType.class);

        if (assetType == null || assetType.getServiceStatus() == null) {
            if (assetType == null || !assetType.getName().equalsIgnoreCase(assetTypeName)) {
                throw new InvalidServiceIdException(environment.getProperty("assetTypeId.invalid"), ServiceEntity.AESSET_TYPE, entityPoint);
            }

        } else {
            serviceValidationExceptionHadle(assetType.getServiceStatus(), ServiceEntity.AESSET_TYPE, entityPoint);
        }
        return assetType;
    }


    @Override
    public BigDecimal stringToBigDecimal(String value) {
        return BigDecimal.valueOf(new Double(value));
    }


    @Override
    public BankTransactionSubCodeResponse validateBankTransactionSubCode(String tenantId, String subCodeId, String subCodeDesc, EntityPoint entityPoint) {

        BankTransactionSubCodeResponse bankTransactionSubCodeResponse = (BankTransactionSubCodeResponse) checkTransactionCodeExist(tenantId, subCodeId.toString(), urlTransactionSubCode, BankTransactionSubCodeResponse.class);
        if (bankTransactionSubCodeResponse == null) {
            throw new ValidateRecordException(environment.getProperty("transactionSubCodeId.invalid"), "transactionSubCodeId");
        }
        if (!bankTransactionSubCodeResponse.getDescription().equals(subCodeDesc)) {
            throw new ValidateRecordException(environment.getProperty("transactionSubCodeId.not-match"), "transactionSubCodeId");
        }

        if (bankTransactionSubCodeResponse.getStatus() != null) {

            if (!bankTransactionSubCodeResponse.getStatus().equals("ACTIVE")) {
                throw new ValidateRecordException(environment.getProperty("transactionSubCodeId.invalid"), "transactionSubCodeId");
            }
        } else {
            throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "transactionSubCodeId");
        }
        return bankTransactionSubCodeResponse;

    }

    @Override
    public GeoResponse validateGeoHierachy(String tenantId, String id, String name, String parentId, String geoLevelCode, String path) {

        GeoResponse geoResponse = (GeoResponse) getDetailFromService(tenantId, id, urlGeoHierachy, GeoResponse.class);

        if (geoResponse == null) {
            throw new ValidateRecordException(environment.getProperty("geoHeirachy.id-invalid"), "message");
        }

        if ((CommonStatus.INACTIVE).equals(geoResponse.getGeohiStatus())) {
            throw new ValidateRecordException(environment.getProperty("geoHeirachy.status-inactive"), "message");
        }
        if (!(name).equals(geoResponse.getGeohiName())) {
            throw new ValidateRecordException(environment.getProperty("geoHeirachy.name-incompatible"), "message");
        }
        if (parentId != null) {
            if (!parentId.equals(geoResponse.getGeohiParentId())) {
                throw new ValidateRecordException(environment.getProperty("geoHeirachy.parent-incompatible"), "message");
            }
        } else if (!("0").equals(geoResponse.getGeohiParentId())) {
            throw new ValidateRecordException(environment.getProperty("geoHeirachy.parent-incompatible"), "message");
        }
        return geoResponse;


    }

    @Override
    public CommonBranchResponseResource validateCommonBranch(String tenantId, String branchId, String branchName) {
        CommonBranchResponseResource commonBranchResponseResource = (CommonBranchResponseResource) getDetailFromService(tenantId, branchId, urlCommonBranch, CommonBranchResponseResource.class);
        if (commonBranchResponseResource == null || commonBranchResponseResource.getServiceStatus() == null) {
            if (branchName != null) {
                if (commonBranchResponseResource == null
                        || !commonBranchResponseResource.getName()
                        .equalsIgnoreCase(branchName)
                        || !commonBranchResponseResource.getBrhStatus()
                        .equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
                    throw new DetailValidateException(environment.getProperty("common.not-match"), ServiceEntity.BRANCH_ID, ServicePoint.LEAD_INFOR);
                }
            }
        } else {
            throw new DetailValidateException(commonBranchResponseResource.getServiceStatus().toString(), ServiceEntity.BRANCH_ID, ServicePoint.LEAD_INFOR);
        }
        return commonBranchResponseResource;
    }

    @Override
    public UserProfileResponse validateUserProfileByUserId(String tenantId, String userId, String userName) {

        String url = urlUserProfile + tenantId + "/user-id/" + userId;
        LoggerRequest.getInstance().logInfo("url " + url);
        UserProfileResponse userProfileResponse = (UserProfileResponse) getByService(url, UserProfileResponse.class);
        if (userProfileResponse == null) {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "userId");
        }
        if (userProfileResponse.getServiceStatus() == null) {
            if (!userProfileResponse.getUserName().equals(userName)) {
                throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "username");
            }
            if (!userProfileResponse.getUserStatus().equals(CommonStatus.ACTIVE.toString())) {
                throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "userStatus");
            }
        } else {
            serviceValidationExceptionHadle(userProfileResponse.getServiceStatus(), ServiceEntity.TRANSACTIONSUBCODE, null);
        }
        return userProfileResponse;

    }

    public Timestamp getSyncTs() {
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


    /**
     * @param String tenantId   - tenantId
     * @param String repaymentFrequencyId   - repayment Frequency Id
     * @param String serviceUrl   - serviceUrl
     * @return - RepaymentFrequencyResponse class
     * @author Senitha
     * <p>
     * Validate Repayment Frequency
     */
    @Override
    public RepaymentFrequencyResponse validateRepaymentFrequency(String tenantId, String repaymentFrequencyId) {

        String url = urlRepaymentFrequency + tenantId + "/" + repaymentFrequencyId;
        RepaymentFrequencyResponse repaymentFrequencyResponse = (RepaymentFrequencyResponse) getDetailFromService(tenantId, repaymentFrequencyId, url, RepaymentFrequencyResponse.class);
        if (repaymentFrequencyResponse == null) {
            throw new ValidateRecordException(environment.getProperty("common.not-available"), "repaymentFrequencyId");
        }
        if (repaymentFrequencyResponse.getStatus() != null) {

            if (!repaymentFrequencyResponse.getStatus().equals("ACTIVE")) {
                throw new ValidateRecordException(environment.getProperty("common.status-inactive"), "repaymentFrequencyId");
            }
        } else {
            throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "repaymentFrequencyId");
        }
        return repaymentFrequencyResponse;
    }

    @Override
    public DocumentResource validateDocument(String tenantId, Long documentId, String documentName) {

        String url = urlDocumentDetails;

        DocumentResource documentResource = (DocumentResource) getDetailFromService(tenantId, documentId.toString(), url, DocumentResource.class);

        if (documentResource == null) {
            throw new ValidateRecordException(environment.getProperty("documentId.invalid"), "documentId");
        }
        if (documentResource.getStatus() != null) {

            if (!documentResource.getStatus().equals(CommonStatus.ACTIVE.toString())) {
                throw new ValidateRecordException(environment.getProperty("common.status-inactive"), "documentId");
            } else {

                if (!documentName.equals(documentResource.getName())) {
                    throw new ValidateRecordException(environment.getProperty("documetName.not-match"), "documentName");
                }
            }
        } else {
            throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "documentId");
        }
        return documentResource;
    }


    private Object getByService(String url, Class<?> classObject) {
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(url, HttpMethod.GET, entity, classObject).getBody();
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
    public RiskTemplateResponse validateRiskTemplate(String tenantId, String riskTemplateId) {

        RiskTemplateResponse riskTemplateResponse = (RiskTemplateResponse) this.getDetailFromService(tenantId,
                riskTemplateId, urlRiskTemplate, RiskTemplateResponse.class);
        if (riskTemplateResponse == null) {
            throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "riskTemplateId");
        }
        if (riskTemplateResponse.getServiceStatus() == null) {
            if (!riskTemplateResponse.getStatus().equals("ACTIVE")) {
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "riskTemplateId");
            }
        } else {
            serviceValidationExceptionHadle(riskTemplateResponse.getServiceStatus(), ServiceEntity.RISK_TEMPLATE_ID,
                    EntityPoint.RISK_TEMPLATE_ID);
        }
        return riskTemplateResponse;

    }

    @Override
    public TaxCodeResponse validateTaxCode(String tenantId, String taxCodeId) {

        TaxCodeResponse taxCodeResponse = (TaxCodeResponse) this.getDetailFromService(tenantId, taxCodeId, urlTaxCode,
                TaxCodeResponse.class);
        if (taxCodeResponse == null) {
            throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "taxCodeId");
        }
        if (taxCodeResponse.getServiceStatus() == null) {
            if (!taxCodeResponse.getTaxCodeStatus().equals("ACTIVE")) {
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "taxCodeId");
            }
        } else {
            serviceValidationExceptionHadle(taxCodeResponse.getServiceStatus(), ServiceEntity.TAX_CODE_ID,
                    EntityPoint.TAX_CODE_ID);
        }
        return taxCodeResponse;

    }

    @Override
    public void customerSubTypeNonIndExistValidation(String tenantId, Long customerSubCategoryNonIndId,
                                                     String customerSubCategoryNonIndDesc) {
        CustomerSubTypeNonInd customerSubTypeNonInd = (CustomerSubTypeNonInd) remoteService.checkExist(tenantId,
                customerSubCategoryNonIndId.toString(), urlLegalStructure, CustomerSubTypeNonInd.class);
        if (customerSubTypeNonInd == null || customerSubTypeNonInd.getServiceStatus() == null) {
            if (customerSubTypeNonInd == null || customerSubCategoryNonIndDesc.equals(""))
                throw new InvalidServiceIdException(environment.getProperty("customerSubCategoryNonInd.not-null"),
                        ServiceEntity.CUSTSUBTYPENONIND, EntityPoint.TAXPROFILE);
            if (customerSubTypeNonInd == null
                    || !customerSubTypeNonInd.getDescription().equals(customerSubCategoryNonIndDesc)
                    || !customerSubTypeNonInd.getStatus().equals("ACTIVE"))
                throw new InvalidServiceIdException(environment.getProperty("customerSubCategoryNonInd.invalid"),
                        ServiceEntity.CUSTSUBTYPENONIND, EntityPoint.TAXPROFILE);
        } else {
            remoteService.serviceValidationExceptionHadle(customerSubTypeNonInd.getServiceStatus(),
                    ServiceEntity.CUSTSUBTYPENONIND, EntityPoint.TAXPROFILE);
        }
    }

    @Override
    public void customerSubTypeIndExistValidation(String tenantId, Long customerSubCategoryIndId,
                                                  String customerSubCategoryIndDesc) {
        CustomerSubTypeInd customerSubTypeInd = (CustomerSubTypeInd) remoteService.checkExist(tenantId,
                customerSubCategoryIndId.toString(), urlComnCommonIndividualType, CustomerSubTypeInd.class);
        if (customerSubTypeInd == null || customerSubTypeInd.getServiceStatus() == null) {
            if (customerSubTypeInd == null || customerSubCategoryIndDesc.equals("")) // Added by Senitha on 01-MAY-2020
                throw new InvalidServiceIdException(environment.getProperty("customerSubCategoryInd.not-null"),
                        ServiceEntity.CUSTSUBTYPEIND, EntityPoint.TAXPROFILE);
            if (customerSubTypeInd == null
                    || !customerSubTypeInd.getPersonTypeDescription().equals(customerSubCategoryIndDesc)
                    || !customerSubTypeInd.getPersonTypeStatus().equals("ACTIVE"))
                throw new InvalidServiceIdException(environment.getProperty("customerSubCategoryInd.invalid"),
                        ServiceEntity.CUSTSUBTYPEIND, EntityPoint.TAXPROFILE);
        } else {
            remoteService.serviceValidationExceptionHadle(customerSubTypeInd.getServiceStatus(),
                    ServiceEntity.CUSTSUBTYPEIND, EntityPoint.TAXPROFILE);
        }
    }


    @Override
    public void customerResidentTypeExistValidation(String tenantId, Long customerResidentTypeId,
                                                    String customerResidentTypeDesc) {
        CustomerResidencyType customerResidencyType = (CustomerResidencyType) remoteService.checkExist(tenantId,
                customerResidentTypeId.toString(), urlResidencyType, CustomerResidencyType.class);
        if (customerResidencyType == null || customerResidencyType.getServiceStatus() == null) {
            if (customerResidencyType == null || customerResidentTypeDesc.equals(""))
                throw new InvalidServiceIdException(environment.getProperty("customerResidentType.not-null"),
                        ServiceEntity.RESIDENCYTYPE, EntityPoint.TAXPROFILE);
            if (customerResidencyType == null || customerResidencyType.getDescription() == null
                    || !customerResidencyType.getDescription().equals(customerResidentTypeDesc)
                    || !customerResidencyType.getStatus().equals("ACTIVE"))
                throw new InvalidServiceIdException(environment.getProperty("customerResidentType.invalid"),
                        ServiceEntity.RESIDENCYTYPE, EntityPoint.TAXPROFILE);
        } else {
            remoteService.serviceValidationExceptionHadle(customerResidencyType.getServiceStatus(),
                    ServiceEntity.RESIDENCYTYPE, EntityPoint.TAXPROFILE);
        }
    }

    @Override
    public void prodCategoryValidation(String tenantId, Long productCategoryId, String productCategoryDesc) {
        ProductCommonList productCategoryCommonList = (ProductCommonList) remoteService.checkExist(tenantId,
                productCategoryId.toString(), urlCasaProductBcaComnList, ProductCommonList.class);
        if (productCategoryCommonList == null || productCategoryCommonList.getServiceStatus() == null) {
            if (productCategoryCommonList == null
                    || !productCategoryCommonList.getReferenceCode().equals("ProductCategory")
                    || !productCategoryCommonList.getDescription().equalsIgnoreCase(productCategoryDesc)
                    || !productCategoryCommonList.getStatus().equals("ACTIVE")) {
                throw new InvalidServiceIdException(environment.getProperty("productCategoryComnListId.not-match"),
                        ServiceEntity.PRODUCTCATEGORY, EntityPoint.TAXPROFILE);
            }
        } else {
            remoteService.serviceValidationExceptionHadle(productCategoryCommonList.getServiceStatus(),
                    ServiceEntity.PRODUCTCATEGORY, EntityPoint.TAXPROFILE);
        }
    }

    @Override
    public void customerCategoryExistValidation(String tenantId, Long customerCategoryId, String customerCategoryCode,
                                                String customerCategory) {
        CustomerCategoryCommonList customerCategoryCommonList = (CustomerCategoryCommonList) remoteService.checkExist(
                tenantId, customerCategoryId.toString(), urlPersonCommonList, CustomerCategoryCommonList.class);
        if (customerCategoryCommonList == null || customerCategoryCommonList.getServiceStatus() == null) {
            if (customerCategoryCommonList == null
                    || !customerCategoryCommonList.getPcmlsReferenceCode().equals("ORGANIZATIONTYPE")
                    || !customerCategoryCommonList.getCmlsDesc().equalsIgnoreCase(customerCategory)
                    || !customerCategoryCommonList.getPcmlsCode().equalsIgnoreCase(customerCategoryCode)
                    || !customerCategoryCommonList.getPcmlsStatus().equals("ACTIVE"))
                /*
                 * if (!customerCategoryCommonList.getPcmlsCode().equalsIgnoreCase(
                 * customerCategoryCode)) { throw new
                 * InvalidServiceIdException(environment.getProperty(
                 * "customerCategoryCode.invalid"), ServiceEntity.CUSTCATEGORYCODE,
                 * TaxType.TAXPROFILE); }else { throw new
                 * InvalidServiceIdException(environment.getProperty(
                 * "customerCategoryId.invalid"), ServiceEntity.CUSTCATEGORYID,
                 * TaxType.TAXPROFILE); }
                 */
                throw new InvalidServiceIdException(environment.getProperty("common.invalid"),
                        ServiceEntity.CUSTCATEGORYID, EntityPoint.TAXPROFILE);
        } else {
            remoteService.serviceValidationExceptionHadle(customerCategoryCommonList.getServiceStatus(),
                    ServiceEntity.CUSTSUBTYPEIND, EntityPoint.TAXPROFILE);
        }
    }

    @Override
    public void applicableProductValidation(String tenantId, Long applicableProductId, String applicableProductName) {
        CasaSubProduct casaSubProduct = (CasaSubProduct) remoteService.checkExist(tenantId,
                applicableProductId.toString(), urlCasaSubProduct, CasaSubProduct.class);
        if (casaSubProduct == null || casaSubProduct.getServiceStatus() == null) {
            if (casaSubProduct == null || !casaSubProduct.getId().equals(applicableProductId)
                    || !casaSubProduct.getName().equalsIgnoreCase(applicableProductName)
                    || !casaSubProduct.getStatus().equals("ACTIVE"))
                throw new InvalidServiceIdException(environment.getProperty("applicableProductId.not-match"),
                        ServiceEntity.APPPROD, EntityPoint.TAXPROFILE);
        } else {
            remoteService.serviceValidationExceptionHadle(casaSubProduct.getServiceStatus(), ServiceEntity.APPPROD,
                    EntityPoint.TAXPROFILE);
        }
    }

    @Override
    public PerCommonList validatePersonCommonList(String tenantId, String id, String desc, String refCode, ServiceEntity serviceEntity, EntityPoint entityPoint, String fileld) {

        PerCommonList perCommonList = (PerCommonList) getDetailFromService(tenantId, id, urlPersonCommonList, PerCommonList.class);

        if (perCommonList == null || perCommonList.getServiceStatus() == null) {
            if (perCommonList == null) {
                throw new ValidateRecordException(environment.getProperty(COMMON_NOT_AVAILABLE), fileld);

            } else if ((desc != null && !perCommonList.getCmlsDesc().equalsIgnoreCase(desc))
                    || !perCommonList.getPcmlsReferenceCode().equalsIgnoreCase(refCode)) {
                throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), fileld);

            } else if (!CommonStatus.ACTIVE.toString().equalsIgnoreCase(perCommonList.getPcmlsStatus())) {
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), fileld);

            }
        } else {
            serviceValidationExceptionHadle(perCommonList.getServiceStatus(), serviceEntity, entityPoint);
        }
        return perCommonList;
    }

    @Override
    public PerCommonList getPersonCommonListItemById(String tenantId, String id) {

        PerCommonList perCommonList = (PerCommonList) getDetailFromService(tenantId, id, urlPersonCommonList, PerCommonList.class);
        return perCommonList;
    }

    /**
     * Converts string to Timestamp
     *
     * @param strDate the value of date
     * @return The converted value into Timestamp
     * @author Thushan
     */
    @Override
    public Timestamp StringToTimeStamp(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert parsedDate != null;
        return new Timestamp(parsedDate.getTime());
    }

    //Added by SewwandiH for Officer type >>Officer type
    @Override
    public OfficerType getOfficerType(String tenantId, Long officerTypeId) {
        OfficerType officerType = (OfficerType) getDetailFromService(tenantId, officerTypeId.toString(), urlOfficerType, OfficerType.class);
        return officerType;
    }

    @Override
    public OfficerType validateOfficerType(String tenantId, String officerTypeIdd, String officerTypeName) {
        OfficerType officerType = (OfficerType) getDetailFromService(tenantId, officerTypeIdd, urlOfficerType, OfficerType.class);

        if (officerType == null || officerType.getServiceStatus() == null) {
            if (officerType == null || !officerType.getDesgName().equalsIgnoreCase(officerTypeName)) {
                throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "officerTypeId");
            }

        } else {
            serviceValidationExceptionHadle(officerType.getServiceStatus(), ServiceEntity.OFFICER_TYPE, EntityPoint.OFFICER_ELIGIBILITY);
        }
        return officerType;
    }

    @Override
	public Object getCustomerById(String tenantId, String customerId) {
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            Object customerRequestResponseResource = restTemplate.getForObject(commonModuleProperties.getComnCustomer() + "/customer/" + tenantId + "/" + customerId, Object.class, entity);
            return customerRequestResponseResource;
        } catch (RestClientException e) {
            String result = null;
            if (e.getMessage().contains("503")) {
                result = serviceStatus + ServiceStatus.NOT_AVAILABLE.toString() + "\"}";
            } else if (e.getMessage().contains("500")) {
                result = serviceStatus + ServiceStatus.EXCEPTION.toString() + "\"}";
            } else if (e.getMessage().contains("204")) {
                result = serviceStatus + ServiceStatus.NO_CONTENT.toString() + "\"}";
            } else if (e.getMessage().contains("404")) {
                result = serviceStatus + ServiceStatus.NOT_FOUND.toString() + "\"}";
            } else if (e.getMessage().contains("401")) {
                result = serviceStatus + ServiceStatus.UNAUTHORIZED.toString() + "\"}";
            } else if (e.getMessage().contains("403")) {
                result = serviceStatus + ServiceStatus.FORBIDDEN.toString() + "\"}";
            } else if (e.getMessage().contains("400")) {
                result = serviceStatus + ServiceStatus.BAD_REQUEST.toString() + "\"}";
            } else if (e.getMessage().contains("504")) {
                result = serviceStatus + ServiceStatus.GATEWAY_TIME_OUT.toString() + "\"}";
            } else {
                result = serviceStatus + ServiceStatus.OTHER.toString() + "\"}";
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(result, Object.class);
            } catch (IOException e1) {
                return null;
            }
        }
    }

    @Override
    public Object getCollateralById(String tenantId, String collateralsId) {
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            Object collateralRequestResponseResource = restTemplate.getForObject(commonModuleProperties.getComnCustomer() + "/customer/" + tenantId + "/" + collateralsId, Object.class, entity);
            return collateralRequestResponseResource;
        } catch (RestClientException e) {
            String result = null;
            if (e.getMessage().contains("503")) {
                result = serviceStatus + ServiceStatus.NOT_AVAILABLE.toString() + "\"}";
            } else if (e.getMessage().contains("500")) {
                result = serviceStatus + ServiceStatus.EXCEPTION.toString() + "\"}";
            } else if (e.getMessage().contains("204")) {
                result = serviceStatus + ServiceStatus.NO_CONTENT.toString() + "\"}";
            } else if (e.getMessage().contains("404")) {
                result = serviceStatus + ServiceStatus.NOT_FOUND.toString() + "\"}";
            } else if (e.getMessage().contains("401")) {
                result = serviceStatus + ServiceStatus.UNAUTHORIZED.toString() + "\"}";
            } else if (e.getMessage().contains("403")) {
                result = serviceStatus + ServiceStatus.FORBIDDEN.toString() + "\"}";
            } else if (e.getMessage().contains("400")) {
                result = serviceStatus + ServiceStatus.BAD_REQUEST.toString() + "\"}";
            } else if (e.getMessage().contains("504")) {
                result = serviceStatus + ServiceStatus.GATEWAY_TIME_OUT.toString() + "\"}";
            } else {
                result = serviceStatus + ServiceStatus.OTHER.toString() + "\"}";
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(result, Object.class);
            } catch (IOException e1) {
                return null;
            }
        }
    }

    @Override
    public CustomerRequestResponseResource getCustomerByLeadId(String tenantId, String customerId, String leadId) {

        LeadInfoRequestResponseResource leadInfoRequestResponseResource = remoteService.getLeadInfoById(tenantId,leadId,commonModuleProperties.getLendingOrigination());

        for (CustomerRequestResponseResource customerRequestResponseResource : leadInfoRequestResponseResource.getCustomers()) {
            if (customerRequestResponseResource.getCustomerId().equals(customerId)) {
                return customerRequestResponseResource;
            }
        }
        return null;
    }
    
    @Override
    public LeadInfoRequestResponseResource getLeadInfoById(String tenantId, String leadId) {

        LeadInfoRequestResponseResource leadInfoRequestResponseResource = remoteService.getLeadInfoById(tenantId,leadId,commonModuleProperties.getLendingOrigination());
        return leadInfoRequestResponseResource;
    }
    
    
    @Override
    public CustomerDetResource getCustomerDet(String tenantId, String customerId) {
        String url = commonModuleProperties.getComnCustomer() + "/customer/" + tenantId + "/" + customerId;
        CustomerDetResource customerDetResource = (CustomerDetResource) getByService(url, CustomerDetResource.class);
        return customerDetResource;
    }
    
    @Override
    public CustomerPendDetResource getPendingCustomerDet(String tenantId, String customerId) {
        String url = commonModuleProperties.getComnCustomer() + "/pending-customer/" + tenantId + "/" + customerId;
        CustomerPendDetResource customerDetResource = (CustomerPendDetResource) getByService(url, CustomerPendDetResource.class);
        return customerDetResource;
    }
    
    @Override
    public TaxDeclarationCustomerTypeResponse getCustomerTaxDeclarationDet(String tenantId, String customerId , String controllerUrl) {
        String url = commonModuleProperties.getComnCustomer() + "/"+controllerUrl+"/" + tenantId + "/" + customerId;
        TaxDeclarationCustomerTypeResponse customerDetResource = (TaxDeclarationCustomerTypeResponse) getByService(url, TaxDeclarationCustomerTypeResponse.class);
        return customerDetResource;
    }

	@Override
	public Long initiateCreditAppraisalWorkFlow(CreateCreditAppraisalProcessInstanceRequest processInstanceRequest,
			String tenantId) {
		
		processInstanceRequest.setCreditAppraisalApproveUrl(creditAppraisalLeadStatusUpdateUrl+tenantId+"/lead/"+processInstanceRequest.getCreditAppraisalLeadNo()+"/status-update");
		
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(usernameBPM, passwordBPM);
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplateSetRequestFactory();
        Long processId = null;
        ObjectMapper mapper = new ObjectMapper();	
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        try {
        	 HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(processInstanceRequest), headers);
            //processId = restTemplate.exchange(creditAppraisalStartProcessTempBpm.replace("tenantId", tenantId), HttpMethod.POST, entity, Long.class).getBody();
        } catch (RestClientException | JsonProcessingException e) {
            if (e.getMessage().contains("503")) {
                throw new WorkFlowException(environment.getProperty("common.not-available"), WorkflowType.CREDIT_APPRAISAL);
            } else if (e.getMessage().contains("500")) {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), WorkflowType.CREDIT_APPRAISAL);
            } else if (e.getMessage().contains("404")) {
                throw new WorkFlowException(environment.getProperty("common.not-found"), WorkflowType.CREDIT_APPRAISAL);
            } else if (e.getMessage().contains("400")) {
                throw new WorkFlowException(environment.getProperty("common.bad-request"), WorkflowType.CREDIT_APPRAISAL);
            } else {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), WorkflowType.CREDIT_APPRAISAL);
            }
        }
        
		return processId;
	}

	@Override
	public String  workFlowTaskStatesUpdate(BpmTaskRequest bpmTaskRequest, String tenantId,String userId,String taskStates) {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(usernameBPM, passwordBPM);
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplateSetRequestFactory();   
        
        String url=creditAppraisalStatesTempBpm.replace("containerId", bpmTaskRequest.getContainerId());
        url=url.replace("taskInstanceId", bpmTaskRequest.getTaskInstanceId());
        url=url.replace("statesId", taskStates.trim());
		
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        
		HttpEntity<String> entity = new HttpEntity<>(headers);
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		
		return restTemplate.exchange(url+userId,HttpMethod.PUT,entity,
				String.class).getBody();
	}

	@Override
	public String creditAppraisalWorkFlowApproveTaskComplete(
			CreditAppraisalApproveTaskInstanceRequest approveTaskInstanceRequest, String tenantId,String userId,String taskStates) {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(usernameBPM, passwordBPM);
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplateSetRequestFactory();        
        
        
       String url =creditAppraisalCompleteTempBpm.replace("containerId", approveTaskInstanceRequest.getContainerId());
       url=url.replace("taskInstanceId", approveTaskInstanceRequest.getTaskInstanceId());
       url=url.replace("statesId", taskStates.trim());
        
        
		ObjectMapper mapper = new ObjectMapper();	
		String response;
		try {
			HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(approveTaskInstanceRequest.getBpmRequest()), headers);
			response=restTemplate.exchange(url+userId, HttpMethod.PUT, entity, String.class).getBody();
		} catch (JsonProcessingException e) {
			 if (e.getMessage().contains("503")) {
	                throw new WorkFlowException(environment.getProperty("common.not-available"), WorkflowType.CREDIT_APPRAISAL);
	            } else if (e.getMessage().contains("500")) {
	                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), WorkflowType.CREDIT_APPRAISAL);
	            } else if (e.getMessage().contains("404")) {
	                throw new WorkFlowException(environment.getProperty("common.not-found"), WorkflowType.CREDIT_APPRAISAL);
	            } else if (e.getMessage().contains("400")) {
	                throw new WorkFlowException(environment.getProperty("common.bad-request"), WorkflowType.CREDIT_APPRAISAL);
	            } else {
	                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), WorkflowType.CREDIT_APPRAISAL);
	            }
		}
		return response;
		
	}

	@Override
	public String creditAppraisalWorkFlowUpdateTaskComplete(CreditAppraisalUpdateTaskInstanceBpmRequest updateTaskInstanceRequest,
			String tenantId,BpmTaskRequest bpmTaskRequest,String userId,String taskStates) {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(usernameBPM, passwordBPM);
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplateSetRequestFactory();  
        
        String url =creditAppraisalStatesTempBpm.replace("containerId", bpmTaskRequest.getContainerId());
        url=url.replace("taskInstanceId", bpmTaskRequest.getTaskInstanceId());
        url=url.replace("statesId", taskStates.trim());
        
		ObjectMapper mapper = new ObjectMapper();	
		String response;

		try {
			HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(updateTaskInstanceRequest), headers);
			response=restTemplate.exchange(url+userId, HttpMethod.PUT, entity, String.class).getBody();
		} catch (JsonProcessingException e) {
			 if (e.getMessage().contains("503")) {
	                throw new WorkFlowException(environment.getProperty("common.not-available"), WorkflowType.CREDIT_APPRAISAL);
	            } else if (e.getMessage().contains("500")) {
	                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), WorkflowType.CREDIT_APPRAISAL);
	            } else if (e.getMessage().contains("404")) {
	                throw new WorkFlowException(environment.getProperty("common.not-found"), WorkflowType.CREDIT_APPRAISAL);
	            } else if (e.getMessage().contains("400")) {
	                throw new WorkFlowException(environment.getProperty("common.bad-request"), WorkflowType.CREDIT_APPRAISAL);
	            } else {
	                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), WorkflowType.CREDIT_APPRAISAL);
	            }
		}
		return response;
	}

	@Override
	public CreditAppraisalResponse getAllUpdateTasks(Pageable pageable, String tenantId, CreditAppraisalTaskListRequest taskListRequest) {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("username", LogginAuthentcation.getInstance().getUserName());
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplateSetRequestFactory();  
        
        StringBuilder urlString = new StringBuilder();
		urlString.append(comnPamSupport);
//		urlString.append("http://localhost:8080/comn-pam-support");
		urlString.append("/pam-credit-appraisal/");
		urlString.append(tenantId);
		urlString.append("/tasks");
//		urlString.append("?page="+pageable.getPageNumber()+"&size="+pageable.getPageSize());
		ObjectMapper mapper = new ObjectMapper();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		CreditAppraisalResponse response;
		try {
			HttpEntity<String> requestEntity = new HttpEntity<>(mapper.writeValueAsString(taskListRequest), headers);
			ResponseEntity<CreditAppraisalResponse> responseEntity = restTemplate.exchange(
					urlString.toString(), HttpMethod.POST, requestEntity,
					CreditAppraisalResponse.class);
			if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
				 throw new WorkFlowException("Credit appraisal workflow fail.", WorkflowType.CREDIT_APPRAISAL);
			}
			response = responseEntity.getBody();
		} catch (JsonProcessingException e) {
			if (e.getMessage().contains("503")) {
                throw new WorkFlowException(environment.getProperty("common.not-available"), WorkflowType.CREDIT_APPRAISAL);
            } else if (e.getMessage().contains("500")) {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), WorkflowType.CREDIT_APPRAISAL);
            } else if (e.getMessage().contains("404")) {
                throw new WorkFlowException(environment.getProperty("common.not-found"), WorkflowType.CREDIT_APPRAISAL);
            } else if (e.getMessage().contains("400")) {
                throw new WorkFlowException(environment.getProperty("common.bad-request"), WorkflowType.CREDIT_APPRAISAL);
            } else {
                throw new WorkFlowException(environment.getProperty("common.internal-server-error"), WorkflowType.CREDIT_APPRAISAL);
            }
		
		}
		return response;
	}

	@Override
	public void getApprovalDetailByLeadId(String tenantId, String leadId) {
		// TODO Auto-generated method stub
		
	}

	

}

