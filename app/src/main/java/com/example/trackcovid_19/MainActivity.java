package com.example.trackcovid_19;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mauth;
    private FirebaseUser mcurrentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mauth = FirebaseAuth.getInstance();
        mcurrentuser = mauth.getCurrentUser();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setItemIconTintList(null);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mcurrentuser==null){
            Intent activityToLgoin = new Intent(MainActivity.this,LoginScreen.class);
            activityToLgoin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activityToLgoin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(activityToLgoin);
            finish();
        }

    }
}
