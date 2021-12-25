package com.fusionx.lending.transaction.mt;

/**
 * @author Amal
 * @since 25/01/2019
 */
public final class TenantHolder {

    private static final ThreadLocal<String> TENANT = new ThreadLocal<>();

    private TenantHolder() {
    }

    public static String getTenantId() {
        return TENANT.get();
    }

    public static void setTenantId(String tenantId) {

        TENANT.set(tenantId);

    }

    public static void clear() {
        TENANT.remove();
    }


}
