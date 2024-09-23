import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class update_page extends JDialog {
    private JTextField driver_name;
    private JTextField driver_phone;
    private JTextField amb_no;
    private JButton UPDATEButton;
    private JTextField amb_availability;
    private JPanel mainPanel;

//    ButtonGroup bg = new ButtonGroup();
//    bg.add(yes_button);


    public update_page(JFrame parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(mainPanel);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register_user();



            }
        });
        setVisible(true);

    }
    void register_user(){
        String driv_name = driver_name.getText();
        String ambul_no = amb_no.getText();
        String driv_no = driver_phone.getText();
        String amb_avail = amb_availability.getText();
        if(driv_name.isEmpty()||ambul_no.isEmpty()||driv_no.isEmpty()||amb_avail.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please enter all fields","try again", JOptionPane.ERROR_MESSAGE);
            return;


        }
        if(driv_no.length() != 10){
            JOptionPane.showMessageDialog(this,"Phone number is not valid, try again","try again",JOptionPane.ERROR_MESSAGE);
            return;
        }
        user = addUserToDatabase(driv_name,ambul_no,driv_no,amb_avail);
        if(user!=null){
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(this,"Failed to register new driver","try again",JOptionPane.ERROR_MESSAGE);

        }
    };
    public User user;

    private User addUserToDatabase(String name, String amb_no, String driver_phone, String amb_avail){
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost:3306/ambulance_management_system";
        final String USERNAME = "root";
        final String PASSWORD = "JayJeeBell_311005";

        try{
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO ambulance_details(driver_name, ambulance_no, driver_phone, ambulance_availability)" + "VALUES(?,?,?,?)";
            PreparedStatement preparedstatement = conn.prepareStatement(sql);
            preparedstatement.setString(1, name);
            preparedstatement.setString(2,amb_no);
            preparedstatement.setString(3,driver_phone);
            preparedstatement.setString(4,amb_avail);


            int addedRows = preparedstatement.executeUpdate();
            if(addedRows > 0){
                user = new User();
                user.driver_name = name;
                user.ambulance_no = amb_no;
                user.driver_phone = driver_phone;
                user.ambulance_availability = amb_avail;

            }
            stmt.close();
            conn.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return user;


    }

    public void Update_page(){
        setContentPane(mainPanel);
        setTitle("UPDATE FORM");
        setSize(450,300);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

    }



    public static void main(String[] args) {
        update_page MainFr = new update_page(null);
        MainFr.Update_page();
        User user = MainFr.user;
        if(user!=null){
            System.out.println("Successful registration of " + user.driver_name);
        }
        else{
            System.out.println("registration cancelled");
        }


    }


}
