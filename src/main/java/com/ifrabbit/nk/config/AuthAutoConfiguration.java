package com.ifrabbit.nk.config;

import com.auth0.jwt.JWT;
import com.ifrabbit.nk.annotation.Auth;
import com.ifrabbit.nk.interceptor.AuthFilter;
import com.ifrabbit.nk.interceptor.AuthInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Jarvis Song
 */
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "biz.authentication", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass({JWT.class, Auth.class})
@Configuration
public class AuthAutoConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor());
    }

    @Bean
    AuthFilter authFilter() {
        return new AuthFilter();
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(authFilter());
        registration.addUrlPatterns("/uflo/*");
        registration.setName("authFilter");
        registration.setOrder(1);
        return registration;
    }

}
