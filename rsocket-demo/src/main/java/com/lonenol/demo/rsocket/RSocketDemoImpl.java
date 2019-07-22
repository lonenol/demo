package com.lonenol.demo.rsocket;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.util.DefaultPayload;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author lone
 * @date 2019-07-21
 */
public class RSocketDemoImpl extends AbstractRSocket {


    private static Logger log = LoggerFactory.getLogger(RSocketDemoImpl.class);

    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        try {
            return Mono.just(DefaultPayload.create("world"));
        } catch (Exception x) {
            return Mono.error(x);
        }
    }

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        try {
            log.info("record forget:{}", payload.getDataUtf8());
            return Mono.empty();
        } catch (Exception x) {
            return Mono.error(x);
        }
    }

    @Override
    public Flux<Payload> requestStream(Payload payload) {
        return Flux.range(0,5).map(it->DefaultPayload.create("five"));
    }

    @Override
    public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
        CountPublisher count = new CountPublisher("server");
        Flux.from(payloads).doOnComplete(count::sayResult).subscribe(count::process);
        return Flux.from(count);
    }
}
