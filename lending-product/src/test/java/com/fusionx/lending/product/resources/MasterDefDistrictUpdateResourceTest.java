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
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class MasterDefDistrictUpdateResourceTest {

/*	
	public String longText = "The Jews who descended from Abraham were never really the nation we associate with greatness.  They did not conquer and build a great empire like the Romans did or build large monuments like the Egyptians did with the pyramids. Their fame comes from the Law and Boo"
			+ "k which they wrote; from some remarkable individuals that were Jewish; and that they have survived as a somewhat different people group for thousands of years.  Their greatness is not because of anything they did, but rather what was done to and through them. "
			+ " The promise says repeatedly “I will …” – that would be the power behind the promise.  Their unique greatness happened because God made it happen rather than some ability, conquest or power of their own";


	
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private MasterDefDistrictUpdateResource setMasterDefDistrictUpdateResource() {
		
		MasterDefDistrictUpdateResource masterDefDistrictUpdateResource = new MasterDefDistrictUpdateResource();
		masterDefDistrictUpdateResource.setId("1000");
		masterDefDistrictUpdateResource.setDistrictId("1000");
		masterDefDistrictUpdateResource.setDistrictName("rajagiriya");
		masterDefDistrictUpdateResource.setStatus("ACTIVE");
		

		
		return masterDefDistrictUpdateResource;
		
	}
	

	
    /**
     * Id  should consists of alphanumeric characters only.
     * Expected: {common.numeric-pattern}
     */
/*    @Test
    public void idPattern() {
    	MasterDefDistrictUpdateResource masterDefDistrictUpdateResource = setMasterDefDistrictUpdateResource();
    	masterDefDistrictUpdateResource.setId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(masterDefDistrictUpdateResource, localValidatorFactory));
    }
	
	
	/**
     * district Id Cannot be null.
     * Expected: {common.not-null}
     */
/*	@Test
    public void districtIdNotNull() {
		MasterDefDistrictUpdateResource masterDefDistrictUpdateResource = setMasterDefDistrictUpdateResource();
		masterDefDistrictUpdateResource.setDistrictId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefDistrictUpdateResource, localValidatorFactory));
    }
	
    /**
     * district Id  should consists of alphanumeric characters only.
     * Expected: {common.numeric-pattern}
     */
 /*   @Test
    public void districtIdPattern() {
    	MasterDefDistrictUpdateResource masterDefDistrictUpdateResource = setMasterDefDistrictUpdateResource();
    	masterDefDistrictUpdateResource.setDistrictId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(masterDefDistrictUpdateResource, localValidatorFactory));
    }
    
    /**
     * Status Cannot be blank.
     * Expected: {common.not-null}
     */
 /*   @Test
    public void statusNotNull() {
    	MasterDefDistrictUpdateResource masterDefDistrictUpdateResource = setMasterDefDistrictUpdateResource();
    	masterDefDistrictUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefDistrictUpdateResource, localValidatorFactory));
		
    }
    
    /**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
 /*   @Test
    public void statusPattern() {
    	MasterDefDistrictUpdateResource masterDefDistrictUpdateResource = setMasterDefDistrictUpdateResource();
    	masterDefDistrictUpdateResource.setStatus("ABCD");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(masterDefDistrictUpdateResource, localValidatorFactory));
    }
    
    /**
     * District Name  cannot be null.
     * Expected: {common.not-null}
     */
/*	@Test
    public void districtNameNotNull() {
		MasterDefDistrictUpdateResource masterDefDistrictUpdateResource = setMasterDefDistrictUpdateResource();
		masterDefDistrictUpdateResource.setDistrictName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefDistrictUpdateResource, localValidatorFactory));
    }
	
    /**
     * district Name  size should not exceed.
     * Expected: {common-name.size}
     */
/*	@Test
    public void districtNameSize() {
		MasterDefDistrictUpdateResource masterDefDistrictUpdateResource = setMasterDefDistrictUpdateResource();
		masterDefDistrictUpdateResource.setDistrictName(longText);
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(masterDefDistrictUpdateResource, localValidatorFactory));
    }*/
}
