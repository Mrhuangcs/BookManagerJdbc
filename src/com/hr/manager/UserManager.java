package com.hr.manager;

import com.hr.dao.UserBookDao;
import com.hr.dao.UserDao;
import com.hr.dao.impl.UserBookDaoImpl;
import com.hr.dao.impl.UserDaoImpl;
import com.hr.entry.User;
import com.hr.util.JdbcUtil;
import com.hr.menu.Menu;
import com.hr.util.NumberUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

public class UserManager {

    private UserDao userDao=new UserDaoImpl();
    private UserBookDao userBookDao=new UserBookDaoImpl();
    public  void findAllUser(){
        findUser();
        System.out.println("返回请输入0");
        Scanner scanner = new Scanner(System.in);
        String str=scanner.nextLine();
        int i =NumberUtil.isInteger(str);
        while (i!=0){
            System.out.println("输入有误！！");
            i=NumberUtil.isInteger(str);
        }
    }
    public  void deleteUser(){
        System.out.println("--------删除用户-------");
        findUser();
        int i=1;
        while (i==1){
            Scanner scanner = new Scanner(System.in);
            System.out.printf("请输入需要删除的用户名：");
            String userName = scanner.nextLine();
            Connection con=null;
            try {
                 con = JdbcUtil.getCon();
                boolean existUser = userDao.isExistUser(userName);
                ResultSet borrowUser = userBookDao.findUserBorrowBook(userName, con);
                boolean f=true;
                if (borrowUser.next()){
                    f=false;
                }
                if (userName.equals("admin")){
                    System.out.println("不能删除管理员！！");
                }else {
                    if (existUser&&f){
                        System.out.println("该用户存在，是否删除用户："+userName+"？  是请输入y,否请输入n  ");
                        String str = scanner.nextLine();
                        while (true){
                            if (str.equals("y")){
                                boolean b = userDao.deleteUser(userName, con);
                                if(b){
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
                        System.out.println("再次删除请输入1   返回请输入0");
                        Scanner scanner1 = new Scanner(System.in);
                         str = scanner1.nextLine();
                        i = NumberUtil.isInteger(str);
                        while (i!=1&&i!=0){
                            System.out.println("输入有误！！请输入数字1或0");
                            str=scanner1.nextLine();
                            i = NumberUtil.isInteger(str);
                        }
                    }else {
                        if(f){
                            System.out.println("该用户名不存在！！");
                        }else {
                            System.out.println("无法删除该用户，该用户还有书籍未归还！");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
    public  void updateUser(){
        System.out.println("--------修改用户信息-------");
        findUser();
        int i=1;
        while (i==1){
            Scanner scanner = new Scanner(System.in);
            System.out.printf("请输入需要修改信息的用户名：");
            String userName = scanner.nextLine();
            Connection con=null;
            try {
                con = JdbcUtil.getCon();
                boolean existUser = userDao.isExistUser(userName);
                if(userName.equals("admin")){
                    System.out.println("管理员信息暂时无法修改，如若修改密码请到上一个菜单。");
                }else {
                    if (existUser){
                        Scanner scanner1 = new Scanner(System.in);
                        System.out.printf("请输入新的用户名：");
                        String newUserName = scanner1.nextLine();
                        System.out.printf("请输入新的密码：");
                        String password=scanner1.nextLine();
                        System.out.printf("请输入新的性别：");
                        String gender = scanner1.nextLine();
                        while (!gender.equals("男")&&!gender.equals("女")){
                            System.out.println("输入有误！！请输入正确的性别！！");
                            gender=scanner.nextLine();
                        }
                        System.out.printf("请输入新的班级：");
                        String classes=scanner.nextLine();
                        User user = new User();
                        int gen=1;
                        if (gender.equals("女")){
                            gen=0;
                        }
                        user.setUserName(newUserName);
                        user.setPassword(password);
                        user.setGender(gen);
                        user.setClasses(classes);
                        boolean b = userDao.updateUser(user, userName,con);
                        if (b){
                            System.out.println("修改成功！！");
                        }
                        System.out.println("再次修改请输入1   返回请输入0");
                        String str = scanner.nextLine();
                        i = NumberUtil.isInteger(str);
                        while (i!=1&&i!=0){
                            System.out.println("输入有误！！请输入数字1或0");
                            str=scanner.nextLine();
                            i = NumberUtil.isInteger(str);
                        }
                    }else {
                        System.out.println("该用户名不存在！！");
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void addUser() {
        System.out.println("------添加用户-------");
        int i=1;
        while (i==1){
            Scanner scanner = new Scanner(System.in);
            System.out.printf("请输入账号：");
            String userName=scanner.nextLine();
            System.out.printf("请输入密码：");
            String password=scanner.nextLine();
            System.out.printf("请输入性别：");
            String gender=scanner.nextLine();
            while (!gender.equals("男")&&!gender.equals("女")){
                System.out.println("输入有误！！请输入正确的性别！！");
                gender=scanner.nextLine();
            }
            System.out.printf("请输入班级：");
            String classes=scanner.nextLine();
            System.out.printf("请输入账号余额：");
            String str = scanner.nextLine();
            double balance= NumberUtil.isDouble(str);
            int i1=0;
            if(gender.equals("男")){
                i1=1;
            }
            User user = new User(null, userName, password, i1,classes, balance,0);
            try {
                if(userDao.isExistUser(userName)){
                    System.out.println("添加失败！！该用户已存在！！");
                }else {
                    if (userDao.register(user)){
                        System.out.println("添加成功！！");
                        System.out.println("需要再次添加输入1，  返回输入0");
                         str = scanner.nextLine();
                        i = NumberUtil.isInteger(str);
                        while (i!=1&&i!=0){
                            System.out.println("输入有误！！请输入数字1或0");
                            str=scanner.nextLine();
                            i = NumberUtil.isInteger(str);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void findUser(){
        try {
            ResultSet allUser = userDao.findAllUser();
            System.out.println("用户ID    "+"用户名          "+"性别       "+"班级       "+"账户余额     "+"未归还书籍数量");
            while (allUser.next()){
                int user_id = allUser.getInt("user_id");
                String user_name = allUser.getString("user_name");
                int gender = allUser.getInt("gender");
                String classes = allUser.getString("classes");
                double balance = allUser.getDouble("balance");
                int borrow_book = allUser.getInt("borrow_book");
                User user = new User(user_id, user_name, null, gender,classes, balance, borrow_book);
                String ge="男";
                if(gender==0){
                    ge="女";
                }
                System.out.printf("%2s%12s%12s%12s%12s%12d\n",user.getUserId(),user.getUserName(),ge,user.getClasses(),user.getBalance().toString(),user.getBorrowBook());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updatePassword(User user) {
        System.out.println("----修改密码------");
        int i=1;
        while (i==1){
            Scanner scanner = new Scanner(System.in);
            System.out.printf("请输入原来的密码：");
            String password = scanner.nextLine();
            if (password.equals(user.getPassword())){
                Connection con = null;
                try {
                    while (true){
                        System.out.printf("请输入新的密码：");
                        String newPassword = scanner.nextLine();
                        System.out.printf("再次输入新的密码：");
                        String newPassword1 = scanner.nextLine();
                        if (newPassword.equals(newPassword1)){
                            con=JdbcUtil.getCon();
                            userDao.updateUserPassword(user.getUserId(), newPassword, con);
                            Menu.user.setPassword(newPassword);
                            break;
                        }else {
                            System.out.println("前后密码不一致，请重新输入！！");
                        }
                    }
                    System.out.println("再次修改密码请输入1  返回请输入0");
                    String str = scanner.nextLine();
                    i = NumberUtil.isInteger(str);
                    while (i!=1&&i!=0){
                        System.out.println("输入有误！！请输入数字1或0");
                        str=scanner.nextLine();
                        i = NumberUtil.isInteger(str);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                System.out.println("密码错误！！");
            }
        }
    }
}
