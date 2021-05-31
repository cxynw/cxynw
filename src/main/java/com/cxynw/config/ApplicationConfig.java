package com.cxynw.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
public class ApplicationConfig {

    private String name;
    private String keywords;
    private String description;
    private String webHost;

}
