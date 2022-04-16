import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;

public class PatientForm extends JFrame {

    private JPanel patientPanel;
    private JTable upComing;
    private JTable history;
    private JButton setNewAppointmentButton;
    private JPanel upC;
    private JPanel hist;
    Connection myConn;
    Statement myStmnt;

    // for collecting data
    ArrayList<Object> data1 = new ArrayList<>();
    Object [][] historyData;
    Object [][] upComingData;

    int i1 = 0;
    int prcRow =0;


    public PatientForm() {



        setTitle("Patient");
        // establish connection
        connection();
        String histQuery = "select * from Appointments where Date < '2022-07-07' ";
        String upComingQuery = "select * from Appointments where Date >'2022-07-07' " ;

        // fetch data from database
        historyData = getHistData(histQuery);
        upComingData = getUpComingData(upComingQuery);

        createHistTable();
        createUpComingTable();

        setContentPane(patientPanel);
        setMinimumSize(new Dimension(700, 474));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setNewAppointmentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                goToAppointmentScheduler();
            }
        });
    }

    private void goToAppointmentScheduler(){

        AppointmentScheduler as = new AppointmentScheduler(this);
    }

    void connection(){
        try{
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dentalCentreDatabase", "root", "hs6292953");
            myStmnt = myConn.createStatement();
        }
        catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private Object [][]  getHistData(String query){

        // get connection
        try {


            Statement myStmnt = myConn.createStatement();
            ResultSet myRes = myStmnt.executeQuery(query);
            int j =0;
            while (myRes.next()){
                data1.add(myRes.getString("PatientNumber")) ;
                data1.add(myRes.getString("DentistNO"));
                data1.add(myRes.getString("Date"));
                data1.add(myRes.getString("StartTime"));
                data1.add(myRes.getString("EndTime"));
                data1.add(myRes.getString("AppoitmentType"));
                data1.add(myRes.getString("Status"));
                data1.add(myRes.getString("Room"));
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        // previous row in prcRow
        prcRow = i1;
        i1 = data1.size() / 8;

        Object mydata [][] = new Object[i1][];
        int j =0;
        for(int i =0; i < data1.size(); i = i+8){


            Object s[] = {data1.get(i),data1.get(i+1), data1.get(i+2), data1.get(i+3), data1.get(i+4), data1.get(i+5), data1.get(i+6),data1.get(i+7)};
            mydata[j] = s;
            j++;
        }
        data1 = new ArrayList<>();
        return mydata;
    }

    private Object [][] getUpComingData(String query) {

        // get connection
        try {


            Statement myStmnt = myConn.createStatement();
            ResultSet myRes = myStmnt.executeQuery(query);
            int j = 0;
            while (myRes.next()) {
                data1.add(myRes.getString("PatientNumber")) ;
                data1.add(myRes.getString("DentistNO"));
                data1.add(myRes.getString("Date"));
                data1.add(myRes.getString("StartTime"));
                data1.add(myRes.getString("EndTime"));
                data1.add(myRes.getString("AppoitmentType"));
                data1.add(myRes.getString("Status"));
                data1.add(myRes.getString("Room"));
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        // previous row in prcRow
        prcRow = i1;
        i1 = data1.size() / 8;

        Object mydata[][] = new Object[i1][];
        int j = 0;
        for (int i = 0; i < data1.size(); i = i + 8) {


            Object s[] = {data1.get(i), data1.get(i + 1), data1.get(i + 2), data1.get(i + 3), data1.get(i + 4), data1.get(i + 5), data1.get(i + 6), data1.get(i + 7)};
            mydata[j] = s;
            j++;
        }
        data1 = new ArrayList<>();
        return mydata;


    }

    private void createHistTable(){

        hist.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Patient History", TitledBorder.LEFT,
                TitledBorder.TOP));

        history.setModel(new DefaultTableModel(
                                historyData,
                                new String[]{"PatientNumber", "enstist Number", "Date", "Start Time", "End Time","AppointmentType", "Status", "Room" })

                        {
                            @Override
                            public boolean isCellEditable(int row, int column) {

                                return false;
                            }
                        }
        );
    }

    private void createUpComingTable(){

        upC.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Up Coming Appointment", TitledBorder.LEFT,
                TitledBorder.TOP));
        upComing.setModel(new DefaultTableModel(
                                 upComingData,
                                 new String[]{"PatientNumber", "enstist Number", "Date", "Start Time", "End Time","AppointmentType", "Status", "Room" })

                         {
                             @Override
                             public boolean isCellEditable(int row, int column) {

                                 return false;
                             }
                         }
        );

    }


}
