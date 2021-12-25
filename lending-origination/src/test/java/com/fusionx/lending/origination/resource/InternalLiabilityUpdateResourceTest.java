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
public class InternalLiabilityUpdateResourceTest {
	
	
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
	
	
	private InternalLiabilityUpdateResource setInternalLiabilityUpdateResource() {
		
		InternalLiabilityUpdateResource internalLiabilityUpdateResource = new InternalLiabilityUpdateResource();

		
		internalLiabilityUpdateResource.setId("1");
		internalLiabilityUpdateResource.setVersion("0");
		internalLiabilityUpdateResource.setCustomerType("ct");
		internalLiabilityUpdateResource.setCustomerTypeId("1");
		internalLiabilityUpdateResource.setCustomerTypeCode("ctcd");
		internalLiabilityUpdateResource.setFacilityType("ft");
		internalLiabilityUpdateResource.setFacilityTypeId("1");
		internalLiabilityUpdateResource.setFacilityTypeCode("ftcd");
		internalLiabilityUpdateResource.setRepaymentType("rt");
		internalLiabilityUpdateResource.setRepaymentTypeId("1");
		internalLiabilityUpdateResource.setRepaymentTypeCode("rtcd");
		internalLiabilityUpdateResource.setAssetType("at");
		internalLiabilityUpdateResource.setAssetTypeId("1");
		internalLiabilityUpdateResource.setAssetTypeCode("atcd");
		internalLiabilityUpdateResource.setAccountNo("2345");
		internalLiabilityUpdateResource.setFacilityAmount("20.00");
		internalLiabilityUpdateResource.setFacilityCreatedUser("samidu");
		internalLiabilityUpdateResource.setFacilityIssueDate("2020-07-08");
		internalLiabilityUpdateResource.setAvaliableBalance("20.00");
		internalLiabilityUpdateResource.setCurrentDue("20.00");
		internalLiabilityUpdateResource.setCurrentInstallment("20.00");
		internalLiabilityUpdateResource.setNoOfRentalPaid("2");
		internalLiabilityUpdateResource.setOverDue("20.00");
		internalLiabilityUpdateResource.setStatus("ACTIVE");
		internalLiabilityUpdateResource.setWriteOff("YES");
		internalLiabilityUpdateResource.setCompany("and company");
		internalLiabilityUpdateResource.setGroupCompany("abcd");
		internalLiabilityUpdateResource.setNote("note");
		return internalLiabilityUpdateResource;
		

		
	}
	
	/**
     * id Cannot be null.
     * Expected: {common.not-null}
     */
	@Test
    public void IdNotNull() {
		InternalLiabilityUpdateResource internalLiabilityUpdateResource = setInternalLiabilityUpdateResource();
		internalLiabilityUpdateResource.setId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(internalLiabilityUpdateResource, localValidatorFactory));
    }
	
	/**
     * id pattern check.
     * Expected: {common.numeric.pattern}
     */	
	@Test
    public void IdPattern() {
		InternalLiabilityUpdateResource internalLiabilityUpdateResource = setInternalLiabilityUpdateResource();
		internalLiabilityUpdateResource.setId("abc");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(internalLiabilityUpdateResource, localValidatorFactory));
    }

	/**
     * version not null check.
     * Expected: {version.not-null}
     */	
	@Test
    public void versionNotNull() {
		InternalLiabilityUpdateResource internalLiabilityUpdateResource = setInternalLiabilityUpdateResource();
		internalLiabilityUpdateResource.setVersion(null);
		assertEquals("{version.not-null}", TestUtils.getFieldErrorMessageKey(internalLiabilityUpdateResource, localValidatorFactory));
    }
	
	/**
     * version pattern check.
     * Expected: {version.pattern}
     */	
	@Test
    public void versionPattern() {
		InternalLiabilityUpdateResource internalLiabilityUpdateResource = setInternalLiabilityUpdateResource();
		internalLiabilityUpdateResource.setVersion("vrsn");
		assertEquals("{version.pattern}", TestUtils.getFieldErrorMessageKey(internalLiabilityUpdateResource, localValidatorFactory));
    }	
	
	/**
     * facility type not null check.
     * Expected: {common.not-null}
     */
	@Test
    public void facilityTypeNotNull() {
		InternalLiabilityUpdateResource internalLiabilityUpdateResource = setInternalLiabilityUpdateResource();
		internalLiabilityUpdateResource.setFacilityType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(internalLiabilityUpdateResource, localValidatorFactory));
    }

	/**
     * facility type name size check.
     * Expected: {common-name.size}
     */
	@Test
    public void  facilityTypeNameSize() {
		InternalLiabilityUpdateResource internalLiabilityUpdateResource = setInternalLiabilityUpdateResource();
		internalLiabilityUpdateResource.setFacilityType(longText);
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(internalLiabilityUpdateResource, localValidatorFactory));
    }
	
	/**
     * facility type amount null check.
     * Expected: {common.not-null}
     */
	@Test
    public void facilityAmountNotNull() {
		InternalLiabilityUpdateResource internalLiabilityUpdateResource = setInternalLiabilityUpdateResource();
		internalLiabilityUpdateResource.setFacilityAmount(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(internalLiabilityUpdateResource, localValidatorFactory));
    }

	/**
     * facility amount pattern check.
     * Expected: {amount.pattern}
     */
	@Test
    public void  facilityAmountPattern() {
		InternalLiabilityUpdateResource internalLiabilityUpdateResource = setInternalLiabilityUpdateResource();
		internalLiabilityUpdateResource.setFacilityAmount("abcd");
		assertEquals("{amount.pattern}", TestUtils.getFieldErrorMessageKey(internalLiabilityUpdateResource, localValidatorFactory));
    }
	
	
}
