package com.wxx.androiddemo.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者: TangRen on 2019/4/6
 * 包名：com.wxx.androiddemo.greendao.entity
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@Entity
public class GreenDaoEntity {
    @Id(autoincrement = true)
    private Long id;
    private String content;
    @Generated(hash = 319146395)
    public GreenDaoEntity(Long id, String content) {
        this.id = id;
        this.content = content;
    }
    @Generated(hash = 1963997359)
    public GreenDaoEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "GreenDaoEntity->Id="+id+",content="+content;
    }
}
