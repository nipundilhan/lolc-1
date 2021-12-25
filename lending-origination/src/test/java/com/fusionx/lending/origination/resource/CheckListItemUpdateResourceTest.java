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
public class CheckListItemUpdateResourceTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	private CommonUpdateResource setCommonUpdateResource() {
		CommonUpdateResource checkListItemUpdateResource = new CommonUpdateResource();
		checkListItemUpdateResource.setCode("RF01");
		checkListItemUpdateResource.setName("Test");
		checkListItemUpdateResource.setDescription("Test");
		checkListItemUpdateResource.setStatus("ACTIVE");
		checkListItemUpdateResource.setVersion("1");
		return checkListItemUpdateResource;
	}

	/**
	 * code is required. Expected: {common.not-null}
	 */
	@Test
	public void codeNotNull() {
		CommonUpdateResource checkListItemUpdateResource = setCommonUpdateResource();
		checkListItemUpdateResource.setCode(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(checkListItemUpdateResource, localValidatorFactory));
	}

	/**
	 * code cannot be greater than or less than 4 characters. Expected:
	 * {common-code.size}
	 */
	@Test
	public void codeSize() {
		CommonUpdateResource checkListItemUpdateResource = setCommonUpdateResource();
		checkListItemUpdateResource.setCode("ABCDEF");
		assertEquals("{common-code.size}",
				TestUtils.getFieldErrorMessageKey(checkListItemUpdateResource, localValidatorFactory));
	}

	/**
	 * code should be in "^$|^[a-zA-Z0-9]+$" pattern Expected: {common.code-pattern}
	 */
	@Test
	public void codePattern() {
		CommonUpdateResource checkListItemUpdateResource = setCommonUpdateResource();
		checkListItemUpdateResource.setCode("ABC!");
		assertEquals("{common.code-pattern}",
				TestUtils.getFieldErrorMessageKey(checkListItemUpdateResource, localValidatorFactory));
	}

	/**
	 * Name is required. Expected: {common.not-null}
	 */
	@Test
	public void nameNotNull() {
		CommonUpdateResource checkListItemUpdateResource = setCommonUpdateResource();
		checkListItemUpdateResource.setName(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(checkListItemUpdateResource, localValidatorFactory));
	}

	/**
	 * name cannot be greater than 70 characters. Expected: {common-name.size}
	 */
	@Test
	public void nameSize() {
		CommonUpdateResource checkListItemUpdateResource = setCommonUpdateResource();
		checkListItemUpdateResource.setName(
				"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}",
				TestUtils.getFieldErrorMessageKey(checkListItemUpdateResource, localValidatorFactory));

	}

	/**
	 * status is required. Expected: {common.not-null}
	 */
	@Test
	public void statusNotNull() {
		CommonUpdateResource checkListItemUpdateResource = setCommonUpdateResource();
		checkListItemUpdateResource.setStatus(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(checkListItemUpdateResource, localValidatorFactory));
	}

	/**
	 * Status should be ACTIVE or INACTIVE. Expected: {common-status.pattern}
	 */
	@Test
	public void statusPattern() {
		CommonUpdateResource checkListItemUpdateResource = setCommonUpdateResource();
		checkListItemUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}",
				TestUtils.getFieldErrorMessageKey(checkListItemUpdateResource, localValidatorFactory));
	}

	/**
	 * description cannot be greater than 350 characters. Expected:
	 * {common-description.size}
	 */
	@Test
	public void descriptionSize() {
		CommonUpdateResource checkListItemUpdateResource = setCommonUpdateResource();
		checkListItemUpdateResource
				.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{common-description.size}",
				TestUtils.getFieldErrorMessageKey(checkListItemUpdateResource, localValidatorFactory));

	}

	/**
	 * version is required. Expected: {{common.not-null}
	 */
	@Test
	public void versionNotNull() {
		CommonUpdateResource checkListItemUpdateResource = setCommonUpdateResource();
		checkListItemUpdateResource.setVersion(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(checkListItemUpdateResource, localValidatorFactory));
	}

	/**
	 * version should be in "^$|[0-9]+" pattern Expected: {common-numeric.pattern}
	 */
	@Test
	public void versionPattern() {
		CommonUpdateResource checkListItemUpdateResource = setCommonUpdateResource();
		checkListItemUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}",
				TestUtils.getFieldErrorMessageKey(checkListItemUpdateResource, localValidatorFactory));
	}

}
