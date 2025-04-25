package Product;

import Utilities.Validator;

import java.util.Scanner;

public class ProductController {
    private final ProductService service;
    private final Scanner scanner;

    public ProductController(ProductService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    private int readIntWithPrompt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (Validator.isNonEmpty(input)) {
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Ogiltigt nummer. Försök igen.");
                }
            } else {
                System.out.println("Värdet får inte vara tomt. Försök igen.");
            }
        }
    }

    private double readDoubleWithPrompt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (Validator.isNonEmpty(input)) {
                try {
                    return Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    System.out.println("Ogiltigt pris. Försök igen.");
                }
            } else {
                System.out.println("Värdet får inte vara tomt. Försök igen.");
            }
        }
    }

    public void handleMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Produkter ---");
            System.out.println("1. Visa alla produkter");
            System.out.println("2. Sök efter namn");
            System.out.println("3. Sök efter tillverkare-ID");
            System.out.println("4. Uppdatera pris");
            System.out.println("5. Uppdatera lagersaldo");
            System.out.println("6. Lägg till produkt");
            System.out.println("0. Till huvudmeny");
            System.out.print("Välj: ");
            switch (scanner.nextLine()) {
                case "1" -> service.showAllProducts();
                case "2" -> {
                    System.out.print("Sökterm: ");
                    service.searchByName(scanner.nextLine());
                }
                case "3" -> {
                    int manId = readIntWithPrompt("Tillverkare-ID: ");
                    service.searchByManufacturer(manId);
                }
                case "4" -> {
                    int id = readIntWithPrompt("Produkt-ID: ");
                    double price = readDoubleWithPrompt("Nytt pris: ");
                    service.changePrice(id, price);
                }
                case "5" -> {
                    int id = readIntWithPrompt("Produkt-ID: ");
                    int qty = readIntWithPrompt("Nytt antal i lager: ");
                    service.changeStock(id, qty);
                }
                case "6" -> {
                    int manId = readIntWithPrompt("Tillverkare-ID: ");
                    System.out.print("Namn: ");
                    String name = scanner.nextLine();
                    System.out.print("Beskrivning: ");
                    String desc = scanner.nextLine();
                    double price = readDoubleWithPrompt("Pris: ");
                    int stock = readIntWithPrompt("Lager: ");
                    service.addProduct(manId, name, desc, price, stock);
                }
                case "0" -> running = false;
                default -> System.out.println("Ogiltigt val.");
            }
        }
    }
}
