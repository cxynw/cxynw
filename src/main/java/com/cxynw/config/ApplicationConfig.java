package com.cxynw.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
public class ApplicationConfig {

    private String name; // 网站的名字
    private String keywords; // 网站的关键字
    private String description; // 网站的描述
    private String webHost; // 网站的域名

}
