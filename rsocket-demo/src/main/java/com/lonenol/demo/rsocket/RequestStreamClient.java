package com.lonenol.demo.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Flux;

import static com.lonenol.demo.rsocket.Constant.IP;
import static com.lonenol.demo.rsocket.Constant.TCP_PORT;

/**
 * @author lone
 * @date 2019-07-21
 */
public class RequestStreamClient {
    private final RSocket rSocket;

    public RequestStreamClient() {
        this.rSocket = RSocketFactory.connect()
                .transport(TcpClientTransport.create(IP, TCP_PORT))
                .start()
                .block();
    }

    public Flux<String> request() {
        return rSocket.requestStream(DefaultPayload.create("give me five"))
                .map(Payload::getDataUtf8);
    }
}
