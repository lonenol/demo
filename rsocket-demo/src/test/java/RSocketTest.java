import com.lonenol.demo.rsocket.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author lone
 * @date 2019-07-21
 */
public class RSocketTest {

    private Logger log = LoggerFactory.getLogger(getClass());

    private static HelloServer server;

    @BeforeAll
    static void beforeTest() {
        server = new HelloServer();
    }

    @AfterAll
    static void afterTest() {
        server.dispose();
    }

    @Test
    void testRequestResponse() {
        RequestResponseClient client = new RequestResponseClient();
        Mono<String> resp = client.sendRequest("hello");
        Assertions.assertEquals("world", resp.block());
    }

    @Test
    void testFireAndForget() {
        FireAndForgetClient client = new FireAndForgetClient();
        client.biuBiuBiu();
    }

    @Test
    void testRequestChannel() {
        RequestStreamClient client = new RequestStreamClient();
        Flux<String> flux = client.request();
        flux.subscribe(it -> {
            log.info("receive:{}",it);
            Assertions.assertEquals(it, "five");
        });
        flux.blockLast();
    }

    @Test
    void testChannel() {
        ChannelClient client = new ChannelClient();
        client.battle();
    }
}
