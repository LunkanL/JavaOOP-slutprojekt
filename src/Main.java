import Customer.CustomerController;
import Customer.CustomerRepository;
import Customer.CustomerService;
import Order.OrderController;
import Order.OrderRepository;
import Order.OrderService;
import Product.ProductController;
import Product.ProductRepository;
import Product.ProductService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:webbutiken.db")) {
            Scanner scanner = new Scanner(System.in);

            //init
            CustomerRepository customerRepo = new CustomerRepository(conn);
            ProductRepository productRepo = new ProductRepository(conn);
            OrderRepository orderRepo = new OrderRepository(conn);

            CustomerService customerService = new CustomerService(customerRepo);
            ProductService productService = new ProductService(productRepo);
            OrderService orderService = new OrderService(orderRepo, productRepo);

            CustomerController customerController = new CustomerController(customerService, scanner);
            ProductController productController = new ProductController(productService, scanner);
            OrderController orderController = new OrderController(orderService, scanner);



            boolean running = true;
            while (running) {
                System.out.println("\n--- HuvudMeny ---");
                System.out.println("1. Hantera kunder");
                System.out.println("2. Hanter produkter");
                System.out.println("3. Hantera ordrar");
                System.out.println("0. Avsluta");
                System.out.print("Välj: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> customerController.handleMenu();
                    case "2" -> productController.handleMenu();
                    case "3" -> orderController.handleMenu();
                    case "0" -> running = false;
                    default  -> System.out.println("Ogiltigt val.");
                }
            }

        } catch (Exception e) {
            System.out.println("Startfel: " + e.getMessage());
        }
    }
}
