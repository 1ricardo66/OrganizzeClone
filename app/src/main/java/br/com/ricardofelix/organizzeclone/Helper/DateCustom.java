package br.com.ricardofelix.organizzeclone.Helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCustom {

    public static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return sdf.format(date);
    }
}
