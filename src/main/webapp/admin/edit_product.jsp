<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh Sửa Sản Phẩm</title>
</head>
<body>
    <h1>Chỉnh Sửa Sản Phẩm</h1>
    <form action="edit" method="post">
        <input type="hidden" name="id" value="${product.id}">
        <div>
            <label for="name">Tên Sản Phẩm:</label>
            <input type="text" id="name" name="name" value="${product.name}" required>
        </div>
        <div>
            <label for="description">Mô Tả:</label>
            <textarea id="description" name="description" required>${product.description}</textarea>
        </div>
        <div>
            <label for="photo">Ảnh Sản Phẩm:</label>
            <input type="text" id="photo" name="photo" value="${product.photo}" required>
        </div>
        <div>
            <label for="price">Giá:</label>
            <input type="number" id="price" name="price" value="${product.price}" step="0.01" required>
        </div>
        <div>
            <label for="category_id">Danh Mục:</label>
            <select id="category_id" name="category_id" required>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}" <c:if test="${category.id == product.category.id}">selected</c:if>>${category.name}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit">Cập Nhật Sản Phẩm</button>
    </form>
</body>
</html>
