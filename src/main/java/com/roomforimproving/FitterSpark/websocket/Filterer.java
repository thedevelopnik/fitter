package com.roomforimproving.FitterSpark.websocket;

import com.roomforimproving.FitterSpark.database.Users;
import com.roomforimproving.FitterSpark.twitter.MinTweet;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.logging.Filter;

/*
 * Created by davidsudia on 5/5/16.
 */
public class Filterer {

    private HashMap<Session, Filters> filterMap;

    Filterer () { filterMap = new HashMap<Session, Filters>(); }

    public Boolean addOrReplaceFilter(Session session, Filters filter) {
        String apiKey = filter.getApiKey();
        if (Users.findValidApiKey(apiKey)) {
            filterMap.put(session, filter);
            return true;
        }
        return false;
    }

    public void removeFilter(Session session) { filterMap.remove(session); }

    public void processTweet (MinTweet minTweet) {
//         for each session in filterMap
        filterMap.keySet().forEach(session -> {
           if (filterMap.get(session).match(minTweet)) {
               try {
                   session.getRemote().sendString(minTweet.toString());
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        });
//         look at filter

//         apply filter to tweet

//         send when appropriate


    }

}
