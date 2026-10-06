/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gadgetrepair.frames.admin;
import gadgetrepair.frames.admin.AdminDashboardFrame;
import gadgetrepair.data.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class AccountManagementFrame extends javax.swing.JFrame {
    private String username;

    private void loadAccounts() {

  
     DefaultTableModel model =
        (DefaultTableModel) tblAccounts.getModel();

    model.setRowCount(0);

    String sql =
        "SELECT account_id, first_name, last_name, username, role " +
        "FROM accounts " +
        "WHERE is_active = 1 " +
        "ORDER BY account_id";

    try (
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery()
    ) {

        while (rs.next()) {

            model.addRow(new Object[] {
                rs.getInt("account_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("username"),
                rs.getString("role")
            });
        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
            this,
            "Error loading accounts:\n" + e.getMessage()
        );
    }
}
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AccountManagementFrame.class.getName());
private int selectedAccountId = -1;
    /**
     * Creates new form AccountManagementFrame
     */
    public AccountManagementFrame(String username) {
            this.username = username;


        initComponents();
            loadAccounts();


   
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtTitle = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtUserId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAccounts = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        chkShowPassword = new javax.swing.JCheckBox();
        cmbRole = new javax.swing.JComboBox<>();
        btnArchivedAccounts = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtSearchAccount = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 255, 51));

        jPanel1.setBackground(new java.awt.Color(18, 52, 86));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 80));

        txtTitle.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        txtTitle.setForeground(new java.awt.Color(242, 242, 242));
        txtTitle.setText("ACCOUNT MANAGEMENT");
        txtTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Downloads\\LOGO REPAIRsss.png")); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(135, 135));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTitle)
                .addGap(256, 256, 256)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
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

        jLabel1.setText("Account ID:");

        txtUserId.setEditable(false);

        jLabel2.setText("Username:");

        jLabel3.setText("Password:");

        jLabel4.setText("Role:");

        btnAdd.setText("ADD ACCOUNT");
        btnAdd.addActionListener(this::btnAddActionPerformed);

        tblAccounts.setBackground(new java.awt.Color(15, 43, 77));
        tblAccounts.setForeground(new java.awt.Color(255, 255, 255));
        tblAccounts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Account ID", "First Name", "Last Name", "Username", "Role"
            }
        ));
        jScrollPane1.setViewportView(tblAccounts);

        btnEdit.setBackground(new java.awt.Color(24, 91, 160));
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("EDIT");
        btnEdit.addActionListener(this::btnEditActionPerformed);

        btnSave.setBackground(new java.awt.Color(24, 91, 160));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("SAVE");
        btnSave.addActionListener(this::btnSaveActionPerformed);

        btnArchive.setBackground(new java.awt.Color(24, 91, 160));
        btnArchive.setForeground(new java.awt.Color(255, 255, 255));
        btnArchive.setText("ARCHIVE");
        btnArchive.addActionListener(this::btnArchiveActionPerformed);

        btnClose.setBackground(new java.awt.Color(24, 91, 160));
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setText("CLOSE");
        btnClose.addActionListener(this::btnCloseActionPerformed);

        chkShowPassword.setText("Show Password");
        chkShowPassword.addActionListener(this::chkShowPasswordActionPerformed);

        cmbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Role", "Staff", "Technician", "Admin", " " }));
        cmbRole.addActionListener(this::cmbRoleActionPerformed);

        btnArchivedAccounts.setBackground(new java.awt.Color(24, 91, 160));
        btnArchivedAccounts.setForeground(new java.awt.Color(255, 255, 255));
        btnArchivedAccounts.setText("ARCHIVED ACCOUNT");
        btnArchivedAccounts.addActionListener(this::btnArchivedAccountsActionPerformed);

        jLabel7.setText("Search:");

        btnSearch.setBackground(new java.awt.Color(24, 91, 160));
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Search");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        jLabel5.setText("First Name:");

        jLabel6.setText("Last Name:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1202, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(164, 164, 164)
                                    .addComponent(btnEdit)
                                    .addGap(42, 42, 42)
                                    .addComponent(btnSave)
                                    .addGap(44, 44, 44)
                                    .addComponent(btnArchive)
                                    .addGap(53, 53, 53)
                                    .addComponent(btnClose))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtSearchAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnSearch)
                                    .addGap(2, 2, 2)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(36, 36, 36))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel1)
                                                    .addComponent(jLabel2))
                                                .addGap(27, 27, 27)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtFirstName)
                                                .addComponent(txtUserId, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(txtLastName, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addComponent(chkShowPassword))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(62, 62, 62)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnAdd)
                                            .addComponent(cmbRole, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 54, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(387, 387, 387)
                        .addComponent(btnArchivedAccounts)))
                .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtSearchAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(jLabel1)
                    .addComponent(txtUserId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkShowPassword)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(34, 34, 34)
                        .addComponent(btnAdd)))
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnSave)
                    .addComponent(btnArchive)
                    .addComponent(btnClose))
                .addGap(31, 31, 31)
                .addComponent(btnArchivedAccounts)
                .addGap(0, 106, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed

    new AdminDashboardFrame(username).setVisible(true);
    this.dispose();      // TODO add your handling code here:
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
   String firstName = txtFirstName.getText().trim();
    String lastName = txtLastName.getText().trim();
    String username = txtUsername.getText().trim();
    String password = new String(txtPassword.getPassword());
    String role = cmbRole.getSelectedItem().toString();

    // Check empty fields
    if (firstName.isEmpty() ||
        lastName.isEmpty() ||
        username.isEmpty() ||
        password.isEmpty() ||
        role.equals("Select Role")) {

        JOptionPane.showMessageDialog(
            this,
            "Please complete all fields."
        );
        return;
    }

    // Only allow letters, spaces, hyphens and apostrophes
    if (!firstName.matches("[a-zA-ZÀ-ÿ' -]+")) {
        JOptionPane.showMessageDialog(
            this,
            "First name must contain letters only."
        );
        return;
    }

    if (!lastName.matches("[a-zA-ZÀ-ÿ' -]+")) {
        JOptionPane.showMessageDialog(
            this,
            "Last name must contain letters only."
        );
        return;
    }

    String sql =
        "INSERT INTO accounts " +
        "(first_name, last_name, username, password, role, is_first_login, is_active) " +
        "VALUES (?, ?, ?, ?, ?, 1, 1)";

    try (
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql)
    ) {

        pst.setString(1, firstName);
        pst.setString(2, lastName);
        pst.setString(3, username);
        pst.setString(4, password);
        pst.setString(5, role);

        pst.executeUpdate();

        JOptionPane.showMessageDialog(
            this,
            "Account created successfully!"
        );

        loadAccounts();

        txtUserId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        cmbRole.setSelectedIndex(0);

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
            this,
            "Error creating account:\n" + e.getMessage()
        );
    }
  // TODO add your handling code here:

    }//GEN-LAST:event_btnAddActionPerformed

    private void chkShowPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowPasswordActionPerformed
