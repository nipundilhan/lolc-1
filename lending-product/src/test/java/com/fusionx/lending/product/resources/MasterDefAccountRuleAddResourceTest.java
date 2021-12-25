package com.fusionx.lending.product.resources;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
 * Master Def Account Rule Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   18-07-2021   				   	 		Nipun_Dilhan      Created
 *    
 ********************************************************************************************************
 */
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class MasterDefAccountRuleAddResourceTest {
/*	
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private MasterDefAccountRuleAddResource setMasterDefAccountRuleAddResource() {
		
		MasterDefAccountRuleGlPostResource masterDefAccountRuleGlPostResource = new MasterDefAccountRuleGlPostResource();
		masterDefAccountRuleGlPostResource.setAccountWise("YES");
		masterDefAccountRuleGlPostResource.setCustomerWise("YES");
		
		MasterDefAccountRuleReconResource masterDefAccountRuleReconResource = new MasterDefAccountRuleReconResource();
		masterDefAccountRuleReconResource.setEndOfDay("YES");
		masterDefAccountRuleReconResource.setOnDemand("YES");
		masterDefAccountRuleReconResource.setRealTime("YES");
		
		List<CommonListUsageResource> commonList = new ArrayList<>();
		CommonListUsageResource commonListUsageResource = new CommonListUsageResource();
		commonListUsageResource.setId("1");
		commonListUsageResource.setReferenceCode("MSTDEFACCSTND");
		commonListUsageResource.setIsSelected("YES");
		
		MasterDefAccountRuleAddResource masterDefAccountRuleAddResource = new MasterDefAccountRuleAddResource();
		masterDefAccountRuleAddResource.setMasterDefinitionId("1");	
		masterDefAccountRuleAddResource.setReconciliations(masterDefAccountRuleReconResource);
		masterDefAccountRuleAddResource.setGlEntryPosting(masterDefAccountRuleGlPostResource);
		masterDefAccountRuleAddResource.setCommonListUsageList(commonList);
		return masterDefAccountRuleAddResource;
		
	}
	
	/**
     * Id Cannot be null.
     * Expected: {common.not-null}
     */
/*	@Test
    public void masterDefinitionIdNotNull() {
		MasterDefAccountRuleAddResource masterDefAccountRuleAddResource = setMasterDefAccountRuleAddResource();
		masterDefAccountRuleAddResource.setMasterDefinitionId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefAccountRuleAddResource, localValidatorFactory));
    }
	
	/**
     * Id Cannot be null.
     * Expected: {common.not-null}
     */
/*	@Test
    public void masterDefinitionIdPattern() {
		MasterDefAccountRuleAddResource masterDefAccountRuleAddResource = setMasterDefAccountRuleAddResource();
		masterDefAccountRuleAddResource.setMasterDefinitionId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(masterDefAccountRuleAddResource, localValidatorFactory));
    }

*/
}
