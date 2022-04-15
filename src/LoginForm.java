import entity.User;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Locale;

public class LoginForm extends JDialog {
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnOK;
    private JButton btnCancel;
    private JPanel loginPanel;

    public User user;

    public LoginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = getAuthenticatedUser(email, password);

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


    private User getAuthenticatedUser(String email, String password) {
        User user = null;

        Statement st = null;
        ResultSet rs = null;

        try {
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Connection db = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project",
                    "root", "Tom_yin0818");
            st = db.createStatement();
            rs = st.executeQuery("SELECT * FROM project.user");
            ResultSetMetaData rsMetaData = rs.getMetaData();//new
            rsMetaData.getColumnCount();
            System.out.println(" Connection built !");//connection to database is built.

            //panel(db);

            while (rs.next()) {
                System.out.print("Columns returned: \n");
                System.out.print("\t" + rs.getString(1));
                System.out.print("\t" + rs.getString(2));
                System.out.print("\t" + rs.getString(3));
                System.out.print("\t" + rs.getString(4));
                System.out.print("\t" + rs.getString(5));
                System.out.print("\t" + rs.getString(6));
                System.out.print("\t" + rs.getString(7));
                System.out.print("\t" + rs.getString(8));
                System.out.print("\t" + rs.getString(9));
                System.out.print("\t" + rs.getString(10));
                System.out.print("\t" + rs.getString(11));
                System.out.print("\t" + rs.getString(12));
                System.out.print("\t" + rs.getString(13));
                System.out.print("\t" + rs.getString(14));
                System.out.print("\t" + rs.getString(15));

                System.out.println();
            }

            rs.close();
            st.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


        return user;
    }

    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm(null);
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
