package com.fusionx.lending.transaction.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Application Props file.
 *
 * @author ChinthakaMa
 */
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
        List<String> ids = new ArrayList<String>();
        for (PropDataSource dataSource : this.dataSources) {
            ids.add(dataSource.getTenantId());
        }
        tenantIds = ids;
        return tenantIds;
    }

    public void setTenantIds(List<String> tenantIds) {
        this.tenantIds = tenantIds;
    }

}
