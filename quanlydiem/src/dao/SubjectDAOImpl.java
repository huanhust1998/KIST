package dao;

import connectdb.ConnUtils;
import entity.Subject;

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
    public List<Subject> getAllSubject() {
        try {
            List<Subject> subjects = new ArrayList<>();
            connection = ConnUtils.getMySqlConnection();
            statement = connection.createStatement();
            String select = "select * from subject";
            resultSet = statement.executeQuery(select);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Subject subject = new Subject(id,name);
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
    public void insert(Subject subject) {
    	 Scanner scanner = new Scanner(System.in);
         try {
             connection = ConnUtils.getMySqlConnection();
             String sql = "insert into subject values(?,?)";
             preparedStatement = connection.prepareStatement(sql);

             preparedStatement.setInt(1, subject.getId());
             preparedStatement.setString(2, subject.getName());
            
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
