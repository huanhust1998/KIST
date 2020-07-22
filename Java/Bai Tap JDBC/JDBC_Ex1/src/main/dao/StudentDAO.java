package main.dao;


import main.model.Student;

public interface StudentDAO {
    public void insert();

    public void delete(int id);

    public Student search(int id);
}
