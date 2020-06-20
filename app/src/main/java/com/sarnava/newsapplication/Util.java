package com.sarnava.newsapplication;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String getDate(String date){

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");

        Date d = null;
        {
            try {
                d = input.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(d != null){

            String formatted = output.format(d);
            return formatted;
        }
        else {
            return date;
        }
    }

    @BindingAdapter({"url"})
    public static void loadImage(ImageView view, String url) {

        Picasso.get().load(url).into(view);
    }
}
