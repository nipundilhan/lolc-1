package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CreditAppraisalDisbursmentDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.repository.CreditAppraisalDisbursmentDetailsRepository;
import com.fusionx.lending.origination.resource.CreditAppraisalDisbursmentDetailsAddResource;
import com.fusionx.lending.origination.resource.CreditAppraisalDisbursmentDetailsUpdateResource;
import com.fusionx.lending.origination.service.CreditAppraisalDisbursmentDetailsService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * CreditAppraisalDisbursmentDetailsServiceImpl
 * 
 ********************************************************************************************************
 * ### Date Story Point Task No Author Description
 * -------------------------------------------------------------------------------------------------------
 * 1 06-10-2021 FXL-121 FXL-1005 PasinduT Created
 * 
 ********************************************************************************************************
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CreditAppraisalDisbursmentDetailsServiceImpl extends MessagePropertyBase
		implements CreditAppraisalDisbursmentDetailsService {

	@Autowired
	private CreditAppraisalDisbursmentDetailsRepository creditAppraisalDisbursmentDetailsRepository;

	@Autowired
	private ValidateService validateService;

	@Override
	public List<CreditAppraisalDisbursmentDetails> getAll() {
		return creditAppraisalDisbursmentDetailsRepository.findAll();
	}

	@Override
	public Optional<CreditAppraisalDisbursmentDetails> findById(Long id) {
		Optional<CreditAppraisalDisbursmentDetails> isPresentBrand = creditAppraisalDisbursmentDetailsRepository
				.findById(id);
		if (isPresentBrand.isPresent()) {
			return Optional.ofNullable(isPresentBrand.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<CreditAppraisalDisbursmentDetails> findByStatus(String status) {
		return creditAppraisalDisbursmentDetailsRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public CreditAppraisalDisbursmentDetails addCreditAppraisalDisbursmentDetails(String tenantId,
			CreditAppraisalDisbursmentDetailsAddResource creditAppraisalDisbursmentDetailsAddResource) {

		CreditAppraisalDisbursmentDetails creditAppraisalDisbursmentDetails = new CreditAppraisalDisbursmentDetails();
		creditAppraisalDisbursmentDetails.setTenantId(tenantId);
		creditAppraisalDisbursmentDetails.setBalanceDisbursementAmount(
				creditAppraisalDisbursmentDetailsAddResource.getBalanceDisbursementAmount());
		creditAppraisalDisbursmentDetails.setBank(creditAppraisalDisbursmentDetailsAddResource.getBank());
		creditAppraisalDisbursmentDetails.setBankId(creditAppraisalDisbursmentDetailsAddResource.getBankId());
		creditAppraisalDisbursmentDetails.setBranch(creditAppraisalDisbursmentDetailsAddResource.getBranch());
		creditAppraisalDisbursmentDetails.setBranchId(creditAppraisalDisbursmentDetailsAddResource.getBranchId());
		creditAppraisalDisbursmentDetails.setComment(creditAppraisalDisbursmentDetailsAddResource.getComment());
		creditAppraisalDisbursmentDetails.setDeductions(creditAppraisalDisbursmentDetailsAddResource.getDeductions());
		creditAppraisalDisbursmentDetails
				.setDisbursementCondition(creditAppraisalDisbursmentDetailsAddResource.getDisbursementCondition());
		creditAppraisalDisbursmentDetails
				.setDisbursementConditionId(creditAppraisalDisbursmentDetailsAddResource.getDisbursementConditionId());
		creditAppraisalDisbursmentDetails.setAccountNo(creditAppraisalDisbursmentDetailsAddResource.getAccountNo());
		creditAppraisalDisbursmentDetails.setLoanAmount(creditAppraisalDisbursmentDetailsAddResource.getLoanAmount());
		creditAppraisalDisbursmentDetails.setScheduleNo(creditAppraisalDisbursmentDetailsAddResource.getScheduleNo());
		creditAppraisalDisbursmentDetails.setPayeeTypeId(creditAppraisalDisbursmentDetailsAddResource.getPayeeTypeId());
		creditAppraisalDisbursmentDetails.setPayeeType(creditAppraisalDisbursmentDetailsAddResource.getPayeeType());
		creditAppraisalDisbursmentDetails.setPayeeName(creditAppraisalDisbursmentDetailsAddResource.getPayeeName());
		creditAppraisalDisbursmentDetails.setPayMethod(creditAppraisalDisbursmentDetailsAddResource.getPayMethod());
		creditAppraisalDisbursmentDetails.setPayMethodId(creditAppraisalDisbursmentDetailsAddResource.getPayMethodId());

		creditAppraisalDisbursmentDetails
				.setStatus(CommonStatus.valueOf(creditAppraisalDisbursmentDetailsAddResource.getStatus()));
		creditAppraisalDisbursmentDetails.setCreatedDate(validateService.getCreateOrModifyDate());
		creditAppraisalDisbursmentDetails.setSyncTs(validateService.getCreateOrModifyDate());
		creditAppraisalDisbursmentDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return creditAppraisalDisbursmentDetailsRepository.save(creditAppraisalDisbursmentDetails);

	}

	@Override
	public CreditAppraisalDisbursmentDetails updateCreditAppraisalDisbursmentDetails(String tenantId, Long id,
			CreditAppraisalDisbursmentDetailsUpdateResource creditAppraisalDisbursmentDetailsUpdateResource) {

		Optional<CreditAppraisalDisbursmentDetails> isPresentCreditAppraisalDisbursmentDetails = creditAppraisalDisbursmentDetailsRepository
				.findById(id);

		CreditAppraisalDisbursmentDetails creditAppraisalDisbursmentDetails = isPresentCreditAppraisalDisbursmentDetails
				.get();

		creditAppraisalDisbursmentDetails.setTenantId(tenantId);
		creditAppraisalDisbursmentDetails.setBalanceDisbursementAmount(
				creditAppraisalDisbursmentDetailsUpdateResource.getBalanceDisbursementAmount());
		creditAppraisalDisbursmentDetails.setBank(creditAppraisalDisbursmentDetailsUpdateResource.getBank());
		creditAppraisalDisbursmentDetails.setBankId(creditAppraisalDisbursmentDetailsUpdateResource.getBankId());
		creditAppraisalDisbursmentDetails.setBranch(creditAppraisalDisbursmentDetailsUpdateResource.getBranch());
		creditAppraisalDisbursmentDetails.setBranchId(creditAppraisalDisbursmentDetailsUpdateResource.getBranchId());
		creditAppraisalDisbursmentDetails.setComment(creditAppraisalDisbursmentDetailsUpdateResource.getComment());
		creditAppraisalDisbursmentDetails
				.setDeductions(creditAppraisalDisbursmentDetailsUpdateResource.getDeductions());
		creditAppraisalDisbursmentDetails
				.setDisbursementCondition(creditAppraisalDisbursmentDetailsUpdateResource.getDisbursementCondition());
		creditAppraisalDisbursmentDetails.setDisbursementConditionId(
				creditAppraisalDisbursmentDetailsUpdateResource.getDisbursementConditionId());
		creditAppraisalDisbursmentDetails.setAccountNo(creditAppraisalDisbursmentDetailsUpdateResource.getAccountNo());
		creditAppraisalDisbursmentDetails
				.setLoanAmount(creditAppraisalDisbursmentDetailsUpdateResource.getLoanAmount());
		creditAppraisalDisbursmentDetails
				.setScheduleNo(creditAppraisalDisbursmentDetailsUpdateResource.getScheduleNo());
		creditAppraisalDisbursmentDetails
				.setPayeeTypeId(creditAppraisalDisbursmentDetailsUpdateResource.getPayeeTypeId());
		creditAppraisalDisbursmentDetails.setPayeeType(creditAppraisalDisbursmentDetailsUpdateResource.getPayeeType());
		creditAppraisalDisbursmentDetails.setPayeeName(creditAppraisalDisbursmentDetailsUpdateResource.getPayeeName());
		creditAppraisalDisbursmentDetails.setPayMethod(creditAppraisalDisbursmentDetailsUpdateResource.getPayMethod());
		creditAppraisalDisbursmentDetails
				.setPayMethodId(creditAppraisalDisbursmentDetailsUpdateResource.getPayMethodId());

		creditAppraisalDisbursmentDetails.setModifiedDate(validateService.getCreateOrModifyDate());
		creditAppraisalDisbursmentDetails.setSyncTs(validateService.getCreateOrModifyDate());
		creditAppraisalDisbursmentDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

		return creditAppraisalDisbursmentDetailsRepository.saveAndFlush(creditAppraisalDisbursmentDetails);

	}

}
