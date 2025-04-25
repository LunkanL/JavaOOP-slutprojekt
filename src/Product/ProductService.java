package Product;

import Utilities.Validator;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public void showAllProducts() {
        try {
            repo.getAll().forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Fel vid hämtning av produkter: " + e.getMessage());
        }
    }

    public void searchByName(String name) {
        if (!Validator.isNonEmpty(name)) {
            System.out.println("Ogiltig sökterm.");
            return;
        }
        try {
            List<Product> results = repo.searchByName(name);
            if (results.isEmpty()) System.out.println("Inga produkter hittades.");
            else results.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Fel vid sökning på namn: " + e.getMessage());
        }
    }

    public void searchByManufacturer(int manufacturerId) {
        if (!Validator.isValidQuantity(manufacturerId)) {
            System.out.println("Ogiltigt tillverkare-ID.");
            return;
        }
        try {
            List<Product> results = repo.searchByManufacturer(manufacturerId);
            if (results.isEmpty()) System.out.println("Inga produkter hittades för tillverkare.");
            else results.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Fel vid sökning på tillverkare: " + e.getMessage());
        }
    }

    public void changePrice(int productId, double newPrice) {
        if (!Validator.isValidQuantity(productId) || !Validator.isValidPrice(newPrice)) {
            System.out.println("Ogiltigt produkt-ID eller pris.");
            return;
        }
        try {
            repo.updatePrice(productId, newPrice);
            System.out.println("Pris uppdaterat.");
        } catch (SQLException e) {
            System.out.println("Fel vid prisuppdatering: " + e.getMessage());
        }
    }

    public void changeStock(int productId, int newStock) {
        if (!Validator.isValidQuantity(productId) || !Validator.isValidQuantity(newStock)) {
            System.out.println("Ogiltigt produkt-ID eller lagersaldo.");
            return;
        }
        try {
            repo.updateStockQuantity(productId, newStock);
            System.out.println("Lagersaldo uppdaterat.");
        } catch (SQLException e) {
            System.out.println("Fel vid uppdatering av lagersaldo: " + e.getMessage());
        }
    }

    public void addProduct(int manufacturerId, String name, String description, double price, int stockQuantity) {
        if (!Validator.isValidQuantity(manufacturerId) || !Validator.isNonEmpty(name) ||
                !Validator.isNonEmpty(description) || !Validator.isValidPrice(price) || !Validator.isValidQuantity(stockQuantity)) {
            System.out.println("Ogiltig produktdata.");
            return;
        }
        try {
            repo.insert(manufacturerId, name, description, price, stockQuantity);
            System.out.println("Ny produkt tillagd.");
        } catch (SQLException e) {
            System.out.println("Fel vid tillägg av produkt: " + e.getMessage());
        }
    }
}