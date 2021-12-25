package com.fusionx.lending.origination.service.impl;

/**
 * 	Other Income Expenses
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-09-2021   FXL-641  	 FXL-792       Dilki        Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.OtherIncomeDetails;
import com.fusionx.lending.origination.domain.OtherIncomeExpenseType;
import com.fusionx.lending.origination.domain.OtherIncomeExpenses;
import com.fusionx.lending.origination.domain.IncomeType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.OtherIncomeDetailsRepository;
import com.fusionx.lending.origination.repository.OtherIncomeExpenseTypeRepository;
import com.fusionx.lending.origination.repository.OtherIncomeExpensesRepository;
import com.fusionx.lending.origination.repository.IncomeTypeRepository;
import com.fusionx.lending.origination.resource.FrequencyResponse;
import com.fusionx.lending.origination.resource.OtherIncomeExpensesAddResource;
import com.fusionx.lending.origination.resource.OtherIncomeExpensesResource;
import com.fusionx.lending.origination.resource.CurencyResponse;
import com.fusionx.lending.origination.service.OtherIncomeExpensesService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class OtherIncomeExpensesServiceImpl extends MessagePropertyBase implements OtherIncomeExpensesService {

	private static final String OTHER_INCOME_TYPE_ID = "otherIncomeTypeId";

	private OtherIncomeExpensesRepository otherIncomeExpensesRepository;

	@Autowired
	public void setOtherIncomeExpensesRepository(OtherIncomeExpensesRepository otherIncomeExpensesRepository) {
		this.otherIncomeExpensesRepository = otherIncomeExpensesRepository;
	}

	private ValidateService validateService;

	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	private OtherIncomeDetailsRepository otherIncomeDetailsRepository;

	@Autowired
	public void setOtherIncomeDetailsRepository(OtherIncomeDetailsRepository otherIncomeDetailsRepository) {
		this.otherIncomeDetailsRepository = otherIncomeDetailsRepository;
	}

	@Autowired
	private IncomeTypeRepository incomeTypeRepository;

	@Autowired
	private OtherIncomeExpenseTypeRepository otherExpenseTypeRepository;

	@Override
	public Optional<OtherIncomeExpenses> findById(Long id) {
		Optional<OtherIncomeExpenses> isPresentOtherIncomeExpenses = otherIncomeExpensesRepository.findById(id);

		if (isPresentOtherIncomeExpenses.isPresent()) {
			return Optional.ofNullable(setOtherIncomeExpenseDetails(isPresentOtherIncomeExpenses.get()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<OtherIncomeExpenses> findAll() {
		List<OtherIncomeExpenses> otherIncomeExpensesList = otherIncomeExpensesRepository.findAll();

		for (OtherIncomeExpenses otherIncomeExpenses : otherIncomeExpensesList) {
			setOtherIncomeExpenseDetails(otherIncomeExpenses);
		}
		return otherIncomeExpensesList;
	}

	@Override
	public List<OtherIncomeExpenses> findByStatus(String status) {
		List<OtherIncomeExpenses> otherIncomeExpensesList = otherIncomeExpensesRepository
				.findByStatus(CommonStatus.valueOf(status));

		for (OtherIncomeExpenses otherIncomeExpenses : otherIncomeExpensesList) {
			setOtherIncomeExpenseDetails(otherIncomeExpenses);
		}
		return otherIncomeExpensesList;
	}

	@Override
	public void saveOtherIncome(String tenantId, OtherIncomeExpensesAddResource otherIncomeExpensesAddResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");

		Optional<IncomeType> incomeType = Optional.empty();
		Optional<OtherIncomeExpenseType> expenseType = Optional.empty();

		Optional<OtherIncomeDetails> isPresentOtherIncomeDetails = otherIncomeDetailsRepository
				.findById(Long.parseLong(otherIncomeExpensesAddResource.getOtherIncomeDetailsId()));
		if (!isPresentOtherIncomeDetails.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "otherIncomeDetailId");

		if (otherIncomeExpensesAddResource.getOtherIncomeExpenses() != null
				&& !otherIncomeExpensesAddResource.getOtherIncomeExpenses().isEmpty()) {

			int index = 0;
			for (OtherIncomeExpensesResource otherIncomeExpenseTypeResource : otherIncomeExpensesAddResource
					.getOtherIncomeExpenses()) {

				if (otherIncomeExpenseTypeResource.getIncomeTypeId() != null
						&& !otherIncomeExpenseTypeResource.getIncomeTypeId().isEmpty()
						&& otherIncomeExpenseTypeResource.getExpenseTypeId() != null
						&& !otherIncomeExpenseTypeResource.getExpenseTypeId().isEmpty()) {
					throw new ValidateRecordException(
							environment.getProperty(INVALID_INCOME_EXPENSE_TYPE_NOT_EMPTY),"message");

				} else if ((otherIncomeExpenseTypeResource.getIncomeTypeId() == null
						|| otherIncomeExpenseTypeResource.getIncomeTypeId().isEmpty())
						&& (otherIncomeExpenseTypeResource.getExpenseTypeId() == null
								|| otherIncomeExpenseTypeResource.getExpenseTypeId().isEmpty())) {
					throw new ValidateRecordException(environment.getProperty(INVALID_INCOME_EXPENSE_TYPE_EMPTY),"message");
				}

				if (otherIncomeExpenseTypeResource.getIncomeTypeId() != null
						&& !otherIncomeExpenseTypeResource.getIncomeTypeId().isEmpty()) {
					incomeType = incomeTypeRepository.findByIdAndStatus(
							Long.parseLong(otherIncomeExpenseTypeResource.getIncomeTypeId()),
							CommonStatus.ACTIVE.toString());
					if (!incomeType.isPresent()) {
						throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),"incomeTypeId");
					}

					if (!incomeType.get().getName()
							.equalsIgnoreCase(otherIncomeExpenseTypeResource.getIncomeTypeName())) {
						throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH),"incomeTypeName");
					}
				}

				if (otherIncomeExpenseTypeResource.getExpenseTypeId() != null
						&& !otherIncomeExpenseTypeResource.getExpenseTypeId().isEmpty()) {
					expenseType = otherExpenseTypeRepository.findByIdAndStatus(
							Long.parseLong(otherIncomeExpenseTypeResource.getExpenseTypeId()),
							CommonStatus.ACTIVE.toString());
					if (!expenseType.isPresent()) {
						throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),"expenseTypeId");
					}
				}

				FrequencyResponse frequencyResponse = validateService.validateFrequency(tenantId,
						otherIncomeExpenseTypeResource.getFrequencyId());
				if (!frequencyResponse.getCode().equals(otherIncomeExpenseTypeResource.getFrequencyCode())) {
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "frequencyCode");

				}

				CurencyResponse curencyResponse = validateService.validateCurrencyType(tenantId,
						otherIncomeExpenseTypeResource.getCurrencyId(),
						otherIncomeExpenseTypeResource.getCurrencyCode());
				if (!curencyResponse.getCurrencyCode().equals(otherIncomeExpenseTypeResource.getCurrencyCode())) {
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "currencyCode");
				}

				OtherIncomeExpenses otherIncomeExpenses = new OtherIncomeExpenses();
				otherIncomeExpenses.setTenantId(tenantId);
				otherIncomeExpenses.setOtherIncomeDetailsId(isPresentOtherIncomeDetails.get());
				otherIncomeExpenses.setIncomeTypeId(incomeType.isPresent() ? incomeType.get() : null);
				otherIncomeExpenses.setExpenseTypeId(expenseType.isPresent() ? expenseType.get() : null);
				otherIncomeExpenses
						.setAmount(validateService.stringToBigDecimal(otherIncomeExpenseTypeResource.getAmount()));
				otherIncomeExpenses.setFrequencyId(Long.parseLong(frequencyResponse.getId()));
				otherIncomeExpenses.setFrequencyCode(frequencyResponse.getCode());
				otherIncomeExpenses.setCurrencyId(curencyResponse.getCurrencyId());
				otherIncomeExpenses.setCurrencyCode(curencyResponse.getCurrencyCode());
				otherIncomeExpenses.setCurrencyCodeNumeric(curencyResponse.getCodeNumeric());
				otherIncomeExpenses.setDescription(otherIncomeExpenseTypeResource.getDescription());
				otherIncomeExpenses.setStatus(CommonStatus.valueOf(otherIncomeExpenseTypeResource.getStatus()));
				otherIncomeExpenses.setSyncTs(validateService.getSyncTs());
				otherIncomeExpenses.setCreatedDate(validateService.getCreateOrModifyDate());
				otherIncomeExpenses.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				otherIncomeExpensesRepository.save(otherIncomeExpenses);

				index++;
			}
		} else {
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "otherIncomeExenseTypes");
		}
	}

	@Override
	public OtherIncomeExpenses update(String tenantId, Long id,
			OtherIncomeExpensesResource otherIncomeExpenseTypeResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");

		Optional<IncomeType> incomeType = Optional.empty();
		Optional<OtherIncomeExpenseType> expenseType = Optional.empty();

		Optional<OtherIncomeExpenses> isPresentOtherIncomeExpenses = otherIncomeExpensesRepository.findById(id);
		if (!isPresentOtherIncomeExpenses.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "otherIncomeExpensesId");

		if (otherIncomeExpenseTypeResource.getIncomeTypeId() != null
				&& !otherIncomeExpenseTypeResource.getIncomeTypeId().isEmpty()
				&& otherIncomeExpenseTypeResource.getExpenseTypeId() != null
				&& !otherIncomeExpenseTypeResource.getExpenseTypeId().isEmpty()) {
			throw new ValidateRecordException(environment.getProperty(INVALID_INCOME_EXPENSE_TYPE_NOT_EMPTY),
					OTHER_INCOME_TYPE_ID);

		} else if ((otherIncomeExpenseTypeResource.getIncomeTypeId() == null
				|| otherIncomeExpenseTypeResource.getIncomeTypeId().isEmpty())
				&& (otherIncomeExpenseTypeResource.getExpenseTypeId() == null
						|| otherIncomeExpenseTypeResource.getExpenseTypeId().isEmpty())) {
			throw new ValidateRecordException(environment.getProperty(INVALID_INCOME_EXPENSE_TYPE_EMPTY),
					OTHER_INCOME_TYPE_ID);
		}

		if (otherIncomeExpenseTypeResource.getIncomeTypeId() != null
				&& !otherIncomeExpenseTypeResource.getIncomeTypeId().isEmpty()
				&& isPresentOtherIncomeExpenses.get().getExpenseTypeId() != null) {
			throw new ValidateRecordException(environment.getProperty(INVALID_INCOME_TYPE), OTHER_INCOME_TYPE_ID);
		}

		if (otherIncomeExpenseTypeResource.getExpenseTypeId() != null
				&& !otherIncomeExpenseTypeResource.getExpenseTypeId().isEmpty()
				&& isPresentOtherIncomeExpenses.get().getIncomeTypeId() != null) {
			throw new ValidateRecordException(environment.getProperty(INVALID_EXPENSE_TYPE), "otherExpenseTypeId");
		}

		if (otherIncomeExpenseTypeResource.getIncomeTypeId() != null
				&& !otherIncomeExpenseTypeResource.getIncomeTypeId().isEmpty()) {
			incomeType = incomeTypeRepository.findByIdAndStatus(
					Long.parseLong(otherIncomeExpenseTypeResource.getIncomeTypeId()), CommonStatus.ACTIVE.toString());
			if (!incomeType.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), OTHER_INCOME_TYPE_ID);
			}

			if (!incomeType.get().getName().equalsIgnoreCase(otherIncomeExpenseTypeResource.getIncomeTypeName())) {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "otherIncomeTypeName");
			}
		}

		if (otherIncomeExpenseTypeResource.getExpenseTypeId() != null
				&& !otherIncomeExpenseTypeResource.getExpenseTypeId().isEmpty()) {
			expenseType = otherExpenseTypeRepository.findByIdAndStatus(
					Long.parseLong(otherIncomeExpenseTypeResource.getExpenseTypeId()), CommonStatus.ACTIVE.toString());
			if (!expenseType.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "otherExpenseTypeId");
			}
		}

		FrequencyResponse frequencyResponse = validateService.validateFrequency(tenantId,
				otherIncomeExpenseTypeResource.getFrequencyId());
		if (!frequencyResponse.getName().equals(otherIncomeExpenseTypeResource.getFrequencyCode())) {
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "frequencyId");

		}

		CurencyResponse curencyResponse = validateService.validateCurrencyType(tenantId,
				otherIncomeExpenseTypeResource.getCurrencyId(), otherIncomeExpenseTypeResource.getCurrencyCode());

		if (!isPresentOtherIncomeExpenses.get().getVersion().toString()
				.equals(otherIncomeExpenseTypeResource.getVersion())) {
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
		}
		OtherIncomeExpenses otherIncomeExpenses = isPresentOtherIncomeExpenses.get();
		otherIncomeExpenses.setTenantId(tenantId);
		otherIncomeExpenses.setIncomeTypeId(incomeType.isPresent() ? incomeType.get() : null);
		otherIncomeExpenses.setExpenseTypeId(expenseType.isPresent() ? expenseType.get() : null);
		otherIncomeExpenses.setAmount(validateService.stringToBigDecimal(otherIncomeExpenseTypeResource.getAmount()));
		otherIncomeExpenses.setFrequencyId(Long.parseLong(frequencyResponse.getId()));
		otherIncomeExpenses.setFrequencyCode(frequencyResponse.getCode());
		otherIncomeExpenses.setCurrencyId(curencyResponse.getCurrencyId());
		otherIncomeExpenses.setCurrencyCode(curencyResponse.getCurrencyCode());
		otherIncomeExpenses.setCurrencyCodeNumeric(curencyResponse.getCodeNumeric());
		otherIncomeExpenses.setDescription(otherIncomeExpenseTypeResource.getDescription());
		otherIncomeExpenses.setStatus(CommonStatus.valueOf(otherIncomeExpenseTypeResource.getStatus()));
		otherIncomeExpenses.setSyncTs(validateService.getSyncTs());
		otherIncomeExpenses.setModifiedDate(validateService.getCreateOrModifyDate());
		otherIncomeExpenses.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		otherIncomeExpenses = otherIncomeExpensesRepository.save(otherIncomeExpenses);

		return otherIncomeExpenses;
	}

	@Override
	public List<OtherIncomeExpenses> findByOtherIncomeDetailsId(Long otherIncomeDetailsId) {

		List<OtherIncomeExpenses> otherIncomeExpensesList = otherIncomeExpensesRepository
				.findByOtherIncomeDetailsId(otherIncomeDetailsId);

		for (OtherIncomeExpenses otherIncomeExpenses : otherIncomeExpensesList) {
			setOtherIncomeExpenseDetails(otherIncomeExpenses);
		}
		return otherIncomeExpensesList;
	}

	private OtherIncomeExpenses setOtherIncomeExpenseDetails(OtherIncomeExpenses otherIncomeExpenses) {

		if (otherIncomeExpenses.getIncomeTypeId() != null) {
			Optional<IncomeType> incomeType = incomeTypeRepository
					.findById(otherIncomeExpenses.getIncomeTypeId().getId());
			if (incomeType.isPresent()) {
				otherIncomeExpenses.setIncomeTypeId(incomeType.get());
				otherIncomeExpenses.setIncomeTypeName(incomeType.get().getName());
			}
		}
		if (otherIncomeExpenses.getExpenseTypeId() != null) {
			otherIncomeExpenses.setIncomeTypeId(otherIncomeExpenses.getIncomeTypeId());
		}
		return otherIncomeExpenses;
	}

}
