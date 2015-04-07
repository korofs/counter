package com.example.skydiver.counter;

import android.app.Activity;

/**
 * Created by root on 01.04.15.
 */
public class CounterProvider {

    private static CounterProvider obj;
    private CounterProperties counterProperties;
    private Activity activity;
    private CounterProvider(){

        counterProperties = new CounterProperties();
    }

    public static CounterProvider getInstance()
    {
        if (obj == null)
        {
            obj = new CounterProvider();
        }
        return obj;
    }


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public CounterProperties getCounterProperties() {
        return counterProperties;
    }

    public void setCounterProperties(CounterProperties counterProperties) {
        this.counterProperties = counterProperties;
    }

    public void addReply()
    {
        counterProperties.setReplyCount((short)(counterProperties.getReplyCount() + 1));
    }

    public void addPlus()
    {
        counterProperties.setPlusCount((short) (counterProperties.getPlusCount() + 1));
    }

    public void addMinus()
    {
        counterProperties.setMinusCount((short) (counterProperties.getMinusCount() + 1));
    }
}
