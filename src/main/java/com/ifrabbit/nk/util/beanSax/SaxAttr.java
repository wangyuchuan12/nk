package com.ifrabbit.nk.util.beanSax;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SaxAttr {
    public TypeEnum type()default TypeEnum.OBJECT;
    public String name();
    public Class<?>  elementType() default Object.class;
}