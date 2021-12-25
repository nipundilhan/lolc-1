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
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class FeeChargeDetailAdhocUpdateResourceTest {
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
		
		
		private FeeChargeDetailAdhocUpdateResource setFeeChargeDetailAdhocUpdateResource() {
			
			FeeChargeDetailAdhocUpdateResource feeChargeDetailAdhocUpdateResource = new FeeChargeDetailAdhocUpdateResource();
			feeChargeDetailAdhocUpdateResource.setFeeChargePendingId("1");
			feeChargeDetailAdhocUpdateResource.setFeeChargeDetailsAdhocId("1");
			feeChargeDetailAdhocUpdateResource.setVersion("0");
			
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
			
			feeChargeDetailAdhocUpdateResource.setFeeChargeDetailsCommonResource(feeChargeDetailsCommonResource);
			
			return feeChargeDetailAdhocUpdateResource;
		}
		
	
	    
		/**
	     * fee charge pending id should be numeric.
	     * Expected: {common-numeric.pattern}
	     */
/*	    @Test
		public void feeChargePendingIdIdPattern() {
	    	FeeChargeDetailAdhocUpdateResource feeChargeDetailAdhocUpdateResource =  setFeeChargeDetailAdhocUpdateResource();
	    	feeChargeDetailAdhocUpdateResource.setFeeChargePendingId("asd");
			assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(feeChargeDetailAdhocUpdateResource, localValidatorFactory));
		}
	    
	    
		/**
	     * fee charge detail adhoc id should be numeric.
	     * Expected: {common-numeric.pattern}
	     */
/*	    @Test
		public void feeChargeDetailAdhocIdIdPattern() {
	    	FeeChargeDetailAdhocUpdateResource feeChargeDetailAdhocUpdateResource =  setFeeChargeDetailAdhocUpdateResource();
	    	feeChargeDetailAdhocUpdateResource.setFeeChargeDetailsAdhocId("asd");
			assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(feeChargeDetailAdhocUpdateResource, localValidatorFactory));
		}
	    
*/

}
