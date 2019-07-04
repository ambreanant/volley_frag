package com.example.ibt.firstapk.Eblog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.ibt.firstapk.AppController;
import com.example.ibt.firstapk.Esocial.LandingSocial;
import com.example.ibt.firstapk.Ewave.WaveMainActivity;
import com.example.ibt.firstapk.MyVolleyRequest;
import com.example.ibt.firstapk.R;
import com.pkmmte.view.CircularImageView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, TextWatcher {
    CircularImageView fab;
    private NetworkImageView Avtar;
    private ImageLoader imageLoader;
    private LinearLayout layoutFabmyBlog,toolbar_back,Li_exclusive,Li_trending,Li_ncil,appbar,appbar_search,li_tool,li_search;
    TextView profile_lbl;
    FrameLayout fabFrame;
    ArrayAdapter<String> mAdapter;
    CustomAdapterdis adapter;
    CustomAdapterdis_search adapter_search;
    ListView listView,listviewlist_search;
    String username,user_photo,resu="",web_url,count="0";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public boolean isLoading = false,fabExpanded = false;
    public Handler mHandler;
    private List<CategoryModeldis> categoriesdis;
    private List<CategoryModeldis_search> search_categoriesdis;
    Thread thread;
    public View ftView;
    public int currentId,id,i,totalcnt;
    EditText et_search;
    AppController globalClassV;
    ImageView search_cancel,iv_searchback,searchIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_activity_main);

        searchIv=(ImageView)findViewById(R.id.searchIv);
        search_categoriesdis = new ArrayList<>();
        mHandler = new MyHandler();
        listView = (ListView) findViewById(R.id.listviewlist);
        listviewlist_search = (ListView) findViewById(R.id.listviewlist_search);
        Avtar = (NetworkImageView) findViewById(R.id.Avtar);
        profile_lbl=(TextView)findViewById(R.id.profile_lbl);
        fabFrame=(FrameLayout)findViewById(R.id.fabFrame);
        toolbar_back=(LinearLayout)findViewById(R.id.toolbar_back);
        et_search=(EditText)findViewById(R.id.et_search);
        search_cancel=(ImageView)findViewById(R.id.search_cancel);iv_searchback=(ImageView)findViewById(R.id.iv_searchback);
        appbar=(LinearLayout)findViewById(R.id.appbar);
        appbar_search=(LinearLayout)findViewById(R.id.appbar_search);
        //Floating expand button
        fab=(CircularImageView)findViewById(R.id.fab);
        layoutFabmyBlog =(LinearLayout)findViewById(R.id.layoutFabMyBlog);
        Li_exclusive=(LinearLayout)findViewById(R.id.Li_exclusive);
        Li_trending=(LinearLayout)findViewById(R.id.Li_trending);
        Li_ncil=(LinearLayout)findViewById(R.id.Li_ncil);
        li_tool=(LinearLayout)findViewById(R.id.li_tool);
        li_search=(LinearLayout)findViewById(R.id.li_search);

        LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ftView = li.inflate(R.layout.blog_footer_view, null);
        profile_lbl.setText("BLOG");

        globalClassV=(AppController) getApplicationContext();
        username=globalClassV.getUser_id();
        user_photo=globalClassV.getPhoto_url();
        web_url=globalClassV.getWeb_url();
        Avtar.setOnClickListener(this);
        toolbar_back.setOnClickListener(this);
        fab.setOnClickListener(this);
        layoutFabmyBlog.setOnClickListener(this);
        searchIv.setOnClickListener(this);
        li_search.setOnClickListener(this);
        search_cancel.setOnClickListener(this);
        et_search.addTextChangedListener(this);
        closeSubMenusFab();
        //swipe refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.simpleSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,android.R.color.holo_green_dark,android.R.color.holo_orange_dark,android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                refresh();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Avtar:
