import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class Dentist extends JFrame implements ActionListener {
    private JPanel Main;
    private JTable table1;
    private JTextArea textArea1;
    private JButton button1;
    Object [][] data;
    Connection myConn;
    int i1 = 0;
    int prcRow =0;
    Statement myStmnt;
    ArrayList<Object> data1 = new ArrayList<>();
    ResultSet globRES;

    public Dentist() {

        //textArea1.addInputMethodListener(this);
        button1.addActionListener(this::actionPerformed);
        connection();
        data = getData("select * from Appointments where DentistNO = 10006");
        createTable();


        table1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }
        });
        table1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int rSelected = table1.getSelectedRow();


                try {
                    goToAppProc(rSelected);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
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
    void updateTable(Integer input){


        data = null;
        data = getData("select * from Appointments where DentistNO = 10006 and PatientNumber = " + input);
        createTable();

    }
    private Object [][]  getData(String query){

        // get connection
        try {


            Statement myStmnt = myConn.createStatement();
            ResultSet myRes = myStmnt.executeQuery(query);
            globRES = myRes;
            int j =0;
            while (myRes.next()){
                data1.add(myRes.getString("PatientNumber")) ;
                data1.add(myRes.getString("Date"));
                data1.add(myRes.getString("StartTime"));
                data1.add(myRes.getString("EndTime"));
                data1.add(myRes.getString("AppoitmentType"));
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        // previpous row in prcRow
        prcRow = i1;
        i1 = data1.size() / 5;

        Object mydata [][] = new Object[i1][];
        int j =0;
        for(int i =0; i < data1.size(); i = i+5){


            Object s[] = {data1.get(i),data1.get(i+1), data1.get(i+2), data1.get(i+3), data1.get(i+4) };
            mydata[j] = s;
            j++;
        }
        data1 = new ArrayList<>();
        return mydata;
    }


    private void goToAppProc(int rSelected) throws SQLException {

        this.dispose();
        this.setVisible(false);
        // getting patient number
        int pn = Integer.parseInt((String) table1.getValueAt(rSelected, 0));
        AppointmentProcedure appPrc = new AppointmentProcedure(rSelected, pn);
        //appPrc.showAppProc();


    }
    public void close(){
        WindowEvent cw = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(cw);
    }
    private void createTable(){


        table1.setModel(new DefaultTableModel(
                data,
                new String[]{"PatientNumber", "Date", "Start time", "End Time", "Appointment Type"})

                        {
                            @Override
                            public boolean isCellEditable(int row, int column) {

                                return false;
                            }
                        }
        );
    }
    public static void main(String[] args) {


        JFrame frame = new JFrame("Dentist");
        frame.setContentPane(new Dentist().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }





    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == button1){
            String txt =textArea1.getText().trim();

            if(!txt.equals("")) {
                updateTable(Integer.parseInt(textArea1.getText()));
            }
            else{
                data = getData("select * from Appointments where DentistNO = 10006");
                createTable();
            }
        }


    }
}
