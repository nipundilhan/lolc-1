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
public class FeeChargeDetailsCommonResourceTest {
	
	private LocalValidatorFactoryBean localValidatorFactory;
	
	public String longText = "The Jews who descended from Abraham were never really the nation we associate with greatness.  They did not conquer and build a great empire like the Romans did or build large monuments like the Egyptians did with the pyramids. Their fame comes from the Law and Boo"
			+ "k which they wrote; from some remarkable individuals that were Jewish; and that they have survived as a somewhat different people group for thousands of years.  Their greatness is not because of anything they did, but rather what was done to and through them. "
			+ " The promise says repeatedly “I will …” – that would be the power behind the promise.  Their unique greatness happened because God made it happen rather than some ability, conquest or power of their own";
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	
	private FeeChargeDetailsCommonResource setFeeChargeDetailsCommonResource() {
		FeeChargeDetailsCommonResource feeChargeDetailsCommonResource = new FeeChargeDetailsCommonResource();
		feeChargeDetailsCommonResource.setStatus("ACTIVE");
		feeChargeDetailsCommonResource.setType("Rate");
		feeChargeDetailsCommonResource.setMandatory("YES");
		feeChargeDetailsCommonResource.setNote("sample");
		feeChargeDetailsCommonResource.setFeeTypeId("1");
		feeChargeDetailsCommonResource.setFeeTypeName("sample");
		feeChargeDetailsCommonResource.setApplicationFrequencyId("1");
		feeChargeDetailsCommonResource.setApplicationFrequencyName("sample");
		feeChargeDetailsCommonResource.setCalculationFrequencyId("1");
		feeChargeDetailsCommonResource.setCalculationFrequencyName("sample");
		feeChargeDetailsCommonResource.setFeeCategoryId("1");
		feeChargeDetailsCommonResource.setFeeCategoryName("sample");
		return feeChargeDetailsCommonResource;
	}
	
	
    /**
     * Status Cannot be blank.
     * Expected: {common.not-null}
     */
    @Test
    public void statusNotNull() {
		FeeChargeDetailsCommonResource feeChargeDetailsCommonResource = setFeeChargeDetailsCommonResource();
		feeChargeDetailsCommonResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeDetailsCommonResource, localValidatorFactory));
		
    }
    
    /**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
    public void statusPattern() {
		FeeChargeDetailsCommonResource feeChargeDetailsCommonResource = setFeeChargeDetailsCommonResource();
		feeChargeDetailsCommonResource.setStatus("ABC");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(feeChargeDetailsCommonResource, localValidatorFactory));
    }
    /**
     * type Cannot be blank.
     * Expected: {common.not-null}
     */
    @Test
    public void typeNotNull() {
		FeeChargeDetailsCommonResource feeChargeDetailsCommonResource = setFeeChargeDetailsCommonResource();
		feeChargeDetailsCommonResource.setType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeDetailsCommonResource, localValidatorFactory));
		
    }
    
    /**
     * type should be Amount or Rate.
     * Expected: {common-status.pattern}
     */
    @Test
    public void typePattern() {
		FeeChargeDetailsCommonResource feeChargeDetailsCommonResource = setFeeChargeDetailsCommonResource();
		feeChargeDetailsCommonResource.setType("ABC");
		assertEquals("{feeCharge-type-value-error}", TestUtils.getFieldErrorMessageKey(feeChargeDetailsCommonResource, localValidatorFactory));
    }
    /**
     * mandatory Cannot be blank.
     * Expected: {common.not-null}
     */
    @Test
    public void mandatoryNotNull() {
		FeeChargeDetailsCommonResource feeChargeDetailsCommonResource = setFeeChargeDetailsCommonResource();
		feeChargeDetailsCommonResource.setMandatory(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeDetailsCommonResource, localValidatorFactory));
		
    }
    
    /**
     * mandatory should be YES or NO.
     * Expected: {common-enable.pattern}
     */
    @Test
    public void mandatoryPattern() {
		FeeChargeDetailsCommonResource feeChargeDetailsCommonResource = setFeeChargeDetailsCommonResource();
		feeChargeDetailsCommonResource.setMandatory("ABC");
		assertEquals("{common-enable.pattern}", TestUtils.getFieldErrorMessageKey(feeChargeDetailsCommonResource, localValidatorFactory));
    }
	
	/**
     * note size cannot exceed.
     * Expected: {common-name.size}
     */
	@Test
    public void noteSize() {
		FeeChargeDetailsCommonResource feeChargeDetailsCommonResource = setFeeChargeDetailsCommonResource();
		feeChargeDetailsCommonResource.setNote(longText);
		assertEquals("{common-note.size-two}", TestUtils.getFieldErrorMessageKey(feeChargeDetailsCommonResource, localValidatorFactory));
    }
	
	
	/**
     * fee Type Id Cannot be null.
     * Expected: {common.not-null}
     */
	@Test
    public void feeTypeIdNotNull() {
		FeeChargeDetailsCommonResource feeChargeDetailsCommonResource = setFeeChargeDetailsCommonResource();
		feeChargeDetailsCommonResource.setFeeTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeDetailsCommonResource, localValidatorFactory));
    }
	
    /**
     * fee Type Id  should consists of alphanumeric characters only.
     * Expected: {common.numeric-pattern}
     */
    @Test
    public void feeTypeIdPattern() {
		FeeChargeDetailsCommonResource feeChargeDetailsCommonResource = setFeeChargeDetailsCommonResource();
		feeChargeDetailsCommonResource.setFeeTypeId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(feeChargeDetailsCommonResource, localValidatorFactory));
    }
    
    /**
     * fee Type Name  cannot be null.
     * Expected: {common.not-null}
     */
	@Test
    public void feeTypeNameNotNull() {
		FeeChargeDetailsCommonResource feeChargeDetailsCommonResource = setFeeChargeDetailsCommonResource();
		feeChargeDetailsCommonResource.setFeeTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeDetailsCommonResource, localValidatorFactory));
    }
	
    /**
     * fee Type Name  size should not exceed.
     * Expected: {common-name.size}
     */
	@Test
    public void feeTypeNameSize() {
		FeeChargeDetailsCommonResource feeChargeDetailsCommonResource = setFeeChargeDetailsCommonResource();
		feeChargeDetailsCommonResource.setFeeTypeName(longText);
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(feeChargeDetailsCommonResource, localValidatorFactory));
    }
	
	

}
