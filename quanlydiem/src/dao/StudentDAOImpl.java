package dao;

import connectdb.ConnUtils;
import entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentDAOImpl implements StudentDAO{
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private Statement statement;
    @Override
    public List<Student> getAllStudent() {
        try {
            List<Student> students = new ArrayList<>();
            connection = ConnUtils.getMySqlConnection();
            statement = connection.createStatement();
            String select = "select * from student";
            resultSet = statement.executeQuery(select);
            while (resultSet.next()){
                String name = resultSet.getString("name");
                int id = resultSet.getInt("id");
                int age = resultSet.getInt("age");
                Student student = new Student(id,name,age);
                students.add(student);
            }
            return students;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void insert(Student student) {
        Scanner scanner = new Scanner(System.in);
        try {
            connection = ConnUtils.getMySqlConnection();
            String sql = "insert into student values(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.executeUpdate();
            System.out.println("Thêm thành công");

            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
