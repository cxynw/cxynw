package com.cxynw.security;

import com.cxynw.model.enums.LogTypeEnum;
import com.cxynw.service.AccountService;
import com.cxynw.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AccountService accountService;
    private final LogUtils logUtils;
    private final LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
    private final LogoutAuthenticationSuccessHandler logoutAuthenticationSuccessHandler;
    private final DataSource dataSource;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final JdbcTemplate jdbcTemplate;

    public WebSecurityConfiguration(AccountService accountService,
                                    LogUtils logUtils,
                                    LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler,
                                    LogoutAuthenticationSuccessHandler logoutAuthenticationSuccessHandler,
                                    DataSource dataSource,
                                    AuthenticationFailureHandler authenticationFailureHandler,
                                    JdbcTemplate jdbcTemplate){
        this.accountService = accountService;
        this.logUtils = logUtils;
        this.loginAuthenticationSuccessHandler = loginAuthenticationSuccessHandler;
        this.logoutAuthenticationSuccessHandler = logoutAuthenticationSuccessHandler;
        this.dataSource = dataSource;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return username -> {
            logUtils.publishEvent(this, LogTypeEnum.LOGGED_IN,"尝试登录 %s",username);
            UserDetails userDetails = accountService.findUserDetailsByUsername(username);
            if(log.isDebugEnabled()){
                log.debug("user details: {}",userDetails);
            }
            return userDetails;
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/account/**","/editor/**","/upload")
                .authenticated()
                .anyRequest().permitAll()
                .and()

                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(loginAuthenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout.html")
                .logoutSuccessUrl("/index.html")
//                .addLogoutHandler(logoutAuthenticationSuccessHandler)
                .permitAll()
                .and()

                // 记住我的功能
                .rememberMe()
                .rememberMeServices(getMyPersistentTokenBasedRememberMeServices());
    }

    @Bean
    public MyPersistentTokenBasedRememberMeServices getMyPersistentTokenBasedRememberMeServices(){
        MyPersistentTokenBasedRememberMeServices services = new MyPersistentTokenBasedRememberMeServices(
                "remember-me",userDetailsService(),persistentTokenRepository(),jdbcTemplate,logUtils);
        return services;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
