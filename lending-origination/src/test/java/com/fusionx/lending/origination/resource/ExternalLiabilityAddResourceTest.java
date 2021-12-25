package com.fusionx.lending.origination.resource;

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

import com.fusionx.lending.origination.utill.TestUtils;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExternalLiabilityAddResourceTest {

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
	
	private ExternalLiabilityAddResource setExternalLiabilityAddResource() {
		
		ExternalLiabilityAddResource externalLiabilityAddResource = new ExternalLiabilityAddResource();
		externalLiabilityAddResource.setCategory("EXTERNAL");
		externalLiabilityAddResource.setCommitmentTypeId("1");
		externalLiabilityAddResource.setCommitmentTypeName("commitment1");
		externalLiabilityAddResource.setFacilityTypeId("2");
		externalLiabilityAddResource.setFacilityTypeName("facility1");
		externalLiabilityAddResource.setBankId("1");
		externalLiabilityAddResource.setBankName("bank1");
		externalLiabilityAddResource.setBranchId("1");
		externalLiabilityAddResource.setBankBranchCode("branchcode1");
		externalLiabilityAddResource.setRepaymentFrequencyId("1");
		externalLiabilityAddResource.setRepaymentFrequencyName("frequency1");
		externalLiabilityAddResource.setOutstandingAmount("20.00");
		externalLiabilityAddResource.setNoOfRentalPaid("4");
		externalLiabilityAddResource.setRentalAmount("20.00");
		externalLiabilityAddResource.setDisbursementDate("2020-07-08");
		externalLiabilityAddResource.setFacilityAmount("20.00");
		externalLiabilityAddResource.setInterestRate("20.00");
		externalLiabilityAddResource.setOverdueAmount("20.00");
		externalLiabilityAddResource.setNote("note");
		externalLiabilityAddResource.setRemark("remark");	
		externalLiabilityAddResource.setStatus("ACTIVE");
		return externalLiabilityAddResource;
		
	}
	
	
	/**
     * Category Cannot be null.
     * Expected: {common.not-null}
     */
	@Test
    public void categoryNotNull() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setCategory(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
	
    /**
     * category Code should consists of alphanumeric characters only.
     * Expected: {common.code-pattern}
     */
    @Test
    public void categoryValue() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setCategory("ABC");
		assertEquals("{common-informalExternal.pattern}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
    
	/**
     * Commitment Type Id Cannot be null.
     * Expected: {common.not-null}
     */
	@Test
    public void commitmentTypeIdNotNull() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setCommitmentTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
	
    /**
     * Commitment Type Id  should consists of alphanumeric characters only.
     * Expected: {common.numeric-pattern}
     */
    @Test
    public void commitmentTypeIdPattern() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setCommitmentTypeId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
    
    /**
     * Commitment Type Name  cannot be null.
     * Expected: {common.not-null}
     */
	@Test
    public void commitmentTypeNameNotNull() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setCommitmentTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
	
    /**
     * Commitment Type Name  size should not exceed.
     * Expected: {common-name.size}
     */
	@Test
    public void commitmentTypeNameSize() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setCommitmentTypeName(longText);
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
	
	/**
     * Facility Type Id Cannot be null.
     * Expected: {common.not-null}
     */
	@Test
    public void facilityTypeIdNotNull() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setFacilityTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
	
	/**
     * Facility Type Id pattern should be numeric.
     * Expected: {common-numeric.pattern}
     */
    @Test
    public void facilityTypeIdPattern() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setFacilityTypeId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
    
	/**
     * Facility Type Name cannot be null.
     * Expected: {common.not-null}
     */
	@Test
    public void facilityTypeNameNotNull() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setFacilityTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
	
	/**
     * Facility Type Name size cannot exceed.
     * Expected: {common-name.size}
     */
	@Test
    public void facilityTypeNameSize() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setFacilityTypeName(longText);
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
	
    /**
     * bank id consists of numeric  only.
     * Expected: {common-numeric.pattern}
     */
    @Test
    public void bankIdPattern() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setBankId("qwer");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
    
	@Test
    public void bankNameSize() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setBankName(longText);
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
    
	/**
     * Facility Type Name size cannot exceed.
     * Expected: {common-name.size}
     */
    @Test
    public void branchcodeSize() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setBankBranchCode("1234567890abcd1234567890");;
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
    
    
    /**
     * branch id  should consists of numeric  only.
     * Expected: {common-numeric.pattern}
     */
    @Test
    public void branchIdPattern() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setBranchId("qwer");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
    
    /**
     * check rate patter.
     * Expected: {rate.pattern}
     */
    @Test
    public void interestRatePattern() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setInterestRate("9999.9999");
		assertEquals("{rate.pattern}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
    
    /**
     * Date pattern should be "YYYY-MM-DD".
     * Expected: {date.pattern}
     */
    @Test
    public void disbursementDatePattern() {
		ExternalLiabilityAddResource externalLiabilityAddResource = setExternalLiabilityAddResource();
		externalLiabilityAddResource.setDisbursementDate("2010-13-07");
		assertEquals("{date.pattern}", TestUtils.getFieldErrorMessageKey(externalLiabilityAddResource, localValidatorFactory));
    }
}
