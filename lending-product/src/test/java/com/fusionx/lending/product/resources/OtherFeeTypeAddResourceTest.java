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
 * Other Fee Type Add Resource Test
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
public class OtherFeeTypeAddResourceTest {
/*
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private OtherFeeTypeAddResource setOtherFeeTypeAddResource() {
		
		OtherFeeTypeAddResource otherFeeTypeAddResource = new OtherFeeTypeAddResource();
		otherFeeTypeAddResource.setCode("TEST");
		otherFeeTypeAddResource.setName("Test");
		otherFeeTypeAddResource.setDescription("TEST");
		otherFeeTypeAddResource.setStatus("ACTIVE");
		otherFeeTypeAddResource.setFeeCategoryId("1");
		otherFeeTypeAddResource.setFeeCategoryName("Test category");
		otherFeeTypeAddResource.setTransactionCodeId("2");
		otherFeeTypeAddResource.setTransactionCode("Test Code");
		otherFeeTypeAddResource.setTransactionSubCodeId("3");
		otherFeeTypeAddResource.setTransactionCode("Test Sub Code");
		otherFeeTypeAddResource.setRefundable("YES");
		otherFeeTypeAddResource.setRefundablePercentage("8.8");
		otherFeeTypeAddResource.setCollectionType("INCOME");
		return otherFeeTypeAddResource;
		
	}

	/**
     * Code Cannot be blank.
     * Expected: {common.not-null}
     */
/*	@Test
    public void codeNotNull() {
		OtherFeeTypeAddResource otherFeeTypeAddResource = setOtherFeeTypeAddResource();
		otherFeeTypeAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherFeeTypeAddResource, localValidatorFactory));
    }
	
	/**
     * Code Invalid value.
     * Expected: {common.invalid-value}
     */
/*    @Test
    public void codeSize() {
    	OtherFeeTypeAddResource otherFeeTypeAddResource = setOtherFeeTypeAddResource();
    	otherFeeTypeAddResource.setCode("WWWWW");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(otherFeeTypeAddResource, localValidatorFactory));
    }
    
    /**
     * Code should consists of alphanumeric characters only.
     * Expected: {common.code-pattern}
     */
/*    @Test
    public void codePattern() {
    	OtherFeeTypeAddResource otherFeeTypeAddResource = setOtherFeeTypeAddResource();
    	otherFeeTypeAddResource.setCode("!@#$");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(otherFeeTypeAddResource, localValidatorFactory));
    }
    
    /**
     * Status Cannot be blank.
     * Expected: {common.not-null}
     */
/*    @Test
    public void statusNotNull() {
    	OtherFeeTypeAddResource otherFeeTypeAddResource = setOtherFeeTypeAddResource();
    	otherFeeTypeAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherFeeTypeAddResource, localValidatorFactory));
		
    }
    
    /**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
/*    @Test
    public void statusPattern() {
    	OtherFeeTypeAddResource otherFeeTypeAddResource = setOtherFeeTypeAddResource();
    	otherFeeTypeAddResource.setStatus("ABCD");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(otherFeeTypeAddResource, localValidatorFactory));
    }
    
    /**
     * Must be an numeric value.
     * Expected: {common-numeric.pattern}
     */
/*    @Test
    public void feeCategoryIdPattern() {
    	OtherFeeTypeAddResource otherFeeTypeAddResource = setOtherFeeTypeAddResource();
    	otherFeeTypeAddResource.setFeeCategoryId("ABCD");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(otherFeeTypeAddResource, localValidatorFactory));
		
    }
    
    /**
     * Must be an numeric value.
     * Expected: {common-numeric.pattern}
     */
 /*   @Test
    public void transactionCodeIdPattern() {
    	OtherFeeTypeAddResource otherFeeTypeAddResource = setOtherFeeTypeAddResource();
    	otherFeeTypeAddResource.setTransactionCodeId("ABCD");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(otherFeeTypeAddResource, localValidatorFactory));
		
    }
    
    /**
     * Must be an numeric value.
     * Expected: {common-numeric.pattern}
     */
/*    @Test
    public void transactionSubCodeIdPattern() {
    	OtherFeeTypeAddResource otherFeeTypeAddResource = setOtherFeeTypeAddResource();
    	otherFeeTypeAddResource.setTransactionSubCodeId("ABCD");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(otherFeeTypeAddResource, localValidatorFactory));
		
    }*/
    
}
