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
 * 	Salary Income Details List Resource Test
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
public class SalaryIncomeDetailsListResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private SalaryIncomeDetailsListResource setSalaryIncomeDetailsListResource() {
		SalaryIncomeDetailsListResource salaryIncomeDetailsListResource = new SalaryIncomeDetailsListResource();
		salaryIncomeDetailsListResource.setEmploymentType("CURRENT");
		salaryIncomeDetailsListResource.setEmployerId("1");
		salaryIncomeDetailsListResource.setEmployerName("test");
		salaryIncomeDetailsListResource.setExperience("1");
		salaryIncomeDetailsListResource.setDesignationId("1");
		salaryIncomeDetailsListResource.setDesignationName("test");
		salaryIncomeDetailsListResource.setJobTypeId("1");
		salaryIncomeDetailsListResource.setJobTypeDesc("test");
		salaryIncomeDetailsListResource.setSourceType("PRIMARY");
		salaryIncomeDetailsListResource.setStatus("ACTIVE");
		return salaryIncomeDetailsListResource;
	}
	
	/**
     * employmentType is required.
     * Expected: {common.not-null}
     */
    @Test
	public void employmentTypeNotNull() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setEmploymentType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
    
	/**
     * employmentType should be PAST|CURRENT.
     * Expected: {common-employment-type.pattern}
     */
    @Test
	public void employmentTypePattern() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setEmploymentType("ABCDEF");
		assertEquals("{common-employment-type.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
	
	/**
     * employerId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void employerIdPattern() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setEmployerId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
     * employerName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void employerNameNotNull() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setEmployerName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
   	 * employerName cannot be greater than 70 characters.
   	 * Expected: {common-name.size}
   	 */
   	@Test
   	public void employerNameSize() {
   		SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
   		salaryIncomeDetailsListResource.setEmployerName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
   		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
   		
   	}
   	
   	/**
     * experience is required.
     * Expected: {common.not-null}
     */
    @Test
	public void experienceNotNull() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setExperience(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
     * experience should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void experiencePattern() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setExperience("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
     * designationId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void designationIdNotNull() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setDesignationId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
	
    /**
     * designationId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void designationIdPattern() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setDesignationId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
     * designationName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void designationNameNotNull() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setDesignationName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
	 * designationName cannot be greater than 70 characters.
	 * Expected: {common-name.size}
	 */
	@Test
	public void designationNameSize() {
		SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
		salaryIncomeDetailsListResource.setDesignationName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
		
	}
	
	 /**
     * jobTypeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void jobTypeIdNotNull() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setJobTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
	
    /**
     * jobTypeId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void jobTypeIdPattern() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setJobTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
     * jobTypeDesc is required.
     * Expected: {common.not-null}
     */
    @Test
	public void jobTypeDescNotNull() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setJobTypeDesc(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
	 * jobTypeDesc cannot be greater than 70 characters.
	 * Expected: {common-name.size}
	 */
	@Test
	public void jobTypeDescSize() {
		SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
		salaryIncomeDetailsListResource.setJobTypeDesc("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
		
	}
	
	/**
     * sourceType is required.
     * Expected: {common.not-null}
     */
    @Test
	public void sourceTypeNotNull() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setSourceType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
    
	/**
     * sourceType should be PRIMARY or SECONDARY.
     * Expected: {common-source.pattern}
     */
    @Test
	public void sourceTypePattern() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setSourceType("ABCDEF");
		assertEquals("{common-source.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
	
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	SalaryIncomeDetailsListResource salaryIncomeDetailsListResource =setSalaryIncomeDetailsListResource();
    	salaryIncomeDetailsListResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsListResource, localValidatorFactory));
	}
    
    
   
}
