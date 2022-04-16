import entity.Patient;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserRegister extends JFrame {
    private JButton btnRegister;

    private JTextField tfName;
    private JTextField tfId;
    private JTextField tfHouseNumber;
    private JTextField tfEmail;
    private JTextField tfPhone;
    private JTextField tfAddress;
    private JTextField tfMidname;
    private JTextField tfLastname;
    private JTextField tfDateOfBirth;
    private JTextField tfProvince;
    private JTextField tfCity;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPassword;
    private JButton btnCancel;
    private JPanel registerPanel;
    private JTextField tfSsn;
    private JTextField tfAccountType;
    private JTextField tfGender;
    private JTextField tfInsuranceNO;

    public UserRegister() {
       // super(parent);
        setTitle("Dashborad");
        setContentPane(registerPanel);
      //  setModal(true);
        setMinimumSize(new Dimension(500, 1500));
        setSize(1200, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    registerUser();

                    if ((user.getAccountType().equals("Patient") || user.getAccountType().equals("patient"))
                        && user.getAge()>15){
                        dispose();
                        PatientRegister patientRegister = new PatientRegister(UserRegister.this, user.getId());
                    }

                    if ((user.getAccountType().equals("Employee") || user.getAccountType().equals("employee"))
                            && user.getAge()>15){
                        dispose();
                        EmployeeRegister employeeRegister = new EmployeeRegister(UserRegister.this, user.getId());
                    }

                    /*
                    if (user != null) {
                        JOptionPane.showMessageDialog(UserRegister.this,
                                "New user: " + user.getFirstName(),
                                "Successful Registration",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }*/

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

    private void registerUser() throws ParseException {
        Integer id = Integer.parseInt(tfId.getText());
        String firstName = tfName.getText();
        String midName = tfMidname.getText();
        String lastName = tfLastname.getText();
        String email = tfEmail.getText();
        String accountType = tfAccountType.getText();

        Integer ssn = Integer.parseInt(tfSsn.getText());

        Long phone = Long.parseLong(tfPhone.getText());
        Integer houseNumber = Integer.parseInt(tfHouseNumber.getText());
        String address = tfAddress.getText();
        String city = tfCity.getText();
        String province = tfProvince.getText();

        String dateOfBirth = tfDateOfBirth.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword = String.valueOf(pfConfirmPassword.getPassword());
        if (id == null || firstName.isEmpty() || email.isEmpty() || lastName.isEmpty() || phone == null
                || ssn == null || address.isEmpty() || city.isEmpty() || password.isEmpty() || accountType.isEmpty() ||
                dateOfBirth.isEmpty() || houseNumber == null || province.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Converting date type and calculating ages
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dob = formatter.parse(dateOfBirth);
        Date today = new Date();
        Long time = today.getTime() / 1000 - dob.getTime() / 1000;
        int age = Math.round(time) / 31536000;


        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Confirm Password does not match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(id, firstName, midName, lastName, email, accountType, phone, houseNumber, ssn,
                address, city, province, password, dateOfBirth, age);

        if (user != null) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);

    }

    protected User user;
    protected Patient patient;

    private User addUserToDatabase(Integer id, String firstName, String midName, String lastName, String email,
                                   String accountType, Long phone, Integer houseNumber, Integer ssn,
                                   String address, String city, String province, String password, String dateOfBirth,
                                   int age)
            throws ParseException, NullPointerException {
        User user = new User();
        final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";
        final String USERNAME = "root";
        final String PASSWORD = "Tom_yin0818";

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dob = formatter.parse(dateOfBirth);

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO user (ID, Email, AccountType, Password, SSN, PhoneNumber, FirstName, MidName, " +
                    "SecondName, " +
                    "HouseNumber, StreetName, City, Province, DateOfBirth, Age) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, accountType);
            preparedStatement.setString(4, password);
            preparedStatement.setInt(5, ssn);
            preparedStatement.setLong(6, phone);
            preparedStatement.setString(7, firstName);
            preparedStatement.setString(8, midName);
            preparedStatement.setString(9, lastName);
            preparedStatement.setInt(10, houseNumber);
            preparedStatement.setString(11, address);
            preparedStatement.setString(12, city);
            preparedStatement.setString(13, province);
            preparedStatement.setString(14, dateOfBirth);
            preparedStatement.setInt(15, age);

            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.setId(id);
                user.setFirstName(firstName);
                user.setEmail(email);
                user.setAccountType(accountType);
                user.setPassword(password);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;

    }

    public static void main(String[] args) {
        UserRegister myForm = new UserRegister();
    }

}
