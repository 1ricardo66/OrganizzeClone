package br.com.ricardofelix.organizzeclone.Helper;

public class CalendarCustom {

    private static CharSequence []MOUNTH = {"Janeiro","Fevereiro","Março","Abril","Maior","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
    private static CharSequence []DAYS_OF_WEEK = {"Seg","Ter","Quar","Quin","Sex","Sáb","Dom"};

    public static CharSequence[] getMounths(){
        return MOUNTH;
    }

    public static CharSequence[] getDaysOfWeek(){
        return DAYS_OF_WEEK;
    }
}
