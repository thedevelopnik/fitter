package com.roomforimproving.FitterSpark.websocket;

import com.roomforimproving.FitterSpark.database.Users;
import com.roomforimproving.FitterSpark.twitter.MinTweet;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.*;


/*
 * Created by davidsudia on 5/5/16.
 */
public class Filterer {

    private static HashMap<Session, Filters> filterMap;

    public static void init () { filterMap = new HashMap<Session, Filters>(); }

    public static Boolean addOrReplaceFilter(Session session, Filters filter) {
        String apiKey = filter.getApiKey();
        if (Users.findValidApiKey(apiKey)) {
            filterMap.put(session, filter);
            return true;
        }
        return false;
    }

    public static void removeFilter(Session session) { filterMap.remove(session); }

    public static void processTweet (MinTweet minTweet) {
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
    }

}
