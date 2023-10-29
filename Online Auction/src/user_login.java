package auction;

import javax.swing.*;

import java.awt.*;

public class user_login {
    public static void main(String[] args) {
        data_link user = new data_link(); 
        data_link.SignUpGUI signUpGUI = user.new SignUpGUI();
        // data_link.LoginFrame logingui = user.new LoginFrame();
        JFrame frame = new JFrame("Online Auction");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);
        JLabel welcomeLabel = new JLabel("Welcome to Online Auction");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(welcomeLabel, BorderLayout.NORTH);

        JLabel newUserLabel = new JLabel("Are you a new user?");
        newUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(newUserLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpGUI.usersign(); // Call the usersign method when Yes button is clicked
            }
        });

        JButton noButton = new JButton("No");
        noButton.addActionListener(user); // Register ActionListener

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }
}
