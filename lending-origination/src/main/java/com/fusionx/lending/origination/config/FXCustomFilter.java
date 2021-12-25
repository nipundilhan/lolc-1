/**
 * 
 */
package com.fusionx.lending.origination.config;

import java.io.IOException;
import java.net.URL;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.fusionx.lending.origination.error.code.RequestMapperEnum;

/**
 * @author RK
 *
 */
@Component
public class FXCustomFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("at FXCustomFilter.init method ...");

	}

	@Override
	public void doFilter(ServletRequest servReq, ServletResponse servRes, FilterChain filCha)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servReq;
		
		RequestMapperEnum requestMapperEnum = createRequestMapper(httpServletRequest);
		if (requestMapperEnum!=null)
			httpServletRequest.setAttribute("requestMapper", requestMapperEnum);
		
		filCha.doFilter(servReq, servRes);
	}

	public void destroy() {
		System.out.println("at FXCustomFilter.destroy");
	}
	
	private RequestMapperEnum createRequestMapper(HttpServletRequest httpServletRequest) {
		return RequestMapperEnum.getEnumByPattern(httpServletRequest.getRequestURI(), httpServletRequest.getContextPath());
	}
}
