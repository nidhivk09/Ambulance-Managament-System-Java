import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class Registration_Form extends JDialog {
    private JTextField tfName;
    private JTextField tfPhone;
    private JTextField tfAddress;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel registerPanel;

    public Registration_Form(JFrame parent){

        super(parent);
        setTitle("CREATE NEW ACCOUNT");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(1100,900));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();

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

    private void registerUser() {
        String name=tfName.getText();
        String phone=tfPhone.getText();
        String address=tfAddress.getText();
        String password=String.copyValueOf(pfPassword.getPassword());
        String confirmPassword=String.copyValueOf(pfConfirmPassword.getPassword());


        if (name.isEmpty()|| phone.isEmpty()||address.isEmpty()||password.isEmpty()){
            JOptionPane.showMessageDialog(this,"PLEASE ENTER ALL FIELDS","TRY AGAIN",JOptionPane.ERROR_MESSAGE);
            return;

        }

        if (!password.equals(confirmPassword)){
            JOptionPane.showMessageDialog(this,"PASSWORD DOESN'T MATCH","TRYAGAIN",JOptionPane.ERROR_MESSAGE);
            return;

        }


       user= addUserToDatabase(name,phone,address,password);

        if (user!=null){
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(this,"FAILED TO REGISTER NEW USER","TRY AGAIN",JOptionPane.ERROR_MESSAGE);
        }
    }


    public User user;

    private User addUserToDatabase(String name,String phone, String address, String password) {

        User user = null;

        final String DB = "jdbc:mysql://localhost/users?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "mysql123#";
        try {
            Connection conn = DriverManager.getConnection(DB, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql="INSERT INTO users(name,phone,address,password)"+"VALUES(?,?,?,?)";
            PreparedStatement preparedStatement= conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,phone);
            preparedStatement.setString(3,address);
            preparedStatement.setString(4,password);

            int addedRows=preparedStatement.executeUpdate();
            if (addedRows>0){
                user=new User();
                user.name=name;
                user.phone=phone;
                user.address=address;
                user.password=password;

            }
            stmt.close();
            conn.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;

    }




    public static void main(String[] args){
        Registration_Form myForm=new Registration_Form(null);

        User user=myForm.user;
        if (user != null) {

            System.out.println("SUCCESFUL REGISTRATION OF: "+user.name);

        }
        else{
            System.out.println("REGISTRATION CANCELED");
        }

        }

        LoginForm login1=new LoginForm(null);
        login1.setVisible(true);


}
