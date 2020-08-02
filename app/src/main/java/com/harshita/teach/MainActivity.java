package com.harshita.teach;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private Button btnLogOut, btnFind;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ArrayList<Teacher> listOfTeacher = new ArrayList<>();
    private ArrayList<Teacher> listOfResultTeachers = new ArrayList<>();
    private ResultReceiver resultReceiver;
    private EditText subject;
    private String sub, address;
    private double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultReceiver = new AddressResultReceiver(new Handler());

        btnLogOut = findViewById(R.id.logout_btn);
        subject = findViewById(R.id.subject);
        btnFind = findViewById(R.id.submit_btn);

        listOfTeacher.add(new Teacher("Name1", "BBA in MIS", new String[]{"ACCT 2331", "STAT 3331", "BIOL 2339"}, "₹800 - ₹1000", R.drawable.image, 25.4587458, 70.5428795));
        listOfTeacher.add(new Teacher("Name2", "BS in Health Education", new String[]{"BIOL 2331", "CHEM 3331", "HLTA 2339"}, "₹800 - ₹1000", R.drawable.image, 55.4587458, 96.5428795));
        listOfTeacher.add(new Teacher("Name3", "BS in Health Education", new String[]{"ACCT 2331", "BIOL 2331", "CHEM 3331"}, "₹800 - ₹1000", R.drawable.image, 125.4587458, 10.5428795));
        listOfTeacher.add(new Teacher("Name4", "BBA in MIS", new String[]{"BIOL 2331", "STAT 3331", "BIOL 2339"}, "10$ - 15$", R.drawable.image, 96.4587458, 12.5428795));
        listOfTeacher.add(new Teacher("Name5", "BS in Health Education", new String[]{"ACCT 2331", "CHEM 3331", "BIOL 2331"}, "₹800 - ₹1000", R.drawable.image, 58.4587458, 46.5428795));
        listOfTeacher.add(new Teacher("Name6", "BBA in MIS", new String[]{"BIOL 2331", "HLTA 2339", "CHEM 3331"}, "₹800 - ₹1000", R.drawable.image, 22.4587458, 22.5428795));
        listOfTeacher.add(new Teacher("Name7", "BS in Health Education", new String[]{"HLTA 2339", "STAT 3331", "BIOL 2339"}, "₹800 - ₹1000", R.drawable.image, 30.4587458, 50.5428795));
        listOfTeacher.add(new Teacher("Name8", "BS in Health Education", new String[]{"CHEM 3331", "STAT 3331", "BIOL 2331"}, "₹800 - ₹1000", R.drawable.image, 52.4587458, 75.5428795));
        listOfTeacher.add(new Teacher("Name9", "BS in Health Education", new String[]{"CHEM 3331", "STAT 3331", "HLTA 2339"}, "₹800 - ₹1000", R.drawable.image, 33.4587458, 66.5428795));
        listOfTeacher.add(new Teacher("Name10", "BBA in MIS", new String[]{"HLTA 2339", "BIOL 2331", "CHEM 3331"}, "₹800 - ₹1000", R.drawable.image, 21.4587458, 90.5428795));

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(I);
                finish();
                ;
            }
        });

        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        } else {
            getCurrentLocation();
        }

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sub = subject.getText().toString();

                for (int i = 0; i < listOfTeacher.size(); i++) {
                    if (sub.equalsIgnoreCase(listOfTeacher.get(i).getSubjects()[0]) || sub.equalsIgnoreCase(listOfTeacher.get(i).getSubjects()[1]) ||
                            sub.equalsIgnoreCase(listOfTeacher.get(i).getSubjects()[2])) {
                        listOfResultTeachers.add(listOfTeacher.get(i));
                    }
                }

                for (int i = 0; i < listOfResultTeachers.size(); i++) {
                    listOfResultTeachers.get(i).setDistance(distance(latitude, longitude,
                            listOfResultTeachers.get(i).getLat(), listOfResultTeachers.get(i).getLng()));
                }

                Collections.sort(listOfResultTeachers);


                Intent intent = new Intent(MainActivity.this, TeacherListActivity.class);
                intent.putExtra("TeachersListExtra", (ArrayList<Teacher>) listOfResultTeachers);
                intent.putExtra("Latitude", latitude);
                intent.putExtra("Longitude", longitude);
                intent.putExtra("Address", address);

                startActivity(intent);
            }
        });

        final RelativeLayout comingSoonLayout = findViewById(R.id.coming_soon_layout);
        final LinearLayout detailsLayout = findViewById(R.id.details_layout);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_teacher:
                        detailsLayout.setVisibility(View.VISIBLE);
                        comingSoonLayout.setVisibility(View.GONE);
                        break;
                    case R.id.action_video_lecture:
                    case R.id.action_buy_books:
                        detailsLayout.setVisibility(View.GONE);
                        comingSoonLayout.setVisibility(View.VISIBLE);
                        break;
                }

                return true;
            }
        });

    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1.609344;

        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {

        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();

                            Location location = new Location("providerNA");
                            location.setLatitude(latitude);
                            location.setLongitude(longitude);
                            fetchAddressFromLatLong(location);
                        }
                    }
                }, Looper.getMainLooper());
    }

    private void fetchAddressFromLatLong(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECIEVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    private class AddressResultReceiver extends ResultReceiver {

        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            if (resultCode == Constants.SUCCESS_RESULT) {
                address = (resultData.getString(Constants.RESULT_DATA_KEY));
            } else {
                Toast.makeText(MainActivity.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }

        }
    }

}