package auction;

import javax.swing.*;
import java.sql.*;

public class BuyerGUI {
    private JFrame frame;
    private JTextArea textArea;

    public BuyerGUI() {
        frame = new JFrame("Auction Items");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        textArea = new JTextArea();
        textArea.setEditable(false); // Make it non-editable

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/seller_details", "root", "123456789");
            String query = "SELECT user_id, item_name, item_des, base_price, time_period FROM new_table";
            PreparedStatement pstm = con.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int Num_of_columns = metaData.getColumnCount();

            StringBuilder output = new StringBuilder();
            output.append("ITEMS FOR AUCTION:\n");

            for (int i = 1; i <= Num_of_columns; i++) {
                output.append(metaData.getColumnName(i)).append("\t");
            }
            output.append("\n");

            while (rs.next()) {
                for (int i = 1; i <= Num_of_columns; i++) {
                    output.append(rs.getObject(i)).append("\t");
                }
                output.append("\n");
            }

            textArea.setText(output.toString());

            rs.close();
            pstm.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            textArea.setText("Error occurred while querying the database.");
        }
    }

    public void display() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BuyerGUI gui = new BuyerGUI();
            gui.display();
        });
    }
}
