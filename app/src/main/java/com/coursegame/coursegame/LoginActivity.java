package com.coursegame.coursegame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */

public class LoginActivity extends AppCompatActivity{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.


        TextView t=(TextView) findViewById(R.id.large_title);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/four.otf");
        t.setTypeface(custom_font);

        SharedPreferences shp = getSharedPreferences("appData", Context.MODE_PRIVATE);
        String a=shp.getString("status","false");
        //Toast.makeText(getApplicationContext(),a,Toast.LENGTH_SHORT).show();

        if(a.equals("true"))
        {
            Intent i =new Intent(getApplicationContext(),Home.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
//        Intent in =new Intent(getApplication(),Home.class);
//        startActivity(in);
        mEmailView = (EditText) findViewById(R.id.email);


        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().subscribeToTopic("temp1");
                attemptLogin(view);
                //
                //String token = FirebaseInstanceId.getInstance().getToken();
                //Log.i("myKey",token);
            }
        });

        TextView t1=(TextView) findViewById(R.id.new_account);
        t1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in =new Intent(getApplicationContext(),Registration.class);
                startActivity(in);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }



    /**
     * Callback received when a permissions request has been completed.
     */

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(final View v) {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        if (email.equals("") || password.equals("")) {
            Snackbar snackbar = Snackbar.make(v, "All fields are required", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(getResources().getColor(R.color.gold));
            View snackbarView = snackbar.getView();

// change snackbar text color
            int snackbarTextId = android.support.design.R.id.snackbar_text;
            TextView textView = (TextView) snackbarView.findViewById(snackbarTextId);
            textView.setTextColor(getResources().getColor(R.color.gold));
            snackbar.show();


        } else {

            final AlertDialog builder = new AlertDialog.Builder(this).create();
            LayoutInflater inflater = getLayoutInflater();


            View customView = inflater.inflate(R.layout.loading_dialog, null);
            TextView tv = (TextView) customView.findViewById(R.id.loading_text);
            tv.setText("Checking username and password");
            builder.setView(customView);
            builder.show();


            //showProgress(true);
            String url = App.Url + "users/login";
            JSONObject jo = new JSONObject();
            try {
                jo.put("username", email);
                jo.put("password", password);
                jo.put("userType", "0");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            RequestQueue queue = MyRequests.getInstance(getApplicationContext()).getRequestQueue();
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jo, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {


                            try {
                                //Toast.makeText(getApplicationContext(),response.getString("Status"),Toast.LENGTH_LONG).show();
                                if (response.getString("Status") == "true") {

                                    builder.dismiss();
                                    SharedPreferences sharedpreferences = getSharedPreferences("appData", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("status", "true");
                                    JSONObject jo = new JSONObject();
                                    jo = response.getJSONObject("LoggedUser");

                                    editor.putString("id", jo.getString("_id"));
                                    editor.putString("username", jo.getString("username"));
                                    editor.putString("status", "true");
                                    editor.putString("user",jo.toString());
                                    editor.apply();
                                    editor.commit();

                                    Intent in = new Intent(getApplicationContext(), Home.class);
                                    startActivity(in);

                                } else {
                                    builder.dismiss();
                                    Snackbar snackbar = Snackbar.make(v, "Username or password is incorrect", Snackbar.LENGTH_LONG);
                                    snackbar.setActionTextColor(getResources().getColor(R.color.gold));
                                    View snackbarView = snackbar.getView();

// change snackbar text color
                                    int snackbarTextId = android.support.design.R.id.snackbar_text;
                                    TextView textView = (TextView) snackbarView.findViewById(snackbarTextId);
                                    textView.setTextColor(getResources().getColor(R.color.gold));
                                    snackbar.show();

                                }


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
            // Reset errors.
            mEmailView.setError(null);
            mPasswordView.setError(null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */

}

