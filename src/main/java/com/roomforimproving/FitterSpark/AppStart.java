package com.roomforimproving.FitterSpark;

import com.roomforimproving.FitterSpark.websocket.EchoWebSocket;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import spark.Request;
import spark.Response;
import spark.Route;

/*
 * Created by davidsudia on 5/2/16.
 */
public class AppStart {
    public static void main(String[] args) {
        ThymeleafTemplateEngine thymeleafTemplateEngine = new ThymeleafTemplateEngine();

        webSocket("/subscribe", EchoWebSocket.class);

        get("/", (req, res) -> {
            final Map indexMap = new HashMap();
            indexMap.put("title", "index");
            ModelAndView modelAndView = new ModelAndView(indexMap, "index");
            res.status(200);
            return thymeleafTemplateEngine.render(modelAndView);
        });

        get("/login", (req, res) -> {
            final Map indexMap = new HashMap();
            indexMap.put("title", "login");
            ModelAndView modelAndView = new ModelAndView(indexMap, "login");
            res.status(200);
            return thymeleafTemplateEngine.render(modelAndView);
        });
    }
}
