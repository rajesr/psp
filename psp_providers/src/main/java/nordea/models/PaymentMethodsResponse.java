package nordea.models;

import java.util.UUID;

public class PaymentMethodsResponse implements PaymentMethods {
    private Merchant merchant;
    private UUID uuid;
    // TODO: implement payment methods
    private String paymentMethods;

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(String paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
