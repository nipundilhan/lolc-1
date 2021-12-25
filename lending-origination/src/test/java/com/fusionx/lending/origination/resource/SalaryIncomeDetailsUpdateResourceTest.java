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
public class SalaryIncomeDetailsUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private SalaryIncomeDetailsUpdateResource setSalaryIncomeDetailsUpdateResource() {
		SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource = new SalaryIncomeDetailsUpdateResource();
		salaryIncomeDetailsUpdateResource.setEmploymentType("CURRENT");
		salaryIncomeDetailsUpdateResource.setEmployerId("1");
		salaryIncomeDetailsUpdateResource.setEmployerName("test");
		salaryIncomeDetailsUpdateResource.setExperience("1");
		salaryIncomeDetailsUpdateResource.setDesignationId("1");
		salaryIncomeDetailsUpdateResource.setDesignationName("test");
		salaryIncomeDetailsUpdateResource.setJobTypeId("1");
		salaryIncomeDetailsUpdateResource.setJobTypeDesc("test");
		salaryIncomeDetailsUpdateResource.setSourceType("PRIMARY");
		salaryIncomeDetailsUpdateResource.setStatus("ACTIVE");
		salaryIncomeDetailsUpdateResource.setVersion("1");
		return salaryIncomeDetailsUpdateResource;
	}
	
	/**
     * employmentType is required.
     * Expected: {common.not-null}
     */
    @Test
	public void employmentTypeNotNull() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setEmploymentType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
	/**
     * employmentType should be PAST|CURRENT.
     * Expected: {common-employment-type.pattern}
     */
    @Test
	public void employmentTypePattern() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setEmploymentType("ABCDEF");
		assertEquals("{common-employment-type.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
	
	/**
     * employerId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void employerIdPattern() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setEmployerId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * employerName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void employerNameNotNull() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setEmployerName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
   	 * employerName cannot be greater than 70 characters.
   	 * Expected: {common-name.size}
   	 */
   	@Test
   	public void employerNameSize() {
   		SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
   		salaryIncomeDetailsUpdateResource.setEmployerName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
   		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
   		
   	}
   	
   	/**
     * experience is required.
     * Expected: {common.not-null}
     */
    @Test
	public void experienceNotNull() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setExperience(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * experience should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void experiencePattern() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setExperience("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * designationId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void designationIdNotNull() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setDesignationId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
	
    /**
     * designationId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void designationIdPattern() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setDesignationId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * designationName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void designationNameNotNull() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setDesignationName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
	 * designationName cannot be greater than 70 characters.
	 * Expected: {common-name.size}
	 */
	@Test
	public void designationNameSize() {
		SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
		salaryIncomeDetailsUpdateResource.setDesignationName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
		
	}
	
	 /**
     * jobTypeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void jobTypeIdNotNull() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setJobTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
	
    /**
     * jobTypeId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void jobTypeIdPattern() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setJobTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * jobTypeDesc is required.
     * Expected: {common.not-null}
     */
    @Test
	public void jobTypeDescNotNull() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setJobTypeDesc(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
	 * jobTypeDesc cannot be greater than 70 characters.
	 * Expected: {common-name.size}
	 */
	@Test
	public void jobTypeDescSize() {
		SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
		salaryIncomeDetailsUpdateResource.setJobTypeDesc("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
		
	}
	
	/**
     * sourceType is required.
     * Expected: {common.not-null}
     */
    @Test
	public void sourceTypeNotNull() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setSourceType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
	/**
     * sourceType should be PRIMARY or SECONDARY.
     * Expected: {common-source.pattern}
     */
    @Test
	public void sourceTypePattern() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setSourceType("ABCDEF");
		assertEquals("{common-source.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
	
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * version is required.
     * Expected: {common.not-null}
     */
    @Test
	public void versionNotNull() {
    	SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
    	salaryIncomeDetailsUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
	 * version should be in "^$|[0-9]+" pattern
	 * Expected: {common-numeric.pattern}
	 */
	@Test
	public void versionPattern() {
		SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource =setSalaryIncomeDetailsUpdateResource();
		salaryIncomeDetailsUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsUpdateResource, localValidatorFactory));
	}
    
    
   
}
