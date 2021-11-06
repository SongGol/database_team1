package database;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Connector {
    Connection connection;

    public void connect() {
        this.connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String usr = "adm";
            String passwd = "database";
            String url = "jdbc:mysql://49.50.167.99:3306/company?autoReconnection=true";
            this.connection = DriverManager.getConnection(url, usr, passwd);
            System.out.println("SQL 연결 성공");
        } catch (SQLException e) {
            System.out.println("db 연결 실패");
            System.out.println(e);
            e.printStackTrace();
            e.getErrorCode();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
            System.out.print(e);
        }
    }

    public boolean modify(String sql) {
        Statement statement = null;
        boolean rs;

        try {
            statement = this.connection.createStatement();
            rs = statement.execute(sql);
            return rs;
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            return false;
        }
    }


    public ArrayList<HashMap<String, Object>> search(String sql) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        try {
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();


            ResultSetMetaData md = rs.getMetaData();
            int column = md.getColumnCount();
            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<String, Object>();
                for (int i = 1; i <= column; i++) {
                    if (rs.getObject(i) == null) {
                        row.put(md.getColumnName(i), " - ");
                    } else {
                        row.put(md.getColumnName(i), rs.getObject(i).toString());
                    }
                }
                list.add(row);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }

    public void close() {
        try {
            if (this.connection != null) {
                System.out.println("안뇽DB");
                this.connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}