package Order;

import Utilities.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrderController {
    private final OrderService service;
    private final Scanner scanner;

    public OrderController(OrderService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void handleMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Ordrar ---");
            System.out.println("1. Lägg order");
            System.out.println("2. Visa orderhistorik för kund");
            System.out.println("0. Till huvudmeny");
            System.out.print("Välj: ");
            switch (scanner.nextLine()) {
                case "1" -> {
                    System.out.print("Kund-ID: ");
                    String input = scanner.nextLine();

                    if (!Validator.isNonEmpty(input)) {
                        System.out.println("Kund-ID får inte vara tomt.");
                        break;
                    }

                    int customerId;
                    try {
                        customerId = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Ogiltigt ID. Vänligen ange ett giltigt nummer.");
                        break;
                    }

                    Map<Integer, Integer> items = new HashMap<>();

                    while (true) {
                        System.out.print("Produkt-ID (0 för att avbryta): ");
                        input = scanner.nextLine();

                        if (!Validator.isNonEmpty(input)) {
                            System.out.println("Du måste skriva in ett värde.");
                            continue;
                        }

                        int prodId;
                        try {
                            prodId = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println("Ogiltigt produkt-ID. Försök igen.");
                            continue;
                        }

                        if (prodId == 0) break;

                        System.out.print("Antal: ");
                        input = scanner.nextLine();
                        int qty;
                        try {
                            qty = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println("Ogiltigt antal. Försök igen.");
                            continue;
                        }

                        items.put(prodId, qty);
                    }

                    service.placeOrder(customerId, items);
                }

                case "2" -> {
                    System.out.print("Kund-ID: ");
                    String input = scanner.nextLine();

                    if (!Validator.isNonEmpty(input)) {
                        System.out.println("Kund-ID får inte vara tomt.");
                        break;
                    }
                    int customerId;
                    try {
                        customerId = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Ogiltigt ID. Vänligen ange ett giltigt nummer.");
                        break;
                    }
                    service.showOrdersForCustomer(customerId);
                }
                case "0" -> running = false;
                default -> System.out.println("Ogiltigt val.");
            }
        }
    }
}
