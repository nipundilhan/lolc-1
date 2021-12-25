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
 * Eligibility Officer Type Add Resource Test
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6888	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EligibilityOfficerTypeAddResourceTest {

	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private EligibilityOfficerTypeAddResource setEligibilityOfficerTypeAddResource() {
		EligibilityOfficerTypeAddResource eligibilityOfficerTypeAddResource = new EligibilityOfficerTypeAddResource();
		eligibilityOfficerTypeAddResource.setEligibilityId("70");
		eligibilityOfficerTypeAddResource.setOfficerTypeId("181");
		eligibilityOfficerTypeAddResource.setStatus("ACTIVE");
		return eligibilityOfficerTypeAddResource;
	}
	
	
	/**
     * Can not be null.
     * Expected: {common.not-null}
     */
	@Test
	public void eligibilityIdNotNull() {
		EligibilityOfficerTypeAddResource eligibilityOfficerTypeAddResource =setEligibilityOfficerTypeAddResource();
		eligibilityOfficerTypeAddResource.setEligibilityId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityOfficerTypeAddResource, localValidatorFactory));
	}
	
	
	/**
     * Must be a numeric value.
     * Expected: {common-numeric.pattern}
     */
	@Test
	public void eligibilityIdPattern() {
		EligibilityOfficerTypeAddResource eligibilityOfficerTypeAddResource =setEligibilityOfficerTypeAddResource();
		eligibilityOfficerTypeAddResource.setEligibilityId("ABCDEF");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityOfficerTypeAddResource, localValidatorFactory));
	}
	
	
	/**
     * Can not be null.
     * Expected: {common.not-null}
     */
	@Test
	public void officerTypeIdNotNull() {
		EligibilityOfficerTypeAddResource eligibilityOfficerTypeAddResource =setEligibilityOfficerTypeAddResource();
		eligibilityOfficerTypeAddResource.setOfficerTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityOfficerTypeAddResource, localValidatorFactory));
	}
	
	
	/**
     * Must be a numeric value.
     * Expected: {common-numeric.pattern}
     */
	@Test
	public void officerTypeIdPattern() {
		EligibilityOfficerTypeAddResource eligibilityOfficerTypeAddResource =setEligibilityOfficerTypeAddResource();
		eligibilityOfficerTypeAddResource.setOfficerTypeId("ABCDEF");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityOfficerTypeAddResource, localValidatorFactory));
	}
	
	
	/**
     * status is required.
     * Expected: {common.not-null}
     */
	@Test
	public void statusNotNull() {
		EligibilityOfficerTypeAddResource eligibilityOfficerTypeAddResource =setEligibilityOfficerTypeAddResource();
		eligibilityOfficerTypeAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityOfficerTypeAddResource, localValidatorFactory));
	}
	
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
	@Test
	public void statusPattern() {
		EligibilityOfficerTypeAddResource eligibilityOfficerTypeAddResource =setEligibilityOfficerTypeAddResource();
		eligibilityOfficerTypeAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityOfficerTypeAddResource, localValidatorFactory));
	}
}
