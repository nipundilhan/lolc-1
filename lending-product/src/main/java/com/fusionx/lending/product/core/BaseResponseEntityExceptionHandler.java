/*
 * Copyright (c) 2018. LOLC Technology Services Ltd.
 * Author: Rangana Sri Dilruck
 * Date: 2019-06-12
 */

package com.fusionx.lending.product.core;
 

import com.fusionx.lending.product.ErrorCode;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.TaxType;
import com.fusionx.lending.product.error.code.RequestMapperEnum;
import com.fusionx.lending.product.exception.*;
import com.fusionx.lending.product.mt.TenantHolder;
import com.fusionx.lending.product.resources.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This will return the relevant object based on the caught exception
 *
 * @author Rangana
 * @version 1.0
 * @since 2019-08-1
 */
@RestControllerAdvice
public class BaseResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private String tenantNotFound = "tenant.not-found";
    private String commonInternalServerError = "common.internal-server-error";
    private String userNotFound = "common.user-not-found";
    private String project = "lending-product";

    @Autowired
    private Environment environment;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        try {
            TenantHolder.clear();
            Field sField = null;
            String fieldName = null;
            Integer index = null;
            Integer index1 = null;
            Integer atPoint = null;
            String className = ex.getBindingResult().getObjectName();
            switch (className) {
                case "residencyEligibilityAddResource":
                    ResidencyEligibilityResource residencyEligibilityAddResource = new ResidencyEligibilityResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = residencyEligibilityAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(residencyEligibilityAddResource.getClass().cast(residencyEligibilityAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(residencyEligibilityAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "residencyEligibilityUpdateResource":
                    ResidencyEligibilityUpdateResource residencyEligibilityUpdateResource = new ResidencyEligibilityUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = residencyEligibilityUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(residencyEligibilityUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = residencyEligibilityUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(residencyEligibilityUpdateResource.getClass().getSuperclass().cast(residencyEligibilityUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(residencyEligibilityUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "addTaxProfileRequestResource": 
	        		AddTaxProfileRequestResource addTaxProfileRequestResource=addTaxProfileRequestResourceValidation(ex.getBindingResult().getFieldErrors());
					return new ResponseEntity<>(addTaxProfileRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
					
	        	case "updateTaxProfileRequestResource": 
	        		UpdateTaxProfileRequestResource updateTaxProfileRequestResource=updateTaxProfileRequestResourceValidation(ex.getBindingResult().getFieldErrors());
					return new ResponseEntity<>(updateTaxProfileRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
	        	
                /**
                 * @author Senitha
                 * ADDED FOR FX-6154, FX-6508, FX-6511, FX-6512, FX-6515, FX-6509 ON 06-06-2021 ********************************************************************/
                case "addBaseRequest":
                    AddBaseRequest addBaseRequest = new AddBaseRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = addBaseRequest.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(addBaseRequest.getClass().cast(addBaseRequest), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(addBaseRequest, HttpStatus.UNPROCESSABLE_ENTITY);
                case "updateBaseRequest":
                    UpdateBaseRequest updateBaseRequest = new UpdateBaseRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = updateBaseRequest.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(updateBaseRequest, error.getDefaultMessage());
                        } else {
                            sField = updateBaseRequest.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(updateBaseRequest.getClass().getSuperclass().cast(updateBaseRequest), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(updateBaseRequest, HttpStatus.UNPROCESSABLE_ENTITY);

                case "commonListAddResource":
                    CommonListAddResource commonListAddResource = new CommonListAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = commonListAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(commonListAddResource.getClass().cast(commonListAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(commonListAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "commonListUpdateResource":
                    CommonListUpdateResource commonListUpdateResource = new CommonListUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = commonListUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(commonListUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = commonListUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(commonListUpdateResource.getClass().getSuperclass().cast(commonListUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(commonListUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "applicationFrequencyAddResource":
                    ApplicationFrequencyAddResource applicationFrequencyAddResource = new ApplicationFrequencyAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = applicationFrequencyAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(applicationFrequencyAddResource.getClass().cast(applicationFrequencyAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(applicationFrequencyAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "applicationFrequencyUpdateResource":
                    ApplicationFrequencyUpdateResource applicationFrequencyUpdateResource = new ApplicationFrequencyUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = applicationFrequencyUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(applicationFrequencyUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = applicationFrequencyUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(applicationFrequencyUpdateResource.getClass().getSuperclass().cast(applicationFrequencyUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(applicationFrequencyUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "otherFeeTypeAddResource":
                    OtherFeeTypeAddResource otherFeeTypeAddResource = new OtherFeeTypeAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = otherFeeTypeAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(otherFeeTypeAddResource.getClass().cast(otherFeeTypeAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(otherFeeTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "otherFeeTypeUpdateResource":
                    OtherFeeTypeUpdateResource otherFeeTypeUpdateResource = new OtherFeeTypeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = otherFeeTypeUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(otherFeeTypeUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = otherFeeTypeUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(otherFeeTypeUpdateResource.getClass().getSuperclass().cast(otherFeeTypeUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(otherFeeTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                /**
                 * @author Senitha
                 * ENDED FOR FX-6154, FX-6508, FX-6511, FX-6512, FX-6515, FX-6509 ON 06-06-2021 ********************************************************************/

                case "residencyIncludeAddResource":
                    ResidencyIncludeResource residencyIncludeResource = new ResidencyIncludeResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = residencyIncludeResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(residencyIncludeResource.getClass().cast(residencyIncludeResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(residencyIncludeResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "residencyIncludeUpdateResource":
                    ResidencyIncludeUpdateResource residencyIncludeUpdateResource = new ResidencyIncludeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = residencyIncludeUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(residencyIncludeUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = residencyIncludeUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(residencyIncludeUpdateResource.getClass().getSuperclass().cast(residencyIncludeUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(residencyIncludeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "productGroupAddResource":
                    ProductGroupAddResource productGroupAddResource = new ProductGroupAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = productGroupAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(productGroupAddResource.getClass().cast(productGroupAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(productGroupAddResource, HttpStatus.UNPROCESSABLE_ENTITY);


                case "productGroupUpdateResource":
                    ProductGroupUpdateResource productGroupUpdateResource = new ProductGroupUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = productGroupUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(productGroupUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = productGroupUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(productGroupUpdateResource.getClass().getSuperclass().cast(productGroupUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(productGroupUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "addMainProduct":
                    AddMainProduct addMainProduct = new AddMainProduct();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = addMainProduct.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(addMainProduct.getClass().cast(addMainProduct), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(addMainProduct, HttpStatus.UNPROCESSABLE_ENTITY);

                case "systemGeneratedDocumentTypeAddResource": 
      			  SystemGeneratedDocumentTypeAddResource systemGeneratedDocumentTypeAddResource = new SystemGeneratedDocumentTypeAddResource();
	        		
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName=error.getField();
                        if(fieldName.startsWith("documentDetails")) {
		                     fieldName=fieldName.replace("documentDetails", "");
		                         atPoint = fieldName.indexOf(']');
		                         index=Integer.parseInt(fieldName.substring(1, atPoint));
		                         fieldName=fieldName.substring(atPoint+2);
		                         for (int i=0; i<=index; i++) {
		                             if(systemGeneratedDocumentTypeAddResource.getDocumentDetails()==null || systemGeneratedDocumentTypeAddResource.getDocumentDetails().isEmpty()) {
		                            	 systemGeneratedDocumentTypeAddResource.setDocumentDetails(new ArrayList<SystemGeneratedDocumentTypeDetailsAddResource>());
		                            	 systemGeneratedDocumentTypeAddResource.getDocumentDetails().add(i, new SystemGeneratedDocumentTypeDetailsAddResource());
		                             }else{
		                                 if((systemGeneratedDocumentTypeAddResource.getDocumentDetails().size()-1)<i) {
		                                	 systemGeneratedDocumentTypeAddResource.getDocumentDetails().add(i, new SystemGeneratedDocumentTypeDetailsAddResource());
		                                 }
		                             }
		                         } 
		                         
		                        sField=systemGeneratedDocumentTypeAddResource.getDocumentDetails().get(index).getClass().getDeclaredField(fieldName);
                              sField.setAccessible(true);
                              sField.set(systemGeneratedDocumentTypeAddResource.getDocumentDetails().get(index), error.getDefaultMessage());

		                        }
	                    else {
	                        sField =  systemGeneratedDocumentTypeAddResource.getClass().getDeclaredField(error.getField());
	                        sField.setAccessible(true);
	                        sField.set(systemGeneratedDocumentTypeAddResource.getClass().cast(systemGeneratedDocumentTypeAddResource), error.getDefaultMessage());
	                    }
	                }
                    return new ResponseEntity<>(systemGeneratedDocumentTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                    
      		  case "systemGeneratedDocumentTypeUpdateResource": 
      			SystemGeneratedDocumentTypeUpdateResource systemGeneratedDocumentTypeUpdateResource = new SystemGeneratedDocumentTypeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project+ " " +error.getField()+" "+error.getDefaultMessage());
                        fieldName=error.getField();
                            sField =  systemGeneratedDocumentTypeUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(systemGeneratedDocumentTypeUpdateResource, error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(systemGeneratedDocumentTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                    

                case "updateProductRequest":
                    UpdateProductRequest updateProductRequest = new UpdateProductRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = updateProductRequest.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(updateProductRequest, error.getDefaultMessage());

                        } else {
                            sField = updateProductRequest.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(updateProductRequest.getClass().getSuperclass().cast(updateProductRequest), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(updateProductRequest, HttpStatus.UNPROCESSABLE_ENTITY);

                case "addProductSegmentRequest":
                    AddProductSegmentRequest addProductSegmentRequest = new AddProductSegmentRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = addProductSegmentRequest.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(addProductSegmentRequest.getClass().cast(addProductSegmentRequest), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(addProductSegmentRequest, HttpStatus.UNPROCESSABLE_ENTITY);

                case "updateProductSegmentRequest":
                    UpdateProductSegmentRequest updateProductSegmentRequest = new UpdateProductSegmentRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = updateProductSegmentRequest.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(updateProductSegmentRequest, error.getDefaultMessage());
                        } else {
                            sField = updateProductSegmentRequest.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(updateProductSegmentRequest.getClass().getSuperclass().cast(updateProductSegmentRequest), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(updateProductSegmentRequest, HttpStatus.UNPROCESSABLE_ENTITY);

                case "commonAddResource":
                    CommonAddResource commonAddResource = new CommonAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = commonAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(commonAddResource.getClass().cast(commonAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(commonAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "commonUpdateResource":
                    CommonUpdateResource commonUpdateResource = new CommonUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = commonUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(commonUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = commonUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(commonUpdateResource.getClass().getSuperclass().cast(commonUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(commonUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "eligibilityAddResource":
                    EligibilityAddResource eligibilityAddResource = new EligibilityAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = eligibilityAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(eligibilityAddResource.getClass().cast(eligibilityAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(eligibilityAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "eligibilityUpdateResource":
                    EligibilityUpdateResource eligibilityUpdateResource = new EligibilityUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = eligibilityUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = eligibilityUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityUpdateResource.getClass().getSuperclass().cast(eligibilityUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(eligibilityUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
	        	/*case "eligibilityCustomerTypeAddResource":
	        		EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource = new EligibilityCustomerTypeAddResource();
					for (FieldError error : ex.getBindingResult().getFieldErrors()) {
						LoggerRequest.getInstance().logInfo(project+ " " +error.getField()+" "+error.getDefaultMessage());
						sField =  eligibilityCustomerTypeAddResource.getClass().getDeclaredField(error.getField());
			            sField.setAccessible(true);
			            sField.set(eligibilityCustomerTypeAddResource.getClass().cast(eligibilityCustomerTypeAddResource), error.getDefaultMessage());
					}
					return new ResponseEntity<>(eligibilityCustomerTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);*/
	        	/*case "eligibilityCustomerTypeUpdateResource":
	        		EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = new EligibilityCustomerTypeUpdateResource();
					for (FieldError error : ex.getBindingResult().getFieldErrors()) {
						LoggerRequest.getInstance().logInfo(project+ " " +error.getField()+" "+error.getDefaultMessage());
						fieldName=error.getField();
						if(fieldName.startsWith("version")) {
                            sField =  eligibilityCustomerTypeUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityCustomerTypeUpdateResource, error.getDefaultMessage());
                        }else {
                            sField =  eligibilityCustomerTypeUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityCustomerTypeUpdateResource.getClass().getSuperclass().cast(eligibilityCustomerTypeUpdateResource), error.getDefaultMessage());
                        }
					}
					return new ResponseEntity<>(eligibilityCustomerTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);*/

                case "eligibilityCustomerTypeAddResource":
                    EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource = new EligibilityCustomerTypeAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = eligibilityCustomerTypeAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(eligibilityCustomerTypeAddResource.getClass().cast(eligibilityCustomerTypeAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(eligibilityCustomerTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "eligibilityCustomerTypeUpdateResource":
                    EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = new EligibilityCustomerTypeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = eligibilityCustomerTypeUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityCustomerTypeUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = eligibilityCustomerTypeUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityCustomerTypeUpdateResource.getClass().cast(eligibilityCustomerTypeUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(eligibilityCustomerTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);


                case "eligibilityTemplateIndustryAddResource":
                    EligibilityTemplateIndustryAddResource eligibilityTemplateIndustryAddResource = new EligibilityTemplateIndustryAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = eligibilityTemplateIndustryAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(eligibilityTemplateIndustryAddResource.getClass().cast(eligibilityTemplateIndustryAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(eligibilityTemplateIndustryAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "eligibilityTemplateIndustryUpdateResource":
                    EligibilityTemplateIndustryUpdateResource eligibilityTemplateIndustryUpdateResource = new EligibilityTemplateIndustryUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = eligibilityTemplateIndustryUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityTemplateIndustryUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = eligibilityTemplateIndustryUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityTemplateIndustryUpdateResource.getClass().getSuperclass().cast(eligibilityTemplateIndustryUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(eligibilityTemplateIndustryUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "eligibilityTemplateDisbursementAddResource":
                    EligibilityTemplateDisbursementAddResource eligibilityTemplateDisbursementAddResource = new EligibilityTemplateDisbursementAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = eligibilityTemplateDisbursementAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(eligibilityTemplateDisbursementAddResource.getClass().cast(eligibilityTemplateDisbursementAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(eligibilityTemplateDisbursementAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "eligibilityTemplateDisbursementUpdateResource":
                    EligibilityTemplateDisbursementUpdateResource eligibilityTemplateDisbursementUpdateResource = new EligibilityTemplateDisbursementUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = eligibilityTemplateDisbursementUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityTemplateDisbursementUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = eligibilityTemplateDisbursementUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityTemplateDisbursementUpdateResource.getClass().getSuperclass().cast(eligibilityTemplateDisbursementUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(eligibilityTemplateDisbursementUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);


//	        	case "eligibilityTemplateBranchAddResource":
//	        		EligibilityTemplateBranchAddResource eligibilityTemplateBranchAddResource = new EligibilityTemplateBranchAddResource();
//					for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//						LoggerRequest.getInstance().logInfo(project+ " " +error.getField()+" "+error.getDefaultMessage());
//						sField =  eligibilityTemplateBranchAddResource.getClass().getDeclaredField(error.getField());
//			            sField.setAccessible(true);
//			            sField.set(eligibilityTemplateBranchAddResource.getClass().cast(eligibilityTemplateBranchAddResource), error.getDefaultMessage());
//					}
//					return new ResponseEntity<>(eligibilityTemplateBranchAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
//	        	case "eligibilityTemplateBranchUpdateResource":
//	        		EligibilityTemplateBranchUpdateResource eligibilityTemplateBranchUpdateResource = new EligibilityTemplateBranchUpdateResource();
//					for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//						LoggerRequest.getInstance().logInfo(project+ " " +error.getField()+" "+error.getDefaultMessage());
//						fieldName=error.getField();
//						if(fieldName.startsWith("version")) {
//                            sField =  eligibilityTemplateBranchUpdateResource.getClass().getDeclaredField(error.getField());
//                            sField.setAccessible(true);
//                            sField.set(eligibilityTemplateBranchUpdateResource, error.getDefaultMessage());
//                        }else {
//                            sField =  eligibilityTemplateBranchUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
//                            sField.setAccessible(true);
//                            sField.set(eligibilityTemplateBranchUpdateResource.getClass().getSuperclass().cast(eligibilityTemplateBranchUpdateResource), error.getDefaultMessage());
//                        }
//					}
//					return new ResponseEntity<>(eligibilityTemplateBranchUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
//
//
                case "eligibilityTemplateLegalStructureAddResource":
                    EligibilityTemplateLegalStructureAddResource eligibilityTemplateLegalStructureAddResource = new EligibilityTemplateLegalStructureAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = eligibilityTemplateLegalStructureAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(eligibilityTemplateLegalStructureAddResource.getClass().cast(eligibilityTemplateLegalStructureAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(eligibilityTemplateLegalStructureAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "eligibilityTemplateLegalStructureUpdateResource":
                    EligibilityTemplateLegalStructureUpdateResource eligibilityTemplateLegalStructureUpdateResource = new EligibilityTemplateLegalStructureUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = eligibilityTemplateLegalStructureUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityTemplateLegalStructureUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = eligibilityTemplateLegalStructureUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityTemplateLegalStructureUpdateResource.getClass().getSuperclass().cast(eligibilityTemplateLegalStructureUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(eligibilityTemplateLegalStructureUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "repaymentAddResource":
                    RepaymentAddResource repaymentAddResource = new RepaymentAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = repaymentAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(repaymentAddResource.getClass().cast(repaymentAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(repaymentAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "repaymentUpdateResource":
                    RepaymentUpdateResource repaymentUpdateResource = new RepaymentUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = repaymentUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(repaymentUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = repaymentUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(repaymentUpdateResource.getClass().getSuperclass().cast(repaymentUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(repaymentUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "feeChargeAddResource":
                    FeeChargeAddResource feeChargeAddResource = new FeeChargeAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = feeChargeAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(feeChargeAddResource.getClass().cast(feeChargeAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(feeChargeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "feeChargeUpdateResource":
                    FeeChargeUpdateResource feeChargeUpdateResource = new FeeChargeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = feeChargeUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(feeChargeUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = feeChargeUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(feeChargeUpdateResource.getClass().getSuperclass().cast(feeChargeUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(feeChargeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "masterDefinitionAddResource":
                    MasterDefinitionAddResource masterDefinitionAddResource = new MasterDefinitionAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = masterDefinitionAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(masterDefinitionAddResource.getClass().cast(masterDefinitionAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(masterDefinitionAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "masterDefinitionUpdateResource":
                    MasterDefinitionUpdateResource masterDefinitionUpdateResource = new MasterDefinitionUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = masterDefinitionUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(masterDefinitionUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = masterDefinitionUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(masterDefinitionUpdateResource.getClass().getSuperclass().cast(masterDefinitionUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(masterDefinitionUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                /***************************************************Added by Ravishika*********************************************************************************/
                case "featureBenefitItemAddResource":
                    FeatureBenefitItemAddResource featureBenefitItemAddResource = new FeatureBenefitItemAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = featureBenefitItemAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(featureBenefitItemAddResource.getClass().cast(featureBenefitItemAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(featureBenefitItemAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "featureBenefitItemUpdateResource":
                    FeatureBenefitItemUpdateResource featureBenefitItemUpdateResource = new FeatureBenefitItemUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = featureBenefitItemUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(featureBenefitItemUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = featureBenefitItemUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(featureBenefitItemUpdateResource.getClass().getSuperclass().cast(featureBenefitItemUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(featureBenefitItemUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                /***************************************************Ended by Ravishika*********************************************************************************/

                case "addBaseEligibilityRequest":
                    AddBaseEligibilityRequest addBaseEligibilityRequest = new AddBaseEligibilityRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = addBaseEligibilityRequest.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(addBaseEligibilityRequest.getClass().cast(addBaseEligibilityRequest), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(addBaseEligibilityRequest, HttpStatus.UNPROCESSABLE_ENTITY);
                case "updateBaseEligibilityRequest":
                    UpdateBaseEligibilityRequest updateBaseEligibilityRequest = new UpdateBaseEligibilityRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = updateBaseEligibilityRequest.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(updateBaseEligibilityRequest.getClass().cast(updateBaseEligibilityRequest), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(updateBaseEligibilityRequest, HttpStatus.UNPROCESSABLE_ENTITY);
                case "addNotesRequest":
                    AddNotesRequest addNotesRequest = new AddNotesRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = addNotesRequest.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(addNotesRequest.getClass().cast(addNotesRequest), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(addNotesRequest, HttpStatus.UNPROCESSABLE_ENTITY);
                case "updateNotesRequest":
                    UpdateNotesRequest updateNotesRequest = new UpdateNotesRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = updateNotesRequest.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(updateNotesRequest, error.getDefaultMessage());
                        } else {
                            sField = updateNotesRequest.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(updateNotesRequest.getClass().getSuperclass().cast(updateNotesRequest), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(updateNotesRequest, HttpStatus.UNPROCESSABLE_ENTITY);
                //*************************FX-6888***************Start**********************
                case "eligibilityOfficerTypeAddResource":
                    EligibilityOfficerTypeAddResource eligibilityOfficerTypeAddResource = new EligibilityOfficerTypeAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = eligibilityOfficerTypeAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(eligibilityOfficerTypeAddResource.getClass().cast(eligibilityOfficerTypeAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(eligibilityOfficerTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "eligibilityOfficerTypeUpdateResource":
                    EligibilityOfficerTypeUpdateResource eligibilityOfficerTypeUpdateResource = new EligibilityOfficerTypeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = eligibilityOfficerTypeUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(eligibilityOfficerTypeUpdateResource.getClass().cast(eligibilityOfficerTypeUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(eligibilityOfficerTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                //*************************FX-6888***************End**********************
                //*************************FX-6891***************Start**********************
                case "eligibilityCurrencyAddResource":
                    EligibilityCurrencyAddResource eligibilityCurrencyAddResource = new EligibilityCurrencyAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = eligibilityCurrencyAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(eligibilityCurrencyAddResource.getClass().cast(eligibilityCurrencyAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(eligibilityCurrencyAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "eligibilityCurrencyUpdateResource":
                    EligibilityCurrencyUpdateResource eligibilityCurrencyUpdateResource = new EligibilityCurrencyUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = eligibilityCurrencyUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityCurrencyUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = eligibilityCurrencyUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityCurrencyUpdateResource.getClass().getSuperclass().cast(eligibilityCurrencyUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(eligibilityCurrencyUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                //*************************FX-6891***************End**********************
                //*************************FX-6889***************Start**********************
                case "eligibilityCollateralAddResource":
                    EligibilityCollateralAddResource eligibilityCollateralAddResource = new EligibilityCollateralAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = eligibilityCollateralAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(eligibilityCollateralAddResource.getClass().cast(eligibilityCollateralAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(eligibilityCollateralAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "eligibilityCollateralUpdateResource":
                    EligibilityCollateralUpdateResource eligibilityCollateralUpdateResource = new EligibilityCollateralUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = eligibilityCollateralUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityCollateralUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = eligibilityCollateralUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityCollateralUpdateResource.getClass().getSuperclass().cast(eligibilityCollateralUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(eligibilityCollateralUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                //*************************FX-6889***************End**********************
                case "ageEligibilityAddResource":
                    AgeEligibilityAddResource ageEligibilityAddResource = new AgeEligibilityAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = ageEligibilityAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(ageEligibilityAddResource.getClass().cast(ageEligibilityAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(ageEligibilityAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "ageEligibilityUpdateResource":
                    AgeEligibilityUpdateResource ageEligibilityUpdateResource = new AgeEligibilityUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = ageEligibilityUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(ageEligibilityUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = ageEligibilityUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(ageEligibilityUpdateResource.getClass().getSuperclass().cast(ageEligibilityUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(ageEligibilityUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "masterCurrencyAddResource":
                    MasterCurrencyAddResource masterCurrencyAddResource = new MasterCurrencyAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = masterCurrencyAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(masterCurrencyAddResource.getClass().cast(masterCurrencyAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(masterCurrencyAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "masterCurrencyUpdateResource":
                    MasterCurrencyUpdateResource masterCurrencyUpdateResource = new MasterCurrencyUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = masterCurrencyUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(masterCurrencyUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = masterCurrencyUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(masterCurrencyUpdateResource.getClass().getSuperclass().cast(masterCurrencyUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(masterCurrencyUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "interestTierBandSetAddResource":
                    InterestTierBandSetAddResource interestTierBandSetAddResource = new InterestTierBandSetAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = interestTierBandSetAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(interestTierBandSetAddResource.getClass().cast(interestTierBandSetAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(interestTierBandSetAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "interestTierBandSetUpdateResource":
                    InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = new InterestTierBandSetUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = interestTierBandSetUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(interestTierBandSetUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = interestTierBandSetUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(interestTierBandSetUpdateResource.getClass().getSuperclass().cast(interestTierBandSetUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(interestTierBandSetUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "interestTierBandAddResource":
                    InterestTierBandAddResource interestTierBandAddResource = new InterestTierBandAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = interestTierBandAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(interestTierBandAddResource.getClass().cast(interestTierBandAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(interestTierBandAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "interestTierBandUpdateResource":
                    InterestTierBandUpdateResource interestTierBandUpdateResource = new InterestTierBandUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = interestTierBandUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(interestTierBandUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = interestTierBandUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(interestTierBandUpdateResource.getClass().getSuperclass().cast(interestTierBandUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(interestTierBandUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "eligibilityOtherAddResource":
                    EligibilityOtherAddResource eligibilityOtherAddResource = new EligibilityOtherAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        sField = eligibilityOtherAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(eligibilityOtherAddResource.getClass().cast(eligibilityOtherAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(eligibilityOtherAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "eligibilityOtherUpdateResource":
                    EligibilityOtherUpdateResource eligibilityOtherUpdateResource = new EligibilityOtherUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = eligibilityOtherUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityOtherUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = eligibilityOtherUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityOtherUpdateResource.getClass().cast(eligibilityOtherUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(eligibilityOtherUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "eligibilityResidencyEligibilityAddResource":
                    EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource = new EligibilityResidencyEligibilityAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = eligibilityResidencyEligibilityAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(eligibilityResidencyEligibilityAddResource.getClass().cast(eligibilityResidencyEligibilityAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(eligibilityResidencyEligibilityAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "eligibilityResidencyEligibilityUpdateResource":
                    EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = new EligibilityResidencyEligibilityUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = eligibilityResidencyEligibilityUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityResidencyEligibilityUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = eligibilityResidencyEligibilityUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityResidencyEligibilityUpdateResource.getClass().cast(eligibilityResidencyEligibilityUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(eligibilityResidencyEligibilityUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "masterDefAccountRuleAddResource":
                    MasterDefAccountRuleAddResource masterDefAccountRuleAddResource = new MasterDefAccountRuleAddResource();
                    MasterDefAccountRuleReconResource reconciliations = new MasterDefAccountRuleReconResource();
                    MasterDefAccountRuleGlPostResource glEntryPosting = new MasterDefAccountRuleGlPostResource();
                    masterDefAccountRuleAddResource.setReconciliations(reconciliations);
                    masterDefAccountRuleAddResource.setGlEntryPosting(glEntryPosting);

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("commonListUsage")) {
                            fieldName = fieldName.replace("commonListUsageList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (masterDefAccountRuleAddResource.getCommonListUsageList() == null || masterDefAccountRuleAddResource.getCommonListUsageList().isEmpty()) {
                                    masterDefAccountRuleAddResource.setCommonListUsageList(new ArrayList<CommonListUsageResource>());
                                    masterDefAccountRuleAddResource.getCommonListUsageList().add(i, new CommonListUsageResource());
                                } else {
                                    if ((masterDefAccountRuleAddResource.getCommonListUsageList().size() - 1) < i) {
                                        masterDefAccountRuleAddResource.getCommonListUsageList().add(i, new CommonListUsageResource());
                                    }
                                }
                            }
                            sField = masterDefAccountRuleAddResource.getCommonListUsageList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(masterDefAccountRuleAddResource.getCommonListUsageList().get(index), error.getDefaultMessage());
                        } else if (fieldName.startsWith("reconcili")) {
                            fieldName = fieldName.replace("reconciliations.", "");
                            sField = masterDefAccountRuleAddResource.getReconciliations().getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(masterDefAccountRuleAddResource.getReconciliations().getClass().cast(masterDefAccountRuleAddResource.getReconciliations()), error.getDefaultMessage());
                        } else if (fieldName.startsWith("glEntryPo")) {
                            fieldName = fieldName.replace("glEntryPosting.", "");
                            sField = masterDefAccountRuleAddResource.getGlEntryPosting().getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(masterDefAccountRuleAddResource.getGlEntryPosting().getClass().cast(masterDefAccountRuleAddResource.getGlEntryPosting()), error.getDefaultMessage());
                        } else {
                            sField = masterDefAccountRuleAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(masterDefAccountRuleAddResource.getClass().cast(masterDefAccountRuleAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(masterDefAccountRuleAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "masterDefinitionAccountRuleUpdateResource":
                    MasterDefinitionAccountRuleUpdateResource masterDefinitionAccountRuleUpdateResource = new MasterDefinitionAccountRuleUpdateResource();
                    reconciliations = new MasterDefAccountRuleReconResource();
                    glEntryPosting = new MasterDefAccountRuleGlPostResource();
                    masterDefinitionAccountRuleUpdateResource.setReconciliations(reconciliations);
                    masterDefinitionAccountRuleUpdateResource.setGlEntryPosting(glEntryPosting);

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("commonListUsage")) {
                            fieldName = fieldName.replace("commonListUsageList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (masterDefinitionAccountRuleUpdateResource.getCommonListUsageList() == null || masterDefinitionAccountRuleUpdateResource.getCommonListUsageList().isEmpty()) {
                                    masterDefinitionAccountRuleUpdateResource.setCommonListUsageList(new ArrayList<MasterDefAccRuleCommonListUpdateResource>());
                                    masterDefinitionAccountRuleUpdateResource.getCommonListUsageList().add(i, new MasterDefAccRuleCommonListUpdateResource());
                                } else {
                                    if ((masterDefinitionAccountRuleUpdateResource.getCommonListUsageList().size() - 1) < i) {
                                        masterDefinitionAccountRuleUpdateResource.getCommonListUsageList().add(i, new MasterDefAccRuleCommonListUpdateResource());
                                    }
                                }
                            }
                            sField = masterDefinitionAccountRuleUpdateResource.getCommonListUsageList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(masterDefinitionAccountRuleUpdateResource.getCommonListUsageList().get(index), error.getDefaultMessage());
                        } else if (fieldName.startsWith("reconcili")) {
                            fieldName = fieldName.replace("reconciliations.", "");
                            sField = masterDefinitionAccountRuleUpdateResource.getReconciliations().getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(masterDefinitionAccountRuleUpdateResource.getReconciliations().getClass().cast(masterDefinitionAccountRuleUpdateResource.getReconciliations()), error.getDefaultMessage());
                        } else if (fieldName.startsWith("glEntryPo")) {
                            fieldName = fieldName.replace("glEntryPosting.", "");
                            sField = masterDefinitionAccountRuleUpdateResource.getGlEntryPosting().getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(masterDefinitionAccountRuleUpdateResource.getGlEntryPosting().getClass().cast(masterDefinitionAccountRuleUpdateResource.getGlEntryPosting()), error.getDefaultMessage());
                        } else {
                            sField = masterDefinitionAccountRuleUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(masterDefinitionAccountRuleUpdateResource.getClass().cast(masterDefinitionAccountRuleUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(masterDefinitionAccountRuleUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "feeChargeDetailOptionalAddResource":
                    FeeChargeDetailOptionalAddResource feeChargeDetailOptionalAddResource = new FeeChargeDetailOptionalAddResource();
                    FeeChargeDetailsCommonResource commonResource = new FeeChargeDetailsCommonResource();
                    feeChargeDetailOptionalAddResource.setCommonResource(commonResource);

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("commonReso")) {
                            fieldName = fieldName.replace("commonResource.", "");
                            sField = feeChargeDetailOptionalAddResource.getCommonResource().getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(feeChargeDetailOptionalAddResource.getCommonResource().getClass().cast(feeChargeDetailOptionalAddResource.getCommonResource()), error.getDefaultMessage());
                        } else if (fieldName.startsWith("optionsList")) {
                            fieldName = fieldName.replace("optionsList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (feeChargeDetailOptionalAddResource.getOptionsList() == null || feeChargeDetailOptionalAddResource.getOptionsList().isEmpty()) {
                                    feeChargeDetailOptionalAddResource.setOptionsList(new ArrayList<FeeChargeDetailOptionalOptionResource>());
                                    feeChargeDetailOptionalAddResource.getOptionsList().add(i, new FeeChargeDetailOptionalOptionResource());
                                } else {
                                    if ((feeChargeDetailOptionalAddResource.getOptionsList().size() - 1) < i) {
                                        feeChargeDetailOptionalAddResource.getOptionsList().add(i, new FeeChargeDetailOptionalOptionResource());
                                    }
                                }
                            }
                            sField = feeChargeDetailOptionalAddResource.getOptionsList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(feeChargeDetailOptionalAddResource.getOptionsList().get(index), error.getDefaultMessage());
                        } else {
                            sField = feeChargeDetailOptionalAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(feeChargeDetailOptionalAddResource.getClass().cast(feeChargeDetailOptionalAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(feeChargeDetailOptionalAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "feeChargeDetailOptionalUpdateResource":
                    FeeChargeDetailOptionalUpdateResource feeChargeDetailOptionalUpdateResource = new FeeChargeDetailOptionalUpdateResource();
                    commonResource = new FeeChargeDetailsCommonResource();
                    feeChargeDetailOptionalUpdateResource.setCommonResource(commonResource);

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("commonReso")) {
                            fieldName = fieldName.replace("commonResource.", "");
                            sField = feeChargeDetailOptionalUpdateResource.getCommonResource().getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(feeChargeDetailOptionalUpdateResource.getCommonResource().getClass().cast(feeChargeDetailOptionalUpdateResource.getCommonResource()), error.getDefaultMessage());
                        } else if (fieldName.startsWith("optionsList")) {
                            fieldName = fieldName.replace("optionsList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (feeChargeDetailOptionalUpdateResource.getOptionsList() == null || feeChargeDetailOptionalUpdateResource.getOptionsList().isEmpty()) {
                                    feeChargeDetailOptionalUpdateResource.setOptionsList(new ArrayList<FeeChargeDetailOptionalOptionResource>());
                                    feeChargeDetailOptionalUpdateResource.getOptionsList().add(i, new FeeChargeDetailOptionalOptionResource());
                                } else {
                                    if ((feeChargeDetailOptionalUpdateResource.getOptionsList().size() - 1) < i) {
                                        feeChargeDetailOptionalUpdateResource.getOptionsList().add(i, new FeeChargeDetailOptionalOptionResource());
                                    }
                                }
                            }
                            sField = feeChargeDetailOptionalUpdateResource.getOptionsList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(feeChargeDetailOptionalUpdateResource.getOptionsList().get(index), error.getDefaultMessage());
                        } else {
                            sField = feeChargeDetailOptionalUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(feeChargeDetailOptionalUpdateResource.getClass().cast(feeChargeDetailOptionalUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(feeChargeDetailOptionalUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "feeChargeDetailAdhocAddResource":
                    FeeChargeDetailAdhocAddResource feeChargeDetailAdhocAddResource = new FeeChargeDetailAdhocAddResource();
                    FeeChargeDetailsCommonResource feeChargeDetailsCommonResource = new FeeChargeDetailsCommonResource();
                    feeChargeDetailAdhocAddResource.setFeeChargeDetailsCommonResource(feeChargeDetailsCommonResource);

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("feeChargeDetailsCommonRes")) {
                            fieldName = fieldName.replace("feeChargeDetailsCommonResource.", "");
                            sField = feeChargeDetailAdhocAddResource.getFeeChargeDetailsCommonResource().getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(feeChargeDetailAdhocAddResource.getFeeChargeDetailsCommonResource().getClass().cast(feeChargeDetailAdhocAddResource.getFeeChargeDetailsCommonResource()), error.getDefaultMessage());
                        } else {
                            sField = feeChargeDetailAdhocAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(feeChargeDetailAdhocAddResource.getClass().cast(feeChargeDetailAdhocAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(feeChargeDetailAdhocAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "feeChargeDetailAdhocUpdateResource":
                    FeeChargeDetailAdhocUpdateResource feeChargeDetailAdhocUpdateResource = new FeeChargeDetailAdhocUpdateResource();
                    feeChargeDetailsCommonResource = new FeeChargeDetailsCommonResource();
                    feeChargeDetailAdhocUpdateResource.setFeeChargeDetailsCommonResource(feeChargeDetailsCommonResource);

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("feeChargeDetailsCommonRes")) {
                            fieldName = fieldName.replace("feeChargeDetailsCommonResource.", "");
                            sField = feeChargeDetailAdhocUpdateResource.getFeeChargeDetailsCommonResource().getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(feeChargeDetailAdhocUpdateResource.getFeeChargeDetailsCommonResource().getClass().cast(feeChargeDetailAdhocUpdateResource.getFeeChargeDetailsCommonResource()), error.getDefaultMessage());
                        } else {
                            sField = feeChargeDetailAdhocUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(feeChargeDetailAdhocUpdateResource.getClass().cast(feeChargeDetailAdhocUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(feeChargeDetailAdhocUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "featureBenefitTemplateItemAddResource":
                    FeatureBenefitTemplateItemAddResource featureBenefitTemplateItemAddResource = new FeatureBenefitTemplateItemAddResource();
                    FeatureBenefitItemUsageResource featureBenefitItemUsageResource = new FeatureBenefitItemUsageResource();
                    featureBenefitTemplateItemAddResource.setFeatureBenefitItemUsageResource(featureBenefitItemUsageResource);

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("featureBenefitItemUsageRes")) {
                            fieldName = fieldName.replace("featureBenefitItemUsageResource.", "");
                            sField = featureBenefitTemplateItemAddResource.getFeatureBenefitItemUsageResource().getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(featureBenefitTemplateItemAddResource.getFeatureBenefitItemUsageResource().getClass().cast(featureBenefitTemplateItemAddResource.getFeatureBenefitItemUsageResource()), error.getDefaultMessage());
                        } else {
                            sField = featureBenefitTemplateItemAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(featureBenefitTemplateItemAddResource.getClass().cast(featureBenefitTemplateItemAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(featureBenefitTemplateItemAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "featureBenefitTemplateItemUpdateResource":
                    FeatureBenefitTemplateItemUpdateResource featureBenefitTemplateItemUpdateResource = new FeatureBenefitTemplateItemUpdateResource();
                    featureBenefitItemUsageResource = new FeatureBenefitItemUsageResource();
                    featureBenefitTemplateItemUpdateResource.setFeatureBenefitItemUsageResource(featureBenefitItemUsageResource);
                    ;
                    ;

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("featureBenefitItemUsageRes")) {
                            fieldName = fieldName.replace("featureBenefitItemUsageResource.", "");
                            sField = featureBenefitTemplateItemUpdateResource.getFeatureBenefitItemUsageResource().getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(featureBenefitTemplateItemUpdateResource.getFeatureBenefitItemUsageResource().getClass().cast(featureBenefitTemplateItemUpdateResource.getFeatureBenefitItemUsageResource()), error.getDefaultMessage());
                        } else {
                            sField = featureBenefitTemplateItemUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(featureBenefitTemplateItemUpdateResource.getClass().cast(featureBenefitTemplateItemUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(featureBenefitTemplateItemUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "featureBenefitTemplateItemEligibilityAddResource":
                    FeatureBenefitTemplateItemEligibilityAddResource featureBenefitTemplateItemEligibilityAddResource = new FeatureBenefitTemplateItemEligibilityAddResource();
                    FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource = new FeatureBenefitEligibilityUsageResource();
                    featureBenefitTemplateItemEligibilityAddResource.setFeatureBenefitEligibilityUsageResource(featureBenefitEligibilityUsageResource);

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("featureBenefitEligibilityUsageRes")) {
                            fieldName = fieldName.replace("featureBenefitEligibilityUsageResource.", "");
                            sField = featureBenefitTemplateItemEligibilityAddResource.getFeatureBenefitEligibilityUsageResource().getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(featureBenefitTemplateItemEligibilityAddResource.getFeatureBenefitEligibilityUsageResource().getClass().cast(featureBenefitTemplateItemEligibilityAddResource.getFeatureBenefitEligibilityUsageResource()), error.getDefaultMessage());
                        } else {
                            sField = featureBenefitTemplateItemEligibilityAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(featureBenefitTemplateItemEligibilityAddResource.getClass().cast(featureBenefitTemplateItemEligibilityAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(featureBenefitTemplateItemEligibilityAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "featureBenefitTemplateItemEligibilityUpdateResource":
                    FeatureBenefitTemplateItemEligibilityUpdateResource featureBenefitTemplateItemEligibilityUpdateResource = new FeatureBenefitTemplateItemEligibilityUpdateResource();
                    featureBenefitEligibilityUsageResource = new FeatureBenefitEligibilityUsageResource();
                    featureBenefitTemplateItemEligibilityUpdateResource.setFeatureBenefitEligibilityUsageResource(featureBenefitEligibilityUsageResource);

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("featureBenefitEligibilityUsageRes")) {
                            fieldName = fieldName.replace("featureBenefitEligibilityUsageResource.", "");
                            sField = featureBenefitTemplateItemEligibilityUpdateResource.getFeatureBenefitEligibilityUsageResource().getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(featureBenefitTemplateItemEligibilityUpdateResource.getFeatureBenefitEligibilityUsageResource().getClass().cast(featureBenefitTemplateItemEligibilityUpdateResource.getFeatureBenefitEligibilityUsageResource()), error.getDefaultMessage());
                        } else {
                            sField = featureBenefitTemplateItemEligibilityUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(featureBenefitTemplateItemEligibilityUpdateResource.getClass().cast(featureBenefitTemplateItemEligibilityUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(featureBenefitTemplateItemEligibilityUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "feeChargeDetailsAddResource":
                    FeeChargeDetailsAddResource feeChargeDetailsAddResource = new FeeChargeDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = feeChargeDetailsAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(feeChargeDetailsAddResource.getClass().cast(feeChargeDetailsAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(feeChargeDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "feeChargeDetailsUpdateResource":
                    FeeChargeDetailsUpdateResource feeChargeDetailsUpdateResource = new FeeChargeDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = feeChargeDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(feeChargeDetailsUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = feeChargeDetailsUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(feeChargeDetailsUpdateResource.getClass().getSuperclass().cast(feeChargeDetailsUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(feeChargeDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "masterPenalInterestResource":
                    MasterPenalInterestResource masterPenalInterestResource = new MasterPenalInterestResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = masterPenalInterestResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(masterPenalInterestResource.getClass().cast(masterPenalInterestResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(masterPenalInterestResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "subProductAddResource":
                    SubProductAddResource subProductAddResource = new SubProductAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = subProductAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(subProductAddResource.getClass().cast(subProductAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(subProductAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "subProductUpdateResource":
                    SubProductUpdateResource subProductUpdateResource = new SubProductUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = subProductUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(subProductUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = subProductUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(subProductUpdateResource.getClass().getSuperclass().cast(subProductUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(subProductUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "feeChargeCapAddResource":
                    FeeChargeCapAddResource feeChargeCapAddResource = new FeeChargeCapAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = feeChargeCapAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(feeChargeCapAddResource.getClass().cast(feeChargeCapAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(feeChargeCapAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "feeChargeCapUpdateResource":
                    FeeChargeCapUpdateResource feeChargeCapUpdateResource = new FeeChargeCapUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = feeChargeCapUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(feeChargeCapUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = feeChargeCapUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(feeChargeCapUpdateResource.getClass().getSuperclass().cast(feeChargeCapUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(feeChargeCapUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "penalInterestTypeAddResource":
                    PenalInterestTypeAddResource penalInterestTypeAddResource = new PenalInterestTypeAddResource();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("penalInterestTypeDetails")) {
                            fieldName = fieldName.replace("penalInterestTypeDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (penalInterestTypeAddResource.getPenalInterestTypeDetails() == null || penalInterestTypeAddResource.getPenalInterestTypeDetails().isEmpty()) {
                                    penalInterestTypeAddResource.setPenalInterestTypeDetails(new ArrayList<PenalInterestTypeDetailsResource>());
                                    penalInterestTypeAddResource.getPenalInterestTypeDetails().add(i, new PenalInterestTypeDetailsResource());
                                } else {
                                    if ((penalInterestTypeAddResource.getPenalInterestTypeDetails().size() - 1) < i) {
                                        penalInterestTypeAddResource.getPenalInterestTypeDetails().add(i, new PenalInterestTypeDetailsResource());
                                    }
                                }
                            }
                            sField = penalInterestTypeAddResource.getPenalInterestTypeDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(penalInterestTypeAddResource.getPenalInterestTypeDetails().get(index), error.getDefaultMessage());

                        } else {
                            sField = penalInterestTypeAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(penalInterestTypeAddResource.getClass().cast(penalInterestTypeAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(penalInterestTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "penalInterestTypeUpdateResource":
                    PenalInterestTypeUpdateResource penalInterestTypeUpdateResource = new PenalInterestTypeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = penalInterestTypeUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(penalInterestTypeUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = penalInterestTypeUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(penalInterestTypeUpdateResource.getClass().getSuperclass().cast(penalInterestTypeUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(penalInterestTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "loanApplicableRangeAddResource":
                    LoanApplicableRangeAddResource loanApplicableRangeAddResource = new LoanApplicableRangeAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = loanApplicableRangeAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(loanApplicableRangeAddResource.getClass().cast(loanApplicableRangeAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(loanApplicableRangeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "loanApplicableRangeUpdateResource":
                    LoanApplicableRangeUpdateResource loanApplicableRangeUpdateResource = new LoanApplicableRangeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = loanApplicableRangeUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(loanApplicableRangeUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = loanApplicableRangeUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(loanApplicableRangeUpdateResource.getClass().getSuperclass().cast(loanApplicableRangeUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(loanApplicableRangeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "disbursementConditionsAddResource":
                    DisbursementConditionsAddResource disbursementConditionsAddResource = new DisbursementConditionsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = disbursementConditionsAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(disbursementConditionsAddResource.getClass().cast(disbursementConditionsAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(disbursementConditionsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "disbursementConditionsUpdateResource":
                    DisbursementConditionsUpdateResource disbursementConditionsUpdateResource = new DisbursementConditionsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = disbursementConditionsUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(disbursementConditionsUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = disbursementConditionsUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(disbursementConditionsUpdateResource.getClass().getSuperclass().cast(disbursementConditionsUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(disbursementConditionsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "accountStatusAddResource":
                    AccountStatusAddResource accountStatusAddResource = new AccountStatusAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = accountStatusAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(accountStatusAddResource.getClass().cast(accountStatusAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(accountStatusAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "accountStatusUpdateResource":
                    AccountStatusUpdateResource accountStatusUpdateResource = new AccountStatusUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = accountStatusUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(accountStatusUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = accountStatusUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(accountStatusUpdateResource.getClass().getSuperclass().cast(accountStatusUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(accountStatusUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "dueDateTemplatesAddResource":
                    DueDateTemplatesAddResource dueDateTemplatesAddResource = new DueDateTemplatesAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("dueDateTypeList")) {
                            fieldName = fieldName.replace("dueDateTypeList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (dueDateTemplatesAddResource.getDueDateTypeList() == null || dueDateTemplatesAddResource.getDueDateTypeList().isEmpty()) {
                                    dueDateTemplatesAddResource.setDueDateTypeList(new ArrayList<DueDateTypeListResource>());
                                    dueDateTemplatesAddResource.getDueDateTypeList().add(i, new DueDateTypeListResource());
                                } else {
                                    if ((dueDateTemplatesAddResource.getDueDateTypeList().size() - 1) < i) {
                                        dueDateTemplatesAddResource.getDueDateTypeList().add(i, new DueDateTypeListResource());
                                    }
                                }
                            }
                            sField = dueDateTemplatesAddResource.getDueDateTypeList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(dueDateTemplatesAddResource.getDueDateTypeList().get(index), error.getDefaultMessage());


                        } else {
                            sField = dueDateTemplatesAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(dueDateTemplatesAddResource.getClass().cast(dueDateTemplatesAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(dueDateTemplatesAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "dueDateTemplatesUpdateResource":
                    DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource = new DueDateTemplatesUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("dueDateTypeList")) {
                            fieldName = fieldName.replace("dueDateTypeList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (dueDateTemplatesUpdateResource.getDueDateTypeList() == null || dueDateTemplatesUpdateResource.getDueDateTypeList().isEmpty()) {
                                    dueDateTemplatesUpdateResource.setDueDateTypeList(new ArrayList<DueDateTypeListResource>());
                                    dueDateTemplatesUpdateResource.getDueDateTypeList().add(i, new DueDateTypeListResource());
                                } else {
                                    if ((dueDateTemplatesUpdateResource.getDueDateTypeList().size() - 1) < i) {
                                        dueDateTemplatesUpdateResource.getDueDateTypeList().add(i, new DueDateTypeListResource());
                                    }
                                }
                            }
                            sField = dueDateTemplatesUpdateResource.getDueDateTypeList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(dueDateTemplatesUpdateResource.getDueDateTypeList().get(index), error.getDefaultMessage());

                        } else if (fieldName.startsWith("version")) {
                            sField = dueDateTemplatesUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(dueDateTemplatesUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = dueDateTemplatesUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(dueDateTemplatesUpdateResource.getClass().cast(dueDateTemplatesUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(dueDateTemplatesUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessSubCenterProductMapAddResource": 
             	   BusinessSubCenterProductMapAddResource businessSubCenterProductMapAddResource = new BusinessSubCenterProductMapAddResource();
                 for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                     LoggerRequest.getInstance().logInfo(project+ " " +error.getField()+" "+error.getDefaultMessage());
                     fieldName=error.getField();
                     if(fieldName.startsWith("products")) {
                          fieldName=fieldName.replace("products", "");
                              atPoint = fieldName.indexOf(']');
                              index=Integer.parseInt(fieldName.substring(1, atPoint));
                              fieldName=fieldName.substring(atPoint+2);
                              for (int i=0; i<=index; i++) {
                                  if(businessSubCenterProductMapAddResource.getProducts()==null || businessSubCenterProductMapAddResource.getProducts().isEmpty()) {
                                 	 businessSubCenterProductMapAddResource.setProducts(new ArrayList<ProductRequestResource>());
                                 	 businessSubCenterProductMapAddResource.getProducts().add(i, new ProductRequestResource());
                                  }else{
                                      if((businessSubCenterProductMapAddResource.getProducts().size()-1)<i) {
                                     	 businessSubCenterProductMapAddResource.getProducts().add(i, new ProductRequestResource());
                                      }
                                  }
                              }
                              sField=businessSubCenterProductMapAddResource.getProducts().get(index).getClass().getDeclaredField(fieldName);
                              sField.setAccessible(true);
                              sField.set(businessSubCenterProductMapAddResource.getProducts().get(index), error.getDefaultMessage());
                     }else {
                         sField =  businessSubCenterProductMapAddResource.getClass().getDeclaredField(error.getField());
                         sField.setAccessible(true);
                         sField.set(businessSubCenterProductMapAddResource.getClass().cast(businessSubCenterProductMapAddResource), error.getDefaultMessage());
                     }
                 }
                 return new ResponseEntity<>(businessSubCenterProductMapAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessSubCenterProductMapUpdateResource": 
             	   BusinessSubCenterProductMapUpdateResource businessSubCenterProductMapUpdateResource = new BusinessSubCenterProductMapUpdateResource();
                 for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                     LoggerRequest.getInstance().logInfo(project+ " " +error.getField()+" "+error.getDefaultMessage());
                     fieldName=error.getField();
                     if(fieldName.startsWith("products")) {
                          fieldName=fieldName.replace("products", "");
                              atPoint = fieldName.indexOf(']');
                              index=Integer.parseInt(fieldName.substring(1, atPoint));
                              fieldName=fieldName.substring(atPoint+2);
                              for (int i=0; i<=index; i++) {
                                  if(businessSubCenterProductMapUpdateResource.getProducts()==null || businessSubCenterProductMapUpdateResource.getProducts().isEmpty()) {
                                 	 businessSubCenterProductMapUpdateResource.setProducts(new ArrayList<ProductRequestResource>());
                                 	 businessSubCenterProductMapUpdateResource.getProducts().add(i, new ProductRequestResource());
                                  }else{
                                      if((businessSubCenterProductMapUpdateResource.getProducts().size()-1)<i) {
                                     	 businessSubCenterProductMapUpdateResource.getProducts().add(i, new ProductRequestResource());
                                      }
                                  }
                              }
                              sField=businessSubCenterProductMapUpdateResource.getProducts().get(index).getClass().getDeclaredField(fieldName);
                              sField.setAccessible(true);
                              sField.set(businessSubCenterProductMapUpdateResource.getProducts().get(index), error.getDefaultMessage());
                     }else {
                         sField =  businessSubCenterProductMapUpdateResource.getClass().getDeclaredField(error.getField());
                         sField.setAccessible(true);
                         sField.set(businessSubCenterProductMapUpdateResource.getClass().cast(businessSubCenterProductMapUpdateResource), error.getDefaultMessage());
                     }
                 }
                 return new ResponseEntity<>(businessSubCenterProductMapUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessSubCenterEmpMapAddResource": 
                	BusinessSubCenterEmpMapAddResource businessSubCenterEmpMapAddResource = new BusinessSubCenterEmpMapAddResource();
                  for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                      LoggerRequest.getInstance().logInfo(project+ " " +error.getField()+" "+error.getDefaultMessage());
                      fieldName=error.getField();
                      if(fieldName.startsWith("employees")) {
                           fieldName=fieldName.replace("employees", "");
                               atPoint = fieldName.indexOf(']');
                               index=Integer.parseInt(fieldName.substring(1, atPoint));
                               fieldName=fieldName.substring(atPoint+2);
                               for (int i=0; i<=index; i++) {
                                   if(businessSubCenterEmpMapAddResource.getEmployees()==null || businessSubCenterEmpMapAddResource.getEmployees().isEmpty()) {
                                	   businessSubCenterEmpMapAddResource.setEmployees(new ArrayList<Employee>());
                                	   businessSubCenterEmpMapAddResource.getEmployees().add(i, new Employee());
                                   }else{
                                       if((businessSubCenterEmpMapAddResource.getEmployees().size()-1)<i) {
                                    	   businessSubCenterEmpMapAddResource.getEmployees().add(i, new Employee());
                                       }
                                   }
                               }
                               sField=businessSubCenterEmpMapAddResource.getEmployees().get(index).getClass().getDeclaredField(fieldName);
                               sField.setAccessible(true);
                               sField.set(businessSubCenterEmpMapAddResource.getEmployees().get(index), error.getDefaultMessage());
                      }else {
                          sField =  businessSubCenterEmpMapAddResource.getClass().getDeclaredField(error.getField());
                          sField.setAccessible(true);
                          sField.set(businessSubCenterEmpMapAddResource.getClass().cast(businessSubCenterEmpMapAddResource), error.getDefaultMessage());
                      }
                  }
                  return new ResponseEntity<>(businessSubCenterEmpMapAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessSubCenterEmpMapUpdateResource": 
                	BusinessSubCenterEmpMapUpdateResource businessSubCenterEmpMapUpdateResource = new BusinessSubCenterEmpMapUpdateResource();
                  for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                      LoggerRequest.getInstance().logInfo(project+ " " +error.getField()+" "+error.getDefaultMessage());
                      fieldName=error.getField();
                      if(fieldName.startsWith("employees")) {
                           fieldName=fieldName.replace("employees", "");
                               atPoint = fieldName.indexOf(']');
                               index=Integer.parseInt(fieldName.substring(1, atPoint));
                               fieldName=fieldName.substring(atPoint+2);
                               for (int i=0; i<=index; i++) {
                                   if(businessSubCenterEmpMapUpdateResource.getEmployees()==null || businessSubCenterEmpMapUpdateResource.getEmployees().isEmpty()) {
                                	   businessSubCenterEmpMapUpdateResource.setEmployees(new ArrayList<Employee>());
                                	   businessSubCenterEmpMapUpdateResource.getEmployees().add(i, new Employee());
                                   }else{
                                       if((businessSubCenterEmpMapUpdateResource.getEmployees().size()-1)<i) {
                                    	   businessSubCenterEmpMapUpdateResource.getEmployees().add(i, new Employee());
                                       }
                                   }
                               }
                               sField=businessSubCenterEmpMapUpdateResource.getEmployees().get(index).getClass().getDeclaredField(fieldName);
                               sField.setAccessible(true);
                               sField.set(businessSubCenterEmpMapUpdateResource.getEmployees().get(index), error.getDefaultMessage());
                      }else {
                          sField =  businessSubCenterEmpMapUpdateResource.getClass().getDeclaredField(error.getField());
                          sField.setAccessible(true);
                          sField.set(businessSubCenterEmpMapUpdateResource.getClass().cast(businessSubCenterEmpMapUpdateResource), error.getDefaultMessage());
                      }
                  }
                  return new ResponseEntity<>(businessSubCenterEmpMapUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);                 
                case "masterDefCountryMainAddResource":
                    Map<String, String> errors = new HashMap<>();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        String fieldNameAttribte = error.getField();
                        String errorMessage = error.getDefaultMessage();
                        errors.put(fieldNameAttribte, errorMessage);
                    }
                    return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
                case "masterDefCountryMainUpdateResource":
                    errors = new HashMap<>();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        String fieldNameAttribte = error.getField();
                        String errorMessage = error.getDefaultMessage();
                        errors.put(fieldNameAttribte, errorMessage);
                    }
                    return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
                case "coreProductAddResource":
                    CoreProductAddResource coreProductAddResource = new CoreProductAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = coreProductAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(coreProductAddResource.getClass().cast(coreProductAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(coreProductAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "coreProductUpdateResource":
                    CoreProductUpdateResource coreProductUpdateResource = new CoreProductUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = coreProductUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(coreProductUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = coreProductUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(coreProductUpdateResource.getClass().getSuperclass().cast(coreProductUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(coreProductUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "interestTemplateAddResource":
                    InterestTemplateAddResource interestTemplateAddResource = new InterestTemplateAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = interestTemplateAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(interestTemplateAddResource.getClass().cast(interestTemplateAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(interestTemplateAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "interestTemplateUpdateResource":
                    InterestTemplateUpdateResource interestTemplateUpdateResource = new InterestTemplateUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = interestTemplateUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(interestTemplateUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = interestTemplateUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(interestTemplateUpdateResource.getClass().getSuperclass().cast(interestTemplateUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(interestTemplateUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "documentCheckListAddResource":
                    DocumentCheckListAddResource documentCheckListAddResource = new DocumentCheckListAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("documentDetails")) {
                            fieldName = fieldName.replace("documentDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (documentCheckListAddResource.getDocumentDetails() == null || documentCheckListAddResource.getDocumentDetails().isEmpty()) {
                                    documentCheckListAddResource.setDocumentDetails(new ArrayList<DocumentDetailsListResource>());
                                    documentCheckListAddResource.getDocumentDetails().add(i, new DocumentDetailsListResource());
                                } else {
                                    if ((documentCheckListAddResource.getDocumentDetails().size() - 1) < i) {
                                        documentCheckListAddResource.getDocumentDetails().add(i, new DocumentDetailsListResource());
                                    }
                                }
                            }
                            sField = documentCheckListAddResource.getDocumentDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(documentCheckListAddResource.getDocumentDetails().get(index), error.getDefaultMessage());
                        } else {
                            sField = documentCheckListAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(documentCheckListAddResource.getClass().cast(documentCheckListAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(documentCheckListAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "documentCheckListUpdateResource":
                    DocumentCheckListUpdateResource documentCheckListUpdateResource = new DocumentCheckListUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = documentCheckListUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(documentCheckListUpdateResource, error.getDefaultMessage());
                        } else {

                            if (fieldName.startsWith("documentDetails")) {
                                fieldName = fieldName.replace("documentDetails", "");
                                atPoint = fieldName.indexOf(']');
                                index = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index; i++) {
                                    if (documentCheckListUpdateResource.getDocumentDetails() == null || documentCheckListUpdateResource.getDocumentDetails().isEmpty()) {
                                        documentCheckListUpdateResource.setDocumentDetails(new ArrayList<DocumentDetailsListResource>());
                                        documentCheckListUpdateResource.getDocumentDetails().add(i, new DocumentDetailsListResource());
                                    } else {
                                        if ((documentCheckListUpdateResource.getDocumentDetails().size() - 1) < i) {
                                            documentCheckListUpdateResource.getDocumentDetails().add(i, new DocumentDetailsListResource());
                                        }
                                    }
                                }
                                sField = documentCheckListUpdateResource.getDocumentDetails().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(documentCheckListUpdateResource.getDocumentDetails().get(index), error.getDefaultMessage());
                            } else {

                                sField = documentCheckListUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                                sField.setAccessible(true);
                                sField.set(documentCheckListUpdateResource.getClass().getSuperclass().cast(documentCheckListUpdateResource), error.getDefaultMessage());
                            }
                        }
                    }
                    return new ResponseEntity<>(documentCheckListUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "masterDefDueDateMappingUpdateResource":
                    MasterDefDueDateMappingUpdateResource masterDefDueDateMappingUpdateResource = new MasterDefDueDateMappingUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = masterDefDueDateMappingUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(masterDefDueDateMappingUpdateResource.getClass().cast(masterDefDueDateMappingUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(masterDefDueDateMappingUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "subProductDueDateMappingUpdateResource":
                    SubProductDueDateMappingUpdateResource subProductDueDateMappingUpdateResource = new SubProductDueDateMappingUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = subProductDueDateMappingUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(subProductDueDateMappingUpdateResource.getClass().cast(subProductDueDateMappingUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(subProductDueDateMappingUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "officerEligibilityResource":
                    OfficerEligibilityResource officerEligibilityResource = new OfficerEligibilityResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        sField = officerEligibilityResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(officerEligibilityResource.getClass().cast(officerEligibilityResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(officerEligibilityResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "officerEligibilityUpdateResource":
                    OfficerEligibilityUpdateResource officerEligibilityUpdateResource = new OfficerEligibilityUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = officerEligibilityUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(officerEligibilityUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = officerEligibilityUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(officerEligibilityUpdateResource.getClass().getSuperclass().cast(officerEligibilityUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(officerEligibilityUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "eligibilityTemplateBranchAddResource":
                    EligibilityTemplateBranchAddResource eligibilityTemplateBranchAddResource = new EligibilityTemplateBranchAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        sField = eligibilityTemplateBranchAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(eligibilityTemplateBranchAddResource.getClass().cast(eligibilityTemplateBranchAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(eligibilityTemplateBranchAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "eligibilityTemplateBranchUpdateResource":
                    EligibilityTemplateBranchUpdateResource eligibilityTemplateBranchUpdateResource = new EligibilityTemplateBranchUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = eligibilityTemplateBranchUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityTemplateBranchUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = eligibilityTemplateBranchUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(eligibilityTemplateBranchUpdateResource.getClass().getSuperclass().cast(eligibilityTemplateBranchUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(eligibilityTemplateBranchUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);


                case "businessCenterAddResource":
                    BusinessCenterAddResource businessCenterAddResource = new BusinessCenterAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        sField = businessCenterAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(businessCenterAddResource.getClass().cast(businessCenterAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(businessCenterAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessCenterUpdateResource":
                    BusinessCenterUpdateResource businessCenterUpdateResource = new BusinessCenterUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = businessCenterUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessCenterUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = businessCenterUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessCenterUpdateResource.getClass().getSuperclass().cast(businessCenterUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(businessCenterUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessCenterProductMapAddResource":
                    BusinessCenterProductMapAddResource businessCenterProductMapAddResource = new BusinessCenterProductMapAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("products")) {
                            fieldName = fieldName.replace("products", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {

                                if (businessCenterProductMapAddResource.getProducts() == null || businessCenterProductMapAddResource.getProducts().isEmpty()) {
                                    businessCenterProductMapAddResource.setProducts((new ArrayList<ProductRequestResource>()));
                                    businessCenterProductMapAddResource.getProducts().add(i, new ProductRequestResource());
                                } else {
                                    if ((businessCenterProductMapAddResource.getProducts().size() - 1) < i) {
                                        businessCenterProductMapAddResource.getProducts().add(i, new ProductRequestResource());
                                    }
                                }
                            }
                        }
                        sField = businessCenterProductMapAddResource.getProducts().get(index).getClass().getDeclaredField(fieldName);
                        sField.setAccessible(true);
                        sField.set(businessCenterProductMapAddResource.getProducts().get(index), error.getDefaultMessage());

                    }
                    return new ResponseEntity<>(businessCenterProductMapAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "businessCenterProductMapUpdateResource":
                    BusinessCenterProductMapUpdateResource businessCenterProductMapUpdateResource = new BusinessCenterProductMapUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = businessCenterProductMapUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessCenterProductMapUpdateResource, error.getDefaultMessage());
                        } else {
                            if (fieldName.startsWith("products")) {
                                fieldName = fieldName.replace("products", "");
                                atPoint = fieldName.indexOf(']');
                                index = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index; i++) {

                                    if (businessCenterProductMapUpdateResource.getProducts() == null || businessCenterProductMapUpdateResource.getProducts().isEmpty()) {
                                        businessCenterProductMapUpdateResource.setProducts((new ArrayList<ProductRequestResource>()));
                                        businessCenterProductMapUpdateResource.getProducts().add(i, new ProductRequestResource());
                                    } else {
                                        if ((businessCenterProductMapUpdateResource.getProducts().size() - 1) < i) {
                                            businessCenterProductMapUpdateResource.getProducts().add(i, new ProductRequestResource());
                                        }
                                    }
                                }
                            }
                            sField = businessCenterProductMapUpdateResource.getProducts().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessCenterProductMapUpdateResource.getProducts().get(index), error.getDefaultMessage());

                        }
                    }
                    return new ResponseEntity<>(businessCenterProductMapUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessCenterEmpMapAddResource":
                    BusinessCenterEmpMapAddResource businessCenterEmpMapAddResource = new BusinessCenterEmpMapAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("employees")) {
                            fieldName = fieldName.replace("employees", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {

                                if (businessCenterEmpMapAddResource.getEmployees() == null || businessCenterEmpMapAddResource.getEmployees().isEmpty()) {
                                    businessCenterEmpMapAddResource.setEmployees((new ArrayList<Employee>()));
                                    businessCenterEmpMapAddResource.getEmployees().add(i, new Employee());
                                } else {
                                    if ((businessCenterEmpMapAddResource.getEmployees().size() - 1) < i) {
                                        businessCenterEmpMapAddResource.getEmployees().add(i, new Employee());
                                    }
                                }
                            }
                        }
                        sField = businessCenterEmpMapAddResource.getEmployees().get(index).getClass().getDeclaredField(fieldName);
                        sField.setAccessible(true);
                        sField.set(businessCenterEmpMapAddResource.getEmployees().get(index), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(businessCenterEmpMapAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "businessCenterEmpMapUpdateResource":
                    BusinessCenterEmpMapUpdateResource businessCenterEmpMapUpdateResource = new BusinessCenterEmpMapUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = businessCenterEmpMapUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessCenterEmpMapUpdateResource, error.getDefaultMessage());
                        } else {
                            if (fieldName.startsWith("employees")) {
                                fieldName = fieldName.replace("employees", "");
                                atPoint = fieldName.indexOf(']');
                                index = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index; i++) {

                                    if (businessCenterEmpMapUpdateResource.getEmployees() == null || businessCenterEmpMapUpdateResource.getEmployees().isEmpty()) {
                                        businessCenterEmpMapUpdateResource.setEmployees((new ArrayList<Employee>()));
                                        businessCenterEmpMapUpdateResource.getEmployees().add(i, new Employee());
                                    } else {
                                        if ((businessCenterEmpMapUpdateResource.getEmployees().size() - 1) < i) {
                                            businessCenterEmpMapUpdateResource.getEmployees().add(i, new Employee());
                                        }
                                    }
                                }
                            }
//		                              sField =  businessCenterEmpMapUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
//		                              sField.setAccessible(true);
//		                              sField.set(businessCenterEmpMapUpdateResource.getClass().getSuperclass().cast(businessCenterEmpMapUpdateResource), error.getDefaultMessage());
                            sField = businessCenterEmpMapUpdateResource.getEmployees().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessCenterEmpMapUpdateResource.getEmployees().get(index), error.getDefaultMessage());

                        }
                    }
                    return new ResponseEntity<>(businessCenterEmpMapUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "penalInterestAddResource":
                    PenalInterestAddResource penalInterestAddResource = new PenalInterestAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = penalInterestAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(penalInterestAddResource.getClass().cast(penalInterestAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(penalInterestAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "penalInterestUpdateResource":
                    PenalInterestUpdateResource penalInterestUpdateResource = new PenalInterestUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = penalInterestUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(penalInterestUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = penalInterestUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(penalInterestUpdateResource.getClass().getSuperclass().cast(penalInterestUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(penalInterestUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "penalTierBandSetAddRequest":
                    PenalTierBandSetAddRequest penalTierBandSetAddRequest = new PenalTierBandSetAddRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = penalTierBandSetAddRequest.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(penalTierBandSetAddRequest.getClass().cast(penalTierBandSetAddRequest), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(penalTierBandSetAddRequest, HttpStatus.UNPROCESSABLE_ENTITY);
                case "penalTierBandSetUpdateRequest":
                    PenalTierBandSetUpdateRequest penalTierBandSetUpdateRequest = new PenalTierBandSetUpdateRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = penalTierBandSetUpdateRequest.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(penalTierBandSetUpdateRequest, error.getDefaultMessage());
                        } else {
                            sField = penalTierBandSetUpdateRequest.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(penalTierBandSetUpdateRequest.getClass().getSuperclass().cast(penalTierBandSetUpdateRequest), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(penalTierBandSetUpdateRequest, HttpStatus.UNPROCESSABLE_ENTITY);
                case "penalTierBandAddRequest":
                    PenalTierBandAddRequest penalTierBandAddRequest = new PenalTierBandAddRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = penalTierBandAddRequest.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(penalTierBandAddRequest.getClass().cast(penalTierBandAddRequest), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(penalTierBandAddRequest, HttpStatus.UNPROCESSABLE_ENTITY);
                case "penalTierBandUpdateRequest":
                    PenalTierBandUpdateRequest penalTierBandUpdateRequest = new PenalTierBandUpdateRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = penalTierBandUpdateRequest.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(penalTierBandUpdateRequest, error.getDefaultMessage());
                        } else {
                            sField = penalTierBandUpdateRequest.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(penalTierBandUpdateRequest.getClass().getSuperclass().cast(penalTierBandUpdateRequest), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(penalTierBandUpdateRequest, HttpStatus.UNPROCESSABLE_ENTITY);
                case "repaymentFrequencyAddResource":
                    RepaymentFrequencyAddResource repaymentFrequencyAddResource = new RepaymentFrequencyAddResource();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        sField = repaymentFrequencyAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(repaymentFrequencyAddResource.getClass().cast(repaymentFrequencyAddResource),
                                error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(repaymentFrequencyAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "repaymentFrequencyUpdateResource":
                    RepaymentFrequencyUpdateResource repaymentFrequencyUpdateResource = new RepaymentFrequencyUpdateResource();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        sField = repaymentFrequencyUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(repaymentFrequencyUpdateResource.getClass().cast(repaymentFrequencyUpdateResource),
                                error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(repaymentFrequencyUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "structuredLoanCalculationResource":
                    StructuredLoanCalculationResource structuredLoanCalculationResource = new StructuredLoanCalculationResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = structuredLoanCalculationResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(structuredLoanCalculationResource.getClass().cast(structuredLoanCalculationResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(structuredLoanCalculationResource, HttpStatus.UNPROCESSABLE_ENTITY);


                case "revolvingLoanCalculatorAddResource":
                    RevolvingLoanCalculatorAddResource revolvingLoanCalculatorAddResource = new RevolvingLoanCalculatorAddResource();
                    RevolvingDetailsResource revolvingDetailsResource = new RevolvingDetailsResource();
                    revolvingLoanCalculatorAddResource.setRevolvingDetails(revolvingDetailsResource);

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("revolvingDetails")) {
                            if (fieldName.startsWith("revolvingDetails.revolvingPaymentSchedule")) {
                                fieldName = fieldName.replace("revolvingDetails.revolvingPaymentSchedule", "");
                                atPoint = fieldName.indexOf(']');
                                index = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index; i++) {

                                    if (revolvingLoanCalculatorAddResource.getRevolvingDetails().getRevolvingPaymentSchedule() == null || revolvingLoanCalculatorAddResource.getRevolvingDetails().getRevolvingPaymentSchedule().isEmpty()) {
                                        revolvingLoanCalculatorAddResource.getRevolvingDetails().setRevolvingPaymentSchedule((new ArrayList<RevolvingPaymentScheduleResource>()));
                                        revolvingLoanCalculatorAddResource.getRevolvingDetails().getRevolvingPaymentSchedule().add(i, new RevolvingPaymentScheduleResource());
                                    } else {
                                        if ((revolvingLoanCalculatorAddResource.getRevolvingDetails().getRevolvingPaymentSchedule().size() - 1) < i) {
                                            revolvingLoanCalculatorAddResource.getRevolvingDetails().getRevolvingPaymentSchedule().add(i, new RevolvingPaymentScheduleResource());
                                        }
                                    }
                                }

                                sField = revolvingLoanCalculatorAddResource.getRevolvingDetails().getRevolvingPaymentSchedule().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(revolvingLoanCalculatorAddResource.getRevolvingDetails().getRevolvingPaymentSchedule().get(index), error.getDefaultMessage());
                            } else {
                                sField = revolvingLoanCalculatorAddResource.getRevolvingDetails().getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(revolvingLoanCalculatorAddResource.getRevolvingDetails().getClass().cast(revolvingLoanCalculatorAddResource.getRevolvingDetails()), error.getDefaultMessage());
                            }

                        } else if (fieldName.startsWith("fixedChargesDetails")) {
                            fieldName = fieldName.replace("fixedChargesDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {

                                if (revolvingLoanCalculatorAddResource.getFixedChargesDetails() == null || revolvingLoanCalculatorAddResource.getFixedChargesDetails().isEmpty()) {
                                    revolvingLoanCalculatorAddResource.setFixedChargesDetails((new ArrayList<FixedChargesResource>()));
                                    revolvingLoanCalculatorAddResource.getFixedChargesDetails().add(i, new FixedChargesResource());
                                } else {
                                    if ((revolvingLoanCalculatorAddResource.getFixedChargesDetails().size() - 1) < i) {
                                        revolvingLoanCalculatorAddResource.getFixedChargesDetails().add(i, new FixedChargesResource());
                                    }
                                }
                            }

                            sField = revolvingLoanCalculatorAddResource.getFixedChargesDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(revolvingLoanCalculatorAddResource.getFixedChargesDetails().get(index), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(revolvingLoanCalculatorAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "fixedLoanCalculatorAddResource":
                    FixedLoanCalculatorAddResource fixedLoanCalculatorAddResource = new FixedLoanCalculatorAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("fixedChargesDetails")) {
                            fieldName = fieldName.replace("fixedChargesDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {

                                if (fixedLoanCalculatorAddResource.getFixedChargesDetails() == null || fixedLoanCalculatorAddResource.getFixedChargesDetails().isEmpty()) {
                                    fixedLoanCalculatorAddResource.setFixedChargesDetails((new ArrayList<FixedChargesResource>()));
                                    fixedLoanCalculatorAddResource.getFixedChargesDetails().add(i, new FixedChargesResource());
                                } else {
                                    if ((fixedLoanCalculatorAddResource.getFixedChargesDetails().size() - 1) < i) {
                                        fixedLoanCalculatorAddResource.getFixedChargesDetails().add(i, new FixedChargesResource());
                                    }
                                }
                            }

                            sField = fixedLoanCalculatorAddResource.getFixedChargesDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(fixedLoanCalculatorAddResource.getFixedChargesDetails().get(index), error.getDefaultMessage());
                        } else if (fieldName.startsWith("optionalChargesDetails")) {
                            fieldName = fieldName.replace("optionalChargesDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {

                                if (fixedLoanCalculatorAddResource.getOptionalChargesDetails() == null || fixedLoanCalculatorAddResource.getOptionalChargesDetails().isEmpty()) {
                                    fixedLoanCalculatorAddResource.setOptionalChargesDetails((new ArrayList<OptionalChargesResource>()));
                                    fixedLoanCalculatorAddResource.getOptionalChargesDetails().add(i, new OptionalChargesResource());
                                } else {
                                    if ((fixedLoanCalculatorAddResource.getOptionalChargesDetails().size() - 1) < i) {
                                        fixedLoanCalculatorAddResource.getOptionalChargesDetails().add(i, new OptionalChargesResource());
                                    }
                                }
                            }
                            
                            if(fieldName.startsWith("optionalChargesList")) {
                                fieldName = fieldName.replace("optionalChargesList", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {

                                    if (fixedLoanCalculatorAddResource.getOptionalChargesDetails().get(index).getOptionalChargesList() == null || fixedLoanCalculatorAddResource.getOptionalChargesDetails().get(index).getOptionalChargesList().isEmpty()) {
                                        fixedLoanCalculatorAddResource.getOptionalChargesDetails().get(index).setOptionalChargesList((new ArrayList<OptionalChargesListResource>()));
                                        fixedLoanCalculatorAddResource.getOptionalChargesDetails().get(index).getOptionalChargesList().add(i, new OptionalChargesListResource());
                                    } else {
                                        if ((fixedLoanCalculatorAddResource.getOptionalChargesDetails().get(index).getOptionalChargesList().size() - 1) < i) {
                                        	fixedLoanCalculatorAddResource.getOptionalChargesDetails().get(index).getOptionalChargesList().add(i, new OptionalChargesListResource());
                                        }
                                    }
                                }
                                sField = fixedLoanCalculatorAddResource.getOptionalChargesDetails().get(index).getOptionalChargesList().get(index1).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(fixedLoanCalculatorAddResource.getOptionalChargesDetails().get(index).getOptionalChargesList().get(index1), error.getDefaultMessage());
                                
                            } else {
                                sField = fixedLoanCalculatorAddResource.getOptionalChargesDetails().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(fixedLoanCalculatorAddResource.getOptionalChargesDetails().get(index), error.getDefaultMessage());
                            }
                        }  else if (fieldName.startsWith("adhocChargesDetails")) {
                            fieldName = fieldName.replace("adhocChargesDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {

                                if (fixedLoanCalculatorAddResource.getAdhocChargesDetails() == null || fixedLoanCalculatorAddResource.getAdhocChargesDetails().isEmpty()) {
                                    fixedLoanCalculatorAddResource.setAdhocChargesDetails((new ArrayList<AdhocChargesResource>()));
                                    fixedLoanCalculatorAddResource.getAdhocChargesDetails().add(i, new AdhocChargesResource());
                                } else {
                                    if ((fixedLoanCalculatorAddResource.getAdhocChargesDetails().size() - 1) < i) {
                                        fixedLoanCalculatorAddResource.getAdhocChargesDetails().add(i, new AdhocChargesResource());
                                    }
                                }
                            }
                            
                            if(fieldName.startsWith("adhocChargesList")) {
                                fieldName = fieldName.replace("adhocChargesList", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {

                                    if (fixedLoanCalculatorAddResource.getAdhocChargesDetails().get(index).getAdhocChargesList() == null || fixedLoanCalculatorAddResource.getAdhocChargesDetails().get(index).getAdhocChargesList().isEmpty()) {
                                        fixedLoanCalculatorAddResource.getAdhocChargesDetails().get(index).setAdhocChargesList((new ArrayList<AdhocChargesListResource>()));
                                        fixedLoanCalculatorAddResource.getAdhocChargesDetails().get(index).getAdhocChargesList().add(i, new AdhocChargesListResource());
                                    } else {
                                        if ((fixedLoanCalculatorAddResource.getAdhocChargesDetails().get(index).getAdhocChargesList().size() - 1) < i) {
                                        	fixedLoanCalculatorAddResource.getAdhocChargesDetails().get(index).getAdhocChargesList().add(i, new AdhocChargesListResource());
                                        }
                                    }
                                }
                                sField = fixedLoanCalculatorAddResource.getAdhocChargesDetails().get(index).getAdhocChargesList().get(index1).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(fixedLoanCalculatorAddResource.getAdhocChargesDetails().get(index).getAdhocChargesList().get(index1), error.getDefaultMessage());
                                
                            }else {
                                sField = fixedLoanCalculatorAddResource.getAdhocChargesDetails().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(fixedLoanCalculatorAddResource.getAdhocChargesDetails().get(index), error.getDefaultMessage());
                            }
                        }else {
                            sField = fixedLoanCalculatorAddResource.getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(fixedLoanCalculatorAddResource.getClass().cast(fixedLoanCalculatorAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(fixedLoanCalculatorAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "tcStructuredDetailAddResource":
                    TcStructuredDetailAddResource tcStructuredDetailAddResource = new TcStructuredDetailAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = tcStructuredDetailAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(tcStructuredDetailAddResource.getClass().cast(tcStructuredDetailAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(tcStructuredDetailAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "documentAddResource":
                    DocumentAddResource documentAddResource = new DocumentAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = documentAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(documentAddResource.getClass().cast(documentAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(documentAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "documentUpdateResource":
                    DocumentUpdateResource documentUpdateResource = new DocumentUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = documentUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(documentUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = documentUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(documentUpdateResource.getClass().getSuperclass().cast(documentUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(documentUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "checkListAddResource":
                    CheckListAddResource checkListAddResource = new CheckListAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = checkListAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(checkListAddResource.getClass().cast(checkListAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(checkListAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "checkListUpdateResource":
                    CheckListUpdateResource checkListUpdateResource = new CheckListUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = checkListUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(checkListUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = checkListUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(checkListUpdateResource.getClass().getSuperclass().cast(checkListUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(checkListUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "borrowersAddResource":
                    BorrowersAddResource borrowersAddResource = new BorrowersAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = borrowersAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(borrowersAddResource.getClass().cast(borrowersAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(borrowersAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "borrowersUpdateResource":
                    BorrowersUpdateResource borrowersUpdateResource = new BorrowersUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = borrowersUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(borrowersUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = borrowersUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(borrowersUpdateResource.getClass().getSuperclass().cast(borrowersUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(borrowersUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "collateralsAddResource":
                    CollateralsAddResource collateralsAddResource = new CollateralsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = collateralsAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(collateralsAddResource.getClass().cast(collateralsAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(collateralsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "collateralsUpdateResource":
                    CollateralsUpdateResource collateralsUpdateResource = new CollateralsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = collateralsUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(collateralsUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = collateralsUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(collateralsUpdateResource.getClass().getSuperclass().cast(collateralsUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(collateralsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "guarantorAddResource":
                    GuarantorAddResource guarantorAddResource = new GuarantorAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = guarantorAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(guarantorAddResource.getClass().cast(guarantorAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(guarantorAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "guarantorUpdateResource":
                    GuarantorUpdateResource guarantorUpdateResource = new GuarantorUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = guarantorUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(guarantorUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = guarantorUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(guarantorUpdateResource.getClass().getSuperclass().cast(guarantorUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(guarantorUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "lendingAccountDetailAddResource":
                    LendingAccountDetailAddResource lendingAccountDetailAddResource = new LendingAccountDetailAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = lendingAccountDetailAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(lendingAccountDetailAddResource.getClass().cast(lendingAccountDetailAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(lendingAccountDetailAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "lendingAccountDetailUpdateResource":
                    LendingAccountDetailUpdateResource lendingAccountDetailUpdateResource = new LendingAccountDetailUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = lendingAccountDetailUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(lendingAccountDetailUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = lendingAccountDetailUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(lendingAccountDetailUpdateResource.getClass().getSuperclass().cast(lendingAccountDetailUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(lendingAccountDetailUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                default:
                    return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);

            }
        } catch (Exception e) {
            SuccessAndErrorDetails successAndErrorDetailsResource = new SuccessAndErrorDetails();
            successAndErrorDetailsResource.setMessages(environment.getProperty(commonInternalServerError));
            successAndErrorDetailsResource.setDetails(e.getMessage());
            RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
            if (mapperEnum != null)
                successAndErrorDetailsResource.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
            LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsResource.getCode() + " " + successAndErrorDetailsResource.getMessages() + " " + successAndErrorDetailsResource.getDetails());
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @ExceptionHandler({ValidateRecordException.class})
    public ResponseEntity<Object> validateRecordException(ValidateRecordException ex, WebRequest request) {
        try {
            LoggerRequest.getInstance().logInfo(project + " " + ex.getField() + " " + ex.getMessage());
            ValidateResource typeValidation = new ValidateResource();
            Class validationDetailClass = typeValidation.getClass();
            Field sField = validationDetailClass.getDeclaredField(ex.getField());
            sField.setAccessible(true);
            sField.set(typeValidation, ex.getMessage());
            TenantHolder.clear();
            return new ResponseEntity<>(typeValidation, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            SuccessAndErrorDetails successAndErrorDetailsDto = new SuccessAndErrorDetails();
            successAndErrorDetailsDto.setMessages(environment.getProperty("common.internal-server-error"));
            successAndErrorDetailsDto.setDetails(e.getMessage());
            RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
            if (mapperEnum != null)
                successAndErrorDetailsDto.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
            TenantHolder.clear();
            LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsDto.getCode() + " " + successAndErrorDetailsDto.getMessages() + " " + successAndErrorDetailsDto.getDetails());
            return new ResponseEntity<>(successAndErrorDetailsDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        SuccessAndErrorDetails successAndErrorDetailsResource = new SuccessAndErrorDetails();
        successAndErrorDetailsResource.setMessages(environment.getProperty("common.invalid-url-pattern"));
        successAndErrorDetailsResource.setDetails(ex.getMessage());
        successAndErrorDetailsResource.setCode(ErrorCode.COMN00005);
        TenantHolder.clear();
        LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsResource.getCode() + " " + successAndErrorDetailsResource.getMessages() + " " + successAndErrorDetailsResource.getDetails());
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        SuccessAndErrorDetails successAndErrorDetailsResource = new SuccessAndErrorDetails();
        successAndErrorDetailsResource.setMessages(environment.getProperty("common.argument-type-mismatch"));
        successAndErrorDetailsResource.setDetails(ex.getMessage());
        successAndErrorDetailsResource.setCode(ErrorCode.COMN00006);
        TenantHolder.clear();
        LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsResource.getCode() + " " + successAndErrorDetailsResource.getMessages() + " " + successAndErrorDetailsResource.getDetails());
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserNotFound.class})
    public ResponseEntity<Object> userNotFoundException(UserNotFound ex, WebRequest request) {
        SuccessAndErrorDetails successAndErrorDetailsResource = new SuccessAndErrorDetails();
        successAndErrorDetailsResource.setMessages(environment.getProperty(userNotFound));
        successAndErrorDetailsResource.setCode(ErrorCode.COMN00007);
        LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsResource.getCode() + " " + successAndErrorDetailsResource.getMessages() + " " + successAndErrorDetailsResource.getDetails());
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({TenantNotFoundException.class})
    public ResponseEntity<Object> tenantNotFoundException(TenantNotFoundException ex, WebRequest request) {
        SuccessAndErrorDetails successAndErrorDetailsResource = new SuccessAndErrorDetails();
        successAndErrorDetailsResource.setMessages(environment.getProperty(tenantNotFound));
        RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
        if (mapperEnum != null)
            successAndErrorDetailsResource.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
        LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsResource.getCode() + " " + successAndErrorDetailsResource.getMessages() + " " + successAndErrorDetailsResource.getDetails());
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({CannotCreateTransactionException.class})
    public ResponseEntity<Object> cannotCreateTransactionException(CannotCreateTransactionException ex, WebRequest request) {
        SuccessAndErrorDetails successAndErrorDetailsResource = new SuccessAndErrorDetails();
        successAndErrorDetailsResource.setMessages(environment.getProperty(tenantNotFound));
        RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
        if (mapperEnum != null)
            successAndErrorDetailsResource.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
        TenantHolder.clear();
        LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsResource.getCode() + " " + successAndErrorDetailsResource.getMessages() + " " + successAndErrorDetailsResource.getDetails());
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler({ListRecordPrimitiveValidateException.class})
    public ResponseEntity<Object> listRecordValidateException(ListRecordPrimitiveValidateException ex, WebRequest request) {
        ListRecordValidateResource response = new ListRecordValidateResource();
        response.setPath(ex.getPath());
        response.setListName(ex.getListName());
        response.setIndex(ex.getIndex());
        response.setField(ex.getField());
        response.setMessage(ex.getMessage());
        TenantHolder.clear();
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler({PageableLengthException.class})
    public ResponseEntity<Object> pageableLengthException(PageableLengthException ex, WebRequest request) {
        SuccessAndErrorDetails successAndErrorDetailsResource = new SuccessAndErrorDetails();
        successAndErrorDetailsResource.setMessages(ex.getMessage());
        RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
        if (mapperEnum != null)
            successAndErrorDetailsResource.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
        TenantHolder.clear();
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) {
        if (ex.getMessage().contains("org.hibernate.QueryException: could not resolve property:")) {
            SuccessAndErrorDetails successAndErrorDetailsResource = new SuccessAndErrorDetails();
            successAndErrorDetailsResource.setMessages(environment.getProperty("invalid.sort"));
            TenantHolder.clear();
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            SuccessAndErrorDetails successAndErrorDetailsResource = new SuccessAndErrorDetails();
            successAndErrorDetailsResource.setMessages(environment.getProperty(commonInternalServerError));
            successAndErrorDetailsResource.setDetails(ex.getMessage());
            RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
            if (mapperEnum != null)
                successAndErrorDetailsResource.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
            TenantHolder.clear();
            LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsResource.getCode() + " " + successAndErrorDetailsResource.getMessages() + " " + successAndErrorDetailsResource.getDetails());
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler({WorkFlowException.class})
    public ResponseEntity<Object> workFlowException(WorkFlowException ex, WebRequest request) {
        SuccessAndErrorDetails successAndErrorDetailsResource = new SuccessAndErrorDetails();
        successAndErrorDetailsResource.setMessages(ex.getMessage());
        successAndErrorDetailsResource.setDetails(ex.getWorkflowType() + " WorkFlow");
        RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
        if (mapperEnum != null)
            successAndErrorDetailsResource.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
        LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsResource.getCode() + " " + successAndErrorDetailsResource.getMessages() + " " + successAndErrorDetailsResource.getDetails());
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({OtherException.class})
    public ResponseEntity<Object> otherException(OtherException ex, WebRequest request) {
        SuccessAndErrorDetails successAndErrorDetailsDataObject = new SuccessAndErrorDetails();
        successAndErrorDetailsDataObject.setMessages(ex.getMessage());
        RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
        if (mapperEnum != null)
            successAndErrorDetailsDataObject.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({InvalidServiceIdException.class})
    public ResponseEntity<Object> invalidServiceIdException(InvalidServiceIdException ex, WebRequest request) {
        ResidencyEligibilityUpdateResource residencyEligibilityUpdateResource = null;
        ProductGroupUpdateResource productGroupUpdateResource = null;
        ResidencyIncludeUpdateResource residencyIncludeUpdateResource = null;
        UpdateProductRequest addMainProductRequest = null;
        CommonUpdateResource commonUpdateResource = null;
        UpdateBaseRequest updateBaseRequest = null;
        CommonListUpdateResource commonListUpdateResource = null;
        ApplicationFrequencyUpdateResource applicationFrequencyUpdateResource = null;
        OtherFeeTypeUpdateResource otherFeeTypeUpdateResource = null;
        EligibilityUpdateResource eligibilityUpdateResource = null;
        FeeChargeUpdateResource feeChargeUpdateResource = null;
        MasterDefinitionUpdateResource masterDefinitionUpdateResource = null;
        UpdateProductSegmentRequest addProductSegmentRequest = null;
        EligibilityCurrencyUpdateResource eligibilityCurrencyUpdateResource = null;
        EligibilityCollateralUpdateResource eligibilityCollateralUpdateResource = null;
        RepaymentUpdateResource repaymentUpdateResource = null;
        AgeEligibilityUpdateResource ageEligibilityUpdateResource = null;
        InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = null;
        InterestTierBandUpdateResource interestTierBandUpdateResource = null;
        EligibilityOtherUpdateResource eligibilityOtherUpdateResource = null;
        EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = null;
        SubProductUpdateResource subProductUpdateResource = null;
        DisbursementConditionsUpdateResource disbursementConditionsUpdateResource = null;
        CoreProductUpdateResource coreProductUpdateResource = null;
        DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource = null;
        DocumentCheckListUpdateResource documentCheckListUpdateResource = null;
        InterestTemplateUpdateResource interestTemplateUpdateResource = null;
        PenalTierBandSetUpdateRequest penalTierBandSetUpdateRequest = null;
        PenalTierBandUpdateRequest penalTierBandUpdateRequest = null;
        PenalInterestTypeUpdateResource penalInterestTypeUpdateResource = null;
        AddBaseEligibilityRequest addBaseEligibilityRequest = null;
        RevolvingLoanCalculatorAddResource revolvingLoanCalculatorAddResource = null;
        FeeChargeCapUpdateResource feeChargeCapUpdateResource = null;
        EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = null;
        FeeChargeDetailsUpdateResource feeChargeDetailsUpdateResource = null;
        FixedLoanCalculatorAddResource fixedLoanCalculatorAddResource = null;
        PenalInterestUpdateResource penalInterestUpdateResource = null;
        EligibilityTemplateBranchUpdateResource eligibilityTemplateBranchAddResource = null;
        EligibilityTemplateIndustryUpdateResource eligibilityTemplateIndustryAddResource = null;
        EligibilityTemplateDisbursementUpdateResource eligibilityTemplateDisbursementAddResource = null;
        EligibilityTemplateLegalStructureUpdateResource eligibilityTemplateLegalStructureAddResource = null;
        TcStructuredDetailAddResource tcStructuredDetailAddResource = null;
        TaxProfileRequestResource taxProfileRequestResource = null;
        GuarantorAddResource guarantorAddResource = null;

        LoggerRequest.getInstance().logInfo(project + " " + ex.getServiceEntity() + " " + ex.getEntityPoint() + " " + ex.getMessage());
        switch (ex.getEntityPoint()) {
            case RESIDENCY_ELIGIBILITY:
                residencyEligibilityUpdateResource = new ResidencyEligibilityUpdateResource();
                residencyEligibilityUpdateResourceServiceIdException(residencyEligibilityUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case PRODUCT_GROUP:
                productGroupUpdateResource = new ProductGroupUpdateResource();
                productGroupAddResourceResourceServiceIdException(productGroupUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case RESIDENCY_INCLUDE:
                residencyIncludeUpdateResource = new ResidencyIncludeUpdateResource();
                residencyResidencyIncludeUpdateResourceServiceIdException(residencyIncludeUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case MAIN_PRODUCT:
                addMainProductRequest = new UpdateProductRequest();
                productRequestIncludeUpdateResourceServiceIdException(addMainProductRequest, ex.getServiceEntity(), ex.getMessage());
                break;

            case FEATURE_BENEFIT_ELIGIBILITY_TYPE:
                addBaseEligibilityRequest = new AddBaseEligibilityRequest();
                addBaseEligibilityRequestServiceIdException(addBaseEligibilityRequest, ex.getServiceEntity(), ex.getMessage());
                break;

            case REPAYMENT_FREQUENCY:
                commonUpdateResource = new CommonUpdateResource();
                commonUpdateResourceServiceIdException(commonUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case SEGMENT_LIST:
                updateBaseRequest = new UpdateBaseRequest();
                updateBaseRequestServiceIdException(updateBaseRequest, ex.getServiceEntity(), ex.getMessage());
                break;

            case COMMON_LIST_ITEM:
                commonListUpdateResource = new CommonListUpdateResource();
                commonListUpdateResourceIdException(commonListUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case APPLICATION_FREQUENCY:
                applicationFrequencyUpdateResource = new ApplicationFrequencyUpdateResource();
                applicationFrequencyUpdateResourceIdException(applicationFrequencyUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case OTHER_FEE_TYPE:
                otherFeeTypeUpdateResource = new OtherFeeTypeUpdateResource();
                otherFeeTypeUpdateResourceIdException(otherFeeTypeUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case ELIGIBILTY:
                eligibilityUpdateResource = new EligibilityUpdateResource();
                eligibilityUpdateResourceException(eligibilityUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case FEE_CHARGE:
                feeChargeUpdateResource = new FeeChargeUpdateResource();
                feeChargeUpdateResourceException(feeChargeUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case MASTER_DEFINITION:
                masterDefinitionUpdateResource = new MasterDefinitionUpdateResource();
                masterDefinitionUpdateResourceException(masterDefinitionUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case BRAND:
                commonUpdateResource = new CommonUpdateResource();
                commonUpdateResourceServiceIdException(commonUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case REPAYMENTAMOUNTTYPE:
                commonUpdateResource = new CommonUpdateResource();
                commonUpdateResourceServiceIdException(commonUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case PRODUCT_SEGMENT:
                addProductSegmentRequest = new UpdateProductSegmentRequest();
                productSegmentRequestIncludeUpdateResourceServiceIdException(addProductSegmentRequest, ex.getServiceEntity(), ex.getMessage());
                break;

            case ELIGIBILITY_CURRENCY:
                eligibilityCurrencyUpdateResource = new EligibilityCurrencyUpdateResource();
                eligibilityCurrencyUpdateResourceServiceIdException(eligibilityCurrencyUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case ELIGIBILITY_BRANCH:
                eligibilityTemplateBranchAddResource = new EligibilityTemplateBranchUpdateResource();
                eligibilityBranchUpdateResourceServiceIdException(eligibilityTemplateBranchAddResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case ELIGIBILITY_INDUSTRY:
                eligibilityTemplateIndustryAddResource = new EligibilityTemplateIndustryUpdateResource();
                eligibilityIndustryUpdateResourceServiceIdException(eligibilityTemplateIndustryAddResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case ELIGIBILITY_DISBURSMENT:
                eligibilityTemplateDisbursementAddResource = new EligibilityTemplateDisbursementUpdateResource();
                eligibilityDisbursementUpdateResourceServiceIdException(eligibilityTemplateDisbursementAddResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case ELIGIBILITY_LEGAL_STRUCTURE:
                eligibilityTemplateLegalStructureAddResource = new EligibilityTemplateLegalStructureUpdateResource();
                eligibilityLegalStructureUpdateResourceServiceIdException(eligibilityTemplateLegalStructureAddResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case ELIGIBILITY_COLLATERAL:
                eligibilityCollateralUpdateResource = new EligibilityCollateralUpdateResource();
                eligibilityCollateralUpdateResourceServiceIdException(eligibilityCollateralUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case SALES_ACCESS_CHANNEL:
                commonUpdateResource = new CommonUpdateResource();
                commonUpdateResourceServiceIdException(commonUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case REPAYMENT:
                repaymentUpdateResource = new RepaymentUpdateResource();
                repaymentUpdateResourceServiceIdException(repaymentUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case AGE_ELIGIBILITY:
                ageEligibilityUpdateResource = new AgeEligibilityUpdateResource();
                ageEligibilityUpdateResourceServiceIdException(ageEligibilityUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case INTEREST_TIER_BAND_SET:
                interestTierBandSetUpdateResource = new InterestTierBandSetUpdateResource();
                interestTierBandSetUpdateResourceServiceIdException(interestTierBandSetUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case INTEREST_TIER_BAND:
                interestTierBandUpdateResource = new InterestTierBandUpdateResource();
                interestTierBandUpdateResourceServiceIdException(interestTierBandUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case ELIGIBILITY_OTHER:
                eligibilityOtherUpdateResource = new EligibilityOtherUpdateResource();
                eligibilityOtherUpdateResourceServiceIdException(eligibilityOtherUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case ELIGIBILITY_RESIDENCY_ELIGIBILITY:
                eligibilityResidencyEligibilityUpdateResource = new EligibilityResidencyEligibilityUpdateResource();
                eligibilityResidencyEligibilityUpdateResourceServiceIdException(eligibilityResidencyEligibilityUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case SUB_PRODUCT:
                subProductUpdateResource = new SubProductUpdateResource();
                subProductUpdateResourceException(subProductUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case DISBURSEMENT_CONDITIONS:
                disbursementConditionsUpdateResource = new DisbursementConditionsUpdateResource();
                disbursementConditionsUpdateResourceException(disbursementConditionsUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case CORE_PRODUCT:
                coreProductUpdateResource = new CoreProductUpdateResource();
                coreProductUpdateResourceException(coreProductUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case DUE_DATE_DETAILS:
                dueDateTemplatesUpdateResource = new DueDateTemplatesUpdateResource();
                dueDateTemplatesUpdateResourceException(dueDateTemplatesUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case INTEREST_TEMPLATE:
                interestTemplateUpdateResource = new InterestTemplateUpdateResource();
                interestTemplateUpdateResourceException(interestTemplateUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case DOCUMENT_CHECKLIST:
                documentCheckListUpdateResource = new DocumentCheckListUpdateResource();
                documentCheckListUpdateResourceException(documentCheckListUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case PENAL_TIER_BAND_SET:
                penalTierBandSetUpdateRequest = new PenalTierBandSetUpdateRequest();
                penalTierBandSetUpdateRequestException(penalTierBandSetUpdateRequest, ex.getServiceEntity(), ex.getMessage());
                break;
            case PENAL_TIER_BAND:
                penalTierBandUpdateRequest = new PenalTierBandUpdateRequest();
                penalTierBandUpdateRequestException(penalTierBandUpdateRequest, ex.getServiceEntity(), ex.getMessage());
                break;
            case PENAL_INTEREST_TYPE:
                penalInterestTypeUpdateResource = new PenalInterestTypeUpdateResource();
                penalInterestTypeUpdateResourceException(penalInterestTypeUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case TC_REVOLVING_CALCULATION:
                revolvingLoanCalculatorAddResource = new RevolvingLoanCalculatorAddResource();
                revolvingLoanCalculatorAddResourceException(revolvingLoanCalculatorAddResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case FEE_CHARGE_CAP:
                feeChargeCapUpdateResource = new FeeChargeCapUpdateResource();
                feeChargeCapUpdateResourceException(feeChargeCapUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case ELIGIBILITY_CUSTOMER_TYPE:
                eligibilityCustomerTypeUpdateResource = new EligibilityCustomerTypeUpdateResource();
                eligibilityCustomerTypeUpdateResourceException(eligibilityCustomerTypeUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case FEE_CHARGE_DETAILS:
                feeChargeDetailsUpdateResource = new FeeChargeDetailsUpdateResource();
                feeChargeDetailsUpdateResourceResourceException(feeChargeDetailsUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case TC_FIXED_CAL:
                fixedLoanCalculatorAddResource = new FixedLoanCalculatorAddResource();
                fixedLoanCalculatorAddResourceException(fixedLoanCalculatorAddResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case PAY_MODES:
                commonUpdateResource = new CommonUpdateResource();
                commonUpdateResourceServiceIdException(commonUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case TAX_PROFILE:
            	taxProfileRequestResource = new TaxProfileRequestResource();
            	taxProfileRequestResourceServiceIdException(taxProfileRequestResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case PENAL_INTEREST_TEMPLATE:
                penalInterestUpdateResource = new PenalInterestUpdateResource();
                penalInterestUpdateResourceServiceIdException(penalInterestUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;
            case TC_STRUCTURED_CAL:
                tcStructuredDetailAddResource = new TcStructuredDetailAddResource();
                tcStructuredDetailAddResourceServiceIdException(tcStructuredDetailAddResource, ex.getServiceEntity(), ex.getMessage());
                break;

            default:
        }

        if (residencyEligibilityUpdateResource != null) {
            return new ResponseEntity<>(residencyEligibilityUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (residencyIncludeUpdateResource != null) {
            return new ResponseEntity<>(residencyIncludeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (commonUpdateResource != null) {
            return new ResponseEntity<>(commonUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (updateBaseRequest != null) {
            return new ResponseEntity<>(updateBaseRequest, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (commonListUpdateResource != null) {
            return new ResponseEntity<>(commonListUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (applicationFrequencyUpdateResource != null) {
            return new ResponseEntity<>(applicationFrequencyUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (otherFeeTypeUpdateResource != null) {
            return new ResponseEntity<>(otherFeeTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (eligibilityUpdateResource != null) {
            return new ResponseEntity<>(eligibilityUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (feeChargeUpdateResource != null) {
            return new ResponseEntity<>(feeChargeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (masterDefinitionUpdateResource != null) {
            return new ResponseEntity<>(masterDefinitionUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (productGroupUpdateResource != null) {
            return new ResponseEntity<>(productGroupUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (addMainProductRequest != null) {
            return new ResponseEntity<>(addMainProductRequest, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (addProductSegmentRequest != null) {
            return new ResponseEntity<>(addProductSegmentRequest, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (eligibilityCurrencyUpdateResource != null) {
            return new ResponseEntity<>(eligibilityCurrencyUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (eligibilityCollateralUpdateResource != null) {
            return new ResponseEntity<>(eligibilityCollateralUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (repaymentUpdateResource != null) {
            return new ResponseEntity<>(repaymentUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (ageEligibilityUpdateResource != null) {
            return new ResponseEntity<>(ageEligibilityUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (interestTierBandSetUpdateResource != null) {
            return new ResponseEntity<>(interestTierBandSetUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (eligibilityOtherUpdateResource != null) {
            return new ResponseEntity<>(eligibilityOtherUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (eligibilityResidencyEligibilityUpdateResource != null) {
            return new ResponseEntity<>(eligibilityResidencyEligibilityUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (eligibilityTemplateBranchAddResource != null) {
            return new ResponseEntity<>(eligibilityTemplateBranchAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (eligibilityTemplateIndustryAddResource != null) {
            return new ResponseEntity<>(eligibilityTemplateIndustryAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (eligibilityTemplateLegalStructureAddResource != null) {
            return new ResponseEntity<>(eligibilityTemplateLegalStructureAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (eligibilityTemplateDisbursementAddResource != null) {
            return new ResponseEntity<>(eligibilityTemplateDisbursementAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (subProductUpdateResource != null) {
            return new ResponseEntity<>(subProductUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (disbursementConditionsUpdateResource != null) {
            return new ResponseEntity<>(disbursementConditionsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (coreProductUpdateResource != null) {
            return new ResponseEntity<>(coreProductUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (dueDateTemplatesUpdateResource != null) {
            return new ResponseEntity<>(dueDateTemplatesUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (documentCheckListUpdateResource != null) {
            return new ResponseEntity<>(documentCheckListUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (interestTemplateUpdateResource != null) {
            return new ResponseEntity<>(interestTemplateUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (penalTierBandSetUpdateRequest != null) {
            return new ResponseEntity<>(penalTierBandSetUpdateRequest, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (penalTierBandUpdateRequest != null) {
            return new ResponseEntity<>(penalTierBandUpdateRequest, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (penalInterestTypeUpdateResource != null) {
            return new ResponseEntity<>(penalInterestTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (addBaseEligibilityRequest != null) {
            return new ResponseEntity<>(addBaseEligibilityRequest, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (revolvingLoanCalculatorAddResource != null) {
            return new ResponseEntity<>(revolvingLoanCalculatorAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (feeChargeCapUpdateResource != null) {
            return new ResponseEntity<>(feeChargeCapUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (eligibilityCustomerTypeUpdateResource != null) {
            return new ResponseEntity<>(eligibilityCustomerTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (feeChargeDetailsUpdateResource != null) {
            return new ResponseEntity<>(feeChargeDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (fixedLoanCalculatorAddResource != null) {
            return new ResponseEntity<>(fixedLoanCalculatorAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (penalInterestUpdateResource != null) {
            return new ResponseEntity<>(penalInterestUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (tcStructuredDetailAddResource != null) {
            return new ResponseEntity<>(tcStructuredDetailAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (taxProfileRequestResource != null) {
            return new ResponseEntity<>(taxProfileRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

	}

	private void taxProfileRequestResourceServiceIdException(TaxProfileRequestResource taxProfileRequestResource,
			ServiceEntity serviceEntity, String message) {
		switch (serviceEntity) {
		case PRODUCTCATEGORY:
			taxProfileRequestResource.setProductCategoryComnListId(message);
			break;
		case APPLICABLEACCTYPE:
			taxProfileRequestResource.setApplicableAccTypeComnListId(message);
			break;
		case DECLARATIONTYPE:
			taxProfileRequestResource.setDeclarationTypeComnListId(message);
			break;
		case CUSTSUBTYPENONIND:
			taxProfileRequestResource.setCustomerSubTypeNonIndividualId(message);
			break;
		case CUSTSUBTYPEIND:
			taxProfileRequestResource.setCustomerSubTypeIndividualId(message);
			break;
		case RESIDENCYTYPE:
			taxProfileRequestResource.setCustomerResidentTypeId(message);
			break;
		case APPPROD:
			taxProfileRequestResource.setApplicableProductId(message);
		case CUSTCATEGORYID:
			taxProfileRequestResource.setCustomerCategoryId(message);
			/*
			 * case CUSTCATEGORYCODE:
			 * taxProfileRequestResource.setCustomerCategoryCode(ex.getMessage());
			 */
		default:
		}
	}


    private void penalInterestUpdateResourceServiceIdException(PenalInterestUpdateResource penalInterestUpdateResource,
                                                               ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                penalInterestUpdateResource.setVersion(message);
                break;
            case CODE:
                penalInterestUpdateResource.setCode(message);
                break;
            default:
        }
    }


    private void feeChargeDetailsUpdateResourceResourceException(
            FeeChargeDetailsUpdateResource feeChargeDetailsUpdateResource, ServiceEntity serviceEntity,
            String message) {
        switch (serviceEntity) {
            case VERSION:
                feeChargeDetailsUpdateResource.setVersion(message);
                break;
            case CODE:
                feeChargeDetailsUpdateResource.setCode(message);
                break;
            case FEE_CATEGORY_ID:
                feeChargeDetailsUpdateResource.setFeeCategoryId(message);
                break;
            default:
        }
    }


    private void feeChargeCapUpdateResourceException(FeeChargeCapUpdateResource feeChargeCapUpdateResource,
                                                     ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                feeChargeCapUpdateResource.setVersion(message);
                break;
            case CODE:
                feeChargeCapUpdateResource.setCode(message);
                break;
            case PERIOD:
                feeChargeCapUpdateResource.setFeeCapPeriodId(message);
                break;
            default:
        }
    }


    private void revolvingLoanCalculatorAddResourceException(
            RevolvingLoanCalculatorAddResource revolvingLoanCalculatorAddResource, ServiceEntity serviceEntity,
            String message) {
        switch (serviceEntity) {
            case PRODUCT_ID:
                revolvingLoanCalculatorAddResource.setProductId(message);
                break;
            case FEE_CHARGE_ID:
                revolvingLoanCalculatorAddResource.getFixedChargesDetails().get(0).setChargeTypeId(message);
                break;
            default:
        }
    }


    private void penalInterestTypeUpdateResourceException(
            PenalInterestTypeUpdateResource penalInterestTypeUpdateResource, ServiceEntity serviceEntity,
            String message) {
        switch (serviceEntity) {
            case VERSION:
                penalInterestTypeUpdateResource.setVersion(message);
                break;
            case CODE:
                penalInterestTypeUpdateResource.setCode(message);
                break;
            default:
        }
    }


    private void penalTierBandUpdateRequestException(PenalTierBandUpdateRequest penalTierBandUpdateRequest,
                                                     ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                penalTierBandUpdateRequest.setVersion(message);
                break;
            case CODE:
                penalTierBandUpdateRequest.setCode(message);
                break;
            case LOAN_PR_INTEREST_RATE_TYPE_ID:
                penalTierBandUpdateRequest.setLoanPrInterestRateTypeId(message);
                break;
            default:
        }

    }


    private void penalTierBandSetUpdateRequestException(PenalTierBandSetUpdateRequest penalTierBandSetUpdateRequest,
                                                        ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                penalTierBandSetUpdateRequest.setVersion(message);
                break;
            case CODE:
                penalTierBandSetUpdateRequest.setCode(message);
                break;
            case PENAL_INTEREST_TYPE_ID:
                penalTierBandSetUpdateRequest.setPenalInterestTypeId(message);
                break;
            default:
        }

    }


    private void documentCheckListUpdateResourceException(
            DocumentCheckListUpdateResource documentCheckListUpdateResource, ServiceEntity serviceEntity,
            String message) {
        switch (serviceEntity) {
            case VERSION:
                documentCheckListUpdateResource.setVersion(message);
                break;
            case CODE:
                documentCheckListUpdateResource.setCode(message);
                break;
            case PRODUCT_ID:
                documentCheckListUpdateResource.setProductId(message);
                break;
            case SUB_PRODUCT_ID:
                documentCheckListUpdateResource.setSubProductId(message);
                break;
            case APPLICABLE_LEVEL_ID:
                documentCheckListUpdateResource.setApplicableLevelId(message);
                break;
            default:
        }
    }


    private void coreProductUpdateResourceException(CoreProductUpdateResource coreProductUpdateResource,
                                                    ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                coreProductUpdateResource.setVersion(message);
                break;
            case CODE:
                coreProductUpdateResource.setCode(message);
                break;
            default:
        }
    }


    private void disbursementConditionsUpdateResourceException(
            DisbursementConditionsUpdateResource disbursementConditionsUpdateResource, ServiceEntity serviceEntity,
            String message) {
        switch (serviceEntity) {
            case VERSION:
                disbursementConditionsUpdateResource.setVersion(message);
                break;
            case CODE:
                disbursementConditionsUpdateResource.setCode(message);
                break;
            default:
        }
    }


    private void ageEligibilityUpdateResourceServiceIdException(
            AgeEligibilityUpdateResource ageEligibilityUpdateResource, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                ageEligibilityUpdateResource.setVersion(message);
                break;
            case MINIMUM_AGE:
                ageEligibilityUpdateResource.setMinimumAge(message);
                break;
            case MAXIMUM_AGE:
                ageEligibilityUpdateResource.setMaximumAge(message);
                break;
            default:
        }

    }


    private void repaymentUpdateResourceServiceIdException(RepaymentUpdateResource repaymentUpdateResource,
                                                           ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                repaymentUpdateResource.setVersion(message);
                break;
            case CODE:
                repaymentUpdateResource.setCode(message);
                break;
            case REPAYMENT_TYPE_ID:
                repaymentUpdateResource.setRepaymentTypeId(message);
                break;
            case REPAYMENT_FREQUENCY_ID:
                repaymentUpdateResource.setRepaymentFrequencyId(message);
                break;
            case REPAYMENT_AMOUNT_TYPE_ID:
                repaymentUpdateResource.setRepaymentAmountTypeId(message);
                break;
            default:
        }

    }
    protected AddTaxProfileRequestResource addTaxProfileRequestResourceValidation(List<FieldError> fieldErrors) throws NoSuchFieldException, IllegalAccessException{
		Field sField=null;
		AddTaxProfileRequestResource addTaxProfileRequestResource=new AddTaxProfileRequestResource();
		for (FieldError error : fieldErrors) {
			LoggerRequest.getInstance().logInfo(project+ " " +error.getField()+" "+error.getDefaultMessage());
			if(error.getField().startsWith("taxProfileCreatedUser")) {
				 sField =  addTaxProfileRequestResource.getClass().getDeclaredField(error.getField());
				 sField.setAccessible(true);
				 sField.set(addTaxProfileRequestResource, error.getDefaultMessage());
			 }else {
				 sField =  addTaxProfileRequestResource.getClass().getSuperclass().getDeclaredField(error.getField());
				 sField.setAccessible(true);
				 sField.set(addTaxProfileRequestResource.getClass().getSuperclass().cast(addTaxProfileRequestResource), error.getDefaultMessage());
			 }
		 }
		return addTaxProfileRequestResource;
	}
	
	protected UpdateTaxProfileRequestResource updateTaxProfileRequestResourceValidation(List<FieldError> fieldErrors) throws NoSuchFieldException, IllegalAccessException{
		Field sField=null;
		UpdateTaxProfileRequestResource updateTaxProfileRequestResource=new UpdateTaxProfileRequestResource();
		for (FieldError error : fieldErrors) {
			LoggerRequest.getInstance().logInfo(project+ " " +error.getField()+" "+error.getDefaultMessage());
			if(error.getField().startsWith("modifiedUser")) {
				 sField =  updateTaxProfileRequestResource.getClass().getDeclaredField(error.getField());
				 sField.setAccessible(true);
				 sField.set(updateTaxProfileRequestResource, error.getDefaultMessage());
			 }else if(error.getField().startsWith("version")) {
					sField =  updateTaxProfileRequestResource.getClass().getDeclaredField(error.getField());
	                sField.setAccessible(true);
	                sField.set(updateTaxProfileRequestResource, error.getDefaultMessage());
	         }else {
				 sField =  updateTaxProfileRequestResource.getClass().getSuperclass().getDeclaredField(error.getField());
				 sField.setAccessible(true);
				 sField.set(updateTaxProfileRequestResource.getClass().getSuperclass().cast(updateTaxProfileRequestResource), error.getDefaultMessage());
			 }
		 }
		return updateTaxProfileRequestResource;
	}

    private void productRequestIncludeUpdateResourceServiceIdException(UpdateProductRequest addMainProductRequest,
                                                                       ServiceEntity serviceEntity, String message) {

        switch (serviceEntity) {


            case CODE:
                addMainProductRequest.setProductCode(message);
                break;
            case BRAND:
                addMainProductRequest.setBrandId(message);
                break;
            case VERSION:
                addMainProductRequest.setVersion(message);
                break;
            case PRODUCT_GROUP:
                addMainProductRequest.setProductGroupId(message);
                break;


            default:
        }

    }

    private void feeChargeUpdateResourceException(FeeChargeUpdateResource feeChargeUpdateResource,
                                                  ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                feeChargeUpdateResource.setVersion(message);
                break;
            case CODE:
                feeChargeUpdateResource.setCode(message);
                break;
            case FEE_CHARGE_TYPE_ID:
                feeChargeUpdateResource.setFeeChargeTypeId(message);
                break;
            default:
        }
    }

    private void masterDefinitionUpdateResourceException(MasterDefinitionUpdateResource masterDefinitionUpdateResource,
                                                         ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                masterDefinitionUpdateResource.setVersion(message);
                break;
            case CODE:
                masterDefinitionUpdateResource.setCode(message);
                break;
            default:
        }
    }

    private void eligibilityUpdateResourceException(EligibilityUpdateResource eligibilityUpdateResource,
                                                    ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                eligibilityUpdateResource.setVersion(message);
                break;
            case CODE:
                eligibilityUpdateResource.setCode(message);
                break;
            case AGE_ELIGIBILITY_ID:
                eligibilityUpdateResource.setAgeEligiId(message);
                break;
            default:
        }

    }

    private void subProductUpdateResourceException(SubProductUpdateResource subProductUpdateResource,
                                                   ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                subProductUpdateResource.setVersion(message);
                break;
            case CODE:
                subProductUpdateResource.setCode(message);
                break;
            case FEATURE_BENEFIT_ELIGIBILITY_ID:
                subProductUpdateResource.setFeatureBenifitTemplateId(message);
                break;
            case PRODUCT_ID:
                subProductUpdateResource.setProductId(message);
                break;
            case ELIGIBILITY_ID:
                subProductUpdateResource.setEligibilityId(message);
                break;
            case INTEREST_TEMPLATE_ID:
                subProductUpdateResource.setInterestTemplateId(message);
                break;
            case REPAYMENT_ID:
                subProductUpdateResource.setRepaymentId(message);
                break;
            case PREDECESSOR_ID:
                subProductUpdateResource.setPredecessorId(message);
                break;
            case PERIOD:
                subProductUpdateResource.setStateTenurePeriodId(message);
                break;
            case MARKETING_STATE_ID:
                subProductUpdateResource.setMarketingStateId(message);
                break;
            case COREPRODUCT_ID:     
            	subProductUpdateResource.setCoreProductId(message);
                break; 
            default:
        }

    }

    private void residencyEligibilityUpdateResourceServiceIdException(ResidencyEligibilityUpdateResource residencyEligibilityUpdateResource, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case CODE:
                residencyEligibilityUpdateResource.setCode(message);
                break;
            case RESIDENCYTYPE:
                residencyEligibilityUpdateResource.setResidencyTypeId(message);
                break;
            case VERSION:
                residencyEligibilityUpdateResource.setVersion(message);
                break;
            default:
        }
    }


    private void productGroupAddResourceResourceServiceIdException(ProductGroupUpdateResource productGroupUpdateResource, ServiceEntity serviceEntity, String message) {

        switch (serviceEntity) {
            case CODE:
                productGroupUpdateResource.setCode(message);
                break;

            case VERSION:
                productGroupUpdateResource.setVersion(message);
                break;
            default:
        }
    }


    private void residencyResidencyIncludeUpdateResourceServiceIdException(ResidencyIncludeUpdateResource residencyIncludeUpdateResource, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case COUNTRY:
                residencyIncludeUpdateResource.setCountryId(message);
                break;
            case COUNTRY_NAME:
                residencyIncludeUpdateResource.setCountryName(message);
                break;
            case VERSION:
                residencyIncludeUpdateResource.setVersion(message);
                break;
            default:
        }
    }


//	@ExceptionHandler({InvalidDetailListServiceIdException.class})
//	public ResponseEntity<Object> invalidDetailListServiceIdException(InvalidDetailListServiceIdException ex, WebRequest request) {
//		TenantHolder.clear();
//		LoggerRequest.getInstance().logInfo(project+ " "+ex.getServiceEntity()+" "+ex.getMessage());
//		if(ex.getEntityPoint().equals(EntityPoint.MAIN_PRODUCT)) {
//			AddProductRequest validateProductRequest= validateBusinessExpenseTypeAddResource(ex);
//			return new ResponseEntity<>(validateProductRequest, HttpStatus.UNPROCESSABLE_ENTITY);
//
//		}else {
//			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
//		}
//	}

    //	private AddProductRequest validateBusinessExpenseTypeAddResource(InvalidDetailListServiceIdException ex) {
//		AddProductRequest addProductRequest = new AddProductRequest();
//		List<AddProductSegmentRequest> addProductSegmentRequest=new ArrayList<>();
//		Integer index=ex.getIndex();
//		for(int i=0;i<=ex.getIndex();i++){
//			addProductSegmentRequest.add(i, new AddProductSegmentRequest());
//		}
//		LoggerRequest.getInstance().logInfo(project+ " " +ex.getServiceEntity()+" "+ex.getMessage());
//		switch(ex.getServiceEntity())
//        {
//	        case SEGMENT:
//	        	addProductSegmentRequest.get(index).setSegmentId(ex.getMessage());
//	            break;
//
//            default:
//
//        }
//		addProductRequest.setSegments(addProductSegmentRequest);
//		return addProductRequest;
//	}
    private void addBaseEligibilityRequestServiceIdException(AddBaseEligibilityRequest addBaseEligibilityRequest, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case PERIOD:
                addBaseEligibilityRequest.setPeriodId(message);
                break;
            case CODE:
                addBaseEligibilityRequest.setCode(message);
                break;
            default:
        }
    }

    private void commonUpdateResourceServiceIdException(CommonUpdateResource commonUpdateResource, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                commonUpdateResource.setVersion(message);
                break;
            case CODE:
                commonUpdateResource.setCode(message);
                break;
            default:
        }
    }

    private void updateBaseRequestServiceIdException(UpdateBaseRequest updateBaseRequest, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                updateBaseRequest.setVersion(message);
                break;
            case CODE:
                updateBaseRequest.setCode(message);
                break;
            default:
        }
    }

    private void commonListUpdateResourceIdException(CommonListUpdateResource commonListUpdateResource, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                commonListUpdateResource.setVersion(message);
                break;
            case CODE:
                commonListUpdateResource.setCode(message);
                break;
            default:
        }
    }

    private void applicationFrequencyUpdateResourceIdException(ApplicationFrequencyUpdateResource applicationFrequencyUpdateResource, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                applicationFrequencyUpdateResource.setVersion(message);
                break;
            case CODE:
                applicationFrequencyUpdateResource.setCode(message);
                break;
            case FREQUENCY:
                applicationFrequencyUpdateResource.setFrequencyTypeId(message);
                break;
            case UNIT:
                applicationFrequencyUpdateResource.setUnit(message);
                break;
            default:
        }
    }

    private void otherFeeTypeUpdateResourceIdException(OtherFeeTypeUpdateResource otherFeeTypeUpdateResource, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                otherFeeTypeUpdateResource.setVersion(message);
                break;
            case CODE:
                otherFeeTypeUpdateResource.setCode(message);
                break;
            case FEECATEGORYID:
                otherFeeTypeUpdateResource.setFeeCategoryId(message);
                break;
            case TRANSACTIONCODE:
                otherFeeTypeUpdateResource.setTransactionCodeId(message);
                break;
            case TRANSACTIONSUBCODE:
                otherFeeTypeUpdateResource.setTransactionSubCodeId(message);
                break;
            default:
        }
    }

    private void productSegmentRequestIncludeUpdateResourceServiceIdException(
            UpdateProductSegmentRequest updateProductSegmentRequest, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case PRODUCT_SEGMENT_ID:
                updateProductSegmentRequest.setProductSegmentId(message);
                break;
            case PRODUCT:
                updateProductSegmentRequest.setProductId(message);
                break;
            case SEGMENT:
                updateProductSegmentRequest.setSegmentId(message);
                break;

            case VERSION:
                updateProductSegmentRequest.setVersion(message);
                break;
            default:
        }

    }

    private void eligibilityCurrencyUpdateResourceServiceIdException(EligibilityCurrencyUpdateResource eligibilityCurrencyUpdateResource, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case CURRENCY:
                eligibilityCurrencyUpdateResource.setCurrencyId(message);
                break;
            default:
        }
    }

    private void eligibilityBranchUpdateResourceServiceIdException(
            EligibilityTemplateBranchAddResource eligibilityTemplateBranchAddResource, ServiceEntity serviceEntity,
            String message) {
        switch (serviceEntity) {
            case BRANCH_ID:
                eligibilityTemplateBranchAddResource.setBranchId(message);
                break;
            default:
        }
    }

    private void eligibilityIndustryUpdateResourceServiceIdException(
            EligibilityTemplateIndustryUpdateResource eligibilityTemplateIndustryAddResource, ServiceEntity serviceEntity,
            String message) {
        switch (serviceEntity) {
            case INDUSTRY:
                eligibilityTemplateIndustryAddResource.setIndustryId(message);
                break;
            case VERSION:
                eligibilityTemplateIndustryAddResource.setVersion(message);
                break;
            default:
        }
    }

    private void eligibilityDisbursementUpdateResourceServiceIdException(
            EligibilityTemplateDisbursementAddResource eligibilityTemplateDisbursementAddResource,
            ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case DISBURSETYPE:
                eligibilityTemplateDisbursementAddResource.setDisbursementId(message);
                break;
            default:
        }
    }

    private void eligibilityLegalStructureUpdateResourceServiceIdException(
            EligibilityTemplateLegalStructureAddResource eligibilityTemplateLegalStructureAddResource,
            ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case LEGALSTRUCTURE:
                eligibilityTemplateLegalStructureAddResource.setLegalStructureId(message);
                break;
            default:
        }
    }

    private void eligibilityCollateralUpdateResourceServiceIdException(EligibilityCollateralUpdateResource eligibilityCollateralUpdateResource, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case AESSET_TYPE:
                eligibilityCollateralUpdateResource.setAssetTypeId(message);
                break;
            default:
        }
    }

    private void interestTierBandSetUpdateResourceServiceIdException(InterestTierBandSetUpdateResource interestTierBandSetUpdateResource,
                                                                     ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                interestTierBandSetUpdateResource.setVersion(message);
                break;
            case CODE:
                interestTierBandSetUpdateResource.setCode(message);
                break;
            case TIER_BAND_METHOD_ID:
                interestTierBandSetUpdateResource.setTierBandMethodId(message);
                break;
            case CALCULATION_METHOD_ID:
                interestTierBandSetUpdateResource.setCalculateMethodId(message);
                break;
            default:
        }
    }

    private void interestTierBandUpdateResourceServiceIdException(InterestTierBandUpdateResource interestTierBandSetUpdateResource,
                                                                  ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                interestTierBandSetUpdateResource.setVersion(message);
                break;
            case CODE:
                interestTierBandSetUpdateResource.setCode(message);
                break;
            case TIRE_MIN_TERM_ID:
                interestTierBandSetUpdateResource.setTierValueMinTermId(message);
                break;
            case TIRE_MAX_TERM_ID:
                interestTierBandSetUpdateResource.setTierValueMaxTermId(message);
                break;
            case INT_RATE_ID:
                interestTierBandSetUpdateResource.setInterestRateTypeId(message);
                break;
            default:
        }
    }

    private void eligibilityOtherUpdateResourceServiceIdException(EligibilityOtherUpdateResource eligibilityOtherUpdateResource,
                                                                  ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                eligibilityOtherUpdateResource.setVersion(message);
                break;
            case OTHER_ELIGIBILITY_TYPE_ID:
                eligibilityOtherUpdateResource.setOtherEligibilityTypeId(message);
                break;
            default:
        }
    }

    private void eligibilityResidencyEligibilityUpdateResourceServiceIdException(EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource,
                                                                                 ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case VERSION:
                eligibilityResidencyEligibilityUpdateResource.setVersion(message);
                break;
            case ELIGIBILITY_ID:
                eligibilityResidencyEligibilityUpdateResource.setEligibilityId(message);
                break;
            case RESIDENCY_ELIGIBILITY_ID:
                eligibilityResidencyEligibilityUpdateResource.setResidencyEligibilityId(message);
                break;
            default:
        }
    }

    private void dueDateTemplatesUpdateResourceException(DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource,
                                                         ServiceEntity serviceEntity, String message) {

        List<DueDateTypeListResource> dueDateTypeListResource = new ArrayList<>();
        dueDateTypeListResource.add(new DueDateTypeListResource());

        switch (serviceEntity) {
            case VERSION:
                dueDateTypeListResource.get(0).setVersion(message);
                break;
            case ID:
                dueDateTypeListResource.get(0).setId(message);
                break;
            default:
        }

        dueDateTemplatesUpdateResource.setDueDateTypeList(dueDateTypeListResource);

    }

    private void interestTemplateUpdateResourceException(InterestTemplateUpdateResource interestTemplateUpdateResource,
                                                         ServiceEntity serviceEntity, String message) {

        switch (serviceEntity) {
            case VERSION:
                interestTemplateUpdateResource.setVersion(message);
                break;
            case CODE:
                interestTemplateUpdateResource.setCode(message);
                break;
            default:
        }

    }

    private void eligibilityCustomerTypeUpdateResourceException(
            EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource, ServiceEntity serviceEntity,
            String message) {
        switch (serviceEntity) {
            case ID:
                eligibilityCustomerTypeUpdateResource.setId(message);
                break;
            case ELIGIBILITY_ID:
                eligibilityCustomerTypeUpdateResource.setEligibilityId(message);
                break;
            case CUSTOMER_TYPE_ID:
                eligibilityCustomerTypeUpdateResource.setCustomerTypeId(message);
                break;
            case VERSION:
                eligibilityCustomerTypeUpdateResource.setVersion(message);
                break;
            default:
        }
    }


    private void fixedLoanCalculatorAddResourceException(
            FixedLoanCalculatorAddResource fixedLoanCalculatorAddResource, ServiceEntity serviceEntity,
            String message) {
        switch (serviceEntity) {
            case CURRENCY:
                fixedLoanCalculatorAddResource.setCurrencyId(message);
                break;
            case FEE_CATEGORY_ID:
                fixedLoanCalculatorAddResource.setRepaymentFrequencyId(message);
                break;
            default:
        }
    }

    private void tcStructuredDetailAddResourceServiceIdException(
            TcStructuredDetailAddResource tcStructuredDetailAddResource, ServiceEntity serviceEntity, String message) {

        switch (serviceEntity) {
            case CURRENCY:
                tcStructuredDetailAddResource.setCurrencyId(message);
                break;
            case PRODUCT_ID:
                tcStructuredDetailAddResource.setProductId(message);
                break;
            case SUB_PRODUCT_ID:
                tcStructuredDetailAddResource.setSubProductId(message);
                break;
            case REPAYMENT_FREQUENCY_ID:
                tcStructuredDetailAddResource.setRepaymentFrequencyId(message);
                break;
            default:
        }

    }

}



