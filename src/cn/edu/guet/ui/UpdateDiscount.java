/*
 * Created by JFormDesigner on Mon May 10 01:03:56 CST 2021
 */

package cn.edu.guet.ui;

import java.awt.*;
import javax.swing.*;

/**
 * @author 1
 */
public class UpdateDiscount extends JFrame {
    public UpdateDiscount() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        button1 = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        label1 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button1 ----
        button1.setText("\u66f4\u65b0\u6298\u6263");
        contentPane.add(button1);
        button1.setBounds(new Rectangle(new Point(610, 400), button1.getPreferredSize()));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(50, 90, 635, 295);

        //---- label1 ----
        label1.setText("\u4eca\u65e5\u6298\u6263");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 20f));
        contentPane.add(label1);
        label1.setBounds(300, 30, 235, 40);

        contentPane.setPreferredSize(new Dimension(715, 515));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton button1;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
