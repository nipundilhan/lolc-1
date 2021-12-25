package com.fusionx.lending.origination.service.impl;

/**
 * Remote Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-07-2020   FX-3963       FX-4080    SahanAm     Created
 *    
 ********************************************************************************************************
 */

import java.io.IOException;
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
import java.util.stream.Collectors;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusionx.lending.origination.core.DefaultRequestHeaders;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.enums.CommonListReferenceCode;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.ServiceStatus;
import com.fusionx.lending.origination.enums.WorkflowType;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.DetailValidateException;
import com.fusionx.lending.origination.exception.PersonServiceException;
import com.fusionx.lending.origination.exception.WorkFlowException;
import com.fusionx.lending.origination.resource.AssetSubTypePartsResponseList;
import com.fusionx.lending.origination.resource.AssetSubTypePartsResponseResource;
import com.fusionx.lending.origination.resource.AssetTypePledgeTypeMappingResource;
import com.fusionx.lending.origination.resource.AssetTypePledgeTypeMappingResponce;
import com.fusionx.lending.origination.resource.AssetsDetailsRequestResource;
import com.fusionx.lending.origination.resource.ColInetragationAssetsDetailsRequestResource;
import com.fusionx.lending.origination.resource.ColInetragationRequestResponceResource;
import com.fusionx.lending.origination.resource.ContactValidateResource;
import com.fusionx.lending.origination.resource.ExistsDocumentResponseResource;
import com.fusionx.lending.origination.resource.ExistsPersonResponseResource;
import com.fusionx.lending.origination.resource.ExternalCribRulesResource;
import com.fusionx.lending.origination.resource.IdentificationNumberCheckRequestResource;
import com.fusionx.lending.origination.resource.IdentificationValidateResource;
import com.fusionx.lending.origination.resource.InsuranceDetailsAddResource;
import com.fusionx.lending.origination.resource.InsuranceDetailsRequestResource;
import com.fusionx.lending.origination.resource.InsuranceDetailsResponseResource;
import com.fusionx.lending.origination.resource.LastValuationDateResponseResource;
import com.fusionx.lending.origination.resource.PerCommonList;
import com.fusionx.lending.origination.resource.ValuationAndInsuranceDetListResponseResource;
import com.fusionx.lending.origination.resource.ValuationDetailsListAddResource;
import com.fusionx.lending.origination.resource.ValuationDetailsRequestResource;
import com.fusionx.lending.origination.resource.ValuationDetailsUpdateResource;
import com.fusionx.lending.origination.service.RemoteService;
@Component
@Transactional(rollbackFor=Exception.class)
public class RemoteServiceImpl implements RemoteService{

	@Autowired
	private Environment environment;
	
	@Autowired
	private RestTemplate restTemplate;	
	private String serviceStatus= "{\"serviceStatus\" : \"";

    @Value("${pam.username}") 
    private String usernameBPM;

    @Value("${pam.password}")
    private String passwordBPM;
    
    @Value("${pam-cert-location}")
    private String pamCertLocation;
    
//    @Value("${mbpr-ext-crib-container1}")
//	private String urlExtCribRuleContainer;
    
    @Value("${mbpr-ext-crib-container}")
	private String urlExtCribRuleContainer;
  
    @Value("${document-upload}")
    private String urlDocumentUpload;
    
    @Value("${col-integration}")
    private String collateralIntergrationUrl;  
	
    @Value("${comn-person}")
    private String urlPerson;
    
    @Value("${comn-person-common-list}")
    private String urlPersonCommonList;
    
    @Value("${col-valuation-details}")
    private String urlColValuationDetails;
    
