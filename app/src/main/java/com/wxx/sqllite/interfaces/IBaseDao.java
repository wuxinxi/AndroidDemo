package com.wxx.sqllite.interfaces;

/**
 * 作者: TangRen on 2019/3/20
 * 包名：com.wxx.sqllite.interfaces
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public interface IBaseDao<T> {
    Long insert(T entity);
}
