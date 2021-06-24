package com.finnauxapp.Util;


/**
 * Created by rakesh on 11/1/2015.
 */

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {
    private String db_date,text;
    public EditText edittext;
    boolean flag;
    Context context;
    DatePickerDialog dialog;
    boolean ISDISPLAY;
    private DateOnClick dateOnClick;
    public DatePickerFragment(){}
    @SuppressLint("ValidFragment")
    public DatePickerFragment(boolean ISDISPLAY, DateOnClick dateOnClick){
        this.ISDISPLAY=ISDISPLAY;
        this.dateOnClick=dateOnClick;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setEdittext(EditText edittext) {
        this.edittext = edittext;
    }
    public void settext(String text) {
        this.text = text;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        int year = 0,month = 0,day = 0;

        if(edittext.getText().toString().equals(""))
        {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }
        else
        {
            String []date = edittext.getText().toString().split("-");
            year = Integer.parseInt(date[2]);
            month = Integer.parseInt(date[1]);
            day = Integer.parseInt(date[0]);
        }

        dialog = new DatePickerDialog(context, this, year, month, day);
        dialog.getDatePicker().updateDate(year, month, day);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        return dialog;
    }

    public void onDateSet(DatePicker view, int selectedyear, int selectedmonth,	int selectedday)
    {
        SimpleDateFormat format=new SimpleDateFormat("MM-dd-yyyy");
        Date fromDate=null,toDate = null;
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        try {
            fromDate=format.parse((selectedmonth+1)+"-"+selectedday+"-"+selectedyear);
            toDate = format.parse((month+1)+"-"+day+"-"+year);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar newDate = Calendar.getInstance();
        newDate.set(selectedyear, selectedmonth, selectedday);
        Calendar current=Calendar.getInstance();


        edittext.setText(new StringBuilder().append(selectedyear).append("-")
                .append(selectedmonth + 1).append("-").append(selectedday).toString());
        edittext.setTag(new StringBuilder().append(selectedyear).append("-")
                .append(selectedmonth + 1).append("-").append(selectedday).toString());
        dateOnClick.DateOnClicked(edittext.getText().toString());
    }



}