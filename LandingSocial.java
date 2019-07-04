package com.example.ibt.firstapk.Esocial;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ibt.firstapk.AppController;
import com.example.ibt.firstapk.MainActivity;
import com.example.ibt.firstapk.MyVolleyRequest;
import com.example.ibt.firstapk.R;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class LandingSocial extends MainActivity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener, Toolbar.OnMenuItemClickListener {
    private String URL;
    private boolean fabExpanded = false;
    ImageView fab;
    FloatingActionButton scroll_top;
    LinearLayout layoutFabSave,layoutFabEdit,layoutFabPhoto,playlist,Li_exclusive,Li_trending,
            Li_ncil,Li_favourites,li_btmnav,toolbar_back;
    FrameLayout fabFrame,fabGroup;
    int Mx_sn=0;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<Data> listdata;
    private RecyclerView recyclerView;

    private GridLayoutManager layoutManager;
    String username,web_url,photo,regid,flag1="AP",fname;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static final int CHECK_PERMISSION = 8001;
    private static final int CAMERA_RQ = 8099;
    private NetworkImageView Avtar;
    private ImageLoader imageLoader;
    ImageButton cam;
    Uri imageUri;Bitmap bitmap;
    String path,logout="0",audio_path;
    //Customize
    public CharSequence[] values = {" Public "," Personal "};
    RelativeLayout rL_toolright;
    AppController globalClassV;
    BottomNavigationView btmnav;
    TextView profile_lbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_landing_page);
        cam = (ImageButton) findViewById(R.id.cam);
        Li_exclusive=(LinearLayout)findViewById(R.id.Li_exclusive);
        Li_trending=(LinearLayout)findViewById(R.id.Li_trending);
        Li_ncil=(LinearLayout)findViewById(R.id.Li_ncil);
        Li_favourites=(LinearLayout)findViewById(R.id.Li_favourites);
        Avtar = (NetworkImageView) findViewById(R.id.Avtar);
        toolbar_back = (LinearLayout) findViewById(R.id.toolbar_back);
        toolbar_back.setVisibility(View.GONE);
        btmnav = (BottomNavigationView) findViewById(R.id.btmnav);
        fab=(CircularImageView)findViewById(R.id.fab);
        layoutFabSave =  this.findViewById(R.id.layoutFabSave);
        layoutFabEdit =  this.findViewById(R.id.layoutFabEdit);
        layoutFabPhoto = this.findViewById(R.id.layoutFabPhoto);
        playlist= this.findViewById(R.id.playlist);
        fabFrame=(FrameLayout)findViewById(R.id.fabFrame);
        fabGroup=(FrameLayout)findViewById(R.id.fabGroup);
        li_btmnav=(LinearLayout)findViewById(R.id.li_btmnav);
        scroll_top=(FloatingActionButton) findViewById(R.id.scroll_top);
        rL_toolright=(RelativeLayout)findViewById(R.id.rL_toolright);
        rL_toolright.setPadding(0,0,0,0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.social_landingmenu);

        //Globla veriable class
        globalClassV = (AppController) getApplicationContext();
        username = globalClassV.getUser_id();
        regid = globalClassV.getReg_id();
        web_url = globalClassV.getWeb_url();
        photo = globalClassV.getPhoto_url();
        profile_lbl=(TextView)findViewById(R.id.profile_lbl);
        btmnav = (BottomNavigationView) findViewById(R.id.btmnav);
        removeShiftMode(btmnav);
        //layoutManager = new GridLayoutManager(this, 2);
        //layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        listdata = new ArrayList<Data>();
        recyclerAdapter = new RecyclerAdapter(this, listdata);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
        // SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.simpleSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,android.R.color.holo_green_dark,android.R.color.holo_orange_dark,android.R.color.holo_blue_dark);
        flag1=globalClassV.getLast_flag();
        if(flag1 == null){ flag1="AP"; }
        mSwipeRefreshLayout.setOnRefreshListener(this);
        scroll_top.setOnClickListener(this);
        toolbar_back.setOnClickListener(this);
        cam.setOnClickListener(this);
        Avtar.setOnClickListener(this);
        Li_exclusive.setOnClickListener(this);
        Li_trending.setOnClickListener(this);
        Li_ncil.setOnClickListener(this);
        Li_favourites.setOnClickListener(this);
        fabFrame.setOnClickListener(this);
        btmnav.setOnNavigationItemSelectedListener(this);
        toolbar.setOnMenuItemClickListener(this);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                listdata.clear();
                ambidata();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!recyclerView.canScrollVertically(1)) {
                mSwipeRefreshLayout.setRefreshing(true);
                ambidata();
            }
            }
        });
        closeSubMenusFab();
        fab.setOnClickListener(this);

        btmnav.setSelectedItemId(R.id.social);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cam:
               // showPictureDialog();
