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


/**
 * Analyst Update Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *   1   26-08-2021   FXL-117  	 FXL-654       Piyumi     Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnalystUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
	    localValidatorFactory.setProviderClass(HibernateValidator.class);
	    localValidatorFactory.afterPropertiesSet();
	}
	
	private AnalystUpdateResource setAnalystUpdateResource() {
		AnalystUpdateResource analystUpdateResource = new AnalystUpdateResource();
		analystUpdateResource.setAnalystType("INTERNAL");
		analystUpdateResource.setAnalystUserId("1");
		analystUpdateResource.setAnalystUserName("Piyumi");
		analystUpdateResource.setLeadId("1");
		analystUpdateResource.setObservation("Test");
		analystUpdateResource.setStatus("ACTIVE");
		analystUpdateResource.setVersion("1");
		return analystUpdateResource;
	}
	
	/**
	 * analystType is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void analystTypeNotNull() {
		AnalystUpdateResource analystUpdateResource =setAnalystUpdateResource();
		analystUpdateResource.setAnalystType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(analystUpdateResource, localValidatorFactory));
	}
	
	/**
	 * analystType should be INTERNAL or EXTERNAL
	 * Expected: {analyst-type.pattern}
	 */
	@Test
	public void analystTypePattern() {
		AnalystUpdateResource analystUpdateResource =setAnalystUpdateResource();
		analystUpdateResource.setAnalystType("ABCDEF");
		assertEquals("{analyst-type.pattern}", TestUtils.getFieldErrorMessageKey(analystUpdateResource, localValidatorFactory));
	}
	
	 /**
	 * analystUserId is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void otherIncomeCategoryIdNotNull() {
		AnalystUpdateResource analystUpdateResource = setAnalystUpdateResource();
		analystUpdateResource.setAnalystUserId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(analystUpdateResource, localValidatorFactory));
	}
	
	/**
	 *  analystUserName is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void analystUserNameNotNull() {
		AnalystUpdateResource analystUpdateResource = setAnalystUpdateResource();
		analystUpdateResource.setAnalystUserName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(analystUpdateResource, localValidatorFactory));
	}
	
	/**
	 * analystUserName cannot be greater than 70 characters.
	 * Expected: {common-name.size}
	 */
	@Test
	public void analystUserNameSize() {
		AnalystUpdateResource analystUpdateResource =setAnalystUpdateResource();
		analystUpdateResource.setAnalystUserName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(analystUpdateResource, localValidatorFactory));
		
	}
	
	 /**
	 * leadId is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void leadIdNotNull() {
		AnalystUpdateResource analystUpdateResource = setAnalystUpdateResource();
		analystUpdateResource.setLeadId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(analystUpdateResource, localValidatorFactory));
	}
	
	/**
	 * leadId should be in "^$|[0-9]+" pattern
	 * Expected: {common-numeric.pattern}
	 */
	@Test
	public void leadIdPattern() {
		AnalystUpdateResource analystUpdateResource =setAnalystUpdateResource();
		analystUpdateResource.setLeadId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(analystUpdateResource, localValidatorFactory));
	}
	
	
	/**
	 * status is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void statusNotNull() {
		AnalystUpdateResource analystUpdateResource =setAnalystUpdateResource();
		analystUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(analystUpdateResource, localValidatorFactory));
	}
	
	/**
	 * Status should be ACTIVE or INACTIVE.
	 * Expected: {common-status.pattern}
	 */
	@Test
	public void statusPattern() {
		AnalystUpdateResource analystUpdateResource =setAnalystUpdateResource();
		analystUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(analystUpdateResource, localValidatorFactory));
	}
	
	/**
	 * observation is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void observationNotNull() {
		AnalystUpdateResource analystUpdateResource =setAnalystUpdateResource();
		analystUpdateResource.setObservation(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(analystUpdateResource, localValidatorFactory));
	}
	
	/**
	 * observation cannot be greater than 1500 characters.
	 * Expected: {observation.size}
	 */
	@Test
	public void observationSize() {
		AnalystUpdateResource analystUpdateResource =setAnalystUpdateResource();
		analystUpdateResource.setObservation("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{observation.size}", TestUtils.getFieldErrorMessageKey(analystUpdateResource, localValidatorFactory));
		
	}
	    
	/**
	 * version is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void versionNotNull() {
		AnalystUpdateResource analystUpdateResource = setAnalystUpdateResource();
		analystUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(analystUpdateResource, localValidatorFactory));
	}
	
	/**
	 * version should be in "^$|[0-9]+" pattern
	 * Expected: {common-numeric.pattern}
	 */
	@Test
	public void versionPattern() {
		AnalystUpdateResource analystUpdateResource =setAnalystUpdateResource();
		analystUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(analystUpdateResource, localValidatorFactory));
	}


}
