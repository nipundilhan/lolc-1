package com.fusionx.lending.transaction.resource;

import static org.junit.Assert.assertEquals;

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
//import com.fusionx.lending.transaction.utill.TestUtils;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreditNoteTransactionTypeAddResourceTest {
	
/*	
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private CreditNoteTransactionTypeAddResource setCreditNoteTransactionTypeAddResource() {
		CreditNoteTransactionTypeAddResource creditNoteTransactionTypeAddResource = new CreditNoteTransactionTypeAddResource();
		creditNoteTransactionTypeAddResource.setCreditNoteTypeId("1");
		creditNoteTransactionTypeAddResource.setIsDebitBalanceEnable("YES");
		creditNoteTransactionTypeAddResource.setPostingType("CREDIT");
		creditNoteTransactionTypeAddResource.setTransactionSubCode("ABC");
		creditNoteTransactionTypeAddResource.setTransactionSubCodeId("1");
		creditNoteTransactionTypeAddResource.setStatus("ACTIVE");		
		return creditNoteTransactionTypeAddResource;
	}
	
    @Test
	public void creditNoteTypeIdNotNull() {
    	CreditNoteTransactionTypeAddResource creditNoteTransactionTypeAddResource = setCreditNoteTransactionTypeAddResource();
    	creditNoteTransactionTypeAddResource.setCreditNoteTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(creditNoteTransactionTypeAddResource, localValidatorFactory));
	}

    @Test
	public void  creditNoteTypeIdPattern() {
    	CreditNoteTransactionTypeAddResource creditNoteTransactionTypeAddResource = setCreditNoteTransactionTypeAddResource();
    	creditNoteTransactionTypeAddResource.setCreditNoteTypeId("1");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(creditNoteTransactionTypeAddResource, localValidatorFactory));
	}
    
    @Test
	public void postingTypeNotNull() {
    	CreditNoteTransactionTypeAddResource creditNoteTransactionTypeAddResource = setCreditNoteTransactionTypeAddResource();
    	creditNoteTransactionTypeAddResource.setPostingType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(creditNoteTransactionTypeAddResource, localValidatorFactory));
	}

    @Test
	public void  postingTypePattern() {
    	CreditNoteTransactionTypeAddResource creditNoteTransactionTypeAddResource = setCreditNoteTransactionTypeAddResource();
    	creditNoteTransactionTypeAddResource.setPostingType("A");
		assertEquals("{creditDebitIndicator.pattern}", TestUtils.getFieldErrorMessageKey(creditNoteTransactionTypeAddResource, localValidatorFactory));
	}
    
    
    @Test
	public void statusNull() {
    	CreditNoteTransactionTypeAddResource creditNoteTransactionTypeAddResource = setCreditNoteTransactionTypeAddResource();
    	creditNoteTransactionTypeAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(creditNoteTransactionTypeAddResource, localValidatorFactory));
	}

    @Test
	public void  statusPattern() {
    	CreditNoteTransactionTypeAddResource creditNoteTransactionTypeAddResource = setCreditNoteTransactionTypeAddResource();
    	creditNoteTransactionTypeAddResource.setStatus("A");
		assertEquals("{common.status-pattern}", TestUtils.getFieldErrorMessageKey(creditNoteTransactionTypeAddResource, localValidatorFactory));
	}
    
    
    @Test
	public void transactionSubCodeNull() {
    	CreditNoteTransactionTypeAddResource creditNoteTransactionTypeAddResource = setCreditNoteTransactionTypeAddResource();
    	creditNoteTransactionTypeAddResource.setTransactionSubCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(creditNoteTransactionTypeAddResource, localValidatorFactory));
	}

    @Test
	public void  transactionSubCodeSize() {
    	CreditNoteTransactionTypeAddResource creditNoteTransactionTypeAddResource = setCreditNoteTransactionTypeAddResource();
    	creditNoteTransactionTypeAddResource.setTransactionSubCode("AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA");
		assertEquals("{subCode.size}", TestUtils.getFieldErrorMessageKey(creditNoteTransactionTypeAddResource, localValidatorFactory));
	}


*/
}
