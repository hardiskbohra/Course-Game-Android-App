package com.coursegame.coursegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by disha on 22-11-2016.
 */

public class ProfileFragment extends Fragment {

    boolean load;
    EditText fname,lname,username,stdid,prg;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);



        fname=(EditText)rootView.findViewById(R.id.pf_first);
        lname=(EditText)rootView.findViewById(R.id.pf_last);
        username=(EditText)rootView.findViewById(R.id.pf_username);
        stdid=(EditText)rootView.findViewById(R.id.pf_student_id);
        prg=(EditText)rootView.findViewById(R.id.pf_program);


        fname.setText("");



        return  rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences shp = getActivity().getSharedPreferences("appData", Context.MODE_PRIVATE);
        String a=shp.getString("user","");
        JSONObject jo;
        try {
            jo=new JSONObject(a);
            fname.setText(jo.getString("firstName"));
            lname.setText(jo.getString("lastName"));
            username.setText(jo.getString("username"));
            stdid.setText(jo.getString("201512089"));
            prg.setText(jo.getString("programme"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button btn=(Button)getActivity().findViewById(R.id.btn_logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),LoginActivity.class);
                SharedPreferences shp = getActivity().getSharedPreferences("appData", Context.MODE_PRIVATE);
                shp.edit().clear().commit();

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

    }
}
