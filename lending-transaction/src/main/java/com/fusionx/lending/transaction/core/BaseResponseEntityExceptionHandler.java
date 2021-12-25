/*
 * Copyright (c) 2018. LOLC Technology Services Ltd.
 * Author: Ranjith Kodikara
 * Date: 12/12/18 10:45
 */

package com.fusionx.lending.transaction.core;

import com.fusionx.lending.transaction.ErrorCode;
import com.fusionx.lending.transaction.enums.ServiceEntity;
import com.fusionx.lending.transaction.exception.*;
import com.fusionx.lending.transaction.exception.ValidateRecordException;
import com.fusionx.lending.transaction.mt.TenantHolder;
import com.fusionx.lending.transaction.resource.*;
import com.fusionx.lending.transaction.responsehandler.CommonResponseHandler;
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

/**
 * Base Response Entity Exception Handler
 *
 * @author Sanatha
 * @Date 16-APR-2020
 * <p>
 * *******************************************************************************************************
 * ###   Date         		Story Point   	Task No    	Author       	Description
 * -------------------------------------------------------------------------------------------------------
 * 1   16-APR-2020  		FX-3099			FX-3301		Sanatha			Added Version history and casaGlAddResource for CASA GL
 * <p>
 * *******************************************************************************************************
 */

/**
 *
 * This will return the relevant object based on the caught exception
 *
 * @author ranjithk
 * @since 2018-12-13
 * @version 1.0
 *
 */

