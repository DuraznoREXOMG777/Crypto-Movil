package com.escom.topsecret.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.escom.topsecret.Entities.Project;
import com.escom.topsecret.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {
    List<Project> projects;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,  parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Project project = projects.get(position);
        holder.cvArea.setText(project.getArea());
        holder.cvDescription.setText(project.getDescription());
        holder.cvTitle.setText(project.getTitle());
        holder.cvDate.setText(project.getDate());
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cv_logo)
        ImageView cvLogo;
        @BindView(R.id.cv_area)
        TextView cvArea;
        @BindView(R.id.cv_title)
        TextView cvTitle;
        @BindView(R.id.cv_description)
        TextView cvDescription;
        @BindView(R.id.cv_date)
        TextView cvDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
