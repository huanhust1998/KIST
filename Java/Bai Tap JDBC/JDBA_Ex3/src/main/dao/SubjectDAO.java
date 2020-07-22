package main.dao;

import main.model.Subject;

import java.util.List;

public interface SubjectDAO {
    List<Subject> getAllStudent();
    Subject getStudent(int id);
    void delete(int id);
    void insert();
}
