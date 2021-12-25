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
 * Other Fee Type Update Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6604   	 FX-6509	Senitha      Created
 *    
 ********************************************************************************************************
 */
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class OtherFeeTypeUpdateResourceTest {
/*
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private OtherFeeTypeUpdateResource setFeeTypeUpdateResource() {
		
		OtherFeeTypeUpdateResource otherFeeTypeUpdateResource = new OtherFeeTypeUpdateResource();
		otherFeeTypeUpdateResource.setCode("TEST");
		otherFeeTypeUpdateResource.setName("Test");
		otherFeeTypeUpdateResource.setDescription("TEST");
		otherFeeTypeUpdateResource.setStatus("ACTIVE");
		otherFeeTypeUpdateResource.setFeeCategoryId("1");
		otherFeeTypeUpdateResource.setFeeCategoryName("Test category");
		otherFeeTypeUpdateResource.setTransactionCodeId("2");
		otherFeeTypeUpdateResource.setTransactionCode("Test Code");
		otherFeeTypeUpdateResource.setTransactionSubCodeId("3");
		otherFeeTypeUpdateResource.setTransactionCode("Test Sub Code");
		otherFeeTypeUpdateResource.setRefundable("YES");
		otherFeeTypeUpdateResource.setRefundablePercentage("8.8");
		otherFeeTypeUpdateResource.setCollectionType("INCOME");
		otherFeeTypeUpdateResource.setVersion("0");
		return otherFeeTypeUpdateResource;
		
	}
	
	/**
     * Version is required.
     * Expected: {version.not-null}
     */
/*    @Test
	public void versionNotNull() {
    	OtherFeeTypeUpdateResource otherFeeTypeUpdateResource = setFeeTypeUpdateResource();
    	otherFeeTypeUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherFeeTypeUpdateResource, localValidatorFactory));
	}
    
	/**
     * Invalid version.
     * Expected: {version.pattern}
     */
 /*   @Test
	public void versionPattern() {
    	OtherFeeTypeUpdateResource otherFeeTypeUpdateResource = setFeeTypeUpdateResource();
    	otherFeeTypeUpdateResource.setVersion("ABCDE");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(otherFeeTypeUpdateResource, localValidatorFactory));
	}
    */
}
