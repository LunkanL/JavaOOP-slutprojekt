package Customer;

import java.util.Scanner;

import static Utilities.Validator.isNonEmpty;

public class CustomerController {
    private final CustomerService service;
    private final Scanner scanner;

    public CustomerController(CustomerService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    private int readIntWithPrompt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (!isNonEmpty(input)) {
                System.out.println("Värdet får inte vara tomt. Försök igen.");
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ogiltigt nummer. Försök igen.");
            }
        }
    }

    public void handleMenu() {
        while (true) {
            System.out.println("\n--- Kundmeny ---");
            System.out.println("1. Lägg till kund");
            System.out.println("2. Uppdatera e-post");
            System.out.println("3. Uppdatera telefon");
            System.out.println("4. Uppdatera adress");
            System.out.println("5. Uppdatera lösenord");
            System.out.println("6. Visa alla kunder");
            System.out.println("7. Visa kund (ID)");
            System.out.println("0. Till huvudmeny");
            System.out.print("Val: ");
            switch (scanner.nextLine()) {
                case "1" -> addCustomer();
                case "2" -> updateEmail();
                case "3" -> updatePhone();
                case "4" -> updateAddress();
                case "5" -> updatePassword();
                case "6" -> service.showAllCustomers();
                case "7" -> showCustomerById();
                case "0" -> { return; }
                default -> System.out.println("Ogiltigt val.");
            }
        }
    }

    private void addCustomer() {
        System.out.print("Namn: ");
        String name = scanner.nextLine();
        System.out.print("E-post: ");
        String email = scanner.nextLine();
        System.out.print("Telefon: ");
        String phone = scanner.nextLine();
        System.out.print("Adress: ");
        String address = scanner.nextLine();
        System.out.print("Lösenord: ");
        String password = scanner.nextLine();
        service.addCustomer(name, email, phone, address, password);
    }

    private void updateEmail() {
        int id = readIntWithPrompt("Kund-ID: ");
        if (!service.customerExists(id)) {
            System.out.println("Kund med ID " + id + " finns inte.");
            return;
        }

        System.out.print("Ny e-post: ");
        service.updateEmail(id, scanner.nextLine());
    }

    private void updatePhone() {
        int id = readIntWithPrompt("Kund-ID: ");
        if (!service.customerExists(id)) {
            System.out.println("Kund med ID " + id + " finns inte.");
            return;
        }

        System.out.print("Ny telefon: ");
        service.updatePhone(id, scanner.nextLine());
    }

    private void updateAddress() {
        int id = readIntWithPrompt("Kund-ID: ");
        if (!service.customerExists(id)) {
            System.out.println("Kund med ID " + id + " finns inte.");
            return;
        }

        System.out.print("Ny adress: ");
        service.updateAddress(id, scanner.nextLine());
    }

    private void updatePassword() {
        int id = readIntWithPrompt("Kund-ID: ");
        if (!service.customerExists(id)) {
            System.out.println("Kund med ID " + id + " finns inte.");
            return;
        }

        System.out.print("Nytt lösenord: ");
        service.updatePassword(id, scanner.nextLine());
    }

    private void showCustomerById() {
        int id = readIntWithPrompt("Kund-ID: ");
        service.showCustomer(id);
    }
}
