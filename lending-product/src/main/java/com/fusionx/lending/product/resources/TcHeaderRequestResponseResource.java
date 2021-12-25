package com.fusionx.lending.product.resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.product.enums.CalculateMethodEnum;
import com.fusionx.lending.product.enums.CommonStatus;

import java.util.List;

/**
 * Tc Header Request response
 *
 * @author Rohan
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   29-10-2021  						    Rohan      Created
 * <p>
 * *******************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TcHeaderRequestResponseResource {
    private String id;
    private CalculateMethodEnum calculationMethod;
    private CommonStatus status;
    private List<TcCommonPaymentScheduleResponse> tcRevolvingPaymentDetails;
    private List<TcCommonPaymentScheduleResponse> tcStructuredPaymentSchedule;
    private List<TcCommonPaymentScheduleResponse> tcAmortizePaymentDetail;
    private String currencyId;
    private String currencyCode;
    private String currencyCodeNumeric;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CalculateMethodEnum getCalculationMethod() {
        return calculationMethod;
    }

    public void setCalculationMethod(CalculateMethodEnum calculationMethod) {
        this.calculationMethod = calculationMethod;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public List<TcCommonPaymentScheduleResponse> getTcRevolvingPaymentDetails() {
        return tcRevolvingPaymentDetails;
    }

    public void setTcRevolvingPaymentDetails(List<TcCommonPaymentScheduleResponse> tcRevolvingPaymentDetails) {
        this.tcRevolvingPaymentDetails = tcRevolvingPaymentDetails;
    }

    public List<TcCommonPaymentScheduleResponse> getTcStructuredPaymentSchedule() {
        return tcStructuredPaymentSchedule;
    }

    public void setTcStructuredPaymentSchedule(List<TcCommonPaymentScheduleResponse> tcStructuredPaymentSchedule) {
        this.tcStructuredPaymentSchedule = tcStructuredPaymentSchedule;
    }

    public List<TcCommonPaymentScheduleResponse> getTcAmortizePaymentDetail() {
        return tcAmortizePaymentDetail;
    }

    public void setTcAmortizePaymentDetail(List<TcCommonPaymentScheduleResponse> tcAmortizePaymentDetail) {
        this.tcAmortizePaymentDetail = tcAmortizePaymentDetail;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCodeNumeric() {
        return currencyCodeNumeric;
    }

    public void setCurrencyCodeNumeric(String currencyCodeNumeric) {
        this.currencyCodeNumeric = currencyCodeNumeric;
    }
}
