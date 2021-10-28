package com.example.mydiary.utils;

import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

/**
 * Created by Yuzhe You on 2021/10/25.
 * No.1159774
 */

public class GetDate {

    public static StringBuilder getDate(){

        StringBuilder stringBuilder = new StringBuilder();
        Calendar now = Calendar.getInstance();
//
//        String shu = (int)(now.get(Calendar.MONTH) + 1)  + "月";
//        SimpleDateFormat sdf = new SimpleDateFormat("MM");
//        Date date = null;
//        try {
//            date = sdf.parse(shu);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        sdf = new SimpleDateFormat("MMMMM",Locale.US);
//
//        stringBuilder.append(sdf.format(date)+" ");
//        stringBuilder.append(now.get(Calendar.DAY_OF_MONTH) + "");
//        stringBuilder.append(" ," + now.get(Calendar.YEAR));


        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        stringBuilder.append(sdf.format(date));
        return stringBuilder;

    }
}
