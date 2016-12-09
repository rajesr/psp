package nordea.paymentMethods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nordea.NordeaRestController;
import nordea.consumer.PaymentMethodReceiver;
import nordea.models.PaymentMethodsRequest;
import nordea.models.PaymentMethodsResponse;
import nordea.producer.ApiKafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Subscription;

import java.io.IOException;
import java.util.UUID;

@NordeaRestController
public class PaymentMethodsController {

    @Autowired
    private ApiKafkaSender sender;

    @Autowired
    private PaymentMethodReceiver receiver;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/payment-methods", method = RequestMethod.POST)
    public DeferredResult<String> getPaymentMethods(@RequestBody PaymentMethodsRequest paymentMethodsRequest) throws JsonProcessingException {
        DeferredResult deferred = new DeferredResult(90000l);
        UUID uuid = paymentMethodsRequest.getUUID();
        receiver.getObservable()
                .subscribe(message -> {
                    try {
                        PaymentMethodsResponse response = objectMapper.readValue((String) message, PaymentMethodsResponse.class);
                        if (response.getUUID().equals(uuid)) {
                            deferred.setResult(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

        sender.sendMessage("get_payment_methods", objectMapper.writeValueAsString(paymentMethodsRequest));
        return deferred;
    }
}
