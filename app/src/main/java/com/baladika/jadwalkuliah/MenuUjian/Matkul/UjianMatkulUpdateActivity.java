package com.baladika.jadwalkuliah.MenuUjian.Matkul;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baladika.jadwalkuliah.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UjianMatkulUpdateActivity extends AppCompatActivity {

    private DatabaseReference database;

    private EditText etMatkul,etRuangan,etJam,etHari;
    private ProgressDialog loading;
    private Button btnSave,btnCancel;
    private String Uid,SmId;
    private String sId, sMatkul, sRuangan, sJam,sHari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujian_matkul_update);

        database = FirebaseDatabase.getInstance().getReference().child("MenuUjian");

        Uid = getIntent().getStringExtra("Uid");
        SmId = getIntent().getStringExtra("id");

        sId = getIntent().getStringExtra("mid");
        sMatkul = getIntent().getStringExtra("matkul");
        sRuangan = getIntent().getStringExtra("ruangan");
        sJam = getIntent().getStringExtra("jam");
        sHari = getIntent().getStringExtra("hari");

        etMatkul = findViewById(R.id.ujian_matkul_judul_update);
        etRuangan = findViewById(R.id.ujian_matkul_ruangan_update);
        etJam = findViewById(R.id.ujian_matkul_jam_update);
        etHari = findViewById(R.id.ujian_matkul_hari_update);
        btnSave = findViewById(R.id.btn_save_matkul_ujian);
        btnCancel = findViewById(R.id.btn_cancel_matkul_ujian);

        etMatkul.setText(sMatkul);
        etJam.setText(sJam);
        etRuangan.setText(sRuangan);
        etHari.setText(sHari);

        if (sId.equals("")){
            btnSave.setText("Save");
            btnCancel.setText("Cancel");
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Tambah Mata Kuliah Ujian");

        }else {
            btnSave.setText("Edit");
            btnCancel.setText("Delete");
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Edit Mata Kuliah Ujian");
        }


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnCancel.getText().equals("Cancel")){
                    //tutup
                    finish();
                }else {
                    //hapus
                    database.child("Matkul")
                            .child(Uid)
                            .child(SmId)
                            .child(sId)
                            .removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(UjianMatkulUpdateActivity.this,"Data matkul di hapus!",Toast.LENGTH_SHORT).show();
                                    finish();
                                    return;
                                }
                            });

                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                connectedRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        boolean connected = snapshot.getValue(Boolean.class);
                        if (connected) {
                            String Smatkul = etMatkul.getText().toString();
                            String Sruangan = etRuangan.getText().toString();
                            String Sjam = etJam.getText().toString();
                            String Shari = etHari.getText().toString();

                            //printah tambah
                            if (btnSave.getText().equals("Save")){
                                if (Smatkul.equals("")){
                                    etMatkul.setError("Masukkan nama terlebih dahulu!");
                                    etMatkul.requestFocus();
                                }
                                else {
                                    loading = ProgressDialog.show(UjianMatkulUpdateActivity.this,
                                            "",
                                            "Please wait...",
                                            true,
                                            false);
                                    submitUser(new UjianMatkulReq(Smatkul,Sruangan,Sjam,Shari));
                                }
                            }
                            //perintah edit
                            else {
                                if (Smatkul.equals("")){
                                    etMatkul.setError("Masukkan nama terlebih dahulu!");
                                    etMatkul.requestFocus();
                                }
                                else {
                                    loading = ProgressDialog.show(UjianMatkulUpdateActivity.this,
                                            "",
                                            "Please wait...",
                                            true,
                                            false);
                                    editUser(new UjianMatkulReq(Smatkul,Sruangan,Sjam,Shari),sId);
                                }
                            }


                        } else {
                            Toast.makeText(UjianMatkulUpdateActivity.this, "Koneksi gagal! \n Mohon periksa Internet anda.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        System.err.println("Listener was cancelled");
                    }
                });
            }
        });



    }

    private void submitUser(UjianMatkulReq matkulReq) {
        database.child("Matkul")
                .child(Uid)
                .child(SmId)
                .push()
                .setValue(matkulReq)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        etMatkul.setText("");
                        etRuangan.setText("");
                        etJam.setText("");
                        etHari.setText("");

                        Toast.makeText(UjianMatkulUpdateActivity.this, "Data di tambah!", Toast.LENGTH_SHORT).show();
                        finish();
                        return;

                    }
                });
    }

    private void editUser(UjianMatkulReq matkulReq,String id) {
        database.child("Matkul")
                .child(Uid)
                .child(SmId)
                .child(id)
                .setValue(matkulReq)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        etMatkul.setText("");
                        etRuangan.setText("");
                        etJam.setText("");
                        etHari.setText("");

                        Toast.makeText(UjianMatkulUpdateActivity.this, "Matkul di update!", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                });
    }
}
