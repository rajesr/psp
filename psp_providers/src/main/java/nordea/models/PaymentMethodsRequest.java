package nordea.models;

import java.util.UUID;

public class PaymentMethodsRequest implements PaymentMethods {
    private Merchant merchant;
    private UUID uuid;

    public Merchant getMerchant() {
        return merchant;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

}
