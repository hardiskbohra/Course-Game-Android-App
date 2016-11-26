package com.coursegame.coursegame;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class topic_detail extends AppCompatActivity {

    private ProgressDialog pDialog;
    ImageView my_image;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
    public String mtr_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
        ActionBar acb=getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String a=i.getStringExtra("topic_name");
        setTitle(a);
        TextView tv=(TextView)findViewById(R.id.desc_text);
        String a1=i.getStringExtra("topic_desc");
        String a2=i.getStringExtra("course_id");
        String a3=i.getStringExtra("topic_id");
        tv.setText(a1);

        JSONObject jo=App.course_details.get(a2);
        JSONArray jr,jr1;
        final ArrayList<JSONObject> ar=new ArrayList<>();
        final ArrayList<JSONObject> ar1=new ArrayList<>();
        try {
           jr=jo.getJSONArray("materials");
            jr1=jo.getJSONArray("games");
            for(int j=0;j<jr.length();j++)
            {
                if(jr.getJSONObject(j).getString("topicId").equals(a3))
                {
                    ar.add(jr.getJSONObject(j));
                }


            }
            for(int j=0;j<jr1.length();j++)
            {
                if(jr1.getJSONObject(j).getString("topicId").equals(a3))
                {
                    ar1.add(jr1.getJSONObject(j));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListView lv=(ListView)findViewById(R.id.material_listview);
        material_list_adapter mta=new material_list_adapter(getApplicationContext(),R.layout.material_list_item,ar);
        lv.setAdapter(mta);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String n=ar.get(i).getString("_id");
                    mtr_id=n;
                    new DownloadFileFromURL().execute(App.Url+"download/file-"+n+".pdf");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ListView lv1=(ListView)findViewById(R.id.game_listview);
        game_list_adpter mta1=new game_list_adpter(getApplicationContext(),R.layout.material_list_item,ar1);
        lv1.setAdapter(mta1);
        lv1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String link=ar1.get(i).getString("gameLink");
                    Intent in=new Intent(getApplicationContext(),Game.class);
                    in.putExtra("game_link","http://192.168.185.1:4000/"+link);
                    startActivity(in);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        if(shouldAskPermission()==true)
        {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};

            int permsRequestCode = 200;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(perms, permsRequestCode);
            }

        }

//        lv.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    }
//                });




       // Toast.makeText(getApplicationContext(),jr1.toString(),Toast.LENGTH_LONG).show();

    }
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){

            case 200:

                boolean writeAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;

                break;

        }

    }
    private boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();



                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream
                OutputStream output = new FileOutputStream("/sdcard/"+mtr_id+".pdf");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress(""+(int)((total*100)/lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);

            // Displaying downloaded image into image view
            // Reading image path from sdcard
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/"+mtr_id+".pdf";
            File f=new File(imagePath);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.fromFile(f));
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

            // setting downloaded into image view

        }

    }
}
