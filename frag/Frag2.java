package com.example.ananttask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Frag2 extends Fragment {

    RecyclerView recyclerView;
    List<Movies> listCont;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState)
    {
        View view=inflater.inflate(R.layout.frag2,container,false);

        recyclerView =view.findViewById(R.id.list_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(getContext(),listCont);
        recyclerView.setAdapter(adapter);

        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listCont = new ArrayList<>();
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
        listCont.add(new Movies("Mahadi Hasan","01717677540",R.drawable.ic_launcher_background));
    }
}
