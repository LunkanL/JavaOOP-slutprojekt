package Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private final Connection conn;

    public CustomerRepository(Connection conn) {
        this.conn = conn;
    }

    // ny kund
    public void insert(String name,
                       String email,
                       String phone,
                       String address,
                       String password) throws SQLException {
        String sql = """
            INSERT INTO customers
              (name, email, phone, address, password)
            VALUES (?, ?, ?, ?, ?)
            """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, address);
            stmt.setString(5, password);
            stmt.executeUpdate();
        }
    }

    public void updateEmail(int customerId, String newEmail) throws SQLException {
        String sql = "UPDATE customers SET email = ? WHERE customer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, customerId);
            stmt.executeUpdate();
        }
    }

    public void updatePhone(int customerId, String newPhone) throws SQLException {
        String sql = "UPDATE customers SET phone = ? WHERE customer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPhone);
            stmt.setInt(2, customerId);
            stmt.executeUpdate();
        }
    }

    public void updateAddress(int customerId, String newAddress) throws SQLException {
        String sql = "UPDATE customers SET address = ? WHERE customer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newAddress);
            stmt.setInt(2, customerId);
            stmt.executeUpdate();
        }
    }

    // uppdatera lösen
    public void updatePassword(int customerId, String newPassword) throws SQLException {
        String sql = "UPDATE customers SET password = ? WHERE customer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, customerId);
            stmt.executeUpdate();
        }
    }

    // hitta kund genom id
    public Customer findById(int customerId) throws SQLException {
        String sql = """
            SELECT customer_id, name, email, phone, address, password
            FROM customers
            WHERE customer_id = ?
            """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return fromResultSet(rs);
            }
        }
        return null;
    }

    public List<Customer> getAll() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = """
            SELECT customer_id, name, email, phone, address, password
            FROM customers
            """;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(fromResultSet(rs));
            }
        }
        return list;
    }

    private Customer fromResultSet(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("customer_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("address"),
                rs.getString("password")
        );
    }
}
