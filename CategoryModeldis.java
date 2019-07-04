package com.example.ibt.firstapk.Eblog;

public class CategoryModeldis {

    String title,fname,lname,User_photo,likes,Gender,Cmt_cnt,Sno,date_tm,ago_tm,user_name;

    public CategoryModeldis(String title, String fname, String lname,String User_photo,String likes,String Cmt_cnt,String Gender,String Sno,String date_tm,String ago_tm,String user_name) {

        this.title = title;this.fname = fname;this.lname = lname;this.Sno = Sno;this.date_tm=date_tm;this.ago_tm=ago_tm;this.user_name=user_name;

        if(User_photo.equals("NULL") || User_photo.equals("null")){
            if(Gender.equals("M")){ this.User_photo = "https://www.selfun.co.uk/default_images/default-user-male.png"; }
            else if(Gender.equals("F")){ this.User_photo = "https://www.selfun.co.uk/default_images/default-user-female.png"; }
            else{ this.User_photo = "https://www.selfun.co.uk/default_images/default-user-male.png"; }
        }
        else{ this.User_photo = "https://www.selfun.co.uk/"+User_photo; }
        if(likes.equals("NULL") || likes.equals("null")){ this.likes = "0"; }
        else{ this.likes = likes; }
        this.Cmt_cnt = Cmt_cnt;
    }
    public String getSno() {
        return Sno;
    }
    public String gettitle() {
        return title;
    }
    public void settitle(String categoryName) {
        this.title = title;
    }
    public String getfname() {
        return fname+" "+lname;
    }
    public String getsearch() {
        return fname+" "+lname+" "+title;
    }
    public void setfname(String categoryId) {
        this.fname = categoryId;
    }
    public String getlname() {
        return lname;
    }
    public String getvie() {
        return "";
    }
    public String getUser_photo() {
        return User_photo;
    }
    public String getlikecmt() {
        return likes+" Likes. "+Cmt_cnt+" Comments";
    }
    public String getdate_tm() { return date_tm; }
    public String getago_tm() { return ago_tm; }
    public String getUser_name() { return user_name; }
}

