package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Document;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.DocumentAddResource;
import com.fusionx.lending.product.resources.DocumentUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Document
 *
 * @author Rohan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        26-10-2021      -               -           Rohan                  Created
 * <p>
 */
@RestController
@RequestMapping(value = "/document")
@CrossOrigin(origins = "*")
public class DocumentController extends MessagePropertyBase {

    private DocumentService documentService;

    @Autowired
    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }


    /**
     * Saves the document
     *
     * @param tenantId            the tenant id
     * @param documentAddResource the add resource of document
     * @return message if record successfully saved.
     * @see DocumentAddResource
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addDocument(@PathVariable(value = "tenantId") String tenantId,
                                              @Valid @RequestBody DocumentAddResource documentAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        Document document = documentService.addDocument(tenantId, documentAddResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(document.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Updates the document
     *
     * @param tenantId               the id of the tenant
     * @param id                     the id of the object
     * @param documentUpdateResource the object which contains data
     * @return the message
     * @see DocumentUpdateResource
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateDocument(@PathVariable(value = "tenantId") String tenantId,
                                                 @PathVariable(value = "id") Long id,
                                                 @Valid @RequestBody DocumentUpdateResource documentUpdateResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }

        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
        Optional<Document> isPresentDocument = documentService.getById(id);
        if (isPresentDocument.isPresent()) {
            Document document = documentService.updateDocument(tenantId, id, documentUpdateResource);
            successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_UPDATED), document.getId().toString());
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
        } else {
            successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Gets all document
     *
     * @param tenantId the id of tenant
     * @return the list of all document
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllDocuments(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<Document> documents = documentService.getAll();
        if (documents.size() > 0) {
            return new ResponseEntity<>(documents, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns the record by id
     *
     * @param tenantId the tenant id
     * @param id       the id
     * @return the document Record.
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getDocumentById(@PathVariable(value = "tenantId") String tenantId,
                                                  @PathVariable(value = "id") Long id) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        Optional<Document> isPresentDocument = documentService.getById(id);
        if (isPresentDocument.isPresent()) {
            return new ResponseEntity<>(isPresentDocument.get(), HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get document by status
     *
     * @param tenantId the id of tenant
     * @param status   the status
     * @return the list of all document if record exists
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getDocumentByStatus(@PathVariable(value = "tenantId") String tenantId,
                                                      @PathVariable(value = "status") String status) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

        if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
            List<Document> isPresentDocuments = documentService.getByStatus(status);
            if (isPresentDocuments.size() > 0) {
                return new ResponseEntity<>(isPresentDocuments, HttpStatus.OK);
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
     * Returns the record by lending account id
     *
     * @param tenantId         the tenant id
     * @param lendingAccountId the lendingAccountId
     * @return the document Record.
     */
    @GetMapping(value = "/{tenantId}/lending-account/{lendingAccountId}")
    public ResponseEntity<Object> getDocumentByLendingAccount(@PathVariable(value = "tenantId") String tenantId,
                                                              @PathVariable(value = "lendingAccountId") Long lendingAccountId) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        Optional<Document> isPresentDocuments = documentService.getByLendingAccount(lendingAccountId);
        if (isPresentDocuments.isPresent()) {
            return new ResponseEntity<>(isPresentDocuments, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }
}
