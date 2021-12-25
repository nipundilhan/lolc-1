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
 * Document Update Resource
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
public class DocumentUpdateResourceTest {

/*
    private LocalValidatorFactoryBean localValidatorFactory;

    @Before
    public void setUp() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }

    private DocumentUpdateResource setDocumentUpdateResource() {
        DocumentUpdateResource documentUpdateResource = new DocumentUpdateResource();
        documentUpdateResource.setIsPrinted("YES");
        documentUpdateResource.setTenantId("Test");
        documentUpdateResource.setStatus("ACTIVE");
        documentUpdateResource.setLendingAccountDetailId("2");
        return documentUpdateResource;
    }

    /**
     * version is required.
     * Expected: {version.not-null}
     */
/*    @Test
    public void versionNotNull() {
        DocumentUpdateResource documentUpdateResource = setDocumentUpdateResource();
        documentUpdateResource.setVersion(null);
        assertEquals("{version.not-null}", TestUtils.getFieldErrorMessageKey(documentUpdateResource, localValidatorFactory));
    }

    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {version.pattern}
     */
/*    @Test
    public void versionPattern() {
        DocumentUpdateResource documentUpdateResource = setDocumentUpdateResource();
        documentUpdateResource.setVersion("A");
        assertEquals("{version.pattern}", TestUtils.getFieldErrorMessageKey(documentUpdateResource, localValidatorFactory));
    }*/
}
