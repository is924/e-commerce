package online_marketplace.dto.response;

import lombok.Data;

@Data
public class BuyerResponse {
    private Long buyerId;
    private Long userId;
    private String username;
    private String contactInfo;
    private String preferredPaymentMethod;
}
