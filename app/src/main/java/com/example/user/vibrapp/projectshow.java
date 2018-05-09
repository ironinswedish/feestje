package com.example.user.vibrapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class projectshow extends AppCompatActivity {
    protected static ArrayList<Project> projects;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context ctx = this;
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_projectshow);
        if (projects == null) {
            projects = new ArrayList<Project>();
        }
        Log.e("create", String.valueOf(projects.size()));
        TableLayout ll = (TableLayout) findViewById(R.id.table);
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView tv = new TextView(this);
        tv.setText("Titel");
        tv.setTypeface(null, Typeface.BOLD_ITALIC);

        row.addView(tv);
        TextView tv2 = new TextView(this);
        tv2.setText("ProjectToken");
        tv2.setTypeface(null, Typeface.BOLD_ITALIC);

        row.addView(tv2);

        ll.addView(row, 0);

        for (int i = 0; i < projects.size(); i++) {

            row = new TableRow(this);
            lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            tv = new TextView(this);
            if (projects.get(i).getTitel().length() > 20) {
                tv.setText(projects.get(i).getTitel().substring(0, 20).concat("...  "));

            } else {

                tv.setText(projects.get(i).getTitel().concat("     "));
            }
            row.addView(tv);
            tv2 = new TextView(this);
            tv2.setText(projects.get(i).getProjectToken());

            row.addView(tv2);
            Button btn = new Button(this);
            btn.setText("View");
            btn.setId(projects.get(i).getId());
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (experimentshow.experimenten != null) {
                        if (!experimentshow.experimenten.isEmpty()) {
                            experimentshow.experimenten = new ArrayList<Experimenten>();
                        }
                    }
                    new GetClass(projectshow.this).execute("experiment", String.valueOf(v.getId()));
                }
            });
            row.addView(btn);
            ll.addView(row, i + 2);
        }


    }

    /**
     *
     * @param p
     */
    public static void addProject(Project p) {
        if (projects == null) {
            projects = new ArrayList<Project>();
        }
        projects.add(p);
    }


}
