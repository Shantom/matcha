package com.shantom.matcha.utils;

import java.text.DecimalFormat;

public class NumUtils {

    public static String ToHuman(double num){
        String suffix = "";
        if(Math.abs(num)>1e7){
            num/=1e8;
            suffix = "亿";
        }else if(Math.abs(num)>1e4){
            num/=1e4;
            suffix = "万";
        }

        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(num)+suffix;
    }

    public static String ToPercent(double num){
        DecimalFormat format = new DecimalFormat("##0.00%");
        return format.format(num);
    }

    public static void main(String[] args) {
        System.out.println(NumUtils.ToPercent(-0.04417469433894004));
    }
}
