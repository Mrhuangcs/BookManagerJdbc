package com.hr.dao.impl;

import com.hr.dao.UserDao;
import com.hr.entry.User;
import com.hr.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    @Override
    public User login(String userName, String password) throws Exception {

        String sql="select * from user where user_name=? and password=?";
        Connection con = JdbcUtil.getCon();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,userName);
        ps.setString(2,password);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()){
            int user_id = resultSet.getInt("user_id");
            String user_name = resultSet.getString("user_name");
            String password1 = resultSet.getString("password");
            int gender = resultSet.getInt("gender");
            String classes=resultSet.getString("classes");
            double balance = resultSet.getDouble("balance");
            int borrow_book = resultSet.getInt("borrow_book");
            User user = new User(user_id, userName, password1, gender,classes,balance,borrow_book);
            JdbcUtil.CloseCon(con);
            return user;
        }


        return null;
    }

    @Override
    public boolean register(User user) throws Exception {
        String sql="insert into user values(null,?,?,?,?,?,?)";
        Connection con = JdbcUtil.getCon();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,user.getUserName());
        ps.setString(2,user.getPassword());
        ps.setInt(3,user.getGender());
        ps.setString(4,user.getClasses());
        ps.setDouble(5,user.getBalance());
        ps.setInt(6,user.getBorrowBook());
       if( ps.executeUpdate()>0){
           return true;
       }
        return false;
    }

    @Override
    public boolean isExistUser(String userName) throws Exception {
        String sql="select user_name from user where user_name=?";
        Connection con = JdbcUtil.getCon();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,userName);
        if (ps.executeQuery().next()){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateBalance(Double balance,Integer userId,Connection connection) throws Exception {
        String sql="update `user` set balance=balance+? where user_id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDouble(1,balance);
        ps.setInt(2,userId);
       if(ps.executeUpdate()>0){
           return true;
       }
       return false;
    }

    @Override
    public boolean balanceIsEnough(String userName, Double bookPrice,Connection connection) throws Exception {
        String sql="select user_name from user where balance<? and user_name=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDouble(1,bookPrice);
        ps.setString(2,userName);
        if(ps.executeQuery().next()){
            return false;
        }
        return true;
    }

    @Override
    public ResultSet findUserBalance(String userName) throws Exception {
        String sql="select balance from user where user_name=?";
        Connection connection = JdbcUtil.getCon();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,userName);
        return ps.executeQuery();
    }

    @Override
    public ResultSet findAllUser() throws Exception {
        String sql="select * from user";
        Connection con = JdbcUtil.getCon();
        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeQuery();
    }

    @Override
    public boolean updateBorrowBookNumber(Integer borrowNumber,String userName,Connection connection) throws SQLException {
        String sql="update user set borrow_book=borrow_book+? where user_name=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,borrowNumber);
        ps.setString(2,userName);
        if(ps.executeUpdate()>0){
            return true;
        }
        return false;

    }

    @Override
    public boolean deleteUser(String userName, Connection connection) throws SQLException {
        String sql="delete from user where user_name=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,userName);
        if(ps.executeUpdate()>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(User user,String userName, Connection connection) throws SQLException {
        String sql="update user set user_name=?,password=?,gender=?,classes=? where user_name=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,user.getUserName());
        ps.setString(2,user.getPassword());
        ps.setInt(3,user.getGender());
        ps.setString(4,user.getClasses());
        ps.setString(5,userName);
        if(ps.executeUpdate()>0){
            return true;
        }
        return false;
    }
    @Override
    public boolean updateUserPassword(Integer userId, String password,Connection connection) throws SQLException {
        String sql="update user set password=? where user_id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,password);
        ps.setInt(2,userId);
        if(ps.executeUpdate()>0){
            return true;
        }
        return false;
    }

}
