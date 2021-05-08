/*
 * Created by JFormDesigner on Fri Apr 23 21:44:20 CST 2021
 */

package cn.edu.guet.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author 1
 */
public class MainForm extends JFrame {
    public MainForm() {
        initComponents();
    }

    public Object[][] queryData() {
        //因为Swing里也有一个组件叫List
        java.util.List<Users> list = new ArrayList<Users>();
        Connection conn = null;
        String url = "jdbc:oracle:thin:@120.77.242.136:1521:orcl";
        Statement stmt = null;//SQL语句对象，拼SQL
        String sql = "SELECT * FROM users";
        System.out.println("即将执行的sql：" + sql);
        ResultSet rs = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");//
            conn = DriverManager.getConnection(url, "scott", "tiger");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {//rs.next的作用？让游标向下移动
                Users user = new Users();//每次循环，如果user对象，没有放入容器，那么user就是一个”垃圾“
                user.setId(rs.getInt("ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                list.add(user);
            }
        } catch (ClassNotFoundException ee) {
            ee.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //释放资源：数据库连接很昂贵
            try {
                rs.close();
                stmt.close();
                conn.close();//关连接
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        data = new Object[list.size()][head.length];
        //把集合里的数据放入Obejct这个二维数组
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < head.length; j++) {
                data[i][0] = list.get(i).getId();
                data[i][1] = list.get(i).getUsername();
                data[i][2] = list.get(i).getPassword();
            }
        }
        return data;
    }

    private void initComponents() {
        JMenuBar mb = new JMenuBar();
        JMenu mHero = new JMenu("用户管理");
        JMenu mItem = new JMenu("商品管理");
        // 菜单项
        mHero.add(new JMenuItem("添加用户"));
        JMenuItem viewUserMenuItem = new JMenuItem("查看用户");
        viewUserMenuItem.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("鼠标点了我");
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        scrollPane1.setVisible(true);//显示数据的表格
                        label1.setVisible(true);//用户信息的标签
                        label2.setVisible(false);
                        button1.setVisible(true);
                        button2.setVisible(true);
                        button3.setVisible(true);
                        delButton.setVisible(true);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                }
        );
        mHero.add(viewUserMenuItem);
        mHero.add(new JMenuItem("修改密码"));

        mItem.add(new JMenuItem("添加商品"));
        mItem.add(new JMenuItem("查看库存"));

        // 分隔符
        mHero.addSeparator();
        mHero.add(new JMenuItem("退出"));
        mb.add(mHero);
        mb.add(mItem);

