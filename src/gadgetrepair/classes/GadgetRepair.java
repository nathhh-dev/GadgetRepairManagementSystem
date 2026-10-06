package gadgetrepair.classes;

import gadgetrepair.classes.Servicerecord;

public class GadgetRepair extends Servicerecord {

    public GadgetRepair(int serviceId, int customerId, int gadgetId,
                        String description, String status, double cost) {

        super(serviceId, customerId, gadgetId,
              description, status, cost);
    }

    @Override
    public double calculateCost() {
        return cost;
    }
}
