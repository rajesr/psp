package nordea.consumer;


import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import rx.subjects.BehaviorSubject;


public class ApiKafkaReceiver {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ApiKafkaReceiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    BehaviorSubject<Object> subject = BehaviorSubject.create("default");

    @KafkaListener(topics = "purchase.ready")
    public void receiveMessage(String message) {
        subject.onNext(message);
        LOGGER.info("api received message='{}'", message);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public BehaviorSubject<Object> getSubject() {
        return subject;
    }
}
