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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FeeChargeDetailOptionalOptionResourceTest {
	
	private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private FeeChargeDetailOptionalOptionResource setFeeChargeDetailOptionalOptionResource() {
		FeeChargeDetailOptionalOptionResource feeChargeDetailOptionalOptionResource = new FeeChargeDetailOptionalOptionResource();
		feeChargeDetailOptionalOptionResource.setFeeChargeDetailOptionalOptionId("1");
		feeChargeDetailOptionalOptionResource.setOption("1");
		feeChargeDetailOptionalOptionResource.setAmount("9999.99");
		feeChargeDetailOptionalOptionResource.setRate("99.99");	

		return feeChargeDetailOptionalOptionResource;
	}
	
	
	
    /**
     * check rate pattern.
     * Expected: {rate.pattern}
     */
    @Test
    public void ratePattern() {
    	FeeChargeDetailOptionalOptionResource feeChargeDetailOptionalOptionResource = setFeeChargeDetailOptionalOptionResource();
    	feeChargeDetailOptionalOptionResource.setRate("9999.999999");
		assertEquals("{common.rate-pattern.one}", TestUtils.getFieldErrorMessageKey(feeChargeDetailOptionalOptionResource, localValidatorFactory));
    }
    
    /**
     * check amount pattern.
     * Expected: {common.invalid-amount-pattern}
     */
    @Test
    public void amountPattern() {
    	FeeChargeDetailOptionalOptionResource feeChargeDetailOptionalOptionResource = setFeeChargeDetailOptionalOptionResource();
    	feeChargeDetailOptionalOptionResource.setAmount("99999.deci");
		assertEquals("{common.invalid-amount-pattern}", TestUtils.getFieldErrorMessageKey(feeChargeDetailOptionalOptionResource, localValidatorFactory));
    }
    
	/**
     * option Cannot be null.
     * Expected: {common.not-null}
     */
	@Test
    public void OptionNotNull() {
    	FeeChargeDetailOptionalOptionResource feeChargeDetailOptionalOptionResource = setFeeChargeDetailOptionalOptionResource();
    	feeChargeDetailOptionalOptionResource.setOption(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeDetailOptionalOptionResource, localValidatorFactory));
    }
	
    /**
     * option  should consists of alphanumeric characters only.
     * Expected: {common.numeric-pattern}
     */
    @Test
    public void optionPattern() {
    	FeeChargeDetailOptionalOptionResource feeChargeDetailOptionalOptionResource = setFeeChargeDetailOptionalOptionResource();
    	feeChargeDetailOptionalOptionResource.setOption("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(feeChargeDetailOptionalOptionResource, localValidatorFactory));
    }
    
    /**
     * FeeChargeDetailOptionalOptionId  should consists of alphanumeric characters only.
     * Expected: {common.numeric-pattern}
     */
    @Test
    public void FeeChargeDetailOptionalOptionIdPattern() {
    	FeeChargeDetailOptionalOptionResource feeChargeDetailOptionalOptionResource = setFeeChargeDetailOptionalOptionResource();
    	feeChargeDetailOptionalOptionResource.setFeeChargeDetailOptionalOptionId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(feeChargeDetailOptionalOptionResource, localValidatorFactory));
    }
}
