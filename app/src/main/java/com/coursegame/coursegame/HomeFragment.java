package com.coursegame.coursegame;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by disha on 19-11-2016.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    private RecyclerView rc;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static boolean load;
    JSONArray jr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        //Toast.makeText(getActivity(),"Hf",Toast.LENGTH_SHORT).show();
        load=false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


//        WebView wb=(WebView) rootView.findViewById(R.id.myweb);
//        wb.loadUrl("http://www.google.co.in/");

//        WebView mWebView = (WebView) rootView.findViewById(R.id.myweb);
//        mWebView.loadUrl("http://coursegame.pe.hu/");

        // Enable Javascript
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
//        mWebView.setWebViewClient(new WebViewClient());

        final JSONArray j1=new JSONArray();
        JSONObject jo=new JSONObject();
        try {
            jo.put("name","Course one");
            jo.put("desc","This is just for test");
            j1.put(jo);

            jo=new JSONObject();
            jo.put("name","Course two");
            jo.put("desc","This is also just for test");
            j1.put(jo);

        } catch (JSONException e) {
            e.printStackTrace();
        }





        //String url="https://shielded-tor-32602.herokuapp.com/courses";
        rc=(RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rc.setHasFixedSize(true);
        rc.setLayoutManager(mLayoutManager);

        SharedPreferences shp = getActivity().getSharedPreferences("appData", Context.MODE_PRIVATE);
        String temp=shp.getString("id","");
        String url=App.Url+"enrollments/getByStd/"+temp;
        if(!load) {
            final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
            LayoutInflater inflater1 = getActivity().getLayoutInflater();


            View customView = inflater.inflate(R.layout.loading_dialog, null);
            TextView tv=(TextView)customView.findViewById(R.id.loading_text);
            tv.setText("Fetching Content");
            builder.setView(customView);
            builder.show();
            RequestQueue queue = MyRequests.getInstance(getActivity()).getRequestQueue();
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            //Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();

                            try {
                                jr = response.getJSONArray("Result");
                                builder.dismiss();
                                //Toast.makeText(getActivity(), jr.toString(), Toast.LENGTH_LONG).show();
                                mAdapter = new MyCourseAdapter(jr);
                                rc.setAdapter(mAdapter);
                                load=true;

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
        else
        {
            mAdapter = new MyCourseAdapter(jr);
            rc.setAdapter(mAdapter);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        Toast.makeText(getActivity(),"onResume",Toast.LENGTH_SHORT).show();
    }
}
