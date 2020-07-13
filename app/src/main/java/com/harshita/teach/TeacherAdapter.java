package com.harshita.teach;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.MyViewHolder>{

    private ArrayList<Teacher> itemList;

    public TeacherAdapter(ArrayList<Teacher> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public TeacherAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherAdapter.MyViewHolder holder, int position) {

        Teacher row = itemList.get(position);
        holder.nameTeacher.setText(row.getName());
        holder.imageTeacher.setImageResource(row.getImageID());
        holder.majorTeacher.setText(row.getMajor());
        holder.subject1Teacher.setText(row.getSubjects()[0]);
        holder.subject2Teacher.setText(row.getSubjects()[1]);
        holder.subject3Teacher.setText(row.getSubjects()[2]);
        holder.priceRangeTeacher.setText(row.getPriceRange());

    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout main;
        public TextView nameTeacher, majorTeacher, subject1Teacher, subject2Teacher, subject3Teacher, priceRangeTeacher;
        public ImageView imageTeacher;


        public MyViewHolder(@NonNull View parent) {
            super(parent);

            nameTeacher = (TextView) parent.findViewById(R.id.name_teacher);
            majorTeacher = (TextView) parent.findViewById(R.id.teacher_major);
            subject1Teacher = (TextView) parent.findViewById(R.id.teacher_subject1);
            subject2Teacher = (TextView) parent.findViewById(R.id.teacher_subject2);
            subject3Teacher = (TextView) parent.findViewById(R.id.teacher_subject3);
            priceRangeTeacher = (TextView) parent.findViewById(R.id.price_range);
            imageTeacher = (ImageView) parent.findViewById(R.id.image_teacher);
            main = (RelativeLayout) parent.findViewById(R.id.main);

            main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
