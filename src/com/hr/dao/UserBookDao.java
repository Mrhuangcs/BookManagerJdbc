package com.hr.dao;

import java.sql.Connection;
import java.sql.ResultSet;

public interface UserBookDao {

   int deleteUserBorrowBook(String bookName, String userName,Connection connection) throws Exception;
   boolean findIsExistBorrowBook(String userName, String bookName, Connection connection) throws Exception;
   boolean findIsExistBorrowBook( String bookName, Connection connection) throws Exception;
   boolean addUserBorrowBook(Integer userId,Integer bookId,int day,Connection connection) throws Exception;
   ResultSet findUserBorrowBook(String userName, Connection connection) throws Exception;
}
