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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FeeChargeDetailOptionalAddResourceTest {
	
	public String longText = "The Jews who descended from Abraham were never really the nation we associate with greatness.  They did not conquer and build a great empire like the Romans did or build large monuments like the Egyptians did with the pyramids. Their fame comes from the Law and Boo"
			+ "k which they wrote; from some remarkable individuals that were Jewish; and that they have survived as a somewhat different people group for thousands of years.  Their greatness is not because of anything they did, but rather what was done to and through them. "
			+ " The promise says repeatedly “I will …” – that would be the power behind the promise.  Their unique greatness happened because God made it happen rather than some ability, conquest or power of their own";
	

	private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	
	
	private FeeChargeDetailOptionalAddResource setFeeChargeDetailOptionalAddResource() {
		FeeChargeDetailOptionalAddResource feeChargeDetailOptionalAddResource = new FeeChargeDetailOptionalAddResource();
		feeChargeDetailOptionalAddResource.setFeeChargeId("1");
		feeChargeDetailOptionalAddResource.setFeeChargeName("abc");
		feeChargeDetailOptionalAddResource.setEffectiveDate("2020-05-02");
		
		
		List<FeeChargeDetailOptionalOptionResource> feeChargeDetailOptionalOptionResourceList = new ArrayList<>();
		
		FeeChargeDetailOptionalOptionResource feeChargeDetailOptionalOptionResource = new FeeChargeDetailOptionalOptionResource();
		feeChargeDetailOptionalOptionResource.setFeeChargeDetailOptionalOptionId("1");
		feeChargeDetailOptionalOptionResource.setOption("1");
		feeChargeDetailOptionalOptionResource.setAmount("9999.99");
		feeChargeDetailOptionalOptionResource.setRate("99.99");	
		
		feeChargeDetailOptionalOptionResourceList.add(feeChargeDetailOptionalOptionResource);
		
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
		
		feeChargeDetailOptionalAddResource.setCommonResource(feeChargeDetailsCommonResource);
		feeChargeDetailOptionalAddResource.setOptionsList(feeChargeDetailOptionalOptionResourceList);

		return feeChargeDetailOptionalAddResource;
	}
	
	
	/**
     * fee charge  id  is required.
     * Expected: {common.not-null}
     */
    @Test
	public void feeChargeIdNotNull() {
    	FeeChargeDetailOptionalAddResource feeChargeDetailOptionalAddResource = setFeeChargeDetailOptionalAddResource();
    	feeChargeDetailOptionalAddResource.setFeeChargeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeDetailOptionalAddResource, localValidatorFactory));
	}
    
	/**
     * fee charge id should be numeric.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void feeChargeIdIdPattern() {
    	FeeChargeDetailOptionalAddResource feeChargeDetailOptionalAddResource = setFeeChargeDetailOptionalAddResource();
    	feeChargeDetailOptionalAddResource.setFeeChargeId("asd");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(feeChargeDetailOptionalAddResource, localValidatorFactory));
	}
    
    
	/**
     * fee charge  id  is required.
     * Expected: {common.not-null}
     */
    @Test
	public void feeChargeNameNotNull() {
    	FeeChargeDetailOptionalAddResource feeChargeDetailOptionalAddResource = setFeeChargeDetailOptionalAddResource();
    	feeChargeDetailOptionalAddResource.setFeeChargeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeDetailOptionalAddResource, localValidatorFactory));
	}
    
	/**
     * fee charge id should be numeric.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void feeChargeNameSize() {
    	FeeChargeDetailOptionalAddResource feeChargeDetailOptionalAddResource = setFeeChargeDetailOptionalAddResource();
    	feeChargeDetailOptionalAddResource.setFeeChargeName(longText);
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(feeChargeDetailOptionalAddResource, localValidatorFactory));
	}
	
    
	/**
     * effective date  is required.
     * Expected: {common.not-null}
     */
    @Test
	public void effectiveDateNotNull() {
    	FeeChargeDetailOptionalAddResource feeChargeDetailOptionalAddResource = setFeeChargeDetailOptionalAddResource();
    	feeChargeDetailOptionalAddResource.setEffectiveDate(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeDetailOptionalAddResource, localValidatorFactory));
	}
    
    
	/**
     * effective date pattern 2020-08-02
     * Expected: {common.not-null}
     */
    @Test
	public void effectiveDatePatter() {
    	FeeChargeDetailOptionalAddResource feeChargeDetailOptionalAddResource = setFeeChargeDetailOptionalAddResource();
    	feeChargeDetailOptionalAddResource.setEffectiveDate("asdasad");
		assertEquals("{common-date.pattern}", TestUtils.getFieldErrorMessageKey(feeChargeDetailOptionalAddResource, localValidatorFactory));
	}
}
