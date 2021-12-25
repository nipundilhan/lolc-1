package com.fusionx.lending.product.resources;

//import com.fusionx.lending.product.utill.TestUtils;
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
//
//import static org.junit.Assert.assertEquals;

/**
 * Collaterals Add Resource
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   27-10-2021                           Rohan       Created
 * <p>
 * *******************************************************************************************************
 */

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class CollateralsAddResourceTest {
/*
    private LocalValidatorFactoryBean localValidatorFactory;

    @Before
    public void setUp() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }

    private CollateralsAddResource setCollateralsAddResource() {
        CollateralsAddResource collateralsAddResource = new CollateralsAddResource();
        collateralsAddResource.setSequence("test");
        collateralsAddResource.setStatus("ACTIVE");
        collateralsAddResource.setLendingAccountId("234");
        return collateralsAddResource;
    }

    /**
     * owner type is required.
     * Expected: {common.not-null}
     */
 /*   @Test
    public void sequenceNotNull() {
        CollateralsAddResource collateralsAddResource = setCollateralsAddResource();
        collateralsAddResource.setSequence("test");
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(collateralsAddResource, localValidatorFactory));
    }

    /**
     * status is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void statusNotNull() {
        CollateralsAddResource collateralsAddResource = setCollateralsAddResource();
        collateralsAddResource.setStatus(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(collateralsAddResource, localValidatorFactory));
    }

    /**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
 /*   @Test
    public void statusPattern() {
        CollateralsAddResource collateralsAddResource = setCollateralsAddResource();
        collateralsAddResource.setStatus("ABCDEF");
        assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(collateralsAddResource, localValidatorFactory));
    }

    /**
     * lending Account Id is required.
     * Expected: {common.not-null}
     */
 /*   @Test
    public void lendingAccountIdNotNull() {
        CollateralsAddResource collateralsAddResource = setCollateralsAddResource();
        collateralsAddResource.setLendingAccountId(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(collateralsAddResource, localValidatorFactory));
    }*/
}
