package com.wxx.sqllite.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者: TangRen on 2019/3/20
 * 包名：com.wxx.sqllite.annotation
 * 邮箱：996489865@qq.com
 * TODO:表
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TEntity {
    String value();
}
