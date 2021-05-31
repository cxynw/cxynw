package com.cxynw.security;

import com.cxynw.model.enums.CodeEnum;
import com.cxynw.model.enums.LogTypeEnum;
import com.cxynw.model.response.BaseSuccessResponse;
import com.cxynw.utils.LogUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final LogUtils logUtils;
    private final ObjectMapper objectMapper;

    public LoginAuthenticationSuccessHandler(LogUtils logUtils,
                                             ObjectMapper objectMapper) {
        this.logUtils = logUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logUtils.publishEvent(this, LogTypeEnum.LOGGED_IN,"账户%s登录成功",authentication.getPrincipal().toString());

        BaseSuccessResponse<Object> baseErrorResponse = new BaseSuccessResponse<>();
        baseErrorResponse.setCode(CodeEnum.LOGGED_SUCCEED.value());
        baseErrorResponse.setMessage("登录成功");

        ServletOutputStream outputStream = response.getOutputStream();

        byte[] json = objectMapper.writeValueAsBytes(baseErrorResponse);
        outputStream.write(json);

        outputStream.flush();
    }

}
