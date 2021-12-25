package com.fusionx.lending.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.TcHeader;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.TcHeaderService;

/**
 * API Controller related to tc-header.
 *
 * @author Achini
 * @version 1.0.0
 * @since 1.0.0
 *        <p>
 *        <br/>
 *        <br/>
 *        <b>Change History : </b> <br/>
 *        <br/>
 *        # Date Story Point Task No Author Description <br/>
 *        .....................................................................................................................<br/>
 *        1 23-12-2021 - - Achini Created
 *        <p>
 */
@RestController
@RequestMapping(value = "/tc-header")
@CrossOrigin(origins = "*")
public class TcHeaderController extends MessagePropertyBase {

	private TcHeaderService tcHeaderService;

	@Autowired
	public void setLeadInfoService(TcHeaderService tcHeaderService) {
		this.tcHeaderService = tcHeaderService;
	}

	/**
	 * Gets all TcHeader.
	 *
	 * @param tenantId - the tenant id
	 * @return Page<TcHeader>
	 */
	@GetMapping(value = "/{tenantId}/all")
	public Page<TcHeader> getAllLeadInfo(@PathVariable(value = "tenantId") String tenantId,
			@PageableDefault(size = 10) Pageable pageable) {
		return tcHeaderService.findAll(pageable);

	}

	/**
	 * Gets the TcHeader Detail by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id       - TcHeader id
	 * @return TcHeader
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getTcHeaderById(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long id) {

		Optional<TcHeader> tcHeader = tcHeaderService.findDetailById(id);
		if (tcHeader.isPresent()) {
			return new ResponseEntity<>(tcHeader, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Gets the TcHeader Detail by lead id.
	 *
	 * @param tenantId - the tenant id
	 * @param Lead     id - LeadInfo id
	 * @return List<TcHeader>
	 */
	@GetMapping(value = "/{tenantId}/lead/{id}")
	public ResponseEntity<Object> getTcHeaderByLeadId(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "id") Long leadId) {

		List<TcHeader> tcHeader = tcHeaderService.findByLeadId(leadId);
		if (tcHeader != null) {
			return new ResponseEntity<>(tcHeader, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Get the Active TcHeader Detail by lead id.
	 *
	 * @param tenantId - the tenant id
	 * @param Lead     id - LeadInfo id
	 * @return List<TcHeader>
	 */
	@GetMapping(value = "/{tenantId}/active/lead/{id}")
	public ResponseEntity<Object> getActiveTcHeaderByLeadId(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "status") String status, @PathVariable(value = "id") Long leadId) {

		TcHeader tcHeader = tcHeaderService.findActiveTcHeaderByLeadId(leadId);
		if (tcHeader != null) {
			return new ResponseEntity<>(tcHeader, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
}
