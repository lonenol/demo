package com.lonenol.demo.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Mono;

import static com.lonenol.demo.rsocket.Constant.IP;
import static com.lonenol.demo.rsocket.Constant.TCP_PORT;

/**
 * @author lone
 * @date 2019-07-21
 */
public class RequestResponseClient {

    private final RSocket rSocket;

    public RequestResponseClient() {
        this.rSocket = RSocketFactory.connect()
                .transport(TcpClientTransport.create(IP, TCP_PORT))
                .start()
                .block();
    }

    public Mono<String> sendRequest(String string) {
        return rSocket
                .requestResponse(DefaultPayload.create(string))
                .map(Payload::getDataUtf8)
                .onErrorReturn("error");
    }

    public void dispose() {
        rSocket.dispose();
    }
}
