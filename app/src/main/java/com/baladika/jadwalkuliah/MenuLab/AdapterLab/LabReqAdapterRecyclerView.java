package com.baladika.jadwalkuliah.MenuLab.AdapterLab;

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

import com.baladika.jadwalkuliah.MenuLab.Lab.LabReq;
import com.baladika.jadwalkuliah.MenuLab.Lab.LabUpdateActivity;
import com.baladika.jadwalkuliah.R;

import java.util.List;

public class LabReqAdapterRecyclerView extends RecyclerView.Adapter<LabReqAdapterRecyclerView.MyViewHolder> {

    private List<LabReq> LabList;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rl_layout;
        public TextView tv_lab,tv_ruangan,tv_jam,tv_hari,tv_note;

        public MyViewHolder(View view){
            super(view);
            rl_layout = view.findViewById(R.id.lab_layout);
            tv_lab = view.findViewById(R.id.lab_judul);
            tv_ruangan = view.findViewById(R.id.lab_ruangan);
            tv_jam = view.findViewById(R.id.lab_jam);
            tv_hari = view.findViewById(R.id.lab_hari);
            tv_note = view.findViewById(R.id.lab_note);


        }
    }

    public LabReqAdapterRecyclerView(List<LabReq> LabList, Activity activity){
        this.mActivity = activity;
        this.LabList = LabList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lab,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final LabReq lab = LabList.get(position);
        holder.tv_lab.setText(lab.getLab());
        holder.tv_ruangan.setText(lab.getRuangan());
        holder.tv_jam.setText(lab.getJam());
        holder.tv_hari.setText(lab.getHari());
        holder.tv_note.setText(lab.getNote());


        holder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Tahan matkul untuk mengedit ", Toast.LENGTH_SHORT).show();
            }
        });

        holder.rl_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent getDetail = new Intent(mActivity, LabUpdateActivity.class);

                getDetail.putExtra("Uid",lab.getUserId());
                getDetail.putExtra("date",lab.getDate());
                getDetail.putExtra("id",lab.getKey());
                getDetail.putExtra("lab",lab.getLab());
                getDetail.putExtra("ruangan",lab.getRuangan());
                getDetail.putExtra("jam",lab.getJam());
                getDetail.putExtra("hari",lab.getHari());
                getDetail.putExtra("note",lab.getNote());

                mActivity.startActivity(getDetail);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return LabList.size();
    }
}
