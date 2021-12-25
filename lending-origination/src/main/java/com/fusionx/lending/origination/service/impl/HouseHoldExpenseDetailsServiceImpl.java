package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.domain.ExpenseTypeHouseholdExpenseCategories;
import com.fusionx.lending.origination.domain.HouseHoldExpenseCategory;
import com.fusionx.lending.origination.domain.HouseHoldExpenseDetails;
import com.fusionx.lending.origination.domain.IncomeSourceDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ExpenseTypeHouseholdExpenseCategoriesRepository;
import com.fusionx.lending.origination.repository.ExpenseTypeRepository;
import com.fusionx.lending.origination.repository.HouseHoldExpenseCategoryRepository;
import com.fusionx.lending.origination.repository.HouseHoldExpenseDetailsRepository;
import com.fusionx.lending.origination.repository.IncomeSourceDetailsRepository;
import com.fusionx.lending.origination.resource.CurencyResponse;
import com.fusionx.lending.origination.resource.FrequencyResponse;
import com.fusionx.lending.origination.resource.HouseHoldExpenseDetailsAddResource;
import com.fusionx.lending.origination.resource.HouseHoldExpenseDetailsUpdateResource;
import com.fusionx.lending.origination.resource.HouseHoldExpenseInfoResource;
import com.fusionx.lending.origination.service.HouseHoldExpenseDetailsService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * API Service related to House Hold Expense Details.
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
 * 1        02-09-2021      	             FXL-662    Dilhan                    Created
 * <p>
 *
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class HouseHoldExpenseDetailsServiceImpl extends MessagePropertyBase implements HouseHoldExpenseDetailsService {

	@Autowired
	private IncomeSourceDetailsRepository incomeSourceDetailsRepository;

	@Autowired
	private HouseHoldExpenseDetailsRepository houseHoldExpenseDetailsRepository;

	@Autowired
	private HouseHoldExpenseCategoryRepository houseHoldExpenseCategoryRepository;

	@Autowired
	private ExpenseTypeHouseholdExpenseCategoriesRepository expenseTypeHouseholdExpenseCategoriesRepository;

	@Autowired
	private ExpenseTypeRepository expenseTypeRepository;

	@Autowired
	private ValidateService validateService;

	@Override
	public void saveHouseHoldExpenseDetails(String tenantId,
			HouseHoldExpenseDetailsAddResource houseHoldExpenseDetailsAddResource) {

		Optional<IncomeSourceDetails> isPresentIncomeSourceDetails = incomeSourceDetailsRepository.findByIdAndStatus(
				validateService.stringToLong(houseHoldExpenseDetailsAddResource.getIncomeSourceDetailsId()),
				CommonStatus.ACTIVE);

		if (!isPresentIncomeSourceDetails.isPresent()) {
			LoggerRequest.getInstance().logInfo(
					"IncomeSourceDetails********************************Validate Income Source Details*********************************************");
			throw new ValidateRecordException(environment.getProperty("income.source-details"), "incomeSourceDetails");
		}

		if (houseHoldExpenseDetailsAddResource.getHouseHoldExpenseInfo() != null
				&& !houseHoldExpenseDetailsAddResource.getHouseHoldExpenseInfo().isEmpty()) {

			for (HouseHoldExpenseInfoResource item : houseHoldExpenseDetailsAddResource.getHouseHoldExpenseInfo()) {

				HouseHoldExpenseCategory houseHoldExpenseCategory = validateHouseHoldExpenseCategory(
						validateService.stringToLong(item.getHouseHoldExpenseCategoryId()),
						item.getHouseHoldExpenseCategoryName());

				ExpenseType expenseType = validateExpenseType(validateService.stringToLong(item.getExpenseTypeId()),
						item.getExpenseTypeName());

				Optional<ExpenseTypeHouseholdExpenseCategories> isPresentExpenseTypeHouseholdExpenseCategory = expenseTypeHouseholdExpenseCategoriesRepository
						.findByExpenseTypeAndHouseHoldExpenseCategory(expenseType, houseHoldExpenseCategory);

				if (!isPresentExpenseTypeHouseholdExpenseCategory.isPresent()) {
					throw new ValidateRecordException(
							environment.getProperty("expenseType.house-hold-category-not-found"), "expTypeCategory");
				}

				HouseHoldExpenseDetails houseHoldExpenseDetails = new HouseHoldExpenseDetails();
				houseHoldExpenseDetails.setTenantId(tenantId);
				houseHoldExpenseDetails.setIncomeSourceDetails(isPresentIncomeSourceDetails.get());
				houseHoldExpenseDetails.setHouseHoldExpenseCategory(houseHoldExpenseCategory);
				houseHoldExpenseDetails.setExpenseType(expenseType);
				houseHoldExpenseDetails.setDescription(item.getDescription());

				FrequencyResponse calFrequencyResponse = validateService.validateFrequency(tenantId,
						item.getCalculationFrequencyId());
				if (!calFrequencyResponse.getCode().equals(item.getCalculationFrequencyCode())) {
					throw new ValidateRecordException(environment.getProperty("calculation.frequency-code"),
							"calFrequencyCode");
				}

				houseHoldExpenseDetails
						.setCalculationFrequencyId(validateService.stringToLong(calFrequencyResponse.getId()));
				houseHoldExpenseDetails.setCalculationFrequencyCode(calFrequencyResponse.getCode());

				FrequencyResponse occFrequencyResponse = validateService.validateFrequency(tenantId,
						item.getOccuranceFrequencyId());
				if (!occFrequencyResponse.getCode().equals(item.getOccuranceFrequencyCode())) {
					throw new ValidateRecordException(environment.getProperty("occurrence.frequency-code"),
							"occFrequencyCode");
				}

				houseHoldExpenseDetails
						.setOccurranceFrequencyId(validateService.stringToLong(occFrequencyResponse.getId()));
				houseHoldExpenseDetails.setOccurranceFrequencyCode(occFrequencyResponse.getCode());

				houseHoldExpenseDetails.setCost(validateService.stringToBigDecimal(item.getCost()));
				houseHoldExpenseDetails.setFinalCost(validateService.stringToBigDecimal(item.getFinalCost()));

				CurencyResponse curencyResponse = validateService.validateCurrencyType(tenantId, item.getCurrencyId(),
						item.getCurrencyName());
				houseHoldExpenseDetails.setCurrencyId(curencyResponse.getCurrencyId());
				houseHoldExpenseDetails.setCurrencyCode(curencyResponse.getCurrencyCode());
				houseHoldExpenseDetails.setCurrencyCodeNumeric(curencyResponse.getCodeNumeric());

				houseHoldExpenseDetails.setStatus(CommonStatus.valueOf(item.getStatus()));
				houseHoldExpenseDetails.setSyncTs(validateService.getSyncTs());
				houseHoldExpenseDetails.setCreatedDate(validateService.getCreateOrModifyDate());
				houseHoldExpenseDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

				houseHoldExpenseDetailsRepository.save(houseHoldExpenseDetails);
			}

		}

	}

	private HouseHoldExpenseCategory validateHouseHoldExpenseCategory(Long id, String name) {

		Optional<HouseHoldExpenseCategory> isPresentHouseHoldExpenseCategory = houseHoldExpenseCategoryRepository
				.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE.toString());

		if (!isPresentHouseHoldExpenseCategory.isPresent())
			throw new ValidateRecordException(environment.getProperty("category.house-hold"),
					"houseHoldExpenseCategoryId");

		return isPresentHouseHoldExpenseCategory.get();
	}

	private ExpenseType validateExpenseType(Long id, String name) {

		Optional<ExpenseType> isPresentExpenseType = expenseTypeRepository.findByIdAndNameAndStatus(id, name,
				CommonStatus.ACTIVE.toString());

		if (!isPresentExpenseType.isPresent())
			throw new ValidateRecordException(environment.getProperty("expense.type"), "expenseTypeId");

		return isPresentExpenseType.get();
	}

	@Override
	public void updateHouseHoldExpenseDetails(String tenantId,
			HouseHoldExpenseDetailsUpdateResource houseHoldExpenseDetailsUpdateResource) {

		Optional<IncomeSourceDetails> isPresentIncomeSourceDetails = incomeSourceDetailsRepository.findByIdAndStatus(
				validateService.stringToLong(houseHoldExpenseDetailsUpdateResource.getIncomeSourceDetailsId()),
				CommonStatus.ACTIVE);

		if (!isPresentIncomeSourceDetails.isPresent()) {
			LoggerRequest.getInstance().logInfo(
					"IncomeSourceDetails********************************Validate Income Source Details*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
		}
		if (houseHoldExpenseDetailsUpdateResource.getHouseHoldExpenseInfo() != null
				&& !houseHoldExpenseDetailsUpdateResource.getHouseHoldExpenseInfo().isEmpty()) {
			Integer index = 0;
			for (HouseHoldExpenseInfoResource item : houseHoldExpenseDetailsUpdateResource.getHouseHoldExpenseInfo()) {

				HouseHoldExpenseDetails houseHoldExpenseDetails = new HouseHoldExpenseDetails();

				if (item.getId() != null && !item.getId().isEmpty()) {

					Optional<HouseHoldExpenseDetails> isHouseHoldExpenseDetails = houseHoldExpenseDetailsRepository
							.findById(validateService.stringToLong(item.getId()));

					if (!isHouseHoldExpenseDetails.isPresent()) {
						throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);

					} else {
						
						if(item.getVersion() == null || item.getVersion().isEmpty()) {	
							throw new DetailListValidateException(environment.getProperty(BLANK_VERSION),ServiceEntity.VERSION, ServicePoint.HOUSE_HOLD_EXPENSE_DETAILS, index);
						}

						if (!isHouseHoldExpenseDetails.get().getVersion()
								.equals(Long.parseLong(item.getVersion())))
							throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE),ServiceEntity.VERSION, ServicePoint.HOUSE_HOLD_EXPENSE_DETAILS, index);

						houseHoldExpenseDetails = isHouseHoldExpenseDetails.get();
						houseHoldExpenseDetails.setModifiedDate(validateService.getCreateOrModifyDate());
						houseHoldExpenseDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
					}

				} else {

					houseHoldExpenseDetails.setTenantId(tenantId);
					houseHoldExpenseDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
					houseHoldExpenseDetails.setCreatedDate(validateService.getCreateOrModifyDate());
					houseHoldExpenseDetails.setIncomeSourceDetails(isPresentIncomeSourceDetails.get());
				}

				HouseHoldExpenseCategory houseHoldExpenseCategory = validateHouseHoldExpenseCategory(
						validateService.stringToLong(item.getHouseHoldExpenseCategoryId()),
						item.getHouseHoldExpenseCategoryName());

				ExpenseType expenseType = validateExpenseType(validateService.stringToLong(item.getExpenseTypeId()),
						item.getExpenseTypeName());

				Optional<ExpenseTypeHouseholdExpenseCategories> isPresentExpenseTypeHouseholdExpenseCategory = expenseTypeHouseholdExpenseCategoriesRepository
						.findByExpenseTypeAndHouseHoldExpenseCategory(expenseType, houseHoldExpenseCategory);

				if (!isPresentExpenseTypeHouseholdExpenseCategory.isPresent()) {
					throw new ValidateRecordException(
							environment.getProperty("expenseType.house-hold-category-not-found"), "expTypeCategory");
				}

				houseHoldExpenseDetails.setHouseHoldExpenseCategory(houseHoldExpenseCategory);
				houseHoldExpenseDetails.setExpenseType(expenseType);
				houseHoldExpenseDetails.setDescription(item.getDescription());

				FrequencyResponse calFrequencyResponse = validateService.validateFrequency(tenantId,
						item.getCalculationFrequencyId());
				if (!calFrequencyResponse.getCode().equals(item.getCalculationFrequencyCode())) {
					throw new ValidateRecordException(environment.getProperty("calculation.frequency-code"),
							"calFrequencyCode");
				}

				houseHoldExpenseDetails
						.setCalculationFrequencyId(validateService.stringToLong(calFrequencyResponse.getId()));
				houseHoldExpenseDetails.setCalculationFrequencyCode(calFrequencyResponse.getCode());

				FrequencyResponse occFrequencyResponse = validateService.validateFrequency(tenantId,
						item.getOccuranceFrequencyId());
				if (!occFrequencyResponse.getCode().equals(item.getOccuranceFrequencyCode())) {
					throw new ValidateRecordException(environment.getProperty("occurrence.frequency-code"),
							"occFrequencyCode");
				}

				houseHoldExpenseDetails
						.setOccurranceFrequencyId(validateService.stringToLong(occFrequencyResponse.getId()));
				houseHoldExpenseDetails.setOccurranceFrequencyCode(occFrequencyResponse.getCode());

				houseHoldExpenseDetails.setCost(validateService.stringToBigDecimal(item.getCost()));
				houseHoldExpenseDetails.setFinalCost(validateService.stringToBigDecimal(item.getFinalCost()));

				CurencyResponse curencyResponse = validateService.validateCurrencyType(tenantId, item.getCurrencyId(),
						item.getCurrencyName());
				houseHoldExpenseDetails.setCurrencyId(curencyResponse.getCurrencyId());
				houseHoldExpenseDetails.setCurrencyCode(curencyResponse.getCurrencyCode());
				houseHoldExpenseDetails.setCurrencyCodeNumeric(curencyResponse.getCodeNumeric());

				houseHoldExpenseDetails.setStatus(CommonStatus.valueOf(item.getStatus()));
				houseHoldExpenseDetails.setSyncTs(validateService.getSyncTs());
				houseHoldExpenseDetails.setCreatedDate(validateService.getCreateOrModifyDate());
				houseHoldExpenseDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

				houseHoldExpenseDetailsRepository.save(houseHoldExpenseDetails);

			}

		}

	}

	@Override
	public List<HouseHoldExpenseDetails> findByIncomeSourceDetailsId(Long incomeSourceDetailsId) {

		List<HouseHoldExpenseDetails> houseHoldExpenseDetails = houseHoldExpenseDetailsRepository
				.findByIncomeSourceDetailsId(incomeSourceDetailsId);
		return houseHoldExpenseDetails;
	}

}
