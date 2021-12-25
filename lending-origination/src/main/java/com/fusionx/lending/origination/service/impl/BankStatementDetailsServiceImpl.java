package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.BankStatementDetails;
import com.fusionx.lending.origination.domain.BankStatementDocuments;
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.repository.BankStatementDetailsDocumentsRepository;
import com.fusionx.lending.origination.repository.BankStatementDetailsRepository;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.resource.BankResponse;
import com.fusionx.lending.origination.resource.BankStatementDetailsAddResource;
import com.fusionx.lending.origination.resource.BankStatementDetailsUpdateResource;
import com.fusionx.lending.origination.resource.BankStatementDocumentDetailsAddResource;
import com.fusionx.lending.origination.resource.CurencyResponse;
import com.fusionx.lending.origination.resource.ExistsDocumentResponseResource;
import com.fusionx.lending.origination.service.BankStatementDetailsHistoryService;
import com.fusionx.lending.origination.service.BankStatementDetailsService;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.ValidateService;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.enums.ActionType;
import com.fusionx.lending.origination.enums.CommonListReferenceCode;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.TransferType;
import com.fusionx.lending.origination.exception.InvalidDetailListServiceIdException;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;

@Component
@Transactional(rollbackFor = Exception.class)
public class BankStatementDetailsServiceImpl extends MessagePropertyBase implements BankStatementDetailsService {

	@Autowired
	private ValidateService validateService;

	@Autowired
	private CommonListRepository commonListRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private BankStatementDetailsRepository bankStatementDetailsRepository;

	@Autowired
	private RemoteService remoteService;

	@Autowired
	private BankStatementDetailsDocumentsRepository bankStatementDetailsDocumentsRepository;

	@Autowired
	private BankStatementDetailsHistoryService bankStatementDetailsHistoryService;

//	@Value("${comn-currency}")
//	protected String comnCurrencyUrl;

