package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;

@Entity
@Table(name = "bank_statement_details_history")
@Data
public class BankStatementDetailsHistory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2496554410001779344L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@Column(name = "currency_id")
	private String currencyId;

	@Column(name = "currency_name", length = 70, nullable = false)
	private String currencyName;

	@Column(name = "bank_statement_details_id")
	private Long bankStatementDetailsId;

	@Column(name = "bank_id")
	private Long bankId;

	@Column(name = "bank_name", length = 70, nullable = false)
	private String bankName;

	@Column(name = "account_type_id")
	private Long accountTypeId;

	@Column(name = "account_type_name", length = 70, nullable = false)
	private String accountTypeName;

	@Column(name = "account_number")
	private Long accountNumber;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private CommonStatus status;

	@Column(name = "period_from", nullable = false)
	private Timestamp periodFrom;

	@Column(name = "period_to", nullable = false)
	private Timestamp periodTo;

	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", nullable = true, length = 255)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Column(name = "opening_balance")
	private String openingBalance;

	@Column(name = "close_balance")
	private String closeBalance;

	@Column(name = "money_in")
	private String moneyIn;

	@Column(name = "money_out")
	private String moneyOut;

	@Column(name = "history_created_user", length = 255, nullable = false)
	private String historyCreatedUser;

	@Column(name = "history_created_date", nullable = false)
	private Timestamp historyCreatedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Long getBankStatementDetailsId() {
		return bankStatementDetailsId;
	}

	public void setBankStatementDetailsId(Long bankStatementDetailsId) {
		this.bankStatementDetailsId = bankStatementDetailsId;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(Long accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public Timestamp getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(Timestamp periodFrom) {
		this.periodFrom = periodFrom;
	}

	public Timestamp getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(Timestamp periodTo) {
		this.periodTo = periodTo;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(String openingBalance) {
		this.openingBalance = openingBalance;
	}

	public String getCloseBalance() {
		return closeBalance;
	}

	public void setCloseBalance(String closeBalance) {
		this.closeBalance = closeBalance;
	}

	public String getMoneyIn() {
		return moneyIn;
	}

	public void setMoneyIn(String moneyIn) {
		this.moneyIn = moneyIn;
	}

	public String getMoneyOut() {
		return moneyOut;
	}

	public void setMoneyOut(String moneyOut) {
		this.moneyOut = moneyOut;
	}

	public String getHistoryCreatedUser() {
		return historyCreatedUser;
	}

	public void setHistoryCreatedUser(String historyCreatedUser) {
		this.historyCreatedUser = historyCreatedUser;
	}

	public Timestamp getHistoryCreatedDate() {
		return historyCreatedDate;
	}

	public void setHistoryCreatedDate(Timestamp historyCreatedDate) {
		this.historyCreatedDate = historyCreatedDate;
	}

}
