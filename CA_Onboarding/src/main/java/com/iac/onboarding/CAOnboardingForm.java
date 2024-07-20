package com.iac.onboarding;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class CAOnboardingForm extends JFrame {

    private final JTextField emailField;
    private final JTextField phoneField;
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField collegeNameField;
    private final JTextField addressField;
    private final JTextField cityField;
    private final JTextField postalCodeField;
    private final JTextField stateField;
    private final JTextField dobField;

    private final JButton photoIdButton;
    private final JButton studentIdButton;
    private final JButton semesterMarksheetButton;

    private final EmailService emailService;
    private final UTMLinkGenerator utmLinkGenerator;

    private final JComboBox<String> educationField;

    public CAOnboardingForm() {
        setTitle("CA Onboarding Form");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        emailService = new EmailService();
        utmLinkGenerator = new UTMLinkGenerator("https://www.industryacademiacommunity.com/");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        emailField = addField(panel, "Email *", "Enter Your valid Email");
        phoneField = addField(panel, "Phone number *", "Enter Your phone number");
        firstNameField = addField(panel, "First name *", "Enter Your first name");
        lastNameField = addField(panel, "Last name *", "Enter Your last name");
        collegeNameField = addField(panel, "College Full Name *", "Enter Your college name");
        addressField = addField(panel, "Residential Address *", "Enter Your address");
        cityField = addField(panel, "City *", "Enter Your city");
        postalCodeField = addField(panel, "Postal code *", "Enter Your postal code");
        stateField = addField(panel, "State/Region *", "Enter Your state or region");
        dobField = addField(panel, "Date of Birth *", "yyyy-mm-dd");

        String[] educationOptions = {"Select", "High School", "Associate Degree", "Bachelor's Degree", "Master's Degree", "Doctorate"};
        educationField = addComboBox(panel, educationOptions);

        photoIdButton = addFileChooser(panel, "Photo ID & Address Proof *");
        studentIdButton = addFileChooser(panel, "Student ID/Bonafide Certificate *");
        semesterMarksheetButton = addFileChooser(panel, "Previous Semester Marksheet *");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton submitButton = new JButton("Submit");
        JButton resetButton = new JButton("Reset");
        JButton logoutButton = new JButton("Logout");

        submitButton.setBackground(Color.GREEN);
        resetButton.setBackground(Color.RED);
        logoutButton.setBackground(Color.LIGHT_GRAY);

        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(logoutButton);

        panel.add(buttonPanel);

        add(mainPanel);

        submitButton.addActionListener(e -> validateAndSubmit());
        resetButton.addActionListener(e -> resetForm(panel));
        logoutButton.addActionListener(e -> logout());

        photoIdButton.addActionListener(e -> chooseFile(photoIdButton));
        studentIdButton.addActionListener(e -> chooseFile(studentIdButton));
        semesterMarksheetButton.addActionListener(e -> chooseFile(semesterMarksheetButton));
    }

    private JTextField addField(JPanel panel, String labelText, String placeholder) {
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField(20);
        textField.setToolTipText(placeholder);
        label.setLabelFor(textField);

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BorderLayout(5, 5));
        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(textField, BorderLayout.CENTER);

        panel.add(fieldPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        return textField;
    }

    private JComboBox<String> addComboBox(JPanel panel, String[] options) {
        JLabel label = new JLabel("Highest Education *");
        JComboBox<String> comboBox = new JComboBox<>(options);
        label.setLabelFor(comboBox);

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BorderLayout(5, 5));
        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(comboBox, BorderLayout.CENTER);

        panel.add(fieldPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        return comboBox;
    }

    private JButton addFileChooser(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        JButton fileButton = new JButton("Choose File");

        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BorderLayout(5, 5));
        filePanel.add(label, BorderLayout.NORTH);
        filePanel.add(fileButton, BorderLayout.CENTER);

        panel.add(filePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        return fileButton;
    }

    private void chooseFile(JButton button) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            button.setText(fileChooser.getSelectedFile().getName());
        }
    }

    private void resetForm(JPanel panel) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel fieldPanel) {
                Component[] fieldComponents = fieldPanel.getComponents();
                for (Component fieldComponent : fieldComponents) {
                    if (fieldComponent instanceof JTextField) {
                        ((JTextField) fieldComponent).setText("");
                    } else if (fieldComponent instanceof JComboBox) {
                        ((JComboBox<?>) fieldComponent).setSelectedIndex(0);
                    } else if (fieldComponent instanceof JButton) {
                        ((JButton) fieldComponent).setText("Choose File");
                    }
                }
            }
        }
    }

    private void validateAndSubmit() {
        String email = emailField.getText();
        String phone = phoneField.getText();

        if (email.isEmpty() || phone.isEmpty()) {
            showError("Email and Phone number are required.");
            return;
        }

        if (!isValidEmail(email)) {
            showError("Invalid email format.");
            return;
        }

        if (!isValidPhone(phone)) {
            showError("Invalid phone number.");
            return;
        }

        String utmLink = utmLinkGenerator
                .setSource("newsletter")
                .setMedium("email")
                .setCampaign("onboarding")
                .generateLink();

        saveUserData(email, phone, firstNameField.getText(), lastNameField.getText(),
                collegeNameField.getText(), addressField.getText(), cityField.getText(),
                postalCodeField.getText(), stateField.getText(), dobField.getText(),
                (String) educationField.getSelectedItem(),
                photoIdButton.getText(), studentIdButton.getText(), semesterMarksheetButton.getText(),
                utmLink);

        String subject = "Welcome to the CA Program";
        String body = "Dear " + firstNameField.getText() + ",\n\nThank you for joining the CA program. \nPlease Follow this link:\n\n" + utmLink;
        emailService.sendEmail(email, subject, body, utmLinkGenerator);

        JOptionPane.showMessageDialog(null, "Form submitted successfully. A confirmation email has been sent.", "Success", JOptionPane.INFORMATION_MESSAGE);

        resetForm((JPanel) getContentPane().getComponent(0).getComponentAt(0, 0));
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    private void saveUserData(String email, String phone, String firstName, String lastName, String collegeName,
                              String address, String city, String postalCode, String state, String dob,
                              String education, String photoIdPath, String studentIdPath,
                              String semesterMarksheetPath, String utmLink) {

        String insertSQL = "INSERT INTO users (email, phone, first_name, last_name, college_name, address, city, postal_code, state_region, dob, highest_education, photo_id_path, student_id_path, semester_marksheet_path, utm_link) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, email);
            pstmt.setString(2, phone);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setString(5, collegeName);
            pstmt.setString(6, address);
            pstmt.setString(7, city);
            pstmt.setString(8, postalCode);
            pstmt.setString(9, state);
            pstmt.setString(10, dob);
            pstmt.setString(11, education);
            pstmt.setString(12, photoIdPath);
            pstmt.setString(13, studentIdPath);
            pstmt.setString(14, semesterMarksheetPath);
            pstmt.setString(15, utmLink);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        dispose(); // Close the current form
        new Main().setVisible(true); // Show the login form
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CAOnboardingForm().setVisible(true));
    }
}
