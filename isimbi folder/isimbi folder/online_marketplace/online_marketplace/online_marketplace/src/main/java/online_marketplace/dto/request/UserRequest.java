package online_marketplace.dto.request;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String email;
    private String password;
    private String accountType; // "BUYER" or "SELLER"

    // Buyer-specific fields (optional)
    private String contactInfo;
    private String preferredPaymentMethod;

    // Seller-specific fields (optional)
    private String companyName;
}
