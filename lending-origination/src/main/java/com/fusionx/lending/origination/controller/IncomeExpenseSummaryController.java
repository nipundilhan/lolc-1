package com.fusionx.lending.origination.controller;
/**
 * Income Expense Summary Controller
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No       Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   22-09-2021   FXL-115  	 FXL-786       Piyumi     Created
 * <p>
 * *******************************************************************************************************
 */


import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.resource.IncomeExpenseSummaryResponse;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.IncomeExpenseSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value = "income-expense-summary")
@CrossOrigin(origins = "*")
public class IncomeExpenseSummaryController extends MessagePropertyBase {

    @Autowired
    private IncomeExpenseSummaryService incomeExpenseSummaryService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LeadInfoRepository leadInfoRepository;

    /**
     * Get the IncomeExpenseSummary by customerId.
     *
     * @param tenantId   - the tenant id
     * @param customerId - the customerId
     * @return the IncomeExpenseSummaryResponse by customerId
     */
    @GetMapping(value = "/{tenantId}/customer/{customerId}")
    public ResponseEntity<Object> getIncomeExpenseSummaryByCustomerId(@PathVariable(value = "tenantId") String tenantId,
                                                                      @PathVariable(value = "customerId") Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            IncomeExpenseSummaryResponse incomeExpenseSummaryResponse = incomeExpenseSummaryService.findByCustomerId(customerId);
            return new ResponseEntity<>(incomeExpenseSummaryResponse, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }


    /**
     * Get the IncomeExpenseSummary by leadId.
     *
     * @param tenantId - the tenant id
     * @param leadId   - the leadId
     * @return the IncomeExpenseSummaryResponse by leadId
     */
    @GetMapping(value = "/{tenantId}/lead/{leadId}")
    public ResponseEntity<Object> getIncomeExpenseSummaryByLeadId(@PathVariable(value = "tenantId") String tenantId,
                                                                  @PathVariable(value = "leadId") Long leadId) {
        Optional<LeadInfo> lead = leadInfoRepository.findById(leadId);
        if (lead.isPresent()) {
            IncomeExpenseSummaryResponse incomeExpenseSummaryResponse = incomeExpenseSummaryService.findByLeadId(leadId);
            return new ResponseEntity<>(incomeExpenseSummaryResponse, HttpStatus.OK);

        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }


}
