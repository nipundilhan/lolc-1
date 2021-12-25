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
 * 	Document Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021   FXL-115  	 FXL-658       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DocumentAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
	    localValidatorFactory.setProviderClass(HibernateValidator.class);
	    localValidatorFactory.afterPropertiesSet();
	}
	
	private DocumentAddResource setDocumentAddResource() {
		DocumentAddResource documentAddResource = new DocumentAddResource();
		documentAddResource.setDocumentId("1");
		documentAddResource.setDocumentName("Test");
		documentAddResource.setStatus("ACTIVE");
		return documentAddResource;
	}
	
	 /**
	 * documentId is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void otherIncomeCategoryIdNotNull() {
		DocumentAddResource documentAddResource = setDocumentAddResource();
		documentAddResource.setDocumentId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(documentAddResource, localValidatorFactory));
	}
	
	/**
	 * documentId should be in "^$|[0-9]+" pattern
	 * Expected: {common-numeric.pattern}
	 */
	@Test
	public void documentIdPattern() {
		DocumentAddResource documentAddResource =setDocumentAddResource();
		documentAddResource.setDocumentId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(documentAddResource, localValidatorFactory));
	}
	
	/**
	 *  documentName is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void documentNameNotNull() {
		DocumentAddResource documentAddResource = setDocumentAddResource();
		documentAddResource.setDocumentName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(documentAddResource, localValidatorFactory));
	}
	
	/**
	 * documentName cannot be greater than 70 characters.
	 * Expected: {common-name.size}
	 */
	@Test
	public void documentNameSize() {
		DocumentAddResource documentAddResource =setDocumentAddResource();
		documentAddResource.setDocumentName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(documentAddResource, localValidatorFactory));
		
	}
	/**
	 * status is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void statusNotNull() {
		DocumentAddResource documentAddResource =setDocumentAddResource();
		documentAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(documentAddResource, localValidatorFactory));
	}
	
	/**
	 * Status should be ACTIVE or INACTIVE.
	 * Expected: {common-status.pattern}
	 */
	@Test
	public void statusPattern() {
		DocumentAddResource documentAddResource =setDocumentAddResource();
		documentAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(documentAddResource, localValidatorFactory));
	}
	

}
