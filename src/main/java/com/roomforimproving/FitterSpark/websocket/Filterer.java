package com.roomforimproving.FitterSpark.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import java.util.*;
import java.util.logging.Filter;

/*
 * Created by davidsudia on 5/5/16.
 */
public class Filterer {

    private HashMap<Session, Filters> filterMap;

    Filterer () { filterMap = new HashMap<Session, Filters>(); }

    public void addOrReplaceFilter(Session session, Filters filter) {
        filterMap.put(session, filter);
    }

    public void removeFilter(Session session) { filterMap.remove(session); }

    public void processTweet (JSONObject tweet) {


//         for each session in filterMap

//         look at filter

//         apply filter to tweet

//         send when appropriate


    }

}
