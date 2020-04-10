package com.example.trackcovid_19.ui.country;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.trackcovid_19.R;

public class CovidCountryDetail extends AppCompatActivity {

    TextView tvDeatilCountryName, tvDetailTotalcases, tvDeatailTodaycases, tvDetailTotalDeaths,
            tvDetailTodayDeath, tvDetailTotalRecovered, tvDetailTotalActive, tvDetailTotalCritical;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_country_detail);

        //view

        tvDeatilCountryName = findViewById(R.id.tvDeatilCountryName);
        tvDetailTotalcases = findViewById(R.id.tvDetailTotalCases);
        tvDeatailTodaycases = findViewById(R.id.tvDetailTodayCases);
        tvDetailTotalDeaths = findViewById(R.id.tvDetailTotalDeath);
        tvDetailTodayDeath = findViewById(R.id.tvDetailTodayDeath);
        tvDetailTotalRecovered = findViewById(R.id.tvDetailTotalRecovered);
        tvDetailTotalActive = findViewById(R.id.tvDetailTotalActive);
        tvDetailTotalCritical = findViewById(R.id.tvDetailTotalCritical);

        //call covid country
        CovidCountry covidCountry = getIntent().getParcelableExtra("EXTRA_COVID");


        tvDeatilCountryName.setText(covidCountry.getmCovidCountry());
        tvDetailTotalcases.setText(covidCountry.getmCases());
        tvDeatailTodaycases.setText(covidCountry.getmTodayCases());
        tvDetailTotalDeaths.setText(covidCountry.getmDeaths());
        tvDetailTodayDeath.setText(covidCountry.getmTodayDeaths());
        tvDetailTotalRecovered.setText(covidCountry.getmRecovered());
        tvDetailTotalActive.setText(covidCountry.getmActiveCritical());
        tvDetailTotalCritical.setText(covidCountry.getmCritical());

    }
}
