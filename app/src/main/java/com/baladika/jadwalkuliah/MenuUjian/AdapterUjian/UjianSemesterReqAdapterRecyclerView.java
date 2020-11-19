package com.baladika.jadwalkuliah.MenuUjian.AdapterUjian;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baladika.jadwalkuliah.MenuUjian.Matkul.UjianMatkulListActivity;
import com.baladika.jadwalkuliah.MenuUjian.Semester.UjianSemesterReq;
import com.baladika.jadwalkuliah.R;

import java.util.List;

public class UjianSemesterReqAdapterRecyclerView extends RecyclerView.Adapter<UjianSemesterReqAdapterRecyclerView.MyViewHolder> {

    private List<UjianSemesterReq> moviesList;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout semester_layout;
        public TextView tv_smstr,tv_tahun;

        public MyViewHolder(View view){
            super(view);
            semester_layout = view.findViewById(R.id.ujian_semester_layout);
            tv_smstr = view.findViewById(R.id.ujian_sms_judul);
            tv_tahun = view.findViewById(R.id.ujian_sms_tahun);
        }
    }

    public UjianSemesterReqAdapterRecyclerView(List<UjianSemesterReq> moviesList, Activity activity){
        this.mActivity = activity;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ujian_semester,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final UjianSemesterReq Usemester = moviesList.get(position);
        holder.tv_smstr.setText(Usemester.getUsemester());
        holder.tv_tahun.setText(Usemester.getUtahun());

        holder.semester_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getDetail = new Intent(mActivity, UjianMatkulListActivity.class);

                getDetail.putExtra("Uid",Usemester.getUserId());
                getDetail.putExtra("id",Usemester.getKey());
                getDetail.putExtra("semester",Usemester.getUsemester());
                getDetail.putExtra("tahun",Usemester.getUtahun());

                mActivity.startActivity(getDetail);

            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


}