//                video_record();
                record_video();
                break;
            case R.id.Avtar:
                globalClassV.setOth_user(username);
                break;
            case R.id.toolbar_back:
                fisttime_back();
                break;
            case R.id.fab:
                fabFrame.setVisibility(View.VISIBLE);
                if(fabExpanded == true){ closeSubMenusFab(); fabFrame.setVisibility(View.GONE); }
                else{ openSubMenusFab(); fabFrame.setVisibility(View.VISIBLE); }
                break;
            case R.id.fabFrame:
                fabFrame.setVisibility(View.GONE);
                closeSubMenusFab();
                break;
            case R.id.Li_exclusive:
                flag1="EX"; profile_lbl.setText("Exclusive Posts");
                float_btnclick();
                break;
            case R.id.Li_favourites:
                flag1="FV"; profile_lbl.setText("Favourite Posts");
                float_btnclick();
                break;
            case R.id.Li_trending:
                flag1="TR"; profile_lbl.setText("Trending Posts");
                float_btnclick();
                break;
            case R.id.Li_ncil:
                flag1="NC";;username="NCIL"; profile_lbl.setText("NCIL Posts");
                float_btnclick();
                break;
            case R.id.scroll_top:
                recyclerView.smoothScrollToPosition(0);
                break;
        }
    }
    public void float_btnclick(){
        closeSubMenusFab();fabFrame.setVisibility(View.GONE);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.re_recswipe);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 0);layout.setLayoutParams(layoutParams );
        fab.setVisibility(View.GONE);cam.setVisibility(View.GONE);li_btmnav.setVisibility(View.GONE);
        toolbar_back.setVisibility(View.VISIBLE);
        logout="2";Mx_sn=0;listdata.clear();ambidata();
    }
    private void closeSubMenusFab(){
        Li_exclusive.setVisibility(View.INVISIBLE);
        Li_favourites.setVisibility(View.INVISIBLE);
        Li_trending.setVisibility(View.INVISIBLE);
        Li_ncil.setVisibility(View.INVISIBLE);
        fab.setImageResource(R.drawable.fabplus);
        fabExpanded = false;
    }
    private void openSubMenusFab(){
        Li_exclusive.setVisibility(View.VISIBLE);
        Li_favourites.setVisibility(View.VISIBLE);
        Li_trending.setVisibility(View.VISIBLE);
        Li_ncil.setVisibility(View.VISIBLE);
        fab.setImageResource(R.drawable.fabclose);
        fabExpanded = true;
    }
    private void video_record(){
        if (ContextCompat.checkSelfPermission(LandingSocial.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(LandingSocial.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(LandingSocial.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(LandingSocial.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) { startRecordVideo(); }
        else { ActivityCompat.requestPermissions(LandingSocial.this, new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, CHECK_PERMISSION); }
    }

    public void record_video()
    {
        if (ContextCompat.checkSelfPermission(LandingSocial.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(LandingSocial.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(LandingSocial.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(LandingSocial.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(this, com.example.ibt.firstapk.RecordVideo.MainActivity.class);
            startActivity(intent);
        }
        else { ActivityCompat.requestPermissions(LandingSocial.this, new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, CHECK_PERMISSION); }
    }
    public void choosePhotoFromGallary() { Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); startActivityForResult(i, 100); }    public void chooseVideoFromGallary(){  Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI); startActivityForResult(i, 101); }
    //Dialogue on click camera icon
    private void showPictureDialog()
    {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(LandingSocial.this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = { "Select Image from gallery", "Select Video from gallery", "Video Record" };
        pictureDialog.setItems(pictureDialogItems,
            new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: choosePhotoFromGallary();
                        break;
                    case 1: chooseVideoFromGallary();
                        break;
                    case 2: video_record();
                        break;
                }
            }
        });
        pictureDialog.show();
    }
    /*  Mobile + toobar back activity */
    @Override
    public void onBackPressed() { fisttime_back(); }
    public void fisttime_back(){
        if(logout.equals("1")){ back_activity(); }
        else if(logout.equals("2")){
            username=globalClassV.getUser_id(); flag1="AP";profile_lbl.setText("EonNet");
            cam.setVisibility(View.VISIBLE);li_btmnav.setVisibility(View.VISIBLE);
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.re_recswipe);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0, 0, 0, 50);
            layout.setLayoutParams(layoutParams );fab.setVisibility(View.VISIBLE);
            listdata.clear(); Mx_sn=0; logout="0"; ambidata();
        }
        else{ logout="1";
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() { logout = "0"; }
            }, 2000);
            Toast.makeText(LandingSocial.this,"Click again to exit",Toast.LENGTH_SHORT).show();
        }
    }
    public void back_activity(){ finishAffinity(); }
    //On activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            globalClassV=(AppController) getApplicationContext();
            switch (requestCode) {
                case 100:
                    imageUri = intent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    path = cursor.getString(columnIndex);
                    bitmap = BitmapFactory.decodeFile(path);
                    if (bitmap == null){
                        try { bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri); }
                        catch (IOException e) { e.printStackTrace(); }
                    }
                    globalClassV.setFile_path(path);
                    globalClassV.setImg_preview(bitmap);
                    globalClassV.setPrivew_type("i");
                    globalClassV.setAudio_path("NA");
                    File file = new File(path);
                    fname = file.getName();
                    fname =regid+"_"+fname;
                    chech_post_exist(fname);
                    break;
                case 101:
                    imageUri = intent.getData();
                    String[] filePathColumn1 = {MediaStore.Video.Media.DATA};
                    Cursor cursor1 = getContentResolver().query(imageUri, filePathColumn1, null, null, null);
                    assert cursor1 != null;
                    cursor1.moveToFirst();
                    int columnIndex1 = cursor1.getColumnIndex(filePathColumn1[0]);
                    path = cursor1.getString(columnIndex1);
                    String vid_path = intent.getData().toString();
                    globalClassV.setFile_path(path);
                    globalClassV.setVid_preview(vid_path);
                    globalClassV.setPrivew_type("v");
                    globalClassV.setAudio_path("NA");
                    File file2 = new File(path);
                    fname = file2.getName();
                    fname = fname.substring(0, fname.lastIndexOf("."));
                    fname ="a"+regid+"_"+fname+".mp4";
                    chech_post_exist(fname);
                    break;
                case CAMERA_RQ:
                    try
                    {
                        String path1 = intent.getStringExtra("videoUrl");
                        audio_path=intent.getStringExtra("AudioUrl");
                        String speed=intent.getStringExtra("slowmotion");
                        path=path1.substring(7,path1.length());
                        globalClassV.setFile_path(path);
                        globalClassV.setVid_preview(path1);
                        globalClassV.setPrivew_type("r");
                        globalClassV.setAudio_path(audio_path);
                        globalClassV.setSlowmotion(speed);
                        startActivity(new Intent(LandingSocial.this, SocialPostActivity.class));
                    }
                    catch (Exception ex) { }
                    startActivity(new Intent(LandingSocial.this, SocialPostActivity.class));
                    break;
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CHECK_PERMISSION
                && grantResults.length == 4
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED
                && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
            startRecordVideo();
        }
    }

    //Fetch data from database (landing view)
    public void ambidata(){
        URL = web_url + "api.aspx?username=" + username + "&my_username=" + username + "&sn="+Mx_sn+"&flag1="+flag1+"&flag=all_post_andr";
        JsonArrayRequest arrReq = new JsonArrayRequest(URL,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length()>0) {
                            for (int i = 0; i<response.length(); i++) {
                                try {
                                    JSONObject data = response.getJSONObject(i);
                                    Data item = new Data();
                                    item.setId(data.getString("username"));
                                    item.setPostedtime(data.getString("Postedon"));
                                    item.setPostTitle(data.getString("post_text"));
                                    String post_file_name=data.getString("post_file_name");
                                    item.setLike(data.getString("likes"));
                                    item.setFull_name(data.getString("full_name"));
                                    item.setDislike(data.getString("dislikes"));
                                    item.setPostFilename(post_file_name);
                                    String post_type=data.getString("post_type");
                                    item.setPhoto(web_url+data.getString("Photo"));
                                    item.setPostType(post_type);
                                    item.setDisplay_from("LS");
                                    item.setReq_user(username);
                                    item.setReq_flag("AP");
                                    if(post_type.startsWith("I"))
                                    {
                                        item.setPostImage(web_url+data.getString("post_file_URL"));
                                        item.setPostVideo("");
                                    }
                                    else if (post_type.startsWith("V"))
                                    {
                                        item.setPostImage("");
                                        item.setPostVideo(web_url+data.getString("post_file_URL"));
                                    }
                                    if (i==0){
                                        String sn=data.getString("mx_sn");
                                        Mx_sn= Integer.parseInt(sn);
                                        Log.d("abcd", String.valueOf(Mx_sn));
                                        String photo=data.getString("User_photo");
                                        if(username != "NCIL") {
                                            globalClassV.setPhoto_url(photo);
                                            imageLoader = MyVolleyRequest.getInstance(LandingSocial.this).getImageLoader();
                                            imageLoader.get(web_url + photo, imageLoader.getImageListener(Avtar, R.drawable.ic_launcher, android.R.drawable.stat_notify_error));
                                            Avtar.setImageUrl(web_url + photo, imageLoader);
                                        }
                                    }
                                    listdata.add(item);
                                    recyclerAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
        };
        Volley.newRequestQueue(LandingSocial.this).add(arrReq);
    }
    //To show icon in inflator(three dot)
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.landing_menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }
    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {
        if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch(NoSuchMethodException e){ }
                catch(Exception e){ throw new RuntimeException(e); }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    //Bottom navigation shifting issue
    @SuppressLint("RestrictedApi")
    public void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }

    //Swipe refresh
    @Override
    public void onRefresh() {
        if(username.equals("NCIL")){flag1="NC";username="NCIL";profile_lbl.setText("NCIL Post");float_btnclick();}
        else if(logout.equals("2")){ Mx_sn=0; listdata.clear(); float_btnclick();}
        else{Mx_sn=0;listdata.clear();ambidata();}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect:
                //Toast.makeText(this,"chat",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.club:
                //Toast.makeText(this,"Club list",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.social:
                //Toast.makeText(this,"Social default call ambidata",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.trending:
//                startActivity(new Intent(this, GlobalActivity.class));
                return true;
        }
        return false;
    }

    /*check file already exist*/

    public void chech_post_exist(final String filename){
        String URL = "https://www.selfun.co.uk/api.aspx?username="+username+"&file_name="+filename+"&flag1=C&flag=esocial_fileudelete";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONArray mJsonArray = new JSONArray(s);
                    for (int i = 0; i < mJsonArray.length(); i++){
                        JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                        String resu = mJsonObject.getString("resu");
                        if(resu.equals("Y")){
                            Toast.makeText(LandingSocial.this,"Already exist",Toast.LENGTH_LONG).show();
                            showPictureDialog();
                        }
                        else{
                            startActivity(new Intent(LandingSocial.this, SocialPostActivity.class));
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {  }
        }) {
        };
        RequestQueue rQueue = Volley.newRequestQueue(LandingSocial.this);
        rQueue.add(request);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_main_wave) {
            startActivity(new Intent(LandingSocial.this, com.example.ibt.firstapk.Ewave.WaveMainActivity.class));
        } else if (item.getItemId() == R.id.menu_main_blog) {
            startActivity(new Intent(LandingSocial.this, com.example.ibt.firstapk.Eblog.MainActivity.class));
        }
        return false;
    }
}
