package com.baladika.jadwalkuliah.MenuKuliah.Catatan;

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

public class CatatanUpdateActivity extends AppCompatActivity {

    private DatabaseReference database;

    private EditText etCatatan,etTgl,etKet;
    private ProgressDialog loading;
    private Button btnSave,btnCancel;
    private String Uid,SmId,MkId;
    private String sId, sCatatan, sTgl, sKet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan_update);

        database = FirebaseDatabase.getInstance().getReference().child("MenuKuliah");
        Uid = getIntent().getStringExtra("Uid");
        SmId = getIntent().getStringExtra("id");
        MkId = getIntent().getStringExtra("mid");

        sId = getIntent().getStringExtra("idCttn");
        sCatatan = getIntent().getStringExtra("catatan");
        sTgl = getIntent().getStringExtra("tgl");
        sKet = getIntent().getStringExtra("keterangan");

        etCatatan = findViewById(R.id.catatan_judul_update);
        etTgl = findViewById(R.id.catatan_tgl_update);
        etKet = findViewById(R.id.catatan_isi_update);
        btnSave = findViewById(R.id.btn_save_catatan);
        btnCancel = findViewById(R.id.btn_cancel_catatan);

        etCatatan.setText(sCatatan);
        etTgl.setText(sTgl);
        etKet.setText(sKet);

        if (sId.equals("")){
            btnSave.setText("Save");
            btnCancel.setText("Cancel");
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Tambah Catatan");
        }else {
            btnSave.setText("Edit");
            btnCancel.setText("Delete");
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Edit Catatan");
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnCancel.getText().equals("Cancel")){
                    //tutup
                    finish();
                }else {
                    //hapus
                    database.child("Catatan")
                            .child(Uid)
                            .child(SmId)
                            .child(MkId)
                            .child(sId)
                            .removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(CatatanUpdateActivity.this,"Data di hapus!",Toast.LENGTH_SHORT).show();
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
                            String Scatatan = etCatatan.getText().toString();
                            String Stgl = etTgl.getText().toString();
                            String Sket = etKet.getText().toString();


                            //printah tambah
                            if (btnSave.getText().equals("Save")){
                                if (Scatatan.equals("")){
                                    etCatatan.setError("Masukkan nama terlebih dahulu!");
                                    etCatatan.requestFocus();
                                }
                                else {
                                    loading = ProgressDialog.show(CatatanUpdateActivity.this,
                                            "",
                                            "Please wait...",
                                            true,
                                            false);
                                    submitUser(new CatatanReq(Scatatan,Sket,Stgl));
                                }
                            }
                            //perintah edit
                            else {
                                if (Scatatan.equals("")){
                                    etCatatan.setError("Masukkan nama terlebih dahulu!");
                                    etCatatan.requestFocus();
                                }
                                else {
                                    loading = ProgressDialog.show(CatatanUpdateActivity.this,
                                            "",
                                            "Please wait...",
                                            true,
                                            false);
                                    editUser(new CatatanReq(Scatatan,Sket,Stgl),sId);
                                }
                            }

                        } else {
                            Toast.makeText(CatatanUpdateActivity.this, "Koneksi gagal! \n Mohon periksa Internet anda.", Toast.LENGTH_SHORT).show();
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

    private void submitUser(CatatanReq cttnReq) {
        database.child("Catatan")
                .child(Uid)
                .child(SmId)
                .child(MkId)
                .push()
                .setValue(cttnReq)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        etCatatan.setText("");
                        etTgl.setText("");
                        etKet.setText("");

                        Toast.makeText(CatatanUpdateActivity.this, "Data di tambah!", Toast.LENGTH_SHORT).show();
                        finish();
                        return;

                    }
                });
    }

    private void editUser(CatatanReq cttnReq,String id) {
        database.child("Catatan")
                .child(Uid)
                .child(SmId)
                .child(MkId)
                .child(id)
                .setValue(cttnReq)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        etCatatan.setText("");
                        etTgl.setText("");
                        etKet.setText("");

                        Toast.makeText(CatatanUpdateActivity.this, "Data di update!", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                });
    }
}
