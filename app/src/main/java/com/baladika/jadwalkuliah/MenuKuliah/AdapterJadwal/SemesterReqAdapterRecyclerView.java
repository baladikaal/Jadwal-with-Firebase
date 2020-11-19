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

import com.baladika.jadwalkuliah.MenuKuliah.Matkul.MatkulListActivity;
import com.baladika.jadwalkuliah.MenuKuliah.Semester.SemesterReq;
import com.baladika.jadwalkuliah.R;

import java.util.List;

public class SemesterReqAdapterRecyclerView extends RecyclerView.Adapter<SemesterReqAdapterRecyclerView.MyViewHolder> {

    private List<SemesterReq> moviesList;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout semester_layout;
        public TextView tv_smstr,tv_tahun;

        public MyViewHolder(View view){
            super(view);
            semester_layout = view.findViewById(R.id.semester_layout);
            tv_smstr = view.findViewById(R.id.sms_judul);
            tv_tahun = view.findViewById(R.id.sms_tahun);
        }
    }

    public SemesterReqAdapterRecyclerView(List<SemesterReq> moviesList, Activity activity){
        this.mActivity = activity;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_semester,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final SemesterReq movie = moviesList.get(position);
        holder.tv_smstr.setText(movie.getSemester());
        holder.tv_tahun.setText(movie.getTahun());

        holder.semester_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getDetail = new Intent(mActivity, MatkulListActivity.class);

                getDetail.putExtra("Uid",movie.getUserId());
                getDetail.putExtra("id",movie.getKey());
                getDetail.putExtra("semester",movie.getSemester());
                getDetail.putExtra("tahun",movie.getTahun());

                mActivity.startActivity(getDetail);

            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


}