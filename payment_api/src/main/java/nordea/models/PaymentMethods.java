package nordea.models;

import java.util.UUID;

public interface PaymentMethods {
    Merchant getMerchant();
    UUID getUUID();
}
