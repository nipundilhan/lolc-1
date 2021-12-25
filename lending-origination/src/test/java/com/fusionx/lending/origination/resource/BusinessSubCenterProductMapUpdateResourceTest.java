package com.fusionx.lending.origination.resource;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
public class BusinessSubCenterProductMapUpdateResourceTest {

private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private BusinessSubCenterProductMapUpdateResource setBusinessSubCenterProductMapAddResource() {
		BusinessSubCenterProductMapUpdateResource businessSubCenterProductMapAddResource = new BusinessSubCenterProductMapUpdateResource();
		
		businessSubCenterProductMapAddResource.setVersion("1");
		
		List<ProductRequestResource> productList =  new ArrayList<>();
		ProductRequestResource productRequestResource = new ProductRequestResource();
		productRequestResource.setId("1");
		productRequestResource.setProductId("1");
		productRequestResource.setProductName("name");
		productRequestResource.setProductCode("PR01");
		productRequestResource.setStatus("ACTIVE");
		
		productList.add(productRequestResource);
		return businessSubCenterProductMapAddResource;
	}
	
	
	
    @Test
	public void versionNotNull() {
    	BusinessSubCenterProductMapUpdateResource businessSubCenterProductMapUpdateResource = setBusinessSubCenterProductMapAddResource();
    	businessSubCenterProductMapUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterProductMapUpdateResource, localValidatorFactory));
	}
    
    /**
     * businessTypeId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void  versionPattern() {
    	BusinessSubCenterProductMapUpdateResource businessSubCenterProductMapUpdateResource = setBusinessSubCenterProductMapAddResource();
    	businessSubCenterProductMapUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessSubCenterProductMapUpdateResource, localValidatorFactory));
	}
}

