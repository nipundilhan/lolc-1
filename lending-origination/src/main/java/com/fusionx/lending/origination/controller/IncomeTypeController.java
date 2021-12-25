package com.fusionx.lending.origination.controller;
/**
 * Income Type Controller
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   16-08-2021   FXL-340        FXL-534      Piyumi       Modified
 * <p>
 * *******************************************************************************************************
 */

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.IncomeType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.resource.IncomeTypeRequest;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.resource.UpdateIncomeTypeRequest;
import com.fusionx.lending.origination.service.IncomeTypeService;


@RestController
@RequestMapping(value = "/income-type")
@CrossOrigin(origins = "*")
public class IncomeTypeController extends MessagePropertyBase {

    @Autowired
    private IncomeTypeService incomeTypeService;


    /**
     * get all Income Type service
     * @param @PathVariable{tenantId}
     * @param @PathVariable{all}
     * @return List<Type>
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getIncomeType(@PathVariable(value = "tenantId", required = true) String tenantId) {

        List<IncomeType> incomeType = incomeTypeService.findAll();
        int size = incomeType.size();
        if (size > 0) {
            return new ResponseEntity<>(incomeType, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get Income Type service by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/{incomeTypeId}")
    public ResponseEntity<Object> getIncomeTypeIdById(@PathVariable(value = "tenantId", required = true) String tenantId,
                                                      @PathVariable(value = "incomeTypeId", required = true) Long incomeTypeId) {

        Optional<IncomeType> isPresentIncomeType = incomeTypeService.findById(incomeTypeId);
        if (isPresentIncomeType.isPresent()) {
            return new ResponseEntity<>(isPresentIncomeType.get(), HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get Income Type service by Code
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getIncomeTypeByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
                                                      @PathVariable(value = "code", required = true) String code) {

        Optional<IncomeType> isPresentIncomeType = incomeTypeService.findByCode(code);
        if (isPresentIncomeType.isPresent()) {
            return new ResponseEntity<>(isPresentIncomeType.get(), HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get Income Type service by Name
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
    @GetMapping(value = "/{tenantId}/name/{name}")
    public ResponseEntity<Object> getIncomeTypeByName(@PathVariable(value = "tenantId", required = true) String tenantId,
                                                      @PathVariable(value = "name", required = true) String name) {

        List<IncomeType> isPresenIncomeType = incomeTypeService.findByName(name);
        if (!isPresenIncomeType.isEmpty()) {
            return new ResponseEntity<>(isPresenIncomeType, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get Income Type service by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getIncomeTypeByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                            @PathVariable(value = "status", required = true) String status) {
 		
 		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
		 	List<IncomeType> isPresentIncomeType = incomeTypeService.findByStatus(status);
		 	int size = isPresentIncomeType.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(isPresentIncomeType, HttpStatus.OK);
		 	} 
		 	else {
		 		responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		 	}
 		}
 		else{
	 		responseMessage.setMessages(environment.getProperty("common.invalid-status"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}


    /**
     * save Income Type service
     * @param @PathVariable{tenantId}
     * @param @RequestBody{IncomeTypeRequest}
     * @return SuccessAndErrorDetails
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addIncomeType(@PathVariable(value = "tenantId", required = true) String tenantId,
                                                @Valid @RequestBody IncomeTypeRequest incomeTypeRequest) {
        IncomeType incomeType = incomeTypeService.addIncomeType(tenantId, incomeTypeRequest);
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_CREATED), incomeType.getId());
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }


    /**
     * update Income Type service
     * @param @PathVariable{tenantId}
     * @param @RequestBody{UpdateIncomeType}
     * @return SuccessAndErrorDetailsDto
     */
    @PutMapping(value = "/{tenantId}/{incomeTypeId}")
    public ResponseEntity<Object> updateIncomeType(@PathVariable(value = "tenantId", required = true) String tenantId,
                                                   @PathVariable(value = "incomeTypeId", required = true) Long incomeTypeId,
                                                   @Valid @RequestBody UpdateIncomeTypeRequest updateIncomeTypeRequest) {
        updateIncomeTypeRequest.setId(incomeTypeId.toString());
        IncomeType incomeType = incomeTypeService.updateIncomeType(tenantId, updateIncomeTypeRequest);
        SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED), incomeType.getId().toString());
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
    }

    /**
     * Get the  income types by Business Type Id.
     *
     * @param tenantId - the tenant id
     * @param businessTypeId - the businessTypeId
     * @return the  income types by businessTypeId
     */
    @GetMapping(value = "/{tenantId}/business-type/{businessTypeId}")
    public ResponseEntity<Object> getIncomeTypeByBusinessTypeId(
            @PathVariable(value = "tenantId", required = true) String tenantId,
            @PathVariable(value = "businessTypeId", required = true) Long businessTypeId) {

        List<IncomeType> incomeTypes = incomeTypeService.findByBusinessType(businessTypeId);

        if (!incomeTypes.isEmpty()) {
            return new ResponseEntity<>(incomeTypes, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

}
