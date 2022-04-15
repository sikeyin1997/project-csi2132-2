import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AppointmentProcedure extends JFrame{
    private JTextField pn;
    private JTextField description;
    private JTextField date;
    private JTextField feeId;
    private JTextField pc;
    private JTextField tooth;
    private JTextField pt;
    private JTextField amount;
    private JTable table1;
    private JButton updateButton;
    private JButton New;
    private JButton back;
    private JPanel Main;
    private JButton OKButton;
    // selected row
    int srow;
    Connection myConn;
    Statement myStmnt;

    // for collecting data
    ArrayList<Object> data1 = new ArrayList<>();
    int i1 = 0;
    // previous row
    int prcRow =0;
    // table data
    Object [][] data;

    // patient number
    int pNumber;

    public AppointmentProcedure(int row, int patientNumber) {

        setTitle("AppointmentProcedure");
        srow = row;
        // establish connection
        connection();
        String query = "select * from AppointmentProcedure where PatientNumber ="+  patientNumber;

        // fetch data from database
        data = getData(query);

        // create table fetched
        createTable();
        pNumber =patientNumber;

        setContentPane(Main);
        setMinimumSize(new Dimension(700, 474));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                fillForm();
            }
        });
        New.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // clear all textfields
                pn.setText("");
                pt.setText("");
                pc.setText("");
                description.setText("");
                date.setText("");
                tooth.setText("");
                amount.setText("");
                feeId.setText("");
            }
        });
        OKButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                insertIntoDb();
            }
        });
        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                updateDb();
            }
        });
    }

    private void updateDb(){

        String spn = pn.getText();
        String spc = pc.getText();
        String sdate = date.getText();
        String sdescription = description.getText();
        String sfeeId = feeId.getText();
        String sTooth = tooth.getText();
        String sAmount = amount.getText();
        String spt = pt.getText();

        try {
            myStmnt.executeUpdate("update AppointmentProcedure set PatientNumber='"+ spn +"', Date ='"+ sdate +"',ProcedureCode='"+spc+"',ProcedureType ='"+spt+"'," +
                    " Description='"+sdescription+"',Amount='"+ sAmount+"',Tooth='"+sTooth+"',feeID='"+sfeeId+"' where PatientNumber='"+ spn +"' and Date ='"+ sdate +"' ");
            JOptionPane.showMessageDialog(null, "updated");
        }
        catch (Exception e){

            e.printStackTrace();
        }

    }

    private void insertIntoDb(){

        if(pn.getText().equals("") || pNumber != Integer.parseInt(pn.getText())){

            JOptionPane.showMessageDialog(this, "Patient Number not valid !", "ERROR",JOptionPane.PLAIN_MESSAGE);
        }
        String spn = pn.getText();
        String spc = pc.getText();
        String sdate = date.getText();
        String sdescription = description.getText();
        String sfeeId = feeId.getText();
        String sTooth = tooth.getText();
        String sAmount = amount.getText();
        String spt = pt.getText();

        //"insert into AppointmentProcedure values(" + spn + "," + sdate + ","+spc+","+spt+","+sdescription+","+ sAmount+","+sTooth+","+sfeeId+")"
        try {
            myStmnt.executeUpdate("insert into AppointmentProcedure values('"+ spn +"','"+ sdate +"','"+spc+"','"+spt+"','"+sdescription+"','"+ sAmount+"','"+sTooth+"','"+sfeeId+"')");
            JOptionPane.showMessageDialog(null, "insert succeed");
        }
        catch (Exception e){

            e.printStackTrace();
        }
    }

    private void fillForm(){


        if(table1.getValueAt(table1.getSelectedRow(), 0) != null) {
            pn.setText(String.valueOf(Integer.parseInt((String) table1.getValueAt(table1.getSelectedRow(), 0))));
        }
        else
        {
            pn.setText("");
        }
        if(table1.getValueAt(table1.getSelectedRow(), 1) != null) {
            date.setText((String) table1.getValueAt(table1.getSelectedRow(), 1));
        }
        else
        {
            date.setText("");
        }
        if(table1.getValueAt(table1.getSelectedRow(), 2) != null) {
            pc.setText(String.valueOf(Integer.parseInt((String) table1.getValueAt(table1.getSelectedRow(), 2))));
        }
        else
        {
            pc.setText("");
        }
        if(table1.getValueAt(table1.getSelectedRow(), 3) != null) {
            pt.setText((String) table1.getValueAt(table1.getSelectedRow(), 3));
        }
        else
        {
            pt.setText("");
        }
        if(table1.getValueAt(table1.getSelectedRow(), 4) != null) {
            description.setText(table1.getValueAt(table1.getSelectedRow(), 4).toString());
        }
        else
        {
            description.setText("");
        }
        if(table1.getValueAt(table1.getSelectedRow(), 5) != null) {
            amount.setText(String.valueOf(Integer.parseInt((String) table1.getValueAt(table1.getSelectedRow(), 5))));
        }
        else
        {
            amount.setText("");
        }
        if(table1.getValueAt(table1.getSelectedRow(), 6) != "") {
            tooth.setText(table1.getValueAt(table1.getSelectedRow(), 6).toString());
        }
        else
        {
            tooth.setText("");
        }
        if(table1.getValueAt(table1.getSelectedRow(), 7) != "") {
            feeId.setText(String.valueOf(Integer.parseInt((String) table1.getValueAt(table1.getSelectedRow(), 7))));
        }
        else
        {
            feeId.setText("");
        }


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

    private Object [][]  getData(String query){

        // get connection
        try {


            Statement myStmnt = myConn.createStatement();
            ResultSet myRes = myStmnt.executeQuery(query);
            int j =0;
            while (myRes.next()){
                data1.add(myRes.getString("PatientNumber")) ;
                data1.add(myRes.getString("Date"));
                data1.add(myRes.getString("ProcedureCode"));
                data1.add(myRes.getString("ProcedureType"));
                data1.add(myRes.getString("Description"));
                data1.add(myRes.getString("Amount"));
                data1.add(myRes.getString("Tooth"));
                data1.add(myRes.getString("feeID"));
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



    private void createTable(){


        table1.setModel(new DefaultTableModel(
                                data,
                                new String[]{"PatientNumber", "Date", "ProcedureCode", "ProcedureType", "Description","Amount", "Tooth", "feeID" })

                        {
                            @Override
                            public boolean isCellEditable(int row, int column) {

                                return false;
                            }
                        }
        );
    }
}
