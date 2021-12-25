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
 * 	Cultivation Income Details Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-09-2021  	             FXL-661       Dilhan       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CultivationIncomeDetailsAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private CultivationIncomeDetailsAddResource setCultivationIncomeDetailsAddResource() {
		CultivationIncomeDetailsAddResource cultivationIncomeDetailsAddResource = new CultivationIncomeDetailsAddResource();
		cultivationIncomeDetailsAddResource.setIncomeSourceDetailsId("1");
		return cultivationIncomeDetailsAddResource;
	}
	
	 /**
     * incomeSourceDetailsId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void incomeSourceDetailsIdNotNull() {
    	CultivationIncomeDetailsAddResource cultivationIncomeDetailsAddResource = setCultivationIncomeDetailsAddResource();
    	cultivationIncomeDetailsAddResource.setIncomeSourceDetailsId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * incomeSourceDetailsId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void incomeSourceDetailsIdPattern() {
    	CultivationIncomeDetailsAddResource cultivationIncomeDetailsAddResource = setCultivationIncomeDetailsAddResource();
    	cultivationIncomeDetailsAddResource.setIncomeSourceDetailsId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsAddResource, localValidatorFactory));
	}
	

}
