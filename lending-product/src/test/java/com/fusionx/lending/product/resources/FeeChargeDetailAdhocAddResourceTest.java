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

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class FeeChargeDetailAdhocAddResourceTest {
/*	
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
	
	
	private FeeChargeDetailAdhocAddResource setFeeChargeDetailAdhocAddResource() {
		
		FeeChargeDetailAdhocAddResource feeChargeDetailAdhocAddResource = new FeeChargeDetailAdhocAddResource();
		feeChargeDetailAdhocAddResource.setFeeChargeId("1");
		feeChargeDetailAdhocAddResource.setFeeChargeName("abc");
		
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
		
		feeChargeDetailAdhocAddResource.setFeeChargeDetailsCommonResource(feeChargeDetailsCommonResource);
		
		return feeChargeDetailAdhocAddResource;
	}
	
	
	/**
     * fee charge  id  is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void feeChargeIdNotNull() {
    	FeeChargeDetailAdhocAddResource feeChargeDetailAdhocAddResource =  setFeeChargeDetailAdhocAddResource();
    	feeChargeDetailAdhocAddResource.setFeeChargeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeDetailAdhocAddResource, localValidatorFactory));
	}
    
	/**
     * fee charge id should be numeric.
     * Expected: {common-numeric.pattern}
     */
 /*   @Test
	public void feeChargeIdIdPattern() {
    	FeeChargeDetailAdhocAddResource feeChargeDetailAdhocAddResource =  setFeeChargeDetailAdhocAddResource();
    	feeChargeDetailAdhocAddResource.setFeeChargeId("asd");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(feeChargeDetailAdhocAddResource, localValidatorFactory));
	}
    
    
	/**
     * fee charge  id  is required.
     * Expected: {common.not-null}
     */
  /*  @Test
	public void feeChargeNameNotNull() {
    	FeeChargeDetailAdhocAddResource feeChargeDetailAdhocAddResource =  setFeeChargeDetailAdhocAddResource();
    	feeChargeDetailAdhocAddResource.setFeeChargeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeDetailAdhocAddResource, localValidatorFactory));
	}
    
	/**
     * fee charge id should be numeric.
     * Expected: {common-numeric.pattern}
     */
  /*  @Test
	public void feeChargeNameSize() {
    	FeeChargeDetailAdhocAddResource feeChargeDetailAdhocAddResource =  setFeeChargeDetailAdhocAddResource();
    	feeChargeDetailAdhocAddResource.setFeeChargeName(longText);
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(feeChargeDetailAdhocAddResource, localValidatorFactory));
	}
*/
}
