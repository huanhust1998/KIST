package dao;

import entity.Student;

import java.util.List;

public interface StudentDAO {
    List<Student> getAllStudent();
    void insert(Student student);
}
