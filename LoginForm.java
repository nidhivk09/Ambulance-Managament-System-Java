import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog{
    private JTextField tfPhone;
    private JPasswordField pfPassword;
    private JButton btnOK;
    private JButton btnCancel;
    private JPanel loginPanel;

    public LoginForm(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(1100,800));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);



        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phone=tfPhone.getText();
                String password=String.valueOf(pfPassword.getPassword());
                user=getAuthenticatedUser(phone,password);
                if (user!=null){
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(LoginForm.this,"PHONE OR PASSWORD INVALID","TRY AGAIN",JOptionPane.ERROR_MESSAGE);
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
    public User user;
    private User getAuthenticatedUser(String phone,String password){
        User user=null;

        final String DB="jdbc:mysql://localhost/users?serverTimezone=UTC";
        final String USERNAME="root";
        final String PASSWORD="mysql123#";

        try{
            Connection conn= DriverManager.getConnection(DB,USERNAME,PASSWORD);
            Statement stmt=conn.createStatement();
            String sql ="SELECT * FROM users WHERE phone=? AND password=?";
            PreparedStatement preparedStatement= conn.prepareStatement(sql);
            preparedStatement.setString(1,phone);
            preparedStatement.setString(2,password);

            if(conn ==null){
                System.out.println("NO CONNECTION");
            }


            ResultSet resultSet=preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.phone = resultSet.getString("phone");
                user.password = resultSet.getString("password");
            }


            stmt.close();
            conn.close();




        }
        catch(Exception e){
            e.printStackTrace();
        }

        return user;

    }

    public static void main(String[] args) {
        LoginForm loginForm=new LoginForm(null);
        User user= loginForm.user;

        if (user != null){
            System.out.println("Successful Authentication of: "+user.name);
            System.out.println("        Phone: "+user.phone);

        }
        else{
            System.out.println("Authentication canceled");
        }


    }

    public static class User {
        public String name;
        public String phone;
        public String password;

    }
}
//helllo this is to check if code edit is visible