if (chkShowPassword.isSelected()) {
    txtPassword.setEchoChar((char) 0);
} else {
    txtPassword.setEchoChar('•');
}
    
    // TODO add your handling code here:
    }//GEN-LAST:event_chkShowPasswordActionPerformed

    private void cmbRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRoleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbRoleActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
 if (selectedAccountId == -1) {
        JOptionPane.showMessageDialog(
            this,
            "Please click EDIT first."
        );
        return;
    }

    String username = txtUsername.getText().trim();
    String password = new String(txtPassword.getPassword()).trim();

    if (username.isEmpty()) {
        JOptionPane.showMessageDialog(
            this,
            "Username cannot be empty."
        );
        return;
    }

    if (cmbRole.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(
            this,
            "Please select a role."
        );
        return;
    }

    String role = cmbRole.getSelectedItem().toString();

    try (Connection conn = DatabaseConnection.getConnection()) {

        String sql;

        PreparedStatement pst;

        // If password is empty, keep the old password
        if (password.isEmpty()) {

            sql = "UPDATE accounts " +
                  "SET username = ?, role = ? " +
                  "WHERE account_id = ?";

            pst = conn.prepareStatement(sql);

            pst.setString(1, username);
            pst.setString(2, role);
            pst.setInt(3, selectedAccountId);

        } else {

            // If password was entered, update it too
            sql = "UPDATE accounts " +
                  "SET username = ?, password = ?, role = ? " +
                  "WHERE account_id = ?";

            pst = conn.prepareStatement(sql);

            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, role);
            pst.setInt(4, selectedAccountId);
        }

        pst.executeUpdate();
        pst.close();

        JOptionPane.showMessageDialog(
            this,
            "Account updated successfully!"
        );

        // Refresh table
        loadAccounts();

        // Clear fields
        txtUserId.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        cmbRole.setSelectedIndex(0);

        // Reset selected account
        selectedAccountId = -1;

    } catch (SQLException e) {

        if (e.getMessage().contains("Duplicate")) {

            JOptionPane.showMessageDialog(
                this,
                "Username already exists."
            );

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Error updating account:\n" + e.getMessage()
            );
        }
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed

 int row = tblAccounts.getSelectedRow();

    if (row < 0) {
        JOptionPane.showMessageDialog(
            this,
            "Please select an account to edit."
        );
        return;
    }

    // Get account_id from the selected row
    selectedAccountId = Integer.parseInt(
        tblAccounts.getValueAt(row, 0).toString()
    );

    // Show the selected account in the fields
 txtUserId.setText(
    tblAccounts.getValueAt(row, 0).toString()
);

