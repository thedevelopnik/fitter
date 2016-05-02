package com.roomforimproving.FitterSpark.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
 * Created by davidsudia on 5/2/16.
 */
@WebSocket
public class EchoWebSocket {

    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<Session>();

    @OnWebSocketConnect
    public void connected(Session session) {
        sessions.add(session);
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        sessions.remove(session);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        System.out.println("Got: " + message);
        session.getRemote().sendString(message);
    }
}
