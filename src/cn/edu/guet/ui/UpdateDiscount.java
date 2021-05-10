/*
 * Created by JFormDesigner on Mon May 10 01:03:56 CST 2021
 */

package cn.edu.guet.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author 1
 */
public class UpdateDiscount extends JFrame {
    public static void main(String[] args) {
        new UpdateDiscount();
    }
    public UpdateDiscount() {
        initComponents();
    }
    //ȫ����ѯ
    public Object[][] queryData() {
        //��ΪSwing��Ҳ��һ�������List
        java.util.List<Goods> list = new ArrayList<Goods>();
        Connection conn = null;
        String url = "jdbc:oracle:thin:@47.113.217.47:1521:orcl";
        Statement stmt = null;//SQL������ƴSQL
        String sql = "select * from commodity";
        System.out.println("����ִ�е�sql��" + sql);
        ResultSet rs = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");//
            conn = DriverManager.getConnection(url, "scott", "tiger");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {//rs.next�����ã����α������ƶ�
                Goods goods = new Goods();//ÿ��ѭ�������user����û�з�����������ôuser����һ����������
                goods.setName(rs.getString("NAME"));
                goods.setSellingprice(rs.getFloat("SELLINGPRICE"));
                goods.setDiscount(rs.getFloat("DISCOUNT"));
                list.add(goods);
            }
        } catch (ClassNotFoundException ee) {
            ee.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //�ͷ���Դ�����ݿ����Ӻܰ���
            try {
                rs.close();
                stmt.close();
                conn.close();//������
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        data = new Object[list.size()][head.length];
        //�Ѽ���������ݷ���Obejct�����ά����
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < head.length; j++) {
                data[i][0] = list.get(i).getName();
                data[i][1] = list.get(i).getSellingprice();
                data[i][2] = list.get(i).getDiscount();
                data[i][3] = list.get(i).getSellingprice()*list.get(i).getDiscount();
            }
        }
        return data;
    }

    public void updateDiscount(){
        Connection conn = null;
        String url = "jdbc:oracle:thin:@47.113.217.47:1521:orcl";
        PreparedStatement pstmt = null;//SQL������
        String sql = "update commodity set discount=?";//ռλ��
        System.out.println("����ִ�е�sql��" + sql);
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");//
            conn = DriverManager.getConnection(url, "scott", "tiger");
            pstmt = conn.prepareStatement(sql);
            pstmt.setFloat(1, (float) 0.9);
            pstmt.executeUpdate();//�������
        } catch (ClassNotFoundException ee) {
            ee.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //�ͷ���Դ�����ݿ����Ӻܰ���
            try {
                pstmt.close();
                conn.close();//������
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
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
        button1.setText("\u66f4\u65b0\u6298\u6263");//�����ۿ�
        contentPane.add(button1);
        button1.setBounds(new Rectangle(new Point(610, 400), button1.getPreferredSize()));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(50, 90, 635, 295);

        //---- label1 ----
        label1.setText("\u4eca\u65e5\u6298\u6263");//�����ۿ�
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 20f));
        contentPane.add(label1);
        label1.setBounds(300, 30, 235, 40);

        //����¼�
        button1.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        //����
                        updateDiscount();
                        //��ѯ
                        DefaultTableModel tableModel = new DefaultTableModel(queryData(), head) {
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        table1.setModel(tableModel);
                    }
                }
        );

        contentPane.setPreferredSize(new Dimension(715, 515));
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton button1;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private Object[][] data = null;
    private String head[] = {"��Ʒ", "�ۼ�", "�ۿ�","�ۺ��"};
}
