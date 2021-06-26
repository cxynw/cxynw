package com.cxynw.core;

import com.cxynw.exception.api.BaseApiRuntimeException;
import com.cxynw.model.enums.CodeEnum;
import com.cxynw.model.enums.LogTypeEnum;
import com.cxynw.model.response.BaseErrorResponse;
import com.cxynw.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;

@Slf4j
@ControllerAdvice("com.cxynw.controller")
public class BaseExceptionHandler {

    private final LogUtils logUtils;

    public BaseExceptionHandler(LogUtils logUtils) {
        this.logUtils = logUtils;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAccessDeniedException(AccessDeniedException accessDeniedException){
        if(log.isDebugEnabled()){
            log.debug("bind exception handling. exception name: {}",accessDeniedException.getClass().getName());
        }
        logUtils.publishEvent(this,LogTypeEnum.UNAUTHORIZED,accessDeniedException.getMessage());
        return "forward:/error.html";
    }

    /**
     * 参数绑定异常
     *
     * @param bindException
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseErrorResponse handleBindException(
            BindException bindException) {

        if(log.isDebugEnabled()){
            log.debug("bind exception handling. exception name: {}",bindException.getClass().getName());
        }

        HashMap<String, String> errorInfo = generateArgumentErrorInfo(bindException);
        StringBuilder message = new StringBuilder();
        errorInfo.forEach((key,value)->{
            message.append(key).append(value).append("\n");
        });

        logUtils.publishEvent(this, LogTypeEnum.PARAM_BING_ERROR,message.toString());


        BaseErrorResponse baseErrorResponse = new BaseErrorResponse(
                CodeEnum.BIND_ERROR.value(), message.toString()
        );

        return baseErrorResponse;
    }

    /**
     * api在调用的过程中，发生的异常统一默认处理方法
     *
     * @param baseApiRuntimeException
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BaseApiRuntimeException.class)
    public BaseErrorResponse handleBaseApiRuntimeException(
            BaseApiRuntimeException baseApiRuntimeException
    ){
        if(log.isDebugEnabled()){
            log.debug("base api runtime exception handling. exception name: {}",baseApiRuntimeException.getClass().getName());
        }

        return baseApiRuntimeException.convertToBaseResponse();
    }

    /**
     * 这个方法必须放到最下面，否者会因为优先级的问题，导致某些异常的统一处理失效
     *
     * @param throwable
     * @param model
     * @return
     */
    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleThrowable(final Throwable throwable, final Model model){

        if(log.isDebugEnabled()){
            log.debug("throwable handling. exception name: {} message: {}",throwable.getClass().getName(),throwable.getMessage());
        }

        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);

        String type = (throwable != null ? throwable.getMessage() : "Unknown type");
        logUtils.publishEvent(this,LogTypeEnum.THROWABLE,"throwable type: {} message: {}",
                type,errorMessage);

        return "error";
    }

    private HashMap<String,String> generateArgumentErrorInfo(BindException bindException){
        BindingResult bindingResult = bindException.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        HashMap<String,String> errorInfo = new HashMap<>(fieldErrors.size());

        fieldErrors.forEach((item)->{
            String field = item.getField();
            String defaultMessage = item.getDefaultMessage();
            errorInfo.put(field,defaultMessage);
        });
        return errorInfo;
    }

}
