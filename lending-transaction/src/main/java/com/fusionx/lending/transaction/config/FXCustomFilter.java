/**
 *
 */
package com.fusionx.lending.transaction.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

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
        System.out.println("at FXCustomFilter.dofilter...");
        HttpServletRequest htsr = (HttpServletRequest) servReq;
        Principal userPr = htsr.getUserPrincipal();
        System.out.println("User principal:" + userPr);
        filCha.doFilter(servReq, servRes);

    }

    public void destroy() {
        System.out.println("at FXCustomFilter.destroy");
    }
}
