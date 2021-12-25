package com.fusionx.lending.product.resources;
/**
 * Residency Eligibility Notes service
 * @author 	Rangana
 * @Dat     30-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-06-2021   FX-6       FX-6819     Rangana      Created
 *    
 ********************************************************************************************************
 */

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

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class UpdateNotesRequestTest {
	/*
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
    public void setUp() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }
	
	
	private UpdateNotesRequest setUpdateNotesRequest() {
		UpdateNotesRequest updateNotesRequest = new UpdateNotesRequest();
		updateNotesRequest.setNotes("NOTE");
		updateNotesRequest.setStatus("ACTIVE");
		updateNotesRequest.setVersion("1");
		return updateNotesRequest;
	}
	
	/**
     * Note cannot be blank.
     * Expected: {notes.not-null}
     */
 /*   @Test
	public void notesNotNull() {
    	UpdateNotesRequest updateNotesRequest = setUpdateNotesRequest();
    	updateNotesRequest.setNotes(null);
		assertEquals("{notes.not-null}", TestUtils.getFieldErrorMessageKey(updateNotesRequest, localValidatorFactory));
	}
    
	/**
     * Notes cannot be greater than 2000 characters.
     * Expected: {note.size}
     */
/*    @Test
	public void notesSize() {
    	UpdateNotesRequest updateNotesRequest = setUpdateNotesRequest();
    	updateNotesRequest.setNotes("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"+
    	                            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"+
    	                            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"+
    	                            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"+
    	                            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"+
    	                            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"+
    	                            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"+
    	                            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"+
    	                            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"+
    	                            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"+
    	                            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{note.size}", TestUtils.getFieldErrorMessageKey(updateNotesRequest, localValidatorFactory));
	}
	
	/**
     * Status is required.
     * Expected: {status.not-null}
     */
 /*   @Test
	public void statusNotNull() {
    	UpdateNotesRequest updateNotesRequest = setUpdateNotesRequest();
    	updateNotesRequest.setStatus(null);
		assertEquals("{status.not-null}", TestUtils.getFieldErrorMessageKey(updateNotesRequest, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {status.pattern}
     */
 /*   @Test
	public void statusPattern() {
    	UpdateNotesRequest updateNotesRequest = setUpdateNotesRequest();
    	updateNotesRequest.setStatus("ABCDEF");
		assertEquals("{status.pattern}", TestUtils.getFieldErrorMessageKey(updateNotesRequest, localValidatorFactory));
	}
	
	/**
     * Modified user is required.
     * Expected: {modifiedUser.not-null}
     */
    /*@Test
	public void modifiedUserNotNull() {
    	UpdateNotesRequest updateNotesRequest = setUpdateNotesRequest();
    	updateNotesRequest.setModifiedUser(null);
		assertEquals("{modifiedUser.not-null}", TestUtils.getFieldErrorMessageKey(updateNotesRequest, localValidatorFactory));
	}*/
    
	/**
     * Version is required.
     * Expected: {version.not-null}
     */
 /*   @Test
	public void versionNotNull() {
    	UpdateNotesRequest updateNotesRequest = setUpdateNotesRequest();
    	updateNotesRequest.setVersion(null);
		assertEquals("{version.not-null}", TestUtils.getFieldErrorMessageKey(updateNotesRequest, localValidatorFactory));
	}
    
	/**
     * Invalid version.
     * Expected: {version.pattern}
     */
/*    @Test
	public void versionPattern() {
    	UpdateNotesRequest updateNotesRequest = setUpdateNotesRequest();
    	updateNotesRequest.setVersion("ABCDE");
		assertEquals("{version.pattern}", TestUtils.getFieldErrorMessageKey(updateNotesRequest, localValidatorFactory));
	}
*/
}
