<%-- 
    Document   : error
    Created on : Jun 11, 2024, 9:28:09 PM
    Author     : phamm
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error page</title>
    </head>
    <body>
        <h1>You bricked this page</h1>
        <h2>Error: <c:out value="${error}"></c:out></h2>
    </body>
</html>
