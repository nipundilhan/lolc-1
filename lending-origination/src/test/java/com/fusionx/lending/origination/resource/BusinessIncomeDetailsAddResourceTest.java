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
 * 	Business Income Details Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-09-2021   FXL-115  	 FXL-816       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessIncomeDetailsAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
	    localValidatorFactory.setProviderClass(HibernateValidator.class);
	    localValidatorFactory.afterPropertiesSet();
	}
	
	private BusinessIncomeDetailsAddResource setBusinessIncomeDetailsAddResource() {
		BusinessIncomeDetailsAddResource businessIncomeDetailsAddResource = new BusinessIncomeDetailsAddResource();
		businessIncomeDetailsAddResource.setIncomeSourceDetailsId("1");
		businessIncomeDetailsAddResource.setStatus("ACTIVE");
		return businessIncomeDetailsAddResource;
	}
	
	 /**
	 * incomeSourceDetailsId is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void incomeSourceDetailsIdNotNull() {
		BusinessIncomeDetailsAddResource businessIncomeDetailsAddResource = setBusinessIncomeDetailsAddResource();
		businessIncomeDetailsAddResource.setIncomeSourceDetailsId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessIncomeDetailsAddResource, localValidatorFactory));
	}
	
	/**
	 * incomeSourceDetailsId should be in "^$|[0-9]+" pattern
	 * Expected: {common-numeric.pattern}
	 */
	@Test
	public void incomeSourceDetailsIdPattern() {
		BusinessIncomeDetailsAddResource businessIncomeDetailsAddResource =setBusinessIncomeDetailsAddResource();
		businessIncomeDetailsAddResource.setIncomeSourceDetailsId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessIncomeDetailsAddResource, localValidatorFactory));
	}
	
	/**
	 * status is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void statusNotNull() {
		BusinessIncomeDetailsAddResource businessIncomeDetailsAddResource =setBusinessIncomeDetailsAddResource();
		businessIncomeDetailsAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessIncomeDetailsAddResource, localValidatorFactory));
	}
	
	/**
	 * Status should be ACTIVE or INACTIVE.
	 * Expected: {common-status.pattern}
	 */
	@Test
	public void statusPattern() {
		BusinessIncomeDetailsAddResource businessIncomeDetailsAddResource =setBusinessIncomeDetailsAddResource();
		businessIncomeDetailsAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(businessIncomeDetailsAddResource, localValidatorFactory));
	}
	

}
