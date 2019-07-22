package com.lonenol.demo.rsocket;

import io.rsocket.AbstractRSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import static com.lonenol.demo.rsocket.Constant.IP;
import static com.lonenol.demo.rsocket.Constant.TCP_PORT;

/**
 * @author lonenol
 * @date 2019-07-21
 */
public class HelloServer {

    private static final Logger log = LoggerFactory.getLogger(HelloServer.class);

    private final Disposable server;

    public HelloServer() {
        this.server = RSocketFactory.receive()
                .acceptor((setupPayload, reactiveSocket) -> Mono.just(new RSocketDemoImpl()))
                .transport(TcpServerTransport.create(IP, TCP_PORT))
                .start()
                .doOnNext(x -> log.info("Server started"))
                .subscribe();

    }

    public void dispose() {
        this.server.dispose();
    }
}

