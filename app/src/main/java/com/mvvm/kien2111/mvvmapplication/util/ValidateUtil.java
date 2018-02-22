package com.mvvm.kien2111.mvvmapplication.util;

import java.util.regex.Pattern;

/**
 * Created by WhoAmI on 09/02/2018.
 */

public final class ValidateUtil {
    final static Pattern emailPattern = Pattern.compile("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
    final static Pattern phonenumberPattern = Pattern.compile("/\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})/");
    final static Pattern uppercasePattern = Pattern.compile("\\b[A-Z][A-Z0-9]+\\b");
    final static Pattern lowercasePattern = Pattern.compile("\\b[a-z][A-Z0-9]+\\b");
    public static boolean hasMaxLength(String str,int number){
        return str.length()<=number;
    }
    public static boolean hasMinLength(String str,int number){
        return str.length()>=number;
    }
    public static boolean isEmail(String str){
        return emailPattern.matcher(str).matches();
    }
    public static boolean isPhoneNumber(String str){
        return phonenumberPattern.matcher(str).matches();
    }
    public static boolean isOnlyUpperCase(String str){
        return uppercasePattern.matcher(str).matches();
    }
    public static boolean isOnlyLowerCase(String str){
        return lowercasePattern.matcher(str).matches();
    }
}
