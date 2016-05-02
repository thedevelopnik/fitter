package com.roomforimproving.FitterSpark.twitter;

/*
 * Created by davidsudia on 5/2/16.
 */

import com.roomforimproving.FitterSpark.AppConfig;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/*
 * Created by davidsudia on 5/2/16.
 */
public class SampleStream implements Runnable {

    private Queue<Session> sessions;

    public SampleStream(Queue<Session> sessions) {
        this.sessions = sessions;
        (new Thread(this)).start();
    }

    public void run() {
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

        StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();

        endpoint.stallWarnings(false);

        String consumerKey = AppConfig.CONSUMER_KEY;
        String consumerSecret = AppConfig.CONSUMER_SECRET;
        String token = AppConfig.TOKEN;
        String secret = AppConfig.SECRET;

        Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);

        BasicClient client = new ClientBuilder()
                .name("sampleClientBuilder")
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(queue))
                .build();

        client.connect();

        while (true) {
            if (client.isDone()) {
                System.out.println("Error: " + client.getExitEvent().getMessage());
                break;
            }

            try {
                String msg = queue.poll(5, TimeUnit.SECONDS);
                JSONObject obj;
                obj = new JSONObject(msg);
                System.out.println(obj);
                if (obj.has("text")) {
                    for (Session session : sessions) {
                        try {
                            session.getRemote().sendString(obj.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (JSONException ex) {
                System.out.println("JSON Error: " + ex);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        client.stop();

        System.out.printf("The client read %d messages!\n", client.getStatsTracker().getNumMessages());
    }
}

