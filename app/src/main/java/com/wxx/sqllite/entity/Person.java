package com.wxx.sqllite.entity;

import com.wxx.sqllite.annotation.Id;
import com.wxx.sqllite.annotation.TEntity;
import com.wxx.sqllite.annotation.TField;

/**
 * 作者: TangRen on 2019/3/20
 * 包名：com.wxx.sqllite.entity
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@TEntity("person")
public class Person {
    @Id(value = "id", autoincrement = true)
    private Long id;
    @TField("name")
    private String name;
    @TField("age")
    private int age;
    @TField("bytes")
    private byte[] bytes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
