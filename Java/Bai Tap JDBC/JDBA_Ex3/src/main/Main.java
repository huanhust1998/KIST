package main;

import main.dao.*;
import main.model.Student;
import main.model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentDAO studentDAO = new StudentDAOImpl();
        List<Student> students = new ArrayList<>();
        SubjectDAO subjectDAO = new SubjectDAOImpl();
        List<Subject> subjects = new ArrayList<>();
        ScoreDAO scoreDAO = new ScoreDAOImpl();
        System.out.println("1. Add Student \n" +
                "2. Delete Student \n" +
                "3. Search Student \n" +
                "4. Add Subject \n" +
                "5. Delete Subject \n" +
                "6. Search Subject \n" +
                "7. Scoring An Exam for all students \n" +
                "8. Print Score of All Members \n" +
                "9. Print Score of All Subject \n" +
                "10. Exit");


        do {
            System.out.print("Your choice: ");
            int yourChoice = Integer.parseInt(scanner.nextLine());
            if(yourChoice==1){
                studentDAO.insert();
                students = studentDAO.getAllStudent();
            }else if(yourChoice==2) {
                System.out.print("Enter id student: ");
                int id = Integer.parseInt(scanner.nextLine());
                studentDAO.delete(id);
                students = studentDAO.getAllStudent();
            }else if(yourChoice==3){
                System.out.print("Enter id student: ");
                int id = Integer.parseInt(scanner.nextLine());
                Student student = studentDAO.getStudent(id);
                System.out.println(student.getId()+" | "+ student.getName()+" | "+student.getClassName());
            }else if(yourChoice==4){
                subjectDAO.insert();
                subjects = subjectDAO.getAllStudent();
            }else if(yourChoice==5){
                System.out.print("Enter id subject: ");
                int id = Integer.parseInt(scanner.nextLine());
                subjectDAO.delete(id);
                subjects=subjectDAO.getAllStudent();
            }else if(yourChoice==6){
                System.out.print("Enter id subject: ");
                int id = Integer.parseInt(scanner.nextLine());
                Subject subject = subjectDAO.getStudent(id);
                System.out.println(subject.getId()+" | "+subject.getName());
            }else if(yourChoice==7){
                scoreDAO.insert();
            }else if(yourChoice==10){
                System.out.println("Exit");
                break;
            }else{
                System.out.println("Your choice wrong, please choice again");
            }
        }while (true);
    }
}
