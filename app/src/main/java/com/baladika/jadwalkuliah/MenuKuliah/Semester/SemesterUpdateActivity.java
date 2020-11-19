package com.baladika.jadwalkuliah.MenuKuliah.Semester;

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

public class SemesterUpdateActivity extends AppCompatActivity {

    private DatabaseReference database;

    private EditText etSmstr,etTahun;
    private ProgressDialog loading;
    private Button btnSave,btnCancel;
    private String Uid;
    private String sId, sSmstr, sTahun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_update);

        database = FirebaseDatabase.getInstance().getReference().child("MenuKuliah");
        Uid = getIntent().getStringExtra("Uid");

        sId = getIntent().getStringExtra("id");
        sSmstr = getIntent().getStringExtra("semester");
        sTahun = getIntent().getStringExtra("tahun");

        etSmstr = findViewById(R.id.sms_judul_update);
        etTahun = findViewById(R.id.sms_tahun_update);
        btnSave = findViewById(R.id.btn_save_sms);
        btnCancel = findViewById(R.id.btn_cancel_sms);

        etSmstr.setText(sSmstr);
        etTahun.setText(sTahun);

        if (sId.equals("")){
            btnSave.setText("Save");
            btnCancel.setText("Cancel");
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Tambah Semester");
        }else {
            btnSave.setText("Edit");
            btnCancel.setText("Delete");
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Edit Semester");
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnCancel.getText().equals("Cancel")){
                    //tutup
                    finish();
                }else {
                    database.child("Semester")
                            .child(Uid)
                            .child(sId)
                            .removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SemesterUpdateActivity.this,"Data di hapus!",Toast.LENGTH_SHORT).show();
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

                            String Ssmster = etSmstr.getText().toString();
                            String Stahun = etTahun.getText().toString();

                            //printah tambah
                            if (btnSave.getText().equals("Save")){
                                if (Ssmster.equals("")){
                                    etSmstr.setError("Masukkan semester terlebih dahulu!");
                                    etSmstr.requestFocus();
                                }
                                else if (Stahun.equals("")){
                                    etTahun.setError("Masukkan email terlebih dahulu!");
                                    etTahun.requestFocus();
                                }
                                else {
                                    loading = ProgressDialog.show(SemesterUpdateActivity.this,
                                            "",
                                            "Please wait...",
                                            true,
                                            false);
                                    submitSmester(new SemesterReq(Ssmster,Stahun));
                                }
                            }
                            //perintah edit
                            else {
                                if (Ssmster.equals("")){
                                    etSmstr.setError("Masukkan semester terlebih dahulu!");
                                    etSmstr.requestFocus();
                                }
                                else if (Stahun.equals("")){
                                    etTahun.setError("Masukkan email terlebih dahulu!");
                                    etTahun.requestFocus();
                                }
                                else {
                                    loading = ProgressDialog.show(SemesterUpdateActivity.this,
                                            "",
                                            "Please wait...",
                                            true,
                                            false);
                                    editSmester(new SemesterReq(
                                            Ssmster,Stahun),sId);
                                }
                            }


                        } else {
                            Toast.makeText(SemesterUpdateActivity.this, "Koneksi gagal! \n Mohon periksa Internet anda.", Toast.LENGTH_SHORT).show();
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

    private void submitSmester(SemesterReq semesterReq) {
        database.child("Semester")
                .child(Uid)
                .push()
                .setValue(semesterReq)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        etSmstr.setText("");
                        etTahun.setText("");

                        Toast.makeText(SemesterUpdateActivity.this, "Data di tambah!", Toast.LENGTH_SHORT).show();
                        finish();
                        return;

                    }
                });
    }

    private void editSmester(SemesterReq semesterReq,String id) {
        database.child("Semester")
                .child(Uid)
                .child(id)
                .setValue(semesterReq)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        etSmstr.setText("");
                        etTahun.setText("");

                        Toast.makeText(SemesterUpdateActivity.this, "Data di update!", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                });
    }
}
