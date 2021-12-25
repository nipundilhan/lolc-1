package com.fusionx.lending.product.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.DocumentCheckListDetails;
import com.fusionx.lending.product.repository.DocumentCheckListDetailsRepository;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.DocumentCheckListService;

@RestController
@RequestMapping(value = "/document-checklist-details")
@CrossOrigin(origins = "*")
public class DocumentCheckListDetailsController extends MessagePropertyBase{
	
	@Autowired
	private DocumentCheckListDetailsRepository documentCheckListDetailsRepository;
	@Autowired
	private DocumentCheckListService documentCheckListService;
	
	@Autowired
	public void setDocumentCheckListDetailsRepository(
			DocumentCheckListDetailsRepository documentCheckListDetailsRepository) {
		this.documentCheckListDetailsRepository = documentCheckListDetailsRepository;
	}


	/**
	 * get document check list details by id
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {id}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getDocumentCheckListById(@PathVariable(value = "tenantId", required = true) String tenantId,
													         	  @PathVariable(value = "id", required = true) Long id){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<DocumentCheckListDetails> documentCheckListDetails = documentCheckListService.findById(id);
		if(documentCheckListDetails.isPresent()) {
			return new ResponseEntity<>(documentCheckListDetails.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

}
