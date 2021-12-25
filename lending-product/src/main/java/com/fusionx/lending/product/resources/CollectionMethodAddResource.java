package com.fusionx.lending.product.resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Collection method Add Resource
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-10-2021      	-	     	-		Thushan      Created
 *
 ********************************************************************************************************
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CollectionMethodAddResource {

    @NotBlank(message = "{common.not-null}")
    @Size(max = 50, message = "{common.account-number.size}")
    private String accountId;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "DIRECT_CREDIT|TELLER_RECEIPT|SELLER_FROM_LOAN", message = "{collection-method.pattern}")
    private String collectionMethod;

    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-38-2}" + " into loan amount")
    private String transactionAmount;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCollectionMethod() {
		return collectionMethod;
	}

	public void setCollectionMethod(String collectionMethod) {
		this.collectionMethod = collectionMethod;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

    
}
