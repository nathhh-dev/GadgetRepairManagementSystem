package gadgetrepair.classes;

public class Report {

    private int totalCustomers;
    private int totalGadgets;
    private int totalRepairs;
    private int pendingRepairs;
    private int completedRepairs;

    public Report(int totalCustomers, int totalGadgets,
                  int totalRepairs, int pendingRepairs,
                  int completedRepairs) {

        this.totalCustomers = totalCustomers;
        this.totalGadgets = totalGadgets;
        this.totalRepairs = totalRepairs;
        this.pendingRepairs = pendingRepairs;
        this.completedRepairs = completedRepairs;
    }

    public void displayReport() {
        System.out.println("===== REPAIR SUMMARY =====");
        System.out.println("Total Customers: " + totalCustomers);
        System.out.println("Total Gadgets: " + totalGadgets);
        System.out.println("Total Repairs: " + totalRepairs);
        System.out.println("Pending Repairs: " + pendingRepairs);
        System.out.println("Completed Repairs: " + completedRepairs);
    }
}