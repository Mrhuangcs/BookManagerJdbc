package com.hr.menu;

import com.hr.dao.UserDao;
import com.hr.dao.impl.UserDaoImpl;
import com.hr.entry.User;
import com.hr.manager.BookManager;
import com.hr.manager.UserManager;
import com.hr.util.NumberUtil;

import java.util.Scanner;

public class Menu {
    private UserManager userManager=new UserManager();
    private BookManager bookManager=new BookManager();
    public static User user;
    private UserDao userDao=new UserDaoImpl();
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.isLoginOrRegister();

    }

    private void isLoginOrRegister(){
        System.out.println("------图书管理登录注册------");
        System.out.println("0、登录");
        System.out.println("1、注册");
        System.out.println("登录请输入0  注册请输入1");
        Scanner scanner = new Scanner(System.in);
        String str=scanner.nextLine();
        int i = NumberUtil.isInteger(str);
        while (i!=1&&i!=0){
            System.out.println("输入有误！！请输入数字1或0");
            str=scanner.nextLine();
            i = NumberUtil.isInteger(str);
        }
        switch (i){
            case 0:
                loginView();
                break;
            case 1:
                register();
                break;
        }
    }

    private void loginView(){
        System.out.println("------登录-------");
        while (true){
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入账号：");
            String userName=scanner.nextLine();
            System.out.printf("请输入密码：");
            String password=scanner.nextLine();
            try {
                user= userDao.login(userName,password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (user!=null){
                System.out.println("登录成功");
                if (user.getUserName().equals("admin")){
                    adminMenu();
                }else {
                    menu();
                }
                break;
            }
            System.out.println("登录失败！！用户名或密码错误！！");
        }

    }

    private void register(){
        System.out.println("------注册-------");
        int i1=1;
        while (i1==1){
            Scanner scanner = new Scanner(System.in);
            System.out.printf("请输入账号：");
            String userName=scanner.nextLine();
            System.out.printf("请输入密码：");
            String password=scanner.nextLine();
            System.out.printf("请输入性别：");
            String gender=scanner.nextLine();
            int i=0;
            while (!gender.equals("男")&&!gender.equals("女")){
                System.out.println("输入有误！！请输入正确的性别！！");
                gender=scanner.nextLine();
            }
            if(gender.equals("男")){
                i=1;
            }
            System.out.printf("请输入班级：");
            String classes=scanner.nextLine();
            User user = new User(null, userName, password, i,classes, 0.0,0);
            try {
                if(userDao.isExistUser(userName)){
                    System.out.println("注册失败！！该用户已存在！！");
                }else {
                    if (userDao.register(user)){
                        System.out.println("注册成功！！");
                        System.out.println("需要再次注册输入1，  返回登录输入0");
                        String str=scanner.nextLine();
                        i1 = NumberUtil.isInteger(str);
                        while (i1!=1&&i1!=0){
                            System.out.println("输入有误！！请输入数字1或0");
                            str=scanner.nextLine();
                            i1 = NumberUtil.isInteger(str);
                        }
                        if(i1==0){
                            loginView();
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private void userMenu(){
        System.out.println("----用户信息管理------");
        System.out.println("1、查询用户信息");
        System.out.println("2、添加用户");
        System.out.println("3、修改用户");
        System.out.println("4、删除用户");
        System.out.println("0、返回");
        System.out.println("请输入相应的序号进行系统操作");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int i = NumberUtil.isInteger(str);
        while (i>4||i<0){
            System.out.println("请输入正确的序号！！");
            str = scanner.nextLine();
            i = NumberUtil.isInteger(str);
        }
        switch (i){
            case 1:
                userManager.findAllUser();
                userMenu();
                break;
            case 2:
                userManager.addUser();
                userMenu();
                break;
            case 3:
                userManager.updateUser();
                userMenu();
                break;
            case 4:
                userManager.deleteUser();
                userMenu();
                break;
            case 0:
                adminMenu();
                break;
            default:
                break;

        }
    }
    private void bookMenu(){
        System.out.println("----图书管理------");
        System.out.println("1、查询所有书籍");
        System.out.println("2、添加书籍");
        System.out.println("3、删除书籍");
        System.out.println("4、修改书籍");
        System.out.println("0、返回"); System.out.println("请输入相应的序号进行系统操作");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int i = NumberUtil.isInteger(str);
        while (i>4||i<0){
            System.out.println("请输入正确的序号！！");
            str = scanner.nextLine();
            i = NumberUtil.isInteger(str);
        }
        switch (i){
            case 1:
                bookManager.findAllBooks();
                bookMenu();
                break;
            case 2:
                bookManager.addBooks(user);
                bookMenu();
                break;
            case 3:
                bookManager.deleteBook(user);
                bookMenu();
                break;
            case 4:
                try {
                    bookManager.UpdateBook(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bookMenu();
                break;
            case 0:
                adminMenu();
                break;
            default:
                break;

        }
    }

    private void adminMenu(){
        System.out.println("欢迎管理员"+user.getUserName()+"来到图书管理系统");
        System.out.println("1、查询所有书籍");
        System.out.println("2、借阅排行");
        System.out.println("3、用户信息管理");
        System.out.println("4、图书管理");
        System.out.println("5、查询借阅未归还的书籍");
        System.out.println("6、修改密码");
        System.out.println("0、退出");
        System.out.println("请输入相应的序号进行系统操作");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int i = NumberUtil.isInteger(str);
        while (i>6||i<0){
            System.out.println("请输入正确的序号！！");
            str = scanner.nextLine();
            i = NumberUtil.isInteger(str);
        }
        switch (i){
            case 1:
                bookManager.findAllBooks();
                adminMenu();
                break;
            case 2:
                bookManager.rankingBook();
                adminMenu();
                break;
            case 3:
                userMenu();
                break;
            case 4:
                bookMenu();
                break;
            case 5:
                bookManager.findAllBorrowBook();
                adminMenu();
                break;
            case 6:
                userManager.updatePassword(user);
                adminMenu();
                break;
            case 0:
                exitUser();
                break;
            default:
                break;

        }
    }

    private void exitUser() {
        Menu menu = new Menu();
        user=null;
        System.out.println("退出成功，欢迎下次使用。");
        menu.isLoginOrRegister();
    }

    private void menu(){
        System.out.println("欢迎用户"+user.getUserName()+"来到图书管理系统");
        System.out.println("1、借书");
        System.out.println("2、还书");
        System.out.println("3、查询所有书籍");
        System.out.println("4、查询已经借阅的书籍");
        System.out.println("5、借阅排行");
        System.out.println("6、查询账户余额");
        System.out.println("7、充值");
        System.out.println("8、修改密码");
        System.out.println("0、退出");
        System.out.println("请输入相应的序号进行系统操作");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int i = NumberUtil.isInteger(str);
        while (i>8||i<0){
            System.out.println("请输入正确的序号！！");
            str = scanner.nextLine();
            i = NumberUtil.isInteger(str);
        }
        switch (i){
            case 1:
                bookManager.borrowBooks(user);
                menu();
                break;
            case 2:
                bookManager.returnBooks(user);
                menu();
                break;
            case 3:
                bookManager.findAllBooks();
                menu();
                break;
            case 4:
                bookManager.searchUserBorrowBook(user);
                menu();
                break;
            case 5:
                bookManager.rankingBook();
                menu();
                break;
            case 6:
                bookManager.findUserBalance(user);
                menu();
                break;
            case 7:
                bookManager.addBalance(user);
                menu();
                break;
            case 8:
                userManager.updatePassword(user);
                menu();
                break;
            case 0:
                exitUser();
                break;
            default:
                break;

        }
    }
}
