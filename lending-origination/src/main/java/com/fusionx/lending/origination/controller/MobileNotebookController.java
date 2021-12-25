package com.fusionx.lending.origination.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.MobileNotebook;
import com.fusionx.lending.origination.enums.MobileNotebookStatusEnum;
import com.fusionx.lending.origination.exception.PageableLengthException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.AddToLeadResource;
import com.fusionx.lending.origination.resource.MobileNotebookAddResource;
import com.fusionx.lending.origination.resource.MobileNotebookDeleteResource;
import com.fusionx.lending.origination.resource.MobileNotebookReminderResponseResource;
import com.fusionx.lending.origination.resource.MobileNotebookUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.MobileNotebookService;

/**
 * Mobile Notebook Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-06-2021   		         FX-6506    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping("/mobile-notebook")
@CrossOrigin("*")
public class MobileNotebookController extends MessagePropertyBase{
	
	@Autowired
	private MobileNotebookService mobileNotebookService;
	
	/**
	 * @author Senitha
	 * 
	 * get all Mobile Notebook
	 * @param @PathVariable{tenantId}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/all")
	public Page<MobileNotebook> getAllMobileNotebook(@PathVariable(value = "tenantId", required = true) String tenantId,
    	   											   @PageableDefault(size = 10) Pageable pageable){
		
		if(pageable.getPageSize()>Constants.MAX_PAGEABLE_LENGTH) 
			throw new PageableLengthException(environment.getProperty(PAGEABLE_LENGTH));
		return mobileNotebookService.getAll(tenantId,pageable);
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Mobile Notebook by ID
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {id}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getMobileNotebookById(@PathVariable(value = "tenantId", required = true) String tenantId,
														@PathVariable(value = "id", required = true) Long id){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<MobileNotebook> isPresentMobileNotebook = mobileNotebookService.getById(tenantId, id);
		if(isPresentMobileNotebook.isPresent()) {
			return new ResponseEntity<>(isPresentMobileNotebook.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Mobile Notebook Customer Name
	 * @param @PathVariable{tenantId}
	 * @param @pathVariable {customerName}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/customer-name/{customerName}")
	public ResponseEntity<Object> searchMobileNotebookByCustomerName(@PathVariable(value = "tenantId", required = true) String tenantId,
			                                                         @PathVariable(value = "customerName", required = true) String customerName){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<MobileNotebook>isPresentMobileNotebook = mobileNotebookService.searchByCustomerName(tenantId, customerName);
		if(!isPresentMobileNotebook.isEmpty()) {
			return new ResponseEntity<>((Collection<MobileNotebook>)isPresentMobileNotebook,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Mobile Notebook Customer Name
	 * @param @PathVariable{tenantId}
	 * @param @pathVariable {nicNo}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/nic-no/{nicNo}")
	public ResponseEntity<Object> searchMobileNotebookByNicNo(@PathVariable(value = "tenantId", required = true) String tenantId,
			                                                  @PathVariable(value = "nicNo", required = true) String nicNo){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<MobileNotebook>isPresentMobileNotebook = mobileNotebookService.searchByNicNo(tenantId, nicNo);
		if(!isPresentMobileNotebook.isEmpty()) {
			return new ResponseEntity<>((Collection<MobileNotebook>)isPresentMobileNotebook,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Mobile Notebook  by status
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {status}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getMobileNotebookByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
													        @PathVariable(value = "status", required = true) String status){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(MobileNotebookStatusEnum.CREATED.toString()) || status.equals(MobileNotebookStatusEnum.CANCELED.toString())) {
			List<MobileNotebook> isPresentMobileNotebook = mobileNotebookService.getByStatus(tenantId, status);
			if(!isPresentMobileNotebook.isEmpty()) {
				return new ResponseEntity<>(isPresentMobileNotebook, HttpStatus.OK);
			}
			else {
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			throw new ValidateRecordException(environment.getProperty("mobileNotebookStatus-pattern"), "status");
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Mobile Notebook  by on boarding status
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {status}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/onboarding-status/{onboardingStatus}")
	public ResponseEntity<Object> getMobileNotebookByOnboardingStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
													                  @PathVariable(value = "onboardingStatus", required = true) String onboardingStatus){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(onboardingStatus.equals(MobileNotebookStatusEnum.PENDING.toString()) || onboardingStatus.equals(MobileNotebookStatusEnum.COMPLETED.toString())) {
			List<MobileNotebook> isPresentMobileNotebook = mobileNotebookService.getByOnboardingStatus(tenantId, onboardingStatus);
			if(!isPresentMobileNotebook.isEmpty()) {
				return new ResponseEntity<>(isPresentMobileNotebook, HttpStatus.OK);
			}
			else {
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			throw new ValidateRecordException(environment.getProperty("onboardingStatus.pattern"), "onboardingStatus");
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * Generate Reminder
	 * @param @PathVariable{tenantId}
	 * @param @pathVariable {customerName}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/generate-reminder")
	public ResponseEntity<Object> generateReminder(@PathVariable(value = "tenantId", required = true) String tenantId){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<MobileNotebookReminderResponseResource> isPresentMobileNotebook = mobileNotebookService.generateReminder();
		if(!isPresentMobileNotebook.isEmpty()) {
			return new ResponseEntity<>((Collection<MobileNotebookReminderResponseResource>)isPresentMobileNotebook,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
     * save Mobile Notebook
     * @param @PathVariable{tenantId}
     * @param @RequestBody{MobileNotebookAddResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addMobileNotebook(@PathVariable(value = "tenantId", required = true) String tenantId,
   									         		 @Valid @RequestBody MobileNotebookAddResource mobileNotebookAddResource){
		
		MobileNotebook mobileNotebook = mobileNotebookService.addMobileNotebook(tenantId, mobileNotebookAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(mobileNotebook.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
		
	}
	
	/**
	 * @author Senitha
	 * 
     * update Mobile Notebook
     * @param @PathVariable{tenantId}
     * @param @RequestBody{MobileNotebookUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateMobileNotebook(@PathVariable(value = "tenantId", required = true) String tenantId, 
								                        @PathVariable(value = "id", required = true) Long id, 
								                        @Valid @RequestBody MobileNotebookUpdateResource mobileNotebookUpdateResource){
		
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<MobileNotebook>isPresentMobileNotebook = mobileNotebookService.getById(tenantId, id);
		
		if(isPresentMobileNotebook.isPresent()) {
			mobileNotebookUpdateResource.setId(id.toString());
			MobileNotebook mobileNotebook = mobileNotebookService.updateMobileNotebook(tenantId, mobileNotebookUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), mobileNotebook.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
	}	
	
	/**
	 * @author Senitha
	 * 
     * delete Mobile Notebook
     * @param @PathVariable{tenantId}
     * @param @RequestBody{MobileNotebookDeleteResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "{tenantId}/delete")
	public ResponseEntity<Object> deleteMobileNotebook(@PathVariable(value = "tenantId", required = true) String tenantId, 
								                       @Valid @RequestBody MobileNotebookDeleteResource mobileNotebookDeleteResource){
		
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<MobileNotebook>isPresentMobileNotebook = mobileNotebookService.getById(tenantId, Long.parseLong(mobileNotebookDeleteResource.getId()));
		
		if(isPresentMobileNotebook.isPresent()) {
			mobileNotebookDeleteResource.setId(mobileNotebookDeleteResource.getId());
			MobileNotebook mobileNotebook = mobileNotebookService.deleteMobileNotebook(tenantId, mobileNotebookDeleteResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), mobileNotebook.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
	}	
	
	/**
	 * @author Senitha
	 * 
     * Add To Lead
     * @param @PathVariable{tenantId}
     * @param @RequestBody{AddToLeadResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "{tenantId}/add-to-lead/{id}")
	public ResponseEntity<Object> addToLead(@PathVariable(value = "tenantId", required = true) String tenantId, 
					                        @PathVariable(value = "id", required = true) Long id, 
					                        @Valid @RequestBody AddToLeadResource addToLeadResource){
		
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<MobileNotebook>isPresentMobileNotebook = mobileNotebookService.getById(tenantId, id);
		
		if(isPresentMobileNotebook.isPresent()) {
			addToLeadResource.setId(id.toString());
			MobileNotebook mobileNotebook = mobileNotebookService.addToLead(tenantId, addToLeadResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), mobileNotebook.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
	}	

}
