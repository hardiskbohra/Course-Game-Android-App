package com.coursegame.coursegame;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by disha on 22-11-2016.
 */

public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.ViewHolder>
{
    private static JSONArray jr;
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView1,mTextView2;
        public ViewHolder(View v) {
            super(v);
            mTextView1=(TextView) v.findViewById(R.id.course_name);
            mTextView2=(TextView) v.findViewById(R.id.course_desc);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(),getPosition()+" sdsdsd",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(view.getContext(),CourseDetails.class);
            try {
                i.putExtra("course_id",jr.getJSONObject(getPosition()).getString("_id"));
                i.putExtra("course_name",jr.getJSONObject(getPosition()).getString("name"));
                view.getContext().startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

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

            dialog.show();
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyCourseAdapter(JSONArray jr) {

        this.jr=jr;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyCourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        MyCourseAdapter.ViewHolder vh = new MyCourseAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyCourseAdapter.ViewHolder holder, int position) {
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
