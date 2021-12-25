package com.fusionx.lending.product.resources;

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

import com.fusionx.lending.product.utill.TestUtils;
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class MasterDefStateAddResourceTest {
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
	
	private MasterDefStateAddResource setMasterDefStateAddResource() {
		
		MasterDefStateAddResource masterDefStateAddResource = new MasterDefStateAddResource();
		masterDefStateAddResource.setStateId("100");
		masterDefStateAddResource.setStateName("colombo");
		masterDefStateAddResource.setStatus("ACTIVE");
		
		List<MasterDefDistrictAddResource> districtList = new ArrayList<>();
		MasterDefDistrictAddResource masterDefDistrictAddResource = new MasterDefDistrictAddResource();
		masterDefDistrictAddResource.setDistrictId("1000");
		masterDefDistrictAddResource.setDistrictName("rajagiriya");
		masterDefDistrictAddResource.setStatus("ACTIVE");
		masterDefStateAddResource.setDistrict(districtList);
		
		return masterDefStateAddResource;
		
	}
	
	
	
	/**
     * state Id Cannot be null.
     * Expected: {common.not-null}
     */
/*	@Test
    public void stateIdNotNull() {
		MasterDefStateAddResource masterDefStateAddResource = setMasterDefStateAddResource();
		masterDefStateAddResource.setStateId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefStateAddResource, localValidatorFactory));
    }
	
    /**
     * state Id  should consists of numeric characters only.
     * Expected: {common.numeric-pattern}
     */
 /*   @Test
    public void stateIdPattern() {
    	MasterDefStateAddResource masterDefStateAddResource = setMasterDefStateAddResource();
    	masterDefStateAddResource.setStateId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(masterDefStateAddResource, localValidatorFactory));
    }
    
    /**
     * Status Cannot be blank.
     * Expected: {common.not-null}
     */
 /*   @Test
    public void statusNotNull() {
    	MasterDefStateAddResource masterDefStateAddResource = setMasterDefStateAddResource();
    	masterDefStateAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefStateAddResource, localValidatorFactory));
		
    }
    
    /**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
 /*   @Test
    public void statusPattern() {
    	MasterDefStateAddResource masterDefStateAddResource = setMasterDefStateAddResource();
    	masterDefStateAddResource.setStatus("ABCD");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(masterDefStateAddResource, localValidatorFactory));
    }
    
    /**
     * state Name  cannot be null.
     * Expected: {common.not-null}
     */
/*	@Test
    public void stateNameNotNull() {
		MasterDefStateAddResource masterDefStateAddResource = setMasterDefStateAddResource();
		masterDefStateAddResource.setStateName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefStateAddResource, localValidatorFactory));
    }
	
    /**
     * state Name  size should not exceed.
     * Expected: {common-name.size}
     */
/*	@Test
    public void stateNameSize() {
		MasterDefStateAddResource masterDefStateAddResource = setMasterDefStateAddResource();
		masterDefStateAddResource.setStateName(longText);
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(masterDefStateAddResource, localValidatorFactory));
    }*/

}
