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
 * Master Def Account Rule Gl Post Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   18-07-2021   				   	 		Nipun_Dilhan      Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MasterDefAccountRuleGlPostResourceTest {

	
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	
	private MasterDefAccountRuleGlPostResource setMasterDefAccountRuleGlPostResource() {
		
		MasterDefAccountRuleGlPostResource masterDefAccountRuleGlPostResource = new MasterDefAccountRuleGlPostResource();
		masterDefAccountRuleGlPostResource.setAccountWise("YES");
		masterDefAccountRuleGlPostResource.setCustomerWise("YES");
		return masterDefAccountRuleGlPostResource;
		
	}
	
	
	/**
     * account wise value Cannot be null.
     * Expected: {common.not-null}
     */
	@Test
    public void accountWiseNull() {
		MasterDefAccountRuleGlPostResource masterDefAccountRuleGlPostResource = setMasterDefAccountRuleGlPostResource();
		masterDefAccountRuleGlPostResource.setAccountWise(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefAccountRuleGlPostResource, localValidatorFactory));
    }
	
    /**
     *  should be in "YES|NO" pattern
     * Expected: {common.enable-pattern}
     */
    @Test
	public void accountWisePattern() {
		MasterDefAccountRuleGlPostResource masterDefAccountRuleGlPostResource = setMasterDefAccountRuleGlPostResource();
		masterDefAccountRuleGlPostResource.setAccountWise("ABC");
		assertEquals("{common-enable.pattern}", TestUtils.getFieldErrorMessageKey(masterDefAccountRuleGlPostResource, localValidatorFactory));
	}
    
	/**
     * account wise value Cannot be null.
     * Expected: {common.not-null}
     */
	@Test
    public void customeWiseNull() {
		MasterDefAccountRuleGlPostResource masterDefAccountRuleGlPostResource = setMasterDefAccountRuleGlPostResource();
		masterDefAccountRuleGlPostResource.setCustomerWise(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefAccountRuleGlPostResource, localValidatorFactory));
    }
	
    /**
     *  should be in "YES|NO" pattern
     * Expected: {common.enable-pattern}
     */
    @Test
	public void customerWisePattern() {
		MasterDefAccountRuleGlPostResource masterDefAccountRuleGlPostResource = setMasterDefAccountRuleGlPostResource();
		masterDefAccountRuleGlPostResource.setCustomerWise("ABC");
		assertEquals("{common-enable.pattern}", TestUtils.getFieldErrorMessageKey(masterDefAccountRuleGlPostResource, localValidatorFactory));
	}
	
	
}
