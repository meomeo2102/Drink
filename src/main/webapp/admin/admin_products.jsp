<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Quản lý sản phẩm</title>
</head>
<body>
    <h1>Danh sách sản phẩm</h1>
    <table border="3">
        <thead>
            <tr>
                <th>ID</th>
                <th>Tên sản phẩm</th>
                <th>Mô tả</th>
                <th>Giá</th>
                <th>Danh mục</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <!-- Duyệt qua danh sách sản phẩm và hiển thị thông tin -->
            <c:forEach var="product" items="${productList}">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.description}</td>
                    <td>${product.price}</td>
                    <td>${product.category.title}</td>
                    <td>
                        <!-- Form xóa sản phẩm -->
                        <form action="/admin/products" method="post">
                            <input type="hidden" name="action" value="deleteProduct">
                            <input type="hidden" name="productId" value="${product.id}">
                            <input type="submit" value="Xóa">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Thêm liên kết để quay lại trang dashboard -->
    <br>
    <a href="/admin/dashboard">Quay lại Dashboard</a>
</body>
</html>
