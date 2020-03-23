package br.com.ricardofelix.organizzeclone.Helper;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCustom {

    public static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return sdf.format(date);
    }


    public static String splitDate(String date){
        String day, month, year;
        String [] fullDate = date.split("/");

        day = fullDate[0];
        month = fullDate[1];
        year = fullDate[2];

        return month+year;

    }
}
