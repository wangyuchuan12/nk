package com.ifrabbit.nk.usercenter.controller;

import com.bstek.uflo.utils.EnvironmentUtils;
import com.ifrabbit.nk.annotation.Auth;
import com.ifrabbit.nk.constant.Constant;
import com.ifrabbit.nk.usercenter.domain.Staff;
import com.ifrabbit.nk.usercenter.domain.StaffDTO;
import com.ifrabbit.nk.usercenter.service.StaffService;
import ir.nymph.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("usercenter")
public class LoginController {

    @Autowired
    private StaffService staffService;

    @Value("${usercenter.token.expires}")
    private Long expires;

    @Auth(mustAuthentication = false)
    @PostMapping("login")
    Map<String, Object> doLogin(
            @RequestParam(required = false, defaultValue = "true") Boolean cookie,
            @RequestParam String identity,
            @RequestParam String password,
            @RequestParam(required = false) String clientId,
            @RequestParam(required = false) String redirect,
            HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        Staff staff = staffService.login(identity, password);
        if (null == staff) {
            map.put("success", false);
            map.put("message", "用户名或密码错误.");
        } else {
            String token = staffService.generateToken(staff);
            map.put("success", true);
            map.put("expires", expires);
            if (StringUtil.isNotBlank(redirect)) {
                map.put("redirect", redirect);
            }
            Staff u = new Staff();
            u.setId(staff.getId());
            u.setStaffName(staff.getStaffName());
            u.setStaffUsername(staff.getStaffUsername());
//            u.setArea(staff.getArea());
            map.put("user", u);
            if (cookie) {
                Cookie c = new Cookie(Constant.HEADER_TOKEN, token);
                c.setHttpOnly(true);
                c.setPath("/");
                c.setMaxAge((int) (expires / 1000));
                response.addCookie(c);
            } else {
                map.put("token", token);
            }

        }
        return map;
    }

    @RequestMapping("logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void doLogout(HttpServletResponse response) {
        Cookie c = new Cookie(Constant.HEADER_TOKEN, null);
        c.setPath("/");
        c.setMaxAge(0);
        response.addCookie(c);
    }


    @RequestMapping("getLogin")
    public String getLogin() {
        StaffDTO dto = new StaffDTO();
        dto.setStaffUsername(EnvironmentUtils.getEnvironment().getLoginUser());
        Staff staff = staffService.findOne(dto);
        return staff.getStaffName();
    }
}
