package com.bignerdranch.myrxmeizi.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bignerdranch.myrxmeizi.R;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
public class DatePickerFragment extends DialogFragment
{
    private static final String ARG_DATE="date";

    private DatePicker datePicker;

    public static final String EXTRA_DATE="com.bignerdranch.myrxmeizi.date";



    public static DatePickerFragment newInstance(Date date)
    {
        Bundle args=new Bundle();
        args.putSerializable("date",date);
        DatePickerFragment datePickerFragment=new DatePickerFragment();
        datePickerFragment.setArguments(args);
        return datePickerFragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        Date date=(Date)getArguments().getSerializable(ARG_DATE);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date,null);
        datePicker=(DatePicker)view.findViewById(R.id.dialog_date_date_picker);
        datePicker.init(year,month,day,null);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Date Of Stories")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int year=datePicker.getYear();
                        int month=datePicker.getMonth();
                        int day=datePicker.getDayOfMonth();
                        Date date=new GregorianCalendar(year,month,day).getTime();
                        sendResult(Activity.RESULT_OK,date);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode,Date date)
    {
        if(getTargetFragment()==null)
        {
            return;
        }
        Intent intent=new Intent();
        intent.putExtra(EXTRA_DATE,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
