package com.fusionx.lending.transaction.controller;

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

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.domain.AllocationOrder;
import com.fusionx.lending.transaction.resource.AllocationOrderResource;
import com.fusionx.lending.transaction.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.transaction.service.AllocationOrderService;

@RestController
@RequestMapping(value = "/allocation-order")
@CrossOrigin(origins = "*")
public class AllocationOrderController extends MessagePropertyBase {
    @Autowired
    AllocationOrderService allocationOrderService;

    /**
     * Get All Allocation Orders
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{all}
     * @return List<AllocationOrder>
     */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllocationOrders(@PathVariable(value = "tenantId") String tenantId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<AllocationOrder> allocationOrder = allocationOrderService.findAll();

        if (!allocationOrder.isEmpty()) {
            return new ResponseEntity<>(allocationOrder, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get All Allocation Orders By ID
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Optional<AllocationOrder>
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "tenantId") String tenantId,
                                          @PathVariable(value = "id") String id) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<AllocationOrder> allocationOrder = allocationOrderService.findById(Long.parseLong(id));
        if (allocationOrder.isPresent()) {
            return new ResponseEntity<>(allocationOrder, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get All Allocation Orders By Code
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{code}
     * @return Optional<AllocationOrder>
     */
    @GetMapping(value = "/{tenantId}/code/{code}")
    public ResponseEntity<Object> getByCode(@PathVariable(value = "tenantId") String tenantId,
                                            @PathVariable(value = "code") String code) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<AllocationOrder> allocationOrder = allocationOrderService.findByReferenceCode(code);
        if (allocationOrder.isPresent()) {
            return new ResponseEntity<>(allocationOrder, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get All Allocation Orders By Status
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return List<AllocationOrder>
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getByStatus(@PathVariable(value = "tenantId") String tenantId,
                                              @PathVariable(value = "status") String status) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<AllocationOrder> allocationOrder = allocationOrderService.findByStatus(status);
        if (!allocationOrder.isEmpty()) {
            return new ResponseEntity<>(allocationOrder, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get All Allocation Orders By Account Status
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{accountStatus}
     * @return List<AllocationOrder>
     */
    /*@GetMapping(value = "/{tenantId}/account-status/{accountStatus}")
    public ResponseEntity<Object> getByAccountStatus(@PathVariable(value = "tenantId") String tenantId,
                                                     @PathVariable(value = "accountStatus") String accountStatus) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        if (accountStatus.equalsIgnoreCase(AccountStatusEnum.ACTIVE.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.APPROVED_FINAL_WITHDRAWAL.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.BLOCK.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.CANCEL.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.CLOSE_CANCELLED.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.CLOSE_PENDING.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.CLOSED.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.CREATE_BLOCK.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.CREATE_DE_ACTIVATED.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.CREATE_RE_ACTIVATED.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.CREATE_STOP_PAYMENT.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.CREATE_STOP_PAYMENT_REVERSAL.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.CREATED.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.DE_ACTIVATED.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.DORMANT.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.RE_ACTIVATED.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.REJECTED.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.STOP_PAYMENT.toString()) ||
                accountStatus.equalsIgnoreCase(AccountStatusEnum.STOP_PAYMENT_REVERSAL.toString())) {


            List<AllocationOrder> allocationOrder = allocationOrderService.findByAccountStatus(accountStatus);
            if (!allocationOrder.isEmpty()) {
                return new ResponseEntity<>(allocationOrder, HttpStatus.OK);
            } else {
                responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }*/

    /**
     * Get All Allocation Orders By Bank Transaction Sub Code
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{subCode}
     * @return List<AllocationOrder>
     */
    @GetMapping(value = "/{tenantId}/bank-sub-code/{subCode}")
    public ResponseEntity<Object> getByBankSubCode(@PathVariable(value = "tenantId") String tenantId,
                                                   @PathVariable(value = "subCode") String subCode) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<AllocationOrder> allocationOrder = allocationOrderService.findByBankTransactionSubCode(subCode);
        if (!allocationOrder.isEmpty()) {
            return new ResponseEntity<>(allocationOrder, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }
    
    /**
     * Get All Allocation Orders By Allocation template id
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{allocationTemplateId}
     * @return List<AllocationOrder>
     */
    @GetMapping(value = "/{tenantId}/allocation-template-id/{allocationTemplateId}")
    public ResponseEntity<Object> getByAllocationTemplateId(@PathVariable(value = "tenantId") String tenantId,
                                                   @PathVariable(value = "allocationTemplateId") Long allocationTemplateId) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<AllocationOrder> allocationOrder = allocationOrderService.findByAllocationTemplateId(allocationTemplateId);
        if (!allocationOrder.isEmpty()) {
            return new ResponseEntity<>(allocationOrder, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get All Allocation Orders By Bank Transaction Code
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{bankCode}
     * @return List<AllocationOrder>
     */
    @GetMapping(value = "/{tenantId}/bank-code/{bankCode}")
    public ResponseEntity<Object> getByBankCode(@PathVariable(value = "tenantId") String tenantId,
                                                @PathVariable(value = "bankCode") String bankCode) {
        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        List<AllocationOrder> allocationOrder = allocationOrderService.findByBankTransactionCode(bankCode);
        if (!allocationOrder.isEmpty()) {
            return new ResponseEntity<>(allocationOrder, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Save Allocation Order
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{AllocationOrderResource}
     * @return allocationOrder.getReferenceCode()
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> saveAllocationOrder(@PathVariable(value = "tenantId") String tenantId,
                                                      @Valid @RequestBody AllocationOrderResource allocationOrderResource) {

        AllocationOrder allocationOrder = allocationOrderService.addAllocationOrder(tenantId, allocationOrderResource);
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_CREATED));
        successAndErrorDetailsResource.setValue(allocationOrder.getReferenceCode().toString());
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.CREATED);
    }

    /**
     * Update Allocation Order
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{AllocationOrderResource}
     * @return allocationOrder.getReferenceCode()
     */
    @PutMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> updateAllocationOrder(@PathVariable(value = "tenantId") String tenantId,
                                                        @PathVariable(value = "id") Long id,
                                                        @Valid @RequestBody AllocationOrderResource allocationOrderResource) {


        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();

        AllocationOrder recoveryNotifications = allocationOrderService.updateAllocationOrder(tenantId, allocationOrderResource, id);

        successAndErrorDetailsResource.setMessages(environment.getProperty(RECORD_UPDATED));
        successAndErrorDetailsResource.setValue(recoveryNotifications.getReferenceCode().toString());
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);

    }
}
