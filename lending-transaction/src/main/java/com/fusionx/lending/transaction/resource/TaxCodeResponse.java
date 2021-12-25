package com.fusionx.lending.transaction.resource;

import com.fusionx.lending.transaction.enums.ServiceStatus;

/**
 * Tax Code Mapping
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   11-10-2021      		     FXL-1130	Dilki      Created
 * <p>
 * *******************************************************************************************************
 */

public class TaxCodeResponse {

    private Long id;
    private String taxCode;
    private String taxCodeName;
    private String taxCodeStatus;
    private String taxCodeDescription;
    private ServiceStatus serviceStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxCodeName() {
        return taxCodeName;
    }

    public void setTaxCodeName(String taxCodeName) {
        this.taxCodeName = taxCodeName;
    }

    public String getTaxCodeStatus() {
        return taxCodeStatus;
    }

    public void setTaxCodeStatus(String taxCodeStatus) {
        this.taxCodeStatus = taxCodeStatus;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

	public String getTaxCodeDescription() {
		return taxCodeDescription;
	}

	public void setTaxCodeDescription(String taxCodeDescription) {
		this.taxCodeDescription = taxCodeDescription;
	}

}
