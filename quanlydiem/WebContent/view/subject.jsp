<%@ page import="java.util.List" %>
<%@ page import="entity.*" %>
<%@ page import="dao.SubjectDAO" %>
<%@ page import="dao.SubjectDAOImpl" %><%--
  Created by IntelliJ IDEA.
  User: multi
  Date: 7/28/2020
  Time: 3:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Subject</title>
</head>
<body>
<%
	SubjectDAO subjectDAO = new SubjectDAOImpl();
    List<Subject> subjects = subjectDAO.getAllSubject();
    System.out.println("==> " + subjects.toString());
    request.setAttribute("list",subjects);
%>
<table cellspacing="1" border="1" >
    <tr>
        <td>ID</td>
        <td>Name</td>
    </tr>
    <%
        for(Subject student: subjects){
    %>
    <tr>
        <td><%=student.getId()%></td>
        <td><%=student.getName()%></td>
    </tr>
    <%
        }
    %>
</table>
<h1>Add Subject</h1>
<form action="<%=request.getContextPath()%>/addSubjects" method="post">
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
            <td></td>
            <td><br>
                <input type="submit" value="Submit" >
             </td>
        </tr>
    </table>
</form>
</body>
</html>
