import entity.Employee;
import entity.Patient;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginForm extends JFrame {
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnOK;
    private JButton btnCancel;
    private JPanel loginPanel;

    DefaultdatabaseURL defaultdatabaseURL = new DefaultdatabaseURL();

    protected User user;
    protected Employee employee;
    protected Patient patient;

    public LoginForm() {

        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 474));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                getAuthenticatedUser(email, password);

                if(user.getAccountType().equals("patient") || user.getAccountType().equals("Patient")){

                    getAuthennicatePatient(user.getId());

                    PatientForm patientForm = new PatientForm();
                }

                if(user.getAccountType().equals("Employee") || user.getAccountType().equals("employee")){

                    getAuthenicateEmployee(user.getId());
                    System.out.println("the employee type is " + employee.getEmployeeType());



                    if(employee.getEmployeeType().equals("dentist") || employee.getEmployeeType().equals("Dentist")){

                        DentistForm dentistForm = new DentistForm(employee.getEmployeeNumber());
                    }

                    if(employee.getEmployeeType().equals("receptionist") || employee.getEmployeeType().equals("Receptionist")){

                        ReceptionPage receptionPage = new ReceptionPage(LoginForm.this);
                    }

                }

                if (user != null) {
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
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


    private Patient getAuthennicatePatient(Integer id){

        Statement st = null;
        ResultSet rs = null;

        employee = new Employee();

        final String DB_URL = defaultdatabaseURL.getUrl();
        final String USERNAME = defaultdatabaseURL.getUser();
        final String PASSWORD = defaultdatabaseURL.getPassword();

        try {
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Connection db = DriverManager.getConnection(DB_URL,
                    USERNAME, PASSWORD);
            st = db.createStatement();
            String sql = "SELECT * FROM patient WHERE ID=?";
            PreparedStatement preparedStatement = db.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            rs = preparedStatement.executeQuery();


            while (rs.next()) {

                patient.setInsuranceNumber(rs.getLong(1));
                patient.setGender(rs.getString(2));
                patient.setId(rs.getInt(3));

            }


            st.close();
            db.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return patient;
    }

    private Employee getAuthenicateEmployee(Integer id){

        Statement st = null;
        ResultSet rs = null;

        employee = new Employee();

        final String DB_URL = defaultdatabaseURL.getUrl();
        final String USERNAME = defaultdatabaseURL.getUser();
        final String PASSWORD = defaultdatabaseURL.getPassword();

        try {
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Connection db = DriverManager.getConnection(DB_URL,
                    USERNAME, PASSWORD);
            st = db.createStatement();
            String sql = "SELECT * FROM employees WHERE ID=?";
            PreparedStatement preparedStatement = db.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            rs = preparedStatement.executeQuery();


            while (rs.next()) {

               employee.setEmployeeNumber(rs.getInt(1));
               employee.setEmployeeType(rs.getString(2));
               employee.setSalary(rs.getInt(3));
               employee.setEmployeeRole(rs.getString(4));
               employee.setId(rs.getInt(5));
               employee.setBranchName(rs.getString(6));
            }


            st.close();
            db.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

       return employee;

    }

    private User getAuthenticatedUser(String email, String password) {
        user = new User();

        Statement st = null;
        ResultSet rs = null;


        final String DB_URL = defaultdatabaseURL.getUrl();
        final String USERNAME = defaultdatabaseURL.getUser();
        final String PASSWORD = defaultdatabaseURL.getPassword();

        try {
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Connection db = DriverManager.getConnection(DB_URL,
                    USERNAME, PASSWORD);
            st = db.createStatement();
            String sql = "SELECT * FROM project.user WHERE Email=? AND Password=?";
            PreparedStatement preparedStatement = db.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            rs = preparedStatement.executeQuery();

            //panel(db);

            while (rs.next()) {

                user.setId(rs.getInt(1));
                user.setEmail(rs.getString(2));

                user.setAccountType(rs.getString(3));

                user.setPassword(rs.getString(4));
                user.setSsn(rs.getInt(5));
                user.setPhoneNO(rs.getLong(6));
                user.setFirstName(rs.getString(7));
                user.setMidName(rs.getString(8));
                user.setLastName(rs.getString(9));
                user.setHouseNumber(rs.getInt(10));
                user.setStreetName(rs.getString(11));
                user.setCity(rs.getString(12));
                user.setProvince(rs.getString(13));

                String dateOfBirth = rs.getString(14);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date dob = formatter.parse(dateOfBirth);
                user.setDateOfBirth(dob);

                user.setAge(rs.getInt(15));
            }

            rs.close();
            st.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return user;
    }

    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm();
        User user = loginForm.user;
        if (user != null) {
            System.out.println("Successful Authentication of: " + user.getFirstName());
            System.out.println("          Email: " + user.getEmail());
            System.out.println("          Phone: " + user.getPhoneNO());
            System.out.println("          Address: " + user.getStreetName());
        } else {
            System.out.println("Authentication canceled");
        }
    }

}
