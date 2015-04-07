package com.example.skydiver.counter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CounterProperties counterProperties;
        CounterProvider.getInstance().setActivity(this);

        if (CounterProvider.getInstance().getCounterProperties().getReplyCount() == (short) 0 &&
                CounterProvider.getInstance().getCounterProperties().getPlusCount() == (short) 0 &&
                CounterProvider.getInstance().getCounterProperties().getMinusCount() == (short) 0)
        {
            if (savedInstanceState == null) {
                String currentRes = readFromFile();
                if (currentRes != null && !currentRes.equals(""))
                {
                    StringTokenizer stringTokenizer = new StringTokenizer(currentRes, "\n");
                    counterProperties = new CounterProperties();
                    if (stringTokenizer.hasMoreElements())
                    {
                        counterProperties.setReplyCount(Short.parseShort((String)stringTokenizer.nextElement()));
                    }

                    if (stringTokenizer.hasMoreElements())
                    {
                        counterProperties.setPlusCount(Short.parseShort((String)stringTokenizer.nextElement()));
                    }

                    if (stringTokenizer.hasMoreElements())
                    {
                        counterProperties.setMinusCount(Short.parseShort((String)stringTokenizer.nextElement()));
                    }
                }
                else
                {
                    counterProperties = new CounterProperties((short) 0, (short) 0, (short) 0);
                }
            }
            else
            {
                counterProperties = new CounterProperties();
                counterProperties.setReplyCount(savedInstanceState.getShort("replyCount"));
                counterProperties.setPlusCount(savedInstanceState.getShort("plusCount"));
                counterProperties.setMinusCount(savedInstanceState.getShort("minusCount"));
            }
            CounterProvider.getInstance().setCounterProperties(counterProperties);
        }
        else
        {
            counterProperties = CounterProvider.getInstance().getCounterProperties();
        }

        TextView tReply = (TextView)findViewById(R.id.ReplyCount);
        tReply.setText(String.valueOf(counterProperties.getReplyCount()));
        TextView tPlus = (TextView)findViewById(R.id.PlusCount);
        tPlus.setText(String.valueOf(counterProperties.getPlusCount()));
        TextView tMinus = (TextView)findViewById(R.id.MinusCount);
        tMinus.setText(String.valueOf(counterProperties.getMinusCount()));

        //media button
        ComponentName mediaButtonReceiver = new ComponentName(getPackageName(), MediaButtonIntentReceiver.class.getName());

        IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_BUTTON);
        MediaButtonIntentReceiver r = new MediaButtonIntentReceiver();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        registerReceiver(r, filter);

        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.registerMediaButtonEventReceiver(mediaButtonReceiver);

        /*Button button = (Button)findViewById(R.id.ClearData);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CounterProvider.getInstance().getCounterProperties().setPlusCount((short)0);
                CounterProvider.getInstance().getCounterProperties().setReplyCount((short)0);
                CounterProvider.getInstance().getCounterProperties().setMinusCount((short)0);
                TextView tReply = (TextView)findViewById(R.id.ReplyCount);
                tReply.setText(String.valueOf(CounterProvider.getInstance().getCounterProperties().getReplyCount()));
                TextView tPlus = (TextView)findViewById(R.id.PlusCount);
                tPlus.setText(String.valueOf(CounterProvider.getInstance().getCounterProperties().getPlusCount()));
                TextView tMinus = (TextView)findViewById(R.id.MinusCount);
                tMinus.setText(String.valueOf(CounterProvider.getInstance().getCounterProperties().getMinusCount()));
            }
        });*/

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putShort("replyCount", CounterProvider.getInstance().getCounterProperties().getReplyCount());
        outState.putShort("minusCount", CounterProvider.getInstance().getCounterProperties().getMinusCount());
        outState.putShort("plusCount", CounterProvider.getInstance().getCounterProperties().getPlusCount());

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CounterProperties counterProperties = new CounterProperties(savedInstanceState.getShort("replyCount"),
                savedInstanceState.getShort("plusCount"), savedInstanceState.getShort("minusCount"));
        CounterProvider.getInstance().setCounterProperties(counterProperties);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        CounterProperties counterProperties = CounterProvider.getInstance().getCounterProperties();
        writeToFile(counterProperties.toString());

    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("saved_state", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput("saved_state");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString + "\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
