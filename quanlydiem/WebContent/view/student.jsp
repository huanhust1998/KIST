<%@ page import="dao.StudentDAO" %>
<%@ page import="dao.StudentDAOImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Student" %><%--
  Created by IntelliJ IDEA.
  User: multi
  Date: 7/28/2020
  Time: 1:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student</title>
</head>
<body>
<h1>Students</h1>
<%
    StudentDAO studentDAO = new StudentDAOImpl();
    List<Student> students = studentDAO.getAllStudent();
    System.out.println("==> " + students.toString());
    request.setAttribute("list",students);
%>
<table cellspacing="1" border="1" >
    <tr>
        <td align="right">ID</td>
        <td align="right">Name</td>
        <td align="right">Age</td>
    </tr>
    <%
        for(Student student: students){
    %>
    <tr>
        <td><%=student.getId()%></td>
        <td><%=student.getName()%></td>
        <td><%=student.getAge()%></td>
    </tr>
    <%
        }
    %>
</table>
<h1>Add Student</h1>
<form action="<%=request.getContextPath()%>/addStudents" method="post">
    <table cellspacing="5" border="0">
        <tr>
            <td align="right">ID:</td>
            <td><input type="text" name="id"> </td>
        </tr>
        <tr>
            <td align="right">Name:</td>
            <td><input type="text" name="name"> </td>
        </tr>
        <tr>
            <td align="right">Age:</td>
            <td><input type="text" name="age"></td>
        </tr>
        <tr>
            <td></td>
            <td><br>
                <input type="submit" value="Submit" ></td>
        </tr>
    </table>
</form>


</body>
</html>
