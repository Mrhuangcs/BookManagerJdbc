package com.hr.dao;

import com.hr.entry.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDao {

    User login(String userName, String password) throws Exception;
    boolean register(User user) throws Exception;
    boolean isExistUser(String userName) throws Exception;
    boolean updateBalance(Double balance, Integer userId, Connection connection) throws Exception;
    boolean balanceIsEnough(String userName,Double bookPrice,Connection connection) throws Exception;
    ResultSet findUserBalance(String userName) throws Exception;
    ResultSet findAllUser() throws Exception;
    boolean updateBorrowBookNumber(Integer borrowNumber,String userName,Connection connection) throws SQLException;
    boolean deleteUser(String userName,Connection connection) throws SQLException;
    boolean updateUser(User user,String  userName,Connection connection) throws SQLException;
    boolean updateUserPassword(Integer userId,String password,Connection connection) throws SQLException;
}
