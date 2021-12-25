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
 * Approval Category Levels
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-09-2021    		 	 FXL-823 	Dilki        Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApprovalCategoryLevelAddResourceTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	private ApprovalCategoryLevelsAddResource setApprovalCategoryLevelsAddResource() {
		ApprovalCategoryLevelsAddResource approvalCategoryLevelsAddResource = new ApprovalCategoryLevelsAddResource();
		approvalCategoryLevelsAddResource.setCode("RF01");
		approvalCategoryLevelsAddResource.setName("Test");
		approvalCategoryLevelsAddResource.setDescription("Test");
		approvalCategoryLevelsAddResource.setStatus("ACTIVE");
		approvalCategoryLevelsAddResource.setSequence("1");
		return approvalCategoryLevelsAddResource;
	}

	/**
	 * code is required. Expected: {common.not-null}
	 */
	@Test
	public void codeNotNull() {
		ApprovalCategoryLevelsAddResource approvalCategoryLevelsAddResource = setApprovalCategoryLevelsAddResource();
		approvalCategoryLevelsAddResource.setCode(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsAddResource, localValidatorFactory));
	}

	/**
	 * code cannot be greater than or less than 4 characters. Expected:
	 * {common-code.size}
	 */
	@Test
	public void codeSize() {
		ApprovalCategoryLevelsAddResource approvalCategoryLevelsAddResource = setApprovalCategoryLevelsAddResource();
		approvalCategoryLevelsAddResource.setCode("ABCDEF");
		assertEquals("{common-code.size}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsAddResource, localValidatorFactory));
	}

	/**
	 * code should be in "^$|^[a-zA-Z0-9]+$" pattern Expected:
	 * {common.code-pattern}
	 */
	@Test
	public void codePattern() {
		ApprovalCategoryLevelsAddResource approvalCategoryLevelsAddResource = setApprovalCategoryLevelsAddResource();
		approvalCategoryLevelsAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsAddResource, localValidatorFactory));
	}

	/**
	 * Name is required. Expected: {common.not-null}
	 */
	@Test
	public void nameNotNull() {
		ApprovalCategoryLevelsAddResource approvalCategoryLevelsAddResource = setApprovalCategoryLevelsAddResource();
		approvalCategoryLevelsAddResource.setName(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsAddResource, localValidatorFactory));
	}

	/**
	 * name cannot be greater than 70 characters. Expected:
	 * {common-name.size}
	 */
	@Test
	public void nameSize() {
		ApprovalCategoryLevelsAddResource approvalCategoryLevelsAddResource = setApprovalCategoryLevelsAddResource();
		approvalCategoryLevelsAddResource.setName(
				"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsAddResource, localValidatorFactory));

	}

	/**
	 * status is required. Expected: {common.not-null}
	 */
	@Test
	public void statusNotNull() {
		ApprovalCategoryLevelsAddResource approvalCategoryLevelsAddResource = setApprovalCategoryLevelsAddResource();
		approvalCategoryLevelsAddResource.setStatus(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsAddResource, localValidatorFactory));
	}

	/**
	 * Status should be ACTIVE or INACTIVE. Expected:
	 * {common-status.pattern}
	 */
	@Test
	public void statusPattern() {
		ApprovalCategoryLevelsAddResource approvalCategoryLevelsAddResource = setApprovalCategoryLevelsAddResource();
		approvalCategoryLevelsAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsAddResource, localValidatorFactory));
	}

	/**
	 * description cannot be greater than 350 characters. Expected:
	 * {common-description.size}
	 */
	@Test
	public void descriptionSize() {
		ApprovalCategoryLevelsAddResource approvalCategoryLevelsAddResource = setApprovalCategoryLevelsAddResource();
		approvalCategoryLevelsAddResource
				.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
						+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{common-description.size}",
				TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsAddResource, localValidatorFactory));

	}
	
	/**
     * sequence is required.
     * Expected: {common.not-null}
     */
    @Test
	public void sequenceNotNull() {
    	ApprovalCategoryLevelsAddResource approvalCategoryLevelsAddResource =setApprovalCategoryLevelsAddResource();
    	approvalCategoryLevelsAddResource.setSequence(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsAddResource, localValidatorFactory));
	}
    
    /**
     * sequence should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void sequencePattern() {
    	ApprovalCategoryLevelsAddResource approvalCategoryLevelsAddResource =setApprovalCategoryLevelsAddResource();
    	approvalCategoryLevelsAddResource.setSequence("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(approvalCategoryLevelsAddResource, localValidatorFactory));
	}

}
