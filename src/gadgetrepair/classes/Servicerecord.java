package gadgetrepair.classes;

public abstract class Servicerecord {

    protected int serviceId;
    protected int customerId;
    protected int gadgetId;
    protected String description;
    protected String status;
    protected double cost;

    public Servicerecord(int serviceId, int customerId, int gadgetId,
                         String description, String status, double cost) {

        this.serviceId = serviceId;
        this.customerId = customerId;
        this.gadgetId = gadgetId;
        this.description = description;
        this.status = status;
        this.cost = cost;
    }

    public abstract double calculateCost();

    public int getServiceId() {
        return serviceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getGadgetId() {
        return gadgetId;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public double getCost() {
        return cost;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
