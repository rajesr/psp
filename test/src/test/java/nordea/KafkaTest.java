package nordea;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import nordea.models.Merchant;
import nordea.models.PaymentMethodsRequest;
import nordea.models.PaymentMethodsResponse;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class KafkaTest {

    final ObjectMapper mapper = new ObjectMapper();

    private String getBaseUrl() {
        return "http://localhost:8080";
    }

    /**
     * @param requestMappingUrl             should be exactly the same as defined in your RequestMapping
     *                                      value attribute (including the parameters in {})
     *                                      RequestMapping(value = yourRestUrl)
     * @param serviceReturnTypeClass        should be the the return type of the service
     * @param parametersInOrderOfAppearance should be the parameters of the requestMappingUrl ({}) in
     *                                      order of appearance
     * @return the result of the service, or null on error
     */
    protected <T> T getEntity(final String requestMappingUrl, final Class<T> serviceReturnTypeClass, final Object... parametersInOrderOfAppearance) {
        // Make a rest template do do the service call
        final TestRestTemplate restTemplate = new TestRestTemplate();
        // Add correct headers, none for this example
        final HttpEntity<String> requestEntity = new HttpEntity<String>(new HttpHeaders());
        try {
            // Do a call the the url
            final ResponseEntity<T> entity = restTemplate.exchange(getBaseUrl() + requestMappingUrl, HttpMethod.GET, requestEntity, serviceReturnTypeClass,
                    parametersInOrderOfAppearance);
            // Return result
            return entity.getBody();
        } catch (final Exception ex) {
            // Handle exceptions
        }
        return null;
    }

    /**
     * @param requestMappingUrl      should be exactly the same as defined in your RequestMapping
     *                               value attribute (including the parameters in {})
     *                               RequestMapping(value = yourRestUrl)
     * @param serviceReturnTypeClass should be the the return type of the service
     * @param objectToPost           Object that will be posted to the url
     * @return
     */
    protected <T> T postEntity(final String requestMappingUrl, final Class<T> serviceReturnTypeClass, final Object objectToPost) {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonMessageConverter.setObjectMapper(mapper);
        messageConverters.add(jsonMessageConverter);

        restTemplate.getMessageConverters().add(jsonMessageConverter);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept-Charset", "UTF-8");
        try {
            final HttpEntity<String> requestEntity = new HttpEntity<>(mapper.writeValueAsString(objectToPost), headers);
            final ResponseEntity<T> entity = restTemplate.postForEntity(getBaseUrl() + requestMappingUrl, requestEntity, serviceReturnTypeClass);
            return entity.getBody();
        } catch (final Exception ex) {
            // Handle exceptions
        }
        return null;
    }

    @Test
    public void testGetPaymentMethods() throws Exception {
        PaymentMethodsRequest paymentMethodsRequest = new PaymentMethodsRequest();
        Merchant test = new Merchant();
        test.setName("test");
        paymentMethodsRequest.setMerchant(test);
        String response = postEntity("/api/payment-methods", String.class, paymentMethodsRequest);
        PaymentMethodsResponse paymentMethodsResponse = mapper.readValue(response, PaymentMethodsResponse.class);

        assertThat(paymentMethodsResponse.getUuid(), is(not(nullValue())));
        assertThat(paymentMethodsResponse.getPaymentMethods().isEmpty(), is(true));
    }

    @Test
    public void testGetPaymentMethdodsConcurrently() {
        List<Thread> threads = Lists.newArrayList();
        AtomicLong value = new AtomicLong(0l);
        int THREAD_COUNT = 1;
        for (int i = 0; i < THREAD_COUNT; i++) {
            long currentTimeMillis = System.currentTimeMillis();
            int finalI = i;
            threads.add(new Thread(() -> {
                String name = "test" + Integer.toString(finalI);
                PaymentMethodsRequest paymentMethodsRequest = new PaymentMethodsRequest();
                Merchant test = new Merchant();
                test.setName(name);
                paymentMethodsRequest.setMerchant(test);
                String response = postEntity("/api/payment-methods", String.class, paymentMethodsRequest);
                PaymentMethodsResponse paymentMethodsResponse = null;
                try {
                    paymentMethodsResponse = mapper.readValue(response, PaymentMethodsResponse.class);
                } catch (Exception e) {
                    System.out.println("exception");
                }
                assertThat(paymentMethodsResponse.getUuid(), is(not(nullValue())));
                assertThat(paymentMethodsResponse.getMerchant().getName(), is(name));
                assertThat(paymentMethodsResponse.getPaymentMethods().isEmpty(), is(true));
                long millis = System.currentTimeMillis() - currentTimeMillis;
                value.addAndGet(millis);
            }));
        }

        threads.forEach(thread -> {
            thread.start();
        });
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("average time: " + value.get() / THREAD_COUNT  + " ms");
    }
}