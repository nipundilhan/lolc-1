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
 * MasterCurrencyUpdateResourceTest
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   			Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-07-2021   FXL_July_2021_2         FXL-6      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MasterCurrencyUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
		@Before
		public void setUp() {
			localValidatorFactory = new LocalValidatorFactoryBean();
		    localValidatorFactory.setProviderClass(HibernateValidator.class);
		    localValidatorFactory.afterPropertiesSet();
		}
		
		private MasterCurrencyUpdateResource setMasterCurrencyUpdateResource() {
			MasterCurrencyUpdateResource masterCurrencyUpdateResource = new MasterCurrencyUpdateResource();
			masterCurrencyUpdateResource.setId("1");
			masterCurrencyUpdateResource.setCurrencyId("1");
			masterCurrencyUpdateResource.setCurrencyName("Rupees");
			masterCurrencyUpdateResource.setNumOfDecimalPlaces("2");
			masterCurrencyUpdateResource.setStatus("ACTIVE");
			masterCurrencyUpdateResource.setVersion("1");
			return masterCurrencyUpdateResource;
		}
		
		/**
		 * Id should be in "^$|[0-9]+" pattern
		 * Expected: {common-numeric.pattern}
		 */
		@Test
		public void idPattern() {
			MasterCurrencyUpdateResource masterCurrencyUpdateResource = setMasterCurrencyUpdateResource();
			masterCurrencyUpdateResource.setId("A");
			assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(masterCurrencyUpdateResource, localValidatorFactory));
		}
		
		 /**
		 * currencyId is required.
		 * Expected: {common.not-null}
		 */
		@Test
		public void currencyIdNotNull() {
			MasterCurrencyUpdateResource masterCurrencyUpdateResource = setMasterCurrencyUpdateResource();
			masterCurrencyUpdateResource.setCurrencyId(null);
			assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterCurrencyUpdateResource, localValidatorFactory));
		}
		
		/**
		 * currencyId should be in "^$|[0-9]+" pattern
		 * Expected: {common-numeric.pattern}
		 */
		@Test
		public void currencyIdPattern() {
			MasterCurrencyUpdateResource masterCurrencyUpdateResource = setMasterCurrencyUpdateResource();
			masterCurrencyUpdateResource.setCurrencyId("A");
			assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(masterCurrencyUpdateResource, localValidatorFactory));
		}
		
		 /**
		 *  currencyName is required.
		 * Expected: {common.not-null}
		 */
		@Test
		public void currencyNameNotNull() {
			MasterCurrencyUpdateResource masterCurrencyUpdateResource = setMasterCurrencyUpdateResource();
			masterCurrencyUpdateResource.setCurrencyName(null);
			assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterCurrencyUpdateResource, localValidatorFactory));
		}
		
		/**
		 * currencyName cannot be greater than 70 characters.
		 * Expected: {common-name.size}
		 */
		@Test
		public void currencyNameSize() {
			MasterCurrencyUpdateResource masterCurrencyUpdateResource = setMasterCurrencyUpdateResource();
			masterCurrencyUpdateResource.setCurrencyName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
			assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(masterCurrencyUpdateResource, localValidatorFactory));
			
		}
		
		/**
		 * numOfDecimalPlaces is required.
		 * Expected: {common.not-null}
		 */
		@Test
		public void numOfDecimalPlacesNotNull() {
			MasterCurrencyUpdateResource masterCurrencyUpdateResource = setMasterCurrencyUpdateResource();
			masterCurrencyUpdateResource.setNumOfDecimalPlaces(null);
			assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterCurrencyUpdateResource, localValidatorFactory));
		}
		
		/**
		 * numOfDecimalPlaces should be in "^$|[0-9]+" pattern
		 * Expected: {common-numeric.pattern}
		 */
		@Test
		public void numOfDecimalPlacesPattern() {
			MasterCurrencyUpdateResource masterCurrencyUpdateResource = setMasterCurrencyUpdateResource();
			masterCurrencyUpdateResource.setNumOfDecimalPlaces("A");
			assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(masterCurrencyUpdateResource, localValidatorFactory));
		}
		
		/**
		 * status is required.
		 * Expected: {common.not-null}
		 */
		@Test
		public void statusNotNull() {
			MasterCurrencyUpdateResource masterCurrencyUpdateResource = setMasterCurrencyUpdateResource();
			masterCurrencyUpdateResource.setStatus(null);
			assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterCurrencyUpdateResource, localValidatorFactory));
		}
		
		/**
		 * Status should be ACTIVE or INACTIVE.
		 * Expected: {common-status.pattern}
		 */
		@Test
		public void statusPattern() {
			MasterCurrencyUpdateResource masterCurrencyUpdateResource = setMasterCurrencyUpdateResource();
			masterCurrencyUpdateResource.setStatus("ABCDEF");
			assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(masterCurrencyUpdateResource, localValidatorFactory));
		}
		
//		 /**
//	     * version is required.
//	     * Expected: {version.not-null}
//	     */
//	    @Test
//		public void versionNotNull() {
//	    	MasterCurrencyUpdateResource masterCurrencyUpdateResource = setMasterCurrencyUpdateResource();
//	    	masterCurrencyUpdateResource.setVersion(null);
//			assertEquals("{version.not-null}", TestUtils.getFieldErrorMessageKey(masterCurrencyUpdateResource, localValidatorFactory));
//		}
//	    
	    /**
	     * version should be in "^$|[0-9]+" pattern
	     * Expected: {common-numeric.pattern}
	     */
	    @Test
		public void versionPattern() {
	    	MasterCurrencyUpdateResource masterCurrencyUpdateResource = setMasterCurrencyUpdateResource();
	    	masterCurrencyUpdateResource.setVersion("A");
			assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(masterCurrencyUpdateResource, localValidatorFactory));
		}


}
