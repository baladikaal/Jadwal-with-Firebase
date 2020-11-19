package com.baladika.jadwalkuliah.MenuKuliah.CekTugas;

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

public class CekTugasUpdateActivity extends AppCompatActivity {

    private DatabaseReference database;

    private EditText etJudul,etHari,etKet;
    private ProgressDialog loading;
    private Button btnSave,btnCancel;
    private String Uid,SmId,MkId;
    private String sId, sJudul, sHari, sKet;
    String Sjudul,Shari,Sket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_tugas_update);

        database = FirebaseDatabase.getInstance().getReference().child("MenuKuliah");
        Uid = getIntent().getStringExtra("Uid");
        SmId = getIntent().getStringExtra("id");
        MkId = getIntent().getStringExtra("mid");

        sId = getIntent().getStringExtra("idTgs");
        sJudul = getIntent().getStringExtra("judul");
        sHari = getIntent().getStringExtra("hari");
        sKet = getIntent().getStringExtra("keterangan");

        etJudul = findViewById(R.id.tugas_judul_update);
        etHari = findViewById(R.id.tugas_deadline_update);
        etKet = findViewById(R.id.tugas_ket_update);
        btnSave = findViewById(R.id.btn_save_tugas);
        btnCancel = findViewById(R.id.btn_cancel_tugas);

        etJudul.setText(sJudul);
        etHari.setText(sHari);
        etKet.setText(sKet);

        if (sId.equals("")){
            btnSave.setText("Save");
            btnCancel.setText("Cancel");
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Tambah Tugas");
        }else {
            btnSave.setText("Edit");
            btnCancel.setText("Delete");
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Edit Tugas");
        }


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnCancel.getText().equals("Cancel")){
                    //tutup
                    finish();
                }else {
                    //hapus
                    database.child("Tugas")
                            .child(Uid)
                            .child(SmId)
                            .child(MkId)
                            .child(sId)
                            .removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(CekTugasUpdateActivity.this,"Data di hapus!",Toast.LENGTH_SHORT).show();
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

                 Sjudul = etJudul.getText().toString();
                 Shari = etHari.getText().toString();
                 Sket = etKet.getText().toString();

                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                connectedRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        boolean connected = snapshot.getValue(Boolean.class);
                        if (connected) {
                            Toast.makeText(CekTugasUpdateActivity.this, "Konek ", Toast.LENGTH_SHORT).show();

                            //printah tambah
                            if (btnSave.getText().equals("Save")){
                                if (Sjudul.equals("")){
                                    etJudul.setError("Masukkan nama terlebih dahulu!");
                                    etJudul.requestFocus();
                                }
                                else {
                                    loading = ProgressDialog.show(CekTugasUpdateActivity.this,
                                            "",
                                            "Please wait...",
                                            true,
                                            false);
                                    submitUser(new CekTugasReq(Sjudul,Shari,Sket));
                                }
                            }
                            //perintah edit
                            else {
                                if (Sjudul.equals("")){
                                    etJudul.setError("Masukkan nama terlebih dahulu!");
                                    etJudul.requestFocus();
                                }
                                else {
                                    loading = ProgressDialog.show(CekTugasUpdateActivity.this,
                                            "",
                                            "Please wait...",
                                            true,
                                            false);
                                    editUser(new CekTugasReq(Sjudul,Shari,Sket),sId);
                                }
                            }

                        } else {
                            Toast.makeText(CekTugasUpdateActivity.this, "Koneksi gagal! \n Mohon periksa Internet anda.", Toast.LENGTH_SHORT).show();
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

    private void submitUser(CekTugasReq matkulReq) {
        database.child("Tugas")
                .child(Uid)
                .child(SmId)
                .child(MkId)
                .push()
                .setValue(matkulReq)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        etJudul.setText("");
                        etHari.setText("");
                        etKet.setText("");

                        Toast.makeText(CekTugasUpdateActivity.this, "Data di tambah!", Toast.LENGTH_SHORT).show();
                        finish();
                        return;

                    }
                });
    }

    private void editUser(CekTugasReq matkulReq,String id) {
        database.child("Tugas")
                .child(Uid)
                .child(SmId)
                .child(MkId)
                .child(id)
                .setValue(matkulReq)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        etJudul.setText("");
                        etHari.setText("");
                        etKet.setText("");

                        Toast.makeText(CekTugasUpdateActivity.this, "Data di update!", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                });
    }

}
