import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
//hhh
public class AppointmentScheduler extends JDialog {
    private JTextField tfDate;
    private JTextField tfStartTime;
    private JTextField tfEndTime;
    private JTextField tfAppointmentType;
    private JTextField tfStatus;
    private JTextField tfPatientNumber;
    private JButton btnSubmit;
    private JPanel appointmentScheduler;

    public AppointmentScheduler(JFrame parent){
        super(parent);
        setTitle("Schedule an Appointment");
        setContentPane(appointmentScheduler);
        setMinimumSize(new Dimension(450, 475));
        setModal(true);
        setLocationRelativeTo(parent);
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scheduleAppointment();
            }
        });
        setVisible(true);
    }

    private void scheduleAppointment(){
        Integer PatientNumber = Integer.parseInt(tfPatientNumber.getText());
        String Date = tfDate.getText();
        String StartTime = tfStartTime.getText();
        String EndTime = tfEndTime.getText();
        String AppointmentType = tfAppointmentType.getText();
        String Status = tfStatus.getText();

        if (Date.isEmpty() || StartTime.isEmpty() || EndTime.isEmpty() || AppointmentType.isEmpty() || Status.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please enter remaining information",
                    "Try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        appointments = addAppointmentToDB(PatientNumber, Date, StartTime, EndTime, AppointmentType, Status);
        if(appointments != null){
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(this,
                    "Failed to schedule appointment",
                    "Try Again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public Appointments appointments;
    private Appointments addAppointmentToDB(Integer PatientNumber, String Date, String StartTime, String EndTime, String AppointmentType, String Status){
        Appointments appointments = null;
        final String DB_URL = "DBURL";
        final String DB_Username = "root";
        final String DB_Password = "";

        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_Username, DB_Password);

            Statement statement = connection.createStatement();
            String sql = "INSERT INTO Appointments(PatientNumber, DentistNO, Date, StartTime, EndTime, AppointmentType, Status, Room)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, PatientNumber);
            preparedStatement.setNull(2, java.sql.Types.INTEGER);
            preparedStatement.setString(3, Date);
            preparedStatement.setString(4, StartTime);
            preparedStatement.setString(5, EndTime);
            preparedStatement.setString(6, AppointmentType);
            preparedStatement.setString(7, Status);
            preparedStatement.setString(8,null);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows>0){
                appointments = new Appointments();
                appointments.PatientNumber = PatientNumber;
                appointments.Date = Date;
                appointments.StartTime = StartTime;
                appointments.EndTime = EndTime;
                appointments.AppointmentType = AppointmentType;
                appointments.Status = Status;
            }

            statement.close();
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return appointments;
    }
    public static void main(String[] args) {
        AppointmentScheduler myAppointment = new AppointmentScheduler(null);
        Appointments appointments1 = myAppointment.appointments;
        if (appointments1 != null){
            System.out.println("Appointment Schedule Completed");
        }
        else{
            System.out.println("Appointment Schedule Failed");
        }
    }
}

