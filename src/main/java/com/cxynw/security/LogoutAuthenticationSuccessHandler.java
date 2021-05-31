package com.cxynw.security;

import com.cxynw.model.enums.CodeEnum;
import com.cxynw.model.enums.LogTypeEnum;
import com.cxynw.model.response.BaseSuccessResponse;
import com.cxynw.utils.LogUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LogoutAuthenticationSuccessHandler implements LogoutHandler {

    private final LogUtils logUtils;
    private final ObjectMapper objectMapper;

    public LogoutAuthenticationSuccessHandler(LogUtils logUtils,
                                             ObjectMapper objectMapper) {
        this.logUtils = logUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logUtils.publishEvent(this, LogTypeEnum.LOGGED_OUT,"账户%s退出登录成功",authentication.getPrincipal().toString());

        BaseSuccessResponse<Object> baseErrorResponse = new BaseSuccessResponse<>();
        baseErrorResponse.setCode(CodeEnum.LOGOUT_SUCCEED.value());
        baseErrorResponse.setMessage("注销成功");

        try{
            ServletOutputStream outputStream = response.getOutputStream();

            byte[] json = objectMapper.writeValueAsBytes(baseErrorResponse);
            outputStream.write(json);

            outputStream.flush();
        }catch (IOException exception){
            if(log.isDebugEnabled()){
                log.error(exception.getMessage());
            }
        }
    }
}
