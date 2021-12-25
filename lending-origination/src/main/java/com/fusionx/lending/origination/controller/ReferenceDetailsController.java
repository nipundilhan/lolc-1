package com.fusionx.lending.origination.controller;

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
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ReferenceDetails;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.ReferenceDetailsAddResource;
import com.fusionx.lending.origination.resource.ReferenceDetailsUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ReferenceDetailsService;

@RestController
@RequestMapping(value = "/reference-details")
@CrossOrigin(origins = "*")
public class ReferenceDetailsController extends MessagePropertyBase {

	@Autowired
	public ReferenceDetailsService referenceDetailsService;

	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllReferenceDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ReferenceDetails> referenceDetails = referenceDetailsService.findAll();
		int size = referenceDetails.size();
		if (size > 0) {
			return new ResponseEntity<>(referenceDetails, HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getReferenceDetailsById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<ReferenceDetails> isPresentReferenceDetails = referenceDetailsService.findById(id);
		if (isPresentReferenceDetails.isPresent()) {
			return new ResponseEntity<>(isPresentReferenceDetails.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getReferenceDetailsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<ReferenceDetails> isPresentReferenceDetails = referenceDetailsService.findByStatus(status);
			int size = isPresentReferenceDetails.size();
			if (size > 0) {
				return new ResponseEntity<>(isPresentReferenceDetails, HttpStatus.OK);
			} else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(environment.getProperty(COMMON_ERROR));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addReferenceDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody ReferenceDetailsAddResource referenceDetailsAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(NOT_FOUND));
		}

		ReferenceDetails referenceDetails = referenceDetailsService.addReferenceDetails(tenantId, referenceDetailsAddResource);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_CREATED), Long.toString(referenceDetails.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateReferenceDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody ReferenceDetailsUpdateResource referenceDetailsUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(NOT_FOUND));
		}

		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource();
		Optional<ReferenceDetails> isPresentReferenceDetails = referenceDetailsService.findById(id);
		if (isPresentReferenceDetails.isPresent()) {
			ReferenceDetails referenceDetails = referenceDetailsService.updateReferenceDetails(tenantId, id,
					referenceDetailsUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_UPDATED),
					referenceDetails.getId().toString());
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
		} else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

//	@GetMapping(value = "/{tenantId}/lead-id/{id}")
//	public ResponseEntity<Object> getByLeadId(@PathVariable(value = "tenantId", required = true) String tenantId,
//			@PathVariable(value = "id", required = true) Long id) {
//
//		List<ReferenceDetails> referenceDetails = referenceDetailsService.getByLeadId(id);
//		if (!referenceDetails.isEmpty()) {
//			return new ResponseEntity<>(referenceDetails, HttpStatus.OK);
//		} else {
//			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
//			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
//			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
//		}
//	}
}
