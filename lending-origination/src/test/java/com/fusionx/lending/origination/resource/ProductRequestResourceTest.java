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
 * Other Income Type Update Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   2021-08-31   			      FXL-650   Nipun       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductRequestResourceTest {
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private ProductRequestResource setProductRequestResource() {
		ProductRequestResource productRequestResource = new ProductRequestResource();
		productRequestResource.setId("1");
		productRequestResource.setProductId("1");
		productRequestResource.setProductName("name");
		productRequestResource.setProductCode("PR01");
		productRequestResource.setStatus("ACTIVE");
		return productRequestResource;
	}
	
	
    @Test
    public void idPattern() {
    	ProductRequestResource productRequestResource = setProductRequestResource();
    	productRequestResource.setId("ABC");
		assertEquals("{common.invalid-number-format}", TestUtils.getFieldErrorMessageKey(productRequestResource, localValidatorFactory));
    }
    
    @Test
    public void productIdPattern() {
    	ProductRequestResource productRequestResource = setProductRequestResource();
    	productRequestResource.setProductId("ABC");
		assertEquals("{common.invalid-number-format}", TestUtils.getFieldErrorMessageKey(productRequestResource, localValidatorFactory));
    }

	@Test
    public void codeNotNull() {
    	ProductRequestResource productRequestResource = setProductRequestResource();
    	productRequestResource.setProductCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(productRequestResource, localValidatorFactory));
    }
	

	@Test
    public void nameNotNull() {
    	ProductRequestResource productRequestResource = setProductRequestResource();
    	productRequestResource.setProductName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(productRequestResource, localValidatorFactory));
    }
	
	@Test
    public void statusNotNull() {
    	ProductRequestResource productRequestResource = setProductRequestResource();
    	productRequestResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(productRequestResource, localValidatorFactory));
    }
	
    @Test
	public void statusPattern() {
    	ProductRequestResource productRequestResource = setProductRequestResource();
    	productRequestResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(productRequestResource, localValidatorFactory));
	}
}

