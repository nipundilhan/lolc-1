package com.fusionx.lending.origination.resource;

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

import com.fusionx.lending.origination.utill.TestUtils;

/**
 * 	Income Source Details Update Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   31-08-2021   FXL-115  	 FXL-656       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IncomeSourceDetailsUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private IncomeSourceDetailsUpdateResource setUpdateIncomeSourceDetailsUpdateResource() {
		IncomeSourceDetailsUpdateResource incomeSourceDetailsUpdateResource = new IncomeSourceDetailsUpdateResource();
		incomeSourceDetailsUpdateResource.setSourceType("PRIMARY");
		incomeSourceDetailsUpdateResource.setStatus("ACTIVE");
		incomeSourceDetailsUpdateResource.setVersion("1");
		return incomeSourceDetailsUpdateResource;
	}
	
	/**
     * sourceType is required.
     * Expected: {common.not-null}
     */
    @Test
	public void sourceTypeNotNull() {
    	IncomeSourceDetailsUpdateResource incomeSourceDetailsUpdateResource =setUpdateIncomeSourceDetailsUpdateResource();
    	incomeSourceDetailsUpdateResource.setSourceType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsUpdateResource, localValidatorFactory));
	}
    
	/**
     * sourceType should be PRIMARY or SECONDARY.
     * Expected: {common-source.pattern}
     */
    @Test
	public void sourceTypePattern() {
    	IncomeSourceDetailsUpdateResource incomeSourceDetailsUpdateResource =setUpdateIncomeSourceDetailsUpdateResource();
    	incomeSourceDetailsUpdateResource.setSourceType("ABCDEF");
		assertEquals("{common-source.pattern}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsUpdateResource, localValidatorFactory));
	}
	
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	IncomeSourceDetailsUpdateResource incomeSourceDetailsUpdateResource =setUpdateIncomeSourceDetailsUpdateResource();
    	incomeSourceDetailsUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	IncomeSourceDetailsUpdateResource incomeSourceDetailsUpdateResource =setUpdateIncomeSourceDetailsUpdateResource();
    	incomeSourceDetailsUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsUpdateResource, localValidatorFactory));
	}
    
   
    
    /**
     * version is required.
     * Expected: {common.not-null}
     */
    @Test
	public void versionNotNull() {
    	IncomeSourceDetailsUpdateResource incomeSourceDetailsUpdateResource = setUpdateIncomeSourceDetailsUpdateResource();
    	incomeSourceDetailsUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void versionPattern() {
    	IncomeSourceDetailsUpdateResource incomeSourceDetailsUpdateResource =setUpdateIncomeSourceDetailsUpdateResource();
    	incomeSourceDetailsUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsUpdateResource, localValidatorFactory));
	}


}
