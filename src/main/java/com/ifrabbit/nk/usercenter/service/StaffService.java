package com.ifrabbit.nk.usercenter.service;

import com.auth0.jwt.algorithms.Algorithm;
import com.ifrabbit.nk.usercenter.domain.Staff;
import org.springframework.data.support.CrudService;

import java.io.UnsupportedEncodingException;

public interface StaffService extends CrudService<Staff, String> {

    Staff getByUsername(String username);

    Staff getByMobile(String mobile);

    Staff getByEmail(String email);

    Staff login(String identity, String password);

    String encrypt(String password);

    boolean checkPassword(String plaintext, String hashed);

    String generateToken(Staff staff);

    Staff extractToken(String token);

    /**
     * bulk delete users.
     *
     * @param ids user's id
     */
    void batchDelete(String[] ids);

    default Algorithm getDefaultAlgorithm() {
        try {
            return Algorithm.HMAC256("adfa1479akpqo0Il1Xm");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
