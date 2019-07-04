package com.example.ibt.firstapk.Esocial;

public class Data {
  private String id,postedTime,postTitle,postImage,postVideo,postType,display_from,photo,postFilename,like,dislike;
  private String full_name,req_user,req_flag;

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

    public String getFull_name() {
        return full_name;
    }
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

  public String getPostedTime() {
    return postedTime;
  }
  public void setPostedtime(String postedTime) {
    this.postedTime = postedTime;
  }

  public String getPostTitle() {
    return postTitle;
  }
  public void setPostTitle(String postTitle) {
    this.postTitle = postTitle;
  }

  public String getPostImage() {
    return postImage;
  }
  public void setPostImage(String postImage) {
    this.postImage = postImage;
  }

  public String getPostVideo() {
    return postVideo;
  }
  public void setPostVideo(String postVideo) {
    this.postVideo = postVideo;
  }

  public String getPostType() {
    return postType;
  }
  public void setPostType(String postType) {
    this.postType = postType;
  }

  public String getDisplay_from(){return display_from;}
  public void setDisplay_from(String display_from){this.display_from = display_from;}

  public String getPhoto(){return photo;}
  public void setPhoto(String photo){this.photo = photo;}

  public String getPostFilename() {
    return postFilename;
  }
  public void setPostFilename(String postFilename) {
    this.postFilename = postFilename;
  }

  public String getLike() {
    return like;
  }
  public void setLike(String like) {this.like = like;}

  public String getDislike() {
    return dislike;
  }
  public void setDislike(String dislike) {
    this.dislike = dislike;
  }

  public String getReq_user() { return req_user; }
  public void setReq_user(String req_user) { this.req_user=req_user; }

  public String getReq_flag() { return req_flag; }
  public void setReq_flag(String req_flag) { this.req_flag=req_flag; }
}
