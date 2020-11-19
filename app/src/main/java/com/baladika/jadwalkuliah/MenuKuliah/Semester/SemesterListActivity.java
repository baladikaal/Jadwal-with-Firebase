package com.baladika.jadwalkuliah.MenuKuliah.Semester;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baladika.jadwalkuliah.MenuKuliah.AdapterJadwal.SemesterReqAdapterRecyclerView;
import com.baladika.jadwalkuliah.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SemesterListActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<SemesterReq> semesterReq;
    private SemesterReqAdapterRecyclerView semesterReqAdapterRecyclerView;
    private String Uid;
    private RecyclerView jadwal_list;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Jadwal Kuliah");

        database = FirebaseDatabase.getInstance().getReference().child("MenuKuliah");
        Uid = getIntent().getStringExtra("Uid");

        DatabaseReference offline = database.child("Semester");
        offline.keepSynced(true);

        jadwal_list = findViewById(R.id.jadwal_list);

        RecyclerView.LayoutManager mLayoutManager  = new LinearLayoutManager(getApplicationContext());
        jadwal_list.setLayoutManager(mLayoutManager);
        jadwal_list.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(SemesterListActivity.this,
                null,
                "Please wait...",
                true,
                false);


        database.child("Semester").child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                semesterReq = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    SemesterReq semester =noteDataSnapshot.getValue(SemesterReq.class);
                    semester.setKey(noteDataSnapshot.getKey());
                    semester.setUserId(Uid);
                    semesterReq.add(semester);
                }

                semesterReqAdapterRecyclerView = new SemesterReqAdapterRecyclerView(semesterReq, SemesterListActivity.this);
                jadwal_list.setAdapter(semesterReqAdapterRecyclerView);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
                loading.dismiss();
            }
        });

        findViewById(R.id.fab_add_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SemesterListActivity.this, SemesterUpdateActivity.class)
                        .putExtra("Uid",Uid)
                        .putExtra("id","")
                        .putExtra("semester","")
                        .putExtra("tahun",""));
            }
        });
    }
}
