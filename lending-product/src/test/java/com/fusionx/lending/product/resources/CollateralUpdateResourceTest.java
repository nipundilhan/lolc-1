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
 * Collateral Update Resource
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   27-10-2021                  -        Rohan       Created
 * <p>
 * *******************************************************************************************************
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class CollateralUpdateResourceTest {

/*
    private LocalValidatorFactoryBean localValidatorFactory;

    @Before
    public void setUp() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }

    private CollateralsUpdateResource setCollateralsUpdateResource() {
        CollateralsUpdateResource collateralsUpdateResource = new CollateralsUpdateResource();
        collateralsUpdateResource.setSequence("Test");
        collateralsUpdateResource.setStatus("ACTIVE");
        collateralsUpdateResource.setLendingAccountId("2");
        return collateralsUpdateResource;
    }

    /**
     * version is required.
     * Expected: {version.not-null}
     */
/*    @Test
    public void versionNotNull() {
        CollateralsUpdateResource collateralsUpdateResource = setCollateralsUpdateResource();
        collateralsUpdateResource.setVersion(null);
        assertEquals("{version.not-null}", TestUtils.getFieldErrorMessageKey(collateralsUpdateResource, localValidatorFactory));
    }

    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {version.pattern}
     */
 /*   @Test
    public void versionPattern() {
        CollateralsUpdateResource collateralsUpdateResource = setCollateralsUpdateResource();
        collateralsUpdateResource.setVersion("A");
        assertEquals("{version.pattern}", TestUtils.getFieldErrorMessageKey(collateralsUpdateResource, localValidatorFactory));
    }*/
}
