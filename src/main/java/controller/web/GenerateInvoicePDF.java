package controller.web;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Order;
import models.OrderItem;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/secure/generateInvoicePDF")
public class GenerateInvoicePDF extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null || orderIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/secure/invoice.jsp?error=Mã hóa đơn không hợp lệ");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            Order order = orderDAO.getOrderById(orderId);
            if (order == null) {
                response.sendRedirect(request.getContextPath() + "/secure/invoice.jsp?error=Không tìm thấy hóa đơn");
                return;
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=hoa_don_" + orderId + ".pdf");

            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Hóa Đơn"));
            document.add(new Paragraph("Mã Hóa Đơn: " + order.getId()));
            document.add(new Paragraph("Phương Thức Thanh Toán: " + order.getPaymentMethod()));
            document.add(new Paragraph("Tổng Tiền: " + String.format("%,.0f VNĐ", order.getTotalPrice())));

            Table table = new Table(new float[]{3, 1, 2});
            table.addHeaderCell(new Cell().add(new Paragraph("Sản Phẩm")));
            table.addHeaderCell(new Cell().add(new Paragraph("Số Lượng")));
            table.addHeaderCell(new Cell().add(new Paragraph("Giá")));

            for (OrderItem item : order.getItems()) {
                table.addCell(new Cell().add(new Paragraph(item.getProductName())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity()))));
                table.addCell(new Cell().add(new Paragraph(String.format("%,.0f VNĐ", item.getPrice()))));
            }

            document.add(table);
            document.close();

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/secure/invoice.jsp?error=Lỗi khi tạo PDF: " + e.getMessage());
        }
    }
}