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
 * 	Document Update Resource Test
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
public class DocumentUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private DocumentUpdateResource setDocumentUpdateResource() {
		DocumentUpdateResource documentUpdateResource = new DocumentUpdateResource();
		documentUpdateResource.setId("1");
		documentUpdateResource.setDocumentId("1");
		documentUpdateResource.setDocumentName("Test");
		documentUpdateResource.setVersion("0");
		documentUpdateResource.setStatus("ACTIVE");
		return documentUpdateResource;
	}
	
	/**
	 * Id should be in "^$|[0-9]+" pattern
	 * Expected: {common-numeric.pattern}
	 */
	@Test
	public void idPattern() {
		DocumentUpdateResource documentUpdateResource =setDocumentUpdateResource();
		documentUpdateResource.setId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(documentUpdateResource, localValidatorFactory));
	}
	
	 /**
	 * documentId is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void otherIncomeCategoryIdNotNull() {
		DocumentUpdateResource documentUpdateResource = setDocumentUpdateResource();
		documentUpdateResource.setDocumentId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(documentUpdateResource, localValidatorFactory));
	}
	
	/**
	 * documentId should be in "^$|[0-9]+" pattern
	 * Expected: {common-numeric.pattern}
	 */
	@Test
	public void documentIdPattern() {
		DocumentUpdateResource documentUpdateResource =setDocumentUpdateResource();
		documentUpdateResource.setDocumentId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(documentUpdateResource, localValidatorFactory));
	}
	
	/**
	 *  documentName is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void documentNameNotNull() {
		DocumentUpdateResource documentUpdateResource = setDocumentUpdateResource();
		documentUpdateResource.setDocumentName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(documentUpdateResource, localValidatorFactory));
	}
	
	/**
	 * documentName cannot be greater than 70 characters.
	 * Expected: {common-name.size}
	 */
	@Test
	public void documentNameSize() {
		DocumentUpdateResource documentUpdateResource =setDocumentUpdateResource();
		documentUpdateResource.setDocumentName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(documentUpdateResource, localValidatorFactory));
		
	}
	/**
	 * status is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void statusNotNull() {
		DocumentUpdateResource documentUpdateResource =setDocumentUpdateResource();
		documentUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(documentUpdateResource, localValidatorFactory));
	}
	
	/**
	 * Status should be ACTIVE or INACTIVE.
	 * Expected: {common-status.pattern}
	 */
	@Test
	public void statusPattern() {
		DocumentUpdateResource documentUpdateResource =setDocumentUpdateResource();
		documentUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(documentUpdateResource, localValidatorFactory));
	}
	
	/**
	 * version should be in "^$|[0-9]+" pattern
	 * Expected: {common-numeric.pattern}
	 */
	@Test
	public void versionPattern() {
		DocumentUpdateResource documentUpdateResource =setDocumentUpdateResource();
		documentUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(documentUpdateResource, localValidatorFactory));
	}
    
   
}
