<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Thêm Sản Phẩm</title>
</head>
<body>
	<h1>Thêm Sản Phẩm Mới</h1>
<form action="${pageContext.request.contextPath}/admin/product/insert" method="post" enctype="multipart/form-data">
		<div>
			<label for="name">Tên Sản Phẩm:</label> <input type="text" id="name"
				name="name" required>
		</div>
		<div>
			<label for="description">Mô Tả:</label>
			<textarea id="description" name="description" required></textarea>
		</div>
		<div>
			<label for="photo">Ảnh Sản Phẩm:</label> <input type="file"
				id="photo" name="photo" accept="image/*" required>
		</div>
		<div>
			<label for="price">Giá:</label> <input type="number" id="price"
				name="price" step="0.01" required>

		</div>
		<button type="submit">Thêm Sản Phẩm</button>
	</form>
</body>
</html>
