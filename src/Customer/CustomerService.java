package Customer;
import Utilities.Validator;
import java.sql.SQLException;

public class CustomerService {
    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public void addCustomer(String name, String email, String phone, String address, String password) {
        if (!Validator.isNonEmpty(name) || !Validator.isValidEmail(email) ||
                !Validator.isValidPhone(phone) || !Validator.isNonEmpty(address) ||
                !Validator.isNonEmpty(password)) {
            System.out.println("Ogiltig inmatning: Kontrollera namn, e-post, telefon, adress och lösenord.");
            return;
        }
        try {
            repo.insert(name, email, phone, address, password);
            System.out.println("Kund skapad!");
        } catch (SQLException e) {
            System.out.println("Fel vid skapande av kund: " + e.getMessage());
        }
    }

    public void updateEmail(int id, String newEmail) {
        if (!Validator.isValidEmail(newEmail)) {
            System.out.println("Ogiltig e-postadress.");
            return;
        }
        try {
            repo.updateEmail(id, newEmail);
            System.out.println("E-post uppdaterad!");
        } catch (SQLException e) {
            System.out.println("Fel vid uppdatering av e-post: " + e.getMessage());
        }
    }

    public void updatePhone(int id, String newPhone) {
        if (!Validator.isValidPhone(newPhone)) {
            System.out.println("Ogiltigt telefonnummer.");
            return;
        }
        try {
            repo.updatePhone(id, newPhone);
            System.out.println("Telefon uppdaterad!");
        } catch (SQLException e) {
            System.out.println("Fel vid uppdatering av telefon: " + e.getMessage());
        }
    }

    public void updateAddress(int id, String newAddress) {
        if (!Validator.isNonEmpty(newAddress)) {
            System.out.println("Ogiltig adress.");
            return;
        }
        try {
            repo.updateAddress(id, newAddress);
            System.out.println("Adress uppdaterad!");
        } catch (SQLException e) {
            System.out.println("Fel vid uppdatering av adress: " + e.getMessage());
        }
    }

    public void updatePassword(int id, String newPassword) {
        if (!Validator.isNonEmpty(newPassword)) {
            System.out.println("Ogiltigt lösenord.");
            return;
        }
        try {
            repo.updatePassword(id, newPassword);
            System.out.println("Lösenord uppdaterat!");
        } catch (SQLException e) {
            System.out.println("Fel vid uppdatering av lösenord: " + e.getMessage());
        }
    }

    public void showAllCustomers() {
        try {
            repo.getAll().forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Fel vid hämtning av kunder: " + e.getMessage());
        }
    }

    public void showCustomer(int id) {
        try {
            Customer c = repo.findById(id);
            if (c != null) System.out.println(c);
            else System.out.println("Kund ej hittad.");
        } catch (SQLException e) {
            System.out.println("Fel vid hämtning av kund: " + e.getMessage());
        }
    }

    public boolean customerExists(int id) {
        try {
            Customer customer = repo.findById(id);
            return customer != null;
        } catch (SQLException e) {
            System.out.println("Fel vid kontroll av kund: " + e.getMessage());
            return false;
        }
    }

}