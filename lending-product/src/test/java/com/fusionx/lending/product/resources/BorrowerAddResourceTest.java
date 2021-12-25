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

import static org.junit.Assert.assertEquals;

/**
 * Borrower AddR resource
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
public class BorrowerAddResourceTest {
/*
    private LocalValidatorFactoryBean localValidatorFactory;

    @Before
    public void setUp() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }

    private BorrowersAddResource setBorrowersAddResource() {
        BorrowersAddResource borrowersAddResource = new BorrowersAddResource();
        borrowersAddResource.setOwnershipType("test");
        borrowersAddResource.setStatus("ACTIVE");
        borrowersAddResource.setCustomerId("1234");
        borrowersAddResource.setLendingAccountId("234");
        return borrowersAddResource;
    }

    /**
     * owner type is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void ownerShipNotNull() {
        BorrowersAddResource borrowersAddResource = setBorrowersAddResource();
        borrowersAddResource.setOwnershipType("test");
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(borrowersAddResource, localValidatorFactory));
    }*/

    /**
     * status is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void statusNotNull() {
        BorrowersAddResource borrowersAddResource = setBorrowersAddResource();
        borrowersAddResource.setStatus(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(borrowersAddResource, localValidatorFactory));
    }*/

    /**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
/*    @Test
    public void statusPattern() {
        BorrowersAddResource borrowersAddResource = setBorrowersAddResource();
        borrowersAddResource.setStatus("ABCDEF");
        assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(borrowersAddResource, localValidatorFactory));
    }*/

    /**
     * customer Id is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void customerIdNotNull() {
        BorrowersAddResource borrowersAddResource = setBorrowersAddResource();
        borrowersAddResource.setCustomerId(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(borrowersAddResource, localValidatorFactory));
    }*/

    /**
     * lending Account Id is required.
     * Expected: {common.not-null}
     */
/*    @Test
    public void lendingAccountIdNotNull() {
        BorrowersAddResource borrowersAddResource = setBorrowersAddResource();
        borrowersAddResource.setLendingAccountId(null);
        assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(borrowersAddResource, localValidatorFactory));
    }
*/
}
