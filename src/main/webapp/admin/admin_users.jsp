<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="models.User" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <title>Quản lý người dùng</title>
</head>
<body>
    <h1>Danh sách người dùng</h1>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Tên người dùng</th>
                <th>Email</th>
                <th>Vai trò</th>
            </tr>
        </thead>
        <tbody>
            <% 
                // Lấy danh sách người dùng từ request
                List<User> users = (List<User>) request.getAttribute("userList");

                // Kiểm tra nếu danh sách người dùng không rỗng
                if (users != null && !users.isEmpty()) {
                    for (User user : users) {
            %>
            <tr>
                <td><%= user.getId() %></td>
                <td><%= user.getUsername() %></td>
                <td><%= user.getEmail() %></td>
                <td><%= user.isAdmin() ? "Quản trị viên" : "Người dùng" %></td> <!-- Hiển thị vai trò -->
            </tr>
            <% 
                    }
                } else {
            %>
            <tr>
                <td colspan="4">Không có người dùng nào.</td>
            </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
