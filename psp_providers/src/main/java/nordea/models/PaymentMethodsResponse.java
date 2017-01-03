package nordea.models;

import java.util.List;
import java.util.UUID;

public class PaymentMethodsResponse implements PaymentMethods {
    private Merchant merchant;
    private UUID uuid;
    // TODO: implement payment methods
    private List paymentMethods;

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

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

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setPaymentMethods(List paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
