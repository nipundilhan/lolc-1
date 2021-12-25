/*
F * Copyright (c) 2018. LOLC Technology Services Ltd.
 * Author: Ranjith Kodikara
 * Date: 12/12/18 10:45
 */

package com.fusionx.lending.origination.core;

import com.fusionx.lending.origination.ErrorCode;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.error.code.RequestMapperEnum;
import com.fusionx.lending.origination.exception.*;
import com.fusionx.lending.origination.mt.TenantHolder;
import com.fusionx.lending.origination.resource.*;
import com.fusionx.lending.origination.responsehandler.CommonResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Field;
import java.util.*;


/**
 * This will return the relevant object based on the caught exception
 *
 * @author ranjithk
 * @version 1.0
 * @since 2018-12-13
 */
@RestControllerAdvice
public class BaseResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger_bre = LoggerFactory.getLogger(BaseResponseEntityExceptionHandler.class);
    private String tenantNotFound = "common.tenant-not-found";
    private String commonError = "common.error";
    private String eventIdNotFound = "eventIdNotFound";
    private String commonInternalServerError = "common.internal-server-error";
    private String userNotFound = "common.user-not-found";
    private String project = "lending-origination";

    @Autowired
    private Environment environment;


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsDataObject.setMessages(environment.getProperty("common.invalid-url-pattern"));
        successAndErrorDetailsDataObject.setDetails(ex.getMessage());
        successAndErrorDetailsDataObject.setCode(ErrorCode.COMN00005);
        TenantHolder.clear();
        LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsDataObject.getCode() + " " + successAndErrorDetailsDataObject.getMessages() + " " + successAndErrorDetailsDataObject.getDetails());
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsDataObject.setMessages(environment.getProperty("common.argument-type-mismatch"));
        successAndErrorDetailsDataObject.setDetails(ex.getMessage());
        successAndErrorDetailsDataObject.setCode(ErrorCode.COMN00006);
        TenantHolder.clear();
        LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsDataObject.getCode() + " " + successAndErrorDetailsDataObject.getMessages() + " " + successAndErrorDetailsDataObject.getDetails());
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({TenantNotFoundException.class})
    public ResponseEntity<Object> tenantNotFoundException(TenantNotFoundException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsDataObject.setMessages(environment.getProperty(tenantNotFound));
        successAndErrorDetailsDataObject.setCode(ErrorCode.COMN00008);
        LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsDataObject.getCode() + " " + successAndErrorDetailsDataObject.getMessages() + " " + successAndErrorDetailsDataObject.getDetails());
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({UserNotFound.class})
    public ResponseEntity<Object> userNotFoundException(UserNotFound ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsResource.setMessages(environment.getProperty(userNotFound));
        successAndErrorDetailsResource.setCode(ErrorCode.COMN00007);
        LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsResource.getCode() + " " + successAndErrorDetailsResource.getMessages() + " " + successAndErrorDetailsResource.getDetails());
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({CannotCreateTransactionException.class})
    public ResponseEntity<Object> cannotCreateTransactionException(CannotCreateTransactionException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsDataObject.setMessages(environment.getProperty(tenantNotFound));
        successAndErrorDetailsDataObject.setCode(ErrorCode.COMN00008);
        TenantHolder.clear();
        LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsDataObject.getCode() + " " + successAndErrorDetailsDataObject.getMessages() + " " + successAndErrorDetailsDataObject.getDetails());
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({CodeUniqueException.class})
    public ResponseEntity<Object> codeUniqueException(CodeUniqueException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
        if (mapperEnum != null)
            successAndErrorDetailsDataObject.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
        successAndErrorDetailsDataObject.setMessages(environment.getProperty("common.code-duplicate"));
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({NoRecordFoundException.class})
    public ResponseEntity<Object> noRecordFoundException(NoRecordFoundException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
        if (mapperEnum != null)
            successAndErrorDetailsDataObject.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
        successAndErrorDetailsDataObject.setMessages(environment.getProperty("common.record-not-found"));
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({CodeCannotChangeException.class})
    public ResponseEntity<Object> codeCannotChangeException(CodeCannotChangeException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
        if (mapperEnum != null)
            successAndErrorDetailsDataObject.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
        successAndErrorDetailsDataObject.setMessages(environment.getProperty("common.code-cannot-change"));
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({OtherCommonException.class})
    public ResponseEntity<Object> otherCommonException(OtherCommonException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsDataObject.setMessages(ex.getMessage());
        RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
        if (mapperEnum != null)
            successAndErrorDetailsDataObject.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({OtherException.class})
    public ResponseEntity<Object> otherException(OtherException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsDataObject.setMessages(ex.getMessage());
        RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
        if (mapperEnum != null)
            successAndErrorDetailsDataObject.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({RentalCalculationException.class})
    public ResponseEntity<Object> rentalCalculationException(RentalCalculationException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsDataObject.setMessages(ex.getMessage());
        successAndErrorDetailsDataObject.setDetails(ex.getErrorDesc());
        RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
        if (mapperEnum != null)
            successAndErrorDetailsDataObject.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
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


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handle(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

        Map<String, String> errors = new HashMap<>();
        if (!violations.isEmpty()) {

            violations.forEach(
                    violation -> {
                        String fieldName = violation.getPropertyPath().toString();

                        int atPoint = fieldName.indexOf('.');
                        if (atPoint == 0) {
                            fieldName = fieldName.substring(atPoint);
                        } else {
                            fieldName = fieldName.substring(atPoint + 1);
                        }

                        String errorMessage = violation.getMessage();
                        errors.put(fieldName, errorMessage);
                    }
            );
        } else {
            String errorMessage = "ConstraintViolationException occured.";
            errors.put("message", errorMessage);
        }
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        try {
            TenantHolder.clear();
            Field sField = null;
            String fieldName = null;
            Integer index = null;
            Integer index1 = null;
            Integer index2 = null;
            Integer atPoint = null;
            String className = ex.getBindingResult().getObjectName();
            switch (className) {

                //*************************FX-5273*************************************
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
                        sField = commonListUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(commonListUpdateResource.getClass().cast(commonListUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(commonListUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

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
                            sField = commonUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(commonUpdateResource.getClass().cast(commonUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(commonUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

                case "userMappingDetailsAddResource":
                    UserMappingDetailsAddResource userMappingDetailsAddResource = new UserMappingDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = userMappingDetailsAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(userMappingDetailsAddResource.getClass().cast(userMappingDetailsAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(userMappingDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "userMappingDetailsUpdateResource":
                    UserMappingDetailsUpdateResource userMappingDetailsUpdateResource = new UserMappingDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = userMappingDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(userMappingDetailsUpdateResource.getClass().cast(userMappingDetailsUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(userMappingDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

                case "approvalGroupUserMappingDetailsAddResource":
                    ApprovalGroupUserMappingDetailsAddResource approvalGroupUserMappingDetailsAddResource = new ApprovalGroupUserMappingDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = approvalGroupUserMappingDetailsAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(approvalGroupUserMappingDetailsAddResource.getClass().cast(approvalGroupUserMappingDetailsAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(approvalGroupUserMappingDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "approvalGroupUserMappingDetailsUpdateResource":
                    ApprovalGroupUserMappingDetailsUpdateResource approvalGroupUserMappingDetailsUpdateResource = new ApprovalGroupUserMappingDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = approvalGroupUserMappingDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(approvalGroupUserMappingDetailsUpdateResource.getClass().cast(approvalGroupUserMappingDetailsUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(approvalGroupUserMappingDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

                case "financialStatementAddResource":
                    FinancialStatementAddResource financialStatementAddResource = new FinancialStatementAddResource();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("levels")) {
                            fieldName = fieldName.replace("levels", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (financialStatementAddResource.getLevels() == null
                                        || financialStatementAddResource.getLevels().isEmpty()) {
                                    financialStatementAddResource
                                            .setLevels(new ArrayList<FinancialStatementDetailsAddResource>());
                                    financialStatementAddResource.getLevels().add(i,
                                            new FinancialStatementDetailsAddResource());
                                } else {
                                    if ((financialStatementAddResource.getLevels().size() - 1) < i) {
                                        financialStatementAddResource.getLevels().add(i,
                                                new FinancialStatementDetailsAddResource());
                                    }
                                }
                            }

                            sField = financialStatementAddResource.getLevels().get(index).getClass()
                                    .getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(financialStatementAddResource.getLevels().get(index), error.getDefaultMessage());

                        } else {

                            LoggerRequest.getInstance()
                                    .logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                            sField = financialStatementAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(financialStatementAddResource.getClass().cast(financialStatementAddResource),
                                    error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(financialStatementAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "financialStatementUpdateResource":
                    FinancialStatementUpdateResource financialStatementUpdateResource = new FinancialStatementUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("levels")) {
                            fieldName = fieldName.replace("levels", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (financialStatementUpdateResource.getLevels() == null
                                        || financialStatementUpdateResource.getLevels().isEmpty()) {
                                    financialStatementUpdateResource
                                            .setLevels(new ArrayList<FinancialStatementDetailsUpdateResource>());
                                    financialStatementUpdateResource.getLevels().add(i,
                                            new FinancialStatementDetailsUpdateResource());
                                } else {
                                    if ((financialStatementUpdateResource.getLevels().size() - 1) < i) {
                                        financialStatementUpdateResource.getLevels().add(i,
                                                new FinancialStatementDetailsUpdateResource());
                                    }
                                }
                            }

                            sField = financialStatementUpdateResource.getLevels().get(index).getClass()
                                    .getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(financialStatementUpdateResource.getLevels().get(index), error.getDefaultMessage());

                        } else {
                            sField = financialStatementUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(financialStatementUpdateResource.getClass().cast(financialStatementUpdateResource),
                                    error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(financialStatementUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

                case "masterValuePaireAddResource":
                    MasterValuePaireAddResource masterValuePaireAddResource = new MasterValuePaireAddResource();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("valuePaireDetails")) {
                            fieldName = fieldName.replace("valuePaireDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (masterValuePaireAddResource.getValuePaireDetails() == null
                                        || masterValuePaireAddResource.getValuePaireDetails().isEmpty()) {
                                    masterValuePaireAddResource
                                            .setValuePaireDetails(new ArrayList<ValuePaireDetailAddResource>());
                                    masterValuePaireAddResource.getValuePaireDetails().add(i,
                                            new ValuePaireDetailAddResource());
                                } else {
                                    if ((masterValuePaireAddResource.getValuePaireDetails().size() - 1) < i) {
                                        masterValuePaireAddResource.getValuePaireDetails().add(i,
                                                new ValuePaireDetailAddResource());
                                    }
                                }
                            }

                            sField = masterValuePaireAddResource.getValuePaireDetails().get(index).getClass()
                                    .getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(masterValuePaireAddResource.getValuePaireDetails().get(index),
                                    error.getDefaultMessage());

                        } else {

                            LoggerRequest.getInstance()
                                    .logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                            sField = masterValuePaireAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(masterValuePaireAddResource.getClass().cast(masterValuePaireAddResource),
                                    error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(masterValuePaireAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "masterValuePaireUpdateResource":
                    MasterValuePaireUpdateResource masterValuePaireUpdateResource = new MasterValuePaireUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("valuePaireDetails")) {
                            fieldName = fieldName.replace("valuePaireDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (masterValuePaireUpdateResource.getValuePaireDetails() == null
                                        || masterValuePaireUpdateResource.getValuePaireDetails().isEmpty()) {
                                    masterValuePaireUpdateResource
                                            .setValuePaireDetails(new ArrayList<ValuePaireDetailUpdateResource>());
                                    masterValuePaireUpdateResource.getValuePaireDetails().add(i,
                                            new ValuePaireDetailUpdateResource());
                                } else {
                                    if ((masterValuePaireUpdateResource.getValuePaireDetails().size() - 1) < i) {
                                        masterValuePaireUpdateResource.getValuePaireDetails().add(i,
                                                new ValuePaireDetailUpdateResource());
                                    }
                                }
                            }

                            sField = masterValuePaireUpdateResource.getValuePaireDetails().get(index).getClass()
                                    .getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(masterValuePaireUpdateResource.getValuePaireDetails().get(index),
                                    error.getDefaultMessage());

                        } else {
                            sField = masterValuePaireUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(masterValuePaireUpdateResource.getClass().cast(masterValuePaireUpdateResource),
                                    error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(masterValuePaireUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

			case "referenceDetailsAddResource":
				ReferenceDetailsAddResource referenceDetailsAddResource = new ReferenceDetailsAddResource();

				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					fieldName = error.getField();
					if (fieldName.startsWith("contactDetails")) {
						fieldName = fieldName.replace("contactDetails", "");
						atPoint = fieldName.indexOf(']');
						index = Integer.parseInt(fieldName.substring(1, atPoint));
						fieldName = fieldName.substring(atPoint + 2);
						for (int i = 0; i <= index; i++) {
							if (referenceDetailsAddResource.getContactDetails() == null
									|| referenceDetailsAddResource.getContactDetails().isEmpty()) {
								referenceDetailsAddResource
										.setContactDetails(new ArrayList<ReferenceDetailsContactInfoAddResource>());
								referenceDetailsAddResource.getContactDetails().add(i,
										new ReferenceDetailsContactInfoAddResource());
							} else {
								if ((referenceDetailsAddResource.getContactDetails().size() - 1) < i) {
									referenceDetailsAddResource.getContactDetails().add(i,
											new ReferenceDetailsContactInfoAddResource());
								}
							}
						}

						sField = referenceDetailsAddResource.getContactDetails().get(index).getClass()
								.getDeclaredField(fieldName);
						sField.setAccessible(true);
						sField.set(referenceDetailsAddResource.getContactDetails().get(index),
								error.getDefaultMessage());

					} else {

						LoggerRequest.getInstance()
								.logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
						sField = referenceDetailsAddResource.getClass().getDeclaredField(error.getField());
						sField.setAccessible(true);
						sField.set(referenceDetailsAddResource.getClass().cast(referenceDetailsAddResource),
								error.getDefaultMessage());
					}
				}
				return new ResponseEntity<>(referenceDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

			case "referenceDetailsUpdateResource":
				ReferenceDetailsUpdateResource referenceDetailsUpdateResource = new ReferenceDetailsUpdateResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					fieldName = error.getField();
					if (fieldName.startsWith("contactDetails")) {
						fieldName = fieldName.replace("contactDetails", "");
						atPoint = fieldName.indexOf(']');
						index = Integer.parseInt(fieldName.substring(1, atPoint));
						fieldName = fieldName.substring(atPoint + 2);
						for (int i = 0; i <= index; i++) {
							if (referenceDetailsUpdateResource.getContactDetails() == null
									|| referenceDetailsUpdateResource.getContactDetails().isEmpty()) {
								referenceDetailsUpdateResource
										.setContactDetails(new ArrayList<ReferenceDetailsContactInfoUpdateResource>());
								referenceDetailsUpdateResource.getContactDetails().add(i,
										new ReferenceDetailsContactInfoUpdateResource());
							} else {
								if ((referenceDetailsUpdateResource.getContactDetails().size() - 1) < i) {
									referenceDetailsUpdateResource.getContactDetails().add(i,
											new ReferenceDetailsContactInfoUpdateResource());
								}
							}
						}

						sField = referenceDetailsUpdateResource.getContactDetails().get(index).getClass()
								.getDeclaredField(fieldName);
						sField.setAccessible(true);
						sField.set(referenceDetailsUpdateResource.getContactDetails().get(index),
								error.getDefaultMessage());

					} else {
						sField = referenceDetailsUpdateResource.getClass().getDeclaredField(error.getField());
						sField.setAccessible(true);
						sField.set(referenceDetailsUpdateResource.getClass().cast(referenceDetailsUpdateResource),
								error.getDefaultMessage());
					}
				}
				return new ResponseEntity<>(referenceDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

                case "bankStatementDetailsAddResource":
                    BankStatementDetailsAddResource bankStatementDetailsAddResource = new BankStatementDetailsAddResource();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("documents")) {
                            fieldName = fieldName.replace("document", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (bankStatementDetailsAddResource.getDocuments() == null
                                        || bankStatementDetailsAddResource.getDocuments().isEmpty()) {
                                    bankStatementDetailsAddResource
                                            .setDocuments(new ArrayList<BankStatementDocumentDetailsAddResource>());
                                    bankStatementDetailsAddResource.getDocuments().add(i,
                                            new BankStatementDocumentDetailsAddResource());
                                } else {
                                    if ((bankStatementDetailsAddResource.getDocuments().size() - 1) < i) {
                                        bankStatementDetailsAddResource.getDocuments().add(i,
                                                new BankStatementDocumentDetailsAddResource());
                                    }
                                }
                            }

                            sField = bankStatementDetailsAddResource.getDocuments().get(index).getClass()
                                    .getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(bankStatementDetailsAddResource.getDocuments().get(index),
                                    error.getDefaultMessage());

                        } else {
                            LoggerRequest.getInstance()
                                    .logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                            sField = bankStatementDetailsAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(bankStatementDetailsAddResource.getClass().cast(bankStatementDetailsAddResource),
                                    error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(bankStatementDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "bankStatementDetailsUpdateResource":
                    BankStatementDetailsUpdateResource bankStatementDetailsUpdateResource = new BankStatementDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("bankStatementDocumentDetailsAddResource")) {
                            fieldName = fieldName.replace("bankStatementDocumentDetailsAddResource", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (bankStatementDetailsUpdateResource.getDocuments() == null
                                        || bankStatementDetailsUpdateResource.getDocuments().isEmpty()) {
                                    bankStatementDetailsUpdateResource
                                            .setDocuments(new ArrayList<BankStatementDocumentDetailsAddResource>());
                                    bankStatementDetailsUpdateResource.getDocuments().add(i,
                                            new BankStatementDocumentDetailsAddResource());
                                } else {
                                    if ((bankStatementDetailsUpdateResource.getDocuments().size() - 1) < i) {
                                        bankStatementDetailsUpdateResource.getDocuments().add(i,
                                                new BankStatementDocumentDetailsAddResource());
                                    }
                                }
                            }

                            sField = bankStatementDetailsUpdateResource.getDocuments().get(index).getClass()
                                    .getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(bankStatementDetailsUpdateResource.getDocuments().get(index),
                                    error.getDefaultMessage());

                        } else {
                            LoggerRequest.getInstance()
                                    .logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                            sField = bankStatementDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(
                                    bankStatementDetailsUpdateResource.getClass().cast(bankStatementDetailsUpdateResource),
                                    error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(bankStatementDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

                case "financialStatementDetailsAddResource":
                    FinancialStatementDetailsAddResource financialStatementDetailsAddResource = new FinancialStatementDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = financialStatementDetailsAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(financialStatementDetailsAddResource.getClass().cast(financialStatementDetailsAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(financialStatementDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "financialStatementDetailsUpdateResource":
                    FinancialStatementDetailsUpdateResource financialStatementDetailsUpdateResource = new FinancialStatementDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = financialStatementDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(financialStatementDetailsUpdateResource.getClass().cast(financialStatementDetailsUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(financialStatementDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

//                case "referenceDetailsContactInfoAddResource":
//                    ReferenceDetailsContactInfoAddResource referenceDetailsContactInfoAddResource = new ReferenceDetailsContactInfoAddResource();
//                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
//                        sField = referenceDetailsContactInfoAddResource.getClass().getDeclaredField(error.getField());
//                        sField.setAccessible(true);
//                        sField.set(referenceDetailsContactInfoAddResource.getClass().cast(referenceDetailsContactInfoAddResource), error.getDefaultMessage());
//                    }
//                    return new ResponseEntity<>(referenceDetailsContactInfoAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
//
//                case "referenceDetailsContactInfoUpdateResource":
//                    ReferenceDetailsContactInfoUpdateResource referenceDetailsContactInfoUpdateResource = new ReferenceDetailsContactInfoUpdateResource();
//                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
//                        sField = referenceDetailsContactInfoUpdateResource.getClass().getDeclaredField(error.getField());
//                        sField.setAccessible(true);
//                        sField.set(referenceDetailsContactInfoUpdateResource.getClass().cast(referenceDetailsContactInfoUpdateResource), error.getDefaultMessage());
//                    }
//                    return new ResponseEntity<>(referenceDetailsContactInfoUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

                case "checkListItemAddResource":
                    CheckListItemAddResource checkListItemAddResource = new CheckListItemAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = checkListItemAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(checkListItemAddResource.getClass().cast(checkListItemAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(checkListItemAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "checkListItemUpdateResource":
                    CheckListItemUpdateResource checkListItemUpdateResource = new CheckListItemUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = checkListItemUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(checkListItemUpdateResource.getClass().cast(checkListItemUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(checkListItemUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

                case "checkListTemplateAddResource":
                    CheckListTemplateAddResource checkListTemplateAddResource = new CheckListTemplateAddResource();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("checkListItem")) {
                            fieldName = fieldName.replace("checkListItem", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (checkListTemplateAddResource.getCheckListItem() == null || checkListTemplateAddResource.getCheckListItem().isEmpty()) {
                                    checkListTemplateAddResource.setCheckListItem(new ArrayList<CheckListTemplateDetailsAddResource>());
                                    checkListTemplateAddResource.getCheckListItem().add(i, new CheckListTemplateDetailsAddResource());
                                } else {
                                    if ((checkListTemplateAddResource.getCheckListItem().size() - 1) < i) {
                                        checkListTemplateAddResource.getCheckListItem().add(i, new CheckListTemplateDetailsAddResource());
                                    }
                                }
                            }

                            sField = checkListTemplateAddResource.getCheckListItem().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(checkListTemplateAddResource.getCheckListItem().get(index), error.getDefaultMessage());

                        } else {
                            LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                            sField = checkListTemplateAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(checkListTemplateAddResource.getClass().cast(checkListTemplateAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(checkListTemplateAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "checkListTemplateUpdateResource":
                    CheckListTemplateUpdateResource checkListTemplateUpdateResource = new CheckListTemplateUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        sField = checkListTemplateUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(checkListTemplateUpdateResource, error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(checkListTemplateUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

                case "approvalCategoryDetailsAddResource":
                    ApprovalCategoryDetailsAddResource approvalCategoryDetailsAddResource = new ApprovalCategoryDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = approvalCategoryDetailsAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(approvalCategoryDetailsAddResource.getClass().cast(approvalCategoryDetailsAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(approvalCategoryDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "approvalCategoryDetailsUpdateResource":
                    ApprovalCategoryDetailsUpdateResource approvalCategoryDetailsUpdateResource = new ApprovalCategoryDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = approvalCategoryDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(approvalCategoryDetailsUpdateResource.getClass().cast(approvalCategoryDetailsUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(approvalCategoryDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

                case "approvalCategoryLevelDetailsAddResource":
                    ApprovalCategoryLevelDetailsAddResource approvalCategoryLevelDetailsAddResource = new ApprovalCategoryLevelDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = approvalCategoryLevelDetailsAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(approvalCategoryLevelDetailsAddResource.getClass().cast(approvalCategoryLevelDetailsAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(approvalCategoryLevelDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "approvalCategoryLevelDetailsUpdateResource":
                    ApprovalCategoryLevelDetailsUpdateResource approvalCategoryLevelDetailsUpdateResource = new ApprovalCategoryLevelDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = approvalCategoryLevelDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(approvalCategoryLevelDetailsUpdateResource.getClass().cast(approvalCategoryLevelDetailsUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(approvalCategoryLevelDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

                case "approvalCategoryLevelsAddResource":
                    ApprovalCategoryLevelsAddResource approvalCategoryLevelsAddResource = new ApprovalCategoryLevelsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = approvalCategoryLevelsAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(approvalCategoryLevelsAddResource.getClass().cast(approvalCategoryLevelsAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(approvalCategoryLevelsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "approvalCategoryLevelsUpdateResource":
                    ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource = new ApprovalCategoryLevelsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = approvalCategoryLevelsUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(approvalCategoryLevelsUpdateResource.getClass().cast(approvalCategoryLevelsUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(approvalCategoryLevelsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**********************************************************************

                case "riskRatingLevelAddResource":
                    RiskRatingLevelAddResource riskRatingLevelAddResource = new RiskRatingLevelAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = riskRatingLevelAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(riskRatingLevelAddResource.getClass().cast(riskRatingLevelAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(riskRatingLevelAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "riskRatingLevelDetailsAddResource":
                    RiskRatingLevelDetailsAddResource riskRatingLevelDetailsAddResource = new RiskRatingLevelDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = riskRatingLevelDetailsAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(riskRatingLevelDetailsAddResource.getClass().cast(riskRatingLevelDetailsAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(riskRatingLevelDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);


                //**************************************************************added by ravishika for FX-6419,FX-6418*******************************************************
                case "approvalLevelAddResource":
                    ApprovalLevelAddResource approvalLevelAddResource = new ApprovalLevelAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = approvalLevelAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(approvalLevelAddResource.getClass().cast(approvalLevelAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(approvalLevelAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "approvalLevelUpdateResource":
                    ApprovalLevelUpdateResource approvalLevelUpdateResource = new ApprovalLevelUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = approvalLevelUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(approvalLevelUpdateResource.getClass().cast(approvalLevelUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(approvalLevelUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "productCategoryAddResource":
                    ProductCategoryAddResource productCategoryAddResource = new ProductCategoryAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = productCategoryAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(productCategoryAddResource.getClass().cast(productCategoryAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(productCategoryAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "productCategoryUpdateResource":
                    ProductCategoryUpdateResource productCategoryUpdateResource = new ProductCategoryUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = productCategoryUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(productCategoryUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = productCategoryUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(productCategoryUpdateResource.getClass().getSuperclass().cast(productCategoryUpdateResource), error.getDefaultMessage());
                        }
                    }

                    return new ResponseEntity<>(productCategoryUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "applicantBlackListApproveRejectResource":
                    ApplicantBlackListApproveRejectResource applicantBlackListApproveRejectResource = new ApplicantBlackListApproveRejectResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = applicantBlackListApproveRejectResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(applicantBlackListApproveRejectResource.getClass().cast(applicantBlackListApproveRejectResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(applicantBlackListApproveRejectResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //**************************************************************ended by ravishika for FX-6419,FX-6418*******************************************************


                //*************************FX-5269*************************************
                case "businessTypeAddResource":
                    BusinessTypeAddResource businessTypeAddResource = new BusinessTypeAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = businessTypeAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(businessTypeAddResource.getClass().cast(businessTypeAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(businessTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessTypeUpdateResource":
                    BusinessTypeUpdateResource businessTypeUpdateResource = new BusinessTypeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = businessTypeUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(businessTypeUpdateResource.getClass().cast(businessTypeUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(businessTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                //**********************************************************************
                case "houseHoldExpenseCategoryAddResource":
                    HouseHoldExpenseCategoryAddResource houseHoldExpenseCategoryAddResource = new HouseHoldExpenseCategoryAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = houseHoldExpenseCategoryAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(houseHoldExpenseCategoryAddResource.getClass().cast(houseHoldExpenseCategoryAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(houseHoldExpenseCategoryAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "houseHoldExpenseCategoryUpdateResource":
                    HouseHoldExpenseCategoryUpdateResource houseHoldExpenseCategoryUpdateResource = new HouseHoldExpenseCategoryUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = houseHoldExpenseCategoryUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(houseHoldExpenseCategoryUpdateResource.getClass().cast(houseHoldExpenseCategoryUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(houseHoldExpenseCategoryUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "otherDetailsAddRequestResource":
                    OtherDetailsAddRequestResource otherDetailsAddRequestResource = new OtherDetailsAddRequestResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = otherDetailsAddRequestResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(otherDetailsAddRequestResource.getClass().cast(otherDetailsAddRequestResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(otherDetailsAddRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "otherDetailsUpdateRequestResource":
                    OtherDetailsUpdateRequestResource otherDetailsUpdateRequestResource = new OtherDetailsUpdateRequestResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = otherDetailsUpdateRequestResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(otherDetailsUpdateRequestResource.getClass().cast(otherDetailsUpdateRequestResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(otherDetailsUpdateRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "disbursementAddRequestResource":
                    DisbursementAddRequestResource disbursementAddRequestResource = new DisbursementAddRequestResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("disbursementDetails")) {
                            fieldName = fieldName.replace("disbursementDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (disbursementAddRequestResource.getDisbursementDetails() == null || disbursementAddRequestResource.getDisbursementDetails().isEmpty()) {
                                    disbursementAddRequestResource.setDisbursementDetails(new ArrayList<DisbursementDetailsRequestResource>());
                                    disbursementAddRequestResource.getDisbursementDetails().add(i, new DisbursementDetailsRequestResource());
                                } else {
                                    if ((disbursementAddRequestResource.getDisbursementDetails().size() - 1) < i) {
                                        disbursementAddRequestResource.getDisbursementDetails().add(i, new DisbursementDetailsRequestResource());
                                    }
                                }
                            }
                            sField = disbursementAddRequestResource.getDisbursementDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(disbursementAddRequestResource.getDisbursementDetails().get(index), error.getDefaultMessage());
                        } else {
                            sField = disbursementAddRequestResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(disbursementAddRequestResource.getClass().cast(disbursementAddRequestResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(disbursementAddRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "leadInfoStagingAddRequestResource":
                    LeadInfoStagingAddRequestResource leadInfoStagingAddRequestResource = new LeadInfoStagingAddRequestResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = leadInfoStagingAddRequestResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(leadInfoStagingAddRequestResource.getClass().cast(leadInfoStagingAddRequestResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(leadInfoStagingAddRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "leadInfoStagingUpdateRequestResource":
                    LeadInfoStagingUpdateRequestResource leadInfoStagingUpdateRequestResource = new LeadInfoStagingUpdateRequestResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = leadInfoStagingUpdateRequestResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(leadInfoStagingUpdateRequestResource.getClass().cast(leadInfoStagingUpdateRequestResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(leadInfoStagingUpdateRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //*************************FX-5270*************************************
                case "expenseTypeAddResource":
                    ExpenseTypeAddResource expenseTypeAddResource = new ExpenseTypeAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = expenseTypeAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(expenseTypeAddResource.getClass().cast(expenseTypeAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(expenseTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "expenseTypeUpdateResource":
                    ExpenseTypeUpdateResource expenseTypeUpdateResource = new ExpenseTypeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = expenseTypeUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(expenseTypeUpdateResource.getClass().cast(expenseTypeUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(expenseTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                //**********************************************************************
                //*************************FX-5271*************************************
                case "businessExpenseTypeAddResource":
                    BusinessExpenseTypeAddResource businessExpenseTypeAddResource = new BusinessExpenseTypeAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("expenseTypesList")) {
                            fieldName = fieldName.replace("expenseTypesList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessExpenseTypeAddResource.getExpenseTypesList() == null || businessExpenseTypeAddResource.getExpenseTypesList().isEmpty()) {
                                    businessExpenseTypeAddResource.setExpenseTypesList(new ArrayList<BusinessExpenseTypeDetailResource>());
                                    businessExpenseTypeAddResource.getExpenseTypesList().add(i, new BusinessExpenseTypeDetailResource());
                                } else {
                                    if ((businessExpenseTypeAddResource.getExpenseTypesList().size() - 1) < i) {
                                        businessExpenseTypeAddResource.getExpenseTypesList().add(i, new BusinessExpenseTypeDetailResource());
                                    }
                                }
                            }
                            sField = businessExpenseTypeAddResource.getExpenseTypesList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessExpenseTypeAddResource.getExpenseTypesList().get(index), error.getDefaultMessage());
                        } else {
                            sField = businessExpenseTypeAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessExpenseTypeAddResource.getClass().cast(businessExpenseTypeAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(businessExpenseTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessExpenseTypeUpdateResource":
                    BusinessExpenseTypeUpdateResource businessExpenseTypeUpdateResource = new BusinessExpenseTypeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = businessExpenseTypeUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(businessExpenseTypeUpdateResource.getClass().cast(businessExpenseTypeUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(businessExpenseTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                //**********************************************************************
                //*************************FX-5272*************************************
                case "otherIncomeTypeAddResource":
                    OtherIncomeTypeAddResource otherIncomeTypeAddResource = new OtherIncomeTypeAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = otherIncomeTypeAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(otherIncomeTypeAddResource.getClass().cast(otherIncomeTypeAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(otherIncomeTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "otherIncomeTypeUpdateResource":
                    OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource = new OtherIncomeTypeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = otherIncomeTypeUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(otherIncomeTypeUpdateResource.getClass().cast(otherIncomeTypeUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(otherIncomeTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                //**********************************************************************

                case "facilityCalculationRequestResource":
                    FacilityCalculationRequestResource facilityCalculationRequestResource = new FacilityCalculationRequestResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("facilityStructures")) {
                            fieldName = fieldName.replace("facilityStructures", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (facilityCalculationRequestResource.getFacilityStructures() == null || facilityCalculationRequestResource.getFacilityStructures().isEmpty()) {
                                    facilityCalculationRequestResource.setFacilityStructures(new ArrayList<FacilityStructureRequestResource>());
                                    facilityCalculationRequestResource.getFacilityStructures().add(i, new FacilityStructureRequestResource());
                                } else {
                                    if ((facilityCalculationRequestResource.getFacilityStructures().size() - 1) < i) {
                                        facilityCalculationRequestResource.getFacilityStructures().add(i, new FacilityStructureRequestResource());
                                    }
                                }
                            }
                            if (fieldName.startsWith("taxes")) {
                                fieldName = fieldName.replace("taxes", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {
                                    if (facilityCalculationRequestResource.getFacilityStructures().get(index).getTaxes() == null || facilityCalculationRequestResource.getFacilityStructures().get(index).getTaxes().isEmpty()) {
                                        facilityCalculationRequestResource.getFacilityStructures().get(index).setTaxes(new ArrayList<FacilityCalculationTaxRequestResource>());
                                        facilityCalculationRequestResource.getFacilityStructures().get(index).getTaxes().add(i, new FacilityCalculationTaxRequestResource());
                                    } else {
                                        if ((facilityCalculationRequestResource.getFacilityStructures().get(index).getTaxes().size() - 1) < i) {
                                            facilityCalculationRequestResource.getFacilityStructures().get(index).getTaxes().add(i, new FacilityCalculationTaxRequestResource());
                                        }
                                    }
                                }
                                sField = facilityCalculationRequestResource.getFacilityStructures().get(index).getTaxes().get(index1).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(facilityCalculationRequestResource.getFacilityStructures().get(index).getTaxes().get(index1), error.getDefaultMessage());
                            } else if (fieldName.startsWith("fixedCharges")) {
                                fieldName = fieldName.replace("fixedCharges", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {
                                    if (facilityCalculationRequestResource.getFacilityStructures().get(index).getFixedCharges() == null || facilityCalculationRequestResource.getFacilityStructures().get(index).getFixedCharges().isEmpty()) {
                                        facilityCalculationRequestResource.getFacilityStructures().get(index).setFixedCharges(new ArrayList<FacilityCalculationFixedChargesRequestResource>());
                                        facilityCalculationRequestResource.getFacilityStructures().get(index).getFixedCharges().add(i, new FacilityCalculationFixedChargesRequestResource());
                                    } else {
                                        if ((facilityCalculationRequestResource.getFacilityStructures().get(index).getFixedCharges().size() - 1) < i) {
                                            facilityCalculationRequestResource.getFacilityStructures().get(index).getFixedCharges().add(i, new FacilityCalculationFixedChargesRequestResource());
                                        }
                                    }
                                }
                                sField = facilityCalculationRequestResource.getFacilityStructures().get(index).getFixedCharges().get(index1).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(facilityCalculationRequestResource.getFacilityStructures().get(index).getFixedCharges().get(index1), error.getDefaultMessage());
                            } else if (fieldName.startsWith("optionalCharges")) {
                                fieldName = fieldName.replace("optionalCharges", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {
                                    if (facilityCalculationRequestResource.getFacilityStructures().get(index).getOptionalCharges() == null || facilityCalculationRequestResource.getFacilityStructures().get(index).getOptionalCharges().isEmpty()) {
                                        facilityCalculationRequestResource.getFacilityStructures().get(index).setOptionalCharges(new ArrayList<FacilityCalculationOptionalChargesRequestResource>());
                                        facilityCalculationRequestResource.getFacilityStructures().get(index).getOptionalCharges().add(i, new FacilityCalculationOptionalChargesRequestResource());
                                    } else {
                                        if ((facilityCalculationRequestResource.getFacilityStructures().get(index).getOptionalCharges().size() - 1) < i) {
                                            facilityCalculationRequestResource.getFacilityStructures().get(index).getOptionalCharges().add(i, new FacilityCalculationOptionalChargesRequestResource());
                                        }
                                    }
                                }
                                sField = facilityCalculationRequestResource.getFacilityStructures().get(index).getOptionalCharges().get(index1).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(facilityCalculationRequestResource.getFacilityStructures().get(index).getOptionalCharges().get(index1), error.getDefaultMessage());
                            } else if (fieldName.startsWith("periodicCharges")) {
                                fieldName = fieldName.replace("periodicCharges", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {
                                    if (facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges() == null || facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges().isEmpty()) {
                                        facilityCalculationRequestResource.getFacilityStructures().get(index).setPeriodicCharges(new ArrayList<FacilityCalculationPeriodicChargesRequestResource>());
                                        facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges().add(i, new FacilityCalculationPeriodicChargesRequestResource());
                                    } else {
                                        if ((facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges().size() - 1) < i) {
                                            facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges().add(i, new FacilityCalculationPeriodicChargesRequestResource());
                                        }
                                    }
                                }
                                if (fieldName.startsWith("chargeDetails")) {
                                    fieldName = fieldName.replace("chargeDetails", "");
                                    atPoint = fieldName.indexOf(']');
                                    index2 = Integer.parseInt(fieldName.substring(1, atPoint));
                                    fieldName = fieldName.substring(atPoint + 2);
                                    for (int i = 0; i <= index1; i++) {
                                        if (facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges().get(index1).getChargeDetails() == null || facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges().get(index1).getChargeDetails().isEmpty()) {
                                            facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges().get(index1).setChargeDetails(new ArrayList<FacilityCalculationPeriodicChargesDetailRequestResource>());
                                            facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges().get(index1).getChargeDetails().add(i, new FacilityCalculationPeriodicChargesDetailRequestResource());
                                        } else {
                                            if ((facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges().get(index1).getChargeDetails().size() - 1) < i) {
                                                facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges().get(index1).getChargeDetails().add(i, new FacilityCalculationPeriodicChargesDetailRequestResource());
                                            }
                                        }
                                    }
                                    sField = facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges().get(index1).getChargeDetails().get(index2).getClass().getDeclaredField(fieldName);
                                    sField.setAccessible(true);
                                    sField.set(facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges().get(index1).getChargeDetails().get(index2), error.getDefaultMessage());
                                } else {
                                    sField = facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges().get(index1).getClass().getDeclaredField(fieldName);
                                    sField.setAccessible(true);
                                    sField.set(facilityCalculationRequestResource.getFacilityStructures().get(index).getPeriodicCharges().get(index1), error.getDefaultMessage());
                                }
                            } else {
                                sField = facilityCalculationRequestResource.getFacilityStructures().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(facilityCalculationRequestResource.getFacilityStructures().get(index), error.getDefaultMessage());
                            }
                        } else if (fieldName.startsWith("paymentStructures")) {
                            fieldName = fieldName.replace("paymentStructures", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (facilityCalculationRequestResource.getPaymentStructures() == null || facilityCalculationRequestResource.getPaymentStructures().isEmpty()) {
                                    facilityCalculationRequestResource.setPaymentStructures(new ArrayList<FacilityDetailRequestResource>());
                                    facilityCalculationRequestResource.getPaymentStructures().add(i, new FacilityDetailRequestResource());
                                } else {
                                    if ((facilityCalculationRequestResource.getPaymentStructures().size() - 1) < i) {
                                        facilityCalculationRequestResource.getPaymentStructures().add(i, new FacilityDetailRequestResource());
                                    }
                                }
                            }
                            sField = facilityCalculationRequestResource.getPaymentStructures().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(facilityCalculationRequestResource.getPaymentStructures().get(index), error.getDefaultMessage());
                        } else if (fieldName.startsWith("taxes")) {
                            fieldName = fieldName.replace("taxes", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (facilityCalculationRequestResource.getTaxes() == null || facilityCalculationRequestResource.getTaxes().isEmpty()) {
                                    facilityCalculationRequestResource.setTaxes(new ArrayList<FacilityCalculationTaxRequestResource>());
                                    facilityCalculationRequestResource.getTaxes().add(i, new FacilityCalculationTaxRequestResource());
                                } else {
                                    if ((facilityCalculationRequestResource.getTaxes().size() - 1) < i) {
                                        facilityCalculationRequestResource.getTaxes().add(i, new FacilityCalculationTaxRequestResource());
                                    }
                                }
                            }
                            sField = facilityCalculationRequestResource.getTaxes().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(facilityCalculationRequestResource.getTaxes().get(index), error.getDefaultMessage());
                        } else if (fieldName.startsWith("fixedCharges")) {
                            fieldName = fieldName.replace("fixedCharges", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (facilityCalculationRequestResource.getFixedCharges() == null || facilityCalculationRequestResource.getFixedCharges().isEmpty()) {
                                    facilityCalculationRequestResource.setFixedCharges(new ArrayList<FacilityCalculationFixedChargesRequestResource>());
                                    facilityCalculationRequestResource.getFixedCharges().add(i, new FacilityCalculationFixedChargesRequestResource());
                                } else {
                                    if ((facilityCalculationRequestResource.getFixedCharges().size() - 1) < i) {
                                        facilityCalculationRequestResource.getFixedCharges().add(i, new FacilityCalculationFixedChargesRequestResource());
                                    }
                                }
                            }
                            sField = facilityCalculationRequestResource.getFixedCharges().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(facilityCalculationRequestResource.getFixedCharges().get(index), error.getDefaultMessage());
                        } else if (fieldName.startsWith("optionalCharges")) {
                            fieldName = fieldName.replace("optionalCharges", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (facilityCalculationRequestResource.getOptionalCharges() == null || facilityCalculationRequestResource.getOptionalCharges().isEmpty()) {
                                    facilityCalculationRequestResource.setOptionalCharges(new ArrayList<FacilityCalculationOptionalChargesRequestResource>());
                                    facilityCalculationRequestResource.getOptionalCharges().add(i, new FacilityCalculationOptionalChargesRequestResource());
                                } else {
                                    if ((facilityCalculationRequestResource.getOptionalCharges().size() - 1) < i) {
                                        facilityCalculationRequestResource.getOptionalCharges().add(i, new FacilityCalculationOptionalChargesRequestResource());
                                    }
                                }
                            }
                            sField = facilityCalculationRequestResource.getOptionalCharges().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(facilityCalculationRequestResource.getOptionalCharges().get(index), error.getDefaultMessage());
                        } else if (fieldName.startsWith("periodicCharges")) {
                            fieldName = fieldName.replace("periodicCharges", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (facilityCalculationRequestResource.getPeriodicCharges() == null || facilityCalculationRequestResource.getPeriodicCharges().isEmpty()) {
                                    facilityCalculationRequestResource.setPeriodicCharges(new ArrayList<FacilityCalculationPeriodicChargesRequestResource>());
                                    facilityCalculationRequestResource.getPeriodicCharges().add(i, new FacilityCalculationPeriodicChargesRequestResource());
                                } else {
                                    if ((facilityCalculationRequestResource.getPeriodicCharges().size() - 1) < i) {
                                        facilityCalculationRequestResource.getPeriodicCharges().add(i, new FacilityCalculationPeriodicChargesRequestResource());
                                    }
                                }
                            }
                            if (fieldName.startsWith("chargeDetails")) {
                                fieldName = fieldName.replace("chargeDetails", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {
                                    if (facilityCalculationRequestResource.getPeriodicCharges().get(index).getChargeDetails() == null || facilityCalculationRequestResource.getPeriodicCharges().get(index).getChargeDetails().isEmpty()) {
                                        facilityCalculationRequestResource.getPeriodicCharges().get(index).setChargeDetails(new ArrayList<FacilityCalculationPeriodicChargesDetailRequestResource>());
                                        facilityCalculationRequestResource.getPeriodicCharges().get(index).getChargeDetails().add(i, new FacilityCalculationPeriodicChargesDetailRequestResource());
                                    } else {
                                        if ((facilityCalculationRequestResource.getPeriodicCharges().get(index).getChargeDetails().size() - 1) < i) {
                                            facilityCalculationRequestResource.getPeriodicCharges().get(index).getChargeDetails().add(i, new FacilityCalculationPeriodicChargesDetailRequestResource());
                                        }
                                    }
                                }
                                sField = facilityCalculationRequestResource.getPeriodicCharges().get(index).getChargeDetails().get(index1).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(facilityCalculationRequestResource.getPeriodicCharges().get(index).getChargeDetails().get(index1), error.getDefaultMessage());
                            } else {
                                sField = facilityCalculationRequestResource.getPeriodicCharges().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(facilityCalculationRequestResource.getPeriodicCharges().get(index), error.getDefaultMessage());
                            }
                        } else {
                            sField = facilityCalculationRequestResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(facilityCalculationRequestResource.getClass().cast(facilityCalculationRequestResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(facilityCalculationRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "expenceTypeCultivationCategoryAddResource":
                    ExpenceTypeCultivationCategoryAddResource expenceTypeCultivationCategoryAddResource = new ExpenceTypeCultivationCategoryAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("expenseTypes")) {
                            fieldName = fieldName.replace("expenseTypes", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (expenceTypeCultivationCategoryAddResource.getExpenseTypes() == null || expenceTypeCultivationCategoryAddResource.getExpenseTypes().isEmpty()) {
                                    expenceTypeCultivationCategoryAddResource.setExpenseTypes(new ArrayList<ExpenseTypeListResource>());
                                    expenceTypeCultivationCategoryAddResource.getExpenseTypes().add(i, new ExpenseTypeListResource());
                                } else {
                                    if ((expenceTypeCultivationCategoryAddResource.getExpenseTypes().size() - 1) < i) {
                                        expenceTypeCultivationCategoryAddResource.getExpenseTypes().add(i, new ExpenseTypeListResource());
                                    }
                                }
                            }
                            sField = expenceTypeCultivationCategoryAddResource.getExpenseTypes().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(expenceTypeCultivationCategoryAddResource.getExpenseTypes().get(index), error.getDefaultMessage());
                        } else {
                            sField = expenceTypeCultivationCategoryAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(expenceTypeCultivationCategoryAddResource.getClass().cast(expenceTypeCultivationCategoryAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(expenceTypeCultivationCategoryAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "expenceTypeCultivationCategoryUpdateResource":
                    ExpenceTypeCultivationCategoryUpdateResource expenceTypeCultivationCategoryUpdateResource = new ExpenceTypeCultivationCategoryUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("expenseTypes")) {
                            fieldName = fieldName.replace("expenseTypes", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (expenceTypeCultivationCategoryUpdateResource.getExpenseTypes() == null || expenceTypeCultivationCategoryUpdateResource.getExpenseTypes().isEmpty()) {
                                    expenceTypeCultivationCategoryUpdateResource.setExpenseTypes(new ArrayList<ExpenseTypeListResource>());
                                    expenceTypeCultivationCategoryUpdateResource.getExpenseTypes().add(i, new ExpenseTypeListResource());
                                } else {
                                    if ((expenceTypeCultivationCategoryUpdateResource.getExpenseTypes().size() - 1) < i) {
                                        expenceTypeCultivationCategoryUpdateResource.getExpenseTypes().add(i, new ExpenseTypeListResource());
                                    }
                                }
                            }
                            sField = expenceTypeCultivationCategoryUpdateResource.getExpenseTypes().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(expenceTypeCultivationCategoryUpdateResource.getExpenseTypes().get(index), error.getDefaultMessage());
                        } else {
                            sField = expenceTypeCultivationCategoryUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(expenceTypeCultivationCategoryUpdateResource.getClass().cast(expenceTypeCultivationCategoryUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(expenceTypeCultivationCategoryUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                /***************************************************************Added by Ravishika for FX-6418, FX-6136********************************************************************************************************/
                case "expenseTypeHouseholdExpenseCategoriesAddResource":
                    ExpenseTypeHouseholdExpenseCategoriesAddResource expenseTypeHouseholdExpenseCategoriesAddResource = new ExpenseTypeHouseholdExpenseCategoriesAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("expenseTypes")) {
                            fieldName = fieldName.replace("expenseTypes", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (expenseTypeHouseholdExpenseCategoriesAddResource.getExpenseTypes() == null || expenseTypeHouseholdExpenseCategoriesAddResource.getExpenseTypes().isEmpty()) {
                                    expenseTypeHouseholdExpenseCategoriesAddResource.setExpenseTypes(new ArrayList<ExpenseTypeListResource>());
                                    expenseTypeHouseholdExpenseCategoriesAddResource.getExpenseTypes().add(i, new ExpenseTypeListResource());
                                } else {
                                    if ((expenseTypeHouseholdExpenseCategoriesAddResource.getExpenseTypes().size() - 1) < i) {
                                        expenseTypeHouseholdExpenseCategoriesAddResource.getExpenseTypes().add(i, new ExpenseTypeListResource());
                                    }
                                }
                            }
                            sField = expenseTypeHouseholdExpenseCategoriesAddResource.getExpenseTypes().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(expenseTypeHouseholdExpenseCategoriesAddResource.getExpenseTypes().get(index), error.getDefaultMessage());
                        } else {
                            sField = expenseTypeHouseholdExpenseCategoriesAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(expenseTypeHouseholdExpenseCategoriesAddResource.getClass().cast(expenseTypeHouseholdExpenseCategoriesAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(expenseTypeHouseholdExpenseCategoriesAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "expenseTypeHouseholdExpenseCategoriesUpdateResource":
                    ExpenseTypeHouseholdExpenseCategoriesUpdateResource expenseTypeHouseholdExpenseCategoriesUpdateResource = new ExpenseTypeHouseholdExpenseCategoriesUpdateResource();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());

                        fieldName = error.getField();
                        if (fieldName.startsWith("expenseTypes")) {
                            fieldName = fieldName.replace("expenseTypes", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (expenseTypeHouseholdExpenseCategoriesUpdateResource.getExpenseTypes() == null || expenseTypeHouseholdExpenseCategoriesUpdateResource.getExpenseTypes().isEmpty()) {
                                    expenseTypeHouseholdExpenseCategoriesUpdateResource.setExpenseTypes(new ArrayList<ExpenseTypeListResource>());
                                    expenseTypeHouseholdExpenseCategoriesUpdateResource.getExpenseTypes().add(i, new ExpenseTypeListResource());
                                } else {
                                    if ((expenseTypeHouseholdExpenseCategoriesUpdateResource.getExpenseTypes().size() - 1) < i) {
                                        expenseTypeHouseholdExpenseCategoriesUpdateResource.getExpenseTypes().add(i, new ExpenseTypeListResource());
                                    }
                                }
                            }

                            sField = expenseTypeHouseholdExpenseCategoriesUpdateResource.getExpenseTypes().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(expenseTypeHouseholdExpenseCategoriesUpdateResource.getExpenseTypes().get(index), error.getDefaultMessage());
                        } else {
                            sField = expenseTypeHouseholdExpenseCategoriesUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(expenseTypeHouseholdExpenseCategoriesUpdateResource.getClass().cast(expenseTypeHouseholdExpenseCategoriesUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(expenseTypeHouseholdExpenseCategoriesUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "productCategoryProductMapAddResource":
                    ProductCategoryProductMapAddResource productCategoryProductMapAddResource = new ProductCategoryProductMapAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("products")) {
                            fieldName = fieldName.replace("products", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (productCategoryProductMapAddResource.getProducts() == null || productCategoryProductMapAddResource.getProducts().isEmpty()) {
                                    productCategoryProductMapAddResource.setProducts(new ArrayList<ProductRequestResource>());
                                    productCategoryProductMapAddResource.getProducts().add(i, new ProductRequestResource());
                                } else {
                                    if ((productCategoryProductMapAddResource.getProducts().size() - 1) < i) {
                                        productCategoryProductMapAddResource.getProducts().add(i, new ProductRequestResource());
                                    }
                                }
                            }
                            sField = productCategoryProductMapAddResource.getProducts().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(productCategoryProductMapAddResource.getProducts().get(index), error.getDefaultMessage());
                        } else {
                            sField = productCategoryProductMapAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(productCategoryProductMapAddResource.getClass().cast(productCategoryProductMapAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(productCategoryProductMapAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "productCategoryProductMapUpdateResource":
                    ProductCategoryProductMapUpdateResource productCategoryProductMapUpdateResource = new ProductCategoryProductMapUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("products")) {
                            fieldName = fieldName.replace("products", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (productCategoryProductMapUpdateResource.getProducts() == null || productCategoryProductMapUpdateResource.getProducts().isEmpty()) {
                                    productCategoryProductMapUpdateResource.setProducts(new ArrayList<ProductRequestResource>());
                                    productCategoryProductMapUpdateResource.getProducts().add(i, new ProductRequestResource());
                                } else {
                                    if ((productCategoryProductMapUpdateResource.getProducts().size() - 1) < i) {
                                        productCategoryProductMapUpdateResource.getProducts().add(i, new ProductRequestResource());
                                    }
                                }
                            }
                            sField = productCategoryProductMapUpdateResource.getProducts().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(productCategoryProductMapUpdateResource.getProducts().get(index), error.getDefaultMessage());
                        } else {
                            sField = productCategoryProductMapUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(productCategoryProductMapUpdateResource.getClass().cast(productCategoryProductMapUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(productCategoryProductMapUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                /***************************************************************Ended by Ravishika for FX-6418, FX-6136********************************************************************************************************/

                /**
                 * @author Senitha
                 * ADDED FOR FX-6157 ON 21-04-2021 ********************************************************************/
                case "guarantorDetailAddResource":
                    GuarantorDetailAddResource guarantorDetailAddResource = new GuarantorDetailAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("assets")) {
                            fieldName = fieldName.replace("assets", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {

                                if (guarantorDetailAddResource.getAssets() == null || guarantorDetailAddResource.getAssets().isEmpty()) {
                                    guarantorDetailAddResource.setAssets(new ArrayList<NetWorthAssetAddResource>());
                                    guarantorDetailAddResource.getAssets().add(i, new NetWorthAssetAddResource());
                                } else {
                                    if ((guarantorDetailAddResource.getAssets().size() - 1) < i) {
                                        guarantorDetailAddResource.getAssets().add(i, new NetWorthAssetAddResource());
                                    }
                                }
                            }
                            sField = guarantorDetailAddResource.getAssets().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(guarantorDetailAddResource.getAssets().get(index), error.getDefaultMessage());
                        } else if (fieldName.startsWith("liabilities")) {
                            fieldName = fieldName.replace("liabilities", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {

                                if (guarantorDetailAddResource.getLiabilities() == null || guarantorDetailAddResource.getLiabilities().isEmpty()) {
                                    guarantorDetailAddResource.setLiabilities(new ArrayList<NetWorthLiabilityAddResource>());
                                    guarantorDetailAddResource.getLiabilities().add(i, new NetWorthLiabilityAddResource());
                                } else {
                                    if ((guarantorDetailAddResource.getLiabilities().size() - 1) < i) {
                                        guarantorDetailAddResource.getLiabilities().add(i, new NetWorthLiabilityAddResource());
                                    }
                                }
                            }
                            sField = guarantorDetailAddResource.getLiabilities().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(guarantorDetailAddResource.getLiabilities().get(index), error.getDefaultMessage());
                        } else {
                            sField = guarantorDetailAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(guarantorDetailAddResource.getClass().cast(guarantorDetailAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(guarantorDetailAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "guarantorDetailUpdateResource":
                    GuarantorDetailUpdateResource guarantorDetailUpdateResource = new GuarantorDetailUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("assets")) {
                            fieldName = fieldName.replace("assets", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {

                                if (guarantorDetailUpdateResource.getAssets() == null || guarantorDetailUpdateResource.getAssets().isEmpty()) {
                                    guarantorDetailUpdateResource.setAssets(new ArrayList<NetWorthAssetUpdateResource>());
                                    guarantorDetailUpdateResource.getAssets().add(i, new NetWorthAssetUpdateResource());
                                } else {
                                    if ((guarantorDetailUpdateResource.getAssets().size() - 1) < i) {
                                        guarantorDetailUpdateResource.getAssets().add(i, new NetWorthAssetUpdateResource());
                                    }
                                }
                            }
                            sField = guarantorDetailUpdateResource.getAssets().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(guarantorDetailUpdateResource.getAssets().get(index), error.getDefaultMessage());
                        } else if (fieldName.startsWith("liabilities")) {
                            fieldName = fieldName.replace("liabilities", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {

                                if (guarantorDetailUpdateResource.getLiabilities() == null || guarantorDetailUpdateResource.getLiabilities().isEmpty()) {
                                    guarantorDetailUpdateResource.setLiabilities(new ArrayList<NetWorthLiabilityUpdateResource>());
                                    guarantorDetailUpdateResource.getLiabilities().add(i, new NetWorthLiabilityUpdateResource());
                                } else {
                                    if ((guarantorDetailUpdateResource.getLiabilities().size() - 1) < i) {
                                        guarantorDetailUpdateResource.getLiabilities().add(i, new NetWorthLiabilityUpdateResource());
                                    }
                                }
                            }
                            sField = guarantorDetailUpdateResource.getLiabilities().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(guarantorDetailUpdateResource.getLiabilities().get(index), error.getDefaultMessage());
                        } else {
                            sField = guarantorDetailUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(guarantorDetailUpdateResource.getClass().cast(guarantorDetailUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(guarantorDetailUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                /**
                 * @author Senitha
                 * ENDED FOR FX-6157 ON 21-04-2021 ********************************************************************/


                case "daLimitAddResource":
                    DaLimitAddResource daLimitAddResource = new DaLimitAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = daLimitAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(daLimitAddResource.getClass().cast(daLimitAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(daLimitAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "daLimitUpdateResource":
                    DaLimitUpdateResource daLimitUpdateResource = new DaLimitUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = daLimitUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(daLimitUpdateResource.getClass().cast(daLimitUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(daLimitUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "userDaLevelMapAddResource":
                    UserDaLevelMapAddResource userDaLevelMapAddResource = new UserDaLevelMapAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = userDaLevelMapAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(userDaLevelMapAddResource.getClass().cast(userDaLevelMapAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(userDaLevelMapAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "userDaLevelMapUpdateResource":
                    UserDaLevelMapUpdateResource userDaLevelMapUpdateResource = new UserDaLevelMapUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = userDaLevelMapUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(userDaLevelMapUpdateResource.getClass().cast(userDaLevelMapUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(userDaLevelMapUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
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
                        sField = businessCenterProductMapAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(businessCenterProductMapAddResource.getClass().cast(businessCenterProductMapAddResource), error.getDefaultMessage());

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
                            sField = businessCenterProductMapUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessCenterProductMapUpdateResource.getClass().getSuperclass().cast(businessCenterProductMapUpdateResource), error.getDefaultMessage());

                        }
                    }
                    return new ResponseEntity<>(businessCenterProductMapUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "businessCenterEmpMapAddResource":
                    BusinessCenterEmpMapAddResource businessCenterEmpMapAddResource = new BusinessCenterEmpMapAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
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
                        sField = businessCenterEmpMapAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(businessCenterEmpMapAddResource.getClass().cast(businessCenterEmpMapAddResource), error.getDefaultMessage());
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
                            sField = businessCenterEmpMapUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessCenterEmpMapUpdateResource.getClass().getSuperclass().cast(businessCenterEmpMapUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(businessCenterEmpMapUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "approvalCategoryAddResource":
                    ApprovalCategoryAddResource approvalCategoryAddResource = new ApprovalCategoryAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        sField = approvalCategoryAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(approvalCategoryAddResource.getClass().cast(approvalCategoryAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(approvalCategoryAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "approvalCategoryUpdateResource":
                    ApprovalCategoryUpdateResource approvalCategoryUpdateResource = new ApprovalCategoryUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = approvalCategoryUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(approvalCategoryUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = approvalCategoryUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(approvalCategoryUpdateResource.getClass().getSuperclass().cast(approvalCategoryUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(approvalCategoryUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);


                case "approvalCatProductMapAddResource":
                    ApprovalCatProductMapAddResource approvalCatProductMapAddResource = new ApprovalCatProductMapAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("products")) {
                            fieldName = fieldName.replace("products", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (approvalCatProductMapAddResource.getProducts() == null || approvalCatProductMapAddResource.getProducts().isEmpty()) {
                                    approvalCatProductMapAddResource.setProducts(new ArrayList<ApprovalCatProductMapAddProductResource>());
                                    approvalCatProductMapAddResource.getProducts().add(i, new ApprovalCatProductMapAddProductResource());
                                } else {
                                    if ((approvalCatProductMapAddResource.getProducts().size() - 1) < i) {
                                        approvalCatProductMapAddResource.getProducts().add(i, new ApprovalCatProductMapAddProductResource());
                                    }
                                }
                            }
                            sField = approvalCatProductMapAddResource.getProducts().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(approvalCatProductMapAddResource.getProducts().get(index), error.getDefaultMessage());
                        } else {
                            sField = approvalCatProductMapAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(approvalCatProductMapAddResource.getClass().cast(approvalCatProductMapAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(approvalCatProductMapAddResource, HttpStatus.UNPROCESSABLE_ENTITY);


                case "approvalCatProductMapUpdateResource":
                    ApprovalCatProductMapUpdateResource approvalCatProductMapUpdateResource = new ApprovalCatProductMapUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = approvalCatProductMapUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(approvalCatProductMapUpdateResource.getClass().cast(approvalCatProductMapUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(approvalCatProductMapUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);


                case "delegationAuthorityGroupAddResource":
                    DelegationAuthorityGroupAddResource delegationAuthorityGroupAddResource = new DelegationAuthorityGroupAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        sField = delegationAuthorityGroupAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(delegationAuthorityGroupAddResource.getClass().cast(delegationAuthorityGroupAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(delegationAuthorityGroupAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "delegationAuthorityGroupUpdateResource":
                    DelegationAuthorityGroupUpdateResource delegationAuthorityGroupUpdateResource = new DelegationAuthorityGroupUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = delegationAuthorityGroupUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(delegationAuthorityGroupUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = delegationAuthorityGroupUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(delegationAuthorityGroupUpdateResource.getClass().getSuperclass().cast(delegationAuthorityGroupUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(delegationAuthorityGroupUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                /**
                 * @author Senitha
                 * ADDED FOR FX-6506 ON 01-06-2021 ********************************************************************/
                case "mobileNotebookAddResource":
                    MobileNotebookAddResource mobileNotebookAddResource = new MobileNotebookAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = mobileNotebookAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(mobileNotebookAddResource.getClass().cast(mobileNotebookAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(mobileNotebookAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "mobileNotebookUpdateResource":
                    MobileNotebookUpdateResource mobileNotebookUpdateResource = new MobileNotebookUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = mobileNotebookUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(mobileNotebookUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = mobileNotebookUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(mobileNotebookUpdateResource.getClass().getSuperclass().cast(mobileNotebookUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(mobileNotebookUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "mobileNotebookDeleteResource":
                    MobileNotebookDeleteResource mobileNotebookDeleteResource = new MobileNotebookDeleteResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = mobileNotebookDeleteResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(mobileNotebookDeleteResource.getClass().cast(mobileNotebookDeleteResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(mobileNotebookDeleteResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "addToLeadResource":
                    AddToLeadResource addToLeadResource = new AddToLeadResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = addToLeadResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(addToLeadResource.getClass().cast(addToLeadResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(addToLeadResource, HttpStatus.UNPROCESSABLE_ENTITY);
                /**
                 * @author Senitha
                 * ENDED FOR FX-6506 ON 01-06-2021 ********************************************************************/

                case "customerIncomeExpenseRequestResource":
                    CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource = new CustomerIncomeExpenseRequestResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("businessIncomeExpenses")) {
                            fieldName = fieldName.replace("businessIncomeExpenses", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (customerIncomeExpenseRequestResource.getBusinessIncomeExpenses() == null || customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().isEmpty()) {
                                    customerIncomeExpenseRequestResource.setBusinessIncomeExpenses(new ArrayList<BusinessIncomeExpenseRequestResource>());
                                    customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().add(i, new BusinessIncomeExpenseRequestResource());
                                } else {
                                    if ((customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().size() - 1) < i) {
                                        customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().add(i, new BusinessIncomeExpenseRequestResource());
                                    }
                                }
                            }
                            if (fieldName.startsWith("incomeExpenseDetails")) {
                                fieldName = fieldName.replace("incomeExpenseDetails", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {
                                    if (customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseDetails() == null || customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseDetails().isEmpty()) {
                                        customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).setIncomeExpenseDetails(new ArrayList<IncomeExpenseDetailsResource>());
                                        customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseDetails().add(i, new IncomeExpenseDetailsResource());
                                    } else {
                                        if ((customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseDetails().size() - 1) < i) {
                                            customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseDetails().add(i, new IncomeExpenseDetailsResource());
                                        }
                                    }
                                }
                                sField = customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseDetails().get(index1).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseDetails().get(index1), error.getDefaultMessage());
                            } else if (fieldName.startsWith("incomeExpenseTax")) {
                                fieldName = fieldName.replace("incomeExpenseTax", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {
                                    if (customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseTax() == null || customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseTax().isEmpty()) {
                                        customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).setIncomeExpenseTax(new ArrayList<IncomeExpenseTaxResource>());
                                        customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseTax().add(i, new IncomeExpenseTaxResource());
                                    } else {
                                        if ((customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseTax().size() - 1) < i) {
                                            customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseTax().add(i, new IncomeExpenseTaxResource());
                                        }
                                    }
                                }
                                sField = customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseTax().get(index1).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseTax().get(index1), error.getDefaultMessage());
                            } else {
                                sField = customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index), error.getDefaultMessage());
                            }
                        } else if (fieldName.startsWith("customerCultivationIncomes")) {
                            fieldName = fieldName.replace("customerCultivationIncomes", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (customerIncomeExpenseRequestResource.getCustomerCultivationIncomes() == null || customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().isEmpty()) {
                                    customerIncomeExpenseRequestResource.setCustomerCultivationIncomes(new ArrayList<CustomerCultivationIncomeResource>());
                                    customerIncomeExpenseRequestResource.getCustomerCultivationIncomes().add(i, new CustomerCultivationIncomeResource());
                                } else {
                                    if ((customerIncomeExpenseRequestResource.getCustomerCultivationIncomes().size() - 1) < i) {
                                        customerIncomeExpenseRequestResource.getCustomerCultivationIncomes().add(i, new CustomerCultivationIncomeResource());
                                    }
                                }
                            }
                            if (fieldName.startsWith("customerCultivationExpenses")) {
                                fieldName = fieldName.replace("customerCultivationExpenses", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {
                                    if (customerIncomeExpenseRequestResource.getCustomerCultivationIncomes().get(index).getCustomerCultivationExpenses() == null || customerIncomeExpenseRequestResource.getBusinessIncomeExpenses().get(index).getIncomeExpenseDetails().isEmpty()) {
                                        customerIncomeExpenseRequestResource.getCustomerCultivationIncomes().get(index).setCustomerCultivationExpenses(new ArrayList<CustomerCultivationExpenseResource>());
                                        customerIncomeExpenseRequestResource.getCustomerCultivationIncomes().get(index).getCustomerCultivationExpenses().add(i, new CustomerCultivationExpenseResource());
                                    } else {
                                        if ((customerIncomeExpenseRequestResource.getCustomerCultivationIncomes().get(index).getCustomerCultivationExpenses().size() - 1) < i) {
                                            customerIncomeExpenseRequestResource.getCustomerCultivationIncomes().get(index).getCustomerCultivationExpenses().add(i, new CustomerCultivationExpenseResource());
                                        }
                                    }
                                }
                                sField = customerIncomeExpenseRequestResource.getCustomerCultivationIncomes().get(index).getCustomerCultivationExpenses().get(index1).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(customerIncomeExpenseRequestResource.getCustomerCultivationIncomes().get(index).getCustomerCultivationExpenses().get(index1), error.getDefaultMessage());
                            } else {
                                sField = customerIncomeExpenseRequestResource.getCustomerCultivationIncomes().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(customerIncomeExpenseRequestResource.getCustomerCultivationIncomes().get(index), error.getDefaultMessage());
                            }
                        } else if (fieldName.startsWith("salaryIncomes")) {
                            fieldName = fieldName.replace("salaryIncomes", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (customerIncomeExpenseRequestResource.getSalaryIncomes() == null || customerIncomeExpenseRequestResource.getSalaryIncomes().isEmpty()) {
                                    customerIncomeExpenseRequestResource.setSalaryIncomes(new ArrayList<SalaryIncomeAddRequestResource>());
                                    customerIncomeExpenseRequestResource.getSalaryIncomes().add(i, new SalaryIncomeAddRequestResource());
                                } else {
                                    if ((customerIncomeExpenseRequestResource.getSalaryIncomes().size() - 1) < i) {
                                        customerIncomeExpenseRequestResource.getSalaryIncomes().add(i, new SalaryIncomeAddRequestResource());
                                    }
                                }
                            }

                            sField = customerIncomeExpenseRequestResource.getSalaryIncomes().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(customerIncomeExpenseRequestResource.getSalaryIncomes().get(index), error.getDefaultMessage());


                        } else if (fieldName.startsWith("houseHoldExpense")) {
                            fieldName = fieldName.replace("houseHoldExpense", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (customerIncomeExpenseRequestResource.getHouseHoldExpense() == null || customerIncomeExpenseRequestResource.getHouseHoldExpense().isEmpty()) {
                                    customerIncomeExpenseRequestResource.setHouseHoldExpense(new ArrayList<HouseHoldExpenseAddRequestResource>());
                                    customerIncomeExpenseRequestResource.getHouseHoldExpense().add(i, new HouseHoldExpenseAddRequestResource());
                                } else {
                                    if ((customerIncomeExpenseRequestResource.getHouseHoldExpense().size() - 1) < i) {
                                        customerIncomeExpenseRequestResource.getHouseHoldExpense().add(i, new HouseHoldExpenseAddRequestResource());
                                    }
                                }
                            }

                            sField = customerIncomeExpenseRequestResource.getHouseHoldExpense().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(customerIncomeExpenseRequestResource.getHouseHoldExpense().get(index), error.getDefaultMessage());


                        } else if (fieldName.startsWith("otherIncomes")) {
                            fieldName = fieldName.replace("otherIncomes", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (customerIncomeExpenseRequestResource.getOtherIncomes() == null || customerIncomeExpenseRequestResource.getOtherIncomes().isEmpty()) {
                                    customerIncomeExpenseRequestResource.setOtherIncomes(new ArrayList<OtherIncomeAddRequestResource>());
                                    customerIncomeExpenseRequestResource.getOtherIncomes().add(i, new OtherIncomeAddRequestResource());
                                } else {
                                    if ((customerIncomeExpenseRequestResource.getOtherIncomes().size() - 1) < i) {
                                        customerIncomeExpenseRequestResource.getOtherIncomes().add(i, new OtherIncomeAddRequestResource());
                                    }
                                }
                            }

                            sField = customerIncomeExpenseRequestResource.getOtherIncomes().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(customerIncomeExpenseRequestResource.getOtherIncomes().get(index), error.getDefaultMessage());


                        } else if (fieldName.startsWith("financialCommitment")) {
                            fieldName = fieldName.replace("financialCommitment", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (customerIncomeExpenseRequestResource.getFinancialCommitment() == null || customerIncomeExpenseRequestResource.getFinancialCommitment().isEmpty()) {
                                    customerIncomeExpenseRequestResource.setFinancialCommitment(new ArrayList<FinancialCommitmentAddRequestResource>());
                                    customerIncomeExpenseRequestResource.getFinancialCommitment().add(i, new FinancialCommitmentAddRequestResource());
                                } else {
                                    if ((customerIncomeExpenseRequestResource.getFinancialCommitment().size() - 1) < i) {
                                        customerIncomeExpenseRequestResource.getFinancialCommitment().add(i, new FinancialCommitmentAddRequestResource());
                                    }
                                }
                            }

                            sField = customerIncomeExpenseRequestResource.getFinancialCommitment().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(customerIncomeExpenseRequestResource.getFinancialCommitment().get(index), error.getDefaultMessage());


                        }
                    }
                    return new ResponseEntity<>(customerIncomeExpenseRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
                //#####################################################################################
                case "guarantorIncomeRequestResource":
                    GuarantorIncomeRequestResource guarantorIncomeRequestResource = new GuarantorIncomeRequestResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("salaryIncomes")) {
                            fieldName = fieldName.replace("salaryIncomes", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (guarantorIncomeRequestResource.getSalaryIncomes() == null || guarantorIncomeRequestResource.getSalaryIncomes().isEmpty()) {
                                    guarantorIncomeRequestResource.setSalaryIncomes(new ArrayList<SalaryIncomeAddRequestResource>());
                                    guarantorIncomeRequestResource.getSalaryIncomes().add(i, new SalaryIncomeAddRequestResource());
                                } else {
                                    if ((guarantorIncomeRequestResource.getSalaryIncomes().size() - 1) < i) {
                                        guarantorIncomeRequestResource.getSalaryIncomes().add(i, new SalaryIncomeAddRequestResource());
                                    }
                                }
                            }

                            sField = guarantorIncomeRequestResource.getSalaryIncomes().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(guarantorIncomeRequestResource.getSalaryIncomes().get(index), error.getDefaultMessage());


                        } else if (fieldName.startsWith("cultivationIncome")) {
                            fieldName = fieldName.replace("cultivationIncome", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (guarantorIncomeRequestResource.getCultivationIncome() == null || guarantorIncomeRequestResource.getCultivationIncome().isEmpty()) {
                                    guarantorIncomeRequestResource.setCultivationIncome(new ArrayList<GuarantorCultivationIncomeAddResource>());
                                    guarantorIncomeRequestResource.getCultivationIncome().add(i, new GuarantorCultivationIncomeAddResource());
                                } else {
                                    if ((guarantorIncomeRequestResource.getCultivationIncome().size() - 1) < i) {
                                        guarantorIncomeRequestResource.getCultivationIncome().add(i, new GuarantorCultivationIncomeAddResource());
                                    }
                                }
                            }

                            sField = guarantorIncomeRequestResource.getCultivationIncome().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(guarantorIncomeRequestResource.getCultivationIncome().get(index), error.getDefaultMessage());


                        } else if (fieldName.startsWith("otherIncomes")) {
                            fieldName = fieldName.replace("otherIncomes", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (guarantorIncomeRequestResource.getOtherIncomes() == null || guarantorIncomeRequestResource.getOtherIncomes().isEmpty()) {
                                    guarantorIncomeRequestResource.setOtherIncomes(new ArrayList<OtherIncomeAddRequestResource>());
                                    guarantorIncomeRequestResource.getOtherIncomes().add(i, new OtherIncomeAddRequestResource());
                                } else {
                                    if ((guarantorIncomeRequestResource.getOtherIncomes().size() - 1) < i) {
                                        guarantorIncomeRequestResource.getOtherIncomes().add(i, new OtherIncomeAddRequestResource());
                                    }
                                }
                            }

                            sField = guarantorIncomeRequestResource.getOtherIncomes().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(guarantorIncomeRequestResource.getOtherIncomes().get(index), error.getDefaultMessage());


                        } else if (fieldName.startsWith("businessIncome")) {
                            fieldName = fieldName.replace("businessIncome", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (guarantorIncomeRequestResource.getBusinessIncome() == null || guarantorIncomeRequestResource.getBusinessIncome().isEmpty()) {
                                    guarantorIncomeRequestResource.setBusinessIncome(new ArrayList<BusinessIncomeExpenseRequestResource>());
                                    guarantorIncomeRequestResource.getBusinessIncome().add(i, new BusinessIncomeExpenseRequestResource());
                                } else {
                                    if ((guarantorIncomeRequestResource.getBusinessIncome().size() - 1) < i) {
                                        guarantorIncomeRequestResource.getBusinessIncome().add(i, new BusinessIncomeExpenseRequestResource());
                                    }
                                }
                            }

                            sField = guarantorIncomeRequestResource.getBusinessIncome().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(guarantorIncomeRequestResource.getBusinessIncome().get(index), error.getDefaultMessage());


                        }
                    }
                    return new ResponseEntity<>(guarantorIncomeRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //###################################################################################
                case "newFacilityAddResource":
                    NewFacilityAddResource newFacilityAddResource = new NewFacilityAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("contractDetails")) {
                            fieldName = fieldName.replace("contractDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (newFacilityAddResource.getContractDetails() == null || newFacilityAddResource.getContractDetails().isEmpty()) {
                                    newFacilityAddResource.setContractDetails(new ArrayList<FacilityContractDetailsResource>());
                                    newFacilityAddResource.getContractDetails().add(i, new FacilityContractDetailsResource());
                                } else {
                                    if ((newFacilityAddResource.getContractDetails().size() - 1) < i) {
                                        newFacilityAddResource.getContractDetails().add(i, new FacilityContractDetailsResource());
                                    }
                                }
                            }
                            sField = newFacilityAddResource.getContractDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(newFacilityAddResource.getContractDetails().get(index), error.getDefaultMessage());
                        } else if (fieldName.startsWith("otherProducts")) {
                            fieldName = fieldName.replace("otherProducts", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (newFacilityAddResource.getOtherProducts() == null || newFacilityAddResource.getOtherProducts().isEmpty()) {
                                    newFacilityAddResource.setOtherProducts(new ArrayList<FacilityOtherProductsResource>());
                                    newFacilityAddResource.getOtherProducts().add(i, new FacilityOtherProductsResource());
                                } else {
                                    if ((newFacilityAddResource.getOtherProducts().size() - 1) < i) {
                                        newFacilityAddResource.getOtherProducts().add(i, new FacilityOtherProductsResource());
                                    }
                                }
                            }
                            sField = newFacilityAddResource.getOtherProducts().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(newFacilityAddResource.getOtherProducts().get(index), error.getDefaultMessage());
                        } else {
                            sField = newFacilityAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(newFacilityAddResource.getClass().cast(newFacilityAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(newFacilityAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "newFacilityUpdateResource":
                    NewFacilityUpdateResource newFacilityUpdateResource = new NewFacilityUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("contractDetails")) {
                            fieldName = fieldName.replace("contractDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (newFacilityUpdateResource.getContractDetails() == null || newFacilityUpdateResource.getContractDetails().isEmpty()) {
                                    newFacilityUpdateResource.setContractDetails(new ArrayList<FacilityContractDetailsResource>());
                                    newFacilityUpdateResource.getContractDetails().add(i, new FacilityContractDetailsResource());
                                } else {
                                    if ((newFacilityUpdateResource.getContractDetails().size() - 1) < i) {
                                        newFacilityUpdateResource.getContractDetails().add(i, new FacilityContractDetailsResource());
                                    }
                                }
                            }
                            sField = newFacilityUpdateResource.getContractDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(newFacilityUpdateResource.getContractDetails().get(index), error.getDefaultMessage());
                        } else if (fieldName.startsWith("otherProducts")) {
                            fieldName = fieldName.replace("otherProducts", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (newFacilityUpdateResource.getOtherProducts() == null || newFacilityUpdateResource.getOtherProducts().isEmpty()) {
                                    newFacilityUpdateResource.setOtherProducts(new ArrayList<FacilityOtherProductsResource>());
                                    newFacilityUpdateResource.getOtherProducts().add(i, new FacilityOtherProductsResource());
                                } else {
                                    if ((newFacilityUpdateResource.getOtherProducts().size() - 1) < i) {
                                        newFacilityUpdateResource.getOtherProducts().add(i, new FacilityOtherProductsResource());
                                    }
                                }
                            }
                            sField = newFacilityUpdateResource.getOtherProducts().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(newFacilityUpdateResource.getOtherProducts().get(index), error.getDefaultMessage());
                        } else {
                            sField = newFacilityUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(newFacilityUpdateResource.getClass().cast(newFacilityUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(newFacilityUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "customerResource":
                    CustomerResource customerResource = new CustomerResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = customerResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(customerResource.getClass().cast(customerResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(customerResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "updateCustomerResource":
                    UpdateCustomerResource updateCustomerResource = new UpdateCustomerResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = updateCustomerResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(updateCustomerResource, error.getDefaultMessage());
                        } else {
                            sField = updateCustomerResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(updateCustomerResource.getClass().getSuperclass().cast(updateCustomerResource), error.getDefaultMessage());
                        }
                    }

                    return new ResponseEntity<>(updateCustomerResource, HttpStatus.UNPROCESSABLE_ENTITY);

//              case "guarantorAddResource":
//               GuarantorAddResource guarantorAddResource = new GuarantorAddResource();
//                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//                        LoggerRequest.getInstance().logInfo(project+ " " +error.getField()+" "+error.getDefaultMessage());
//                       sField =  guarantorAddResource.getClass().getDeclaredField(error.getField());
//                       sField.setAccessible(true);
//                       sField.set(guarantorAddResource.getClass().cast(guarantorAddResource), error.getDefaultMessage());
//                   }
//                   return new ResponseEntity<>(guarantorAddResource, HttpStatus.UNPROCESSABLE_ENTITY);


                case "guarantorAddResource":
                    GuarantorAddResource guarantorAddResource = new GuarantorAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("identificationDetails")) {
                            fieldName = fieldName.replace("identificationDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (guarantorAddResource.getIdentificationDetails() == null || guarantorAddResource.getIdentificationDetails().isEmpty()) {
                                    guarantorAddResource.setIdentificationDetails(new ArrayList<IdentificationDetailResource>());
                                    guarantorAddResource.getIdentificationDetails().add(i, new IdentificationDetailResource());
                                } else {
                                    if ((guarantorAddResource.getIdentificationDetails().size() - 1) < i) {
                                        guarantorAddResource.getIdentificationDetails().add(i, new IdentificationDetailResource());
                                    }
                                }
                            }
                            sField = guarantorAddResource.getIdentificationDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(guarantorAddResource.getIdentificationDetails().get(index), error.getDefaultMessage());
                        } else if (fieldName.startsWith("addressDetails")) {
                            fieldName = fieldName.replace("addressDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (guarantorAddResource.getAddressDetails() == null || guarantorAddResource.getAddressDetails().isEmpty()) {
                                    guarantorAddResource.setAddressDetails(new ArrayList<AddressDetailsResource>());
                                    guarantorAddResource.getAddressDetails().add(i, new AddressDetailsResource());
                                } else {
                                    if ((guarantorAddResource.getAddressDetails().size() - 1) < i) {
                                        guarantorAddResource.getAddressDetails().add(i, new AddressDetailsResource());
                                    }
                                }
                            }
                            sField = guarantorAddResource.getAddressDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(guarantorAddResource.getAddressDetails().get(index), error.getDefaultMessage());
                        } else if (fieldName.startsWith("contactDetails")) {
                            fieldName = fieldName.replace("contactDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (guarantorAddResource.getContactDetails() == null || guarantorAddResource.getContactDetails().isEmpty()) {
                                    guarantorAddResource.setContactDetails(new ArrayList<ContactDetailsResource>());
                                    guarantorAddResource.getContactDetails().add(i, new ContactDetailsResource());
                                } else {
                                    if ((guarantorAddResource.getContactDetails().size() - 1) < i) {
                                        guarantorAddResource.getContactDetails().add(i, new ContactDetailsResource());
                                    }
                                }
                            }
                            sField = guarantorAddResource.getContactDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(guarantorAddResource.getContactDetails().get(index), error.getDefaultMessage());

                        } else {
                            sField = guarantorAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(guarantorAddResource.getClass().cast(guarantorAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(guarantorAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "updateGuarantorResource":
                    UpdateGuarantorResource updateGuarantorResource = new UpdateGuarantorResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = updateGuarantorResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(updateGuarantorResource, error.getDefaultMessage());
                        } else {
                            sField = updateGuarantorResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(updateGuarantorResource.getClass().getSuperclass().cast(updateGuarantorResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(updateGuarantorResource, HttpStatus.UNPROCESSABLE_ENTITY);


                case "creditAppraiselCollateralDetailsAddResource":
                    CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetailsAddResource = new CreditAppraiselCollateralDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("documentUploadDetails")) {
                            fieldName = fieldName.replace("documentUploadDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (creditAppraiselCollateralDetailsAddResource.getDocumentUploadDetails() == null || creditAppraiselCollateralDetailsAddResource.getDocumentUploadDetails().isEmpty()) {
                                    creditAppraiselCollateralDetailsAddResource.setDocumentUploadDetails(new ArrayList<CreAppCollateralDocumentDetailsResource>());
                                    creditAppraiselCollateralDetailsAddResource.getDocumentUploadDetails().add(i, new CreAppCollateralDocumentDetailsResource());
                                } else {
                                    if ((creditAppraiselCollateralDetailsAddResource.getDocumentUploadDetails().size() - 1) < i) {
                                        creditAppraiselCollateralDetailsAddResource.getDocumentUploadDetails().add(i, new CreAppCollateralDocumentDetailsResource());
                                    }
                                }
                            }

                            sField = creditAppraiselCollateralDetailsAddResource.getDocumentUploadDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(creditAppraiselCollateralDetailsAddResource.getDocumentUploadDetails().get(index), error.getDefaultMessage());


                        } else {
                            sField = creditAppraiselCollateralDetailsAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(creditAppraiselCollateralDetailsAddResource.getClass().cast(creditAppraiselCollateralDetailsAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(creditAppraiselCollateralDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "creditAppraiselCollateralDetailsUpdateResource":
                    CreditAppraiselCollateralDetailsUpdateResource creditAppraiselCollateralDetailsUpdateResource = new CreditAppraiselCollateralDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = creditAppraiselCollateralDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(creditAppraiselCollateralDetailsUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = creditAppraiselCollateralDetailsUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(creditAppraiselCollateralDetailsUpdateResource.getClass().getSuperclass().cast(creditAppraiselCollateralDetailsUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(creditAppraiselCollateralDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "incomeTypeRequest":
                    IncomeTypeRequest incomeTypeRequest = new IncomeTypeRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = incomeTypeRequest.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(incomeTypeRequest.getClass().cast(incomeTypeRequest), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(incomeTypeRequest, HttpStatus.UNPROCESSABLE_ENTITY);

                case "updateIncomeTypeRequest":
                    UpdateIncomeTypeRequest updateIncomeTypeRequest = new UpdateIncomeTypeRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = updateIncomeTypeRequest.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(updateIncomeTypeRequest.getClass().cast(updateIncomeTypeRequest), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(updateIncomeTypeRequest, HttpStatus.UNPROCESSABLE_ENTITY);

                case "valuationAndInsuranceDetailsAddResource":
                    ValuationAndInsuranceDetailsAddResource valuationAndInsuranceDetailsAddResource = new ValuationAndInsuranceDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("valuationDetails")) {
                            fieldName = fieldName.replace("valuationDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (valuationAndInsuranceDetailsAddResource.getValuationDetails() == null || valuationAndInsuranceDetailsAddResource.getValuationDetails().isEmpty()) {
                                    valuationAndInsuranceDetailsAddResource.setValuationDetails(new ArrayList<ValuationDetailsAddResource>());
                                    valuationAndInsuranceDetailsAddResource.getValuationDetails().add(i, new ValuationDetailsAddResource());
                                } else {
                                    if ((valuationAndInsuranceDetailsAddResource.getValuationDetails().size() - 1) < i) {
                                        valuationAndInsuranceDetailsAddResource.getValuationDetails().add(i, new ValuationDetailsAddResource());
                                    }
                                }
                            }
                            sField = valuationAndInsuranceDetailsAddResource.getValuationDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(valuationAndInsuranceDetailsAddResource.getValuationDetails().get(index), error.getDefaultMessage());
//                   } else if (fieldName.startsWith("insuranceDetails")) {
//                           fieldName = fieldName.replace("insuranceDetails.", "");
//                           if (valuationAndInsuranceDetailsAddResource.getInsuranceDetails() == null) {
//                               valuationAndInsuranceDetailsAddResource.setInsuranceDetails(new InsuranceDetailsAddResource());
//                           }
//                           sField = valuationAndInsuranceDetailsAddResource.getInsuranceDetails().getClass().getDeclaredField(fieldName);
//                           sField.setAccessible(true);
//                           sField.set(valuationAndInsuranceDetailsAddResource.getInsuranceDetails(), error.getDefaultMessage());
                        } else {
                            sField = valuationAndInsuranceDetailsAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(valuationAndInsuranceDetailsAddResource.getClass().cast(valuationAndInsuranceDetailsAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(valuationAndInsuranceDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "valuationAndInsuranceDetailsUpdateResource":
                    ValuationAndInsuranceDetailsUpdateResource valuationAndInsuranceDetailsUpdateResource = new ValuationAndInsuranceDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("valuationDetails")) {
                            fieldName = fieldName.replace("valuationDetails.", "");
                            if (valuationAndInsuranceDetailsUpdateResource.getValuationDetails() == null) {
                                valuationAndInsuranceDetailsUpdateResource.setValuationDetails(new ValuationDetailsUpdateResource());
                            }
                            sField = valuationAndInsuranceDetailsUpdateResource.getValuationDetails().getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(valuationAndInsuranceDetailsUpdateResource.getValuationDetails(), error.getDefaultMessage());
//                   } else if (fieldName.startsWith("insuranceDetails")) {
//                           fieldName = fieldName.replace("insuranceDetails.", "");
//                           if (valuationAndInsuranceDetailsUpdateResource.getInsuranceDetails() == null) {
//                               valuationAndInsuranceDetailsUpdateResource.setInsuranceDetails(new InsuranceDetailsUpdateResource());
//                           }
//                           sField = valuationAndInsuranceDetailsUpdateResource.getInsuranceDetails().getClass().getSuperclass().getDeclaredField(fieldName);
//                           sField.setAccessible(true);
//                           sField.set(valuationAndInsuranceDetailsUpdateResource.getInsuranceDetails().getClass().getSuperclass().cast(valuationAndInsuranceDetailsUpdateResource), error.getDefaultMessage());

                        } else {
                            sField = valuationAndInsuranceDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(valuationAndInsuranceDetailsUpdateResource.getClass().cast(valuationAndInsuranceDetailsUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(valuationAndInsuranceDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "linkedPersonResource":
                    LinkedPersonResource linkedPersonResource = new LinkedPersonResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = linkedPersonResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(linkedPersonResource.getClass().cast(linkedPersonResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(linkedPersonResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "identificationDetailResource":
                    IdentificationDetailResource identificationDetailResource = new IdentificationDetailResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = identificationDetailResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(identificationDetailResource.getClass().cast(identificationDetailResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(identificationDetailResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "leadInforBranchAssignUpdateResource":
                    LeadInforBranchAssignUpdateResource leadInforBranchAssignUpdateResource = new LeadInforBranchAssignUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = leadInforBranchAssignUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(leadInforBranchAssignUpdateResource.getClass().cast(leadInforBranchAssignUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(leadInforBranchAssignUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "leadInforMarketingOfficerAssignUpdateResource":
                    LeadInforMarketingOfficerAssignUpdateResource leadInforMarketingOfficerAssignUpdateResource = new LeadInforMarketingOfficerAssignUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = leadInforMarketingOfficerAssignUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(leadInforMarketingOfficerAssignUpdateResource.getClass().cast(leadInforMarketingOfficerAssignUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(leadInforMarketingOfficerAssignUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "leadInforAssignToCurrentUserUpdateResource":
                    LeadInforAssignToCurrentUserUpdateResource leadInforAssignToCurrentUserUpdateResource = new LeadInforAssignToCurrentUserUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = leadInforAssignToCurrentUserUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(leadInforAssignToCurrentUserUpdateResource.getClass().cast(leadInforAssignToCurrentUserUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(leadInforAssignToCurrentUserUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "externalLiabilityAddResource":
                    ExternalLiabilityAddResource externalLiabilityAddResource = new ExternalLiabilityAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = externalLiabilityAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(externalLiabilityAddResource.getClass().cast(externalLiabilityAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(externalLiabilityAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "otherIncomeCategoryAddResource":
                    OtherIncomeCategoryAddResource otherIncomeCategoryAddResource = new OtherIncomeCategoryAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = otherIncomeCategoryAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(otherIncomeCategoryAddResource.getClass().cast(otherIncomeCategoryAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(otherIncomeCategoryAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "otherIncomeCategoryUpdateResource":
                    OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource = new OtherIncomeCategoryUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = otherIncomeCategoryUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(otherIncomeCategoryUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = otherIncomeCategoryUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(otherIncomeCategoryUpdateResource.getClass().getSuperclass().cast(otherIncomeCategoryUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(otherIncomeCategoryUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "otherIncomeExpenseTypeAddResource":
                    OtherIncomeExpenseTypeAddResource otherIncomeExpenseTypeAddResource = new OtherIncomeExpenseTypeAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = otherIncomeExpenseTypeAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(otherIncomeExpenseTypeAddResource.getClass().cast(otherIncomeExpenseTypeAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(otherIncomeExpenseTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "otherIncomeExpenseTypeUpdateResource":
                    OtherIncomeExpenseTypeUpdateResource otherIncomeExpenseTypeUpdateResource = new OtherIncomeExpenseTypeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = otherIncomeExpenseTypeUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(otherIncomeExpenseTypeUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = otherIncomeExpenseTypeUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(otherIncomeExpenseTypeUpdateResource.getClass().getSuperclass().cast(otherIncomeExpenseTypeUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(otherIncomeExpenseTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "exceptionApprovalGroupAddResource":
                    ExceptionApprovalGroupAddResource exceptionApprovalGroupAddResource = new ExceptionApprovalGroupAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = exceptionApprovalGroupAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(exceptionApprovalGroupAddResource.getClass().cast(exceptionApprovalGroupAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(exceptionApprovalGroupAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "exceptionApprovalGroupUpdateResource":
                    ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource = new ExceptionApprovalGroupUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = exceptionApprovalGroupUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(exceptionApprovalGroupUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = exceptionApprovalGroupUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(exceptionApprovalGroupUpdateResource.getClass().cast(exceptionApprovalGroupUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(exceptionApprovalGroupUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "approvalGroupAddResource":
                    ApprovalGroupAddResource approvalGroupAddResource = new ApprovalGroupAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = approvalGroupAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(approvalGroupAddResource.getClass().cast(approvalGroupAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(approvalGroupAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "approvalGroupUpdateResource":
                    ApprovalGroupUpdateResource approvalGroupUpdateResource = new ApprovalGroupUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = approvalGroupUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(approvalGroupUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = approvalGroupUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(approvalGroupUpdateResource.getClass().cast(approvalGroupUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(approvalGroupUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);


                // ####### Added by Sanatha for FXL-357    FXL-427 on 09-AUG-2021 #########
                case "statementTypeAddResource":
                    StatementTypeAddResource statementTypeAddResource = new StatementTypeAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = statementTypeAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(statementTypeAddResource.getClass().cast(statementTypeAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(statementTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "statementTypeUpdateResource":
                    StatementTypeUpdateResource statementTypeUpdateResource = new StatementTypeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = statementTypeUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(statementTypeUpdateResource.getClass().cast(statementTypeUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(statementTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "statementTemplateAddResource":
                    StatementTemplateAddResource statementTemplateAddResource = new StatementTemplateAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("statementTemplateDetails")) {
                            fieldName = fieldName.replace("statementTemplateDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (statementTemplateAddResource.getStatementTemplateDetails() == null || statementTemplateAddResource.getStatementTemplateDetails().isEmpty()) {
                                    statementTemplateAddResource.setStatementTemplateDetails(new ArrayList<StatementTemplateDetailAddResource>());
                                    statementTemplateAddResource.getStatementTemplateDetails().add(i, new StatementTemplateDetailAddResource());
                                } else {
                                    if ((statementTemplateAddResource.getStatementTemplateDetails().size() - 1) < i) {
                                        statementTemplateAddResource.getStatementTemplateDetails().add(i, new StatementTemplateDetailAddResource());
                                    }
                                }
                            }
                            sField = statementTemplateAddResource.getStatementTemplateDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(statementTemplateAddResource.getStatementTemplateDetails().get(index), error.getDefaultMessage());
                        } else {
                            sField = statementTemplateAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(statementTemplateAddResource.getClass().cast(statementTemplateAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(statementTemplateAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                // ####### END FXL-357    FXL-427  #########
                case "exceptionApprovalGroupTypeAddResource":
                    ExceptionApprovalGroupTypeAddResource exceptionApprovalGroupTypeAddResource = new ExceptionApprovalGroupTypeAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = exceptionApprovalGroupTypeAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(exceptionApprovalGroupTypeAddResource.getClass().cast(exceptionApprovalGroupTypeAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(exceptionApprovalGroupTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "exceptionApprovalGroupTypeUpdateResource":
                    ExceptionApprovalGroupTypeUpdateResource exceptionApprovalGroupTypeUpdateResource = new ExceptionApprovalGroupTypeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = exceptionApprovalGroupTypeUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(exceptionApprovalGroupTypeUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = exceptionApprovalGroupTypeUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(exceptionApprovalGroupTypeUpdateResource.getClass().getSuperclass().cast(exceptionApprovalGroupTypeUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(exceptionApprovalGroupTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "analystAddResource":
                    AnalystAddResource analystAddResource = new AnalystAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("analystExceptionDetailList")) {
                            fieldName = fieldName.replace("analystExceptionDetailList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (analystAddResource.getAnalystExceptionDetailList() == null || analystAddResource.getAnalystExceptionDetailList().isEmpty()) {
                                    analystAddResource.setAnalystExceptionDetailList(new ArrayList<AnalystExceptionDetailsResource>());
                                    analystAddResource.getAnalystExceptionDetailList().add(i, new AnalystExceptionDetailsResource());
                                } else {
                                    if ((analystAddResource.getAnalystExceptionDetailList().size() - 1) < i) {
                                        analystAddResource.getAnalystExceptionDetailList().add(i, new AnalystExceptionDetailsResource());
                                    }
                                }
                            }
                            if (fieldName.startsWith("analystExceptionDocumentList")) {
                                fieldName = fieldName.replace("analystExceptionDocumentList", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {
                                    if (analystAddResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList() == null || analystAddResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList().isEmpty()) {
                                        analystAddResource.getAnalystExceptionDetailList().get(index).setAnalystExceptionDocumentList(new ArrayList<DocumentUpdateResource>());
                                        analystAddResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList().add(i, new DocumentUpdateResource());
                                    } else {
                                        if ((analystAddResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList().size() - 1) < i) {
                                            analystAddResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList().add(i, new DocumentUpdateResource());
                                        }
                                    }
                                }
                                sField = analystAddResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList().get(index1).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(analystAddResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList().get(index1), error.getDefaultMessage());
                            } else {
                                sField = analystAddResource.getAnalystExceptionDetailList().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(analystAddResource.getAnalystExceptionDetailList().get(index), error.getDefaultMessage());
                            }
                        } else {
                            sField = analystAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(analystAddResource.getClass().cast(analystAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(analystAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "analystUpdateResource":
                    AnalystUpdateResource analystUpdateResource = new AnalystUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("analystExceptionDetailList")) {
                            fieldName = fieldName.replace("analystExceptionDetailList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (analystUpdateResource.getAnalystExceptionDetailList() == null || analystUpdateResource.getAnalystExceptionDetailList().isEmpty()) {
                                    analystUpdateResource.setAnalystExceptionDetailList(new ArrayList<AnalystExceptionDetailsResource>());
                                    analystUpdateResource.getAnalystExceptionDetailList().add(i, new AnalystExceptionDetailsResource());
                                } else {
                                    if ((analystUpdateResource.getAnalystExceptionDetailList().size() - 1) < i) {
                                        analystUpdateResource.getAnalystExceptionDetailList().add(i, new AnalystExceptionDetailsResource());
                                    }
                                }
                            }
                            if (fieldName.startsWith("analystExceptionDocumentList")) {
                                fieldName = fieldName.replace("analystExceptionDocumentList", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {
                                    if (analystUpdateResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList() == null || analystUpdateResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList().isEmpty()) {
                                        analystUpdateResource.getAnalystExceptionDetailList().get(index).setAnalystExceptionDocumentList(new ArrayList<DocumentUpdateResource>());
                                        analystUpdateResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList().add(i, new DocumentUpdateResource());
                                    } else {
                                        if ((analystUpdateResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList().size() - 1) < i) {
                                            analystUpdateResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList().add(i, new DocumentUpdateResource());
                                        }
                                    }
                                }
                                if (fieldName.startsWith("version")) {
                                    sField = analystUpdateResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList().get(index1).getClass().getDeclaredField(error.getField());
                                    sField.setAccessible(true);
                                    sField.set(analystUpdateResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList().get(index1), error.getDefaultMessage());
                                } else {
                                    sField = analystUpdateResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList().get(index1).getClass().getDeclaredField(fieldName);
                                    sField.setAccessible(true);
                                    sField.set(analystUpdateResource.getAnalystExceptionDetailList().get(index).getAnalystExceptionDocumentList().get(index1), error.getDefaultMessage());
                                }
                            } else if (fieldName.startsWith("version")) {
                                sField = analystUpdateResource.getAnalystExceptionDetailList().get(index).getClass().getDeclaredField(error.getField());
                                sField.setAccessible(true);
                                sField.set(analystUpdateResource.getAnalystExceptionDetailList().get(index), error.getDefaultMessage());
                            } else {
                                sField = analystUpdateResource.getAnalystExceptionDetailList().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(analystUpdateResource.getAnalystExceptionDetailList().get(index), error.getDefaultMessage());
                            }
                        } else if (fieldName.startsWith("version")) {
                            sField = analystUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(analystUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = analystUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(analystUpdateResource.getClass().getSuperclass().cast(analystUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(analystUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessSubCenterAddResource":
                    BusinessSubCenterAddResource businessSubCenterAddResource = new BusinessSubCenterAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = businessSubCenterAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(businessSubCenterAddResource.getClass().cast(businessSubCenterAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(businessSubCenterAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessSubCenterUpdateResource":
                    BusinessSubCenterUpdateResource businessSubCenterUpdateResource = new BusinessSubCenterUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = businessSubCenterUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessSubCenterUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = businessSubCenterUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessSubCenterUpdateResource.getClass().getSuperclass().cast(businessSubCenterUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(businessSubCenterUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessSubCenterProductMapAddResource":
                    BusinessSubCenterProductMapAddResource businessSubCenterProductMapAddResource = new BusinessSubCenterProductMapAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("products")) {
                            fieldName = fieldName.replace("products", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessSubCenterProductMapAddResource.getProducts() == null || businessSubCenterProductMapAddResource.getProducts().isEmpty()) {
                                    businessSubCenterProductMapAddResource.setProducts(new ArrayList<ProductRequestResource>());
                                    businessSubCenterProductMapAddResource.getProducts().add(i, new ProductRequestResource());
                                } else {
                                    if ((businessSubCenterProductMapAddResource.getProducts().size() - 1) < i) {
                                        businessSubCenterProductMapAddResource.getProducts().add(i, new ProductRequestResource());
                                    }
                                }
                            }
                            sField = businessSubCenterProductMapAddResource.getProducts().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessSubCenterProductMapAddResource.getProducts().get(index), error.getDefaultMessage());
                        } else {
                            sField = businessSubCenterProductMapAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessSubCenterProductMapAddResource.getClass().cast(businessSubCenterProductMapAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(businessSubCenterProductMapAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessSubCenterProductMapUpdateResource":
                    BusinessSubCenterProductMapUpdateResource businessSubCenterProductMapUpdateResource = new BusinessSubCenterProductMapUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("products")) {
                            fieldName = fieldName.replace("products", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessSubCenterProductMapUpdateResource.getProducts() == null || businessSubCenterProductMapUpdateResource.getProducts().isEmpty()) {
                                    businessSubCenterProductMapUpdateResource.setProducts(new ArrayList<ProductRequestResource>());
                                    businessSubCenterProductMapUpdateResource.getProducts().add(i, new ProductRequestResource());
                                } else {
                                    if ((businessSubCenterProductMapUpdateResource.getProducts().size() - 1) < i) {
                                        businessSubCenterProductMapUpdateResource.getProducts().add(i, new ProductRequestResource());
                                    }
                                }
                            }
                            sField = businessSubCenterProductMapUpdateResource.getProducts().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessSubCenterProductMapUpdateResource.getProducts().get(index), error.getDefaultMessage());
                        } else {
                            sField = businessSubCenterProductMapUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessSubCenterProductMapUpdateResource.getClass().cast(businessSubCenterProductMapUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(businessSubCenterProductMapUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                //*************************Added by SewwandiH-Risk Grading*************************************

                case "riskGradingAddResource":
                    RiskGradingAddResource riskGradingAddResource = new RiskGradingAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("riskGradingDetails")) {
                            fieldName = fieldName.replace("riskGradingDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (riskGradingAddResource.getRiskGradingDetails() == null || riskGradingAddResource.getRiskGradingDetails().isEmpty()) {
                                    riskGradingAddResource.setRiskGradingDetails(new ArrayList<RiskGradingDetailAddResource>());
                                    riskGradingAddResource.getRiskGradingDetails().add(i, new RiskGradingDetailAddResource());
                                } else {
                                    if ((riskGradingAddResource.getRiskGradingDetails().size() - 1) < i) {
                                        riskGradingAddResource.getRiskGradingDetails().add(i, new RiskGradingDetailAddResource());
                                    }
                                }
                            }
                            sField = riskGradingAddResource.getRiskGradingDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(riskGradingAddResource.getRiskGradingDetails().get(index), error.getDefaultMessage());
                        } else {
                            sField = riskGradingAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(riskGradingAddResource.getClass().cast(riskGradingAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(riskGradingAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "riskGradingUpdateResource":
                    RiskGradingUpdateResource riskGradingUpdateResource = new RiskGradingUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("riskGradingDetails")) {
                            fieldName = fieldName.replace("riskGradingDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (riskGradingUpdateResource.getRiskGradingDetails() == null || riskGradingUpdateResource.getRiskGradingDetails().isEmpty()) {
                                    riskGradingUpdateResource.setRiskGradingDetails(new ArrayList<RiskGradingDetailUpdateResource>());
                                    riskGradingUpdateResource.getRiskGradingDetails().add(i, new RiskGradingDetailUpdateResource());
                                } else {
                                    if ((riskGradingUpdateResource.getRiskGradingDetails().size() - 1) < i) {
                                        riskGradingUpdateResource.getRiskGradingDetails().add(i, new RiskGradingDetailUpdateResource());
                                    }
                                }
                            }
                            sField = riskGradingUpdateResource.getRiskGradingDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(riskGradingUpdateResource.getRiskGradingDetails().get(index), error.getDefaultMessage());
                        } else {
                            sField = riskGradingUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(riskGradingUpdateResource.getClass().cast(riskGradingUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(riskGradingUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);


                //*************************END  - Added by SewwandiH-Risk Grading*************************************

                //****************************Added by SewwandiH-Field setup******************************************
                case "fieldSetupAddResource":
                    FieldSetupAddResource fieldSetupAddResource = new FieldSetupAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = fieldSetupAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(fieldSetupAddResource.getClass().cast(fieldSetupAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(fieldSetupAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "fieldSetupUpdateResource":
                    FieldSetupUpdateResource fieldSetupUpdateResource = new FieldSetupUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = fieldSetupUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(fieldSetupUpdateResource.getClass().cast(fieldSetupUpdateResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(fieldSetupUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                //************************End - Added by SewwandiH-Field setup**********************************************
                case "salaryExpenseTypeAddResource":
                    SalaryExpenseTypeAddResource salaryExpenseTypeAddResource = new SalaryExpenseTypeAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("expenseTypeList")) {
                            fieldName = fieldName.replace("expenseTypeList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (salaryExpenseTypeAddResource.getExpenseTypeList() == null || salaryExpenseTypeAddResource.getExpenseTypeList().isEmpty()) {
                                    salaryExpenseTypeAddResource.setExpenseTypeList(new ArrayList<ExpenseTypeListResource>());
                                    salaryExpenseTypeAddResource.getExpenseTypeList().add(i, new ExpenseTypeListResource());
                                } else {
                                    if ((salaryExpenseTypeAddResource.getExpenseTypeList().size() - 1) < i) {
                                        salaryExpenseTypeAddResource.getExpenseTypeList().add(i, new ExpenseTypeListResource());
                                    }
                                }
                            }
                            sField = salaryExpenseTypeAddResource.getExpenseTypeList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(salaryExpenseTypeAddResource.getExpenseTypeList().get(index), error.getDefaultMessage());
                        } else {
                            sField = salaryExpenseTypeAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(salaryExpenseTypeAddResource.getClass().cast(salaryExpenseTypeAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(salaryExpenseTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "salaryExpenseTypeUpdateResource":
                    SalaryExpenseTypeUpdateResource salaryExpenseTypeUpdateResource = new SalaryExpenseTypeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = salaryExpenseTypeUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(salaryExpenseTypeUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = salaryExpenseTypeUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(salaryExpenseTypeUpdateResource.getClass().getSuperclass().cast(salaryExpenseTypeUpdateResource), error.getDefaultMessage());
                        }
                    }

                    return new ResponseEntity<>(salaryExpenseTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "incomeSourceDetailsAddResource":
                    IncomeSourceDetailsAddResource incomeSourceDetailsAddResource = new IncomeSourceDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        sField = incomeSourceDetailsAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(incomeSourceDetailsAddResource.getClass().cast(incomeSourceDetailsAddResource), error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(incomeSourceDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "incomeSourceDetailsUpdateResource":
                    IncomeSourceDetailsUpdateResource incomeSourceDetailsUpdateResource = new IncomeSourceDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = incomeSourceDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(incomeSourceDetailsUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = incomeSourceDetailsUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(incomeSourceDetailsUpdateResource.getClass().getSuperclass().cast(incomeSourceDetailsUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(incomeSourceDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "houseHoldExpenseDetailsAddResource":
                    HouseHoldExpenseDetailsAddResource houseHoldExpenseDetailsAddResource = new HouseHoldExpenseDetailsAddResource();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("houseHoldExpenseInfo")) {
                            fieldName = fieldName.replace("houseHoldExpenseInfo", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (houseHoldExpenseDetailsAddResource.getHouseHoldExpenseInfo() == null || houseHoldExpenseDetailsAddResource.getHouseHoldExpenseInfo().isEmpty()) {
                                    houseHoldExpenseDetailsAddResource.setHouseHoldExpenseInfo(new ArrayList<HouseHoldExpenseInfoResource>());
                                    houseHoldExpenseDetailsAddResource.getHouseHoldExpenseInfo().add(i, new HouseHoldExpenseInfoResource());
                                } else {
                                    if ((houseHoldExpenseDetailsAddResource.getHouseHoldExpenseInfo().size() - 1) < i) {
                                        houseHoldExpenseDetailsAddResource.getHouseHoldExpenseInfo().add(i, new HouseHoldExpenseInfoResource());
                                    }
                                }
                            }
                            sField = houseHoldExpenseDetailsAddResource.getHouseHoldExpenseInfo().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(houseHoldExpenseDetailsAddResource.getHouseHoldExpenseInfo().get(index), error.getDefaultMessage());

                        } else {
                            sField = houseHoldExpenseDetailsAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(houseHoldExpenseDetailsAddResource.getClass().cast(houseHoldExpenseDetailsAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(houseHoldExpenseDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "houseHoldExpenseDetailsUpdateResource":
                    HouseHoldExpenseDetailsUpdateResource houseHoldExpenseDetailsUpdateResource = new HouseHoldExpenseDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("houseHoldExpenseInfo")) {
                            fieldName = fieldName.replace("houseHoldExpenseInfo", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (houseHoldExpenseDetailsUpdateResource.getHouseHoldExpenseInfo() == null || houseHoldExpenseDetailsUpdateResource.getHouseHoldExpenseInfo().isEmpty()) {
                                    houseHoldExpenseDetailsUpdateResource.setHouseHoldExpenseInfo(new ArrayList<HouseHoldExpenseInfoResource>());
                                    houseHoldExpenseDetailsUpdateResource.getHouseHoldExpenseInfo().add(i, new HouseHoldExpenseInfoResource());
                                } else {
                                    if ((houseHoldExpenseDetailsUpdateResource.getHouseHoldExpenseInfo().size() - 1) < i) {
                                        houseHoldExpenseDetailsUpdateResource.getHouseHoldExpenseInfo().add(i, new HouseHoldExpenseInfoResource());
                                    }
                                }
                            }
                            sField = houseHoldExpenseDetailsUpdateResource.getHouseHoldExpenseInfo().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(houseHoldExpenseDetailsUpdateResource.getHouseHoldExpenseInfo().get(index), error.getDefaultMessage());

                        } else {
                            sField = houseHoldExpenseDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(houseHoldExpenseDetailsUpdateResource.getClass().cast(houseHoldExpenseDetailsUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(houseHoldExpenseDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "salaryIncomeDetailsAddResource":
                    SalaryIncomeDetailsAddResource salaryIncomeDetailsAddResource = new SalaryIncomeDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("salaryIncomeDetailsList")) {
                            fieldName = fieldName.replace("salaryIncomeDetailsList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList() == null || salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().isEmpty()) {
                                    salaryIncomeDetailsAddResource.setSalaryIncomeDetailsList(new ArrayList<SalaryIncomeDetailsListResource>());
                                    salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().add(i, new SalaryIncomeDetailsListResource());
                                } else {
                                    if ((salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().size() - 1) < i) {
                                        salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().add(i, new SalaryIncomeDetailsListResource());
                                    }
                                }
                            }
                            if (fieldName.startsWith("salaryIncomeDocumentList")) {
                                fieldName = fieldName.replace("salaryIncomeDocumentList", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {
                                    if (salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().get(index).getSalaryIncomeDocumentList() == null || salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().get(index).getSalaryIncomeDocumentList().isEmpty()) {
                                        salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().get(index).setSalaryIncomeDocumentList(new ArrayList<DocumentAddResource>());
                                        salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().get(index).getSalaryIncomeDocumentList().add(i, new DocumentAddResource());
                                    } else {
                                        if ((salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().get(index).getSalaryIncomeDocumentList().size() - 1) < i) {
                                            salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().get(index).getSalaryIncomeDocumentList().add(i, new DocumentAddResource());
                                        }
                                    }
                                }
                                sField = salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().get(index).getSalaryIncomeDocumentList().get(index1).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().get(index).getSalaryIncomeDocumentList().get(index1), error.getDefaultMessage());
                            } else {
                                sField = salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().get(index), error.getDefaultMessage());
                            }
                        } else {
                            sField = salaryIncomeDetailsAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(salaryIncomeDetailsAddResource.getClass().cast(salaryIncomeDetailsAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(salaryIncomeDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "salaryIncomeDetailsUpdateResource":
                    SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource = new SalaryIncomeDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("salaryIncomeDocumentList")) {
                            fieldName = fieldName.replace("salaryIncomeDocumentList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (salaryIncomeDetailsUpdateResource.getSalaryIncomeDocumentList() == null || salaryIncomeDetailsUpdateResource.getSalaryIncomeDocumentList().isEmpty()) {
                                    salaryIncomeDetailsUpdateResource.setSalaryIncomeDocumentList(new ArrayList<DocumentUpdateResource>());
                                    salaryIncomeDetailsUpdateResource.getSalaryIncomeDocumentList().add(i, new DocumentUpdateResource());
                                } else {
                                    if ((salaryIncomeDetailsUpdateResource.getSalaryIncomeDocumentList().size() - 1) < i) {
                                        salaryIncomeDetailsUpdateResource.getSalaryIncomeDocumentList().add(i, new DocumentUpdateResource());
                                    }
                                }
                            }
                        } else if (fieldName.startsWith("version")) {
                            sField = salaryIncomeDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(salaryIncomeDetailsUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = salaryIncomeDetailsUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(salaryIncomeDetailsUpdateResource.getClass().getSuperclass().cast(salaryIncomeDetailsUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(salaryIncomeDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "businessAdditionalDetailsAddResource":
                    BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource = new BusinessAdditionalDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("businessDocumentList")) {
                            fieldName = fieldName.replace("businessDocumentList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessAdditionalDetailsAddResource.getBusinessDocumentList() == null || businessAdditionalDetailsAddResource.getBusinessDocumentList().isEmpty()) {
                                    businessAdditionalDetailsAddResource.setBusinessDocumentList(new ArrayList<DocumentAddResource>());
                                    businessAdditionalDetailsAddResource.getBusinessDocumentList().add(i, new DocumentAddResource());
                                } else {
                                    if ((businessAdditionalDetailsAddResource.getBusinessDocumentList().size() - 1) < i) {
                                        businessAdditionalDetailsAddResource.getBusinessDocumentList().add(i, new DocumentAddResource());
                                    }
                                }
                            }
                            sField = businessAdditionalDetailsAddResource.getBusinessDocumentList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessAdditionalDetailsAddResource.getBusinessDocumentList().get(index), error.getDefaultMessage());


                        } else if (fieldName.startsWith("businessAssetdetailList")) {
                            fieldName = fieldName.replace("businessAssetdetailList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessAdditionalDetailsAddResource.getBusinessAssetdetailList() == null || businessAdditionalDetailsAddResource.getBusinessAssetdetailList().isEmpty()) {
                                    businessAdditionalDetailsAddResource.setBusinessAssetdetailList(new ArrayList<BusinessAssetDetailResource>());
                                    businessAdditionalDetailsAddResource.getBusinessAssetdetailList().add(i, new BusinessAssetDetailResource());
                                } else {
                                    if ((businessAdditionalDetailsAddResource.getBusinessAssetdetailList().size() - 1) < i) {
                                        businessAdditionalDetailsAddResource.getBusinessAssetdetailList().add(i, new BusinessAssetDetailResource());
                                    }
                                }
                            }
                            sField = businessAdditionalDetailsAddResource.getBusinessAssetdetailList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessAdditionalDetailsAddResource.getBusinessAssetdetailList().get(index), error.getDefaultMessage());


                        } else if (fieldName.startsWith("businessEmploymentdetailList")) {
                            fieldName = fieldName.replace("businessEmploymentdetailList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessAdditionalDetailsAddResource.getBusinessEmploymentdetailList() == null || businessAdditionalDetailsAddResource.getBusinessEmploymentdetailList().isEmpty()) {
                                    businessAdditionalDetailsAddResource.setBusinessEmploymentdetailList(new ArrayList<BusinessEmploymentDetailResource>());
                                    businessAdditionalDetailsAddResource.getBusinessEmploymentdetailList().add(i, new BusinessEmploymentDetailResource());
                                } else {
                                    if ((businessAdditionalDetailsAddResource.getBusinessEmploymentdetailList().size() - 1) < i) {
                                        businessAdditionalDetailsAddResource.getBusinessEmploymentdetailList().add(i, new BusinessEmploymentDetailResource());
                                    }
                                }
                            }

                            sField = businessAdditionalDetailsAddResource.getBusinessEmploymentdetailList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessAdditionalDetailsAddResource.getBusinessEmploymentdetailList().get(index), error.getDefaultMessage());


                        } else if (fieldName.startsWith("businessClearancedetailList")) {
                            fieldName = fieldName.replace("businessClearancedetailList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessAdditionalDetailsAddResource.getBusinessClearancedetailList() == null || businessAdditionalDetailsAddResource.getBusinessClearancedetailList().isEmpty()) {
                                    businessAdditionalDetailsAddResource.setBusinessClearancedetailList(new ArrayList<BusinessClearanceDetailResource>());
                                    businessAdditionalDetailsAddResource.getBusinessClearancedetailList().add(i, new BusinessClearanceDetailResource());
                                } else {
                                    if ((businessAdditionalDetailsAddResource.getBusinessClearancedetailList().size() - 1) < i) {
                                        businessAdditionalDetailsAddResource.getBusinessClearancedetailList().add(i, new BusinessClearanceDetailResource());
                                    }
                                }
                            }

                            sField = businessAdditionalDetailsAddResource.getBusinessClearancedetailList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessAdditionalDetailsAddResource.getBusinessClearancedetailList().get(index), error.getDefaultMessage());


                        } else {
                            sField = businessAdditionalDetailsAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessAdditionalDetailsAddResource.getClass().cast(businessAdditionalDetailsAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(businessAdditionalDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessAdditionalDetailsUpdateResource":
                    BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource = new BusinessAdditionalDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("businessDocumentList")) {
                            fieldName = fieldName.replace("businessDocumentList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessAdditionalDetailsUpdateResource.getBusinessDocumentList() == null || businessAdditionalDetailsUpdateResource.getBusinessDocumentList().isEmpty()) {
                                    businessAdditionalDetailsUpdateResource.setBusinessDocumentList(new ArrayList<DocumentUpdateResource>());
                                    businessAdditionalDetailsUpdateResource.getBusinessDocumentList().add(i, new DocumentUpdateResource());
                                } else {
                                    if ((businessAdditionalDetailsUpdateResource.getBusinessDocumentList().size() - 1) < i) {
                                        businessAdditionalDetailsUpdateResource.getBusinessDocumentList().add(i, new DocumentUpdateResource());
                                    }
                                }
                            }
                            sField = businessAdditionalDetailsUpdateResource.getBusinessDocumentList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessAdditionalDetailsUpdateResource.getBusinessDocumentList().get(index), error.getDefaultMessage());


                        } else if (fieldName.startsWith("businessAssetdetailList")) {
                            fieldName = fieldName.replace("businessAssetdetailList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessAdditionalDetailsUpdateResource.getBusinessAssetdetailList() == null || businessAdditionalDetailsUpdateResource.getBusinessAssetdetailList().isEmpty()) {
                                    businessAdditionalDetailsUpdateResource.setBusinessAssetdetailList(new ArrayList<BusinessAssetDetailResource>());
                                    businessAdditionalDetailsUpdateResource.getBusinessAssetdetailList().add(i, new BusinessAssetDetailResource());
                                } else {
                                    if ((businessAdditionalDetailsUpdateResource.getBusinessAssetdetailList().size() - 1) < i) {
                                        businessAdditionalDetailsUpdateResource.getBusinessAssetdetailList().add(i, new BusinessAssetDetailResource());
                                    }
                                }
                            }
                            sField = businessAdditionalDetailsUpdateResource.getBusinessAssetdetailList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessAdditionalDetailsUpdateResource.getBusinessAssetdetailList().get(index), error.getDefaultMessage());


                        } else if (fieldName.startsWith("businessEmploymentdetailList")) {
                            fieldName = fieldName.replace("businessEmploymentdetailList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessAdditionalDetailsUpdateResource.getBusinessEmploymentdetailList() == null || businessAdditionalDetailsUpdateResource.getBusinessEmploymentdetailList().isEmpty()) {
                                    businessAdditionalDetailsUpdateResource.setBusinessEmploymentdetailList(new ArrayList<BusinessEmploymentDetailResource>());
                                    businessAdditionalDetailsUpdateResource.getBusinessEmploymentdetailList().add(i, new BusinessEmploymentDetailResource());
                                } else {
                                    if ((businessAdditionalDetailsUpdateResource.getBusinessEmploymentdetailList().size() - 1) < i) {
                                        businessAdditionalDetailsUpdateResource.getBusinessEmploymentdetailList().add(i, new BusinessEmploymentDetailResource());
                                    }
                                }
                            }

                            sField = businessAdditionalDetailsUpdateResource.getBusinessEmploymentdetailList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessAdditionalDetailsUpdateResource.getBusinessEmploymentdetailList().get(index), error.getDefaultMessage());


                        } else if (fieldName.startsWith("businessClearancedetailList")) {
                            fieldName = fieldName.replace("businessClearancedetailList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessAdditionalDetailsUpdateResource.getBusinessClearancedetailList() == null || businessAdditionalDetailsUpdateResource.getBusinessClearancedetailList().isEmpty()) {
                                    businessAdditionalDetailsUpdateResource.setBusinessClearancedetailList(new ArrayList<BusinessClearanceDetailResource>());
                                    businessAdditionalDetailsUpdateResource.getBusinessClearancedetailList().add(i, new BusinessClearanceDetailResource());
                                } else {
                                    if ((businessAdditionalDetailsUpdateResource.getBusinessClearancedetailList().size() - 1) < i) {
                                        businessAdditionalDetailsUpdateResource.getBusinessClearancedetailList().add(i, new BusinessClearanceDetailResource());
                                    }
                                }
                            }

                            sField = businessAdditionalDetailsUpdateResource.getBusinessClearancedetailList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessAdditionalDetailsUpdateResource.getBusinessClearancedetailList().get(index), error.getDefaultMessage());

                        } else if (fieldName.startsWith("version")) {
                            sField = businessAdditionalDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessAdditionalDetailsUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = businessAdditionalDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessAdditionalDetailsUpdateResource.getClass().cast(businessAdditionalDetailsUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(businessAdditionalDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "cultivationIncomeDetailsAddResource":
                    CultivationIncomeDetailsAddResource cultivationIncomeDetailsAddResource = new CultivationIncomeDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("cultivationIncomeDetailsList")) {
                            fieldName = fieldName.replace("cultivationIncomeDetailsList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList() == null || cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().isEmpty()) {
                                    cultivationIncomeDetailsAddResource.setCultivationIncomeDetailsList(new ArrayList<CultivationIncomeDetailsListResource>());
                                    cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().add(i, new CultivationIncomeDetailsListResource());
                                } else {
                                    if ((cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().size() - 1) < i) {
                                        cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().add(i, new CultivationIncomeDetailsListResource());
                                    }
                                }
                            }
                            if (fieldName.startsWith("cultivationIncomeDocumentList")) {
                                fieldName = fieldName.replace("cultivationIncomeDocumentList", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {
                                    if (cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().get(index).getCultivationIncomeDocumentList() == null || cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().get(index).getCultivationIncomeDocumentList().isEmpty()) {
                                        cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().get(index).setCultivationIncomeDocumentList(new ArrayList<DocumentAddResource>());
                                        cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().get(index).getCultivationIncomeDocumentList().add(i, new DocumentAddResource());
                                    } else {
                                        if ((cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().get(index).getCultivationIncomeDocumentList().size() - 1) < i) {
                                            cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().get(index).getCultivationIncomeDocumentList().add(i, new DocumentAddResource());
                                        }
                                    }
                                }
                                sField = cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().get(index).getCultivationIncomeDocumentList().get(index1).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().get(index).getCultivationIncomeDocumentList().get(index1), error.getDefaultMessage());
                            } else {
                                sField = cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().get(index), error.getDefaultMessage());
                            }
                        } else {
                            sField = cultivationIncomeDetailsAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(cultivationIncomeDetailsAddResource.getClass().cast(cultivationIncomeDetailsAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(cultivationIncomeDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);


                case "cultivationIncomeDetailsUpdateResource":
                    CultivationIncomeDetailsUpdateResource cultivationIncomeDetailsUpdateResource = new CultivationIncomeDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("cultivationIncomeDocumentList")) {
                            fieldName = fieldName.replace("cultivationIncomeDocumentList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (cultivationIncomeDetailsUpdateResource.getCultivationIncomeDocumentList() == null || cultivationIncomeDetailsUpdateResource.getCultivationIncomeDocumentList().isEmpty()) {
                                    cultivationIncomeDetailsUpdateResource.setCultivationIncomeDocumentList(new ArrayList<DocumentUpdateResource>());
                                    cultivationIncomeDetailsUpdateResource.getCultivationIncomeDocumentList().add(i, new DocumentUpdateResource());
                                } else {
                                    if ((cultivationIncomeDetailsUpdateResource.getCultivationIncomeDocumentList().size() - 1) < i) {
                                        cultivationIncomeDetailsUpdateResource.getCultivationIncomeDocumentList().add(i, new DocumentUpdateResource());
                                    }
                                }
                            }
                            if (fieldName.startsWith("documentId") || fieldName.startsWith("documentName") || fieldName.startsWith("status")) {
                                sField = cultivationIncomeDetailsUpdateResource.getCultivationIncomeDocumentList().get(index).getClass().getSuperclass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(cultivationIncomeDetailsUpdateResource.getCultivationIncomeDocumentList().get(index).getClass().getSuperclass().cast(cultivationIncomeDetailsUpdateResource.getCultivationIncomeDocumentList().get(index)), error.getDefaultMessage());
//
//		                        	   sField =  cultivationIncomeDetailsUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
//			                            sField.setAccessible(true);
//			                            sField.set(cultivationIncomeDetailsUpdateResource.getClass().getSuperclass().cast(cultivationIncomeDetailsUpdateResource), error.getDefaultMessage());


                            } else {
                                sField = cultivationIncomeDetailsUpdateResource.getCultivationIncomeDocumentList().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(cultivationIncomeDetailsUpdateResource.getCultivationIncomeDocumentList().get(index), error.getDefaultMessage());
                            }

                        } else {
                            sField = cultivationIncomeDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(cultivationIncomeDetailsUpdateResource.getClass().cast(cultivationIncomeDetailsUpdateResource), error.getDefaultMessage());
                        }
//
                    }
                    return new ResponseEntity<>(cultivationIncomeDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "cultivationIncomeExpensesAddResource":
                    CultivationIncomeExpensesAddResource cultivationIncomeExpensesAddResource = new CultivationIncomeExpensesAddResource();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("incomeOrExpenseDetails")) {
                            fieldName = fieldName.replace("incomeOrExpenseDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (cultivationIncomeExpensesAddResource.getIncomeOrExpenseDetails() == null || cultivationIncomeExpensesAddResource.getIncomeOrExpenseDetails().isEmpty()) {
                                    cultivationIncomeExpensesAddResource.setIncomeOrExpenseDetails(new ArrayList<CultivationIncomeExpenseResource>());
                                    cultivationIncomeExpensesAddResource.getIncomeOrExpenseDetails().add(i, new CultivationIncomeExpenseResource());
                                } else {
                                    if ((cultivationIncomeExpensesAddResource.getIncomeOrExpenseDetails().size() - 1) < i) {
                                        cultivationIncomeExpensesAddResource.getIncomeOrExpenseDetails().add(i, new CultivationIncomeExpenseResource());
                                    }
                                }
                            }

                            sField = cultivationIncomeExpensesAddResource.getIncomeOrExpenseDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(cultivationIncomeExpensesAddResource.getIncomeOrExpenseDetails().get(index), error.getDefaultMessage());

                        } else {
                            sField = cultivationIncomeExpensesAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(cultivationIncomeExpensesAddResource.getClass().cast(cultivationIncomeExpensesAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(cultivationIncomeExpensesAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "cultivationIncomeExpensesUpdateResource":
                    CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResource = new CultivationIncomeExpensesUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        sField = cultivationIncomeExpensesUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(cultivationIncomeExpensesUpdateResource, error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(cultivationIncomeExpensesUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "otherIncomeExpensesAddResource":
                    OtherIncomeExpensesAddResource otherIncomeExpensesAddResource = new OtherIncomeExpensesAddResource();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("otherIncomeExpenses")) {
                            fieldName = fieldName.replace("otherIncomeExpenses", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (otherIncomeExpensesAddResource.getOtherIncomeExpenses() == null
                                        || otherIncomeExpensesAddResource.getOtherIncomeExpenses().isEmpty()) {
                                    otherIncomeExpensesAddResource
                                            .setOtherIncomeExpenses(new ArrayList<OtherIncomeExpensesResource>());
                                    otherIncomeExpensesAddResource.getOtherIncomeExpenses().add(i,
                                            new OtherIncomeExpensesResource());
                                } else {
                                    if ((otherIncomeExpensesAddResource.getOtherIncomeExpenses().size() - 1) < i) {
                                        otherIncomeExpensesAddResource.getOtherIncomeExpenses().add(i,
                                                new OtherIncomeExpensesResource());
                                    }
                                }
                            }
                            sField = otherIncomeExpensesAddResource.getOtherIncomeExpenses().get(index).getClass()
                                    .getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(otherIncomeExpensesAddResource.getOtherIncomeExpenses().get(index),
                                    error.getDefaultMessage());
                        } else {
                            sField = otherIncomeExpensesAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(otherIncomeExpensesAddResource.getClass().cast(otherIncomeExpensesAddResource),
                                    error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(otherIncomeExpensesAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "otherIncomeExpensesResource":
                    OtherIncomeExpensesResource otherIncomeExpensesResource = new OtherIncomeExpensesResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance()
                                .logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        sField = otherIncomeExpensesResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(otherIncomeExpensesResource, error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(otherIncomeExpensesResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "businessIncomeDetailsAddResource":
                    BusinessIncomeDetailsAddResource businessIncomeDetailsAddResource = new BusinessIncomeDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("businessIncomeDocumentList")) {
                            fieldName = fieldName.replace("businessIncomeDocumentList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessIncomeDetailsAddResource.getBusinessIncomeDocumentList() == null || businessIncomeDetailsAddResource.getBusinessIncomeDocumentList().isEmpty()) {
                                    businessIncomeDetailsAddResource.setBusinessIncomeDocumentList(new ArrayList<DocumentAddResource>());
                                    businessIncomeDetailsAddResource.getBusinessIncomeDocumentList().add(i, new DocumentAddResource());
                                } else {
                                    if ((businessIncomeDetailsAddResource.getBusinessIncomeDocumentList().size() - 1) < i) {
                                        businessIncomeDetailsAddResource.getBusinessIncomeDocumentList().add(i, new DocumentAddResource());
                                    }
                                }
                            }
                            sField = businessIncomeDetailsAddResource.getBusinessIncomeDocumentList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessIncomeDetailsAddResource.getBusinessIncomeDocumentList().get(index), error.getDefaultMessage());


                        } else {
                            sField = businessIncomeDetailsAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessIncomeDetailsAddResource.getClass().cast(businessIncomeDetailsAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(businessIncomeDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessIncomeDetailsUpdateResource":
                    BusinessIncomeDetailsUpdateResource businessIncomeDetailsUpdateResource = new BusinessIncomeDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("businessIncomeDocumentList")) {
                            fieldName = fieldName.replace("businessIncomeDocumentList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessIncomeDetailsUpdateResource.getBusinessIncomeDocumentList() == null || businessIncomeDetailsUpdateResource.getBusinessIncomeDocumentList().isEmpty()) {
                                    businessIncomeDetailsUpdateResource.setBusinessIncomeDocumentList(new ArrayList<DocumentUpdateResource>());
                                    businessIncomeDetailsUpdateResource.getBusinessIncomeDocumentList().add(i, new DocumentUpdateResource());
                                } else {
                                    if ((businessIncomeDetailsUpdateResource.getBusinessIncomeDocumentList().size() - 1) < i) {
                                        businessIncomeDetailsUpdateResource.getBusinessIncomeDocumentList().add(i, new DocumentUpdateResource());
                                    }
                                }
                            }
                            sField = businessIncomeDetailsUpdateResource.getBusinessIncomeDocumentList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessIncomeDetailsUpdateResource.getBusinessIncomeDocumentList().get(index), error.getDefaultMessage());

                        } else if (fieldName.startsWith("version")) {
                            sField = businessIncomeDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessIncomeDetailsUpdateResource, error.getDefaultMessage());
                        } else {
                            sField = businessIncomeDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessIncomeDetailsUpdateResource.getClass().cast(businessIncomeDetailsUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(businessIncomeDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessIncomeExpensesAddResource":
                    BusinessIncomeExpensesAddResource businessIncomeExpensesAddResource = new BusinessIncomeExpensesAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("businessIncomeExenseTypes")) {
                            fieldName = fieldName.replace("businessIncomeExenseTypes", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessIncomeExpensesAddResource.getBusinessIncomeExenseTypes() == null || businessIncomeExpensesAddResource.getBusinessIncomeExenseTypes().isEmpty()) {
                                    businessIncomeExpensesAddResource.setBusinessIncomeExenseTypes(new ArrayList<BusinessIncomeExpenseTypeResource>());
                                    businessIncomeExpensesAddResource.getBusinessIncomeExenseTypes().add(i, new BusinessIncomeExpenseTypeResource());
                                } else {
                                    if ((businessIncomeExpensesAddResource.getBusinessIncomeExenseTypes().size() - 1) < i) {
                                        businessIncomeExpensesAddResource.getBusinessIncomeExenseTypes().add(i, new BusinessIncomeExpenseTypeResource());
                                    }
                                }
                            }
                            sField = businessIncomeExpensesAddResource.getBusinessIncomeExenseTypes().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessIncomeExpensesAddResource.getBusinessIncomeExenseTypes().get(index), error.getDefaultMessage());


                        } else {
                            sField = businessIncomeExpensesAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessIncomeExpensesAddResource.getClass().cast(businessIncomeExpensesAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(businessIncomeExpensesAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessIncomeExpenseTypeResource":
                    BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = new BusinessIncomeExpenseTypeResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = businessIncomeExpenseTypeResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessIncomeExpenseTypeResource, error.getDefaultMessage());
                        } else {
                            sField = businessIncomeExpenseTypeResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessIncomeExpenseTypeResource.getClass().getSuperclass().cast(businessIncomeExpenseTypeResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(businessIncomeExpenseTypeResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessTaxDetailsAddResource":
                    BusinessTaxDetailsAddResource businessTaxDetailsAddResource = new BusinessTaxDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("businessTaxDetails")) {
                            fieldName = fieldName.replace("businessTaxDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (businessTaxDetailsAddResource.getBusinessTaxDetails() == null || businessTaxDetailsAddResource.getBusinessTaxDetails().isEmpty()) {
                                    businessTaxDetailsAddResource.setBusinessTaxDetails(new ArrayList<BusinessTaxTypeResource>());
                                    businessTaxDetailsAddResource.getBusinessTaxDetails().add(i, new BusinessTaxTypeResource());
                                } else {
                                    if ((businessTaxDetailsAddResource.getBusinessTaxDetails().size() - 1) < i) {
                                        businessTaxDetailsAddResource.getBusinessTaxDetails().add(i, new BusinessTaxTypeResource());
                                    }
                                }
                            }
                            sField = businessTaxDetailsAddResource.getBusinessTaxDetails().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(businessTaxDetailsAddResource.getBusinessTaxDetails().get(index), error.getDefaultMessage());


                        } else {
                            sField = businessTaxDetailsAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessTaxDetailsAddResource.getClass().cast(businessTaxDetailsAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(businessTaxDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "businessTaxTypeResource":
                    BusinessTaxTypeResource businessTaxTypeResource = new BusinessTaxTypeResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("version")) {
                            sField = businessTaxTypeResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessTaxTypeResource, error.getDefaultMessage());
                        } else {
                            sField = businessTaxTypeResource.getClass().getSuperclass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(businessTaxTypeResource.getClass().getSuperclass().cast(businessTaxTypeResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(businessTaxTypeResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "financeStatementAddRequest":
                    FinanceStatementAddRequest financeStatementAddRequest = new FinanceStatementAddRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("documentList")) {
                            fieldName = fieldName.replace("documentList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (financeStatementAddRequest.getDocumentList() == null || financeStatementAddRequest.getDocumentList().isEmpty()) {
                                    financeStatementAddRequest.setDocumentList(new ArrayList<DocumentRefAddResource>());
                                    financeStatementAddRequest.getDocumentList().add(i, new DocumentRefAddResource());
                                } else {
                                    if ((financeStatementAddRequest.getDocumentList().size() - 1) < i) {
                                        financeStatementAddRequest.getDocumentList().add(i, new DocumentRefAddResource());
                                    }
                                }
                            }
                            if (fieldName.startsWith("documentId") || fieldName.startsWith("documentName") || fieldName.startsWith("status")) {
                                sField = financeStatementAddRequest.getDocumentList().get(index).getClass().getSuperclass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(financeStatementAddRequest.getDocumentList().get(index).getClass().getSuperclass().cast(financeStatementAddRequest.getDocumentList().get(index)), error.getDefaultMessage());

                            } else {
                                sField = financeStatementAddRequest.getDocumentList().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(financeStatementAddRequest.getDocumentList().get(index), error.getDefaultMessage());
                            }


                        } else {
                            sField = financeStatementAddRequest.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(financeStatementAddRequest.getClass().cast(financeStatementAddRequest), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(financeStatementAddRequest, HttpStatus.UNPROCESSABLE_ENTITY);

                case "financeStatementUpdateRequest":
                    FinanceStatementUpdateRequest financeStatementUpdateRequest = new FinanceStatementUpdateRequest();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        sField = financeStatementUpdateRequest.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(financeStatementUpdateRequest.getClass().cast(financeStatementUpdateRequest), error.getDefaultMessage());

                    }
                    return new ResponseEntity<>(financeStatementUpdateRequest, HttpStatus.UNPROCESSABLE_ENTITY);
                case "financialStatementDetailMainResource":
                    FinancialStatementDetailMainResource financialStatementDetailMainResource = new FinancialStatementDetailMainResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("statementDetailList")) {
                            fieldName = fieldName.replace("statementDetailList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (financialStatementDetailMainResource.getStatementDetailList() == null || financialStatementDetailMainResource.getStatementDetailList().isEmpty()) {
                                    financialStatementDetailMainResource.setStatementDetailList(new ArrayList<FinancialStatementDetailAddResource>());
                                    financialStatementDetailMainResource.getStatementDetailList().add(i, new FinancialStatementDetailAddResource());
                                } else {
                                    if ((financialStatementDetailMainResource.getStatementDetailList().size() - 1) < i) {
                                        financialStatementDetailMainResource.getStatementDetailList().add(i, new FinancialStatementDetailAddResource());
                                    }
                                }
                            }
                            if (fieldName.startsWith("noteList")) {
                                fieldName = fieldName.replace("noteList", "");
                                atPoint = fieldName.indexOf(']');
                                index1 = Integer.parseInt(fieldName.substring(1, atPoint));
                                fieldName = fieldName.substring(atPoint + 2);
                                for (int i = 0; i <= index1; i++) {
                                    if (financialStatementDetailMainResource.getStatementDetailList().get(index).getNoteList() == null || financialStatementDetailMainResource.getStatementDetailList().get(index).getNoteList().isEmpty()) {
                                        financialStatementDetailMainResource.getStatementDetailList().get(index).setNoteList(new ArrayList<FinancialStatementDetailNoteRequest>());
                                        financialStatementDetailMainResource.getStatementDetailList().get(index).getNoteList().add(i, new FinancialStatementDetailNoteRequest());
                                    } else {
                                        if ((financialStatementDetailMainResource.getStatementDetailList().get(index).getNoteList().size() - 1) < i) {
                                            financialStatementDetailMainResource.getStatementDetailList().get(index).getNoteList().add(i, new FinancialStatementDetailNoteRequest());
                                        }
                                    }
                                }
                                sField = financialStatementDetailMainResource.getStatementDetailList().get(index).getNoteList().get(index1).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(financialStatementDetailMainResource.getStatementDetailList().get(index).getNoteList().get(index1), error.getDefaultMessage());
                            } else {
                                sField = financialStatementDetailMainResource.getStatementDetailList().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(financialStatementDetailMainResource.getStatementDetailList().get(index), error.getDefaultMessage());
                            }

                        } else {
                            sField = financialStatementDetailMainResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(financialStatementDetailMainResource.getClass().cast(financialStatementDetailMainResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(financialStatementDetailMainResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "financialStatementDetailUpdateMainResource":
                	
            		Map<String, String> errors = new HashMap<>();
            		ex.getBindingResult().getAllErrors().forEach(error -> {
            			String fieldNm = ((FieldError) error).getField();
            			String errorMessage = error.getDefaultMessage();
            			errors.put(fieldNm, errorMessage);
            		});
                    return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);   
           
                case "salaryIncomeExpensesAddResource":
                    SalaryIncomeExpensesAddResource salaryIncomeExpensesAddResource = new SalaryIncomeExpensesAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        sField = salaryIncomeExpensesAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(salaryIncomeExpensesAddResource, error.getDefaultMessage());

                    }
                    return new ResponseEntity<>(salaryIncomeExpensesAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "salaryIncomeExpensesUpdateResource":
                    SalaryIncomeExpensesUpdateResource salaryIncomeExpensesUpdateResource = new SalaryIncomeExpensesUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        sField = salaryIncomeExpensesUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(salaryIncomeExpensesUpdateResource, error.getDefaultMessage());

                    }
                    return new ResponseEntity<>(salaryIncomeExpensesUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                case "creditAppraisalDisbursmentDetailsAddResource":
                    CreditAppraisalDisbursmentDetailsAddResource creditAppraisalDisbursmentDetailsAddResource = new CreditAppraisalDisbursmentDetailsAddResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        sField = creditAppraisalDisbursmentDetailsAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(creditAppraisalDisbursmentDetailsAddResource, error.getDefaultMessage());

                    }
                    return new ResponseEntity<>(creditAppraisalDisbursmentDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "creditAppraisalDisbursmentDetailsUpdateResource":
                    CreditAppraisalDisbursmentDetailsUpdateResource creditAppraisalDisbursmentDetailsUpdateResource = new CreditAppraisalDisbursmentDetailsUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        sField = creditAppraisalDisbursmentDetailsUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(creditAppraisalDisbursmentDetailsUpdateResource, error.getDefaultMessage());

                    }

                    return new ResponseEntity<>(creditAppraisalDisbursmentDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                case "internalLiabilityAddResourceList":

                    List<InternalLiabilityAddResource> liabilityList = new ArrayList<>();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        fieldName = error.getField();
                        if (fieldName.startsWith("internalLiabilityAddResourceList")) {
                            fieldName = fieldName.replace("internalLiabilityAddResourceList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (liabilityList.isEmpty()) {
                                    liabilityList.add(i,
                                            new InternalLiabilityAddResource());
                                } else {
                                    if ((liabilityList.size() - 1) < i) {
                                        liabilityList.add(i,
                                                new InternalLiabilityAddResource());
                                    }
                                }
                            }

                            sField = liabilityList.get(index).getClass()
                                    .getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(liabilityList.get(index), error.getDefaultMessage());

                        }
                    }
                    return new ResponseEntity<>(liabilityList, HttpStatus.UNPROCESSABLE_ENTITY);

                case "documentCheckListAddResource":
                    DocumentCheckListAddResource documentCheckListAddResource = new DocumentCheckListAddResource();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("documentDetails")) {
                            fieldName = fieldName.replace("documentDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (documentCheckListAddResource.getDocumentDetails() == null || documentCheckListAddResource.getDocumentDetails().isEmpty()) {
                                    documentCheckListAddResource.setDocumentDetails(new ArrayList<DocumentAddResource>());
                                    documentCheckListAddResource.getDocumentDetails().add(i, new DocumentAddResource());
                                } else {
                                    if ((documentCheckListAddResource.getDocumentDetails().size() - 1) < i) {
                                        documentCheckListAddResource.getDocumentDetails().add(i, new DocumentAddResource());
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
                        fieldName = error.getField();
                        if (fieldName.startsWith("documentDetails")) {
                            fieldName = fieldName.replace("documentDetails", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (documentCheckListUpdateResource.getDocumentDetails() == null || documentCheckListUpdateResource.getDocumentDetails().isEmpty()) {
                                    documentCheckListUpdateResource.setDocumentDetails(new ArrayList<DocumentUpdateResource>());
                                    documentCheckListUpdateResource.getDocumentDetails().add(i, new DocumentUpdateResource());
                                } else {
                                    if ((documentCheckListUpdateResource.getDocumentDetails().size() - 1) < i) {
                                        documentCheckListUpdateResource.getDocumentDetails().add(i, new DocumentUpdateResource());
                                    }
                                }
                            }
                            if (fieldName.startsWith("version") || fieldName.startsWith("id")) {
                                sField = documentCheckListUpdateResource.getDocumentDetails().get(index).getClass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(documentCheckListUpdateResource.getDocumentDetails().get(index), error.getDefaultMessage());

                            } else {
                                sField = documentCheckListUpdateResource.getDocumentDetails().get(index).getClass().getSuperclass().getDeclaredField(fieldName);
                                sField.setAccessible(true);
                                sField.set(documentCheckListUpdateResource.getDocumentDetails().get(index).getClass().getSuperclass().cast(documentCheckListUpdateResource.getDocumentDetails().get(index)), error.getDefaultMessage());
                            }
                        } else {
                            sField = documentCheckListUpdateResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            //sField.set(documentCheckListUpdateResource, error.getDefaultMessage());
                            sField.set(documentCheckListUpdateResource.getClass().cast(documentCheckListUpdateResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(documentCheckListUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                    
                case "guarantorSalaryIncomeAddResource":
                	GuarantorSalaryIncomeAddResource guarantorSalaryIncomeAddResource = new GuarantorSalaryIncomeAddResource();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("guSalaryIncomeDetailsList")) {
                            fieldName = fieldName.replace("guSalaryIncomeDetailsList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (guarantorSalaryIncomeAddResource.getGuSalaryIncomeDetailsList() == null || guarantorSalaryIncomeAddResource.getGuSalaryIncomeDetailsList().isEmpty()) {
                                	guarantorSalaryIncomeAddResource.setGuSalaryIncomeDetailsList(new ArrayList<GuarantorSalaryIncomeListResource>());
                                	guarantorSalaryIncomeAddResource.getGuSalaryIncomeDetailsList().add(i, new GuarantorSalaryIncomeListResource());
                                } else {
                                    if ((guarantorSalaryIncomeAddResource.getGuSalaryIncomeDetailsList().size() - 1) < i) {
                                    	guarantorSalaryIncomeAddResource.getGuSalaryIncomeDetailsList().add(i, new GuarantorSalaryIncomeListResource());
                                    }
                                }
                            }
                            sField = guarantorSalaryIncomeAddResource.getGuSalaryIncomeDetailsList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(guarantorSalaryIncomeAddResource.getGuSalaryIncomeDetailsList().get(index), error.getDefaultMessage());

                        } else {
                            sField = guarantorSalaryIncomeAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(guarantorSalaryIncomeAddResource.getClass().cast(guarantorSalaryIncomeAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(guarantorSalaryIncomeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                    
                case "guarantorSalaryIncomeUpdateResource":
                	GuarantorSalaryIncomeUpdateResource guarantorSalaryIncomeUpdateResource = new GuarantorSalaryIncomeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        sField = guarantorSalaryIncomeUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(guarantorSalaryIncomeUpdateResource, error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(guarantorSalaryIncomeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                    
                case "guarantorOtherIncomeAddResource":
                	GuarantorOtherIncomeAddResource guarantorOtherIncomeAddResource = new GuarantorOtherIncomeAddResource();

                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        if (fieldName.startsWith("guOtherIncomeDetailsList")) {
                            fieldName = fieldName.replace("guOtherIncomeDetailsList", "");
                            atPoint = fieldName.indexOf(']');
                            index = Integer.parseInt(fieldName.substring(1, atPoint));
                            fieldName = fieldName.substring(atPoint + 2);
                            for (int i = 0; i <= index; i++) {
                                if (guarantorOtherIncomeAddResource.getGuOtherIncomeDetailsList() == null || guarantorOtherIncomeAddResource.getGuOtherIncomeDetailsList().isEmpty()) {
                                	guarantorOtherIncomeAddResource.setGuOtherIncomeDetailsList(new ArrayList<GuarantorOtherIncomeListResource>());
                                	guarantorOtherIncomeAddResource.getGuOtherIncomeDetailsList().add(i, new GuarantorOtherIncomeListResource());
                                } else {
                                    if ((guarantorOtherIncomeAddResource.getGuOtherIncomeDetailsList().size() - 1) < i) {
                                    	guarantorOtherIncomeAddResource.getGuOtherIncomeDetailsList().add(i, new GuarantorOtherIncomeListResource());
                                    }
                                }
                            }
                            sField = guarantorOtherIncomeAddResource.getGuOtherIncomeDetailsList().get(index).getClass().getDeclaredField(fieldName);
                            sField.setAccessible(true);
                            sField.set(guarantorOtherIncomeAddResource.getGuOtherIncomeDetailsList().get(index), error.getDefaultMessage());

                        } else {
                            sField = guarantorOtherIncomeAddResource.getClass().getDeclaredField(error.getField());
                            sField.setAccessible(true);
                            sField.set(guarantorOtherIncomeAddResource.getClass().cast(guarantorOtherIncomeAddResource), error.getDefaultMessage());
                        }
                    }
                    return new ResponseEntity<>(guarantorOtherIncomeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
                    
                case "guarantorOtherIncomeUpdateResource":
                	GuarantorOtherIncomeUpdateResource guarantorOtherIncomeUpdateResource = new GuarantorOtherIncomeUpdateResource();
                    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                        fieldName = error.getField();
                        sField = guarantorOtherIncomeUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(guarantorOtherIncomeUpdateResource, error.getDefaultMessage());
                    }
                    return new ResponseEntity<>(guarantorOtherIncomeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
                    
//                case "guarantorBusinessIncomeAddResource":
//                	GuarantorBusinessIncomeAddResource guarantorBusinessIncomeAddResource = new GuarantorBusinessIncomeAddResource();
//                	
//                	for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//                		LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
//                		fieldName = error.getField();
//                		if (fieldName.startsWith("guBusinessIncomeDetailsList")) {
//                			fieldName = fieldName.replace("guBusinessIncomeDetailsList", "");
//                			atPoint = fieldName.indexOf(']');
//                			index = Integer.parseInt(fieldName.substring(1, atPoint));
//                			fieldName = fieldName.substring(atPoint + 2);
//                			for (int i = 0; i <= index; i++) {
//                				if (guarantorBusinessIncomeAddResource.getGuBusinessIncomeDetailsList() == null || guarantorBusinessIncomeAddResource.getGuBusinessIncomeDetailsList().isEmpty()) {
//                					guarantorBusinessIncomeAddResource.setGuBusinessIncomeDetailsList(new ArrayList<GuarantorBusinessIncomeListResource>());
//                					guarantorBusinessIncomeAddResource.getGuBusinessIncomeDetailsList().add(i, new GuarantorBusinessIncomeListResource());
//                				} else {
//                					if ((guarantorBusinessIncomeAddResource.getGuBusinessIncomeDetailsList().size() - 1) < i) {
//                						guarantorBusinessIncomeAddResource.getGuBusinessIncomeDetailsList().add(i, new GuarantorBusinessIncomeListResource());
//                					}
//                				}
//                			}
//                			sField = guarantorBusinessIncomeAddResource.getGuBusinessIncomeDetailsList().get(index).getClass().getDeclaredField(fieldName);
//                			sField.setAccessible(true);
//                			sField.set(guarantorBusinessIncomeAddResource.getGuBusinessIncomeDetailsList().get(index), error.getDefaultMessage());
//                			
//                		} else {
//                			sField = guarantorBusinessIncomeAddResource.getClass().getDeclaredField(error.getField());
//                			sField.setAccessible(true);
//                			sField.set(guarantorBusinessIncomeAddResource.getClass().cast(guarantorBusinessIncomeAddResource), error.getDefaultMessage());
//                		}
//                	}
//                	return new ResponseEntity<>(guarantorBusinessIncomeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
//                	
//                case "guarantorBusinessIncomeUpdateResource":
//                	GuarantorBusinessIncomeUpdateResource guarantorBusinessIncomeUpdateResource = new GuarantorBusinessIncomeUpdateResource();
//                	for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//                		LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
//                		fieldName = error.getField();
//                		sField = guarantorBusinessIncomeUpdateResource.getClass().getDeclaredField(error.getField());
//                		sField.setAccessible(true);
//                		sField.set(guarantorBusinessIncomeUpdateResource, error.getDefaultMessage());
//                	}
//                	return new ResponseEntity<>(guarantorBusinessIncomeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

                default:
                    return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } catch (Exception e) {
            SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
            successAndErrorDetailsResource.setMessages(environment.getProperty(commonInternalServerError));
            successAndErrorDetailsResource.setDetails(e.getMessage());
            RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
            if (mapperEnum != null)
                successAndErrorDetailsResource.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
            TenantHolder.clear();
            LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsResource.getCode() + " " + successAndErrorDetailsResource.getMessages() + " " + successAndErrorDetailsResource.getDetails());
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        if (ex.getMessage().contains("org.hibernate.QueryException: could not resolve property: sort of")) {
            SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
            successAndErrorDetailsResource.setMessages(environment.getProperty("invalid.sort"));
            TenantHolder.clear();
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        successAndErrorDetailsDataObject.setMessages(environment.getProperty(commonInternalServerError));
        successAndErrorDetailsDataObject.setDetails(ex.getMessage());
        RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
        if (mapperEnum != null)
            successAndErrorDetailsDataObject.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
        TenantHolder.clear();
        LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsDataObject.getCode() + " " + successAndErrorDetailsDataObject.getMessages() + " " + successAndErrorDetailsDataObject.getDetails());
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InvalidServiceIdException.class})
    public ResponseEntity<Object> invalidServiceIdException(InvalidServiceIdException ex, WebRequest request) {
        ValidateResource validateResource = new ValidateResource();
        RiskGradingAddResource riskGradingAddResource = new RiskGradingAddResource();

        LoggerRequest.getInstance().logInfo(project + " " + ex.getServiceEntity() + " " + ex.getMessage());
        switch (ex.getServiceEntity()) {
            case BUSINESS_TYPE_ID:
                validateResource.setUserId(ex.getMessage());
                break;

            case COMPANY:
                validateResource.setCompanyId(ex.getMessage());
                break;

            case CONTACT_TYPE:
                validateResource.setContactTypeId(ex.getMessage());
                break;

            case IDENTIFICATION_TYPE:
                validateResource.setIdentificationTypeId(ex.getMessage());
                break;

            case ASSET_TYPE_ID:
                validateResource.setSecurityTypeId(ex.getMessage());
                break;

            case ASSET_SUB_TYPE_ID:
                validateResource.setSecuritySubTypeId(ex.getMessage());
                break;
            case ADDRESS_TYPE:
                validateResource.setAddressTypeId(ex.getMessage());
                break;
            case ADDRESS_COUNTRY:
                validateResource.setCountryGeoId(ex.getMessage());
                break;
            case GEO_LEVEL_ID:
                validateResource.setGeoLevelId(ex.getMessage());
                break;
            case CONTACT_VALUE:
                validateResource.setContactNo(ex.getMessage());
                break;

            case IDENTIFICATION_NO:
                validateResource.setIdentificationNo(ex.getMessage());
                break;

            case PENDING_IDENTIFICATION_ID:
                validateResource.setPedingIdentificationId(ex.getMessage());
                break;
            case SECTOR:
                validateResource.setSectorId(ex.getMessage());
                break;
            case SUBSECTOR:
                validateResource.setSubSectorId(ex.getMessage());
                break;
            case BANK:
                validateResource.setBankId(ex.getMessage());
                break;
            case BANK_BRANCH:
                validateResource.setBankBranchId(ex.getMessage());
                break;
            case BUSINESS_PERSON_TYPE:
                riskGradingAddResource.setBusinessPersonTypeId(ex.getMessage());
                break;
            case INDUSTRY_TYPE:
                validateResource.setIndustryTypeId(ex.getMessage());
                break;
            case EMPLOYER_ID:
                validateResource.setEmployerId(ex.getMessage());
                break;
            case USER_PROFILE:
                validateResource.setUserId(ex.getMessage());
                break;
            case TAX_CODE_ID:
                validateResource.setTaxCodeId(ex.getMessage());
                break;
            case LANGUAGE_ID:
                validateResource.setLanguageId(ex.getMessage());
                break;
            case BRANCH_ID:
                validateResource.setBranchId(ex.getMessage());
                break;
            case FINANCIAL_STATEMENT_TEMPLATE_FORMULA:
                validateResource.setFormula(ex.getMessage());
                break;
            case FINANCIAL_STATEMENT_LEVEL:
                validateResource.setFinancialStatementLevel(ex.getMessage());
                break;
            case PRODUCT_ID:
                validateResource.setProductId(ex.getMessage());
                ;
                break;
            case SUB_PRODUCT_ID:
                validateResource.setSubProductId(ex.getMessage());
                break;
            case CODE:
                validateResource.setCode(ex.getMessage());
                break;
            case VERSION:
                validateResource.setVersion(ex.getMessage());
                break;
            default:

        }
        return new ResponseEntity<>(validateResource, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({TableRelatedPrimitiveValidationException.class})
    public ResponseEntity<Object> listRecordValidateException(TableRelatedPrimitiveValidationException ex, WebRequest request) {

        TenantHolder.clear();
        return new ResponseEntity<>(ex.getResource(), HttpStatus.UNPROCESSABLE_ENTITY);
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
            CommonResponseHandler successAndErrorDetailsDto = new CommonResponseHandler();
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

    @ExceptionHandler({OptimisticLockException.class})
    public ResponseEntity<Object> optimisticLockException(OptimisticLockException ex, WebRequest request) {
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
            CommonResponseHandler successAndErrorDetailsDto = new CommonResponseHandler();
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

    @ExceptionHandler({DetailValidateException.class})
    public ResponseEntity<Object> detailValidateException(DetailValidateException ex, WebRequest request) {
        GuarantorIncomeRequestResource guarantorIncomeRequestResource = null;

        CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource = null;
        CreditAppraiselCollateralDetailsUpdateResource creditAppraiselCollateralDetailsUpdateResource = null;
        ValuationDetailsUpdateResource valuationDetailsAddRes = null;
        InsuranceDetailsUpdateResource insuranceDetailsUpdateRes = null;
        CustomerResource customerResource = null;

        OtherDetailsAddRequestResource otherDetailsAddRequestResource = null;

        GuarantorAddResource guarantorAddResource = null;
        LinkedPersonResource linkedPersonResource = null;
        LeadInforBranchAssignUpdateResource leadInforBranchAssignUpdateResource = null;


        switch (ex.getServicePoint()) {
            case GUARANTOR_INCOME_EXPENSE:
                guarantorIncomeRequestResource = new GuarantorIncomeRequestResource();
                guarantorIncomeRequestResourceValidateException(guarantorIncomeRequestResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case CREDIT_APP_COLLLATERAL_DETAILS:
                creditAppraiselCollateralDetailsUpdateResource = new CreditAppraiselCollateralDetailsUpdateResource();
                creditAppraiselCollateralDetailsResourceValidateException(creditAppraiselCollateralDetailsUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case COL_VALUATION_DETAILS:
                valuationDetailsAddRes = new ValuationDetailsUpdateResource();
                valuationColDetailsResourceValidateException(valuationDetailsAddRes, ex.getServiceEntity(), ex.getMessage());
                break;

            case CUSTOMER:
                customerResource = new CustomerResource();
                customerResourceValidateException(customerResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case GUARANTOR:
                guarantorAddResource = new GuarantorAddResource();
                guarantorResourceValidateException(guarantorAddResource, ex.getServiceEntity(), ex.getMessage());
                break;

//		        	case COL_INSURANCE_DETAILS:
//		        		insuranceDetailsUpdateRes=new InsuranceDetailsUpdateResource();
//		        		insuranceColDetailsResourceValidateException(insuranceDetailsUpdateRes, ex.getServiceEntity(), ex.getMessage());
//			            break;
            case OTHER_DETAILS:
                otherDetailsAddRequestResource = new OtherDetailsAddRequestResource();
                otherDetailsAddRequestResourceValidateException(otherDetailsAddRequestResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case LINKED_PERSON:
                linkedPersonResource = new LinkedPersonResource();
                linkedPersonResourceValidateException(linkedPersonResource, ex.getServiceEntity(), ex.getMessage());
                break;

            case LEAD_INFOR:
                leadInforBranchAssignUpdateResource = new LeadInforBranchAssignUpdateResource();
                leadInforBranchAssignUpdateResourceValidateException(leadInforBranchAssignUpdateResource, ex.getServiceEntity(), ex.getMessage());
                break;

            default:
        }

        TenantHolder.clear();
        if (guarantorIncomeRequestResource != null) {
            return new ResponseEntity<>(guarantorIncomeRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);

        } else if (customerIncomeExpenseRequestResource != null) {
            return new ResponseEntity<>(customerIncomeExpenseRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);

        } else if (creditAppraiselCollateralDetailsUpdateResource != null) {
            return new ResponseEntity<>(creditAppraiselCollateralDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

        } else if (valuationDetailsAddRes != null) {
            return new ResponseEntity<>(valuationDetailsAddRes, HttpStatus.UNPROCESSABLE_ENTITY);

        } else if (otherDetailsAddRequestResource != null) {
            return new ResponseEntity<>(otherDetailsAddRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);

        } else if (customerResource != null) {
            return new ResponseEntity<>(customerResource, HttpStatus.UNPROCESSABLE_ENTITY);

        } else if (guarantorAddResource != null) {
            return new ResponseEntity<>(guarantorAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

        } else if (linkedPersonResource != null) {
            return new ResponseEntity<>(linkedPersonResource, HttpStatus.UNPROCESSABLE_ENTITY);

        } else if (leadInforBranchAssignUpdateResource != null) {
            return new ResponseEntity<>(leadInforBranchAssignUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);

        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    private void insuranceColDetailsResourceValidateException(InsuranceDetailsUpdateResource insuranceDetailsUpdateRes,
                                                              ServiceEntity serviceEntity, String message) {

        switch (serviceEntity) {
            case ASSETS_ENTITY_ID:
                insuranceDetailsUpdateRes.setAssetsEntity(message);
                break;
            case VERSION:
                insuranceDetailsUpdateRes.setVersion(message);
                break;
            case INSURANCE_TYPE:
                insuranceDetailsUpdateRes.setInsuranceType(message);
                break;
            case INSURANCE_TYPE_CODE:
                insuranceDetailsUpdateRes.setInsuranceCoverType(message);
                break;
            case INSU_COMP_MAINT:
                insuranceDetailsUpdateRes.setInsuranceCompanyMaint(message);
                break;
            case INSU_COMP_MAINT_CODE:
                insuranceDetailsUpdateRes.setInsuranceCompanyMaintCode(message);
                break;
            case INSU_COVER_TYPE:
                insuranceDetailsUpdateRes.setInsuranceCoverType(message);
                break;
            case INSU_COVER_TYPE_CODE:
                insuranceDetailsUpdateRes.setInsuranceCoverTypeCode(message);
                break;
            case INSU_SUB_TYPE:
                insuranceDetailsUpdateRes.setInsuranceSubType(message);
                break;
            case COVER_NOTE_NO:
                insuranceDetailsUpdateRes.setCoverNoteNumber(message);
                break;
            case COVER_NOTE_PERIOD_FROM:
                insuranceDetailsUpdateRes.setCoverNotePeriodFrom(message);
                break;
            case COVER_NOTE_PERIOD_TO:
                insuranceDetailsUpdateRes.setCoverNotePeriodTo(message);
                break;
            case COVER_NOTE_STATUS:
                insuranceDetailsUpdateRes.setCoverNoteStatus(message);
                break;
            case POLICY_NO:
                insuranceDetailsUpdateRes.setPolicyNo(message);
                break;
            case POLICY_PERIOD_FROM:
                insuranceDetailsUpdateRes.setPolicyCoverPeriodFrom(message);
                break;
            case POLICY_PERIOD_TO:
                insuranceDetailsUpdateRes.setPolicyCoverPeriodTo(message);
                break;
            case NEXT_RENEW_DATE:
                insuranceDetailsUpdateRes.setNextRenewalDate(message);
                break;
            case INSU_DETAIL_ID:
                insuranceDetailsUpdateRes.setInsuId(message);
                break;

            default:
        }

    }

    private void valuationColDetailsResourceValidateException(ValuationDetailsUpdateResource valuationDetailsAddRes,
                                                              ServiceEntity serviceEntity, String message) {

        switch (serviceEntity) {
            case VALUER_ID:
                valuationDetailsAddRes.setValuerId(message);
                break;

            case COL_VALUATION_DETAILS:
                valuationDetailsAddRes.setValuationDetailsId(message);
                break;

            case VERSION:
                valuationDetailsAddRes.setVersion(message);
                break;

            default:
        }
    }

    private void customerResourceValidateException(CustomerResource customerResource, ServiceEntity serviceEntity, String message) {

        switch (serviceEntity) {
            case CORPORATE_CATEGORY_ID:
                customerResource.setCorporateCategoryId(message);
                break;

            default:
        }
    }

    private void guarantorResourceValidateException(GuarantorAddResource guarantorAddResource, ServiceEntity serviceEntity, String message) {

        switch (serviceEntity) {
            case GENDER:
                guarantorAddResource.setGenderId(message);
                break;

            case TITLE:
                guarantorAddResource.setTitleId(message);
                break;

            default:
        }

    }

    @ExceptionHandler({DetailListValidateException.class})
    public ResponseEntity<Object> detailListValidateException(DetailListValidateException ex, WebRequest request) {
        ExpenceTypeCultivationCategoryAddResource expenceTypeCultivationCategoryAddResource = null;
        ExpenseTypeHouseholdExpenseCategoriesAddResource expenseTypeHouseholdExpenseCategoriesAddResource = null;
        ApprovalCatProductMapAddResource approvalCatProductMapAddResource = null;
        GuarantorIncomeRequestResource guarantorIncomeRequestResource = null;
        CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource = null;
        NewFacilityAddResource newFacilityAddResource = null;
        CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetailsAddResource = null;
        DisbursementAddRequestResource disbursementAddRequestResource = null;
        AdditionalDocumentAddRequestResource additionalDocumentAddRequestResource = null;
        ProductCategoryProductMapAddResource productCategoryProductMapAddResource = null;
        OtherIncomeExpenseTypeAddResource otherIncomeExpenseTypeAddResource = null;
        AnalystUpdateResource analystUpdateResource = null;
        SalaryIncomeDetailsAddResource salaryIncomeDetailsAddResource = null;
        SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource = null;
        BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource = null;
        CultivationIncomeDetailsAddResource cultivationIncomeDetailsAddResource = null;
        CultivationIncomeDetailsUpdateResource cultivationIncomeDetailsUpdateResource = null;
        BusinessIncomeDetailsUpdateResource businessIncomeDetailsUpdateResource = null;
        BusinessIncomeExpensesAddResource businessIncomeExpensesAddResource = null;
        BusinessTaxDetailsAddResource businessTaxDetailsAddResource = null;
        FinanceStatementAddRequest financeStatementAddRequest = null;
        FinancialStatementUpdateResource financialStatementUpdateResource = null;
        FinancialStatementDetailMainResource financialStatementDetailMainResource = null;
        OtherIncomeExpensesAddResource otherIncomeExpensesAddResource = null;
        CustomerResource customerResource = null;
        DocumentCheckListUpdateResource documentCheckListUpdateResource = null;
        HouseHoldExpenseDetailsUpdateResource houseHoldExpenseDetailsUpdateResource = null;
        ExternalLiabilityUpdateResource externalLiabilityUpdateResource = null;

        switch (ex.getServicePoint()) {
            case EXPENCE_TYPE_CULTIVATION_CATEGORY:
                expenceTypeCultivationCategoryAddResource = new ExpenceTypeCultivationCategoryAddResource();
                expenceTypeCultivationCategoryAddResourceListValidateException(expenceTypeCultivationCategoryAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case OTHER_INCOME_EXPENSES:
                otherIncomeExpensesAddResource = new OtherIncomeExpensesAddResource();
                expenceTypeOtherIncomeAddResourceListValidateException(otherIncomeExpensesAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case EXPENCE_TYPE_HOUSEHOLD_EXPENSE_CATEGORY:
                expenseTypeHouseholdExpenseCategoriesAddResource = new ExpenseTypeHouseholdExpenseCategoriesAddResource();
                expenseTypeHouseholdExpenseCategoriesAddResourceListValidateException(expenseTypeHouseholdExpenseCategoriesAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case APPROVAL_CAT_MAP_WITH_PRODUCT:
                approvalCatProductMapAddResource = new ApprovalCatProductMapAddResource();
                approvalCatProductMapAddResourceListValidateException(approvalCatProductMapAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;

            case GUARANTOR_OTHER_INCOME:
                guarantorIncomeRequestResource = new GuarantorIncomeRequestResource();
                otherIncomeAddRequestResourceListValidateException(guarantorIncomeRequestResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case FACILITY:
                newFacilityAddResource = new NewFacilityAddResource();
                newFacilityAddResourceValidateException(newFacilityAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case CUSTOMER_SALARY_INCOME:
                customerIncomeExpenseRequestResource = new CustomerIncomeExpenseRequestResource();
                salaryIncomeAddRequestResourceListValidateException(customerIncomeExpenseRequestResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case CUSTOMER_OTHER_INCOME:
                customerIncomeExpenseRequestResource = new CustomerIncomeExpenseRequestResource();
                otherIncomeAddRequestResourceListValidateException(customerIncomeExpenseRequestResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case CUSTOMER_FINANCIAL_COMMITMENT:
                customerIncomeExpenseRequestResource = new CustomerIncomeExpenseRequestResource();
                financialCommitmentAddRequestResourceListValidateException(customerIncomeExpenseRequestResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case GUARANTOR_SALARY_INCOME:
                guarantorIncomeRequestResource = new GuarantorIncomeRequestResource();
                salaryIncomeAddRequestResourceListValidateException(guarantorIncomeRequestResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case GUARANTOR_CULTIVATION_INCOME:
                guarantorIncomeRequestResource = new GuarantorIncomeRequestResource();
                guarantorCultivationIncomeAddRequestResourceListValidateException(guarantorIncomeRequestResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case CREDIT_APP_COLL_DOC_DETAILS:
                creditAppraiselCollateralDetailsAddResource = new CreditAppraiselCollateralDetailsAddResource();
                creditAppraiselCollateralDetailsAddResourceListValidateException(creditAppraiselCollateralDetailsAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case DISBURSEMENT_DETAILS:
                disbursementAddRequestResource = new DisbursementAddRequestResource();
                disbursementDetailsAddRequestResourceListValidateException(disbursementAddRequestResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case ADDITIONAL_DOCUMENT:
                additionalDocumentAddRequestResource = new AdditionalDocumentAddRequestResource();
                additionalDocumentAddRequestResourceListValidateException(additionalDocumentAddRequestResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case BUSINESS_INCOME_EXPENSE:
                guarantorIncomeRequestResource = new GuarantorIncomeRequestResource();
                guarantorBusinessIncomeAddRequestResourceListValidateException(guarantorIncomeRequestResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case NEW_FACILITY:
                newFacilityAddResource = new NewFacilityAddResource();
                facilityOtherProductsResourceValidateException(newFacilityAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case NEW_FACILITY_CONT:
                newFacilityAddResource = new NewFacilityAddResource();
                facilityContactResourceValidateException(newFacilityAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case BUSINESS_INCOME_EXPENSES:
                customerIncomeExpenseRequestResource = new CustomerIncomeExpenseRequestResource();
                customerIncomeExpenseRequestResourceValidateException(customerIncomeExpenseRequestResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case CUSTOMER_CUTIVATION_EXPENSE:
                customerIncomeExpenseRequestResource = new CustomerIncomeExpenseRequestResource();
                customerCultivationIncomeResourceValidateException(customerIncomeExpenseRequestResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
            case PRODUCT_PRODUCT_CATEGORY:
                productCategoryProductMapAddResource = new ProductCategoryProductMapAddResource();
                productCategoryProductMapAddResourceListValidateException(productCategoryProductMapAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case EXPENCE_TYPE_OTHER_INCOME_CATEGORY:
                otherIncomeExpenseTypeAddResource = new OtherIncomeExpenseTypeAddResource();
                otherIncomeExpenseTypeAddResourceListValidateException(otherIncomeExpenseTypeAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case ANALYST_EXCEPTION_DETAILS:
                analystUpdateResource = new AnalystUpdateResource();
                analystUpdateResourceListValidateException(analystUpdateResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case SALARY_INCOME_DETAILS:
                salaryIncomeDetailsAddResource = new SalaryIncomeDetailsAddResource();
                salaryIncomeDetailsAddResourceListValidateException(salaryIncomeDetailsAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case SALARY_INCOME_DOCUMENTS:
                salaryIncomeDetailsUpdateResource = new SalaryIncomeDetailsUpdateResource();
                salaryIncomeDetailsUpdateResourceListValidateException(salaryIncomeDetailsUpdateResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case BUSINESS_ASSET_DETAILS:
                businessAdditionalDetailsUpdateResource = new BusinessAdditionalDetailsUpdateResource();
                businessAssetDetailsResourceListValidateException(businessAdditionalDetailsUpdateResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case BUSINESS_RISK_DETAILS:
                businessAdditionalDetailsUpdateResource = new BusinessAdditionalDetailsUpdateResource();
                businessRiskDetailsResourceListListValidateException(businessAdditionalDetailsUpdateResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case BUSINESS_CLEARANCE_DETAILS:
                businessAdditionalDetailsUpdateResource = new BusinessAdditionalDetailsUpdateResource();
                businessClearanceDetailsResourceListValidateException(businessAdditionalDetailsUpdateResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case BUSINESS_DOCUMENT_DETAILS:
                businessAdditionalDetailsUpdateResource = new BusinessAdditionalDetailsUpdateResource();
                businessDocumentDetailsResourceListValidateException(businessAdditionalDetailsUpdateResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case BUSINESS_EMPLOYMENT_DETAILS:
                businessAdditionalDetailsUpdateResource = new BusinessAdditionalDetailsUpdateResource();
                businessEmploymentDetailsResourceListValidateException(businessAdditionalDetailsUpdateResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case CULTIVATION_INCOME_DETAILS:
                cultivationIncomeDetailsAddResource = new CultivationIncomeDetailsAddResource();
                cultivationIncomeDetailsAddResourceListValidateException(cultivationIncomeDetailsAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case CULTIVATION_INCOME_DOCUMENTS:
                cultivationIncomeDetailsUpdateResource = new CultivationIncomeDetailsUpdateResource();
                cultivationIncomeDetailsUpdateResourceListValidateException(cultivationIncomeDetailsUpdateResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case BUSINESS_INCOME_DOCUMENTS:
                businessIncomeDetailsUpdateResource = new BusinessIncomeDetailsUpdateResource();
                businessIncomeDetailsUpdateResourceListValidateException(businessIncomeDetailsUpdateResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case BUSINESS_INCOME_EXPENSE_TYPES:
                businessIncomeExpensesAddResource = new BusinessIncomeExpensesAddResource();
                businessIncomeExpensesAddResourceListValidateException(businessIncomeExpensesAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case BUSINESS_TAX_DETAILS:
                businessTaxDetailsAddResource = new BusinessTaxDetailsAddResource();
                businessTaxDetailsAddResourceListValidateException(businessTaxDetailsAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case FINANCIAL_STATEMENT_TEMPLATE:
                financialStatementUpdateResource = new FinancialStatementUpdateResource();
                financialStatementUpdateResourceListValidateException(financialStatementUpdateResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case FINANCIAL_STATEMENT:
                financeStatementAddRequest = new FinanceStatementAddRequest();
                financialStatementAddResourceListValidateException(financeStatementAddRequest, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case FINANCIAL_STATEMENT_DETAIL:
                financialStatementDetailMainResource = new FinancialStatementDetailMainResource();
                financialStatementDetailMainResourceListValidateException(financialStatementDetailMainResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case FINANCIAL_STATEMENT_DETAIL_NOTE:
                financialStatementDetailMainResource = new FinancialStatementDetailMainResource();
                financialStatementDetailNoteListValidateException(financialStatementDetailMainResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case LINKED_PERSON:
                customerResource = new CustomerResource();
                customerResourceLinkedPersonListValidateException(customerResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case DOCUMENT_CHECKLIST_DETAILS:
                documentCheckListUpdateResource = new DocumentCheckListUpdateResource();
                documentCheckListUpdateResourceListValidateException(documentCheckListUpdateResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case EXTERNAL_LIABILITY:          	
                externalLiabilityUpdateResource = new ExternalLiabilityUpdateResource();
            	externalLiabilityUpdateResourceListValidateException(externalLiabilityUpdateResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
                break;
            case HOUSE_HOLD_EXPENSE_DETAILS:
            	houseHoldExpenseDetailsUpdateResource = new HouseHoldExpenseDetailsUpdateResource();
            	houseHoldExpenseDetailsUpdateResourceListValidateException(houseHoldExpenseDetailsUpdateResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
            	break;
            default:

        }
        TenantHolder.clear();
        if (expenceTypeCultivationCategoryAddResource != null) {
            return new ResponseEntity<>(expenceTypeCultivationCategoryAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (expenseTypeHouseholdExpenseCategoriesAddResource != null) {
            return new ResponseEntity<>(expenseTypeHouseholdExpenseCategoriesAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (approvalCatProductMapAddResource != null) {
            return new ResponseEntity<>(approvalCatProductMapAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (customerIncomeExpenseRequestResource != null) {
            return new ResponseEntity<>(customerIncomeExpenseRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (newFacilityAddResource != null) {
            return new ResponseEntity<>(newFacilityAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (guarantorIncomeRequestResource != null) {
            return new ResponseEntity<>(guarantorIncomeRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (creditAppraiselCollateralDetailsAddResource != null) {
            return new ResponseEntity<>(creditAppraiselCollateralDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (disbursementAddRequestResource != null) {
            return new ResponseEntity<>(disbursementAddRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (additionalDocumentAddRequestResource != null) {
            return new ResponseEntity<>(additionalDocumentAddRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (otherIncomeExpenseTypeAddResource != null) {
            return new ResponseEntity<>(otherIncomeExpenseTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (analystUpdateResource != null) {
            return new ResponseEntity<>(analystUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (salaryIncomeDetailsAddResource != null) {
            return new ResponseEntity<>(salaryIncomeDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (salaryIncomeDetailsUpdateResource != null) {
            return new ResponseEntity<>(salaryIncomeDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (businessAdditionalDetailsUpdateResource != null) {
            return new ResponseEntity<>(businessAdditionalDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (cultivationIncomeDetailsAddResource != null) {
            return new ResponseEntity<>(cultivationIncomeDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (cultivationIncomeDetailsUpdateResource != null) {
            return new ResponseEntity<>(cultivationIncomeDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (businessIncomeDetailsUpdateResource != null) {
            return new ResponseEntity<>(businessIncomeDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (businessIncomeExpensesAddResource != null) {
            return new ResponseEntity<>(businessIncomeExpensesAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (businessTaxDetailsAddResource != null) {
            return new ResponseEntity<>(businessTaxDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (financeStatementAddRequest != null) {
            return new ResponseEntity<>(financeStatementAddRequest, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (financialStatementDetailMainResource != null) {
            return new ResponseEntity<>(financialStatementDetailMainResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (customerResource != null) {
            return new ResponseEntity<>(customerResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (otherIncomeExpensesAddResource != null) {
            return new ResponseEntity<>(otherIncomeExpensesAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (financialStatementUpdateResource != null) {
            return new ResponseEntity<>(financialStatementUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (documentCheckListUpdateResource != null) {
            return new ResponseEntity<>(documentCheckListUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (houseHoldExpenseDetailsUpdateResource != null) {
            return new ResponseEntity<>(houseHoldExpenseDetailsUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (externalLiabilityUpdateResource != null) {
	        return new ResponseEntity<>(externalLiabilityUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
	    }
        else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }


    }


    private void houseHoldExpenseDetailsUpdateResourceListValidateException(
			HouseHoldExpenseDetailsUpdateResource houseHoldExpenseDetailsUpdateResource, ServiceEntity serviceEntity,
			String message, Integer index) {
    	
    	 List<HouseHoldExpenseInfoResource> houseHoldExpenseInfoResource = new ArrayList<>();
         for (int i = 0; i <= index; i++) {
        	 houseHoldExpenseInfoResource.add(i, new HouseHoldExpenseInfoResource());
         }
         switch (serviceEntity) {
             case VERSION:
            	 houseHoldExpenseInfoResource.get(index).setVersion(message);
                 break;

             default:
         }
         houseHoldExpenseDetailsUpdateResource.setHouseHoldExpenseInfo(houseHoldExpenseInfoResource);
		
	}

	private void facilityContactResourceValidateException(NewFacilityAddResource newFacilityAddResource,
                                                          ServiceEntity serviceEntity, String message, Integer index) {
        List<FacilityContractDetailsResource> facilityContractDetailsResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            facilityContractDetailsResource.add(i, new FacilityContractDetailsResource());
        }
        switch (serviceEntity) {
            case CONTRACTS: {
                facilityContractDetailsResource.get(index).setContractNo(message);
                facilityContractDetailsResource.get(index).setContractStatus(message);
                facilityContractDetailsResource.get(index).setContractStatusDes(message);
                facilityContractDetailsResource.get(index).setLeseCode(message);
                facilityContractDetailsResource.get(index).setStatus(message);
                break;
            }
            case CONTACT_NO:
                facilityContractDetailsResource.get(index).setContractNo(message);
                break;
            case CONTACT_STATUS:
                facilityContractDetailsResource.get(index).setContractStatus(message);
                break;
            case CONTACT_STATUS_DEC:
                facilityContractDetailsResource.get(index).setContractStatusDes(message);
                break;
            case LEASE_CODE:
                facilityContractDetailsResource.get(index).setLeseCode(message);
                break;
            case STATUS:
                facilityContractDetailsResource.get(index).setStatus(message);
                break;
            default:
        }
        newFacilityAddResource.setContractDetails(facilityContractDetailsResource);

    }

    private void facilityOtherProductsResourceValidateException(NewFacilityAddResource newFacilityAddResource,
                                                                ServiceEntity serviceEntity, String message, Integer index) {
        List<FacilityOtherProductsResource> facilityOtherProductsResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            facilityOtherProductsResource.add(i, new FacilityOtherProductsResource());
        }
        switch (serviceEntity) {
            case PRODUCT_ID:
                facilityOtherProductsResource.get(index).setProductId(message);
                break;
            default:
        }
        newFacilityAddResource.setOtherProducts(facilityOtherProductsResource);

    }

    private void approvalCatProductMapAddResourceListValidateException(
            ApprovalCatProductMapAddResource approvalCatProductMapAddResource, ServiceEntity serviceEntity,
            String message, Integer index) {
        List<ApprovalCatProductMapAddProductResource> approvalCatProductMapAddProductResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            approvalCatProductMapAddProductResource.add(i, new ApprovalCatProductMapAddProductResource());
        }
        switch (serviceEntity) {
            case PRODUCT_ID:
                approvalCatProductMapAddProductResource.get(index).setProductId(message);
                break;
            default:
        }
        approvalCatProductMapAddResource.setProducts(approvalCatProductMapAddProductResource);

    }


    private void productCategoryProductMapAddResourceListValidateException(
            ProductCategoryProductMapAddResource productCategoryProductMapAddResource,
            ServiceEntity serviceEntity, String message, Integer index) {

        List<ProductRequestResource> productListResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            productListResource.add(i, new ProductRequestResource());
        }
        switch (serviceEntity) {
            case PRODUCT_PRODUCT_CATEGORY_DET_ID:
                productListResource.get(index).setId(message);
                break;
            default:
        }
        productCategoryProductMapAddResource.setProducts(productListResource);
        ;


    }


    private void expenseTypeHouseholdExpenseCategoriesAddResourceListValidateException(
            ExpenseTypeHouseholdExpenseCategoriesAddResource expenseTypeHouseholdExpenseCategoriesAddResource,
            ServiceEntity serviceEntity, String message, Integer index) {

        List<ExpenseTypeListResource> expenseTypeListResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            expenseTypeListResource.add(i, new ExpenseTypeListResource());
        }
        switch (serviceEntity) {
            case EXPENSE_TYPE_ID:
                expenseTypeListResource.get(index).setExpenseTypeId(message);
                break;
            case EXPENCE_TYPE_HOUSEHOLD_EXPENSE_CATEGORY_DET_ID:
                expenseTypeListResource.get(index).setId(message);
                break;
            default:
        }
        expenseTypeHouseholdExpenseCategoriesAddResource.setExpenseTypes(expenseTypeListResource);


    }

    private void expenceTypeCultivationCategoryAddResourceListValidateException(ExpenceTypeCultivationCategoryAddResource expenceTypeCultivationCategoryAddResource, ServiceEntity serviceEntity, String message, Integer index) {
        List<ExpenseTypeListResource> expenseTypeListResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            expenseTypeListResource.add(i, new ExpenseTypeListResource());
        }
        switch (serviceEntity) {
            case EXPENSE_TYPE_ID:
                expenseTypeListResource.get(index).setExpenseTypeId(message);
                break;
            case EXPENCE_TYPE_CULTIVATION_CATEGORY_DET_ID:
                expenseTypeListResource.get(index).setId(message);
                break;
            default:
        }
        expenceTypeCultivationCategoryAddResource.setExpenseTypes(expenseTypeListResource);
    }

    private void expenceTypeOtherIncomeAddResourceListValidateException(OtherIncomeExpensesAddResource otherIncomeExpensesAddResource, ServiceEntity serviceEntity, String message, Integer index) {
        List<OtherIncomeExpensesResource> otherIncomeExpensesResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            otherIncomeExpensesResource.add(i, new OtherIncomeExpensesResource());
        }
        switch (serviceEntity) {
            case OTHER_INCOME_TYPE:
                otherIncomeExpensesResource.get(index).setIncomeTypeId(message);
                break;
            case OTHER_INCOME_TYPE_NAME:
                otherIncomeExpensesResource.get(index).setIncomeTypeName(message);
                break;
            case OTHER_EXPENSE_TYPE:
                otherIncomeExpensesResource.get(index).setExpenseTypeId(message);
                break;
            case FREQUENCY:
                otherIncomeExpensesResource.get(index).setFrequencyId(message);
                break;
            case VERSION:
                otherIncomeExpensesResource.get(index).setVersion(message);
                break;
            default:
        }
        otherIncomeExpensesAddResource.setOtherIncomeExpenses(otherIncomeExpensesResource);
    }

    private void otherIncomeAddRequestResourceListValidateException(GuarantorIncomeRequestResource guarantorIncomeRequestResource, ServiceEntity serviceEntity, String message, Integer index) {
        List<OtherIncomeAddRequestResource> otherIncomeAddRequestListResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            otherIncomeAddRequestListResource.add(i, new OtherIncomeAddRequestResource());
        }
        switch (serviceEntity) {
            case OTHER_INCOME_ID:
                otherIncomeAddRequestListResource.get(index).setId(message);
                break;
            case CUSTOMER_ID:
                otherIncomeAddRequestListResource.get(index).setGuarantorId(message);
                break;
            case VERSION:
                otherIncomeAddRequestListResource.get(index).setVersion(message);
                break;
            case OTHER_INCOME_TYPE_ID:
                otherIncomeAddRequestListResource.get(index).setOtherIncomeTypeId(message);
                break;
            default:
        }
        guarantorIncomeRequestResource.setOtherIncomes(otherIncomeAddRequestListResource);
    }

    private void guarantorCultivationIncomeAddRequestResourceListValidateException(GuarantorIncomeRequestResource guarantorIncomeRequestResource, ServiceEntity serviceEntity, String message, Integer index) {
        List<GuarantorCultivationIncomeAddResource> guarantorCultivationIncomeAddRequestListResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            guarantorCultivationIncomeAddRequestListResource.add(i, new GuarantorCultivationIncomeAddResource());
        }
        switch (serviceEntity) {
            case CULTIVATION_INCOME_ID:
                guarantorCultivationIncomeAddRequestListResource.get(index).setId(message);
                break;
            case CUSTOMER_ID:
                guarantorCultivationIncomeAddRequestListResource.get(index).setGuarantorId(message);
                break;
            case VERSION:
                guarantorCultivationIncomeAddRequestListResource.get(index).setVersion(message);
                break;
            case CULTIVATION_INCOME_TYPE_ID:
                guarantorCultivationIncomeAddRequestListResource.get(index).setCultivationCategoryId(message);
                break;
            default:
        }
        guarantorIncomeRequestResource.setCultivationIncome(guarantorCultivationIncomeAddRequestListResource);
    }

    private void guarantorBusinessIncomeAddRequestResourceListValidateException(GuarantorIncomeRequestResource guarantorIncomeRequestResource, ServiceEntity serviceEntity, String message, Integer index) {
        List<BusinessIncomeExpenseRequestResource> guarantorBusinessIncomeAddRequestListResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            guarantorBusinessIncomeAddRequestListResource.add(i, new BusinessIncomeExpenseRequestResource());
        }
        switch (serviceEntity) {
            case BUSINESS_INCOME_EXPENSE_ID:
                guarantorBusinessIncomeAddRequestListResource.get(index).setId(message);
                break;
            case BUSINESS_INCOME_TYPE_ID:
                guarantorBusinessIncomeAddRequestListResource.get(index).setBusinessTypesId(message);
                break;


            default:
        }
        guarantorIncomeRequestResource.setBusinessIncome(guarantorBusinessIncomeAddRequestListResource);
    }

    private void creditAppraiselCollateralDetailsAddResourceListValidateException(CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetailsAddResource, ServiceEntity serviceEntity, String message, Integer index) {
        List<CreAppCollateralDocumentDetailsResource> creAppCollateralDocumentDetailsResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            creAppCollateralDocumentDetailsResource.add(i, new CreAppCollateralDocumentDetailsResource());
        }
        switch (serviceEntity) {
            case DOCUMENT_ID:
                creAppCollateralDocumentDetailsResource.get(index).setDocumentId(message);
                break;

            default:
        }
        creditAppraiselCollateralDetailsAddResource.setDocumentUploadDetails(creAppCollateralDocumentDetailsResource);
    }

    private void salaryIncomeAddRequestResourceListValidateException(CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource, ServiceEntity serviceEntity, String message, Integer index) {
        List<SalaryIncomeAddRequestResource> salaryIncomeAddRequestListResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            salaryIncomeAddRequestListResource.add(i, new SalaryIncomeAddRequestResource());
        }
        switch (serviceEntity) {
            case SALARY_INCOME_ID:
                salaryIncomeAddRequestListResource.get(index).setId(message);
                break;
            case CUSTOMER_ID:
                salaryIncomeAddRequestListResource.get(index).setCustomerId(message);
                break;
            case VERSION:
                salaryIncomeAddRequestListResource.get(index).setVersion(message);
                break;
            case KEY_PERSON_ID:
                salaryIncomeAddRequestListResource.get(index).setKeyPersonId(message);
                break;
            default:
        }
        customerIncomeExpenseRequestResource.setSalaryIncomes(salaryIncomeAddRequestListResource);
    }

    private void otherIncomeAddRequestResourceListValidateException(CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource, ServiceEntity serviceEntity, String message, Integer index) {
        List<OtherIncomeAddRequestResource> otherIncomeAddRequestListResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            otherIncomeAddRequestListResource.add(i, new OtherIncomeAddRequestResource());
        }
        switch (serviceEntity) {
            case OTHER_INCOME_ID:
                otherIncomeAddRequestListResource.get(index).setId(message);
                break;
            case CUSTOMER_ID:
                otherIncomeAddRequestListResource.get(index).setCustomerId(message);
                break;
            case VERSION:
                otherIncomeAddRequestListResource.get(index).setVersion(message);
                break;
            case OTHER_INCOME_TYPE_ID:
                otherIncomeAddRequestListResource.get(index).setOtherIncomeTypeId(message);
                break;
            default:
        }
        customerIncomeExpenseRequestResource.setOtherIncomes(otherIncomeAddRequestListResource);
    }

    private void financialCommitmentAddRequestResourceListValidateException(CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource, ServiceEntity serviceEntity, String message, Integer index) {
        List<FinancialCommitmentAddRequestResource> financialCommitmentAddRequestListResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            financialCommitmentAddRequestListResource.add(i, new FinancialCommitmentAddRequestResource());
        }
        switch (serviceEntity) {
            case FINANCIAL_COMMITMENT_ID:
                financialCommitmentAddRequestListResource.get(index).setId(message);
                break;
            case CUSTOMER_ID:
                financialCommitmentAddRequestListResource.get(index).setCustomerId(message);
                break;
            case VERSION:
                financialCommitmentAddRequestListResource.get(index).setVersion(message);
                break;
            case TYPE_OF_COMMITMENT:
                financialCommitmentAddRequestListResource.get(index).setTypeOfCommitmentId(message);
                break;
            case TYPE_OF_FACILITY:
                financialCommitmentAddRequestListResource.get(index).setTypeOfFacilityId(message);
                break;
            default:
        }
        customerIncomeExpenseRequestResource.setFinancialCommitment(financialCommitmentAddRequestListResource);
    }

    private void salaryIncomeAddRequestResourceListValidateException(GuarantorIncomeRequestResource guarantorIncomeRequestResource, ServiceEntity serviceEntity, String message, Integer index) {
        List<SalaryIncomeAddRequestResource> salaryIncomeAddRequestListResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            salaryIncomeAddRequestListResource.add(i, new SalaryIncomeAddRequestResource());
        }
        switch (serviceEntity) {
            case SALARY_INCOME_ID:
                salaryIncomeAddRequestListResource.get(index).setId(message);
                break;
            case CUSTOMER_ID:
                salaryIncomeAddRequestListResource.get(index).setGuarantorId(message);
                break;
            case GUARANTOR_ID:
                salaryIncomeAddRequestListResource.get(index).setGuarantorId(message);
                break;
            case VERSION:
                salaryIncomeAddRequestListResource.get(index).setVersion(message);
                break;
            default:
        }
        guarantorIncomeRequestResource.setSalaryIncomes(salaryIncomeAddRequestListResource);
    }

    private void disbursementDetailsAddRequestResourceListValidateException(DisbursementAddRequestResource disbursementAddRequestResource, ServiceEntity serviceEntity, String message, Integer index) {
        List<DisbursementDetailsRequestResource> disbursementDetailsAddRequestListResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            disbursementDetailsAddRequestListResource.add(i, new DisbursementDetailsRequestResource());
        }
        switch (serviceEntity) {
            case DISBURSEMENT_DETAILS_ID:
                disbursementDetailsAddRequestListResource.get(index).setId(message);
                break;
            case CUSTOMER_ID:
                disbursementDetailsAddRequestListResource.get(index).setCustomerId(message);
                break;
            case VERSION:
                disbursementDetailsAddRequestListResource.get(index).setVersion(message);
                break;
            case PAY_METHODS:
                disbursementDetailsAddRequestListResource.get(index).setPayMethodId(message);
                break;
            case BANK:
                disbursementDetailsAddRequestListResource.get(index).setBankId(message);
                break;
            case BANK_BRANCH:
                disbursementDetailsAddRequestListResource.get(index).setBankBranchId(message);
                break;
            case LEAD_INFO_ID:
                disbursementDetailsAddRequestListResource.get(index).setLeadInfoId(message);
                break;
            default:
        }
        disbursementAddRequestResource.setDisbursementDetails(disbursementDetailsAddRequestListResource);
    }
    
    private void externalLiabilityUpdateResourceListValidateException(ExternalLiabilityUpdateResource externalLiabilityUpdateResource, ServiceEntity serviceEntity, String message, Integer index) {

        switch (serviceEntity) {

            case BANK_BRANCH:
            	externalLiabilityUpdateResource.setBranchId(message);
                break;
            default:
        }

    }

    private void additionalDocumentAddRequestResourceListValidateException(AdditionalDocumentAddRequestResource additionalDocumentAddRequestResource, ServiceEntity serviceEntity, String message, Integer index) {
        List<AdditionalDocumentRequestResource> additionalDocumentAddRequestListResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            additionalDocumentAddRequestListResource.add(i, new AdditionalDocumentRequestResource());
        }
        switch (serviceEntity) {
            case ADDITIONAL_DOCUMENT_ID:
                additionalDocumentAddRequestListResource.get(index).setId(message);
                break;
            case CUSTOMER_ID:
                additionalDocumentAddRequestListResource.get(index).setCustomerId(message);
                break;
            case VERSION:
                additionalDocumentAddRequestListResource.get(index).setVersion(message);
                break;
            case LEAD_INFO_ID:
                additionalDocumentAddRequestListResource.get(index).setLeadInfoId(message);
                break;
            default:
        }
        additionalDocumentAddRequestResource.setAdditionalDocuments(additionalDocumentAddRequestListResource);
    }


    private void guarantorIncomeRequestResourceValidateException(
            GuarantorIncomeRequestResource guarantorIncomeRequestResource, ServiceEntity serviceEntity, String message) {

        switch (serviceEntity) {
            case GUARANTOR_ID:
                guarantorIncomeRequestResource.setGuarantorId(message);
                break;


            default:
        }

    }



	/*private void legalContractAcceptResourceDetailListValidateException(LegalContractAcceptResource legalContractAcceptResource, ServiceEntity serviceEntity, String message, Integer index) {
		List<LegalContractAcceptRequestResource> legalContractAcceptRequestResources=new ArrayList<>();
		for(int i=0;i<=index;i++){
			legalContractAcceptRequestResources.add(i, new LegalContractAcceptRequestResource());
		}
		switch(serviceEntity)
        {
	        case LEGAL_CONTRACT_ID:
	        	legalContractAcceptRequestResources.get(index).setContrctId(message);
	            break;
	        default:
        }
		legalContractAcceptResource.setContracts(legalContractAcceptRequestResources);
	}*/

	/*private void legalContractRejectResourceDetailListValidateException(LegalContractRejectResource legalContractRejectResource, ServiceEntity serviceEntity, String message, Integer index) {
		List<LegalContractRejectRequestResource> legalContractRejectRequestResources=new ArrayList<>();
		for(int i=0;i<=index;i++){
			legalContractRejectRequestResources.add(i, new LegalContractRejectRequestResource());
		}
		switch(serviceEntity)
        {
	        case LEGAL_CONTRACT_ID:
	        	legalContractRejectRequestResources.get(index).setContrctId(message);
	            break;
	        case REJECT_REASON_ID:
	        	legalContractRejectRequestResources.get(index).setRejectReasonId(message);
	            break;
	        case REJECT_REASON_NAME:
	        	legalContractRejectRequestResources.get(index).setRejectReasonName(message);
	            break;
	        default:
        }
		legalContractRejectResource.setContracts(legalContractRejectRequestResources);
	}*/


    @ExceptionHandler({InvalidDetailListServiceIdException.class})
    public ResponseEntity<Object> invalidDetailListServiceIdException(InvalidDetailListServiceIdException ex, WebRequest request) {
        TenantHolder.clear();
        LoggerRequest.getInstance().logInfo(project + " " + ex.getServiceEntity() + " " + ex.getMessage());
        switch (ex.getTransferType()) {
            case BUSINESS_EXPENSE_TYPE_MAPPING:
                BusinessExpenseTypeAddResource businessExpenseTypeAddResource = validateBusinessExpenseTypeAddResource(ex);
                return new ResponseEntity<>(businessExpenseTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

            case COLLLATERAL_DOC_DETAILS:
                CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetailsAddResource = validateCreAppCollateralDocumentDetails(ex);
                return new ResponseEntity<>(creditAppraiselCollateralDetailsAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

//			case CRIB_IDENTIFICATION:
//				RelationCribDetailsAddResource externalCribDetailRes = validateExtCribRelResource(ex);
//				return new ResponseEntity<>(externalCribDetailRes, HttpStatus.UNPROCESSABLE_ENTITY);

            default:
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }


    private BusinessExpenseTypeAddResource validateBusinessExpenseTypeAddResource(InvalidDetailListServiceIdException ex) {
        BusinessExpenseTypeAddResource businessExpenseTypeAddResources = new BusinessExpenseTypeAddResource();
        List<BusinessExpenseTypeDetailResource> businessExpenseTypeDetailResource = new ArrayList<>();
        Integer index = ex.getIndex();
        for (int i = 0; i <= ex.getIndex(); i++) {
            businessExpenseTypeDetailResource.add(i, new BusinessExpenseTypeDetailResource());
        }
        LoggerRequest.getInstance().logInfo(project + " " + ex.getServiceEntity() + " " + ex.getMessage());
        switch (ex.getServiceEntity()) {
            case BUSINESS_TYPE_ID:
                businessExpenseTypeAddResources.setBusinessTypesId(ex.getMessage());
                break;
            case EXPENSE_TYPE_ID:
                businessExpenseTypeDetailResource.get(index).setExpenseTypesId(ex.getMessage());
                break;
            case BUSINESS_EXPENSE_TYPE_ID:
                businessExpenseTypeDetailResource.get(index).setExpenseTypes(ex.getMessage());
                break;
            default:

        }
        businessExpenseTypeAddResources.setExpenseTypesList(businessExpenseTypeDetailResource);
        return businessExpenseTypeAddResources;
    }


//	private RelationCribDetailsAddResource validateExtCribRelResource(InvalidDetailListServiceIdException ex) {
//		RelationCribDetailsAddResource externalCribDetailResource = new RelationCribDetailsAddResource();
//		List<RelationCribDetailsResource>  externalCribDetailResource1 = new ArrayList<>();
//		 List<ExternalCribRequestResource> relationCribRequestDet =  new ArrayList<>();
//		Integer index=ex.getIndex();
//		for(int i=0;i<=ex.getIndex();i++){
//			relationCribRequestDet.add(i, new ExternalCribRequestResource());
//		}
//		LoggerRequest.getInstance().logInfo(project+ " " +ex.getServiceEntity()+" "+ex.getMessage());
//		switch(ex.getServiceEntity())
//        {
//
//	        case IDENTIFICATION_TYPE:
//	        	relationCribRequestDet.get(index).setIdentificationtype(ex.getMessage());
//	            break;
//
//	        case IDENTIFICATION_NO:
//	        	relationCribRequestDet.get(index).setIdentificationNumber(ex.getMessage());
//	            break;
//
//            default:
//
//        }
//		externalCribDetailResource.setRelationCribDetails(relationCribRequestDet);
//
//		return externalCribDetailResource;
//	}


    private CreditAppraiselCollateralDetailsAddResource validateCreAppCollateralDocumentDetails(InvalidDetailListServiceIdException ex) {
        CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetailsAddRes = new CreditAppraiselCollateralDetailsAddResource();
        List<CreAppCollateralDocumentDetailsResource> creAppCollateralDocumentDetailsResources = new ArrayList<>();
        Integer index = ex.getIndex();
        for (int i = 0; i <= ex.getIndex(); i++) {
            creAppCollateralDocumentDetailsResources.add(i, new CreAppCollateralDocumentDetailsResource());
        }
        LoggerRequest.getInstance().logInfo(project + " " + ex.getServiceEntity() + " " + ex.getMessage());
        switch (ex.getServiceEntity()) {
            case DOCUMENT_ID:
                creAppCollateralDocumentDetailsResources.get(index).setDocumentId(ex.getMessage());
                break;

            default:

        }
        creditAppraiselCollateralDetailsAddRes.setDocumentUploadDetails(creAppCollateralDocumentDetailsResources);
        return creditAppraiselCollateralDetailsAddRes;
    }

    @ExceptionHandler({WorkFlowException.class})
    public ResponseEntity<Object> workFlowException(WorkFlowException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsResource.setMessages(ex.getMessage());
        successAndErrorDetailsResource.setDetails(ex.getWorkflowType() + " WorkFlow");
        RequestMapperEnum mapperEnum = (RequestMapperEnum) request.getAttribute("requestMapper", 0);
        if (mapperEnum != null)
            successAndErrorDetailsResource.setCode(RequestMapperEnum.getErrorCode(mapperEnum));
        LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsResource.getCode() + " " + successAndErrorDetailsResource.getMessages() + " " + successAndErrorDetailsResource.getDetails());
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void incomeExpenseDetailsResourceListValidateException(CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource, ServiceEntity serviceEntity,
                                                                   String message, Integer index, Integer index1) {
        List<BusinessIncomeExpenseRequestResource> businessIncomeExpenseRequestResource = new ArrayList<>();
        List<IncomeExpenseDetailsResource> incomeExpenseDetailsResource = new ArrayList<>();

        for (int i = 0; i <= index; i++) {
            businessIncomeExpenseRequestResource.add(i, new BusinessIncomeExpenseRequestResource());
        }

        for (int i = 0; i <= index1; i++) {
            incomeExpenseDetailsResource.add(i, new IncomeExpenseDetailsResource());
        }
        switch (serviceEntity) {
            case INCOME_TYPE_ID: {
                incomeExpenseDetailsResource.get(index1).setIncomeTypeId(message);
                businessIncomeExpenseRequestResource.get(index).setIncomeExpenseDetails(incomeExpenseDetailsResource);
                break;
            }
            case EXPENSE_TYPE_ID: {
                incomeExpenseDetailsResource.get(index1).setExpenseTypeId(message);
                businessIncomeExpenseRequestResource.get(index).setIncomeExpenseDetails(incomeExpenseDetailsResource);
                break;
            }
            case INCOME_EXPENSE_DETAILS_ID: {
                incomeExpenseDetailsResource.get(index1).setId(message);
                businessIncomeExpenseRequestResource.get(index).setIncomeExpenseDetails(incomeExpenseDetailsResource);
                break;
            }
            default:
        }
        customerIncomeExpenseRequestResource.setBusinessIncomeExpenses(businessIncomeExpenseRequestResource);
    }

    private void incomeExpenseTaxResourceListValidateException(CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource, ServiceEntity serviceEntity,
                                                               String message, Integer index, Integer index1) {
        List<BusinessIncomeExpenseRequestResource> businessIncomeExpenseRequestResource = new ArrayList<>();
        List<IncomeExpenseTaxResource> incomeExpenseTaxResource = new ArrayList<>();

        for (int i = 0; i <= index; i++) {
            businessIncomeExpenseRequestResource.add(i, new BusinessIncomeExpenseRequestResource());
        }

        for (int i = 0; i <= index1; i++) {
            incomeExpenseTaxResource.add(i, new IncomeExpenseTaxResource());
        }
        switch (serviceEntity) {
            case TAX_APPLICABLE_ON_ID: {
                incomeExpenseTaxResource.get(index1).setApplicableOnId(message);
                businessIncomeExpenseRequestResource.get(index).setIncomeExpenseTax(incomeExpenseTaxResource);
                break;
            }
            case INCOME_EXPENSE_TAX_ID: {
                incomeExpenseTaxResource.get(index1).setId(message);
                businessIncomeExpenseRequestResource.get(index).setIncomeExpenseTax(incomeExpenseTaxResource);
                break;
            }
            default:
        }
        customerIncomeExpenseRequestResource.setBusinessIncomeExpenses(businessIncomeExpenseRequestResource);
    }

    private void customerCultivationIncomeResourceListValidateException(CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource, ServiceEntity serviceEntity,
                                                                        String message, Integer index, Integer index1) {
        List<CustomerCultivationIncomeResource> customerCultivationIncomeResource = new ArrayList<>();
        List<CustomerCultivationExpenseResource> customerCultivationExpenseResource = new ArrayList<>();

        for (int i = 0; i <= index; i++) {
            customerCultivationIncomeResource.add(i, new CustomerCultivationIncomeResource());
        }

        for (int i = 0; i <= index1; i++) {
            customerCultivationExpenseResource.add(i, new CustomerCultivationExpenseResource());
        }
        switch (serviceEntity) {
            case CUSTOMER_CULTIVATION_EXPENSE_ID: {
                customerCultivationExpenseResource.get(index1).setId(message);
                customerCultivationIncomeResource.get(index).setCustomerCultivationExpenses(customerCultivationExpenseResource);
                break;
            }
            case EXPENSE_TYPE_ID: {
                customerCultivationExpenseResource.get(index1).setExpenseTypeId(message);
                customerCultivationIncomeResource.get(index).setCustomerCultivationExpenses(customerCultivationExpenseResource);
                break;
            }
            default:
        }
        customerIncomeExpenseRequestResource.setCustomerCultivationIncomes(customerCultivationIncomeResource);
    }

    private void customerIncomeExpenseRequestResourceValidateException(
            CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource, ServiceEntity serviceEntity, String message, Integer index) {
        List<BusinessIncomeExpenseRequestResource> businessIncomeExpenseRequestResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            businessIncomeExpenseRequestResource.add(i, new BusinessIncomeExpenseRequestResource());
        }
        switch (serviceEntity) {
            case BUSINESS_INCOME_EXPENSE_ID:
                businessIncomeExpenseRequestResource.get(index).setId(message);
                break;
            case BUSINESS_TYPE_ID:
                businessIncomeExpenseRequestResource.get(index).setBusinessTypesId(message);
                break;
            case VERSION:
                businessIncomeExpenseRequestResource.get(index).setVersion(message);
                break;
            default:
        }

        customerIncomeExpenseRequestResource.setBusinessIncomeExpenses(businessIncomeExpenseRequestResource);
    }

    private void creditAppraiselCollateralDetailsResourceValidateException(
            CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetailsAddResource, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case CUSTOMER_ID:
                creditAppraiselCollateralDetailsAddResource.setCustomerId(message);
                break;
            case ASSETS_ENTITY_ID:
                creditAppraiselCollateralDetailsAddResource.setAssetEntityId(message);
                break;
            case ASSETS_DETAIL_ID:
                creditAppraiselCollateralDetailsAddResource.setAssetDetailId(message);
                break;
            case ASSET_SUB_TYPE_ID:
                creditAppraiselCollateralDetailsAddResource.setAssetSubTypeId(message);
                break;
            case ASSETS_PART_ID:
                creditAppraiselCollateralDetailsAddResource.setAssetPartId(message);
                break;
            case ASSETS_PART_NAME:
                creditAppraiselCollateralDetailsAddResource.setAssetPartName(message);
                break;
            case SUB_TYPE_NAME:
                creditAppraiselCollateralDetailsAddResource.setSubTypeName(message);
                break;
            case SUUPLIER_REF_CODE:
                creditAppraiselCollateralDetailsAddResource.setSupplierReferenceCode(message);
                break;
            case REG_AUTHORITY:
                creditAppraiselCollateralDetailsAddResource.setRegistrationAuthority(message);
                break;
            case PLEDGE_TYPE_NAME:
                creditAppraiselCollateralDetailsAddResource.setPledgeTypeName(message);
                break;
            case ASSET_TYPE_ID:
                creditAppraiselCollateralDetailsAddResource.setAssetTypeId(message);
                break;
            case ASSET_REGISTR_AUTH_ID:
                creditAppraiselCollateralDetailsAddResource.setRegistrationAuthorityId(message);
                break;
            case ASSET_SUPPLIER_ID:
                creditAppraiselCollateralDetailsAddResource.setSupplierId(message);
                break;
            case PLEDGE_TYPE:
                creditAppraiselCollateralDetailsAddResource.setPledgeTypeId(message);
                break;
            case SUB_TYPE_ID:
                creditAppraiselCollateralDetailsAddResource.setSubTypeId(message);
                break;

            case COMN_CUST_ID:
                creditAppraiselCollateralDetailsAddResource.setComnCustomerId(message);
                break;
            case PEN_CUSTOMER_ID:
                creditAppraiselCollateralDetailsAddResource.setPendingCustomerId(message);
                break;

            default:
        }
    }


    private void customerCultivationIncomeResourceValidateException(
            CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource, ServiceEntity serviceEntity, String message, Integer index) {
        List<CustomerCultivationIncomeResource> customerCultivationIncomeResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            customerCultivationIncomeResource.add(i, new CustomerCultivationIncomeResource());
        }
        switch (serviceEntity) {
            case OWNERSHIP_ID:
                customerCultivationIncomeResource.get(index).setOwnershipId(message);
                break;
            case LAND_OWNERSHIP_ID:
                customerCultivationIncomeResource.get(index).setLandOwnershipId(message);
                break;
            case NO_OF_UNIT_ID:
                customerCultivationIncomeResource.get(index).setNoOfUnitId(message);
                break;
            case CULTIVATION_CATEGORY_ID:
                customerCultivationIncomeResource.get(index).setCultivationCategoryId(message);
                break;
            case CUSTOMER_CULTIVATION_INCOME_ID:
                customerCultivationIncomeResource.get(index).setId(message);
                break;
            case VERSION:
                customerCultivationIncomeResource.get(index).setVersion(message);
                break;
            default:
        }
        customerIncomeExpenseRequestResource.setCustomerCultivationIncomes(customerCultivationIncomeResource);
    }

    private void otherDetailsAddRequestResourceValidateException(
            OtherDetailsAddRequestResource otherDetailsAddRequestResource, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case PURPOSE_ID:
                otherDetailsAddRequestResource.setPurposeId(message);
                break;
            default:
        }

    }


    private void newFacilityAddResourceValidateException(NewFacilityAddResource newFacilityAddResource, ServiceEntity serviceEntity,
                                                         String message, Integer index) {
        List<FacilityContractDetailsResource> contractDetails = new ArrayList<>();
        List<FacilityOtherProductsResource> otherProducts = new ArrayList<>();

        for (int i = 0; i <= index; i++) {
            contractDetails.add(i, new FacilityContractDetailsResource());
            otherProducts.add(i, new FacilityOtherProductsResource());
        }
        switch (serviceEntity) {
            case CONTRACT_DETAILS_ID:
                contractDetails.get(index).setId(message);
                break;
            case OTHER_PRODUCT_DETAILS_ID:
                otherProducts.get(index).setId(message);
                break;
            default:
        }
        newFacilityAddResource.setContractDetails(contractDetails);
        newFacilityAddResource.setOtherProducts(otherProducts);
    }

    @ExceptionHandler({CustomerIncomeExpenseValidateException.class})
    public ResponseEntity<Object> customerIncomeExpenseValidateException(CustomerIncomeExpenseValidateException ex, WebRequest request) {
        CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource = null;

        switch (ex.getServicePoint()) {
            case INCOME_EXPENSE_DETAILS:
                customerIncomeExpenseRequestResource = new CustomerIncomeExpenseRequestResource();
                incomeExpenseDetailsResourceListValidateException(customerIncomeExpenseRequestResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex(), ex.getIndex1());
                break;
            case INCOME_EXPENSE_TAX:
                customerIncomeExpenseRequestResource = new CustomerIncomeExpenseRequestResource();
                incomeExpenseTaxResourceListValidateException(customerIncomeExpenseRequestResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex(), ex.getIndex1());
                break;
            case CUSTOMER_CUTIVATION_EXPENSE:
                customerIncomeExpenseRequestResource = new CustomerIncomeExpenseRequestResource();
                customerCultivationIncomeResourceListValidateException(customerIncomeExpenseRequestResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex(), ex.getIndex1());
                break;
            default:
        }
        TenantHolder.clear();
        if (customerIncomeExpenseRequestResource != null) {
            return new ResponseEntity<>(customerIncomeExpenseRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private void linkedPersonResourceValidateException(
            LinkedPersonResource linkedPersonResource, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case RELATION_TYPE:
                linkedPersonResource.setRelationshipTypeId(message);
                break;
            case PERSON_ID:
                linkedPersonResource.setPerId(message);
                break;
            default:
        }

    }

    private void leadInforBranchAssignUpdateResourceValidateException(
            LeadInforBranchAssignUpdateResource leadInforBranchAssignUpdateResource, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case BRANCH_ID:
                leadInforBranchAssignUpdateResource.setBranchId(message);
                break;
            default:
        }

    }

    private void otherIncomeExpenseTypeAddResourceListValidateException(
            OtherIncomeExpenseTypeAddResource otherIncomeExpenseTypeAddResource,
            ServiceEntity serviceEntity, String message, Integer index) {

        List<ExpenseTypeListResource> expenseTypeListResource = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            expenseTypeListResource.add(i, new ExpenseTypeListResource());
        }
        switch (serviceEntity) {
            case EXPENSE_TYPE_ID:
                expenseTypeListResource.get(index).setExpenseTypeId(message);
                break;
            case EXPENCE_TYPE_OTHER_INCOME_CATEGORY_DET_ID:
                expenseTypeListResource.get(index).setId(message);
                break;
            default:
        }
        otherIncomeExpenseTypeAddResource.setExpenseTypeList(expenseTypeListResource);


    }

    private void analystUpdateResourceListValidateException(
            AnalystUpdateResource analystUpdateResource,
            ServiceEntity serviceEntity, String message, Integer index) {

        List<AnalystExceptionDetailsResource> analystExceptionDetailsResourceList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            analystExceptionDetailsResourceList.add(i, new AnalystExceptionDetailsResource());
        }
        switch (serviceEntity) {
            case ANALYST_EXCEPTION_DETAIL_ID:
                analystExceptionDetailsResourceList.get(index).setId(message);
                break;
            case EXCEPTION_TYPE_ID:
                analystExceptionDetailsResourceList.get(index).setExceptionTypeId(message);
                break;
            case VERSION:
                analystExceptionDetailsResourceList.get(index).setVersion(message);
                break;
            case AUTHORITY_ID:
                analystExceptionDetailsResourceList.get(index).setAuthorizedUserId(message);
                break;
            case ANALYST_EXCEPTION_DOCUMENT_ID:
                analystExceptionDetailsResourceList.get(index).setId(message);
                break;
            case DOCUMENT_ID:
                analystExceptionDetailsResourceList.get(index).setId(message);
                break;
            case DOCUMENT_VERSION:
                analystExceptionDetailsResourceList.get(index).setId(message);
                break;
            default:
        }
        analystUpdateResource.setAnalystExceptionDetailList(analystExceptionDetailsResourceList);


    }

    private void salaryIncomeDetailsAddResourceListValidateException(
            SalaryIncomeDetailsAddResource salaryIncomeDetailsAddResource,
            ServiceEntity serviceEntity, String message, Integer index) {

        List<SalaryIncomeDetailsListResource> salaryIncomeDetailsList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            salaryIncomeDetailsList.add(i, new SalaryIncomeDetailsListResource());
        }
        switch (serviceEntity) {
            case DESIGNATION:
                salaryIncomeDetailsList.get(index).setDesignationName(message);
                break;
            case JOB_TYPE_ID:
                salaryIncomeDetailsList.get(index).setJobTypeId(message);
                break;
            case SOURCE_TYPE:
                salaryIncomeDetailsList.get(index).setSourceType(message);
                break;
            default:
        }
        salaryIncomeDetailsAddResource.setSalaryIncomeDetailsList(salaryIncomeDetailsList);


    }

    private void salaryIncomeDetailsUpdateResourceListValidateException(
            SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource,
            ServiceEntity serviceEntity, String message, Integer index) {

        List<DocumentUpdateResource> salaryIncomeDocumentList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            salaryIncomeDocumentList.add(i, new DocumentUpdateResource());
        }
        switch (serviceEntity) {
            case ID:
                salaryIncomeDocumentList.get(index).setId(message);
                break;
            case VERSION:
                salaryIncomeDocumentList.get(index).setVersion(message);
                break;
            case DOCUMENT_ID:
                salaryIncomeDocumentList.get(index).setDocumentId(message);
                break;

            default:
        }
        salaryIncomeDetailsUpdateResource.setSalaryIncomeDocumentList(salaryIncomeDocumentList);


    }

    private void cultivationIncomeDetailsAddResourceListValidateException(
            CultivationIncomeDetailsAddResource cultivationIncomeDetailsAddResource, ServiceEntity serviceEntity,
            String message, Integer index) {

        List<CultivationIncomeDetailsListResource> cultivationIncomeDetailsList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            cultivationIncomeDetailsList.add(i, new CultivationIncomeDetailsListResource());
        }
        switch (serviceEntity) {
            case CULTIVATION_CATEGORY_ID:
                cultivationIncomeDetailsList.get(index).setCultivationCategoryId(message);
                break;
            case LAND_OWNERSHIP_ID:
                cultivationIncomeDetailsList.get(index).setLandOwnershipId(message);
                break;
            case LAND_OWNERSHIP_REF_CODE:
                cultivationIncomeDetailsList.get(index).setLandOwnershipId(message);
                break;
            case PLANT_OWNERSHIP_ID:
                cultivationIncomeDetailsList.get(index).setPlantOwnershipId(message);
                break;
            case PLANT_OWNERSHIP_REF_CODE:
                cultivationIncomeDetailsList.get(index).setPlantOwnershipId(message);
                break;
            case SOURCE_TYPE:
                cultivationIncomeDetailsList.get(index).setSourceType(message);
                break;
            default:
        }
        cultivationIncomeDetailsAddResource.setCultivationIncomeDetailsList(cultivationIncomeDetailsList);

    }

    private void businessEmploymentDetailsResourceListValidateException(
            BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource, ServiceEntity serviceEntity,
            String message, Integer index) {

        List<BusinessEmploymentDetailResource> businessEmploymentDetailResourceList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            businessEmploymentDetailResourceList.add(i, new BusinessEmploymentDetailResource());
        }
        switch (serviceEntity) {
            case ID:
                businessEmploymentDetailResourceList.get(index).setId(message);
                break;
            case EMPLOYMENT_TYPE_ID:
                businessEmploymentDetailResourceList.get(index).setEmploymentTypeId(message);
                break;
            case VERSION:
                businessEmploymentDetailResourceList.get(index).setVersion(message);
                break;
            default:
        }
        businessAdditionalDetailsUpdateResource.setBusinessEmploymentdetailList(businessEmploymentDetailResourceList);

    }

    private void businessDocumentDetailsResourceListValidateException(
            BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource, ServiceEntity serviceEntity,
            String message, Integer index) {

        List<DocumentUpdateResource> documentUpdateResourceList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            documentUpdateResourceList.add(i, new DocumentUpdateResource());
        }
        switch (serviceEntity) {
            case ID:
                documentUpdateResourceList.get(index).setId(message);
                break;
            case DOCUMENT_ID:
                documentUpdateResourceList.get(index).setDocumentId(message);
                break;
            case DOCUMENT_NAME:
                documentUpdateResourceList.get(index).setDocumentName(message);
                break;
            case VERSION:
                documentUpdateResourceList.get(index).setVersion(message);
                break;
            default:
        }
        businessAdditionalDetailsUpdateResource.setBusinessDocumentList(documentUpdateResourceList);

    }

    private void businessClearanceDetailsResourceListValidateException(
            BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource, ServiceEntity serviceEntity,
            String message, Integer index) {

        List<BusinessClearanceDetailResource> businessClearanceDetailResourceList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            businessClearanceDetailResourceList.add(i, new BusinessClearanceDetailResource());
        }
        switch (serviceEntity) {
            case ID:
                businessClearanceDetailResourceList.get(index).setId(message);
                break;
            case CLEARANCE_TYPE_ID:
                businessClearanceDetailResourceList.get(index).setClearanceTypeId(message);
                break;
            case CLEARANCE_TYPE_NAME:
                businessClearanceDetailResourceList.get(index).setClearanceTypeName(message);
                break;
            case CLEARANCE_AUTHORITY_ID:
                businessClearanceDetailResourceList.get(index).setAuthorityId(message);
                break;
            case CLEARANCE_AUTHORITY_NAME:
                businessClearanceDetailResourceList.get(index).setAuthorityName(message);
                break;
            case OBTAIN_DATE:
                businessClearanceDetailResourceList.get(index).setObtainDate(message);
                break;
            case EXPIRE_DATE:
                businessClearanceDetailResourceList.get(index).setExpireDate(message);
                break;
            case VERSION:
                businessClearanceDetailResourceList.get(index).setVersion(message);
                break;
            default:
        }
        businessAdditionalDetailsUpdateResource.setBusinessClearancedetailList(businessClearanceDetailResourceList);


    }

    private void businessRiskDetailsResourceListListValidateException(
            BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource, ServiceEntity serviceEntity,
            String message, Integer index) {

        List<BusinessRiskDetailResource> businessRiskDetailResourceList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            businessRiskDetailResourceList.add(i, new BusinessRiskDetailResource());
        }
        switch (serviceEntity) {
            case ID:
                businessRiskDetailResourceList.get(index).setId(message);
                break;
            case RISK_TYPE_ID:
                businessRiskDetailResourceList.get(index).setRiskTypeId(message);
                break;
            case RISK_TYPE_NAME:
                businessRiskDetailResourceList.get(index).setRiskTypeName(message);
                break;
            case RISK_GRADING_ID:
                businessRiskDetailResourceList.get(index).setRiskGradingId(message);
                break;
            case RISK_GRADING_NAME:
                businessRiskDetailResourceList.get(index).setRiskGradingName(message);
                break;
            case RISK_LEVEL_ID:
                businessRiskDetailResourceList.get(index).setRiskRatingLevelId(message);
                break;
            case RISK_LEVEL_NAME:
                businessRiskDetailResourceList.get(index).setRiskRatingType(message);
                break;
            case RISK_AUTHORITY_ID:
                businessRiskDetailResourceList.get(index).setRiskAuthorityId(message);
                break;
            case RISK_AUTHORITY_NAME:
                businessRiskDetailResourceList.get(index).setRiskAuthorityName(message);
                break;
            case VERSION:
                businessRiskDetailResourceList.get(index).setVersion(message);
                break;
            default:
        }
        businessAdditionalDetailsUpdateResource.setBusinessRiskdetailList(businessRiskDetailResourceList);

    }

    private void businessAssetDetailsResourceListValidateException(
            BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource, ServiceEntity serviceEntity,
            String message, Integer index) {

        List<BusinessAssetDetailResource> businessAssetDetailResourceList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            businessAssetDetailResourceList.add(i, new BusinessAssetDetailResource());
        }
        switch (serviceEntity) {
            case ID:
                businessAssetDetailResourceList.get(index).setId(message);
                break;
            case ASSET_TYPE_ID:
                businessAssetDetailResourceList.get(index).setAssetTypeId(message);
                break;
            case ASSET_TYPE_NAME:
                businessAssetDetailResourceList.get(index).setAssetTypeName(message);
                break;
            case ASSET_OWNERSHIP_ID:
                businessAssetDetailResourceList.get(index).setOwnershipId(message);
                break;
            case ASSET_OWNERSHIP_NAME:
                businessAssetDetailResourceList.get(index).setOwnershipName(message);
                break;
            case VERSION:
                businessAssetDetailResourceList.get(index).setVersion(message);
                break;
            default:
        }
        businessAdditionalDetailsUpdateResource.setBusinessAssetdetailList(businessAssetDetailResourceList);

    }

    private void cultivationIncomeDetailsUpdateResourceListValidateException(
            CultivationIncomeDetailsUpdateResource cultivationIncomeDetailsUpdateResource, ServiceEntity serviceEntity,
            String message, Integer index) {

        List<DocumentUpdateResource> cultivationIncomeDocumentList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            cultivationIncomeDocumentList.add(i, new DocumentUpdateResource());
        }
        switch (serviceEntity) {
            case ID:
                cultivationIncomeDocumentList.get(index).setId(message);
                break;
            case VERSION:
                cultivationIncomeDocumentList.get(index).setVersion(message);
                break;
            case DOCUMENT_ID:
                cultivationIncomeDocumentList.get(index).setDocumentId(message);
                break;

            default:
        }
        cultivationIncomeDetailsUpdateResource.setCultivationIncomeDocumentList(cultivationIncomeDocumentList);


    }


    private void businessIncomeDetailsUpdateResourceListValidateException(
            BusinessIncomeDetailsUpdateResource businessIncomeDetailsUpdateResource, ServiceEntity serviceEntity,
            String message, Integer index) {

        List<DocumentUpdateResource> documentUpdateResourceList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            documentUpdateResourceList.add(i, new DocumentUpdateResource());
        }
        switch (serviceEntity) {
            case ID:
                documentUpdateResourceList.get(index).setId(message);
                break;
            case DOCUMENT_ID:
                documentUpdateResourceList.get(index).setDocumentId(message);
                break;
            case DOCUMENT_NAME:
                documentUpdateResourceList.get(index).setDocumentName(message);
                break;
            case VERSION:
                documentUpdateResourceList.get(index).setVersion(message);
                break;
            default:
        }
        businessIncomeDetailsUpdateResource.setBusinessIncomeDocumentList(documentUpdateResourceList);

    }

    private void businessIncomeExpensesAddResourceListValidateException(
            BusinessIncomeExpensesAddResource businessIncomeExpensesAddResource, ServiceEntity serviceEntity,
            String message, Integer index) {

        List<BusinessIncomeExpenseTypeResource> businessIncomeExpenseTypeResourceList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            businessIncomeExpenseTypeResourceList.add(i, new BusinessIncomeExpenseTypeResource());
        }
        switch (serviceEntity) {
            case BUSINESS_INCOME_TYPE:
                businessIncomeExpenseTypeResourceList.get(index).setBusinessIncomeTypeId(message);
                break;
            case BUSINESS_INCOME_TYPE_NAME:
                businessIncomeExpenseTypeResourceList.get(index).setBusinessIncomeTypeName(message);
                break;
            case BUSINESS_EXPENSE_TYPE:
                businessIncomeExpenseTypeResourceList.get(index).setBusinessExpenseTypeId(message);
                break;
            case FREQUENCY_NAME:
                businessIncomeExpenseTypeResourceList.get(index).setFrequencyName(message);
                break;
            default:
        }
        businessIncomeExpensesAddResource.setBusinessIncomeExenseTypes(businessIncomeExpenseTypeResourceList);

    }

    private void businessTaxDetailsAddResourceListValidateException(
            BusinessTaxDetailsAddResource businessTaxDetailsAddResource, ServiceEntity serviceEntity, String message,
            Integer index) {

        List<BusinessTaxTypeResource> businessTaxTypeResourceList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            businessTaxTypeResourceList.add(i, new BusinessTaxTypeResource());
        }
        switch (serviceEntity) {
            case FREQUENCY_NAME:
                businessTaxTypeResourceList.get(index).setFrequencyName(message);
                break;
            default:
        }
        businessTaxDetailsAddResource.setBusinessTaxDetails(businessTaxTypeResourceList);


    }

    private void financialStatementUpdateResourceListValidateException(
            FinancialStatementUpdateResource financialStatementUpdateResource, ServiceEntity serviceEntity, String message,
            Integer index) {

        List<FinancialStatementDetailsUpdateResource> docList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            docList.add(i, new FinancialStatementDetailsUpdateResource());
        }
        switch (serviceEntity) {
            case FINANCIAL_STATEMENT_LEVEL:
                docList.get(index).setLevelId(message);
                break;
            case VERSION:
                docList.get(index).setVersion(message);
            default:
        }
        financialStatementUpdateResource.setLevels(docList);


    }

    private void financialStatementAddResourceListValidateException(
            FinanceStatementAddRequest financeStatementAddRequest, ServiceEntity serviceEntity, String message,
            Integer index) {

        List<DocumentRefAddResource> docList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            docList.add(i, new DocumentRefAddResource());
        }
        switch (serviceEntity) {
            case ID:
                docList.get(index).setId(message);
                break;
            case DOCUMENT_ID:
                docList.get(index).setDocumentId(message);
                break;
            case VERSION:
                docList.get(index).setVersion(message);
                break;
            case DOCUMENT_NAME:
                docList.get(index).setDocumentName(message);
                break;
            default:
        }
        financeStatementAddRequest.setDocumentList(docList);


    }


    private void financialStatementDetailMainResourceListValidateException(
            FinancialStatementDetailMainResource financialStatementDetailMainResource, ServiceEntity serviceEntity, String message,
            Integer index) {

        List<FinancialStatementDetailAddResource> detailList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            detailList.add(i, new FinancialStatementDetailAddResource());
        }
        switch (serviceEntity) {
            case FINANCIAL_STATEMENT_TEMPLATE:
                detailList.get(index).setFinancailStatementTemplateDetailId(message);
                break;
            default:
        }
        financialStatementDetailMainResource.setStatementDetailList(detailList);

    }


    private void financialStatementDetailNoteListValidateException(
            FinancialStatementDetailMainResource financialStatementDetailMainResource, ServiceEntity serviceEntity, String message,
            Integer index) {

        List<FinancialStatementDetailAddResource> detailList = new ArrayList<>();
        detailList.add(0, new FinancialStatementDetailAddResource());

        List<FinancialStatementDetailNoteRequest> noteList = new ArrayList<>();

        for (int i = 0; i <= index; i++) {
            noteList.add(i, new FinancialStatementDetailNoteRequest());
        }
        switch (serviceEntity) {
            case NOTE_ID:
                noteList.get(index).setNoteId(message);
            case VERSION:
                noteList.get(index).setVersion(message);
                break;
            default:
        }
        detailList.get(0).setNoteList(noteList);
        financialStatementDetailMainResource.setStatementDetailList(detailList);

    }

    private void customerResourceLinkedPersonListValidateException(CustomerResource customerResource,
                                                                   ServiceEntity serviceEntity, String message, Integer index) {

        List<LinkedPersonResource> detailList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            detailList.add(i, new LinkedPersonResource());
        }
        switch (serviceEntity) {
            case RELATIONSHIP_TYPE_ID:
                detailList.get(index).setRelationshipTypeId(message);
                break;
            case GENDER_ID:
                detailList.get(index).setGenderId(message);
                break;
            case TITLE:
                detailList.get(index).setTitleId(message);
            case ID:
                detailList.get(index).setId(message);
                break;
            default:
        }
        customerResource.setLinkedPersonList(detailList);
    }

    private void documentCheckListUpdateResourceListValidateException(
            DocumentCheckListUpdateResource documentCheckListUpdateResource, ServiceEntity serviceEntity,
            String message, Integer index) {

        List<DocumentUpdateResource> documentList = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            documentList.add(i, new DocumentUpdateResource());
        }
        switch (serviceEntity) {
            case ID:
                documentList.get(index).setId(message);
                break;
            case VERSION:
                documentList.get(index).setVersion(message);
                break;
            case DOCUMENT_ID:
                documentList.get(index).setDocumentId(message);
                break;
            case DOCUMENT_NAME:
                documentList.get(index).setDocumentName(message);
                break;

            default:
        }
        documentCheckListUpdateResource.setDocumentDetails(documentList);
    }

}
