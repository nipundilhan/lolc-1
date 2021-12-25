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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessSubCenterUpdateResourceTest {


private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private BusinessSubCenterUpdateResource setBusinessSubCenterUpdateResource() {
		BusinessSubCenterUpdateResource businessSubCenterAddResource = new BusinessSubCenterUpdateResource();
		businessSubCenterAddResource.setVersion("0");
		businessSubCenterAddResource.setBusinessCenterId("1");
		businessSubCenterAddResource.setBusinessCenterName("name");
		businessSubCenterAddResource.setCode("LB01");
		businessSubCenterAddResource.setName("name");
		businessSubCenterAddResource.setEmpId("10");
		businessSubCenterAddResource.setEmpNo("20EMP");
		businessSubCenterAddResource.setEmpName("EMPNAME");
		businessSubCenterAddResource.setMaxClientPerSubCenter("20");
		businessSubCenterAddResource.setMaxSubCenterLimit("1000");
		businessSubCenterAddResource.setStatus("ACTIVE");		
		return businessSubCenterAddResource;
	}
	
    
/**
 * version is required.
 * Expected: {common.not-null}
 */
@Test
public void versionNotNull() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setVersion(null);
	assertEquals("{version.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}

/**
 * version should be in "^$|[0-9]+" pattern
 * Expected: {common-numeric.pattern}
 */
@Test
public void versionPattern() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setVersion("A");
	assertEquals("{version.pattern}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}







/**
* businessTypeId is required.
* Expected: {common.not-null}
*/
@Test
public void businessCenterIdNotNull() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setBusinessCenterId(null);
	assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}

/**
* businessTypeId Pattern is "^$|[0-9]+".
* Expected: {common-numeric.pattern}
*/
@Test
public void  businessCenterIdPattern() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setBusinessCenterId("A");
	assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}

/**
*  businessTypename is required.
* Expected: {common.not-null}
*/
@Test
public void businessCenterNameNotNull() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setBusinessCenterName(null);
	assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}



/**
* code is required.
* Expected: {code.not-null}
*/
@Test
public void codeNotNull() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setCode(null);
	assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}

/**
* code cannot be greater than or less than 4 characters.
* Expected: {common.code.size}
*/
@Test
public void codeSize() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setCode("ABCDEF");
	assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}

/**
* code should be in "^$|^[a-zA-Z0-9]+$" pattern
* Expected: {code.pattern}
*/
@Test
public void codePattern() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setCode("ABC!");
	assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}

/**
*  Name is required.
* Expected: {name.not-null}
*/
@Test
public void nameNotNull() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setName(null);
	assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}

/**
* name cannot be greater than 70 characters.
* Expected: {common-name.size}
*/
@Test
public void nameSize() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
	assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	
}

/**
* status is required.
* Expected: {status.not-null}
*/
@Test
public void statusNotNull() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setStatus(null);
	assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}

/**
* Status should be ACTIVE or INACTIVE.
* Expected: {value.pattern}
*/
@Test
public void statusPattern() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setStatus("ABCDEF");
	assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}


@Test
public void maxClientPerSubCenterNotNull() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setMaxClientPerSubCenter(null);
	assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}

/**
* businessTypeId Pattern is "^$|[0-9]+".
* Expected: {common-numeric.pattern}
*/
@Test
public void  maxClientPerSubCenterIdPattern() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setMaxClientPerSubCenter("A");
	assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}



@Test
public void maxSubCenterLimitNotNull() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setMaxSubCenterLimit(null);
	assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}

/**
* businessTypeId Pattern is "^$|[0-9]+".
* Expected: {common-numeric.pattern}
*/
@Test
public void  maxSubCenterLimitPattern() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setMaxSubCenterLimit("A");
	assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}


@Test
public void empIdNotNull() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setEmpId(null);
	assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}



@Test
public void empNameNotNull() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setEmpName(null);
	assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}

@Test
public void empNoNotNull() {
	BusinessSubCenterUpdateResource businessSubCenterAddResource = setBusinessSubCenterUpdateResource();
	businessSubCenterAddResource.setEmpNo(null);
	assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
}
	
}
