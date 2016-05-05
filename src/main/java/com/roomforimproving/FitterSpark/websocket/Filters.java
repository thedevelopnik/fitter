package com.roomforimproving.FitterSpark.websocket;

import com.roomforimproving.FitterSpark.twitter.MinTweet;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Created by davidsudia on 5/5/16.
 */
public class Filters {
    private String apiKey;
    private List<String> keywords;
    private Boolean kincaid;

    public Filters(String filterObject) {
        JSONObject jsonMessage = new JSONObject(filterObject);
        this.apiKey = jsonMessage.getString("apiKey");
        String temp = jsonMessage.getString("keywords");
        this.keywords = Arrays.asList(temp.split(","));
        this.kincaid = jsonMessage.getBoolean("kincaid");
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public Boolean getKincaid() {
        return kincaid;
    }

    public void setKincaid(Boolean kincaid) {
        this.kincaid = kincaid;
    }

    public Boolean match(MinTweet minTweet) {


        return true;
    }
}
