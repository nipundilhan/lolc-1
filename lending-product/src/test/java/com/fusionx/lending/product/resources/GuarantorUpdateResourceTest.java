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
 * Guarantor Update Resource
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
public class GuarantorUpdateResourceTest {

/*
    private LocalValidatorFactoryBean localValidatorFactory;

    @Before
    public void setUp() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }

    private GuarantorUpdateResource setGuarantorUpdateResource() {
        GuarantorUpdateResource guarantorUpdateResource = new GuarantorUpdateResource();
        guarantorUpdateResource.setSequence("test");
        guarantorUpdateResource.setGuaranteePercentage("12");
        guarantorUpdateResource.setValue("12");
        guarantorUpdateResource.setBondNumber("2");
        guarantorUpdateResource.setTenantId("test");
        guarantorUpdateResource.setStatus("ACTIVE");
        guarantorUpdateResource.setLendingAccountDetail("2");
        return guarantorUpdateResource;
    }

    /**
     * version is required.
     * Expected: {version.not-null}
     */
/*    @Test
    public void versionNotNull() {
        GuarantorUpdateResource guarantorUpdateResource = setGuarantorUpdateResource();
        guarantorUpdateResource.setVersion(null);
        assertEquals("{version.not-null}", TestUtils.getFieldErrorMessageKey(guarantorUpdateResource, localValidatorFactory));
    }

    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {version.pattern}
     */
 /*   @Test
    public void versionPattern() {
        GuarantorUpdateResource guarantorUpdateResource = setGuarantorUpdateResource();
        guarantorUpdateResource.setVersion("A");
        assertEquals("{version.pattern}", TestUtils.getFieldErrorMessageKey(guarantorUpdateResource, localValidatorFactory));
    }*/
}
