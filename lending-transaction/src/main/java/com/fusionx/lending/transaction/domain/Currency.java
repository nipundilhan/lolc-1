package com.fusionx.lending.transaction.domain;
/**
 * This model is for save currency info
 *
 * @author Rangana
 * @Date 25-03-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point                                Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   25-03-2019   FX-73 ,FX-74        						  FX-74     Rangana       Created
 * <p>
 * *******************************************************************************************************
 */

import com.fusionx.lending.transaction.enums.CurrencyStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "comn_currency_detail")
@Data
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long currencyId;

    private String currencyCode;

    private String currencyName;

    private String codeNumeric;

    private Integer exponentConversions;

    @Enumerated(value = EnumType.STRING)
    private CurrencyStatus currencyStatus;

    private String currencyCreatedUser;

    private Timestamp currencyCreatedDate;

    private String currencyModifiedUser;

    private Timestamp currencyModifiedDate;

    private String tenantId;

    @Version
    private Long currencyVersion;

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCodeNumeric() {
        return codeNumeric;
    }

    public void setCodeNumeric(String codeNumeric) {
        this.codeNumeric = codeNumeric;
    }

    public Integer getExponentConversions() {
        return exponentConversions;
    }

    public void setExponentConversions(Integer exponentConversions) {
        this.exponentConversions = exponentConversions;
    }

    public CurrencyStatus getCurrencyStatus() {
        return currencyStatus;
    }

    public void setCurrencyStatus(CurrencyStatus currencyStatus) {
        this.currencyStatus = currencyStatus;
    }

    public void setCurrencyStatus(String currencyStatus) {
        this.currencyStatus = CurrencyStatus.valueOf(currencyStatus);
    }

    public String getCurrencyCreatedUser() {
        return currencyCreatedUser;
    }

    public void setCurrencyCreatedUser(String currencyCreatedUser) {
        this.currencyCreatedUser = currencyCreatedUser;
    }

    public Timestamp getCurrencyCreatedDate() {
        return currencyCreatedDate;
    }

    public void setCurrencyCreatedDate(Timestamp currencyCreatedDate) {
        this.currencyCreatedDate = currencyCreatedDate;
    }

    public String getCurrencyModifiedUser() {
        return currencyModifiedUser;
    }

    public void setCurrencyModifiedUser(String currencyModifiedUser) {
        this.currencyModifiedUser = currencyModifiedUser;
    }

    public Timestamp getCurrencyModifiedDate() {
        return currencyModifiedDate;
    }

    public void setCurrencyModifiedDate(Timestamp currencyModifiedDate) {
        this.currencyModifiedDate = currencyModifiedDate;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getCurrencyVersion() {
        return currencyVersion;
    }

    public void setCurrencyVersion(Long currencyVersion) {
        this.currencyVersion = currencyVersion;
    }
}
