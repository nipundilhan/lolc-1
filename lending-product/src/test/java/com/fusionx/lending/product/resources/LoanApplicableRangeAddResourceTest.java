//package com.fusionx.lending.product.resources;
//
//import static org.junit.Assert.assertEquals;
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
//public class LoanApplicableRangeAddResourceTest {
//
//	private LocalValidatorFactoryBean localValidatorFactory;
//
//	@Before
//	public void setUp() {
//		localValidatorFactory = new LocalValidatorFactoryBean();
//		localValidatorFactory.setProviderClass(HibernateValidator.class);
//		localValidatorFactory.afterPropertiesSet();
//	}
//
//	private LoanApplicableRangeAddResource setLoanApplicableRangeAddResource() {
//
//		LoanApplicableRangeAddResource loanApplicableRangeAddResource = new LoanApplicableRangeAddResource();
//		loanApplicableRangeAddResource.setCode("TEST");
//		loanApplicableRangeAddResource.setName("Test");
//		loanApplicableRangeAddResource.setStatus("ACTIVE");
//		loanApplicableRangeAddResource.setMaximumAmount("500000.00");
//		loanApplicableRangeAddResource.setMinimumAmount("100000.00");
//		loanApplicableRangeAddResource.setDefaultAmount("1500000.00");
//		loanApplicableRangeAddResource.setMaximumRate("6.45");
//		loanApplicableRangeAddResource.setMaximumRate("3.56");
//		loanApplicableRangeAddResource.setDefaultRate("4.23");
//		
//		return loanApplicableRangeAddResource;
//
//	}
//	
//	/**
//     * Code Cannot be blank.
//     * Expected: {common.not-null}
//     */
//	@Test
//    public void codeNotNull() {
//		LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//		loanApplicableRangeAddResource.setCode(null);
//		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//	
//	/**
//     * Code Invalid value.
//     * Expected: {common.invalid-value}
//     */
//    @Test
//    public void codeSize() {
//    	LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//    	loanApplicableRangeAddResource.setCode("WWWWW");
//		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//    
//    /**
//     * Code should consists of alphanumeric characters only.
//     * Expected: {common.code-pattern}
//     */
//    @Test
//    public void codePattern() {
//    	LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//    	loanApplicableRangeAddResource.setCode("!@#$");
//		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//    
////    /**
////     * Status Cannot be blank.
////     * Expected: {common.not-null}
////     */
////    @Test
////    public void statusNotNull() {
////    	LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
////    	loanApplicableRangeAddResource.setStatus(null);
////		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
////		
////    }
////    
////    /**
////     * Status should be ACTIVE or INACTIVE.
////     * Expected: {common-status.pattern}
////     */
////    @Test
////    public void statusPattern() {
////    	LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
////    	loanApplicableRangeAddResource.setStatus("ABCD");
////		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
////    }
////    
//    /**
//     * Maximum Amount Cannot be blank.
//     * Expected: {common.not-null}
//     */
//	@Test
//    public void maxAmountNotNull() {
//		LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//		loanApplicableRangeAddResource.setMaximumAmount(null);
//		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//	
//	 /**
//     * Maximum Amount should be digits.
//     * Expected: {common-amount.pattern}
//     */
//    @Test
//    public void maxAmountPattern() {
//    	LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//    	loanApplicableRangeAddResource.setMaximumAmount("ABCD");
//		assertEquals("{common-amount.pattern}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//    
//    /**
//     * Minimum Amount Cannot be blank.
//     * Expected: {common.not-null}
//     */
//	@Test
//    public void miniAmountNotNull() {
//		LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//		loanApplicableRangeAddResource.setMinimumAmount(null);
//		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//	
//	 /**
//     * Minimum Amount should be digits.
//     * Expected: {common-amount.pattern}
//     */
//    @Test
//    public void miniAmountPattern() {
//    	LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//    	loanApplicableRangeAddResource.setMinimumAmount("ABCD");
//		assertEquals("{common-amount.pattern}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//    
//    /**
//     * Default Amount Cannot be blank.
//     * Expected: {common.not-null}
//     */
//	@Test
//    public void defaultAmountNotNull() {
//		LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//		loanApplicableRangeAddResource.setDefaultAmount(null);
//		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//	
//	 /**
//     * Status should be digits.
//     * Expected: {common-amount.pattern}
//     */
//    @Test
//    public void defaultAmountPattern() {
//    	LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//    	loanApplicableRangeAddResource.setDefaultAmount("ABCD");
//		assertEquals("{common-amount.pattern}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//    
//    /**
//     * Maximum Rate Cannot be blank.
//     * Expected: {common.not-null}
//     */
//	@Test
//    public void maxRateNotNull() {
//		LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//		loanApplicableRangeAddResource.setMaximumRate(null);
//		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//	
//	 /**
//     * Maximum Rate should be digits.
//     * Expected: {rate.pattern}
//     */
//    @Test
//    public void maxRatePattern() {
//    	LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//    	loanApplicableRangeAddResource.setMaximumRate("ABCD");
//		assertEquals("{rate.pattern}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//    
//    /**
//     * Minimum Rate Cannot be blank.
//     * Expected: {common.not-null}
//     */
//	@Test
//    public void miniRateNotNull() {
//		LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//		loanApplicableRangeAddResource.setMinimumRate(null);
//		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//	
//	 /**
//     * Minimum Rate should be digits.
//     * Expected: {rate.pattern}
//     */
//    @Test
//    public void miniRatePattern() {
//    	LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//    	loanApplicableRangeAddResource.setMinimumRate("ABCD");
//		assertEquals("{rate.pattern}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//    
//    /**
//     * Default Rate Cannot be blank.
//     * Expected: {common.not-null}
//     */
//	@Test
//    public void defaultRateNotNull() {
//		LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//		loanApplicableRangeAddResource.setDefaultRate(null);
//		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//	
//	 /**
//     * Default Rate should be digits.
//     * Expected: {rate.pattern}
//     */
//    @Test
//    public void defaultRatePattern() {
//    	LoanApplicableRangeAddResource loanApplicableRangeAddResource = setLoanApplicableRangeAddResource();
//    	loanApplicableRangeAddResource.setDefaultRate("ABCD");
//		assertEquals("{rate.pattern}", TestUtils.getFieldErrorMessageKey(loanApplicableRangeAddResource, localValidatorFactory));
//    }
//    
//
//}
