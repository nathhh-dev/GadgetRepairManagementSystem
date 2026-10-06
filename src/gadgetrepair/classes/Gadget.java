package gadgetrepair.classes;

public class Gadget {

    private int gadgetId;
    private int customerId;
    private String gadgetType;
    private String brandModel;
    private String problem;

    public Gadget(int gadgetId, int customerId, String gadgetType,
                  String brandModel, String problem) {

        this.gadgetId = gadgetId;
        this.customerId = customerId;
        this.gadgetType = gadgetType;
        this.brandModel = brandModel;
        this.problem = problem;
    }

    public int getGadgetId() {
        return gadgetId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getGadgetType() {
        return gadgetType;
    }

    

    public String getBrandModel() {
        return brandModel;
    }

    public String getProblem() {
        return problem;
    }
     public void setGadgetId(int gadgetId) {
        this.gadgetId = gadgetId;
    }
      public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setGadgetType(String type) {
        this.gadgetType = type;
    }

   

    public void setBrandModel(String model) {
        this.brandModel = model;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}