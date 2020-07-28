package dao;

import entity.Subject;

import java.util.List;

public interface SubjectDAO {
    List<Subject> getAllSubject();
    void insert(Subject subject);
}
