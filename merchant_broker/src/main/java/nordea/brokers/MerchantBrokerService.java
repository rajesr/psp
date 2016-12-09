package nordea.brokers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nordea.producer.MerchantBrokerSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MerchantBrokerService {

    @Autowired
    private MerchantBrokerSender sender;


    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MerchantBrokerService.class);

    @KafkaListener(topics = "get_payment_methods")
    public void getMerchantData(String message) throws IOException {
        LOGGER.info("received message='{}'", message);
        // include merchant here
        sender.sendMessage("verifone_get_payment_methods", message);
    }

}
