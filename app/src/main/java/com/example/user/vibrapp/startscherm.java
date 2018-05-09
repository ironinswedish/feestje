package com.example.user.vibrapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;



import java.util.logging.LogRecord;

import static com.example.user.vibrapp.projectshow.projects;

public class startscherm extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscherm);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            int permission = 1000;
            ActivityCompat.requestPermissions((Activity) this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, permission);
            Log.e("requestSingleUpdate: ", "perm" + permission);

        }
       /* Button button = (Button) findViewById(R.id.startB2);
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Location mlocation = location;
                Log.e("Location Changes", location.toString());
                Log.e("onLocationChanged: ", String.valueOf(location.getLatitude()));
                Log.e("onLocationChanged: ", String.valueOf(location.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("Status Changed", String.valueOf(status));
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Provider Enabled", provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Provider Disabled", provider);
            }
        };

        // Now first make a criteria with your requirements
        // this is done to save the battery life of the device
        // there are various other other criteria you can search for..

        // Now create a location manager
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // This is the Best And IMPORTANT part
        final Looper looper = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            Log.e("a", "doeMeting: ");            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setSpeedRequired(false);
            criteria.setCostAllowed(true);
            criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
            criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

            locationManager.requestSingleUpdate(criteria, locationListener, looper);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(startscherm.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(startscherm.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    Log.e("perm", "onClick: " );                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                criteria.setPowerRequirement(Criteria.POWER_LOW);
                criteria.setAltitudeRequired(false);
                criteria.setBearingRequired(false);
                criteria.setSpeedRequired(false);
                criteria.setCostAllowed(true);
                criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
                criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

                locationManager.requestSingleUpdate(criteria, locationListener, looper);
                Intent intent = new Intent(startscherm.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
    }

    public void opvragenMeting(View view) {

        //TODO: IMPLEMENT BELOW

        /*if (Main2Activity.a != null) {
            String login=new GetClass(Main3Activity.this).doInBackground("login",Main3Activity.mode);

            if (login.equals("check")) {
                new GetClass(Main3Activity.this).execute("projectsToAdd");
            } else {

                Intent intent = new Intent(this, projectshowToAdd.class);
                startActivity(intent);
            }*/


        if (Main2Activity.a != null) {

            if (projectshow.projects != null) {
                projectshow.projects.clear();
            }

            //TODO:CHECK TOKEN

            new GetClass(startscherm.this).execute("projects");
        } else {
            Main3Activity.mode = "opvragen";
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        }
    }

    public void doeMeting(View view) {
        Intent intent = new Intent(startscherm.this, MainActivity.class);
        startActivity(intent);
    }


    public void openSettings(View view) {
        final int[] change = {0};
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Settings");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        //    LinearLayout layoutsettings = new LinearLayout(this);
        //    layout.setOrientation(LinearLayout.HORIZONTAL);
        final EditText hertz = new EditText(this);
        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // updated continuously as the user slides the thumb
                if (change[0] == 0) {
                    change[0] = 1;

                    hertz.setText(String.valueOf(Math.round(4.9 * progress + 10)));
                    MainActivity.hertz = (int) Math.round(4.9 * progress + 10);
                    change[0] = 0;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // called when the user first touches the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // called after the user finishes moving the SeekBar
            }
        };
        hertz.setHint("Hertz");
        hertz.setInputType(InputType.TYPE_CLASS_NUMBER);
        final SeekBar seekBar = new SeekBar(this);
        hertz.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (change[0] == 0) {
                    try {
                        if (s != "") {

                            if (Integer.parseInt(s.toString()) <= 500) {
                                change[0] = 1;

                                seekBar.setProgress((int) Math.round((Integer.parseInt(s.toString()) - 10) / 4.9));
                                MainActivity.hertz = Integer.parseInt(s.toString());
                                change[0] = 0;
                            } else {
                                hertz.setText("500");
                                MainActivity.hertz = 500;

                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        hertz.setText(String.valueOf(MainActivity.hertz));
        seekBar.setProgress((int) Math.round((MainActivity.hertz - 10) / 4.9));
        int progress = seekBar.getProgress();
        //      tvProgressLabel = findViewById(R.id.textView);
        //     tvProgressLabel.setText("Progress: " + progress);


        //    layoutsettings.addView(hertz); // Notice this is an add method

        layout.addView(seekBar);
        layout.addView(hertz);
        LinearLayout layout2 = new LinearLayout(this);
        layout2.setOrientation(LinearLayout.HORIZONTAL);


        final EditText timing = new EditText(this);

        timing.setHint("Tijd(seconden)");
        timing.setInputType(InputType.TYPE_CLASS_NUMBER);

        CheckBox cb = new CheckBox(this);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                          @Override
                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                              MainActivity.timing = isChecked;
                                              if (!timing.getText().toString().isEmpty())
                                                  MainActivity.hoelangMeten = Integer.parseInt(timing.getText().toString());
                                          }
                                      }
        );
        timing.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                    try {
                        if (s != "") {

                                MainActivity.hoelangMeten = Integer.parseInt(s.toString());
                            Log.e("onTextChanged: ", String.valueOf(MainActivity.hoelangMeten));
                        }
                    } catch (Exception e) {
                    }
                }

        });
        timing.setText("15");

        layout2.addView(cb);
        layout2.addView(timing);
        layout.addView(layout2);
        alert.setView(layout);
        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Editable YouEditTextValue = titleBox.getText();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });
        alert.show();
    }
}
