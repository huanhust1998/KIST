package main.dao;

import main.model.Student;

import java.util.List;

public interface StudentDAO {
    List<Student> getAllStudent();
    Student getStudent(int id);
    void delete(int id);
    void insert();
}
