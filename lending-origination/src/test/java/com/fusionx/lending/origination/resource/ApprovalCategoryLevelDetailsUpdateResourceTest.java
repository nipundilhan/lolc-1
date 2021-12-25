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
/**
 * Approval Category Levels
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-09-2021    		 	 FXL-823 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.utill.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApprovalCategoryLevelDetailsUpdateResourceTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	private ApprovalCategoryLevelsUpdateResource setApprovalCategoryLevelsUpdateResource() {
		ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource = new ApprovalCategoryLevelsUpdateResource();
		approvalCategoryLevelsUpdateResource.setCode("RF01");
		approvalCategoryLevelsUpdateResource.setName("Test");
		approvalCategoryLevelsUpdateResource.setDescription("Test");
		approvalCategoryLevelsUpdateResource.setStatus("ACTIVE");
		approvalCategoryLevelsUpdateResource.setSequence("1");
		approvalCategoryLevelsUpdateResource.setVersion("1");
		return approvalCategoryLevelsUpdateResource;
	}

	/**
	 * code is required. Expected: {common.not-null}
	 */
	@Test
	public void codeNotNull() {
		ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource = setApprovalCategoryLevelsUpdateResource();
		approvalCategoryLevelsUpdateResource.setCode(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsUpdateResource, localValidatorFactory));
	}

	/**
	 * code cannot be greater than or less than 4 characters. Expected:
	 * {common-code.size}
	 */
	@Test
	public void codeSize() {
		ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource = setApprovalCategoryLevelsUpdateResource();
		approvalCategoryLevelsUpdateResource.setCode("ABCDEF");
		assertEquals("{common-code.size}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsUpdateResource, localValidatorFactory));
	}

	/**
	 * code should be in "^$|^[a-zA-Z0-9]+$" pattern Expected: {common.code-pattern}
	 */
	@Test
	public void codePattern() {
		ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource = setApprovalCategoryLevelsUpdateResource();
		approvalCategoryLevelsUpdateResource.setCode("ABC!");
		assertEquals("{common.code-pattern}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsUpdateResource, localValidatorFactory));
	}

	/**
	 * Name is required. Expected: {common.not-null}
	 */
	@Test
	public void nameNotNull() {
		ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource = setApprovalCategoryLevelsUpdateResource();
		approvalCategoryLevelsUpdateResource.setName(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsUpdateResource, localValidatorFactory));
	}

	/**
	 * name cannot be greater than 70 characters. Expected: {common-name.size}
	 */
	@Test
	public void nameSize() {
		ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource = setApprovalCategoryLevelsUpdateResource();
		approvalCategoryLevelsUpdateResource.setName(
				"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsUpdateResource, localValidatorFactory));

	}

	/**
	 * status is required. Expected: {common.not-null}
	 */
	@Test
	public void statusNotNull() {
		ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource = setApprovalCategoryLevelsUpdateResource();
		approvalCategoryLevelsUpdateResource.setStatus(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsUpdateResource, localValidatorFactory));
	}

	/**
	 * Status should be ACTIVE or INACTIVE. Expected: {common-status.pattern}
	 */
	@Test
	public void statusPattern() {
		ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource = setApprovalCategoryLevelsUpdateResource();
		approvalCategoryLevelsUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsUpdateResource, localValidatorFactory));
	}

	/**
	 * description cannot be greater than 350 characters. Expected:
	 * {common-description.size}
	 */
	@Test
	public void descriptionSize() {
		ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource = setApprovalCategoryLevelsUpdateResource();
		approvalCategoryLevelsUpdateResource
				.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{common-description.size}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsUpdateResource, localValidatorFactory));

	}

	/**
	 * version is required. Expected: {{common.not-null}
	 */
	@Test
	public void versionNotNull() {
		ApprovalCategoryLevelsUpdateResource commonUpdateResource = setApprovalCategoryLevelsUpdateResource();
		commonUpdateResource.setVersion(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(commonUpdateResource, localValidatorFactory));
	}

	/**
	 * version should be in "^$|[0-9]+" pattern Expected: {common-numeric.pattern}
	 */
	@Test
	public void versionPattern() {
		ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource = setApprovalCategoryLevelsUpdateResource();
		approvalCategoryLevelsUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsUpdateResource, localValidatorFactory));
	}

	/**
	 * sequence is required. Expected: {common.not-null}
	 */
	@Test
	public void sequenceNotNull() {
		ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource = setApprovalCategoryLevelsUpdateResource();
		approvalCategoryLevelsUpdateResource.setSequence(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsUpdateResource, localValidatorFactory));
	}

	/**
	 * sequence should be in "^$|[0-9]+" pattern. Expected: {common-numeric.pattern}
	 */
	@Test
	public void sequencePattern() {
		ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource = setApprovalCategoryLevelsUpdateResource();
		approvalCategoryLevelsUpdateResource.setSequence("A");
		assertEquals("{common-numeric.pattern}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsUpdateResource, localValidatorFactory));
	}

}
