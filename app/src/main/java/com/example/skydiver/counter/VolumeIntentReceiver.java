package com.example.skydiver.counter;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class VolumeIntentReceiver extends BroadcastReceiver {
    public VolumeIntentReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
            int newVolume = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
            int oldVolume = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0);
            if (newVolume > oldVolume) {
                //volume up
                CounterProvider.getInstance().addPlus();
                if (CounterProvider.getInstance().getActivity() != null)
                {
                    TextView t = (TextView)CounterProvider.getInstance().getActivity().findViewById(R.id.PlusCount);
                    t.setText(String.valueOf(CounterProvider.getInstance().getCounterProperties().getPlusCount()));
                }
            }
            if (newVolume < oldVolume)
            {
                //volume down
                CounterProvider.getInstance().addMinus();
                if (CounterProvider.getInstance().getActivity() != null)
                {
                    TextView t = (TextView)CounterProvider.getInstance().getActivity().findViewById(R.id.MinusCount);
                    t.setText(String.valueOf(CounterProvider.getInstance().getCounterProperties().getMinusCount()));
                }
            }
        }
    }
}
