package Order;

import java.time.LocalDateTime;
import java.util.Map;

public class Order {
    private final int orderId;
    private final int customerId;
    private final LocalDateTime orderDate;
    private final Map<Integer, Integer> productQuantities;

    // Kontsruktor för nya ordrar
    public Order(int customerId, Map<Integer, Integer> productQuantities) {
        this.orderId = -1;
        this.customerId = customerId;
        this.orderDate = null;
        this.productQuantities = productQuantities;
    }

    // Konstruktor för att läsa db
    public Order(int orderId, int customerId, LocalDateTime orderDate, Map<Integer, Integer> productQuantities) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.productQuantities = productQuantities;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Map<Integer, Integer> getProductQuantities() {
        return productQuantities;
    }
}
