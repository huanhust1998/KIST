package main.dao;

import main.model.Student;
import main.connectdb.ConnUtils;

import java.sql.*;
import java.util.Scanner;

public class StudentDAOImpl implements StudentDAO {
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private Statement statement;

    public StudentDAOImpl() {
    }

    public void insert() {
        Scanner scanner = new Scanner(System.in);
        try {
            connection = ConnUtils.getMySQLConnection();
            String sql = "insert into student values(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            int id;
            boolean existed;
            do {
                existed = false;
                System.out.print("Nhập id: ");
                id = Integer.parseInt(scanner.nextLine());
                connection = ConnUtils.getMySQLConnection();
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

            System.out.print("Nhập name: ");
            String name = scanner.nextLine();
            System.out.print("Nhập class_name: ");
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

    public void delete(int id) {
        try {
            connection = ConnUtils.getMySQLConnection();
            statement = connection.createStatement();
            String sql = "Delete from student where id = " + id;
            statement.executeUpdate(sql);
            sql = "SELECT * from student";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " | " + resultSet.getString("name") + " | " + resultSet.getString("class_name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Student search(int id) {
        try {
            connection = ConnUtils.getMySQLConnection();
            statement = connection.createStatement();
            String sql = "Select * from student where id =" + id;
            resultSet = statement.executeQuery(sql);
            Student student = null;
            while (resultSet.next()) {
                student = new Student(resultSet.getString("name"), resultSet.getInt("id"), resultSet.getString("class_name"));
            }
            return student;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}
