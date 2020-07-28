<%--
  Created by IntelliJ IDEA.
  User: multi
  Date: 7/28/2020
  Time: 1:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="header.html"/>
<h1>Student - Subject - Score Management</h1>
<div class="btn-group">
    <button onclick="window.location.href = 'view/student.jsp'">Student</button>
    <button onclick="window.location.href = 'view/subject.jsp'">Subject</button>
    <button onclick="window.location.href = 'scoring.jsp'">Scoring</button>
</div>
<jsp:include page="footer.html"/>
</body>
</html>