//                startActivity(new Intent(this, EprofileLanding.class));
                break;
            case R.id.toolbar_back:
                startActivity(new Intent(this, LandingSocial.class));
                break;
            case R.id.layoutFabMyBlog:
                startActivity(new Intent(MainActivity.this,myblog.class));
                break;
            case R.id.fab:
                if (fabExpanded == true){ closeSubMenusFab(); fabFrame.setBackgroundColor(Color.TRANSPARENT); }
                else { openSubMenusFab(); fabFrame.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d8a6a6a6"))); }
                break;
            case R.id.searchIv:
                InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                li_tool.setVisibility(View.GONE);appbar.setVisibility(View.GONE);
                appbar_search.setVisibility(View.VISIBLE); count="1";
                break;
            case R.id.search_cancel:
                et_search.setText("");
                search_categoriesdis.clear();
                break;
            case R.id.iv_searchback:
                back_activity();
                break;
            case R.id.li_search:
                back_activity();
                break;
        }
    }
    //Swipe refresh
    @Override
    public void onRefresh() { refresh(); }
    public void refresh()
    {
        String URL = "https://www.selfun.co.uk/api.aspx?flag=blv";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        System.out.println(response);
                        JSONArray obj = new JSONArray(response);
                        categoriesdis = new ArrayList<>();
                        for (i = 0; i < obj.length(); i++){
                            JSONObject mJsonObject = obj.getJSONObject(i);
                            String Sno =  mJsonObject.getString("Sno");
                            String title = mJsonObject.getString("title");
                            String fname = mJsonObject.getString("Fname");
                            String lname = mJsonObject.getString("Lname");
                            String User_photo = mJsonObject.getString("User_photo");
                            String likes = mJsonObject.getString("likes");
                            String Cmt_cnt = mJsonObject.getString("Cmt_cnt");
                            String Gender = mJsonObject.getString("Gender");
                            String user_name=mJsonObject.getString("Username");
                            String date_tm=mJsonObject.getString("date_tm");
                            String ago_tm=mJsonObject.getString("ago_tm");

                            if (i==0){
                                String photo=mJsonObject.getString("log_photo");
                                globalClassV.setPhoto_url(photo);
                                imageLoader= MyVolleyRequest.getInstance(MainActivity.this).getImageLoader();
                                imageLoader.get(web_url+photo,imageLoader.getImageListener(Avtar, R.drawable.ic_launcher, android.R.drawable.stat_notify_error));
                                Avtar.setImageUrl(web_url+photo,imageLoader);
                            }
                            categoriesdis.add(new CategoryModeldis( title,fname,lname,User_photo,likes,Cmt_cnt,Gender,Sno,date_tm,ago_tm,user_name));
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                        adapter = new CustomAdapterdis(MainActivity.this, categoriesdis);
                        listView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Connection lost", Toast.LENGTH_SHORT).show();
                }
            }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("flag1","ab");
                params.put("title","ab");//not using
                params.put("sn","0");
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        /*list adapter click event start*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CategoryModeldis model = categoriesdis.get(i); //changed it to model because viewers will confused about it
                String cat_id = model.gettitle();
                String b_photo = model.getUser_photo();
                globalClassV.setBlog_title(cat_id);
                startActivity(new Intent(MainActivity.this,personal_blog_display.class));
            }
        });
        /*list adapter click event end*/
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) { }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(view.getLastVisiblePosition() == totalItemCount-1 && listView.getCount() >=9 && isLoading == false) {
                    totalcnt=totalItemCount;
                    isLoading = true;
                    thread = new ThreadGetMoreData();
                    thread.start();
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after){ }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(et_search.getText().toString().length()>0){ blog_search(et_search.getText().toString()); }
        else{ search_categoriesdis.clear(); }
    }
    @Override
    public void afterTextChanged(Editable s) {  }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    listView.addFooterView(ftView);
                    break;
                case 1:
                    adapter.addListItemToAdapter((ArrayList<CategoryModeldis>)msg.obj);
                    listView.removeFooterView(ftView);
                    isLoading=false;
                    break;
                default:
                    break;
            }
        }
    }
    private ArrayList<CategoryModeldis> getMoreData() {
        final ArrayList<CategoryModeldis>lst = new ArrayList<>();
        String URL = "https://www.selfun.co.uk/api.aspx?flag=blv";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray obj = new JSONArray(response);
                        System.out.print(response);
                        for (int i = 0; i < obj.length(); i++){
                            JSONObject mJsonObject = obj.getJSONObject(i);
                            String Sno =  mJsonObject.getString("Sno");
                            String title = mJsonObject.getString("title");
                            String fname = mJsonObject.getString("Fname");
                            String lname = mJsonObject.getString("Lname");
                            String User_photo = mJsonObject.getString("User_photo");
                            String likes = mJsonObject.getString("likes");
                            String Cmt_cnt = mJsonObject.getString("Cmt_cnt");
                            String Gender = mJsonObject.getString("Gender");
                            String date_tm=mJsonObject.getString("date_tm");
                            String ago_tm=mJsonObject.getString("ago_tm");
                            String user_name=mJsonObject.getString("Username");

                            categoriesdis.add(new CategoryModeldis( title,fname,lname,User_photo,likes,Cmt_cnt,Gender,Sno,date_tm,ago_tm,user_name));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("flag1","ab");
                params.put("title","ab");//not using
                params.put("sn",String.valueOf(totalcnt+1));
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        return lst;
    }
    public class ThreadGetMoreData extends Thread {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            ArrayList<CategoryModeldis> lstResult = getMoreData();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg = mHandler.obtainMessage(1, lstResult);
            mHandler.sendMessage(msg);
        }
    }
    //closes FAB submenus
    private void closeSubMenusFab(){
        Li_exclusive.setVisibility(View.INVISIBLE);
        layoutFabmyBlog.setVisibility(View.INVISIBLE);
        Li_trending.setVisibility(View.INVISIBLE);
        Li_ncil.setVisibility(View.INVISIBLE);
        // fabPhot3o.setVisibility(View.INVISIBLE);
        fab.setImageResource(R.drawable.fabplus);
        fabExpanded = false;
    }
    //Opens FAB submenus
    private void openSubMenusFab(){
        Li_exclusive.setVisibility(View.VISIBLE);
        layoutFabmyBlog.setVisibility(View.VISIBLE);
        Li_trending.setVisibility(View.VISIBLE);
        Li_ncil.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fab.setImageResource(R.drawable.fabclose);
        fabExpanded = true;
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back_activity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void back_activity()
    {
        if(count.equals("1")){
            appbar.setVisibility(View.VISIBLE);
            appbar_search.setVisibility(View.GONE);
            li_tool.setVisibility(View.VISIBLE);
            count="0";et_search.setText("");  search_categoriesdis.clear();
        }else{ startActivity(new Intent(MainActivity.this,LandingSocial.class)); }
    }

    public void blog_search(String blog_search)
    {
        System.out.println("in blog search:"+blog_search);

        String URL = "https://selfun.co.uk/api.aspx?search="+blog_search+"&flag1=B&flag=global_search&sn=0";

        System.out.println("in blog URL:"+URL);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                                Log.d("message",response);
                                System.out.println(response);
                            //converting response to json object
                            JSONArray obj = new JSONArray(response);
                            for (i = 0; i < obj.length(); i++){
//                                    Log.d("count",i+"+++");
                                JSONObject mJsonObject = obj.getJSONObject(i);

                                String Sno =  mJsonObject.getString("Sno");
                                String title = mJsonObject.getString("title");
                                String fname = mJsonObject.getString("Fname");
                                String lname = mJsonObject.getString("Lname");
                                String User_photo = mJsonObject.getString("User_photo");
                                String likes = mJsonObject.getString("likes");
                                String Cmt_cnt = mJsonObject.getString("Cmt_cn");
                                String Gender = mJsonObject.getString("Gender");
                                String date_tm=mJsonObject.getString("date_tm");
                                String ago_tm=mJsonObject.getString("ago_tm");
                                String username=mJsonObject.getString("username");

                                search_categoriesdis.add(new CategoryModeldis_search( title,fname,lname,User_photo,likes,Cmt_cnt,Gender,Sno,date_tm,ago_tm,username));
                                // Toast.makeText(MainActivity.this,categoryName,Toast.LENGTH_SHORT).show();
                            }
//                            mSwipeRefreshLayout.setRefreshing(false);
                            adapter_search = new CustomAdapterdis_search(MainActivity.this, search_categoriesdis);
                            listviewlist_search.setAdapter(adapter_search);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("flag1","B");
//                params.put("search",blog_search);//not using
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        /*list adapter click event start*/
        listviewlist_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                    Toast.makeText(getApplicationContext(), "Clicked product id =" + view.getTag(), Toast.LENGTH_SHORT).show();

                CategoryModeldis_search model = search_categoriesdis.get(i); //changed it to model because viewers will confused about it

                String cat_id = model.gettitle();
//                String b_photo = model.getUser_photo();
                globalClassV.setBlog_title(cat_id);

                startActivity(new Intent(MainActivity.this,personal_blog_display.class));
            }
        });
        /*list adapter click event end*/
    }

}
