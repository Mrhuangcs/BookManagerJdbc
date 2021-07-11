package com.hr.dao;

import com.hr.entry.Book;

import java.sql.Connection;
import java.sql.ResultSet;

public interface BookDao {
    int addBook(Book book,Connection connection) throws Exception;
    int deleteBook(Integer bookId,Connection connection) throws Exception;
    int updateBook(Book book,Connection connection) throws Exception;
    int updateBorrowBookNumber(String bookName,Connection connection) throws Exception;
    int updateReturnBookNumber(String bookName,Connection connection) throws Exception;
    ResultSet findAllBook(Connection connection) throws Exception;
    ResultSet findBookByBookName(String bookName,Connection connection) throws Exception;
    ResultSet findBookByBookId(Integer bookId,Connection connection) throws Exception;
    ResultSet RankingBook() throws Exception;



}
