package Order;
import Product.ProductRepository;
import Utilities.Validator;

import java.sql.SQLException;
import java.util.Map;

public class OrderService {
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    public OrderService(OrderRepository orderRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public void placeOrder(int customerId, Map<Integer,Integer> items) {
        if (!Validator.isValidQuantity(customerId) || items.isEmpty()) {
            System.out.println("Ogiltig kund eller inga produkter valda.");
            return;
        }
        try {
            for (var entry : items.entrySet()) {
                int pid = entry.getKey(), qty = entry.getValue();
                if (!Validator.isValidQuantity(pid) || !Validator.isValidQuantity(qty)) {
                    System.out.println("Ogiltigt produkt-ID eller antal.");
                    return;
                }
                var product = productRepo.searchById(pid);
                if (product == null) {
                    System.out.println("Produkt med ID " + pid + " hittades inte.");
                    return;
                }
                if (qty > product.getStockQuantity()) {
                    System.out.printf("Ej tillräckligt i lager för %s (finns %d, efterfrågat %d).\n", product.getName(), product.getStockQuantity(), qty);
                    return;
                }
            }

            int orderId = orderRepo.insertOrder(customerId);
            for (var entry : items.entrySet()) {
                int pid = entry.getKey(), qty = entry.getValue();
                var product = productRepo.searchById(pid);
                double price = product.getPrice();
                productRepo.updateStockQuantity(pid, product.getStockQuantity() - qty);
                orderRepo.insertOrderProduct(orderId, pid, qty, price);
            }

            System.out.println("Order lagd (ID = " + orderId + ")");
        } catch (SQLException e) {
            System.out.println("Fel vid orderläggning: " + e.getMessage());
        }
    }

    public void showOrdersForCustomer(int customerId) {
        if (!Validator.isValidQuantity(customerId)) {
            System.out.println("Ogiltigt kund-ID.");
            return;
        }
        try {
            var lines = orderRepo.getOrdersForCustomer(customerId);
            if (lines.isEmpty()) System.out.println("Inga ordrar för kund " + customerId);
            else lines.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Fel vid hämtning av orderhistorik: " + e.getMessage());
        }
    }
}