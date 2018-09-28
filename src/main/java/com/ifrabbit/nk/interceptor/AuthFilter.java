package com.ifrabbit.nk.interceptor;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ifrabbit.nk.context.UserContext;
import com.ifrabbit.nk.usercenter.domain.Staff;
import com.ifrabbit.nk.usercenter.service.StaffService;
import ir.nymph.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

import static com.ifrabbit.nk.constant.Constant.HEADER_TOKEN;

public class AuthFilter implements Filter {
    @Autowired
    private StaffService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 获取token: 1. header 2.cookie
        String token = request.getHeader(HEADER_TOKEN);
        if (StringUtil.isBlank(token) || "undefined".equals(token)) {
            Cookie cookie = WebUtils.getCookie(request, HEADER_TOKEN);
            if (null != cookie) {
                token = cookie.getValue();
            }
        }

        // 需要登录但没有token.
        if (StringUtil.isBlank(token)) {

            return;
        }

        Staff user = null;
        // 解析token.
        JWTVerifier verifier = JWT.require(userService.getDefaultAlgorithm())
                .withIssuer("u").build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            Date expiresAt = jwt.getExpiresAt();
            if (null != expiresAt && new Date().before(expiresAt)) {
                String userId = jwt.getSubject();
                user = new Staff(userId);
                Claim name = jwt.getClaim("name");
                if (null != name) {
                    user.setStaffName(name.asString());
                }
                Claim username = jwt.getClaim("username");
                if (null != username) {
                    user.setStaffUsername(username.asString());
                }

            }
        } catch (JWTVerificationException e) {
        }

        // 未找到用户信息.
        if (null == user) {
            return;
        }

        // 用户信息放入ThreadLocal.
        UserContext.set(user);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.clear();
        }

    }

    @Override
    public void destroy() {

    }
}
