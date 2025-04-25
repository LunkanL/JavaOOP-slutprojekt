package Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final Connection conn;

    public ProductRepository(Connection conn) {
        this.conn = conn;
    }

    // hämta alla produkter
    public List<Product> getAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT product_id, manufacturer_id, name, description, price, stock_quantity FROM products";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(fromResultSet(rs));
            }
        }
        return list;
    }

    // sök produkter genom namn
    public List<Product> searchByName(String term) throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT product_id, manufacturer_id, name, description, price, stock_quantity "
                + "FROM products WHERE name LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + term + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(fromResultSet(rs));
                }
            }
        }
        return list;
    }

    // sök produkter genom tillvärkare
    public List<Product> searchByManufacturer(int manufacturerId) throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT product_id, manufacturer_id, name, description, price, stock_quantity "
                + "FROM products WHERE manufacturer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, manufacturerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(fromResultSet(rs));
                }
            }
        }
        return list;
    }

    // uppdatera pris
    public void updatePrice(int productId, double newPrice) throws SQLException {
        String sql = "UPDATE products SET price = ? WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newPrice);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
    }

    // uppdatera antal i lager
    public void updateStockQuantity(int productId, int newQty) throws SQLException {
        String sql = "UPDATE products SET stock_quantity = ? WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newQty);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
    }

    // lägg till ny produkt
    public void insert(int manufacturerId, String name, String description, double price, int stockQuantity) throws SQLException {
        String sql = "INSERT INTO products (manufacturer_id, name, description, price, stock_quantity) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, manufacturerId);
            stmt.setString(2, name);
            stmt.setString(3, description);
            stmt.setDouble(4, price);
            stmt.setInt(5, stockQuantity);
            stmt.executeUpdate();
        }
    }

    // hämta produkt gneom id
    public Product searchById(int productId) throws SQLException {
        String sql = "SELECT product_id, manufacturer_id, name, description, price, stock_quantity "
                + "FROM products WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return fromResultSet(rs);
                }
            }
        }
        return null;
    }

    private Product fromResultSet(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("product_id"),
                rs.getInt("manufacturer_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getInt("stock_quantity")
        );
    }
}
