package gadgetrepair.services;

import gadgetrepair.classes.Servicerecord;

public class DiagnosticService extends Servicerecord {

    public DiagnosticService(int serviceId, int customerId, int gadgetId,
                             String description, String status, double cost) {

        super(serviceId, customerId, gadgetId,
              description, status, cost);
    }

    @Override
    public double calculateCost() {
        return cost;
    }
}