package com.cxynw.security;

import com.cxynw.model.enums.LogTypeEnum;
import com.cxynw.utils.LogUtils;
import org.springframework.core.log.LogMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * 修改spring security提供的记住我逻辑
 * 不知道什么原因，就算正常使用，也会触发CookieTheftException
 * 如果有人知道这个是为什么，请务必要教教我
 *
 */
public class MyPersistentTokenBasedRememberMeServices extends PersistentTokenBasedRememberMeServices {

    private final PersistentTokenRepository tokenRepository;
    private final JdbcTemplate jdbcTemplate;
    private final LogUtils logUtils;

    /**
     *
     * @param key 这个key指的是那个cookie存储着令牌
     * @param userDetailsService
     * @param tokenRepository
     */
    public MyPersistentTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository,
                                                    JdbcTemplate jdbcTemplate,LogUtils logUtils) {
        super(key, userDetailsService, tokenRepository);
        this.tokenRepository = tokenRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.logUtils = logUtils;
    }


    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request,
                                                 HttpServletResponse response) {
        if (cookieTokens.length != 2) {
            throw new InvalidCookieException("Cookie token did not contain " + 2 + " tokens, but contained '"
                    + Arrays.asList(cookieTokens) + "'");
        }
        String presentedSeries = cookieTokens[0];
        String presentedToken = cookieTokens[1];
        PersistentRememberMeToken token = this.tokenRepository.getTokenForSeries(presentedSeries);
        if (token == null) {
            // No series match, so we can't authenticate using this cookie
            throw new RememberMeAuthenticationException("No persistent token found for series id: " + presentedSeries);
        }
        // We have a match for this user/series combination
        if (!presentedToken.equals(token.getTokenValue())) {
            // Token doesn't match series value. Delete all logins for this user and throw
            // an exception to warn them.
//            this.tokenRepository.removeUserTokens(token.getUsername());
//            throw new CookieTheftException(this.messages.getMessage(
//                    "PersistentTokenBasedRememberMeServices.cookieStolen",
//                    "Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack."));

            // 把发生了异常的token进行删除
            // 这个表是spring security集成的记住我功能提供的表，其中series是主键
            try {
                jdbcTemplate.update("delete from persistent_logins where series = ?", token.getSeries());
            }catch (Exception e){
                this.logger.error("Failed to delete token:",e);
            }
            // 移除客户端的cookie
            cancelCookie(request,response);
            // 把这个记录下来，防止真的发生了CookieTheft不知道
            logUtils.publishEvent(this, LogTypeEnum.COOKIE_THEFT_EXCEPTION,"username: %s",token.getUsername());

            throw new RememberMeAuthenticationException("无效token");
        }
        if (token.getDate().getTime() + getTokenValiditySeconds() * 1000L < System.currentTimeMillis()) {
            throw new RememberMeAuthenticationException("Remember-me login has expired");
        }
        // Token also matches, so login is valid. Update the token value, keeping the
        // *same* series number.
        this.logger.debug(LogMessage.format("Refreshing persistent login token for user '%s', series '%s'",
                token.getUsername(), token.getSeries()));
        PersistentRememberMeToken newToken = new PersistentRememberMeToken(token.getUsername(), token.getSeries(),
                generateTokenData(), new Date());
        try {
            this.tokenRepository.updateToken(newToken.getSeries(), newToken.getTokenValue(), newToken.getDate());
            addCookie(newToken, request, response);
        }
        catch (Exception ex) {
            this.logger.error("Failed to update token: ", ex);
            throw new RememberMeAuthenticationException("Autologin failed due to data access problem");
        }
        return getUserDetailsService().loadUserByUsername(token.getUsername());
    }

    protected void addCookie(PersistentRememberMeToken token, HttpServletRequest request, HttpServletResponse response) {
        setCookie(new String[] { token.getSeries(), token.getTokenValue() }, getTokenValiditySeconds(), request,
                response);
    }

}
