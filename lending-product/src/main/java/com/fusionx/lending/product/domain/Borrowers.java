package com.fusionx.lending.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.OwnershipTypeEnum;
import com.fusionx.lending.product.enums.YesNo;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "borrowers")
public class Borrowers extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 65364566L;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ownership_type", length = 70, nullable = false)
    private OwnershipTypeEnum ownershipType;

    @Column(name = "tenant_id", length = 10, nullable = false)
    private String tenantId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private CommonStatus status;

    @Column(name = "created_user", nullable=false)
    private String createdUser;

    @Column(name = "created_date", nullable=false)
    private Timestamp createdDate;

    @Column(name = "modified_user")
    private String modifiedUser;

    @Column(name = "modified_date")
    private Timestamp modifiedDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "lending_account_id", nullable = false)
    private LendingAccountDetail lendingAccountId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

}
