package com.baladika.jadwalkuliah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baladika.jadwalkuliah.MenuAkun.AkunActivity;
import com.baladika.jadwalkuliah.MenuKuliah.Semester.SemesterListActivity;
import com.baladika.jadwalkuliah.MenuLab.Hari.HariListActivity;
import com.baladika.jadwalkuliah.MenuUjian.Semester.UjianSemesterListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity{

    private TextView HomeName;
    private DatabaseReference database;
    private String Uid;
    TextView Day,txtJam,Jam;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        loading = ProgressDialog.show(HomeActivity.this,
                "",
                "Please wait...",
                true,
                false);

        database = FirebaseDatabase.getInstance().getReference();
        Uid = getIntent().getStringExtra("Uid");

        HomeName = findViewById(R.id.home_nama);
        Day = findViewById(R.id.hari);
        txtJam = findViewById(R.id.txtJam);
        Jam = findViewById(R.id.JamKe);

        DatabaseReference offline = FirebaseDatabase.getInstance().getReference().child("Users");
        offline.keepSynced(true);


        setUcapan();
        jamKuliah();
        klik();



        database.child("Users").child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loading.dismiss();
                String Snama = dataSnapshot.child("nama").getValue(String.class);
                HomeName.setText(Snama.toUpperCase());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setUcapan(){

        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String s = sdf.format(new Date());
        int jam = Integer.parseInt(s);

        if (jam >= 15 && jam <= 17){
            Day.setText("SORE");
        }else if (jam > 17 && jam < 18){
            Day.setText("PETANG");
        }else if (jam >= 18 ){
            Day.setText("MALAM");
        }else if (jam >= 00 || jam >= 1 || jam >= 01){
            Day.setText("PAGI");
        }else if (jam >= 11){
            Day.setText("SIANG");
        }
        else{
            Day.setText("ERROR");
        }
    }

    private void jamKuliah(){

        //format jam 7.30
        SimpleDateFormat sdf = new SimpleDateFormat("hh.mm");
        String s = sdf.format(new Date());
        Double jam = Double.parseDouble(s);

        if (jam >= 7.30 && jam <= 8.30){
            Jam.setText("1");
        }
        if (jam >= 8.30 && jam <= 9.30){
            Jam.setText("2");
        }
        if (jam >= 9.30 && jam <= 10.30){
            Jam.setText("3");
        }
        if (jam >= 10.30 && jam <= 11.30){
            Jam.setText("4");
        }
        if (jam >= 11.30 && jam <= 12.30){
            Jam.setText("5");
        }
        if (jam >= 12.30 && jam <= 13.30){
            Jam.setText("6");
        }
        if (jam >= 13.30 && jam <= 14.30){
            Jam.setText("7");
        }
        if (jam >= 14.30 && jam <= 15.30){
            Jam.setText("8");
        }
        if (jam >= 15.30 && jam <= 16.30){
            Jam.setText("9");
        }
        if (jam >= 16.30  && jam <= 17.30){
            Jam.setText("10");
        }
        else{
            Jam.setText("");
            txtJam.setText("Tidak ada Kuliah");
        }
    }


    void klik(){

        findViewById(R.id.jadwal_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SemesterListActivity.class);
                intent.putExtra("Uid", Uid);
                startActivity(intent);
            }
        });

        findViewById(R.id.ujian_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UjianSemesterListActivity.class);
                intent.putExtra("Uid", Uid);
                startActivity(intent);
            }
        });

        findViewById(R.id.lab_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HariListActivity.class);
                intent.putExtra("Uid", Uid);
                startActivity(intent);
            }
        });

        findViewById(R.id.akun_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AkunActivity.class);
                intent.putExtra("Uid", Uid);
                startActivity(intent);
            }
        });

    }
}
