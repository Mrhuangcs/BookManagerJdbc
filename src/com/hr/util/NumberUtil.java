package com.hr.util;

import java.util.Scanner;

public class NumberUtil {

    public static Double isDouble(String str)
    {
        boolean isDouble=false;
        Scanner scanner = new Scanner(System.in);
        Double balance=0.0;
        while (true){
            try {
                Double.parseDouble(str);
                isDouble=true;
            } catch(NumberFormatException ex){}
            if(isDouble){
                balance =Double.parseDouble(str);
                break;
            }else {
                System.out.println("输入的是无效的数值！！请输入正确的数值！！");
                str = scanner.nextLine();
            }
        }
        return balance;
    }
    public static boolean isInt(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException ex){}
        return false;
    }
    public static int isInteger(String str){
        Scanner scanner = new Scanner(System.in);
        int i=0;
        while (true){
            if(isInt(str)){
                i =Integer.parseInt(str);
                break;
            }else {
                System.out.println("输入的是无效的数值！！请输入正确的数值！！");
                str = scanner.nextLine();
            }
        }
        return i;
    }
}
