package com.baladika.jadwalkuliah.MenuKuliah.Matkul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baladika.jadwalkuliah.MenuKuliah.Catatan.CatatanListActivity;
import com.baladika.jadwalkuliah.MenuKuliah.CekTugas.CekTugasListActivity;
import com.baladika.jadwalkuliah.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MatkulActivity extends AppCompatActivity {

    private DatabaseReference database;
    private ProgressDialog loading;
    private TextView iNama,iNpm,iHari,iRuangan,iJam,iTahun;
    private String Uid, SmsId;
    private String sId, sMatkul, sRuangan, sJam, sHari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matkul);

        database = FirebaseDatabase.getInstance().getReference().child("MenuKuliah");

        loading = ProgressDialog.show(MatkulActivity.this,
                "",
                "Please wait...",
                true,
                false);

        Uid = getIntent().getStringExtra("Uid");
        SmsId = getIntent().getStringExtra("id");

        sId = getIntent().getStringExtra("mid");
        sMatkul = getIntent().getStringExtra("matkul");
        sRuangan = getIntent().getStringExtra("ruangan");
        sJam = getIntent().getStringExtra("jam");
        sHari = getIntent().getStringExtra("hari");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(sMatkul);

        DatabaseReference offline1 = database.child("Matkul");
        DatabaseReference offline2 = database.child("Users");
        DatabaseReference offline3 = database.child("Semester");
        offline1.keepSynced(true);
        offline2.keepSynced(true);
        offline3.keepSynced(true);

        iNama = findViewById(R.id.act_matkul_nama);
        iNpm = findViewById(R.id.act_matkul_npm);
        iHari = findViewById(R.id.act_matkul_hari);
        iRuangan = findViewById(R.id.act_matkul_ruangan);
        iJam = findViewById(R.id.act_matkul_jam);
        iTahun = findViewById(R.id.act_matkul_tahun);

        iRuangan.setText(sRuangan);
        iJam.setText(sJam);
        iHari.setText(sHari);

        FirebaseDatabase.getInstance().getReference().child("Users").child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loading.dismiss();
                String Gnama = dataSnapshot.child("nama").getValue(String.class);
                String Gnpm = dataSnapshot.child("npm").getValue(String.class);
                iNama.setText(Gnama);
                iNpm.setText(Gnpm);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.child("Semester").child(Uid).child(SmsId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loading.dismiss();
                String Gtahun = dataSnapshot.child("tahun").getValue(String.class);
                iTahun.setText(Gtahun);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        klik();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.matkul_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.edit_matkul:
                startActivity(new Intent(MatkulActivity.this, MatkulUpdateActivity.class)
                        .putExtra("Uid",Uid)
                        .putExtra("id",SmsId)
                        .putExtra("mid",sId)
                        .putExtra("matkul",sMatkul)
                        .putExtra("ruangan",sRuangan)
                        .putExtra("jam",sJam)
                        .putExtra("hari",sHari));
                finish();

                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    void klik(){
        findViewById(R.id.tugas_matkul).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatkulActivity.this, CekTugasListActivity.class);
                intent.putExtra("Uid", Uid);
                intent.putExtra("id", SmsId);
                intent.putExtra("mid", sId);
                startActivity(intent);
            }
        });

        findViewById(R.id.catatan_matkul).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatkulActivity.this, CatatanListActivity.class);
                intent.putExtra("Uid", Uid);
                intent.putExtra("id", SmsId);
                intent.putExtra("mid", sId);
                startActivity(intent);
            }
        });

    }
}
