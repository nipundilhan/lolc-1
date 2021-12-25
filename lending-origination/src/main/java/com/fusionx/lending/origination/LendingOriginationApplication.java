package com.fusionx.lending.origination;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableCircuitBreaker
@SpringBootApplication(exclude={SolrAutoConfiguration.class})
@EnableHystrixDashboard
@EnableAutoConfiguration
@EnableSwagger2
public class LendingOriginationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LendingOriginationApplication.class, args);
	}
	
	@Bean
    public Filter filter(){
        ShallowEtagHeaderFilter filter=new ShallowEtagHeaderFilter();
        return filter;
    }
	
}