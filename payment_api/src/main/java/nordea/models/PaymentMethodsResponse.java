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
    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
