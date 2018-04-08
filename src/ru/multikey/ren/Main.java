package ru.multikey.ren;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/test_db?useSSL=false";
        String user = "root";
        String pass = "fer1nan2s";
        Class.forName("com.mysql.jdbc.Driver");

        try(Connection conn = DriverManager.getConnection(url, user, pass)) {
            Statement stat = conn.createStatement();
            stat.execute("DROP TABLE IF EXISTS Books");
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS Books (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, dt DATE, PRIMARY KEY (id))");
            stat.executeUpdate("INSERT INTO Books (name) VALUES ('Inferno')");
            stat.executeUpdate("INSERT INTO Books (name) VALUES ('Davinchi')");
            stat.executeUpdate("INSERT INTO Books (name) VALUES ('Solomon Key')");

            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //PreparedStatement preparedStatement = conn.prepareStatement("", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet res = statement.executeQuery("SELECT * FROM Books");
            while (res.next()){
                System.out.println(res.getInt("id"));
                System.out.println(res.getString("name"));
            }

            res.last();
            res.updateString("name", "new Val");
            res.updateRow();

            res.moveToInsertRow();
            res.updateString("name", "inserted row");
            res.insertRow();

            res.absolute(2);
            res.deleteRow();

            res.beforeFirst();
            while(res.next()){
                System.out.println(res.getInt("id"));
                System.out.println(res.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}