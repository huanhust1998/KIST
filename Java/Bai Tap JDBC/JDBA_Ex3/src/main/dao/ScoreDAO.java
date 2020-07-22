package main.dao;

import main.model.Student;
import main.model.Subject;

import java.util.List;

public interface ScoreDAO {
    void getScoreAllMembers(List<Student> students);
    void getScoreAllSubject(List<Subject> subjects);
    void insert();
}
