package com.fusionx.lending.transaction.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author RK
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
//@EnableJpaRepositories(basePackages = "com.fusionx.lending.micro.repo") // basePackageClasses = UsersRepository.class)
//@EnableAutoConfiguration

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private FXAuthenticationProvider fap;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        System.out.println("Check auth provider");
        auth.authenticationProvider(fap);
        System.out.println("checked auth provider");
    }

    /*
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("through configure...");

//http.authorizeRequests().antMatchers("/services/country/all").hasRole("FUSION_CLIENT").anyRequest().authenticated().and().httpBasic();
//http.authorizeRequests().anyRequest().permitAll().anyRequest().authenticated().and().httpBasic();
		/*http.authorizeRequests().antMatchers("/loan-trial-calculation/all").permitAll()
				.antMatchers("/actuator/hystrix.stream").permitAll()
				.antMatchers("/hystrix").permitAll()
				.antMatchers("/hystrix.stream").permitAll()
				.antMatchers("/hystrix/*").permitAll()
				.antMatchers("/hystrix.stream/**").permitAll()
				.antMatchers("/services/country/**").hasAnyRole("ADMIN", "FUSION_CLIENT")
				.antMatchers("/services/**").hasRole("FUSION_CLIENT")
				.antMatchers("/loan-trial-calculation/**").permitAll()
				.antMatchers("/settings").hasRole("ADMIN")
				.anyRequest().fullyAuthenticated()
				.and().httpBasic();*/

        http.authorizeRequests().antMatchers("/**").permitAll()
                .anyRequest().fullyAuthenticated()
                .and().httpBasic();


        http.csrf().disable();

    }

}
