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
/**
 * MasterDefinitionAddResourceTest
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   			Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-07-2021   FXL-1   				    FXL-5      Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.product.utill.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MasterDefinitionAddResourceTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	private MasterDefinitionAddResource setMasterDefinitionAddResource() {
		MasterDefinitionAddResource masterDefinitionAddResource = new MasterDefinitionAddResource();
		masterDefinitionAddResource.setCode("LCLO");
		masterDefinitionAddResource.setName("SME Loan - Local");
		masterDefinitionAddResource.setDescription("General Loan Module for Sri Lanka");
		masterDefinitionAddResource.setBusinessPrinciple("GENERAL");
		masterDefinitionAddResource.setModule("LEASE");
		masterDefinitionAddResource.setSubModuleCode("GLLC");
		masterDefinitionAddResource.setSubModuleName("General Loan LCU");
		masterDefinitionAddResource.setStatus("ACTIVE");
		return masterDefinitionAddResource;
	}

	/**
	 * code is required. Expected: {common.not-null}
	 */
	@Test
	public void codeNotNull() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setCode(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));
	}

	/**
	 * code cannot be greater than or less than 4 characters. Expected:
	 * {common-code.size}
	 */
	@Test
	public void codeSize() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setCode("WWWWW");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));
	}

	/**
	 * code should be in "^$|^[a-zA-Z0-9]+$" pattern Expected: {common.code-pattern}
	 */
	@Test
	public void codePattern() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));
	}

	/**
	 * name is required. Expected: {common.not-null}
	 */
	@Test
	public void nameNotNull() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setName(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));
	}

	/**
	 * name cannot be greater than 70 characters. Expected: {common-name.size}
	 */
	@Test
	public void nameSize() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setName(
				"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));

	}

	/**
	 * businessPrinciple cannot be greater than 70 characters. Expected:
	 * {common-businessPrinciple.size}
	 */
	@Test
	public void businessPrincipleSize() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setBusinessPrinciple(
				"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{masterDefinition-businessPrinciple.pattern}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));

	}

	/**
	 * businessPrinciple is required. Expected: {common.not-null}
	 */
	@Test
	public void businessPrincipleNotNull() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setBusinessPrinciple(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));
	}

	/**
	 * module cannot be greater than 70 characters. Expected: {common-module.size}
	 */
	@Test
	public void moduleSize() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setModule(
				"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-module.size}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));

	}

	/**
	 * module is required. Expected: {common.not-null}
	 */
	@Test
	public void moduleNotNull() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setModule(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));
	}

	/**
	 * subModuleSubModuleName cannot be greater than 70 characters. Expected:
	 * {common-subModuleSubModuleName.size}
	 */
	@Test
	public void subModuleSubModuleNameSize() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setSubModuleName(
				"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-subModuleName.size}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));

	}

	/**
	 * subModuleName is required. Expected: {common.not-null}
	 */
	@Test
	public void subModuleNameNotNull() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setSubModuleName(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));
	}

	/**
	 * subModuleCode cannot be greater than 4 characters. Expected:
	 * {common-code.size}
	 */
	@Test
	public void subModuleCodeSize() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setSubModuleCode(
				"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-code.size}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));

	}

	/**
	 * subModuleCode is required. Expected: {common.not-null}
	 */
	@Test
	public void subModuleCodeNotNull() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setSubModuleCode(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));
	}

	/**
	 * Description cannot be greater than 350 characters. Expected:
	 * {common-description.size}
	 */
	@Test
	public void descriptionSize() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setName(
				"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"
						+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"
						+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"
						+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));

	}

	/**
	 * status is required. Expected: {common.not-null}
	 */
	@Test
	public void statusNotNull() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setStatus(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));
	}

	/**
	 * Status should be ACTIVE or INACTIVE. Expected: {common-status.pattern}
	 */
	@Test
	public void statusPattern() {
		MasterDefinitionAddResource masterDefinitionAddResource = setMasterDefinitionAddResource();
		masterDefinitionAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionAddResource, localValidatorFactory));
	}

}
