package nordea;

import com.fasterxml.jackson.databind.ObjectMapper;
import nordea.models.Merchant;
import nordea.models.PaymentMethodsRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaTest {

    final ObjectMapper mapper = new ObjectMapper();

    private String getBaseUrl() {
        return "http://localhost:8080";
    }

    /**
     * @param requestMappingUrl
     *            should be exactly the same as defined in your RequestMapping
     *            value attribute (including the parameters in {})
     *            RequestMapping(value = yourRestUrl)
     * @param serviceReturnTypeClass
     *            should be the the return type of the service
     * @param parametersInOrderOfAppearance
     *            should be the parameters of the requestMappingUrl ({}) in
     *            order of appearance
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
     *
     * @param requestMappingUrl
     *            should be exactly the same as defined in your RequestMapping
     *            value attribute (including the parameters in {})
     *            RequestMapping(value = yourRestUrl)
     * @param serviceReturnTypeClass
     *            should be the the return type of the service
     * @param objectToPost
     *            Object that will be posted to the url
     * @return
     */
    protected <T> T postEntity(final String requestMappingUrl, final Class<T> serviceReturnTypeClass, final Object objectToPost) {
        final TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept-Charset", "UTF-8");
        try {
            final HttpEntity<String> requestEntity = new HttpEntity<String>(mapper.writeValueAsString(objectToPost), headers);
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
        paymentMethodsRequest.setMerchant(new Merchant("test"));
        Object o = postEntity("/api/payment-methods", Object.class, paymentMethodsRequest);
        System.out.println(o);
    }


}