package main;

import main.dao.StudentDAOImpl;
import main.model.Student;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        StudentDAOImpl studentDAO = new StudentDAOImpl();
        System.out.println("1.ADD");
        System.out.println("2.DELETE");
        System.out.println("3.SEARCH");
        System.out.println("4.EXIT");
        do {
            System.out.print("Your choice: ");
            int yourChoice = Integer.parseInt(scanner.nextLine());
            if (yourChoice == 1) {
                studentDAO.insert();
            } else if (yourChoice == 2) {
                System.out.print("Nhập id sinh viên cần xóa: ");
                int id = Integer.parseInt(scanner.nextLine());
                studentDAO.delete(id);
            } else if (yourChoice == 3) {
                System.out.print("Nhập id sinh viên cần tim kiếm: ");
                int id = Integer.parseInt(scanner.nextLine());
                Student student = studentDAO.search(id);
                System.out.println(student.getId() + " | " + student.getName() + " | " + student.getClass_name());
            } else if (yourChoice == 4) {
                System.out.println("Thoát chương trình");
                break;
            } else {
                System.out.println("Bạn chọn sai vui lòng chọn lại");
            }
        } while (true);
    }
}
