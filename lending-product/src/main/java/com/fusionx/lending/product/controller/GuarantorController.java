package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Guarantor;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.GuarantorAddResource;
import com.fusionx.lending.product.resources.GuarantorUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.GuarantorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Guarantor.
 *
 * @author Thushan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #          Date            Story Point     Task No       Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        26-10-2021          -               -           Thushan                  Created
 * <p>
 */
@RestController
@RequestMapping(value = "/guarantor")
@CrossOrigin(origins = "*")
public class GuarantorController extends MessagePropertyBase {
    
    private GuarantorService guarantorService;

    @Autowired
    public void setGuarantorService(GuarantorService guarantorService) {
        this.guarantorService = guarantorService;
    }

    /**
     * Get guarantor by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return a guarantor if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getGuarantorById(@PathVariable(value = "tenantId") String tenantId,
                                                   @PathVariable(value = "id") Long id) {
        Optional<Guarantor> isPresentGuarantor = guarantorService.findById(id);
        if (isPresentGuarantor.isPresent()) {
            return new ResponseEntity<>(isPresentGuarantor.get(), HttpStatus.OK);
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get guarantor by lending account id
     *
     * @param tenantId the id of tenant
     * @param lendingAccountId the id of the lending account
     * @return a guarantor if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/lending-account/{lendingAccountId}")
    public ResponseEntity<Object> getGuarantorByLendingAccountId(@PathVariable(value = "tenantId") String tenantId,
                                                                 @PathVariable(value = "lendingAccountId") Long lendingAccountId) {
        Guarantor guarantor = guarantorService.findByLendingAccountId(lendingAccountId);
        if (guarantor != null ) {
            return new ResponseEntity<>(guarantor, HttpStatus.OK);
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get guarantor by status
     *
     * @param tenantId the id of tenant
     * @param status   the status
     * @return the list of all  guarantor status if record exists, otherwise <code>204 - No Content</code>
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getGuarantorByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                       @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<Guarantor> isPresentGuarantor = guarantorService.findByStatus(status);
            int size = isPresentGuarantor.size();
            if (size > 0) {
                return new ResponseEntity<>(isPresentGuarantor, HttpStatus.OK);
            } else {
                responseMessage.setMessages(RECORD_NOT_FOUND);
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            responseMessage.setMessages(environment.getProperty(INVALID_STATUS));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Saves the given brand object
     *
     * @param tenantId          the tenant id
     * @param GuarantorAddResource the object to save
     * @return the message
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addGuarantor(@PathVariable(value = "tenantId") String tenantId,
                                               @Valid @RequestBody GuarantorAddResource GuarantorAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        Guarantor guarantor = guarantorService.addGuarantor(tenantId, GuarantorAddResource);
        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(guarantor.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Updates the given brand record
     * @param tenantId the tenant id
     * @param id the id
     * @param GuarantorUpdateResource object to update
     * @return the message
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateGuarantor(@PathVariable(value = "tenantId") String tenantId,
                                                  @PathVariable(value = "id") Long id,
                                                  @Valid @RequestBody GuarantorUpdateResource GuarantorUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<Guarantor> isPresentGuarantor = guarantorService.findById(id);
        if (isPresentGuarantor.isPresent()) {
            Guarantor guarantor = guarantorService.updateGuarantor(tenantId, id, GuarantorUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED),Long.toString(guarantor.getId()));
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    
}
