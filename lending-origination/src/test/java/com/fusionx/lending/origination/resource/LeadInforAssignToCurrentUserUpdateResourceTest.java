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
 * LeadInforAssignToCurrentUserUpdateResourceTest
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   			Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   09-08-2021   FXL_380         			FXL-421      Piyumi       Created
 * <p>
 * *******************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LeadInforAssignToCurrentUserUpdateResourceTest {

    private LocalValidatorFactoryBean localValidatorFactory;

    @Before
    public void setUp() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }

    private LeadInforAssignToCurrentUserUpdateResource setLeadInforAssignToCurrentUserUpdateResource() {
        LeadInforAssignToCurrentUserUpdateResource leadInforAssignToCurrentUserUpdateResource = new LeadInforAssignToCurrentUserUpdateResource();
        leadInforAssignToCurrentUserUpdateResource.setBranchId("1");
        leadInforAssignToCurrentUserUpdateResource.setBranchName("Name");
        leadInforAssignToCurrentUserUpdateResource.setMarketingOfficerId("1");
        List<LeadListResource> leadList = new ArrayList<>();
        LeadListResource idList = new LeadListResource();
        idList.setLeadId("1l");
        idList.setVersion("1");
        leadList.add(idList);
        leadInforAssignToCurrentUserUpdateResource.setLeadIdList(leadList);
        return leadInforAssignToCurrentUserUpdateResource;
    }

    /**
     * BranchId is required.
     * Expected: {common.not-null}
     */
    @Test
    public void branchIdNotNull() {
        LeadInforAssignToCurrentUserUpdateResource leadInforAssignToCurrentUserUpdateResource = setLeadInforAssignToCurrentUserUpdateResource();
        leadInforAssignToCurrentUserUpdateResource.setBranchId(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(leadInforAssignToCurrentUserUpdateResource, localValidatorFactory));
    }

    /**
     * BranchId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
   /* @Test
    public void branchIdPattern() {
        LeadInforAssignToCurrentUserUpdateResource leadInforAssignToCurrentUserUpdateResource = setLeadInforAssignToCurrentUserUpdateResource();
        leadInforAssignToCurrentUserUpdateResource.setBranchId("A");
        assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(leadInforAssignToCurrentUserUpdateResource, localValidatorFactory));
    }*/

    /**
     * BranchName is required.
     * Expected: {common.not-null}
     */
    @Test
    public void branchNameNotNull() {
        LeadInforAssignToCurrentUserUpdateResource leadInforAssignToCurrentUserUpdateResource = setLeadInforAssignToCurrentUserUpdateResource();
        leadInforAssignToCurrentUserUpdateResource.setBranchName(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(leadInforAssignToCurrentUserUpdateResource, localValidatorFactory));
    }

    /**
     * BranchName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
   /* @Test
    public void branchNameSize() {
        LeadInforAssignToCurrentUserUpdateResource leadInforAssignToCurrentUserUpdateResource = setLeadInforAssignToCurrentUserUpdateResource();
        leadInforAssignToCurrentUserUpdateResource.setBranchName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
        assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(leadInforAssignToCurrentUserUpdateResource, localValidatorFactory));

    }*/

    /**
     * marketingOfficerId is required.
     * Expected: {common.not-null}
     */
    @Test
    public void marketingOfficerIdNotNull() {
        LeadInforAssignToCurrentUserUpdateResource leadInforAssignToCurrentUserUpdateResource = setLeadInforAssignToCurrentUserUpdateResource();
        leadInforAssignToCurrentUserUpdateResource.setMarketingOfficerId(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(leadInforAssignToCurrentUserUpdateResource, localValidatorFactory));
    }

    /**
     * marketingOfficerId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    /*@Test
    public void marketingOfficerIdPattern() {
        LeadInforAssignToCurrentUserUpdateResource leadInforAssignToCurrentUserUpdateResource = setLeadInforAssignToCurrentUserUpdateResource();
        leadInforAssignToCurrentUserUpdateResource.setMarketingOfficerId("A");
        assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(leadInforAssignToCurrentUserUpdateResource, localValidatorFactory));
    }*/

    /**
     * leadIdList is required.
     * Expected: {common.not-null}
     */
    @Test
    public void leadIdListNotNull() {
        LeadInforAssignToCurrentUserUpdateResource leadInforAssignToCurrentUserUpdateResource = setLeadInforAssignToCurrentUserUpdateResource();
        leadInforAssignToCurrentUserUpdateResource.setLeadIdList(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(leadInforAssignToCurrentUserUpdateResource, localValidatorFactory));
    }


}
