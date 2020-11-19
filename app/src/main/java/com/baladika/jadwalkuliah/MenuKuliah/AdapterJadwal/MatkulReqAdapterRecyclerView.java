package com.baladika.jadwalkuliah.MenuKuliah.AdapterJadwal;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baladika.jadwalkuliah.MenuKuliah.Matkul.MatkulActivity;
import com.baladika.jadwalkuliah.MenuKuliah.Matkul.MatkulReq;
import com.baladika.jadwalkuliah.R;

import java.util.List;

public class MatkulReqAdapterRecyclerView extends RecyclerView.Adapter<MatkulReqAdapterRecyclerView.MyViewHolder> {

    private List<MatkulReq> matkulList;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rl_layout;
        public TextView tv_matkul,tv_ruangan,tv_jam,tv_hari;

        public MyViewHolder(View view){
            super(view);
            rl_layout = view.findViewById(R.id.matkul_layout);
            tv_matkul = view.findViewById(R.id.matkul_judul_1);
            tv_ruangan = view.findViewById(R.id.matkul_ruangan_1);
            tv_jam = view.findViewById(R.id.matkul_jam_1);
            tv_hari = view.findViewById(R.id.matkul_hari_1);

        }
    }

    public MatkulReqAdapterRecyclerView(List<MatkulReq> moviesList, Activity activity){
        this.mActivity = activity;
        this.matkulList = moviesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_matkul,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MatkulReq matkul = matkulList.get(position);
        holder.tv_matkul.setText(matkul.getMatkul());
        holder.tv_ruangan.setText(matkul.getRuangan());
        holder.tv_jam.setText(matkul.getJam());
        holder.tv_hari.setText(matkul.getHari());


        holder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getDetail = new Intent(mActivity, MatkulActivity.class);

                getDetail.putExtra("Uid",matkul.getUserId());
                getDetail.putExtra("id",matkul.getSmstrId());
                getDetail.putExtra("mid",matkul.getKey());
                getDetail.putExtra("matkul",matkul.getMatkul());
                getDetail.putExtra("ruangan",matkul.getRuangan());
                getDetail.putExtra("jam",matkul.getJam());
                getDetail.putExtra("hari",matkul.getHari());

                mActivity.startActivity(getDetail);

            }
        });
    }

    @Override
    public int getItemCount() {
        return matkulList.size();
    }
}
