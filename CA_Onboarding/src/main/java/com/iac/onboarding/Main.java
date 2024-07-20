package com.iac.onboarding;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Main {

    // JDBC connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/onboarding";
    private static final String JDBC_USER = "patil";
    private static final String JDBC_PASSWORD = "girishpatil123";

    private static JFrame mainFrame; // Declare main frame as a class variable

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::showLoginAndRegistration);
    }


    private static void showLoginAndRegistration() {
        mainFrame = new JFrame("Login and Registration");
        mainFrame.setSize(360, 640); // Mobile screen size adjusted to 5.5 inches
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null); // Custom layout for absolute positioning

        JButton loginButton = createStyledButton("Login");
        JButton registerButton = createStyledButton("Register");

        loginButton.setBounds(50, 250, 260, 80); // Positioning login button
        registerButton.setBounds(50, 360, 260, 80); // Positioning register button

        // Add action listener to loginButton
        loginButton.addActionListener(e -> {
            openLoginForm(); // Method to open login form
            mainFrame.setVisible(false); // Hide main frame
        });

        // Add action listener to registerButton
        registerButton.addActionListener(e -> {
            openRegistrationForm(); // Method to open registration form
            mainFrame.setVisible(false); // Hide main frame
        });

        // Add buttons to panel
        panel.add(loginButton);
        panel.add(registerButton);

        // Set background color for the panel
        panel.setBackground(new Color(41, 128, 185)); // Dark Blue

        // Add panel to main frame
        mainFrame.add(panel);
        mainFrame.setVisible(true);
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(39, 174, 96)); // Green
        button.setFocusPainted(false); // Remove focus border
        button.setBorderPainted(false); // Remove border
        button.setOpaque(true); // Ensure button is opaque for background color to show
        return button;
    }

    private static void openLoginForm() {
        JFrame loginFrame = new JFrame("Login Form");
        loginFrame.setSize(360, 480); // Adjust size for login form
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null); // Custom layout for absolute positioning

        // Add login form components (example: username and password fields, login button)
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 30); // Example positioning for username label
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.BLACK);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 50, 160, 30); // Example positioning for username field

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 100, 30); // Example positioning for password label
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.BLACK);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 160, 30); // Example positioning for password field

        JButton loginButton = createStyledButton("Login");
        loginButton.setBounds(120, 160, 120, 40); // Example positioning for login button

        JButton backButton = createStyledButton("Back"); // Back button added
        backButton.setBounds(10, 10, 100, 30); // Example positioning for back button

        // Add action listener to backButton
        backButton.addActionListener(e -> {
            mainFrame.setVisible(true); // Show main frame
            loginFrame.dispose(); // Close login frame
        });

        // Add action listener to loginButton (perform login functionality)
        loginButton.addActionListener(e -> {
            if (validateLogin(usernameField.getText(), new String(passwordField.getPassword()))) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                openCAOnboardingForm(); // Redirect to CA Onboarding form
                loginFrame.dispose(); // Close login frame
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username or password. Please try again.");
            }
        });

        // Add components to panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(backButton); // Add back button to panel

        // Set background color for the panel
        panel.setBackground(new Color(236, 240, 241)); // Light Gray

        // Add panel to login frame
        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }

    private static void openRegistrationForm() {
        JFrame registrationFrame = new JFrame("Registration Form");
        registrationFrame.setSize(360, 480); // Adjust size for registration form
        registrationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registrationFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null); // Custom layout for absolute positioning

        // Add registration form components (example: fields for username, email, password, register button)
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 30); // Example positioning for username label
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.BLACK);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 50, 160, 30); // Example positioning for username field

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 100, 100, 30); // Example positioning for email label
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setForeground(Color.BLACK);

        JTextField emailField = new JTextField();
        emailField.setBounds(150, 100, 160, 30); // Example positioning for email field

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 100, 30); // Example positioning for password label
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.BLACK);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 150, 160, 30); // Example positioning for password field

        JButton registerButton = createStyledButton("Register");
        registerButton.setBounds(120, 210, 120, 40); // Example positioning for register button

        JButton backButton = createStyledButton("Back"); // Back button added
        backButton.setBounds(10, 10, 100, 30); // Example positioning for back button

        // Add action listener to backButton
        backButton.addActionListener(e -> {
            mainFrame.setVisible(true); // Show main frame
            registrationFrame.dispose(); // Close registration frame
        });

        // Add action listener to registerButton (perform registration functionality)
        registerButton.addActionListener(e -> {
            if (registerUser(usernameField.getText(), emailField.getText(), new String(passwordField.getPassword()))) {
                JOptionPane.showMessageDialog(registrationFrame, "Registration successful!");
                openCAOnboardingForm(); // Redirect to CA Onboarding form
                registrationFrame.dispose(); // Close registration frame
            } else {
                JOptionPane.showMessageDialog(registrationFrame, "Registration failed. Please try again.");
            }
        });

        // Add components to panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);
        panel.add(backButton); // Add back button to panel

        // Set background color for the panel
        panel.setBackground(new Color(236, 240, 241)); // Light Gray

        // Add panel to registration frame
        registrationFrame.add(panel);
        registrationFrame.setVisible(true);
    }

    private static void openCAOnboardingForm() {
        CAOnboardingForm form = new CAOnboardingForm();
        form.setVisible(true);
    }

    private static boolean registerUser(String username, String email, String password) {
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            //e. printStackTrace();
            return false;
        }
    }

    private static boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // True if user exists with given username and password
        } catch (SQLException e) {
            //e.printStackTrace();
            return false;
        }
    }

    public void setVisible(boolean isVisible) {
        super.notifyAll(); // Call the JFrame setVisible method
    }
}