        this.setJMenuBar(mb);
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        button1 = new JButton();
        button2 = new JButton();
        label2 = new JLabel();
        button3 = new JButton();
        delButton = new JButton();
        panel1 = new JPanel();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        textField3 = new JTextField();
        textField4 = new JTextField();
        textField5 = new JTextField();
        button5 = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("\u7528\u6237\u4fe1\u606f");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 54f));
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(285, 20), label1.getPreferredSize()));

        //---- button1 ----
        button1.setText("\u5237\u65b0");
        contentPane.add(button1);
        button1.setBounds(new Rectangle(new Point(370, 420), button1.getPreferredSize()));

        //---- button2 ----
        button2.setText("\u65b0\u589e");
        contentPane.add(button2);
        button2.setBounds(new Rectangle(new Point(470, 420), button2.getPreferredSize()));

        //---- label2 ----
        label2.setText("\u7535\u5b50\u5c0f\u5356\u94fa");
        label2.setFont(label2.getFont().deriveFont(label2.getFont().getSize() + 64f));
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(0, 729), label2.getPreferredSize()));

        //---- button3 ----
        button3.setText("\u4fee\u6539");
        contentPane.add(button3);
        button3.setBounds(new Rectangle(new Point(565, 420), button3.getPreferredSize()));

        //---- delButton ----
        delButton.setText("\u5220\u9664");
        contentPane.add(delButton);
        delButton.setBounds(new Rectangle(new Point(660, 420), delButton.getPreferredSize()));

        //======== panel1 ========
        {
            panel1.setLayout(null);

            //---- label5 ----
            label5.setText("\u7528\u6237ID\uff1a");
            label5.setFont(label5.getFont().deriveFont(label5.getFont().getSize() + 12f));
            panel1.add(label5);
            label5.setBounds(new Rectangle(new Point(40, 45), label5.getPreferredSize()));

            //---- label6 ----
            label6.setText("\u7528\u6237\u540d\uff1a");
            label6.setFont(label6.getFont().deriveFont(label6.getFont().getSize() + 12f));
            panel1.add(label6);
            label6.setBounds(40, 85, 97, 32);

            //---- label7 ----
            label7.setText("\u5bc6\u7801\uff1a");
            label7.setFont(label7.getFont().deriveFont(label7.getFont().getSize() + 12f));
            panel1.add(label7);
            label7.setBounds(45, 130, 97, 32);
            panel1.add(textField3);
            textField3.setBounds(155, 50, 125, 35);
            panel1.add(textField4);
            textField4.setBounds(155, 90, 125, 35);
            panel1.add(textField5);
            textField5.setBounds(155, 130, 125, 35);

            //---- button5 ----
            button5.setText("\u4fdd\u5b58");
            button5.setFont(button5.getFont().deriveFont(button5.getFont().getSize() + 12f));
            panel1.add(button5);
            button5.setBounds(new Rectangle(new Point(170, 210), button5.getPreferredSize()));
        }
        contentPane.add(panel1);
        panel1.setBounds(235, 110, 495, 295);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 449, 580, 275);

        contentPane.setPreferredSize(new Dimension(905, 510));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        panel1.setVisible(false);
        table1 = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(queryData(), head) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(tableModel);
        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(170, 115, 580, 275);
        scrollPane1.setVisible(false);//显示数据的表格
        label1.setVisible(false);//用户信息的标签
        label2.setVisible(true);
        button1.setVisible(false);
        button1.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DefaultTableModel tableModel = new DefaultTableModel(queryData(), head) {
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        table1.setModel(tableModel);
                    }
                }
        );
        button2.setVisible(false);
        button2.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        scrollPane1.setVisible(false);
                        panel1.setVisible(true);
                    }
                }
        );
        button3.setVisible(false);
        delButton.setVisible(false);
        delButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int count=table1.getSelectedRow();//获取你选中的行号（记录）
                        String id= table1.getValueAt(count, 0).toString();//读取你获取行号的某一列的值（也就是字段）
                        Connection conn = null;
                        String url = "jdbc:oracle:thin:@120.77.242.136:1521:orcl";
                        PreparedStatement pstmt = null;//SQL语句对象
                        String sql = "DELETE FROM users WHERE id=?";//占位符
                        System.out.println("即将执行的sql：" + sql);
                        try {
                            Class.forName("oracle.jdbc.driver.OracleDriver");//
                            conn = DriverManager.getConnection(url, "scott", "tiger");
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1, Integer.parseInt(id));
                            pstmt.executeUpdate();//删除数据
                        } catch (ClassNotFoundException ee) {
                            ee.printStackTrace();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } finally {
                            //释放资源：数据库连接很昂贵
                            try {
                                pstmt.close();
                                conn.close();//关连接
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }

                        }
                    }
                }
        );
        button5.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String id = textField3.getText();
                        String username = textField4.getText();
                        String password = textField5.getText();
                        Connection conn = null;
                        String url = "jdbc:oracle:thin:@120.77.242.136:1521:orcl";
                        PreparedStatement pstmt = null;//SQL语句对象
                        String sql = "INSERT INTO users VALUES(?,?,?)";//占位符
                        System.out.println("即将执行的sql：" + sql);
                        try {
                            Class.forName("oracle.jdbc.driver.OracleDriver");//
                            conn = DriverManager.getConnection(url, "scott", "tiger");
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1, Integer.parseInt(id));
                            pstmt.setString(2, username);
                            pstmt.setString(3, password);
                            pstmt.executeUpdate();//添加数据
                        } catch (ClassNotFoundException ee) {
                            ee.printStackTrace();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } finally {
                            //释放资源：数据库连接很昂贵
                            try {
                                pstmt.close();
                                conn.close();//关连接
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }

                        }
                    }
                }
        );
    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JButton button1;
    private JButton button2;
    private JLabel label2;
    private JButton button3;
    private JButton delButton;

    private JPanel panel1;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton button5;
    private JScrollPane scrollPane1;
    private JTable table1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private Object[][] data = null;
    private String head[] = {"id", "username", "password"};
}
