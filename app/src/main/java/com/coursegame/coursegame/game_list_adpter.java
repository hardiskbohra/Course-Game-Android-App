package com.coursegame.coursegame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by disha on 24-11-2016.
 */

public class game_list_adpter extends ArrayAdapter<JSONObject> {

    ArrayList<JSONObject> ar;

    public game_list_adpter(Context context, int resource, ArrayList<JSONObject> objects) {
        super(context, resource, objects);
        this.ar=objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.material_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tv1 = (TextView) convertView.findViewById(R.id.mtr_title);
        TextView tv2 = (TextView) convertView.findViewById(R.id.mtr_desc);
        JSONObject jo=ar.get(position);
        // Populate the data into the template view using the data object
        try {
            tv1.setText(jo.getString("title"));
            tv2.setText(jo.getString("desc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Return the completed view to render on screen
        return convertView;
    }

}
