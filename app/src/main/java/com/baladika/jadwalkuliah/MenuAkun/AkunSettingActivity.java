package com.baladika.jadwalkuliah.MenuAkun;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baladika.jadwalkuliah.Model.Request;
import com.baladika.jadwalkuliah.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class AkunSettingActivity extends AppCompatActivity {

    private DatabaseReference database;
    private ProgressDialog loading;

    private Button btnUpload,btnCancel,btnSave;
    private String Uid;
    private ImageView iFoto;
    private EditText iNama,iNpm,iKelas;
    private TextView tEmail;
    StorageReference mStorageRef;
    private StorageTask uploadTask;
    public Uri imguri;
    private StorageReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_setting);

        Uid = getIntent().getStringExtra("Uid");

        database = FirebaseDatabase.getInstance().getReference().child("Users");
        DatabaseReference offline = database;
        offline.keepSynced(true);


        loading = ProgressDialog.show(AkunSettingActivity.this,
                "",
                "Please wait...",
                true,
                false);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mStorageRef = FirebaseStorage.getInstance().getReference("Images").child(Uid).child("userFoto");

        btnUpload = findViewById(R.id.btn_upload_akun);
        btnCancel = findViewById(R.id.btn_cancel_akun);
        btnSave = findViewById(R.id.btn_save_akun);
        iFoto = findViewById(R.id.setting_foto);
        tEmail = findViewById(R.id.setting_email);
        iNama = findViewById(R.id.setting_nama);
        iNpm = findViewById(R.id.setting_npm);
        iKelas = findViewById(R.id.setting_kelas);


        database.child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loading.dismiss();
                String Gnama = dataSnapshot.child("nama").getValue(String.class);
                String Gnpm = dataSnapshot.child("npm").getValue(String.class);
                String Gkelas = dataSnapshot.child("kelas").getValue(String.class);
                String Gemail = dataSnapshot.child("email").getValue(String.class);
                final String Gfoto = dataSnapshot.child("foto").getValue(String.class);
                if (Gfoto.isEmpty()){
                    iFoto.setImageResource(R.drawable.ic_launcher_foreground);
                    iNama.setText(Gnama);
                    iNpm.setText(Gnpm);
                    iKelas.setText(Gkelas);
                }else {
                    Picasso.get().load(Gfoto).into(iFoto);
                    Picasso.get().load(Gfoto).networkPolicy(NetworkPolicy.OFFLINE).into(iFoto, new Callback() {
                        @Override
                        public void onSuccess() {}
                        @Override
                        public void onError(Exception e) {Picasso.get().load(Gfoto).into(iFoto); }
                    });
                    iNama.setText(Gnama);
                    iNpm.setText(Gnpm);
                    iKelas.setText(Gkelas);
                }
                iNama.setText(Gnama);
                iNpm.setText(Gnpm);
                iKelas.setText(Gkelas);
                tEmail.setText(Gemail);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Inama = iNama.getText().toString();
                final String Inpm = iNpm.getText().toString();
                final String Ikelas = iKelas.getText().toString();
                final String Iemail = tEmail.getText().toString();


                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                connectedRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        boolean connected = snapshot.getValue(Boolean.class);
                        if (connected){
                            loading = ProgressDialog.show(AkunSettingActivity.this,
                                    "",
                                    "Please wait...",
                                    true,
                                    false);
                            if (uploadTask!=null && uploadTask.isInProgress()){
                                Toast.makeText(AkunSettingActivity.this,"Upload foto...", Toast.LENGTH_LONG).show();
                            }

                            else {

                                ref = mStorageRef.child("PP_"+Uid+"."+getExtensi(imguri));

                                uploadTask=ref.putFile(imguri)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        final String Ifoto =  String.valueOf(uri);
                                                        editAkun(new Request(
                                                                Inama,Inpm,Ikelas,Iemail,Ifoto));
                                                    }
                                                });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                // Handle unsuccessful uploads
                                                // ...
                                            }
                                        });
                            }
                        }else {
                            Toast.makeText(AkunSettingActivity.this, "Koneksi gagal! \n Mohon periksa Internet anda.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        System.err.println("Listener was cancelled");
                    }
                });
            }
        });


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser();
            }
        });

    }

    private String getExtensi(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }


    private void FileChooser() {
        Intent intent = new Intent();
        intent.setType("image/'");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imguri=data.getData();
            iFoto.setImageURI(imguri);
        }

    }

    private void editAkun(Request request){
        database.child(Uid)
                .setValue(request)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();
                        Toast.makeText(AkunSettingActivity.this, "Data di update!", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                });

    }
}
