import entity.Patient;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.ParseException;


public class PatientRegister extends JDialog {

    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel registerPanel;
    private JTextField tfGender;
    private JTextField tfInsuranceNO;

    private DefaultdatabaseURL defaultdatabaseURL = new DefaultdatabaseURL();


    public PatientRegister(JFrame parent, Integer id) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(800, 1500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    registerPatient(id);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void registerPatient(Integer id) throws ParseException {

        //patient section
        String gender = tfGender.getText();
        Long insuranceNo = Long.parseLong(tfInsuranceNO.getText());


        if (insuranceNo == null) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        addUserToDatabase(insuranceNo, gender, id);

        //  patient = addPatientToDatabase(id, insuranceNo, gender);
        //System.out.println(patient.getGender()+", "+patient.getInsuranceNumber());

        if (patient.getId() != null) {
            System.out.println("done");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new patient",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    //  protected User user;
    protected Patient patient;


    private Patient addUserToDatabase(Long insuranceNo, String gender, Integer id)
            throws ParseException, NullPointerException {
        User user = null;
        patient = new Patient();
        final String DB_URL = defaultdatabaseURL.getUrl();
        final String USERNAME = defaultdatabaseURL.getUser();
        final String PASSWORD = defaultdatabaseURL.getPassword();


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();

            String sql2 = "INSERT INTO patient (InsuranceNumber, Gender, ID) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
            preparedStatement2.setLong(1, insuranceNo);
            preparedStatement2.setString(2, gender);
            preparedStatement2.setInt(3, id);


            //Insert row into the table
            int addedRows2 = preparedStatement2.executeUpdate();
            if (addedRows2 > 0) {
                patient.setInsuranceNumber(insuranceNo);
                patient.setGender(gender);
                patient.setId(id);
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return patient;
    }
}
