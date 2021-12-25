package com.fusionx.lending.product.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusionx.lending.product.config.CommonModuleProperties;
import com.fusionx.lending.product.core.DefaultRequestHeaders;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.LeadApprovalDetail;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.LeadStatusEnum;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.WorkFlowException;
import com.fusionx.lending.product.resources.BpmTaskRequest;
import com.fusionx.lending.product.resources.CreateCreditAppraisalProcessInstanceRequest;
import com.fusionx.lending.product.resources.CreditAppraisalApproveTaskInstanceRequest;
import com.fusionx.lending.product.resources.CreditAppraisalLevelDetail;
import com.fusionx.lending.product.resources.CreditAppraisalMainResponse;
import com.fusionx.lending.product.resources.CreditAppraisalRequest;
import com.fusionx.lending.product.resources.CreditAppraisalResponse;
import com.fusionx.lending.product.resources.CreditAppraisalTaskListRequest;
import com.fusionx.lending.product.resources.CreditAppraisalTaskListResponse;
import com.fusionx.lending.product.resources.CreditAppraisalUpdateTaskInstanceBpmRequest;
import com.fusionx.lending.product.resources.CreditAppraisalUpdateTaskInstanceRequest;
import com.fusionx.lending.product.resources.CreditAppraisalUserDetail;
import com.fusionx.lending.product.resources.LeadApprovalDetailAddResources;
import com.fusionx.lending.product.resources.LeadInfoRequestResponseResource;
import com.fusionx.lending.product.service.CreditAppraisalService;
import com.fusionx.lending.product.service.LeadApprovalDetailService;
import com.fusionx.lending.product.service.ValidationService;

