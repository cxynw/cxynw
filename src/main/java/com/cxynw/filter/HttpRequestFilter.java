package com.cxynw.filter;

import com.cxynw.model.does.HttpRequest;
import com.cxynw.service.HttpRequestService;
import com.cxynw.utils.LogUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 记录http请求，用于以后分析
 */
@Component
public class HttpRequestFilter implements Filter {

    private final HttpRequestService httpRequestService;
    private final LogUtils logUtils;

    public HttpRequestFilter(HttpRequestService httpRequestService, LogUtils logUtils) {
        this.httpRequestService = httpRequestService;
        this.logUtils = logUtils;
    }

    public void record(HttpServletRequest request){
        String method = request.getMethod();
        String protocol = request.getProtocol();
        String requestURI = request.getRequestURI();
        String contentType = request.getContentType();
        String queryString = request.getQueryString();
        long contentLength = request.getContentLengthLong();
        String characterEncoding = request.getCharacterEncoding();
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setRequestMethod(method);
        httpRequest.setProtocol(protocol);
        httpRequest.setRequestURI(requestURI);
        httpRequest.setContentType(contentType);
        httpRequest.setQueryString(queryString);
        httpRequest.setContentLength(contentLength);
        httpRequest.setCharacterEncoding(characterEncoding);
        httpRequest.setUserAgent(userAgent);
        httpRequest.setReferer(referer);

        httpRequestService.asyncRecord(httpRequest);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        record((HttpServletRequest) request);

        chain.doFilter(request,response);
    }
}
