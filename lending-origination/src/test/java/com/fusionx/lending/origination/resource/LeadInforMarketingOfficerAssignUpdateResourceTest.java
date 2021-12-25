package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.utill.TestUtils;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * LeadInforMarketingOfficerAssignUpdateResourceTest
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   			Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   09-08-2021   FXL_380         FXL-421      Piyumi       Created
 * <p>
 * *******************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LeadInforMarketingOfficerAssignUpdateResourceTest {

    private LocalValidatorFactoryBean localValidatorFactory;

    @Before
    public void setUp() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }

    private LeadInforMarketingOfficerAssignUpdateResource setLeadInforMarketingOfficerAssignUpdateResource() {
        LeadInforMarketingOfficerAssignUpdateResource leadInforMarketingOfficerAssignUpdateResource = new LeadInforMarketingOfficerAssignUpdateResource();
        leadInforMarketingOfficerAssignUpdateResource.setMarketingOfficerId("1");
        List<LeadListResource> leadList = new ArrayList<>();
        LeadListResource idList = new LeadListResource();
        idList.setLeadId("1l");
        idList.setVersion("0");
        leadList.add(idList);
        leadInforMarketingOfficerAssignUpdateResource.setLeadIdList(leadList);
        return leadInforMarketingOfficerAssignUpdateResource;
    }

    /**
     * marketingOfficerId is required.
     * Expected: {common.not-null}
     */
    @Test
    public void marketingOfficerIdNotNull() {
        LeadInforMarketingOfficerAssignUpdateResource leadInforMarketingOfficerAssignUpdateResource = setLeadInforMarketingOfficerAssignUpdateResource();
        leadInforMarketingOfficerAssignUpdateResource.setMarketingOfficerId(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(leadInforMarketingOfficerAssignUpdateResource, localValidatorFactory));
    }

    /**
     * marketingOfficerId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
   /* @Test
    public void marketingOfficerIdPattern() {
        LeadInforMarketingOfficerAssignUpdateResource leadInforMarketingOfficerAssignUpdateResource = setLeadInforMarketingOfficerAssignUpdateResource();
        leadInforMarketingOfficerAssignUpdateResource.setMarketingOfficerId("A");
        assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(leadInforMarketingOfficerAssignUpdateResource, localValidatorFactory));
    }*/

    /**
     * leadIdList is required.
     * Expected: {common.not-null}
     */
    @Test
    public void leadIdListNotNull() {
        LeadInforMarketingOfficerAssignUpdateResource leadInforMarketingOfficerAssignUpdateResource = setLeadInforMarketingOfficerAssignUpdateResource();
        leadInforMarketingOfficerAssignUpdateResource.setLeadIdList(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(leadInforMarketingOfficerAssignUpdateResource, localValidatorFactory));
    }


}
