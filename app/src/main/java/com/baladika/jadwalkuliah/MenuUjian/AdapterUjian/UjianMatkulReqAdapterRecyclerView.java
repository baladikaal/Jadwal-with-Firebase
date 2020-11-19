package com.baladika.jadwalkuliah.MenuUjian.AdapterUjian;

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

import com.baladika.jadwalkuliah.MenuKuliah.Matkul.MatkulUpdateActivity;
import com.baladika.jadwalkuliah.MenuUjian.Matkul.UjianMatkulReq;
import com.baladika.jadwalkuliah.MenuUjian.Matkul.UjianMatkulUpdateActivity;
import com.baladika.jadwalkuliah.R;

import java.util.List;

public class UjianMatkulReqAdapterRecyclerView extends RecyclerView.Adapter<UjianMatkulReqAdapterRecyclerView.MyViewHolder> {

    private List<UjianMatkulReq> matkulList;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rl_layout;
        public TextView tv_matkul,tv_ruangan,tv_jam,tv_hari;

        public MyViewHolder(View view){
            super(view);
            rl_layout = view.findViewById(R.id.ujian_matkul_layout);
            tv_matkul = view.findViewById(R.id.ujian_matkul_judul);
            tv_ruangan = view.findViewById(R.id.ujian_matkul_ruangan);
            tv_jam = view.findViewById(R.id.ujian_matkul_jam);
            tv_hari = view.findViewById(R.id.ujian_matkul_hari);

        }
    }

    public UjianMatkulReqAdapterRecyclerView(List<UjianMatkulReq> moviesList, Activity activity){
        this.mActivity = activity;
        this.matkulList = moviesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ujian_matkul,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final UjianMatkulReq Umatkul = matkulList.get(position);
        holder.tv_matkul.setText(Umatkul.getUmatkul());
        holder.tv_ruangan.setText(Umatkul.getUruangan());
        holder.tv_jam.setText(Umatkul.getUjam());
        holder.tv_hari.setText(Umatkul.getUhari());


        holder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Tahan matkul untuk mengedit ", Toast.LENGTH_SHORT).show();
            }
        });

        holder.rl_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent getDetail = new Intent(mActivity, UjianMatkulUpdateActivity.class);

                getDetail.putExtra("Uid",Umatkul.getUserId());
                getDetail.putExtra("id",Umatkul.getSmstrId());
                getDetail.putExtra("mid",Umatkul.getKey());
                getDetail.putExtra("matkul",Umatkul.getUmatkul());
                getDetail.putExtra("ruangan",Umatkul.getUruangan());
                getDetail.putExtra("jam",Umatkul.getUjam());
                getDetail.putExtra("hari",Umatkul.getUhari());

                mActivity.startActivity(getDetail);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return matkulList.size();
    }
}
