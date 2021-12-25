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
/**
 * MasterDefinitionUpdateResourceTest
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   			Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-07-2021   FXL-1   				    FXL-5      Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.product.utill.TestUtils;

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class MasterDefinitionUpdateResourceTest {
/*
	private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	private MasterDefinitionUpdateResource setMasterDefinitionUpdateResource() {
		MasterDefinitionUpdateResource masterDefinitionUpdateResource = new MasterDefinitionUpdateResource();
		masterDefinitionUpdateResource.setVersion("1");
		return masterDefinitionUpdateResource;
	}

	/**
	 * Version is required. Expected: {version.not-null}
	 */
/*	@Test
	public void versionNotNull() {
		/*MasterDefinitionUpdateResource masterDefinitionUpdateResource = setMasterDefinitionUpdateResource();
		masterDefinitionUpdateResource.setVersion(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(masterDefinitionUpdateResource, localValidatorFactory));*/
//	}
/*
//	/**
//	 * Invalid version. Expected: {version.pattern}
//	 */
//	@Test
//	public void versionPattern() {
//		MasterDefinitionUpdateResource masterDefinitionUpdateResource = setMasterDefinitionUpdateResource();
//		masterDefinitionUpdateResource.setVersion("A");
//		assertEquals("{common-numeric.pattern}",
//				TestUtils.getFieldErrorMessageKey(masterDefinitionUpdateResource, localValidatorFactory));
//	}

}
