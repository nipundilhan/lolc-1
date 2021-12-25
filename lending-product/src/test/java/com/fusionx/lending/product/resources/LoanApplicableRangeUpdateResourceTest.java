//package com.fusionx.lending.product.resources;
//
//import static org.junit.Assert.assertEquals;
//
//import org.hibernate.validator.HibernateValidator;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//
//import com.fusionx.lending.product.utill.TestUtils;
//
///**
// * Common list Update Resource Test
// * 
// ********************************************************************************************************
// *  ###   Date         Story Point   Task No    Author       Description
// *-------------------------------------------------------------------------------------------------------
// *    1   08-07-2021      		             	Dilhan         Created
// *    
// ********************************************************************************************************
// */
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class LoanApplicableRangeUpdateResourceTest {
//	
//private LocalValidatorFactoryBean localValidatorFactory;
//	
//	@Before
//	public void setUp() {
//		localValidatorFactory = new LocalValidatorFactoryBean();
//        localValidatorFactory.setProviderClass(HibernateValidator.class);
//        localValidatorFactory.afterPropertiesSet();
//	}
//	
//	private LoanApplicableRangeUpdateResource setLoanApplicableRangeUpdateResource() {
//		
//		LoanApplicableRangeUpdateResource loanApplicableRangeUpdateResource = new LoanApplicableRangeUpdateResource();
//		loanApplicableRangeUpdateResource.setCode("TEST");
//		loanApplicableRangeUpdateResource.setName("Test");
//		loanApplicableRangeUpdateResource.setStatus("ACTIVE");
//		loanApplicableRangeUpdateResource.setMaximumAmount("500000.00");
//		loanApplicableRangeUpdateResource.setMinimumAmount("100000.00");
//		loanApplicableRangeUpdateResource.setDefaultAmount("1500000.00");
//		loanApplicableRangeUpdateResource.setMaximumRate("6.45");
//		loanApplicableRangeUpdateResource.setMaximumRate("3.56");
//		loanApplicableRangeUpdateResource.setDefaultRate("4.24");
//		loanApplicableRangeUpdateResource.setVersion("0");
//		return loanApplicableRangeUpdateResource;
//		
//	}
//	
//	/**
//     * Version is required.
//     * Expected: {version.not-null}
//     */
//    @Test
//	public void versionNotNull() {
//    	LoanApplicableRangeUpdateResource loanApplicableRangeUpdateResource = setLoanApplicableRangeUpdateResource();
//    	loanApplicableRangeUpdateResource.setVersion(null);
//		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeUpdateResource, localValidatorFactory));
//	}
//    
//	/**
//     * Invalid version.
//     * Expected: {version.pattern}
//     */
//    @Test
//	public void versionPattern() {
//    	LoanApplicableRangeUpdateResource loanApplicableRangeUpdateResource = setLoanApplicableRangeUpdateResource();
//    	loanApplicableRangeUpdateResource.setVersion("ABCDE");
//		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeUpdateResource, localValidatorFactory));
//	}
//
//}
