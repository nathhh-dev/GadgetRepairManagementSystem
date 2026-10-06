/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gadgetrepair.classes;

import gadgetrepair.data.DatabaseConnection;
import gadgetrepair.frames.Loginframee;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        // Set Nimbus Look and Feel
      try {
    for (javax.swing.UIManager.LookAndFeelInfo info :
            javax.swing.UIManager.getInstalledLookAndFeels()) {

        if ("Nimbus".equals(info.getName())) {
            javax.swing.UIManager.setLookAndFeel(info.getClassName());
            break;
        }
    }

} catch (Exception ex) {
    ex.printStackTrace();
}

        // Start the Login Frame
        java.awt.EventQueue.invokeLater(() -> {
            new Loginframee().setVisible(true);
        });
    }
}