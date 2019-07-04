package com.example.ibt.firstapk.Eblog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.ibt.firstapk.MyVolleyRequest;
import com.example.ibt.firstapk.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdapterdis extends BaseAdapter {
    Activity activity;
    Context mContext;
    List<CategoryModeldis> users;
    LayoutInflater inflater;
    private ImageLoader imageLoader;
    ArrayList<CategoryModeldis> arraylist;
    public CustomAdapterdis(Activity activity) {
        this.activity = activity;
    }
    public CustomAdapterdis(Context context, List<CategoryModeldis> users) {
        this.activity   = activity;
        this.users      = users;
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<CategoryModeldis>();
        this.arraylist.addAll(users);
    }
    public void addListItemToAdapter(List<CategoryModeldis> list) {
        users.addAll(list);
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return users.size();
    }
    @Override
    public Object getItem(int i) {
        return i;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = inflater.inflate(R.layout.blog_list_view_itemdis, viewGroup, false);
            holder = new ViewHolder();
            holder.b_title = (TextView)view.findViewById(R.id.text);
            holder.b_fname = (TextView)view.findViewById(R.id.text1);
            holder.b_likecmt = (TextView)view.findViewById(R.id.text2);
            holder.blog_ago= (TextView)view.findViewById(R.id.text3);
            holder.b_userprofile = (ImageView) view.findViewById(R.id.image);
            view.setTag(holder);
        }else holder = (ViewHolder)view.getTag();

        CategoryModeldis model = users.get(i);
        holder.b_title.setText(model.gettitle());
        holder.b_fname.setText("Author- "+model.getfname());
        holder.b_likecmt.setText(model.getlikecmt());
        String pro_photo = model.getUser_photo().toString();
        Picasso.with(mContext).load(pro_photo).placeholder(R.drawable.ic_launcher).into(holder.b_userprofile);
//        imageLoader= MyVolleyRequest.getInstance(mContext).getImageLoader();
//        imageLoader.get(pro_photo,imageLoader.getImageListener( holder.b_userprofile, R.drawable.ic_launcher, android.R.drawable.stat_notify_error));
//        holder.b_userprofile.setImageUrl(pro_photo,imageLoader);

        String user_name=model.getUser_name();
        holder.b_userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), EprofileLanding.class);
//                intent.putExtra("oth_user",user_name);
//                intent.putExtra("oth_photo",pro_photo);
//                intent.putExtra("from","1");
//                v.getContext().startActivity(intent);
            }
        });
        String ago_tm=model.getago_tm();
        String str2="Hrs";
        String str3="Min";
        if( contains( ago_tm, str2 ) ) { holder.blog_ago.setText(model.getago_tm()); }
        else if( contains( ago_tm, str3 ) ) { holder.blog_ago.setText(model.getago_tm()); }
        else{ holder.blog_ago.setText(model.getdate_tm()); }
        return view;
    }

    public void updateRecords(List<CategoryModeldis>  users){ this.users = users; }
    class ViewHolder{
        TextView b_title,b_fname,b_likecmt,blog_ago;
        ImageView b_userprofile;
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        users.clear();
        if(charText.length() == 0) { users.addAll(arraylist); }
        else{
            for (CategoryModeldis wp : arraylist){
                if (wp.getsearch().toLowerCase(Locale.getDefault()).contains(charText)){ users.add(wp); }
            }
        }
        notifyDataSetChanged();
    }

    public boolean contains( String haystack, String needle ) {
        haystack = haystack == null ? "" : haystack;
        needle = needle == null ? "" : needle;
        return haystack.toLowerCase().contains( needle.toLowerCase() );
    }
}

