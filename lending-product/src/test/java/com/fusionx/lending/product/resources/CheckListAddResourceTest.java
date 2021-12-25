package com.fusionx.lending.product.resources;

import com.fusionx.lending.product.utill.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Check List Add Resource
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   26-10-2021                  -        Rohan       Created
 * <p>
 * *******************************************************************************************************
 */
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class CheckListAddResourceTest {

	/*
    private LocalValidatorFactoryBean localValidatorFactory;

    @Before
    public void setUp() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }

    private CheckListAddResource setCheckListAddResource() {
        CheckListAddResource checkListAddResource = new CheckListAddResource();
        checkListAddResource.setCheckMark("YES");
        checkListAddResource.setRemarks("Test");
        checkListAddResource.setTenantId("Test");
        checkListAddResource.setStatus("ACTIVE");
        checkListAddResource.setLendingAccountDetailId("2");
        return checkListAddResource;
    }

    /**
     * checkMark is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void checkMarkNotNull() {
        CheckListAddResource checkListAddResource = setCheckListAddResource();
        checkListAddResource.setCheckMark(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(checkListAddResource, localValidatorFactory));
    }

    /**
     * checkMark should be YES or NO.
     * Expected: {common-status.pattern}
     */
/*    @Test
    public void checkMarkPattern() {
        CheckListAddResource checkListAddResource = setCheckListAddResource();
        checkListAddResource.setCheckMark("asdasd");
        assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(checkListAddResource, localValidatorFactory));
    }

    /**
     * status is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void statusNotNull() {
        CheckListAddResource checkListAddResource = setCheckListAddResource();
        checkListAddResource.setStatus(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(checkListAddResource, localValidatorFactory));
    }

    /**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
 /*   @Test
    public void statusPattern() {
        CheckListAddResource checkListAddResource = setCheckListAddResource();
        checkListAddResource.setStatus("asdasd");
        assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(checkListAddResource, localValidatorFactory));
    }

    /**
     * lending account ID is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void lendingAccountIdNotNull() {
        CheckListAddResource checkListAddResource = setCheckListAddResource();
        checkListAddResource.setLendingAccountDetailId(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(checkListAddResource, localValidatorFactory));
    } */

    /**
     * document checklist detail ID is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void documentChecklistDetailIdNotNull() {
        CheckListAddResource checkListAddResource = setCheckListAddResource();
        checkListAddResource.setDocumentCheckListDetailId(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(checkListAddResource, localValidatorFactory));
    }*/

}
