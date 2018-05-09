package com.example.user.vibrapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class GetClass extends AsyncTask<String, Integer, String> {
    // String ip="10.108.16.247";
    // String ip = "10.108.32.60";
    private ProgressDialog dialog;
    public async delegate = null;//Call back interface
    Activity a;


    public GetClass(Activity activity) {
        dialog = new ProgressDialog(activity);
        a = activity;

    }

    @Override
    protected String doInBackground(String... strings) {
        if (strings[0].equals("projects")) {
            return projects();
        } else if (strings[0].equals("experiment")) {
            return experiment(strings[1]);
        } else if (strings[0].equals("projectsToAdd")) {
            return projectsToAdd();
        } else if (strings[0].equals("login")) {
            return login();

        } else if (strings[0].equals("metingOphalen")) {
            return metingOphalen(strings[1], strings[2]);
        }


        return null;

    }

    private String metingOphalen(String exId, String arrayId) {
        StringBuilder builder = new StringBuilder();
        // ArrayList<String> meting = new ArrayList<String>();
        try {


            URL url = new URL("http://".concat(HttpClass.ip).concat(":8080/VibreREST/rest/parameter_service/metingOphalen"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("token", Main2Activity.a);
            connection.setRequestProperty("experimentID", exId);
            connection.setRequestProperty("arrayID", arrayId);

            connection.connect();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s;

            while ((s = rd.readLine()) != null) {
                builder.append(s);
                Log.e("&", builder.toString());
            }

            connection.disconnect();
            JSONObject meting = new JSONObject(builder.toString());
            Meting m = new Meting(meting);
            metingshow.meting = m;
            Log.e("metingOpvragen: ", meting.toString());
            Intent intent = new Intent(a, metingshow.class);
            a.startActivity(intent);
        } catch (Exception e) {
            dialog.dismiss();

            Log.e("get", "ERROR");
            Log.e("get", e.toString());
            a.runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(a, "Error occurred trying to connect the server, please try again!", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }

        return null;


    }

    @Override
    protected void onPostExecute(String s) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private String projectsToAdd() {
        if (projectshowToAdd.projects != null) {
            projectshowToAdd.projects.clear();
        }
        StringBuilder builder = new StringBuilder();
        ArrayList<String> projects = new ArrayList<String>();
        try {


            URL url = new URL("http://".concat(HttpClass.ip).concat(":8080/VibreREST/rest/parameter_service/project"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("token", Main2Activity.a);

            connection.connect();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s;

            while ((s = rd.readLine()) != null) {
                builder.append(s);
                projects.add(s);
                Log.e("&", builder.toString());
                Log.e("&", projects.toString());
            }

            connection.disconnect();
            for (int i = 0; i < projects.size(); i++) {
                projectshowToAdd.addProject(new Project(projects.get(i)));
            }
                /*if (strings[3].equals("toevoegenEigenProj")) {
                    saveToken();
                }*/
            Intent intent = new Intent(a, projectshowToAdd.class);
            a.startActivity(intent);
        } catch (Exception e) {
            Log.e("get", "ERROR");
            Log.e("get", e.toString());
            dialog.dismiss();

            a.runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(a, "Error occurred trying to connect the server, please try again!", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }

        return null;

    }

    private String experiment(String projid) {
        StringBuilder builder = new StringBuilder();
        ArrayList<String> experiment = new ArrayList<String>();
        try {


            URL url = new URL("http://".concat(HttpClass.ip).concat(":8080/VibreREST/rest/parameter_service/experiment"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("token", Main2Activity.a);
            connection.setRequestProperty("projID", String.valueOf(projid));

            connection.connect();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s;

            while ((s = rd.readLine()) != null) {
                builder.append(s);
                experiment.add(s);
                Log.e("&", builder.toString());
                Log.e("&", experiment.toString());
            }

            connection.disconnect();
            for (int i = 0; i < experiment.size(); i++) {
                experimentshow.addExperiment(new Experimenten(experiment.get(i)));
            }
                /*if (strings[3].equals("toevoegenEigenProj")) {
                    saveToken();
                }*/
            Intent intent = new Intent(a, experimentshow.class);
            a.startActivity(intent);
        } catch (Exception e) {
            Log.e("get", "ERROR");
            Log.e("get", e.toString());
            dialog.dismiss();

            a.runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(a, "Error occurred trying to connect the server, please try again!", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }


        return null;
    }

    private String projects() {
        StringBuilder builder = new StringBuilder();
        ArrayList<String> projects = new ArrayList<String>();
        try {


            URL url = new URL("http://".concat(HttpClass.ip).concat(":8080/VibreREST/rest/parameter_service/project"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("token", Main2Activity.a);

            connection.connect();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s;

            while ((s = rd.readLine()) != null) {
                builder.append(s);
                projects.add(s);
                Log.e("&", builder.toString());
                Log.e("&", projects.toString());
            }

            connection.disconnect();
            for (int i = 0; i < projects.size(); i++) {
                projectshow.addProject(new Project(projects.get(i)));
            }
                /*if (strings[3].equals("toevoegenEigenProj")) {
                    saveToken();
                }*/
            Intent intent = new Intent(a, projectshow.class);
            a.startActivity(intent);
        } catch (Exception e) {
            Log.e("get", "ERROR");
            Log.e("get", e.toString());
            dialog.dismiss();

            a.runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(a, "Error occurred trying to connect the server, please try again!", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }


        return null;
    }

    private String login() {
        StringBuilder builder = new StringBuilder();
        try {


            URL url = new URL("http://".concat(HttpClass.ip).concat(":8080/VibreREST/rest/parameter_service/token"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("token", Main2Activity.a);

            connection.connect();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s;

            while ((s = rd.readLine()) != null) {
                builder.append(s);
            }

            connection.disconnect();
                /*if (strings[3].equals("toevoegenEigenProj")) {
                    saveToken();
                }*/

        } catch (Exception e) {
            Log.e("get", "ERROR");
            Log.e("get", e.toString());
            dialog.dismiss();

            a.runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(a, "Error occurred trying to connect the server, please try again!", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }
        return builder.toString();
    }

    @Override
    protected void onPreExecute() {
        this.dialog.show();
        this.dialog.setCancelable(false);
        this.dialog.setMessage("Processing... in get");
    }

}