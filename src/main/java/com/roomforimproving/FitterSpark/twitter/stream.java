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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/*
 * Created by davidsudia on 5/2/16.
 */
public class SampleStream implements Runnable {

    public SampleStream () {
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
        try {
            PrintWriter writer = new PrintWriter("OneThousandTweets.json", "UTF-8");
            writer.print("[");

            for (int msgRead = 0; msgRead < 10; msgRead ++) {
                if (client.isDone()) {
                    System.out.println("Error: " + client.getExitEvent().getMessage());
                    break;
                }

                try {
                    String msg = queue.poll(5, TimeUnit.SECONDS);
                    JSONObject obj;
                    obj = new JSONObject(msg);
                    if (obj.has("text")) {
                        System.out.println(msgRead);
                        writer.println(obj.toString() + ",");
                    }
                } catch (JSONException ex) {
                    System.out.println("JSON Error: " + ex);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            writer.println("]");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.stop();

        System.out.printf("The client read %d messages!\n", client.getStatsTracker().getNumMessages());
    }
}

