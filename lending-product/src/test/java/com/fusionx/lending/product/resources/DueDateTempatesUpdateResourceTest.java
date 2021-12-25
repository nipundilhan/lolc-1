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
 * Common update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021   FXL-1          FXL-3      Piyumi       Created
 *    
 ********************************************************************************************************
 */

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class DueDateTempatesUpdateResourceTest {
/*	
private LocalValidatorFactoryBean localValidatorFactory;
	

		@Before
		public void setUp() {
			localValidatorFactory = new LocalValidatorFactoryBean();
		    localValidatorFactory.setProviderClass(HibernateValidator.class);
		    localValidatorFactory.afterPropertiesSet();
		}
		
		private DueDateTemplatesUpdateResource setDueDateTemplatesUpdateResource() {
			DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource = new DueDateTemplatesUpdateResource();
			dueDateTemplatesUpdateResource.setCode("RF01");
			dueDateTemplatesUpdateResource.setName("Test");
			dueDateTemplatesUpdateResource.setEffectiveDate("1990-01-01");
			dueDateTemplatesUpdateResource.setDueDateType("START");
			dueDateTemplatesUpdateResource.setStatus("ACTIVE");
			dueDateTemplatesUpdateResource.setVersion("1");
			return dueDateTemplatesUpdateResource;
		}
		
		 /**
		 * code is required.
		 * Expected: {common.not-null}
		 */
/*		@Test
/*		public void codeNotNull() {
			DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource = setDueDateTemplatesUpdateResource();
			dueDateTemplatesUpdateResource.setCode(null);
			assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesUpdateResource, localValidatorFactory));
		}
		
		/**
		 * code cannot be greater than or less than 4 characters.
		 * Expected: {common-code.size}
		 */
/*		@Test
/*		public void codeSize() {
			DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource = setDueDateTemplatesUpdateResource();
			dueDateTemplatesUpdateResource.setCode("ABCDEF");
			assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesUpdateResource, localValidatorFactory));
		}
		
		/**
		 * code should be in "^$|^[a-zA-Z0-9]+$" pattern
		 * Expected: {common.code-pattern}
		 */
/*		@Test
		public void codePattern() {
			DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource =setDueDateTemplatesUpdateResource();
			dueDateTemplatesUpdateResource.setCode("ABC!");
			assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesUpdateResource, localValidatorFactory));
		}
		
		 /**
		 *  Name is required.
		 * Expected: {common.not-null}
		 */
/*		@Test
		public void nameNotNull() {
			DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource = setDueDateTemplatesUpdateResource();
			dueDateTemplatesUpdateResource.setName(null);
			assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesUpdateResource, localValidatorFactory));
		}
		
		/**
		 * name cannot be greater than 70 characters.
		 * Expected: {common-name.size}
		 */
/*		@Test
		public void nameSize() {
			DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource =setDueDateTemplatesUpdateResource();
			dueDateTemplatesUpdateResource.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
			assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesUpdateResource, localValidatorFactory));
			
		}
		
		/**
		 * status is required.
		 * Expected: {common.not-null}
		 */
/*		@Test
		public void statusNotNull() {
			DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource =setDueDateTemplatesUpdateResource();
			dueDateTemplatesUpdateResource.setStatus(null);
			assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesUpdateResource, localValidatorFactory));
		}
		
		/**
		 * Status should be ACTIVE or INACTIVE.
		 * Expected: {common-status.pattern}
		 */
/*		@Test
		public void statusPattern() {
			DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource =setDueDateTemplatesUpdateResource();
			dueDateTemplatesUpdateResource.setStatus("ABCDEF");
			assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesUpdateResource, localValidatorFactory));
		}
		
		/**
		 * effectiveDate is required.
		 * Expected: {common.not-null}
		 */
/*		@Test
		public void effectiveDateNotNull() {
			DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource =setDueDateTemplatesUpdateResource();
			dueDateTemplatesUpdateResource.setEffectiveDate(null);
			assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesUpdateResource, localValidatorFactory));
		}
		
		/**
		 * effectiveDate should be "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" pattern.
		 * Expected: {common.invalid-date-pattern}
		 */
/*		@Test
		public void effectiveDatePattern() {
			DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource =setDueDateTemplatesUpdateResource();
			dueDateTemplatesUpdateResource.setEffectiveDate("1990/SEP/23");
			assertEquals("{common.invalid-date-pattern}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesUpdateResource, localValidatorFactory));
		}
		
		
		/**
		 * dueDateType is required.
		 * Expected: {common.not-null}
		 */
/*		@Test
		public void dueDateTypeNotNull() {
			DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource =setDueDateTemplatesUpdateResource();
			dueDateTemplatesUpdateResource.setDueDateType(null);
			assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesUpdateResource, localValidatorFactory));
		}
		
		/**
		 * dueDateType should be START or END or CUSTOMIZED or MULTIPLE.
		 * Expected: {due-date-type.pattern}
		 */
/*		@Test
		public void dueDateTypePattern() {
			DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource =setDueDateTemplatesUpdateResource();
			dueDateTemplatesUpdateResource.setDueDateType("ABCDEF");
			assertEquals("{due-date-type.pattern}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesUpdateResource, localValidatorFactory));
		}
		

	
	 /**
     * version is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void versionNotNull() {
    	DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource =setDueDateTemplatesUpdateResource();
    	dueDateTemplatesUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesUpdateResource, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {version.pattern}
     */
/*    @Test
	public void versionPattern() {
    	DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource =setDueDateTemplatesUpdateResource();
    	dueDateTemplatesUpdateResource.setVersion("A");
		assertEquals("{version.pattern}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesUpdateResource, localValidatorFactory));
	}
*/
}
