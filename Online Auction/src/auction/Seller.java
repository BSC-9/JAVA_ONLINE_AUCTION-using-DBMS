package auction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Seller {
    public static void insertData(String userId, String itemName, String itemDesc, int baseBid, long bidTime) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/seller_details", "root", "123456789");
            String query = "INSERT INTO new_table (user_id, item_name, item_des, base_price, time_period) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstm = con.prepareStatement(query);

            pstm.setString(1, userId);
            pstm.setString(2, itemName);
            pstm.setString(3, itemDesc);
            pstm.setInt(4, baseBid);
            pstm.setLong(5, bidTime);

            pstm.executeUpdate();
            pstm.close();
            con.close();

            System.out.println("The user has been uploaded to the database.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error occurred while uploading user to the database.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();

        System.out.print("Enter item description: ");
        String itemDesc = scanner.nextLine();

        System.out.print("Enter base bid amount: ");
        int baseBid = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter bid time (in days): ");
        long bidTime = Long.parseLong(scanner.nextLine());

        insertData(userId, itemName, itemDesc, baseBid, bidTime);
    }
}
