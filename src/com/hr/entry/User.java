package com.hr.entry;

public class User {

    private Integer userId;
    private String userName;
    private String password;
    private int gender;
    private String classes;
    private Double balance;
    private int borrowBook;
    public User() {
    }

    public User(Integer userId, String userName, String password, int gender, String classes, Double balance, int borrowBook) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.classes = classes;
        this.balance = balance;
        this.borrowBook = borrowBook;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public int getBorrowBook() {
        return borrowBook;
    }

    public void setBorrowBook(int borrowBook) {
        this.borrowBook = borrowBook;
    }
}
