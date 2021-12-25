package com.fusionx.lending.origination.resource;

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

import com.fusionx.lending.origination.utill.TestUtils;

/**
 * 	Financial Statement Detail Note Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   25-09-2021   			 	 		       Nipun       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FinancialStatementDetailNoteResourceTest {

	
private LocalValidatorFactoryBean localValidatorFactory;

public String longText = "The Jews who descended from Abraham were never really the nation we associate with greatness.  They did not conquer and build a great empire like the Romans did or build large monuments like the Egyptians did with the pyramids. Their fame comes from the Law and Boo"
		+ "k which they wrote; from some remarkable individuals that were Jewish; and that they have survived as a somewhat different people group for thousands of years.  Their greatness is not because of anything they did, but rather what was done to and through them. "
		+ "k which they wrote; from some remarkable individuals that were Jewish; and that they have survived as a somewhat different people group for thousands of years.  Their greatness is not because of anything they did, but rather what was done to and through them. "
		+ "k which they wrote; from some remarkable individuals that were Jewish; and that they have survived as a somewhat different people group for thousands of years.  Their greatness is not because of anything they did, but rather what was done to and through them. "
		+ " The promise says repeatedly “I will …” – that would be the power behind the promise.  Their unique greatness happened because God made it happen rather than some ability, conquest or power of their own";
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
	    localValidatorFactory.setProviderClass(HibernateValidator.class);
	    localValidatorFactory.afterPropertiesSet();
	}
	
	public FinancialStatementDetailNoteRequest setFinancialStatementDetailNoteRequest() {
		FinancialStatementDetailNoteRequest  financialStatementDetailNoteRequest = new FinancialStatementDetailNoteRequest();
		financialStatementDetailNoteRequest.setNoteId("1");
		financialStatementDetailNoteRequest.setItemName("name");
		financialStatementDetailNoteRequest.setDescription("desc");
		financialStatementDetailNoteRequest.setValue("10.00");
		financialStatementDetailNoteRequest.setNoOfItem("1");
		return financialStatementDetailNoteRequest;
	}

	
    @Test
    public void noteIdPattern() {
    	FinancialStatementDetailNoteRequest  financialStatementDetailNoteRequest = setFinancialStatementDetailNoteRequest();
    	financialStatementDetailNoteRequest.setNoteId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(financialStatementDetailNoteRequest, localValidatorFactory));
    }
	

    @Test
    public void noOfItemsPattern() {
    	FinancialStatementDetailNoteRequest  financialStatementDetailNoteRequest = setFinancialStatementDetailNoteRequest();
    	financialStatementDetailNoteRequest.setNoOfItem("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(financialStatementDetailNoteRequest, localValidatorFactory));
    }
	
	@Test
    public void itemNameNotNull() {
		FinancialStatementDetailNoteRequest  financialStatementDetailNoteRequest = setFinancialStatementDetailNoteRequest();
		financialStatementDetailNoteRequest.setItemName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(financialStatementDetailNoteRequest, localValidatorFactory));
    }
	
	@Test
    public void itemNamesize() {
		FinancialStatementDetailNoteRequest  financialStatementDetailNoteRequest = setFinancialStatementDetailNoteRequest();
		financialStatementDetailNoteRequest.setItemName(longText);
		assertEquals("{common-name1.size}", TestUtils.getFieldErrorMessageKey(financialStatementDetailNoteRequest, localValidatorFactory));
	}
	
	
	@Test
    public void descriptionSize() {
		FinancialStatementDetailNoteRequest  financialStatementDetailNoteRequest = setFinancialStatementDetailNoteRequest();
		financialStatementDetailNoteRequest.setDescription(longText);
		assertEquals("{common-description.size}", TestUtils.getFieldErrorMessageKey(financialStatementDetailNoteRequest, localValidatorFactory));
	}
	
	@Test
    public void valueNotNull() {
		FinancialStatementDetailNoteRequest  financialStatementDetailNoteRequest = setFinancialStatementDetailNoteRequest();
		financialStatementDetailNoteRequest.setValue(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(financialStatementDetailNoteRequest, localValidatorFactory));
    }

	
	@Test
    public void valuePattern() {
		FinancialStatementDetailNoteRequest  financialStatementDetailNoteRequest = setFinancialStatementDetailNoteRequest();
		financialStatementDetailNoteRequest.setValue("as");
		assertEquals("{common-amount.pattern}", TestUtils.getFieldErrorMessageKey(financialStatementDetailNoteRequest, localValidatorFactory));
    }
	
	
	
	
}
