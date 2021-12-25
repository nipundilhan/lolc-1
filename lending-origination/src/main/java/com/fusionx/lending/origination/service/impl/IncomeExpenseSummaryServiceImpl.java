package com.fusionx.lending.origination.service.impl;

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.*;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.IncomeTypeEnum;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.*;
import com.fusionx.lending.origination.resource.IncomeExpenseSummaryResponse;
import com.fusionx.lending.origination.service.IncomeExpenseSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Income Expense Summary Service Impl
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No       Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   21-09-2021   FXL-115  	 FXL-786       Piyumi     Created
 * <p>
 * *******************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class IncomeExpenseSummaryServiceImpl extends MessagePropertyBase implements IncomeExpenseSummaryService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LinkedPersonRepository linkedPersonRepository;

    @Autowired
    private IncomeSourceDetailsRepository incomeSourceDetailsRepository;

    @Autowired
    private BusinessIncomeDetailsRepository businessIncomeDetailsRepository;

    @Autowired
    private BusinessIncomeExpensesRepository businessIncomeExpensesRepository;

    @Autowired
    private BusinessTaxDetailsRepository businessTaxDetailsRepository;

    @Autowired
    private SalaryIncomeDetailsRepository salaryIncomeDetailsRepository;

    @Autowired
    private SalaryIncomeExpensesRepository salaryIncomeExpensesRepository;

    @Autowired
    private CultivationIncomeDetailsRepository cultivationIncomeDetailsRepository;

    @Autowired
    private CultivationIncomeExpensesRepository cultivationIncomeExpensesRepository;

    @Autowired
    private OtherIncomeDetailsRepository otherIncomeDetailsRepository;

    @Autowired
    private HouseHoldExpenseDetailsRepository houseHoldExpenseDetailsRepository;

    @Autowired
    private LeadInfoRepository leadInfoRepository;

    @Autowired
    private OtherIncomeExpensesRepository otherIncomeExpensesRepository;


    @Override
    public IncomeExpenseSummaryResponse findByCustomerId(Long customerId) {
        IncomeExpenseSummaryResponse incomeExpenseSummaryResponse = new IncomeExpenseSummaryResponse();

        Optional<Customer> customer = customerRepository.findById(customerId);
        if (!customer.isPresent()) {
            throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
        }
        List<LinkedPerson> linkedPersonList = linkedPersonRepository.findByCustomerId(customerId);

        setBusinessIncomeExpenseAndTaxDetails(null, customer.get().getId(), linkedPersonList, incomeExpenseSummaryResponse);
        setSalaryIncomeExpenseDetails(null, customer.get().getId(), linkedPersonList, incomeExpenseSummaryResponse);
        setCultivationIncomeExpenseDetails(null, customer.get().getId(), linkedPersonList, incomeExpenseSummaryResponse);
        setOtherIncomeExpenseDetails(null, customer.get().getId(), linkedPersonList, incomeExpenseSummaryResponse);
        setHouseHoldExpenseDetails(null, customer.get().getId(), linkedPersonList, incomeExpenseSummaryResponse);

        BigDecimal grossIncome = incomeExpenseSummaryResponse.getNetBusinessIncome().add(incomeExpenseSummaryResponse.getNetCultivationIncome())
                .add(incomeExpenseSummaryResponse.getNetSalaryIncome()).add(incomeExpenseSummaryResponse.getNetOtherIncome());

        incomeExpenseSummaryResponse.setTotalGrossIncome(grossIncome);

        // dependancy task not developed
        incomeExpenseSummaryResponse.setTotalFinancialCommitment(BigDecimal.ZERO);

        BigDecimal totalNetIncome = grossIncome.subtract(incomeExpenseSummaryResponse.getTotalHouseHoldExpenses().add(incomeExpenseSummaryResponse.getTotalFinancialCommitment()));
        incomeExpenseSummaryResponse.setTotalNetIncome(totalNetIncome);

        BigDecimal fourtyPercentNetIncome = totalNetIncome.multiply(Constants.FOURTY_PERCENT);
        incomeExpenseSummaryResponse.setFourtyPercentNetIncome(fourtyPercentNetIncome);
        return incomeExpenseSummaryResponse;
    }

    private void setBusinessIncomeExpenseAndTaxDetails(Long leadId, Long customerId, List<LinkedPerson> linkedPersonList, IncomeExpenseSummaryResponse incomeExpenseSummaryResponse) {
        List<IncomeSourceDetails> incomeSourceDetailList = new ArrayList<>();
        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;

        if (leadId != null) {
            List<IncomeSourceDetails> leadIncomeSourceDetailList = incomeSourceDetailsRepository.findByLeadIdAndIncomeTypeAndStatus(leadId, IncomeTypeEnum.BUSINESS, CommonStatus.ACTIVE);
            incomeSourceDetailList.addAll(leadIncomeSourceDetailList);

        } else if (customerId != null) {
            List<IncomeSourceDetails> customerIncomeSourceDetailList = incomeSourceDetailsRepository.findByCustomerIdAndIncomeTypeAndStatus(customerId, IncomeTypeEnum.BUSINESS, CommonStatus.ACTIVE);
            incomeSourceDetailList.addAll(customerIncomeSourceDetailList);
        }

        if (linkedPersonList != null && !linkedPersonList.isEmpty()) {
            for (LinkedPerson linkedPerson : linkedPersonList) {
                List<IncomeSourceDetails> linkedPersonIncomeSourceDetailList = incomeSourceDetailsRepository.findByLinkedPersonIdAndIncomeTypeAndStatus(linkedPerson.getId(), IncomeTypeEnum.BUSINESS, CommonStatus.ACTIVE);
                incomeSourceDetailList.addAll(linkedPersonIncomeSourceDetailList);
            }
        }

        if (!incomeSourceDetailList.isEmpty()) {

            for (IncomeSourceDetails incomeSourceDetail : incomeSourceDetailList) {
                List<BusinessIncomeDetails> businessIncomeDetailList = businessIncomeDetailsRepository.findByIncomeSourceDetailIdAndStatus(incomeSourceDetail.getId(), CommonStatus.ACTIVE);

                if (!businessIncomeDetailList.isEmpty()) {
                    for (BusinessIncomeDetails businessIncomeDetail : businessIncomeDetailList) {
                        List<BusinessIncomeExpenses> businessIncomeExpensesList = businessIncomeExpensesRepository.findByBusinessIncomeDetailIdAndStatus(businessIncomeDetail.getId(), CommonStatus.ACTIVE);
                        List<BusinessTaxDetails> businessTaxDetailList = businessTaxDetailsRepository.findByBusinessIncomeDetailIdAndStatus(businessIncomeDetail.getId(), CommonStatus.ACTIVE);

                        if (!businessIncomeExpensesList.isEmpty()) {
                            for (BusinessIncomeExpenses businessIncomeExpense : businessIncomeExpensesList) {

                                if (businessIncomeExpense.getBusinessIncomeType() != null) {
                                    income = income.add(businessIncomeExpense.getAmount());

                                } else if (businessIncomeExpense.getBusinessExpenseType() != null) {
                                    expense = expense.add(businessIncomeExpense.getAmount());
                                }
                            }
                        }

                        if (!businessTaxDetailList.isEmpty()) {
                            for (BusinessTaxDetails businessTaxDetail : businessTaxDetailList) {
                                tax = tax.add(businessTaxDetail.getAmount());
                            }
                        }
                    }
                }
            }
        }

        incomeExpenseSummaryResponse.setBusinessIncome(income);
        incomeExpenseSummaryResponse.setBusinessExpenses(expense);
        incomeExpenseSummaryResponse.setTaxes(tax);
        BigDecimal netIncome = income.subtract(expense.add(tax));
        incomeExpenseSummaryResponse.setNetBusinessIncome(netIncome);

    }

    private void setSalaryIncomeExpenseDetails(Long leadId, Long customerId, List<LinkedPerson> linkedPersonList, IncomeExpenseSummaryResponse incomeExpenseSummaryResponse) {
        List<IncomeSourceDetails> incomeSourceDetailList = new ArrayList<>();
        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;

        if (leadId != null) {
            List<IncomeSourceDetails> leadIncomeSourceDetailList = incomeSourceDetailsRepository.findByLeadIdAndIncomeTypeAndStatus(leadId, IncomeTypeEnum.SALARY, CommonStatus.ACTIVE);
            incomeSourceDetailList.addAll(leadIncomeSourceDetailList);

        } else if (customerId != null) {
            List<IncomeSourceDetails> customerIncomeSourceDetailList = incomeSourceDetailsRepository.findByCustomerIdAndIncomeTypeAndStatus(customerId, IncomeTypeEnum.SALARY, CommonStatus.ACTIVE);
            incomeSourceDetailList.addAll(customerIncomeSourceDetailList);
        }

        if (linkedPersonList != null && !linkedPersonList.isEmpty()) {
            for (LinkedPerson linkedPerson : linkedPersonList) {
                List<IncomeSourceDetails> linkedPersonIncomeSourceDetailList = incomeSourceDetailsRepository.findByLinkedPersonIdAndIncomeTypeAndStatus(linkedPerson.getId(), IncomeTypeEnum.SALARY, CommonStatus.ACTIVE);
                incomeSourceDetailList.addAll(linkedPersonIncomeSourceDetailList);
            }
        }

        if (!incomeSourceDetailList.isEmpty()) {

            for (IncomeSourceDetails incomeSourceDetail : incomeSourceDetailList) {
                List<SalaryIncomeDetails> salaryIncomeDetailList = salaryIncomeDetailsRepository.findByIncomeSourceDetailIdAndStatus(incomeSourceDetail.getId(), CommonStatus.ACTIVE);

                if (!salaryIncomeDetailList.isEmpty()) {
                    for (SalaryIncomeDetails salaryIncomeDetail : salaryIncomeDetailList) {
                        List<SalaryIncomeExpenses> salaryIncomeExpensesList = salaryIncomeExpensesRepository.findBySalaryIncomeDetailsIdAndStatus(salaryIncomeDetail.getId(), CommonStatus.ACTIVE);

                        if (!salaryIncomeExpensesList.isEmpty()) {

                            for (SalaryIncomeExpenses salaryIncomeExpenses : salaryIncomeExpensesList) {

                                if (salaryIncomeExpenses.getSalaryIncomeType() != null) {
                                    income = income.add(salaryIncomeExpenses.getAmount());

                                } else if (salaryIncomeExpenses.getExpenseType() != null) {
                                    expense = expense.add(salaryIncomeExpenses.getAmount());

                                }
                            }
                        }
                    }
                }
            }
        }
        incomeExpenseSummaryResponse.setSalaryIncome(income);
        incomeExpenseSummaryResponse.setSalaryExpenses(expense);
        BigDecimal netIncome = income.subtract(expense);
        incomeExpenseSummaryResponse.setNetSalaryIncome(netIncome);

    }

    private void setCultivationIncomeExpenseDetails(Long leadId, Long customerId, List<LinkedPerson> linkedPersonList, IncomeExpenseSummaryResponse incomeExpenseSummaryResponse) {
        List<IncomeSourceDetails> incomeSourceDetailList = new ArrayList<>();

        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;

        if (leadId != null) {
            List<IncomeSourceDetails> leadIncomeSourceDetailList = incomeSourceDetailsRepository.findByLeadIdAndIncomeTypeAndStatus(leadId, IncomeTypeEnum.CULTIVATION, CommonStatus.ACTIVE);
            incomeSourceDetailList.addAll(leadIncomeSourceDetailList);

        } else if (customerId != null) {
            List<IncomeSourceDetails> customerIncomeSourceDetailList = incomeSourceDetailsRepository.findByCustomerIdAndIncomeTypeAndStatus(customerId, IncomeTypeEnum.CULTIVATION, CommonStatus.ACTIVE);
            incomeSourceDetailList.addAll(customerIncomeSourceDetailList);
        }

        if (linkedPersonList != null && !linkedPersonList.isEmpty()) {
            for (LinkedPerson linkedPerson : linkedPersonList) {
                List<IncomeSourceDetails> linkedPersonIncomeSourceDetailList = incomeSourceDetailsRepository.findByLinkedPersonIdAndIncomeTypeAndStatus(linkedPerson.getId(), IncomeTypeEnum.CULTIVATION, CommonStatus.ACTIVE);
                incomeSourceDetailList.addAll(linkedPersonIncomeSourceDetailList);
            }
        }

        if (!incomeSourceDetailList.isEmpty()) {
            for (IncomeSourceDetails incomeSourceDetail : incomeSourceDetailList) {
                List<CultivationIncomeDetails> cultivationIncomeDetailList = cultivationIncomeDetailsRepository.findByIncomeSourceDetailIdAndStatus(incomeSourceDetail.getId(), CommonStatus.ACTIVE);

                if (!cultivationIncomeDetailList.isEmpty()) {
                    for (CultivationIncomeDetails cultivationIncomeDetail : cultivationIncomeDetailList) {
                        List<CultivationIncomeExpenses> cultivationIncomeExpensesList = cultivationIncomeExpensesRepository.findByCultivationIncomeDetailsAndStatus(cultivationIncomeDetail, CommonStatus.ACTIVE);

                        if (!cultivationIncomeExpensesList.isEmpty()) {

                            for (CultivationIncomeExpenses cultivationIncomeExpenses : cultivationIncomeExpensesList) {

                                if (cultivationIncomeExpenses.getCultivationIncomeType() != null) {
                                    income = income.add(cultivationIncomeExpenses.getAmount());

                                } else if (cultivationIncomeExpenses.getExpenseType() != null) {
                                    expense = expense.add(cultivationIncomeExpenses.getAmount());
                                }
                            }
                        }
                    }
                }
            }
        }

        incomeExpenseSummaryResponse.setCultivationIncome(income);
        incomeExpenseSummaryResponse.setCultivationExpenses(expense);
        BigDecimal netIncome = income.subtract(expense);
        incomeExpenseSummaryResponse.setNetCultivationIncome(netIncome);
    }

    private void setOtherIncomeExpenseDetails(Long leadId, Long customerId, List<LinkedPerson> linkedPersonList, IncomeExpenseSummaryResponse incomeExpenseSummaryResponse) {
        List<IncomeSourceDetails> incomeSourceDetailList = new ArrayList<>();
        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;

        if (leadId != null) {
            List<IncomeSourceDetails> leadIncomeSourceDetailList = incomeSourceDetailsRepository.findByLeadIdAndIncomeTypeAndStatus(leadId, IncomeTypeEnum.OTHER, CommonStatus.ACTIVE);
            incomeSourceDetailList.addAll(leadIncomeSourceDetailList);

        } else if (customerId != null) {
            List<IncomeSourceDetails> customerIncomeSourceDetailList = incomeSourceDetailsRepository.findByCustomerIdAndIncomeTypeAndStatus(customerId, IncomeTypeEnum.OTHER, CommonStatus.ACTIVE);
            incomeSourceDetailList.addAll(customerIncomeSourceDetailList);
        }

        if (linkedPersonList != null && !linkedPersonList.isEmpty()) {
            for (LinkedPerson linkedPerson : linkedPersonList) {
                List<IncomeSourceDetails> linkedPersonIncomeSourceDetailList = incomeSourceDetailsRepository.findByLinkedPersonIdAndIncomeTypeAndStatus(linkedPerson.getId(), IncomeTypeEnum.OTHER, CommonStatus.ACTIVE);
                incomeSourceDetailList.addAll(linkedPersonIncomeSourceDetailList);
            }
        }

        if (!incomeSourceDetailList.isEmpty()) {
            for (IncomeSourceDetails incomeSourceDetail : incomeSourceDetailList) {
                List<OtherIncomeDetails> otherIncomeDetailList = otherIncomeDetailsRepository.findByIncomeSourceDetailsIdAndStatus(incomeSourceDetail, CommonStatus.ACTIVE);

                if (!otherIncomeDetailList.isEmpty()) {
                    for (OtherIncomeDetails otherIncomeDetail : otherIncomeDetailList) {
                        List<OtherIncomeExpenses> otherIncomeExpensesList = otherIncomeExpensesRepository.findByOtherIncomeDetailsIdAndStatus(otherIncomeDetail, CommonStatus.ACTIVE);

                        if (!otherIncomeExpensesList.isEmpty()) {
                            for (OtherIncomeExpenses otherIncomeExpenses : otherIncomeExpensesList) {

                                if (otherIncomeExpenses.getIncomeTypeId() != null) {
                                    income = income.add(otherIncomeExpenses.getAmount());

                                } else if (otherIncomeExpenses.getExpenseTypeId() != null) {
                                    expense = expense.add(otherIncomeExpenses.getAmount());
                                }
                            }
                        }
                    }
                }
            }

        }
        incomeExpenseSummaryResponse.setOtherIncome(income);
        incomeExpenseSummaryResponse.setOtherExpenses(expense);
        BigDecimal netIncome = income.subtract(expense);
        incomeExpenseSummaryResponse.setNetOtherIncome(netIncome);

    }

    private void setHouseHoldExpenseDetails(Long leadId, Long customerId, List<LinkedPerson> linkedPersonList, IncomeExpenseSummaryResponse incomeExpenseSummaryResponse) {
        List<IncomeSourceDetails> incomeSourceDetailList = new ArrayList<>();
        BigDecimal expense = BigDecimal.ZERO;

        if (leadId != null) {
            List<IncomeSourceDetails> leadIncomeSourceDetailList = incomeSourceDetailsRepository.findByLeadIdAndStatus(leadId, CommonStatus.ACTIVE);
            incomeSourceDetailList.addAll(leadIncomeSourceDetailList);

        } else if (customerId != null) {
            List<IncomeSourceDetails> customerIncomeSourceDetailList = incomeSourceDetailsRepository.findByCustomerIdAndStatus(customerId, CommonStatus.ACTIVE);
            incomeSourceDetailList.addAll(customerIncomeSourceDetailList);
        }

        if (linkedPersonList != null && !linkedPersonList.isEmpty()) {
            for (LinkedPerson linkedPerson : linkedPersonList) {
                List<IncomeSourceDetails> linkedPersonIncomeSourceDetailList = incomeSourceDetailsRepository.findByLinkedPersonIdAndStatus(linkedPerson.getId(), CommonStatus.ACTIVE);
                incomeSourceDetailList.addAll(linkedPersonIncomeSourceDetailList);
            }
        }

        if (!incomeSourceDetailList.isEmpty()) {

            for (IncomeSourceDetails incomeSourceDetail : incomeSourceDetailList) {
                List<HouseHoldExpenseDetails> houseHoldExpenseDetailList = houseHoldExpenseDetailsRepository.findByIncomeSourceDetailsIdAndStatus(incomeSourceDetail.getId(), CommonStatus.ACTIVE);

                if (!houseHoldExpenseDetailList.isEmpty()) {
                    for (HouseHoldExpenseDetails houseHoldExpenseDetail : houseHoldExpenseDetailList) {
                        expense = expense.add(houseHoldExpenseDetail.getFinalCost());
                    }
                }
            }
        }
        incomeExpenseSummaryResponse.setTotalHouseHoldExpenses(expense);
    }

    @Override
    public IncomeExpenseSummaryResponse findByLeadId(Long leadId) {
        IncomeExpenseSummaryResponse incomeExpenseSummaryResponse = new IncomeExpenseSummaryResponse();
        IncomeExpenseSummaryResponse incomeExpenseSummaryLeadResponse = new IncomeExpenseSummaryResponse();
        List<IncomeExpenseSummaryResponse> incomeExpenseSummaryResponseList = new ArrayList<>();
        BigDecimal businessIncome = BigDecimal.ZERO;
        BigDecimal businessExpenses = BigDecimal.ZERO;
        BigDecimal taxes = BigDecimal.ZERO;
        BigDecimal netBusinessIncome = BigDecimal.ZERO;
        BigDecimal salaryIncome = BigDecimal.ZERO;
        BigDecimal salaryExpenses = BigDecimal.ZERO;
        BigDecimal netSalaryIncome = BigDecimal.ZERO;
        BigDecimal cultivationIncome = BigDecimal.ZERO;
        BigDecimal cultivationExpenses = BigDecimal.ZERO;
        BigDecimal netCultivationIncome = BigDecimal.ZERO;
        BigDecimal otherIncome = BigDecimal.ZERO;
        BigDecimal otherExpenses = BigDecimal.ZERO;
        BigDecimal netOtherIncome = BigDecimal.ZERO;
        BigDecimal totalGrossIncome = BigDecimal.ZERO;
        BigDecimal totalHouseHoldExpenses = BigDecimal.ZERO;
        BigDecimal totalFinancialCommitment = BigDecimal.ZERO;
        BigDecimal totalNetIncome = BigDecimal.ZERO;
        BigDecimal fourtyPercentNetIncome = BigDecimal.ZERO;

        Optional<LeadInfo> lead = leadInfoRepository.findById(leadId);
        if (!lead.isPresent()) {
            throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
        }
        setBusinessIncomeExpenseAndTaxDetails(leadId, null, null, incomeExpenseSummaryLeadResponse);
        setSalaryIncomeExpenseDetails(leadId, null, null, incomeExpenseSummaryLeadResponse);
        setCultivationIncomeExpenseDetails(leadId, null, null, incomeExpenseSummaryLeadResponse);
        setOtherIncomeExpenseDetails(leadId, null, null, incomeExpenseSummaryLeadResponse);
        setHouseHoldExpenseDetails(leadId, null, null, incomeExpenseSummaryLeadResponse);

        BigDecimal grossIncomeLead = incomeExpenseSummaryLeadResponse.getNetBusinessIncome().add(incomeExpenseSummaryLeadResponse.getNetCultivationIncome())
                .add(incomeExpenseSummaryLeadResponse.getNetSalaryIncome()).add(incomeExpenseSummaryLeadResponse.getNetOtherIncome());

        incomeExpenseSummaryLeadResponse.setTotalGrossIncome(grossIncomeLead);

        // dependancy task not developed
        incomeExpenseSummaryLeadResponse.setTotalFinancialCommitment(BigDecimal.ZERO);

        BigDecimal totalNetIncomeLead = grossIncomeLead.subtract(incomeExpenseSummaryLeadResponse.getTotalHouseHoldExpenses().add(incomeExpenseSummaryLeadResponse.getTotalFinancialCommitment()));
        incomeExpenseSummaryLeadResponse.setTotalNetIncome(totalNetIncomeLead);

        BigDecimal fourtyPercentNetIncomeLead = totalNetIncomeLead.multiply(Constants.FOURTY_PERCENT);
        incomeExpenseSummaryLeadResponse.setFourtyPercentNetIncome(fourtyPercentNetIncomeLead);

        incomeExpenseSummaryResponseList.add(incomeExpenseSummaryLeadResponse);

        List<Customer> customerList = customerRepository.findByLeadId(lead.get().getId());
        if (!customerList.isEmpty()) {
            for (Customer customer : customerList) {
                incomeExpenseSummaryResponseList.add(findByCustomerId(customer.getId()));
            }
        }

        if (!incomeExpenseSummaryResponseList.isEmpty()) {
            for (IncomeExpenseSummaryResponse incomeExpenseSummaryListResp : incomeExpenseSummaryResponseList) {

                businessIncome = businessIncome.add(incomeExpenseSummaryListResp.getBusinessIncome());
                businessExpenses = businessExpenses.add(incomeExpenseSummaryListResp.getBusinessExpenses());
                taxes = taxes.add(incomeExpenseSummaryListResp.getTaxes());
                netBusinessIncome = netBusinessIncome.add(incomeExpenseSummaryListResp.getNetBusinessIncome());
                salaryIncome = salaryIncome.add(incomeExpenseSummaryListResp.getSalaryIncome());
                salaryExpenses = salaryExpenses.add(incomeExpenseSummaryListResp.getSalaryExpenses());
                netSalaryIncome = netSalaryIncome.add(incomeExpenseSummaryListResp.getNetSalaryIncome());
                cultivationIncome = cultivationIncome.add(incomeExpenseSummaryListResp.getCultivationIncome());
                cultivationExpenses = cultivationExpenses.add(incomeExpenseSummaryListResp.getCultivationExpenses());
                netCultivationIncome = netCultivationIncome.add(incomeExpenseSummaryListResp.getNetCultivationIncome());
                otherIncome = otherIncome.add(incomeExpenseSummaryListResp.getOtherIncome());
                otherExpenses = otherExpenses.add(incomeExpenseSummaryListResp.getOtherExpenses());
                netOtherIncome = netOtherIncome.add(incomeExpenseSummaryListResp.getNetOtherIncome());
                totalGrossIncome = totalGrossIncome.add(incomeExpenseSummaryListResp.getTotalGrossIncome());
                totalHouseHoldExpenses = totalHouseHoldExpenses.add(incomeExpenseSummaryListResp.getTotalHouseHoldExpenses());
                totalFinancialCommitment = totalFinancialCommitment.add(incomeExpenseSummaryListResp.getTotalFinancialCommitment());
                totalNetIncome = totalNetIncome.add(incomeExpenseSummaryListResp.getTotalNetIncome());
                fourtyPercentNetIncome = fourtyPercentNetIncome.add(incomeExpenseSummaryListResp.getFourtyPercentNetIncome());

            }

        }
        incomeExpenseSummaryResponse.setBusinessIncome(businessIncome);
        incomeExpenseSummaryResponse.setBusinessExpenses(businessExpenses);
        incomeExpenseSummaryResponse.setTaxes(taxes);
        incomeExpenseSummaryResponse.setNetBusinessIncome(netBusinessIncome);
        incomeExpenseSummaryResponse.setSalaryIncome(salaryIncome);
        incomeExpenseSummaryResponse.setSalaryExpenses(salaryExpenses);
        incomeExpenseSummaryResponse.setNetSalaryIncome(netSalaryIncome);
        incomeExpenseSummaryResponse.setCultivationIncome(cultivationIncome);
        incomeExpenseSummaryResponse.setCultivationExpenses(cultivationExpenses);
        incomeExpenseSummaryResponse.setNetCultivationIncome(netCultivationIncome);
        incomeExpenseSummaryResponse.setOtherIncome(otherIncome);
        incomeExpenseSummaryResponse.setOtherExpenses(otherExpenses);
        incomeExpenseSummaryResponse.setNetOtherIncome(netOtherIncome);
        incomeExpenseSummaryResponse.setTotalGrossIncome(totalGrossIncome);
        incomeExpenseSummaryResponse.setTotalHouseHoldExpenses(totalHouseHoldExpenses);
        incomeExpenseSummaryResponse.setTotalFinancialCommitment(totalFinancialCommitment);
        incomeExpenseSummaryResponse.setTotalNetIncome(totalNetIncome);
        incomeExpenseSummaryResponse.setFourtyPercentNetIncome(fourtyPercentNetIncome);

        return incomeExpenseSummaryResponse;
    }

}
