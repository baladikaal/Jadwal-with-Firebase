package com.baladika.jadwalkuliah.MenuUjian.Matkul;

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

import com.baladika.jadwalkuliah.MenuUjian.AdapterUjian.UjianMatkulReqAdapterRecyclerView;
import com.baladika.jadwalkuliah.MenuUjian.Semester.UjianSemesterUpdateActivity;
import com.baladika.jadwalkuliah.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UjianMatkulListActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<UjianMatkulReq> UmatkulReqList;
    private UjianMatkulReqAdapterRecyclerView ujianMatkulReqAdapterRecyclerView;
    private String Uid,SmId;
    private RecyclerView jadwal_list;
    private ProgressDialog loading;

    private String sId, sSmstr, sTahun,sHari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujian_matkul_list);

        database = FirebaseDatabase.getInstance().getReference().child("MenuUjian");
        Uid = getIntent().getStringExtra("Uid");
        SmId = getIntent().getStringExtra("id");

        sId = getIntent().getStringExtra("mid");
        sSmstr = getIntent().getStringExtra("semester");
        sTahun = getIntent().getStringExtra("tahun");

        DatabaseReference offline = database.child("Matkul");
        offline.keepSynced(true);

        jadwal_list = findViewById(R.id.jadwal_ujian_matkul_list);

        RecyclerView.LayoutManager mLayoutManager  = new LinearLayoutManager(getApplicationContext());
        jadwal_list.setLayoutManager(mLayoutManager);
        jadwal_list.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(UjianMatkulListActivity.this,
                null,
                "Please wait...",
                true,
                false);

        database.child("Matkul").child(Uid).child(SmId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UmatkulReqList = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                    UjianMatkulReq matkul =noteDataSnapshot.getValue(UjianMatkulReq.class);
                    matkul.setKey(noteDataSnapshot.getKey());
                    //mengiri id user dan matkul ke MatkulReq
                    matkul.setUserId(Uid);
                    matkul.setSmstrId(SmId);
                    UmatkulReqList.add(matkul);
                }

                ujianMatkulReqAdapterRecyclerView = new UjianMatkulReqAdapterRecyclerView(UmatkulReqList, UjianMatkulListActivity.this);
                jadwal_list.setAdapter(ujianMatkulReqAdapterRecyclerView);
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

        findViewById(R.id.fab_add_ujian_matkul).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UjianMatkulListActivity.this, UjianMatkulUpdateActivity.class)
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
        inflater.inflate(R.menu.menu_ujian_semester,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.edit_ujian_semester:
                startActivity(new Intent(UjianMatkulListActivity.this, UjianSemesterUpdateActivity.class)
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
