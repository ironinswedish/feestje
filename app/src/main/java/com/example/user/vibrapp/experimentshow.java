package com.example.user.vibrapp;

import android.graphics.Typeface;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class experimentshow extends AppCompatActivity {
    protected static ArrayList<Experimenten> experimenten;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_experimentshow);
        if (experimenten == null) {
            experimenten = new ArrayList<Experimenten>();
        }
        Log.e("create", String.valueOf(experimenten.size()));
        TableLayout ll = findViewById(R.id.tblEx);
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView tv = new TextView(this);
        tv.setText("Titel");
        tv.setTypeface(null, Typeface.BOLD_ITALIC);

        row.addView(tv);
        TextView tv2 = new TextView(this);
        tv2.setText("Datum");
        tv2.setTypeface(null, Typeface.BOLD_ITALIC);

        row.addView(tv2);

        ll.addView(row, 0);

        for (int i = 0; i < experimenten.size(); i++) {

            row = new TableRow(this);
            lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            tv = new TextView(this);
            if (experimenten.get(i).getTitel().length() > 20) {
                tv.setText(experimenten.get(i).getTitel().substring(0, 20).concat("...  "));

            } else {

                tv.setText(experimenten.get(i).getTitel().concat("     "));
            }
            row.addView(tv);
            tv2 = new TextView(this);
            Calendar c = Calendar.getInstance();
            c.setTime(experimenten.get(i).getDate());
            tv2.setText(experimenten.get(i).getDate().getHours() + ":" + (((int) experimenten.get(i).getDate().getMinutes())<10?"0".concat(String.valueOf(experimenten.get(i).getDate().getMinutes())):experimenten.get(i).getDate().getMinutes()) + " " + c.get(Calendar.DATE) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.YEAR));

            row.addView(tv2);
            if (experimenten.get(i).getIdMeting() != -1) {
                Button btn = new Button(this);
                btn.setText("View");
                btn.setId(experimenten.get(i).getId());
                btn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new GetClass(experimentshow.this).execute("metingOphalen", String.valueOf(v.getId()),"0");




                    }
                });
                row.addView(btn);
            }
            ll.addView(row, i + 1);
        }


    }

    public static void addExperiment(Experimenten e) {
        if (experimenten == null) {
            experimenten = new ArrayList<Experimenten>();
        }
        experimenten.add(e);
    }


}
