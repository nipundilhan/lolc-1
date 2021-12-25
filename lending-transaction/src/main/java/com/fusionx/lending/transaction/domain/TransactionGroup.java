package com.fusionx.lending.transaction.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "transaction_group")
@Data
public class TransactionGroup implements Serializable {

    private static final long serialVersionUID = 67645675L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trn_no_generator")
    @SequenceGenerator(name = "trn_no_generator", sequenceName = "transaction_no_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;

    @Column(name = "tenant_id", length = 20, nullable = false)
    private String tenantId;

    @Column(name = "created_user", length = 255, nullable = false)
    private String createdUser;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
