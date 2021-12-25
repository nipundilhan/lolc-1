package com.fusionx.lending.product.resources;

//import com.fusionx.lending.product.utill.TestUtils;
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

import static org.junit.Assert.assertEquals;

/**
 * Guarantor Add Resource
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   26-10-2021                  -        Rohan       Created
 * <p>
 * *******************************************************************************************************
 */

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class GuarantorAddResourceTest {
/*
    private LocalValidatorFactoryBean localValidatorFactory;

    @Before
    public void setUp() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }

    private GuarantorAddResource setGuarantorAddResource() {
        GuarantorAddResource guarantorAddResource = new GuarantorAddResource();
        guarantorAddResource.setSequence("test");
        guarantorAddResource.setGuaranteePercentage("12");
        guarantorAddResource.setValue("12");
        guarantorAddResource.setBondNumber("2");
        guarantorAddResource.setTenantId("test");
        guarantorAddResource.setStatus("ACTIVE");
        guarantorAddResource.setLendingAccountDetail("2");
        return guarantorAddResource;
    }

    /**
     * sequence is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void sequenceNotNull() {
        GuarantorAddResource guarantorAddResource = setGuarantorAddResource();
        guarantorAddResource.setSequence(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(guarantorAddResource, localValidatorFactory));
    }


    /**
     * guaranteePercentage is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void guaranteePercentageNotNull() {
        GuarantorAddResource guarantorAddResource = setGuarantorAddResource();
        guarantorAddResource.setGuaranteePercentage(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(guarantorAddResource, localValidatorFactory));
    }

    /**
     * guaranteePercentage should be "^$|\\d{1,20}\\.\\d{1,2}$".
     * Expected: {common-status.pattern}
     */
/*    @Test
    public void guaranteePercentagePattern() {
        GuarantorAddResource guarantorAddResource = setGuarantorAddResource();
        guarantorAddResource.setGuaranteePercentage("A");
        assertEquals("{common.invalid-amount-pattern-8-2}", TestUtils.getFieldErrorMessageKey(guarantorAddResource, localValidatorFactory));
    }

    /**
     * value is required.
     * Expected: {common.not-null}
     */
 /*   @Test
    public void valueNotNull() {
        GuarantorAddResource guarantorAddResource = setGuarantorAddResource();
        guarantorAddResource.setValue(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(guarantorAddResource, localValidatorFactory));
    }

    /**
     * value should be "^$|\\d{1,20}\\.\\d{1,2}$".
     * Expected: {common-status.pattern}
     */
/*    @Test
    public void valuePattern() {
        GuarantorAddResource guarantorAddResource = setGuarantorAddResource();
        guarantorAddResource.setValue("A");
        assertEquals("{common.invalid-amount-pattern-38-2}", TestUtils.getFieldErrorMessageKey(guarantorAddResource, localValidatorFactory));
    }

    /**
     * bondNumber is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void bondNumberNotNull() {
        GuarantorAddResource guarantorAddResource = setGuarantorAddResource();
        guarantorAddResource.setBondNumber(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(guarantorAddResource, localValidatorFactory));
    }

    /**
     * status is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void statusNotNull() {
        GuarantorAddResource guarantorAddResource = setGuarantorAddResource();
        guarantorAddResource.setStatus(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(guarantorAddResource, localValidatorFactory));
    }

    /**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
/*    @Test
    public void statusPattern() {
        GuarantorAddResource guarantorAddResource = setGuarantorAddResource();
        guarantorAddResource.setStatus("ACTIVE");
        assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(guarantorAddResource, localValidatorFactory));
    }

    /**
     * lending account ID is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void lendingAccountIdNotNull() {
        GuarantorAddResource guarantorAddResource = setGuarantorAddResource();
        guarantorAddResource.setLendingAccountDetail(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(guarantorAddResource, localValidatorFactory));
    }*/
}
