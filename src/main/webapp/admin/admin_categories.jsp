<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách danh mục</title>
    <!-- Thêm link để sử dụng phông chữ hỗ trợ tiếng Việt -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
        }
    </style>
</head>
<body>
    <h1>Danh sách danh mục</h1>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Tên danh mục</th>
                <th>Mô tả</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="category" items="${categoryList}">
                <tr>
                    <td>${category.id}</td>
                    <td>${category.title}</td>
                    <td>${category.description}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
