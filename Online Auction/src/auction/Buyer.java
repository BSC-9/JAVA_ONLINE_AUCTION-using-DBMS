package auction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSetMetaData;

public class Buyer {
    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/seller_details", "root", "123456789")) {
            String query = "SELECT item_id, item_name, item_des, base_price, time_period FROM new_table";
            PreparedStatement pstm = con.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int numOfColumns = metaData.getColumnCount();
            System.out.println("ITEMS FOR AUCTION:\n");

            for (int i = 1; i <= numOfColumns; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= numOfColumns; i++) {
                    System.out.print(rs.getObject(i) + "\t");
                }
                System.out.println();
            }

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the item id that you would like to bid for: ");
            String itemId = scanner.nextLine();

            System.out.print("Enter your id: ");
            String buyer_id = scanner.nextLine();

            System.out.print("Enter your name: ");
            String buyer_name = scanner.nextLine();

            System.out.print("Enter your BID AMOUNT: ");
            double bid_amount = Double.parseDouble(scanner.nextLine());

            double base_price = 0;
            rs.beforeFirst(); // Reset the ResultSet cursor
            while (rs.next()) {
                if (rs.getString("item_id").equals(itemId)) {
                    base_price = rs.getDouble("base_price");
                    break;
                }
            }

            if (bid_amount <= base_price) {
                System.out.println("Enter the bid amount more than the base price");
                return;
            }

            double newPrice = getNewPrice(con, itemId, bid_amount);

            storeBuyerDetails(con, Integer.parseInt(itemId), buyer_id, newPrice);

            rs.close();
            pstm.close();
            scanner.close(); // Close the scanner
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error occurred while querying the database.");
        }
    }

    private static double getNewPrice(Connection con, String itemId, double incrementValue) throws SQLException {
        String getPriceQuery = "SELECT base_price FROM new_table WHERE item_id = ?";
        PreparedStatement getPricePstm = con.prepareStatement(getPriceQuery);
        getPricePstm.setString(1, itemId);
        ResultSet priceRs = getPricePstm.executeQuery();
        double basePrice = 0;

        if (priceRs.next()) {
            basePrice = priceRs.getDouble("base_price");
        }

        priceRs.close();
        getPricePstm.close();

        return basePrice + incrementValue;
    }

    private static void storeBuyerDetails(Connection con, int itemId, String buyerId, double newPrice) throws SQLException {
        String updateQuery = "UPDATE new_table SET base_price = ?, buyer_id = ? WHERE item_id = ?";
        PreparedStatement updatePstm = con.prepareStatement(updateQuery);
        updatePstm.setDouble(1, newPrice);
        updatePstm.setString(2, buyerId);
        updatePstm.setInt(3, itemId);
        updatePstm.executeUpdate();
        updatePstm.close();
    }
}
