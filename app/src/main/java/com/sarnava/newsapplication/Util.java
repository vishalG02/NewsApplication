package com.sarnava.newsapplication;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    private static final int BLOCK_TIME = 50;
    private static boolean isBlockClick;

    /**
     * Block any event occurs in 1000 millisecond to prevent spam action
     * @return false if not in block state, otherwise return true.
     */
    public static boolean block(int blockInMillis) {
        if (!isBlockClick) {
            isBlockClick= true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isBlockClick= false;
                }
            }, blockInMillis);
            return false;
        }
        return true;
    }

    public static boolean block() {
        return block(BLOCK_TIME );
    }

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
