package com.lonenol.demo.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Flux;

import static com.lonenol.demo.rsocket.Constant.IP;
import static com.lonenol.demo.rsocket.Constant.TCP_PORT;

/**
 * @author lone
 * @date 2019-07-21
 */
public class ChannelClient {

    private final RSocket rSocket;

    public ChannelClient() {
        this.rSocket = RSocketFactory.connect()
                .transport(TcpClientTransport.create(IP, TCP_PORT))
                .start()
                .block();
    }


    public void battle() {
        CountPublisher count = new CountPublisher("client");
        Flux<Payload> flux = rSocket.requestChannel(Flux.from(count)).doOnNext(count::process);
        flux.blockLast();
        count.sayResult();
    }
}
