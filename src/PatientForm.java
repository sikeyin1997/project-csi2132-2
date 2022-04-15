import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class PatientForm extends JDialog {
    private JTextField tfFirstName;
    private JTextField tfSecondName;
    private JTextField tfMidName;
    private JTextField tfHouseNumber;
    private JTextField tfStreetName;
    private JTextField tfCity;
    private JTextField tfProvince;
    private JTextField tfDateOfBirth;
    private JTextField tfAge;
    private JTextField tfInsuranceNumber;
    private JTextField tfGender;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton btnSubmit;
    private JTextField tfEmail;
    private JTextField tfPhoneNumber;
    private JPanel patientPanel;
    private JTextField tfSSN;

    public PatientForm(JFrame parent){
        super(parent);
        setTitle("Create a new account");
        setContentPane(patientPanel);
        setMinimumSize(new Dimension(450, 475));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerPatient();
            }
        });
        setVisible(true);
    }

    private void registerPatient(){
        String FirstName = tfFirstName.getText();
        String SecondName = tfSecondName.getText();
        String MidName = tfMidName.getText();
        String Email = tfEmail.getText();
        Integer PhoneNumber = Integer.parseInt(tfPhoneNumber.getText());
        Integer InsuranceNumber = Integer.parseInt(tfInsuranceNumber.getText());
        Integer SSN = Integer.parseInt(tfSSN.getText());
        String Gender = tfGender.getText();
        Integer HouseNumber = Integer.parseInt(tfHouseNumber.getText());
        String StreetName = tfStreetName.getText();
        String City = tfCity.getText();
        String Province = tfProvince.getText();
        String DateOfBirth = tfDateOfBirth.getText();
        Integer Age = Integer.parseInt(tfAge.getText());
        String Password = String.valueOf(passwordField1.getPassword());
        String ConfirmPassword = String.valueOf(passwordField2.getPassword());

        if(!passwordField1.equals(passwordField2)){
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match");
            return;
        }

        patient = addPatientToDatabase(FirstName, SecondName, MidName, Email, PhoneNumber, InsuranceNumber, SSN, Gender, HouseNumber, StreetName, City, Province, DateOfBirth, Age, Password);
        if(patient != null){
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to Register Patient",
                    "Try Again,",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public Patient patient;
    private Patient addPatientToDatabase(String FirstName, String SecondName, String MidName, String Email, Integer PhoneNumber, Integer InsuranceNumber, Integer SSN, String Gender, Integer HouseNumber, String StreetName, String City, String Province, String DateOfBirth, Integer Age, String Password){
        Patient patient = null;
        final String DB_URL = "DBlink"; //link here
        final String DBUserName = "root";
        final String DBPassword = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, DBUserName, Password);

            Statement statement = conn.createStatement();
            String sql = ("BEGIN; INSERT INTO user (Email, Password, SSN, PhoneNumber, FirstName, MidName, SecondName, HouseNumber, StreetName, City, Province, DateOfBirth, Age)"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                    + "INSERT INTO Patient (InsuranceNumber, Gender)"
                    + "VALUES(?, ?)"
                    + "COMMIT;");
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, Email);
            preparedStatement.setString(2, Password);
            preparedStatement.setInt(3, SSN);
            preparedStatement.setInt(4, PhoneNumber);
            preparedStatement.setString(5, FirstName);
            preparedStatement.setString(6, MidName);
            preparedStatement.setString(7, SecondName);
            preparedStatement.setInt(8, HouseNumber);
            preparedStatement.setString(9, StreetName);
            preparedStatement.setString(10, City);
            preparedStatement.setString(11, Province);
            preparedStatement.setString(12, DateOfBirth);
            preparedStatement.setInt(13, Age);
            preparedStatement.setInt(14, InsuranceNumber);
            preparedStatement.setString(15, Gender);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows>0){
                patient = new Patient();
                patient.Email = Email;
                patient.Password = Password;
                patient.SSN = SSN;
                patient.PhoneNumber = PhoneNumber;
                patient.FirstName = FirstName;
                patient.MidName = MidName;
                patient.SecondName = SecondName;
                patient.HouseNumber = HouseNumber;
                patient.StreetName = StreetName;
                patient.City = City;
                patient.Province = Province;
                patient.DateOfBirth = DateOfBirth;
                patient.Age = Age;
                patient.InsuranceNumber = InsuranceNumber;
                patient.Gender = Gender;
            }

            statement.close();
            conn.close();

        } catch(Exception e){
            e.printStackTrace();
        }
        return patient;
    }
    public static void main(String[] args) {
        PatientForm myForm = new PatientForm(null);
        Patient patient = myForm.patient;
        if (patient != null){
            System.out.println("Successful Registration of: " + patient.FirstName + patient.SecondName);
        }
        else {
            System.out.println("Unsuccessful Registration");
        }
    }

}
