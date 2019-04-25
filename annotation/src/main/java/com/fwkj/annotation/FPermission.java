package com.fwkj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fjj
 * @email 1025461682@qq.com
 * @phone 17684220995
 * @file description :
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface FPermission {
    String[] values();
}
