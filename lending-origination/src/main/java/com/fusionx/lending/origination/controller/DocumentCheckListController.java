package com.fusionx.lending.origination.controller;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.DocumentCheckList;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.DocumentCheckListAddResource;
import com.fusionx.lending.origination.resource.DocumentCheckListUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.DocumentCheckListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Document CheckList Controller
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   12-07-2021   							Dilhan        Created
 * <p>
 * *******************************************************************************************************
 */

@RestController
@RequestMapping(value = "/document-checklist")
@CrossOrigin(origins = "*")
public class DocumentCheckListController extends MessagePropertyBase {

    @Autowired
    private DocumentCheckListService documentCheckListService;

    /**
     * get all document check list
     *
     * @param tenantId the tenant id
     * @return List
     **/
    @GetMapping("/{tenantId}/all")
    public ResponseEntity<Object> getAllDocumentCheckList(
            @PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<DocumentCheckList> isPresentDocumentCheckList = documentCheckListService.getAll();
        if (!isPresentDocumentCheckList.isEmpty()) {
            return new ResponseEntity<>(isPresentDocumentCheckList, HttpStatus.OK);
        } else {
            responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get document check list by id
     *
     * @param tenantId the tenant id
     * @param id       the id of the record
     * @return Optional
     **/
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getDocumentCheckListById(@PathVariable(value = "tenantId") String tenantId,
                                                           @PathVariable(value = "id") Long id) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<DocumentCheckList> isPresentDocumentCheckList = documentCheckListService.getById(id);
        if (isPresentDocumentCheckList.isPresent()) {
            return new ResponseEntity<>(isPresentDocumentCheckList.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get list of {@link DocumentCheckList by status}
     *
     * @param tenantId the tenant id
     * @param status   the status should be <code>ACTIVE</code>or <code>INACTIVE</code>
     * @return the list of {@link DocumentCheckList}
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getDocumentCheckListByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                               @PathVariable(value = "status") String status) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        if (status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
            List<DocumentCheckList> isPresentDocumentCheckList = documentCheckListService.getByStatus(status);
            if (!isPresentDocumentCheckList.isEmpty()) {
                return new ResponseEntity<>(isPresentDocumentCheckList, HttpStatus.OK);
            } else {
                responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            throw new ValidateRecordException(environment.getProperty("common-status.pattern"), "message");
        }
    }

//	/**
//	 * get document check list by code
//	 * @param @pathVariable{tenantId}
//	 * @param @pathVariable {code}
//	 * @return Optional
//	 **/
//	@GetMapping(value = "/{tenantId}/code/{code}")
//	public ResponseEntity<Object> getDocumentCheckListByCode(@PathVariable(value = "tenantId") String tenantId,
//													   	            @PathVariable(value = "code") String code){
//		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
//		Optional<DocumentCheckList> isPresentDocumentCheckList = documentCheckListService.getByCode(code);
//		if(isPresentDocumentCheckList.isPresent()) {
//			return new ResponseEntity<>(isPresentDocumentCheckList.get(), HttpStatus.OK);
//		}
//		else {
//			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
//			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
//		}
//	}

//	  /**
//     * Get document check list by name
//     *
//     * @param tenantId the id of tenant
//     * @param name   the name
//     * @return the list of all document check list if record exists, otherwise <code>204 - No Content</code>
//     */
//	@GetMapping(value = "/{tenantId}/name/{name}")
//	public ResponseEntity<Object> getDocumentCheckListByName(@PathVariable(value = "tenantId") String tenantId,
//															        @PathVariable(value = "name") String name){
//		List<DocumentCheckList> isPresentDocumentCheckList = documentCheckListService.findByName(name);
//		if(!isPresentDocumentCheckList.isEmpty()) {
//			return new ResponseEntity<>(isPresentDocumentCheckList, HttpStatus.OK);
//		}
//		else {
//			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
//			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
//			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
//		}
//	}
//	

    /**
     * Saves the given document check list
     *
     * @param tenantId                     the tenant id
     * @param documentCheckListAddResource the resource file
     * @return the {@link SuccessAndErrorDetailsResource object}
     */
    @PostMapping("/{tenantId}")
    public ResponseEntity<Object> addDocumentCheckList(
            @PathVariable(value = "tenantId") String tenantId,
            @Valid @RequestBody DocumentCheckListAddResource documentCheckListAddResource) {
        DocumentCheckList documentCheckList = documentCheckListService.addDocumentCheckList(tenantId,
                documentCheckListAddResource);
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(
                getEnvironment().getProperty(RECORD_CREATED), Long.toString(documentCheckList.getId()));
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    /**
     * Update the given document check list record
     *
     * @param tenantId                        the tenant id
     * @param id                              the id of updating record
     * @param documentCheckListUpdateResource the resource file which contains update data
     * @return the successAndErrorDetailsResource
     */
    @PutMapping(value = "{tenantId}/{id}")
    public ResponseEntity<Object> updateDocumentCheckList(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody DocumentCheckListUpdateResource documentCheckListUpdateResource) {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Optional<DocumentCheckList> isPresentDocumentCheckList = documentCheckListService.getById(id);
        if (isPresentDocumentCheckList.isPresent()) {
            DocumentCheckList updateDocumentCheckList = documentCheckListService.updateDocumentCheckList(tenantId, id,
                    documentCheckListUpdateResource);
            successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
                    getEnvironment().getProperty(RECORD_UPDATED), updateDocumentCheckList.getId().toString());
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
        } else {
            successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}