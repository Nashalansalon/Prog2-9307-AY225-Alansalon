import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * Attendance Tracker Application
 * Sleek UI with checkbox-based E-Signature
 */
public class AttendanceTrackerApp {

    public static void main(String[] args) {

        // Create the main frame
        JFrame frame = new JFrame("Attendance Tracker");
        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center window

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels
        JLabel nameLabel = new JLabel("Attendance Name:");
        JLabel courseLabel = new JLabel("Course / Year:");
        JLabel timeLabel = new JLabel("Time In:");
        JLabel signatureLabel = new JLabel("E-Signature:");

        // Sleek text fields
        JTextField nameField = new JTextField();
        JTextField courseField = new JTextField();
        JTextField timeField = new JTextField();

        nameField.setPreferredSize(new Dimension(200, 28));
        courseField.setPreferredSize(new Dimension(200, 28));
        timeField.setPreferredSize(new Dimension(200, 28));
        timeField.setEditable(false);

        // Get system date and time (formatted)
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMMM dd, yyyy  |  hh:mm a");
        String timeIn = LocalDateTime.now().format(formatter);
        timeField.setText(timeIn);

        // E-signature checkbox
        JCheckBox signatureCheckBox = new JCheckBox("I confirm this is my name");
        signatureCheckBox.setEnabled(false);

        // Update checkbox text when name is typed
        nameField.addCaretListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                signatureCheckBox.setText("E-Signature: " + name);
                signatureCheckBox.setEnabled(true);
            } else {
                signatureCheckBox.setText("E-Signature");
                signatureCheckBox.setEnabled(false);
            }
        });

        // Submit button
        JButton submitButton = new JButton("Submit Attendance");

        submitButton.addActionListener(e -> {
            if (nameField.getText().isEmpty() ||
                courseField.getText().isEmpty() ||
                !signatureCheckBox.isSelected()) {

                JOptionPane.showMessageDialog(frame,
                        "Please complete all fields and confirm E-Signature.",
                        "Incomplete Form",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Attendance Submitted Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Layout positioning
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(courseLabel, gbc);

        gbc.gridx = 1;
        panel.add(courseField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(timeLabel, gbc);

        gbc.gridx = 1;
        panel.add(timeField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(signatureLabel, gbc);

        gbc.gridx = 1;
        panel.add(signatureCheckBox, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(submitButton, gbc);

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }
}