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
 * Comman List Usage Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   18-07-2021   				   	 		Nipun_Dilhan      Created
 *    
 ********************************************************************************************************
 */

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class CommonListUsageResourceTest {
/*	
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private CommonListUsageResource setCommonListUsageResource() {
		
		CommonListUsageResource commonListUsageResource = new CommonListUsageResource();
		commonListUsageResource.setId("1");
		commonListUsageResource.setReferenceCode("MSTDEFACCSTND");
		commonListUsageResource.setIsSelected("YES");
		return commonListUsageResource;
		
	}
	
	
	/**
     *id Cannot be null.
     * Expected: {common.not-null}
     */
/*	@Test
    public void idNotNull() {
		CommonListUsageResource commonListUsageResource = setCommonListUsageResource();
		commonListUsageResource.setId(null);;
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(commonListUsageResource, localValidatorFactory));
    }
	
	
	/**
     *id pattern should  be numeric.
     * Expected: {common.not-null}
     */
/*	@Test
    public void idPattern() {
		CommonListUsageResource commonListUsageResource = setCommonListUsageResource();
		commonListUsageResource.setId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(commonListUsageResource, localValidatorFactory));
    }
	
	
	/**
     *reference code Cannot be null.
     * Expected: {common.not-null}
     */
/*	@Test
    public void ReferenceCodeNotNull() {
		CommonListUsageResource commonListUsageResource = setCommonListUsageResource();
		commonListUsageResource.setReferenceCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(commonListUsageResource, localValidatorFactory));
    }
	
	/**
     *isSelected Cannot be null.
     * Expected: {common.not-null}
     */
/*	@Test
    public void isSelectedNotNull() {
		CommonListUsageResource commonListUsageResource = setCommonListUsageResource();
		commonListUsageResource.setIsSelected(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(commonListUsageResource, localValidatorFactory));
    }
	
    /**
     * isSelected should be in "YES|NO" pattern
     * Expected: {common.status-pattern}
     */
/*    @Test
	public void isSelectedPattern() {
		CommonListUsageResource commonListUsageResource = setCommonListUsageResource();
		commonListUsageResource.setIsSelected("ABC");
		assertEquals("{common-enable.pattern}", TestUtils.getFieldErrorMessageKey(commonListUsageResource, localValidatorFactory));
	}
    
    /**
     * referenceCode should be in "MSTDEFACCSTND|MSTDEFSETACC" pattern
     * Expected: {common.status-pattern}
     */
 /*   @Test
	public void isReferenceCodePattern() {
		CommonListUsageResource commonListUsageResource = setCommonListUsageResource();
		commonListUsageResource.setReferenceCode("ABC");
		assertEquals("reference code should be MSTDEFACCSTND or MSTDEFSETACC", TestUtils.getFieldErrorMessageKey(commonListUsageResource, localValidatorFactory));
	}
*/
}
