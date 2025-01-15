package controller.admin.management.product;

import dao.ProductDAO;
import models.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

@WebServlet("/admin/product/insert")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class InsertProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Không cần xử lý danh mục nữa
        request.getRequestDispatcher("/admin/insert_product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));

            // Lấy thông tin file ảnh
            Part filePart = request.getPart("photo");
            String fileName = extractFileName(filePart);
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

            // Tạo thư mục nếu chưa tồn tại
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            // Lưu file ảnh
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);

            // Lưu thông tin sản phẩm mà không cần liên kết danh mục
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPhoto(fileName); // Lưu tên file vào DB
            product.setPrice(price);

            ProductDAO productDAO = new ProductDAO();
            productDAO.addProduct(product);

            response.sendRedirect("/admin/product/manage?success=Product added successfully");
        } catch (NumberFormatException e) {
            response.sendRedirect("/admin/product/manage?error=Invalid input format");
        } catch (Exception e) {
            response.sendRedirect("/admin/product/manage?error=An error occurred");
        }
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String content : contentDisp.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return "default.jpg"; // Tên file mặc định nếu không tìm thấy
    }
}
