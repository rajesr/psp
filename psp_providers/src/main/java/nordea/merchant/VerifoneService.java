package nordea.merchant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import nordea.models.PaymentMethodsRequest;
import nordea.models.PaymentMethodsResponse;
import nordea.producer.PspProviderSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VerifoneService {

    @Autowired
    private PspProviderSender sender;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(VerifoneService.class);

    @KafkaListener(topics = "verifone_get_payment_methods")
    public void getPaymentMethods(String message) throws IOException, InterruptedException {
        LOGGER.info("received message='{}'", message);
        PaymentMethodsRequest paymentMethodsRequest = objectMapper.readValue(message, PaymentMethodsRequest.class);
        PaymentMethodsResponse paymentMethodsResponse = new PaymentMethodsResponse();
        paymentMethodsResponse.setMerchant(paymentMethodsRequest.getMerchant());
        paymentMethodsResponse.setUuid(paymentMethodsRequest.getUuid());
        // TODO: fetch payment methods and return as json string
        paymentMethodsResponse.setPaymentMethods(Lists.newArrayList());
        //Thread.sleep((long) (Math.random() * 1000));
        sender.sendMessage("get_payment_methods_success", objectMapper.writeValueAsString(paymentMethodsResponse));
    }
}
