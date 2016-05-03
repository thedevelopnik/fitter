package com.roomforimproving.FitterSpark;

import com.roomforimproving.FitterSpark.websocket.EchoWebSocket;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/*
 * Created by davidsudia on 5/2/16.
 */
public class AppStart {
    public static void main(String[] args) {
        webSocket("/subscribe", EchoWebSocket.class);

        final Map indexMap = new HashMap();
        indexMap.put("title", "index");
        get("/", (req, res) -> new ModelAndView(indexMap, "index"), new ThymeleafTemplateEngine());

        final Map loginMap = new HashMap();
        loginMap.put("title", "login");
        get("/login", (req, res) -> new ModelAndView(loginMap, "login"), new ThymeleafTemplateEngine());
    }
}