@RestControllerAdvice
public class BaseResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private String tenantNotFound = "common.tenant-not-found";
    private String commonInternalServerError = "common.internal-server-error";
    private String userNotFound = "common.user-not-found";
    private String project = "lending-transaction";

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
        successAndErrorDetailsDataObject.setMessages(ex.getMessage());
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({OtherException.class})
    public ResponseEntity<Object> otherException(OtherException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        LoggerRequest.getInstance().logInfo("********************************" + ex.getMessage() + "*********************************************" + ex.getStatus());
        successAndErrorDetailsResource.setDetails(ex.getMessage());
        successAndErrorDetailsResource.setMessages(ex.getMessage());
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({NoRecordFoundException.class})
    public ResponseEntity<Object> noRecordFoundException(NoRecordFoundException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsDataObject.setMessages(ex.getMessage());
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({CodeCannotChangeException.class})
    public ResponseEntity<Object> codeCannotChangeException(CodeCannotChangeException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsDataObject.setMessages(ex.getMessage());
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({OtherCommonException.class})
    public ResponseEntity<Object> otherCommonException(OtherCommonException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsDataObject.setMessages(ex.getMessage());
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({PageableLengthException.class})
    public ResponseEntity<Object> pageableLengthException(PageableLengthException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsResource.setMessages(ex.getMessage());
        TenantHolder.clear();
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({BackendDataException.class})
    public ResponseEntity<Object> backendDataException(BackendDataException ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        LoggerRequest.getInstance().logInfo("********************************" + ex.getMessage() + "*********************************************" + ex.getCode());
        successAndErrorDetailsResource.setDetails(ex.getMessage());
        successAndErrorDetailsResource.setMessages(ex.getCode());
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Field sField = null;
        String fieldName = null;
        Integer index = null;
        Integer index1 = null;
        Integer atPoint = null;
        try {
            String className = ex.getBindingResult().getObjectName();
            if (className.equals("bankTransactionCodeResource")) {
                BankTransactionCodeResource bankTransactionCodeResource = new BankTransactionCodeResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                    if (error.getField().startsWith("createdUser")) {
                        sField = bankTransactionCodeResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(bankTransactionCodeResource, error.getDefaultMessage());
                    } else {
                        sField = bankTransactionCodeResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(bankTransactionCodeResource.getClass().cast(bankTransactionCodeResource), error.getDefaultMessage());
                    }
                }
                return new ResponseEntity<>(bankTransactionCodeResource, HttpStatus.UNPROCESSABLE_ENTITY);
            } else if (className.equals("bankTransactionCodeUpdateResource")) {
                BankTransactionCodeUpdateResource bankTransactionCodeUpdateResource = new BankTransactionCodeUpdateResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                    if (error.getField().startsWith("modifiedUser")) {
                        sField = bankTransactionCodeUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(bankTransactionCodeUpdateResource, error.getDefaultMessage());
                    } else if (error.getField().startsWith("version")) {
                        sField = bankTransactionCodeUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(bankTransactionCodeUpdateResource, error.getDefaultMessage());
                    } else {
                        sField = bankTransactionCodeUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(bankTransactionCodeUpdateResource.getClass().getSuperclass().cast(bankTransactionCodeUpdateResource), error.getDefaultMessage());
                    }
                }
                return new ResponseEntity<>(bankTransactionCodeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
            } else if (className.equals("bankTransactionSubCodeResource")) {
                BankTransactionSubCodeResource bankTransactionSubCodeResource = new BankTransactionSubCodeResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
					/*if(error.getField().startsWith("createdUser")) {
						sField =  bankTransactionSubCodeResource.getClass().getDeclaredField(error.getField());
		                sField.setAccessible(true);
		                sField.set(bankTransactionSubCodeResource, error.getDefaultMessage());
		            }
					else {*/
                    sField = bankTransactionSubCodeResource.getClass().getDeclaredField(error.getField());
                    sField.setAccessible(true);
                    sField.set(bankTransactionSubCodeResource.getClass().cast(bankTransactionSubCodeResource), error.getDefaultMessage());
                    // }
                }
                return new ResponseEntity<>(bankTransactionSubCodeResource, HttpStatus.UNPROCESSABLE_ENTITY);
            } else if (className.equals("bankTransactionSubCodeUpdateResource")) {
                BankTransactionSubCodeUpdateResource bankTransactionSubCodeUpdateResource = new BankTransactionSubCodeUpdateResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                    if (error.getField().startsWith("modifiedUser")) {
                        sField = bankTransactionSubCodeUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(bankTransactionSubCodeUpdateResource, error.getDefaultMessage());
                    } else if (error.getField().startsWith("version")) {
                        sField = bankTransactionSubCodeUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(bankTransactionSubCodeUpdateResource, error.getDefaultMessage());
                    } else {
                        sField = bankTransactionSubCodeUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(bankTransactionSubCodeUpdateResource.getClass().getSuperclass().cast(bankTransactionSubCodeUpdateResource), error.getDefaultMessage());
                    }
                }
                return new ResponseEntity<>(bankTransactionSubCodeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
            } else if (className.equals("addTransEventSubCode")) {

                AddTransEventSubCode addTransEventSubCode = new AddTransEventSubCode();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                    if (error.getField().startsWith("createdUser")) {
                        sField = addTransEventSubCode.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(addTransEventSubCode, error.getDefaultMessage());
                    } else {
                        sField = addTransEventSubCode.getClass().getSuperclass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(addTransEventSubCode.getClass().getSuperclass().cast(addTransEventSubCode), error.getDefaultMessage());
                    }
                }
                return new ResponseEntity<>(addTransEventSubCode, HttpStatus.UNPROCESSABLE_ENTITY);
            } else if (className.equals("updateTransEventSubCode")) {
                UpdateTransEventSubCode updateTransEventSubCode = new UpdateTransEventSubCode();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                    if (error.getField().startsWith("modifiedUser")) {
                        sField = updateTransEventSubCode.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(updateTransEventSubCode, error.getDefaultMessage());
                    } else if (error.getField().startsWith("version")) {
                        sField = updateTransEventSubCode.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(updateTransEventSubCode, error.getDefaultMessage());
                    } else {
                        sField = updateTransEventSubCode.getClass().getSuperclass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(updateTransEventSubCode.getClass().getSuperclass().cast(updateTransEventSubCode), error.getDefaultMessage());
                    }
                }
                return new ResponseEntity<>(updateTransEventSubCode, HttpStatus.UNPROCESSABLE_ENTITY);
            }

//**********************************************************************************************************************
			else if (className.equals("addTaxCodeRequestResource")) {
				AddTaxCodeRequestResource addTaxCodeRequestResource = new AddTaxCodeRequestResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					LoggerRequest.getInstance()
							.logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
					if (error.getField().startsWith("taxCodeCreatedUser")) {
						sField = addTaxCodeRequestResource.getClass().getDeclaredField(error.getField());
						sField.setAccessible(true);
						sField.set(addTaxCodeRequestResource, error.getDefaultMessage());
					} else {
						sField = addTaxCodeRequestResource.getClass().getSuperclass()
								.getDeclaredField(error.getField());
						sField.setAccessible(true);
						sField.set(addTaxCodeRequestResource.getClass().getSuperclass().cast(addTaxCodeRequestResource),
								error.getDefaultMessage());
					}
				}
				return new ResponseEntity<>(addTaxCodeRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);

			} else if (className.equals("updateTaxCodeRequestResource")) {
				UpdateTaxCodeRequestResource updateTaxCodeRequestResource = new UpdateTaxCodeRequestResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					LoggerRequest.getInstance()
							.logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
					if (error.getField().startsWith("modifiedUser")) {
						sField = updateTaxCodeRequestResource.getClass().getDeclaredField(error.getField());
						sField.setAccessible(true);
						sField.set(updateTaxCodeRequestResource, error.getDefaultMessage());
					} else if (error.getField().startsWith("version")) {
						sField = updateTaxCodeRequestResource.getClass().getDeclaredField(error.getField());
						sField.setAccessible(true);
						sField.set(updateTaxCodeRequestResource, error.getDefaultMessage());
					} else {
						sField = updateTaxCodeRequestResource.getClass().getSuperclass()
								.getDeclaredField(error.getField());
						sField.setAccessible(true);
						sField.set(updateTaxCodeRequestResource.getClass().getSuperclass()
								.cast(updateTaxCodeRequestResource), error.getDefaultMessage());
					}
				}
				return new ResponseEntity<>(updateTaxCodeRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}

//**********************************************************************************************************************
			else if (className.equals("addTaxEventRequestResource")) {
				AddTaxEventRequestResource addTaxEventRequestResource = new AddTaxEventRequestResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					LoggerRequest.getInstance()
							.logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
//					if (error.getField().startsWith("taxEventCreatedUser")) {
//						sField = addTaxEventDetailsRequestResource.getClass().getDeclaredField(error.getField());
//						sField.setAccessible(true);
//						sField.set(addTaxEventDetailsRequestResource, error.getDefaultMessage());
//					} else {
						sField = addTaxEventRequestResource.getClass().getSuperclass()
								.getDeclaredField(error.getField());
						sField.setAccessible(true);
						sField.set(
								addTaxEventRequestResource.getClass().getSuperclass().cast(addTaxEventRequestResource),
								error.getDefaultMessage());
//					}
				}
				return new ResponseEntity<>(addTaxEventRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
				
			} else if (className.equals("updateTaxEventRequestResource")) {
				UpdateTaxEventRequestResource updateTaxEventRequestResource = new UpdateTaxEventRequestResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					LoggerRequest.getInstance()
							.logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
					fieldName = error.getField();
					if (fieldName.startsWith("version")) {
						sField = updateTaxEventRequestResource.getClass().getDeclaredField(error.getField());
						sField.setAccessible(true);
						sField.set(updateTaxEventRequestResource, error.getDefaultMessage());
					} else {
						sField = updateTaxEventRequestResource.getClass().getSuperclass().getDeclaredField(error.getField());
						sField.setAccessible(true);
						sField.set(updateTaxEventRequestResource.getClass().getSuperclass().cast(updateTaxEventRequestResource),
								error.getDefaultMessage());
					}
				}
				return new ResponseEntity<>(updateTaxEventRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}

//**********************************************************************************************************************
			else if (className.equals("addTaxEventDetailsRequestResource")) {

				AddTaxEventDetailsRequestResource addTaxEventDetailsRequestResource = new AddTaxEventDetailsRequestResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					LoggerRequest.getInstance()
							.logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
//					if (error.getField().startsWith("taxEventCreatedUser")) {
//						sField = addTaxEventDetailsRequestResource.getClass().getDeclaredField(error.getField());
//						sField.setAccessible(true);
//						sField.set(addTaxEventDetailsRequestResource, error.getDefaultMessage());
//					} else {
						sField = addTaxEventDetailsRequestResource.getClass().getSuperclass()
								.getDeclaredField(error.getField());
						sField.setAccessible(true);
						sField.set(
								addTaxEventDetailsRequestResource.getClass().getSuperclass().cast(addTaxEventDetailsRequestResource),
								error.getDefaultMessage());
//					}
				}
				return new ResponseEntity<>(addTaxEventDetailsRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);

			} else if (className.equals("updateTaxEventDetailsRequestResource")) {
				UpdateTaxEventDetailsRequestResource updateTaxEventDetailsRequestResource = new UpdateTaxEventDetailsRequestResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					LoggerRequest.getInstance()
							.logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
					if (error.getField().startsWith("modifiedUser")) {
						sField = updateTaxEventDetailsRequestResource.getClass().getDeclaredField(error.getField());
						sField.setAccessible(true);
						sField.set(updateTaxEventDetailsRequestResource, error.getDefaultMessage());
					} else if (error.getField().startsWith("version")) {
						sField = updateTaxEventDetailsRequestResource.getClass().getDeclaredField(error.getField());
						sField.setAccessible(true);
						sField.set(updateTaxEventDetailsRequestResource, error.getDefaultMessage());
					} else {
						sField = updateTaxEventDetailsRequestResource.getClass().getSuperclass()
								.getDeclaredField(error.getField());
						sField.setAccessible(true);
						sField.set(updateTaxEventDetailsRequestResource.getClass().getSuperclass()
								.cast(updateTaxEventDetailsRequestResource), error.getDefaultMessage());
					}
				}
				return new ResponseEntity<>(updateTaxEventDetailsRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);

			} 
            else if (className.equals("addTransEventAccStatus")) {

                AddTransEventAccStatus addTransEventAccStatus = new AddTransEventAccStatus();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                    if (error.getField().startsWith("createdUser")) {
                        sField = addTransEventAccStatus.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(addTransEventAccStatus, error.getDefaultMessage());
                    } else {
                        sField = addTransEventAccStatus.getClass().getSuperclass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(addTransEventAccStatus.getClass().getSuperclass().cast(addTransEventAccStatus), error.getDefaultMessage());
                    }
                }
                return new ResponseEntity<>(addTransEventAccStatus, HttpStatus.UNPROCESSABLE_ENTITY);
            } else if (className.equals("updateTransEventAccStatus")) {
                UpdateTransEventAccStatus updateTransEventAccStatus = new UpdateTransEventAccStatus();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                    if (error.getField().startsWith("modifiedUser")) {
                        sField = updateTransEventAccStatus.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(updateTransEventAccStatus, error.getDefaultMessage());
                    } else if (error.getField().startsWith("version")) {
                        sField = updateTransEventAccStatus.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(updateTransEventAccStatus, error.getDefaultMessage());
                    } else {
                        sField = updateTransEventAccStatus.getClass().getSuperclass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(updateTransEventAccStatus.getClass().getSuperclass().cast(updateTransEventAccStatus), error.getDefaultMessage());
                    }
                }
                return new ResponseEntity<>(updateTransEventAccStatus, HttpStatus.UNPROCESSABLE_ENTITY);
            } else if (className.equals("transactionEventAddResource")) {

                TransactionEventAddResource transactionEventAddResource = new TransactionEventAddResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());

                    sField = transactionEventAddResource.getClass().getDeclaredField(error.getField());
                    sField.setAccessible(true);
                    sField.set(transactionEventAddResource, error.getDefaultMessage());

                }
                return new ResponseEntity<>(transactionEventAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
            }else if (className.equals("transactionEventUpdateResource")) {
            	TransactionEventUpdateResource transactionEventUpdateResource = new TransactionEventUpdateResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                    if (error.getField().startsWith("modifiedUser")) {
                        sField = transactionEventUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(transactionEventUpdateResource, error.getDefaultMessage());
                    } else if (error.getField().startsWith("version")) {
                        sField = transactionEventUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(transactionEventUpdateResource, error.getDefaultMessage());
                    } else {
                        sField = transactionEventUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(transactionEventUpdateResource.getClass().getSuperclass().cast(transactionEventUpdateResource), error.getDefaultMessage());
                    }
                }
                return new ResponseEntity<>(transactionEventUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
            } 
            //Added by Sanatha on 16-APR-2020 for CASA GL
            else if (className.equals("allocationOrderResource")) {
                AllocationOrderResource allocationOrderResource = new AllocationOrderResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                    sField = allocationOrderResource.getClass().getDeclaredField(error.getField());
                    sField.setAccessible(true);
                    sField.set(allocationOrderResource, error.getDefaultMessage());
                }
                return new ResponseEntity<>(allocationOrderResource, HttpStatus.UNPROCESSABLE_ENTITY);

            } else if (className.equals("coreTransactionAddResource")) {

                CoreTransactionAddResource coreTransactionAddResource = new CoreTransactionAddResource();

                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    sField = coreTransactionAddResource.getClass().getDeclaredField(error.getField());
                    sField.setAccessible(true);
                    sField.set(coreTransactionAddResource.getClass().cast(coreTransactionAddResource), error.getDefaultMessage());
                }
                return new ResponseEntity<>(coreTransactionAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
            } else if (className.equals("coreTransactionUpdateResource")) {
                CoreTransactionUpdateResource coreTransactionUpdateResource = new CoreTransactionUpdateResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    sField = coreTransactionUpdateResource.getClass().getDeclaredField(error.getField());
                    sField.setAccessible(true);
                    sField.set(coreTransactionUpdateResource.getClass().cast(coreTransactionUpdateResource), error.getDefaultMessage());
                }
                return new ResponseEntity<>(coreTransactionUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
            } else if (className.equals("addScheduleEventResource")) {
                AddScheduleEventResource addScheduleEventResource = new AddScheduleEventResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    sField = addScheduleEventResource.getClass().getDeclaredField(error.getField());
                    sField.setAccessible(true);
                    sField.set(addScheduleEventResource.getClass().cast(addScheduleEventResource), error.getDefaultMessage());
                }
                return new ResponseEntity<>(addScheduleEventResource, HttpStatus.UNPROCESSABLE_ENTITY);
            } else if (className.equals("creditNoteAddResource")) {
                CreditNoteAddResource creditNoteAddResource = new CreditNoteAddResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                    sField = creditNoteAddResource.getClass().getDeclaredField(error.getField());
                    sField.setAccessible(true);
                    sField.set(creditNoteAddResource, error.getDefaultMessage());
                }
                return new ResponseEntity<>(creditNoteAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

            } else if (className.equals("creditNoteUpdateResource")) {
                CreditNoteUpdateResource creditNoteUpdateResource = new CreditNoteUpdateResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
                    sField = creditNoteUpdateResource.getClass().getDeclaredField(error.getField());
                    sField.setAccessible(true);
                    sField.set(creditNoteUpdateResource, error.getDefaultMessage());
                }
                return new ResponseEntity<>(creditNoteUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
            } 
        else if(className.equals("allocationTemplateAddResource")) {
			AllocationTemplateAddResource allocationTemplateAddResource = new AllocationTemplateAddResource();
			for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					LoggerRequest.getInstance().logInfo(project+ " " +error.getField()+" "+error.getDefaultMessage());
					sField =  allocationTemplateAddResource.getClass().getDeclaredField(error.getField());
	                sField.setAccessible(true);
	                sField.set(allocationTemplateAddResource, error.getDefaultMessage());
			}
			return new ResponseEntity<>(allocationTemplateAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
		
	}else if(className.equals("allocationTemplateUpdateResource")) {
		AllocationTemplateUpdateResource allocationTemplateUpdateResource = new AllocationTemplateUpdateResource();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			LoggerRequest.getInstance().logInfo(project+ " " +error.getField()+" "+error.getDefaultMessage());
			if(error.getField().startsWith("version")) {
				sField =  allocationTemplateUpdateResource.getClass().getDeclaredField(error.getField());
                sField.setAccessible(true);
                sField.set(allocationTemplateUpdateResource, error.getDefaultMessage());
            }
			else {
				sField =  allocationTemplateUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                sField.setAccessible(true);
                sField.set(allocationTemplateUpdateResource.getClass().getSuperclass().cast(allocationTemplateUpdateResource), error.getDefaultMessage());
			}
			
		}
		return new ResponseEntity<>(allocationTemplateUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
		
	} else if (className.equals("transEventCreditNoteAddResource")) {
		TransEventCreditNoteAddResource transEventCreditNoteAddResource = new TransEventCreditNoteAddResource();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
            sField = transEventCreditNoteAddResource.getClass().getDeclaredField(error.getField());
            sField.setAccessible(true);
            sField.set(transEventCreditNoteAddResource, error.getDefaultMessage());
        }
        return new ResponseEntity<>(transEventCreditNoteAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

    } else if (className.equals("transEventCreditNoteUpdateResource")) {
    	TransEventCreditNoteUpdateResource transEventCreditNoteUpdateResource = new TransEventCreditNoteUpdateResource();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
            sField = transEventCreditNoteUpdateResource.getClass().getDeclaredField(error.getField());
            sField.setAccessible(true);
            sField.set(transEventCreditNoteUpdateResource, error.getDefaultMessage());
        }
        return new ResponseEntity<>(transEventCreditNoteUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
    } else if (className.equals("waiveOffTypeAddResource")) {
    	WaiveOffTypeAddResource waiveOffTypeAddResource = new WaiveOffTypeAddResource();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
            sField = waiveOffTypeAddResource.getClass().getDeclaredField(error.getField());
            sField.setAccessible(true);
            sField.set(waiveOffTypeAddResource, error.getDefaultMessage());
        }
        return new ResponseEntity<>(waiveOffTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

    } else if (className.equals("waiveOffTypeUpdateResource")) {
    	WaiveOffTypeUpdateResource waiveOffTypeUpdateResource = new WaiveOffTypeUpdateResource();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
            if(error.getField().startsWith("version")) {
				sField =  waiveOffTypeUpdateResource.getClass().getDeclaredField(error.getField());
                sField.setAccessible(true);
                sField.set(waiveOffTypeUpdateResource, error.getDefaultMessage());
            }
			else {
				sField =  waiveOffTypeUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                sField.setAccessible(true);
                sField.set(waiveOffTypeUpdateResource.getClass().getSuperclass().cast(waiveOffTypeUpdateResource), error.getDefaultMessage());
			}
        }
        return new ResponseEntity<>(waiveOffTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
        
    } else if (className.equals("waiveOffTransactionTypeAddResource")) {
    	WaiveOffTransactionTypeAddResource waiveOffTransactionTypeAddResource = new WaiveOffTransactionTypeAddResource();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
            sField = waiveOffTransactionTypeAddResource.getClass().getDeclaredField(error.getField());
            sField.setAccessible(true);
            sField.set(waiveOffTransactionTypeAddResource, error.getDefaultMessage());
        }
        return new ResponseEntity<>(waiveOffTransactionTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

    } else if (className.equals("waiveOffTransactionTypeUpdateResource")) {
    	WaiveOffTransactionTypeUpdateResource waiveOffTransactionTypeUpdateResource = new WaiveOffTransactionTypeUpdateResource();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
            if(error.getField().startsWith("version")) {
				sField =  waiveOffTransactionTypeUpdateResource.getClass().getDeclaredField(error.getField());
                sField.setAccessible(true);
                sField.set(waiveOffTransactionTypeUpdateResource, error.getDefaultMessage());
            }
			else {
				sField =  waiveOffTransactionTypeUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                sField.setAccessible(true);
                sField.set(waiveOffTransactionTypeUpdateResource.getClass().getSuperclass().cast(waiveOffTransactionTypeUpdateResource), error.getDefaultMessage());
			}
        }
        return new ResponseEntity<>(waiveOffTransactionTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
    } else if (className.equals("waiveOffApprovalGroupAddResource")) {
    	WaiveOffApprovalGroupAddResource waiveOffApprovalGroupAddResource = new WaiveOffApprovalGroupAddResource();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
            sField = waiveOffApprovalGroupAddResource.getClass().getDeclaredField(error.getField());
            sField.setAccessible(true);
            sField.set(waiveOffApprovalGroupAddResource, error.getDefaultMessage());
        }
        return new ResponseEntity<>(waiveOffApprovalGroupAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

    } else if (className.equals("waiveOffApprovalGroupUpdateResource")) {
    	WaiveOffApprovalGroupUpdateResource waiveOffApprovalGroupUpdateResource = new WaiveOffApprovalGroupUpdateResource();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
            if(error.getField().startsWith("version")) {
				sField =  waiveOffApprovalGroupUpdateResource.getClass().getDeclaredField(error.getField());
                sField.setAccessible(true);
                sField.set(waiveOffApprovalGroupUpdateResource, error.getDefaultMessage());
            }
			else {
				sField =  waiveOffApprovalGroupUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                sField.setAccessible(true);
                sField.set(waiveOffApprovalGroupUpdateResource.getClass().getSuperclass().cast(waiveOffApprovalGroupUpdateResource), error.getDefaultMessage());
			}
        }
        return new ResponseEntity<>(waiveOffApprovalGroupUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
    } else if (className.equals("waiveOffApprovalTypesAddResource")) {
    	WaiveOffApprovalTypesAddResource waiveOffApprovalTypesAddResource = new WaiveOffApprovalTypesAddResource();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
            sField = waiveOffApprovalTypesAddResource.getClass().getDeclaredField(error.getField());
            sField.setAccessible(true);
            sField.set(waiveOffApprovalTypesAddResource, error.getDefaultMessage());
        }
        return new ResponseEntity<>(waiveOffApprovalTypesAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

    } else if (className.equals("waiveOffApprovalTypesUpdateResource")) {
    	WaiveOffApprovalTypesUpdateResource waiveOffApprovalTypesUpdateResource = new WaiveOffApprovalTypesUpdateResource();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
            if(error.getField().startsWith("version")) {
				sField =  waiveOffApprovalTypesUpdateResource.getClass().getDeclaredField(error.getField());
                sField.setAccessible(true);
                sField.set(waiveOffApprovalTypesUpdateResource, error.getDefaultMessage());
            }
			else {
				sField =  waiveOffApprovalTypesUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                sField.setAccessible(true);
                sField.set(waiveOffApprovalTypesUpdateResource.getClass().getSuperclass().cast(waiveOffApprovalTypesUpdateResource), error.getDefaultMessage());
			}
        }
        return new ResponseEntity<>(waiveOffApprovalTypesUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
    } else if (className.equals("waiveOffApprovalUsersAddResource")) {
    	WaiveOffApprovalUsersAddResource waiveOffApprovalUsersAddResource = new WaiveOffApprovalUsersAddResource();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
            sField = waiveOffApprovalUsersAddResource.getClass().getDeclaredField(error.getField());
            sField.setAccessible(true);
            sField.set(waiveOffApprovalUsersAddResource, error.getDefaultMessage());
        }
        return new ResponseEntity<>(waiveOffApprovalUsersAddResource, HttpStatus.UNPROCESSABLE_ENTITY);

    }else if (className.equals("creditNoteTransactionTypeAddResource")) {

    	CreditNoteTransactionTypeAddResource creditNoteTransactionTypeAddResource = new CreditNoteTransactionTypeAddResource();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            sField = creditNoteTransactionTypeAddResource.getClass().getDeclaredField(error.getField());
            sField.setAccessible(true);
            sField.set(creditNoteTransactionTypeAddResource.getClass().cast(creditNoteTransactionTypeAddResource), error.getDefaultMessage());
        }
        return new ResponseEntity<>(creditNoteTransactionTypeAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
    } else if (className.equals("creditNoteTransactionTypeUpdateResource")) {
    	CreditNoteTransactionTypeUpdateResource creditNoteTransactionTypeUpdateResource = new CreditNoteTransactionTypeUpdateResource();
    	for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            if(error.getField().startsWith("version")) {
				sField =  creditNoteTransactionTypeUpdateResource.getClass().getDeclaredField(error.getField());
                sField.setAccessible(true);
                sField.set(creditNoteTransactionTypeUpdateResource, error.getDefaultMessage());
            }
			else {
				sField =  creditNoteTransactionTypeUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                sField.setAccessible(true);
                sField.set(creditNoteTransactionTypeUpdateResource.getClass().getSuperclass().cast(creditNoteTransactionTypeUpdateResource), error.getDefaultMessage());
			}
    	}
        return new ResponseEntity<>(creditNoteTransactionTypeUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    
    else if (className.equals("waiveOffApprovalUsersUpdateResource")) {
    	WaiveOffApprovalUsersUpdateResource waiveOffApprovalUsersUpdateResource = new WaiveOffApprovalUsersUpdateResource();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            LoggerRequest.getInstance().logInfo(project + " " + error.getField() + " " + error.getDefaultMessage());
            if(error.getField().startsWith("version")) {
				sField =  waiveOffApprovalUsersUpdateResource.getClass().getDeclaredField(error.getField());
                sField.setAccessible(true);
                sField.set(waiveOffApprovalUsersUpdateResource, error.getDefaultMessage());
            }
			else {
				sField =  waiveOffApprovalUsersUpdateResource.getClass().getSuperclass().getDeclaredField(error.getField());
                sField.setAccessible(true);
                sField.set(waiveOffApprovalUsersUpdateResource.getClass().getSuperclass().cast(waiveOffApprovalUsersUpdateResource), error.getDefaultMessage());
			}
        }
        return new ResponseEntity<>(waiveOffApprovalUsersUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
    }
            else
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            SuccessAndErrorDetailsResource successAndErrorDetailsDto = new SuccessAndErrorDetailsResource();
            successAndErrorDetailsDto.setMessages(environment.getProperty("common.internal-server-error"));
            successAndErrorDetailsDto.setDetails(e.getMessage());
            successAndErrorDetailsDto.setCode(ErrorCode.COMN00003);
            TenantHolder.clear();
            LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsDto.getCode() + " " + successAndErrorDetailsDto.getMessages() + " " + successAndErrorDetailsDto.getDetails());
            return new ResponseEntity<>(successAndErrorDetailsDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler({OptimisticLockException.class})
    public ResponseEntity<Object> optimisticLockException(OptimisticLockException ex, WebRequest request) {
        try {
            LoggerRequest.getInstance().logInfo(project + " " + ex.getField() + " " + ex.getMessage());
            UpdateTransEventSubCode typeValidation = new UpdateTransEventSubCode();
            Class validationDetailClass = typeValidation.getClass();

            Field sField = validationDetailClass.getDeclaredField(ex.getField());
            sField.setAccessible(true);
            sField.set(typeValidation, ex.getMessage());
            TenantHolder.clear();

            return new ResponseEntity<>(typeValidation, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
            successAndErrorDetailsResource.setMessages(environment.getProperty(commonInternalServerError));
            successAndErrorDetailsResource.setDetails(e.getMessage());
            successAndErrorDetailsResource.setCode(ErrorCode.COMN00003);
            TenantHolder.clear();
            LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsResource.getCode() + " " + successAndErrorDetailsResource.getMessages() + " " + successAndErrorDetailsResource.getDetails());
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) {
        SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
        LoggerRequest.getInstance().logCommonError(ex.getMessage());
        successAndErrorDetailsDataObject.setMessages(environment.getProperty(commonInternalServerError));
        successAndErrorDetailsDataObject.setDetails(ex.getMessage());
        successAndErrorDetailsDataObject.setCode(ErrorCode.COMN00003);
        TenantHolder.clear();
        LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsDataObject.getCode() + " " + successAndErrorDetailsDataObject.getMessages() + " " + successAndErrorDetailsDataObject.getDetails());
        return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InvalidServiceIdException.class})
    public ResponseEntity<Object> invalidServiceIdException(InvalidServiceIdException ex, WebRequest request) {

        ValidateResource validateResource = new ValidateResource();
        LoggerRequest.getInstance().logInfo(project + " " + ex.getServiceEntity() + " " + ex.getMessage());

        switch (ex.getServiceEntity()) {
            case REPORT_GENERATE: //Added by Sanatha on 16-APR-2020 for CASA GL
                validateResource.setMessage(ex.getMessage());            
            case BANKTRANSACTIONSUB:
            	validateResource.setMessage(ex.getMessage());
                break;
            default:

        }
        return new ResponseEntity<>(validateResource, HttpStatus.UNPROCESSABLE_ENTITY);

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
            successAndErrorDetailsDto.setCode(ErrorCode.COMN00003);
            TenantHolder.clear();
            LoggerRequest.getInstance().logCommonError(project + " " + successAndErrorDetailsDto.getCode() + " " + successAndErrorDetailsDto.getMessages() + " " + successAndErrorDetailsDto.getDetails());
            return new ResponseEntity<>(successAndErrorDetailsDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void creditNoteAddResourceServiceIdException(CreditNoteAddResource creditNoteAddResource, ServiceEntity serviceEntity, String message) {
        switch (serviceEntity) {
            case CODE:
                creditNoteAddResource.setCode(message);
                break;

            default:
        }
    }

}