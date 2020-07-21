package main.dao;

import main.connectdb.ConnUtils;
import main.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentDAOImpl implements StudentDAO {
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
                String classname = resultSet.getString("classname");
                Student student = new Student(name,id,classname);
                System.out.println(id+" | "+name+" | "+classname);
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
    public Student getStudent(int id) {
        try {
            Student student = null;
            connection = ConnUtils.getMySqlConnection();
            statement = connection.createStatement();
            String select = "select * from student where id= "+ id;
            resultSet = statement.executeQuery(select);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String classname = resultSet.getString("classname");
                student = new Student(name, id, classname);
            }
            statement.close();
            connection.close();
            return student;
        } catch (SQLException throwables) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public void delete(int id) {
        try {
            connection = ConnUtils.getMySqlConnection();
            statement = connection.createStatement();
            String delete = "Delete from student where id = "+ id;
            statement.executeUpdate(delete);
            System.out.println("Delete complement!");
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            System.out.println("Exception");
        } catch (ClassNotFoundException e) {
            System.out.println("Exception");
        }
    }

    @Override
    public void insert() {
        Scanner scanner = new Scanner(System.in);
        try {
            connection = ConnUtils.getMySqlConnection();
            String sql = "insert into student values(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            int id;
            boolean existed;
            do {
                existed = false;
                System.out.print("Nhập id student: ");
                id = Integer.parseInt(scanner.nextLine());
                connection = ConnUtils.getMySqlConnection();
                statement = connection.createStatement();
                String sql1 = "Select * from student";
                resultSet = statement.executeQuery(sql1);
                while (resultSet.next()) {
                    if (id == resultSet.getInt("id")) {
                        existed = true;
                        System.out.println("Id da ton tai.");
                        break;
                    }
                }
            } while (existed);

            System.out.print("Nhập name student: ");
            String name = scanner.nextLine();
            System.out.print("Nhập class_name student: ");
            String class_name = scanner.nextLine();
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, class_name);
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

