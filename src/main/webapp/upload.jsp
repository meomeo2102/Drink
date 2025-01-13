<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/template/includes/headerResource.jsp" />
<title>Upload Avatar</title>
</head>
<body>

	<div align="center">

		<h1>File Upload</h1>

		<form method="post" action="UploadServlet"
			enctype="multipart/form-data">

			<p>
				Select file to upload: <input type="file" name="photo" size="60" />
			</p>
			
			<input type="submit" value="Upload" />

		</form>
	</div>

</body>
</html>