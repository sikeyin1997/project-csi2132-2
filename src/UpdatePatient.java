import entity.Patient;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdatePatient extends JDialog {
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

    ResultSet globRES;


    public UpdatePatient(JFrame parent, Integer userId) throws SQLException {
        super(parent);
        setTitle("update patient info");
        setContentPane(updatePatientPanel);
        setMinimumSize(new Dimension(800, 1000));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ResultSet rs = null;

        DefaultdatabaseURL defaultdatabaseURL = new DefaultdatabaseURL();
        final String DB_URL = defaultdatabaseURL.getUrl();
        final String USERNAME = defaultdatabaseURL.getUser();
        final String PASSWORD = defaultdatabaseURL.getPassword();
        Connection myConn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        Statement myStmnt = myConn.createStatement();
        String query = "select * from user, patient  where user.ID = " + userId.toString();
        rs = myStmnt.executeQuery(query);

        if (rs.next()) {
            tfEmail.setText(rs.getString("Email"));
            tfName.setText(rs.getString("FirstName"));
            tfMidname.setText(rs.getString("MidName"));
            tfLastname.setText(rs.getString("SecondName"));
            tfSsn.setText(String.valueOf(rs.getInt("SSN")));
            tfPhone.setText(String.valueOf(rs.getInt("PhoneNumber")));
            tfHouseNumber.setText(String.valueOf(rs.getInt("HouseNumber")));
            tfAddress.setText(rs.getString("StreetName"));
            tfCity.setText(rs.getString("City"));
            tfProvince.setText(rs.getString("Province"));
            tfDateOfBirth.setText(rs.getString("DateOfBirth"));
            insuranceNo.setText(String.valueOf(rs.getInt("InsuranceNumber")));
            gender.setText(rs.getString("Gender"));
            myStmnt.close();
            myConn.close();

        }


        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updatePatient();
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

            Integer phone = Integer.parseInt(tfPhone.getText());
            Integer houseNumber = Integer.parseInt(tfHouseNumber.getText());
            String address = tfAddress.getText();
            String city = tfCity.getText();
            String province = tfProvince.getText();

            String dateOfBirth = tfDateOfBirth.getText();
            Integer insurance = Integer.parseInt(insuranceNo.getText());
            String gend = gender.getText();
            System.out.println(province);

            if (id == null || firstName.isEmpty() || email.isEmpty() || lastName.isEmpty()
                    || phone == null || ssn == null || address.isEmpty() || city.isEmpty() ||
                    dateOfBirth.isEmpty() || houseNumber == null || province.isEmpty() || gend.isEmpty() || insurance == null) {
                JOptionPane.showMessageDialog(this,
                        "Please enter all fields",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }



           User user = updatePatientInDatabase(id, firstName, midName, lastName, email, phone, houseNumber, ssn,
                    address, city, province, dateOfBirth, insurance, gend);
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
                                             String dateOfBirth,
                                             Integer insurance,
                                             String gender)
                throws ParseException {
            User user = new User();
            Patient patient = new Patient();

            DefaultdatabaseURL defaultdatabaseURL = new DefaultdatabaseURL();
            final String DB_URL = defaultdatabaseURL.getUrl();
            final String USERNAME = defaultdatabaseURL.getUser();
            final String PASSWORD = defaultdatabaseURL.getPassword();

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
                    user.setPhoneNO(Long.valueOf(phone));
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

                Statement stmt2 = conn.createStatement();
                String sql2 = "UPDATE patient " +
                        "SET InsuranceNumber = ?, gender = ?) " +
                        "where ID = ? ";
                PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
                preparedStatement2.setString(2, gender);
                preparedStatement2.setInt(1, insurance);
                preparedStatement2.setInt(3, id);

                //Insert row into the table
                int addedRows2 = preparedStatement2.executeUpdate();
                if (addedRows2 > 0) {
                    patient = new Patient();
                    patient.setId(id);
                    patient.setGender(gender);
                    patient.setInsuranceNumber(Long.valueOf(insurance));


                }

                stmt2.close();
                conn.close();


                if (user != null) {
                    System.out.println("Successful update of: " + user.getFirstName());
                }
                else {
                    System.out.println("update canceled");
                }



            }catch(Exception e){
                e.printStackTrace();
            }
            return user;
        }

        public static void main(String[] args) throws SQLException {
            int id = 000001;
            UpdatePatient myForm = new UpdatePatient(null, id);


        }
    }

