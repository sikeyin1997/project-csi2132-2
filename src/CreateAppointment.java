import entity.Appointments;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class CreateAppointment extends JDialog {
    private JTextField patientId;
    private JTextField dentistNo;
    private JTextField date;
    private JTextField startTime;
    private JTextField endTime;
    private JTextField appointType;
    private JTextField status;
    private JTextField room;
    private JButton btnCancel;
    private JPanel appointmentPanel;
    private JLabel tfName;
    private JLabel roomLabel;
    private JButton btnAddAppoint;

    public CreateAppointment(JFrame parent) {
        super(parent);
        setTitle("Create a new appointment");
        setContentPane(appointmentPanel);
        setMinimumSize(new Dimension(650, 674));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnAddAppoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAppointment();
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

    private void addAppointment() {


        String patId = patientId.getText();
        String denID = dentistNo.getText();
        String appDate = date.getText();
        Time sTime = Time.valueOf(startTime.getText()+":00");
        Time eTime = Time.valueOf(endTime.getText()+":00");
        String type = appointType.getText();
        String stat = status.getText();
        String rm = room.getText();


        if (patId.isEmpty() || denID.isEmpty() || appDate.isEmpty() || type.isEmpty() || stat.isEmpty() || rm.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        appointment = addAppointmentToDatabase(patId, denID, appDate, sTime, eTime, type, stat, rm);
        if (appointment != null) {
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public Appointments appointment;
    private Appointments addAppointmentToDatabase(
                                            String patId,
                                            String denID,
                                            String appDate,
                                            Time sTime,
                                            Time eTime,
                                            String type,
                                            String stat,
                                            String rm)
    {

        final String DB_URL = "jdbc:mysql://localhost/project?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "password";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Appointments (patId, denID, appDate, sTime, eTime, type, stat, rm) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, patId);
            preparedStatement.setString(2, denID);
            preparedStatement.setString(3, appDate);
            preparedStatement.setTime(4, sTime);
            preparedStatement.setTime(5, eTime);
            preparedStatement.setString(6, type);
            preparedStatement.setString(7, stat);
            preparedStatement.setString(8, rm);

            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                appointment = new Appointments();
                appointment.setPatientNumber(Integer.valueOf(patId));
                appointment.setDentistNO(Integer.valueOf(denID));
                Date date = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(appDate);
                appointment.setDate(date);
                appointment.setStartTime(sTime);
                appointment.setEndTime(eTime);
                appointment.setAppointmentType(type);
                appointment.setStatus(stat);
                appointment.setRoom(rm);

            }

            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return appointment;
    }

    public static void main(String[] args) {
        CreateAppointment myForm = new CreateAppointment(null);
        Appointments appointment = myForm.appointment;
        if (appointment != null) {
            System.out.println("Appointment created");
        }
        else {
            System.out.println("Appointment canceled");
        }
    }



}
