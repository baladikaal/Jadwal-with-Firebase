package com.baladika.jadwalkuliah.MenuKuliah.AdapterJadwal;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baladika.jadwalkuliah.MenuKuliah.CekTugas.CekTugasReq;
import com.baladika.jadwalkuliah.MenuKuliah.CekTugas.CekTugasUpdateActivity;
import com.baladika.jadwalkuliah.R;

import java.util.List;

public class CekTugasReqAdapterRecyclerView extends RecyclerView.Adapter<CekTugasReqAdapterRecyclerView.MyViewHolder> {

    private List<CekTugasReq> tugasList;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rl_layout;
        public TextView tv_judul,tv_hari,tv_ket;

        public MyViewHolder(View view){
            super(view);
            rl_layout = view.findViewById(R.id.tugas_layout);
            tv_judul = view.findViewById(R.id.tugas_judul);
            tv_hari = view.findViewById(R.id.tugas_hari);
            tv_ket = view.findViewById(R.id.tugas_ket);

        }
    }

    public CekTugasReqAdapterRecyclerView(List<CekTugasReq> moviesList, Activity activity){
        this.mActivity = activity;
        this.tugasList = moviesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cektugas,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final CekTugasReq tugas = tugasList.get(position);
        holder.tv_judul.setText(tugas.getTugas());
        holder.tv_hari.setText(tugas.getHari());
        holder.tv_ket.setText(tugas.getKeterangan());


        holder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Tahan tugas untuk mengedit ", Toast.LENGTH_SHORT).show();
            }
        });


        holder.rl_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent getDetail = new Intent(mActivity, CekTugasUpdateActivity.class);
//
                getDetail.putExtra("Uid",tugas.getUserId());
                getDetail.putExtra("id",tugas.getSmstrId());
                getDetail.putExtra("mid",tugas.getMatkulID());
                getDetail.putExtra("idTgs",tugas.getKey());
                getDetail.putExtra("judul",tugas.getTugas());
                getDetail.putExtra("hari",tugas.getHari());
                getDetail.putExtra("keterangan",tugas.getKeterangan());
                mActivity.startActivity(getDetail);


                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return tugasList.size();
    }
}
