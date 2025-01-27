/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author TechCare
 */
public class Validation {

    public static boolean isValidMail(String email) {

        String EMAIL_STRING = "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
        return Pattern.compile(EMAIL_STRING).matcher(email).matches();
    }

    public static boolean isPhoneNumber(String phonenumber) {
        String phonePattern = "^[0-9]{10,11}$";
        return Pattern.compile(phonePattern).matcher(phonenumber).matches();
    }

    public static boolean checkinputInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int num = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;

    }

    public static boolean isvalidate(String date) {
        String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        Pattern pattern = null;
        Matcher matcher;
        pattern = Pattern.compile(DATE_PATTERN);
        matcher = pattern.matcher(date); 
        if (matcher.matches()) {

            matcher.reset();

            if (matcher.find()) {

                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31")
                        && (month.equals("4") || month.equals("6") || month.equals("9")
                        || month.equals("11") || month.equals("04") || month.equals("06")
                        || month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if (year % 4 == 0) {
                        if (day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (day.equals("29") || day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
