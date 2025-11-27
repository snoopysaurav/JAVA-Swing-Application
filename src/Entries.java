import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Entries extends JFrame {
    DefaultTableModel model;
    Entries(){
        this.setTitle("Entries");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400,400);

//        TableModel Setup
        model = new DefaultTableModel();
        model.addColumn("Student Id");
        model.addColumn("Firstname");
        model.addColumn("Lastname");
        model.addColumn("Age");
        model.addColumn("Action");

//      JTable Setup
        JTable table = new JTable(model);
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);

        showQuery();

        this.add(scrollPane);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    private void showQuery() {
        String url = "jdbc:mysql://localhost:3306/javadb";
        String user = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM student");
            while(resultSet.next()){
                int id= resultSet.getInt(1);
                String fname = resultSet.getString(2);
                String lname = resultSet.getString(3);
                int age = resultSet.getInt(4);
                model.addRow(new Object[]{id,fname,lname,age});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
