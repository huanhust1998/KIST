package main.dao;

import main.connectdb.ConnUtils;
import main.model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubjectDAOImpl implements SubjectDAO {
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private Statement statement;
    @Override
    public List<Subject> getAllStudent() {
        try {
            List<Subject> subjects = new ArrayList<>();
            connection = ConnUtils.getMySqlConnection();
            statement = connection.createStatement();
            String select = "select * from subject";
            resultSet = statement.executeQuery(select);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Subject subject = new Subject(name,id);
                System.out.println(id+" | "+name);
                subjects.add(subject);
            }
            return subjects;
        } catch (SQLException throwables) {
            return  null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public Subject getStudent(int id) {
        try {
            Subject subject = null;
            connection = ConnUtils.getMySqlConnection();
            statement = connection.createStatement();
            String select = "select * from subject where id = "+id;
            resultSet = statement.executeQuery(select);
            while (resultSet.next()){
                String name = resultSet.getString("name");
                subject = new Subject(name,id);
            }
            return subject;
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
            String delete = "Delete from subject where id ="+id;
            statement.executeUpdate(delete);
            System.out.println("Delete complement!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert() {
        Scanner scanner = new Scanner(System.in);
        try {
            connection = ConnUtils.getMySqlConnection();
            String insert = "insert into subject value(?,?)";
            preparedStatement = connection.prepareStatement(insert);
            int id;
            boolean existed;
            do {
                existed = false;
                System.out.print("Nhập id subject: ");
                id = Integer.parseInt(scanner.nextLine());
                connection = ConnUtils.getMySqlConnection();
                statement = connection.createStatement();
                String sql1 = "Select * from subject";
                resultSet = statement.executeQuery(sql1);
                while (resultSet.next()) {
                    if (id == resultSet.getInt("id")) {
                        existed = true;
                        System.out.println("Id da ton tai.");
                        break;
                    }
                }
            } while (existed);

            System.out.print("Nhập name subject: ");
            String name = scanner.nextLine();
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
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
