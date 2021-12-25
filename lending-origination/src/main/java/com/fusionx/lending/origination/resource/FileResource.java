package com.fusionx.lending.origination.resource;

import org.springframework.core.io.Resource;

public class FileResource {

	private Resource resource;
	
	private String fileName;

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
