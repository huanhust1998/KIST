package com.example.emaillist.repository;

import com.example.emaillist.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository implements JdbcUserRepository{
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Iterable<User> findAll() {
        return jdbcTemplate.query("select* from useremail",this::mapRowToIngredient);
    }

    @Override
    public User findById(int id) {
        return jdbcTemplate.queryForObject(
                "select * from useremail where id=?",
                this::mapRowToIngredient, id);
    }

    @Override
    public User save(User user) {
        jdbcTemplate.update(
                "insert into useremail (name,age,email) values(?,?,?) ",
                user.getName(),
                user.getAge(),
                user.getEmail()
        );
        return user;
    }
    private User mapRowToIngredient(ResultSet rs, int rowNum)throws SQLException {
        return new User(
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("email")
        );
    }
}
