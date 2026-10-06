package gadgetrepair.classes;

public class Repair {

     private int repairId;
    private int gadgetId;
    private String repairStatus;
    private String repairDescription;
    private String technician;
    private String dateBrought;

    public Repair(int repairId, int gadgetId, String repairStatus,
                  String repairDescription, String technician,
                  String dateBrought) {

        this.repairId = repairId;
        this.gadgetId = gadgetId;
        this.repairStatus = repairStatus;
        this.repairDescription = repairDescription;
        this.technician = technician;
        this.dateBrought = dateBrought;
    }

    public int getRepairId() {
        return repairId;
    }

    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }

    public int getGadgetId() {
        return gadgetId;
    }

    public void setGadgetId(int gadgetId) {
        this.gadgetId = gadgetId;
    }

    public String getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
    }

    public String getRepairDescription() {
        return repairDescription;
    }

    public void setRepairDescription(String repairDescription) {
        this.repairDescription = repairDescription;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public String getDateBrought() {
        return dateBrought;
    }

    public void setDateBrought(String dateBrought) {
        this.dateBrought = dateBrought;
    }
}