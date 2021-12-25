package com.fusionx.lending.origination.mt;

import static com.fusionx.lending.origination.Constants.DEFAULT_TENANT_ID;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 * @author Amal
 * @since 25/01/2019 
 */
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantHolder.getTenantId() != null ? TenantHolder.getTenantId() : DEFAULT_TENANT_ID;
    }


    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }


}
