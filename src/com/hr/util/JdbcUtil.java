package com.hr.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
    public static Connection getCon() throws Exception {
        String url="jdbc:mysql://localhost:3306/book_manager?useUnicode=true&characterEncoding=utf8&useSSL=false";
        String driver="com.mysql.jdbc.Driver";
        String userName="root";
        String password="123456";
        Class.forName(driver);
        Connection connection= DriverManager.getConnection(url, userName, password);
        return connection;

    }

    public static void CloseCon(Connection con) throws SQLException {
        if(con!=null) {
            con.close();
        }
    }

    public static void main(String[] args) {
        try {
            Connection con=getCon();
            System.out.println(con);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
