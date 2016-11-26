package com.coursegame.coursegame;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by disha on 20-11-2016.
 */

public class CoursesFragment extends Fragment {
    private RecyclerView rc;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static boolean load;
    JSONArray jr;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        load=false;
        //Toast.makeText(getActivity(),"Cf",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.refresh:

                Toast.makeText(getActivity(),"Refress",Toast.LENGTH_SHORT).show();
                return true;
            default:
                break;
        }

        return true;
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //Toast.makeText(getActivity(),"Cf",Toast.LENGTH_SHORT).show();

//        WebView wb=(WebView) rootView.findViewById(R.id.myweb);
//        wb.loadUrl("http://www.google.co.in/");

//        WebView mWebView = (WebView) rootView.findViewById(R.id.myweb);
//        mWebView.loadUrl("http://coursegame.pe.hu/");

        // Enable Javascript
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
//        mWebView.setWebViewClient(new WebViewClient());

        SharedPreferences shp = getActivity().getSharedPreferences("appData", Context.MODE_PRIVATE);
        final String temp=shp.getString("id","");


        String url=App.Url+"courses/getByStd/"+temp;
       Log.i("url_en",url);
        rc=(RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rc.setHasFixedSize(true);

        rc.setLayoutManager(mLayoutManager);


//        //Toast.makeText(getActivity(),"fgfgfg",Toast.LENGTH_LONG).show();

        if(!load) {
            RequestQueue queue = MyRequests.getInstance(getActivity()).getRequestQueue();
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            //Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();

                            try {
                                Log.i("enroll_course",response.toString());
                                jr = response.getJSONArray("Result");

                                mAdapter = new CourseAdapter(jr,temp);
                                rc.setAdapter(mAdapter);
                                load=true;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                        }
                    });
            queue.add(jsObjRequest);
        }
        else
        {
            mAdapter = new CourseAdapter(jr,temp);
            rc.setAdapter(mAdapter);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}


