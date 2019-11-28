package com.example.demo.dto;

import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * @Description
 * @Author zhangzhiqiang1
 * @Date 2019/11/6 16:28
 * @Version 1.0
 **/

public class User {

    private long id;
    private String name;
    private String password;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
