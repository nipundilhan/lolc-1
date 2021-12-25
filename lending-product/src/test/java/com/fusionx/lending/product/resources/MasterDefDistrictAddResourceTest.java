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








/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class MasterDefDistrictAddResourceTest {
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
	
	private MasterDefDistrictAddResource setMasterDefDistrictAddResource() {
		
		MasterDefDistrictAddResource masterDefDistrictAddResource = new MasterDefDistrictAddResource();
		masterDefDistrictAddResource.setDistrictId("1000");
		masterDefDistrictAddResource.setDistrictName("rajagiriya");
		masterDefDistrictAddResource.setStatus("ACTIVE");
		return masterDefDistrictAddResource;
		
	}
	
	
	/**
     * district Id Cannot be null.
     * Expected: {common.not-null}
     */
/*	@Test
    public void districtIdNotNull() {
		MasterDefDistrictAddResource masterDefDistrictAddResource = setMasterDefDistrictAddResource();
		masterDefDistrictAddResource.setDistrictId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefDistrictAddResource, localValidatorFactory));
    }
	
    /**
     * district Id  should consists of alphanumeric characters only.
     * Expected: {common.numeric-pattern}
     */
/*    @Test
    public void districtIdPattern() {
    	MasterDefDistrictAddResource masterDefDistrictAddResource = setMasterDefDistrictAddResource();
    	masterDefDistrictAddResource.setDistrictId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(masterDefDistrictAddResource, localValidatorFactory));
    }
    
    /**
     * Status Cannot be blank.
     * Expected: {common.not-null}
     */
 /*   @Test
    public void statusNotNull() {
    	MasterDefDistrictAddResource masterDefDistrictAddResource = setMasterDefDistrictAddResource();
    	masterDefDistrictAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefDistrictAddResource, localValidatorFactory));
		
    }
    
    /**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
  /*  @Test
    public void statusPattern() {
    	MasterDefDistrictAddResource masterDefDistrictAddResource = setMasterDefDistrictAddResource();
    	masterDefDistrictAddResource.setStatus("ABCD");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(masterDefDistrictAddResource, localValidatorFactory));
    }
    
    /**
     * District Name  cannot be null.
     * Expected: {common.not-null}
     */
/*	@Test
    public void districtNameNotNull() {
		MasterDefDistrictAddResource masterDefDistrictAddResource = setMasterDefDistrictAddResource();
		masterDefDistrictAddResource.setDistrictName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefDistrictAddResource, localValidatorFactory));
    }
	
    /**
     * district Name  size should not exceed.
     * Expected: {common-name.size}
     */
/*	@Test
    public void districtNameSize() {
		MasterDefDistrictAddResource masterDefDistrictAddResource = setMasterDefDistrictAddResource();
		masterDefDistrictAddResource.setDistrictName(longText);
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(masterDefDistrictAddResource, localValidatorFactory));
    }*/
}
