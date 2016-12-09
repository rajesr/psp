package nordea.consumer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;


public class PspProviderReceiver {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(PspProviderReceiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "purchase.t")
    public void receiveMessage(String message) {
        LOGGER.info("received message='{}'", message);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
