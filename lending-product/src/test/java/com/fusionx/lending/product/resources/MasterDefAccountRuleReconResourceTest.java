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
 * Master Def Account Rule Recon Resource Test
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
public class MasterDefAccountRuleReconResourceTest {
	
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	
	private MasterDefAccountRuleReconResource setMasterDefAccountRuleReconResource() {
		
		MasterDefAccountRuleReconResource masterDefAccountRuleReconResource = new MasterDefAccountRuleReconResource();
		masterDefAccountRuleReconResource.setEndOfDay("YES");
		masterDefAccountRuleReconResource.setOnDemand("YES");
		masterDefAccountRuleReconResource.setRealTime("YES");
		return masterDefAccountRuleReconResource;
		
	}
	
	
	/**
     * end of the day Cannot be null.
     * Expected: {common.not-null}
     */
	@Test
    public void endOfDayNotNull() {
		MasterDefAccountRuleReconResource masterDefAccountRuleReconResource = setMasterDefAccountRuleReconResource();
		masterDefAccountRuleReconResource.setEndOfDay(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefAccountRuleReconResource, localValidatorFactory));
    }
	
    /**
     * end of the day should be in "YES|NO" pattern
     * Expected: {common-enable.pattern}
     */
    @Test
	public void endOfDayPattern() {
		MasterDefAccountRuleReconResource masterDefAccountRuleReconResource = setMasterDefAccountRuleReconResource();
		masterDefAccountRuleReconResource.setEndOfDay("ABC");
		assertEquals("{common-enable.pattern}", TestUtils.getFieldErrorMessageKey(masterDefAccountRuleReconResource, localValidatorFactory));
	}
    
	/**
     * end of the day Cannot be null.
     * Expected: {common.not-null}
     */
	@Test
    public void onDemandNotNull() {
		MasterDefAccountRuleReconResource masterDefAccountRuleReconResource = setMasterDefAccountRuleReconResource();
		masterDefAccountRuleReconResource.setOnDemand(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefAccountRuleReconResource, localValidatorFactory));
    }
	
    /**
     * end of the day should be in "YES|NO" pattern
     * Expected: {common-enable.pattern}
     */
    @Test
	public void onDemandPattern() {
		MasterDefAccountRuleReconResource masterDefAccountRuleReconResource = setMasterDefAccountRuleReconResource();
		masterDefAccountRuleReconResource.setOnDemand("ABC");
		assertEquals("{common-enable.pattern}", TestUtils.getFieldErrorMessageKey(masterDefAccountRuleReconResource, localValidatorFactory));
	}
    
	/**
     * end of the day Cannot be null.
     * Expected: {common.not-null}
     */
	@Test
    public void realTimeNotNull() {
		MasterDefAccountRuleReconResource masterDefAccountRuleReconResource = setMasterDefAccountRuleReconResource();
		masterDefAccountRuleReconResource.setRealTime(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefAccountRuleReconResource, localValidatorFactory));
    }
	
    /**
     * end of the day should be in "YES|NO" pattern
     * Expected: {common-enable.pattern}
     */
    @Test
	public void realTimePattern() {
		MasterDefAccountRuleReconResource masterDefAccountRuleReconResource = setMasterDefAccountRuleReconResource();
		masterDefAccountRuleReconResource.setRealTime("ABC");
		assertEquals("{common-enable.pattern}", TestUtils.getFieldErrorMessageKey(masterDefAccountRuleReconResource, localValidatorFactory));
	}
	
	

}
