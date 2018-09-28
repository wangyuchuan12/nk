package com.ifrabbit.nk.usercenter.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ifrabbit.nk.redisService.StaffRedisService;
import com.ifrabbit.nk.usercenter.domain.Staff;
import com.ifrabbit.nk.usercenter.domain.StaffDTO;
import com.ifrabbit.nk.usercenter.repository.StaffRepository;
import com.ifrabbit.nk.usercenter.service.StaffService;
import ir.nymph.collection.CollectionUtil;
import ir.nymph.crypto.BCrypt;
import ir.nymph.lang.Snowflake;
import ir.nymph.lang.Validator;
import ir.nymph.util.ArrayUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class StaffServiceImpl extends AbstractCrudService<StaffRepository, Staff, String> implements StaffService {

    /**
     * 构造函数.
     *
     * @param repository 注入的Repository
     */
    @Autowired
    public StaffServiceImpl(StaffRepository repository) {
        super(repository);
    }

    @Autowired
    private Snowflake idWorker;

    @Autowired
    private StaffRedisService staffRedisService;

    @Value("${usercenter.token.expires}")
    private Long expires;

    @Override
    @Transactional(readOnly = true)
    public Staff login(String identity, String password) {
        // 先判断identity类型
        Staff staff;
        if (Validator.isMobile(identity)) {
            // 手机号码
            staff = getByMobile(identity);
        } else if (Validator.isEmail(identity)) {
            // Email
            staff = getByEmail(identity);
        } else {
            // 用户名
            staff = getByUsername(identity);
        }
        if (null == staff) {
            return null;
        }
        if (!checkPassword(password, staff.getStaffPassword())) {
            // 密码错误
            return null;
        }
        // 登录成功, 生成token(jwt)
        return staff;
    }

    @Override
    public String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean checkPassword(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }

    @Override
    public String generateToken(Staff staff) {
        Builder builder = JWT.create().withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expires))
                .withSubject(staff.getId()).withIssuer("u").withClaim("username",
                        staff.getStaffUsername());
//        if (StringUtil.isNotBlank(user.getArea())) {
//            builder.withClaim("area", user.getArea());
//        }
        return builder.sign(getDefaultAlgorithm());
    }

    @Override
    public Staff extractToken(String token) {
        JWTVerifier verifier = JWT.require(getDefaultAlgorithm()).withIssuer("u").build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            Date expiresAt = jwt.getExpiresAt();
            if (null != expiresAt && new Date().before(expiresAt)) {
                String userId = jwt.getSubject();
                if (null != userId) {
                    return getBasic(userId);
                }
            }
        } catch (JWTVerificationException e) {
            return null;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Staff getByUsername(String username) {
        StaffDTO cond = new StaffDTO();
        cond.setStaffUsername(username);
        return getRepository().findOne(cond);

    }

    @Override
    @Transactional(readOnly = true)
    public Staff getByMobile(String mobile) {
        StaffDTO cond = new StaffDTO();
        cond.setStaffMobil(mobile);
        return getRepository().findOne(cond);
    }

    @Override
    @Transactional(readOnly = true)
    public Staff getByEmail(String email) {
        StaffDTO cond = new StaffDTO();
        cond.setStaffEmail(email);
        return getRepository().findOne(cond);
    }

    @Transactional
    public void deleteId(String id) {
//        Staff staff = new Staff(id);
////        user.setDeleted(true);
//        updateIgnore(staff);
//        getRepository().deleteRoleUserByUserId(id);
//        getRepository().deleteGroupUserByUserId(id);
        Staff staff = new Staff(id);
        staff.setStaffState(9);
        updateIgnore(staff);
    }

    @Override
    @Transactional
    public void batchDelete(String[] ids) {
        if (ArrayUtil.isEmpty(ids)) {
            return;
        }
        for (String id : ids) {
            deleteId(id);
            staffRedisService.doDelCache(id);
        }
    }



    @Override
    @Transactional
    public void insert(Staff staff) {
//        staff.setId(String.valueOf(this.idWorker.nextId()));
//        user.setDeleted(false);
        super.insert(staff);
        Staff sta = this.getByMobile(staff.getStaffMobil());
        if (CollectionUtil.isNotEmpty(staff.getRoles())) {
            staff.getRoles().forEach(role -> {
                getRepository().insertRoleUser(role.getId(), sta.getId());
            });
        }
        if (CollectionUtil.isNotEmpty(staff.getGroups())) {
            staff.getGroups().forEach(group -> {
                getRepository().insertGroupUser(group.getId(), sta.getId());
            });
        }
        staffRedisService.doCache(staff);
    }

    @Override
    @Transactional
    public void updateIgnore(Staff staff) {
        super.updateIgnore(staff);
        if(staff.getStaffIntparama() == null || staff.getStaffIntparama() != 1){
            getRepository().deleteRoleUserByUserId(staff.getId());
        }
        staff.setStaffIntparama(null);
        super.updateIgnore(staff);
        if (CollectionUtil.isNotEmpty(staff.getRoles())) {
            staff.getRoles().forEach(role -> {
                getRepository().insertRoleUser(role.getId(), staff.getId());
            });
        }
        staffRedisService.doCache(staff);
//        getRepository().deleteGroupUserByUserId(staff.getId());
//        if (CollectionUtil.isNotEmpty(staff.getGroups())) {
//            staff.getGroups().forEach(group -> {
//                getRepository().insertGroupUser(group.getId(), staff.getId());
//            });
//        }
    }

    public void delete(String id){
        getRepository().delete(id);
        staffRedisService.doDelCache(id);
    }
}
