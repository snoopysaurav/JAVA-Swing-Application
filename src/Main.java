import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class Main extends JFrame {
    JPanel top, bottom;
    JLabel id, fname, lname, age;
    JTextField idText, fnameText, lnameText, ageText;
    JButton submit, clear, delete, update, show;

    public Main() {
            this.setTitle("Java CRUD Application");
            this.setLayout(new BorderLayout(10,10));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//            set margin of the window
            ((JPanel) this.getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));


            top = new JPanel();
            bottom = new JPanel();
            top = new JPanel();


            id = new JLabel(" ID :");
            fname = new JLabel(" Firstname :");
            lname = new JLabel(" Lastname :");
            age = new JLabel(" Age :");

            idText = new JTextField(25);
            fnameText = new JTextField(25);
            lnameText = new JTextField(25);
            ageText = new JTextField(25);

//          ---------- BUTTONS ---------
            submit = new JButton("Submit");
            submit.setFocusable(false);
            submit.addActionListener(e -> dbQuery(buildInsertQuery()));

            clear = new JButton("Clear");
            clear.setFocusable(false);
            clear.addActionListener(e ->{
                idText.setText("");
                fnameText.setText("");
                lnameText.setText("");
                ageText.setText("");
            });

            show = new JButton("Show Entries");
            show.setFocusable(false);
            show.addActionListener(e->{
                new Entries();
            });

            update= new JButton("Update");
            update.setFocusable(false);
            update.addActionListener(e->{
                dbQuery(buildUpdateQuery());
            });

            delete= new JButton("Delete");
            delete.setFocusable(false);
            delete.addActionListener(e -> {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Student ID"));
                dbQuery(buildDeleteQuery(id));
            });


//          ------ PANELS ----------

            top.setLayout(new GridLayout(5,2,10,10));

            top.add(id);
            top.add(idText);
            top.add(fname);
            top.add(fnameText);
            top.add(lname);
            top.add(lnameText);
            top.add(age);
            top.add(ageText);
            top.add(submit);
            top.add(clear);

            bottom.setLayout(new GridLayout(1,3,20,10));
            bottom.add(show);
            bottom.add(update);
            bottom.add(delete);

            this.add(top, BorderLayout.NORTH);
            this.add(bottom, BorderLayout.SOUTH);
            this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        }
        private String buildInsertQuery() {
            return "INSERT INTO student (id, fname, lname, age) VALUES (" +
                    idText.getText() + ", '" + fnameText.getText() + "', '" + lnameText.getText() + "', " + ageText.getText()
                    + ")";
        }
        private String buildDeleteQuery(int id){
            return  "DELETE FROM student WHERE id ="+ id;
        }
        private String buildUpdateQuery() {
        return "UPDATE student SET fname = '" + fnameText.getText() +
                "', lname = '" + lnameText.getText() +
                "', age = " + ageText.getText() +
                " WHERE id = " + idText.getText();
        }

        private void dbQuery(String query) {
            String url = "jdbc:mysql://localhost:3306/javadb";
            String user = "root";
            String password = "";

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();

                int affectedRows = stmt.executeUpdate(query);
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Query Executed Successfully.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
        public static void main(String[] args) {
            new Main();
        }
    }