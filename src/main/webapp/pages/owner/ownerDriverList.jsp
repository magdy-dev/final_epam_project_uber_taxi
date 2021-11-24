<%@ page import="com.magdy.taxiwebappliction.entity.Driver" %><%--
  Created by IntelliJ IDEA.
  User: magdy
  Date: 11/13/2021
  Time: 10:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>


    <title>Title</title>
</head>
<body style="background: gold">
<tag:userMenu/>

<div class="row">

    <div class="container">
        <h3 class="text-center">All Clients Orders</h3>

        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Last Name</th>
                <th>Number</th>
                <th>Email</th>
                <th>Car Number</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="driver"  items="${sessionScope.driverList}">
                <tr>
                    <td><c:out value="${driver.id}"/></td>
                    <td><c:out value="${driver.name}"/></td>
                    <td><c:out value="${driver.lastName}"/></td>
                    <td><c:out value="${driver.phoneNumber}"/></td>
                    <td><c:out value="${driver.email}"/></td>
                    <td><c:out value="${driver.carNumber}"/></td>
                    <td><a href="${pageContext.request.contextPath}/controller?driverId=${driver.id}&command=deleteDriver">Delete</a>
                    </td>
                </tr>

            </c:forEach>
            </tbody>

        </table>
    </div>
</div>

</body>
</html>
