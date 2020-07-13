package com.harshita.teach;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class TeacherListActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 101;
    ArrayList<Teacher> listTeachers = new ArrayList<Teacher>();
    private double longitude, latitude;
    private String add;
    private RecyclerView recyclerview;
    private TeacherAdapter mAdapter;

    private TextView address, noResultText;
    private Button btnFind;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);

        address = findViewById(R.id.address_text);
        btnFind = findViewById(R.id.find_btn);
        layout = findViewById(R.id.map_layout);
        noResultText = findViewById(R.id.no_result_text);

        listTeachers = (ArrayList<Teacher>) getIntent().getSerializableExtra("TeachersListExtra");
        latitude = (double) getIntent().getSerializableExtra("Latitude");
        longitude = (double) getIntent().getSerializableExtra("Longitude");
        add = (String) getIntent().getSerializableExtra("Address");

        address.setText("Your address:\n" + add);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(TeacherListActivity.this);

        recyclerview=(RecyclerView)findViewById(R.id.recycler_view);
        mAdapter = new TeacherAdapter(listTeachers);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(mLayoutManger);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.GONE);
                if(listTeachers.size() != 0) {
                    recyclerview.setVisibility(View.VISIBLE);
                } else {

                    noResultText.setVisibility(View.VISIBLE);

                }

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng latLng = new LatLng(latitude,longitude);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I Am Here");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
        googleMap.addMarker(markerOptions);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}