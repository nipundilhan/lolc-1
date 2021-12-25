package com.fusionx.lending.product.mt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.util.Assert;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Amal
 * @since 25/01/2019 
 */
public class MultiTenantDataSources {

    private Map<String, DataSource> dataSources = new HashMap<>();

    @Getter
    @Setter
    private String defaultTenantId;


    public MultiTenantDataSources(String defaultTenantId) {
        Assert.hasText(defaultTenantId, "Default Tenant Id is required");
        this.defaultTenantId = defaultTenantId;
    }


    public void add(String tenantId, DataSource dataSource) {
        dataSources.put(tenantId, dataSource);
    }


    public void remove(String tenantId) {
        dataSources.remove(tenantId);
    }


    public DataSource get(String tenantId) {
        return dataSources.get(tenantId);
    }


    public DataSource getDefault() {
        return dataSources.get(defaultTenantId);
    }


    public Collection<DataSource> getAll() {
        return dataSources.values();
    }

}
