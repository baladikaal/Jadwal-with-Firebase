package com.baladika.jadwalkuliah.MenuKuliah.CekTugas;

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

import com.baladika.jadwalkuliah.MenuKuliah.AdapterJadwal.CekTugasReqAdapterRecyclerView;
import com.baladika.jadwalkuliah.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CekTugasListActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<CekTugasReq> tugasReqList;
    private CekTugasReqAdapterRecyclerView cekTugasReqAdapterRecyclerView;
    private String Uid,SmId,MkId;
    private RecyclerView jadwal_list;
    private ProgressDialog loading;
    private FloatingActionButton fab_addTugas;

    private String sId, sJudul, sHari, sKet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_tugas_list);

        database = FirebaseDatabase.getInstance().getReference().child("MenuKuliah");
        Uid = getIntent().getStringExtra("Uid");
        SmId = getIntent().getStringExtra("id");
        MkId = getIntent().getStringExtra("mid");

        sId = getIntent().getStringExtra("idTgs");
        sJudul = getIntent().getStringExtra("judul");
        sHari = getIntent().getStringExtra("hari");
        sKet = getIntent().getStringExtra("keterangan");

        DatabaseReference offline = database.child("Tugas");
        offline.keepSynced(true);

        jadwal_list = findViewById(R.id.jadwal_tugas_list);
        fab_addTugas = findViewById(R.id.fab_add_tugas);

        RecyclerView.LayoutManager mLayoutManager  = new LinearLayoutManager(getApplicationContext());
        jadwal_list.setLayoutManager(mLayoutManager);
        jadwal_list.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(CekTugasListActivity.this,
                null,
                "Please wait...",
                true,
                false);

        database.child("Tugas").child(Uid).child(SmId).child(MkId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tugasReqList = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    CekTugasReq tugas =noteDataSnapshot.getValue(CekTugasReq.class);
                    tugas.setKey(noteDataSnapshot.getKey());
                    //mengiri id user dan matkul ke MatkulReq
                    tugas.setUserId(Uid);
                    tugas.setSmstrId(SmId);
                    tugas.setMatkulID(MkId);
                    tugasReqList.add(tugas);
                }

                cekTugasReqAdapterRecyclerView = new CekTugasReqAdapterRecyclerView(tugasReqList, CekTugasListActivity.this);
                jadwal_list.setAdapter(cekTugasReqAdapterRecyclerView);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
                loading.dismiss();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TUGAS");

        fab_addTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CekTugasListActivity.this, CekTugasUpdateActivity.class)
                        .putExtra("Uid",Uid)
                        .putExtra("id",SmId)
                        .putExtra("mid",MkId)
                        .putExtra("idTgs","")
                        .putExtra("judul","")
                        .putExtra("hari","")
                        .putExtra("keterangan",""));
            }
        });

    }
}
