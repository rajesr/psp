package nordea.models;

import java.util.UUID;

import static nordea.utils.Uuid.generate;

public class PaymentMethodsRequest implements PaymentMethods {
    private Merchant merchant;
    private UUID uuid;

    public PaymentMethodsRequest() {
        this.uuid = generate();
    }

    public Merchant getMerchant() {
        return merchant;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

}
