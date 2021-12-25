package com.fusionx.lending.transaction.domain;

import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.enums.CoreTransactionMethodCode;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "core_transaction_method_history")
@Data
public class CoreTransactionHistory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 9990000001L;

    @Column(name = "id")
    private Long id;

    @Column(name = "tenant_id")
    private String tenantId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "code")
    private CoreTransactionMethodCode code;

    @Column(name = "name")
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private CommonStatus status;


    @Column(name = "description")
    private String description;

    @Column(name = "core_transaction_id")
    private Long coreTransactionId;


    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "modified_user")
    private String modifiedUser;

    @Column(name = "modified_date")
    private Timestamp modifiedDate;

    @Column(name = "version")
    private Long version;


    @Column(name = "history_created_user")
    private String historyCreatedUser;

    @Column(name = "history_created_date")
    private Timestamp historyCreatedDate;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

   

    public CoreTransactionMethodCode getCode() {
		return code;
	}

	public void setCode(CoreTransactionMethodCode code) {
		this.code = code;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCoreTransactionId() {
        return coreTransactionId;
    }

    public void setCoreTransactionId(Long coreTransactionId) {
        this.coreTransactionId = coreTransactionId;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getHistoryCreatedUser() {
        return historyCreatedUser;
    }

    public void setHistoryCreatedUser(String historyCreatedUser) {
        this.historyCreatedUser = historyCreatedUser;
    }

    public Timestamp getHistoryCreatedDate() {
        return historyCreatedDate;
    }

    public void setHistoryCreatedDate(Timestamp historyCreatedDate) {
        this.historyCreatedDate = historyCreatedDate;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }


}
