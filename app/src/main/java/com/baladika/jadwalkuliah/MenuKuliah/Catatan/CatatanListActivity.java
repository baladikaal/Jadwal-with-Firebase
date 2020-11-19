package com.baladika.jadwalkuliah.MenuKuliah.Catatan;

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

import com.baladika.jadwalkuliah.MenuKuliah.AdapterJadwal.CatatanReqAdapterRecyclerView;
import com.baladika.jadwalkuliah.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CatatanListActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<CatatanReq> cattnReqList;
    private CatatanReqAdapterRecyclerView catatanReqAdapterRecyclerView;
    private String Uid,SmId,MkId;
    private RecyclerView jadwal_list;
    private ProgressDialog loading;
    private FloatingActionButton fab_addCatatan;

    private String sId, sCatatan, sTgl, sKet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan_list);

        database = FirebaseDatabase.getInstance().getReference().child("MenuKuliah");
        Uid = getIntent().getStringExtra("Uid");
        SmId = getIntent().getStringExtra("id");
        MkId = getIntent().getStringExtra("mid");

        sId = getIntent().getStringExtra("idCttn");
        sCatatan = getIntent().getStringExtra("catatan");
        sTgl = getIntent().getStringExtra("tgl");
        sKet = getIntent().getStringExtra("keterangan");

        DatabaseReference offline = database.child("Catatan");
        offline.keepSynced(true);

        jadwal_list = findViewById(R.id.jadwal_catatan_list);
        fab_addCatatan = findViewById(R.id.fab_add_catatan);

        RecyclerView.LayoutManager mLayoutManager  = new LinearLayoutManager(getApplicationContext());
        jadwal_list.setLayoutManager(mLayoutManager);
        jadwal_list.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(CatatanListActivity.this,
                null,
                "Please wait...",
                true,
                false);

        database.child("Catatan").child(Uid).child(SmId).child(MkId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cattnReqList = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    CatatanReq cattn =noteDataSnapshot.getValue(CatatanReq.class);
                    cattn.setKey(noteDataSnapshot.getKey());
                    //mengiri id user dan matkul ke MatkulReq
                    cattn.setUserId(Uid);
                    cattn.setSmstrId(SmId);
                    cattn.setMatkulID(MkId);
                    cattnReqList.add(cattn);
                }

                catatanReqAdapterRecyclerView = new CatatanReqAdapterRecyclerView(cattnReqList, CatatanListActivity.this);
                jadwal_list.setAdapter(catatanReqAdapterRecyclerView);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
                loading.dismiss();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("CATATAN");

        fab_addCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date c = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = df.format(c);


                startActivity(new Intent(CatatanListActivity.this, CatatanUpdateActivity.class)
                        .putExtra("Uid",Uid)
                        .putExtra("id",SmId)
                        .putExtra("mid",MkId)
                        .putExtra("idCttn","")
                        .putExtra("catatan","")
                        .putExtra("tgl",formattedDate)
                        .putExtra("keterangan",""));
            }
        });
    }
}
