package com.ifrabbit.nk.interceptor;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ifrabbit.nk.annotation.Auth;
import com.ifrabbit.nk.context.UserContext;
import com.ifrabbit.nk.usercenter.domain.Staff;
import com.ifrabbit.nk.usercenter.service.StaffService;
import ir.nymph.http.HttpUtil;
import ir.nymph.util.ArrayUtil;
import ir.nymph.util.NetUtil;
import ir.nymph.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.ifrabbit.nk.constant.Constant.HEADER_TOKEN;

public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private StaffService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if (((HandlerMethod) handler).getBeanType() == BasicErrorController.class) {
            return true;
        }

        HandlerMethod hm = (HandlerMethod) handler;
        Auth auth = hm.getMethodAnnotation(Auth.class);// 如果auth没有设置，默认只需要登录，不做权限控制
        if (null == auth) {
            // 查找Class上是否有@Auth
            auth = hm.getBeanType().getAnnotation(Auth.class);
        }

        if (null != auth && !auth.mustAuthentication()) {
            return true;
        }

        // 内部接口.
        if (null != auth && auth.inner()) {
            String ip = HttpUtil.getClientIP(request);
            if (NetUtil.isInnerIP(ip)) {
                return true;
            }
        }

        /** Must Login **/

        // 获取token: 1. header 2.cookie
        String token = request.getHeader(HEADER_TOKEN);
        if (StringUtil.isBlank(token) || "undefined".equals(token)) {
            Cookie cookie = WebUtils.getCookie(request, HEADER_TOKEN);
            if (null != cookie) {
                token = cookie.getValue();
            }
        }

        // 需要登录但没有token.
        if (StringUtil.isBlank(token) || "undefined".equals(token)) {
            if (request.getRequestURL().toString().contains("phone/posun")||request.getRequestURL().toString().contains("message/data")||request.getRequestURL().toString().contains("phone/tag")){
                return true;
            }
            unauthorized(response);
            return false;
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
            unauthorized(response);
            return false;
        }

        // 只需要登录, 不需要进行权限判断.
        if (null == auth || auth.onlyAuthentication()) {
            // 用户信息放入ThreadLocal.
            UserContext.set(user);
            return true;
        }

        // 获取用户权限分配信息
        String action = auth.value();
        // if (StringUtil.isBlank(action)) {
        // action = hm.getBeanType().getSimpleName() + "." + hm.getMethod().getName();
        // }
        if (StringUtil.isNotBlank(action)) {
            if (ArrayUtil.isEmpty(user.getActions())
                    || !ArrayUtil.contains(user.getActions(), action)) {
                forbidden(response);
                return false;
            }
        }

        // 用户信息放入ThreadLocal.
        UserContext.set(user);
        return true;
    }

    /**
     * 处理401.
     */
    private void unauthorized(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);// 401
        response.getWriter()
                .write("{\"success\":false,\"code\":401,\"message\":\"没有登录.\"}");
        response.getWriter().close();
    }

    /**
     * 处理403.
     */
    private void forbidden(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);// 403
        response.getWriter()
                .write("{\"success\":false,\"code\":403,\"message\":\"没有权限.\"}");
        response.getWriter().close();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        UserContext.clear();
    }

}
