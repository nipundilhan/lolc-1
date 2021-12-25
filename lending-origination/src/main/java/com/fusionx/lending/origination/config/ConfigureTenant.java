package com.fusionx.lending.origination.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import com.fusionx.lending.origination.exception.TenantNotFoundException;
import com.fusionx.lending.origination.mt.TenantHolder;


@Aspect
@Configuration
public class ConfigureTenant {
	
	@Before("execution(* com.fusionx.lending.origination.controller.*.*(..))")
	public void before(JoinPoint joinPoint){
        Object[] methodeParams = joinPoint.getArgs(); 
        String tenantId = (String)methodeParams[0]; 
        if (tenantId == null || tenantId.equalsIgnoreCase("null") || tenantId.isEmpty()) {
			throw new TenantNotFoundException(null);
		}else {
			TenantHolder.setTenantId(tenantId);
		}
    }

	
	@After("execution(* com.fusionx.lending.origination.controller.*.*(..))")
    public void after(JoinPoint joinPoint){
		TenantHolder.clear();
    } 
	
}
