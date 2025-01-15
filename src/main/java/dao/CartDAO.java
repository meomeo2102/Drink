package dao;

import models.Cart;
import models.CartItem;
import models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private Connection connection;

    public CartDAO(Connection connection) {
        this.connection = connection;
    }

    public Cart getCartByUserId(int userId) {
        String sql = "SELECT * FROM `dbo.cart` WHERE UserId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int cartId = rs.getInt("CartId");
                Cart cart = new Cart(cartId, userId);

                // Populate cart items
                String itemQuery = "SELECT * FROM `dbo.cartitem` WHERE CartId = ?";
                try (PreparedStatement itemStmt = connection.prepareStatement(itemQuery)) {
                    itemStmt.setInt(1, cartId);
                    ResultSet itemRs = itemStmt.executeQuery();
                    while (itemRs.next()) {
                        int productId = itemRs.getInt("ProductId");
                        int quantity = itemRs.getInt("Quantity");
                        System.out.println(quantity);
                        ProductDAO productDAO = new ProductDAO();
                        Product product = productDAO.getProductById(productId);
                        
                        if (product != null) {
                            cart.addItem(product.getId(), quantity);
                        } else {
                            System.err.println("Product not found: ID = " + productId);
                        }
                        
                    }
                }

                return cart;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log SQL exceptions
        }
        return null;
    }

    public void createCart(Cart cart) {
        String query = "INSERT INTO `dbo.cart` (UserId) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cart.getUserId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cart.setCartId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateCart(Cart cart) {
        String deleteItems = "DELETE FROM `dbo.cartitem` WHERE CartId = ?";
        String insertItem = "INSERT INTO `dbo.cartitem` (CartId, ProductId, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteItems)) {
            deleteStmt.setInt(1, cart.getCartId());
            deleteStmt.executeUpdate();

            try (PreparedStatement insertStmt = connection.prepareStatement(insertItem)) {
                for (CartItem item : cart.getItems().values()) {
                    insertStmt.setInt(1, cart.getCartId());
                    insertStmt.setInt(2, item.getProduct().getId());
                    insertStmt.setInt(3, item.getQuantity());
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Phương thức close để đóng kết nối
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Log lỗi khi đóng kết nối thất bại
            }
        }
    }
    
    // Method to fetch all CartItems for a specific cartId
    public List<CartItem> getCartItems(int cartId) {
        String query = "SELECT * FROM `dbo.cartitem` WHERE CartId = ?";
        List<CartItem> items = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();

            ProductDAO productDAO = new ProductDAO();
            // Fetch product details for each cart item
            while (rs.next()) {
                int productId = rs.getInt("ProductId");
                int quantity = rs.getInt("Quantity");

                // Fetch the product
                Product product = productDAO.getProductById(productId);
                if (product != null) {
                    items.add(new CartItem(product, quantity));
                } else {
                    System.err.println("Product not found for ID: " + productId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    // Clear all items in the cart for a given cartId
    public void clearCart(int cartId) {
        String query = "DELETE FROM `dbo.cartitem` WHERE CartId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cartId); // Bind the cart ID
            stmt.executeUpdate();  // Execute the DELETE query
        } catch (SQLException e) {
            e.printStackTrace();  // Log the SQL exception
        }
    }

	public boolean removeCartItem(int cartId, int pId) {
		String query = "DELETE FROM `dbo.cartitem` WHERE CartId = ? AND ProductId = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        // Gán giá trị cho các tham số trong câu lệnh SQL
	        stmt.setInt(1, cartId); // Gán cartId vào tham số đầu tiên
	        stmt.setInt(2, pId);   // Gán pId vào tham số thứ hai

	        // Thực thi câu lệnh DELETE
	        int rowsAffected = stmt.executeUpdate();
	        // Kiểm tra nếu có ít nhất một hàng bị ảnh hưởng (xóa thành công)
	        return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();  // Log the SQL exception
        }
	    return false;
	}

	
}
