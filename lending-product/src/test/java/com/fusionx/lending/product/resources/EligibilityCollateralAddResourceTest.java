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
 * Eligibility Collateral Add Resource Test
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6889	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EligibilityCollateralAddResourceTest {

	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private EligibilityCollateralAddResource setEligibilityCollateralAddResource() {
		EligibilityCollateralAddResource eligibilityCollateralAddResource = new EligibilityCollateralAddResource();
		eligibilityCollateralAddResource.setEligibilityId("70");
		eligibilityCollateralAddResource.setAssetTypeId("181");
		eligibilityCollateralAddResource.setAssetTypeName("Machinery");
		eligibilityCollateralAddResource.setStatus("ACTIVE");
		return eligibilityCollateralAddResource;
	}
	
	
	/**
     * Can not be null.
     * Expected: {common.not-null}
     */
	@Test
	public void eligibilityIdNotNull() {
		EligibilityCollateralAddResource eligibilityCollateralAddResource =setEligibilityCollateralAddResource();
		eligibilityCollateralAddResource.setEligibilityId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCollateralAddResource, localValidatorFactory));
	}
	
	
	/**
     * Must be a numeric value.
     * Expected: {common-numeric.pattern}
     */
	@Test
	public void eligibilityIdPattern() {
		EligibilityCollateralAddResource eligibilityCollateralAddResource =setEligibilityCollateralAddResource();
		eligibilityCollateralAddResource.setEligibilityId("ABCDEF");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCollateralAddResource, localValidatorFactory));
	}
	
	
	/**
     * Can not be null.
     * Expected: {common.not-null}
     */
	@Test
	public void assetTypeIdNotNull() {
		EligibilityCollateralAddResource eligibilityCollateralAddResource =setEligibilityCollateralAddResource();
		eligibilityCollateralAddResource.setAssetTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCollateralAddResource, localValidatorFactory));
	}
	
	
	/**
     * Must be a numeric value.
     * Expected: {common-numeric.pattern}
     */
	@Test
	public void assetTypeIdPattern() {
		EligibilityCollateralAddResource eligibilityCollateralAddResource =setEligibilityCollateralAddResource();
		eligibilityCollateralAddResource.setAssetTypeId("ABCDEF");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCollateralAddResource, localValidatorFactory));
	}
	
	
	/**
     * Can not be null.
     * Expected: {common.not-null}
     */
	@Test
	public void assetTypeNameNotNull() {
		EligibilityCollateralAddResource eligibilityCollateralAddResource =setEligibilityCollateralAddResource();
		eligibilityCollateralAddResource.setAssetTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCollateralAddResource, localValidatorFactory));
	}
	
	
	/**
     * status is required.
     * Expected: {common.not-null}
     */
	@Test
	public void statusNotNull() {
		EligibilityCollateralAddResource eligibilityCollateralAddResource =setEligibilityCollateralAddResource();
		eligibilityCollateralAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCollateralAddResource, localValidatorFactory));
	}
	
	
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
	@Test
	public void statusPattern() {
		EligibilityCollateralAddResource eligibilityCollateralAddResource =setEligibilityCollateralAddResource();
		eligibilityCollateralAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCollateralAddResource, localValidatorFactory));
	}
	
}
