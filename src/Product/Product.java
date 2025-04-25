package Product;

public class Product {
    private final int productId;
    private final int manufacturerId;
    private final String name;
    private final String description;
    private final double price;
    private final int stockQuantity;

    public Product(int productId, int manufacturerId, String name, String description, double price, int stockQuantity) {
        this.productId = productId;
        this.manufacturerId = manufacturerId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Tillverkare-ID: %d | %s | %s | Pris: %.2f kr | Lager: %d",
                productId, manufacturerId, name, description, price, stockQuantity
        );
    }
}
