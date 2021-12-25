package com.fusionx.lending.transaction.service;

import com.fusionx.lending.transaction.domain.CreditNoteType;
import com.fusionx.lending.transaction.resource.CreditNoteAddResource;
import com.fusionx.lending.transaction.resource.CreditNoteUpdateResource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * CreditNoteType Service
 * <p>
 * *******************************************************************************************************
 * ###   	Date         	Story Point   	Task No    		Author       	Description
 * -------------------------------------------------------------------------------------------------------
 * 1   	09-AUG-2021      		     					Sanatha      	Initial Development
 * <p>
 * *******************************************************************************************************
 */
public interface CreditNoteTypeService {

    /**
     * Find all CreditNoteType
     *
     * @return - JSON Array of all CreditNoteType objects
     * @author Sanatha
     */
    public List<CreditNoteType> findAll();

    /**
     * Find CreditNoteType by Id
     *
     * @return - JSON Array of Bank CreditNoteType objects
     * @author Sanatha
     */
    public Optional<CreditNoteType> findById(Long id);

    /**
     * Find CreditNoteType by Status
     *
     * @return - JSON Array of CreditNoteType objects
     * @author Sanatha
     */
    public Optional<Collection<CreditNoteType>> findByStatus(String status);

    /**
     * Find CreditNoteType by Code
     *
     * @return - JSON Array of CreditNoteType objects
     * @author Sanatha
     */
    public Optional<CreditNoteType> findByCode(String code);

    /**
     * Find CreditNoteType by Name
     *
     * @return - JSON Array of CreditNoteType objects
     * @author Sanatha
     */
    public Optional<Collection<CreditNoteType>> findByName(String name);

    /**
     * Adds the credit note type.
     *
     * @param tenantId              the tenant id
     * @param creditNoteAddResource the credit note add resource
     * @return the credit note type
     * @author Sanatha
     */
    public CreditNoteType addCreditNoteType(String tenantId, CreditNoteAddResource creditNoteAddResource);

    /**
     * Update the credit note type.
     *
     * @param tenantId                 the tenant id
     * @param creditNoteUpdateResource the credit note add resource
     * @return the credit note type
     * @author Sanatha
     */
    public CreditNoteType updateCreditNoteType(String tenantId, CreditNoteUpdateResource creditNoteUpdateResource, Long id);

}
