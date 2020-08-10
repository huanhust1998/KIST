package com.example.emaillist.repository;

import com.example.emaillist.entity.User;

public interface JdbcUserRepository {
    Iterable<User> findAll();
    User findById(int id);
    User save(User user);
}
