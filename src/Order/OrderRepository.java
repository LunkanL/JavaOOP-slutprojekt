package Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private final Connection conn;

    public OrderRepository(Connection conn) {
        this.conn = conn;
    }

    public int insertOrder(int customerId) throws SQLException {
        String sql = "INSERT INTO orders (customer_id, order_date) VALUES (?, CURRENT_TIMESTAMP)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        throw new SQLException("Kunde inte skapa order.");
    }


    public void insertOrderProduct(int orderId, int productId, int quantity, double unitPrice) throws SQLException {
        String sql = "INSERT INTO orders_products (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, unitPrice);
            stmt.executeUpdate();
        }
    }

    // orderhistorik
    public List<String> getOrdersForCustomer(int customerId) throws SQLException {
        List<String> results = new ArrayList<>();
        String sql = """
            SELECT o.order_id,
                   o.order_date,
                   p.name AS product_name,
                   op.quantity,
                   p.price
              FROM orders o
              JOIN orders_products op ON o.order_id = op.order_id
              JOIN products p ON p.product_id = op.product_id
             WHERE o.customer_id = ?
             ORDER BY o.order_date DESC, o.order_id
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                int lastOrderId = -1;
                String lastOrderDate = null;
                double total = 0;
                while (rs.next()) {
                    int oid = rs.getInt("order_id");
                    String date = rs.getString("order_date");
                    String prod = rs.getString("product_name");
                    int qty = rs.getInt("quantity");
                    double price = rs.getDouble("price");

                    if (oid != lastOrderId) {
                        if (lastOrderId != -1) {
                            results.add(String.format(" → Totalt: %.2f kr%n", total));
                        }
                        lastOrderId = oid;
                        lastOrderDate = date;
                        total = 0;
                        results.add(String.format("Order #%d | Datum: %s", oid, lastOrderDate));
                    }
                    double lineSum = price * qty;
                    total += lineSum;
                    results.add(String.format("   %s x%d vid pris %.2f kr = %.2f kr", prod, qty, price, lineSum));
                }
                if (lastOrderId != -1) {
                    results.add(String.format(" → Totalt: %.2f kr%n", total));
                }
            }
        }
        return results;
    }
}
