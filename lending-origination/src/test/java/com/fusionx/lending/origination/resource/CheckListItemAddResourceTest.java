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
 * Common update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   18-08-2021   FXL-627          FXL-563     Piyumi       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckListItemAddResourceTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	private CheckListItemAddResource setCheckListItemAddResource() {
		CheckListItemAddResource checkListItemAddResource = new CheckListItemAddResource();
		checkListItemAddResource.setCode("RF01");
		checkListItemAddResource.setName("Test");
		checkListItemAddResource.setDescription("Test");
		checkListItemAddResource.setStatus("ACTIVE");
		checkListItemAddResource.setCategoryId("1");
		checkListItemAddResource.setCategoryName("LOLC");
		return checkListItemAddResource;
	}

	/**
	 * code is required. Expected: {common.not-null}
	 */
	@Test
	public void codeNotNull() {
		CheckListItemAddResource checkListItemAddResource = setCheckListItemAddResource();
		checkListItemAddResource.setCode(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(checkListItemAddResource, localValidatorFactory));
	}

	/**
	 * code cannot be greater than or less than 4 characters. Expected:
	 * {common-code.size}
	 */
	@Test
	public void codeSize() {
		CheckListItemAddResource checkListItemAddResource = setCheckListItemAddResource();
		checkListItemAddResource.setCode("ABCDEF");
		assertEquals("{common-code.size}",
				TestUtils.getFieldErrorMessageKey(checkListItemAddResource, localValidatorFactory));
	}

	/**
	 * code should be in "^$|^[a-zA-Z0-9]+$" pattern Expected: {common.code-pattern}
	 */
	@Test
	public void codePattern() {
		CheckListItemAddResource checkListItemAddResource = setCheckListItemAddResource();
		checkListItemAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}",
				TestUtils.getFieldErrorMessageKey(checkListItemAddResource, localValidatorFactory));
	}

	/**
	 * Name is required. Expected: {common.not-null}
	 */
	@Test
	public void nameNotNull() {
		CheckListItemAddResource checkListItemAddResource = setCheckListItemAddResource();
		checkListItemAddResource.setName(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(checkListItemAddResource, localValidatorFactory));
	}

	/**
	 * name cannot be greater than 70 characters. Expected:
	 * {common-name.size}
	 */
	@Test
	public void nameSize() {
		CheckListItemAddResource checkListItemAddResource = setCheckListItemAddResource();
		checkListItemAddResource.setName(
				"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}",
				TestUtils.getFieldErrorMessageKey(checkListItemAddResource, localValidatorFactory));

	}

	/**
	 * status is required. Expected: {common.not-null}
	 */
	@Test
	public void statusNotNull() {
		CheckListItemAddResource checkListItemAddResource = setCheckListItemAddResource();
		checkListItemAddResource.setStatus(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(checkListItemAddResource, localValidatorFactory));
	}

	/**
	 * Status should be ACTIVE or INACTIVE. Expected: {common-status.pattern}
	 */
	@Test
	public void statusPattern() {
		CheckListItemAddResource checkListItemAddResource = setCheckListItemAddResource();
		checkListItemAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}",
				TestUtils.getFieldErrorMessageKey(checkListItemAddResource, localValidatorFactory));
	}

	/**
	 * description cannot be greater than 350 characters. Expected:
	 * {common-description.size}
	 */
	@Test
	public void descriptionSize() {
		CheckListItemAddResource checkListItemAddResource = setCheckListItemAddResource();
		checkListItemAddResource.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{common-description.size}",
				TestUtils.getFieldErrorMessageKey(checkListItemAddResource, localValidatorFactory));

	}

}
