package com.hr.dao.impl;

import com.hr.dao.BookDao;
import com.hr.entry.Book;
import com.hr.util.JdbcUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookDaoImpl implements BookDao {
    @Override
    public int addBook(Book book,Connection connection) throws Exception {
        String sql="insert into book values(null,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,book.getBookName());
        ps.setString(2,book.getAuthor());
        ps.setDouble(3,book.getPrice());
        ps.setInt(4,book.getNumber());
        ps.setInt(5,book.getBorrow());
        return ps.executeUpdate();
    }

    @Override
    public int deleteBook(Integer bookId,Connection connection) throws Exception {
        String sql="delete from book where book_id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,bookId);
        return ps.executeUpdate();
    }

    @Override
    public int updateBook(Book book,Connection connection) throws Exception {
        String sql="update book set `book_name`=?,author=?,price=?,`number`=?,borrow=? where book_id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,book.getBookName());
        ps.setString(2,book.getAuthor());
        ps.setDouble(3,book.getPrice());
        ps.setInt(4,book.getNumber());
        ps.setInt(5,book.getBorrow());
        ps.setInt(6,book.getBookId());
        return ps.executeUpdate();
    }

    @Override
    public int updateBorrowBookNumber(String bookName,Connection connection) throws Exception {
        String sql="update book set `number`=`number`-1,borrow=borrow+1 where book_name=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,bookName);
        return ps.executeUpdate();
    }

    @Override
    public int updateReturnBookNumber(String bookName, Connection connection) throws Exception {
        String sql="update book set `number`=`number`+1 where book_name=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,bookName);
        return ps.executeUpdate();
    }

    @Override
    public ResultSet findAllBook(Connection connection) throws Exception {
        String sql="select * from book";
        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }

    @Override
    public ResultSet findBookByBookName(String bookName,Connection connection) throws Exception {
        String sql="select * from book where book_name=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,bookName);
        return ps.executeQuery();
    }

    @Override
    public ResultSet findBookByBookId(Integer bookId, Connection connection) throws Exception {
        String sql="select * from book where book_id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,bookId);
        return ps.executeQuery();
    }

    @Override
    public ResultSet RankingBook() throws Exception {
        String sql="select * from book order by borrow desc";
        Connection con = JdbcUtil.getCon();
        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeQuery();
    }
}
