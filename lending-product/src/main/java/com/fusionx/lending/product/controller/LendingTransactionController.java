package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.Constants;
import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.LendingTransaction;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.BorrowersAddResource;
import com.fusionx.lending.product.resources.LendingTransactionAddResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.LendingTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Lending Transaction.
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
 * 1        28-10-2021          -               -           Thushan                  Created
 * <p>
 */
@RestController
@RequestMapping(value = "/lending-transaction-controller")
@CrossOrigin(origins = "*")
public class LendingTransactionController extends MessagePropertyBase {

    private LendingTransactionService lendingTransactionService;

    @Autowired
    public void setLendingTransactionService(LendingTransactionService lendingTransactionService) {
        this.lendingTransactionService = lendingTransactionService;
    }

    /**
     * Get lending transaction by id
     *
     * @param tenantId the id of tenant
     * @param id       the id of the record
     * @return a borrowers if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getLendingTransactionById(@PathVariable(value = "tenantId") String tenantId,
                                                   @PathVariable(value = "id") Long id) {
        Optional<LendingTransaction> isPresentTransaction = lendingTransactionService.findById(id);
        if (isPresentTransaction.isPresent()) {
            return new ResponseEntity<>(isPresentTransaction.get(), HttpStatus.OK);
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get lending transaction by lending account id
     *
     * @param tenantId the id of tenant
     * @param lendingAccountId the id of the lending account
     * @return a borrowers if record exists, otherwise <code>204</code>
     */
    @GetMapping(value = "/{tenantId}/lending-account/{lendingAccountId}")
    public ResponseEntity<Object> getLendingTransactionByLendingAccountId(@PathVariable(value = "tenantId") String tenantId,
                                                                 @PathVariable(value = "lendingAccountId") Long lendingAccountId) {
        List<LendingTransaction> lendingTransactionList = lendingTransactionService.findByLendingAccountId(lendingAccountId);
        if (!lendingTransactionList.isEmpty()) {
            return new ResponseEntity<>(lendingTransactionList, HttpStatus.OK);
        } else {
            SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Saves the given lending transaction object
     *
     * @param tenantId          the tenant id
     * @param lendingTransactionAddResource the object to save
     * @return the message
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> addLendingTransaction(@PathVariable(value = "tenantId") String tenantId,
                                               @Valid @RequestBody LendingTransactionAddResource lendingTransactionAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        LendingTransaction lendingTransaction = lendingTransactionService.addLendingTransaction(tenantId, lendingTransactionAddResource);
        SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(lendingTransaction.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }

    /**
     * Search lending transaction
     *
     * @param tenantId the tenant id
     * @param searchQ the search query
     * @param pageable the pageable
     * @return the pageable of lending transaction
     */
    @GetMapping(value = "/{tenantId}/search")
    public Page<LendingTransaction> searchLendingTransactions(@PathVariable(value = "tenantId") String tenantId,
                                                              @RequestParam(value = "searchq", required = false) String searchQ,
                                                              @RequestParam(value = "searchParam", required = false) String searchParam,
                                                              @PageableDefault(size = Constants.DEFAULT_PAGE_SIZE) Pageable pageable) {
        if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH)
            throw new PageableLengthException(environment.getProperty(PAGEABLE_LENGTH));
        return lendingTransactionService.searchLendingTransactions(searchQ, searchParam, pageable);
    }
}
