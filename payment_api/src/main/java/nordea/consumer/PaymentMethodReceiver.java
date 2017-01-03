package nordea.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import rx.Observable;
import rx.subjects.PublishSubject;

import java.io.IOException;

@Component
public class PaymentMethodReceiver {

    PublishSubject<Object> publishSubject = PublishSubject.create();

    private static final Logger LOGGER = LoggerFactory
            .getLogger(PaymentMethodReceiver.class);

    @KafkaListener(topics = "get_payment_methods_success")
    public void getMerchantData(String message) throws IOException {
        LOGGER.info("received message='{}' send to stream", message);
        publishSubject.onNext(message);
    }

    public Observable<Object> getObservable() {
        return publishSubject;
    }
}
