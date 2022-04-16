import entity.Appointments;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ReceptionPage extends JDialog {
    private JPanel receptionPanel;
    private JLabel lbAdmin;
    private JButton appointmentButton;
    private JButton registerButton;
    private JButton updateButton;
    private JTextField PatientId;

    public ReceptionPage(JFrame parent) {
        super(parent);
        setTitle("Reception");
        setContentPane(receptionPanel);
        setMinimumSize(new Dimension(600, 529));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);




        appointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAppointment appointmentForm = new CreateAppointment(null);
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
                UserRegister registrationForm = new UserRegister();
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
                DefaultdatabaseURL defaultdatabaseURL = new DefaultdatabaseURL();
                final String DB_URL = defaultdatabaseURL.getUrl();
                final String USERNAME = defaultdatabaseURL.getUser();
                final String PASSWORD = defaultdatabaseURL.getPassword();
                ResultSet rs = null;
                try {
                    //First, connect to MYSQL server and create the database if not created
                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    Statement statement = conn.createStatement();
                    String query = "select * from users where ID = " + PatientId.getText();

                    rs = statement.executeQuery(query);

                    if (rs.next()) {
                        UpdatePatient updatePatient = new UpdatePatient(null, Integer.parseInt(PatientId.getText()));

                    }


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }


        });
        setVisible(true);
    }



    public static void main(String[] args) {
        ReceptionPage myForm = new ReceptionPage(null);
    }
}
