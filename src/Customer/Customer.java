package Customer;

public class Customer {
    private final int customerId;
    private final String name;
    private final String email;
    private final String phone;
    private final String address;
    private final String password;

    public Customer(int customerId, String name, String email, String phone, String address, String password) {
        this.customerId = customerId;
        this.name       = name;
        this.email      = email;
        this.phone      = phone;
        this.address    = address;
        this.password   = password;
    }

    public int getCustomerId() { return customerId; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPhone()    { return phone; }
    public String getAddress()  { return address; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Namn: %s | E-post: %s | Telefon: %s | Adress: %s",
                customerId, name, email, phone, address
        );
    }
}
