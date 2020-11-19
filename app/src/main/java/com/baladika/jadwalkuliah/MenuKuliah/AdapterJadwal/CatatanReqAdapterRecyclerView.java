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

import com.baladika.jadwalkuliah.MenuKuliah.Catatan.CatatanReq;
import com.baladika.jadwalkuliah.MenuKuliah.Catatan.CatatanUpdateActivity;
import com.baladika.jadwalkuliah.R;

import java.util.List;

public class CatatanReqAdapterRecyclerView extends RecyclerView.Adapter<CatatanReqAdapterRecyclerView.MyViewHolder> {

    private List<CatatanReq> catatanList;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rl_layout;
        public TextView tv_judul,tv_ket,tv_tgl;

        public MyViewHolder(View view){
            super(view);
            rl_layout = view.findViewById(R.id.catatan_layout);
            tv_judul = view.findViewById(R.id.catatan_judul);
            tv_tgl = view.findViewById(R.id.catatan_tgl);
            tv_ket = view.findViewById(R.id.catatan_isi);

        }
    }

    public CatatanReqAdapterRecyclerView(List<CatatanReq> moviesList, Activity activity){
        this.mActivity = activity;
        this.catatanList = moviesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_catatan,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final CatatanReq cttn = catatanList.get(position);
        holder.tv_judul.setText(cttn.getCatatan());
        holder.tv_tgl.setText(cttn.getTgl());
        holder.tv_ket.setText(cttn.getKeterangan());


        holder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Tahan catatan untuk mengedit ", Toast.LENGTH_SHORT).show();
            }
        });


        holder.rl_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent getDetail = new Intent(mActivity, CatatanUpdateActivity.class);
//
                getDetail.putExtra("Uid",cttn.getUserId());
                getDetail.putExtra("id",cttn.getSmstrId());
                getDetail.putExtra("mid",cttn.getMatkulID());
                getDetail.putExtra("idCttn",cttn.getKey());
                getDetail.putExtra("catatan",cttn.getCatatan());
                getDetail.putExtra("tgl",cttn.getTgl());
                getDetail.putExtra("keterangan",cttn.getKeterangan());
                mActivity.startActivity(getDetail);


                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return catatanList.size();
    }
}