    @Value("${col-insurance-details}")
    private String urlColInsuranceDetails;    
    
    
	/**
    * get id and description from service
    * @param Url
    * @param Description
    * @param tenantId
	* @return Object
    */
	@Override
	public Object checkIsExist(String tenantId, String id, String serviceUrl, Class<?> classObject) {
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
			}else if(e.getMessage().contains("504")) {
				result=serviceStatus+ ServiceStatus.GATEWAY_TIME_OUT.toString()+"\"}";
			}else {
				result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
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
		public List<PerCommonList> getPerCommonListByType(String tenantId,  String type ,Class<?> class1) {
			try {
				String url = urlPersonCommonList + tenantId + "/type/" + type ;
				return getAllOrganizationTypeList(url );
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
		
		private List<PerCommonList> getAllOrganizationTypeList(String url) {
			  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		      HttpEntity <String> entity = new HttpEntity<>(headers);
		      ResponseEntity<PerCommonList[]> responseVal = restTemplate.exchange(url, HttpMethod.GET, entity, PerCommonList[].class);
		      if(responseVal.getBody()!=null)
		    	  return Arrays.stream(responseVal.getBody()).collect(Collectors.toList());
		      else return null;
			}
		

	/**
	    * get id and description from service
	    * @param Url
	    * @param Description
	    * @param tenantId
		* @return Object
	    */
		@Override
		public Object checkIsExistAssetDetails( String id, String serviceUrl, Class<?> classObject) {
			try {
			  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		      HttpEntity <String> entity = new HttpEntity<>(headers);
		      return restTemplate.exchange(serviceUrl+"/"+id, HttpMethod.GET, entity, classObject).getBody();
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
				}else if(e.getMessage().contains("504")) {
					result=serviceStatus+ ServiceStatus.GATEWAY_TIME_OUT.toString()+"\"}";
				}else {
					result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
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
	public Long stringToLong(String value){
		return Long.parseLong(value);
	}
	@Override
	public Date formatDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	@Override
	public Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}



	@Override
	public void futureDateValidation(String date, ServiceEntity serviceEntity, ServicePoint servicePoint) {
		Date formatDate=formatDate(date);
		if( formatDate!=null && formatDate.after(new Date())) {
			throw new DetailValidateException(environment.getProperty("date.future"), serviceEntity, servicePoint);
		}
	}
	

	
	@Override
	public Object validateRelationRelationshipType(String tenantId, String relationCode, CommonListReferenceCode relationshiptype, 
			ServicePoint externalCrib, Class<?> classObject) {
		try {
			  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		      HttpEntity <String> entity = new HttpEntity<>(headers);   
		      String serviceUrl = urlPersonCommonList+tenantId+"/reference-code/"+relationshiptype.toString()+"/code/"+relationCode;
		      return restTemplate.exchange(serviceUrl, HttpMethod.GET, entity, classObject).getBody();
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
				}else if(e.getMessage().contains("504")) {
					result=serviceStatus+ ServiceStatus.GATEWAY_TIME_OUT.toString()+"\"}";
				}else {
					result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
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
    public ExternalCribRulesResource invokeExternalCribRule(WorkflowType workflowType, String domainPath, String domainName, String tenantId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(usernameBPM, passwordBPM);
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplateSetRequestFactory();
        String json="{\"lookup\": \"kie-session\", \"commands\": [{\"insert\": {\"object\": {\""+domainPath+"\": {\"referenceCode\":\""+workflowType+"\"}},\"out-identifier\":\""+domainName+"\"}},{\"fire-all-rules\": {}}]}";
        HttpEntity <String> entity = new HttpEntity<>(json, headers);
        ExternalCribRulesResource extlCribRulesResource=new ExternalCribRulesResource();
        try {
            String responseString=restTemplate.exchange(urlExtCribRuleContainer.replace("tenantId", tenantId), HttpMethod.POST, entity, String.class).getBody();
            JSONObject responseObj = new JSONObject(responseString);
            JSONObject responseResult =responseObj.getJSONObject("result");
            JSONObject responseExecutionResults =responseResult.getJSONObject("execution-results");
            JSONArray responseResults = responseExecutionResults.getJSONArray("results");
            JSONObject responseResults0 = responseResults.getJSONObject(0);
            JSONObject value = responseResults0.getJSONObject("value");
            JSONObject responseTransactionApproval =value.getJSONObject(domainPath);
           
            extlCribRulesResource.setConversionRequired(responseTransactionApproval.getString("convertionRequired"));   
            extlCribRulesResource.setCribReportRequired(responseTransactionApproval.getString("cribRequired"));
           
           
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
        return extlCribRulesResource;
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
	public Boolean checkIsExistPartsForAssetSubType(String tenantId, String id, String serviceUrl) {
		try {
			  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		      HttpEntity <String> entity = new HttpEntity<>(headers);
		      HttpStatus httpStatus = restTemplate.exchange(serviceUrl+tenantId+"/assetSubType/"+id, HttpMethod.GET, entity, String.class).getStatusCode();
		      if(HttpStatus.OK.equals(httpStatus))
		    	  return Boolean.TRUE;
		      else
		    	  return Boolean.FALSE;
		} catch (RestClientException e) {
			 return Boolean.FALSE;
		}
	}
	
	
	@Override
	public AssetTypePledgeTypeMappingResponce checkIsExistAssetTypePledgeTypeMap(String tenantId, Long id, String serviceUrl, Class<?> classObject) {
		
		AssetTypePledgeTypeMappingResponce responseList = null;
		try {
		  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	  	HttpEntity<List<AssetTypePledgeTypeMappingResource>> entity = new HttpEntity<List<AssetTypePledgeTypeMappingResource>>(headers);
	  	AssetTypePledgeTypeMappingResource[] responseArray =restTemplate.exchange(serviceUrl+tenantId+"/assetType/"+id, HttpMethod.GET, entity, AssetTypePledgeTypeMappingResource[].class).getBody();
		if(responseArray!=null) {
			List<AssetTypePledgeTypeMappingResource> response = Arrays.asList(responseArray);
			responseList=new AssetTypePledgeTypeMappingResponce();
			responseList.setAssetTypePledgeTypeMappingResource(response);
		}

		return responseList;
	      //return restTemplate.exchange(serviceUrl+tenantId+"/assetType/"+id, HttpMethod.GET, entity, classObject).getBody();
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
			}else if(e.getMessage().contains("504")) {
				result=serviceStatus+ ServiceStatus.GATEWAY_TIME_OUT.toString()+"\"}";
			}else {
				result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
			}
			ObjectMapper mapper = new ObjectMapper();
			try {
				return (AssetTypePledgeTypeMappingResponce) mapper.readValue(result, classObject);
			} catch (IOException e1) {
				return null;
			}
		}
	}	


	/**
	    * get id and description from service
	    * @param Url
	    * @param Description
	    * @param tenantId
		* @return Object
	    */

//	@Override
//	public AssetTypePledgeTypeMappingResponce checkIsExistAssetTypePledgeTypeMap(String tenantId, Long id, String serviceUrl, Class<?> classObject) {
//		
//		AssetTypePledgeTypeMappingResponce responseList = null;
//		try {
//		  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
//	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//
//	  	HttpEntity<List<AssetTypePledgeTypeMappingResource>> entity = new HttpEntity<List<AssetTypePledgeTypeMappingResource>>(headers);
//	  	AssetTypePledgeTypeMappingResource[] responseArray =restTemplate.exchange(serviceUrl+tenantId+"/assetType/"+id, HttpMethod.GET, entity, AssetTypePledgeTypeMappingResource[].class).getBody();
//		if(responseArray!=null) {
//			List<AssetTypePledgeTypeMappingResource> response = Arrays.asList(responseArray);
//			responseList=new AssetTypePledgeTypeMappingResponce();
//			responseList.setAssetTypePledgeTypeMappingResource(response);
//		}
//
//		return responseList;
//	      //return restTemplate.exchange(serviceUrl+tenantId+"/assetType/"+id, HttpMethod.GET, entity, classObject).getBody();
//		} catch (RestClientException e) {
//			String result=null;
//			if(e.getMessage().contains("503")) {
//				result=serviceStatus +ServiceStatus.NOT_AVAILABLE.toString()+"\"}";
//			}else if(e.getMessage().contains("500")) {
//				result=serviceStatus+ ServiceStatus.EXCEPTION.toString()+"\"}";
//			}else if(e.getMessage().contains("404")) {
//				result=serviceStatus+ ServiceStatus.NOT_FOUND.toString()+"\"}";
//			}else if(e.getMessage().contains("400")) {
//				result=serviceStatus+ ServiceStatus.BAD_REQUEST.toString()+"\"}";
//			}else if(e.getMessage().contains("504")) {
//				result=serviceStatus+ ServiceStatus.GATEWAY_TIME_OUT.toString()+"\"}";
//			}else {
//				result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
//			}
//			ObjectMapper mapper = new ObjectMapper();
//			try {
//				return (AssetTypePledgeTypeMappingResponce) mapper.readValue(result, classObject);
//			} catch (IOException e1) {
//				return null;
//			}
//		}
//	}	
	
		@Override             
		public Object checkIsExistPartsByAssetSubType(String tenantId, String id, String serviceUrl, Class<?> classObject) {
			try {
      
			  AssetSubTypePartsResponseList responseList = null;
			  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			  	HttpEntity<List<AssetSubTypePartsResponseResource>> entity = new HttpEntity<List<AssetSubTypePartsResponseResource>>(headers);
			  	AssetSubTypePartsResponseResource[] responseArray =restTemplate.exchange(serviceUrl+tenantId+"/assetSubType/"+id, HttpMethod.GET, entity, AssetSubTypePartsResponseResource[].class).getBody();
				if(responseArray!=null) {
					List<AssetSubTypePartsResponseResource> response = Arrays.asList(responseArray);
					responseList=new AssetSubTypePartsResponseList();
					responseList.setAssetSubTypePartsResponseResource(response);
				}
		    return responseList;
			
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
				}else if(e.getMessage().contains("504")) {
					result=serviceStatus+ ServiceStatus.GATEWAY_TIME_OUT.toString()+"\"}";
				}else {
					result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
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
		    * get id and description from service
		    * @param Url
		    * @param Description
		    * @param tenantId
			* @return Object
		    */
			@Override             
			public Object checkIsExistAssetTypeSubType(String tenantId, String assetTypeId, String subTypeId, String serviceUrl, Class<?> classObject) {
				try {
				  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
			      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			      HttpEntity <String> entity = new HttpEntity<>(headers);                          
			      return restTemplate.exchange(serviceUrl+tenantId+"/asset-type-id/"+assetTypeId+"/asset-sub-type-id/"+subTypeId, HttpMethod.GET, entity, classObject).getBody();
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
					}else if(e.getMessage().contains("504")) {
						result=serviceStatus+ ServiceStatus.GATEWAY_TIME_OUT.toString()+"\"}";
					}else {
						result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
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
	public Object checkIsExistAssetsDetail(String tenantId, String id, String serviceUrl, Class<?> classObject) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public ExistsDocumentResponseResource existDocument(String tenantId, String documentId , String origin) {
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity <String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(urlDocumentUpload+tenantId+"/exist/"+documentId+"?origin=" + origin , HttpMethod.GET, entity, ExistsDocumentResponseResource.class).getBody();
        } catch (RestClientException e) {
            String result=null;
            if(e.getMessage().contains("503")) {
                result=serviceStatus +ServiceStatus.NOT_AVAILABLE.toString()+"\"}";
            } else if(e.getMessage().contains("500")) {
                result=serviceStatus+ ServiceStatus.EXCEPTION.toString()+"\"}";
            } else if(e.getMessage().contains("404")) {
                result=serviceStatus+ ServiceStatus.NOT_FOUND.toString()+"\"}";
            } else if(e.getMessage().contains("400")) {
                result=serviceStatus+ ServiceStatus.BAD_REQUEST.toString()+"\"}";
            } else if(e.getMessage().contains("504")) {
                result=serviceStatus+ ServiceStatus.GATEWAY_TIME_OUT.toString()+"\"}";
            } else {
                result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
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
	public void serviceValidationListExceptionHadle(ServiceStatus serviceStatus, ServiceEntity serviceEntity, ServicePoint servicePoint, Integer index) {
		switch(serviceStatus) { 
	        case NOT_AVAILABLE: 
	        	throw new DetailListValidateException(environment.getProperty("common.not-available"), serviceEntity, servicePoint, index);
	        case NOT_FOUND: 
	        	throw new DetailListValidateException(environment.getProperty("common.not-found"), serviceEntity, servicePoint, index);
	        case EXCEPTION: 
	        	throw new DetailListValidateException(environment.getProperty("common.internal-server-error"), serviceEntity, servicePoint, index);
	        case BAD_REQUEST: 
	        	throw new DetailListValidateException(environment.getProperty("common.bad-request"), serviceEntity, servicePoint, index);
	        case GATEWAY_TIME_OUT: 
	        	throw new DetailListValidateException(environment.getProperty("common.gateway-time-out"), serviceEntity, servicePoint, index);
	        default:
	        	throw new DetailListValidateException(environment.getProperty("common.internal-server-error"), serviceEntity, servicePoint, index);
		}
	}

	@Override
	public ColInetragationAssetsDetailsRequestResource integrateWithCollateral(String tenantId,  String createdUser,
			AssetsDetailsRequestResource assetsDetailsRequestResource, Class<ColInetragationAssetsDetailsRequestResource> colInetragationAssetsDetailsRequestResource) {
		try {
			HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	        headers.set("username", createdUser);
		    LoggerRequest.getInstance().logInfo1("******<<<<<<IN entity >>>>>>******"+assetsDetailsRequestResource);

	        
			HttpEntity <AssetsDetailsRequestResource> entity = new HttpEntity<>(assetsDetailsRequestResource, headers);
		    ResponseEntity<ColInetragationAssetsDetailsRequestResource> response =restTemplate.exchange(collateralIntergrationUrl+"/"+tenantId+"/", HttpMethod.POST, entity, ColInetragationAssetsDetailsRequestResource.class);
		   return response.getBody();
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
		         }else {
						result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
		         }
		         ObjectMapper mapper = new ObjectMapper();
		         try {
		             return mapper.readValue(result, ColInetragationAssetsDetailsRequestResource.class);
		         } catch (IOException e1) {
		             return null;
		         }
		     }
	
	}
	
//	@Override
//	public ColInetragationRequestResponceResource valuationsIntegratewithCol(String tenantId, String createdUser,
//			ValuationDetailsAddResource valuationDetailsAddResource, Long assetEntityId,
//			Class<ColInetragationRequestResponceResource> class1) {
//
//		try {
//			HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
//	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//	        headers.set("username", createdUser);
//		    LoggerRequest.getInstance().logInfo1("******<<<<<<IN entity >>>>>>******"+valuationDetailsAddResource);
//        
//			HttpEntity <ValuationDetailsAddResource> entity = new HttpEntity<>(valuationDetailsAddResource, headers);
//		    ResponseEntity<ColInetragationRequestResponceResource> response =restTemplate.exchange(urlColValuationDetails+tenantId+"/assetEntity/"+assetEntityId, 
//		    		HttpMethod.POST, entity, ColInetragationRequestResponceResource.class);
//		   return response.getBody();
//			} catch (RestClientException e) {
//		         String result=null;
//		         if(e.getMessage().contains("503")) {
//		             result=serviceStatus +ServiceStatus.NOT_AVAILABLE.toString()+"\"}";
//		         }else if(e.getMessage().contains("500")) {
//		             result=serviceStatus+ ServiceStatus.EXCEPTION.toString()+"\"}";
//		         }else if(e.getMessage().contains("404")) {
//		             result=serviceStatus+ ServiceStatus.NOT_FOUND.toString()+"\"}";
//		         }else if(e.getMessage().contains("400")) {
//		             result=serviceStatus+ ServiceStatus.BAD_REQUEST.toString()+"\"}";
//		         }else {
//						result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
//		         }
//		         ObjectMapper mapper = new ObjectMapper();
//		         try {
//		             return mapper.readValue(result, ColInetragationRequestResponceResource.class);
//		         } catch (IOException e1) {
//		             return null;
//		         }
//		     }
//	}

	@Override
	public ColInetragationRequestResponceResource valuationsIntegratewithCol(String tenantId, String createdUser,
			ValuationDetailsListAddResource valuationDetailsAddResourceList, Long assetEntityId,
			Class<ColInetragationRequestResponceResource> class1) {
		try {
			HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	        headers.set("username", createdUser);
		    LoggerRequest.getInstance().logInfo1("******<<<<<<IN entity >>>>>>******"+valuationDetailsAddResourceList);

		    HttpEntity <ValuationDetailsListAddResource> entity = new HttpEntity<>(valuationDetailsAddResourceList, headers);
		    ResponseEntity<ColInetragationRequestResponceResource> response =restTemplate.exchange(urlColValuationDetails+tenantId, 
	    		HttpMethod.POST, entity, ColInetragationRequestResponceResource.class);
//		    ResponseEntity<ColInetragationRequestResponceResource> response =restTemplate.exchange("http://localhost:8080/col-collateral/asset-valuation-detail/"+tenantId, 
//		    		HttpMethod.POST, entity, ColInetragationRequestResponceResource.class);
		   return response.getBody();
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
		         }else {
		        	 if(e instanceof HttpStatusCodeException) {
		 				String errorResponse = ((HttpStatusCodeException)e).getResponseBodyAsString();
		 				result = errorResponse;
		 				ObjectMapper mapper = new ObjectMapper();
		 				try {	
		 					return mapper.readValue(result, ColInetragationRequestResponceResource.class);
		 				} catch (IOException e1) {
		 					return null;
		 				} 
		 			 } 
		         }
		         ObjectMapper mapper = new ObjectMapper();
		         try {
		             return mapper.readValue(result, ColInetragationRequestResponceResource.class);
		         } catch (IOException e1) {
		             return null;
		         }
		     }
	}

	
//	@Override
//	public ColInetragationRequestResponceResource valuationsIntegratewithCol(String tenantId, String createdUser,
//			ValuationDetailsListAddResource valuationDetailsAddResourceList, Long assetEntityId,
//			Class<ColInetragationRequestResponceResource> class1) {
//		ColInetragationRequestResponceResource colInetragationRequestResponceRes = null;
//		try {
//			HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
//	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//	        headers.set("username", createdUser);
//		    LoggerRequest.getInstance().logInfo1("******<<<<<<IN entity >>>>>>******"+valuationDetailsAddResourceList);
//
//	        
//		    HttpEntity <ValuationDetailsListAddResource> entity = new HttpEntity<>(valuationDetailsAddResourceList, headers);
////		    ResponseEntity<ColInetragationRequestResponceResource> response =restTemplate.exchange(urlColValuationDetails+tenantId+"/assetEntity/"+assetEntityId, 
////		    		HttpMethod.POST, entity, ColInetragationRequestResponceResource.class);
////		   return response.getBody();
////		    
//		   String resposeMsg =restTemplate.exchange(urlColValuationDetails+tenantId, 
//		    		HttpMethod.POST, entity, String.class).getBody();
//			if(resposeMsg!=null ) {				
//				colInetragationRequestResponceRes =new ColInetragationRequestResponceResource();
//				colInetragationRequestResponceRes.setMessages(resposeMsg);			
//			}
//			
////			} catch (HttpClientErrorException e) {
////			colInetragationRequestResponceRes.setMessages(e.getResponseBodyAsString());
////		    }
//			//UNPROCESSABLE_ENTITY
////			}catch (HttpClientErrorException ex) {
////		
////			    if (ex.getStatusCode() != null) {
////			    	colInetragationRequestResponceRes.setMessages(ex.getMessage());
////			    }
//			}
//	
//			catch (RestClientException e) {
//				colInetragationRequestResponceRes=new ColInetragationRequestResponceResource();
////				if(e instanceof HttpStatusCodeException) {
////					String errorResponse = ((HttpStatusCodeException)e).getResponseBodyAsString();
////					colInetragationRequestResponceRes.setMessages(e.getMessage());
////				}
//				if(e.getMessage().contains("503")) {
//					colInetragationRequestResponceRes.setServiceStatus(ServiceStatus.NOT_AVAILABLE);
//					colInetragationRequestResponceRes.setMessages(e.getMessage());
//				}else if(e.getMessage().contains("500")) {
//					colInetragationRequestResponceRes.setServiceStatus(ServiceStatus.EXCEPTION);
//					colInetragationRequestResponceRes.setMessages(e.getMessage());
//				}else if(e.getMessage().contains("404")) {
//					colInetragationRequestResponceRes.setServiceStatus(ServiceStatus.NOT_FOUND);
//					colInetragationRequestResponceRes.setMessages(e.getMessage());
//				}else if(e.getMessage().contains("400")) {
//					colInetragationRequestResponceRes.setServiceStatus(ServiceStatus.BAD_REQUEST);
//					colInetragationRequestResponceRes.setMessages(e.getMessage());
//				}else if(e.getMessage().contains("504")) {
//					colInetragationRequestResponceRes.setServiceStatus(ServiceStatus.GATEWAY_TIME_OUT);
//					colInetragationRequestResponceRes.setMessages(e.getMessage());;
//				}else {
//					colInetragationRequestResponceRes.setServiceStatus(ServiceStatus.EXCEPTION);
//					colInetragationRequestResponceRes.setMessages(e.getMessage());
//					
//				}
//			}
//			return colInetragationRequestResponceRes;
//	}
	
	@Override
	public ColInetragationRequestResponceResource valuationUpdateIntegratewithCol(String tenantId, String createdUser,
			ValuationDetailsUpdateResource valuationDetails, Class<ColInetragationRequestResponceResource> class1) {
		
		try {
			
			HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	        headers.set("username", createdUser);
		    LoggerRequest.getInstance().logInfo1("******<<<<<<IN entity >>>>>>******"+valuationDetails);
		    Long valuationId = Long.parseLong(valuationDetails.getValuationDetailsId());
        
			HttpEntity <ValuationDetailsUpdateResource> entity = new HttpEntity<>(valuationDetails, headers);
			ResponseEntity<ColInetragationRequestResponceResource> response =restTemplate.exchange(urlColValuationDetails+tenantId+"/"+valuationId, 
		    		HttpMethod.PUT, entity, ColInetragationRequestResponceResource.class);
//		    ResponseEntity<ColInetragationRequestResponceResource> response =restTemplate.exchange("http://localhost:8080/col-collateral/asset-valuation-detail/"+tenantId+"/"+valuationId, 
//		    		HttpMethod.PUT, entity, ColInetragationRequestResponceResource.class);
		   return response.getBody();
			} catch (RestClientException e) {
				String result=null;
				if(e instanceof HttpStatusCodeException) {
				String errorResponse = ((HttpStatusCodeException)e).getResponseBodyAsString();
				result = errorResponse;
				ObjectMapper mapper = new ObjectMapper();
				try {	
					return mapper.readValue(result, ColInetragationRequestResponceResource.class);
				} catch (IOException e1) {
					return null;
				} } 
		         if(e.getMessage().contains("503")) {
		             result=serviceStatus +ServiceStatus.NOT_AVAILABLE.toString()+"\"}";
		         }else if(e.getMessage().contains("500")) {
		             result=serviceStatus+ ServiceStatus.EXCEPTION.toString()+"\"}";
		         }else if(e.getMessage().contains("404")) {
		             result=serviceStatus+ ServiceStatus.NOT_FOUND.toString()+"\"}";
		         }else if(e.getMessage().contains("400")) {
		             result=serviceStatus+ ServiceStatus.BAD_REQUEST.toString()+"\"}";
//		         }else {
//						result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
		         }
		         ObjectMapper mapper = new ObjectMapper();
		         try {
		             return mapper.readValue(result, ColInetragationRequestResponceResource.class);
		         } catch (IOException e1) {
		             return null;
		         }
		     }
	}
	
	
	@Override
	public void serviceValidationExceptionHadle(ServiceStatus serviceStatus, ServiceEntity serviceEntity, ServicePoint servicePoint) {
		switch(serviceStatus) { 
	        case NOT_AVAILABLE: 
	        	throw new DetailValidateException(environment.getProperty("common.not-available"), serviceEntity, servicePoint);
	        case NOT_FOUND: 
	        	throw new DetailValidateException(environment.getProperty("common.not-found"), serviceEntity, servicePoint);
	        case EXCEPTION: 
	        	throw new DetailValidateException(environment.getProperty("common.internal-server-error"), serviceEntity, servicePoint);
	        case BAD_REQUEST: 
	        	throw new DetailValidateException(environment.getProperty("common.bad-request"), serviceEntity, servicePoint);
	        case GATEWAY_TIME_OUT: 
	        	throw new DetailValidateException(environment.getProperty("common.gateway-time-out"), serviceEntity, servicePoint);
	        default:
	        	throw new DetailValidateException(environment.getProperty("common.internal-server-error"), serviceEntity, servicePoint);
		}
	}
	
	
	
	@Override
	public Boolean existsPersonIdentification(String tenantId, Long personId, Long pidtId) {
		try {
			HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity <String> entity = new HttpEntity<>(headers);
			ExistsPersonResponseResource existsPersonResponseResource= restTemplate.exchange(urlPerson+"identification/"+tenantId+"/exist/"+personId+"/"+pidtId, HttpMethod.GET, entity, ExistsPersonResponseResource.class).getBody();
			return existsPersonResponseResource.getValue();
		} catch (RestClientException e) {
			restClientException(e);
			return Boolean.FALSE;
		}
	}
	@Override
	public Boolean existsPendingPersonIdentification(String tenantId, Long ppidtId) {
		try {
			HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity <String> entity = new HttpEntity<>(headers);
			ExistsPersonResponseResource existsPersonResponseResource= restTemplate.exchange(urlPerson+"pending-identification/"+tenantId+"/exist/"+ppidtId, HttpMethod.GET, entity, ExistsPersonResponseResource.class).getBody();
			return existsPersonResponseResource.getValue();
		} catch (RestClientException e) {
			restClientException(e);
			return Boolean.FALSE;
		}
	}

	@Override
	public Boolean existsPersonAddress(String tenantId, Long personId, Long paddId) {
		try {
			HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity <String> entity = new HttpEntity<>(headers);
			ExistsPersonResponseResource existsPersonResponseResource= restTemplate.exchange(urlPerson+"address/"+tenantId+"/exist/"+personId+"/"+paddId, HttpMethod.GET, entity, ExistsPersonResponseResource.class).getBody();
			return existsPersonResponseResource.getValue();
		} catch (RestClientException e) {
			restClientException(e);
			return Boolean.FALSE;
		}
	}

	@Override
	public Boolean existsPersonContact(String tenantId, Long personId, Long pconId) {
		try {
			HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity <String> entity = new HttpEntity<>(headers);
			ExistsPersonResponseResource existsPersonResponseResource= restTemplate.exchange(urlPerson+"contact/"+tenantId+"/exist/"+personId+"/"+pconId, HttpMethod.GET, entity, ExistsPersonResponseResource.class).getBody();
			return existsPersonResponseResource.getValue();
		} catch (RestClientException e) {
			restClientException(e);
			return Boolean.FALSE;
		}
	}
	
	private void restClientException(RestClientException e) {
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
	
	@Override
	public Boolean validatePersonIdentification(String tenantId, IdentificationValidateResource identificationValidateResource) {
		try {
			  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		      HttpEntity <IdentificationValidateResource> entity = new HttpEntity<>(identificationValidateResource, headers);
		      HttpStatus httpStatus = restTemplate.exchange(urlPerson+"identification-type/"+tenantId+"/validate", HttpMethod.POST, entity, String.class).getStatusCode();
		      if(HttpStatus.ACCEPTED.equals(httpStatus))
		    	  return Boolean.TRUE;
		      else
		    	  return Boolean.FALSE;
		} catch (RestClientException e) {
			 return Boolean.FALSE;
		}
	}
	

	@Override
	public Boolean validatePersonContact(String tenantId, ContactValidateResource contactValidateResource) {
		try {
			  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		      HttpEntity <ContactValidateResource> entity = new HttpEntity<>(contactValidateResource, headers);
		      HttpStatus httpStatus = restTemplate.exchange(urlPerson+"contact-type/"+tenantId+"/validate", HttpMethod.POST, entity, String.class).getStatusCode();
		      if(HttpStatus.ACCEPTED.equals(httpStatus))
		    	  return Boolean.TRUE;
		      else
		    	  return Boolean.FALSE;
		} catch (RestClientException e) {
			 return Boolean.FALSE;
		}
	}

	@Override
	public LastValuationDateResponseResource checkLastValuationDate(String tenantId, Long assetEntityId,
			String urlColValuationDetails, Class<?> classObject) {
			try {
			  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		      HttpEntity <String> entity = new HttpEntity<>(headers);
		      return (LastValuationDateResponseResource) restTemplate.exchange(urlColValuationDetails+tenantId+"/lastValuationDate/"+assetEntityId, HttpMethod.GET, entity, classObject).getBody();
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
				}else if(e.getMessage().contains("504")) {
					result=serviceStatus+ ServiceStatus.GATEWAY_TIME_OUT.toString()+"\"}";
				}else {
					result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
				}
				ObjectMapper mapper = new ObjectMapper();
				try {
					return (LastValuationDateResponseResource) mapper.readValue(result, classObject);
				} catch (IOException e1) {
					return null;
				}
			}
		}

//	@Override
//	public ColInetragationRequestResponceResource insuranceAddIntegratewithCol(String tenantId, String createdUser,
//			InsuranceDetailsAddResource insuranceDetails, Class<ColInetragationRequestResponceResource> class1) {
//
//		try {
//			HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
//	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//	        headers.set("username", createdUser);
//		    LoggerRequest.getInstance().logInfo1("******<<<<<<IN entity >>>>>>******"+insuranceDetails);
//        
//			HttpEntity <InsuranceDetailsAddResource> entity = new HttpEntity<>(insuranceDetails, headers);
//		    ResponseEntity<ColInetragationRequestResponceResource> response =restTemplate.exchange(urlColInsuranceDetails+tenantId, 
//		    		HttpMethod.POST, entity, ColInetragationRequestResponceResource.class);
//		   return response.getBody();
//			} catch (RestClientException e) {
//		         String result=null;
//		         if(e.getMessage().contains("503")) {
//		             result=serviceStatus +ServiceStatus.NOT_AVAILABLE.toString()+"\"}";
//		         }else if(e.getMessage().contains("500")) {
//		             result=serviceStatus+ ServiceStatus.EXCEPTION.toString()+"\"}";
//		         }else if(e.getMessage().contains("404")) {
//		             result=serviceStatus+ ServiceStatus.NOT_FOUND.toString()+"\"}";
//		         }else if(e.getMessage().contains("400")) {
//		             result=serviceStatus+ ServiceStatus.BAD_REQUEST.toString()+"\"}";
//		         }else {
//						result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
//		         }
//		         ObjectMapper mapper = new ObjectMapper();
//		         try {
//		             return mapper.readValue(result, ColInetragationRequestResponceResource.class);
//		         } catch (IOException e1) {
//		             return null;
//		         }
//		     }
//	}
	@Override
	public ColInetragationRequestResponceResource insuranceAddIntegratewithCol(String tenantId, String createdUser,
			InsuranceDetailsAddResource insuranceDetails, Class<ColInetragationRequestResponceResource> class1) {

		try {
			HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	        headers.set("username", createdUser);
		    LoggerRequest.getInstance().logInfo1("******<<<<<<IN entity >>>>>>******"+insuranceDetails);
        
			HttpEntity <InsuranceDetailsAddResource> entity = new HttpEntity<>(insuranceDetails, headers);
		    ResponseEntity<ColInetragationRequestResponceResource> response =restTemplate.exchange(urlColInsuranceDetails+tenantId, 
		    		HttpMethod.POST, entity, ColInetragationRequestResponceResource.class);
		   return response.getBody();
			} catch (RestClientException e) {
				String result=null;
				if(e instanceof HttpStatusCodeException) {
				String errorResponse = ((HttpStatusCodeException)e).getResponseBodyAsString();
				result = errorResponse;
				ObjectMapper mapper = new ObjectMapper();
				try {	
					return mapper.readValue(result, ColInetragationRequestResponceResource.class);
				} catch (IOException e1) {
					return null;
				} } 
		         if(e.getMessage().contains("503")) {
		             result=serviceStatus +ServiceStatus.NOT_AVAILABLE.toString()+"\"}";
		         }else if(e.getMessage().contains("500")) {
		             result=serviceStatus+ ServiceStatus.EXCEPTION.toString()+"\"}";
		         }else if(e.getMessage().contains("404")) {
		             result=serviceStatus+ ServiceStatus.NOT_FOUND.toString()+"\"}";
		         }else if(e.getMessage().contains("400")) {
		             result=serviceStatus+ ServiceStatus.BAD_REQUEST.toString()+"\"}";
//		         }else {
//						result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
		         }
		         ObjectMapper mapper = new ObjectMapper();
		         try {
		             return mapper.readValue(result, ColInetragationRequestResponceResource.class);
		         } catch (IOException e1) {
		             return null;
		         }
		     }
	}
	

	@Override
	public ColInetragationRequestResponceResource insuranceUPdateIntegratewithCol(String tenantId, String createdUser,
			InsuranceDetailsAddResource insuranceDetails, Class<ColInetragationRequestResponceResource> class1) {
		try {
			HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	        headers.set("username", createdUser);
		    LoggerRequest.getInstance().logInfo1("******<<<<<<IN entity >>>>>>******"+insuranceDetails);
		    String id = insuranceDetails.getInsuId();
			HttpEntity <InsuranceDetailsAddResource> entity = new HttpEntity<>(insuranceDetails, headers);
			ResponseEntity<ColInetragationRequestResponceResource> response =restTemplate.exchange(urlColInsuranceDetails+tenantId+"/"+id, 
		    		HttpMethod.PUT, entity, ColInetragationRequestResponceResource.class);
		   return response.getBody();
			} catch (RestClientException e) {
				String result=null;
				if(e instanceof HttpStatusCodeException) {
				String errorResponse = ((HttpStatusCodeException)e).getResponseBodyAsString();
				result = errorResponse;
				ObjectMapper mapper = new ObjectMapper();
				try {	
					return mapper.readValue(result, ColInetragationRequestResponceResource.class);
				} catch (IOException e1) {
					return null;
				} } 
		         if(e.getMessage().contains("503")) {
		             result=serviceStatus +ServiceStatus.NOT_AVAILABLE.toString()+"\"}";
		         }else if(e.getMessage().contains("500")) {
		             result=serviceStatus+ ServiceStatus.EXCEPTION.toString()+"\"}";
		         }else if(e.getMessage().contains("404")) {
		             result=serviceStatus+ ServiceStatus.NOT_FOUND.toString()+"\"}";
		         }else if(e.getMessage().contains("400")) {
		             result=serviceStatus+ ServiceStatus.BAD_REQUEST.toString()+"\"}";
//		         }else {
//						result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
		         }
		         ObjectMapper mapper = new ObjectMapper();
		         try {
		             return mapper.readValue(result, ColInetragationRequestResponceResource.class);
		         } catch (IOException e1) {
		             return null;
		         }
		     }
	}

	@Override
	public InsuranceDetailsResponseResource checkIsExistInsuDetails(String tenantId, String assetEntityId,
			String urlAssetEntity, Class<?> classObject) {
		InsuranceDetailsResponseResource responseList = null;
		try {
		  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	  	HttpEntity<List<InsuranceDetailsRequestResource>> entity = new HttpEntity<List<InsuranceDetailsRequestResource>>(headers);
	  	InsuranceDetailsRequestResource[] responseArray =restTemplate.exchange(urlColInsuranceDetails+tenantId+"/assetEntity/"+assetEntityId, HttpMethod.GET, entity, InsuranceDetailsRequestResource[].class).getBody();
		if(responseArray!=null) {
			List<InsuranceDetailsRequestResource> response = Arrays.asList(responseArray);
			responseList=new InsuranceDetailsResponseResource();
			responseList.setInsuranceDetailsRequestResource(response);
		}

		return responseList;
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
				}else if(e.getMessage().contains("504")) {
					result=serviceStatus+ ServiceStatus.GATEWAY_TIME_OUT.toString()+"\"}";
				}else {
					result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
				}
				ObjectMapper mapper = new ObjectMapper();
				try {
					return (InsuranceDetailsResponseResource) mapper.readValue(result, classObject);
				} catch (IOException e1) {
					return null;
				}
			}
		}

	@Override
	public ValuationAndInsuranceDetListResponseResource checkIsExitingAssetValuations(String tenantId, Long assetEntityId,
			String urlColValuationDetails, Class<?> classObject) {
		ValuationAndInsuranceDetListResponseResource responseList = null;
		try {
		  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	  	HttpEntity<List<ValuationDetailsRequestResource>> entity = new HttpEntity<List<ValuationDetailsRequestResource>>(headers);
	  	ValuationDetailsRequestResource[] responseArray =restTemplate.exchange(urlColValuationDetails+tenantId+"/assetEntity/"+assetEntityId, HttpMethod.GET, entity, ValuationDetailsRequestResource[].class).getBody();
		if(responseArray!=null) {
			List<ValuationDetailsRequestResource> response = Arrays.asList(responseArray);
			responseList=new ValuationAndInsuranceDetListResponseResource();
			responseList.setValuationDetails(response);
		}

		return responseList;
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
				}else if(e.getMessage().contains("504")) {
					result=serviceStatus+ ServiceStatus.GATEWAY_TIME_OUT.toString()+"\"}";
				}else {
					result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
				}
				ObjectMapper mapper = new ObjectMapper();
				try {
					return (ValuationAndInsuranceDetListResponseResource) mapper.readValue(result, classObject);
				} catch (IOException e1) {
					return null;
				}
			}
		}
	
	

	@Override
	public ValuationAndInsuranceDetListResponseResource checkIsExitingAssetInsurance(String tenantId, Long assetEntityId,
			String urlColValuationDetails, Class<?> classObject) {
		ValuationAndInsuranceDetListResponseResource responseList = null;
		try {
		  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	
	  	HttpEntity<List<InsuranceDetailsRequestResource>> entity = new HttpEntity<List<InsuranceDetailsRequestResource>>(headers);
	  	InsuranceDetailsRequestResource[] responseArray =restTemplate.exchange(urlColValuationDetails+tenantId+"/assetEntity/"+assetEntityId, HttpMethod.GET, entity, InsuranceDetailsRequestResource[].class).getBody();
		if(responseArray!=null) {
			List<InsuranceDetailsRequestResource> response = Arrays.asList(responseArray);
			responseList=new ValuationAndInsuranceDetListResponseResource();
			responseList.setInsuranceDetails(response);
		}
	
		return responseList;
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
				}else if(e.getMessage().contains("504")) {
					result=serviceStatus+ ServiceStatus.GATEWAY_TIME_OUT.toString()+"\"}";
				}else {
					result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
				}
				ObjectMapper mapper = new ObjectMapper();
				try {
					return (ValuationAndInsuranceDetListResponseResource) mapper.readValue(result, classObject);
				} catch (IOException e1) {
					return null;
				}
			}
		}

@Override
public Object checkIsExistInsuAsset(String tenantId, String id, String serviceUrl, Class<?> classObject) {
	try {
	  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
      HttpEntity <String> entity = new HttpEntity<>(headers);
      return restTemplate.exchange(serviceUrl+tenantId+"/basic/"+id, HttpMethod.GET, entity, classObject).getBody();
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
		}else if(e.getMessage().contains("504")) {
			result=serviceStatus+ ServiceStatus.GATEWAY_TIME_OUT.toString()+"\"}";
		}else {
			result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
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
public PerCommonList isExistPerCommonList(String tenantId, String id, CommonListReferenceCode gender,
		String urlPerCmlist, Class<PerCommonList> class1) {
	try {
		  HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <String> entity = new HttpEntity<>(headers);
	      return restTemplate.exchange(urlPerCmlist+tenantId+"/"+id, HttpMethod.GET, entity, PerCommonList.class).getBody();
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
			}else if(e.getMessage().contains("504")) {
				result=serviceStatus+ ServiceStatus.GATEWAY_TIME_OUT.toString()+"\"}";
			}else {
				result=serviceStatus+ ServiceStatus.OTHER.toString()+"\"}";
			}
			ObjectMapper mapper = new ObjectMapper();
			try {
				return mapper.readValue(result, PerCommonList.class);
			} catch (IOException e1) {
				return null;
			}
		}
}


@Override 
public Boolean validatePersonIdentificationType(String tenantId, Long personId, Long penPersonId, Long pidtId, Long ppidtId, Long identificationTypeId) {
	HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	HttpEntity <String> entity = new HttpEntity<>(headers);
	if(personId!=null) {
		if(pidtId!=null) {
			ExistsPersonResponseResource existsPersonResponseResource= restTemplate.exchange(urlPerson+"identification/"+tenantId+"/exist-type-without/"+personId+"/"+pidtId+"/"+identificationTypeId, HttpMethod.GET, entity, ExistsPersonResponseResource.class).getBody();
			return existsPersonResponseResource.getValue();
		}else {
			ExistsPersonResponseResource existsPersonResponseResource= restTemplate.exchange(urlPerson+"identification/"+tenantId+"/exist-type/"+personId+"/"+identificationTypeId, HttpMethod.GET, entity, ExistsPersonResponseResource.class).getBody();
			return existsPersonResponseResource.getValue();
		}
	}else {
		if(penPersonId!=null) {
			String stringPpidtId="";
			if(ppidtId!=null)
				stringPpidtId=ppidtId.toString();
			ExistsPersonResponseResource existsPersonResponseResource= restTemplate.exchange(urlPerson+"pending-identification/"+tenantId+"/exist-type/"+penPersonId+"/"+identificationTypeId+"?ppidtId="+stringPpidtId, HttpMethod.GET, entity, ExistsPersonResponseResource.class).getBody();
			return existsPersonResponseResource.getValue();
		}else {
			return Boolean.TRUE;
		}
	}
}


@Override 
public Boolean validatePersonIdentificationNumber(String tenantId, Long pidtId, Long ppidtId, Long identificationTypeId, String identificationNumber) {
	IdentificationNumberCheckRequestResource identificationNumberCheckRequestResource = new IdentificationNumberCheckRequestResource();
	identificationNumberCheckRequestResource.setIdentificationNumber(identificationNumber);
	HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	HttpEntity <IdentificationNumberCheckRequestResource> entity = new HttpEntity<>(identificationNumberCheckRequestResource, headers);
	if(pidtId!=null || ppidtId!=null) {
	ExistsPersonResponseResource existsPersonResponseResource=null;
	if(pidtId!=null && ppidtId!=null) {
	existsPersonResponseResource= restTemplate.exchange(urlPerson+"identification/"+tenantId+"/exist-number-without/"+identificationTypeId+"?pidtId="+pidtId+"&ppidtId="+ppidtId, HttpMethod.POST, entity, ExistsPersonResponseResource.class).getBody();
	}else if(pidtId!=null) {
	existsPersonResponseResource= restTemplate.exchange(urlPerson+"identification/"+tenantId+"/exist-number-without/"+identificationTypeId+"?pidtId="+pidtId, HttpMethod.POST, entity, ExistsPersonResponseResource.class).getBody();
	}else if(ppidtId!=null) {
	existsPersonResponseResource= restTemplate.exchange(urlPerson+"identification/"+tenantId+"/exist-number-without/"+identificationTypeId+"?ppidtId="+ppidtId, HttpMethod.POST, entity, ExistsPersonResponseResource.class).getBody();
	}
	return existsPersonResponseResource.getValue();
	}else {
	ExistsPersonResponseResource existsPersonResponseResource= restTemplate.exchange(urlPerson+"identification/"+tenantId+"/exist-number/"+identificationTypeId, HttpMethod.POST, entity, ExistsPersonResponseResource.class).getBody();
	return existsPersonResponseResource.getValue();
	}
}



	
}

