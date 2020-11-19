package com.baladika.jadwalkuliah.MenuLab.Lab;

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

import com.baladika.jadwalkuliah.MenuLab.AdapterLab.LabReqAdapterRecyclerView;
import com.baladika.jadwalkuliah.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LabListActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<LabReq> labReq;
    private LabReqAdapterRecyclerView labReqAdapterRecyclerView;
    private String Uid,Date;
    private RecyclerView jadwal_list;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_list);

        database = FirebaseDatabase.getInstance().getReference().child("MenuLab");
        Uid = getIntent().getStringExtra("Uid");
        Date = getIntent().getStringExtra("date");



        DatabaseReference offline = database;
        offline.keepSynced(true);

        jadwal_list = findViewById(R.id.jadwal_lab_list);

        RecyclerView.LayoutManager mLayoutManager  = new LinearLayoutManager(getApplicationContext());
        jadwal_list.setLayoutManager(mLayoutManager);
        jadwal_list.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(LabListActivity.this,
                null,
                "Please wait...",
                true,
                false);

        database.child(Uid).child("Hari").child(Date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                labReq = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    LabReq lab =noteDataSnapshot.getValue(LabReq.class);
                    lab.setDate(Date);
                    lab.setKey(noteDataSnapshot.getKey());
                    lab.setUserId(Uid);
                    labReq.add(lab);
                }

                labReqAdapterRecyclerView = new LabReqAdapterRecyclerView(labReq, LabListActivity.this);
                jadwal_list.setAdapter(labReqAdapterRecyclerView);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
                loading.dismiss();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Jadwal Laboratorium");

        findViewById(R.id.fab_add_lab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabListActivity.this, LabUpdateActivity.class)
                        .putExtra("Uid",Uid)
                        .putExtra("date",Date)
                        .putExtra("id","")
                        .putExtra("lab","")
                        .putExtra("jam","")
                        .putExtra("hari",Date)
                        .putExtra("note",""));
            }
        });
    }


}
