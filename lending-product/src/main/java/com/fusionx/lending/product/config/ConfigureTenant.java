package com.fusionx.lending.product.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import com.fusionx.lending.product.exception.TenantNotFoundException;
import com.fusionx.lending.product.mt.TenantHolder;

@Aspect
@Configuration
public class ConfigureTenant {
	
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    private void pointcutController() {}
	
	@Before("pointcutController()")
    public void before(JoinPoint joinPoint){
        Object[] methodeParams = joinPoint.getArgs(); 
        String tenantId = (String)methodeParams[0]; 
        if (tenantId == null || tenantId.equalsIgnoreCase("null") || tenantId.isEmpty()) {
			throw new TenantNotFoundException(null);
		}else {
			TenantHolder.setTenantId(tenantId);
		}
    }
	
	@After("pointcutController()")
    public void after(JoinPoint joinPoint){
		TenantHolder.clear();
    }

}
