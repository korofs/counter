package com.example.skydiver.counter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

public class MediaButtonIntentReceiver extends BroadcastReceiver {

    public MediaButtonIntentReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        if (!Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
            return;
        }
        KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
        if (event == null) {
            return;
        }

        int action = event.getAction();
        if (action == KeyEvent.ACTION_DOWN) {
            if ((KeyEvent.KEYCODE_MEDIA_PLAY == event.getKeyCode())
                    || (KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE == event.getKeyCode())
                    || (KeyEvent.KEYCODE_HEADSETHOOK == event.getKeyCode())) {
                CounterProvider.getInstance().addReply();
                if (CounterProvider.getInstance().getActivity() != null)
                {
                    TextView t = (TextView)CounterProvider.getInstance().getActivity().findViewById(R.id.ReplyCount);
                    t.setText(String.valueOf(CounterProvider.getInstance().getCounterProperties().getReplyCount()));
                }
            }
            if (KeyEvent.KEYCODE_VOLUME_UP == event.getKeyCode() || KeyEvent.KEYCODE_MEDIA_NEXT == event.getKeyCode()) {
                CounterProvider.getInstance().addPlus();
                if (CounterProvider.getInstance().getActivity() != null)
                {
                    TextView t = (TextView)CounterProvider.getInstance().getActivity().findViewById(R.id.PlusCount);
                    t.setText(String.valueOf(CounterProvider.getInstance().getCounterProperties().getPlusCount()));
                }
            }
            if (KeyEvent.KEYCODE_VOLUME_DOWN == event.getKeyCode() || KeyEvent.KEYCODE_MEDIA_PREVIOUS == event.getKeyCode()) {
                CounterProvider.getInstance().addMinus();
                if (CounterProvider.getInstance().getActivity() != null)
                {
                    TextView t = (TextView)CounterProvider.getInstance().getActivity().findViewById(R.id.MinusCount);
                    t.setText(String.valueOf(CounterProvider.getInstance().getCounterProperties().getMinusCount()));
                }
            }

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(CounterProvider.getInstance().getActivity());
            dlgAlert.setMessage("code = " + event.getKeyCode());
            dlgAlert.setTitle("Info");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.create().show();
        }

        abortBroadcast();
    }
}
