
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File; 

public class StudentRecordSystem extends JFrame {

    DefaultTableModel model;
    JTable table;
    JTextField txtStudentID, txtFirstName, txtLastName;
    JLabel lblStatus; 

    public StudentRecordSystem() {
        this.setTitle("Records - Nathaniel Alansalon 2023-XXXX");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"StudentID","first_name","last_name","LAB WORK 1","LAB WORK 2","LAB WORK 3","PRELIM EXAM","ATTENDANCE GRADE"}, 0);
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        add(new JScrollPane(table), BorderLayout.CENTER); 

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 4));
        txtStudentID = new JTextField();
        txtFirstName = new JTextField();
        txtLastName = new JTextField();

        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");

        inputPanel.add(new JLabel("StudentID"));
        inputPanel.add(new JLabel("First Name"));
        inputPanel.add(new JLabel("Last Name"));
        inputPanel.add(new JLabel(""));

        inputPanel.add(txtStudentID);
        inputPanel.add(txtFirstName);
        inputPanel.add(txtLastName);
        inputPanel.add(btnAdd);

        add(inputPanel, BorderLayout.SOUTH);

        // Top panel with Delete button and status label
        lblStatus = new JLabel("");
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(btnDelete, BorderLayout.WEST);
        topPanel.add(lblStatus, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        loadCSV();

        // ADD
        btnAdd.addActionListener(e -> {
            int cols = model.getColumnCount();
            Object[] row = new Object[cols];
            row[0] = txtStudentID.getText();
            row[1] = txtFirstName.getText();
            row[2] = txtLastName.getText();
            for (int i = 3; i < cols; i++) row[i] = "";
            model.addRow(row);
            txtStudentID.setText("");
            txtFirstName.setText("");
            txtLastName.setText("");
        });

        // DELETE
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                model.removeRow(row);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    void loadCSV() {
        String[] paths = {
                "class_records.csv",
                "PrelimExam" + File.separator + "JAVA" + File.separator + "class_records.csv"
        };

        boolean loaded = false;
        for (String path : paths) {
            int rowCount = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String headerLine = br.readLine(); // read header
                if (headerLine == null) throw new Exception("Empty CSV");
                String[] headers = headerLine.split(",");
                // Set table columns to match header
                model.setColumnIdentifiers(headers);

                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",", -1);
                    if (data.length < headers.length) {
                        String[] padded = new String[headers.length];
                        System.arraycopy(data, 0, padded, 0, data.length);
                        for (int i = data.length; i < headers.length; i++) padded[i] = "";
                        model.addRow(padded);
                    } else if (data.length > headers.length) {
                        String[] truncated = new String[headers.length];
                        System.arraycopy(data, 0, truncated, 0, headers.length);
                        model.addRow(truncated);
                    } else {
                        model.addRow(data);
                    }
                    rowCount++;
                }
                lblStatus.setText("Loaded: " + path + " (" + rowCount + " rows)");
                loaded = true;
                break;
            } catch (Exception e) {
                // try next path
            }
        }

        if (!loaded) {
            JOptionPane.showMessageDialog(this,
                    "Error loading CSV file. Please ensure 'class_records.csv' is placed in the project root or in 'PrelimExam/JAVA'.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentRecordSystem app = new StudentRecordSystem();
            app.setLocationRelativeTo(null);
        });
    }
}
