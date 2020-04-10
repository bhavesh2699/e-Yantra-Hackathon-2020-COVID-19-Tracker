package com.example.trackcovid_19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class GetLocation extends AppCompatActivity {
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private TextView mtxt;
    private FirebaseUser user;
    private DatabaseReference mdatabase;
    private FirebaseUser mcurrentuser;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private final int PERMISSION_REQ_FINE_LOCATION =100;
    private final int PERMISSION_REQ_CODE_COARSE =99;
    LocationTrack locationTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("UserLocation");

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }


        Button btn = (Button) findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    locationTrack = new LocationTrack(GetLocation.this);


                if (locationTrack.canGetLocation()) {


                    double longitude = locationTrack.getLongitude();
                    double latitude = locationTrack.getLatitude();

                    Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
                    // store these under uid in location as latitude and longitude
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null){
                        String uid = user.getUid();
                        mdatabase.child(uid).child("lng").push().setValue(longitude);
                        mdatabase.child(uid).child("lat").push().setValue(latitude);
                        Intent tomain = new Intent(GetLocation.this,MainActivity.class);
                        startActivity(tomain);
                    }

                } else {

                    locationTrack.showSettingsAlert();
                }

            }


        });

    }


    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            FirebaseAuth.getInstance().signOut();
                                            finish();
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener,DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(GetLocation.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", listener)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }




//    public boolean hasPermissions(String permission){
//        return (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED);
//    }
//    private void requestPermission(String permission,int code) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{
//                    permission}, code);
//        }
//    }


//
//    @Override
//    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case PERMISSION_REQ_FINE_LOCATION:{
//
//                boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
//                        ACCESS_FINE_LOCATION
//                );
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    openAttendanceActivity();
//                }
//                else if(!showRationale){
//                    notifyUser(true);
//                }else{
//                    // permission denied
//                    notifyUser(false);
//                }
//            }break;
//            case PERMISSION_REQ_CODE_COARSE:{
//
//                boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
//                        ACCESS_COARSE_LOCATION
//                );
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    openAttendanceActivity();
//                }
//                else if(!showRationale){
//                    notifyUser(true);
//                }else{
//                    // permission denied
//                    notifyUser(false);
//                }
//            }
//        }
//    }
//
//    private void requestPermission(){
//        if (!hasPermissions(ACCESS_FINE_LOCATION)) {
//            requestPermission(ACCESS_FINE_LOCATION,PERMISSION_REQ_FINE_LOCATION);
//
//        }else if (!hasPermissions(ACCESS_COARSE_LOCATION)) {
//            requestPermission(ACCESS_COARSE_LOCATION,PERMISSION_REQ_CODE_COARSE);
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (!hasPermissions(ACCESS_FINE_LOCATION)) {
//            requestPermission(ACCESS_FINE_LOCATION,PERMISSION_REQ_FINE_LOCATION);
//
//        }else if (!hasPermissions(ACCESS_COARSE_LOCATION)) {
//            requestPermission(ACCESS_COARSE_LOCATION,PERMISSION_REQ_CODE_COARSE);
//        }
//        else{
//            openAttendanceActivity();
//        }
//    }
//    private void notifyUser(final boolean openSetting){
//
//        showMessageDialog(this, "Please provide permission to continue", new OkListener() {
//            @Override
//            public void onOkClicked() {
//                if(openSetting) {
//                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                    Uri uri = Uri.fromParts("package", getPackageName(), null);
//                    intent.setData(uri);
//                    startActivityForResult(intent, 2);
//                }
//                else
//                    requestPermission();
//            }
//        });
//    }
//
//    public void openAttendanceActivity(){
//      //after permission granted
//    }
//
//    public static void showMessageDialog(Activity activity, String msg, final OkListener listener) {
//        final AlertDialog.Builder builder= new AlertDialog.Builder(activity);
//        builder.setMessage(msg)
//                .setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        listener.onOkClicked();
//                    }
//                })
//        ;
//        AlertDialog alert = builder.create();
//        if(!activity.isFinishing())
//            alert.show();
//    }
//
//    public interface OkListener {
//        void onOkClicked();
//    }

}
