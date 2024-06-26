package com.vijay.event_registration_system;

import com.vijay.event_registration_system.filters.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventRegistrationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventRegistrationSystemApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean<AuthFilter> filterFilterRegistrationBean(){
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/api/events/*","/api/registrations/*");
		return registrationBean;
	}
}
