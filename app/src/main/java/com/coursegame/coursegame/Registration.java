package com.coursegame.coursegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Registration extends AppCompatActivity {

    EditText fname,lname,uname,password,contact,std_id;
    Spinner batch,year,sem;
    AppCompatButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);



        batch=(Spinner)findViewById(R.id.batch_spinner);
        year=(Spinner)findViewById(R.id.year_spinner);
        sem=(Spinner)findViewById(R.id.sem_spinner);

        btn=(AppCompatButton)findViewById(R.id.r_submit);

        fname=(EditText)findViewById(R.id.r_first_name);
        lname=(EditText)findViewById(R.id.r_last_name);
        uname=(EditText)findViewById(R.id.r_email);
        password=(EditText)findViewById(R.id.r_password);
        contact=(EditText)findViewById(R.id.r_contact);
        std_id=(EditText)findViewById(R.id.r_student_id);

        String[] b,s,y;

        b=new String[2];
        s=new String[4];
        y=new String[5];

        b[0]="MSCIT";
        b[1]="BTECH";

        s[0]="M1";
        s[1]="M2";
        s[2]="M3";
        s[3]="M4";

        y[0]="2012";
        y[1]="2013";
        y[2]="2014";
        y[3]="2015";
        y[4]="2016";






        ArrayAdapter<String> adapter_batch = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, b);

        ArrayAdapter<String> adapter_sem = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, s);

        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, y);
        batch.setAdapter(adapter_batch);
        sem.setAdapter(adapter_sem);
        year.setAdapter(adapter_year);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
                String sfname,slname,suname,spassword,scontact,sstd_id,sbatch,syear,ssem;
                sfname=fname.getText().toString();
                slname=lname.getText().toString();
                suname=uname.getText().toString();
                spassword=password.getText().toString();
                scontact=contact.getText().toString();
                sstd_id=std_id.getText().toString();

                sbatch=batch.getSelectedItem().toString();
                syear=year.getSelectedItem().toString();
                ssem=sem.getSelectedItem().toString();


//                {
//                    "_id": "5835d7f475e7bb0012eb58dd",
//                        "firstName": "Mansi",
//                        "lastName": "Shah",
//                        "userType": "1",
//                        "username": "ms@gmail.com",
//                        "contact": "9858975252",
//                        "specialization": "sen",
//                        "university": "daiict",
//                        "city": "baroda",
//                        "country": "india",
//                        "facultyType": null,
//                        "programme": null,
//                        "studentId": null,
//                        "year": null,
//                        "semester": null
//                },


                JSONObject jo=new JSONObject();
                try {
                    jo.put("firstName",sfname);
                    jo.put("lastName",slname);
                    jo.put("userType","0");
                    jo.put("username",suname);
                    jo.put("contact",scontact);
                    jo.put("studentId",sstd_id);
                    jo.put("year",syear);
                    jo.put("semester",ssem);
                    jo.put("password",spassword);
                    jo.put("programme",sbatch);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue queue = MyRequests.getInstance(getApplicationContext()).getRequestQueue();
                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.POST, App.Url+"users/"+"register", jo, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                //Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
                                try {
                                    if(response.getString("Status").equals("true"))
                                    {
                                        Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {


                            }
                        });
                queue.add(jsObjRequest);

            }

        });

    }
}
