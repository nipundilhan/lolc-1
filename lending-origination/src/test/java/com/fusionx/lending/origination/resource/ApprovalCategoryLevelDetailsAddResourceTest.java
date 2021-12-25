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
 * Approval Category Level Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-09-2021    		 	 FXL-840 	Dilki        Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApprovalCategoryLevelDetailsAddResourceTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	private ApprovalCategoryLevelDetailsAddResource setApprovalCategoryLevelDetailsAddResource() {
		ApprovalCategoryLevelDetailsAddResource approvalCategoryLevelDetailsAddResource = new ApprovalCategoryLevelDetailsAddResource();
		approvalCategoryLevelDetailsAddResource.setCode("RF01");
		approvalCategoryLevelDetailsAddResource.setName("Test");
		approvalCategoryLevelDetailsAddResource.setMandatory("YES");
		approvalCategoryLevelDetailsAddResource.setStatus("ACTIVE");
		approvalCategoryLevelDetailsAddResource.setSequence("1");
		return approvalCategoryLevelDetailsAddResource;
	}

	/**
	 * code is required. Expected: {common.not-null}
	 */
	@Test
	public void codeNotNull() {
		ApprovalCategoryLevelDetailsAddResource approvalCategoryLevelDetailsAddResource = setApprovalCategoryLevelDetailsAddResource();
		approvalCategoryLevelDetailsAddResource.setCode(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelDetailsAddResource, localValidatorFactory));
	}

	/**
	 * code cannot be greater than or less than 4 characters. Expected:
	 * {common-code.size}
	 */
	@Test
	public void codeSize() {
		ApprovalCategoryLevelDetailsAddResource approvalCategoryLevelDetailsAddResource = setApprovalCategoryLevelDetailsAddResource();
		approvalCategoryLevelDetailsAddResource.setCode("ABCDEF");
		assertEquals("{common-code.size}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelDetailsAddResource, localValidatorFactory));
	}

	/**
	 * code should be in "^$|^[a-zA-Z0-9]+$" pattern Expected: {common.code-pattern}
	 */
	@Test
	public void codePattern() {
		ApprovalCategoryLevelDetailsAddResource approvalCategoryLevelDetailsAddResource = setApprovalCategoryLevelDetailsAddResource();
		approvalCategoryLevelDetailsAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelDetailsAddResource, localValidatorFactory));
	}

	/**
	 * Name is required. Expected: {common.not-null}
	 */
	@Test
	public void nameNotNull() {
		ApprovalCategoryLevelDetailsAddResource approvalCategoryLevelDetailsAddResource = setApprovalCategoryLevelDetailsAddResource();
		approvalCategoryLevelDetailsAddResource.setName(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelDetailsAddResource, localValidatorFactory));
	}

	/**
	 * name cannot be greater than 70 characters. Expected: {common-name.size}
	 */
	@Test
	public void nameSize() {
		ApprovalCategoryLevelDetailsAddResource approvalCategoryLevelDetailsAddResource = setApprovalCategoryLevelDetailsAddResource();
		approvalCategoryLevelDetailsAddResource.setName(
				"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelDetailsAddResource, localValidatorFactory));

	}

	/**
	 * status is required. Expected: {common.not-null}
	 */
	@Test
	public void statusNotNull() {
		ApprovalCategoryLevelDetailsAddResource approvalCategoryLevelDetailsAddResource = setApprovalCategoryLevelDetailsAddResource();
		approvalCategoryLevelDetailsAddResource.setStatus(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelDetailsAddResource, localValidatorFactory));
	}

	/**
	 * Status should be ACTIVE or INACTIVE. Expected: {common-status.pattern}
	 */
	@Test
	public void statusPattern() {
		ApprovalCategoryLevelDetailsAddResource approvalCategoryLevelDetailsAddResource = setApprovalCategoryLevelDetailsAddResource();
		approvalCategoryLevelDetailsAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelDetailsAddResource, localValidatorFactory));
	}

	/**
	 * sequence is required. Expected: {common.not-null}
	 */
	@Test
	public void sequenceNotNull() {
		ApprovalCategoryLevelDetailsAddResource approvalCategoryLevelDetailsAddResource = setApprovalCategoryLevelDetailsAddResource();
		approvalCategoryLevelDetailsAddResource.setSequence(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelDetailsAddResource, localValidatorFactory));
	}

	/**
	 * sequence should be in "^$|[0-9]+" pattern. Expected: {common-numeric.pattern}
	 */
	@Test
	public void sequencePattern() {
		ApprovalCategoryLevelDetailsAddResource approvalCategoryLevelDetailsAddResource = setApprovalCategoryLevelDetailsAddResource();
		approvalCategoryLevelDetailsAddResource.setSequence("A");
		assertEquals("{common-numeric.pattern}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelDetailsAddResource, localValidatorFactory));
	}

}
