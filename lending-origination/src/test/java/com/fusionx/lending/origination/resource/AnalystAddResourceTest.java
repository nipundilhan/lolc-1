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
 * 	Analyst Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-08-2021   FXL-117  	 FXL-654       Piyumi     Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnalystAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private AnalystAddResource setAnalystAddResource() {
		AnalystAddResource analystAddResource = new AnalystAddResource();
		analystAddResource.setAnalystType("INTERNAL");
		analystAddResource.setAnalystUserId("1");
		analystAddResource.setAnalystUserName("Piyumi");
		analystAddResource.setLeadId("1");
		analystAddResource.setObservation("Test");
		analystAddResource.setStatus("ACTIVE");
		return analystAddResource;
	}
	
	/**
     * analystType is required.
     * Expected: {common.not-null}
     */
    @Test
	public void analystTypeNotNull() {
    	AnalystAddResource analystAddResource =setAnalystAddResource();
    	analystAddResource.setAnalystType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(analystAddResource, localValidatorFactory));
	}
    
	/**
     * analystType should be INTERNAL or EXTERNAL
     * Expected: {analyst-type.pattern}
     */
    @Test
	public void analystTypePattern() {
    	AnalystAddResource analystAddResource =setAnalystAddResource();
    	analystAddResource.setAnalystType("ABCDEF");
		assertEquals("{analyst-type.pattern}", TestUtils.getFieldErrorMessageKey(analystAddResource, localValidatorFactory));
	}
	
	 /**
     * analystUserId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void otherIncomeCategoryIdNotNull() {
    	AnalystAddResource analystAddResource = setAnalystAddResource();
    	analystAddResource.setAnalystUserId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(analystAddResource, localValidatorFactory));
	}
    
    /**
     *  analystUserName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void analystUserNameNotNull() {
    	AnalystAddResource analystAddResource = setAnalystAddResource();
    	analystAddResource.setAnalystUserName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(analystAddResource, localValidatorFactory));
	}
    
    /**
     * analystUserName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void analystUserNameSize() {
    	AnalystAddResource analystAddResource =setAnalystAddResource();
    	analystAddResource.setAnalystUserName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(analystAddResource, localValidatorFactory));
		
    }
    
	 /**
     * leadId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void leadIdNotNull() {
    	AnalystAddResource analystAddResource = setAnalystAddResource();
    	analystAddResource.setLeadId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(analystAddResource, localValidatorFactory));
	}
    
    /**
     * leadId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void leadIdPattern() {
    	AnalystAddResource analystAddResource =setAnalystAddResource();
    	analystAddResource.setLeadId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(analystAddResource, localValidatorFactory));
	}
	
	
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	AnalystAddResource analystAddResource =setAnalystAddResource();
    	analystAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(analystAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	AnalystAddResource analystAddResource =setAnalystAddResource();
    	analystAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(analystAddResource, localValidatorFactory));
	}
    
    /**
     * observation is required.
     * Expected: {common.not-null}
     */
    @Test
	public void observationNotNull() {
    	AnalystAddResource analystAddResource =setAnalystAddResource();
    	analystAddResource.setObservation(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(analystAddResource, localValidatorFactory));
	}
    
    /**
     * observation cannot be greater than 1500 characters.
     * Expected: {observation.size}
     */
    @Test
    public void observationSize() {
    	AnalystAddResource analystAddResource =setAnalystAddResource();
    	analystAddResource.setObservation("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{observation.size}", TestUtils.getFieldErrorMessageKey(analystAddResource, localValidatorFactory));
		
    }
    
   
}
