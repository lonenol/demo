package com.lonenol.demo.rsocket;

import io.rsocket.Payload;
import io.rsocket.util.DefaultPayload;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * @author lone
 * @date 2019-07-21
 */
public class CountPublisher implements Publisher<Payload> {


    private static final Logger log = LoggerFactory.getLogger(CountPublisher.class);
    public static final Integer MAX = 60;
    private Subscriber<? super Payload> subscriber;
    private Integer cur = 1;
    private String name;


    public CountPublisher(String name) {
        this.name = name;
    }

    @Override
    public void subscribe(Subscriber<? super Payload> s) {
        this.subscriber = s;
        sendData();
    }

    private void sendData() {
        new Thread(() -> {
            IntStream.range(1, 30)
                    .mapToObj(it -> DefaultPayload.create("" + ThreadLocalRandom.current().nextInt(1,4)))
                    .forEach(subscriber::onNext);
            subscriber.onComplete();
        }).start();
    }


    public void process(Payload payload) {
        cur += Integer.valueOf(payload.getDataUtf8());
    }

    public void sayResult() {
        if(cur < MAX) {
            log.info("===={}: Great!!! I Lose, My num is {}", name,cur);
        } else {
            log.info("===={}: Yeah!!! I Win, My num is {}", name,cur);
        }
    }
}
