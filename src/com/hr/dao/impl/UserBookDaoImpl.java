package com.hr.dao.impl;

import com.hr.dao.UserBookDao;
import com.hr.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserBookDaoImpl implements UserBookDao {
    @Override
    public int deleteUserBorrowBook(String bookName,String userName,Connection connection) throws Exception {
        String sql="delete FROM user_book WHERE book_id=(SELECT book_id FROM book WHERE book_name=?) AND user_id=(SELECT user_id FROM `user` WHERE user_name=?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,bookName);
        ps.setString(2,userName);
        return ps.executeUpdate();
    }

    public static void main(String[] args) throws Exception {
       UserBookDao userBookDao=new UserBookDaoImpl();
        int i = userBookDao.deleteUserBorrowBook("高等数学", "jing", JdbcUtil.getCon());
        System.out.println(i);
    }
    @Override
    public boolean findIsExistBorrowBook(String userName, String bookName,Connection connection) throws Exception {
        String sql="SELECT * FROM user_book WHERE book_id=(SELECT book_id FROM book WHERE book_name=?) AND user_id=(SELECT user_id FROM `user` WHERE user_name=?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,bookName);
        ps.setString(2,userName);
        ResultSet set = ps.executeQuery();
        if(set.next()){
            return true;
        }
        return false;
    }

    @Override
    public boolean findIsExistBorrowBook(String bookName, Connection connection) throws Exception {
        String sql="SELECT * FROM user_book WHERE book_id=(SELECT book_id FROM book WHERE book_name=?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,bookName);
        ResultSet set = ps.executeQuery();
        if(set.next()){
            return true;
        }
        return false;
    }

    @Override
    public boolean addUserBorrowBook(Integer userId,Integer bookId,int day,Connection connection) throws Exception {
        String sql="insert into user_book values(null,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,userId);
        ps.setInt(2,bookId);
        ps.setInt(3,day);
        if( ps.executeUpdate()>0){
            return true;
        }
        return false;
    }

    @Override
    public ResultSet findUserBorrowBook(String userName, Connection connection) throws Exception {
        String sql="select * from user_book where user_id=(SELECT user_id FROM `user` WHERE user_name=?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,userName);
        return ps.executeQuery();
    }

}
