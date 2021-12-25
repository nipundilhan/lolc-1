package com.fusionx.lending.product.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeeChargeDetailsPending;
import com.fusionx.lending.product.domain.PenalInterest;
import com.fusionx.lending.product.domain.PenalInterestPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AgeEligibilityAddResource;
import com.fusionx.lending.product.resources.PenalInterestAddResource;
import com.fusionx.lending.product.resources.PenalInterestUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.PenalInterestService;

@RestController
@RequestMapping(value = "/penal-interest")
@CrossOrigin(origins = "*")
public class PenalInterestController extends MessagePropertyBase{

	private static final Logger logger = LoggerFactory.getLogger(PenalInterestController.class);

	@Autowired
	private PenalInterestService penalInterestService;

	@Autowired
	private Environment environment;

	private String userNotFound = "common.user-not-found";

	/**
     * Gets all penal interest. list
     *
     * @param tenantId the id of tenant
     * @return the list of penal interest.
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllPenalTemplate(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<PenalInterest> penalInterest = penalInterestService.findAll();
        int size = penalInterest.size();
        if (size > 0) {
            return new ResponseEntity<>(penalInterest, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get penal interest by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return list of penal interest if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getPenalTemplateById(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        Optional<PenalInterest> isPresentPenalInterest = penalInterestService.findById(id);
        if (isPresentPenalInterest.isPresent()) {
            return new ResponseEntity<>(isPresentPenalInterest.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get penal interest by status
     *
     * @param tenantId the id of tenant
     * @param status   the status
     * @return the list of penal interest if record exists, otherwise <code>204 - No Content</code>
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getPenalInterestByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                            @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<PenalInterest> isPresentPenalInterest = penalInterestService.findByStatus(status);
            int size = isPresentPenalInterest.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentPenalInterest, HttpStatus.OK);
            } else {
                responseMessage.setMessages(RECORD_NOT_FOUND);
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
        	responseMessage.setMessages(environment.getProperty(COMMON_STATUS_PATTERN));
			return new ResponseEntity<>(responseMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    
    /**
     * Get penal interest by code
     *
     * @param tenantId the id of tenant
     * @param code   the code
     * @return  core product if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getCoreProductByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		Optional<PenalInterest> isPresentPenalInterest = penalInterestService.getPenalTemplateByCode(code);
		if(isPresentPenalInterest.isPresent()) {
			return new ResponseEntity<>(isPresentPenalInterest.get(), HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	  /**
     * Get penal interest by name
     *
     * @param tenantId the id of tenant
     * @param name   the name
     * @return the list of all core product if record exists, otherwise <code>204 - No Content</code>
     */
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getPenalInterestByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		List<PenalInterest> isPresentPenalInterest = penalInterestService.findByName(name);
		if(!isPresentPenalInterest.isEmpty()) {
			return new ResponseEntity<>(isPresentPenalInterest, HttpStatus.OK);
		}
		else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

