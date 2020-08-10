package com.example.emaillist.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {
    private int id;
    private String name;
    private int age;
    private String email;

    public User(String name, int age, String email) {
        this.name=name;
        this.age=age;
        this.email=email;
    }
}
