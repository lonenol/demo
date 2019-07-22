package com.lonenol.demo.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.lonenol.demo.rsocket.Constant.IP;
import static com.lonenol.demo.rsocket.Constant.TCP_PORT;

/**
 * @author lone
 * @date 2019-07-21
 */
public class FireAndForgetClient {

    private final RSocket rSocket;

    public FireAndForgetClient() {
        this.rSocket = RSocketFactory.connect()
                .transport(TcpClientTransport.create(IP, TCP_PORT))
                .start()
                .block();
    }


    public void biuBiuBiu(){
        IntStream.range(0,10)
                .mapToObj(it-> DefaultPayload.create("biubiubiu"+it))
                .forEach(it -> rSocket.fireAndForget(it).block());
    }
}
