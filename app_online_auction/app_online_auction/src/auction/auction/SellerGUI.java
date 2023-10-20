package auction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SellerGUI {
    private JTextField userIdField;
    private JTextField itemNameField;
    private JTextField itemDescField;
    private JTextField baseBidField;
    private JTextField bidTimeField;

    public SellerGUI() {
        JFrame frame = new JFrame("Auction Seller");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        userIdField = new JTextField(20);
        itemNameField = new JTextField(20);
        itemDescField = new JTextField(20);
        baseBidField = new JTextField(20);
        bidTimeField = new JTextField(20);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/seller_details", "root", "Gopal@2005");
                    String query = "INSERT INTO new_table (user_id, item_name, item_des, base_price, time_period) VALUES (?, ?, ?, ?, ?)";

                    PreparedStatement pstm = con.prepareStatement(query);

                    pstm.setString(1, userIdField.getText());
                    pstm.setString(2, itemNameField.getText());
                    pstm.setString(3, itemDescField.getText());
                    pstm.setInt(4, Integer.parseInt(baseBidField.getText()));
                    pstm.setLong(5, Long.parseLong(bidTimeField.getText()));

                    pstm.executeUpdate();
                    pstm.close();
                    con.close();

                    JOptionPane.showMessageDialog(null, "The user has been uploaded to the database.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while uploading user to the database.");
                }
            }
        });

        panel.add(new JLabel("Enter the user ID: "));
        panel.add(userIdField);
        panel.add(new JLabel("Enter the item name: "));
        panel.add(itemNameField);
        panel.add(new JLabel("Enter item description: "));
        panel.add(itemDescField);
        panel.add(new JLabel("Enter the base bid amount: "));
        panel.add(baseBidField);
        panel.add(new JLabel("Enter the time period of the bid (in days): "));
        panel.add(bidTimeField);
        panel.add(submitButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SellerGUI();
            }
        });
    }
}
