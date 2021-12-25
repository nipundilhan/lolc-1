package com.fusionx.lending.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.YesNo;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Lending Account Detail
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-10-2021      		                Thushan      Created
 *
 ********************************************************************************************************
 */
@Data
@Entity
@Table(name = "guarantor")
public class Guarantor extends BaseEntity implements Serializable {

    @Column(name = "sequence")
    private Long sequence;

    @Column(name = "guarantee_percentage", columnDefinition = "Decimal(4,2)")
    private BigDecimal guaranteePercentage;

    @Column(name = "value", columnDefinition = "Decimal(38,2)")
    private BigDecimal value;

    @Column(name = "bond_number", length = 20)
    private String bondNumber;

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

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "lending_account_id", nullable=false)
    private LendingAccountDetail lendingAccountDetail;
}
