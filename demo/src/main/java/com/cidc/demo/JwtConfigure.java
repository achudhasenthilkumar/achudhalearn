//package com.cidc.demo;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class JwtConfigure extends WebSecurityConfigurerAdapter {
////
////	@Autowired
////	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
////
////	@Autowired
////	private JwtUserDetailsService jwtUserDetailsService;
////
////	@Autowired
////	private JwtRequestFilter jwtRequestFilter;
////
////	@Bean
////	public PasswordEncoder passwordEncoder() {
////		return new BCryptPasswordEncoder();
////	}
////
////	@Bean
////	@Override
////	public AuthenticationManager authenticationManagerBean() throws Exception {
////		return super.authenticationManagerBean();
////	}
////
////	@Override
////	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
////	}
//
//	@Override
//	protected void configure(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity.csrf().disable().authorizeRequests().antMatchers("/authenticate").permitAll()
//				.antMatchers("/Users/restapi").permitAll()
//				.antMatchers("/Users/login").permitAll()
//				.antMatchers("/Users/auth").permitAll()
//				.anyRequest().authenticated().and().exceptionHandling();
////				.authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
////				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//	}
//}
