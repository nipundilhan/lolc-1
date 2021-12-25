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
 * LeadInforBranchAssignUpdateResourceTest
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
public class LeadInforBranchAssignUpdateResourceTest {

    private LocalValidatorFactoryBean localValidatorFactory;

    @Before
    public void setUp() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }

    private LeadInforBranchAssignUpdateResource setLeadInforBranchAssignUpdateResource() {
        LeadInforBranchAssignUpdateResource leadInforBranchAssignUpdateResource = new LeadInforBranchAssignUpdateResource();
        leadInforBranchAssignUpdateResource.setBranchId("1");
        leadInforBranchAssignUpdateResource.setBranchName("Name");
        List<LeadListResource> leadList = new ArrayList<>();
        LeadListResource idList = new LeadListResource();
        idList.setLeadId("1l");
        idList.setVersion("0");
        leadList.add(idList);
        leadInforBranchAssignUpdateResource.setLeadIdList(leadList);
        return leadInforBranchAssignUpdateResource;
    }

    /**
     * BranchId is required.
     * Expected: {common.not-null}
     */
   /* @Test
    public void marketingOfficerIdNotNull() {
        LeadInforBranchAssignUpdateResource leadInforBranchAssignUpdateResource = setLeadInforBranchAssignUpdateResource();
        leadInforBranchAssignUpdateResource.setBranchId(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(leadInforBranchAssignUpdateResource, localValidatorFactory));
    }*/

    /**
     * BranchId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
    public void currencyIdPattern() {
        LeadInforBranchAssignUpdateResource leadInforBranchAssignUpdateResource = setLeadInforBranchAssignUpdateResource();
        leadInforBranchAssignUpdateResource.setBranchId("A");
        assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(leadInforBranchAssignUpdateResource, localValidatorFactory));
    }

    /**
     * BranchName is required.
     * Expected: {common.not-null}
     */
   /* @Test
    public void branchNameNotNull() {
        LeadInforBranchAssignUpdateResource leadInforBranchAssignUpdateResource = setLeadInforBranchAssignUpdateResource();
        leadInforBranchAssignUpdateResource.setBranchName(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(leadInforBranchAssignUpdateResource, localValidatorFactory));
    }*/

    /**
     * BranchName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
   /* @Test
    public void branchNameSize() {
        LeadInforBranchAssignUpdateResource leadInforBranchAssignUpdateResource = setLeadInforBranchAssignUpdateResource();
        leadInforBranchAssignUpdateResource.setBranchName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
        assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(leadInforBranchAssignUpdateResource, localValidatorFactory));

    }*/

    /**
     * leadIdList is required.
     * Expected: {common.not-null}
     */
   /* @Test
    public void leadIdListNotNull() {
        LeadInforBranchAssignUpdateResource leadInforBranchAssignUpdateResource = setLeadInforBranchAssignUpdateResource();
        leadInforBranchAssignUpdateResource.setLeadIdList(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(leadInforBranchAssignUpdateResource, localValidatorFactory));
    }*/


}
