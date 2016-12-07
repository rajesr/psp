package nordea.consumer;


import java.util.concurrent.CountDownLatch;

import nordea.producer.MerchantBrokerSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;


public class MerchantBrokerReceiver {

    @Autowired
    private MerchantBrokerSender sender;


    private static final Logger LOGGER = LoggerFactory
            .getLogger(MerchantBrokerReceiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "purchase.t")
    public void receiveMessage(String message) {
        LOGGER.info("received message='{}'", message);
        sender.sendMessage("purchase.ready", message + " done");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
