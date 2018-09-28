package com.ifrabbit.nk.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,
        ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {

    /**
     * 唯一CODE.不填会自动根据方法名生成.
     */
    String value() default "";

    /**
     * 是否需要登录.
     */
    boolean mustAuthentication() default true;

    /**
     * 是否仅需要登录，不需要权限控制。
     */
    boolean onlyAuthentication() default false;

    String[] roles() default {};

    /**
     * 是否内部接口. 如果是内部接口, 内网调用将直接放行.
     */
    boolean inner() default false;

    String description() default "";

}
