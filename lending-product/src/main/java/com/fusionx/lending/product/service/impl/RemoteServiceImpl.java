package com.fusionx.lending.product.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusionx.lending.product.core.DefaultRequestHeaders;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.ServiceStatus;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.resources.*;
import com.fusionx.lending.product.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * API Service related to Remote API Calls.
 *
 * @author Thushan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author          Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        08-10-2021         -               -          Thushan          Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class RemoteServiceImpl implements RemoteService {

	@Autowired
	private Environment environment;

    private final RestTemplate restTemplate;

    @Autowired
    public RemoteServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String serviceStatus = "{\"serviceStatus\" : \"";

    /**
     * Check is exist entity in another service
     * Support only HTTP get method
     *
     * @param tenantId   the tenant id
     * @param id         the related id
     * @param serviceUrl the relevant service URL
     * @return Object
     */
    @Override
    public Boolean checkIsExist(String tenantId, String id, String serviceUrl) {
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            HttpStatus httpStatus = restTemplate.exchange(serviceUrl + tenantId + "/" + id, HttpMethod.GET, entity, Object.class).getStatusCode();
            if (httpStatus.value() == 200)
                return true;
        } catch (RestClientException e) {
            String result = null;
            if (e.getMessage().contains("503")) {
                return false;// Not Available
            } else if (e.getMessage().contains("401")) {
                return false;// unauthorized
            } else if (e.getMessage().contains("500")) {
                return false;//exception
            } else if (e.getMessage().contains("403")) {
                return false;//for bidden
            } else if (e.getMessage().contains("404")) {
                return false;//not found
            } else if (e.getMessage().contains("204")) {
                return false;//bad request
            } else if (e.getMessage().contains("400")) {
                return false;//bad request
            } else if (e.getMessage().contains("504")) {
                return false;//gateway time out
            } else {
                return false;//other
            }
        }
        return false;
    }

    /**
     * Check is exist entity in another service
     * Support only HTTP get method
     *
     * @param tenantId the tenant id
     * @param id       the related id
     * @return Object
     * @Param serviceUrl    the relevant service URL
     */
    @Override
    public Boolean checkIsExistWithStatus(String tenantId, String id, String serviceUrl, String status) {
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            HttpStatus httpStatus = restTemplate.exchange(serviceUrl + tenantId + "/" + id + "/" + status, HttpMethod.GET, entity, Object.class).getStatusCode();
            if (httpStatus.value() == 200)
                return true;
        } catch (RestClientException e) {
            String result = null;
            if (e.getMessage().contains("503")) {
                return false;// Not Available
            } else if (e.getMessage().contains("401")) {
                return false;// unauthorized
            } else if (e.getMessage().contains("500")) {
                return false;//exception
            } else if (e.getMessage().contains("403")) {
                return false;//for bidden
            } else if (e.getMessage().contains("404")) {
                return false;//not found
            } else if (e.getMessage().contains("204")) {
                return false;//bad request
            } else if (e.getMessage().contains("400")) {
                return false;//bad request
            } else if (e.getMessage().contains("504")) {
                return false;//gateway time out
            } else {
                return false;//other
            }
        }
        return false;
    }


    /**
     * retrieve product using Product Id
     *
     * @param tenantId   the tenant id
     * @param productId  the product id
     * @param serviceUrl the lending-organization service url
     * @return ProductRequestResponseResource
     */
    @Override
    public ProductRequestResponseResource getProductById(String tenantId, String productId, String serviceUrl) {
        try {
            ProductRequestResponseResource productRequestResponseResource;
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            productRequestResponseResource = restTemplate.exchange(serviceUrl + "/core-product/" + tenantId + "/" + productId, HttpMethod.GET, entity, ProductRequestResponseResource.class).getBody();
            return productRequestResponseResource;
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
                return mapper.readValue(result, ProductRequestResponseResource.class);
            } catch (IOException e1) {
                return null;
            }
        }
    }

    @Override
    public SubProductRequestResponseResource getSubProductIdById(String tenantId, String subProductId, String serviceUrl) {
        try {
            SubProductRequestResponseResource subProductRequestResponseResource;
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            subProductRequestResponseResource = restTemplate.exchange(serviceUrl + "/sub-product/" + tenantId + "/" + subProductId, HttpMethod.GET, entity, SubProductRequestResponseResource.class).getBody();
            return subProductRequestResponseResource;
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
                return mapper.readValue(result, SubProductRequestResponseResource.class);
            } catch (IOException e1) {
                return null;
            }
        }
    }


    /**
     * retrieve bank branch using Id
     *
     * @param tenantId   the tenant id
     * @param branchId   the bank id
     * @param serviceUrl the comon-comon service URL
     * @return BankBranchRequestResponseResource
     */
    @Override
    public BankBranchRequestResponseResource getBranchById(String tenantId, String branchId, String serviceUrl) {
        try {
            BankBranchRequestResponseResource bankBranchRequestResponseResource;
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            bankBranchRequestResponseResource = restTemplate.getForObject(serviceUrl + "/comn-bank-branch/" + tenantId + "/" + branchId, BankBranchRequestResponseResource.class, entity);
            return bankBranchRequestResponseResource;
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
                return mapper.readValue(result, BankBranchRequestResponseResource.class);
            } catch (IOException e1) {
                return null;
            }
        }
    }

    /**
     * retrieve bank account by Id
     *
     * @param tenantId   the tenant id
     * @param accountId  the account id
     * @param serviceUrl the comon-customer service URL
     * @return Object
     */
    @Override
    public Object getAccountById(String tenantId, String accountId, String serviceUrl) {
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            Object bankAccountRequestResponseResource = restTemplate.getForObject(serviceUrl + "/account/" + tenantId + "/" + accountId, Object.class, entity);
            return bankAccountRequestResponseResource;
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

    /**
     * retrieve bank  using Id
     *
     * @param tenantId   the tenant id
     * @param bankId     the bank id
     * @param serviceUrl the comon-comon service URL
     * @return BankRequestResponseResource
     */
    @Override
    public BankRequestResponseResource getBankById(String tenantId, String bankId, String serviceUrl) {
        try {
            BankRequestResponseResource bankRequestResponseResource;
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            bankRequestResponseResource = restTemplate.exchange(serviceUrl + "/comn-bank/" + tenantId + "/" + bankId, HttpMethod.GET, entity, BankRequestResponseResource.class).getBody();
            return bankRequestResponseResource;
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
                return mapper.readValue(result, BankRequestResponseResource.class);
            } catch (IOException e1) {
                return null;
            }
        }
    }

    /**
     *
     * @param tenantId the tenant id
     * @param leadId the lead id
     * @param serviceUrl the lending-origination service url
     * @return LeadInfoRequestResponseResource
     */
    @Override
    public LeadInfoRequestResponseResource getLeadInfoById(String tenantId, String leadId, String serviceUrl) {
        try {
            LeadInfoRequestResponseResource leadInfoRequestResponseResource;
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            leadInfoRequestResponseResource = restTemplate.getForObject(serviceUrl + "/lead-info/" + tenantId + "/" + leadId, LeadInfoRequestResponseResource.class, entity);
            return leadInfoRequestResponseResource;
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
                return mapper.readValue(result, LeadInfoRequestResponseResource.class);
            } catch (IOException e1) {
                return null;
            }
        }
    }

    /**
     *
     * @param tenantId the tenant id
     * @param serviceUrl the casa service URL
     * @param savingAccountAddResource the automated-savings-account object
     * @return savingAccountResponseResource
     */
    @Override
    public SavingAccountResponseResource addSavingAccount(String tenantId, String serviceUrl, SavingAccountAddResource savingAccountAddResource) {
        try {
            SavingAccountResponseResource savingAccountResponse;
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("username", LogginAuthentcation.getInstance().getUserName());
            HttpEntity<SavingAccountAddResource> entity = new HttpEntity<>(savingAccountAddResource, headers);
            savingAccountResponse = restTemplate.exchange(serviceUrl + "/automated-process/casa-account/" + tenantId, HttpMethod.POST, entity, SavingAccountResponseResource.class).getBody();
            return savingAccountResponse;
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
            } else if (e.getMessage().contains("422")) {
                result = serviceStatus + ServiceStatus.UNPROCESSABLE_ENTITY.toString() + "\"}";
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
                return mapper.readValue(result, SavingAccountResponseResource.class);
            } catch (IOException e1) {
                return null;
            }
        }
    }
    
    @Override
	public Object checkExist(String tenantId, String id, String serviceUrl, Class<?> classObject) {
		try {
			  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		      HttpEntity <String> entity = new HttpEntity<>(headers);
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
	public void serviceValidationExceptionHadle(ServiceStatus serviceStatus, ServiceEntity serviceEntity, EntityPoint entityPoint) {
		switch(serviceStatus) { 
	        case NOT_AVAILABLE: 
	        	throw new InvalidServiceIdException(environment.getProperty("common.not-available"), serviceEntity,entityPoint);
	        case NOT_FOUND: 
	        	throw new InvalidServiceIdException(environment.getProperty("common.not-found"), serviceEntity,entityPoint);
	        case EXCEPTION: 
	        	throw new InvalidServiceIdException(environment.getProperty("common.internal-server-error"), serviceEntity,entityPoint);
	        case BAD_REQUEST: 
	        	throw new InvalidServiceIdException(environment.getProperty("common.bad-request"), serviceEntity,entityPoint);
	        default:
		}
	}

    /**
     *
     * @param tenantId the tenant id
     * @param serviceUrl  the lending-organization service url
     * @param leadStatus the leadStatus
     * @return List<LeadInfoRequestResponseResource>
     */
    @Override
    public List<LeadInfoRequestResponseResource> getAllAppraisalByLeadsStatus(String tenantId, String serviceUrl, String leadStatus) {
        try {
            LeadInfoRequestResponseResource[] leadInfoResponse;
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            leadInfoResponse = restTemplate.getForObject(serviceUrl + "/lead-info/" + tenantId + "/lead-status/" + leadStatus, LeadInfoRequestResponseResource[].class, entity);
            return Arrays.asList(leadInfoResponse);
        } catch (RestClientException e) {
            String result = null;
            if (e.getMessage().contains("503")) {
                result = serviceStatus + ServiceStatus.NOT_AVAILABLE + "\"}";
            } else if (e.getMessage().contains("500")) {
                result = serviceStatus + ServiceStatus.EXCEPTION + "\"}";
            } else if (e.getMessage().contains("204")) {
                result = serviceStatus + ServiceStatus.NO_CONTENT + "\"}";
            } else if (e.getMessage().contains("404")) {
                result = serviceStatus + ServiceStatus.NOT_FOUND + "\"}";
            } else if (e.getMessage().contains("401")) {
                result = serviceStatus + ServiceStatus.UNAUTHORIZED + "\"}";
            } else if (e.getMessage().contains("403")) {
                result = serviceStatus + ServiceStatus.FORBIDDEN + "\"}";
            } else if (e.getMessage().contains("400")) {
                result = serviceStatus + ServiceStatus.BAD_REQUEST + "\"}";
            } else if (e.getMessage().contains("504")) {
                result = serviceStatus + ServiceStatus.GATEWAY_TIME_OUT + "\"}";
            } else {
                result = serviceStatus + ServiceStatus.OTHER + "\"}";
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                return Collections.unmodifiableList(mapper.readValue(result, List.class));
            } catch (IOException e1) {
                return null;
            }
        }
    }

    /**
     *
     * @param tenantId the tenant id
     * @param serviceUrl the lending-origination service url
     * @param leadId the lead id
     * @return guarantorRequestResponseResource
     */
    @Override
    public GuarantorRequestResponseResource getGuarantorByLeadId(String tenantId, String serviceUrl, String leadId) {
        try {
            GuarantorRequestResponseResource GuarantorRequestResponseResource;
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            GuarantorRequestResponseResource = restTemplate.getForObject(serviceUrl + "/guarantor/" + tenantId + "/lead-id/" + leadId, GuarantorRequestResponseResource.class, entity);
            return GuarantorRequestResponseResource;
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
                return mapper.readValue(result, GuarantorRequestResponseResource.class);
            } catch (IOException e1) {
                return null;
            }
        }
    }

    @Override
    public TcHeaderRequestResponseResource getTcHeaderById(String tenantId, String tcId, String serviceUrl) {
        try {
            TcHeaderRequestResponseResource tcHeaderRequestResponseResource;
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            tcHeaderRequestResponseResource = restTemplate.getForObject(serviceUrl + "/" + tenantId + "/" + tcId, TcHeaderRequestResponseResource.class, entity);
            return tcHeaderRequestResponseResource;
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
                return mapper.readValue(result, TcHeaderRequestResponseResource.class);
            } catch (IOException e1) {
                return null;
            }
        }
    }

}
