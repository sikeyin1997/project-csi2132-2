import entity.Employee;
import entity.Patient;

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

public class EmployeeRegister extends JDialog {

    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel registerPanel;
    private JTextField tfEmployeeNo;
    private JTextField tfSalary;
    private JTextField tfEmployeeType;
    private JTextField tfEmployeeRole;
    private JTextField tfBranchName;
    private JTextField tfGender;
    private JTextField tfInsuranceNo;


    public EmployeeRegister(JFrame parent, Integer id) {
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
                    registerEmployee(id);
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

    private void registerEmployee(Integer id) throws ParseException {


        //patient section
        String gender = tfGender.getText();

        Long insuranceNo = null;
        if (!tfInsuranceNo.getText().isEmpty()){
            insuranceNo = Long.parseLong(tfInsuranceNo.getText());
        }
        System.out.println(insuranceNo);

        //Employee section
        Integer employeeNumber = Integer.parseInt(tfEmployeeNo.getText());
        System.out.println(employeeNumber);

        Integer salary = 0;
        if(!tfSalary.getText().isEmpty()){
            salary = Integer.parseInt(tfSalary.getText());
        }

        String employeeType = tfEmployeeType.getText();
        String employeeRole = tfEmployeeRole.getText();
        String branchName = tfBranchName.getText();



        if (employeeNumber == null || employeeType.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all mandatory fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }


        addEmployeeToDataBase(employeeNumber, employeeType, salary, employeeRole, id, branchName);

        // If you wish to be register as patient as well
        if( insuranceNo != null ){
            addPatientToDatabase(id, insuranceNo, gender);
        }


        if (employee != null) {
            System.out.println("done");

            dispose();
        }

        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    protected Patient patient;
    protected Employee employee;


    // Add employee to database
    private Employee addEmployeeToDataBase(Integer employeeNumber, String employeeType, Integer salary,
                                           String employeeRole, Integer id, String branchName) {

        final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";
        final String USERNAME = "root";
        final String PASSWORD = "Tom_yin0818";

         employee = new Employee();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Employees (EmployeeNumber, EmployeeType, Salary, EmployeeRole, ID, BranchName) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, employeeNumber);
            preparedStatement.setString(2, employeeType);
            preparedStatement.setInt(3, salary);
            preparedStatement.setString(4, employeeRole);
            preparedStatement.setInt(5, id);
            preparedStatement.setString(6, branchName);


            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                employee.setEmployeeNumber(employeeNumber);
                employee.setEmployeeType(employeeType);
                employee.setSalary(salary);
                employee.setEmployeeRole(employeeRole);
                employee.setId(id);
                employee.setBranchName(branchName);
            }

            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return employee;
    }


    // Employee can be registered as patient
    private Patient addPatientToDatabase(Integer id, Long insuranceNo, String gender){

        final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";
        final String USERNAME = "root";
        final String PASSWORD = "Tom_yin0818";

        patient = new Patient();

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO patient (InsuranceNumber, Gender, ID) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, insuranceNo);
            preparedStatement.setString(2, gender);
            preparedStatement.setInt(3, id);


            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                patient.setInsuranceNumber(insuranceNo);
                patient.setGender(gender);
                patient.setId(id);
            }

            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return patient;
    }

}
