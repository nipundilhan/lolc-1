package com.fusionx.lending.product.resources;

import static org.junit.Assert.assertEquals;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fusionx.lending.product.utill.TestUtils;

/**
 * MasterCurrencyAddResourceTest
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   			Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-07-2021   FXL_July_2021_2         FXL-6      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MasterCurrencyAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private MasterCurrencyAddResource setMasterCurrencyAddResource() {
		MasterCurrencyAddResource masterCurrencyAddResource = new MasterCurrencyAddResource();
		masterCurrencyAddResource.setCurrencyId("1");
		masterCurrencyAddResource.setCurrencyName("Rupees");
		masterCurrencyAddResource.setNumOfDecimalPlaces("2");
		masterCurrencyAddResource.setStatus("ACTIVE");
		return masterCurrencyAddResource;
	}
	
	 /**
     * currencyId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void currencyIdNotNull() {
    	MasterCurrencyAddResource masterCurrencyAddResource = setMasterCurrencyAddResource();
    	masterCurrencyAddResource.setCurrencyId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterCurrencyAddResource, localValidatorFactory));
	}
    
    /**
     * currencyId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void currencyIdPattern() {
    	MasterCurrencyAddResource masterCurrencyAddResource = setMasterCurrencyAddResource();
    	masterCurrencyAddResource.setCurrencyId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(masterCurrencyAddResource, localValidatorFactory));
	}
	
	 /**
     *  currencyName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void currencyNameNotNull() {
    	MasterCurrencyAddResource masterCurrencyAddResource = setMasterCurrencyAddResource();
    	masterCurrencyAddResource.setCurrencyName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterCurrencyAddResource, localValidatorFactory));
	}
    
    /**
     * currencyName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void currencyNameSize() {
    	MasterCurrencyAddResource masterCurrencyAddResource = setMasterCurrencyAddResource();
    	masterCurrencyAddResource.setCurrencyName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(masterCurrencyAddResource, localValidatorFactory));
		
    }
    
    /**
     * numOfDecimalPlaces is required.
     * Expected: {common.not-null}
     */
    @Test
	public void numOfDecimalPlacesNotNull() {
    	MasterCurrencyAddResource masterCurrencyAddResource = setMasterCurrencyAddResource();
    	masterCurrencyAddResource.setNumOfDecimalPlaces(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterCurrencyAddResource, localValidatorFactory));
	}
    
    /**
     * numOfDecimalPlaces should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void numOfDecimalPlacesPattern() {
    	MasterCurrencyAddResource masterCurrencyAddResource = setMasterCurrencyAddResource();
    	masterCurrencyAddResource.setNumOfDecimalPlaces("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(masterCurrencyAddResource, localValidatorFactory));
	}
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	MasterCurrencyAddResource masterCurrencyAddResource = setMasterCurrencyAddResource();
    	masterCurrencyAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterCurrencyAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	MasterCurrencyAddResource masterCurrencyAddResource = setMasterCurrencyAddResource();
    	masterCurrencyAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(masterCurrencyAddResource, localValidatorFactory));
	}

}
