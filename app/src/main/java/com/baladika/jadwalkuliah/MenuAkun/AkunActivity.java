package com.baladika.jadwalkuliah.MenuAkun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baladika.jadwalkuliah.MainActivity;
import com.baladika.jadwalkuliah.Menu_masuk.LoginActivity;
import com.baladika.jadwalkuliah.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class AkunActivity extends AppCompatActivity {

    private DatabaseReference database;
    private ProgressDialog loading;
    private TextView iNama,iNpm,iKelas;
    private ImageView FotoAkun;
    private String Uid;
    private Button btnSetting, btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        iNama = findViewById(R.id.akun_nama);
        iNpm = findViewById(R.id.akun_npm);
        iKelas = findViewById(R.id.akun_kelas);
        btnSetting = findViewById(R.id.akun_setting);
        btnLogout = findViewById(R.id.akun_logout);
        FotoAkun = findViewById(R.id.akun_foto);

        database = FirebaseDatabase.getInstance().getReference();

        loading = ProgressDialog.show(AkunActivity.this,
                "",
                "Please wait...",
                true,
                false);

        Uid = getIntent().getStringExtra("Uid");

        DatabaseReference offline = database.child("Users");
        offline.keepSynced(true);

        database.child("Users").child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loading.dismiss();
                String Gnama = dataSnapshot.child("nama").getValue(String.class);
                String Gnpm = dataSnapshot.child("npm").getValue(String.class);
                String Gkelas = dataSnapshot.child("kelas").getValue(String.class);
                final String Gfoto = dataSnapshot.child("foto").getValue(String.class);

                if (Gfoto.isEmpty()){
                    FotoAkun.setImageResource(R.drawable.ic_launcher_foreground);
                    iNama.setText(Gnama);
                    iNpm.setText(Gnpm);
                    iKelas.setText(Gkelas);
                }else {
                    Picasso.get().load(Gfoto).into(FotoAkun);
                    Picasso.get().load(Gfoto).networkPolicy(NetworkPolicy.OFFLINE).into(FotoAkun, new Callback() {
                        @Override
                        public void onSuccess() {}
                        @Override
                        public void onError(Exception e) {Picasso.get().load(Gfoto).into(FotoAkun); }
                    });
                    iNama.setText(Gnama);
                    iNpm.setText(Gnpm);
                    iKelas.setText(Gkelas);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AkunActivity.this, AkunSettingActivity.class);
                intent.putExtra("Uid", Uid);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AkunActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}
