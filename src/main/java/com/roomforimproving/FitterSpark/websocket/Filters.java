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
    private Integer kincaid;

    public Filters(String filterObject) {
        JSONObject jsonMessage = new JSONObject(filterObject);
        this.apiKey = jsonMessage.getString("apiKey");
        String temp = jsonMessage.getString("keywords");
        this.keywords = Arrays.asList(temp.split(","));
        this.kincaid = jsonMessage.getInt("kincaid");
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

    public Integer getKincaid() {
        return kincaid;
    }

    public void setKincaid(Integer kincaid) {
        this.kincaid = kincaid;
    }

    public Boolean match(MinTweet minTweet) {
        // only send Tweet if Kincaid >=
        if (minTweet.getGrade() >= this.getKincaid()) {
            if(!keywords.isEmpty()) {
                for(String word : this.keywords) {
                    if (minTweet.getText().contains(word)) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }
}
