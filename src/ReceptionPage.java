import entity.Appointments;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ReceptionPage extends JFrame {
    private JPanel receptionPanel;
    private JLabel lbAdmin;
    private JButton appointmentButton;
    private JButton registerButton;
    private JButton updateButton;
    private JTextField PatientId;

    public ReceptionPage() {
        setTitle("Reception");
        setContentPane(receptionPanel);
        setMinimumSize(new Dimension(600, 529));
        setSize(1300, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        appointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAppointment appointmentForm = new CreateAppointment(ReceptionPage.this);
                Appointments appointment = appointmentForm.appointment;

                if (appointment != null) {
                    JOptionPane.showMessageDialog(ReceptionPage.this,
                            "New appointment created",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationForm registrationForm = new RegistrationForm(ReceptionPage.this);
                User user = registrationForm.user;

                if (user != null) {
                    JOptionPane.showMessageDialog(ReceptionPage.this,
                            "New user: " + user.getFirstName(),
                            "Successful Registration",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String MYSQL_SERVER_URL = "jdbc:mysql://localhost/";
                final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
                final String USERNAME = "root";
                final String PASSWORD = "";
                ResultSet rs = null;
                try {
                    //First, connect to MYSQL server and create the database if not created
                    Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
                    Statement statement = conn.createStatement();
                    String query = "select * from users where ID = " + PatientId.getText();

                    rs = statement.executeQuery(query);

                    if (rs.next()) {
                        UpdatePatient updatePatient = new UpdatePatient(ReceptionPage.this, Integer.parseInt(PatientId.getText()));

                    }


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }


        });
    }



    public static void main(String[] args) {
        ReceptionPage myForm = new ReceptionPage();
    }
}
