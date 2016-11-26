package com.coursegame.coursegame;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CourseDetails extends AppCompatActivity {

    private RecyclerView rc;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JSONArray jr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
       ActionBar acb=getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("course_name"));
        final String c_id=intent.getStringExtra("course_id");
        String url=App.Url+"courses/getById/"+c_id;
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.show();
        rc=(RecyclerView) findViewById(R.id.recycle_1);
        mLayoutManager = new LinearLayoutManager(this);
        rc.setHasFixedSize(true);

        rc.setLayoutManager(mLayoutManager);

//        Dialog dialog = new Dialog(getApplicationContext());
//        dialog.setContentView(R.layout.loading_dialog);
//        dialog.setTitle("Title...");
//        dialog.show();

        RequestQueue queue = MyRequests.getInstance(getApplicationContext()).getRequestQueue();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        builder.dismiss();
                        try {
                            jr = response.getJSONArray("topics");
                            App.course_details.put(c_id,response);
                            mAdapter = new TopicAdapter(jr,c_id);
                            rc.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });
        queue.add(jsObjRequest);
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
}
