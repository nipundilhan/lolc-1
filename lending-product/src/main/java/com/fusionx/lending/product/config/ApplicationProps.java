package com.fusionx.lending.product.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "tenant")
public class ApplicationProps {

	private List<PropDataSource> dataSources;
	
	private List<String> tenantIds;
	
	
	public List<PropDataSource> getDataSources() {
		return dataSources;
	}

	public void setDataSources(List<PropDataSource> dataSources) {
		this.dataSources = dataSources;
	}

	public List<String> getTenantIds() {
		List<String> ids=new ArrayList<>();
		for (PropDataSource dataSource : this.dataSources) {
			ids.add(dataSource.getTenantId());
		}
		tenantIds=ids;
		return tenantIds;
	}

	public void setTenantIds(List<String> tenantIds) {
		this.tenantIds = tenantIds;
	}
	
	
}
