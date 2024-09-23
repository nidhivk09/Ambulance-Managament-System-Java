import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Add_Update {
    private JButton ADDAMBULANCEDATAButton;
    private JButton UPDATEAMBULACEDATAButton;
    private JPanel Choice;

    public Add_Update(JFrame parent) {
        super();
        parent.setTitle("CHOICE ");
        parent.setContentPane(Choice);
        parent.setMinimumSize(new Dimension(1100, 800));
        parent.setLocationRelativeTo(parent);
        parent.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        parent.setVisible(true);
        ADDAMBULANCEDATAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }



        public static void main(String[] args) {
        Add_Update add=new Add_Update(null);
    }



}



