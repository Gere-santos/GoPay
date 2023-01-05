package com.example.gopayments.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataCustom {
    private static Calendar calendar;
    public static String dataAtual(){

        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = simpleDateFormat.format(data);
        return dataString;
    }
    public static String dataeHoraAtual(){
        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataeHoraString = simpleDateFormat.format(calendar.getTime());
        String retorno [] = dataeHoraString.split(" ");
        String data = retorno[0];
        String horario = retorno[1];
        String horaedata = horario + "    " + data;

        return horaedata;
    }

    public static String mesAnoDataEscolhida (String data){
        String retornoData[] =  data.split("/");
        String dia = retornoData[0];
        String mes = retornoData[1];
        String ano = retornoData[2];

        String mesAno = mes + ano;
        return mesAno;
    }
}
