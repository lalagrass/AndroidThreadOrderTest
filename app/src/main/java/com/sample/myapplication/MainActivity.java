package com.sample.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    LinearLayout myLinear;
    ScrollView myScroll;
    private static volatile boolean isRunning = false;
    static long c1, c2, c3;
    private static long bound = 100000000;
    Runnable r1 = new Runnable() {
        @Override
        public void run() {
            while (isRunning) {
                if (c1++ > bound) {
                    c1 = 0;
                    LogIt(1);
                }
            }
        }
    };
    Runnable r2 = new Runnable() {
        @Override
        public void run() {
            while (isRunning) {
                if (c2++ > bound) {
                    c2 = 0;
                    LogIt(2);
                }
            }
        }
    };
    Runnable r3 = new Runnable() {
        @Override
        public void run() {
            while (isRunning) {
                if (c3++ > bound) {
                    c3 = 0;
                    LogIt(3);
                }
            }
        }
    };

    private synchronized void LogIt(final Integer i) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (myLinear != null) {
                    TextView t = new TextView(MainActivity.this);
                    t.setText(i.toString());
                    myLinear.addView(t);
                    myScroll.fullScroll(View.FOCUS_DOWN);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myLinear = (LinearLayout) this.findViewById(R.id.linear);
        myScroll = (ScrollView) this.findViewById(R.id.myScroll);
        StartTest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        isRunning = false;
        super.onPause();
    }

    private void StartTest() {
        isRunning = true;
        new Thread(r1).start();
        new Thread(r2).start();
        new Thread(r3).start();
    }
}
