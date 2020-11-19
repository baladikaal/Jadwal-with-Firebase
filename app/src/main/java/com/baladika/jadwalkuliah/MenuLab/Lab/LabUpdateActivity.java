package com.baladika.jadwalkuliah.MenuLab.Lab;

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

public class LabUpdateActivity extends AppCompatActivity {

    private DatabaseReference database;

    private EditText etLab,etRuangan,etHari,etJam,etNote;
    private ProgressDialog loading;
    private Button btnSave,btnCancel;
    private String Uid,Date;
    private String sId,sLab,sRuangan,sHari,sJam,sNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_update);

        database = FirebaseDatabase.getInstance().getReference().child("MenuLab");
        Uid = getIntent().getStringExtra("Uid");
        Date = getIntent().getStringExtra("date");


        sId = getIntent().getStringExtra("id");
        sLab = getIntent().getStringExtra("lab");
        sRuangan = getIntent().getStringExtra("ruangan");
        sHari = getIntent().getStringExtra("hari");
        sJam = getIntent().getStringExtra("jam");
        sNote = getIntent().getStringExtra("note");

        etLab = findViewById(R.id.lab_judul_update);
        etRuangan = findViewById(R.id.lab__ruangan_update);
        etHari = findViewById(R.id.lab__hari_update);
        etJam = findViewById(R.id.lab__jam_update);
        etNote = findViewById(R.id.lab_note_update);
        btnSave = findViewById(R.id.btn_save_lab);
        btnCancel = findViewById(R.id.btn_cancel_lab);

        etLab.setText(sLab);
        etRuangan.setText(sRuangan);
        etHari.setText(sHari);
        etJam.setText(sJam);
        etNote.setText(sNote);

        if (sId.equals("")){
            btnSave.setText("Save");
            btnCancel.setText("Cancel");
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Tambah Jadwal Laboratorium");

        }else {
            btnSave.setText("Edit");
            btnCancel.setText("Delete");
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Edit Jadwal Laboratorium");
        }


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnCancel.getText().equals("Cancel")){
                    //tutup
                    finish();
                }else {
                    //hapus
                    database.child(Uid)
                            .child("Hari")
                            .child(Date)
                            .child(sId)
                            .removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(LabUpdateActivity.this,"Data matkul di hapus!",Toast.LENGTH_SHORT).show();
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
                            String Slab = etLab.getText().toString();
                            String Sruangan = etRuangan.getText().toString();
                            String Sjam = etJam.getText().toString();
                            String Shari = etHari.getText().toString();
                            String Snote = etNote.getText().toString();

                            //printah tambah
                            if (btnSave.getText().equals("Save")){
                                if (Slab.equals("")){
                                    etLab.setError("Masukkan nama terlebih dahulu!");
                                    etLab.requestFocus();
                                }
                                else {
                                    loading = ProgressDialog.show(LabUpdateActivity.this,
                                            "",
                                            "Please wait...",
                                            true,
                                            false);
                                    submitUser(new LabReq(Slab,Sruangan,Sjam,Shari,Snote));
                                }
                            }
                            //perintah edit
                            else {
                                if (Slab.equals("")){
                                    etLab.setError("Masukkan nama terlebih dahulu!");
                                    etLab.requestFocus();
                                }
                                else {
                                    loading = ProgressDialog.show(LabUpdateActivity.this,
                                            "",
                                            "Please wait...",
                                            true,
                                            false);
                                    editUser(new LabReq(Slab,Sruangan,Sjam,Shari,Snote),sId);
                                }
                            }


                        } else {
                            Toast.makeText(LabUpdateActivity.this, "Koneksi gagal! \n Mohon periksa Internet anda.", Toast.LENGTH_SHORT).show();
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

    private void submitUser(LabReq labReq) {
        database.child(Uid)
                .child("Hari")
                .child(Date)
                .push()
                .setValue(labReq)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        etLab.setText("");
                        etRuangan.setText("");
                        etJam.setText("");
                        etHari.setText("");
                        etNote.setText("");

                        Toast.makeText(LabUpdateActivity.this, "Data di tambah!", Toast.LENGTH_SHORT).show();
                        finish();
                        return;

                    }
                });
    }

    private void editUser(LabReq labReq,String id) {
        database.child(Uid)
                .child("Hari")
                .child(Date)
                .child(id)
                .setValue(labReq)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        etLab.setText("");
                        etRuangan.setText("");
                        etJam.setText("");
                        etHari.setText("");
                        etNote.setText("");

                        Toast.makeText(LabUpdateActivity.this, "Matkul di update!", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                });
    }
}
