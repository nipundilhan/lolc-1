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

import com.fusionx.lending.origination.resource.FacilityCalculationRequestResource;
import com.fusionx.lending.origination.utill.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FacilityCalculationRequestResourceTest {

	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
    public void setUp() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }
	
	/**
     * productCode field is required.
     * Expected: {productCode.not-null}
     */
    @Test
    public void productCodeNotNull() {
    	FacilityCalculationRequestResource facilityCalculationRequestResource=setFacilityCalculationRequestResource();
    	facilityCalculationRequestResource.setProductCode(null);
		assertEquals("{productCode.not-null}", TestUtils.getFieldErrorMessageKey(facilityCalculationRequestResource, localValidatorFactory));
		
    }
    /**
     * productCode field is required.
     * Expected: {productCode.not-null}
     */
    @Test
    public void productCodeNotEmpty() {
    	FacilityCalculationRequestResource facilityCalculationRequestResource=setFacilityCalculationRequestResource();
    	facilityCalculationRequestResource.setProductCode("");
		assertEquals("{productCode.not-null}", TestUtils.getFieldErrorMessageKey(facilityCalculationRequestResource, localValidatorFactory));
		
    }
    
    /**
     * productName field is required.
     * Expected: {productName.not-null}
     */
    @Test
    public void productNameNotNull() {
    	FacilityCalculationRequestResource facilityCalculationRequestResource=setFacilityCalculationRequestResource();
    	facilityCalculationRequestResource.setProductName(null);
		assertEquals("{productName.not-null}", TestUtils.getFieldErrorMessageKey(facilityCalculationRequestResource, localValidatorFactory));
		
    }
    /**
     * productName field is required.
     * Expected: {productName.not-null}
     */
    @Test
    public void productNameNotEmpty() {
    	FacilityCalculationRequestResource facilityCalculationRequestResource=setFacilityCalculationRequestResource();
    	facilityCalculationRequestResource.setProductName("");
		assertEquals("{productName.not-null}", TestUtils.getFieldErrorMessageKey(facilityCalculationRequestResource, localValidatorFactory));
		
    }
	private FacilityCalculationRequestResource setFacilityCalculationRequestResource() {
		FacilityCalculationRequestResource facilityCalculationRequestResource=new FacilityCalculationRequestResource();
		facilityCalculationRequestResource.setProductCode("GRLO");
		facilityCalculationRequestResource.setProductName("Group Loan");
		facilityCalculationRequestResource.setSubProductCode("GENE");
		facilityCalculationRequestResource.setSubProductName("Genaral");
		facilityCalculationRequestResource.setCalculationMethodCode("FITE");
		facilityCalculationRequestResource.setCalculationMethodName("Fixed Term");
		facilityCalculationRequestResource.setPaymentFrequencyCode("MONTH");
		facilityCalculationRequestResource.setPaymentFrequencyName("Monthly");
		facilityCalculationRequestResource.setCurrencyCode("LKR");
		facilityCalculationRequestResource.setLoanAmount("10000.00");
		facilityCalculationRequestResource.setTerm("12");
		facilityCalculationRequestResource.setRate("12.00");
		facilityCalculationRequestResource.setLoanAmountWithTax("10500.00");
		facilityCalculationRequestResource.setAmountFinance("12000.00");
		facilityCalculationRequestResource.setTotalReceivable("12000.00");
		return facilityCalculationRequestResource;
	}
}
