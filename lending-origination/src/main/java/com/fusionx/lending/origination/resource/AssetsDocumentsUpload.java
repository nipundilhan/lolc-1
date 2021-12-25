package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AssetsDocumentsUpload {
	@Valid
	@JsonProperty(value = "documentUploadDetails")
	List<AssetDetailsDocumentsUploadRequestResource> documentUploadDetails;

	public List<AssetDetailsDocumentsUploadRequestResource> getDocumentUploadDetails() {
		return documentUploadDetails;
	}

	public void setDocumentUploadDetails(List<AssetDetailsDocumentsUploadRequestResource> documentUploadDetails) {
		this.documentUploadDetails = documentUploadDetails;
	}
	
	
}
