package com.baladika.jadwalkuliah.MenuKuliah.Matkul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.baladika.jadwalkuliah.MenuKuliah.AdapterJadwal.MatkulReqAdapterRecyclerView;
import com.baladika.jadwalkuliah.R;
import com.baladika.jadwalkuliah.MenuKuliah.Semester.SemesterUpdateActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MatkulListActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<MatkulReq> matkulReqList;
    private MatkulReqAdapterRecyclerView matkulReqAdapterRecyclerView;
    private String Uid,SmId;
    private RecyclerView jadwal_list;
    private ProgressDialog loading;

    private String sId, sSmstr, sTahun,sHari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matkul_list);

        database = FirebaseDatabase.getInstance().getReference().child("MenuKuliah");

        Uid = getIntent().getStringExtra("Uid");
        SmId = getIntent().getStringExtra("id");

        sId = getIntent().getStringExtra("mid");
        sSmstr = getIntent().getStringExtra("semester");
        sTahun = getIntent().getStringExtra("tahun");

        jadwal_list = findViewById(R.id.jadwal_matkul_list);

        DatabaseReference offline = database.child("Matkul");
        offline.keepSynced(true);

        RecyclerView.LayoutManager mLayoutManager  = new LinearLayoutManager(getApplicationContext());
        jadwal_list.setLayoutManager(mLayoutManager);
        jadwal_list.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(MatkulListActivity.this,
                null,
                "Please wait...",
                true,
                false);

        database.child("Matkul").child(Uid).child(SmId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                matkulReqList = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    MatkulReq matkul =noteDataSnapshot.getValue(MatkulReq.class);
                    matkul.setKey(noteDataSnapshot.getKey());
                    //mengiri id user dan matkul ke MatkulReq
                    matkul.setUserId(Uid);
                    matkul.setSmstrId(SmId);
                    matkulReqList.add(matkul);
                }

                matkulReqAdapterRecyclerView = new MatkulReqAdapterRecyclerView(matkulReqList, MatkulListActivity.this);
                jadwal_list.setAdapter(matkulReqAdapterRecyclerView);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
                loading.dismiss();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(sSmstr);

        findViewById(R.id.fab_add_matkul).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatkulListActivity.this, MatkulUpdateActivity.class)
                        .putExtra("Uid",Uid)
                        .putExtra("id",SmId)
                        .putExtra("mid","")
                        .putExtra("matkul","")
                        .putExtra("ruangan","")
                        .putExtra("jam","")
                        .putExtra("hari",""));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.edit_semester:
                startActivity(new Intent(MatkulListActivity.this, SemesterUpdateActivity.class)
                        .putExtra("Uid",Uid)
                        .putExtra("id",SmId)
                        .putExtra("semester",sSmstr)
                        .putExtra("tahun",sTahun));
                finish();

                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
