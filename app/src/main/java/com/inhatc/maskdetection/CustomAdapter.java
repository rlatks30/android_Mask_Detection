package com.inhatc.maskdetection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Board> arrayList;
    private Context context;

    public CustomAdapter(ArrayList<Board> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.txt_id.setText(arrayList.get(position).getId());
        holder.txt_title.setText(arrayList.get(position).getTitle());
        holder.txt_con.setText(arrayList.get(position).getContent());
        holder.txt_regdet.setText(arrayList.get(position).getRegdet());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView txt_id, txt_title,txt_con, txt_regdet;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txt_id = itemView.findViewById(R.id.txt_id);
            this.txt_title = itemView.findViewById(R.id.txt_title);
            this.txt_con = itemView.findViewById(R.id.txt_content);
            this.txt_regdet = itemView.findViewById(R.id.txt_regdet);

        }
    }
}
