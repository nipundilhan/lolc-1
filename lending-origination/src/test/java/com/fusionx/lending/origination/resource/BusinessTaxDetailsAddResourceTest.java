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
 * 	Business Tax Details Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-09-2021   FXL-115  	 FXL-785       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessTaxDetailsAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private BusinessTaxDetailsAddResource setBusinessTaxDetailsAddResource() {
		BusinessTaxDetailsAddResource businessTaxDetailsAddResource = new BusinessTaxDetailsAddResource();
		businessTaxDetailsAddResource.setBusinessIncomeDetailId("1");
		return businessTaxDetailsAddResource;
	}
	
	 /**
     * businessIncomeDetailId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessIncomeDetailIdNotNull() {
    	BusinessTaxDetailsAddResource businessTaxDetailsAddResource = setBusinessTaxDetailsAddResource();
    	businessTaxDetailsAddResource.setBusinessIncomeDetailId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessTaxDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * businessIncomeDetailId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void businessIncomeDetailIdPattern() {
    	BusinessTaxDetailsAddResource businessTaxDetailsAddResource = setBusinessTaxDetailsAddResource();
    	businessTaxDetailsAddResource.setBusinessIncomeDetailId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessTaxDetailsAddResource, localValidatorFactory));
	}
	

}
