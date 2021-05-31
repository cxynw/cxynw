package com.cxynw.config;

import com.cxynw.filter.HttpRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfiguration implements WebMvcConfigurer {

    private final HttpRequestFilter httpRequestFilter;

    public SpringMvcConfiguration(HttpRequestFilter httpRequestFilter) {
        this.httpRequestFilter = httpRequestFilter;
    }

    @Bean
    public FilterRegistrationBean  getHttpRequestFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(httpRequestFilter);
        bean.addUrlPatterns("/**");
        return bean;
    }

}
