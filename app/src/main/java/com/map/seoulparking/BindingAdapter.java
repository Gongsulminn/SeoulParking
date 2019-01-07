package com.map.seoulparking;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 2018-12-26.
 */

public class BindingAdapter {

    @android.databinding.BindingAdapter({"imageRes"})
    public static void favoriteImage(ImageView imageView ,  Drawable drawable){
        imageView.setImageDrawable(drawable);
    }

    @android.databinding.BindingAdapter({"starttime" , "endtime"})
    public static void setTimeText(TextView textView , String starttime , String endtime){
        StringBuffer time =  new StringBuffer(starttime + endtime);
        time.insert(4, "~");
        time.insert(2 , ":");
        time.insert(8 , ":");
        textView.setText(textView.getText().toString() + time);
    }

    @android.databinding.BindingAdapter(value = {"rate" , "price"} , requireAll = false)
    public static void setTimeRate(TextView textView , String rate , String price){
        if (rate != null)
            textView.setText(textView.getText() + price + "원" + " / " + rate +"분");
        else{
            price = price == null ? "0" : price;
            textView.setText(textView.getText() + price + "원");
        }
    }
}