	@Override
	public Page<BankStatementDetails> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<BankStatementDetails> findById(Long id, String tenantId) {
		Optional<BankStatementDetails> isPresentBankStatementDetails = bankStatementDetailsRepository.findById(id);
		if (isPresentBankStatementDetails.isPresent()) {
			return Optional.ofNullable(isPresentBankStatementDetails.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<BankStatementDetails> findByStatus(String status, String tenantId) {
		return bankStatementDetailsRepository.findByStatus(CommonStatus.valueOf(status));
	}

	private Timestamp dateTimeConvertion(String dateFromTo) {
		try {
			DateFormat formatter;
			formatter = new SimpleDateFormat("yyyy-mm-dd");
			Date date = (Date) formatter.parse(dateFromTo);
			java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());

			return timeStampDate;
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
			return null;
		}
	}

	@Override
	public BankStatementDetails addBankStatementDetails(String tenantId, String createdUser,
			BankStatementDetailsAddResource bankStatementDetailsAddResource) {

		CurencyResponse curencyResponse = validateService.validateCurrencyType(tenantId,
				bankStatementDetailsAddResource.getCurrencyId(), bankStatementDetailsAddResource.getCurrencyName());

		Optional<Customer> customer = this.customerRepository
				.findById(Long.parseLong(bankStatementDetailsAddResource.getCustomerId()));
		if (!customer.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "customerId");
		}

		BankResponse bankResponse = validateService.validateCompany(tenantId,
				Long.parseLong(bankStatementDetailsAddResource.getBankId()),
				bankStatementDetailsAddResource.getBankName());

		CommonList accountType = this.validateAccountType(
				validateService.stringToLong(bankStatementDetailsAddResource.getAccountTypeId()),
				bankStatementDetailsAddResource.getAccountTypeName());

		BankStatementDetails bankStatementDetails = new BankStatementDetails();
		bankStatementDetails.setTenantId(tenantId);
		bankStatementDetails.setBankId(bankResponse.getId());
		bankStatementDetails.setBankName(bankResponse.getBankName());
		bankStatementDetails.setCustomer(customer.get());
		bankStatementDetails.setAccountTypeId(accountType.getId());
		bankStatementDetails.setAccountTypeName(accountType.getName());
		bankStatementDetails.setAccountNumber(Long.parseLong(bankStatementDetailsAddResource.getAccountNumber()));
		bankStatementDetails.setPeriodFrom(dateTimeConvertion(bankStatementDetailsAddResource.getPeriodFrom()));
		bankStatementDetails.setPeriodTo(dateTimeConvertion(bankStatementDetailsAddResource.getPeriodTo()));
		bankStatementDetails.setCurrencyId(curencyResponse.getCurrencyId().toString());
		bankStatementDetails.setCurrencyName(curencyResponse.getCurrencyName());
		bankStatementDetails.setOpeningBalance(bankStatementDetailsAddResource.getOpeningBalance());
		bankStatementDetails.setCloseBalance(bankStatementDetailsAddResource.getCloseBalance());
		bankStatementDetails.setMoneyIn(bankStatementDetailsAddResource.getMoneyIn());
		bankStatementDetails.setMoneyOut(bankStatementDetailsAddResource.getMoneyOut());
		bankStatementDetails.setStatus(CommonStatus.valueOf(bankStatementDetailsAddResource.getStatus()));
		bankStatementDetails.setCreatedDate(validateService.getCreateOrModifyDate());
		bankStatementDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		bankStatementDetails.setSyncTs(validateService.getCreateOrModifyDate());
		bankStatementDetailsRepository.save(bankStatementDetails);

		for (BankStatementDocumentDetailsAddResource bankStatementDocumentDetailsAddResource : bankStatementDetailsAddResource
				.getDocuments()) {
			Integer index = 0;
			validateService.validateDocument(tenantId, bankStatementDocumentDetailsAddResource.getDocumentId(),
					bankStatementDocumentDetailsAddResource.getDocumentName(), ServicePoint.BANK_STATEMENT_DETAILS,
					Constants.ORIGIN_CUSTOMER, index);

			BankStatementDocuments bankStatementDocuments = new BankStatementDocuments();
			bankStatementDocuments.setTenantId(tenantId);
			bankStatementDocuments.setBankStatementDetails(bankStatementDetails);
			bankStatementDocuments
					.setDocumentId(Long.parseLong(bankStatementDocumentDetailsAddResource.getDocumentId()));
			bankStatementDocuments.setDocumentName(bankStatementDocumentDetailsAddResource.getDocumentName());
			bankStatementDocuments.setStatus(bankStatementDocumentDetailsAddResource.getStatus());
			bankStatementDocuments.setCreatedUser(createdUser);
			bankStatementDocuments.setCreatedDate(validateService.getCreateOrModifyDate());
			bankStatementDocuments.setSyncTs(validateService.getCreateOrModifyDate());
			bankStatementDetailsDocumentsRepository.save(bankStatementDocuments);
			index++;
		}

		return bankStatementDetails;
	}

	private void validateBankStatementDetailsDocUpload(String tenantId, String createdUser,
			BankStatementDetails bankStatementDetails,
			List<BankStatementDocumentDetailsAddResource> bankStatementDocumentDetailsAddResource, ActionType save) {
		Integer index = 0;
		for (BankStatementDocumentDetailsAddResource bankStatementDocumentDetailsAddResource2 : bankStatementDocumentDetailsAddResource) {

			LoggerRequest.getInstance().logInfo(
					"BankStatementDocumentDetails********************************Validate Document Id***************************");
			validateDocumentId(tenantId, bankStatementDocumentDetailsAddResource2.getDocumentId(),
					bankStatementDocumentDetailsAddResource2.getDocumentName(), index, ActionType.SAVE);

			LoggerRequest.getInstance().logInfo(
					"BankStatementDocumentDetails********************************Save Documents***********************");
			saveBankStatementDocumenrDetails(tenantId, createdUser, bankStatementDetails,
					bankStatementDocumentDetailsAddResource2);

			index++;
		}

	}

	private void validateBankStatementDocUploadUpdate(String tenantId, String createdUser,
			BankStatementDetails bankStatementDetails,
			List<BankStatementDocumentDetailsAddResource> bankStatementDocumentDetailsAddResource, ActionType save) {
		Integer index = 0;
		for (BankStatementDocumentDetailsAddResource bankStatementDocumentDetailsAddResource2 : bankStatementDocumentDetailsAddResource) {
			if (bankStatementDocumentDetailsAddResource2.getDocumentId() == null) {
				LoggerRequest.getInstance().logInfo(
						"BankStatementDocumentDetails********************************Validate Document Id***************************");
				validateDocumentId(tenantId, bankStatementDocumentDetailsAddResource2.getDocumentId(),
						bankStatementDocumentDetailsAddResource2.getDocumentName(), index, ActionType.SAVE);

				LoggerRequest.getInstance().logInfo(
						"BankStatementDocumentDetails********************************Save Documents***********************");
				saveBankStatementDocumenrDetails(tenantId, createdUser, bankStatementDetails,
						bankStatementDocumentDetailsAddResource2);
			} else {

				LoggerRequest.getInstance().logInfo(
						"BankStatementDocumentDetails********************************Validate Document Id***************************");
				validateDocumentId(tenantId, bankStatementDocumentDetailsAddResource2.getDocumentId(),
						bankStatementDocumentDetailsAddResource2.getDocumentName(), index, ActionType.UPDATE);

				LoggerRequest.getInstance().logInfo(
						"BankStatementDocumentDetails********************************Save Documents***********************");
				updateBankStatementDocumentDetails(tenantId, createdUser, bankStatementDetails,
						bankStatementDocumentDetailsAddResource2);

			}
			index++;
		}

	}

	private void saveBankStatementDocumenrDetails(String tenantId, String createdUser,
			BankStatementDetails bankStatementDetails,
			BankStatementDocumentDetailsAddResource bankStatementDocumentDetailsAddResource) {
		BankStatementDocuments bankStatementDocuments = new BankStatementDocuments();
		bankStatementDocuments.setTenantId(tenantId);
		bankStatementDocuments.setBankStatementDetails(bankStatementDetails);
		bankStatementDocuments.setDocumentId(Long.parseLong(bankStatementDocumentDetailsAddResource.getDocumentId()));
		bankStatementDocuments.setDocumentName(bankStatementDocumentDetailsAddResource.getDocumentName());
		bankStatementDocuments.setStatus(bankStatementDocumentDetailsAddResource.getStatus());
		bankStatementDocuments.setCreatedUser(createdUser);
		bankStatementDocuments.setCreatedDate(validateService.getCreateOrModifyDate());
		bankStatementDocuments.setSyncTs(validateService.getCreateOrModifyDate());
		bankStatementDocuments = bankStatementDetailsDocumentsRepository.saveAndFlush(bankStatementDocuments);

	}

	private void updateBankStatementDocumentDetails(String tenantId, String createdUser,
			BankStatementDetails bankStatementDetails,
			BankStatementDocumentDetailsAddResource bankStatementDocumentDetailsAddResource) {

		Optional<BankStatementDocuments> isPresentBankStatementDocumentsOld = bankStatementDetailsDocumentsRepository
				.findById(Long.parseLong(bankStatementDocumentDetailsAddResource.getId()));
		if (!isPresentBankStatementDocumentsOld.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty("common.record-not-found"),
					ServiceEntity.BANK_STATEMENT_DETAILS);
		}
		BankStatementDocuments BankStatementDocumentsOld = isPresentBankStatementDocumentsOld.get();
		BankStatementDocumentsOld.setTenantId(tenantId);
		BankStatementDocumentsOld.setBankStatementDetails(bankStatementDetails);
		BankStatementDocumentsOld
				.setDocumentId(Long.parseLong(bankStatementDocumentDetailsAddResource.getDocumentId()));
		BankStatementDocumentsOld.setDocumentName(bankStatementDocumentDetailsAddResource.getDocumentName());
		BankStatementDocumentsOld.setStatus(bankStatementDocumentDetailsAddResource.getStatus());
		BankStatementDocumentsOld.setModifiedUser(createdUser);
		BankStatementDocumentsOld.setModifiedDate(validateService.getCreateOrModifyDate());
		BankStatementDocumentsOld.setSyncTs(validateService.getCreateOrModifyDate());
		bankStatementDetailsDocumentsRepository.saveAndFlush(BankStatementDocumentsOld);

	}

