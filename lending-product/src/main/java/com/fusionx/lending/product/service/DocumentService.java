package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.Document;
import com.fusionx.lending.product.resources.DocumentAddResource;
import com.fusionx.lending.product.resources.DocumentUpdateResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to document.
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
 * 1        26-10-2021      -               -           Rohan       Created
 * <p>
 */
@Service
public interface DocumentService {

    /**
     * saves the document
     *
     * @param tenantId            the tenant id
     * @param documentAddResource the add resource of document
     * @return the document
     */
    Document addDocument(String tenantId, DocumentAddResource documentAddResource);

    /**
     * Gets the document by id
     *
     * @param id the id of the record
     * @return the document
     */
    Optional<Document> getById(Long id);

    /**
     * update the document
     *
     * @param tenantId               the tenant id
     * @param id                     the id of the record
     * @param documentUpdateResource the update resource of document
     * @return the document
     */
    Document updateDocument(String tenantId, Long id, DocumentUpdateResource documentUpdateResource);

    /**
     * Returns the documents by status
     *
     * @param status the status <code>ACTIVE | INACTIVE </code>
     * @return the list of document
     */
    List<Document> getByStatus(String status);

    /**
     * Returns the documents by lending account
     *
     * @param lendingAccountId the lending account id
     * @return the list of document
     */
    Optional<Document> getByLendingAccount(Long lendingAccountId);

    /**
     * Returns all documents
     *
     * @return the list of document
     */
    List<Document> getAll();
}