    /**
     * Saves the penal interest.
     *
     * @param tenantId                  the id of the tenant
     * @param ageEligibilityAddResource the object to save
     * @return message if record successfully saved.
     * @see AgeEligibilityAddResource
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addPenalInterest(@PathVariable(value = "tenantId") String tenantId,
                                                    @Valid @RequestBody PenalInterestAddResource penalInterestAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        PenalInterest penalInterest = penalInterestService.addPenalInterestTemplate(tenantId, penalInterestAddResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(penalInterest.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Updates the given penal interest.
     *
     * @param tenantId                     the id of the tenant
     * @param id                           the id of the object
     * @param penalInterestUpdateResource the object which contains data
     * @return the message
     * @see PenalInterestUpdateResource
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updatePenalInterest(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "id") Long id,
                                                       @Valid @RequestBody PenalInterestUpdateResource penalInterestUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<PenalInterest> isPresentPenalInterest = penalInterestService.findById(id);
        if (isPresentPenalInterest.isPresent()) {
        	PenalInterest penalInterest = penalInterestService.updatePenalInterestTemplate(tenantId, id, penalInterestUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), penalInterest.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    
    
    /**
	* approve penal interest pending
	* @param @PathVariable{tenantId}
	* @param @PathVariable{pendingId}
	* @return SuccessAndErrorDetailsDto
	*/
	@PutMapping(value = "{tenantId}/approve/{pendingId}")
	public ResponseEntity<Object> approve(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<PenalInterestPending> isPresentPenalInterestPending = penalInterestService.getByPendingId(pendingId);
		if(isPresentPenalInterestPending.isPresent()) {
	
			if(penalInterestService.approveReject(tenantId, pendingId, WorkflowStatus.COMPLETE)) {
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty("common.approved"), pendingId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-approved"));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}

		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * reject penal interest pending
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{pendingId}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "{tenantId}/reject/{pendingId}")
	public ResponseEntity<Object> reject(@PathVariable(value = "tenantId", required = true) String tenantId, @PathVariable(value = "pendingId", required = true) Long pendingId){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<PenalInterestPending> isPresentPenalInterestPending = penalInterestService.getByPendingId(pendingId);
		if(isPresentPenalInterestPending.isPresent()) {
	
			if(penalInterestService.approveReject(tenantId, pendingId, WorkflowStatus.REJECT)) {
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(environment.getProperty("common.rejected"), pendingId.toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
			} else {
				successAndErrorDetailsResource.setMessages(environment.getProperty("common.can-not-rejected"));
				return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
			}

		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
    

//	/**
//	 * get Penal Interest with sub details Info by penalInterestId
//	 * 
//	 * @param @PathVariable{tenantId}
//	 * @param @PathVariable{penalInterestId}
//	 * @return Optional<PenalInterest> penalInterests
//	 */
//	@GetMapping(value = "/details/{tenantId}/{penalInterestId}")
//	public ResponseEntity<Object> getCreditInterestWithSubDetailsById(
//			@PathVariable(value = "tenantId", required = true) String tenantId,
//			@PathVariable(value = "penalInterestId", required = true) Long penalInterestId) {
//		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
//
//		Optional<PenalInterestTemplate> penalInterests = service.findWithSubDetailsById(penalInterestId);
//		if (penalInterests.isPresent()) {
//			return new ResponseEntity<>(penalInterests.get(), HttpStatus.OK);
//		} else {
//			responseMessage.setMessages(environment.getProperty("record-not-found"));
//			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
//		}
//	}
//
//	/**
//	 * get Penal Interest by Id
//	 * 
//	 * @param @PathVariable{tenantId}
//	 * @param @PathVariable{code}
//	 * @return Optional<PenalInterest> penalInterests
//	 */
//	@GetMapping(value = "/{tenantId}/code/{code}")
//	public ResponseEntity<Object> getByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
//			@PathVariable(value = "code", required = true) String code) {
//		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
//
//		Optional<PenalInterestTemplate> penalInterests = service.findByCode(code);
//		if (penalInterests.isPresent()) {
//			return new ResponseEntity<>(penalInterests.get(), HttpStatus.OK);
//		} else {
//			responseMessage.setMessages(environment.getProperty("record-not-found"));
//			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
//		}
//	}

// 	/**
//     * get Penal Interest Info by effective date
//     * @param @PathVariable{tenantId}
//     * @param @PathVariable{effectiveDate}
//     * @return List<PenalInterestResource> penalInterests
//     */
// 	@GetMapping(value = "/{tenantId}/common-details/{effectiveDate}")
// 	public ResponseEntity<Object> getPenalInterestByEffectiveDate(@PathVariable(value = "tenantId", required = true) String tenantId,
// 			                                    @PathVariable(value = "effectiveDate", required = true) String effectiveDate) {
// 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
//
// 		List<PenalInterestResource> penalInterests = service.findPenalInterestByEffectiveDate(effectiveDate);
// 		
// 		if(penalInterests!=null && !penalInterests.isEmpty()) {
// 			return new ResponseEntity<>(penalInterests, HttpStatus.OK);
// 		} 
// 		else {
// 			responseMessage.setMessages(environment.getProperty("record-not-found"));
//			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
// 		}
// 	}
}
