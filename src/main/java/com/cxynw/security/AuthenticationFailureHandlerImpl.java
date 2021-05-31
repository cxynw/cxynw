package com.cxynw.security;

import com.cxynw.model.enums.CodeEnum;
import com.cxynw.model.enums.LogTypeEnum;
import com.cxynw.model.response.BaseErrorResponse;
import com.cxynw.utils.LogUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 账户登录失败
 *
 */
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {


    private final LogUtils logUtils;
    private final ObjectMapper objectMapper;

    public AuthenticationFailureHandlerImpl(LogUtils logUtils,
                                             ObjectMapper objectMapper) {
        this.logUtils = logUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logUtils.publishEvent(this, LogTypeEnum.LOGGED_FAIL,exception.getMessage());

        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        BaseErrorResponse baseErrorResponse = new BaseErrorResponse();
        baseErrorResponse.setCode(CodeEnum.LOGGED_FAIL.value());
        baseErrorResponse.setMessage("用户名或者密码错误");

        ServletOutputStream outputStream = response.getOutputStream();

        byte[] json = objectMapper.writeValueAsBytes(baseErrorResponse);
        outputStream.write(json);

        outputStream.flush();
    }


}
