package com.fusionx.lending.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for Generate contract number.
 *
 * @author Rohan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        20-10-2021      -               -           Rohan                     Created
 * <p>
 */
@Configuration
public class ContractNumberFormatProperties {

    @Value("${format.contract-number}")
    private String[] contractNumberFormatArray;

    public String[] getContractNumberFormatArray() {
        return contractNumberFormatArray;
    }

    public void setContractNumberFormatArray(String[] contractNumberFormatArray) {
        this.contractNumberFormatArray = contractNumberFormatArray;
    }
}
