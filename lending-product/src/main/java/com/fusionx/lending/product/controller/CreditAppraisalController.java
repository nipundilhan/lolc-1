package com.fusionx.lending.product.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.LeadApprovalDetail;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.BpmTaskRequest;
import com.fusionx.lending.product.resources.CreditAppraisalApproveTaskInstanceRequest;
import com.fusionx.lending.product.resources.CreditAppraisalLevelDetail;
import com.fusionx.lending.product.resources.CreditAppraisalMainResponse;
import com.fusionx.lending.product.resources.CreditAppraisalRequest;
import com.fusionx.lending.product.resources.CreditAppraisalTaskListRequest;
import com.fusionx.lending.product.resources.CreditAppraisalTaskListResponse;
import com.fusionx.lending.product.resources.CreditAppraisalUpdateTaskInstanceRequest;
import com.fusionx.lending.product.service.CreditAppraisalService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * API Service related to Credit Appraisal workflow.
 *
 * @author Achini
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        23-11-2021      -               FXL-1947     Achini      Created
 * <p>
 *
 */

@RestController
@RequestMapping(value = "/credit-appraisal")
@CrossOrigin(origins = "*")
public class CreditAppraisalController extends MessagePropertyBase{
	
	@Autowired
	private CreditAppraisalService creditAppraisalService;
	
	@ApiOperation(value = "get credit appraisal bpm approve task list")
	@PostMapping(value = "/{tenantId}/approve/tasks")
	public Page<CreditAppraisalMainResponse> readAllApproveTask(@PathVariable(value = "tenantId") String tenantId,
			@PageableDefault(size = 10) Pageable pageable,
			@Valid @RequestBody CreditAppraisalRequest request) throws Exception {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
		return creditAppraisalService.readAllApproveTask(pageable, tenantId, request);
	}
	
	@ApiOperation(value = "get credit appraisal bpm update task list")
	@PostMapping(value = "/{tenantId}/update/tasks")
	public Page<CreditAppraisalMainResponse> readAllUpdateTask(@PathVariable(value = "tenantId") String tenantId,
			@PageableDefault(size = 10) Pageable pageable,
			@Valid @RequestBody CreditAppraisalRequest request) throws Exception {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
		return creditAppraisalService.readAllUpdateTask(pageable, tenantId, request);
	}
	
	@ApiOperation(value = "create credit appraisal bpm approve process")
	@PostMapping(value = "/{tenantId}/lead/{leadNo}/process-start")
	public Long createProcess(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "leadNo") String leadNo,
			@Valid @RequestBody CreditAppraisalLevelDetail request) throws Exception {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
		return creditAppraisalService.createProcess(tenantId, leadNo,request);
		
	}
	
	@ApiOperation(value = "Assign a task to login user")
	@PutMapping(value = "/{tenantId}/task-claim")
	public String claimTask(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody BpmTaskRequest request) throws Exception {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
		return creditAppraisalService.claimTask(request, tenantId);
	}

	@ApiOperation(value = "Start a task to login user")
	@PutMapping(value = "/{tenantId}/task-start")
	public String startTask(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody BpmTaskRequest request) throws Exception {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
		return creditAppraisalService.startTask(request, tenantId);
	}
	
	@ApiOperation(value = "Release assign task")
	@PutMapping(value = "/{tenantId}/task-release")
	public String releaseTask(@PathVariable(value = "tenantId") String tenantId,
			@Valid @RequestBody BpmTaskRequest request) throws Exception {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
		return creditAppraisalService.releaseTask(request, tenantId);
	}
	
	
	@ApiOperation(value = "Complete apprve task.")
	@PutMapping(value = "/{tenantId}/lead/{leadNo}/approve/task-complete")
	public ResponseEntity<Object> completeApprveTask(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "leadNo") String leadNo,
			@Valid @RequestBody CreditAppraisalApproveTaskInstanceRequest request) throws Exception {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
		LeadApprovalDetail leadApprovalDetail= creditAppraisalService.completeApproveTaskInstance(tenantId, leadNo, request);
		
		return new ResponseEntity<>(leadApprovalDetail, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Complete update task.")
	@PutMapping(value = "/{tenantId}/lead/{leadNo}/update/task-complete")
	public ResponseEntity<Object> completeUpdateTask(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "leadNo") String leadNo,
			@Valid @RequestBody CreditAppraisalUpdateTaskInstanceRequest request) throws Exception {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
		LeadApprovalDetail leadApprovalDetail= creditAppraisalService.completeUpdateTaskInstance(tenantId, leadNo, request);
		return new ResponseEntity<>(leadApprovalDetail, HttpStatus.OK);
	}
}
