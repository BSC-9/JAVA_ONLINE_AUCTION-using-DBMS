package auction;
import java.sql.*;
import java.util.Scanner;
import java.*;

public class data_link {
    private Connection con;
    private Scanner input;

    public data_link() {
        String url = "jdbc:mysql://localhost:3306/app_users";
        String username = "root";
        String password = "Gopal@2005";
        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("The connection is established!!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1); // Exit the program on connection error
        }
        input = new Scanner(System.in); // Initialize the Scanner
    }
    
    public void sign_up() {
        try {
            System.out.println("Enter a unique user_id: ");
            String id = input.nextLine();

            System.out.println("Enter your name: ");
            String name = input.nextLine();

            System.out.println("Enter the password: ");
            String userPassword = input.nextLine();

            System.out.println("Enter your mobile number: ");
            int mobile = Integer.parseInt(input.nextLine());

            System.out.println("Enter your address: ");
            String address = input.nextLine();

            System.out.println("Enter your email: ");
            String email = input.nextLine();

            System.out.println("What type of user: buyer or seller? ");
            String type = input.nextLine().toLowerCase();

            String user_type;

            if (type.equals("buyer") || type.equals("seller")) {
                user_type = type;
            } else {
                System.out.println("Please enter 'buyer' or 'seller'");
                return; // Exit the sign-up process if user type is invalid
            }

            String query = "INSERT INTO user_details (user_id, user_name, password, phone_num, address, email, user_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(query);

            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, userPassword);
            pstm.setInt(4, mobile);
            pstm.setString(5, address);
            pstm.setString(6, email);
            pstm.setString(7, user_type);

            int rowsAffected = pstm.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("You are registered successfully!!");
            } else {
                System.out.println("Registration failed. Please try again.");
            }

            pstm.close();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void login() {
        try {
            System.out.println("Enter your user_id: ");
            String id = input.nextLine();

            System.out.println("Enter your password: ");
            String password = input.nextLine();

            String query = "SELECT * FROM user_details WHERE user_id = ? AND password = ?";
            PreparedStatement pstm = con.prepareStatement(query);

            pstm.setString(1, id);
            pstm.setString(2, password);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful!");
                // You can add code here to perform actions after successful login
            } else {
                System.out.println("Invalid user_id or password.");
            }

            rs.close();
            pstm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
