package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CultivationIncomeDetails;
import com.fusionx.lending.origination.domain.CultivationIncomeExpenses;
import com.fusionx.lending.origination.domain.CultivationIncomeType;
import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CultivationIncomeDetailsRepository;
import com.fusionx.lending.origination.repository.CultivationIncomeExpensesRepository;
import com.fusionx.lending.origination.repository.CultivationIncomeTypeRepository;
import com.fusionx.lending.origination.repository.ExpenseTypeRepository;
import com.fusionx.lending.origination.resource.CultivationIncomeExpenseResource;
import com.fusionx.lending.origination.resource.CultivationIncomeExpensesAddResource;
import com.fusionx.lending.origination.resource.CultivationIncomeExpensesUpdateResource;
import com.fusionx.lending.origination.resource.CurencyResponse;
import com.fusionx.lending.origination.resource.FrequencyResponse;
import com.fusionx.lending.origination.service.CultivationIncomeExpensesService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * API Service related to Cultivation Income Expenses.
 *
 * @author Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        13-09-2021      	             FXL-661    Dilhan                    Created
 * <p>
 *
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class CultivationIncomeExpensesServiceImpl extends MessagePropertyBase
		implements CultivationIncomeExpensesService {

	@Autowired
	private ValidateService validateService;

	@Autowired
	private CultivationIncomeExpensesRepository cultivationIncomeExpensesRepository;

	@Autowired
	private CultivationIncomeTypeRepository cultivationIncomeTypeRepository;

	@Autowired
	private ExpenseTypeRepository expenseTypeRepository;

	@Autowired
	private CultivationIncomeDetailsRepository cultivationIncomeDetailsRepository;

	@Override
	public Optional<CultivationIncomeExpenses> findById(String tenantId, Long id) {
		Optional<CultivationIncomeExpenses> isPresentCultivationIncomeExpenses = cultivationIncomeExpensesRepository
				.findById(id);

		if (isPresentCultivationIncomeExpenses.isPresent()) {
			return Optional.ofNullable(isPresentCultivationIncomeExpenses.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<CultivationIncomeExpenses> findAll(String tenantId) {
		List<CultivationIncomeExpenses> cultivationIncomeExpenses = cultivationIncomeExpensesRepository.findAll();

		return cultivationIncomeExpenses;
	}

	@Override
	public List<CultivationIncomeExpenses> findByStatus(String tenantId, String status) {
		List<CultivationIncomeExpenses> cultivationIncomeExpenses = cultivationIncomeExpensesRepository
				.findByStatus(CommonStatus.valueOf(status));

		return cultivationIncomeExpenses;
	}

	@Override
	public List<CultivationIncomeExpenses> findByCultivationIncomeDetailsId(String tenantId,
			Long incomeSourceDetailsId) {
		Optional<CultivationIncomeDetails> cultivationIncomeDetails = cultivationIncomeDetailsRepository
				.findById(incomeSourceDetailsId);
		if (!cultivationIncomeDetails.isPresent()) {
			return null;
		}
		List<CultivationIncomeExpenses> cultivationIncomeExpenses = cultivationIncomeExpensesRepository
				.findByCultivationIncomeDetails(cultivationIncomeDetails.get());

		return cultivationIncomeExpenses;
	}

	@Override
	public void saveCultivationIncomeExpenses(String tenantId,
			CultivationIncomeExpensesAddResource cultivationIncomeExpensesAddResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");

		Optional<CultivationIncomeDetails> isPresentCultivationIncomeDetails = cultivationIncomeDetailsRepository
				.findById(validateService
						.stringToLong(cultivationIncomeExpensesAddResource.getCultivationIncomeDetailsId()));
		if (!isPresentCultivationIncomeDetails.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");

		FrequencyResponse frequencyResponse = validateService.validateFrequency(tenantId,
				cultivationIncomeExpensesAddResource.getFrequencyId());
		if (!frequencyResponse.getCode().equals(cultivationIncomeExpensesAddResource.getFrequencyCode())) {
			throw new ValidateRecordException(environment.getProperty("frequency.code"), "frequencyCode");
		}

		CurencyResponse curencyResponse = validateService.validateCurrencyType(tenantId,
				cultivationIncomeExpensesAddResource.getCurrencyId(),
				cultivationIncomeExpensesAddResource.getCurrencyName());

		if (cultivationIncomeExpensesAddResource.getIncomeOrExpenseDetails() != null
				&& !cultivationIncomeExpensesAddResource.getIncomeOrExpenseDetails().isEmpty()) {

			for (CultivationIncomeExpenseResource item : cultivationIncomeExpensesAddResource
					.getIncomeOrExpenseDetails()) {

				Optional<CultivationIncomeType> isPresentCultivationIncomeType = null;
				Optional<ExpenseType> isPresentExpenseType = null;
				if ((item.getIncomeTypeId() == null && item.getExpenseTypeId() == null)
						|| (item.getIncomeTypeId() != null && item.getExpenseTypeId() != null)) {
					throw new ValidateRecordException(getEnvironment().getProperty("incomeType.expense-type"),
							"message");
				}

				if (item.getIncomeTypeId() != null) {
					isPresentCultivationIncomeType = cultivationIncomeTypeRepository.findByIdAndNameAndStatus(
							validateService.stringToLong(item.getIncomeTypeId()), item.getTypeName(),
							CommonStatus.ACTIVE);

					if (!isPresentCultivationIncomeType.isPresent())
						throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");

				}

				if (item.getExpenseTypeId() != null) {
					isPresentExpenseType = expenseTypeRepository.findByIdAndNameAndStatus(
							validateService.stringToLong(item.getExpenseTypeId()), item.getTypeName(),
							CommonStatus.ACTIVE.toString());

					if (!isPresentExpenseType.isPresent())
						throw new ValidateRecordException(environment.getProperty("expense.type"), "expenseTypeId");
				}

				CultivationIncomeExpenses cultivationIncomeExpenses = new CultivationIncomeExpenses();
				cultivationIncomeExpenses.setCultivationIncomeDetails(isPresentCultivationIncomeDetails.get());

				if (isPresentCultivationIncomeType != null) {
					cultivationIncomeExpenses.setCultivationIncomeType(isPresentCultivationIncomeType.get());
				} else {
					cultivationIncomeExpenses.setExpenseType(isPresentExpenseType.get());
				}

				cultivationIncomeExpenses.setDescription(item.getDescription());
				cultivationIncomeExpenses.setFrequencyId(validateService.stringToLong(frequencyResponse.getId()));
				cultivationIncomeExpenses.setFrequencyCode(frequencyResponse.getCode());
				cultivationIncomeExpenses.setCurrencyId(curencyResponse.getCurrencyId());
				cultivationIncomeExpenses.setCurrencyCodeNumeric(curencyResponse.getCodeNumeric());
				cultivationIncomeExpenses.setCurrencyCode(curencyResponse.getCurrencyCode());
				cultivationIncomeExpenses.setAmount(validateService.stringToBigDecimal(item.getAmount()));

				cultivationIncomeExpenses.setTenantId(tenantId);
				cultivationIncomeExpenses.setStatus(CommonStatus.valueOf(item.getStatus()));
				cultivationIncomeExpenses.setSyncTs(validateService.getSyncTs());
				cultivationIncomeExpenses.setCreatedDate(validateService.getCreateOrModifyDate());
				cultivationIncomeExpenses.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				cultivationIncomeExpenses = cultivationIncomeExpensesRepository.save(cultivationIncomeExpenses);

			}

		}

	}

	@Override
	public CultivationIncomeExpenses updateCultivationIncomeExpenses(String tenantId, Long cultivationIncomeExpenseId,
			CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");

		FrequencyResponse frequencyResponse = validateService.validateFrequency(tenantId,
				cultivationIncomeExpensesUpdateResource.getFrequencyId());
		if (!frequencyResponse.getCode().equals(cultivationIncomeExpensesUpdateResource.getFrequencyCode())) {
			throw new ValidateRecordException(environment.getProperty("frequency.code"), "frequencyCode");
		}

		CurencyResponse curencyResponse = validateService.validateCurrencyType(tenantId,
				cultivationIncomeExpensesUpdateResource.getCurrencyId(),
				cultivationIncomeExpensesUpdateResource.getCurrencyName());

		Optional<CultivationIncomeType> isPresentCultivationIncomeType = null;
		Optional<ExpenseType> isPresentExpenseType = null;

		Optional<CultivationIncomeExpenses> isPresentCultivationIncomeExpense = cultivationIncomeExpensesRepository
				.findById(cultivationIncomeExpenseId);

		if (!isPresentCultivationIncomeExpense.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");

		if (!isPresentCultivationIncomeExpense.get().getVersion()
				.equals(Long.parseLong(cultivationIncomeExpensesUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");

		if ((cultivationIncomeExpensesUpdateResource.getIncomeTypeId() == null
				&& cultivationIncomeExpensesUpdateResource.getExpenseTypeId() == null)
				|| (cultivationIncomeExpensesUpdateResource.getIncomeTypeId() != null
						&& cultivationIncomeExpensesUpdateResource.getExpenseTypeId() != null)) {
			throw new ValidateRecordException(getEnvironment().getProperty("incomeType.expense-type"), "message");
		}

		if ((cultivationIncomeExpensesUpdateResource.getExpenseTypeId() != null)
				&& (isPresentCultivationIncomeExpense.get().getExpenseType() == null)) {
			throw new ValidateRecordException(getEnvironment().getProperty("expenseType.can-not-update"), "message");
		}
		if ((cultivationIncomeExpensesUpdateResource.getIncomeTypeId() != null)
				&& (isPresentCultivationIncomeExpense.get().getCultivationIncomeType() == null)) {
			throw new ValidateRecordException(getEnvironment().getProperty("incomeType.can-not-update"), "message");
		}

		if (cultivationIncomeExpensesUpdateResource.getIncomeTypeId() != null) {
			isPresentCultivationIncomeType = cultivationIncomeTypeRepository.findByIdAndNameAndStatus(
					validateService.stringToLong(cultivationIncomeExpensesUpdateResource.getIncomeTypeId()),
					cultivationIncomeExpensesUpdateResource.getTypeName(), CommonStatus.ACTIVE);

			if (!isPresentCultivationIncomeType.isPresent())
				throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");

		}

		if (cultivationIncomeExpensesUpdateResource.getExpenseTypeId() != null) {
			isPresentExpenseType = expenseTypeRepository.findByIdAndNameAndStatus(
					validateService.stringToLong(cultivationIncomeExpensesUpdateResource.getExpenseTypeId()),
					cultivationIncomeExpensesUpdateResource.getTypeName(), CommonStatus.ACTIVE.toString());

			if (!isPresentExpenseType.isPresent())
				throw new ValidateRecordException(environment.getProperty("expense.type"), "expenseTypeId");
		}

		CultivationIncomeExpenses cultivationIncomeExpenses = isPresentCultivationIncomeExpense.get();

		if (isPresentCultivationIncomeType != null) {
			cultivationIncomeExpenses.setCultivationIncomeType(isPresentCultivationIncomeType.get());
		} else {
			cultivationIncomeExpenses.setExpenseType(isPresentExpenseType.get());
		}

		cultivationIncomeExpenses.setDescription(cultivationIncomeExpensesUpdateResource.getDescription());
		cultivationIncomeExpenses.setFrequencyId(validateService.stringToLong(frequencyResponse.getId()));
		cultivationIncomeExpenses.setFrequencyCode(frequencyResponse.getCode());
		cultivationIncomeExpenses.setCurrencyId(curencyResponse.getCurrencyId());
		cultivationIncomeExpenses.setCurrencyCodeNumeric(curencyResponse.getCodeNumeric());
		cultivationIncomeExpenses.setCurrencyCode(curencyResponse.getCurrencyCode());
		cultivationIncomeExpenses
				.setAmount(validateService.stringToBigDecimal(cultivationIncomeExpensesUpdateResource.getAmount()));

		cultivationIncomeExpenses.setStatus(CommonStatus.valueOf(cultivationIncomeExpensesUpdateResource.getStatus()));
		cultivationIncomeExpenses.setSyncTs(validateService.getSyncTs());
		cultivationIncomeExpenses.setModifiedDate(validateService.getCreateOrModifyDate());
		cultivationIncomeExpenses.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		return cultivationIncomeExpenses = cultivationIncomeExpensesRepository.save(cultivationIncomeExpenses);

	}

	@Override
	public List<CultivationIncomeExpenses> getCultivationIncomeDetails(Long id) {

		Optional<CultivationIncomeDetails> cultivationIncomeDetailsOptional = cultivationIncomeDetailsRepository
				.findById(id);
		if (!cultivationIncomeDetailsOptional.isPresent()) {
			return null;
		}

		List<CultivationIncomeExpenses> incomeList = cultivationIncomeExpensesRepository
				.findAllByCultivationIncomeDetailsAndExpenseType(cultivationIncomeDetailsOptional.get(),
						null);
		return incomeList;
	}

	@Override
	public List<CultivationIncomeExpenses> getCultivationExpenseDetails(Long id) {
		
		Optional<CultivationIncomeDetails> cultivationIncomeDetailsOptional = cultivationIncomeDetailsRepository
				.findById(id);
		if (!cultivationIncomeDetailsOptional.isPresent()) {
			return null;
		}

		List<CultivationIncomeExpenses> expenseList = cultivationIncomeExpensesRepository
				.findAllByCultivationIncomeDetailsAndCultivationIncomeType(cultivationIncomeDetailsOptional.get(),
						null);
		return expenseList;
	}

}
