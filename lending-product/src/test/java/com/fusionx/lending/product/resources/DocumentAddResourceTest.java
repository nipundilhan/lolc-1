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
 * Documentt Add Resource
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
public class DocumentAddResourceTest {
/*
    private LocalValidatorFactoryBean localValidatorFactory;

    @Before
    public void setUp() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }

    private DocumentAddResource setDocumentAddResource() {
        DocumentAddResource documentAddResource = new DocumentAddResource();
        documentAddResource.setIsPrinted("YES");
        documentAddResource.setTenantId("Test");
        documentAddResource.setStatus("ACTIVE");
        documentAddResource.setLendingAccountDetailId("2");
        return documentAddResource;
    }

    /**
     * isPrinted is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void checkIsPrintNotNull() {
        DocumentAddResource documentAddResource = setDocumentAddResource();
        documentAddResource.setIsPrinted(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(documentAddResource, localValidatorFactory));
    }

    /**
     * isPrinted should be YES or NO.
     * Expected: {common-status.pattern}
     */
/*    @Test
    public void checkMarkPattern() {
        DocumentAddResource documentAddResource = setDocumentAddResource();
        documentAddResource.setIsPrinted("sadsd");
        assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(documentAddResource, localValidatorFactory));
    }

    /**
     * status is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void statusNotNull() {
        DocumentAddResource documentAddResource = setDocumentAddResource();
        documentAddResource.setStatus(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(documentAddResource, localValidatorFactory));
    }

    /**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
/*    @Test
    public void statusPattern() {
        DocumentAddResource documentAddResource = setDocumentAddResource();
        documentAddResource.setStatus("ACTIVE");
        assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(documentAddResource, localValidatorFactory));
    }

    /**
     * lending account ID is required.
     * Expected: {common.not-null}
     */
 /*   @Test
    public void lendingAccountIdNotNull() {
        DocumentAddResource documentAddResource = setDocumentAddResource();
        documentAddResource.setLendingAccountDetailId(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(documentAddResource, localValidatorFactory));
    }*/
}
