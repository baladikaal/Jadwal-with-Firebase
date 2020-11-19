package com.baladika.jadwalkuliah.MenuUjian.Semester;

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

import com.baladika.jadwalkuliah.MenuUjian.AdapterUjian.UjianSemesterReqAdapterRecyclerView;
import com.baladika.jadwalkuliah.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UjianSemesterListActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<UjianSemesterReq> semesterReq;
    private UjianSemesterReqAdapterRecyclerView ujianSemesterReqAdapterRecyclerView;
    private String Uid;
    private RecyclerView jadwal_list;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujian_semester_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Jadwal Ujian");

        database = FirebaseDatabase.getInstance().getReference().child("MenuUjian");
        Uid = getIntent().getStringExtra("Uid");

        DatabaseReference offline = database.child("Semester");
        offline.keepSynced(true);

        jadwal_list = findViewById(R.id.ujian_jadwal_list);

        RecyclerView.LayoutManager mLayoutManager  = new LinearLayoutManager(getApplicationContext());
        jadwal_list.setLayoutManager(mLayoutManager);
        jadwal_list.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(UjianSemesterListActivity.this,
                null,
                "Please wait...",
                true,
                false);

        database.child("Semester").child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                semesterReq = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    UjianSemesterReq semester =noteDataSnapshot.getValue(UjianSemesterReq.class);
                    semester.setKey(noteDataSnapshot.getKey());
                    semester.setUserId(Uid);
                    semesterReq.add(semester);
                }

                ujianSemesterReqAdapterRecyclerView = new UjianSemesterReqAdapterRecyclerView(semesterReq, UjianSemesterListActivity.this);
                jadwal_list.setAdapter(ujianSemesterReqAdapterRecyclerView);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
                loading.dismiss();
            }
        });

        findViewById(R.id.fab_add_ujian_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UjianSemesterListActivity.this, UjianSemesterUpdateActivity.class)
                        .putExtra("Uid",Uid)
                        .putExtra("id","")
                        .putExtra("semester","")
                        .putExtra("tahun",""));
            }
        });
    }
}
