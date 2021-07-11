package com.hr.manager;

import com.hr.dao.BookDao;
import com.hr.dao.UserBookDao;
import com.hr.dao.UserDao;
import com.hr.dao.impl.BookDaoImpl;
import com.hr.dao.impl.UserBookDaoImpl;
import com.hr.dao.impl.UserDaoImpl;
import com.hr.entry.Book;
import com.hr.entry.User;
import com.hr.util.JdbcUtil;
import com.hr.util.NumberUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class BookManager {
    private UserDao userDao=new UserDaoImpl();
    private UserBookDao userBookDao=new UserBookDaoImpl();
    private BookDao bookDao=new BookDaoImpl();
    public  void UpdateBook(User user) throws Exception {
        int i=1;
        if(user.getUserName().equals("admin")){
            while (i==1){
                try {
                    Connection con = JdbcUtil.getCon();
                    System.out.println("----图书修改------");
                    findALLBooks();
                    Scanner scanner = new Scanner(System.in);
                    System.out.printf("请输入图书名：");
                    String bookName = scanner.nextLine();
                    ResultSet set = bookDao.findBookByBookName(bookName, con);
                    Book book = findBook(set);
                    if (book!=null){
                        System.out.printf("请输入修改后的图书名：");
                         bookName = scanner.nextLine();
                        System.out.printf("请输入修改后的作者名：");
                        String author = scanner.nextLine();
                        System.out.printf("请输入修改后的订阅单价：");
                        String str=scanner.nextLine();
                        double price = NumberUtil.isDouble(str);
                        System.out.printf("请输入修改后的数量：");
                        str=scanner.nextLine();
                        int number = NumberUtil.isInteger(str);
                        System.out.printf("请输入修改后的借阅量：");
                        str=scanner.nextLine();
                        int borrow = NumberUtil.isInteger(str);
                        book.setBookName(bookName);
                        book.setAuthor(author);
                        book.setPrice(price);
                        book.setNumber(number);
                        book.setBorrow(borrow);
                        int i1 = bookDao.updateBook(book, con);
                        System.out.println("修改成功！！");
                    }else {
                        System.out.println("该书籍不存在！！！！");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("再次修改请输入1   返回请输入0");
                Scanner scanner1 = new Scanner(System.in);
                String str=scanner1.nextLine();
                i = NumberUtil.isInteger(str);
                while (i!=1&&i!=0){
                    System.out.println("输入有误！！请输入数值1或0");
                    str=scanner1.nextLine();
                    i = NumberUtil.isInteger(str);
                }
            }
        }else {
            System.out.println("你不是管理员，没有该权限！！");
        }
    }

    public void deleteBook(User user) {
        int i=1;
        if(user.getUserName().equals("admin")){
            findALLBooks();
            while (i==1){
                try {
                    Connection con = JdbcUtil.getCon();
                    System.out.println("----图书删除------");
                    Scanner scanner = new Scanner(System.in);
                    System.out.printf("请输入需要删除的图书名：");
                    String bookName = scanner.nextLine();
                    ResultSet set = bookDao.findBookByBookName(bookName, con);
                    boolean isExistBorrowBook = userBookDao.findIsExistBorrowBook(bookName, con);
                    Book book = findBook(set);
                    if (book!=null&&!isExistBorrowBook){
                        System.out.println("该书籍存在，是否删除？  是请输入y,否请输入n  ");
                        String str = scanner.nextLine();
                        while (true){
                            if (str.equals("y")){
                                int i1 = bookDao.deleteBook(book.getBookId(), con);
                                if(i1>0){
                                    System.out.println("删除成功！！");
                                    break;
                                }
                            }else if (str.equals("n")){
                                System.out.println("取消删除成功");
                                break;
                            }else {
                                System.out.println("请输入正确指令！！");
                                Scanner scanner1 = new Scanner(System.in);
                                str = scanner1.nextLine();
                                while (!str.equals("y")&&!str.equals("n")){
                                    System.out.println("输入有误！！");
                                    str=scanner.nextLine();
                                }
                            }
                        }
                    }
                    else {
                        if (isExistBorrowBook){
                            System.out.println("无法删除此书籍，还有用户还未归还该书籍");
                        }else {
                            System.out.println("该书籍不存在！！");
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("再次删除请输入1   返回请输入0");
                Scanner scanner1 = new Scanner(System.in);
                String str=scanner1.nextLine();
                i = NumberUtil.isInteger(str);
                while (i!=1&&i!=0){
                    System.out.println("输入有误！！请输入数字1或0");
                    str=scanner1.nextLine();
                    i = NumberUtil.isInteger(str);
                }
            }
        }else {
            System.out.println("你不是管理员，没有该权限！！");
        }

    }
    public void addBooks(User user) {
        int i=1;
        if(user.getUserName().equals("admin")){
            while (i==1){
                System.out.println("----图书添加------");
                findALLBooks();
                Scanner scanner = new Scanner(System.in);
                System.out.printf("请输入图书名：");
                String bookName = scanner.nextLine();
                System.out.printf("请输入作者名：");
                String author = scanner.nextLine();
                System.out.printf("请输入订阅单价 天/元：");
                String str = scanner.nextLine();
                double price = NumberUtil.isDouble(str);
                System.out.printf("请输入数量：");
                str = scanner.nextLine();
                int number = NumberUtil.isInteger(str);
                Book book = new Book(null, bookName, author, price, number, 0);
                Connection con = null;
                try {
                    con = JdbcUtil.getCon();
                    ResultSet set = bookDao.findBookByBookName(bookName, con);
                    if (set.next()){
                        System.out.println("该书籍已经存在！！");
                    }else {
                        bookDao.addBook(book,con);
                        System.out.println("添加成功！！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("再次添加请输入1   返回请输入0");
                Scanner scanner1 = new Scanner(System.in);
                 str = scanner1.nextLine();
                i = NumberUtil.isInteger(str);
                while (i!=1&&i!=0){
                    System.out.println("输入有误！！请输入数字1或0");
                    str=scanner1.nextLine();
                    i = NumberUtil.isInteger(str);
                }

            }
        }else {
            System.out.println("你不是管理员，没有该权限！！");
        }
    }

    public void searchUserBorrowBook(User user) {
        findUserBorrowBook(user);
        System.out.println("返回请输入0");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int i = NumberUtil.isInteger(str);
        while (i!=0){
            System.out.println("输入有误！！");
            str = scanner.nextLine();
            i=NumberUtil.isInteger(str);
        }
    }

    public void findAllBooks() {
        System.out.println("---查询所有书籍------");
        findALLBooks();
        System.out.println("返回请输入0");
        Scanner scanner = new Scanner(System.in);
        String str=scanner.nextLine();
        int i = NumberUtil.isInteger(str);
        while (i!=0){
            System.out.println("输入有误！！");
            str=scanner.nextLine();
            i=NumberUtil.isInteger(str);
        }
    }

    public Book findBook(ResultSet bookSet) throws SQLException {
        Book book=null;
       if (bookSet.next()){
           System.out.println("序号        "+"书名            "+"作者       "+"借阅单价 天/元       "+"数量"+"        "+"借阅次数");
           int book_id = bookSet.getInt("book_id");
           String book_name = bookSet.getString("book_name");
           String author = bookSet.getString("author");
           double price = bookSet.getDouble("price");
           int number = bookSet.getInt("number");
           int borrow = bookSet.getInt("borrow");
            book = new Book(book_id, book_name, author, price, number, borrow);
           System.out.printf("%2d%12s%12s%12s%16d%14d\n",book.getBookId(),book.getBookName(),book.getAuthor()
           ,book.getPrice().toString(),book.getNumber(),book.getBorrow());
       }
       return book;
    }
    public void findALLBooks(){
        ResultSet allBook = null;
        try {
            Connection con = JdbcUtil.getCon();
            allBook = bookDao.findAllBook(con);
            System.out.println("序号        "+"书名            "+"作者       "+"借阅单价 天/元       "+"数量"+"        "+"借阅次数");
            while (allBook.next()) {
                int book_id = allBook.getInt("book_id");
                String book_name = allBook.getString("book_name");
                String author = allBook.getString("author");
                double price = allBook.getDouble("price");
                int number = allBook.getInt("number");
                int borrow = allBook.getInt("borrow");
                Book book = new Book(book_id, book_name, author, price, number, borrow);
                System.out.printf("%2d%12s%12s%12s%16d%14d\n",book.getBookId(),book.getBookName(),book.getAuthor()
                        ,book.getPrice().toString(),book.getNumber(),book.getBorrow());
            }
            JdbcUtil.CloseCon(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int findUserBorrowBook(User user){
        ResultSet userBook = null;
        int i=0;
        try {
            Connection con = JdbcUtil.getCon();
            userBook = userBookDao.findUserBorrowBook(user.getUserName(),con);
            System.out.println("序号        "+"书名            "+"作者       "+"借阅单价 天/元"+"     借阅天数");
            while (userBook.next()) {
                int bookId = userBook.getInt("book_id");
                ResultSet set = bookDao.findBookByBookId(bookId, con);
                if (set.next()){
                    String book_name = set.getString("book_name");
                    int day = userBook.getInt("day");
                    ResultSet bs = bookDao.findBookByBookName(book_name,con);
                    if(bs.next()){
                        i++;
                        int book_id = bs.getInt("book_id");
                        String author = bs.getString("author");
                        double price = bs.getDouble("price");
                        int number = bs.getInt("number");
                        int borrow = bs.getInt("borrow");
                        Book book = new Book(book_id, book_name, author, price, number, borrow);
                        System.out.printf("%2d%12s%12s%12s%15d\n",i,book.getBookName(),book.getAuthor()
                                ,book.getPrice().toString(),day);
                    }
                }
            }
            JdbcUtil.CloseCon(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public void borrowBooks(User user) {
        System.out.println("--------借书--------");
        int i=1;
        boolean is=true;
        Double total=0.0;
        while (i==1){
            findALLBooks();
            System.out.println("已借阅的书有:");
            findUserBorrowBook(user);
            Connection connection =null;
            System.out.println("需要借阅多少本书：");
            Scanner scanner1 = new Scanner(System.in);
            String str = scanner1.nextLine();
            int num = NumberUtil.isInteger(str);
            while (num>5){
                System.out.println("最多借阅五本书！！");
                str = scanner1.nextLine();
                num = NumberUtil.isInteger(str);
            }
            int a=num;
            try {
                connection=JdbcUtil.getCon();
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (num!=0){
                System.out.println("请输入你要添加书的名字：");
                Scanner scanner = new Scanner(System.in);
                String bookName=scanner.nextLine();

                try {
                    boolean isExistBorrowBook = userBookDao.findIsExistBorrowBook(user.getUserName(), bookName,connection);
                    ResultSet set = bookDao.findBookByBookName(bookName, connection);
                    if (set.next()){
                        if (isExistBorrowBook) {
                            System.out.println("该书籍已经借阅，请勿重复借阅！！");
                        } else {
                            int number = set.getInt("number");
                            if (number==0){
                                System.out.println("该书籍库存不足！！");

                            }else {
                                System.out.println("请输入你要借阅的天数：");
                                str=scanner.nextLine();
                                int day=NumberUtil.isInteger(str);
                                while (day>5){
                                    System.out.println("最多借阅5天！！");
                                    str=scanner.nextLine();
                                    day=NumberUtil.isInteger(str);
                                }
                                connection.setAutoCommit(false);
                                Double price = set.getDouble("price");
                                Double c=Double.parseDouble(String.valueOf(day));
                                price=price*c;
                                total+=price;
                                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                                String format = decimalFormat.format(price);
                                price=Double.parseDouble(format);
                                boolean b1 = userDao.balanceIsEnough(user.getUserName(), price, connection);
                                if (b1){
                                    boolean b2 = userDao.updateBalance(-price, user.getUserId(), connection);
                                    int i1 = bookDao.updateBorrowBookNumber(bookName, connection);
                                    ResultSet set1 = bookDao.findBookByBookName(bookName, connection);
                                    if (set1.next()){
                                        int bookId=set1.getInt("book_id");
                                        boolean b = userBookDao.addUserBorrowBook(user.getUserId(), bookId,day,connection);
                                        boolean b3 = userDao.updateBorrowBookNumber(1, user.getUserName(), connection);
                                        if (b&&i1>0&&b2&&b3) {
                                            num--;
                                        } else {
                                            System.out.println("添加失败！！请重新添加！！");
                                        }
                                    }
                                }else {
                                    System.out.println("余额不足，请充值");
                                    i=0;
                                    is=false;
                                    if(connection!=null){
                                        connection.rollback();
                                    }
                                    findUserBalance(user);
                                    break;
                                }
                            }
                        }
                    }else {
                        System.out.println("该书籍不存在，请输入正确的书籍名！！");
                    }
                }catch (Exception e) {
                    try {
                        if(connection!=null){
                            connection.rollback();
                        }
                        System.out.println("添加失败！！请重新添加！！");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            if(is){
                try {
                    if(connection!=null){
                        connection.commit();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if(a!=0){
                    System.out.println("添加成功！！");
                    System.out.println("已借阅的书有:");
                    findUserBorrowBook(user);
                    try {
                        ResultSet userBalance = userDao.findUserBalance(user.getUserName());
                        if (userBalance.next()) {
                            double balance = userBalance.getDouble("balance");
                            System.out.println("扣除"+total+"元，你的余额还剩：" + balance + "元");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("最少要借一本书！！");
                }
                System.out.println("再次借阅请输入1   返回请输入0");
                Scanner scanner = new Scanner(System.in);
                str = scanner.nextLine();
                i = NumberUtil.isInteger(str);
                while (i!=1&&i!=0){
                    System.out.println("输入有误！！请输入数字1或0");
                    str=scanner.nextLine();
                    i = NumberUtil.isInteger(str);
                }
            }

        }
    }

    public void returnBooks(User user){
        System.out.println("--------还书--------");
        int i=1;
        boolean is=true;
        Connection con = null;
        while (i==1){
            try {
                con=JdbcUtil.getCon();
                if (!userBookDao.findUserBorrowBook(user.getUserName(),con).next()){
                    System.out.println("没有借阅的书籍需要归还。");
                    System.out.println("返回请输入0");
                }else {
                    System.out.println("已借阅的书有:");
                    int borrowBook = findUserBorrowBook(user);
                    System.out.println("需要归还多少本书：");
                    Scanner scanner1 = new Scanner(System.in);
                    String str = scanner1.nextLine();
                    int num = NumberUtil.isInteger(str);
                    while (num>borrowBook){
                        System.out.println("数量超出了你为归还书的数量！！");
                        str = scanner1.nextLine();
                        num = NumberUtil.isInteger(str);
                    }
                    int a=num;
                    System.out.println("请输入你要归还的书的书名：");
                    while (num!=0){
                        Scanner scanner = new Scanner(System.in);
                        String bookName = scanner.nextLine();
                        boolean userBook=userBookDao.findIsExistBorrowBook(user.getUserName(), bookName, con);
                        if (userBook){
                            con.setAutoCommit(false);
                            userBookDao.deleteUserBorrowBook(bookName,user.getUserName(),con);
                            bookDao.updateReturnBookNumber(bookName,con);
                            num--;
                        }else {
                            System.out.println("该书籍名不存在，请输入正确的书籍名！！");
                        }
                    }if (a!=0){
                        System.out.println("书籍归还成功！");
                        boolean b = userDao.updateBorrowBookNumber(-a, user.getUserName(), con);
                        con.commit();
                    }else {
                        System.out.println("最低要归还一本书！！");
                    }
                    System.out.println("再次归还请输入1   返回请输入0");
                }
                Scanner scanner = new Scanner(System.in);
                String str = scanner.nextLine();
                i = NumberUtil.isInteger(str);
                while (i!=1&&i!=0){
                    System.out.println("输入有误！！请输入数字1或0");
                    str=scanner.nextLine();
                    i = NumberUtil.isInteger(str);
                }
            } catch (Exception e) {
                if(con!=null){
                    try {
                        con.rollback();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                e.printStackTrace();
            }
        }
    }
    public void rankingBook() {

        try {
            ResultSet set = bookDao.RankingBook();
            System.out.println("序号        "+"书名            "+"作者       "+"借阅单价 天/元       "+"数量"+"        "+"借阅次数");
            while (set.next()) {
                int book_id = set.getInt("book_id");
                String book_name = set.getString("book_name");
                String author = set.getString("author");
                double price = set.getDouble("price");
                int number = set.getInt("number");
                int borrow = set.getInt("borrow");
                Book book = new Book(book_id, book_name, author, price, number, borrow);
                System.out.printf("%2d%12s%12s%12s%16d%14d\n",book.getBookId(),book.getBookName(),book.getAuthor()
                        ,book.getPrice().toString(),book.getNumber(),book.getBorrow());
            }
            System.out.println("返回请输入0");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            int i = NumberUtil.isInteger(str);
            while (i!=0){
                System.out.println("输入有误！！");
                str = scanner.nextLine();
                i=NumberUtil.isInteger(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findUserBalance(User user) {
        ResultSet userBalance = null;
        try {
            userBalance = userDao.findUserBalance(user.getUserName());
            if (userBalance.next()){
                double balance = userBalance.getDouble("balance");
                System.out.println("你的余额还剩："+balance+"元");
                System.out.println("返回请输入0");
                Scanner scanner = new Scanner(System.in);
                String str = scanner.nextLine();
                int i = NumberUtil.isInteger(str);
                while (i!=0){
                    System.out.println("输入有误！！");
                    str = scanner.nextLine();
                    i=NumberUtil.isInteger(str);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findAllBorrowBook() {
        System.out.println("------借阅未归还的书籍----");
        Connection connection=null;
        try {
            ResultSet allUser = userDao.findAllUser();
            connection=JdbcUtil.getCon();
            while (allUser.next()){
                String user_name = allUser.getString("user_name");
                ResultSet set = userBookDao.findUserBorrowBook(user_name, connection);
                if (set.next()){
                    User user = new User();
                    System.out.println("用户 "+user_name+"未归还的书有：");
                    user.setUserName(user_name);
                    findUserBorrowBook(user);
                }
            }
            System.out.println("返回请输入0");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            int i = NumberUtil.isInteger(str);
            while (i!=0){
                System.out.println("输入有误！！");
                str = scanner.nextLine();
                i=NumberUtil.isInteger(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addBalance(User user) {
        System.out.println("------充值------");
        int i=1;
        while (i==1){
            Scanner scanner = new Scanner(System.in);
            System.out.printf("请输入充值金额：");
            double balance = scanner.nextDouble();
            Connection con = null;
            try {
                con = JdbcUtil.getCon();
                boolean b = userDao.updateBalance(balance, user.getUserId(), con);
                if (b){
                    System.out.println("充值成功！！");
                   ResultSet userBalance = userDao.findUserBalance(user.getUserName());
                    if (userBalance.next()){
                         balance = userBalance.getDouble("balance");
                        System.out.println("你的余额还剩："+balance+"元");
                        System.out.println("再次充值请输入1  返回请输入0");
                        Scanner scanner1 = new Scanner(System.in);
                        String str = scanner1.nextLine();
                        i = NumberUtil.isInteger(str);
                        while (i!=1&&i!=0){
                            System.out.println("输入有误！！请输入数字1或0");
                            str=scanner1.nextLine();
                            i = NumberUtil.isInteger(str);
                        }

                    }
                }
            } catch (Exception e) {
                System.out.println("充值失败！！");
                e.printStackTrace();
            }
        }
    }


}
