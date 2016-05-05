package com.roomforimproving.FitterSpark.twitter;

import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Created by davidsudia on 5/5/16.
 */
public class MinTweet {
    private String id;
    private String text;
    private String user;
    private String profileImageUrl;
    private String lang;
    private String mediaUrl;
    private float grade;

    public MinTweet(JSONObject tweet) {

        // grab data from tweet
        this.id = (String) tweet.get("id");
        this.text = (String) tweet.get("text");
        JSONObject user = (JSONObject) tweet.get("user");
        this.user = (String) user.get("screen_name");
        this.profileImageUrl = (String) user.get("profile_image_url_https");
        this.lang = (String) tweet.get("lang");
        if (tweet.has("extended_entities")) {
            JSONObject extended = (JSONObject) tweet.get("extended_entities");
            if (extended.has("media")) {
                ArrayList<JSONObject> media = (ArrayList<JSONObject>) extended.get("media");
                JSONObject mediaObj = media.get(0);
                this.mediaUrl = (String) mediaObj.get("media_url");
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;

    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}