	private void validateDocumentId(String tenantId, String documentId, String documentName, Integer index,
			ActionType actionType) {
		ExistsDocumentResponseResource existsDocumentResponseResource = remoteService.existDocument(tenantId,
				documentId, Constants.ORIGIN_MICRO_BPR);
		if (actionType.equals(ActionType.SAVE)) {
			if (existsDocumentResponseResource == null || existsDocumentResponseResource.getServiceStatus() == null) {
				if (existsDocumentResponseResource == null || !existsDocumentResponseResource.getValue())
					throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "documentId");
			} else {
				remoteService.serviceValidationListExceptionHadle(existsDocumentResponseResource.getServiceStatus(),
						ServiceEntity.DOCUMENT_ID, ServicePoint.BANK_STATEMENT_DETAILS, index);
			}
		} else if (actionType.equals(ActionType.UPDATE)) {
			if (existsDocumentResponseResource == null || existsDocumentResponseResource.getServiceStatus() == null) {
				if (existsDocumentResponseResource == null || !existsDocumentResponseResource.getValue())
					throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "documentId");
			} else {
				remoteService.serviceValidationListExceptionHadle(existsDocumentResponseResource.getServiceStatus(),
						ServiceEntity.DOCUMENT_ID, ServicePoint.BANK_STATEMENT_DETAILS, index);
			}
		}

	}

	@Override
	public BankStatementDetails updateBankStatementDetails(String tenantId, Long id, String createdUser,
			BankStatementDetailsUpdateResource bankStatementDetailsUpdateResource) {

		Optional<BankStatementDetails> isPresentBankStatementDetails = bankStatementDetailsRepository.findById(id);

		CurencyResponse curencyResponse = validateService.validateCurrencyType(tenantId,
				bankStatementDetailsUpdateResource.getCurrencyId(),
				bankStatementDetailsUpdateResource.getCurrencyName());

		bankStatementDetailsHistoryService.saveHistory(tenantId, isPresentBankStatementDetails.get(),
				LogginAuthentcation.getInstance().getUserName());

		BankStatementDetails bankStatementDetails = isPresentBankStatementDetails.get();
		bankStatementDetails.setTenantId(tenantId);
		bankStatementDetails.setBankId(Long.parseLong(bankStatementDetailsUpdateResource.getBankId()));
		bankStatementDetails.setBankName(bankStatementDetailsUpdateResource.getBankName());
		bankStatementDetails.setAccountTypeId(Long.parseLong(bankStatementDetailsUpdateResource.getAccountTypeId()));
		bankStatementDetails.setAccountTypeName(bankStatementDetailsUpdateResource.getAccountTypeName());
		bankStatementDetails.setAccountNumber(Long.parseLong(bankStatementDetailsUpdateResource.getAccountNumber()));
		bankStatementDetails.setPeriodFrom(dateTimeConvertion(bankStatementDetailsUpdateResource.getPeriodFrom()));
		bankStatementDetails.setPeriodTo(dateTimeConvertion(bankStatementDetailsUpdateResource.getPeriodTo()));
		bankStatementDetails.setCurrencyId(curencyResponse.getCurrencyId().toString());
		bankStatementDetails.setCurrencyName(curencyResponse.getCurrencyName());
		bankStatementDetails.setOpeningBalance(bankStatementDetailsUpdateResource.getOpeningBalance());
		bankStatementDetails.setCloseBalance(bankStatementDetailsUpdateResource.getCloseBalance());
		bankStatementDetails.setMoneyIn(bankStatementDetailsUpdateResource.getMoneyIn());
		bankStatementDetails.setMoneyOut(bankStatementDetailsUpdateResource.getMoneyOut());
		bankStatementDetails.setStatus(CommonStatus.valueOf(bankStatementDetailsUpdateResource.getStatus()));
		bankStatementDetails.setModifiedDate(validateService.getCreateOrModifyDate());
		bankStatementDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		bankStatementDetails.setSyncTs(validateService.getCreateOrModifyDate());

		if (bankStatementDetailsUpdateResource.getDocuments() != null
				&& bankStatementDetailsUpdateResource.getDocuments().isEmpty()) {
			validateBankStatementDocUploadUpdate(tenantId, createdUser, bankStatementDetails,
					bankStatementDetailsUpdateResource.getDocuments(), ActionType.SAVE);
		}

		return bankStatementDetailsRepository.save(bankStatementDetails);
	}

	@Override
	public List<BankStatementDetails> getByCustometId(Long customerId) {
		return this.bankStatementDetailsRepository.findByCustomerId(customerId);
	}

	@Override
	public List<BankStatementDetails> findAll() {

		return this.bankStatementDetailsRepository.findAll();
	}

	public CommonList validateAccountType(Long id, String name) {

		Optional<CommonList> commonListItem = commonListRepository.findById(id);
		if (!commonListItem.isPresent())
			throw new ValidateRecordException(environment.getProperty(NOT_FOUND), "accountTypeId");

		else if (!CommonStatus.ACTIVE.toString().equalsIgnoreCase(commonListItem.get().getStatus()))
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "accountTypeId");

		else if (!CommonListReferenceCode.STMT_ACC_TYPES.toString()
				.equalsIgnoreCase(commonListItem.get().getReferenceCode())
				|| !commonListItem.get().getName().equalsIgnoreCase(name))
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "accountTypeId");

		return commonListItem.get();
	}

}
