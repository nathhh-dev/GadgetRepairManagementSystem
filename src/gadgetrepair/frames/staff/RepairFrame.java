/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gadgetrepair.frames.staff;

import gadgetrepair.frames.staff.DashboardFrame;
import gadgetrepair.data.DatabaseConnection;
import gadgetrepair.frames.admin.AdminDashboardFrame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class RepairFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(RepairFrame.class.getName());

    private String userRole;
    private String username;

    
    private void loadCustomerGadgets(int customerId) {

    cmbGadget.removeAllItems();

    String sql =
        "SELECT gadget_id, gadget_type, brand_model, problem " +
        "FROM gadgets " +
        "WHERE customer_id = ? AND is_active = 1 " +
        "ORDER BY gadget_id";

    try (
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql)
    ) {

        pst.setInt(1, customerId);

        try (ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                int gadgetId = rs.getInt("gadget_id");
                String gadgetType = rs.getString("gadget_type");
                String brandModel = rs.getString("brand_model");

                cmbGadget.addItem(
                    gadgetId + " - " +
                    gadgetType + " - " +
                    brandModel
                );
            }
        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
            this,
            "Error loading gadgets:\n" + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}

    
    private void loadTechnicians() {

        cmbRepairTechnician.removeAllItems();
        cmbRepairTechnician.addItem("Select Technician");

        String sql =
            "SELECT t.technician_id, a.username " +
            "FROM technicians t " +
            "JOIN accounts a ON t.account_id = a.account_id " +
            "WHERE a.is_active = 1 " +
            "ORDER BY a.username";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                cmbRepairTechnician.addItem(
                    rs.getInt("technician_id")
                    + " - "
                    + rs.getString("username")
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                this,
                "Error loading technicians:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void loadGadgetsByCustomer() {

        cmbGadget.removeAllItems();
        cmbGadget.addItem("Select Gadget");

        txtProblem.setText("");

        String customerIdText =
            txtSelectedCustomerId.getText().trim();

        if (customerIdText.isEmpty()) {
            return;
        }

        try {

            int customerId =
                Integer.parseInt(customerIdText);

            String sql =
                "SELECT gadget_id, gadget_type, brand_model " +
                "FROM gadgets " +
                "WHERE customer_id = ? " +
                "AND is_active = 1 " +
                "ORDER BY gadget_id";

            try (
                Connection conn =
                    DatabaseConnection.getConnection();

                PreparedStatement pst =
                    conn.prepareStatement(sql)
            ) {

                pst.setInt(1, customerId);

                try (ResultSet rs = pst.executeQuery()) {

                    while (rs.next()) {

                        cmbGadget.addItem(
                            rs.getInt("gadget_id")
                            + " - "
                            + rs.getString("gadget_type")
                            + " - "
                            + rs.getString("brand_model")
                        );
                    }
                }
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "Invalid Customer ID."
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                this,
                "Error loading gadgets:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
     private void loadProblemFromSelectedGadget() {

        if (
            cmbGadget.getSelectedItem() == null ||
            cmbGadget.getSelectedIndex() == 0
        ) {

            txtProblem.setText("");
            return;
        }

        String gadgetText =
            cmbGadget.getSelectedItem().toString();

        try {

            int gadgetId =
                Integer.parseInt(
                    gadgetText.split(" - ")[0]
                );

            String sql =
                "SELECT problem " +
                "FROM gadgets " +
                "WHERE gadget_id = ? " +
                "AND is_active = 1";

            try (
                Connection conn =
                    DatabaseConnection.getConnection();

                PreparedStatement pst =
                    conn.prepareStatement(sql)
            ) {

                pst.setInt(1, gadgetId);

                try (ResultSet rs =
                    pst.executeQuery()) {

                    if (rs.next()) {

                        txtProblem.setText(
                            rs.getString("problem")
                        );

                    } else {

                        txtProblem.setText("");
                    }
                }
            }

        } catch (
            NumberFormatException |
            SQLException e
        ) {

            txtProblem.setText("");

            JOptionPane.showMessageDialog(
                this,
                "Error loading gadget problem:\n"
                + e.getMessage()
            );
        }
    }

   
    private void refreshRepairTable() {

        DefaultTableModel model =
            (DefaultTableModel) tblRepairs.getModel();

        model.setRowCount(0);

        String sql =
            "SELECT r.repair_id, " +
            "CONCAT(c.first_name, ' ', c.last_name) AS customer_name, " +
            "CONCAT(g.gadget_type, ' - ', g.brand_model) AS gadget_name, " +
            "r.repair_description, " +
            "a.username AS technician_name, " +
            "r.repair_status, " +
            "r.date_brought " +
            "FROM repairs r " +
            "JOIN gadgets g ON r.gadget_id = g.gadget_id " +
            "JOIN customers c ON g.customer_id = c.customer_id " +
            "LEFT JOIN technicians t " +
            "ON r.technician_id = t.technician_id " +
            "LEFT JOIN accounts a " +
            "ON t.account_id = a.account_id " +
            "WHERE r.is_archived = 0 " +
            "ORDER BY r.repair_id";

        try (
            Connection conn =
                DatabaseConnection.getConnection();

            PreparedStatement pst =
                conn.prepareStatement(sql);

            ResultSet rs =
                pst.executeQuery()
        ) {

            while (rs.next()) {

                model.addRow(new Object[]{
                    rs.getInt("repair_id"),
                    rs.getString("customer_name"),
                    rs.getString("gadget_name"),
                    rs.getString("repair_description"),
                    rs.getString("technician_name"),
                    rs.getString("repair_status"),
                    rs.getDate("date_brought")
                });
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                this,
                "Error loading repairs:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void clearRepairFields() {

    txtSelectedCustomerId.setText("");
    txtSelectedCustomerName.setText("");
    txtSelectedContact.setText("");

    cmbGadget.setSelectedIndex(-1);

    txtProblem.setText("");

    cmbRepairTechnician.setSelectedIndex(-1);

    cmbRepairStatus.setSelectedIndex(-1);

    cldrDate.setDate(null);

    tblCustomerResults.clearSelection();
}

    
    public RepairFrame(String userRole) {

        initComponents();

        this.userRole = userRole;

        loadTechnicians();

        cmbGadget.removeAllItems();
        cmbGadget.addItem("Select Gadget");

        cmbRepairStatus.setSelectedItem("Pending");

        cldrDate.setDate(new java.util.Date());

        refreshRepairTable();

        tblRepairs.setDefaultEditor(Object.class, null);
        tblCustomerResults.setDefaultEditor(Object.class, null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtTitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        txtRepairId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbRepairTechnician = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        btnCreate = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        cmbGadget = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cmbRepairStatus = new javax.swing.JComboBox<>();
        cldrDate = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        txtCustomerSearch = new javax.swing.JTextField();
        btnSearchCustomer = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtProblem = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtSelectedCustomerId = new javax.swing.JTextField();
        txtSelectedCustomerName = new javax.swing.JTextField();
        txtSelectedContact = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCustomerResults = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSearchRepair = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRepairs = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnRepairs = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(18, 52, 86));
        jPanel1.setPreferredSize(new java.awt.Dimension(850, 80));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Downloads\\LOGO REPAIRsss.png")); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(135, 135));

        txtTitle.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        txtTitle.setForeground(new java.awt.Color(242, 242, 242));
        txtTitle.setText("REPAIR MANAGEMENT");
        txtTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTitle)
                .addGap(373, 373, 373)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(txtTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setPreferredSize(new java.awt.Dimension(300, 230));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("REPAIR INFORMATION");

        jLabel2.setText("Repair ID:");

        txtRepairId.setEditable(false);
        txtRepairId.addActionListener(this::txtRepairIdActionPerformed);

        jLabel3.setText("Problem:");

        jLabel4.setText("Repair Technician:");

        cmbRepairTechnician.addActionListener(this::cmbRepairTechnicianActionPerformed);

        jLabel5.setText("Date Brought");

        btnCreate.setBackground(new java.awt.Color(24, 91, 160));
        btnCreate.setForeground(new java.awt.Color(255, 255, 255));
        btnCreate.setText("CREATE");
        btnCreate.addActionListener(this::btnCreateActionPerformed);

        btnEdit.setBackground(new java.awt.Color(24, 91, 160));
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("EDIT");
        btnEdit.addActionListener(this::btnEditActionPerformed);

        btnSave.setBackground(new java.awt.Color(24, 91, 160));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("SAVE");
        btnSave.addActionListener(this::btnSaveActionPerformed);

        jLabel11.setText("Gadget:");

        cmbGadget.addActionListener(this::cmbGadgetActionPerformed);

        jLabel12.setText("Status:");

        cmbRepairStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Status", "Pending", "In Progress", "Completed" }));
        cmbRepairStatus.addActionListener(this::cmbRepairStatusActionPerformed);

        jLabel1.setText("Search Customer:");

        btnSearchCustomer.setText("Search");
        btnSearchCustomer.addActionListener(this::btnSearchCustomerActionPerformed);

        txtProblem.setEditable(false);
        txtProblem.setColumns(20);
        txtProblem.setRows(5);
        jScrollPane3.setViewportView(txtProblem);

        jLabel13.setText("Selected Customer");

        jLabel14.setText("Customer ID:");

        jLabel15.setText("Customer Name:");

        jLabel16.setText("Contact Number:");

        txtSelectedCustomerId.setEditable(false);
        txtSelectedCustomerId.addActionListener(this::txtSelectedCustomerIdActionPerformed);

        txtSelectedCustomerName.setEditable(false);
        txtSelectedCustomerName.addActionListener(this::txtSelectedCustomerNameActionPerformed);

        txtSelectedContact.setEditable(false);
        txtSelectedContact.addActionListener(this::txtSelectedContactActionPerformed);

        tblCustomerResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Customer ID", "Customer Name", "Contact Number"
            }
        ));
        tblCustomerResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCustomerResultsMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblCustomerResults);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel3.setPreferredSize(new java.awt.Dimension(550, 330));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("REPAIR RECORDS");

        jLabel6.setText("Search :");

        txtSearchRepair.addActionListener(this::txtSearchRepairActionPerformed);

        btnSearch.setBackground(new java.awt.Color(24, 91, 160));
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("SEARCH");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(500, 100));

        tblRepairs.setBackground(new java.awt.Color(15, 43, 77));
        tblRepairs.setForeground(new java.awt.Color(255, 255, 255));
        tblRepairs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Repair ID", "Customer ", "Gadget", "Problem", "Repair Technician", "Status", "Date Brought"
            }
        ));
        jScrollPane1.setViewportView(tblRepairs);

        btnBack.setBackground(new java.awt.Color(15, 43, 77));
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("BACK");
        btnBack.addActionListener(this::btnBackActionPerformed);

        btnRepairs.setText("COMPLETED REPAIRS");
        btnRepairs.addActionListener(this::btnRepairsActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel10))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnRepairs)
                                .addGap(127, 127, 127)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(29, 29, 29)
                                    .addComponent(txtSearchRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(45, 45, 45)
                                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(330, 330, 330)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtSearchRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBack)
                            .addComponent(btnRepairs))
                        .addGap(29, 61, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(275, 275, 275)
                .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(jLabel9)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbRepairTechnician, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cldrDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane3)
                                    .addComponent(cmbGadget, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(cmbRepairStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtRepairId)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtSelectedCustomerId, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCustomerSearch, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtSelectedCustomerName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(btnSearchCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE))))
                        .addGap(40, 40, 40))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtSelectedContact, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 795, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtRepairId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtCustomerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSearchCustomer))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(txtSelectedCustomerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(txtSelectedCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSelectedContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(cmbGadget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(81, 81, 81)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbRepairTechnician, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(cmbRepairStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cldrDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(101, 101, 101))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCreate)
                            .addComponent(btnSave)
                            .addComponent(btnEdit))
                        .addGap(68, 68, 68))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1516, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 709, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed

        String customerIdText =
            txtSelectedCustomerId.getText().trim();

        if (customerIdText.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Please search and select a customer first."
            );

            return;
        }

        if (cmbGadget.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(
                this,
                "Please select a gadget."
            );

            return;
        }

        if (cmbRepairTechnician.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(
                this,
                "Please select a technician."
            );

            return;
        }

        if (cmbRepairStatus.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(
                this,
                "Please select a repair status."
            );

            return;
        }

        if (cldrDate.getDate() == null) {

            JOptionPane.showMessageDialog(
                this,
                "Please select the date brought."
            );

            return;
        }

        try {

            int customerId =
                Integer.parseInt(customerIdText);

            int gadgetId =
                Integer.parseInt(
                    cmbGadget
                        .getSelectedItem()
                        .toString()
                        .split(" - ")[0]
                );

            int technicianId =
                Integer.parseInt(
                    cmbRepairTechnician
                        .getSelectedItem()
                        .toString()
                        .split(" - ")[0]
                );

            String status =
                cmbRepairStatus
                    .getSelectedItem()
                    .toString();

            String problem =
                txtProblem.getText().trim();

            if (problem.isEmpty()) {

                JOptionPane.showMessageDialog(
                    this,
                    "Please select a gadget with a recorded problem."
                );

                return;
            }

            String dateBrought =
                new java.text.SimpleDateFormat(
                    "yyyy-MM-dd"
                ).format(cldrDate.getDate());

            // Check if gadget belongs to selected customer
            String checkSql =
                "SELECT gadget_id " +
                "FROM gadgets " +
                "WHERE gadget_id = ? " +
                "AND customer_id = ? " +
                "AND is_active = 1";

            try (
                Connection conn =
                    DatabaseConnection.getConnection();

                PreparedStatement checkPst =
                    conn.prepareStatement(checkSql)
            ) {

                checkPst.setInt(1, gadgetId);
                checkPst.setInt(2, customerId);

                try (ResultSet rs =
                    checkPst.executeQuery()) {

                    if (!rs.next()) {

                        JOptionPane.showMessageDialog(
                            this,
                            "The selected gadget does not belong " +
                            "to the selected customer."
                        );

                        return;
                    }
                }

                String sql =
                    "INSERT INTO repairs " +
                    "(gadget_id, technician_id, " +
                    "repair_description, repair_status, " +
                    "date_brought, is_archived) " +
                    "VALUES (?, ?, ?, ?, ?, 0)";

                try (
                    PreparedStatement pst =
                        conn.prepareStatement(sql)
                ) {

                    pst.setInt(1, gadgetId);
                    pst.setInt(2, technicianId);
                    pst.setString(3, problem);
                    pst.setString(4, status);
                    pst.setString(5, dateBrought);

                    pst.executeUpdate();
                }
            }

            JOptionPane.showMessageDialog(
                this,
                "Repair created successfully!"
            );

            refreshRepairTable();

            clearRepairFields();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "Invalid Customer, Gadget, or Technician ID."
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                this,
                "Error creating repair:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    // TODO add your handling code here:
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
  

        int row =
            tblRepairs.getSelectedRow();

        if (row < 0) {

            JOptionPane.showMessageDialog(
                this,
                "Please select a repair to edit."
            );

            return;
        }

        int repairId =
            Integer.parseInt(
                tblRepairs
                    .getValueAt(row, 0)
                    .toString()
            );

        String sql =
            "SELECT r.repair_id, " +
            "r.gadget_id, " +
            "r.technician_id, " +
            "r.repair_description, " +
            "r.repair_status, " +
            "r.date_brought, " +
            "g.customer_id, " +
            "c.first_name, " +
            "c.last_name, " +
            "c.contact_number " +
            "FROM repairs r " +
            "JOIN gadgets g " +
            "ON r.gadget_id = g.gadget_id " +
            "JOIN customers c " +
            "ON g.customer_id = c.customer_id " +
            "WHERE r.repair_id = ? " +
            "AND r.is_archived = 0";

        try (
            Connection conn =
                DatabaseConnection.getConnection();

            PreparedStatement pst =
                conn.prepareStatement(sql)
        ) {

            pst.setInt(1, repairId);

            try (ResultSet rs =
                pst.executeQuery()) {

                if (!rs.next()) {

                    JOptionPane.showMessageDialog(
                        this,
                        "Repair not found."
                    );

                    return;
                }

                txtRepairId.setText(
                    String.valueOf(
                        rs.getInt("repair_id")
                    )
                );

                int customerId =
                    rs.getInt("customer_id");

                txtSelectedCustomerId.setText(
                    String.valueOf(customerId)
                );

                txtSelectedCustomerName.setText(
                    rs.getString("first_name")
                    + " "
                    + rs.getString("last_name")
                );

                txtSelectedContact.setText(
                    rs.getString("contact_number")
                );

                // Load customer's gadgets
                loadGadgetsByCustomer();

                int gadgetId =
                    rs.getInt("gadget_id");

                for (
                    int i = 0;
                    i < cmbGadget.getItemCount();
                    i++
                ) {

                    String item =
                        cmbGadget.getItemAt(i);

                    if (
                        item.startsWith(
                            gadgetId + " - "
                        )
                    ) {

                        cmbGadget.setSelectedIndex(i);
                        break;
                    }
                }

                int technicianId =
                    rs.getInt("technician_id");

                for (
                    int i = 0;
                    i < cmbRepairTechnician.getItemCount();
                    i++
                ) {

                    String item =
                        cmbRepairTechnician
                            .getItemAt(i);

                    if (
                        item.startsWith(
                            technicianId + " - "
                        )
                    ) {

                        cmbRepairTechnician
                            .setSelectedIndex(i);

                        break;
                    }
                }

                cmbRepairStatus.setSelectedItem(
                    rs.getString("repair_status")
                );

                cldrDate.setDate(
                    rs.getDate("date_brought")
                );

                loadProblemFromSelectedGadget();
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                this,
                "Error loading repair:\n"
                + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    
   // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

   String repairIdText =
            txtRepairId.getText().trim();

        String customerIdText =
            txtSelectedCustomerId.getText().trim();

        if (repairIdText.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Please click EDIT first."
            );

            return;
        }

        if (customerIdText.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Please select a customer first."
            );

            return;
        }

        if (cmbGadget.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(
                this,
                "Please select a gadget."
            );

            return;
        }

        if (cmbRepairTechnician.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(
                this,
                "Please select a technician."
            );

            return;
        }

        if (cmbRepairStatus.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(
                this,
                "Please select a repair status."
            );

            return;
        }

        if (cldrDate.getDate() == null) {

            JOptionPane.showMessageDialog(
                this,
                "Please select the date brought."
            );

            return;
        }

        try {

            int repairId =
                Integer.parseInt(repairIdText);

            int customerId =
                Integer.parseInt(customerIdText);

            int gadgetId =
                Integer.parseInt(
                    cmbGadget
                        .getSelectedItem()
                        .toString()
                        .split(" - ")[0]
                );

            int technicianId =
                Integer.parseInt(
                    cmbRepairTechnician
                        .getSelectedItem()
                        .toString()
                        .split(" - ")[0]
                );

            String status =
                cmbRepairStatus
                    .getSelectedItem()
                    .toString();

            String problem =
                txtProblem.getText().trim();

            if (problem.isEmpty()) {

                JOptionPane.showMessageDialog(
                    this,
                    "Please select a gadget with a recorded problem."
                );

                return;
            }

            String dateBrought =
                new java.text.SimpleDateFormat(
                    "yyyy-MM-dd"
                ).format(cldrDate.getDate());

            String checkSql =
                "SELECT gadget_id " +
                "FROM gadgets " +
                "WHERE gadget_id = ? " +
                "AND customer_id = ? " +
                "AND is_active = 1";

            try (
                Connection conn =
                    DatabaseConnection.getConnection();

                PreparedStatement checkPst =
                    conn.prepareStatement(checkSql)
            ) {

                checkPst.setInt(1, gadgetId);
                checkPst.setInt(2, customerId);

                try (ResultSet rs =
                    checkPst.executeQuery()) {

                    if (!rs.next()) {

                        JOptionPane.showMessageDialog(
                            this,
                            "The selected gadget does not belong " +
                            "to the selected customer."
                        );

                        return;
                    }
                }

                String sql =
                    "UPDATE repairs SET " +
                    "gadget_id = ?, " +
                    "technician_id = ?, " +
                    "repair_description = ?, " +
                    "repair_status = ?, " +
                    "date_brought = ? " +
                    "WHERE repair_id = ? " +
                    "AND is_archived = 0";

                try (
                    PreparedStatement pst =
                        conn.prepareStatement(sql)
                ) {

                    pst.setInt(1, gadgetId);
                    pst.setInt(2, technicianId);
                    pst.setString(3, problem);
                    pst.setString(4, status);
                    pst.setString(5, dateBrought);
                    pst.setInt(6, repairId);

                    int updated =
                        pst.executeUpdate();

                    if (updated == 0) {

                        JOptionPane.showMessageDialog(
                            this,
                            "Repair was not found."
                        );

                        return;
                    }
                }
            }

            JOptionPane.showMessageDialog(
                this,
                "Repair updated successfully!"
            );

            refreshRepairTable();

            clearRepairFields();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "Invalid ID."
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                this,
                "Error updating repair:\n"
                + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
      // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
  String search =
            txtSearchRepair.getText().trim();

        if (search.isEmpty()) {

            refreshRepairTable();
            return;
        }

        DefaultTableModel model =
            (DefaultTableModel) tblRepairs.getModel();

        model.setRowCount(0);

        String sql =
            "SELECT r.repair_id, " +
            "CONCAT(c.first_name, ' ', c.last_name) " +
            "AS customer_name, " +
            "CONCAT(g.brand_model, ' - ', g.gadget_type) " +
            "AS gadget_name, " +
            "r.repair_description, " +
            "a.username AS technician_name, " +
            "r.repair_status, " +
            "r.date_brought " +
            "FROM repairs r " +
            "JOIN gadgets g " +
            "ON r.gadget_id = g.gadget_id " +
            "JOIN customers c " +
            "ON g.customer_id = c.customer_id " +
            "LEFT JOIN technicians t " +
            "ON r.technician_id = t.technician_id " +
            "LEFT JOIN accounts a " +
            "ON t.account_id = a.account_id " +
            "WHERE r.is_archived = 0 " +
            "AND (" +
            "CAST(r.repair_id AS CHAR) LIKE ? " +
            "OR CONCAT(c.first_name, ' ', c.last_name) LIKE ? " +
            "OR g.brand_model LIKE ? " +
            "OR g.gadget_type LIKE ? " +
            "OR r.repair_description LIKE ?" +
            ") " +
            "ORDER BY r.repair_id";

        try (
            Connection conn =
                DatabaseConnection.getConnection();

            PreparedStatement pst =
                conn.prepareStatement(sql)
        ) {

            String keyword =
                "%" + search + "%";

            for (int i = 1; i <= 5; i++) {
                pst.setString(i, keyword);
            }

            try (ResultSet rs =
                pst.executeQuery()) {

                boolean found = false;

                while (rs.next()) {

                    found = true;

                    model.addRow(
                        new Object[]{
                            rs.getInt("repair_id"),
                            rs.getString("customer_name"),
                            rs.getString("gadget_name"),
                            rs.getString("repair_description"),
                            rs.getString("technician_name"),
                            rs.getString("repair_status"),
                            rs.getDate("date_brought")
                        }
                    );
                }

                if (!found) {

                    JOptionPane.showMessageDialog(
                        this,
                        "No matching repair found."
                    );

                    refreshRepairTable();
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                this,
                "Error searching repairs:\n"
                + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

      // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
   
        if ("admin".equalsIgnoreCase(userRole)) {

            new AdminDashboardFrame(username).setVisible(true);

        } else {

            new DashboardFrame(username).setVisible(true);
        }

        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackActionPerformed

    private void cmbRepairTechnicianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRepairTechnicianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbRepairTechnicianActionPerformed

    private void txtSearchRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchRepairActionPerformed

    private void cmbGadgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbGadgetActionPerformed
         loadProblemFromSelectedGadget();


       // TODO add your handling code here:
    }//GEN-LAST:event_cmbGadgetActionPerformed

    private void cmbRepairStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRepairStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbRepairStatusActionPerformed

    private void btnRepairsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepairsActionPerformed
new CompletedRepairFrame().setVisible(true);
this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnRepairsActionPerformed

    private void txtRepairIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRepairIdActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_txtRepairIdActionPerformed

    private void btnSearchCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCustomerActionPerformed

        String search =
            txtCustomerSearch.getText().trim();

        DefaultTableModel model =
            (DefaultTableModel) tblCustomerResults.getModel();

        model.setRowCount(0);

        if (search.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Please enter a Customer ID, name, or contact number."
            );

            return;
        }

        String sql =
            "SELECT customer_id, first_name, " +
            "last_name, contact_number " +
            "FROM customers " +
            "WHERE is_active = 1 " +
            "AND (" +
            "CAST(customer_id AS CHAR) LIKE ? " +
            "OR first_name LIKE ? " +
            "OR last_name LIKE ? " +
            "OR contact_number LIKE ?" +
            ") " +
            "ORDER BY customer_id";

        try (
            Connection conn =
                DatabaseConnection.getConnection();

            PreparedStatement pst =
                conn.prepareStatement(sql)
        ) {

            String keyword =
                "%" + search + "%";

            pst.setString(1, keyword);
            pst.setString(2, keyword);
            pst.setString(3, keyword);
            pst.setString(4, keyword);

            try (ResultSet rs =
                pst.executeQuery()) {

                boolean found = false;

                while (rs.next()) {

                    String fullName =
                        rs.getString("first_name")
                        + " "
                        + rs.getString("last_name");

                    model.addRow(
                        new Object[]{
                            rs.getInt("customer_id"),
                            fullName,
                            rs.getString("contact_number")
                        }
                    );

                    found = true;
                }

                if (!found) {

                    JOptionPane.showMessageDialog(
                        this,
                        "Customer not found."
                    );
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                this,
                "Error searching customer:\n"
                + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchCustomerActionPerformed

    private void tblCustomerResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCustomerResultsMouseClicked
        int row = tblCustomerResults.getSelectedRow();

    if (row < 0) {
        return;
    }

    String customerId =
        tblCustomerResults.getValueAt(row, 0).toString();

    String customerName =
        tblCustomerResults.getValueAt(row, 1).toString();

    String contactNumber =
        tblCustomerResults.getValueAt(row, 2).toString();

    txtSelectedCustomerId.setText(customerId);
    txtSelectedCustomerName.setText(customerName);
    txtSelectedContact.setText(contactNumber);

    // Load only gadgets belonging to this customer
    loadGadgetsByCustomer();
    


        // TODO add your handling code here:
    }//GEN-LAST:event_tblCustomerResultsMouseClicked

    private void txtSelectedCustomerIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSelectedCustomerIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSelectedCustomerIdActionPerformed

    private void txtSelectedCustomerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSelectedCustomerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSelectedCustomerNameActionPerformed

    private void txtSelectedContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSelectedContactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSelectedContactActionPerformed

  
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRepairs;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchCustomer;
    private com.toedter.calendar.JDateChooser cldrDate;
    private javax.swing.JComboBox<String> cmbGadget;
    private javax.swing.JComboBox<String> cmbRepairStatus;
    private javax.swing.JComboBox<String> cmbRepairTechnician;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tblCustomerResults;
    private javax.swing.JTable tblRepairs;
    private javax.swing.JTextField txtCustomerSearch;
    private javax.swing.JTextArea txtProblem;
    private javax.swing.JTextField txtRepairId;
    private javax.swing.JTextField txtSearchRepair;
    private javax.swing.JTextField txtSelectedContact;
    private javax.swing.JTextField txtSelectedCustomerId;
    private javax.swing.JTextField txtSelectedCustomerName;
    private javax.swing.JLabel txtTitle;
    // End of variables declaration//GEN-END:variables
}
