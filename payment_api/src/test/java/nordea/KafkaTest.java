package nordea;

import nordea.consumer.ApiKafkaReceiver;
import nordea.producer.ApiKafkaSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaTest {

    @Autowired
    private ApiKafkaSender sender;

    @Autowired
    private ApiKafkaReceiver receiver;

    @Test
    public void testReceiver() throws Exception {
        Thread.sleep(2000);
        sender.sendMessage("purchase.t", "Hello Kafka!");
        Thread.sleep(2000);
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(receiver.getLatch().getCount()).isEqualTo(0);
    }


}