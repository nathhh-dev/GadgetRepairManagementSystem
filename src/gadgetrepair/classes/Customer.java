package gadgetrepair.classes;

public class Customer extends Person {

    private int customerId;

    public Customer(int customerId, String firstName, String lastName, String contactNumber) {
        super(firstName, lastName, contactNumber);
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String getRole() {
        return "Customer";
    }
}