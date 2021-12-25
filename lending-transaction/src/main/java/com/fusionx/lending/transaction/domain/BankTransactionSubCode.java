package com.fusionx.lending.transaction.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.AllowDormant;
import com.fusionx.lending.transaction.enums.BankTransactionSubCodeStatus;
import com.fusionx.lending.transaction.enums.PostingType;
import com.fusionx.lending.transaction.enums.TransactionCategory;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Table(name = "bank_transaction_sub_code")
@Data
public class BankTransactionSubCode extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 9990000002L;

    @Column(name = "tenant_id")
    private String tenantId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "code_id", nullable = true)
    private BankTransactionCode bankTransactionCode;

    @Column(name = "sub_code")
    private String subCode;

    @Column(name = "description")
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private BankTransactionSubCodeStatus status;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "modified_user")
    private String modifiedUser;

    @Column(name = "modified_date")
    private Timestamp modifiedDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "posting_type")
    private PostingType postingType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "allow_dormant")
    private AllowDormant allowDormant;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_category")
    private TransactionCategory transactionCategory;

    @Transient
    private Long codeId;

    @Transient
    private String code;

    @Transient
    private String codeDescription;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public BankTransactionCode getBankTransactionCode() {
        if (bankTransactionCode != null)
            return bankTransactionCode;
        else
            return null;
    }

    public void setBankTransactionCode(BankTransactionCode bankTransactionCode) {
        this.bankTransactionCode = bankTransactionCode;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BankTransactionSubCodeStatus getStatus() {
        return status;
    }

    public void setStatus(BankTransactionSubCodeStatus status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = BankTransactionSubCodeStatus.valueOf(status);
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getCodeId() {
        if (bankTransactionCode != null)
            return bankTransactionCode.getId();
        else
            return null;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public PostingType getPostingType() {
        return postingType;
    }

    public void setPostingType(PostingType postingType) {
        this.postingType = postingType;
    }

    public void setPostingType(String postingType) {
        this.postingType = PostingType.valueOf(postingType);
    }

    public AllowDormant getAllowDormant() {
        return allowDormant;
    }

    public void setAllowDormant(AllowDormant allowDormant) {
        this.allowDormant = allowDormant;
    }

    public void setAllowDormant(String allowDormant) {
        this.allowDormant = AllowDormant.valueOf(allowDormant);
    }

    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(TransactionCategory transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = TransactionCategory.valueOf(transactionCategory);
    }

    public String getCode() {
        if (bankTransactionCode != null)
            return bankTransactionCode.getCode();
        else
            return null;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeDescription() {
        if (bankTransactionCode != null)
            return bankTransactionCode.getDescription();
        else
            return null;
    }

    public void setCodeDescription(String codeDescription) {
        this.codeDescription = codeDescription;
    }

}
