package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.SalaryIncomeExpenses;
import com.fusionx.lending.origination.resource.SalaryIncomeExpenseDetailResponse;
import com.fusionx.lending.origination.resource.SalaryIncomeExpensesAddMainResource;
import com.fusionx.lending.origination.resource.SalaryIncomeExpensesAddResource;
import com.fusionx.lending.origination.resource.SalaryIncomeExpensesUpdateMainResource;
import com.fusionx.lending.origination.resource.SalaryIncomeExpensesUpdateResource;

/**
 * API Service related to salary income expense
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        12-09-2021      -               FXL-784     Nipun Dilhan      Created
 * <p>
 *
 */

@Service
public interface SalaryIncomeExpensesService {
	
	SalaryIncomeExpenses saveIncomeExpense(String tenantId,String user,Long salaryIncomeDetailId, SalaryIncomeExpensesAddResource salaryIncomeExpensesAddResource);
	
	SalaryIncomeExpenses updateIncomeExpense(String tenantId,String user,Long salaryIncomeDetailId, SalaryIncomeExpensesUpdateResource salaryIncomeExpensesUpdateResource);
	
	SalaryIncomeExpenses findById(Long id);

	SalaryIncomeExpenseDetailResponse getSalaryIncomeExpenseDetails(Long id);
	
	
}
