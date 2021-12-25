package com.fusionx.lending.origination.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UpadetLeadFinalApprovalFlowStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean approve;
}