package nordea.models;

import java.util.UUID;

public class PaymentMethodsResponse implements PaymentMethods {
    private Merchant merchant;
    private UUID uuid;

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
}
