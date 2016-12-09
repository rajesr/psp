package nordea.models;

import java.util.UUID;

import static nordea.utils.Uuid.generate;

public class PaymentMethodsRequest implements PaymentMethods {
    private Merchant merchant;
    private UUID uuid;

    public Merchant getMerchant() {
        return merchant;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }
}
