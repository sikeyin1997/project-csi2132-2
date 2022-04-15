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
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdatePatient extends JDialog{
    private JPanel updatePatientPanel;
    private JTextField tfName;
    private JTextField tfEmail;
    private JTextField tfAddress;
    private JButton btnUpdate;
    private JButton btnCancel;
    private JTextField tfId;
    private JTextField tfMidname;
    private JTextField tfLastname;
    private JTextField tfCity;
    private JTextField tfProvince;
    private JTextField tfDateOfBirth;
    private JTextField tfPhone;
    private JTextField tfHouseNumber;
    private JTextField tfSsn;
    private JTextField gender;
    private JTextField insuranceNo;
    private JLabel InsuranceNo;


        public UpdatePatient(JFrame parent) {
            super(parent);
            setTitle("update patient info");
            setContentPane(registerPanel);
            setMinimumSize(new Dimension(800, 1500));
            setModal(true);
            setLocationRelativeTo(parent);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            btnUpdate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        registerUser();
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

        private void updatePatient() throws ParseException {
            Integer id = Integer.parseInt(tfId.getText());
            String firstName = tfName.getText();
            String midName = tfMidname.getText();
            String lastName = tfLastname.getText();
            String email = tfEmail.getText();
            Integer ssn = Integer.parseInt(tfSsn.getText());
            System.out.println("work");

            Integer phone = 4566545;//Integer.parseInt(tfPhone.getText());
            Integer houseNumber = Integer.parseInt(tfHouseNumber.getText());
            String address = tfAddress.getText();
            String city = tfCity.getText();
            String province = tfProvince.getText();

            String dateOfBirth = tfDateOfBirth.getText();
            System.out.println(province);

            if (id == null || firstName.isEmpty() || email.isEmpty() || lastName.isEmpty()
                    || phone == null || ssn == null || address.isEmpty() || city.isEmpty() ||
                    dateOfBirth.isEmpty() || houseNumber == null || province.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter all fields",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }



            user = updatePatientInDatabase(id, firstName, midName, lastName, email, phone, houseNumber, ssn,
                    address, city, province, dateOfBirth);
            if (user != null) {
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(this,
                        "Failed to register new user",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        public User user;
        private User updatePatientInDatabase(Integer id,
                                             String firstName,
                                             String midName,
                                             String lastName,
                                             String email,
                                             Integer phone,
                                             Integer houseNumber,
                                             Integer ssn,
                                             String address,
                                             String city,
                                             String province,
                                             String dateOfBirth)
                throws ParseException {
            User user = null;
            final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";
            final String USERNAME = "root";
            final String PASSWORD = "password";

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dob = formatter.parse(dateOfBirth);

            Date today = new Date();
            Long time= today.getTime() / 1000 - dob.getTime() / 1000;

            int age = Math.round(time) / 31536000;

            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                // Connected to database successfully...

                Statement stmt = conn.createStatement();
                String sql = "UPDATE user " +
                        "SET Email = ?, SSN = ?, PhoneNumber = ?, FirstName = ?, MidName = ?, " +
                        "SecondName = ?, " +
                        "HouseNumber = ?, StreetName = ?, City = ?, Province = ?, DateOfBirth = ?, Age = ?) " +
                        "where ID = ? ";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setInt(2, ssn);
                preparedStatement.setInt(3, phone);
                preparedStatement.setString(4, firstName);
                preparedStatement.setString(5, midName);
                preparedStatement.setString(6, lastName);
                preparedStatement.setInt(7, houseNumber);
                preparedStatement.setString(8, address);
                preparedStatement.setString(9, city);
                preparedStatement.setString(10, province);
                preparedStatement.setString(11, dateOfBirth);
                preparedStatement.setInt(12, age);
                preparedStatement.setInt(13, id);

                //Insert row into the table
                int addedRows = preparedStatement.executeUpdate();
                if (addedRows > 0) {
                    user = new User();
                    user.setId(id);
                    user.setFirstName(firstName);
                    user.setEmail(email);
                    user.setSsn(ssn);
                    user.setPhoneNO(phone);
                    user.setFirstName(firstName);
                    user.setMidName(midName);
                    user.setLastName(lastName);
                    user.setHouseNumber(houseNumber);
                    user.setStreetName(address);
                    user.setCity(city);
                    user.setProvince(province);
                    user.setDateOfBirth(dob);
                    user.setAge(age);
                }

                stmt.close();
                conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }

            return user;
        }

        public static void main(String[] args) {
            RegistrationForm myForm = new RegistrationForm(null);
            User user = myForm.user;
            if (user != null) {
                System.out.println("Successful registration of: " + user.getFirstName());
            }
            else {
                System.out.println("Registration canceled");
            }
        }
    }

