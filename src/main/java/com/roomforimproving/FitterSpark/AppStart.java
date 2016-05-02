package com.roomforimproving.FitterSpark;

import com.roomforimproving.FitterSpark.websocket.EchoWebSocket;

import static spark.Spark.*;

/*
 * Created by davidsudia on 5/2/16.
 */
public class AppStart {
    public static void main(String[] args) {
        webSocket("/subscribe", EchoWebSocket.class);
        init();
    }
}
