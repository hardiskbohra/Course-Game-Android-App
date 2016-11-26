package com.coursegame.coursegame;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by disha on 21-11-2016.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private static JSONArray jr;
    private static String std_id;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public CourseAdapter()
    {

    };
    public void swap(){
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        // each data item is just a string in this case
        public TextView mTextView1,mTextView2;
        CourseAdapter c;
        public ViewHolder(View v,CourseAdapter c) {
            super(v);
            mTextView1=(TextView) v.findViewById(R.id.course_name);
            mTextView2=(TextView) v.findViewById(R.id.course_desc);
            v.setOnClickListener(this);
            this.c=c;
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(),getPosition()+" sdsdsd",Toast.LENGTH_SHORT).show();
            open(view);

        }

        public void open(final View view){
            final Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.course_dialog);
            dialog.setTitle("Title...");

            // set the custom dialog components - text, image and button
            TextView text = (TextView) dialog.findViewById(R.id.cdl_title);
            TextView text1 = (TextView) dialog.findViewById(R.id.cdl_dec);
            try {
                text.setText(jr.getJSONObject(getPosition()).getString("name"));
                text1.setText(jr.getJSONObject(getPosition()).getString("desc"));

            } catch (JSONException e) {
                e.printStackTrace();
            }



            Button dialogButton = (Button) dialog.findViewById(R.id.cdl_cancel);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Button enroll = (Button) dialog.findViewById(R.id.cdl_enroll);
            enroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    String url="https://shielded-tor-32602.herokuapp.com/enrollments/insert";
                    String c_id="";
                    JSONObject j=new JSONObject();
                    try {

                        c_id=jr.getJSONObject(getPosition()).getString("_id");
                        j.put("userId",std_id);
                        j.put("courseId",c_id);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("abc",j.toString());
                    RequestQueue queue = MyRequests.getInstance(view.getContext()).getRequestQueue();
                    final String finalC_id = c_id;
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest
                            (Request.Method.POST, url, j, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    //Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
                                    FirebaseMessaging.getInstance().subscribeToTopic(finalC_id);
                                        Log.i("enroll_course2323",response.toString());
                                    jr.remove(getPosition());
                                    c.notifyDataSetChanged();



                                           //jr = response.getJSONArray("Result");

                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO Auto-generated method stub

                                }
                            });
                    queue.add(jsObjRequest);


                }
            });

            dialog.show();
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public CourseAdapter(JSONArray jr,String course_id) {

        this.jr=jr;
        this.std_id=course_id;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v,this);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try {
            holder.mTextView1.setText(jr.getJSONObject(position).getString("name"));
            holder.mTextView2.setText(jr.getJSONObject(position).getString("desc"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    // Return the size of your dataset (invoked by the layout manager)

    @Override
    public int getItemCount() {
        return jr.length();
    }
}
