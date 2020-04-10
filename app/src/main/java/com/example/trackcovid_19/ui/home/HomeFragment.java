package com.example.trackcovid_19.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trackcovid_19.ChatBot;
import com.example.trackcovid_19.IndiaCovidCases;
import com.example.trackcovid_19.More;
import com.example.trackcovid_19.R;
import com.example.trackcovid_19.bot;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private TextView tvTotalConfirmed, tvToalDeaths, tvTotalRecovered, tvLastUpdated;
    private ProgressBar progressBar;
    private FloatingActionButton optionsBtn;
    private FloatingActionButton mMoreBtn;
    private FloatingActionButton btnIndia;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //call view
        tvTotalConfirmed = root.findViewById(R.id.tvTotalConfirmed);
        tvToalDeaths = root.findViewById(R.id.tvTotalDeath);
        tvTotalRecovered = root.findViewById(R.id.tvTotalRecorved);
        tvLastUpdated = root.findViewById(R.id.tvLastUpdate);
        progressBar = root.findViewById(R.id.progress_circular_home);
        optionsBtn = root.findViewById(R.id.btnoptions);
        mMoreBtn = root.findViewById(R.id.btnMore);
        btnIndia = root.findViewById(R.id.btnIndia);

        btnIndia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toIndia = new Intent(HomeFragment.this.getActivity(), IndiaCovidCases.class);
                startActivity(toIndia);
            }
        });

        mMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMore = new Intent(HomeFragment.this.getActivity(), More.class);
                startActivity(toMore);
            }
        });

        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBot = new Intent(HomeFragment.this.getActivity(), ChatBot.class);
                startActivity(toBot);

            }
        });

        //call volley
        getdata();
        
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Animation aniFade = AnimationUtils.loadAnimation(HomeFragment.this.getActivity(),R.anim.left);
        btnIndia.startAnimation(aniFade);
        Animation aniFade2 = AnimationUtils.loadAnimation(HomeFragment.this.getActivity(),R.anim.left);
        mMoreBtn.startAnimation(aniFade2);
        Animation aniFade3 = AnimationUtils.loadAnimation(HomeFragment.this.getActivity(),R.anim.right);
        optionsBtn.startAnimation(aniFade3);

    }

    private String getDate(long milliSecond){
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa" );
        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
        return formatter.format(calendar.getTime());
    }

    private void getdata() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "https://corona.lmao.ninja/all";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    tvTotalConfirmed.setText(jsonObject.getString("cases"));
                    tvToalDeaths.setText(jsonObject.getString("deaths"));
                    tvTotalRecovered.setText(jsonObject.getString("recovered"));
                    tvLastUpdated.setText("Last updated"+"\n"+getDate(jsonObject.getLong("updated")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("Error Resonse", error.toString());
                    }


    });

        queue.add(stringRequest);
    }
}