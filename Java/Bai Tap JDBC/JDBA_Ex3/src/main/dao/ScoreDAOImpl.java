package main.dao;

import main.connectdb.ConnUtils;
import main.model.Student;
import main.model.Subject;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class ScoreDAOImpl implements ScoreDAO {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    @Override
    public void getScoreAllMembers(List<Student> students) {
        try {
            connection = ConnUtils.getMySqlConnection();
            statement = connection.createStatement();
            String select = "Select sv. ";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getScoreAllSubject(List<Subject> subjects) {

    }

    @Override
    public void insert() {
        Scanner scanner = new Scanner(System.in);
        try {
            connection = ConnUtils.getMySqlConnection();
            String insert = "insert into score values(?,?,?)";
            preparedStatement = connection.prepareStatement(insert);
            //nhập id sinh viên
            int studentId;
            boolean existed;
            do {
                existed = true;
                System.out.print("Nhập id student: ");
                studentId = Integer.parseInt(scanner.nextLine());
                connection = ConnUtils.getMySqlConnection();
                statement = connection.createStatement();
                String sql1 = "Select * from student";
                resultSet = statement.executeQuery(sql1);
                while (resultSet.next()) {
                    if (studentId == resultSet.getInt("id")) {
                        existed=false;
                        break;
                    }
                }
                if(existed==true) System.out.println("Student does not exist!");
            } while (existed);
            //nhập id subject
            int subjectId;
            do {
                existed = true;
                System.out.print("Nhập id subject: ");
                subjectId = Integer.parseInt(scanner.nextLine());
                connection = ConnUtils.getMySqlConnection();
                statement = connection.createStatement();
                String sql1 = "Select * from subject";
                resultSet = statement.executeQuery(sql1);
                while (resultSet.next()) {
                    if (subjectId == resultSet.getInt("id")) {
                        existed = false;
                        break;
                    }
                }
                if(existed==true) System.out.println("Subject does not exist.");
            } while (existed);
            //nhập điểm
            System.out.print("Score: ");
            int score = scanner.nextInt();

            preparedStatement.setInt(1,studentId);
            preparedStatement.setInt(2,subjectId);
            preparedStatement.setInt(3,score);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
            statement.close();
            System.out.println("Cho điểm thành công.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
