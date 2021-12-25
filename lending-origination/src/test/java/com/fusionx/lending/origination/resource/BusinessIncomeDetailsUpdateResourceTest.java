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
 * 	Business Income Details Update Resource Test
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
public class BusinessIncomeDetailsUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}

	private BusinessIncomeDetailsUpdateResource setBusinessIncomeDetailsUpdateResource() {
		BusinessIncomeDetailsUpdateResource businessIncomeDetailsUpdateResource = new BusinessIncomeDetailsUpdateResource();
		businessIncomeDetailsUpdateResource.setStatus("ACTIVE");
		businessIncomeDetailsUpdateResource.setVersion("1");
		return businessIncomeDetailsUpdateResource;
	}
	
	
	/**
	 * status is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void statusNotNull() {
		BusinessIncomeDetailsUpdateResource businessIncomeDetailsUpdateResource =setBusinessIncomeDetailsUpdateResource();
		businessIncomeDetailsUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessIncomeDetailsUpdateResource, localValidatorFactory));
	}
	
	/**
	 * Status should be ACTIVE or INACTIVE.
	 * Expected: {common-status.pattern}
	 */
	@Test
	public void statusPattern() {
		BusinessIncomeDetailsUpdateResource businessIncomeDetailsUpdateResource =setBusinessIncomeDetailsUpdateResource();
		businessIncomeDetailsUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(businessIncomeDetailsUpdateResource, localValidatorFactory));
	}
	
	/**
	 * version is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void versionNotNull() {
		BusinessIncomeDetailsUpdateResource businessIncomeDetailsUpdateResource =setBusinessIncomeDetailsUpdateResource();
		businessIncomeDetailsUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessIncomeDetailsUpdateResource, localValidatorFactory));
	}
	
	/**
	 * version should be in "^$|[0-9]+" pattern
	 * Expected: {common-numeric.pattern}
	 */
	@Test
	public void versionPattern() {
		BusinessIncomeDetailsUpdateResource businessIncomeDetailsUpdateResource =setBusinessIncomeDetailsUpdateResource();
		businessIncomeDetailsUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
   
}
