package com.example.ananttask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{
    Context context;
    List<Movies> moviesList;

    public RecyclerViewAdapter(Context context, List<Movies> moviesList)
    {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v= LayoutInflater.from(context).inflate(R.layout.item_movie,viewGroup,false);
        MyViewHolder  myviewHolder=new MyViewHolder(v);
        return myviewHolder;
    }

    public int getItemCount()
    {
        return moviesList.size();
    }

    public void onBindViewHolder(MyViewHolder holder,int position)
    {
        holder.name.setText(moviesList.get(position).getName());
        holder.phone_num.setText(moviesList.get(position).getPhn());
//        holder.imageView.setImageResource(moviesList.get(position).getPhoto());
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView phone_num;
        ImageView imageView;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_contact);
            phone_num = (TextView) itemView.findViewById(R.id.ph_number);
            imageView = (ImageView) itemView.findViewById(R.id.img);

        }
    }
}