@Service
public class CreditAppraisalServiceImpl implements CreditAppraisalService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CommonModuleProperties commonModuleProperties;
	@Autowired
	private ValidationService validationService;
	
	@Autowired
	private LeadApprovalDetailService leadApprovalDetailService;

	@Override
	public Long createProcess(String tenantId, String leadNo,CreditAppraisalLevelDetail creditAppraisalLevelDetail) throws Exception {
		// TODO Auto-generated method stub
		CreateCreditAppraisalProcessInstanceRequest processInstanceRequest = new CreateCreditAppraisalProcessInstanceRequest();
//		processInstanceRequest.setCreditAppraisalApproveUrl("http://localhost:8081/demo/approve");
		processInstanceRequest.setCreditAppraisalLeadNo(leadNo);		

		processInstanceRequest.setCreditAppraisalLevels(creditAppraisalLevelDetail);
		processInstanceRequest.setProcessCreateUser(LogginAuthentcation.getInstance().getUserName());

		return validationService.initiateCreditAppraisalWorkFlow(processInstanceRequest, tenantId);

	}

	@Override
	public Page<CreditAppraisalMainResponse> readAllApproveTask(Pageable pageable, String tenantId,
			CreditAppraisalRequest request) throws Exception {
		CreditAppraisalTaskListRequest taskListRequest = new CreditAppraisalTaskListRequest();
		List<String> status;
		taskListRequest.setAssignTask(request.isAssignTask());
		if (request.isAssignTask()) {
			status = new ArrayList<String>();
			status.add("Reserved");
			status.add("InProgress");

		} else {
			status = new ArrayList<String>();
			status.add("Ready");
		}
		taskListRequest.setTaskStatusList(status);
		taskListRequest.setTaskOwner(LogginAuthentcation.getInstance().getUserName());
		taskListRequest.setTaskName("CreditAppraisalTask");

		CreditAppraisalResponse response = validationService.getAllUpdateTasks(pageable, tenantId,
				taskListRequest);
//		Page<CreditAppraisalTaskListResponse> creditAppraisalTaskList = response.getCreditAppraisalTaskList();
		List<CreditAppraisalTaskListResponse> taskList = response.getCreditAppraisalTaskList();

		List<CreditAppraisalMainResponse> mainResponseList = new ArrayList<>();
		CreditAppraisalMainResponse mainResponse = new CreditAppraisalMainResponse();
		LeadInfoRequestResponseResource leadInfo;
		for (CreditAppraisalTaskListResponse creditAppraisalTaskListResponse : taskList) {
			leadInfo = validationService.getLeadInfoById(tenantId, creditAppraisalTaskListResponse.getLeadNo());
			mainResponse.setCreatedAt(creditAppraisalTaskListResponse.getCreatedAt());// task created date
			mainResponse.setCustomerName(leadInfo.getCustomers().get(0).getFullName());
			mainResponse.setCustomerReferenceCode(leadInfo.getCustomers().get(0).getReferenceNo());
			mainResponse.setLeadNo(leadInfo.getId());
			mainResponse.setStatus(leadInfo.getLeadStatus());
			mainResponse.setContainerId(creditAppraisalTaskListResponse.getContainerId());
			mainResponse.setTaskInstantId(creditAppraisalTaskListResponse.getTaskInstantId());
			mainResponseList.add(mainResponse);
		}
		return new PageImpl<>(mainResponseList, PageRequest.of(response.getPageNumber(), response.getPageSize()), response.getNumberOfElements());

	}

	@Override
	public Page<CreditAppraisalMainResponse> readAllUpdateTask(Pageable pageable, String tenantId,
			CreditAppraisalRequest request) throws Exception {
		CreditAppraisalTaskListRequest taskListRequest = new CreditAppraisalTaskListRequest();
		List<String> status;
		taskListRequest.setAssignTask(request.isAssignTask());
		if (request.isAssignTask()) {
			status = new ArrayList<String>();
			status.add("Reserved");
			status.add("InProgress");

		} else {
			status = new ArrayList<String>();
			status.add("Ready");
		}
		taskListRequest.setTaskStatusList(status);
		taskListRequest.setTaskOwner(LogginAuthentcation.getInstance().getUserName());
		taskListRequest.setTaskName("CreditAppraisalUpdateTask");

		CreditAppraisalResponse response = validationService.getAllUpdateTasks(pageable, tenantId,
				taskListRequest);
//		Page<CreditAppraisalTaskListResponse> creditAppraisalTaskList = response.getCreditAppraisalTaskList();
		List<CreditAppraisalTaskListResponse> taskList = response.getCreditAppraisalTaskList();

		List<CreditAppraisalMainResponse> mainResponseList = new ArrayList<>();
		CreditAppraisalMainResponse mainResponse = new CreditAppraisalMainResponse();
		LeadInfoRequestResponseResource leadInfo;
		for (CreditAppraisalTaskListResponse creditAppraisalTaskListResponse : taskList) {
			leadInfo = validationService.getLeadInfoById(tenantId, creditAppraisalTaskListResponse.getLeadNo());
			mainResponse.setCreatedAt(creditAppraisalTaskListResponse.getCreatedAt());// task created date
			mainResponse.setCustomerName(leadInfo.getCustomers().get(0).getFullName());
			mainResponse.setCustomerReferenceCode(leadInfo.getCustomers().get(0).getReferenceNo());
			mainResponse.setLeadNo(leadInfo.getId());
			mainResponse.setStatus(leadInfo.getLeadStatus());
			mainResponseList.add(mainResponse);
		}
		return new PageImpl<>(mainResponseList, PageRequest.of(response.getPageNumber(), response.getPageSize()), response.getNumberOfElements());

	}

	@Override
	public String claimTask(BpmTaskRequest bpmTaskRequest, String tenantId) throws Exception {
		String claimResponse = validationService.workFlowTaskStatesUpdate(bpmTaskRequest, tenantId,
				LogginAuthentcation.getInstance().getUserName(), "claimed");
		return claimResponse;
	}

	@Override
	public String startTask(BpmTaskRequest bpmTaskRequest, String tenantId) throws Exception {
		String startResponse = validationService.workFlowTaskStatesUpdate(bpmTaskRequest, tenantId,
				LogginAuthentcation.getInstance().getUserName(), "started");
		return startResponse;
	}

	

	@Override
	public String releaseTask(BpmTaskRequest bpmTaskRequest, String tenantId) throws Exception {
		String releaseResponse = validationService.workFlowTaskStatesUpdate(bpmTaskRequest, tenantId,
				LogginAuthentcation.getInstance().getUserName(), "released");
		return releaseResponse;
	}

	@Override
	public LeadApprovalDetail completeApproveTaskInstance(String tenantId, String leadNo,
			CreditAppraisalApproveTaskInstanceRequest request) {
		BpmTaskRequest bpmTaskRequest = new BpmTaskRequest();
		bpmTaskRequest.setContainerId(request.getContainerId());
		bpmTaskRequest.setTaskInstanceId(request.getTaskInstanceId());
		//start task
		String startResponse = validationService.workFlowTaskStatesUpdate(bpmTaskRequest, tenantId,LogginAuthentcation.getInstance().getUserName(), "started");
		
		String completeResponse = validationService.creditAppraisalWorkFlowApproveTaskComplete(request, tenantId,
				LogginAuthentcation.getInstance().getUserName(), "completed");
		
		LeadApprovalDetailAddResources leadApprovalDetailAddResources=new LeadApprovalDetailAddResources();
		leadApprovalDetailAddResources.setAction(LeadStatusEnum.APPROVE.name()); 
		leadApprovalDetailAddResources.setApprovalGroupId(request.getApprovalGroupId());
		leadApprovalDetailAddResources.setApprovalGroupUserId(request.getApprovalGroupUserId());
		leadApprovalDetailAddResources.setComments(request.getComments());
		leadApprovalDetailAddResources.setLeadId(Long.parseLong(leadNo));
		leadApprovalDetailAddResources.setStatus(CommonStatus.ACTIVE.name());
		
		
		
		return leadApprovalDetailService.addLeadApprovalDetails(tenantId, leadApprovalDetailAddResources);
	}

	@Override
	public LeadApprovalDetail completeUpdateTaskInstance(String tenantId, String leadNo, CreditAppraisalUpdateTaskInstanceRequest request) {

		String startResponse = validationService.workFlowTaskStatesUpdate(request.getBpmTaskRequest(), tenantId,
				LogginAuthentcation.getInstance().getUserName(), "started");
		
		CreditAppraisalUpdateTaskInstanceBpmRequest bpmRequest=new CreditAppraisalUpdateTaskInstanceBpmRequest();
		bpmRequest.setCreditAppraisalLevelDetail(request.getCreditAppraisalLevelDetail());
		String completeResponse = validationService.creditAppraisalWorkFlowUpdateTaskComplete(
				bpmRequest, tenantId, request.getBpmTaskRequest(), LogginAuthentcation.getInstance().getUserName(),
				"completed");

		LeadApprovalDetailAddResources leadApprovalDetailAddResources=new LeadApprovalDetailAddResources();
		leadApprovalDetailAddResources.setAction(LeadStatusEnum.APPROVE.name()); 
		leadApprovalDetailAddResources.setApprovalGroupId(request.getApprovalGroupId());
		leadApprovalDetailAddResources.setApprovalGroupUserId(request.getApprovalGroupUserId());
		leadApprovalDetailAddResources.setComments(request.getComments());
		leadApprovalDetailAddResources.setLeadId(Long.parseLong(leadNo));
		leadApprovalDetailAddResources.setStatus(CommonStatus.ACTIVE.name());		
		
		return leadApprovalDetailService.addLeadApprovalDetails(tenantId, leadApprovalDetailAddResources);
	}

}
