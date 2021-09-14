package com.cxynw;

import com.cxynw.config.ApplicationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;

@Slf4j
@EnableCaching //开启缓存
@EnableAsync //开启异步方法
@SpringBootApplication //Spring boot启动类
@EnableJpaRepositories //启动JPA仓库
public class CxynwApplication {

    @Bean("app")
    @ConfigurationProperties(prefix = "application")
    public ApplicationConfig getApplicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        return applicationConfig;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CxynwApplication.class, args);
        int beanDefinitionCount = context.getBeanDefinitionCount();
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        String applicationName = context.getApplicationName();
        ApplicationConfig applicationConfig = context.getBean(ApplicationConfig.class);

        if(log.isInfoEnabled()){
            log.info("application config: [{}]", applicationConfig);
            log.info(String.format("application name: %s",applicationName));
            log.info(String.format("bean definition count: %d",beanDefinitionCount));
            log.info(String.format("bean definition names: %s", Arrays.toString(beanDefinitionNames)));
        }
    }

}
