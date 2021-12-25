package com.fusionx.lending.transaction.domain;

import com.fusionx.lending.transaction.core.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "alert_log")
@Data
public class AlertLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 155632236;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "log_id")
    private Long logId;

    @Column(name = "event_category")
    private String eventCategory;

    @Column(name = "alert_event_id")
    private Long alertEventId;

    @Column(name = "alert_event")
    private String alertEvent;

    @Column(name = "alert_type_id")
    private Long alertTypeId;

    @Column(name = "alert_type")
    private String alertType;

    @Column(name = "account_id")
    private Long accountId;


    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_schema")
    private String accountSchema;


    @Column(name = "alert_mask_id")
    private Long alertMaskId;

    @Column(name = "alert_mask")
    private String alertMask;

    @Column(name = "contact_type_id")
    private Long contactTypeId;

    @Column(name = "contact_type")
    private String contactType;

    @Column(name = "contact_value")
    private String contactValue;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer")
    private String customer;


    @Column(name = "transaction_amount")
    private String transactionAmount;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "message_header")
    private String messageHeader;

    @Column(name = "message_body")
    private String messageBody;

    @Column(name = "message_footer")
    private String messageFooter;

    @Column(name = "priority_level")
    private Long priorityLevel;

    @Column(name = "service_provider_id")
    private Long serviceProviderId;

    @Column(name = "service_provider")
    private String serviceProvider;

    @Column(name = "comment")
    private String comment;


    @Column(name = "status")
    private String status;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "delivery_user")
    private String deliveryUser;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public Long getAlertEventId() {
        return alertEventId;
    }

    public void setAlertEventId(Long alertEventId) {
        this.alertEventId = alertEventId;
    }

    public String getAlertEvent() {
        return alertEvent;
    }

    public void setAlertEvent(String alertEvent) {
        this.alertEvent = alertEvent;
    }

    public Long getAlertTypeId() {
        return alertTypeId;
    }

    public void setAlertTypeId(Long alertTypeId) {
        this.alertTypeId = alertTypeId;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountSchema() {
        return accountSchema;
    }

    public void setAccountSchema(String accountSchema) {
        this.accountSchema = accountSchema;
    }

    public Long getAlertMaskId() {
        return alertMaskId;
    }

    public void setAlertMaskId(Long alertMaskId) {
        this.alertMaskId = alertMaskId;
    }

    public String getAlertMask() {
        return alertMask;
    }

    public void setAlertMask(String alertMask) {
        this.alertMask = alertMask;
    }

    public Long getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(Long contactTypeId) {
        this.contactTypeId = contactTypeId;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(String messageHeader) {
        this.messageHeader = messageHeader;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageFooter() {
        return messageFooter;
    }

    public void setMessageFooter(String messageFooter) {
        this.messageFooter = messageFooter;
    }

    public Long getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(Long priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public Long getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDeliveryUser() {
        return deliveryUser;
    }

    public void setDeliveryUser(String deliveryUser) {
        this.deliveryUser = deliveryUser;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

}
