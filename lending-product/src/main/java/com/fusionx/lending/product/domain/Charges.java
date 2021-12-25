package com.fusionx.lending.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.ChargeCategoryEnum;
import com.fusionx.lending.product.enums.YesNo;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


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
@Table(name = "charges")
public class Charges extends BaseEntity  implements Serializable {

    @Column(name = "charge_Type_Id")
    private Long chargeTypeId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "charge_category", length = 20)
    private ChargeCategoryEnum chargeCategory;

    @Column(name = "period_number")
    private Long periodNumber;

    @Column(name = "charge_amount", columnDefinition = "Decimal(38,2)")
    private BigDecimal chargeAmount;

    @Column(name = "frequency_code", length = 20)
    private String frequencyCode;

    @Column(name = "currency_code", length = 20)
    private String currencyCode;

    @Column(name = "currency_code_numeric", length = 3)
    private String currencyCodeNumeric;

    @Column(name = "tenant_id", length = 10, nullable = false)
    private String tenantId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private YesNo status;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdUser", column = @Column(name = "created_user")),
            @AttributeOverride(name = "createdDate", column = @Column(name = "created_date")),
            @AttributeOverride(name = "modifiedUser", column = @Column(name = "modified_user")),
            @AttributeOverride(name = "modifiedDate", column = @Column(name = "modified_date"))
    })
    private AuditData auditData;

    @Column(name = "application_frequency_code", nullable = false)
    private Long applicationFrequencyCode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "lending_account_detail", nullable=false)
    private LendingAccountDetail lendingAccountDetail;

    @Column(name = "frequency_type_id", nullable = false)
    private Long frequencyTypeId;

    @Column(name = "currency_id", nullable = false)
    private Long currencyId;



}
