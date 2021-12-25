package com.fusionx.lending.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for common modules.
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
 * 1        08-10-2021      -               -           Rohan                     Created
 * <p>
 */
@Configuration
public class CommonModuleProperties {

    @Value("${service.url.lending-product}")
    private String lendingProduct;

    @Value("${service.url.comn-person}")
    private String comnPerson;

    @Value("${service.url.comn-customer}")
    private String comnCustomer;

    @Value("${service.url.comn-common}")
    private String comnCommon;

    @Value("${service.url.lending-origination}")
    private String lendingOrigination;

    @Value("${service.url.comn-supplies-entities}")
    private String comnSuppliesEntities;

    @Value("${service.url.col-collateral}")
    private String colCollateral;

    @Value("${service.url.casa-account}")
    private String caseAccount;
    
    

    public String getLendingProduct() {
        return lendingProduct;
    }

    public void setLendingProduct(String lendingProduct) {
        this.lendingProduct = lendingProduct;
    }

    public String getComnPerson() {
        return comnPerson;
    }

    public void setComnPerson(String comnPerson) {
        this.comnPerson = comnPerson;
    }

    public String getComnCustomer() {
        return comnCustomer;
    }

    public void setComnCustomer(String comnCustomer) {
        this.comnCustomer = comnCustomer;
    }

    public String getComnCommon() {
        return comnCommon;
    }

    public void setComnCommon(String comnCommon) {
        this.comnCommon = comnCommon;
    }

    public String getLendingOrigination() {
        return lendingOrigination;
    }

    public void setLendingOrigination(String lendingOrigination) {
        this.lendingOrigination = lendingOrigination;
    }

    public String getComnSuppliesEntities() {
        return comnSuppliesEntities;
    }

    public void setComnSuppliesEntities(String comnSuppliesEntities) {
        this.comnSuppliesEntities = comnSuppliesEntities;
    }

    public String getColCollateral() {
        return colCollateral;
    }

    public void setColCollateral(String colCollateral) {
        this.colCollateral = colCollateral;
    }

    public String getCaseAccount() {
        return caseAccount;
    }

    public void setCaseAccount(String caseAccount) {
        this.caseAccount = caseAccount;
    }
    
   
}