txtFirstName.setText(
    tblAccounts.getValueAt(row, 1).toString()
);

txtLastName.setText(
    tblAccounts.getValueAt(row, 2).toString()
);

txtUsername.setText(
    tblAccounts.getValueAt(row, 3).toString()
);

cmbRole.setSelectedItem(
    tblAccounts.getValueAt(row, 4).toString()
);

    // Leave password empty
    txtPassword.setText("");

    JOptionPane.showMessageDialog(
        this,
        "Account selected. You can now edit it."
    );

    // TODO add your handling code here:

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
 
    int row = tblAccounts.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(
            this,
            "Please select an account first."
        );
        return;
    }

    int accountId = Integer.parseInt(
        tblAccounts.getValueAt(row, 0).toString()
    );

    String username = tblAccounts.getValueAt(row, 1).toString();

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to archive account '" + username + "'?",
        "Archive Account",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    String sql =
        "UPDATE accounts SET is_active = 0 " +
        "WHERE account_id = ?";

    try (
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql)
    ) {

        pst.setInt(1, accountId);

        pst.executeUpdate();

        JOptionPane.showMessageDialog(
            this,
            "Account archived successfully!"
        );

        // Refresh active accounts
        loadAccounts();

        // Clear fields
        txtUserId.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        cmbRole.setSelectedIndex(0);

        // Reset selected account
        selectedAccountId = -1;

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
            this,
            "Error archiving account:\n" + e.getMessage()
        );
    }


        // TODO add your handling code here:
    }//GEN-LAST:event_btnArchiveActionPerformed

    private void btnArchivedAccountsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchivedAccountsActionPerformed
    new ArchivedAccountsFrame(username).setVisible(true);
    this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnArchivedAccountsActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
    String search = txtSearchAccount.getText().trim();
    if (search.isEmpty()) {
         loadAccounts();
        return;
    }

    DefaultTableModel model =
        (DefaultTableModel) tblAccounts.getModel();

    model.setRowCount(0);

    String sql =
        "SELECT account_id, first_name, last_name, username, role " +
        "FROM accounts " +
        "WHERE is_active = 1 " +
        "AND (" +
        "CAST(account_id AS CHAR) LIKE ? OR " +
        "first_name LIKE ? OR " +
        "last_name LIKE ? OR " +
        "username LIKE ? OR " +
        "role LIKE ?" +
        ") " +
        "ORDER BY account_id";

    try (
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql)
    ) {

        String value = "%" + search + "%";

        pst.setString(1, value);
        pst.setString(2, value);
        pst.setString(3, value);
        pst.setString(4, value);
        pst.setString(5, value);

        try (ResultSet rs = pst.executeQuery()) {

            boolean found = false;

            while (rs.next()) {

                found = true;

                model.addRow(new Object[] {
                    rs.getInt("account_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getString("role")
                });
            }

            if (!found) {

                JOptionPane.showMessageDialog(
                    this,
                    "No matching account found."
                );

                loadAccounts ();
            }
        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
            this,
            "Error searching accounts:\n" + e.getMessage()
        );
    }
     // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    
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
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnArchive;
    private javax.swing.JButton btnArchivedAccounts;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JCheckBox chkShowPassword;
    private javax.swing.JComboBox<String> cmbRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAccounts;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtSearchAccount;
    private javax.swing.JLabel txtTitle;
    private javax.swing.JTextField txtUserId;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
