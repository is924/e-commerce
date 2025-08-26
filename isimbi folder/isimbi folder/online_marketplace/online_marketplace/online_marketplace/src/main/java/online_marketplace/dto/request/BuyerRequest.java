package online_marketplace.dto.request;

import lombok.Data;

@Data
public class BuyerRequest {
    //private Long userId;
    private String contactInfo;
    private String preferredPaymentMethod;
}
