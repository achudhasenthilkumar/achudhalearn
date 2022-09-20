//package com.cidc.demo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//@EnableWebSecurity
//@Configuration
//public class JwtCon {
//
//	 protected void configure(HttpSecurity http) throws Exception
//	    {
//	        http
//	         .csrf().disable()
//	         .authorizeRequests().anyRequest().permitAll();
//	    }
//
//	    @Autowired
//	    public void configureGlobal(AuthenticationManagerBuilder auth)
//	            throws Exception
//	    {
//	        auth.inMemoryAuthentication()
//	        	.withUser("admin")
//	        	.password("{noop}password")
//	        	.roles("USER");
//	    }
//}
