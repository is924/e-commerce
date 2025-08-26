package online_marketplace.dto.response;

import lombok.Data;

@Data
public class SellerResponse {
    private Long sellerId;
    private Long userId;
    private String username;
    private String companyName;
    private String contactInfo;
    private Float averageRating;
}
