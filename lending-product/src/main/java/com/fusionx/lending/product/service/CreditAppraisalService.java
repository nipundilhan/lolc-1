package com.fusionx.lending.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fusionx.lending.product.domain.LeadApprovalDetail;
import com.fusionx.lending.product.resources.BpmTaskRequest;
import com.fusionx.lending.product.resources.CreditAppraisalApproveTaskInstanceRequest;
import com.fusionx.lending.product.resources.CreditAppraisalLevelDetail;
import com.fusionx.lending.product.resources.CreditAppraisalMainResponse;
import com.fusionx.lending.product.resources.CreditAppraisalRequest;
import com.fusionx.lending.product.resources.CreditAppraisalUpdateTaskInstanceRequest;

public interface CreditAppraisalService {
	Page<CreditAppraisalMainResponse> readAllApproveTask(Pageable pageable,String tenantId,CreditAppraisalRequest request) throws Exception;
	Page<CreditAppraisalMainResponse> readAllUpdateTask(Pageable pageable,String tenantId,CreditAppraisalRequest request) throws Exception;
	String claimTask(BpmTaskRequest data,String tenantId) throws Exception;
	String startTask(BpmTaskRequest data,String tenantId) throws Exception;
	String releaseTask(BpmTaskRequest data,String tenantId) throws Exception;
	Long createProcess(String tenantId,String leadNo,CreditAppraisalLevelDetail creditAppraisalLevelDetail) throws Exception;
	LeadApprovalDetail completeApproveTaskInstance(String tenantId,String leadNo,CreditAppraisalApproveTaskInstanceRequest request);
	LeadApprovalDetail completeUpdateTaskInstance(String tenantId,String leadNo,CreditAppraisalUpdateTaskInstanceRequest request);
}
