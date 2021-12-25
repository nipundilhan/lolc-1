package com.fusionx.lending.origination.resource;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 	Business Sub Center Product Map Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   31-08-2021             	 FXL-650       Nipun Dilhan     Created
 *    
 ********************************************************************************************************
*/

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessSubCenterProductMapAddResourceTest {

private LocalValidatorFactoryBean localValidatorFactory;
	
//	@Before
//	public void setUp() {
//		localValidatorFactory = new LocalValidatorFactoryBean();
//        localValidatorFactory.setProviderClass(HibernateValidator.class);
//        localValidatorFactory.afterPropertiesSet();
//	}
//	
//	private BusinessSubCenterProductMapAddResource setBusinessSubCenterProductMapAddResource() {
//		BusinessSubCenterProductMapAddResource businessSubCenterProductMapAddResource = new BusinessSubCenterProductMapAddResource();
//		
//		List<ProductRequestResource> productList =  new ArrayList<>();
//		ProductRequestResource productRequestResource = new ProductRequestResource();
//		productRequestResource.setId("1");
//		productRequestResource.setProductId("1");
//		productRequestResource.setProductName("name");
//		productRequestResource.setProductCode("PR01");
//		productRequestResource.setStatus("ACTIVE");
//		
//		productList.add(productRequestResource);
//		return businessSubCenterProductMapAddResource;
//	}

}
