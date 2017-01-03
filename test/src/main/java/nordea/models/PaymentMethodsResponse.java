package nordea.models;

import java.util.List;
import java.util.UUID;

public class PaymentMethodsResponse implements PaymentMethods {
    private Merchant merchant;
    private UUID uuid;
    // TODO: implement payment methods
    private List paymentMethods;

    public Merchant getMerchant() {
        return merchant;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    public List getPaymentMethods() {
        return paymentMethods;
    }
}
