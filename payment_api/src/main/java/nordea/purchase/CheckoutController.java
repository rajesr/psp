package nordea.purchase;

import nordea.NordeaRestController;
import nordea.consumer.ApiKafkaReceiver;
import nordea.producer.ApiKafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;


@NordeaRestController
public class CheckoutController {

    @Autowired
    private ApiKafkaSender sender;

    @Autowired
    private ApiKafkaReceiver receiver;

    /**
     * @param name
     * @return
     */
    @RequestMapping("/checkout")
    public DeferredResult<String> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        DeferredResult deffered = new DeferredResult(90000l);
        receiver.getSubject()
                .subscribe(message -> {
                    deffered.setResult(message);
                    System.out.println("got something" + message);
                })
                .unsubscribe();

        sender.sendMessage("purchase.t", name);


        return deffered;


    }

}